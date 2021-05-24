package co.com.univision.widgets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EntityScan(basePackageClasses = {
        WidgetsApplication.class
})
public class WidgetsApplication {



    public static void main(String[] args) {
        SpringApplication.run(WidgetsApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
