package cs1.softwareProject.explore;

// creating the user object
public class userObject {

	public String username;
	public String fname;
	public String lname;
	public String age;
	public String nationality;
	public String about;
	public String occupation;
	public String image;
	public int id;

	userObject(int id, String u, String fname, String lname, String img,
			String age, String nationality, String occu, String about) {
		this.image = img;
		this.about = about;
		this.id = id;
		this.username = u;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.nationality = nationality;
		this.occupation = occu;

	}

	public String getUsername() {
		return username;
	}

	public String getFullName() {
		return fname +" "+ lname;
	}

	public int getIDs() {
		return this.id;
	}

}
