package com.example.soapClient.exception;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.ws.soap.SOAPFaultException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.example.soap.wsdl.EquationException;


import java.util.Map;

@ControllerAdvice
public class SoapExceptionHandler {
    @ExceptionHandler(SOAPFaultException.class)
    public ResponseEntity<Object> handleSoapFault(SOAPFaultException ex) {
        try {
            JAXBContext context = JAXBContext.newInstance(EquationException.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            EquationException fault = (EquationException) unmarshaller.unmarshal(
                    ex.getFault().getDetail().getFirstChild()
            );

            EquationExceptionDto dto = new EquationExceptionDto(
                    fault.getMessage(),
                    fault.getFormula(),
                    fault.getD()
            );
            return ResponseEntity.badRequest().body(dto);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Ошибка при обработке SOAP Fault",
                    "error", e.getMessage()
            ));
        }
    }
    public record EquationExceptionDto(String message, String formula, double D) {}
}


