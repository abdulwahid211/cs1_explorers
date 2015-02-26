package cs1.softwareProject.explore;

import java.util.ArrayList;
import java.util.HashSet;


public class groupData {

	public static ArrayList<Group> user_groups = new ArrayList<Group>();

	public ArrayList<Group> getGroup() {
		
		return user_groups;
	}

	public void addCars(Group user_group) {
		user_groups.add(user_group);
	}

}
