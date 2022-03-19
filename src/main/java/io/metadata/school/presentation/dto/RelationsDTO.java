package io.metadata.school.presentation.dto;

import java.util.ArrayList;
import java.util.List;

import io.metadata.school.domain.model.Student;

public class RelationsDTO {
	private List<RelationResponseDTO> relations = new ArrayList<>();
	
	public RelationsDTO(List<Student> students) {
		students.forEach(student -> student.getCourses().forEach(course -> getRelations().add(new RelationResponseDTO(student, course))));
	}

	public List<RelationResponseDTO> getRelations() {
		return relations;
	}
}
