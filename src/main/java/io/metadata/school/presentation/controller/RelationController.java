package io.metadata.school.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.metadata.school.application.services.StudentService;
import io.metadata.school.application.services.exception.NoDataToDeleteException;
import io.metadata.school.application.services.exception.StudentAlreadyHasThisCourseException;
import io.metadata.school.application.services.exception.StudentOrCourseNotFoundException;
import io.metadata.school.domain.exceptions.CourseWithTooManyStudentsException;
import io.metadata.school.domain.exceptions.StudentWithTooManyCoursesException;
import io.metadata.school.presentation.constants.Constants;
import io.metadata.school.presentation.dto.RelationDTO;
import io.metadata.school.presentation.dto.RelationsDTO;

@RestController
@RequestMapping("relation")
public class RelationController {

	@Autowired
	private StudentService service;

	@GetMapping
	public ResponseEntity<RelationsDTO> getAll() {

		try {

			var students = service.findAllRelationsBetweenStudentAndCourse();
			var studentsRelations = new RelationsDTO(students);

			if (studentsRelations.getRelations().size() > 0) {
				return new ResponseEntity<RelationsDTO>(studentsRelations, HttpStatus.OK);
			}
			return new ResponseEntity<RelationsDTO>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RelationsDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping
	public ResponseEntity<String> registerStudentToCourse(@RequestBody RelationDTO relation) {
		try {
			service.registerIntoCourse(relation.getStudentId(), relation.getCourseId());
		} catch (StudentAlreadyHasThisCourseException | CourseWithTooManyStudentsException
				| StudentWithTooManyCoursesException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (StudentOrCourseNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(Constants.CONFIRM_REGISTRATION, HttpStatus.CREATED);
	}

	@DeleteMapping("{student_id}/{course_id}")
	public ResponseEntity<String> deleteRegister(@PathVariable("student_id") Integer studentId,
			@PathVariable("course_id") Integer courseId) {
		try {
			service.deleteCourseRegistration(studentId, courseId);
			return new ResponseEntity<String>(Constants.CONFIRM_REGISTRATION_DELETION, HttpStatus.ACCEPTED);
		} catch (NoDataToDeleteException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
