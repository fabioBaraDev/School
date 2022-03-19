package io.metadata.school.infrastructure.repository;

import java.util.List;

public interface FileSystemRepository {
	public List<String> getFileData(String fileName);
}
