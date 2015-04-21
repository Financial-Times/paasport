#!/usr/bin/python
'''
Map the database to functions

The Data is returned in the web.py kinda way, that is as a Storage object.

This can be converted to a dict, if you so wish, or you can interrogate it as follows:
data =  model.get_machines_by_cluster_name('pare')
for datum in data:
	    print datum.name

'''

import web

import os
import psycopg2
import urlparse

urlparse.uses_netloc.append("postgres")
url = urlparse.urlparse(os.environ["DATABASE_URL"])

db = web.database(dbn='postgres', db=url.path[1:], user=url.username,
		password=url.password, host=url.hostname, port=url.port)

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

	return db.query('SELECT * from cluster')

def get_cluster_by_id(cluster_id):
	return db.query("SELECT * FROM cluster WHERE id = '{0}'".format(cluster_id)).list()

def delete_cluster_by_id(cluster_id):
	return db.delete('cluster', where="id = '{0}'".format(cluster_id))

def get_machines_by_cluster_name(cluster_name):
	'''
	takes a cluster name and returns all the machines associated with it
	'''
	return db.query("SELECT * FROM machines WHERE machines.cluster_id = (SELECT id FROM cluster WHERE name =='{0}')".format(cluster_name)).list()

def get_machines_by_cluster_id(cluster_name):
	'''
	takes a cluster name and returns all the machines associated with it
	'''
	return db.query("SELECT * FROM machines WHERE machines.cluster_id == {0}".format(cluster_name)).list()

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
	return cluster_id

def create_machine_by_id(new_instance_id, machine_name, machine_state,	machine_metadata, new_cluster_id, machine_hostname):
	'''
	creates a machine and assosisates it with a cluster id
	'''
	machine_id = db.insert('machines', name=machine_name, instance_id= new_instance_id, hostname=machine_hostname,  state=machine_state, metadata=machine_metadata, cluster_id = new_cluster_id )
	print machine_id


def create_machine_by_name(new_instance_id, machine_name, machine_hostname,  machine_state, machine_metadata, cluster_name):
	'''
	creates a machine and assosiates it with a cluster name
	'''
	#get the cluster ID from the name
	cluster_id = db.query("SELECT id FROM cluster WHERE name = '{0}'".format(cluster_name))
	if cluster_id == []:
		raise ValueError('Cluster does not exsist, cannot attach machine')
	#now pass the id onto the create_machine_by_id function
	create_machine_by_id(new_instance_id, machine_name, machine_hostname, machine_state, machine_metadata, cluster_id[0].id)


def delete_machine(machine_id):
	'''
	Deletes a machine by a given ID
	returns the 1 if a machine is deleted
	'''
	return db.delete('machines', where="id={0}".format(machine_id))

def delete_all_machines_in_cluster(cluster_id):
	'''
	deletes all the machines that live in a given cluster ID
	returns number of machines deleted, or 0 if none
	'''
	return db.delete('machines', where="cluster_id = {0}".format(cluster_id))


