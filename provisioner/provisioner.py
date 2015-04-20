#!/usr/bin/python

import web
import json

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
		'/clusters/?', 'clusters',
)


app = web.application(mappings, globals())

class cluster:
	def GET(self, id):
		'''Returns single cluster
		'''
		return "ID, " + str(id)

class clusters:
	def GET(self):
		''' Returns all clsuters '''
		return ''
	def POST(self):
		''' Return '''
		return web.data()

if __name__ == '__main__':
	app.run()
