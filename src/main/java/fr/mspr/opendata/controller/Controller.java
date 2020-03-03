package fr.mspr.opendata.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.mspr.opendata.entity.dto.EntryDto;
import fr.mspr.opendata.service.EntryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

	@Autowired
	private EntryService entryService;
	
	@GetMapping(value = "/entry")
    public List<EntryDto> getAll() {
		return entryService.getAll();
    }
	
	@PostMapping(value = "/upload", consumes = "multipart/form-data")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> uploadCsv(@RequestParam("file") MultipartFile file) {
		try {
			entryService.save(file.getInputStream());
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	@PostMapping(value = "/upload", consumes = "text/csv")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> uploadCsv(@RequestBody InputStream body) {
		try {
			entryService.save(body);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
}
