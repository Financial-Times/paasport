#!/usr/bin/env bash

HOSTNAME=$1
SOURCE_TAR_URL=$2

deployToHost ()
{
    hostname=$1
    sourceTarUrl=$2
    echo "Will deploy ${sourceTarUrl} to host :: ${hostname}"
    ssh -o StrictHostKeyChecking=no ec2-user@${hostname} /opt/code-deploy/deploy.sh ${sourceTarUrl}
}

main ()
{
    hostname=$1
    sourceTarUrl=$2
    echo "PaasPort Code Deploy Commencing..."
    deployToHost $hostname $sourceTarUrl
}

main $HOSTNAME $SOURCE_TAR_URL