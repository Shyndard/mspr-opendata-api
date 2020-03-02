package fr.mspr.opendata.dao;

import fr.mspr.opendata.entity.dto.EntryDto;

public interface EntryDao {

	void save(EntryDto entry);
    
}