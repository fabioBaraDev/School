package io.metadata.school.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.metadata.school.domain.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query(value = "SELECT c.id, c.name FROM student s INNER JOIN course_registration cr ON (s.id = cr.student_id) INNER JOIN course c ON (c.id = cr.course_id) WHERE s.name LIKE %:name% ", nativeQuery = true)
	List<Course> findByStudentName(@Param("name") String name);

	@Query(value = "SELECT c.id, c.name FROM student s INNER JOIN course_registration cr ON (s.id = cr.student_id) INNER JOIN course c ON (c.id = cr.course_id) WHERE s.id = :id ", nativeQuery = true)
	List<Course> findByStudentId(@Param("id") Integer id);

	@Query(value = "SELECT c.id, c.name FROM course c LEFT JOIN course_registration cr ON (c.id = cr.course_id) WHERE cr.course_id IS NULL", nativeQuery = true)
	List<Course> findCoursesWithoutStudents();
	
	@Query(value = "SELECT c.id, c.name FROM course c WHERE c.name LIKE %:name% ", nativeQuery = true)
	List<Course> findByName(String name);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM course where id =:id ", nativeQuery = true)
	Integer deleteByCourseId(@Param("id") Integer id);

}
