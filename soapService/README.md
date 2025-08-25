# SOAP-сервис решения квадратного уравнения

## Описание

Сервис решает квадратное уравнение вида:

A×X2+B×X+C=0

Через SOAP-интерфейс принимает параметры `a`, `b`, `c` и возвращает уравнение, дискриминант и корни.

- элементы `a`, `b`, `c` обязательны.
- Если дискриминант < 0 — возвращается SOAP Fault с сообщением и деталями.
- Автоматически генерируется WSDL с XSD-схемами.

---

## Быстрый старт

### Запуск через Docker

```
docker build -t equation-soap-service .
docker run -p 8080:8080 equation-soap-service
```



## WSDL
```
http://localhost:8080/ws/equationSolver.wsdl
```

## Пример запроса

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:eq="http://akmedvedev.dev/equation/solver">
    <soapenv:Body>
        <eq:request>
            <a>1</a>
            <b>-3</b>
            <c>2</c>
        </eq:request>
    </soapenv:Body>
</soapenv:Envelope>
```

## Пример ответа

```xml
<response>
  <formula>1x^2-3x+2=0</formula>
  <D>1</D>
  <x1>2</x1>
  <x2>1</x2>
</response>

```