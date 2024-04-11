package com.bookbazaar.hub.ratingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookbazaar.hub.ratingservice.dto.ReviewRequestDto;
import com.bookbazaar.hub.ratingservice.service.ReviewService;
import com.bookbazaar.hub.ratingservice.utils.ApiHttpResponse;
import com.bookbazaar.hub.ratingservice.utils.AppConstants;
import com.bookbazaar.hub.ratingservice.utils.CommonsUtils;
import com.bookbazaar.hub.ratingservice.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class ReviewController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@Autowired
    private ReviewService reviewService;
	
	@Autowired
	CommonsUtils commonsUtils;

    @PostMapping("/add")
    public ResponseEntity<ApiHttpResponse> addReview(@RequestBody ReviewRequestDto requestDto) {
    	
    	JsonNode resultNode = JacksonUtil.mapper.createArrayNode();
    	
    	try {
    		logger.info("Request from user for addReview : {}" , JacksonUtil.mapper.writeValueAsString(requestDto));
    		resultNode = reviewService.addReview(requestDto);
    		
    		return new ResponseEntity<>(new ApiHttpResponse(commonsUtils.getStatusCode(resultNode), 
    				resultNode.get(AppConstants.ERROR_OBJECT), 
					resultNode.get(AppConstants.DATA_OBJECT)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiHttpResponse(AppConstants.INTERNAL_SERVER_ERROR, 
					resultNode.get(AppConstants.ERROR_OBJECT)), HttpStatus.INTERNAL_SERVER_ERROR);
		}
        
    }

    @GetMapping("/show")
    public ResponseEntity<ApiHttpResponse> getReviewForBook(@RequestParam Long bookId) {
    	
    	JsonNode resultNode = JacksonUtil.mapper.createArrayNode();
    	
    	try {
    		
    		resultNode = reviewService.getReview(bookId);
    		
    		return new ResponseEntity<>(new ApiHttpResponse(commonsUtils.getStatusCode(resultNode),
    				resultNode.get(AppConstants.ERROR_OBJECT), 
					resultNode.get(AppConstants.DATA_OBJECT)), HttpStatus.OK);
    		
		} catch (Exception e) {
			return new ResponseEntity<>(new ApiHttpResponse(AppConstants.INTERNAL_SERVER_ERROR, 
					resultNode.get(AppConstants.ERROR_OBJECT)), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
