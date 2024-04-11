package com.bookbazaar.hub.ratingservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookbazaar.hub.ratingservice.entity.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {

	
}
