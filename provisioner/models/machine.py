import boto.ec2

# EU-WEST-1: RHEL7 HVM
AMI_ID = 'ami-25158352'


def create_many(definitions):
	return map(create_new, definitions)


def create_new(data):
	# boto create instance
	name = data['name'] if 'name' in data else 'unnamed instance'
	cpu = int(0 if not 'cpu' in data else data['cpu'])
	memory = int(0 if not 'memory' in data else data['memory'])
	disk = int( 0)
	region = str('eu-west-1')
	metadata = {}

	security_groups = [ 'sg-fb4c1a9e' ]
	connection = boto.ec2.connect_to_region(region)
	instance = connection.run_instances(AMI_ID,
			instance_type='m3.medium').instances[0]

	return instance


def delete_instance(instanceId, region):
	connection = boto.ec2.connect_to_region(region)
	connection.terminate_instances(instance_ids=[instanceId])
	return True
