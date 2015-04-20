#!/usr/bin/python

import web
import json

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
		'/clusters/([0-9a-zA-Z\-]+)', 'cluster',
		'/clusters/?', 'cluster_collection',
		'/clusters/([0-9a-zA-Z\-]+)/machines/?', 'machines',
		'/clusters/([0-9a-zA-Z\-]+)/machines/?', 'machines',
)


app = web.application(mappings, globals())

class cluster:
	def GET(self, id):
		'''Returns single cluster
		'''
		return "ID, " + str(id)

	def DELETE(self, id):
		return "DONE"

	def PATCH(self, id):
		return "DONE"

class machines:
	def GET(self, clusterId):
		return "machines of " + str(clusterId)
	def POST(self, clusterId):
		return Machine.create_new(json.loads(web.data()))

class cluster_collection:
	def GET(self):
		''' Returns all clsuters '''
		return ''
	def POST(self):
		''' Return '''
		return web.data()

if __name__ == '__main__':
	app.run()
