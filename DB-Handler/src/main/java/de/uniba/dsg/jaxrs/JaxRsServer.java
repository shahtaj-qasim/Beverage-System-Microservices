package de.uniba.dsg.jaxrs;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

public class JaxRsServer {
    private static Properties properties = Configuration.loadProperties();

    public static void main(String[] args) throws IOException {
        String serverUri = properties.getProperty("serverUri");

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        ResourceConfig config = ResourceConfig.forApplicationClass(ExamplesApi.class);
//        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
        JdkHttpServerFactory.createHttpServer(baseUri, config);
        System.out.println("Server ready to serve your JAX-RS requests... " + serverUri);
        System.out.println("Press any key to exit...");
        System.in.read();
        System.out.println("Stopping server");
//        server.stop(1);
    }
}
