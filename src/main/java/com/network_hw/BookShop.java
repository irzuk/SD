package com.network_hw;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class BookShop {

    private HashMap<Integer, Book> storage = new HashMap<>();

    {  // for tests
        Review first = new Review(1, 1, Review.Status.NEW, "New review", "You can see this review only if you're a moderator.");
        Review second = new Review(2, 2, Review.Status.BY_MODERATOR, "By moderator", "You can't see this description in short version.");
        Review third = new Review(3, 1, Review.Status.APPROVED, "Approved", "You can't see this description in short version.");

        storage.put(1, new Book(1,
                "Book1",
                "This is really long description should be cropped a some point, that you won;t see this part in search.",
                "Author1",
                Arrays.asList(first, second, third),
                5));
        storage.put(2, new Book(2,
                "Book3",
                "This is really long description should be cropped a some point, that you won;t see this part in search.",
                "Author2",
                Arrays.asList(first, second, third),
                10));
        storage.put(3, new Book(3,
                "Book3",
                "This is really long description should be cropped a some point, that you won;t see this part in search.",
                "Author3",
                Arrays.asList(first, second, third),
                15));

    }

    @GetMapping("/booksearch/")
    public ResponseEntity<List<BookSearchView>> getSearchResult(@RequestParam(required = false) String name, @RequestParam(required = false) String author, @RequestParam(defaultValue = "10") Integer limit, @RequestParam(defaultValue = "0") Integer offset) {
        List<BookSearchView> books = storage.values().stream().filter(book -> name == null || book.getName().contains(name)).filter(book -> author == null || book.getName().contains(author)).map(Book::getSearchView).skip(offset).limit(limit).toList();

        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/booksearch/{id}")
    public ResponseEntity<BookFullView> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(storage.get(id).getFullView());
    }

    @GetMapping("/booksearch/{id}/{review_id}")
    public ResponseEntity<ReviewFullView> getBookWithExtendedReview(@PathVariable Integer id, @PathVariable Integer review_id) {
        return ResponseEntity.ok().body(storage.get(id).getReviews().get(review_id).getFullView());
    }

}
