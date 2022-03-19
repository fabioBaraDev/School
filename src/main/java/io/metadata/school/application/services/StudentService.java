package io.metadata.school.application.services;

import java.util.List;
import java.util.Optional;

import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentAlreadyHasThisCourseException;
import io.metadata.school.application.services.exception.StudentNotFoundException;
import io.metadata.school.application.services.exception.StudentOrCourseNotFoundException;
import io.metadata.school.domain.model.Student;

public interface StudentService {

	public Student save(Student student);
	public Student update(Student student) throws StudentNotFoundException;
	public void deleteById(Integer id) throws NoDataToDeleteException;
	public List<Student> findByName(String name);
	public Optional<Student> findById(Integer id);
	public List<Student> findByCourseName(String name);
	public List<Student> findByCourseId(Integer id);
	public List<Student> findWithoutCourse();
	public void registerIntoCourse(Integer studentId, Integer courseId) throws StudentOrCourseNotFoundException, StudentAlreadyHasThisCourseException;
	public List<Student> findAllRelationsBetweenStudentAndCourse();
	public List<Student> findAll();
	public void deleteCourseRegistration(Integer studentId, Integer courseId) throws NoDataToDeleteException;
}
