package com.monaschinas.readr.platform.shared.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
      .info(
        new Info()
          .title("Monaschinas Readr API")
          .version("1.0")
          .description("Readr")
          .termsOfService("https://readr-landing.netlify.app/terms-of-use/")
          .license(
            new License()
              .name("Apache 2.0 License")
              .url("https://readr-landing.netlify.app/license/")
          )
          .contact(
            new Contact()
              .url("https://monaschinas.com")
              .name("Monaschinas")
          )
      );
  }
}
