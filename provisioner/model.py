#!/usr/bin/python
'''
Map the database to functions
'''

import web

db = web.database(dbn='sqlite', db='database.db')

def get_clusters():
	'''returns all the clusters:
	{
		[
			id:		"001",
			name:		"dave the god of biscuits",
			owner:		"Dave, The man",
			metadata:	"MAny things that are useful"
		]
	}
	Doesn't return the special first cluster that looks after machines that are on
	and provisioned but not assigned to a cluster.
	'''

	return db.select('cluster').list()

def get_machines_by_cluster_name(cluster_name):
	'''
	takes a cluster name and returns all the machines associated with it
	'''
	return db.query("SELECT * FROM machines WHERE machines.cluster_id = (SELECT id FROM cluster WHERE name =='{0}')".format(cluster_name)).list()

def get_machines_by_cluster_id(cluster_name):
	'''
	takes a cluster name and returns all the machines associated with it
	'''
	return db.query("SELECT * FROM machines WHERE machines.cluster_id = {0})".format(cluster_name)).list()

def create_cluster(cluster_name, cluster_owner, cluster_metadata):
	'''
	creates a cluster with the supplied name, owner and metadata
	returns the new cluster ID
	'''

	#first let us see if we have a cluster called that already:
	used_already = db.query("select name from cluster where name = '{0}'".format(cluster_name)).list()
	if used_already == [] :
		print "Woo we don't have a name clash"
	else:
		print "Cluster {0} already exsists".format(cluster_name)
		raise ValueError('The cluster name already exsists')

	#yes we have a unique name, begin the insert
	cluster_id = db.insert('cluster', name = cluster_name, owner = cluster_owner, metadata = cluster_metadata )
	print cluster_id

def test(name):
	test = db.select('cluster', where='id=1').list()
	return test




