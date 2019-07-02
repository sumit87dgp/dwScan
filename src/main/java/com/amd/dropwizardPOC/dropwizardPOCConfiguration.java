package com.amd.dropwizardPOC;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import jersey.repackaged.com.google.common.cache.CacheBuilderSpec;

import com.fasterxml.jackson.annotation.JsonProperty;




import javax.validation.Valid;
import javax.validation.constraints.*;

public class dropwizardPOCConfiguration extends Configuration {
    private static final String DATABASE = "database";
    //private static final String AUTHCACHEPOLICY ="authenticationCachePolicy";
    
    
    private CacheBuilderSpec cacheui;
	
    @Valid
    @NotNull
    private DataSourceFactory _dataSourceFactory= new DataSourceFactory();
    
    @JsonProperty(DATABASE)
    public DataSourceFactory getDataSourceFactory() {
    	return _dataSourceFactory;
    }
    
    @JsonProperty(DATABASE)
    public void setDataSourceFactory(final DataSourceFactory dataSourceFactory) {
    	this._dataSourceFactory= dataSourceFactory;
    }
    
//    @JsonProperty(AUTHCACHEPOLICY)
//    public void setCachebuilderSpec() {
//    	this.cacheui = CacheBuilderSpec.parse(AUTHCACHEPOLICY);
//    }
//    @JsonProperty(AUTHCACHEPOLICY)
//    public CacheBuilderSpec getCacheBuilderSpec() {
//    	return cacheui;
//    }
	
    
    
    
}
