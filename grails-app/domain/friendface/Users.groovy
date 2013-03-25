package friendface

import grails.converters.JSON

import org.apache.log4j.Logger

class Users {
	
	String name;
	String username;
	double lat;
	double lon;
	String profile_img_url;
	
	double distanceAway;
	double antipodalPoint;
	
	static transients = ['distanceAway', 'antipodalPoint']

    public List<Users> getClose(){
		println "Triggered getCloseMethod"
		return getCloseUsers(lat, lon);
	}
	
	public List<Users> getNearAntipodal(){
		println "Triggered getNearAntipodal"
		
		def antiLat = -lat;
		def antiLon = 0;
		if(lon + 180 > 180){
			antiLon = lon-180;
		}
		else{
			antiLon = lon+180;
		}
		
		return getCloseUsers(antiLat, antiLon)
	}
	
	public List<Users> getCloseUsers(thisLat, thisLon){
		def returnList = new ArrayList<Users>();
		
		def users = Users.getAll()
		users.each { user ->
			def a = 69.16 * (user.lat - thisLat)
			def b = 48.91 * (user.lon - thisLon)
			def c = Math.sqrt((a*a) + (b*b))
			if(c <= 1 && !user.username.equals(username)){
				println "Adding user: ${user.name} with a C value of ${c}"
				user.setDistanceAway(c);
				returnList.add(user);
			}
		}
		
		return returnList;
	}
}
