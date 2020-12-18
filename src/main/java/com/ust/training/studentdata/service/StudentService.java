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
import com.ust.training.studentdata.common.SearchCriteriaDTO;
import com.ust.training.studentdata.common.StudentDTO;
import com.ust.training.studentdata.dao.StudentDAO;
import com.ust.training.studentdata.model.Student;
import com.ust.training.studentdata.repo.IStudentRepo;
import com.ust.training.studentdata.temp.StudentServiceException;
import lombok.extern.slf4j.Slf4j;

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
   * getStudentDetails to get all details of student
   * 
   * @param id
   * 
   * @return student obj
   */

  public StudentDTO getStudentDetails(String id) {
    log.debug("Begining of getStudentDetails method");
    StudentDTO studentDTO = new StudentDTO();
    try {
      Student student = repository.findById(id).block();
      if (null == student) {

        return null;
      }

      BeanUtils.copyProperties(student, studentDTO);
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
  public List<Student> searchStudent(SearchCriteriaDTO studentDTO) {
    log.debug("Begining of Searchstudent method");
    List<Student> students = null;

    try {

      students =
          dao.getStudentByDepartmentandRollNo(studentDTO.getDepartment(), studentDTO.getRollNo());
      log.debug("Ending of search Student method");

    } catch (Exception exception) {
      log.error("Exception in search Student :", exception);
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
    Student student = null;
    try {
      student = repository.findById(id).block();
      if (null != student) {
        repository.delete(student).block();
      }
      log.debug("Ending of deleteStudentDetails method");
      return student;

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
      Student student = repository.findById(studentDTO.getId()).block();
      if (null != student) {
        repository.delete(student).block();
      }
      studentDetails.setId(studentDTO.getId());
      studentDetails.setFirstName(studentDTO.getFirstName());
      studentDetails.setLastName(studentDTO.getLastName());
      studentDetails.setAddress(studentDTO.getAddress());
      studentDetails.setRollNo(studentDTO.getRollNo());
      studentDetails.setDepartment(studentDTO.getDepartment());
      savedStudent = repository.save(studentDetails).block();

      log.debug("Ending of updateStudent method");
      return savedStudent;

    } catch (Exception exception) {
      log.error("Exception in updateStudent method:", exception);
      throw new StudentServiceException("Exception in updating a Student", exception);
    }


  }
}


