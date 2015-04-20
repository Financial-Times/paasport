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




