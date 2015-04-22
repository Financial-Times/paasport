#!/usr/bin/env sh

ID=`curl -q -XPOST http://paasport-provisioner.herokuapp.com/clusters -d"{\"name\": \"$1\", \"owner\": \"paasport-control\", \"metadata\": \"\"}" | cut -f2 -d " " | sed s/,//g`
echo Cluster $ID
curl -XPOST http://paasport-provisioner.herokuapp.com/clusters/$ID/machines -d '[{"name": "testtesttest"}, {"name": "testestest"}]' 2&>1 /dev/null
./deploy.py $ID &
curl -XPOST http://ec2-52-17-74-125.eu-west-1.compute.amazonaws.com:8080/paasport/code-deploy/$ID/deployments -H 'Accept: application/json' -H 'Content-Type: application/json' -d'{"sourceTar":{"url":"https://s3-eu-west-1.amazonaws.com/paasport-code-deploy/demo-dw-java-app-1.tar","version":"not-used-just-yet" }}' &

wait

curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1;
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
sleep 1
curl http://$1.paasport.labs.ft.com/__gtg
