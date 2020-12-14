/***
 * Project Name:StudentData
 */

package com.ust.training.studentdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.cosmos.GatewayConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.repository.config.EnableReactiveCosmosRepositories;
/**
 * 
 * @author Akhila
 * 
 * StudentConfig  class to configure properties of cosmos db
 *
 */
@Configuration
@EnableReactiveCosmosRepositories(basePackages = "com.ust.training.studentdata")
public class StudentConfig extends AbstractCosmosConfiguration {

  @Value("${azure.cosmosdb.uri}")
  private String cosmosDbUrl;

  @Value("${azure.cosmosdb.key}")
  private String cosmosDbKey;

  @Value("${azure.cosmosdb.database}")
  private String databaseName;

  @Bean
  public CosmosClientBuilder getCosmosClientBuilder() {
    return new CosmosClientBuilder().endpoint(cosmosDbUrl).key(cosmosDbKey)
        .directMode(new DirectConnectionConfig(), new GatewayConnectionConfig());

  }

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }

  @Bean
  public CosmosContainer getCosmosContainer() {
    return new CosmosClientBuilder().endpoint(cosmosDbUrl).key(cosmosDbKey)
        .directMode(new DirectConnectionConfig(), new GatewayConnectionConfig()).buildClient()
        .getDatabase(databaseName).getContainer("studentDB");

  }

  @Bean
  public CosmosClient getCosmosClient() {
    return new CosmosClientBuilder().endpoint(cosmosDbUrl).key(cosmosDbKey)
        .directMode(new DirectConnectionConfig(), new GatewayConnectionConfig()).buildClient();

  }
}
