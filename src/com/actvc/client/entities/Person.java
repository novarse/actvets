package com.actvc.client.entities;

import com.actvc.client.Gender;

public interface Person {

	boolean isActive();

	String getFirstName();

	String getLastName();

	Long getId();

	Gender getGender();

	String getGrade();

	int getSubGrade();

	String getCriteriumGrade();

	int getNumber();

	String getAVCCNumber();

	String getEmail();

	String getPhoneHome();

	String getStreet();

	String getSuburb();

	String getState();

	String getPostcode();

	String getPhoneWorkOrMobile();

	Object getDob();

	boolean isFirstAid();

	String getEmergencyContact();

	String getPhoneEmergencyContact();

	String getPhoneEmergencyContact2();

	boolean isDirector();

	// setters
	public void setFirstName(String firstName);

	public void setLastName(String lastName);

	public void setGrade(String grade);

	public void setNumber(int raceNumber);

	public void setAVCCNumber(String aVCCNumber);

	public void setEmail(String email);

	public void setPhoneHome(String phoneHome);

	public void setStreet(String street);

	public void setSuburb(String suburb);

	public void setState(String state);

	public void setPostcode(String postcode);

	public void setPhoneWorkOrMobile(String phoneWorkOrMobile);

	public void setDob(Object dob);

	public void setFirstAid(boolean firstAid);

	public void setEmergencyContact(String emergencyContact);

	public void setPhoneEmergencyContact(String phoneEmergencyContact);

	public void setGender(Gender gender);

	public void setPhoneEmergencyContact2(String phoneEmergencyContact2);

	public void setActive(boolean isActive);

	public void setSubGrade(int subGrade);

	public void setCriteriumGrade(String criteriumGrade);

}