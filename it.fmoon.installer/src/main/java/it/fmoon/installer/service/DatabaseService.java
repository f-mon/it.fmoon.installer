package it.fmoon.installer.service;

import liquibase.database.Database;

public interface DatabaseService {
	
	Database getDatabase();
	
}
