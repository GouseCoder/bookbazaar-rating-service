package com.bookbazaar.hub.ratingservice.dto;

public class ReviewResponseDto {
	
	private Long userId;
    private String firstName;
    private String lastName;
    private int rating;
    private String comments;
    
	public ReviewResponseDto(Long userId, String firstName, String lastname, int rating, String comments) {
		
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastname;
		this.rating = rating;
		this.comments = comments;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    
    


}
