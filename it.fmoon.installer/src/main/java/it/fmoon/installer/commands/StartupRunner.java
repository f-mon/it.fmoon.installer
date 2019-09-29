package it.fmoon.installer.commands;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Shell;
import org.springframework.stereotype.Component;

import it.fmoon.installer.utils.StringInputProvider;

@Component
public class StartupRunner {

	@Autowired
	private Shell shell;

	@PostConstruct
	public void run() throws Exception {
		shell.run(new StringInputProvider("status"));
	}
}