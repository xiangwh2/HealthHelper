package com.example.healthhelper;

import java.util.ArrayList;
import java.util.List;

public class FriendManager {
	private List<Friend> friends;
	
	public FriendManager() {
		friends = new ArrayList<Friend>();
	}
	
	public List<Friend> getFriendList() {
		return friends;
	}
}
