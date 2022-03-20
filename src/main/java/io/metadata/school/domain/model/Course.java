package io.metadata.school.domain.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.exceptions.CourseWithTooManyStudentsException;

@Entity
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Student> students = new HashSet<>();

	/* 
	 * do not use this constructor, kept to JPA only
	 */
	public Course() {}
	
	public Course(Optional<String> name) throws CourseDataNotProvidedException {
		if (isNotAValidName(name)) {
			throw new CourseDataNotProvidedException();
		}
		this.name = name.get();
	}

	public Course(Optional<String> name, Optional<Integer> id) throws CourseDataNotProvidedException {
		if (isNotAValidName(name)|| id.isEmpty()) {
			throw new CourseDataNotProvidedException();
		}
		this.id = id.get();
		this.name = name.get();
	}	

	private Boolean isNotAValidName(Optional<String> name) {
		return name.isEmpty() || name.get().isBlank() || name.get().isEmpty();
	}
	
	private Boolean itHasTooManyStudents() {
		return this.students.size() >= 50;
	}
	
	public void addStudent(Student student) throws CourseWithTooManyStudentsException {
		if(itHasTooManyStudents()) {
			throw new CourseWithTooManyStudentsException();
		}
		this.students.add(student);
	}

	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
