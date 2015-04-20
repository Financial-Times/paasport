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
	'/cluster', 'cluster',
)


app = web.application(mappings, globals())

class cluster:
	def GET(self):
		'''Returns all clusters
		'''
		return "Many clusters"
	def POST(self):
		'''Creates a cluster
		data required:
		{
			'name':		'some sort of name'
			'owner'		'Willem Koopman'
			'metadata'	'json blob'
		}
		'''
		return web.data()

if __name__ == '__main__':
	app.run()
