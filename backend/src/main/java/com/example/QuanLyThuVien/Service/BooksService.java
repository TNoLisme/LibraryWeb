package com.example.QuanLyThuVien.Service;

import com.example.QuanLyThuVien.DTO.BookDto;
import com.example.QuanLyThuVien.DTO.CategoryDTO;
import com.example.QuanLyThuVien.Entity.Book;
import com.example.QuanLyThuVien.Entity.BookCategory;
import com.example.QuanLyThuVien.Entity.Category;
import com.example.QuanLyThuVien.Repo.BookRepository;
import com.example.QuanLyThuVien.Repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    // Lấy kết quả từ repository
    List<Object[]> results = booksRepository.getAllBooksWithCategories();

    // Sử dụng Map để nhóm các BookDto theo bookId
    Map<Integer, BookDto> bookMap = new HashMap<>();

    for (Object[] row : results) {
        // Kiểm tra số cột trong row để tránh lỗi
        if (row.length >= 6) {  // Đảm bảo có đủ 6 cột từ kết quả truy vấn
            Integer bookId = (Integer) row[0];
            String title = (String) row[1];
            String author = (String) row[2];
            Integer publishYear = (Integer) row[3];
            Integer quantity = (Integer) row[4];
            Integer categoryId = row[5] != null ? (Integer) row[5] : null;
            String categoryName = (row.length > 6 && row[6] != null) ? (String) row[6] : null; // Lấy tên danh mục

            // Nếu bookId chưa tồn tại trong map, khởi tạo một BookDto mới
            BookDto bookDto = bookMap.computeIfAbsent(bookId, id -> new BookDto(
                id, title, author, publishYear, quantity, new ArrayList<>()
            ));

            // Thêm category vào danh sách nếu categoryId và categoryName không null
            if (categoryId != null && categoryName != null) {
                CategoryDTO categoryDto = new CategoryDTO(categoryId, categoryName);
                bookDto.getCategoryDtos().add(categoryDto);
            }
        }
    }

    // Trả về danh sách BookDto từ Map
    return new ArrayList<>(bookMap.values());
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
        List<CategoryDTO> categoryDtos = book.getCategories()
                                             .stream()
                                             .map(category -> new CategoryDTO(category.getId(), category.getName()))  // Chuyển từ Category sang CategoryDTO
                                             .collect(Collectors.toList());
        return new BookDto(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getPublishYear(),
            book.getQuantity(),
            categoryDtos  
        );
    }


    public boolean existsByCategoryId(int categoryId) {
        // Kiểm tra xem có ít nhất một sách thuộc thể loại này
        return booksRepository.countByCategoriesId(categoryId) > 0;
    }


    public Book convertToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setPublishYear(bookDto.getPublishYear());
        book.setQuantity(bookDto.getQuantity());

        // Chuyển danh sách categoryIds thành danh sách Category entities
        List<Category> categories = new ArrayList<>();
        for (Integer categoryId : bookDto.getCategoryIds()) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + categoryId));
            categories.add(category);
        }
        book.setCategories(categories);

        return book;
    }



}
