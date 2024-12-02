create database qltv;
use qltv;
CREATE TABLE Categories (
    categoryID INT AUTO_INCREMENT PRIMARY KEY, -- ID thể loại
    name VARCHAR(255) NOT NULL UNIQUE          -- Tên thể loại
);

CREATE TABLE Books (
    bookID INT AUTO_INCREMENT PRIMARY KEY, -- ID sách
    title VARCHAR(255) NOT NULL,           -- Tên sách
    author VARCHAR(255) NOT NULL,          -- Tác giả
    categoryID INT,                        -- ID thể loại (liên kết đến Categories)
    publishYear INT,                       -- Năm xuất bản
    quantity INT DEFAULT 1,                -- Số lượng sách
    FOREIGN KEY (categoryID) REFERENCES Categories(categoryID) ON DELETE SET NULL
);

CREATE TABLE Members (
    memberID INT AUTO_INCREMENT PRIMARY KEY, -- ID thành viên
    fullName VARCHAR(255) NOT NULL,          -- Tên đầy đủ
    email VARCHAR(255) UNIQUE NOT NULL,      -- Email (đăng nhập)
    password VARCHAR(255) NOT NULL,          -- Mật khẩu
);

CREATE TABLE BorrowRequests (
    requestID INT AUTO_INCREMENT PRIMARY KEY, -- ID yêu cầu
    memberID INT,                             -- ID thành viên
    bookID INT,                               -- ID sách
    borrowDate DATE NOT NULL,                 -- Ngày mượn
    returnDate DATE,                          -- Ngày trả (có thể NULL nếu chưa trả)
    status ENUM('pending', 'approved', 'returned') DEFAULT 'pending', -- Trạng thái yêu cầu
    FOREIGN KEY (memberID) REFERENCES Members(memberID) ON DELETE CASCADE,
    FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE
);

CREATE TABLE Reviews (
    reviewID INT AUTO_INCREMENT PRIMARY KEY,  -- ID đánh giá
    bookID INT,                               -- ID sách
    memberID INT,                             -- ID thành viên
    rating INT CHECK (rating BETWEEN 1 AND 5),-- Xếp hạng (1-5)
    comment TEXT,                             -- Nội dung đánh giá
    reviewDate timestamp DEFAULT current_timestamp,     -- Ngày đánh giá
    FOREIGN KEY (bookID) REFERENCES Books(bookID) ON DELETE CASCADE,
    FOREIGN KEY (memberID) REFERENCES Members(memberID) ON DELETE CASCADE
);

CREATE TABLE Admins (
    adminID INT AUTO_INCREMENT PRIMARY KEY, -- ID admin
    fullName VARCHAR(255) NOT NULL,         -- Tên đầy đủ admin
    email VARCHAR(255) UNIQUE NOT NULL,     -- Email đăng nhập admin
    password VARCHAR(255) NOT NULL          -- Mật khẩu admin
);

CREATE TABLE MemberPenalties (
    penaltyRecordID INT AUTO_INCREMENT PRIMARY KEY,  -- ID bản ghi phạt
    memberID INT,                                   -- ID của thành viên
    fineID INT,                                     -- ID mức phạt từ bảng Fines
    penaltyDate DATE NOT NULL,                       -- Ngày phạt
    paidStatus ENUM('paid', 'unpaid') DEFAULT 'unpaid',  -- Trạng thái thanh toán
    FOREIGN KEY (memberID) REFERENCES Members(memberID) ON DELETE CASCADE,
    FOREIGN KEY (fineID) REFERENCES Fines(fineID) ON DELETE CASCADE
);

CREATE TABLE Fines (
    fineID INT AUTO_INCREMENT PRIMARY KEY,  -- ID của mức phạt
    fineReason VARCHAR(255) NOT NULL,       -- Lý do phạt
    fineAmount DECIMAL(10, 2) NOT NULL      -- Mức phạt (số tiền)
);

-- Thêm một số lý do phạt giả định
INSERT INTO Fines (fineReason, fineAmount) VALUES
('Late book return', 10.00),              -- Phạt 10.00 vì trả sách trễ
('Damaged book', 20.00),               -- Phạt 20.00 vì sách bị hư hỏng
('Lost book', 30.00);                -- Phạt 30.00 vì đánh mất sách
	
