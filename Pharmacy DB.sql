-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 07, 2022 at 04:09 PM
-- Server version: 10.1.40-MariaDB
-- PHP Version: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacy`
--

-- --------------------------------------------------------

--
-- Table structure for table `banner`
--

CREATE TABLE `banner` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `link` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `banner`
--

INSERT INTO `banner` (`id`, `name`, `link`) VALUES
(1, 'casca remedies', 'https://storage.ko-fi.com/cdn/useruploads/display/8e35e332-97f6-43bd-aa84-fbc7d611fb38_pcd-pharma.png'),
(2, 'covid 19 vaccine', 'https://static.wixstatic.com/media/719dd2_685e472b1a5f44a6ac38d4443d5de81d~mv2.png/v1/fit/w_940%2Ch_746%2Cal_c/file.png'),
(3, 'Cetaphil', 'https://deals.hidubai.com/wp-content/uploads/2020/04/22160340/Image-2020-04-22T155847.744.jpg'),
(5, 'Aster offer', 'https://www.offeraty.com/sites/offeraty.com/files/offers/aster-pharmacy-bundle-offer-offer-23478.jpeg'),
(8, 'Morgan Smith', 'https://www.mobihealthnews.com/sites/default/files/Amazon_Pharmacy.jpeg'),
(9, 'discounts at blood glucose monitoring system', 'https://deals.hidubai.com/wp-content/uploads/2020/04/29115209/DEALS-IMAGE-2020-12-29T115356.804.jpg'),
(10, 'medlife coupons', 'https://www.promoscode.in/wp-content/uploads/2017/03/Medlife-1.png'),
(11, 'GrabOn', 'https://cdn.grabon.in/gograbon/images/merchant/1617024182334.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `comment` text NOT NULL,
  `value` float NOT NULL,
  `name` text NOT NULL,
  `product_id` int(11) NOT NULL,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `user_id`, `comment`, `value`, `name`, `product_id`, `updated_at`, `created_at`) VALUES
(1, 30, 'so bad', 4, 'Ali Assalem', 5, '2022-08-15', '2022-08-15'),
(2, 28, 'bad', 0.5, 'Ahmed Assalem', 5, '2022-08-15', '2022-08-15');

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `id` bigint(11) UNSIGNED NOT NULL,
  `name` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `img` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` text COLLATE utf8mb4_unicode_ci,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`id`, `name`, `img`, `email`, `updated_at`, `created_at`) VALUES
(1, 'store 1', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRoIOLUgsOA8XaLPEGC93vmkLXytqLwImR45g91cUNLQ1SWA3nTT_N1CNpwmpKkZ_hGq1I&usqp=CAU', 'admin@gmail.com', '0000-00-00', '0000-00-00'),
(2, 'store 2', 'https://www.elsaad.com/images/logo2.png', 'admin2@gmail.com', '0000-00-00', '0000-00-00'),
(3, 'store 3', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQoW-LWwJ_v3trN2F9De0n_iCmEERH2iOxnLH5XyNKxG0-X2ZTXF4fBdDFbphscYEC_loI&usqp=CAU', 'admin3@gmail.com', '0000-00-00', '0000-00-00'),
(4, 'store 4', 'https://i.pinimg.com/originals/d6/d5/c3/d6d5c360c7e621863975b09530e80468.jpg', '', '0000-00-00', '0000-00-00'),
(5, 'store 5', 'https://thumbs.dreamstime.com/b/capsule-pharmacy-medical-logo-template-vector-nature-leaf-blue-green-color-suitable-health-business-doctor-167427252.jpgq=tbn:ANd9GcRoIOLUgsOA8XaLPEGC93vmkLXyt', '', '0000-00-00', '0000-00-00'),
(6, 'store 6', 'https://img.freepik.com/premium-vector/logo-drugstore_579179-644.jpg?w=2000', NULL, '0000-00-00', '0000-00-00'),
(18, 'Store8', 'http://192.168.137.1:8000/storage/Pharmacy/Company/1802499-d4d0bf96-96ef-4da2-819c-703771483e39.jpg', '', '2022-08-12', '2022-08-12');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `link` text NOT NULL,
  `company_id` bigint(11) NOT NULL,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `name`, `link`, `company_id`, `updated_at`, `created_at`) VALUES
(1, 'Optilar', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTyWjefvURi1OWPttZ8iglGX5UBUmvPxdN64Q&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(2, 'Otrivin\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTlHm5dFLvTh21-iWUdBlEaQqKsLiUpGrrtg&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(3, 'Ophthazolin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTunZh-tw0j8oVZIiMioc3nqZIq3zjWAYfM-BqIqJQBp-JXURJAeo30M1LtuuRJNvMz29M&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(4, 'Isilin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTA2OswNYGQjGlUURexoptGP2lXp6at5eGc8q8RdHrVWP1H9LcoV8m3di4wgi5u9gOQphE&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(5, 'P.N.D\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqEHqdHDHPT4odidJIJL_Vj3NgU4sIA3zZLw&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(6, 'Enapril', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQC-vW1StjblXJPyp7eK53lQ7Of0wQEV6agaw&usqp=CAU', 1, '0000-00-00', '0000-00-00'),
(7, 'Broncho', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ6uR19tu0Y2B47DjHgulkuvAVYPGB_9FiXiM-e8rhR3244rm5RLpri9grOyZxP7Sht4b8&usqp=CAU', 2, '0000-00-00', '0000-00-00'),
(8, 'pectomed', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfWhNY42kmg4jgtYaFMd-MxyqQkaVdXTr4SiEG7wbOMab1R6SPU_lW0hmBYOKFU1l_8XU&usqp=CAU', 2, '0000-00-00', '0000-00-00'),
(9, 'Pulmoclox', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTksKDWrHMsDn_K9Rr6mm8NsdU2Bp_oNxcA2CCQghpdVROQvr1DmeGgpILMUiGRXqfBLqI&usqp=CAU', 2, '0000-00-00', '0000-00-00'),
(10, 'Pulmocodin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRD0uF2DHIioQKxfoGowlCYYv5HZf2vQSju5pJ2vm7f96ZrHwU0R4k5L3IJvNzB7HzmW2M&usqp=CAU', 2, '0000-00-00', '0000-00-00'),
(11, 'Polycort', 'https://ibnhayyanpharma.com/wp-content/uploads/2019/09/POLYCORT_ophtEn.jpg', 2, '0000-00-00', '0000-00-00'),
(12, 'Mefen', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReND9ZyDaT1DEPHNRVMz31mW90bxQRV3YFhmm5q2JAagANHT2XsehlMNy_ut7s35-kH5A&usqp=CAU', 2, '0000-00-00', '0000-00-00'),
(13, 'Bebe- calme', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQuEf-bWJ59uKifjIFbFgLsfh2zvZ2m7FlDG3OhLipvm6JKFD2oqhJxFod6Hpx0ko3CZL4&usqp=CAU', 3, '0000-00-00', '0000-00-00'),
(14, 'Baby tal', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZcZR7SXbKA2K1nZbYC_9OsDYZ0z82AUO440jf1SJKVcGaG54RyG1WBZhga2BGmI2a6oM&usqp=CAU', 3, '0000-00-00', '0000-00-00'),
(15, 'Beta-salic', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRya3mxfWx9fhk4DjuV8B925i6D7kG4lh4VsiCcAnFN3-n31g4860-SIYt7FZ5JoG-vBZ0&usqp=CAU', 3, '0000-00-00', '0000-00-00'),
(16, 'Bitasirc', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgrXiu2dkhTulY31QZZ3a3x9Pa6qJhBq6OauqUBCif6G8-wa5xP8rC-URICOGLt4l2hEs&usqp=CAU', 3, '0000-00-00', '0000-00-00'),
(17, 'Pain stop\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT0_B4vUNC3UlOxLZH5gvaGXpIqS_M9HUOiD8ts61VEyX6skEgzgXjsklxgnz_mK-_1tZk&usqp=CAU', 3, '0000-00-00', '0000-00-00'),
(18, 'Tussilet', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeqOIl7w65J13DiP4pMdB_BeUqA9XaTNxwjM8CcnygyWx_H5lVXUJI9W2uu316wBMyHQM&usqp=CAU', 4, '0000-00-00', '0000-00-00'),
(19, 'Toxil', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQDomcVjeHlI0QyQ3VumSPdWDZqntNzgw0KmSznNu1qWS_WmWXIeMuDtgaDvkdv_I1350&usqp=CAU', 4, '0000-00-00', '0000-00-00'),
(20, 'Tullin-D\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYAVK0VHJ047gy6eKDsr_YGCn7l7kIhA-OkBeN7QOCuB6MsgSo3ALlKc0HNem5ENRMnZk&usqp=CAU', 4, '0000-00-00', '0000-00-00'),
(21, 'Tearmond\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIY2Bu9sfqe8KM0k1lzA8sFIGzXvznpstlNGo75sNAQCo3ZRpGTw77DXJ_2SMQ9TXnyFo&usqp=CAU', 4, '0000-00-00', '0000-00-00'),
(22, 'Timodor', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTHU6HHZlKmhqLj1oXoP_E9E30zDxCfWUgMwHmqpPFWtAyZi8CKfkuBYZ6YATM4mMgzRhk&usqp=CAU', 4, '0000-00-00', '0000-00-00'),
(23, 'Dazel kit\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlB4LhGJzYr2SZizHWiytQiGYYmDM6T01r2APC4r5KL_TGv_VqvoA-6-x8v9H9E6-ASXQ&usqp=CAU', 5, '0000-00-00', '0000-00-00'),
(24, 'Dilozole', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQBJ3njSOcivQzxVwMLZsZM4lO5Ng9ZJadQa0J_xlLLOWiqnShQn-egDN4Pj_-nS2tRLk&usqp=CAU', 5, '0000-00-00', '0000-00-00'),
(25, 'Dermocal\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRyj3IhN-dA-lyuU1022Y0yQ78qThRSg1n_Zb0vHaEI6u9R69ST20aeQmIaysXwY0C3kXY&usqp=CAU', 5, '0000-00-00', '0000-00-00'),
(26, 'Duspatalin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQP30qZWj29ZrbVCqlXXOtOlLCvUO3OkZ1bLcPqVMQcHNMg6sa-hF-_N6_O_ZBWy5mdGqw&usqp=CAU', 5, '0000-00-00', '0000-00-00'),
(27, 'Riabal', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVy_0wZF-CGTZtxIcM_YY7Tr85KjiNpJdSwYYQ8cRPEyz5Mz72t9YnlN95knClZfHkYDc&usqp=CAU', 5, '0000-00-00', '0000-00-00'),
(28, 'Dospin-A', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzOcAZwjXg4Zl4LVlVS_GvWo3Xcl-IAhpOUJ2B21mjgbQwFZfarkiVpEn8IawT2NyNFNA&usqp=CAU', 6, '0000-00-00', '0000-00-00'),
(29, 'Dolozox', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS3BprITzSjUl4qCJArhFeWbq_PqOa7bu23HAI3Ahs8bnS54JZL0E7RF_4bakAiazLms8o&usqp=CAU', 6, '0000-00-00', '0000-00-00'),
(30, 'Rowatinex', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTrGsGk5WGns_fIWH9mUYYwk2loSj7JGQEtQju8h059LI3UrA04wDvAooeEEPVCs8dgMgs&usqp=CAU', 6, '0000-00-00', '0000-00-00'),
(77, 'Menu1', 'http://192.168.137.1:8000/storage/Pharmacy/Menu/181976-16dad197-7cc7-454c-977d-c0c01f111472.jpg', 18, '2022-08-12', '2022-08-12'),
(78, 'm', 'http://192.168.137.1:8000/storage/Pharmacy/Menu/0712357-856efaf6-ce5a-44cd-b447-b8612d55fa89.jpg', 1, '2022-08-15', '2022-08-15');

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `notifications`
--

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL,
  `userEmail` text NOT NULL,
  `to` text NOT NULL,
  `data` text NOT NULL,
  `orderName` text NOT NULL,
  `orderQty` int(11) NOT NULL,
  `orderPrice` float NOT NULL,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL,
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notifications`
--

INSERT INTO `notifications` (`id`, `userEmail`, `to`, `data`, `orderName`, `orderQty`, `orderPrice`, `updated_at`, `created_at`, `img`) VALUES
(55, 'ali1assalem@gmail.com', '1', 'You have new order from email ali1assalem@gmail.com', 'Naphazoline nitrate', 2, 5.8, '2022-08-12', '2022-08-12', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTaHR0NBZmID8lQICaJlhmAT_afNKp_NaacA&usqp=CAU'),
(56, 'ali1assalem@gmail.com', '18', 'You have new order from email ali1assalem@gmail.com', 'p1', 1, 1, '2022-08-12', '2022-08-12', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1830415-5133c67e-4536-4baf-9521-f66d912ca8a7.jpg'),
(57, 'ali1assalem@gmail.com', '18', 'You have new order from email ali1assalem@gmail.com', 'p1', 2, 2, '2022-08-12', '2022-08-12', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1830415-5133c67e-4536-4baf-9521-f66d912ca8a7.jpg'),
(58, 'ali1assalem@gmail.com', '1', 'You have new order from email ali1assalem@gmail.com', 'Ketorolac tromethamine', 2, 4, '2022-08-14', '2022-08-14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRaXQMoGxPvxW42HOdDBHajfKC-iPj8yPU1BKmc588alI0Y-TGmtHbie8akMKIuuGLQw0k&usqp=CAU'),
(59, 'admin@gmail.com', 'ali1assalem@gmail.com', 'Your order has been update to Shipping from store 1', 'Ketorolac tromethamine', 2, 4, '2022-08-14', '2022-08-14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRaXQMoGxPvxW42HOdDBHajfKC-iPj8yPU1BKmc588alI0Y-TGmtHbie8akMKIuuGLQw0k&usqp=CAU'),
(60, 'ahmed@gmail.com', '1', 'You have new order from email ahmed@gmail.com', 'Antazoline sulphate', 2, 6, '2022-08-14', '2022-08-14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8bsw87VIKPFqg0DDt_OtYy7HA0RLr3EE8lQ&usqp=CAU'),
(61, 'ali1assalem@gmail.com', '3', 'You have new order from email ali1assalem@gmail.com', 'Phenobarbital', 3, 10.5, '2022-08-14', '2022-08-14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgdqTMOzT4SE5YPVB413xeAnpN37O-pDufUnvYZM3kjDQKnkXSpJkn9G96ZuE4Yi_Za8c&usqp=CAU'),
(62, 'admin3@gmail.com', 'ali1assalem@gmail.com', 'Your order has been update to Shipping from store 3', 'Phenobarbital', 3, 10.5, '2022-08-14', '2022-08-14', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgdqTMOzT4SE5YPVB413xeAnpN37O-pDufUnvYZM3kjDQKnkXSpJkn9G96ZuE4Yi_Za8c&usqp=CAU'),
(63, 'ali1assalem@gmail.com', '1', 'You have new order from email ali1assalem@gmail.com', 'Octoxynol', 8, 24, '2022-08-15', '2022-08-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxeNlPAm0eYxNS76hpXKmEWB3oIFTkqSE1vA&usqp=CAU'),
(64, 'admin@gmail.com', 'ali1assalem@gmail.com', 'Your order has been update to Shipped from store 1', 'Octoxynol', 8, 24, '2022-08-15', '2022-08-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxeNlPAm0eYxNS76hpXKmEWB3oIFTkqSE1vA&usqp=CAU'),
(65, 'ahmed@gmail.com', '1', 'You have new order from email ahmed@gmail.com', 'prod22', 2, 4, '2022-08-15', '2022-08-15', 'http://192.168.137.1:8000/storage/Pharmacy/Product/0737579-a9bca43f-7ece-4942-82cc-d5701808ae05.jpg'),
(66, 'admin@gmail.com', 'ahmed@gmail.com', 'Your order has been update to Processing from store 1', 'prod22', 2, 4, '2022-08-15', '2022-08-15', 'http://192.168.137.1:8000/storage/Pharmacy/Product/0737579-a9bca43f-7ece-4942-82cc-d5701808ae05.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `orderId` bigint(20) NOT NULL,
  `orderStatus` tinyint(4) NOT NULL,
  `orderDetail` text NOT NULL,
  `orderPrice` float NOT NULL,
  `orderComment` text NOT NULL,
  `orderAddress` text NOT NULL,
  `userEmail` text NOT NULL,
  `paymentMethod` text NOT NULL,
  `storeId` int(11) NOT NULL,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`orderId`, `orderStatus`, `orderDetail`, `orderPrice`, `orderComment`, `orderAddress`, `userEmail`, `paymentMethod`, `storeId`, `updated_at`, `created_at`) VALUES
(26, 0, '{\"amount\":2,\"company\":\"store 1\",\"companyId\":1,\"id\":\"11\",\"link\":\"https://encrypted-tbn0.gstatic.com/images?q\\u003dtbn:ANd9GcTTaHR0NBZmID8lQICaJlhmAT_afNKp_NaacA\\u0026usqp\\u003dCAU\",\"name\":\"Naphazoline nitrate\",\"price\":5.8}', 5.8, 'hi iam ali.', 'homs syria', 'ali1assalem@gmail.com', 'COD', 1, '2022-08-12', '2022-08-12'),
(27, -1, '{\"amount\":1,\"company\":\"Store8\",\"companyId\":18,\"id\":\"120\",\"link\":\"http://192.168.137.1:8000/storage/Pharmacy/Product/1830415-5133c67e-4536-4baf-9521-f66d912ca8a7.jpg\",\"name\":\"p1\",\"price\":1.0}', 1, 'd', 'e', 'ali1assalem@gmail.com', 'COD', 18, '2022-08-12', '2022-08-12'),
(28, -1, '{\"amount\":2,\"company\":\"Store8\",\"companyId\":18,\"id\":\"120\",\"link\":\"http://192.168.137.1:8000/storage/Pharmacy/Product/1830415-5133c67e-4536-4baf-9521-f66d912ca8a7.jpg\",\"name\":\"p1\",\"price\":2.0}', 2, 'y', 'y', 'ali1assalem@gmail.com', 'COD', 18, '2022-08-12', '2022-08-12'),
(29, 2, '{\"amount\":2,\"company\":\"store 1\",\"companyId\":1,\"id\":\"1\",\"link\":\"https://encrypted-tbn0.gstatic.com/images?q\\u003dtbn:ANd9GcRaXQMoGxPvxW42HOdDBHajfKC-iPj8yPU1BKmc588alI0Y-TGmtHbie8akMKIuuGLQw0k\\u0026usqp\\u003dCAU\",\"name\":\"Ketorolac tromethamine\",\"price\":4.0}', 4, 'yy', 'hu', 'ali1assalem@gmail.com', 'COD', 1, '2022-08-14', '2022-08-14'),
(30, 0, '{\"amount\":2,\"company\":\"store 1\",\"companyId\":1,\"id\":\"10\",\"link\":\"https://encrypted-tbn0.gstatic.com/images?q\\u003dtbn:ANd9GcQ8bsw87VIKPFqg0DDt_OtYy7HA0RLr3EE8lQ\\u0026usqp\\u003dCAU\",\"name\":\"Antazoline sulphate\",\"price\":6.0}', 6, 'r', 's', 'ahmed@gmail.com', 'COD', 1, '2022-08-14', '2022-08-14'),
(31, 2, '{\"amount\":3,\"company\":\"store 3\",\"companyId\":3,\"id\":\"43\",\"link\":\"https://encrypted-tbn0.gstatic.com/images?q\\u003dtbn:ANd9GcRgdqTMOzT4SE5YPVB413xeAnpN37O-pDufUnvYZM3kjDQKnkXSpJkn9G96ZuE4Yi_Za8c\\u0026usqp\\u003dCAU\",\"name\":\"Phenobarbital\",\"price\":10.5}', 10.5, 't', 'g', 'ali1assalem@gmail.com', 'COD', 3, '2022-08-14', '2022-08-14'),
(32, 3, '{\"amount\":8,\"company\":\"store 1\",\"companyId\":1,\"id\":\"4\",\"link\":\"https://encrypted-tbn0.gstatic.com/images?q\\u003dtbn:ANd9GcTxeNlPAm0eYxNS76hpXKmEWB3oIFTkqSE1vA\\u0026usqp\\u003dCAU\",\"name\":\"Octoxynol\",\"price\":24.0}', 24, 'quicly', 'homs syria', 'ali1assalem@gmail.com', 'COD', 1, '2022-08-15', '2022-08-15'),
(33, 1, '{\"amount\":2,\"company\":\"store 1\",\"companyId\":1,\"id\":\"121\",\"link\":\"http://192.168.137.1:8000/storage/Pharmacy/Product/0737579-a9bca43f-7ece-4942-82cc-d5701808ae05.jpg\",\"name\":\"prod22\",\"price\":4.0}', 4, 'nowww', 'ggyy', 'ahmed@gmail.com', 'COD', 1, '2022-08-15', '2022-08-15');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `id` int(20) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `img` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` double(8,2) NOT NULL,
  `menu_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `updated_at` date NOT NULL,
  `created_at` date NOT NULL,
  `rate` float DEFAULT NULL,
  `qty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`id`, `name`, `img`, `price`, `menu_id`, `company_id`, `updated_at`, `created_at`, `rate`, `qty`) VALUES
(1, 'Ketorolac tromethamine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRaXQMoGxPvxW42HOdDBHajfKC-iPj8yPU1BKmc588alI0Y-TGmtHbie8akMKIuuGLQw0k&usqp=CAU', 2.00, 1, 1, '2022-08-14', '0000-00-00', 1.25, 184),
(2, 'Benzalkonium chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBs8lFaRgL6C4xEb0lQ6uXbANMDuxKt0GReA&usqp=CAU', 2.50, 1, 1, '2022-08-11', '0000-00-00', 0.5, 228),
(3, 'Edetate disodium', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQbgJaHoT4HLELjrWqavXEGkIPAnFAsF5le2Q&usqp=CAU', 2.50, 1, 1, '2022-06-15', '0000-00-00', 3.75, 170),
(4, 'Octoxynol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxeNlPAm0eYxNS76hpXKmEWB3oIFTkqSE1vA&usqp=CAU', 3.00, 1, 1, '2022-08-15', '0000-00-00', 0.5, 278),
(5, 'Sodium chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS290InZE_yMQIYAWCYl_ChtzQNhrOQ1iGa2A&usqp=CAU', 4.00, 1, 1, '2022-08-15', '0000-00-00', 2.25, 496),
(6, 'Xylometazoline HCL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDw0JAxwQxqFgS0ZsLNlgB33FSosdAPhaUNg&usqp=CAU', 2.70, 2, 1, '2022-08-11', '0000-00-00', 4.5, 219),
(7, 'Sodium phosphate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRN-PpL7tt6uUropWp5CPtThpYgchUDsx14Aw&usqp=CAU', 3.00, 2, 1, '2022-03-20', '0000-00-00', 0.5, 133),
(8, 'Sodium chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0_4R498H2Pmr-0j48EQ7hznx7aXZHVw5MnQ&usqp=CAU', 2.40, 2, 1, '2022-04-18', '0000-00-00', 2.5, 233),
(9, 'Methylhydroxypropylcellulos', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScXWa6JkTjkDSCIl3OCyDIF57cIvRGZElkM7dbXUtevnMs7MISPhh2FxH_vLLxfd6h_rs&usqp=CAU', 2.30, 2, 1, '2022-05-01', '0000-00-00', 3.5, 539),
(10, 'Antazoline sulphate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8bsw87VIKPFqg0DDt_OtYy7HA0RLr3EE8lQ&usqp=CAU', 3.00, 3, 1, '2022-08-14', '0000-00-00', NULL, 121),
(11, 'Naphazoline nitrate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTaHR0NBZmID8lQICaJlhmAT_afNKp_NaacA&usqp=CAU', 2.90, 3, 1, '2022-08-12', '0000-00-00', 1, 540),
(12, 'Benzothonum chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqlULkI4bXneyeWj8kwIwD1hPhhIVln-D9Xw&usqp=CAU', 2.40, 3, 1, '0000-00-00', '0000-00-00', NULL, 432),
(13, 'Diphenhydramine HCL', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStKOIQ4bR6bAEVxwRrNF2FoUu88oI9J_yM7A&usqp=CAU', 2.50, 4, 1, '2022-04-18', '0000-00-00', NULL, 537),
(14, 'Ammonium Chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpnOmEIBLWVEBLshnHWm-BGN9TLJ2mOoEPOg&usqp=CAU', 3.00, 4, 1, '0000-00-00', '0000-00-00', NULL, 234),
(15, 'Dexamethasone', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqjNNmrEMDoazzGUw4S5DdhqSNeo2TP8rNNw&usqp=CAU', 2.30, 5, 1, '0000-00-00', '0000-00-00', NULL, 543),
(16, 'Neomycin sulfate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ1lrW3NnQXA4LYF1ccLFHCqhYHnXPRYmnG8w&usqp=CAU', 4.00, 5, 1, '0000-00-00', '0000-00-00', NULL, 876),
(17, 'Polymyxin B sulfate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0spZXXPJCd1j0xri0q6rva5l3NWubW2uG0Q&usqp=CAU', 3.00, 5, 1, '0000-00-00', '0000-00-00', NULL, 543),
(18, 'Enalapril', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcReXDSlZu82xG9zkRezO7XHgF_-AP6_hOMZrw&usqp=CAU', 2.40, 6, 1, '2022-08-10', '0000-00-00', NULL, 233),
(19, 'Enalapril Krka', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-ZpbyHGPXfRNFrNbAwYFZ5Gp69MJjj9qCIg&usqp=CAU', 3.80, 6, 1, '0000-00-00', '0000-00-00', NULL, 543),
(20, 'Lobelia Tinct', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSTgN5DS1IQJG-rF5n4rEEzQ7F6RwwrbzA7xSYJDyHXDhEWi4pYd28KPjg-NGgGtPYXQiI&usqp=CAU', 0.00, 7, 2, '0000-00-00', '0000-00-00', NULL, 234),
(21, 'Scilla Tinct', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpmjxV81z3X_mRiOWyuQd7TKpHX25obonta9sJwNZTF2SPn98z82ALyZg6POlxIBFmtrc&usqp=CAU', 2.40, 7, 2, '0000-00-00', '0000-00-00', NULL, 543),
(22, 'Liquorice Ext', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTO_6SYiPpzsU3lEDCJETXb79W8Loa0WeynZh6g9_zmKYXpTSeSmTCaFy2TqAiNk8vx-W0&usqp=CAU', 2.60, 7, 2, '0000-00-00', '0000-00-00', NULL, 123),
(23, 'Stramonium Tinct', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYfp507G9Ok_nXpeAwLdLVam9bDclwEGvj-jhJhS0z4ZHZnK04_3GkJtNn848DPZm-oBI&usqp=CAU', 3.40, 7, 2, '0000-00-00', '0000-00-00', NULL, 543),
(24, 'Dextromethorphan HBr', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_o-SYTmy_DAg_bzYtc79YowRFkLN__uZDg7fu326f4h0WlCBNyXJWqPzB0UNWneucWBg&usqp=CAU', 5.00, 8, 2, '0000-00-00', '0000-00-00', NULL, 589),
(25, 'Guaifenesin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIAU65xwVItUasNAN35AFPDFa2TDGu6mFOnky6M7nIX73XXCavXkCBN77iVL5dcmLTJH0&usqp=CAU', 3.50, 8, 2, '0000-00-00', '0000-00-00', NULL, 234),
(26, 'Mepyramine maleate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQzleSKE6gyJW8lSmLhVXU5wBxO_YkHoQ-3EP9zHV_TtIdxjzKHguTMbL7pTjUCQwzT48k&usqp=CAU', 2.70, 8, 2, '0000-00-00', '0000-00-00', NULL, 56),
(27, 'Amoxicillin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ7NCVH3Giksw4jBRFkUXk1QhyfsmB1JZ_RLoztMTAIhTQna1mPgt6LRN9Gz-wUG4myG1Q&usqp=CAU', 2.80, 9, 2, '0000-00-00', '0000-00-00', NULL, 543),
(28, 'Cloxacillin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQtFW9SYdwKUblF9TL86EU0vaJtjMj1BS0bJ_7c95oUedKHr_SySQMZpXDYAsupOzsiY0&usqp=CAU', 6.00, 9, 2, '0000-00-00', '0000-00-00', NULL, 655),
(29, 'Glyceryl Guaiacolate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShTLO8FxIrYAKvE7SrGkjXqU8SHAifCjxJFZFouYReH7MjXi6oI0kTY96JeGu4wde8xT0&usqp=CAU', 3.40, 10, 2, '0000-00-00', '0000-00-00', NULL, 654),
(30, 'Chlorpheniramine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR8CdOpbBKjru4QVpianvhPAxxnfSrmqwGXHCVWDMbECv1nD9HaX3b3QYYqWjS0ORvAmRI&usqp=CAU', 2.30, 10, 2, '0000-00-00', '0000-00-00', NULL, 123),
(31, 'Phenylephrine ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-gJh7mCcjFHIBtXbt_6cEy8KmwhYSDUxZN7XY_vgLI44itxGTzwBatg_cXmX8n8g93jA&usqp=CAU', 4.00, 10, 2, '0000-00-00', '0000-00-00', NULL, 129),
(32, 'Codeine phosphate ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCh5qGIp5dbmtIAbOPX7jfY2z-icuw0adg3AjyUAvgBlTFcyCGU6RR8fjjrjRR-rxafsY&usqp=CAU', 3.60, 10, 2, '0000-00-00', '0000-00-00', NULL, 654),
(33, 'Vitamin C', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgNyI-ix7yQFSck6j3bFikNwgDpvL-sMOwtxMa-7_OpPB1zqgmi9Qgx3foNC6WPDdkXKw&usqp=CAU', 2.50, 10, 2, '0000-00-00', '0000-00-00', NULL, 653),
(34, 'Dexochlorphenaramine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRB8yEERrWqowZKww3u2q_zuo55UXgNMmeevD7MZX_8i4EHQNWlZjU7xRDBC0quy_79Kyg&usqp=CAU', 4.00, 10, 2, '0000-00-00', '0000-00-00', NULL, 345),
(35, 'Oxytetracycline', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRC6ImuVvk-v448WKy3IZNWK5JGyUay6A9jkFO6DX1OlEBbjDxQGEQjcvLIhVPhQK11mLo&usqp=CAU', 2.50, 11, 2, '0000-00-00', '0000-00-00', NULL, 866),
(36, 'Hydrocortisone acetate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCRUJybw1ZydjEMPh_0jMGsQROIuo4WZVHBikndeUtXG3UgKkJMUQV4ctJrlENRSmI1-8&usqp=CAU', 3.50, 11, 2, '0000-00-00', '0000-00-00', NULL, 654),
(37, 'Polymixin-b-sulphate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4M4o_xinlChWDq6krS0_NWBP4JxOdM9aFxuS3gkO3WhlPbub_NhPfnLMNWbaCTBP1BnE&usqp=CAU', 3.20, 11, 2, '0000-00-00', '0000-00-00', NULL, 744),
(38, 'Mefenamic acid', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQvrtU8gnIkzKt1PK9z9JUiMWtud770HFRen0J_tBhLzGZV3s77mRUYAWC5BD7Mg5ieiPs&usqp=CAU', 3.00, 12, 2, '0000-00-00', '0000-00-00', NULL, 432),
(39, 'Diphenhydramine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQM9zwapQ81MPGAUVWxUsrfPuONH2BenTCBRiv1vjKQIh-enJ07Kdt_RRJBnld3ql4RYY0&usqp=CAU', 2.40, 13, 3, '0000-00-00', '0000-00-00', NULL, 346),
(40, 'Belladonna Tincture', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBq-a-x3WS9-RZcxFKpt9cmWzgXd82RHqfnPyxqMq-_Fa6ssSdflHw2_QHCf6BpoSSRC4&usqp=CAU', 2.30, 13, 3, '0000-00-00', '0000-00-00', NULL, 654),
(41, 'Cardamon Tincture', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSl3d8zaTgxFgUj2OTZH8sutBI-5_DtY-FkAUgRdKIEIPuFEpy4Eeib2Dkp7hp2gJKJ0ew&usqp=CAU', 2.50, 13, 3, '0000-00-00', '0000-00-00', NULL, 765),
(42, 'Pipenzolate methylbromide', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAIqPD3fLXizEl7iXTaoQfcZtQVTXGTN3Y2ZGzQRQcdg6MowYh9HcnQNV6CyTDN2YyASE&usqp=CAU', 3.00, 14, 3, '0000-00-00', '0000-00-00', NULL, 456),
(43, 'Phenobarbital', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgdqTMOzT4SE5YPVB413xeAnpN37O-pDufUnvYZM3kjDQKnkXSpJkn9G96ZuE4Yi_Za8c&usqp=CAU', 3.50, 14, 3, '2022-08-14', '0000-00-00', NULL, 453),
(44, 'Simethicone', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrX4V2NifalPgIL7xtcmX7jyFhhXhZFjDLMQL0iR0wCto5MqdT2wA_0RKAlVlsjOxVN6g&usqp=CAU', 2.50, 14, 3, '0000-00-00', '0000-00-00', NULL, 456),
(45, '', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgSnuYHNndRmg9s0onaj7Ksv3Q9TUxViEkORFNWLqgo1Q_JSajCh6SlmdYLymt6vMx4kE&usqp=CAU', 4.00, 15, 3, '0000-00-00', '0000-00-00', NULL, 456),
(46, 'Salicylic acid', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSv3pUIWPE8kvfZA33lKetAp4a8JN_2ofE3qhSJ0b8Of1BKEgmi_Oa81MaUBsYmqhRpKUE&usqp=CAU', 3.50, 15, 3, '0000-00-00', '0000-00-00', NULL, 769),
(47, 'Betahistine dichlorhydrate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW8acF0JMNNcNjgY7JxXnTB85O-Lmkdi3G3PrQrcnAoQIOA9PX5BJVEZvo8sesElIRzWQ&usqp=CAU', 4.50, 16, 3, '0000-00-00', '0000-00-00', NULL, 659),
(48, 'Pizotifen', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRdcAFjSSRaN0ggrlJsRx7-yma9uHwMTCT1xbuKv6XvOx5Oxmixop7Js1auXBk8hxh8lrk&usqp=CAU', 3.50, 16, 3, '0000-00-00', '0000-00-00', NULL, 765),
(49, 'Bisoprolol\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQHWYvE50WjXwQ9w5w1h1cRghC3PB99HtoeNx4TvZDL4z-ZA-9WQaF5_LYFK-1GNckWoYg&usqp=CAU', 2.50, 16, 3, '0000-00-00', '0000-00-00', NULL, 789),
(50, 'Dextropropoxyphen\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5_rPc1S5X8yb_lv-VbmL9ghw2jtc0rk_Zt59Ha-I2aEhdk3bOCgOht1ml8lbu8tmou8c&usqp=CAU', 2.20, 17, 3, '0000-00-00', '0000-00-00', NULL, 789),
(51, 'Paracetamol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0YUOdSoP43gXrEsDURIku92kH_l6B508MXKkgjsnQuQ6xvyMYp5T6mP4x1KozLEh1oQ&usqp=CAU', 2.00, 17, 3, '0000-00-00', '0000-00-00', NULL, 987),
(52, 'Panadol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwiPyi0P2xlDEMNC9Sc_sDf837CyH8_7RRIl5iEafO5pmTR-HHFGxhywSQ1ed2-BWdSW0&usqp=CAU', 3.00, 17, 3, '0000-00-00', '0000-00-00', NULL, 876),
(53, 'Chlorphenarmine maleat', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTKVy3htsK5LnVIawYfJMbFmg4e_vD6dBhoPjFYY1tNCFpfdZh-obQX0rqmUtne2pLLG8&usqp=CAU', 2.50, 18, 4, '0000-00-00', '0000-00-00', NULL, 789),
(54, 'Phenylephrine HCL\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcToUcbAMomr2nHWnHg0t7jzET6Jev3lwFOsioz5Go_dCs6X-FE2bY0ddmebJGfD2JVFQFA&usqp=CAU', 3.50, 18, 4, '0000-00-00', '0000-00-00', NULL, 345),
(55, 'Dextromethorphan HBr', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTVGS9-aevNThdfBSKy0IJqWWUSKUC9rb9-BpbuhNYXaOnZFU6IpDZJspcaSFgxNBufGtM&usqp=CAU', 3.40, 19, 4, '0000-00-00', '0000-00-00', NULL, 976),
(56, 'Oxomemazine HCl', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT740KupFfjgkBIZm1h792HYEBMKZDBgEQBiQ4vhy_0BpOJSpYlWVmMuMLJZxqy-htA6BE&usqp=CAU', 3.50, 19, 4, '0000-00-00', '0000-00-00', NULL, 433),
(57, 'Sodium Benzoate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGKhjD01aeP3nesJuyzY12qq4Ga_2xNjkxDH-KD31yGn9nHOsLdzQKZ_OAS0in-nTl_to&usqp=CAU', 2.20, 19, 4, '0000-00-00', '0000-00-00', NULL, 567),
(58, 'Diltiazem', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsQlwp6TGVhyoLC63xhms49BEkfvXBSikvPfMKYQNXwZMJ2Q7oRSNIxCNlrVujgTqgbq0&usqp=CAU', 2.30, 19, 4, '0000-00-00', '0000-00-00', NULL, 943),
(59, 'Paracetamol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0YUOdSoP43gXrEsDURIku92kH_l6B508MXKkgjsnQuQ6xvyMYp5T6mP4x1KozLEh1oQ&usqp=CAU', 2.50, 20, 4, '0000-00-00', '0000-00-00', NULL, 344),
(60, 'Chlorpheniramine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRjsWMw395Cb9gOelB5A0HtsGq0_jRMWF-nzIPZYIbCRZdvC9gydSm42JonhVEItEc1egk&usqp=CAU', 2.30, 20, 4, '0000-00-00', '0000-00-00', NULL, 444),
(61, 'Dextromethorphan HBr', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTchlT-21urUYF18h4F1SM9aHbJRSfq_rTePOmqu2lXWbTzVRH3eQKwUR0w9L5_sFcSCac&usqp=CAU', 2.60, 20, 4, '0000-00-00', '0000-00-00', NULL, 349),
(62, 'Pseudo-Ephedrine HCl\r\n', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR3AteLwOVvUetl5bERVStli9SWhaU4CcVY3QH--Y77ge8qU2qGbJJjLhsjJDCPOmwHK4w&usqp=CAU', 4.00, 20, 4, '0000-00-00', '0000-00-00', NULL, 765),
(63, 'Glycerin ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwQM_DfduIC6R64bjydMwSmyt4TrgJ3UidVfr0jiMiIUnHlOw56Hhk-aKGn2qDK0l0Wb0&usqp=CAU', 4.20, 21, 4, '2022-04-02', '0000-00-00', 1.25, 234),
(64, 'Hypromellose ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSkdY1on7hewy1e1y58aB-u3tmGALTrXQyucnluYnTxH40IVlcTYcMFwI3qvSTljqkUtss&usqp=CAU', 3.50, 21, 4, '0000-00-00', '0000-00-00', NULL, 345),
(65, 'Polyethylene glycol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRZwJLehTsiEfsEp2hvcuhs48Hno3MMb-LigUX-efRjnVOfAnj1I4atVsXdRS1JIzBmXsU&usqp=CAU', 2.50, 21, 4, '0000-00-00', '0000-00-00', NULL, 234),
(66, 'Dorzolamide hydrochloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREsS-ea2CvQG_OUEiWG_Ub1vFPIbCQwePdNssU0RWmO-JVkiD6Z85ojwxSLaJO271DJMY&usqp=CAU', 3.20, 22, 4, '0000-00-00', '0000-00-00', NULL, 236),
(67, 'Timolol maleate', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNWZRjgxUjNuMcsP2UpjWo2YSVrAZBfpSgM-6lPg_dl0MFNdxKU8c3CJ6vhhgVVJKFabc&usqp=CAU', 3.50, 22, 4, '0000-00-00', '0000-00-00', NULL, 765),
(68, 'Atenolol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAQiRuidr-uh0_6NYtjX_E6kj5q_xFzAMjjKFWHXZvrtNsw2RD4bl-CtTlRoLoKqpw5XI&usqp=CAU', 3.00, 22, 4, '0000-00-00', '0000-00-00', NULL, 833),
(69, 'Fluconazole', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUFsDU8koGva5KgLRRXxgJPAIbiBqX4ve2_UAhJqS_aspU_Ua-Kwca6vbI5PTxQk8raDs&usqp=CAU', 2.50, 23, 5, '0000-00-00', '0000-00-00', NULL, 456),
(70, '', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIDn19oax6XxjbJmmZhQ0dgvmJfd6qyHg7NkE3kspZQ8_zidn9xOKvkFrE6_VBiyStKbw&usqp=CAU', 2.30, 23, 5, '0000-00-00', '0000-00-00', NULL, 345),
(71, 'Secnidazole', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQh_UP_8yQ-2AHcbdrZQYk9dxB6rofqqI3ZzUM0f_ivz7-g6OT8Ui9gL3-TCi9fysDymwE&usqp=CAU', 2.50, 23, 5, '0000-00-00', '0000-00-00', NULL, 654),
(72, 'Oxybutynin chloride', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTpSN_EfBUAEZBsoVpO0j-g6665Jy_F9s5sseeSU9WKZandry5648TRawsb7IKDR6WP1-o&usqp=CAU', 3.50, 23, 5, '0000-00-00', '0000-00-00', NULL, 787),
(73, 'Diloxanide furoate ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPdI4kkvrZBYYhreydmuftBjKxr8aZgx_OpvI_0ySJNCACOIuxtkK1lCPVs_Fg_wcRrQI&usqp=CAU', 2.50, 24, 5, '0000-00-00', '0000-00-00', NULL, 654),
(74, 'Metronidazole', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRlfSLCAYwH6tae26VMgKZn_yffforqggPXdDkphBgpKLBJyVOdqV6PeNALRMKUNpGpUVo&usqp=CAU', 2.30, 24, 5, '0000-00-00', '0000-00-00', NULL, 554),
(75, 'Benzoyl', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSjKxF5We2JOHwPKtE4DuaHyS7ikrs7DWiwa1JMjkegVwVWaXC21vsjhi6uSlkEp9N9ijQ&usqp=CAU', 3.80, 24, 5, '0000-00-00', '0000-00-00', NULL, 654),
(76, 'Calamine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAnP465hQh1tAJr2vd27q8DMWt1uh6X9gzMPo4UFh-i5vsWNnOFfbFfZpiq-vjX8v-cLs&usqp=CAU', 2.50, 25, 5, '0000-00-00', '0000-00-00', NULL, 245),
(77, 'Glycerine', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4bGUtaF1m6ji9UaTXVeZ2cbbcNT4iMuf4x0eeEhEUYz_9NrtXWCuy-CBDA9JX_r9NpVw&usqp=CAU', 3.50, 25, 5, '0000-00-00', '0000-00-00', NULL, 654),
(78, 'Camphor', 'https://cdn.shopify.com/s/files/1/0010/3746/7714/products/Schwabe-Camphora-Homeopathy-Dilution-6C-30C-200C-1M-10M-CM.jpg?v=1630400212', 4.00, 25, 5, '0000-00-00', '0000-00-00', NULL, 452),
(79, 'Dermodin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJ5aWjeLVreH93JpL--CGZyr9V0EpXynqJuoZAo1IJLUSTaHmTFbRPKFDiL-d6Qttgego&usqp=CAU', 4.20, 25, 5, '0000-00-00', '0000-00-00', NULL, 453),
(80, 'Mebeverin HCL ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJjkz7SYlb5duIl7YWcoVcveLrbKwnspOnIuKBhan8EVWJgr_P91rZwNf4i3lWWE5Nkfs&usqp=CAU', 4.70, 26, 5, '0000-00-00', '0000-00-00', NULL, 244),
(81, 'Spironolactone', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4zqT5PLzzKGys5rqGx_8R2vUK7QkD-ZLFXhaZs4szsKiMbq7Be-YCL4qoSghUfZNfknY&usqp=CAU', 4.50, 26, 5, '0000-00-00', '0000-00-00', NULL, 234),
(82, 'Prifinium bromide', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQgr_7tGNsTYS8Y-3gmPuZ3Qui_mpVpbCxPeJJmGqtKTxiORut02UTobvn3SzY4rxIflBw&usqp=CAU', 3.80, 27, 5, '0000-00-00', '0000-00-00', NULL, 456),
(83, 'Paracetamol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0YUOdSoP43gXrEsDURIku92kH_l6B508MXKkgjsnQuQ6xvyMYp5T6mP4x1KozLEh1oQ&usqp=CAU', 2.50, 27, 5, '0000-00-00', '0000-00-00', NULL, 653),
(84, 'Spiramycin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmVOKxO4vWKx887j7I4iGGWVgH5nCX9Y2oMgN5XTjFxDONR-ssSWdIKwSQ-SCx4NJWL3Q&usqp=CAU', 3.80, 27, 5, '0000-00-00', '0000-00-00', NULL, 345),
(85, 'Tretinion', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRc2HkzXE_AMW5aiOUTCCzOvGkKHQSSS3OdafgC3GSbxtu4jgiCC2a74qAV8trUtJU94qo&usqp=CAU', 3.00, 27, 5, '0000-00-00', '0000-00-00', NULL, 345),
(86, 'Rabeprazole', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSJ6o8vbycrwgNnr7a9V7BIlsvdWKj7I46RzvOIGsYHlMs9jtJxjoCk195p8fWYnwRO18I&usqp=CAU', 2.50, 27, 5, '0000-00-00', '0000-00-00', NULL, 234),
(87, 'Clopidogrel Bisulfate', 'https://5.imimg.com/data5/ER/NF/MY-6299000/clopidogrel-bisulphate-75-mg-aspirin-75-mg-tablets-500x500.jpg', 3.90, 28, 6, '0000-00-00', '0000-00-00', NULL, 765),
(88, 'Atrovastatin ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR_QRzQOUxRxBER07mRbpV6VkKOy_wRqSs21LP3UVv08ZWpbUShE1Yr9S1xiiz_TEm3vNk&usqp=CAU', 2.30, 28, 6, '0000-00-00', '0000-00-00', NULL, 765),
(89, 'Aspirin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdVadmab7-NuXKqGDW-idRwXeK2ClrNgi-3aD4vOBHOEXsF52sd8Bu1niUob0IMxsDki0&usqp=CAU', 3.30, 28, 6, '0000-00-00', '0000-00-00', NULL, 998),
(90, 'Cabergoline', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRMncmA3UAHktGT7tyaDdpf-b9fYdAvqHe5LFUkdP-hFWdRaEASN0y_Ikwrtmnk4oV9s80&usqp=CAU', 4.50, 28, 6, '0000-00-00', '0000-00-00', NULL, 654),
(91, 'Diclofenac ', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRaJdfbOBoUh__tYjLSsZf7z_O5fHnSU_aDMSuRcFmyu5qwz8akDMdQfnwDHSnYfh99HUw&usqp=CAU', 3.80, 29, 6, '0000-00-00', '0000-00-00', NULL, 543),
(92, 'Chlorzoxazone', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSIRv-qztNN0EMKZU4Vb5w8lI1_w5WtvM2M8f9ntVUx0EcXSNKjEeaiXrz4kjhUnEBJWLo&usqp=CAU', 1.50, 29, 6, '2022-05-23', '0000-00-00', 3, 532),
(93, 'Paracetamol', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0YUOdSoP43gXrEsDURIku92kH_l6B508MXKkgjsnQuQ6xvyMYp5T6mP4x1KozLEh1oQ&usqp=CAU', 2.50, 29, 6, '0000-00-00', '0000-00-00', NULL, 456),
(94, 'Pinene', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRV3WYgln_BGpbzwZu5dYta9sZM6bJ7pVGrykKUVLnXn8i1PfhFPP4ZwYi3dg85czyH9aY&usqp=CAU', 3.90, 30, 6, '0000-00-00', '0000-00-00', NULL, 765),
(95, 'Fhiral', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSlSghAuAerqJe6VJ8VWjP6n9cIREYUTpkds8ihm2fVk0lJvERzr7bCIZaiAkylxk69K48&usqp=CAU', 3.50, 30, 6, '0000-00-00', '0000-00-00', NULL, 654),
(96, 'Ceftriaxone', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR2n0r8I6u9DDQUDrcxVa9f4OOGKw1RZlAJFHhhEsm6-A6Hi7borrTRFme45O3k3KHGb9s&usqp=CAU', 4.60, 30, 6, '0000-00-00', '0000-00-00', NULL, 345),
(97, ' Betamethason', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZpKERxKZ3gXU-SpTsruiKaZgZBIMR1EDcEBMgj6HqZFc7chdRjvCZBFf8dhRH-KcX0vI&usqp=CAU', 2.40, 30, 6, '2022-06-15', '0000-00-00', 2.5, 765),
(98, 'Simvastatin', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSB3ylFHf9FeGtk06dNunbw7a7BurKY4zOI_3bxcRUnYaEMOK4QDJE7gc2KCO8LydgL6yY&usqp=CAU', 2.50, 30, 6, '0000-00-00', '0000-00-00', NULL, 543),
(111, 'product1', 'http://192.168.137.1:8000/storage/Pharmacy/Product/0713841-7b25c29e-a042-4953-9fba-ac36de5bbc97.jpg', 3.50, 64, 1, '2022-04-18', '2022-04-18', NULL, 1),
(112, 'product 1', 'http://192.168.137.1:8000/storage/Pharmacy/Product/2113215-10432660-62f0-4d40-a074-d40ac4577767.jpg', 35.00, 67, 10, '2022-05-06', '2022-05-06', NULL, 891),
(113, 'product2', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1850304-d4c08319-a142-4406-a42c-d8bfc55bb3e5.jpg', 6.00, 64, 1, '2022-06-15', '2022-06-15', NULL, 240),
(115, 'Cetamol new', 'http://192.168.137.1:8000/storage/Pharmacy/Product/104918-70ef8a77-5b1b-4506-8247-e101fec47d38.jpg', 4.00, 1, 1, '2022-08-10', '2022-08-10', NULL, 400),
(116, 'new product ^updated', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1130846-4cc81dc1-e115-4b3c-ae1a-62b4fccf5082.jpg', 9.00, 68, 1, '2022-08-10', '2022-08-10', NULL, 150),
(117, 'new product777', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1842374-134b2f9d-3f43-47fc-bc48-938b9e723e43.jpg', 258.00, 68, 1, '2022-08-11', '2022-08-11', NULL, 5233),
(120, 'p1', 'http://192.168.137.1:8000/storage/Pharmacy/Product/1830415-5133c67e-4536-4baf-9521-f66d912ca8a7.jpg', 1.00, 77, 18, '2022-08-12', '2022-08-12', NULL, 1),
(121, 'prod22', 'http://192.168.137.1:8000/storage/Pharmacy/Product/0737579-a9bca43f-7ece-4942-82cc-d5701808ae05.jpg', 2.00, 78, 1, '2022-08-15', '2022-08-15', NULL, 462);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  `image` mediumtext COLLATE utf8mb4_unicode_ci,
  `api_token` text COLLATE utf8mb4_unicode_ci,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `image`, `api_token`, `created_at`, `updated_at`) VALUES
(27, 'Admin Store1', 'admin@gmail.com', '$2y$10$vOGsVEVO1hmVMNsuADyVc.Gq8.hPLJMI2CfpFACPaFzdDXWkif2rW', 'users_profiles/2359486-admin@gmail.com.jpg', 'cR3X4v94RPyEYn3N2iCYir:APA91bHqIB6GUKLV9TX6G5fklVNY6wYOb6Tv-CAn7EWeP5XHRuNgSQ5CvfUpdWLcrohyi73o7FJAaSTzhkfmRNQ1rdPC6RDEtxq9BVplCrxvF7jSMdmIkpibHy9vcTIigDWVCYfzaUPI', '2022-08-10 20:16:20', '2022-08-15 04:55:17'),
(28, 'Ahmed Assalem', 'ahmed@gmail.com', '$2y$10$CJas0qGQmUuUgQ4fvPU9IeUBd/r.IUkVgJJ5/fmH9dG9tu5dAT.Ca', 'users_profiles/1523761-ahmed@gmail.com.png', 'fz6jlHZcR_aal3RlyNyWX5:APA91bGhLFYpdSj7ZoPxpETMpZPkiAFJ6XGnS1Cco5tXRvNXkD_TYnzafkahWmZDhWKUg0xmTfVAEAa8m7jwmlsor_pHVn2gYs9hyHuUO0gjbG32J9jBG3NSEW4tkj0RUo1hQyC2bRCy', '2022-08-11 11:09:20', '2022-08-15 04:55:53'),
(29, 'Hadi Assalem', 'Hadi@gmail.com', '$2y$10$I5Rg.Sk5XRUpwMIN1S.KeujRP5FNY3Dq/CSDPpjmKHQ62S3bvXRM6', 'users_profiles/155679-Hadi@gmail.com.jpg', 'eWRvA5OBTxOoG8cExWm8G6:APA91bHHWP1UfbIe8CV2SRAQft_iXQPeRPl8DR4T_Ow7aBuxaYInL-sS0Em4bR_1EjUKUjDHAom25_gKwHPJ2ogRFaMeCZMAbxdQZdpfK-B80xP0RmnuUjfxEXv_qUwWXm48VVmL6Qlj', '2022-08-11 11:11:02', '2022-08-14 13:22:44'),
(30, 'Ali Assalem', 'ali1assalem@gmail.com', '$2y$10$0BaL2vQEJtblzE4YW2ZQQ.xlTx9hSerA3pgs03kqneApRcaaekcOi', 'users_profiles/0732429-ali1assalem@gmail.com.png', 'da87GJUyTVycAAVfYltDui:APA91bGOJhMZt4UhF-CklEsyMWFHw61Lvyko21VukzX_MdOfNAT0Vy_fdHPUAK1t-UL6Q00T2zBNsJjjsG6wC9r1FFoAd1k_a5FTDz7JCO7c9uptBfn2z3XMxeMuuWpvJSRFQsY22mDP', '2022-08-11 11:25:31', '2022-08-15 04:52:46'),
(31, 'Admin Store 2', 'admin2@gmail.com', '$2y$10$XTJp9n7FIQaeth59UFMXM.esDzPz/a2DvVdQfJLvlgK8Ie2GOaXBW', NULL, 'ft1buMlKTiCTuv5dnOueuG:APA91bGn_FnzTvISeYjHqAjVhSF40XEUTw2Pv2ZtiANyGuGueRRbBbRltF9kwNZqYPUXBdgbSmfb8LJhtw9_SN3ZcLFWumuwVA0L4T84pYZIcjysBESmpn282kAmELXKyd1Zd0gR3Mj5', '2022-08-12 15:49:06', '2022-08-14 13:23:28'),
(32, 'Hadi Assalem', 'admin3@gmail.com', '$2y$10$cfDPfy7psjiTPSW3vFoJierlQWE0hHLt2DES9/HrsisW.LwmN8imO', NULL, 'cPDh16jxQ0KFskCu2kcjux:APA91bGuKobBlrUefW1j7j3VBGEmeSx6jBeYj3lrQIdu8t_RkuVgo_V38tHpyEC5_d5PFXpGLvIX4ITidDKRCxWpOUNC1EK1Ex5TVx681GDn4yT6l90u8AXrYoxY-v4uTsfe9fwmoY5H', '2022-08-14 13:07:40', '2022-08-14 13:22:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notifications`
--
ALTER TABLE `notifications`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`orderId`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `banner`
--
ALTER TABLE `banner`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=79;

--
-- AUTO_INCREMENT for table `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `notifications`
--
ALTER TABLE `notifications`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `orderId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=122;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
