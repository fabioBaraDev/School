package io.metadata.school.presentation.mappers;

import java.util.Optional;

import io.metadata.school.domain.exceptions.CourseDataNotProvidedException;
import io.metadata.school.domain.model.Course;
import io.metadata.school.presentation.dto.CourseDTO;

public class CourseMapper {


	public static Course mapperToDomain(CourseDTO dto) throws CourseDataNotProvidedException {
		if (dto.getName() == null || dto.getName().equals("")) {
			throw new CourseDataNotProvidedException();
		}
		return new Course(Optional.of(dto.getName()));
	}

	public static Course mapperToDomainWithId(Integer id, CourseDTO dto) throws CourseDataNotProvidedException {
		if (dto.getName() == null || dto.getName().equals("")) {
			throw new CourseDataNotProvidedException();
		}

		return new Course(Optional.of(dto.getName()), Optional.of(id));
	}

}
