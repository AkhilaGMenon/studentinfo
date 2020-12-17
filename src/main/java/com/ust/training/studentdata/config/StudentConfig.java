/***
 * Project Name:StudentData
 */

package com.ust.training.studentdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableReactiveCosmosRepositories;
import com.ust.training.studentdata.constant.StudentServiceConstants;

/**
 * 
 * StudentConfig class to configure properties of cosmosdb
 * 
 * @author Akhila
 * 
 */
@Configuration
@EnableReactiveCosmosRepositories(basePackages =StudentServiceConstants.STUDENT_BASE_PACKAGE)
public class StudentConfig extends AbstractCosmosConfiguration {
  @Value("${azure.cosmosdb.uri}")
  private String cosmosDbUrl;

  @Value("${azure.cosmosdb.key}")
  private String cosmosDbKey;

  @Value("${azure.cosmosdb.database}")
  private String databaseName;

  /***
   * Method for returning the cosmosDBUrl and key
   * 
   * @return CosmosClientBuilder
   */
  @Bean
  public CosmosClientBuilder getCosmosClientBuilder() {
    return new CosmosClientBuilder().endpoint(cosmosDbUrl)
        .key(cosmosDbKey)
        .directMode(new DirectConnectionConfig(), new GatewayConnectionConfig());

  }

  /***
   * Method for returning the cosmosDBUrl and key
   * 
   * @return CosmosContainer
   */

  /***
   * Method for returning the cosmosDBUrl and key
   * 
   * @return CosmosClient
   */
  @Bean
  public CosmosClient getCosmosClient() {
    return new CosmosClientBuilder().endpoint(cosmosDbUrl)
        .key(cosmosDbKey)
        .directMode(new DirectConnectionConfig(), new GatewayConnectionConfig()).buildClient();

  }

  /**
   * Method to getDatabaseName
   * 
   * @return databaseName
   */
  @Override
  protected String getDatabaseName() {
    return databaseName;
  }
}
