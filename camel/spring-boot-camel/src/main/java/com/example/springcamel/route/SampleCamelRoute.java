package com.example.springcamel.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class SampleCamelRoute extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    restConfiguration()
      .component("servlet")
      .bindingMode(RestBindingMode.json);

    rest().get("/hello")
      .to("direct:hello");
 
//    from("direct:hello")
//      .log(LoggingLevel.INFO, "Hello World")
//      .transform().simple("Hello World");
   }
}
