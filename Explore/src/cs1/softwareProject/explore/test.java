package cs1.softwareProject.explore;

public class test {

	public String name;
	public String post;
	public String des;
	test(String name, String postCode, String Description){
		this.name = name;
		this.post = postCode;
		this.des = Description;
	}
	public void setPos(String a){
		this.post =a;
	}
	
	public void setName(String a){
		this.name =a;
	}
	
	public void setDes(String a){
		this.des =a;
	}
	
	
	
}
