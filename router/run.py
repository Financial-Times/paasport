#!/usr/bin/python
# Layer 7 Router
#
from BaseHTTPServer import BaseHTTPRequestHandler,HTTPServer
import json, re, os, subprocess


PORT_NUMBER = 8080

class UserError(Exception):
	def __init__(self, value):
		self.value = value
	def __str__(self):
		return repr(self.value)



def writedefaultvcl ():
	vcl = "vcl 4.0;\n\nimport directors;\n"
	services = []
	for filename in os.listdir('/etc/varnish/services'):
		vcl += "include \"services/"+filename+"\";\n"
		services.append(filename.split('.',1)[0])

	vcl += "\n\nbackend default {\n\t.host = \"127.0.0.1\";\n\t.port = \"8080\";\n}\n\nsub vcl_recv {\n"

	for service in services:
		vcl += "\tcall service_"+service+";\n"
	vcl += "}\n\n"


	subprocess.check_call(["sudo", "service", "varnish", "reload"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)



	# Write the VCL to disk
	with open('/etc/varnish/default.vcl', 'w') as outfile:
		outfile.write(vcl)



#This class will handles any incoming request from
#the browser 
class myHandler(BaseHTTPRequestHandler):
	servicematch = re.compile('/service/(?P<hostname>[^/]+)')
	
	#Handler for the GET requests
	def do_GET(self):
		try:
			service = self.servicematch.search(self.path)
			if service is not None:
				hostname = service.group('hostname')

				# write the json to disk in case we need it later
				try:
					with open('data/'+re.sub(r'\W+', '', hostname)+'.json', 'r') as outfile:
						output = outfile.read()
				except IOError as e:
					raise UserError("Service Not Found with hostname "+hostname)

				self.send_response(200)
				self.send_header('Content-type','text/json')
				self.end_headers()
				self.wfile.write(output)
				return
			else:
				raise UserError("Not Found")
		except UserError as e:
			if "Not Found" in e.value:
				self.send_response(404)
			else:
				self.send_response(400)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write(e.value + "\n")
			return
		except Exception as e:
			self.send_response(500)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write("Internal Error: " + str(e) + "\n")
	def do_PUT(self):
		try:
			service = self.servicematch.search(self.path)
			if service is not None:
				hostname = service.group('hostname')
				if not 'Content-Length' in self.headers:
					raise UserError("No Content-Length Header")
				varLen = int(self.headers['Content-Length'])
				jsoninput = self.rfile.read(varLen)
				try:
					data = json.loads(jsoninput)
				except:
					raise UserError("Expected JSON")

				# Validate inputs
				machines = data.setdefault('machines', [])
				addons = data.setdefault('addons', [])
				if not isinstance(machines, list):
					raise UserError('The property "machines" must be a list')
				if not isinstance(addons, list):
					raise UserError('The property "addons" must be a list')
				if len(machines) == 0:
					raise UserError('Each service should have atleast one machine to serve traffic from.  Syntax: {"machines":["hostname-a"]}')
				
				# Put sanitised inputs back on the data dictionary
				# (leave any other keys untouched)
				data['machines'] = machines
				data['addons'] = addons
				data['directorname'] = re.sub(r'\W+', '', hostname)

				# write the json to disk in case we need it later
				with open('data/'+data['directorname']+'.json', 'w') as outfile:
					json.dump(data, outfile)

				vcl = ""
				backendnames = []
				for backend in data['machines']:
					if ':' in backend:
						(backendhost, backendport) = backend.rsplit(':', 2)
					else:
						backendhost = backend
						backendport = "80"
					backendname = data['directorname'] + re.sub(r'\W+', '', backend)
					backendnames.append(backendname)
					vcl += "backend "+backendname+" {\n\t.host = \""+backendhost+"\";\n\t.port = \""+backendport+"\";\n\t.probe = {\n\t\t.url = \"/__gtg\";\n\t}\n}\n\n"
				
				vcl += "sub vcl_init {\n\tnew "+data['directorname']+" = directors.random();\n"
				
				for backendname in backendnames:
					vcl += "\t"+data['directorname']+".add_backend("+backendname+", 1);\n"

				vcl += "}\n\n"

				vcl += "sub service_"+data['directorname']+" {\n\tif (req.http.Host == \""+hostname+"\") {\n\t\tset req.backend_hint = "+data['directorname']+".backend();\n\t}\n"

				# TODO: addons go here

				vcl += "}\n\n"


				# Write the VCL to disk
				with open('/etc/varnish/services/'+data['directorname']+'.vcl', 'w') as outfile:
					outfile.write(vcl)

				writedefaultvcl()

				self.send_response(201)
				self.send_header('Content-type','text/plain')
				self.end_headers()
				self.wfile.write("Service Set\n")
				return
			else:
				raise UserError("Not Found")
		except UserError as e:
			if e.value == "Not Found":
				self.send_response(404)
			else:
				self.send_response(400)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write(e.value + "\n")
			return
		except Exception as e:
			self.send_response(500)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write("Internal Error: " + str(e) + "\n")
	
try:
	#Create a web server and define the handler to manage the
	#incoming request
	server = HTTPServer(('', PORT_NUMBER), myHandler)
	print 'Started httpserver on port ' , PORT_NUMBER
	
	server.serve_forever()

except KeyboardInterrupt:
	print '^C received, shutting down the web server'
	server.socket.close()
	
