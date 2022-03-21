package io.metadata.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.InitialLoaderService;
import io.metadata.school.application.services.StudentService;
import io.metadata.school.domain.model.Student;
import io.metadata.school.presentation.dto.StudentDTO;

@AutoConfigureMockMvc
@WebMvcTest
public class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService service;

	@MockBean
	private InitialLoaderService initialLoaderService;

	@MockBean
	private CourseService courseService;

	private StudentDTO getStudent() {
		var student = new StudentDTO();
		student.setName("Student 1");
		return student;
	}

	@Test
	public void mustUpdate() throws Exception {
		var student = getStudent();
		mockMvc.perform(put("/student/update/1").contentType(MediaType.APPLICATION_JSON).content(student.toString()))
				.andExpect(status().isAccepted());

	}
	
	@Test
	public void mustSave() throws Exception {
		var student = getStudent();
		mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(student.toString()))
				.andExpect(status().isCreated());

	}	
	
	@Test
	public void mustDelete() throws Exception {
		var student = getStudent();
		mockMvc.perform(delete("/student/1").contentType(MediaType.APPLICATION_JSON).content(student.toString()))
				.andExpect(status().isAccepted());

	}

	@Test
	public void mustGetAllData() throws Exception {

		List<Student> students = List.of(new Student(Optional.of("Student 1"), Optional.of(1)),
				new Student(Optional.of("Student 2"), Optional.of(2)));

		given(service.findAll()).willReturn(students);
		
		mockMvc.perform(get("/student").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
