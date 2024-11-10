package live.allone.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${domain.back}")
    private String domainBack;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(apiInfo())
            .servers(List.of(defualtServer(), localServer()));
    }

    private Server defualtServer() {
        Server server = new Server();
        server.setDescription("default");
        server.setUrl(domainBack);
        return server;
    }

    private Server localServer() {
        Server server = new Server();
        server.setDescription("local");
        server.setUrl("http://localhost:8080");
        return server;
    }

    private Info apiInfo() {
        return new Info()
            .title("ALL ONE")
            .description("ALL ONE API 문서")
            .version("0.0.1");
    }
}
