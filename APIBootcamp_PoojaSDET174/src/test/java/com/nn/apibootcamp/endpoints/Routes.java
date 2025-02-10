package com.nn.apibootcamp.endpoints;

import com.nn.apibootcamp.constants.EnvironmentConfiguration;

public class Routes {

	public static String GET_ALL_USERS_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.GET_USERS;
	public static String GET_USERID_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.GET_BY_USERID
			+ "{userId}";
	public static String GET_FIRSTNAME_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.GET_BY_FIRSTNAME
			+ "{username}";
	public static String PUT_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.PUT + "{userId}";
	public static String POST_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.POST;
	public static String DELETE_USERID_URL = EnvironmentConfiguration.BASE_URL + EnvironmentConfiguration.DELETE_BY_USERID
			+ "{userId}";
	public static String DELETE_FIRSTNAME_URL = EnvironmentConfiguration.BASE_URL
			+ EnvironmentConfiguration.DELETE_BY_FIRSTNAME + "{username}";
}
