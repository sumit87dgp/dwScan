package com.amd.dropwizardPOC.ldapService;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.amd.dropwizardPOC.api.User;

public class AuthServiceWithLDAP {

	private static final String USER_NAME_ATTR = "sAMAccountName";
	private static final String DISPLAY_NAME_ATTR = "displayName";
	private static final String USERID = "mailnickname";
	private static final String DISPLAY_FIRST_NAME = "givenname";
	private static final String DISPLAY_LAST_NAME = "sn";
	private static final String DISPLAY_EMAIL_ID = "mail";

	private static final String LDAP_IMPL = "com.sun.jndi.ldap.LdapCtxFactory";
	private static final String AUTHENTICATION_TYPE = "simple";
	private static final String AMD_DOMAIN = "amd\\";
	private static final String LldapAdServer = "ldap://atlldap.amd.com:389";

	public static User AuthenticateUser(String ldapUserName, String ldapPassword, String userToSearch) {
		// boolean isAuthenticated = false;
		// InetAddress.getCanonicalHostName();
		User objUser = null;
		if (ldapUserName.trim().isEmpty() || ldapPassword.trim().isEmpty()) {
			// return isAuthenticated;
			return objUser;
		}
		Hashtable<String, String> env = new Hashtable<String, String>();

		env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_IMPL);
		env.put(Context.PROVIDER_URL, LldapAdServer);

		env.put(Context.SECURITY_AUTHENTICATION, AUTHENTICATION_TYPE);
		// some hacking stuff
		String userNameWithDomain = ldapUserName;
		if (!userNameWithDomain.startsWith(AMD_DOMAIN)) {
			userNameWithDomain = AMD_DOMAIN + userNameWithDomain;
		}

		env.put(Context.SECURITY_PRINCIPAL, userNameWithDomain);
		env.put(Context.SECURITY_CREDENTIALS, ldapPassword);		
		DirContext ctx = null;
		try {
			ctx = new InitialDirContext(env);
			SearchControls sc = new SearchControls();
			String[] attributeFilter = new String[] { DISPLAY_NAME_ATTR, DISPLAY_FIRST_NAME, USERID, DISPLAY_LAST_NAME,
					DISPLAY_EMAIL_ID };
			sc.setReturningAttributes(attributeFilter);
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			NamingEnumeration<SearchResult> searchResults = ctx.search(
					
					"DC=amd,DC=com",
					
					"(" + USER_NAME_ATTR + "=" + userToSearch + ")", sc);
			if (searchResults.hasMore()) {
				SearchResult searchResult = searchResults.next();
//				
				objUser = new User(searchResult.getAttributes().get(USERID).get().toString(),
						searchResult.getAttributes().get(DISPLAY_FIRST_NAME).get().toString(),
						searchResult.getAttributes().get(DISPLAY_LAST_NAME).get().toString(),
						searchResult.getAttributes().get(DISPLAY_EMAIL_ID).get().toString());
			}
		} catch (NamingException e) {			
			System.out.println(e);
			
		} finally {
			if (ctx != null) {
				try {
					ctx.close();
				} catch (NamingException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return objUser;

	}

}
