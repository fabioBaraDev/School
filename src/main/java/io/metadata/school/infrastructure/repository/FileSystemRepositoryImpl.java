package io.metadata.school.infrastructure.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class FileSystemRepositoryImpl implements FileSystemRepository{
 
	public List<String> getFileData(String fileName) {
		InputStream in = new FileSystemRepositoryImpl().getClass().getResourceAsStream("/" + fileName);

		InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
		BufferedReader br = new BufferedReader(isr);
		
		return br.lines().collect(Collectors.toList());
	}
}
