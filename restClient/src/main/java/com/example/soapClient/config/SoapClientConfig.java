package com.example.soapClient.config;

import com.example.soap.wsdl.EquationSolverPort;
import com.example.soap.wsdl.EquationSolverPortService;
import com.example.soapClient.service.EquationClient;
import jakarta.xml.ws.BindingProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapClientConfig {

    @Value("${soap.client.url}")
    private String endpointUrl;

    @Bean
    public EquationSolverPort equationSolverPort() {
        EquationSolverPortService service = new EquationSolverPortService();
        EquationSolverPort soapPort = service.getEquationSolverPortSoap11();


        BindingProvider bindingProvider = (BindingProvider) soapPort;
        bindingProvider.getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl
        );

        return soapPort;
    }

    @Bean
    public EquationClient equationClient(EquationSolverPort soapClient) {
        return new EquationClient(soapClient);
    }
}
