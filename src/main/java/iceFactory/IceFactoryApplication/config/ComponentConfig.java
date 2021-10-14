package iceFactory.IceFactoryApplication.config;

import iceFactory.IceFactoryApplication.model.Owner;
import iceFactory.IceFactoryApplication.service.CustomRestTemplateCustomizer;
import iceFactory.IceFactoryApplication.service.IceFactoryAPIService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ComponentConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public IceFactoryAPIService service (RestTemplate restTemplate ){
        return new IceFactoryAPIService(restTemplate);
    }

    @Bean
    public Owner owner(){
        return new Owner();
    }

    @Bean
    public CustomRestTemplateCustomizer customRestTemplateCustomizer() {
        return new CustomRestTemplateCustomizer();
    }

    @Bean
    @DependsOn(value = {"customRestTemplateCustomizer"})
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder(customRestTemplateCustomizer());
    }
}
