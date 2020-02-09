package bk.asyncexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsyncExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncExampleApplication.class, args);
//        SpringApplication application = new SpringApplication(AsyncExampleApplication.class);
//        application.setWebApplicationType(WebApplicationType.NONE);
//        ConfigurableApplicationContext ctx = application.run(args);
    }

}
