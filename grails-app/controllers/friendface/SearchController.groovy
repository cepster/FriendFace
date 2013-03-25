package friendface

import grails.converters.JSON
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil


class SearchController {
	
    def index() { 
		log.debug "Controller: Search, Action: Index"
	}
	
	def getNearbyPeople(){
		log.debug "Search/getNearbyPeople"
		def searchName = params.get("username")
		def format = params.get("format")
		log.debug "Format: ${format}"
		def user = Users.findByUsername(searchName)
		if(user == null){
			render "ERROR: No user by that username"
			return;
		}
		
		if(format == null || format == "json" || format == ""){
			render serializeUserListJson(user, user.getClose())
		}
		else{
			render(text: serializeUserListXml(user, user.getClose()), contentType: "text/xml", encoding: "UTF-8")
		}
	}
	
	def getSandwichCandidates(){
		log.debug "Search/getSandwichCandidates"
		def searchName = params.get("username")
		def format = params.get("format")
		
		def user = Users.findByUsername(searchName)
		log.debug "Found user: ${user?.name}, userID: ${user?.id}"
		if(user == null){
			render "ERROR: No user by that username"
			return;
		}
		
		if(format == null || format == "json" || format == ""){
			render serializeUserListJson(user, user.getNearAntipodal())
		}
		else{
			render(text: serializeUserListXml(user, user.getNearAntipodal()), contentType: "text/xml", encoding: "UTF-8")
		}
	}
		
	def JSON serializeUserListJson(searchUser, userList){
		HashMap jsonMap = new HashMap()
		jsonMap.center = [lat: searchUser.lat, lon: searchUser.lon]
		jsonMap.users = userList.collect {user ->
			return [name: user.name, 
				    distanceAway: (double)Math.round(user.distanceAway * 1000) / 1000, 
					profile_img_url: user.profile_img_url]
		}
		 
		JSON returnJSON = jsonMap as JSON
		println "Serialization: " + returnJSON.toString()
		return returnJSON
	}
	
	def serializeUserListXml(searchUser, userList){
		
		def results = {
			mkp.xmlDeclaration()
			results(radius:1){
				center{
					lat searchUser.lat
					lon searchUser.lon
					}
				userList.each {thisUser-> user(id:thisUser.id,
											   name:thisUser.name,
											   username:thisUser.username,
											   lat:thisUser.lat,
											   lon:thisUser.lon,
											   distance:thisUser.distanceAway){
								}
					}
				}
		}
		
		def xml = new StreamingMarkupBuilder().bind(results)
		return xml
	}
}
