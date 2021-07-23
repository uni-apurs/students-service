package com.apurs.microservices.studentsservice.dto;

import java.time.ZonedDateTime;

public class StudentCreateDTO {
	private int syllabusId;
	private String firstName;
	private String lastName;
	private String index;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
	
	public StudentCreateDTO(int syllabusId, String firstName, String lastName, String index) {
		super();
		this.syllabusId = syllabusId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.index = index;
		this.setCreatedAt(ZonedDateTime.now());
		this.setUpdatedAt(ZonedDateTime.now());
	}

	public int getSyllabusId() {
		return syllabusId;
	}

	public void setSyllabusId(int syllabusId) {
		this.syllabusId = syllabusId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(ZonedDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public ZonedDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(ZonedDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
