package fr.mspr.opendata.service;

import java.io.IOException;
import java.io.InputStream;

public interface EntryService {

    public void save(InputStream body) throws IOException;
    
}