package com.jwtAuthentication.jwtAuthentication.routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class TenantAwareRoutingSource extends AbstractRoutingDataSource {
	
	@Override
    protected Object determineCurrentLookupKey() {
        String client_id = ThreadLocalStorage.getTenantName();
        if(client_id == null) {
        	return "marvel";
        }
        System.out.println("Client from headers: " + client_id);
		return client_id;
    }
	
}
