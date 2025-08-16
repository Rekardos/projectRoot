package com.config;

import com.exception.NegativeDiscriminantException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.namespace.QName;

@Configuration
@EnableWs
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext ctx) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(ctx);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "equationSolver")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema quadraticSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EquationSolverPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://akmedvedev.dev/equation/solver");
        wsdl11Definition.setSchema(quadraticSchema);
        return wsdl11Definition;
    }

    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new SoapFaultMappingExceptionResolver() {
            @Override
            protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
                if (ex instanceof NegativeDiscriminantException nde) {
                    SoapFaultDetail detail = fault.addFaultDetail();
                    detail.addFaultDetailElement(new QName("formula")).addText(nde.getFormula());
                    detail.addFaultDetailElement(new QName("D")).addText(String.valueOf(nde.getD()));
                }
            }
        };

        SoapFaultDefinition defaultFault = new SoapFaultDefinition();
        defaultFault.setFaultCode(SoapFaultDefinition.SERVER);
        resolver.setDefaultFault(defaultFault);

        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public XsdSchema quadraticSchema() {
        return new SimpleXsdSchema(new ClassPathResource("equation-solver.xsd"));
    }
}
