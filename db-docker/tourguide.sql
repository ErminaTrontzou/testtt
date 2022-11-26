-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 27 Οκτ 2022 στις 21:13:33
-- Έκδοση διακομιστή: 10.4.22-MariaDB
-- Έκδοση PHP: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `tourguide`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `images`
--

CREATE TABLE `images` (
  `id` int(11) NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `latitude` varchar(255) NOT NULL,
  `longitude` varchar(255) NOT NULL,
  `views` int(11) NOT NULL,
  `owner_name` varchar(255) NOT NULL,
  `date_taken` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `images`
--

INSERT INTO `images` (`id`, `file_name`, `description`, `title`, `latitude`, `longitude`, `views`, `owner_name`, `date_taken`) VALUES
(1, 'lalala', 'okeydesc', 'This is the title', '-098765', '-098765', 0, 'Your mum', '2022-10-20 18:09:16'),
(2, 'ok', 'lalala', 'this is the title', '87654', '87654', 13, 'your mother', '2022-10-20 18:09:54'),
(3, 'https://live.staticflickr.com/65535/48685085288_db0141fdfd_o.jpg', 'The White Tower of Thessaloniki (Ladino: Kuli Blanka, Greek: Λευκός Πύργος Lefkos Pyrgos, Turkish: Beyaz Kule), is a monument and museum on the waterfront of the city of Thessaloniki, capital of the region of Macedonia in northern Greece. The present towe', 'White Tower Thessaloniki', '40.626580', '22.947538', 282, 'txikita69', '2015-08-17 17:19:09');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `image_tags`
--

CREATE TABLE `image_tags` (
  `id` int(11) NOT NULL,
  `image_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `image_tags`
--

INSERT INTO `image_tags` (`id`, `image_id`, `tag_id`) VALUES
(2, 1, 1),
(1, 1, 3),
(3, 2, 2),
(4, 3, 2);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `tags`
--

CREATE TABLE `tags` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Άδειασμα δεδομένων του πίνακα `tags`
--

INSERT INTO `tags` (`id`, `name`) VALUES
(1, 'cool'),
(2, 'ok'),
(3, 'aaaa');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `is_admin` tinyint(1) NOT NULL,
  `is_disabled` tinyint(1) NOT NULL,
  `disabled` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user_collections`
--

CREATE TABLE `user_collections` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `is_public` tinyint(1) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user_collections_images`
--

CREATE TABLE `user_collections_images` (
  `id` int(11) NOT NULL,
  `image_id` int(11) NOT NULL,
  `user_collection_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `image_tags`
--
ALTER TABLE `image_tags`
  ADD PRIMARY KEY (`id`),
  ADD KEY `image_id` (`image_id`,`tag_id`),
  ADD KEY `tag_id` (`tag_id`);

--
-- Ευρετήρια για πίνακα `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `user_collections`
--
ALTER TABLE `user_collections`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Ευρετήρια για πίνακα `user_collections_images`
--
ALTER TABLE `user_collections_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `image_id` (`image_id`,`user_collection_id`),
  ADD KEY `user_collection_id` (`user_collection_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `images`
--
ALTER TABLE `images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT για πίνακα `image_tags`
--
ALTER TABLE `image_tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `tags`
--
ALTER TABLE `tags`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT για πίνακα `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `user_collections`
--
ALTER TABLE `user_collections`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `user_collections_images`
--
ALTER TABLE `user_collections_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `image_tags`
--
ALTER TABLE `image_tags`
  ADD CONSTRAINT `image_tags_ibfk_1` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`),
  ADD CONSTRAINT `image_tags_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`);

--
-- Περιορισμοί για πίνακα `user_collections`
--
ALTER TABLE `user_collections`
  ADD CONSTRAINT `user_collections_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- Περιορισμοί για πίνακα `user_collections_images`
--
ALTER TABLE `user_collections_images`
  ADD CONSTRAINT `user_collections_images_ibfk_1` FOREIGN KEY (`user_collection_id`) REFERENCES `user_collections` (`id`),
  ADD CONSTRAINT `user_collections_images_ibfk_2` FOREIGN KEY (`image_id`) REFERENCES `images` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
