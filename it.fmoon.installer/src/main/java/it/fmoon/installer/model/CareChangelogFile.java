package it.fmoon.installer.model;

import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import it.fmoon.installer.utils.StringPathUtils;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;

public class CareChangelogFile implements ChangeLogElement {
	
	private static Pattern changelogFileNamePattern = Pattern.compile("db\\.changelog-(\\d\\d\\d\\d-\\d\\d-\\d\\d)-(.+)\\.(.+)");
	
	private CareModule careModule;
	
	private CareModuleVersion version;
	private String date;
	
	private Path resource;
	
	private String changeSetName;
	private String extension;

	private DatabaseChangeLog databaseChangeLog;

	public static boolean isAChangelog(Path file) {
		String filename = file.getFileName().toString();
		return changelogFileNamePattern.asPredicate().test(filename);
	}

	public static CareChangelogFile fromFile(
			Path file, 
			CareModuleRepository moduleRepository) 
	{
		CareChangelogFile changelogFile = new CareChangelogFile();
		changelogFile.resource = file;
		
		Matcher matcher = changelogFileNamePattern.matcher(file.getFileName().toString());
		if (matcher.find()) {
			
			changelogFile.date = matcher.group(1);
			changelogFile.changeSetName = matcher.group(2);
			changelogFile.extension = matcher.group(3);
		}
		
		String fullPath = file.toString();
		String moduleName = StringPathUtils.extract(fullPath,
			"db"+File.separator+"changelog"+File.separator,
			File.separator);
		if (moduleName!=null) {
			System.out.println("Module -> "+moduleName);
			
			String versionTag = null;
			String parentFileName = file.getParent().getFileName().toString();
			if (!parentFileName.equals(moduleName)) {
				versionTag = parentFileName;
			}
			
			CareModule module = moduleRepository.getOrCreateModule(moduleName);
			CareModuleVersion version = null;
			if (versionTag!=null) {
				version = module.getOrCreateVersion(versionTag);
			}
			changelogFile.setModule(module,version);
		}
		return changelogFile;
	}


	public CareModule getCareModule() {
		return careModule;
	}	
	
	public String getName() {
		return this.changeSetName!=null?this.changeSetName:"???";
	}
	
	public DatabaseChangeLog getChangeLog() {
		return databaseChangeLog;
	}
	public boolean hasChangeLogInfo() {
		return databaseChangeLog!=null;
	}
	
	public Stream<ChangeSet> getChangeSets() {
		if (hasChangeLogInfo()) {			
			return databaseChangeLog.getChangeSets().stream();
		}
		return null;
	}

	public void setModule(CareModule careModule, CareModuleVersion version) {
		if (this.careModule!=careModule) {			
			this.careModule = careModule;
			if (this.careModule!=null) {				
				this.careModule.addChangelog(this);
			}
		}
		if (version!=null) {
			if (this.version!=version) {
				Preconditions.checkArgument(version.getCareModule()==this.careModule);
				this.version = version;
				this.version.addChangeLogFile(this);
			}
		}
	}

	public String getExtension() {
		return extension;
	}

	public String getLocation() {
		return resource.toFile().getAbsolutePath();
	}

	public void setParsedDatabaseChangeLog(DatabaseChangeLog databaseChangeLog) {
		this.databaseChangeLog = databaseChangeLog;
	}
	
}
