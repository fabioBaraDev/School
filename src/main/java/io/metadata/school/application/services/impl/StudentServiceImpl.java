package io.metadata.school.application.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.StudentService;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentAlreadyHasThisCourseException;
import io.metadata.school.application.services.exception.StudentNotFoundException;
import io.metadata.school.application.services.exception.StudentOrCourseNotFoundException;
import io.metadata.school.domain.exceptions.CourseWithTooManyStudentsException;
import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.exceptions.StudentWithTooManyCoursesException;
import io.metadata.school.domain.model.Course;
import io.metadata.school.domain.model.Student;
import io.metadata.school.infrastructure.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Autowired
	private CourseService courseService;

	@Override
	public Student save(Student student) {
		return repository.save(student);
	}

	@Override
	public Student update(Student student) throws StudentNotFoundException, StudentDataNotProvidedException {
		var studentFound = findById(student.getId());
		if (studentFound.isEmpty())
			throw new StudentNotFoundException();

		var name = Optional.of(student.getName());
		var id = Optional.of(studentFound.get().getId());
		var studentUpdated = new Student(name, id);
				
		return repository.save(studentUpdated);
	}

	@Override
	public void deleteById(Integer id) throws NoDataToDeleteException {
		var rowsAffected = repository.deleteByStudentId(id);
		verifyRowsAffected(rowsAffected);
	}

	private void verifyRowsAffected(Integer rowsAffected) throws NoDataToDeleteException {
		if (!(rowsAffected > 0)) {
			throw new NoDataToDeleteException();
		}
	}

	@Override
	public List<Student> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Student> findAll() {
		return repository.findAll();
	}

	@Override
	public List<Student> findByCourseName(String name) {
		return repository.findByCourseName(name);
	}

	@Override
	public List<Student> findByCourseId(Integer id) {
		return repository.findByCourseId(id);
	}

	@Override
	public List<Student> findWithoutCourse() {
		return repository.findWithoutCourses();
	}

	@Override
	public Optional<Student> findById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public void registerIntoCourse(Integer studentId, Integer courseId)
			throws StudentOrCourseNotFoundException, StudentAlreadyHasThisCourseException,
			CourseWithTooManyStudentsException, StudentWithTooManyCoursesException {

		var student = findById(studentId);
		var course = courseService.findById(courseId);

		validateData(student, course);
		isItAlredyHasThisCourse(student.get(), courseId);
		registerStudentIntoCourse(student.get(), course.get());

	}

	private void validateData(Optional<Student> student, Optional<Course> course)
			throws StudentOrCourseNotFoundException {
		if (student.isEmpty() || course.isEmpty()) {
			throw new StudentOrCourseNotFoundException();
		}
	}

	private void isItAlredyHasThisCourse(Student student, Integer courseId)
			throws StudentAlreadyHasThisCourseException {
		if (student.getCourses().stream().filter(row -> row.getId() == courseId).count() > 0) {
			throw new StudentAlreadyHasThisCourseException();
		}

	}

	private void registerStudentIntoCourse(Student student, Course course)
			throws CourseWithTooManyStudentsException, StudentWithTooManyCoursesException {
		student.registerIntoCourse(course);
		save(student);
	}

	@Override
	public List<Student> findAllRelationsBetweenStudentAndCourse() {
		return repository.findAll().stream().filter(row -> row.getCourses().size() > 0).collect(Collectors.toList());
	}

	@Override
	public void deleteCourseRegistration(Integer studentId, Integer courseId) throws NoDataToDeleteException {
		var rowsAffected = repository.deleteCourseRegistration(studentId, courseId);
		verifyRowsAffected(rowsAffected);
	}
}
