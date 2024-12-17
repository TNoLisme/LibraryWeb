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
        try {
            // Log thông tin sách trước khi lưu
            System.out.println("Attempting to save book: " + bookDto.getTitle());

            // Kiểm tra dữ liệu đầu vào hợp lệ
            if (bookDto == null || bookDto.getTitle() == null || bookDto.getTitle().isEmpty()) {
                throw new IllegalArgumentException("Book data is invalid: Missing title");
            }

            // Chuyển đổi BookDto sang Book entity
            Book book = convertToEntity(bookDto);

            // Lưu sách vào cơ sở dữ liệu
            Book savedBook = booksRepository.save(book);

            // Log kết quả lưu sách
            System.out.println("Book saved successfully with ID: " + savedBook.getId());

            // Trả về BookDto đã được lưu
            return convertToDto(savedBook);
        } catch (IllegalArgumentException e) {
            // Log lỗi nếu dữ liệu không hợp lệ
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại lỗi để xử lý ở nơi gọi
        } catch (Exception e) {
            // Log lỗi hệ thống (kết nối cơ sở dữ liệu, lỗi khác)
            System.err.println("Unexpected error occurred while saving book: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error occurred while saving book", e); // Ném ra lỗi để xử lý ở nơi gọi
        }
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
