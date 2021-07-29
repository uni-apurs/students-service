package com.apurs.microservices.studentsservice.service;

import java.util.List;

import com.apurs.microservices.studentsservice.dto.StudentCreateDTO;
import com.apurs.microservices.studentsservice.dto.StudentDTO;
import com.apurs.microservices.studentsservice.dto.StudentUpdateDTO;

public interface StudentService {
	public abstract List<StudentDTO> findAll();
	public abstract StudentDTO findOne(Integer id);
	public abstract StudentDTO insert(StudentCreateDTO student) throws Exception;
	public abstract StudentDTO update(StudentUpdateDTO student) throws Exception;
	public abstract boolean delete(Integer id);
	
	public abstract List<StudentDTO> findStudentsBySyllabusName(String syllabusName);
}
