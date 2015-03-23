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
	public String postCode;
	public String language;
	
	public Group(int gid, int aid, String groupName,String in ,String pos,String t,String des ,String age,int im, String lan ){
		this.groupId =gid;
		this.language = lan;
		this.postCode = pos;
		this.adminId =aid;
		this.time =t;
		this.ageGroup = age;
		this.description = des;
		this.nameOfEvent = groupName;
		this.image = im;
		this.location = in;
	}
	
	public int getIDs(){
		return this.adminId;
	}
	
	public String getName(){
		return this.nameOfEvent;
	}
	
	public int getgId(){
		return this.groupId;
	}
	public String getLan(){
		return this.language;
	}

	

}