package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.BookCategory;
import com.example.QuanLyThuVien.Entity.Category;
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
    public List<BookDto> getAllBooks() {
       return booksRepository.getAllBooks();
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
            book.setId(id);
            Book updatedBook = booksRepository.save(book);
            return convertToDto(updatedBook);
        }
        throw new RuntimeException("Book with id " + id + " not found");
    }

    public void deleteBook(int id) {
        booksRepository.deleteById(id);
    }

    private BookDto convertToDto(Book book) {
        List<Integer> categoryIds = book.getCategories()
                                        .stream()
                                        .map(category -> category.getId()) // Chuyển từ Category sang ID
                                        .collect(Collectors.toList());
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublishYear(),
                book.getQuantity(),
                categoryIds
        );
    }

    private Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublishYear(bookDto.getPublishYear());
        book.setQuantity(bookDto.getQuantity());

        List<Category> categories = bookDto.getCategoryIds()
                                           .stream()
                                           .map(categoryId -> {
                                               Category category = categoryRepository.findById(categoryId)
                                                                                      .orElseThrow(() -> new RuntimeException("Category not found"));
                                               return category;
                                           })
                                           .collect(Collectors.toList());
        book.setCategories(categories); 

        return book;
    }

}
