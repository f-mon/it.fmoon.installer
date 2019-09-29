package it.fmoon.installer.commands;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import it.fmoon.installer.model.CareModule;
import it.fmoon.installer.service.ChangeLogsService;
import it.fmoon.installer.utils.OutputBuilder;
import it.fmoon.installer.utils.OutputPrinter;

@ShellComponent
public class ChangelogsCommands {
	
	@Autowired
	private ChangeLogsService changeLogsService;
	
    @ShellMethod(
    	value = "Scan and Lists all the modules/versions/changelogs"
    	)
    public Object scan() {
    	Collection<CareModule> modules = changeLogsService.scanAndGetModules();
    	return list();
    }
	
    @ShellMethod(
    	value = "Lists all the modules/versions/changelogs"
    	)
    public Object list() {
    	Collection<CareModule> modules = changeLogsService.getModules();
    	
    	OutputBuilder sb = new OutputBuilder();
    	sb.bigLineSeparator();
    	
    	modules.forEach(mod->{
    		sb.appendWithFixWidth(mod.getModuleName(),40).space()
    			.append("(").appendWithFixWidth(mod.getChangeLogFiles().size(),3).append(" changelogs )").space()
    			.append("(").appendWithFixWidth(mod.getVersions().size(),3).append(" versions )").newLine();
    		sb.lineSeparator();
    	});
    	return sb.build();
    }
    
}
