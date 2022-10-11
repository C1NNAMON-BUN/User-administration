package com.nix.zhylina.configs;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.nix.zhylina.exceptions.ConstraintViolationExceptionMapper;
import com.nix.zhylina.rest.AuthenticationRestController;
import com.nix.zhylina.rest.RestService;
import com.nix.zhylina.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Arrays;
import java.util.List;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

@Configuration
public class CxfConfig {
    protected String restAddress() {
        return Constants.DEFAULT_ADDRESS_OF_LOCALHOST;
    }

    @Bean
    public Bus cxf() {
        return new SpringBus();
    }

    @Bean
    @DependsOn("cxf")
    public Server cxfRestServer() {
        JAXRSServerFactoryBean cxfServer = new JAXRSServerFactoryBean();

        cxfServer.setServiceBeanObjects(restService(), authenticationRestController());
        cxfServer.setAddress(restAddress());
        cxfServer.setProviders(providers());

        return cxfServer.create();
    }

    @Bean
    public List<Object> providers() {
        return Arrays.asList(new JacksonJaxbJsonProvider(), new ConstraintViolationExceptionMapper());
    }

    @Bean
    public RestService restService() {
        return new RestService();
    }

    @Bean
    public AuthenticationRestController authenticationRestController() {
        return new AuthenticationRestController();
    }
}
