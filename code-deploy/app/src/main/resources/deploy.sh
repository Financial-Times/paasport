#!/usr/bin/env bash

## TODO: Add checks to prevent concurrent execution of this script

SOURCE_TAR_URL=$1
WORKSPACE=/opt/code-deploy/workspace

downloadAndUntar ()
{
    sourceTarUrl=$1
    slugName=code-deploy-slug.tar

    rm -rf $WORKSPACE
    mkdir -p $WORKSPACE
    cd $WORKSPACE
    curl -o $slugName $sourceTarUrl
    tar -xvf $slugName
}

processProcFile ()
{
    cd $WORKSPACE
    applicationType=`cat Procfile | awk -F':' '{print $2}' | awk -F' ' '{print $1}'`
    runCommand=`cat Procfile | awk -F':' '{print $2}'`

    # Setup pre-requisistes
    if [ "$applicationType" == "java" ]
    then
        echo "java..."
        # TODO: This doesn't work. sudo needs tty
        sudo yum -y install java
    elif [ "$applicationType" = "node" ]
    then
        echo "node..."
        # TODO: install node
    fi

    # Run binary
    echo "will run command ${runCommand}..."
    $runCommand > /tmp/code-deploy.log &
}

main ()
{
    sourceTarUrl=$1
    downloadAndUntar $sourceTarUrl
    processProcFile
}

main $SOURCE_TAR_URL