package it.fmoon.installer.workspace.model;

import java.util.ArrayList;
import java.util.List;

public class InstallerWorkspaceState {

	private List<String> scanLocations = new ArrayList<String>();
	private DatasourceConfig datasource = new DatasourceConfig(); 

	public List<String> getScanLocations() {
		return scanLocations;
	}
	public void setScanLocations(List<String> scanLocations) {
		this.scanLocations = scanLocations;
	}
	
	public DatasourceConfig getDatasource() {
		return datasource;
	}
	public void setDatasource(DatasourceConfig datasource) {
		this.datasource = datasource;
	}
	
	public class DatasourceConfig {
		private String driverClassName;
		private String url;
		private String username;
		private String password;
		
		public String getDriverClassName() {
			return driverClassName;
		}
		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
	}
}
