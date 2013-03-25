package linkedin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LinkedInOAuth {

	private static final String redirectURI = "http://localhost:8080/FriendFace/OAuthTest/query";
	private static final String client_id = "mrp8foh25d3v";
	private static final String client_secret = "K59FwKYpbAqzuess";
	
	public static String getOutput(String urlString, String accessToken){
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		try {
			 url = new URL(urlString);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("GET");
			 rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			 while ((line = rd.readLine()) != null) {
				result += line;
			 }
			 rd.close();
		} catch (Exception e) {
			 System.out.println("ERROR: " + e.toString());
		}
		
		return result;
	}

	public static String getAccessTokenJSON(String code){
		
		String urlString = "https://www.linkedin.com/uas/oauth2/accessToken?" +
					       "grant_type=authorization_code" +
					       "&code=" + code +
					       "&redirect_uri=" + redirectURI +
					       "&client_id=" + client_id +
					       "&client_secret=" + client_secret;
		
		
		URL url;
		HttpURLConnection conn;
		BufferedReader rd;
		String line;
		String result = "";
		  try {
			 url = new URL(urlString);
			 conn = (HttpURLConnection) url.openConnection();
			 conn.setRequestMethod("GET");
			 rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			 while ((line = rd.readLine()) != null) {
				result += line;
			 }
			 rd.close();
		  } catch (Exception e) {
		  	 System.out.println("ERROR: " + e.toString());
		  }
		  
		  return result;
	}

}
