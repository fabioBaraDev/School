package io.metadata.school.presentation.mappers;

import java.util.Optional;

import io.metadata.school.domain.exceptions.StudentDataNotProvidedException;
import io.metadata.school.domain.model.Student;
import io.metadata.school.presentation.dto.StudentDTO;

public class StudentMapper {

	public static Student mapperToDomain(StudentDTO dto) throws StudentDataNotProvidedException {
		if (dto.getName() == null || dto.getName().equals("")) {
			throw new StudentDataNotProvidedException();
		}
		return new Student(Optional.of(dto.getName()));
	}

	public static Student mapperToDomainWithId(Integer id, StudentDTO dto) throws StudentDataNotProvidedException {
		if (dto.getName() == null || dto.getName().equals("")) {
			throw new StudentDataNotProvidedException();
		}

		return new Student(Optional.of(dto.getName()), Optional.of(id));
	}

}