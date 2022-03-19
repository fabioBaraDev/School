package io.metadata.school.application.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.InitialLoaderService;
import io.metadata.school.application.services.StudentService;
import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.model.Course;
import io.metadata.school.domain.model.Student;
import io.metadata.school.infrastructure.repository.FileSystemRepository;

@Service
public class InitialLoaderServiceImpl implements InitialLoaderService {

	@Autowired
	private FileSystemRepository repository;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	List<String> studentNames;
	List<String> coursesNames;
	List<Student> students;
	List<Course> courses;

	@Override
	public void loadAllData() {

		getStudentAndCoursesNamesFromFile();
		buildStudents();
		buildCourses();
		saveAllStudents();
		saveAllCourses();
	}

	private void getStudentAndCoursesNamesFromFile() {
		studentNames = repository.getFileData("students_initial_data.txt");
		coursesNames = repository.getFileData("courses_initial_data.txt");
	}

	private void buildStudents() {
		students = studentNames.stream().map(name -> getStudent(name)).collect(Collectors.toList());
	}

	private Student getStudent(String name) {
		try {
			return new Student(Optional.of(name));
		} catch (StudentDataNotProvidedException e) {
			System.out.println("Problem to load students names");
		}
		return null;
	}

	private void buildCourses() {
		courses = coursesNames.stream().map(name -> getCourse(name)).collect(Collectors.toList());
	}

	private Course getCourse(String name) {
		try {
			return new Course(Optional.of(name));
		} catch (CourseDataNotProvidedException e) {
			System.out.println("Problem to load students names");
		}
		return null;
	}

	private void saveAllStudents() {
		students.forEach(student -> studentService.save(student));
	}

	private void saveAllCourses() {
		courses.forEach(course -> courseService.save(course));
	}
}
