package com.example.findlocal;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

@Configuration
public class ServerConfiguration implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
       MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
       jsonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));

       MappingJackson2HttpMessageConverter octetConverter = new MappingJackson2HttpMessageConverter();
       octetConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

       converters.add(0, jsonConverter);
       converters.add(1, octetConverter);
    }
}
