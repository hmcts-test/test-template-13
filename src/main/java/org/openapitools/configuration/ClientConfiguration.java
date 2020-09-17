package org.openapitools.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitResourceDetails;

@Configuration
@EnableConfigurationProperties
public class ClientConfiguration {

  @Value("${swaggerpetstore.security.apiKey.key:}")
  private String apiKeyKey;

  @Bean
  @ConditionalOnProperty(name = "swaggerpetstore.security.apiKey.key")
  public ApiKeyRequestInterceptor apiKeyRequestInterceptor() {
    return new ApiKeyRequestInterceptor("header", "api_key", this.apiKeyKey);
  }

  @Bean
  @ConditionalOnProperty("swaggerpetstore.security.petstoreAuth.client-id")
  public OAuth2FeignRequestInterceptor petstoreAuthRequestInterceptor() {
    return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), petstoreAuthResourceDetails());
  }

  @Bean
  @ConditionalOnProperty("swaggerpetstore.security.petstoreAuth.client-id")
  @ConfigurationProperties("swaggerpetstore.security.petstoreAuth")
  public ImplicitResourceDetails petstoreAuthResourceDetails() {
    ImplicitResourceDetails details = new ImplicitResourceDetails();
    details.setUserAuthorizationUri("https://petstore.swagger.io/oauth/authorize");
    return details;
  }

}
