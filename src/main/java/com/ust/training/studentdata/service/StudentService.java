/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.service;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ust.training.studentdata.StudentDataApplication;
import com.ust.training.studentdata.Exception.StudentServiceException;
import com.ust.training.studentdata.common.SearchByDTO;
import com.ust.training.studentdata.common.StudentDTO;
import com.ust.training.studentdata.dao.StudentDAO;
import com.ust.training.studentdata.model.Student;
import com.ust.training.studentdata.repo.StudentRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author Akhila
 *
 */
@Service
public class StudentService {
  @Autowired
  private StudentRepo repository;
  @Autowired
  private StudentDAO dao;

  private static final Logger log = LoggerFactory.getLogger(StudentDataApplication.class);

  /***
   * 
   * Save student details
   * 
   * @param studentDTO
   * @return student obj
   */
  public Student saveStudentDetails(StudentDTO studentDTO) {

    Student details = new Student();
    details.setId(studentDTO.getId());
    details.setFirstName(studentDTO.getFirstName());
    details.setLastName(studentDTO.getLastName());
    details.setAddress(studentDTO.getAddress());
    details.setRollNo(studentDTO.getRollNo());
    details.setDepartment(studentDTO.getDepartment());
    log.debug("Saving studentDetails: {}", details);
    // Save the User class to Azure CosmosDB database.
    Mono<Student> saveStudent = repository.save(details);
    // Nothing happens until we subscribe to these Monos.
    Student savedStudent = saveStudent.block();
    log.debug("Saved StudentDetails");
    return savedStudent;
  }

  /***
   * 
   * 
   * @param id
   * @return student obj
   */

  public StudentDTO getStudentDetails(String id) {
    StudentDTO studentDTO = new StudentDTO();
    log.debug("Begining of get method");
    try {
      Student findByIdStudent = repository.findById(id).block();
      if (null == findByIdStudent) {

        return null;
      }
      log.debug("Ending of get method");
      BeanUtils.copyProperties(findByIdStudent, studentDTO);
      return studentDTO;

    } catch (Exception e) {
      log.error("Exception:", e);
      throw new StudentServiceException("Exception in getStudentDetails", e);
    }

  }

  /**
   * Search student by department and rollNo
   * 
   * @param studentDTO
   * @return
   */
  public List<Student> searchStudentByQueryWithDepartmentAndRollnumber(SearchByDTO studentDTO) {

    List<Student> students = null;
    log.debug("Begining of deletestudent method");
    try {

      students =
          dao.getStudentByDepartmentandRollNo(studentDTO.getDepartment(), studentDTO.getRollNo());
      if (students.isEmpty()) {
        Flux<Student> studentsList = repository.findAll();
        students = studentsList.toStream().collect(Collectors.toList());
      } else {
        log.debug("ending of deletestudent method");
      }
    } catch (Exception exception) {
      log.error("Exception :", exception);
      throw new StudentServiceException("Exception in DeleteStudent", exception);

    }
    return students;

  }

  /***
   *
   * Delete a particular student based on id
   * 
   * @param id
   * @return String
   */
  public String deleteStudentDetails(String id) {

    log.debug("Begining of delete method");
    try {
      Student findStudentById = repository.findById(id).block();
      // Student findStudentById = findByIdStudent.block();
      if (null != findStudentById) {
        repository.delete(findStudentById).block();
        log.debug("Ending of delete method");
        return "Student deleted";
      }
    } catch (Exception e) {
      log.error("Exception:", e);
      throw new StudentServiceException("Exception in DeleteStudentDetails", e);
    }
    return "Student not deleted";
  }

  /***
   * updating the details of student
   * 
   * @param student
   * @return student object
   */

  public Student updateStudent(StudentDTO studentDTO) {

    Student savedStudent = null;
    log.debug("inside update student");
    try {

      Student studentDetails = new Student();
      Mono<Student> findByIdStudent = repository.findById(studentDTO.getId());
      Student student = findByIdStudent.block();
      if (null != studentDetails) {
        repository.delete(student).block();
        studentDetails.setId(studentDTO.getId());
        studentDetails.setFirstName(studentDTO.getFirstName());
        studentDetails.setLastName(studentDTO.getLastName());
        studentDetails.setAddress(studentDTO.getAddress());
        studentDetails.setRollNo(studentDTO.getRollNo());
        studentDetails.setDepartment(studentDTO.getDepartment());
        Mono<Student> saveStudent = repository.save(student);
        savedStudent = saveStudent.block();
      }
      return savedStudent;

    } catch (Exception exception) {
      log.error("Exception:", exception);
      throw new StudentServiceException("Exception in Saving a Student", exception);
    }

  }
}


