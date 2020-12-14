/***
 * Project Name:StudentData
 */

package com.ust.training.studentdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ust.training.studentdata.constant.StudentConstants;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * class containing Main method where execution starts
 * 
 * @author Akhila
 *
 */
@SpringBootApplication
@EnableSwagger2
public class StudentDataApplication implements WebMvcConfigurer {

  private static final Logger LOG = LoggerFactory.getLogger(StudentDataApplication.class);

  public static void main(String[] args) {
    try {
      SpringApplication.run(StudentDataApplication.class, args);
    } catch (Exception e) {
      LOG.error("Exception:", e);
    }
  }

  /**
   * Docket api() point to the basepackage
   * 
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage(StudentConstants.STUDENT_BASE_PACKAGE))
        .paths(PathSelectors.any()).build();
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs");
    registry.addRedirectViewController("/documentation/configuration/ui", "/configuration/ui");
    registry.addRedirectViewController("/documentation/configuration/security",
        "/configuration/security");
    registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
    registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
    registry.addRedirectViewController("/documentation/", "/documentation/swagger-ui.html");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/documentation/**")
        .addResourceLocations("classpath:/META-INF/resources/");
  }
}
