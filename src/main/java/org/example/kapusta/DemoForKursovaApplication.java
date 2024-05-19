package org.example.kapusta;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
class LibraryController {

    private final BookRepository bookRepository;

    public LibraryController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book) {
        String authorName = book.getAuthorName().toLowerCase(); // Using lowercased author name as key
        Book existingBook = bookRepository.findById(authorName).orElse(null);
        if (existingBook != null) {
            existingBook.setQuantity(existingBook.getQuantity() + book.getQuantity());
            bookRepository.save(existingBook);
        } else {
            // If book doesn't exist, add new entry
            bookRepository.save(book);
        }
        return "redirect:/books/all";
    }

    @GetMapping("/addBook")
    public String showAddBookForm(Model model) {
        Book book = new Book();

        model.addAttribute("book", book);
        return "addBookForm";
    }

    @GetMapping("/books/all")
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "booksList";
    }

    @GetMapping("/books/authors")
    public String getBooksByAuthor(Model model) {
        List<Book> booksByAuthor = bookRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Book::getAuthorName))
                .collect(Collectors.toList());
        model.addAttribute("books", booksByAuthor);
        return "redirect:/books/all";
    }
}
