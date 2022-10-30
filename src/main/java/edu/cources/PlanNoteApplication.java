package edu.cources;

import edu.cources.plannote.config.YamlConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication()
@EnableConfigurationProperties(YamlConfig.class)
public class PlanNoteApplication {
    public static void main(String... args) {
        SpringApplication.run(PlanNoteApplication.class, args);
    }
}