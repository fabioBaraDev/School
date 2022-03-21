package io.metadata.school.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import io.metadata.school.application.services.InitialLoaderService;
import io.metadata.school.application.services.StudentService;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentAlreadyHasThisCourseException;
import io.metadata.school.application.services.exception.StudentOrCourseNotFoundException;
import io.metadata.school.domain.exceptions.CourseWithTooManyStudentsException;
import io.metadata.school.domain.exceptions.StudentWithTooManyCoursesException;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@TestMethodOrder(OrderAnnotation.class)
public class RelationServiceTests {

	@Autowired
	private StudentService studentService;

	@BeforeAll
	public static void init(@Autowired InitialLoaderService loaderService) {
		loaderService.loadAllData();
	}

	@Test
	@Order(1)
	public void mustRegisterCourse() throws StudentOrCourseNotFoundException, StudentAlreadyHasThisCourseException,
			CourseWithTooManyStudentsException, StudentWithTooManyCoursesException {
		studentService.registerIntoCourse(1, 1);
		var relations = studentService.findAllRelationsBetweenStudentAndCourse();
		assertTrue(relations.size() > 0);
	}

	@Test
	@Order(2)
	public void mustDeleteCourseRegistration() throws NoDataToDeleteException {
		studentService.deleteCourseRegistration(1, 1);
		var relations = studentService.findAllRelationsBetweenStudentAndCourse();
		assertTrue(relations.size() == 0);
	}

	@Test
	@Order(3)
	public void mustNotDeleteCourseRegistration() {
		var control = false;
		try {
			studentService.deleteCourseRegistration(1, 1);
		} catch (NoDataToDeleteException e) {
			control = true;
		}
		assertTrue(control);
	}

	@Test
	@Order(4)
	public void mustNotRegisterCourseStudenteNotFoundTest() throws StudentAlreadyHasThisCourseException,
			CourseWithTooManyStudentsException, StudentWithTooManyCoursesException {
		var control = false;
		try {
			studentService.registerIntoCourse(1000000, 1);
		} catch (StudentOrCourseNotFoundException e) {
			control = true;
		}
		assertTrue(control);
	}

	@Test
	@Order(5)
	public void mustNotRegisterCourseCourseNotFoundTest() throws StudentAlreadyHasThisCourseException,
			CourseWithTooManyStudentsException, StudentWithTooManyCoursesException {
		var control = false;
		try {
			studentService.registerIntoCourse(1, 1000000);
		} catch (StudentOrCourseNotFoundException e) {
			control = true;
		}
		assertTrue(control);
	}

	@Test
	@Order(6)
	public void mustNotRegisterCourseStudentAlreadyHasThisCourseTest() throws CourseWithTooManyStudentsException,
			StudentWithTooManyCoursesException, StudentOrCourseNotFoundException, NoDataToDeleteException {
		var control = false;
		try {
			studentService.registerIntoCourse(1, 1);
			studentService.registerIntoCourse(1, 1);
		} catch (StudentAlreadyHasThisCourseException e) {
			control = true;
		}
		studentService.deleteCourseRegistration(1, 1);
		assertTrue(control);
	}

	@Test
	@Order(7)
	public void mustNotRegisterCourseCourseWithTooManyStudentsTest() throws StudentWithTooManyCoursesException,
			StudentOrCourseNotFoundException, NoDataToDeleteException, StudentAlreadyHasThisCourseException {
		var control = false;
		try {

			var intList = IntStream.rangeClosed(1, 51).boxed().collect(Collectors.toList());
			for (Integer row : intList) {
				studentService.registerIntoCourse(row, 1);
			}
		} catch (CourseWithTooManyStudentsException e) {
			control = true;
		}
		studentService.deleteCourseRegistration(1, 1);
		assertTrue(control);
	}

	@Test
	@Order(8)
	public void mustNotRegisterCourseStudentWithTooManyCoursesTest() throws CourseWithTooManyStudentsException,
			StudentOrCourseNotFoundException, NoDataToDeleteException, StudentAlreadyHasThisCourseException {
		var control = false;
		try {

			var intList = IntStream.rangeClosed(1, 6).boxed().collect(Collectors.toList());
			for (Integer row : intList) {
				studentService.registerIntoCourse(1, row);
			}
		} catch (StudentWithTooManyCoursesException e) {
			control = true;
		}
		studentService.deleteCourseRegistration(1, 1);
		assertTrue(control);
	}
}