package cs1.softwareProject.explorers;

public class Group {
	
	public String nameOfEvent;
	public int image;
	public String description;
	public String time;
	public String location;
	public String ageGroup;
	
	public Group(String groupName,String in ,String t,String des ,String age,int im ){
		this.time =t;
		this.ageGroup = age;
		this.description = des;
		this.nameOfEvent = groupName;
		this.image = im;
		this.location = in;
	}
	
	

}
