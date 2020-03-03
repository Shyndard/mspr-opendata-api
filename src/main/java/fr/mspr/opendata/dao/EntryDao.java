package fr.mspr.opendata.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.mspr.opendata.entity.dto.EntryDto;

@Repository
public interface EntryDao extends CrudRepository<EntryDto, Long> {

    
}