package com.projectmilestonetool.exceptions;

// as we want the projectIdIdentifier attribute to be validated we create a object of this and add our own message to this object attribute. 
public class ProjectIdExceptionResponse {
	
	private String projectIdentifier;

	public ProjectIdExceptionResponse(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
		System.out.println("********************ProjectIdresponse Object create***************");
	}

	public String getProjectIdentifier() {
		return projectIdentifier;
	}

	public void setProjectIdentifier(String projectIdentifier) {
		this.projectIdentifier = projectIdentifier;
	}
	
	

}
