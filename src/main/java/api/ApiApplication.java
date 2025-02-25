package api;

import jakarta.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;


@ApplicationPath("/api")
/*
public class ApiApplication extends Application {
    public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(MyResource.class);
        s.add(OpenApiResource.class);
        return s;
    }
}
*/
// Swagger Suport 
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Jersey Simple API",
                version = "0.0",
                description = "Swagger Core で定義を生成",
                license = @io.swagger.v3.oas.annotations.info.License (name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "http://hoge.com", name = "yamada", email = "yamada@hoge.com")
        ),
        servers = {
                @Server(
                        description = "server 1",
                        url = "/jersey3s")
        }
)
public class ApiApplication extends ResourceConfig {
    public ApiApplication() {
  
        OpenApiResource openApiResource = new OpenApiResource();
        register(openApiResource);
        
        packages("api.resource"); // リソースクラスを含むパッケージ
        // register(new JettisonFeature()); // エラーじゃないが、class だけで良い
        // register(JettisonFeature.class);
        register(JacksonFeature.class);
    }
}
