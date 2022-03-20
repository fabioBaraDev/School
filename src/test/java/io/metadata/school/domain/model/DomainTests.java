package io.metadata.school.domain.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.exceptions.CourseWithTooManyStudentsException;
import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.exceptions.StudentWithTooManyCoursesException;

@SpringBootTest
public class DomainTests {

	@Test
	void studentMustNotHaveMoreThan5CoursesTest()
			throws StudentDataNotProvidedException, CourseWithTooManyStudentsException, CourseDataNotProvidedException {

		var studentHasTooManyCouses = false;
		var student = new Student(Optional.of("Jon"));
		var IntList = getIntListByRange(1, 6);

		for (Integer row : IntList) {
			try {
				student.registerIntoCourse(getMockCourse(row));
			} catch (StudentWithTooManyCoursesException e) {
				studentHasTooManyCouses = true;
			}

		}

		assertTrue(studentHasTooManyCouses);
	}

	void studentMustHaveLessThan5CoursesTest()
			throws StudentDataNotProvidedException, CourseWithTooManyStudentsException, CourseDataNotProvidedException {

		var studentHasLessThan5Couses = true;
		var student = new Student(Optional.of("Jon"));
		var IntList = getIntListByRange(1, 3);

		for (Integer row : IntList) {
			try {
				student.registerIntoCourse(getMockCourse(row));
			} catch (StudentWithTooManyCoursesException e) {
				studentHasLessThan5Couses = false;
			}

		}

		if (student.getCourses().size() > 5) {
			studentHasLessThan5Couses = false;
		}

		assertTrue(studentHasLessThan5Couses);
	}

	@Test
	void courseMustNotHaveMoreThan50StudentsTest()
			throws StudentDataNotProvidedException, StudentWithTooManyCoursesException, CourseDataNotProvidedException {

		var courseHasTooManyStudents = false;
		var course = new Course(Optional.of("Medicine"), Optional.of(1));
		var IntList = getIntListByRange(1, 51);

		for (Integer row : IntList) {
			try {
				var student = new Student(Optional.of("Student " + row), Optional.of(row));
				student.registerIntoCourse(course);
			} catch (CourseWithTooManyStudentsException e) {
				courseHasTooManyStudents = true;
			}
		}

		assertTrue(courseHasTooManyStudents);
	}

	void courseMustHaveLessThan50StudentsTest()
			throws StudentDataNotProvidedException, StudentWithTooManyCoursesException, CourseDataNotProvidedException {

		var courseHasLess50Students = true;
		var course = new Course(Optional.of("Medicine"), Optional.of(1));
		var IntList = getIntListByRange(1, 51);

		for (Integer row : IntList) {
			try {
				var student = new Student(Optional.of("Student " + row), Optional.of(row));
				student.registerIntoCourse(course);
			} catch (CourseWithTooManyStudentsException e) {
				courseHasLess50Students = false;
			}
		}
		
		try {
			course.addStudent(new Student(Optional.of("Student "), Optional.of(100)));
		} catch (CourseWithTooManyStudentsException e) {
			courseHasLess50Students = false;
		}

		assertTrue(courseHasLess50Students);
	}

	private List<Integer> getIntListByRange(int init, int end) {
		return IntStream.rangeClosed(init, end).boxed().collect(Collectors.toList());
	}

	private Course getMockCourse(Integer num) throws CourseDataNotProvidedException {
		return new Course(Optional.of("Course " + num));
	}

}
