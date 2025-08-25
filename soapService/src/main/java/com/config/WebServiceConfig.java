package com.config;

import com.exception.NegativeDiscriminantException;
import dev.akmedvedev.equation.solver.EquationException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWs
public class WebServiceConfig implements WsConfigurer {
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
            private final JAXBContext jaxbContext = createJAXBContext();

            @Override
            protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
                if (ex instanceof NegativeDiscriminantException nde) {
                    SoapFaultDetail detail = fault.addFaultDetail();
                    EquationException faultInfo = new EquationException();
                    faultInfo.setMessage(nde.getMessage());
                    faultInfo.setD(nde.getD());
                    faultInfo.setFormula(nde.getFormula());
                    marshal(detail, faultInfo);
                }
            }

            private JAXBContext createJAXBContext() {
                try {
                    return JAXBContext.newInstance(EquationException.class);
                } catch (JAXBException e) {
                    throw new RuntimeException("Не удалось создать JAXBContext", e);
                }
            }

            private void marshal(SoapFaultDetail detail, Object faultInfo) {
                try {
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.marshal(faultInfo, detail.getResult());
                } catch (JAXBException e) {
                    throw new RuntimeException("Ошибка маршалинга SOAP Fault", e);
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

    @Bean
    public PayloadValidatingInterceptor payloadValidatingInterceptor(XsdSchema mySchema){
        PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        interceptor.setXsdSchema(mySchema);
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(true);
        return interceptor;
    }

    @Bean
    public WsConfigurer wsConfigurer(PayloadValidatingInterceptor interceptor) {
        return new WsConfigurer() {
            @Override
            public void addInterceptors(List<EndpointInterceptor> interceptors) {
                interceptors.add(interceptor);
            }
        };
    }
}
