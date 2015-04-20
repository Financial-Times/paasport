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

def create_machine_by_id(new_instance_id, machine_name, machine_state, machine_metadata, new_cluster_id):
	'''
	creates a machine and assosisates it with a cluster id
	'''
	machine_id = db.insert('machines', name=machine_name, instance_id= new_instance_id, state=machine_state, metadata=machine_metadata, cluster_id = new_cluster_id )
	print machine_id


def create_machine_by_name(new_instance_id, machine_name, machine_state, machine_metadata, cluster_name):
	'''
	creates a machine and assosiates it with a cluster name
	'''
	#get the cluster ID from the name
	cluster_id = db.query("SELECT id FROM cluster WHERE name = '{0}'".format(cluster_name))
	if cluster_id == []:
		raise ValueError('Cluster does not exsist, cannot attach machine')
	#now pass the id onto the create_machine_by_id function
	create_machine_by_id(new_instance_id, machine_name, machine_state, machine_metadata, cluster_id[0].id)


def test(name):
	test = db.select('cluster', where='id=1').list()
	return test




