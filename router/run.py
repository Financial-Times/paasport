#!/usr/bin/python
# Layer 7 Router
#
from BaseHTTPServer import BaseHTTPRequestHandler,HTTPServer
import json, re


PORT_NUMBER = 8080

class UserError(Exception):
	def __init__(self, value):
		self.value = value
	def __str__(self):
		return repr(self.value)


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
				# TODO: sanitise hostname - this currently presents a potential security vulnerability
				with open('data/'+hostname+'.json', 'r') as outfile:
					output = outfile.read()

				self.send_response(200)
				self.send_header('Content-type','text/json')
				self.end_headers()
				self.wfile.write(output)
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
		except e:
			self.send_response(500)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write("Internal Error:" + str(e) + "\n")
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

				# write the json to disk in case we need it later
				# TODO: sanitise hostname - this currently presents a potential security vulnerability
				with open('data/'+hostname+'.json', 'w') as outfile:
					json.dump(data, outfile)

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
		except e:
			self.send_response(500)
			self.send_header('Content-type','text/plain')
			self.end_headers()
			self.wfile.write("Internal Error:" + str(e) + "\n")
	
try:
	#Create a web server and define the handler to manage the
	#incoming request
	server = HTTPServer(('', PORT_NUMBER), myHandler)
	print 'Started httpserver on port ' , PORT_NUMBER
	
	server.serve_forever()

except KeyboardInterrupt:
	print '^C received, shutting down the web server'
	server.socket.close()
	
