# PaasPort
PaasPort - An FT Hackday Production

## Goal
To build a PaaS suited for FT systems* which enables bootstrapping a new production ready system in a flash.

*system = webapp, webservice, ?

## Architecture
### Components
* Provisioner
* Code Deploy
* Router

## Code Deploy
### Pre-requisite
The machine(s) onto which the app is deployed should have the [deploy.sh](code-deploy/app/src/main/resources/deploy.sh) script installed at `/opt/code-deploy/deploy.sh` with executable permissions to `ec2-user` user. Additionally, passwordless ssh should be setup between the code-deploy server and the target machine by adding the following public key of the code-deploy server to the targer machine
> ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDi/iPsl3RzeL3HxZ0gHHc3NawBqNEvj7ZaPra7blErlsAA+HZbagkfo2hDqRZtpM62MJxAgFaxG35qlsDUm5O5KPsdmC4Fjl/yrV4ZoOuvM5B81uCzoZOObvabG51wAtBzDTu+HCdy1ETIe+jmCNeqzRbqFEov8HZWgnFnaw4zNBKAZwN6f3Ju53Xzhr9PmYSnFU0rE1MlIm14v/r1votm0VgllmYLduH9LKwwTPXYcEOcjTa/oqkpHTbsakxKvaZdSGR6uozVfBHfi0lIo7H6sVKfn5AWfXHUfIvaEwer9OcWJATbTEogRbYUnDp3ogOBpxnkrqvghxQsNzfxqt1B ec2-user@ip-172-31-24-12.eu-west-1.compute.internal

### API Spec
Production Hostname: `http://ec2-52-17-74-125.eu-west-1.compute.amazonaws.com:8080/`

#### Create deployment
>`HTTP POST /paasport/code-deploy/{clusterId}/deployments`    
`Accept: application/json`     
`Content-Type: application/json`

> `Payload`

        {  
    	    "sourceTar":{  
              "url":"https://s3-eu-west-1.amazonaws.com/paasport-code-deploy/demo-app-java.tar",
              "version":"not-used-just-yet"
            }
        }

#### Get deployment by id
> `HTTP GET paasport/code-deploy/{deploymentId}`     
`Content-Type: application/json`

> `Resopnse`

        {
            "uuid": "db0b5c45-07af-453c-b894-e9b90bea9b01",
            "sourceTar": {
                "url": "https://s3-eu-west-1.amazonaws.com/paasport-code-deploy/demo-app-java.tar",
                "version": null
            },
            "status": "completed",
            "message": null,
            "timestamp": 1429605787918,
            "hostCount": 1,
            "completedCount": 1
        }

          
#### Get deployment history for a cluster
> `HTTP GET paasport/code-deploy/{clusterId}/deployments`     
`Content-Type: application/json`

> `Resopnse`

          [
              {
                  "uuid": "387d766b-fdf8-487b-8ad8-3f68ecaa9770",
                  "sourceTar": {
                      "url": "https://s3-eu-west-1.amazonaws.com/paasport-code-deploy/demo-app-java.tar",
                      "version": null
                  },
                  "status": "completed",
                  "message": null,
                  "timestamp": 1429609049597,
                  "hostCount": 1,
                  "completedCount": 1
              }
          ]


    
## Router
Uses varnish for handling the requests and a python API for configuring varnish
### Pre-requesites
* Python 2.x
* Varnish 4.x

The router API is started by running `./run.py` in the `router` directory.  The user running the script should have the following permissions:
* Read/write access to `router/data/**`
* Write/directory listing access to `/etc/varnish/service/*.vcl` and `/etc/varnish/addon/*.vcl`
* Write access to `/etc/varnish/default.vcl`
* Exec permissions for `sudo service varnish reload`

### API
#### `/service/$hostname`
`$hostname` indicates which host header to match for routing traffic to the service.
##### GET Request
If the service is already set up, returns the definition of the service as JSON.
Otherwise, returns a 404.

##### PUT Request
Adds a new service or updates an existing service.  Input should be a JSON object, which can contain the following keys:
* `machines` *required* An array of one or more machines where the service is running.  Each machine should be specified as `hostname[:port]`
* `addons` *optional* An array of layer 7 addons in the order that traffic should be routed there before hitting the service.
* `directorname` This is set based on the `$hostname` and santised for use in varnish config.  If this appears in the PUT Request, it **will** be overwritten.
* Other keys are not used, but will be saved in the config, and returned by any GET request.

##### DELETE Request
Not yet implemented.

