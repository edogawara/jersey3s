package api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
        title = "Jersey Sample API",
        version = "0.0",
        description = "Swagger Core で定義を生成"
    ),
    servers = {
        @Server(
        description = "my server",
        url = "/jersey3s")
    }
)
@SecurityScheme(name = "myBearerSecurity", // for Swagger define 
                                           // use @SecurityRequirement(name = "myBearerSecurity")
	type = SecuritySchemeType.HTTP,
	scheme = "bearer" 
)
public class ApiApplication extends ResourceConfig {

	// CORS 対応(static class or new instance) 
	public static class CORSFilter implements ContainerResponseFilter {
	    @Override
	    public void filter(ContainerRequestContext request, ContainerResponseContext response) {
	        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
	        response.getHeaders().putSingle("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE");
	        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Content-Type");
	        // If you need access from local files
	        response.getHeaders().putSingle("Access-Control-Allow-Headers", "Authorization"); 
	    }
	}
    public ApiApplication() {
  
        OpenApiResource openApiResource = new OpenApiResource();
        register(openApiResource);
        register(CORSFilter.class);
        
        packages("api.resource"); // リソースクラスを含むパッケージ
        // register(JettisonFeature.class);
        register(JacksonFeature.class);
    }
}