package cs1.softwareProject.explore;

// creating the user object
public class userObject {

	public String username;
	public int image;
	public int id;
	public String bio;
	public String interest;

	userObject(int id, String u, int img, String inter, String b) {
		this.image = img;
		this.id = id;
		this.username = u;
		this.interest = inter;
		this.bio = b;
	}

	public String getUsername() {
		return username;
	}

	public int getIDs() {
		return this.id;
	}

}
