package io.metadata.school.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import io.metadata.school.presentation.dto.CourseDTO;


@AutoConfigureMockMvc
@WebMvcTest
public class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService service;

	@MockBean
	private InitialLoaderService initialLoaderService;

	@MockBean
	private StudentService studentService;

	private CourseDTO getCourse() {
		var course = new CourseDTO();
		course.setName("Course 1");
		return course;
	}

	@Test
	public void mustUpdate() throws Exception {
		var course = getCourse();
		mockMvc.perform(put("/course/update/1").contentType(MediaType.APPLICATION_JSON).content(course.toString()))
				.andExpect(status().isAccepted());

	}
	
	@Test
	public void mustSave() throws Exception {
		var course = getCourse();
		mockMvc.perform(post("/course").contentType(MediaType.APPLICATION_JSON).content(course.toString()))
				.andExpect(status().isCreated());

	}	
	
	@Test
	public void mustDelete() throws Exception {
		var course = getCourse();
		mockMvc.perform(delete("/course/1").contentType(MediaType.APPLICATION_JSON).content(course.toString()))
				.andExpect(status().isAccepted());

	}

	@Test
	public void mustGetAllData() throws Exception {

		List<Course> courses = List.of(new Course(Optional.of("Course 1"), Optional.of(1)),
				new Course(Optional.of("Course 2"), Optional.of(2)));

		given(service.findAll()).willReturn(courses);
		
		mockMvc.perform(get("/course").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

}
