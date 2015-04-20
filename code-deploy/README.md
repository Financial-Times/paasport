# appName

## Table of Contents
1. Introduction
2. Local developer machine setup
3. How to use the db-migration module
4. Build and release pipeline

## Introduction
TODO

## Local developer machine setup
### Pre-requisites
TODO

### How to build the service
Run the following command at the root level of the repository
> mvn clean install

### How to deploy the service
Post the maven build, run the following command (from root level of the repo) to deploy the service
> java -jar app/target/{appName}.jar server 

Alternately, you can use your preferred method (example: use your IDE) to run the main method in the
class: [App.java](app/src/main/java/com/ft/membership/appName/App.java)

## How to run tests
### Unit/component
> mvn clean test

### Integration
> mvn clean verify

Optional: `-DAPP.ENV={LOCAL|INT}`       
The property allows a different (say environment specific) version of configuration to be used while running the 
Integration tests. Default version used is the file with suffix `-LOCAL.yml`. More values may be used for the APP.ENV 
property as long as there is a yml file for the value you want to use in 
[app/src/test/resources](app/src/test/resources)

### Acceptance
Refer to [Acceptance-Tests Readme](acceptance-tests/README.md)

### Performance
TODO

## Build and release pipeline
Build and release to designated environments (INT, TEST and PROD) is achieved via Jenkins. The Jenkins pipeline can be
found [here][1]. The jenkins pipeline, implements the
[AIM Build and Release Pipeline described here](https://sites.google.com/a/ft.com/technology/systems/membership/identity-and-access-management/03-engineering#TOC-Build-and-Release-Pipeline).

## Environments Info
[EnvironmentInfo.markdown](EnvironmentInfo.markdown)

## Additional documentation
Additional documentation, including Architecture, Wiki and Runbook can be found
[here][2].

[1]: http://TODO:jenkinsUrl
[2]: http://TODO:googleSiteUrl
