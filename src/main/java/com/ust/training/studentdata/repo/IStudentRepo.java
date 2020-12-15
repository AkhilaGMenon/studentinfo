/***
 * 
 * Project Name :StudentData
 * 
 */
package com.ust.training.studentdata.repo;

import org.springframework.stereotype.Repository;
import com.azure.spring.data.cosmos.repository.ReactiveCosmosRepository;
import com.ust.training.studentdata.model.Student;

/**
 * IStudentRepo is an interface which extends ReactiveCosmosRepository for using CRUD operation over student class.
 * 
 * @author Akhila
 *
 */

@Repository
public interface IStudentRepo extends ReactiveCosmosRepository<Student, String> {

}
