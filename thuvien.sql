-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th9 06, 2024 lúc 04:38 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `thuvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `ma_ncc` int(11) NOT NULL,
  `ten_ncc` varchar(100) NOT NULL,
  `dia_chi` varchar(100) NOT NULL,
  `sdt` varchar(10) NOT NULL,
  `email` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`ma_ncc`, `ten_ncc`, `dia_chi`, `sdt`, `email`) VALUES
(1, 'Công ty cổ phần phát hành sách TPHCM', '60-62 Lê Lợi, P. Bến Nghé, Q. 1,TPHCM', '0283822579', 'fahasasg@gmail.com'),
(2, 'Công Ty TNHH Văn Hóa Việt Long', '14/35, Đào Duy Anh, P.9, Q. Phú Nhuận,Tp.HCM', '0987234543', 'vietlong@gmail.com'),
(3, 'Nhà Sách Trực Tuyến BOOKBUY.VN', '147 Pasteur, P. 6, Q. 3,Tp. Hồ Chí Minh', '0933109009', 'bookbuy@gmail.com'),
(4, 'Nhà sách Bích Quân', '249 Lý Thường Kiệt, KP. Thống Nhất 1, Dĩ An,Bình Dương', '0944566788', 'sachsichau@gmail.com'),
(5, 'Nhà Sách Trực Tuyến BOOKBUY.VN', '147 Pasteur, P. 6, Q. 3,Tp. Hồ Chí Minh', '0933109009', 'bookbuy@gmail.com'),
(6, 'Nhà sách Bích Quân', '249 Lý Thường Kiệt, KP. Thống Nhất 1, Dĩ An,Bình Dương', '0944566788', 'sachhaipho@gmail.com'),
(7, 'Hiệu Sách Tiến Thành', 'Số 11-13 Đường 53, P. 10, Q. 6,TPHCM', '0919196677', 'kimlong488@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieumuon`
--

CREATE TABLE `phieumuon` (
  `ma_phieumuon` int(11) NOT NULL,
  `ma_taikhoan` int(11) NOT NULL,
  `ma_sach` int(11) NOT NULL,
  `ten_nv` varchar(50) DEFAULT NULL,
  `ngay_muon` date DEFAULT NULL,
  `ngay_han` date DEFAULT NULL,
  `ngay_tra` date DEFAULT NULL,
  `trang_thai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieumuon`
--

INSERT INTO `phieumuon` (`ma_phieumuon`, `ma_taikhoan`, `ma_sach`, `ten_nv`, `ngay_muon`, `ngay_han`, `ngay_tra`, `trang_thai`) VALUES
(1, 2, 1, '', NULL, NULL, NULL, 1),
(3, 2, 2, 'admin', '2024-09-06', '2024-09-20', '2024-09-13', 6),
(13, 3, 1, 'admin', '2024-09-06', '2024-09-20', '2024-09-07', 3),
(14, 3, 2, 'admin', '2024-09-03', '2024-09-17', '2024-09-12', 5),
(15, 3, 3, 'admin', '2024-09-03', '2024-09-17', NULL, 2),
(16, 5, 1, 'admin', '2024-09-03', '2024-09-17', '2024-09-20', 4),
(17, 5, 2, '', NULL, NULL, NULL, 1),
(18, 5, 4, '', NULL, NULL, NULL, 1),
(19, 2, 3, NULL, NULL, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieuphat`
--

CREATE TABLE `phieuphat` (
  `ma_phieuphat` int(11) NOT NULL,
  `ma_phieumuon` int(11) NOT NULL,
  `ma_sach` int(11) NOT NULL,
  `ten_nv` varchar(50) DEFAULT NULL,
  `ngay_tao` date DEFAULT NULL,
  `tien_phat` int(11) NOT NULL,
  `mo_ta` varchar(100) DEFAULT NULL,
  `trang_thai` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieuphat`
--

INSERT INTO `phieuphat` (`ma_phieuphat`, `ma_phieumuon`, `ma_sach`, `ten_nv`, `ngay_tao`, `tien_phat`, `mo_ta`, `trang_thai`) VALUES
(2, 14, 2, 'admin', '2024-09-03', 2300, 'Làm hỏng sách ít', 1),
(3, 16, 1, 'admin', '2024-09-03', 6000, 'Trả trễ hạn', 1),
(4, 3, 2, 'admin', '2024-09-06', 11500, 'Làm hỏng sách nhiều', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sach`
--

CREATE TABLE `sach` (
  `ma_sach` int(11) NOT NULL,
  `ma_loai` int(11) DEFAULT NULL,
  `ten_sach` varchar(100) DEFAULT NULL,
  `tac_gia` varchar(100) DEFAULT NULL,
  `mo_ta` varchar(100) DEFAULT NULL,
  `NXB` varchar(50) DEFAULT NULL,
  `ngay_xuat_ban` date DEFAULT NULL,
  `so_luong` int(11) NOT NULL,
  `gia` int(11) NOT NULL,
  `hinh` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `sach`
--

INSERT INTO `sach` (`ma_sach`, `ma_loai`, `ten_sach`, `tac_gia`, `mo_ta`, `NXB`, `ngay_xuat_ban`, `so_luong`, `gia`, `hinh`) VALUES
(1, 2, 'Nghìn lẻ một đêm', 'Antoine Galland', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2014-08-15', 98, 53000, 'nghinle1dem.jpg'),
(2, 1, 'Địa lý 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2019-08-03', 97, 23000, 'diali10.jpg'),
(3, 1, 'Địa lý 11', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo TPHCM', 'Nhà xuất bản Giáo dục Việt Nam', '2016-05-12', 95, 24000, 'diali11.jpg'),
(4, 1, 'Vật lý 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2018-11-23', 98, 27000, 'nghinle1dem.jpg'),
(5, 1, 'Toán 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2017-03-14', 100, 35000, 'nghinle1dem.jpg'),
(6, 1, 'Sinh học 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2019-07-20', 100, 28000, 'nghinle1dem.jpg'),
(7, 1, 'Hóa học 11', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2020-01-05', 100, 37000, 'nghinle1dem.jpg'),
(8, 1, 'Ngữ văn 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2015-09-10', 100, 25000, 'nghinle1dem.jpg'),
(9, 1, 'Lịch sử 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2021-06-18', 100, 22000, 'nghinle1dem.jpg'),
(10, 1, 'Công nghệ 11', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2017-10-30', 100, 17000, 'nghinle1dem.jpg'),
(11, 1, 'Tiếng Anh 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2020-12-25', 100, 30000, 'nghinle1dem.jpg'),
(12, 1, 'GDCD 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2019-04-07', 100, 15000, 'nghinle1dem.jpg'),
(13, 1, 'Địa lý 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2016-05-12', 100, 29000, 'nghinle1dem.jpg'),
(14, 1, 'Vật lý 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2018-11-23', 100, 32000, 'nghinle1dem.jpg'),
(15, 1, 'Toán 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2017-03-14', 100, 47000, 'nghinle1dem.jpg'),
(16, 1, 'Sinh học 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2019-07-20', 100, 36000, 'nghinle1dem.jpg'),
(17, 1, 'Hóa học 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2020-01-05', 100, 42000, 'nghinle1dem.jpg'),
(18, 1, 'Ngữ văn 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2015-09-10', 100, 21000, 'nghinle1dem.jpg'),
(19, 1, 'Lịch sử 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2021-06-18', 100, 20000, 'nghinle1dem.jpg'),
(20, 1, 'Công nghệ 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2017-10-30', 100, 25000, 'nghinle1dem.jpg'),
(21, 1, 'Tiếng Anh 10', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2020-12-25', 100, 32000, 'nghinle1dem.jpg'),
(22, 1, 'GDCD 12', 'Bộ Giáo dục và Đào tạo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'Nhà xuất bản Giáo dục Việt Nam', '2019-04-07', 100, 12000, 'nghinle1dem.jpg'),
(23, 2, 'Cuộc Phiêu Lưu Kỳ Thú', 'Nguyễn Văn A', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2017-04-11', 100, 103000, 'nghinle1dem.jpg'),
(24, 3, 'Hành Trình Vượt Biển', 'Trần Thị Bé Thơ', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2018-06-20', 100, 65000, 'nghinle1dem.jpg'),
(25, 4, 'Thế Giới Phù Thủy', 'Lê Quốc Cường', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2019-08-15', 100, 78000, 'nghinle1dem.jpg'),
(26, 5, 'Kỳ Bí Phương Đông', 'Hoàng Minh Dương', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-02-28', 100, 0, 'nghinle1dem.jpg'),
(27, 6, 'Mật Mã Da Vinci', 'Leonard David', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2016-09-12', 100, 0, 'nghinle1dem.jpg'),
(28, 7, 'Biệt Đội Siêu Anh Hùng', 'Đỗ Quốc Phúc', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2019-05-05', 100, 0, 'nghinle1dem.jpg'),
(29, 8, 'Vùng Đất Bất Tận', 'Ngô Thanh Giảng', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2021-03-10', 100, 0, 'nghinle1dem.jpg'),
(30, 9, 'Cuộc Sống Màu Hồng', 'Đặng Thị Hường', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-11-18', 100, 0, 'nghinle1dem.jpg'),
(31, 10, 'Sức Mạnh Tinh Thần', 'Nguyễn Hoàng Yến', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2015-07-22', 100, 0, 'nghinle1dem.jpg'),
(32, 2, 'Hành Trình Mới', 'Vũ Quang', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2018-12-30', 100, 0, 'nghinle1dem.jpg'),
(33, 3, 'Người Hùng Tí Hon', 'Lê Minh K', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2017-03-01', 100, 0, 'nghinle1dem.jpg'),
(34, 4, 'Chuyện Kể Đêm Khuya', 'Nguyễn Thị Lan', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2018-09-15', 100, 0, 'nghinle1dem.jpg'),
(35, 5, 'Biển Sâu Mắt Đen', 'Trần Quốc Minh', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2019-04-27', 100, 0, 'nghinle1dem.jpg'),
(36, 6, 'Vương Quốc Lạ Lùng', 'Đặng Thị Nga', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-01-20', 100, 0, 'nghinle1dem.jpg'),
(37, 7, 'Cuộc Phiêu Lưu Thời Gian', 'Phạm Văn Tiến', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2016-10-30', 100, 0, 'nghinle1dem.jpg'),
(38, 8, 'Những Ngày Rực Rỡ', 'Vũ Thị Phụng', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2019-06-18', 100, 0, 'nghinle1dem.jpg'),
(39, 9, 'Thành Phố Mộng Mơ', 'Lê Văn Quỳnh', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2021-04-25', 100, 0, 'nghinle1dem.jpg'),
(40, 10, 'Hành Trình Về Nhà', 'Ngô Minh Rì', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-08-14', 100, 0, 'nghinle1dem.jpg'),
(41, 2, 'Vùng Trời Bình Yên', 'Đỗ Thị S', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2015-05-10', 100, 0, 'nghinle1dem.jpg'),
(42, 3, 'Phép Màu Cuối Năm', 'Nguyễn Thanh Tảo', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2018-11-21', 100, 0, 'nghinle1dem.jpg'),
(43, 4, 'The Mysterious Island', 'Jules Verne', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2017-01-19', 100, 0, 'nghinle1dem.jpg'),
(44, 5, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2018-07-09', 100, 0, 'nghinle1dem.jpg'),
(45, 6, 'The Hobbit', 'J.R.R. Tolkien', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2019-03-22', 100, 0, 'nghinle1dem.jpg'),
(46, 7, 'To Kill a Mockingbird', 'Harper Lee', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-05-13', 100, 0, 'nghinle1dem.jpg'),
(47, 8, '1984', 'George Orwell', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2021-02-27', 100, 0, 'nghinle1dem.jpg'),
(48, 9, 'Pride and Prejudice', 'Jane Austen', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2020-08-01', 100, 0, 'nghinle1dem.jpg'),
(49, 10, 'The Catcher in the Rye', 'J.D. Salinger', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2016-11-11', 100, 0, 'nghinle1dem.jpg'),
(50, 2, 'The Great Gatsby', 'F. Scott Fitzgerald', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2019-09-30', 100, 0, 'nghinle1dem.jpg'),
(51, 3, 'Brave New World', 'Aldous Huxley', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Kim Đồng', '2018-04-05', 100, 0, 'nghinle1dem.jpg'),
(52, 4, 'Animal Farm', 'George Orwell', 'Sách giáo khoa địa lý 10 của bộ Giáo dục và Đào tạo', 'NXB Trẻ', '2017-12-18', 100, 0, 'nghinle1dem.jpg'),
(53, 2, 'Sách của đạt', 'John Son', 'quyển sách thú vị quyển sách thú vị quyển sách thú vị', 'NXB Trẻ', '2017-06-22', 0, 0, 'diali10.jpg'),
(54, 7, 'Truyện kinj dị đêm khuya', 'John Smith', 'SÁch đọc lúc 12h đêm', 'NXB Kim Đồng', '2021-06-22', 0, 0, 'diali10.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `ma_taikhoan` int(11) NOT NULL,
  `ma_quyen` int(11) NOT NULL DEFAULT 2,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(150) DEFAULT NULL,
  `hoten` varchar(100) DEFAULT NULL,
  `sdt` varchar(10) DEFAULT NULL,
  `diachi` varchar(100) DEFAULT NULL,
  `ngay_tao` date DEFAULT NULL,
  `trang_thai` int(11) NOT NULL DEFAULT 1,
  `avatar` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`ma_taikhoan`, `ma_quyen`, `email`, `password`, `hoten`, `sdt`, `diachi`, `ngay_tao`, `trang_thai`, `avatar`) VALUES
(1, 1, 'admin', '$2b$12$SdNDFlgV4w5SAVaHCHshdOZv0z6uR4XRL/dwfjNyHsGLnb4K2GtyO', 'admin', '0999888333', 'admin web quan ly thu vien', '2024-08-25', 1, 'logo.jpg'),
(2, 2, 'dat@gmail.com', '$2b$12$ayRkEdPfHlqO3gVIDteoWeotecUd3H.X/4FZd3jaJItv4CgyhXPk6', 'datne', '0123456789', 'an dương vương p5 q5', '2024-08-30', 1, 'logo.jpg'),
(3, 2, 'dat2@gmail.com', '$2a$10$ATyuwMhdhMlSpfCeOxk.Aui06Estpq0yXrrWcqz9FgU', 'tuấn đạt', '0999888333', 'Phường Minh Phương, Thành phố Việt Trì, Phú Thọ', '2024-08-30', 1, 'logo.jpg'),
(4, 2, 'dat3@gmail.com', '$2a$10$pUyP0nEyiJYnhp22EgoJ3.qmmL8BIGD5TLE4NkOdt3N', 'dat333', '0123456789', 'an dương vương p5 q5', '2024-08-30', 1, 'logo.jpg'),
(5, 2, 'tuan@gmail.com', '$2a$10$w7NPHD3arNTHRf8fKbdrE.nkKPRslbvXG0nJyERbC1I', 'đạt tuấn', '0923456781', 'an dương vương p5 q5', '2024-08-30', 1, 'logo.jpg'),
(6, 2, 'tuan2@gmail.com', '$2a$10$IP0HD9MpvRWga.BLVW38OOxVJ5/DO9ZGX./EQiFcaM7D8kIQnyyzm', 'dat333', '0923456781', 'Phường Minh Phương, Thành phố Việt Trì, Phú Thọ', '2024-09-06', 1, 'dat333'),
(7, 2, 'tuan3@gmail.com', '$2a$10$Cuyr0goIH3IlCA8BVVw83.djIp/yGZTOcrAaOGAzAoXX23faGiBxa', 'datne', '0923456781', 'an dương vương p5 q5', '2024-09-06', 1, 'datne'),
(8, 2, 'dat4@gmail.com', '$2a$10$.0jMnXggI7m/.K7.ws.7QuvYOLAgbKG7KU4eiOEq2iSPyCZlcKQFm', 'tuấn đạt', '0999888333', 'an dương vương p5 q5', '2024-09-06', 1, 'diali10.jpg'),
(9, 2, 'dat5@gmail.com', '$2a$10$jNXFLLLjwo54H.wgR9Q7heO9xnb.uSXsXDBro5ZK1QfQZnZDMN97G', 'tuấn đạt', '0923456781', 'an dương vương p5 q5', '2024-09-06', 1, 'logo.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `theloai`
--

CREATE TABLE `theloai` (
  `ma_loai` int(11) NOT NULL,
  `ten_loai` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `theloai`
--

INSERT INTO `theloai` (`ma_loai`, `ten_loai`) VALUES
(1, 'Sách giáo khoa'),
(2, 'Viễn tưởng'),
(3, 'Trinh thám'),
(4, 'Tiểu thuyết'),
(5, 'Phiêu lưu'),
(6, 'Lãng mạn'),
(7, 'Kinh dị'),
(8, 'Cổ tích'),
(9, 'Hài hước'),
(10, 'Lịch sử');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`ma_ncc`);

--
-- Chỉ mục cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  ADD PRIMARY KEY (`ma_phieumuon`);

--
-- Chỉ mục cho bảng `phieuphat`
--
ALTER TABLE `phieuphat`
  ADD PRIMARY KEY (`ma_phieuphat`);

--
-- Chỉ mục cho bảng `sach`
--
ALTER TABLE `sach`
  ADD PRIMARY KEY (`ma_sach`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`ma_taikhoan`);

--
-- Chỉ mục cho bảng `theloai`
--
ALTER TABLE `theloai`
  ADD PRIMARY KEY (`ma_loai`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  MODIFY `ma_ncc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `phieumuon`
--
ALTER TABLE `phieumuon`
  MODIFY `ma_phieumuon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `phieuphat`
--
ALTER TABLE `phieuphat`
  MODIFY `ma_phieuphat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT cho bảng `sach`
--
ALTER TABLE `sach`
  MODIFY `ma_sach` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  MODIFY `ma_taikhoan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `theloai`
--
ALTER TABLE `theloai`
  MODIFY `ma_loai` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
