package com.example.katzgrau.photosandroid57;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

//import javafx.scene.image.Image;


/**
 * @author Asad Dar
 * @author Tim Katzgrau
 * This class will represent an Album
 **/
public class Album implements Serializable {
	
	/**
	 * the name of the album
	 **/
	public String name;
	
	/**
	 * a list of photos that this album contains
	 **/
	private ArrayList<Photo> photos;
	
	/**
	 * @param name
	 * a name for the album
	 **/
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * @param photo
	 * the photo to be added to the album
	 **/
	public void addPhoto(Photo photo) {
		photos.add(photo);
	}
	
	/**
	 * @param name
	 * the name that the album will be renamed to
	 **/
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return a list of the photos in this album
	 **/
	public ArrayList<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * @return a range from oldest to newest date of photos
	 **/
	public String dateRange() {
		if (photos.size() == 0) {
			return "";
		}
		
		ArrayList<Photo> temp = new ArrayList<Photo>();
		for (int i = 0; i < photos.size(); i++) {
			temp.add(photos.get(i));
		}
		
		//temp.sort(Comparator.comparing(Photo::getDate));
		
		return temp.get(0).stringDate + " - " + temp.get(temp.size()-1).stringDate;
	}
	
	/**
	 * @return a string with the album's name
	 **/
	public String toString() {
		return name;
	}
	

}
