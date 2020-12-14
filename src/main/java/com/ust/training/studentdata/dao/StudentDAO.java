/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import com.ust.training.studentdata.constant.SQLQueries;
import com.ust.training.studentdata.model.Student;

/***
 * DAO class for for performing dao operations
 * 
 * @author Akhila
 *
 */
@Component
public class StudentDAO {

  @Value("${azure.cosmosdb.database}")
  private String databaseName;

  @Autowired
  private CosmosClientBuilder cosmosClientBuilder;
  
  private String collectionName = "studentDB";

  /***
   * This method accepts 2 parameters department and rollNo for the query query execution happening
   * here.
   * 
   * @param department
   * @param rollNo
   * @return List of Students
   */
  public List<Student> getStudentByDepartmentandRollNo(String department, Integer rollNo) {
    
    // SqlQuerySpec Initializes a new instance of the SqlQuerySpec class.
    SqlQuerySpec querySpec = new SqlQuerySpec();
    String query = null;
    System.out.println("RollNo" + rollNo);
    if (department.isEmpty() ||rollNo==0) {
      query = SQLQueries.BASE_QUERY.concat(SQLQueries.ROLL_NUMBER_CRITERIA)
          .concat(SQLQueries.DEPARTMENT_OR_CRITERIA);
    } else {
      query = SQLQueries.BASE_QUERY.concat(SQLQueries.DEPARTMENT_CRITERIA)
          .concat(SQLQueries.ROLL_NUMBER_CRITERIA);
    }

    querySpec.setQueryText(query);
    SqlParameter departmentParam = new SqlParameter("@department", department);
    SqlParameter rollNoparam = new SqlParameter("@rollNo", rollNo);
    List<SqlParameter> paramList = new ArrayList<>();
    paramList.add(departmentParam);
    paramList.add(rollNoparam);
    querySpec.setParameters(paramList);
    List<Student> studentList = cosmosClientBuilder.buildClient().getDatabase(databaseName)
        .getContainer(collectionName).queryItems(querySpec, getQueryOptions(), Student.class)
        .stream().collect(Collectors.toList());

    return studentList;
  }


  /***
   * Its for Query options
   * 
   * @return the CosmosQueryOptions
   */

  private CosmosQueryRequestOptions getQueryOptions() {
    CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
    options.setQueryMetricsEnabled(Boolean.FALSE);
    return options;
  }
}


