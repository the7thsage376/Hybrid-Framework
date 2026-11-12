package api.base;

import utils.ConfigLoader;

public class BaseUri{
    public static String baseURL ="https://www.ndosiautomation.co.za";

    public static final String BASE_URL = ConfigLoader.getProperty("base.url");
    public static final String USER_EMAIL = ConfigLoader.getProperty("user.email");
    public static final String USER_PASSWORD = ConfigLoader.getProperty("user.password");

}