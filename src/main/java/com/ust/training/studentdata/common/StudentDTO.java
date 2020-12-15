/***
 * Project Name:StudentData
 */
package com.ust.training.studentdata.common;

import lombok.Data;

/**
 * DTO hides the model class
 * 
 * @author Akhila
 *
 */

@Data
public class StudentDTO {

  private String id;
  private Integer rollNo;
  private String firstName;
  private String department;
  private String lastName;
  private String address;

}

