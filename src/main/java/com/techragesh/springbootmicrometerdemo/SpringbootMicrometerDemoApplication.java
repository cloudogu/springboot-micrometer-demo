package com.techragesh.springbootmicrometerdemo;

import de.triology.cb.CommandBus;
import de.triology.cb.decorator.LoggingCommandBus;
import de.triology.cb.decorator.MicrometerCountingCommandBus;
import de.triology.cb.spring.Registry;
import de.triology.cb.spring.SpringCommandBus;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootMicrometerDemoApplication {

    private final ApplicationContext applicationContext;

    public SpringbootMicrometerDemoApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public CommandBus commandBus() {
        return new MicrometerCountingCommandBus(
                new LoggingCommandBus(new SpringCommandBus(new Registry(applicationContext))),
                c -> Counter.builder("command.counter")
                        .description("command execution counter")
                        .tags("command", c.getSimpleName())
                        .register(Metrics.globalRegistry));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootMicrometerDemoApplication.class, args);
    }
}
