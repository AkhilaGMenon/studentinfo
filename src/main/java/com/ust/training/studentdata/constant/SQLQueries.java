/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.constant;

/**
 * SQLQuery Constants
 * 
 * @author Akhila
 *
 */
public class SQLQueries {
  /**
   * 
   * Default constructor of SQLQueries class
   */
  private SQLQueries() {

  }

  public static String BASE_QUERY                          = "select * from studentDB where 1=1  ";
  public static String DEPARTMENT_CRITERIA                 = " and studentDB.department=@department";
  public static String ROLL_NUMBER_CRITERIA                = " and studentDB.rollNo=@rollNo";
  public static final String DATABASE_PARAM_DEPARTMENT     = "@department";
  public static final String DATABASE_PARAM_ROLLNUMBER     = "@rollNo";

}
