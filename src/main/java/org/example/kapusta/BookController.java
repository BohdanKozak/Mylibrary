package org.example.kapusta;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @DeleteMapping("/books/{name}")
    public ResponseEntity<String> removeBookByName(@PathVariable String name) {
        Book book = bookRepository.findByTitle(name); // Пошук книги за назвою
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        if(book.getQuantity() == 1){
            bookRepository.delete(book);
            return ResponseEntity.ok("Book successfully deleted");
        }
        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);
        return ResponseEntity.ok("Book quantity decremented");
    }

    @GetMapping("/books/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://127.0.0.1:5500")
    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            if (book.getAuthorName() == null) {
                throw new IllegalArgumentException("Author name is null");
            }
            String authorName = book.getAuthorName().toLowerCase();
            Optional<Book> existingBookOptional = bookRepository.findById(authorName);

            if (existingBookOptional.isPresent()) {
                Book existingBook = existingBookOptional.get();
                existingBook.setQuantity(existingBook.getQuantity() + book.getQuantity());
                bookRepository.save(existingBook);
            } else {
                bookRepository.save(book);
            }
            return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Для дебагінгу
            return new ResponseEntity<>("Failed to add book: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/books/{name}")
    public ResponseEntity<String> addOneBook(@PathVariable String name) {
        Book book = bookRepository.findByTitle(name); // Пошук книги за назвою
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);
        return ResponseEntity.ok("Book quantity incremented");
    }
}
