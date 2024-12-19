# Dự án Web Quản Lý Thư Viện: Library Web

## Giới thiệu:
Dự án Quản Lý Thư Viện là một ứng dụng web được xây dựng bằng Java Spring Boot và các công cụ khác nhằm hỗ trợ việc quản lý sách, người dùng, và các hoạt động phạt, mượn/trả sách trong thư viện. Dự án hướng tới việc số hóa quy trình quản lý thư viện, giúp tiết kiệm thời gian và nâng cao hiệu quả công việc.
Đây là một trang web giúp cho việc quản lý thư viện của thủ thư (admin) trở nên dễ dàng hơn với các thao tác đơn giản. Người mượn sách (user) có thể đánh giá lại các sách mà mình đã và đang mượn, kiểm tra xem mình có bị phạt hay không thông qua tài khoản user được thư viện cấp cho.

## Mục lục chính:
1. Cơ sở dữ liệu và mô hình EER
2. Các tính năng chính
3. Công nghệ sử dụng
4. Hướng dẫn cài đặt và chạy dự án
5. Liên hệ/Phản hồi
6. Các thành viên và đóng góp

## 1. Cơ sở dữ liệu và mô hình EER:
- Gồm các bảng và mô hình như hình vẽ:

## 2. Các tính năng chính:

### 2.1 Admin:
- **Quản lý sách**:
  - Thêm sách, sửa thông tin sách, xóa sách
  - Xem đánh giá về sách
- **Xử lý các yêu cầu**:
  - Mượn sách, trả sách, các trạng thái bị phạt
  - Tìm kiếm nhanh theo tên sách, tên người mượn, trạng thái các yêu cầu mượn sách.
  - Sửa, xóa các yêu cầu theo thực tế.
- **Thống kê người mượn bị phạt**:
  - Hiển thị danh sách các người mượn bị phạt và trạng thái nộp phạt.
  - Cập nhật trạng thái đã nộp phạt hay chưa.
- **Quản lý thể loại sách**:
  - Thêm, sửa, xóa thể loại các sách có trong thư viện
- **Quản lý thành viên**:
  - Thêm, sửa, xóa thành viên trong thư viện.

### 2.2 User:
- **Đánh giá lại sách** đã và đang mượn.
- **Kiểm tra phạt**: Kiểm tra xem người mượn có đang bị phạt hay không.

## 3. Công nghệ sử dụng:
- **Database**:
  - MySQL
- **Back-end**:
  - Java
  - Spring Boot (Spring MVC, Spring Data JPA)
  - JSON serialization/deserialization
- **Front-end**:
  - HTML
  - SCSS
  - JavaScript
- **Các công nghệ khác**:
  - Deploy web: Vercel
  - JWT (JSON Web Token) cho bảo mật.

## 4. Hướng dẫn cài đặt và chạy dự án:

### 4.1 Clone dự án về:
```bash
git clone https://github.com/TNoLisme/LibraryWeb.git
```
### 4.2 Cài đặt cơ sở dữ liệu:
- Cài đặt MySQL Workbench (nếu chưa tải).
- Tạo cơ sở dữ liệu mới:
```sql
CREATE DATABASE qltv;
```
### 4.3 Cấu hình kết nối cơ sở dữ liệu:
Cập nhật cấu hình kết nối cơ sở dữ liệu trong file application.properties:
```
spring.application.name=QuanLyThuVien
spring.datasource.url=jdbc:mysql://localhost:3306/qltv
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
Thay yourpassword bằng mật khẩu của bạn khi đăng nhập MySQL.

### 4.4 Chạy và sử dụng ứng dụng:
- Mở trình duyệt và truy cập vào địa chỉ sau để sử dụng ứng dụng:
```
http://localhost:8080
```
- Nếu mọi thứ cài đặt thành công, bạn sẽ thấy giao diện của hệ thống quản lý thư viện.

- Tài khoản admin:
    - Tên đăng nhập: admin
    - Mật khẩu: 123
- Tài khoản user: Có thể xem hoặc tạo thông qua tài khoản admin.

### 5. Liên hệ và phản hồi:
Mọi ý kiến đóng góp, thắc mắc và phản hồi xin gửi qua email: 
thinh01092005@gmail.com
### 6. Các thành viên và đóng góp:
   #### 1.Nguyễn Văn Thịnh - MSV: 230217206

  - Đóng góp: Front-end và back-end phần đăng nhập, quản lý sách, đăng xuất, thiết kế cơ sở dữ liệu, test lỗi.


  #### 2.Nguyễn Trường Sơn - MSV:

  - Đóng góp: Front-end và back-end phần thành viên, mượn sách, triển khai web, test lỗi.


  #### 3.Đào Ngọc Tân - MSV:

  - Đóng góp: Front-end và back-end phần loại sách, đánh giá lại, phạt, test lỗi.
    
  #### 4.Ngô Anh Tú - MSV:

  - Đóng góp: Front-end và back-end phần danh sách phạt, test lỗi.

