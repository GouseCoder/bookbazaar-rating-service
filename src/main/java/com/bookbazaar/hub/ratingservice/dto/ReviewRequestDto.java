package com.bookbazaar.hub.ratingservice.dto;

public class ReviewRequestDto {
	
	private Long userId;
	private int rating;
	private Long bookId;
	private String comments;
	
	
	public Long getUserId() {
		return userId;
	}
	public Long getBookId() {
		return bookId;
	}
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
