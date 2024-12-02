package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BooksService {

    @Autowired
    private BookRepository booksRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Create or update a book
    public BookDto saveBook(BookDto bookDto) {
        Book book = convertToEntity(bookDto);
        Book savedBook = booksRepository.save(book);
        return convertToDto(savedBook);
    }

    // Get all books
    public List<Book> getAllBooks() {
       return booksRepository.findAll();
    }

    // Get book by id
    public Optional<BookDto> getBookById(int id) {
        Optional<Book> bookOptional = booksRepository.findById(id);
        return bookOptional.map(this::convertToDto);
    }

    // Update book
    public BookDto updateBook(int id, BookDto bookDto) {
        if (booksRepository.existsById(id)) {
            Book book = convertToEntity(bookDto);
            book.setId(id); // Đảm bảo ID là của bản ghi đang cập nhật
            Book updatedBook = booksRepository.save(book);
            return convertToDto(updatedBook);
        }
        throw new RuntimeException("Book with id " + id + " not found");
    }

    // Delete book
    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    // Convert Book to BookDto
    private BookDto convertToDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishYear(),
                book.getQuantity(),
                book.getCateID().getId()
        );
    }

    // Convert BookDto to Book
    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublishYear(bookDto.getPublishYear());
        book.setQuantity(bookDto.getQuantity());
        book.setCateID(categoryRepository.getReferenceById(bookDto.getCateID()));
        return book;
    }
}
