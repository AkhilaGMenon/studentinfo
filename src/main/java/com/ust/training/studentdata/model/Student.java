/***
 * Project Name : StudentData
 */

package com.ust.training.studentdata.model;

import org.springframework.data.annotation.Id;
import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.ust.training.studentdata.constant.StudentDBConstants;
import lombok.Data;

/**
 * Model class is an Object Class that represent database entities
 * 
 * Annotation @Container specifies container name .
 * 
 * @author Akhila
 *
 */
@Container(containerName =StudentDBConstants.COLLECTION_NAME)
@Data

public class Student {
  @Id
  private String id;
  private Integer rollNo;
  private String firstName;
  private String department;
  @PartitionKey
  private String lastName;
  private String address;

}
