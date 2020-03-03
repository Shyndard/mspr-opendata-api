package fr.mspr.opendata.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.mspr.opendata.service.EntryService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {

	@Autowired
	private EntryService entryService;
	
	@PostMapping(value = "/upload", consumes = "text/csv")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> uploadSimple(@RequestBody InputStream body) {
		try {
			entryService.save(body);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
}
