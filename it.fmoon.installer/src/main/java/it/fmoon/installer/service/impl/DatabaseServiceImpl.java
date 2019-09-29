package it.fmoon.installer.service.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import it.fmoon.installer.service.DatabaseService;
import it.fmoon.installer.service.events.ReloadedConfigurations;
import it.fmoon.installer.service.events.ReloadedDatabase;
import it.fmoon.installer.workspace.model.InstallerWorkspaceState.DatasourceConfig;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;

@Component
public class DatabaseServiceImpl implements DatabaseService {
	
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	private Database database;
	
	public DatabaseServiceImpl() {
		super();
	}

	@EventListener
	public void onConfigLoaded(ReloadedConfigurations event) {
		try {
			DataSource dataSource = initDataSource(event.getInstallerWorkspaceState().getDatasource());
			DatabaseConnection conn = new JdbcConnection(dataSource.getConnection());
			database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(conn);		
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println("Database created");
		applicationEventPublisher.publishEvent(new ReloadedDatabase());
	}
	
	
	@Override
	public Database getDatabase() {
		return database;
	}
	
	public DataSource initDataSource(DatasourceConfig datasourceConfig) {
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(datasourceConfig.getDriverClassName());
        dataSourceBuilder.url(datasourceConfig.getUrl());
        dataSourceBuilder.username(datasourceConfig.getUsername());
        dataSourceBuilder.password(datasourceConfig.getPassword());
        return dataSourceBuilder.build();
	}

}
