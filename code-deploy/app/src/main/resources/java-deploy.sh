#!/usr/bin/env bash

CLUSTER_ID=$1
SOURCE_TAR_URL=$2

getTar ()
{
    sourceTarUrl=$1
    echo "Downloading source tar..."
    currentDir=`pwd`
    cd /tmp
    curl -O $sourceTarUrl
    cd $currentDir
}

main ()
{
    clusterId=$1
    sourceTarUrl=$2
    echo "PaasPort Code Deploy Commencing..."
    getTar $sourceTarUrl
}

main $CLUSTER_ID $SOURCE_TAR_URL
