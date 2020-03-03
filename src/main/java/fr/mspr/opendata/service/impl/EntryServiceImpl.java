package fr.mspr.opendata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.mspr.opendata.dao.EntryDao;
import fr.mspr.opendata.entity.dto.EntryDto;
import fr.mspr.opendata.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryDao entryDao;

	public void save(InputStream file) throws IOException {
		CSVParser records = CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(file));
		records.forEach(item -> {
			entryDao.save(new EntryDto(item.get(0), item.get(1), item.get(2)));
		});
    }

	@Override
	public List<EntryDto> getAll() {
		List<EntryDto> result = new ArrayList<>();
		entryDao.findAll().forEach(result::add);
		return result;
	}

}