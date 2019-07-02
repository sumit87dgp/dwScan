package com.amd.dropwizardPOC;



import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import com.amd.dropwizardPOC.core.AuthTokens;
import com.amd.dropwizardPOC.core.ScanMaps;
import com.amd.dropwizardPOC.core.User;
import com.amd.dropwizardPOC.db.AuthDao;
import com.amd.dropwizardPOC.db.ScanDao;
import com.amd.dropwizardPOC.db.UserDao;
import com.amd.dropwizardPOC.resources.AuthResource;
import com.amd.dropwizardPOC.resources.ScanResource;
import com.amd.dropwizardPOC.resources.UserResource;
import com.amd.dropwizardPOC.security.AppBasicAutheticator;
import com.amd.dropwizardPOC.security.AuthenticateFilter;
import com.amd.dropwizardPOC.security.CustomAuthFilter;
import com.amd.dropwizardPOC.security.CustomAuthenticator;
import com.amd.dropwizardPOC.security.TokenServices;
import com.google.common.cache.CacheBuilderSpec;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.CachingAuthenticator;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javassist.expr.NewArray;



public class dropwizardPOCApplication extends Application<dropwizardPOCConfiguration> {

    public static void main(final String[] args) throws Exception {
        new dropwizardPOCApplication().run(args);
    }
    
    private final HibernateBundle<dropwizardPOCConfiguration> hibernateBundle=
    		new HibernateBundle<dropwizardPOCConfiguration>(User.class,ScanMaps.class,AuthTokens.class) {
    			public DataSourceFactory getDataSourceFactory(dropwizardPOCConfiguration configuration) {
    				return configuration.getDataSourceFactory();
    			}
			};
			
//			private final HibernateBundle<dropwizardPOCConfiguration> hibernateBundle1=
//		    		new HibernateBundle<dropwizardPOCConfiguration>(ScanMaps.class) {
//		    			public DataSourceFactory getDataSourceFactory(dropwizardPOCConfiguration configuration) {
//		    				return configuration.getDataSourceFactory();
//		    			}
//					};

    @Override
    public String getName() {
        return "dropwizardPOC";
    }

    @Override
    public void initialize(final Bootstrap<dropwizardPOCConfiguration> bootstrap) {
        // TODO: application initialization
    	bootstrap.addBundle(hibernateBundle);
    	//bootstrap.addBundle(hibernateBundle1);
    }

    @Override
    public void run(final dropwizardPOCConfiguration configuration,
                    final Environment environment) {
    	
    	
    	
    	// Custom A
    	// Instantiate DAO classes with session factory
    	final UserDao dao = new UserDao(hibernateBundle.getSessionFactory());
    	environment.jersey().register(new UserResource(dao));
    	
    	final ScanDao scanDao = new ScanDao(hibernateBundle.getSessionFactory());
    	environment.jersey().register(new ScanResource(scanDao));
    	
    	AuthDao authDao = new AuthDao(hibernateBundle.getSessionFactory());
    	
    	
    	environment.jersey().register(new AuthResource(new TokenServices(authDao)));
    	TokenServices tokenservices = new TokenServices(authDao);
//    	CustomAuthenticator authenticator = new CustomAuthenticator(services);
//    	CustomAuthFilter filter= new CustomAuthFilter(authenticator);
//    	environment.jersey().register(new AuthDynamicFeature(filter));
    	
    	SetUpCORS(environment);
    	//setUpBasicAuth(environment);
        setupCustomAuth(environment, tokenservices);
        
    	
    	
    }
    
    private void SetUpCORS(Environment environment) {
    	final FilterRegistration.Dynamic cors =
    	        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

    	    // Configure CORS parameters
    	    cors.setInitParameter("allowedOrigins", "*");
    	    cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
    	    cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    	    
    	 // Add URL mapping
            cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");  
    	
    }
    
    private void setUpBasicAuth(Environment environment) {
    	// *--- Basic Authentication-----------------------------------------------------******
    	// Setting up dropwizard Auth Caching
    	CachingAuthenticator<BasicCredentials, com.amd.dropwizardPOC.security.User> cachingAuthenticator = new CachingAuthenticator<>(
    		    environment.metrics(), new AppBasicAutheticator(),CacheBuilderSpec.parse("maximumSize=5,expireAfterAccess=3s"));
   	
    	// DropWizard security - custom classes
    	
    	
    	environment.jersey().register(new AuthDynamicFeature(new 
    			BasicCredentialAuthFilter.Builder<com.amd.dropwizardPOC.security.User>()
    			//.setAuthenticator(cachingAuthenticator)
    			.setAuthenticator(new AppBasicAutheticator())
    			.setRealm("BASIC-AUTH-REALM")
    			.buildAuthFilter()));
    	
    	//If you want to use @Auth to inject a custom Principal type into your resource
        //environment.jersey().register(new AuthValueFactoryProvider.Binder<>(com.amd.dropwizardPOC.security.User.class));
    	// *--------------------------------------------------------------------------------------*****
    }
    
 // * -- CUSTOM AUTHENTICATION -------------------------------------------------------************
    
    private void setupCustomAuth(Environment environment,TokenServices tokenServices) {

    	//CustomAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle).create(clazz)
//    	TokenServices services = new UnitOfWorkAwareProxyFactory(hibernateBundle)
//    			.create(TokenServices.class,new Class<?>[] {AuthDao.class},new Object[] {authDao});
    	CustomAuthenticator authenticator = new UnitOfWorkAwareProxyFactory(hibernateBundle)
    		      .create(CustomAuthenticator.class, new Class<?>[]{TokenServices.class}, new Object[]{tokenServices});
    	
    	//CustomAuthenticator authenticator = new CustomAuthenticator(services);
    	CustomAuthFilter filter= new CustomAuthFilter(authenticator);
    	environment.jersey().register(new AuthDynamicFeature(filter));
//    	environment.jersey().register(new AuthDynamicFeature(new 
//    			CustomAuthFilter(authenticator)));
    }

}
