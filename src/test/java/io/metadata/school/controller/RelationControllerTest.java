package io.metadata.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import io.metadata.school.domain.model.Course;
import io.metadata.school.domain.model.Student;
import io.metadata.school.presentation.dto.RelationDTO;


@AutoConfigureMockMvc
@WebMvcTest
public class RelationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService service;

	@MockBean
	private InitialLoaderService initialLoaderService;

	@MockBean
	private CourseService courseService;
	
	@Test
	public void mustDeleteRelation() throws Exception {

		mockMvc.perform(delete("/relation/1/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());

	}	
		
	
	@Test
	public void mustRegisterStudentToCourse() throws Exception {
		
		var relation = new RelationDTO();
		relation.setCourseId(1);
		relation.setStudentId(1);
		
		mockMvc.perform(post("/relation").contentType(MediaType.APPLICATION_JSON).content(relation.toString()))
				.andExpect(status().isCreated());

	}

	@Test
	public void mustNotFindRelations() throws Exception {
		
		List<Student> students = List.of(new Student(Optional.of("Student 1"), Optional.of(1)),
				new Student(Optional.of("Student 2"), Optional.of(2)));

		given(service.findAllRelationsBetweenStudentAndCourse()).willReturn(students);
		
		mockMvc.perform(get("/relation").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void mustGetAllData() throws Exception {
		var student1 = new Student(Optional.of("Student 1"), Optional.of(1));
		var student2 = new Student(Optional.of("Student 2"), Optional.of(2));

		student1.registerIntoCourse(new Course(Optional.of("Course 1"), Optional.of(1)));
		student1.registerIntoCourse(new Course(Optional.of("Course 2"), Optional.of(2)));
		
		List<Student> students = List.of(student1, student2);
			
		given(service.findAllRelationsBetweenStudentAndCourse()).willReturn(students);
		
		mockMvc.perform(get("/relation").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
