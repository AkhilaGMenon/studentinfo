/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.common;

import lombok.Data;

/**
 * Class for searching student
 * 
 * @author Akhila
 *
 *
 */

@Data
public class SearchCriteriaDTO {

  private Integer rollNo;
  private String department;

}
