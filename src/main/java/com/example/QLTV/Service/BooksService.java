package com.example.QLTV.Service;

import com.example.QLTV.Entity.Books;
import com.example.QLTV.Repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    // Create or update a book
    public Books saveBook(Books book) {
        return booksRepository.save(book);
    }

    // Get all books
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    // Get book by id
    public Optional<Books> getBookById(int id) {
        return booksRepository.findById(id);
    }

    // Update book
    public Books updateBook(int id, Books bookDetails) {
        if (booksRepository.existsById(id)) {
            bookDetails.setBookId(id); // Ensure the id remains the same
            return booksRepository.save(bookDetails);
        }
        return null;
    }

    // Delete book
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }
}

