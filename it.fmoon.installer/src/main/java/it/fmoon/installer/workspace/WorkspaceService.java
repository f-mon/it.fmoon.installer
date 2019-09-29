package it.fmoon.installer.workspace;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.fmoon.installer.service.events.ReloadedConfigurations;
import it.fmoon.installer.workspace.model.InstallerWorkspaceState;

@Component
public class WorkspaceService {

	private InstallerWorkspaceState state;
	
	private File currentDir;
	private File storeFile;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	@PostConstruct
	public void checkInitializeWorkspace() {
		this.currentDir = new File(System.getProperty("user.dir"));
		this.storeFile = new File(this.currentDir,"care-installer.yml");
		if (this.storeFile.exists()) {
			this.reload();
		}
	}
	


	public boolean isInitialized() {
		return state!=null;
	}
	
	public void initializeWorkspace() {
		this.state = new InstallerWorkspaceState();
		this.store();
	}
	
	

	private void store() {
		try {
			this.objectMapper.writerWithDefaultPrettyPrinter().writeValue(this.storeFile,this.state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private void retrieve() {
		try {
			this.state = this.objectMapper
				.readerFor(InstallerWorkspaceState.class)
				.readValue(this.storeFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String serializedState() {
		try {
			return this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.state);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addScanLocation(String newLocation) {
		this.state.getScanLocations().add(newLocation);
		store();
	}

	public File getCurrentDir() {
		return this.currentDir;
	}

	public void reload() {
		retrieve();
		this.applicationEventPublisher.publishEvent(new ReloadedConfigurations(this.state));
	}

	public List<String> getChangelogLocations() {
		return this.state.getScanLocations();
	}
	
}
