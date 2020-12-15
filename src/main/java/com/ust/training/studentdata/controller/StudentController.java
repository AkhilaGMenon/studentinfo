/***
 * 
 * Project Name :StudentData
 * 
 */

package com.ust.training.studentdata.controller;

import java.util.List;
import javax.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ust.training.studentdata.annotation.SwaggerToken;
import com.ust.training.studentdata.common.SearchCriteriaByDTO;
import com.ust.training.studentdata.common.StudentDTO;
import com.ust.training.studentdata.model.Student;
import com.ust.training.studentdata.service.StudentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * StudentController is a controller class which contains post delete and get methods mapping
 * methods
 * 
 * @author Akhila
 *
 */
@Slf4j
@RestController
public class StudentController {

  @Autowired
  StudentService studentService;

  /***
   * PostMapping adds a new student and save the detail
   * 
   * @param studentDTO
   * @return ResponseEntity of student
   */

  @PostMapping
  @SwaggerToken
  @ApiOperation(value = "Adding new student and save student detail",
      notes = "Returns 200 OK/204 NO_CONTENT", httpMethod = HttpMethod.POST)

  public ResponseEntity<Student> addStudentDetails(@RequestBody StudentDTO studentDTO) {

    log.debug("Begining of addStudentDetails method");
    Student detail = studentService.saveStudentDetails(studentDTO);
    if (null != detail) {
      log.info("Status:200  Response:", studentDTO);
      return ResponseEntity.ok(detail);
    } else {
      log.info("Status:204 Response:");
      log.debug("Ending ofaddStudentDetails method");
      return ResponseEntity.noContent().build();
    }

  }

  /**
   * 
   * search student by department and rollNO
   * 
   * @param searchByDTO
   * @return Response entity of student
   */

  @PostMapping("/search")
  @SwaggerToken
  @ApiOperation(value = "Search Student By Department and RollNumber ",
      notes = "Returns 200 OK/204 NO_CONTENT", httpMethod = HttpMethod.POST)

  public ResponseEntity<List<Student>> searchStudentByDepartmentRollNumber(
      @RequestBody SearchCriteriaByDTO searchByDTO) {
    log.debug("Begining of searchStudentByDepartmentRollNumber search method");
    List<Student> student =
        studentService.searchStudentByQueryWithDepartmentAndRollnumber(searchByDTO);
    if (CollectionUtils.isEmpty(student)) {
      log.info("Status:200  Response:", student);
      log.debug("Ending the searchStudentByDepartmentRollNumber operation");
      return ResponseEntity.ok(student);
    } else {
      log.debug("Ending the Post operation");
      log.info("Status:204  Response:");
      return ResponseEntity.noContent().build();
    }
  }

  /***
   * DeleteMapping deleting an existing student
   * 
   * @param id
   * @return ResponseEntity of student
   */

  @DeleteMapping("/{id}")
  @SwaggerToken
  @ApiOperation(value = "Deleting student by id", notes = "Returns 200 OK/204 NO_CONTENT",
      httpMethod = HttpMethod.DELETE)

  public ResponseEntity<String> deleteStudent(@PathVariable("id") String id) {

    log.debug("Begining the deleteStudent operation");
    Student details = null;
    String message = null;
    details = studentService.deleteStudentDetails(id);
    if (null != details) {
      log.info("Status:200 Response:");
      message = "Student Deleted";
      log.debug("Ending the deleteStudent operation");
      return ResponseEntity.ok(message);

    }
    log.info("status:204 Response:");
    return ResponseEntity.noContent().build();

  }

  /***
   * GetMapping for getting a student with a particular id
   * 
   * @param id
   * @return ResponseEntity of studentDTO
   */

  @GetMapping("/{id}")

  @SwaggerToken
  @ApiOperation(value = "Getting student by id", notes = "Returns 200 OK/204 NO_CONTENT",
      httpMethod = HttpMethod.GET)

  public ResponseEntity<StudentDTO> getStudent(@PathVariable("id") String id) {

    log.debug("Begining the getStudent operation");
    StudentDTO details = studentService.getStudentDetails(id);
    if (null != details) {
      log.info("Status:200  Response:", details);
      return ResponseEntity.ok(details);
    } else {
      log.debug("Ending of getStudent operation");
      log.info("Status:204 Response:");
      return ResponseEntity.noContent().build();
    }

  }

}
