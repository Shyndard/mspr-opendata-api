package fr.mspr.opendata.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.mspr.opendata.entity.dto.EntryDto;
import fr.mspr.opendata.service.BackupService;
import fr.mspr.opendata.service.EntryService;
import io.swagger.annotations.Api;

@RestController
@Api(value = "Data management")
public class Controller {

	@Autowired
	private EntryService entryService;
	@Autowired
	private BackupService backupService;

	@GetMapping(value = "/entry")
	public List<EntryDto> getAll() {
		return entryService.getAll();
	}
	
	@GetMapping(value = "/backup")
	public ResponseEntity<Object> backup() {
		try {
			backupService.startBackup();
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file) {
		try {
			entryService.save(file.getInputStream(), file.getContentType());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
