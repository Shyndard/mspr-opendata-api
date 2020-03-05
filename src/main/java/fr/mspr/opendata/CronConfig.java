package fr.mspr.opendata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import fr.mspr.opendata.service.BackupService;

@Configuration
@EnableScheduling
public class CronConfig {

	@Autowired
	private BackupService backupService;

	@Scheduled(cron = "0 0 6 * * ?")
	public void startBackup() {
		try {
			backupService.startBackup();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
