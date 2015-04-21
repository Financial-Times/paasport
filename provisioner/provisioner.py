#!/usr/bin/python

import os
import web
import json
import models.db
import models.machine

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
		'/nursery/?', 'nursery_manager'
)


class ProvisionerApp(web.application):
	def run(self, port=8080, *middleware):
		func = self.wsgifunc(*middleware)
		return web.httpserver.runsimple(func, ('0.0.0.0', port))

port = int(os.environ['PORT']) if 'PORT' in os.environ else 8080
app = ProvisionerApp(mappings, globals())

class cluster:
	def GET(self, id):
		return json.dumps(models.db.get_cluster_by_id(id)[0])

	def DELETE(self, id):
		return json.dumps(models.db.delete_cluster_by_id(id))

class machine_collection:
	def GET(self, cluster_id):
		return json.dumps(models.machine.get_instances_in_cluster(cluster_id))

	def POST(self, cluster_id):
		data = json.loads(web.data())
		return json.dumps(models.machine.create_many(data, cluster_id))

class machine:
	def GET(self, clusterId, machineId):
		pass

	def DELETE(self, clusterId, machineId):
		pass

class cluster_collection:
	def GET(self):
		''' Returns all clsuters '''
		clusters = models.db.get_clusters()
		data = []
		for cluster in clusters:
			data.append({'name':cluster.name, 'owner':cluster.owner,
				'metadata': cluster.metadata, 'id': cluster.id})

		return json.dumps(data)

	def POST(self):
		''' Return '''
		data = json.loads(web.data())
		clusterId = models.db.create_cluster(data['name'], data['owner'], data['metadata'])
		return json.dumps({
			'name': data['name'],
			'owner': data['owner'],
			'metadata': data['metadata'],
			'id': clusterId
		})

class nursery_manager:
	def POST(self):
		model.machine.create_new_in_nursery()
		return "creating"

if __name__ == '__main__':
	app.run(port=port)
