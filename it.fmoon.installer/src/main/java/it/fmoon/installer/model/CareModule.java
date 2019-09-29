package it.fmoon.installer.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class CareModule implements ChangeLogElement {

	private String moduleName;

	private Set<CareModuleVersion> versions = new LinkedHashSet<>();
	
	private Set<CareChangelogFile> changeLogFiles = new LinkedHashSet<>();

	public CareModule(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	
	public Collection<CareChangelogFile> getChangeLogFiles() {
		return Collections.unmodifiableCollection(changeLogFiles);
	}
	public Collection<CareModuleVersion> getVersions() {
		return Collections.unmodifiableCollection(versions);
	}

	public void addChangelog(CareChangelogFile file) {
		this.changeLogFiles.add(file);
		file.setModule(this,null);
	}

	public CareModuleVersion getOrCreateVersion(String versionTag) {
		return versions.stream()
			.filter(v->versionTag.equals(v.getVersionTag()))
			.findFirst()
			.orElseGet(()->{
				CareModuleVersion version = new CareModuleVersion(this,versionTag);
				versions.add(version);
				return version;
			});
	}

}
