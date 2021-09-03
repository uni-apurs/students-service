package com.apurs.microservices.studentsservice.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.apurs.microservices.studentsservice.dto.StudentCreateDTO;
import com.apurs.microservices.studentsservice.dto.StudentDTO;
import com.apurs.microservices.studentsservice.dto.StudentUpdateDTO;
import com.apurs.microservices.studentsservice.model.Student;
import com.apurs.microservices.studentsservice.repository.StudentRepository;
import com.apurs.microservices.syllabusesservice.dto.SyllabusDTO;

@Service
public class StudentServiceImpl implements StudentService {
	
	private StudentRepository studentRepository;
	
	private RestTemplate restTemplate = new RestTemplate();
	private ModelMapper modelMapper = new ModelMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate = new JdbcTemplate();
	
	@Value("${app.syllabusesEndpoint}")
	private String syllabusesEndpoint;
	
	public StudentServiceImpl(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Override
	public List<StudentDTO> findAll() {
		List<Student> students = studentRepository.findAll();
		List<StudentDTO> dtos = new ArrayList<StudentDTO>();
		
		for (Student student : students)
			dtos.add(modelMapper.map(student, StudentDTO.class));
		
		return dtos;
	}

	@Override
	public StudentDTO findOne(Integer id) {
		Student student = studentRepository.getById(id);
		return modelMapper.map(student, StudentDTO.class);
	}

	@Override
	public StudentDTO insert(StudentCreateDTO student) throws Exception {
		ResponseEntity<String> res = restTemplate.getForEntity(syllabusesEndpoint + student.getSyllabusId(), String.class);

		if (!res.getStatusCode().equals(HttpStatus.OK))
			throw new Exception("Invalid syllabusId.");
		
		Student createStudent = modelMapper.map(student, Student.class);
		createStudent =	studentRepository.save(createStudent);
		System.out.println(createStudent.getId());
		return modelMapper.map(createStudent, StudentDTO.class);
	}

	@Override
	public StudentDTO update(StudentUpdateDTO student) throws Exception {
		if (!studentRepository.existsById(student.getId()))
			return null;
		
		ResponseEntity<String> res = restTemplate.getForEntity(syllabusesEndpoint + student.getSyllabusId(), String.class);
		
		if (!res.getStatusCode().equals(HttpStatus.OK))
			throw new Exception("Invalid syllabusId.");

		Student tempStudent = studentRepository.getById(student.getId());
		Student updatedStudent = modelMapper.map(student, Student.class);
		updatedStudent.setCreatedAt(tempStudent.getCreatedAt());
		updatedStudent = studentRepository.save(updatedStudent);
		return modelMapper.map(updatedStudent, StudentDTO.class);
	}

	@Override
	public boolean delete(Integer id) {
		if (!studentRepository.existsById(id))
			return false;

		studentRepository.deleteById(id);
		return true;
	}
	
	@Override
	public List<StudentDTO> findStudentsBySyllabusName(String syllabusName) {
		ResponseEntity<List<SyllabusDTO>> res = restTemplate.exchange(syllabusesEndpoint + "?name=" + syllabusName, HttpMethod.GET, null, new ParameterizedTypeReference<List<SyllabusDTO>>() {});
		List<SyllabusDTO> matches = res.getBody();
		
		if (res.getBody().isEmpty())
			return null;
		
		String inSql = "SELECT * FROM student WHERE \"syllabusId\" IN (";
		for (SyllabusDTO match : matches) {
			inSql += match.getId() + ", ";
		}
		inSql = inSql.substring(0, inSql.length() - 2) + ")";
		
		List<StudentDTO> students = new ArrayList<StudentDTO>();
		
		students = jdbcTemplate.query(inSql, (rs, rowNum) ->
				new StudentDTO(
					rs.getInt("id"),
					rs.getInt("syllabusId"),
					rs.getString("firstName"),
					rs.getString("lastName"),
					rs.getString("index")
				));
		
		return students;
	}
}
