package fr.mspr.opendata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mspr.opendata.dao.EntryDao;
import fr.mspr.opendata.entity.dto.EntryDto;
import fr.mspr.opendata.service.CsvUtils;
import fr.mspr.opendata.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryDao entryDao;

	public void save(InputStream body) throws IOException {
        saveAll(CsvUtils.read(EntryDto.class, body));
    }

	private void saveAll(List<EntryDto> entries) {
        entries.forEach(entry -> {
            entryDao.save(entry);
        });
	}

}