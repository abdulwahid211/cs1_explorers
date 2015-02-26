package cs1.softwareProject.explore;

public class Group {
	
	public int groupId;
	public int adminId;
	public String nameOfEvent;
	public int image;
	public String description;
	public String time;
	public String location;
	public String ageGroup;
	
	public Group(int gid, int aid, String groupName,String in ,String t,String des ,String age,int im ){
		this.groupId =gid;
		this.adminId =aid;
		this.time =t;
		this.ageGroup = age;
		this.description = des;
		this.nameOfEvent = groupName;
		this.image = im;
		this.location = in;
	}
	
	public int getIDs(){
		return this.groupId;
	}
	
	

}
