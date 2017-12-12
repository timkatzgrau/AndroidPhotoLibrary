package com.example.katzgrau.photosandroid57;

import java.io.Serializable;

/**
 * @author Asad Dar
 * @author Tim Katzgrau
 * This class will represent a Tag
 **/
public class Tag implements Serializable {
	/**
	 * key of tag
	 **/
	public String key;
	
	/**
	 * value of tag
	 **/
	public String value;
	
	/**
	 * @param key
	 * key for tag
	 * @param value
	 * value for tag
	 **/
	public Tag (String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * @return string representation of tag object
	 **/
	public String toString() {
		return "(" + key + ", " + value + ")";
	}
	
	/**
	 * @param o
	 * tag to be compared to
	 * @return if tags are equal
	 **/
	public boolean equals(Object o) {
		Tag tag = (Tag)o;
		
		if (key == tag.key && value == tag.value) {
			return true;
		} else {
			return false;
		}
	}



}
