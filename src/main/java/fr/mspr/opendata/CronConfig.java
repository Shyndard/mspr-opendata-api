package fr.mspr.opendata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import fr.mspr.opendata.dao.BackupDao;

@Configuration
@EnableScheduling
public class CronConfig {

	@Value("${s3.bucket.key}")
	private String key;

	@Value("${s3.bucket.token}")
	private String token;

	@Value("${s3.bucket.name}")
	private String name;

	@Value("${s3.bucket.endpoint}")
	private String endpoint;
	
	@Value("${s3.bucket.region}")
	private String region;

	@Autowired
	private BackupDao backupDao;

	@Scheduled(cron = "0 0 6 * * ?")
	public void startBackup() {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(key, token);
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds));
		builder.setEndpointConfiguration(new EndpointConfiguration(endpoint, "fr-par"));

		String pattern = "MM-dd-yyyy--HH-mm-ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		String backupName = simpleDateFormat.format(new Date()) + ".zip";
		backupDao.startBackup(backupName);
		AmazonS3 client = builder.build();
		client.putObject(new PutObjectRequest(name, backupName, new File(backupName)));
	}
}
