/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.controller;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ust.training.studentdata.common.SearchCriteriaDTO;
import com.ust.training.studentdata.common.StudentDTO;
import com.ust.training.studentdata.model.Student;
import com.ust.training.studentdata.service.StudentService;

/**
 * StudentControllerTestCase is a test class 
 * 
 * @author Akhila
 *
 */
@RunWith(JUnit4.class)
public class StudentControllerTestCase {

  @Mock
  private StudentService studentService;
  @InjectMocks
  private StudentController studentController;

  MockMvc mockMvc;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
  }

  /**
   * 
   * Test for {@link StudentController#addStudentDetails(StudentDTO)} with no content as response
   * 
   * @throws Exception
   */
  @Test
  public void testAddStudentDetailsWithNoContent() throws Exception {

    StudentDTO student = new StudentDTO();
    student.setFirstName("Ammu");
    student.setId("5");
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(student);
    Mockito.when(studentService.updateStudent(Mockito.any(StudentDTO.class))).thenReturn(null);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 204);

  }

  /**
   *
   * Test for {@link StudentController#addStudentDetails(StudentDTO)} with success as response
   * 
   * @throws Exception
   */
  @Test
  public void testAddStudentDetailsWithSuccess() throws Exception {

    Student studentResponse = new Student();
    studentResponse.setFirstName("Ammu");
    studentResponse.setId("5");
    StudentDTO student = new StudentDTO();
    student.setFirstName("Ammu");
    student.setId("5");
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(student);
    Mockito.when(studentService.updateStudent(Mockito.any(StudentDTO.class)))
        .thenReturn(studentResponse);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);
    Student res = mapper.readValue(result.getResponse().getContentAsString(), Student.class);

    assertEquals(res.getFirstName(), "Ammu");


  }

  /**
   *
   * Test for {@link StudentController#getStudent(String)} with no content as response
   * 
   * @throws Exception
   */
  @Test
  public void testgetStudentWithNoContent() throws Exception {
    StudentDTO student = new StudentDTO();
    student.setFirstName("Ammu");
    student.setId("5");
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(student);
    Mockito.when(studentService.getStudentDetails(Mockito.any(String.class))).thenReturn(null);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/5").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 204);

  }

  /**
   *
   * Test for {@link StudentController#getStudent(String)} with success as response
   * 
   * @throws Exception
   */
  @Test
  public void testgetStudentWithSuccess() throws Exception {

    StudentDTO student = new StudentDTO();
    student.setFirstName("Ammu");
    student.setId("5");
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(student);
    Mockito.when(studentService.getStudentDetails(Mockito.any(String.class))).thenReturn(student);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/5").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);

  }

  /**
   *
   * Test for {@link StudentController#deleteStudent(String , HttpHeaders)} with no content as
   * response
   * 
   * @throws Exception
   */
  @Test
  public void testdeleteStudentWithNoContent() throws Exception {
    String request = "1";

    Mockito.when(studentService.deleteStudentDetails(Mockito.any(String.class))).thenReturn(null);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/1").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 204);

  }

  /**
   *
   * Test for {@link StudentController#deleteStudent(String)} with success as response
   * 
   * @throws Exception
   */
  @Test
  public void testdeleteStudentWithSuccess() throws Exception {
    Student student = new Student();
    student.setFirstName("Akhila");
    student.setRollNo(6);
    String request = "6";
    Mockito.when(studentService.deleteStudentDetails(Mockito.any(String.class)))
        .thenReturn(student);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/6").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);
  }

  /**
   *
   * Test for {@link StudentController#searchStudentCriteria(SearchCriteriaDTO)} with no content as
   * response
   * 
   * @throws Exception
   */
  @Test
  public void testsearchStudentCriteriaWithNoContent() throws Exception {

    ObjectMapper mapper = new ObjectMapper();
    SearchCriteriaDTO criteriaSearchDTO = new SearchCriteriaDTO();
    criteriaSearchDTO.setRollNo(1);
    criteriaSearchDTO.setDepartment("CE");
    String request = mapper.writeValueAsString(criteriaSearchDTO);
    Mockito.when(studentService.searchStudent(Mockito.any(SearchCriteriaDTO.class)))
        .thenReturn(null);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/search").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 204);

  }

  /**
   * 
   * Test for {@link StudentController#searchStudentCriteria(SearchCriteriaDTO)} with success as
   * response
   * 
   * @throws Exception
   */
  @Test
  public void testSearchStudentCriteriaWithSuccess() throws Exception {

    List<Student> students = new ArrayList<Student>();
    Student s = new Student();
    students.add(s);
    SearchCriteriaDTO student = new SearchCriteriaDTO();
    student.setRollNo(4);
    student.setDepartment("Biology");
    ObjectMapper mapper = new ObjectMapper();
    String request = mapper.writeValueAsString(student);
    Mockito.when(studentService.searchStudent(Mockito.any(SearchCriteriaDTO.class)))
        .thenReturn(students);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/search").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON).content(request);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertEquals(result.getResponse().getStatus(), 200);

  }


}

