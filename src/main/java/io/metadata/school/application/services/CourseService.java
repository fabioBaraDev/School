package io.metadata.school.application.services;

import java.util.List;
import java.util.Optional;

import io.metadata.school.application.services.exception.CourseNotFoundException;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.domain.model.Course;

public interface CourseService {

	public Course save(Course course);
	public Course update(Course course) throws CourseNotFoundException;
	public void deleteByCourseId(Integer id) throws NoDataToDeleteException;
	public List<Course> findByName(String name);
	public Optional<Course> findById(Integer id);
	public List<Course> findByStudentName(String name);
	public List<Course> findByStudentId(Integer id);
	public List<Course> findWithoutStudent();
	public List<Course> findAll();
	
}