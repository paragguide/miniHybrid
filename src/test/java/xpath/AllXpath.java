package xpath;

public class AllXpath 
{
	// facebook
	
	public static final String uid = "//*[@id=\"email\"]";
	public static final String pd = "//*[@id=\"pass\"]";
	public static final String btn = "//*[@name = 'login']";
	
	// amazon login xpath
	
		public static final String signin = "//*[text() = 'Hello, sign in']"; // constant
		public static final String auid = "//*[@id=\"ap_email\"]";
		public static final String btn1 = "//*[@id=\"continue\"]";
		public static final String err1 = "//*[@id=\"auth-error-message-box\"]/div/div/ul/li/span";
	    public static final String pwd = "//*[@id=\"ap_password\"]";
	    public static final String btn2 = "//*[@id=\"signInSubmit\"]";
	    public static final String err2 = "//*[@id=\"auth-error-message-box\"]/div/div/ul/li/span";

	    // amazon search
	    public static final String srchbox = "//*[@id=\"twotabsearchtextbox\"]";

	

}
