package friendface

import grails.converters.JSON
import groovy.json.JsonSlurper
import linkedin.LinkedInOAuth

class OAuthTestController {
	
	private static final redirectURI = "http://localhost:8080/FriendFace/OAuthTest/query"
	private static final client_id = "mrp8foh25d3v";
	private static final client_secret = "K59FwKYpbAqzuess"; 
	private static accessToken = "";

    def index() { 
		log.debug "OAuthTest/index"
		
		//Redirect to LinkedIn login page
		String url = "https://www.linkedin.com/uas/oauth2/authorization?" +
				     "response_type=code" +
					 "&client_id=${client_id}" + 
					 "&state=DCEEFWF45453sdffef424" +
					 "&redirect_uri=${redirectURI}";
		
		redirect(url: url);
	
	}
	
	def query(){
		log.debug "OAuthTest/query"
		
		def code = params.get("code");
		println "CODE: ${code}"
		def accessTokenJson = new JsonSlurper().parseText( LinkedInOAuth.getAccessTokenJSON(code) )
		accessToken = accessTokenJson.access_token;
		redirect(action:"search")
	}
	
	def search(){
		log.debug "OAuthTest/search"
		
		if(accessToken.equals("")){
			redirect(action:"index")
		}
	}
	
	def queryData(){
		log.debug "OAuthTest/queryData"
		
		def companySearch = params.get("searchString")
		
		String url = "";
		if(companySearch != null){
			println "Querying for companies"
			url = "https://api.linkedin.com/v1/companies?email-domain=${companySearch}&" +
				  "oauth2_access_token=${accessToken}&format=json"
		}
		else{
			url = "https://api.linkedin.com/v1/people/~?" +
				"oauth2_access_token=${accessToken}&format=json"
		}
		
					 
		def output = LinkedInOAuth.getOutput(url, accessToken);
		println output
	    def profileInfo = new JsonSlurper().parseText(output)
	
		render(profileInfo as JSON)
	}
	
	
}

