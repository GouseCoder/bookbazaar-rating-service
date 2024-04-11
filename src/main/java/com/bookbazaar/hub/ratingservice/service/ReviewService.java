package com.bookbazaar.hub.ratingservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookbazaar.hub.ratingservice.dto.ReviewRequestDto;
import com.bookbazaar.hub.ratingservice.entity.Book;
import com.bookbazaar.hub.ratingservice.entity.Review;
import com.bookbazaar.hub.ratingservice.entity.UserInfo;
import com.bookbazaar.hub.ratingservice.repo.BooksRepository;
import com.bookbazaar.hub.ratingservice.repo.ReviewRepository;
import com.bookbazaar.hub.ratingservice.repo.UserRepository;
import com.bookbazaar.hub.ratingservice.utils.AppConstants;
import com.bookbazaar.hub.ratingservice.utils.CommonsUtils;
import com.bookbazaar.hub.ratingservice.utils.JacksonUtil;
import com.bookbazaar.hub.ratingservice.utils.ResponseConstants;
import com.bookbazaar.hub.ratingservice.utils.ResponseKeyConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ReviewService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);
	
	@Autowired
    private ReviewRepository reviewRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BooksRepository bookRepository;
	
	@Autowired
	CommonsUtils commonsUtils;

    public JsonNode addReview(ReviewRequestDto reviewRequestDto) {
    	
    	ObjectNode resultNode = commonsUtils.createResultNode();
    	ObjectNode dataObject = (ObjectNode) resultNode.get(AppConstants.DATA_OBJECT);
    	
    	try {
			
    		UserInfo user = userRepository.findById(reviewRequestDto.getUserId())
    				.orElseThrow(() -> new IllegalArgumentException("User not found with id: " + reviewRequestDto.getUserId()));
    		
    		Book book = bookRepository.findById(reviewRequestDto.getBookId())
    				.orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + reviewRequestDto.getBookId()));
    		
    		// Create and save the review entity
    		Review review = new Review();
    		review.setUser(user);
    		review.setBook(book);
    		review.setRating(reviewRequestDto.getRating());
    		review.setComments(reviewRequestDto.getComments());
    		
    		saveAvgRating(reviewRequestDto.getBookId(), reviewRequestDto.getRating());
    		
    		reviewRepository.save(review);
    		
    		dataObject.put(AppConstants.ERROR_CODE, ResponseConstants.REVIEW_ADDED);
    		dataObject.put(AppConstants.ERROR_REASON, "Thank you for your feedback");
    		
		} catch (Exception e) {
			logger.error("Exception in addReview " + e);
		}
    	
    	return resultNode;
    	
    }

    public JsonNode getReview(Long bookId) {
    	
    	ObjectNode resultNode = commonsUtils.createResultNode();
    	ObjectNode dataObject = (ObjectNode) resultNode.get(AppConstants.DATA_OBJECT);
    	ArrayNode reviewsArray = JacksonUtil.mapper.createArrayNode();
    	
    	try {
    		
    		List<Review> reviews = reviewRepository.findByBookId(bookId);
            
    		for(Review review : reviews) {
    			ObjectNode reviewObject = JacksonUtil.mapper.createObjectNode();
    			reviewObject.put(ResponseKeyConstants.USER_ID, review.getUser().getUserId());
    			reviewObject.put(ResponseKeyConstants.FIRST_NAME, review.getUser().getFirstName());
    			reviewObject.put(ResponseKeyConstants.LAST_NAME, review.getUser().getLastname());
    			reviewObject.put(ResponseKeyConstants.RATING, review.getRating());
    			reviewObject.put(ResponseKeyConstants.COMMENT, review.getComments());
    			reviewsArray.add(reviewObject);
    		}
    		
    		dataObject.set("Reviews", reviewsArray);
			
		} catch (Exception e) {
			logger.error("Exception in getReview " + e);
		}
        
        return resultNode; 
    }
    
    public void saveAvgRating(Long bookId, int newRating) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + bookId));

        // Get the total number of reviews
        int totalReviews = book.getReviews().size();

        // Calculate the sum of all ratings
        int sumOfRatings = book.getReviews().stream().mapToInt(Review::getRating).sum();

        // Calculate the new average rating
        int newAvgRating = (sumOfRatings + newRating) / (totalReviews + 1);

        // Update the book's average rating
        book.setRatinginStar(newAvgRating);

        // Save the updated book entity
        bookRepository.save(book);
    }


}
