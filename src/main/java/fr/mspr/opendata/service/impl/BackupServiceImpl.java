package fr.mspr.opendata.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;

import fr.mspr.opendata.dao.BackupDao;
import fr.mspr.opendata.service.BackupService;

@Service
public class BackupServiceImpl implements BackupService {

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

	@Override
	public void startBackup() throws Exception {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(key, token);
		AmazonS3ClientBuilder builder = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds));
		builder.setEndpointConfiguration(new EndpointConfiguration(endpoint, "fr-par"));

		String pattern = "MM-dd-yyyy--HH-mm-ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("fr", "FR"));
		String backupName = simpleDateFormat.format(new Date()) + ".zip";
		File file = new File(backupName);
		try {
			backupDao.startBackup(backupName);
			AmazonS3 client = builder.build();
			
			client.putObject(new PutObjectRequest(name, backupName, file));
		} catch (Exception ex) {
			throw new Exception("Backup creation failed");
		} finally {
			file.delete();
		}
	}
}
