import os
import base64
import boto.ec2

# EU-WEST-1: RHEL7 HVM
AMI_ID = 'ami-25158352'
region = 'eu-west-1'

def generate_userdata():
	'''Read environment vars and various other things to
	get SSH keys, usernames and deploy scripts and inject them into an
	instance as a userdata script
	'''
	ssh_key					= os.environ["DEPLOY_SSH_KEY"]
	deploy_script_location	= os.environ["DEPLOY_SCRIPT_LOCATION"]
	with open(deploy_script_location, 'r') as deploy_handle:
		deploy_script = deploy_handle.readlines()
	deploy_handle.close()
	user_data = '''#!/bin/bash

	printf '{0}' >> /home/ec2-user/.ssh/authorized_keys;
	echo {1} > /opt/code-deploy/deploy.sh
	sudo chmod a+x /opt/code-deploy/deploy.sh
	echo "all done"

	'''.format(ssh_key, deploy_script)
	print user_data

	return user_data

def create_many(definitions, cluster_id):
	return map(format_instance, map(lambda data: create_new(data, cluster_id),
		definitions))

def create_new(data, cluster_id):
	# Always keep the nursery full
	name = data['name'] if 'name' in data else 'unnamed instance'
	nursery_instance = create_new_in_nursery(data)

	instances = transfer_machine_from_nursery_to_cluster(cluster_id, new_name=name)

	if len(instances) == 1:
		return instances[0]

	raise Exception("No available instances")

def create_new_in_nursery(data):
	security_groups = [ 'sg-8a2574ef' ]
	connection = boto.ec2.connect_to_region(region)
	instance = connection.run_instances(AMI_ID, instance_type='m3.medium',
			user_data=generate_userdata(), key_name="LukeBlaney",
			security_group_ids=security_groups).instances[0]
	connection.create_tags([instance.id], { 'cluster': '__nursery__' })

	return instance

def transfer_machine_from_nursery_to_cluster(cluster_id, new_name="clustered_machine"):
	connection = boto.ec2.connect_to_region(region)

	# TODO: RACE CONDITIONS COULD OCCUR HERE! Need a DLM?
	instances = connection.get_only_instances(filters={ 'instance-state-name':
		'running', 'tag-key': 'cluster', 'tag-value':
		'__nursery__'})

	connection.create_tags(map(lambda instance: instance.id, instances),
			tags={ 'cluster': str(cluster_id), 'Name': new_name })
	# END OF RACE CONDITION TERRITORY
	return instances

def get_instances_in_cluster(cluster_id):
	connection = boto.ec2.connect_to_region(region)
	return 	map(format_instance, connection.get_only_instances(filters={ 'tag-key': 'cluster',
				'tag-value': cluster_id }))

def format_instance(instance):
	return {
		'id': instance.id,
		'name': instance.tags['Name'],
#		'hostname': instance.private_ip_address,
		'hostname': instance.public_dns_name,
# hardcoded m3.medium
		'cpu': 1,
		'memory': '4026531840',
		'disk': '4',
		'region': region,
		'metadata': '{}',
		'state': instance.state
	}

def get_instance_info(instance_ids):
	connection = boto.ec2.connect_to_region(region)
	instance_states = connection.get_only_instances(instance_ids=instance_ids)
	return instances


def delete_instance(instance_id, region):
	connection = boto.ec2.connect_to_region(region)
	connection.terminate_instances(instance_ids=[instance_id])
	return True
