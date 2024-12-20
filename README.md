<a name = 'header'></a>
# Dự án Web Quản Lý Thư Viện: Library Web

<a name = 'intro'></a>
## Giới thiệu:
- Dự án Quản Lý Thư Viện là một ứng dụng web được xây dựng bằng Java Spring Boot và các công cụ khác nhằm hỗ trợ việc quản lý sách, người dùng, và các hoạt động phạt, mượn/trả sách trong thư viện. Dự án hướng tới việc số hóa quy trình quản lý thư viện, giúp tiết kiệm thời gian và nâng cao hiệu quả công việc.


- Đây là một trang web giúp cho việc quản lý thư viện của thủ thư (admin) trở nên dễ dàng hơn với các thao tác đơn giản. Người mượn sách (user) có thể đánh giá lại các sách mà mình đã và đang mượn, kiểm tra xem mình có bị phạt hay không thông qua tài khoản user được thư viện cấp cho.

<a name = 'map'></a>
## Mục lục chính:
1. [Cơ sở dữ liệu và mô hình ER Diagram](#database)
2. [Các tính năng chính](#issue)
3. [Công nghệ sử dụng](#technique)
4. [Hướng dẫn cài đặt và chạy dự án](#tutorial)
5. [Liên hệ/Phản hồi](#contact)
6. [Các thành viên và đóng góp](#contribute)

<a name = 'database'></a>
## 1. Cơ sở dữ liệu và mô hình EER:
- Gồm các bảng và mô hình như hình vẽ:

  ![Mô hình ER Diagram](./image/ER%20Diagram.png)

- Sơ đồ mô hình thực thể liên kết ER:
   ![Mô hình ER](./image/Sơ%20đồ%20ER.png)

<a name = 'issue'></a>
## 2. Các tính năng chính:

<a name = 'issue-admin'></a>
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

<a name = 'issue-normal-user'></a>
### 2.2 User:
- **Đánh giá lại sách**: đánh giá sách đã và đang mượn theo số sao và bình luận phản hồi.
- **Kiểm tra phạt**: Kiểm tra xem người mượn có đang bị phạt hay không.

<a name = 'technique'></a>
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

<a name = 'tutorial'></a>
## 4. Hướng dẫn cài đặt và chạy dự án:
- Bạn có thể truy cập trực tiếp vào link vercel sau và sử dụng web: https://library-web-nine.vercel.app/
- Hoặc bạn có thể clone dự án về và chạy trên máy của bạn. Sau đây là hướng dẫn clone và chạy:

### 4.1 Clone dự án về:
```bash
git clone https://github.com/TNoLisme/LibraryWeb.git
```
### 4.2 Cài đặt cơ sở dữ liệu:
- Cài đặt MySQL Workbench (nếu chưa tải).
- Tạo cơ sở dữ liệu mới: [chi tiết tại đây](databaseqltv.sql)
```sql
CREATE DATABASE qltv;
```
### 4.3 Cấu hình kết nối cơ sở dữ liệu:
Hiện tại project đang ở trạng thái deployed. Để có thể chạy ở môi trường local, hãy làm theo hướng dẫn sau:

- Cập nhật cấu hình kết nối cơ sở dữ liệu trong file [application.properties](backend/src/main/resources/application.properties):
```
spring.application.name=QuanLyThuVien
spring.datasource.url=jdbc:mysql://localhost:3306/qltv
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=none
```
Thay yourpassword bằng mật khẩu của bạn khi đăng nhập MySQL.

- Cập nhật lại BASE_URL tại [config.js](frontend/assets/js/config.js)
```
let BASE_URL = "http://localhost:8080"
```

- Cập nhật lại nguồn config tại [WebMvcConfig.java](backend/src/main/java/com/example/QuanLyThuVien/Config/WebMvcConfig.java)
```
configuration.setAllowedOrigins(List.of("*"));
```

### 4.4 Chạy và sử dụng ứng dụng:
- Mở file backend của dự án trên 1 môi trường( khuyến khích Intelij IDEA phiên bản mới nhất hoặc Visual Studio Code) và tìm [file main chính](backend/src/main/java/com/example/QuanLyThuVien/QuanLyThuVienApplication.java) để run:
```
 LibraryWeb\backend\src\main\java\com\example\QuanLyThuVien\QuanLyThuVienApplication.java
```
- Mở trình duyệt và truy cập vào địa chỉ sau để sử dụng ứng dụng:
  (hoặc Open with Live Server một file .html bất kỳ trong thư mục frontend, đối với Visual Studio Code)
```
http://localhost:8080
```
- Nếu mọi thứ cài đặt thành công, bạn sẽ thấy giao diện đăng nhập của hệ thống quản lý thư viện. Khi đăng nhập thành công sẽ hiển thị dashboard của web(đây là phần tĩnh cố định),
hãy thực hiện các thao tác mà bạn muốn thông qua các phần được liệt kê bên góc trái.

### 4.5 Thông tin đăng nhập
- Tài khoản admin:
    - Tên đăng nhập: admin
    - Mật khẩu: 123
- Tài khoản user: Có thể xem hoặc tạo thông qua tài khoản admin.

<a name = 'contact'></a>
### 5. Liên hệ và phản hồi:
Mọi ý kiến đóng góp, thắc mắc và phản hồi xin gửi qua email: 
thinh01092005@gmail.com

<a name = 'contribute'></a>
### 6. Các thành viên và đóng góp:
   #### 1.Nguyễn Văn Thịnh - MSV: 230217206

  - Đóng góp: Front-end các phần, thiết kế cơ sở dữ liệu, readme, test lỗi.


  #### 2.Nguyễn Trường Sơn - MSV: 23021686

  - Đóng góp: Back-end phần quản lý người dùng, login, triển khai web, thiết kế cơ sở dữ liệu.


  #### 3.Đào Ngọc Tân - MSV: 23021690

  - Đóng góp: Back-end quản lý sách và thể loại sách, mượn trả, review, thiết kế cơ sở dữ liệu. 
    
  #### 4.Ngô Anh Tú - MSV: 23021702

  - Đóng góp: Update front-end và back-end phần danh sách phạt, test lỗi và sửa lỗi phát sinh của cơ sở dữ liệu, insert data.

