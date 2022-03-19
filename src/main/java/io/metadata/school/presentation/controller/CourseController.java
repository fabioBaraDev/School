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

import io.metadata.school.application.services.CourseService;
import io.metadata.school.application.services.exception.CourseNotFoundException;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.model.Course;
import io.metadata.school.presentation.dto.CourseDTO;
import io.metadata.school.presentation.mappers.CourseMapper;
import io.metadata.school.presentation.constants.Constants;

@RestController
@RequestMapping("course")
public class CourseController {

	@Autowired
	private CourseService service;

	@PostMapping
	public ResponseEntity<String> create(@RequestBody CourseDTO courseDTO) {
		try {
			var course = CourseMapper.mapperToDomain(courseDTO);
			service.save(course);

			return new ResponseEntity<String>(Constants.COURSE_INSERTED, HttpStatus.CREATED);
		} catch (CourseDataNotProvidedException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody CourseDTO courseDTO) {
		try {
			var course = CourseMapper.mapperToDomainWithId(id, courseDTO);
			service.update(course);
			return new ResponseEntity<String>(Constants.COURSE_UPDATED, HttpStatus.ACCEPTED);
		} catch (CourseDataNotProvidedException | CourseNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {
		try {
			service.deleteByCourseId(id);
			return new ResponseEntity<String>(Constants.COURSE_DELETED, HttpStatus.ACCEPTED);
		} catch (NoDataToDeleteException e) {
			return new ResponseEntity<String>(
					e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}catch (DataIntegrityViolationException e) {
			return new ResponseEntity<String>(
					Constants.COURSE_HAS_REGISTRATION_ERROR,
					HttpStatus.BAD_REQUEST);
		}  catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
	public ResponseEntity<List<Course>> getAll() {

		try {
			var course = service.findAll();
			return getResponse(course);

		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/id/{id}")
	public ResponseEntity<Course> getById(@PathVariable Integer id) {

		try {
			var course = service.findById(id);

			if (course.isPresent()) {
				return new ResponseEntity<Course>(course.get(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Course>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Course>> getByName(@PathVariable String name) {

		try {
			var course = service.findByName(name);
			return getResponse(course);

		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/student/id/{id}")
	public ResponseEntity<List<Course>> getByStudentId(@PathVariable Integer id) {

		try {
			var course = service.findByStudentId(id);
			return getResponse(course);

		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/student/name/{name}")
	public ResponseEntity<List<Course>> getByStudentName(@PathVariable String name) {

		try {
			var course = service.findByStudentName(name);
			return getResponse(course);

		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/student/empty")
	public ResponseEntity<List<Course>> getWithNoStudent() {

		try {
			var course = service.findWithoutStudent();
			return getResponse(course);

		} catch (Exception e) {
			return new ResponseEntity<List<Course>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private ResponseEntity<List<Course>> getResponse(List<Course> course) {
		if (course.size() > 0) {
			return new ResponseEntity<List<Course>>(course, HttpStatus.OK);

		}
		return new ResponseEntity<List<Course>>(HttpStatus.NOT_FOUND);
	}

}
