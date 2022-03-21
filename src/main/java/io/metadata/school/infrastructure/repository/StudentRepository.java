package io.metadata.school.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import io.metadata.school.domain.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	@Query(value = "SELECT s.id, s.name FROM student s INNER JOIN course_registration cr ON (s.id = cr.student_id) INNER JOIN course c ON (c.id = cr.course_id) WHERE c.name LIKE %:name% ", nativeQuery = true)
	List<Student> findByCourseName(@Param("name") String name);

	@Query(value = "SELECT s.id, s.name FROM student s INNER JOIN course_registration cr ON (s.id = cr.student_id) INNER JOIN course c ON (c.id = cr.course_id) WHERE c.id = :id ", nativeQuery = true)
	List<Student> findByCourseId(@Param("id") Integer id);

	@Query(value = "SELECT s.id, s.name FROM student s LEFT JOIN course_registration cr ON (s.id = cr.student_id) WHERE cr.student_id IS NULL", nativeQuery = true)
	List<Student> findWithoutCourses();

	@Query(value = "SELECT s.id, s.name FROM student s WHERE s.name LIKE %:name% ", nativeQuery = true)
	List<Student> findByName(String name);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM student where id =:id ", nativeQuery = true)
	Integer deleteByStudentId(@Param("id") Integer id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM course_registration cr where cr.student_id =:studentId AND cr.course_id=:courseId ", nativeQuery = true)
	Integer deleteCourseRegistration(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

}
