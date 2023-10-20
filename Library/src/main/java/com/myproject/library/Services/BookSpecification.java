package com.myproject.library.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.myproject.library.Models.Book;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class BookSpecification {
    public static Specification<Book> searchBooks(String title, String isbn, String publisher, Date dateAdded) {
        return (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
            }

            if (isbn != null && !isbn.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("isbn"), isbn));
            }

            if (publisher != null && !publisher.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("publisher")), "%" + publisher.toLowerCase() + "%"));
            }

            if (dateAdded != null) {
                predicates.add(criteriaBuilder.equal(root.get("dateAdded"), dateAdded));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
