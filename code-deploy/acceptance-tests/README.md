Acceptance Tests Monitor
========================

## Introduction
The acceptance tests are packaged as a dropwizard app and invoked using 
[JUnitCore](http://junit.sourceforge.net/javadoc/org/junit/runner/JUnitCore.html) on a schedule in INT, TEST and PROD.  
In addition to the [scheduled task](https://github.com/atam4j/atam4j/blob/master/src/main/java/me/atam/atam4j/AcceptanceTestsRunnerTask.java) 
in the dropwizard app, tests can be run via the maven command or by using the 'Run tests' option in your favourite IDE 
on the package [com.ft.membership.appName.acceptancetests.tests](src/main/java/com/ft/membership/appName/acceptancetests/tests).

## Running acceptance tests
### Against Local machine
Acceptance tests can be run by activating the maven profile named **at** 
> mvn clean test -Pat

### Against a specific environment
Include system property **APP.ENV** whose value would be the name of the environment against which the tests need to be run.
The possible values are *int*, *test*, *prod*  
**Note:** In order to run tests against an environment, there should be yaml file with name 
**acceptance-tests-<APP.ENV value>.yaml** in the [folder](src/main/resources)
> mvn clean test -Pat -DAPP.ENV=int
    
## Adding new tests to the acceptance test suite
Create a new Junit test class in package 
[com.ft.membership.appName.acceptancetests.tests](src/main/java/com/ft/membership/appName/acceptancetests/tests) to get 
it automatically included to the test suite.
      