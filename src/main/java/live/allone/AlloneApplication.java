package live.allone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AlloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlloneApplication.class, args);
    }

}
