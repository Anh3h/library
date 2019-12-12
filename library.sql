SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Create Database: `library`
--
CREATE DATABASE IF NOT EXISTS library;
USE library;

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE IF NOT EXISTS `book` (
  `id` varchar(255) NOT NULL,
  `author` varchar(255) NOT NULL,
  `available_qty` int(11) NOT NULL,
  `down_votes` int(11) NOT NULL,
  `edition` varchar(255) DEFAULT NULL,
  `isbn` varchar(255) NOT NULL,
  `num_of_borrows` int(11) NOT NULL,
  `publication_date` datetime DEFAULT NULL,
  `publisher` varchar(255) DEFAULT NULL,
  `shelf` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `total_qty` int(11) NOT NULL,
  `up_votes` int(11) NOT NULL,
  `topic_id` varchar(255) NOT NULL,
  `cover_image` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`id`, `author`, `available_qty`, `down_votes`, `edition`, `isbn`, `num_of_borrows`, `publication_date`, `publisher`, `shelf`, `title`, `total_qty`, `up_votes`, `topic_id`, `cover_image`) VALUES
('0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', 'K. N. King', 1, 0, '', '', 2, '2018-07-16 23:11:19', 'CreateSpace Independent', 'Level 1 B1', 'C Programming: A Modern Approach', 1, 1, '921ad0c0-f6bd-428e-ba38-6bb00384bec6', 'https://www.googleapis.com/download/storage/v1/b/e-library/o/41%2BbFKtFHjL-2018-07-31-112522018.jpg?generation=1533036323459983&alt=media'),
('185b6639-16d2-47d2-b09c-6a805c09b764', 'Dietel', 0, -1, '', '0133811905', 0, '2014-02-17 05:26:36', "O'RIEILLY", 'Level 1 B1', 'Java™ How To Program', 0, 1, '921ad0c0-f6bd-428e-ba38-6bb00384bec6', 'https://www.googleapis.com/download/storage/v1/b/e-library/o/java-HTP-2018-08-09-105002484.jpg?generation=1533811808828088&alt=media'),
('cc675a91-e392-4bf9-a0a6-4d1eef7134d2', '', 1, 0, '', '56780986789', 2, '2018-07-31 13:06:17', '', 'Level 2 B1', 'Digital marketing', 1, 0, '921ad0c0-f6bd-428e-ba38-6bb00384bec6', NULL),
('ba4df098-16a1-4448-805e-3c7379caa389', 'Ed Wright', 3, 0, 'Kindle Edition', '12345256', 0, NULL, '', 'Level 2 b3', 'Positive Thinking - The Key to Success', 3, 0, 'a85eea19-3012-480d-98a7-47221948d908', 'https://www.googleapis.com/download/storage/v1/b/e-library/o/positive-thinkx-2018-08-09-110157581.jpg?generation=1533812519398880&alt=media'),
('002bc556-fd02-4118-b919-578e322cf427', ' W. Chan Kim and Renée Mauborgne', 3, 0, '', '123453573', 0, '2017-09-26 01:00:00', '', 'Level 1 b3', 'Blue Ocean Shift: Beyond Competing', 3, 0, 'fdf995d5-8993-4db0-88ee-f5a5befc1fd2', 'https://www.googleapis.com/download/storage/v1/b/e-library/o/marketing-2018-08-09-121907157.jpg?generation=1533817150160278&alt=media');

-- --------------------------------------------------------

--
-- Table structure for table `comment`
--

CREATE TABLE IF NOT EXISTS `comment` (
  `id` varchar(255) NOT NULL,
  `text` varchar(255) NOT NULL,
  `book_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comment`
--

INSERT INTO `comment` (`id`, `text`, `book_id`, `user_id`) VALUES
('1668ca6e-a321-42e4-a45f-8c572805a2bf', 'Great book', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', '2939d03b-d94f-4442-8d31-c5ebd2fbc009'),
('5b026536-4dbd-444d-8ac0-a4cffc797a96', 'Expected more', 'cc675a91-e392-4bf9-a0a6-4d1eef7134d2', '533bf99b-5cd7-433a-9a3d-47be3ee5f55f'),
('6f0e7283-6cc2-4343-968f-7d38129ad450', 'Good book of beginners', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('82a2c347-ce37-48cf-87dd-670323826161', 'I especially liked the comments', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('33708291-8c65-4c12-91ca-141da2e60924', 'Same old... same old', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e');

-- --------------------------------------------------------

--
-- Table structure for table `favorite_books`
--

CREATE TABLE IF NOT EXISTS `favorite_books` (
  `user_id` varchar(255) NOT NULL,
  `book_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `favorite_books`
--

INSERT INTO `favorite_books` (`user_id`, `book_id`) VALUES
('2939d03b-d94f-4442-8d31-c5ebd2fbc009', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab'),
('2939d03b-d94f-4442-8d31-c5ebd2fbc009', '185b6639-16d2-47d2-b09c-6a805c09b764'),
('61991d73-b5b9-4e2e-9df9-5f3d96df2f4e', '185b6639-16d2-47d2-b09c-6a805c09b764');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `id` varchar(255) NOT NULL,
  `action` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `done` bit(1) NOT NULL,
  `book_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`id`, `action`, `content`, `created_at`, `done`, `book_id`, `user_id`) VALUES
('26644678-f818-40fc-8d27-659f9244d05e', 'Update book', 'There updates on your favorite book', '2018-08-01 21:12:46', b'0', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', '2939d03b-d94f-4442-8d31-c5ebd2fbc009'),
('1b9c5134-c02b-482d-8b1f-02f5c4c7222e', 'Update book', 'There updates on your favorite book', '2018-08-16 22:59:30', b'1', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('d9ff1366-3ff9-4748-92e7-b52d6279fb38', 'Update book', 'There updates on your favorite book', '2018-08-16 22:59:30', b'0', '185b6639-16d2-47d2-b09c-6a805c09b764', '2939d03b-d94f-4442-8d31-c5ebd2fbc009'),
('4463ee3d-8860-43c2-b2f9-359b1ea1c9cc', 'Update book', 'There updates on your favorite book', '2018-08-16 23:00:08', b'0', '185b6639-16d2-47d2-b09c-6a805c09b764', '2939d03b-d94f-4442-8d31-c5ebd2fbc009'),
('6073e896-5d0f-47a3-b7a2-60df820100d7', 'Update book', 'There updates on your favorite book', '2018-08-16 23:00:08', b'1', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('552690ec-168c-4053-baba-9023ccb0abea', 'New Comment', 'A new comment about your favorite book as been added', '2018-08-16 23:13:29', b'1', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('86c06100-72c7-4acc-81ea-b1f01f366524', 'New Comment', 'A new comment about your favorite book as been added', '2018-08-16 23:14:49', b'1', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('9f1715d8-1a14-44f5-8f6a-ceeff9c1ddc6', 'New Comment', 'A new comment about your favorite book as been added', '2018-08-17 01:09:09', b'0', '185b6639-16d2-47d2-b09c-6a805c09b764', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e');

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
('351ec4ef-99af-4190-97c7-ce01a71c821e', 'ADMIN'),
('b3553bc8-f57e-4385-beea-cdb492ef90bc', 'LIBARIAN'),
('cb3e2b7d-34fa-4d47-b35e-d82d8689eddb', 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `topic`
--

CREATE TABLE IF NOT EXISTS `topic` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `topic`
--

INSERT INTO `topic` (`id`, `name`) VALUES
('373f2dbc-0879-465f-8410-3aad1e265ba2', 'Physics'),
('921ad0c0-f6bd-428e-ba38-6bb00384bec6', 'Computer programming'),
('fdf995d5-8993-4db0-88ee-f5a5befc1fd2', 'Marketing'),
('a85eea19-3012-480d-98a7-47221948d908', 'Entrepreneurship');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
  `id` varchar(255) NOT NULL,
  `check_in` datetime NOT NULL,
  `check_in_status` varchar(255) NOT NULL,
  `check_out` datetime NOT NULL,
  `check_out_status` varchar(255) NOT NULL,
  `book_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `check_in`, `check_in_status`, `check_out`, `check_out_status`, `book_id`, `user_id`) VALUES
('460ea95c-0d5e-4aa2-88d7-3b02613e7c27', '2018-07-16 23:57:09', 'PENDING', '2018-07-16 23:57:09', 'ACCEPTED', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', '2939d03b-d94f-4442-8d31-c5ebd2fbc009'),
('b5cdd856-dcbd-4d17-9f0e-d89c471c3ff4', '2018-07-31 13:06:18', 'PENDING', '2018-07-31 13:06:18', 'PENDING', 'cc675a91-e392-4bf9-a0a6-4d1eef7134d2', '533bf99b-5cd7-433a-9a3d-47be3ee5f55f'),
('36ee0007-5eee-4c27-95bf-56340943228a', '2018-08-02 01:00:00', 'PENDING', '2018-08-01 01:00:00', 'PENDING', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', '7115c1f7-c96c-4268-9d37-6e5650bce07c'),
('237b8397-8f4b-4508-b02c-1b69da1b160b', '2018-08-19 01:00:00', 'PENDING', '2018-08-18 01:00:00', 'PENDING', '0fb6f11d-34cc-4dd5-8d45-5ad751ebb7ab', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('cca5a757-6631-43ea-b35f-968fedc281f6', '2018-08-21 01:00:00', 'DENIED', '2018-08-19 01:00:00', 'DENIED', 'cc675a91-e392-4bf9-a0a6-4d1eef7134d2', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e'),
('3fc0ca0b-08ae-41ac-ac8f-5a1f67b56955', '2018-08-20 01:00:00', 'PENDING', '2018-08-19 01:00:00', 'PENDING', 'cc675a91-e392-4bf9-a0a6-4d1eef7134d2', '61991d73-b5b9-4e2e-9df9-5f3d96df2f4e');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(255) NOT NULL,
  `dob` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `dob`, `email`, `first_name`, `last_name`, `password`, `telephone`, `username`, `role_id`) VALUES
('7115c1f7-c96c-4268-9d37-6e5650bce07c', '2018-08-21 01:00:00', 'admin@gmail.com', 'Main', 'Admin', '{bcrypt}$2a$10$fU8WnHtUxeiZD4IfvvPW5eudP9DSqg6Nom2p/zq20LMRLCY3BRXpG', '', 'admin', 'cb3e2b7d-34fa-4d47-b35e-d82d8689eddb'),
('61991d73-b5b9-4e2e-9df9-5f3d96df2f4e', '2018-07-16 22:23:00', 'janedoe@mail.com', 'Jane', 'Doe', '{bcrypt}$2a$10$mhBAjwnLHzZcOGB3IUsciOfeJ8JEDED0EwHcH6n6IJjd7JNw/gIVK', '', 'janedoe', 'b3553bc8-f57e-4385-beea-cdb492ef90bc'),
('2939d03b-d94f-4442-8d31-c5ebd2fbc009', '2018-07-16 22:32:05', 'mikemill@mail.com', 'Mike', 'Mill', '{bcrypt}$2a$10$aInoRlyPTvkI0RuRnylNxeiN99g8jG1QCyUMWov5RlGM.GT0FUHkK', '', 'mike-mill', 'cb3e2b7d-34fa-4d47-b35e-d82d8689eddb'),
('533bf99b-5cd7-433a-9a3d-47be3ee5f55f', '2018-07-31 12:20:24', 'john@mail.com', 'John', 'Doe', '{bcrypt}$2a$10$x8letcKXvevNi74VbAMtFuwHKepfPwBnlYDkMwTyFoUkJy4.Bk7Na', '', '', 'cb3e2b7d-34fa-4d47-b35e-d82d8689eddb'),
('d82f6483-79ce-4edb-8177-507224279699', NULL, 'mike-mill@mail.cm', 'Mike', 'Mill', '{bcrypt}$2a$10$yrNt.VG.wk85mIuPFl9BXeMmQO3HVNTcba0.lU.tOXPZCtTdD/9Va', NULL, 'Mike Mill', 'cb3e2b7d-34fa-4d47-b35e-d82d8689eddb'),
('ed49695e-3d1f-4064-bc60-6cd6af969005', NULL, 'mileymill@mail.cm', 'Miley', 'mill', '{bcrypt}$2a$10$Iv/bReypykLb3uEmqiXvGekLqLFMzfC1r9VN18OIcwtcCtFtFWy.6', NULL, 'Miley mill', 'cb3e2b7d-34fa-4d47-b35e-d82d8689eddb');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ehpdfjpu1jm3hijhj4mm0hx9h` (`isbn`),
  ADD KEY `FKcic9k4m2mf4vle91a24xv35k1` (`topic_id`);

--
-- Indexes for table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKkko96rdq8d82wm91vh2jsfak7` (`book_id`),
  ADD KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`);

--
-- Indexes for table `favorite_books`
--
ALTER TABLE `favorite_books`
  ADD PRIMARY KEY (`user_id`,`book_id`),
  ADD KEY `FKl8p2m9gngy4kn79b8gd0ir31q` (`book_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKcd63rxqpw244o20e5xn64yj0` (`book_id`),
  ADD KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8hddvclv2iqa3sg1dm8295pqw` (`book_id`),
  ADD KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn82ha3ccdebhokx3a8fgdqeyy` (`role_id`);

