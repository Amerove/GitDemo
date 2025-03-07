package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification req;
	JsonPath js;
	
	public RequestSpecification requestSpecification() throws IOException {
		if(req==null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		req =new RequestSpecBuilder().setBaseUri(Utils.getProperties("baseURI"))
			.addQueryParam("key", "qaclick123")
			.addFilter(RequestLoggingFilter.logRequestTo(log))
			.addFilter(ResponseLoggingFilter.logResponseTo(log))
			.setContentType(ContentType.JSON).build();
		
		return req;
		}
		return req;
	}
	
	public static String getProperties(String key) throws IOException {
		
		Properties prop = new Properties();
		File propFile = new File(System.getProperty("user.dir") + "\\src\\test\\java\\resources\\Config.properties");
		FileInputStream fis = new FileInputStream(propFile);
		prop.load(fis);
		return prop.getProperty(key);
	}
	
	public String getJsonPath(Response response, String key) {
		
		 String resp = response.asString();
		 js = new JsonPath(resp);
		 
		 return js.get(key).toString();
	}
}
