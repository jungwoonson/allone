package live.allone.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("domain")
    private String domain;

    @Bean
    public OpenAPI openAPI() {

        Server server = new Server();
        server.setUrl(domain);

        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .addServersItem(server);
    }

    private Info apiInfo() {
        return new Info()
                .title("ALL ONE")
                .description("ALL ONE API 문서")
                .version("0.0.1");
    }
}
