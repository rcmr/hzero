package org.hzero.asgard;

import org.hzero.autoconfigure.EnableHZeroAsgard;
import io.choerodon.eureka.event.EurekaEventHandler;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableHZeroAsgard
@EnableEurekaClient
@SpringBootApplication
public class AsgardApplication {

    public static void main(String[] args) {
        EurekaEventHandler.getInstance().init();
        SpringApplication.run(AsgardApplication.class, args);
    }
}


