package it.fmoon.installer.service;

import java.util.Collection;

import it.fmoon.installer.model.CareChangelogFile;
import it.fmoon.installer.model.CareModule;

public interface ChangeLogsService {

	void analyzeChangeLogFile(CareChangelogFile changeLogFile);

	Collection<CareModule> scanAndGetModules();
	
	Collection<CareModule> getModules();

}
