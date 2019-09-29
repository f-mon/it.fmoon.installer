package it.fmoon.installer.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import it.fmoon.installer.workspace.WorkspaceService;

@ShellComponent
public class StatusCommands {
    
	@Autowired
	private WorkspaceService workspaceService;
	
    @ShellMethod(
    	value = "Print the workspace status"
    	)
    public Object status() {
    	if (!workspaceService.isInitialized()) {
    		return "installer workspace not initialized in current dir ("+workspaceService.getCurrentDir()+")";
    	} else {
    		return workspaceService.serializedState();
    	}
    }
    
    @ShellMethod(
    	value = "Initialize a new installer workspace in current dir"
    	)
    public Object initialize() {
    	workspaceService.initializeWorkspace();
        return status();
    }
    
    @ShellMethod(
    	value = "Add a scan Location for changelogs"
    	)
    public Object addScanLocation(String newLocation) {
    	workspaceService.addScanLocation(newLocation);
        return status();
    }
    
    @ShellMethod(
    	value = "Reload workspace configuration"
    	)
    public Object reload() {
    	workspaceService.reload();
        return status();
    }
}