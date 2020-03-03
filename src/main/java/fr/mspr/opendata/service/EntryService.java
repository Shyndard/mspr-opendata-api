package fr.mspr.opendata.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fr.mspr.opendata.entity.dto.EntryDto;

public interface EntryService {

    public void save(InputStream body) throws IOException;

	public List<EntryDto> getAll();
    
}