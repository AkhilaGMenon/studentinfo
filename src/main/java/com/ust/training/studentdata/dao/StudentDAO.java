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
import org.springframework.util.StringUtils;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.SqlParameter;
import com.azure.cosmos.models.SqlQuerySpec;
import com.ust.training.studentdata.constant.SQLQueries;
import com.ust.training.studentdata.constant.StudentDBConstants;
import com.ust.training.studentdata.exception.StudentServiceException;
import com.ust.training.studentdata.model.Student;
import lombok.extern.slf4j.Slf4j;

/***
 * DAO class for for performing dao operations
 * 
 * @author Akhila
 *
 */
@Component
@Slf4j

public class StudentDAO {

  @Autowired
  private CosmosClientBuilder cosmosClientBuilder;
  @Value("${azure.cosmosdb.database}")
  private String databaseName;

  /***
   * This method accepts 2 parameters department and rollNo for the query query execution
   * 
   * SqlQuerySpec Initializes a new instance of the SqlQuerySpec class.
   * 
   * 
   * @param department
   * @param rollNo
   * @return List of Students
   */
  public List<Student> getStudentByDepartmentandRollNo(String department, Integer rollNo) {

    log.debug("Begining of getStudentByDepartmentandRollNo method");
    List<Student> studentList = null;

    try {
      SqlQuerySpec querySpec = new SqlQuerySpec();
      String query = SQLQueries.BASE_QUERY;
      List<SqlParameter> paramList = new ArrayList<>();
      SqlParameter departmentParam =
          new SqlParameter(SQLQueries.DATABASE_PARAM_DEPARTMENT, department);
      SqlParameter rollNoparam = new SqlParameter(SQLQueries.DATABASE_PARAM_ROLLNUMBER, rollNo);
      if (!StringUtils.isEmpty(department)) {
        query = query.concat(SQLQueries.DEPARTMENT_CRITERIA);
        paramList.add(departmentParam);
      }
      if (null != rollNo) {
        query = query.concat(SQLQueries.ROLL_NUMBER_CRITERIA);
        paramList.add(rollNoparam);

      }
      querySpec.setQueryText(query);
      querySpec.setParameters(paramList);
      studentList = cosmosClientBuilder.buildClient().getDatabase(databaseName)
          .getContainer(StudentDBConstants.COLLECTION_NAME)
          .queryItems(querySpec, getQueryOptions(), Student.class).stream()
          .collect(Collectors.toList());
    } catch (Exception e) {
      log.error("Exception in getStudentByDepartmentandRollNo: in StudentDAO class:", e);
      throw new StudentServiceException("Exception in getStudentByDepartmentandRollNo", e);
    }
    log.debug("Ending of getStudentByDepartmentandRollNo method:");
    return studentList;

  }


  /***
   * CosmosQueryRequestOptions is for Query options
   * 
   * @return the CosmosQueryOptions
   */

  private CosmosQueryRequestOptions getQueryOptions() {
    CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
    options.setQueryMetricsEnabled(Boolean.FALSE);
    return options;
  }
}


