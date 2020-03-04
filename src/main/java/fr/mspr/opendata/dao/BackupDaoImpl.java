package fr.mspr.opendata.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BackupDaoImpl implements BackupDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void startBackup(String name) {
		jdbcTemplate.batchUpdate("BACKUP TO '" + name + "';");
	}

}
