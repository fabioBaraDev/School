package io.metadata.school.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import io.metadata.school.application.services.InitialLoaderService;

@Controller
public class InitialLoadController {

	@Autowired
	private InitialLoaderService service;
	
	public void load() {
		service.loadAllData();
	}
}
