package com.agji.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    @Bean
//    public ServletContextAware endpointExporterInitializer(final ApplicationContext applicationContext) {
//        return new ServletContextAware() {
//            @Override
//            public void setServletContext(ServletContext servletContext) {
//                ServerEndpointExporter exporter = new ServerEndpointExporter();
//                exporter.setApplicationContext(applicationContext);
//                exporter.afterPropertiesSet();
//            }
//        };
//    }
}
