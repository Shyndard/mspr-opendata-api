package fr.mspr.opendata.service.impl;

import java.io.IOException;
import java.io.InputStream;

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
		entryDao.saveAll(CsvUtils.read(EntryDto.class, body));
    }

}