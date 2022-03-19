package io.metadata.school.domain.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;

@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "course_registration", joinColumns = {
			@JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false, updatable = false) })
	private Set<Course> courses = new HashSet<>();

	/* 
	 * do not use this constructor, kept to JPA only
	 */
	public Student() {}
	
	public Student(Optional<String> name) throws StudentDataNotProvidedException {
		if (isNotAValidName(name)) {
			throw new StudentDataNotProvidedException();
		}
		this.name = name.get();
	}
		
	public Student(Optional<String> name, Optional<Integer> id) throws StudentDataNotProvidedException {
		if (isNotAValidName(name) || id.isEmpty()) {
			throw new StudentDataNotProvidedException();
		}
		this.id = id.get();
		this.name = name.get();
	}
	
	private Boolean isNotAValidName(Optional<String> name) {
		return name.isEmpty() || name.get().isBlank() || name.get().isEmpty();
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public void registerIntoCourse(Course course) {
		if (course.itDoesNotHasTooManyStudents() && itDoNotHasTooManyCourses()) {
			courses.add(course);
		}
	}

	private Boolean itDoNotHasTooManyCourses() {
		return courses.size() < 5;
	}
}
