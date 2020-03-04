package fr.mspr.opendata.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import fr.mspr.opendata.dao.EntryDao;
import fr.mspr.opendata.entity.dto.EntryDto;
import fr.mspr.opendata.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {

	@Autowired
	private EntryDao entryDao;

	public void save(InputStream file, String contentType) throws IOException {
		if ("text/xml".equals(contentType)) {
			saveFromXml(file);
		} else {
			saveFromCsv(file);
		}
	}

	private void saveFromXml(InputStream file) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(file);
			document.getDocumentElement().normalize();
			NodeList nodeList = document.getElementsByTagName("import");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node nNode = nodeList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String id = eElement.getElementsByTagName("id").item(0).getTextContent();
					String name = eElement.getElementsByTagName("name").item(0).getTextContent();
					String value = eElement.getElementsByTagName("value").item(0).getTextContent();
					entryDao.save(new EntryDto(id, name, value));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void saveFromCsv(InputStream file) {
		try {
			CSVParser records = CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(file));
			records.forEach(item -> {
				if (item.size() == 1) {
					String[] data = getCsvData(item.get(0));
					if (data.length == 3) {
						entryDao.save(new EntryDto(data[0], data[1], data[2]));
					}
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String[] getCsvData(String data) {
		String[] split = data.split(";");
		return split.length == 0 ? data.split(",") : split;
	}

	@Override
	public List<EntryDto> getAll() {
		List<EntryDto> result = new ArrayList<>();
		entryDao.findAll().forEach(result::add);
		return result;
	}

}