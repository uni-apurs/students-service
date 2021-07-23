package com.apurs.microservices.studentsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apurs.microservices.studentsservice.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	List<Student> findByFirstNameContainingIgnoreCase(String firstName);
	List<Student> findByLastNameContainingIgnoreCase(String lastName);
	List<Student> findByIndexContainingIgnoreCase(String indexNum);
}
