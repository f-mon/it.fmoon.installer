package it.fmoon.installer.model;

public interface CareModuleRepository {

	CareModule getOrCreateModule(String moduleName);

}
