package feast.config;

import feast.models.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "feast")
public class AppConfig {

    @Bean
    public static Duck2 getDuck(Chicken3 chicken) {
        return new Duck2(chicken);
    }



}
