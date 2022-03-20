package io.metadata.school.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.metadata.school.application.services.StudentService;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentNotFoundException;
import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.model.Student;
import io.metadata.school.presentation.constants.Constants;
import io.metadata.school.presentation.dto.StudentDTO;
import io.metadata.school.presentation.mappers.StudentMapper;

@RestController
@RequestMapping("student")
public class StudentController {

	@Autowired
	private StudentService service;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody StudentDTO studentDTO) {
		try {
			var student = StudentMapper.mapperToDomain(studentDTO);
			service.save(student);
			return new ResponseEntity<String>(Constants.STUDENT_INSERTED, HttpStatus.CREATED);
		} catch (StudentDataNotProvidedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody StudentDTO studentDTO) {
		try {
			var student = StudentMapper.mapperToDomainWithId(id, studentDTO);
			service.update(student);
			return new ResponseEntity<String>(Constants.STUDENT_UPDATED, HttpStatus.ACCEPTED);
		} catch (StudentDataNotProvidedException | StudentNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		try {
			service.deleteById(id);
			return new ResponseEntity<String>(Constants.STUDENT_DELETED, HttpStatus.ACCEPTED);

		} catch (NoDataToDeleteException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(Constants.STUDENT_HAS_REGISTRATION_ERROR, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Student>> getAll() {

		try {
			var student = service.findAll();
			return getResponse(student);

		} catch (Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Student> getById(@PathVariable Integer id) {

		try {
			var student = service.findById(id);

			if (student.isPresent()) {
				return new ResponseEntity<Student>(student.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Student>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Student>> getByName(@PathVariable String name) {

		try {
			var student = service.findByName(name);
			return getResponse(student);

		} catch (Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/course/id/{id}")
	public ResponseEntity<List<Student>> getByCourseId(@PathVariable Integer id) {

		try {
			var student = service.findByCourseId(id);
			return getResponse(student);

		} catch (Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/course/name/{name}")
	public ResponseEntity<List<Student>> getByCourseName(@PathVariable String name) {

		try {
			var student = service.findByCourseName(name);
			return getResponse(student);

		} catch (Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/course/empty")
	public ResponseEntity<List<Student>> getWithNoCourse() {

		try {
			var student = service.findWithoutCourse();
			return getResponse(student);

		} catch (Exception e) {
			return new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<List<Student>> getResponse(List<Student> student) {
		if (student.size() > 0) {
			return new ResponseEntity<List<Student>>(student, HttpStatus.OK);

		}
		return new ResponseEntity<List<Student>>(HttpStatus.NOT_FOUND);
	}
}
