package org.prgrms.kdt.configuration;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("api/**")
        .allowedOrigins("*");
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    var messageConverter = new MarshallingHttpMessageConverter();
    var xStreamMarshaller = new XStreamMarshaller();
    messageConverter.setMarshaller(xStreamMarshaller);
    messageConverter.setUnmarshaller(xStreamMarshaller);
    converters.add(messageConverter);
  }
}