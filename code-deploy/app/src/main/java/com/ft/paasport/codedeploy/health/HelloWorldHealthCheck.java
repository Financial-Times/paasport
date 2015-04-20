package com.ft.paasport.codedeploy.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * @author anuragkapur
 */
public class HelloWorldHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
