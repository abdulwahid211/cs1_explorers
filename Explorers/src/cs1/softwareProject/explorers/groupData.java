package cs1.softwareProject.explorers;

import java.util.ArrayList;


public class groupData {

	public static ArrayList<Group> user_groups = new ArrayList<Group>();

	public ArrayList<Group> getCars() {
		return user_groups;
	}

	public void addCars(Group user_group) {
		user_groups.add(user_group);
	}

}
