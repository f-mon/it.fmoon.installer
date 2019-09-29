package it.fmoon.installer.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CareModuleVersion implements ChangeLogElement {
	
	private String versionTag;
	private CareModule careModule;

	public CareModuleVersion(CareModule careModule, String versionTag) {
		this.careModule = careModule;
		this.versionTag = versionTag;
	}
	
	public String getVersionTag() {
		return versionTag;
	}
	public CareModule getCareModule() {
		return careModule;
	}

	private Set<CareChangelogFile> changeLogFiles = new LinkedHashSet<>();
	
	public Collection<CareChangelogFile> getChangeLogFiles() {
		return Collections.unmodifiableCollection(changeLogFiles);
	}

	public void addChangeLogFile(CareChangelogFile careChangelogFile) {
		changeLogFiles.add(careChangelogFile);
		careChangelogFile.setModule(careModule, this);
	}
	
	
	
}
