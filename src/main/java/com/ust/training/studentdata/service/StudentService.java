/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.service;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ust.training.studentdata.common.SearchCriteriaByDTO;
import com.ust.training.studentdata.common.StudentDTO;
import com.ust.training.studentdata.dao.StudentDAO;
import com.ust.training.studentdata.exception.StudentServiceException;
import com.ust.training.studentdata.model.Student;
import com.ust.training.studentdata.repo.IStudentRepo;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 
 * StudentService has CRUD method service for Student resource
 * 
 * @author Akhila
 *
 */
@Slf4j
@Service
public class StudentService {
  @Autowired
  private IStudentRepo repository;
  @Autowired
  private StudentDAO dao;

  /***
   * 
   * Save student details
   * 
   * @param studentDTO
   * @return student obj
   */
  public Student saveStudentDetails(StudentDTO studentDTO) {

    log.debug("Begining of saveStudentDetails method");
    Student details = new Student();
    try {
      details.setId(studentDTO.getId());
      details.setFirstName(studentDTO.getFirstName());
      details.setLastName(studentDTO.getLastName());
      details.setAddress(studentDTO.getAddress());
      details.setRollNo(studentDTO.getRollNo());
      details.setDepartment(studentDTO.getDepartment());
      log.debug("Saving studentDetails: {}", details);
      Mono<Student> saveStudent = repository.save(details);
      Student savedStudent = saveStudent.block();
      log.debug("Ending of saveStudentDetails");
      return savedStudent;
    } catch (Exception e) {
      log.error("Exception in saveStudentDetails:", e);
      throw new StudentServiceException("Exception in getStudentDetails", e);
    }

  }

  /***
   * getStudentDetails to get all details of student
   * 
   * @param id
   * 
   * @return student obj
   */

  public StudentDTO getStudentDetails(String id) {
    StudentDTO studentDTO = new StudentDTO();
    log.debug("Begining of getStudentDetails method");
    try {
      Student findByIdStudent = repository.findById(id).block();
      if (null == findByIdStudent) {

        return null;
      }

      BeanUtils.copyProperties(findByIdStudent, studentDTO);
      log.debug("Ending of getStudentDetails method");
      return studentDTO;

    } catch (Exception e) {
      log.error("Exception in getStudentDetails method:", e);
      throw new StudentServiceException("Exception in getStudentDetails", e);
    }

  }

  /**
   * Search student by department and rollNo
   * 
   * @param studentDTO
   * @return students
   */
  public List<Student> searchStudentByQueryWithDepartmentAndRollnumber(
      SearchCriteriaByDTO studentDTO) {
    log.debug("Begining of Searchstudent method");
    List<Student> students = null;

    try {

      students =
          dao.getStudentByDepartmentandRollNo(studentDTO.getDepartment(), studentDTO.getRollNo());

    } catch (Exception exception) {
      log.error("Exception in searchStudentByQueryWithDepartmentAndRollnumber:", exception);
      throw new StudentServiceException("Exception in Searchtudent", exception);

    }
    return students;

  }

  /***
   *
   * Delete a particular student based on id
   * 
   * @param id
   * @return Student Obj
   */
  public Student deleteStudentDetails(String id) {

    log.debug("Begining of deleteStudentDetails method");
    Student findStudentById = null;
    try {
      findStudentById = repository.findById(id).block();
      if (null != findStudentById) {
        repository.delete(findStudentById).block();
      }
      log.debug("Ending of deleteStudentDetails method");
      return findStudentById;

    } catch (Exception e) {
      log.error("Exception in deleteStudentDetails:", e);
      throw new StudentServiceException("Exception in DeleteStudentDetails", e);
    }

  }

  /***
   * updating the details of student
   * 
   * @param student
   * @return student object
   */

  public Student updateStudent(StudentDTO studentDTO) {
    log.debug("inside updateStudent method");

    Student savedStudent = null;
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
      log.debug("Ending of updateStudent method");
      return savedStudent;

    } catch (Exception exception) {
      log.error("Exception in updateStudent method:", exception);
      throw new StudentServiceException("Exception in updating a Student", exception);
    }

  }
}


