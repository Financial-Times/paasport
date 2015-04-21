#!/usr/bin/python
# Glue stuff together
#

import urllib, json, urllib2, sys
if len(sys.argv) < 2:
	sys.exit("Specify cluster id")

id = sys.argv[1]
url = "http://paasport-provisioner.herokuapp.com/clusters/"+id
response = urllib.urlopen(url);
data = json.loads(response.read())

machineresponse = urllib.urlopen(url+"/machines");
machinedata = json.loads(machineresponse.read())

hostname = data['name']+".paasport.labs.ft.com"

routerdata = {
	"machines": [],
}

for machine in machinedata:
	if len(machine["hostname"]) > 0:
		routerdata["machines"].append(machine["hostname"]+":8080")

req = urllib2.Request("http://router.paasport.labs.ft.com/service/"+hostname, data=json.dumps(routerdata))
req.get_method = lambda: 'PUT'
response = urllib2.urlopen(req)

print "Cluster "+id+" now available at " + hostname+"\n"
