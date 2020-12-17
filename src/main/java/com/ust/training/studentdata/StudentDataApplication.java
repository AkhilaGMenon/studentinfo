/***
 * Project Name:StudentData
 */

package com.ust.training.studentdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ust.training.studentdata.constant.StudentServiceConstants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@SpringBootApplication
@EnableSwagger2
public class StudentDataApplication implements WebMvcConfigurer {

  public static void main(String[] args) {
    log.debug("Starting of main Method");
    try {
      SpringApplication.run(StudentDataApplication.class, args);
    } catch (Exception e) {
      log.error("Exception in StudentDataApplication:", e);
    }
  }

  /**
   * Docket api() point to the basepackage
   * 
   */
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage(StudentServiceConstants.STUDENT_BASE_PACKAGE_CONTROLLER))
        .paths(PathSelectors.any()).build();
  }
  /**
   * 
   * Method to addViewControllers
   */

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/documentation/v2/api-docs", "/v2/api-docs");
    registry.addRedirectViewController("/documentation/configuration/ui", "/configuration/ui");
    registry.addRedirectViewController("/documentation/configuration/security","/configuration/security");
    registry.addRedirectViewController("/documentation/swagger-resources", "/swagger-resources");
    registry.addRedirectViewController("/documentation", "/documentation/swagger-ui.html");
    registry.addRedirectViewController("/documentation/", "/documentation/swagger-ui.html");
  }
/**
 * 
 * Method to addResourceHandlers
 */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/documentation/**").addResourceLocations("classpath:/META-INF/resources/");
  }
}
