package io.metadata.school.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import io.metadata.school.application.services.InitialLoaderService;
import io.metadata.school.application.services.StudentService;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentNotFoundException;
import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.model.Student;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class StudentServiceTests {

	@Autowired
	private StudentService service;

	@BeforeAll
	public static void init(@Autowired InitialLoaderService loaderService) {
		loaderService.loadAllData();
	}

	@Test
	public void mustSaveStudentTest() throws StudentDataNotProvidedException {
		
		var student = new Student(Optional.of("Alexandro"));
		var savedStudent = service.save(student);
		assertTrue(savedStudent.getName() == "Alexandro");
	}

	@Test
	public void mustUpdateStudentTest() throws StudentDataNotProvidedException, StudentNotFoundException {
		var student = new Student(Optional.of("Alexandro"), Optional.of(1));
		var updatedStudent = service.update(student);
		assertTrue(updatedStudent.getId() == 1 && updatedStudent.getName() == "Alexandro");
	}

	@Test
	public void mustNotUpdateStudentTest() throws StudentDataNotProvidedException {
		var isNotUpdated = false;
		var student = new Student(Optional.of("Alexandro"), Optional.of(100000000));
		try {
			service.update(student);
		} catch (StudentNotFoundException e) {
			isNotUpdated = true;
		}
		assertTrue(isNotUpdated);
	}

	@Test
	public void mustDeleteStudentTest() throws NoDataToDeleteException {
		service.deleteById(1);
		var student = service.findById(1);
		assertTrue(student.isEmpty());
	}

	@Test 
	public void mustNotDeleteStudentTest() {
		var isNotDeleted = false;
		try {
			service.deleteById(100000);
		} catch (NoDataToDeleteException e) {
			isNotDeleted = true;
		}
		assertTrue(isNotDeleted);
	}

	@Test
	public void mustFindStudentByNameTest() {
		var student = service.findByName("Eva");
		assertTrue(student.size() > 0);
	}

	@Test
	public void mustNotFindStudentByNameTest() {
		var student = service.findByName("GlassRabs");
		assertTrue(student.size() == 0);
	}

	@Test
	public void mustFindStudentByIdTest() {
		var student = service.findById(4);
		assertTrue(student.isPresent());
	}

	@Test
	public void mustNotFindStudentByIdTest() {
		var student = service.findById(40000);
		assertTrue(student.isEmpty());
	}

	@Test
	public void mustNotFindStudentByCourseNameTest() {
		var student = service.findByCourseName("Medicine");
		assertTrue(student.isEmpty());
	}

	@Test
	public void mustNotFindStudentByCourseIdTest() {
		var student = service.findByCourseId(2);
		assertTrue(student.isEmpty());
	}

	@Test
	public void mustFindStudentWithoutCourseTest() {
		var student = service.findWithoutCourse();
		assertTrue(student.size() > 0);
	}

	@Test
	public void mustFindAllStudentsTest() {
		var student = service.findAll();
		assertTrue(student.size() > 0);
	}
}
