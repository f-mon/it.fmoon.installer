package it.fmoon.installer.service.events;

import it.fmoon.installer.workspace.model.InstallerWorkspaceState;

public class ReloadedConfigurations {
	
	private final InstallerWorkspaceState installerWorkspaceState;

	public ReloadedConfigurations(InstallerWorkspaceState installerWorkspaceState) {
		super();
		this.installerWorkspaceState = installerWorkspaceState;
	}

	public InstallerWorkspaceState getInstallerWorkspaceState() {
		return installerWorkspaceState;
	}
	
}
