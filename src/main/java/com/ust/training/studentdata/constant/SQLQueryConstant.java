/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.constant;

/**
 * SQLQueryConstant class contains constants used
 * 
 * @author Akhila
 *
 */
public class SQLQueryConstant {
  /**
   * 
   * Default constructor of SQLQueries class
   */
  private SQLQueryConstant() {

  }

  public static final String BASE_QUERY                    = "select * from studentDB where 1=1  ";
  public static final String DEPARTMENT_CRITERIA           = " and studentDB.department=@department";
  public static final String ROLL_NUMBER_CRITERIA          = " and studentDB.rollNo=@rollNo";
  public static final String DATABASE_PARAM_DEPARTMENT     = "@department";
  public static final String DATABASE_PARAM_ROLLNUMBER     = "@rollNo";
  public static final String COLLECTION_NAME               = "studentDB";
  public static final String DATABASE_NAME                 ="${azure.cosmosdb.database}";
  public static final String DATABASE_KEY                  ="${azure.cosmosdb.key}";
  public static final String DATABASE_URI                  ="${azure.cosmosdb.uri}";

}
