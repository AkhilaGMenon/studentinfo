/***
 * Project Name : StudentData
 */

package com.ust.training.studentdata.model;

import org.springframework.data.annotation.Id;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Data;

/**
 * Model class is an Object Class that represent database entities
 * 
 * Annotation @Container(containerName="studentDB") specifies container name .
 * 
 * @author Akhila
 *
 */
@Container(containerName = "studentDB")


@Data

public class Student {
  @Id
  private String id;
  private int rollNo;
  private String firstName;
  private String department;
  @PartitionKey
  private String lastName;
  private String address;

}
