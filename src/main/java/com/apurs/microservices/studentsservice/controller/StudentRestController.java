package com.apurs.microservices.studentsservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apurs.microservices.studentsservice.dto.StudentCreateDTO;
import com.apurs.microservices.studentsservice.dto.StudentDTO;
import com.apurs.microservices.studentsservice.dto.StudentUpdateDTO;
import com.apurs.microservices.studentsservice.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentRestController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("")
	public List<StudentDTO> getStudent(){
		return studentService.findAll();
	}
	
	@GetMapping("/{id}")
	public StudentDTO getStudent(@PathVariable("id") Integer id) {
		return studentService.findOne(id);
	}
	
	@PostMapping("")
	public ResponseEntity<StudentDTO> insertStudent(@RequestBody StudentCreateDTO student) throws Exception {
		if(studentService.insert(student) != null)
			return new ResponseEntity<>(HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("")
	public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentUpdateDTO student) throws Exception {
		if (studentService.update(student) != null)
			return new ResponseEntity<>(HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<StudentDTO> deleteStudent(@PathVariable("id") Integer id) {
		if (studentService.delete(id))
			return new ResponseEntity<>(HttpStatus.OK);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
