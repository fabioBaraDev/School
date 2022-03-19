package io.metadata.school.application.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.exception.CourseNotFoundException;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.domain.model.Course;
import io.metadata.school.infrastructure.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository repository;
	
	@Override
	public Course save(Course course) {
		return repository.save(course);
	}

	@Override
	public Course update(Course course) throws CourseNotFoundException {
		var courseFound = findById(course.getId());
		if(courseFound.isEmpty())
			throw new CourseNotFoundException();
		
		courseFound.get().setName(course.getName());
		return repository.save(courseFound.get());
	}

	@Override
	public void deleteByCourseId(Integer id) throws NoDataToDeleteException{
		var rowsAffected = repository.deleteByCourseId(id);
		verifyRowsAffected(rowsAffected);
	}

	private void verifyRowsAffected(Integer rowsAffected) throws NoDataToDeleteException {
		if(!(rowsAffected > 0)) {
			throw new NoDataToDeleteException();
		}
	}

	@Override
	public List<Course> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Course> findByStudentName(String name) {
		return repository.findByStudentName(name);
	}

	@Override
	public List<Course> findByStudentId(Integer id) {
		return repository.findByStudentId(id);
	}

	@Override
	public List<Course> findWithoutStudent() {
		return repository.findCoursesWithoutStudents();
	}

	@Override
	public Optional<Course> findById(Integer id) {
		return repository.findById(id);
	}
	

	@Override
	public List<Course> findAll() {
		return repository.findAll();
	}
}
