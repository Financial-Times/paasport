#!/usr/bin/python

import os
import web
import json
import models.db

from models.machine import Machine

'''A system to create groups of machines (clusters). With methods to:
* create a cluster
* list clusters
* Retreive cluster metadata
* create machines inside cluster
* destroy a machine inside a cluster
* list machines in a cluster
* retrieve machine metdata
'''

mappings = (
		'/clusters/([0-9a-zA-Z\-]+)/?', 'cluster',
		'/clusters/?', 'cluster_collection',
		'/clusters/([0-9a-zA-Z\-]+)/machines/?', 'machine_collection',
		'/clusters/([0-9a-zA-Z\-]+)/machines/([0-9a-zA-Z\-]+)/?', 'machine',
)


class ProvisionerApp(web.application):
	def run(self, port=8080, *middleware):
		func = self.wsgifunc(*middleware)
		return web.httpserver.runsimple(func, ('0.0.0.0', port))

port = int(os.environ['PORT']) if 'PORT' in os.environ else 8080
app = ProvisionerApp(mappings, globals())

class cluster:
	def GET(self, id):
		'''Returns single cluster
		'''
		return "ID, " + str(id)

	def DELETE(self, id):
		return "DONE"

	def PATCH(self, id):
		return "DONE"

class machine_collection:
	def GET(self, clusterId):
		return "machines of " + str(clusterId)

	def POST(self, clusterId):
		data = json.loads(web.data())
		return Machine.create_new(data)

class machine:
	def GET(self, clusterId, machineId):
		pass

	def DELETE(self, clusterId, machineId):
		# TODO get the region from the database
		region = 'eu-west-1'
		return Machine.delete_instance(machineId, region)

class cluster_collection:
	def GET(self):
		''' Returns all clsuters '''
		clusters = models.db.get_clusters()
		data = []
		for cluster in clusters:
			data.append({'name':cluster.name, 'owner':cluster.owner, 'metadata': cluster.metadata})


		return ''
	def POST(self):
		''' Return '''
		return web.data()

if __name__ == '__main__':
	app.run(port=port)
