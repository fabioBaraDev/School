package io.metadata.school.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.InitialLoaderService;
import io.metadata.school.application.services.exception.CourseNotFoundException;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.model.Course;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class CourseServiceTests {

	@Autowired
	private CourseService courseService;

	@BeforeAll
	public static void init(@Autowired InitialLoaderService loaderService) {
		loaderService.loadAllData();
	}

	@Test
	void mustFindAllCoursesTest() {
		var courses = courseService.findAll();
		assertTrue(courses.size() >= 14);
	}

	@Test
	void mustFindCourseWithoutStudentTest() {
		var courses = courseService.findWithoutStudent();
		assertTrue(courses.size() >= 14);
	}

	@Test
	void mustNotFindCourseByStudentIdTest() {
		var courses = courseService.findByStudentId(1);
		assertTrue(courses.size() == 0);

	}

	@Test
	void mustNotFindCourseByStudentNameTest() {
		var courses = courseService.findByStudentName("Eva");
		assertTrue(courses.size() == 0);
	}

	@Test
	void mustFindCourseByIdTest() {
		var courses = courseService.findById(1);
		assertTrue(courses.get().getId() == 1);
	}

	@Test
	void mustNotFindCourseByIdTest() {
		var courses = courseService.findById(1000);
		assertTrue(courses.isEmpty());

	}

	@Test
	void mustFindCourseByNameTest() {
		var courses = courseService.findByName("Spanish");
		assertTrue(courses.size() == 1);

	}

	@Test
	void mustNotFindCourseByNameTest() {
		var courses = courseService.findByName("GlassRabs");
		assertTrue(courses.size() == 0);
	}

	@Test
	void mustDeleteCourseByIdTest() throws NoDataToDeleteException {
		courseService.deleteByCourseId(1);
		var courses = courseService.findById(1);
		assertTrue(courses.isEmpty());
	}

	@Test
	void mustNotDeleteCourseByIdTest() {
		var control = false;
		try {
			courseService.deleteByCourseId(100000);
		} catch (NoDataToDeleteException e) {
			control = true;
		}
		assertTrue(control);
	}

	@Test
	void mustUpdateCourseTest() throws CourseNotFoundException, CourseDataNotProvidedException {
		var course = new Course(Optional.of("IT"), Optional.of(10));
		var courseUpdated = courseService.update(course);
		assertTrue(courseUpdated.getName() == "IT");
	}

	@Test
	void mustSaveCourseTest() throws CourseNotFoundException, CourseDataNotProvidedException {
		var course = new Course(Optional.of("IT"));
		var courseUpdated = courseService.save(course);
		assertTrue(courseUpdated.getName() == "IT");
	}
}
