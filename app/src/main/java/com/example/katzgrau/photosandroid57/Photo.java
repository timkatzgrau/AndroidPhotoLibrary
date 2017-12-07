package com.example.katzgrau.photosandroid57;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//import javafx.scene.image.Image;

/**
 * @author Asad Dar
 * @author Tim Katzgrau
 * This class will represent a Photo
 **/
public class Photo implements Serializable {
	
	/**
	 * file path for photo
	 **/
	private String photoFileURI;
	
	/**
	 * user photo is created by
	 **/
	private User author;
	
	/**
	 * list of tags associated with photo
	 **/
	private ArrayList<Tag> tags;
	
	/**
	 * string representation of date
	 **/
	public String stringDate;
	
	/**
	 * millisecond version of date
	 **/
	public long date;
	
	/**
	 * caption belonging to photo
	 **/
	private String caption;
	
	/**
	 * @param file
	 * file to be made into photo
	 **/
	public Photo(File file) {
		photoFileURI = file.toURI().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		date = file.lastModified();
		stringDate = sdf.format(date);
		tags = new ArrayList<Tag>();
	}
	
	/**
	 * @return get date of photo
	 **/
	public long getDate() {
		return date;
	}
	
	/**
	 * @return get file path of photo
	 **/
	public String getPhotoFileURI() {
		return photoFileURI;
	}
	
	/**
	 * @param caption
	 * caption to be used for photo
	 **/
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * @return get caption of photo
	 **/
	public String getCaption() {
		return caption;
	}
	
	/**
	 * @param tag
	 * tag to be added to photo
	 **/
	public void addTag(Tag tag) {
		tags.add(tag);
	}
	
	/**
	 * @param tag
	 * tag to be removed from photo
	 **/
	public void removeTag(Tag tag) {
		tags.remove(tag);
	}
	
	/**
	 * @return get list of tags associated with photo
	 **/
	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	/**
	 * @return get photo as Image object
	 **/
	/*public Image getImage() {
		Image image = new Image(photoFileURI);
		return image;
	}*/

}
