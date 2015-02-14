package cs1.softwareProject.explorers;

import java.util.ArrayList;


public class userData {

	public static ArrayList<userObject> user_joined = new ArrayList<userObject>();

	public ArrayList<userObject> getUsers() {
		return user_joined;
	}

	public void addUsers(userObject user) {
		user_joined.add(user);
	}

}
