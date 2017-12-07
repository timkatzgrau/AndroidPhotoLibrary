package com.example.katzgrau.photosandroid57;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Asad Dar
 * @author Tim Katzgrau
 * This class will represent a User
 **/
public class User implements Serializable {
	
	/**
	 * user's username
	 **/
	public String username;
	
	/**
	 * albums belonging to a user
	 **/
	private ArrayList<Album> albums;
	
	/**
	 * @param username
	 * username to identify user
	 **/
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	/**
	 * @return list of albums belonging to user
	 **/
	public ArrayList<Album> getAlbums() {
		return albums;
	}
	
	/**
	 * @return string representation of user
	 **/
	public String toString() {
		return username;
	}

}
