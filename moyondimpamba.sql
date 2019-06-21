-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jun 21, 2019 at 08:55 AM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `moyondimpamba`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointments`
--

CREATE TABLE IF NOT EXISTS `appointments` (
  `user_name` varchar(50) NOT NULL,
  `user_age` int(4) NOT NULL,
  `appointment_state` tinyint(1) NOT NULL DEFAULT '0',
  `date` date NOT NULL,
  `user_address` varchar(100) NOT NULL,
  `user_message` varchar(300) NOT NULL,
  `name` varchar(30) NOT NULL,
  `sent_to` varchar(30) NOT NULL,
  `user_phone` varchar(20) NOT NULL,
`appointment_id` int(11) NOT NULL,
  `appointment_time` varchar(40) NOT NULL,
  `attached_pic` int(11) NOT NULL,
  `patient_id` int(10) NOT NULL,
  `city` varchar(20) NOT NULL,
  `phone` varchar(16) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=85 ;

--
-- Dumping data for table `appointments`
--

INSERT INTO `appointments` (`user_name`, `user_age`, `appointment_state`, `date`, `user_address`, `user_message`, `name`, `sent_to`, `user_phone`, `appointment_id`, `appointment_time`, `attached_pic`, `patient_id`, `city`, `phone`) VALUES
('alexis Mwengo', 21, 1, '2019-06-20', 'zfbg', 'Check-up', 'doc3 doooo', 'Private Doctor', '088180884', 67, '10 - 11 am', 0, 0, 'zomba', '0998263738'),
('alexis Mwengo', 21, 0, '2019-06-21', 'fgg g gg', 'Headache', 'Mlambe Catholic (Pvt) Hospital', 'Health Facility', '0881785688', 68, '10 - 11 am', 0, 0, 'Lunzu, Blantyre', '0938774743'),
('Alexis Mwengo', 21, 0, '2019-06-14', 'Blantyre', 'Recovery Followup', 'Nsanje District Hospital', 'Health Facility', '0881004199', 69, '04 - 05 pm', 0, 0, 'Nsanje ', '0992737373'),
('Alexis Mwengo', 21, 1, '2019-06-28', 'Zomba', 'Headache', 'KuChawe Clinic', 'Health Facility', '0881004199', 70, '11 - 12 am', 0, 0, 'Zomba', '088264377'),
('Alexis Mwengo', 21, 1, '2019-06-30', 'Blantyre', 'Help needed asap', 'dummy doctor', 'Private Doctor', '097643484', 71, '04 - 05 pm', 0, 0, 'Mulanje', '0999188282'),
('Omega Zimba', 24, 0, '2019-06-27', 'Nzimba', 'Stomachache', 'Kamuzu Central Hospital', 'Health Facility', '0876438454', 72, '11 - 12 am', 0, 0, 'Lilongwe', '01983743764'),
('Omega Zimba', 24, 3, '2019-06-03', 'zomba', 'cufd g', 'dummy doctor', 'Private Doctor', '088527805', 73, '10 - 11 am', 0, 0, 'Mulanje', '0999188282'),
('peter wachenjera', 80, 0, '2019-06-22', 'mulanje', 'tth', 'doc1 doc2', 'Private Doctor', '0958458052', 74, '10 - 11 am', 0, 0, 'Lilongwe', '0881004199'),
('peter wachenjera', 58, 1, '2019-06-28', 'vfbh cc', 'yhb', 'Mpondabwino (Pvt) Clinic', 'Health Facility', '0995808554', 75, '03 - 04 pm', 0, 0, 'Zomba', '0999274364'),
('peter wachenjera', 0, 0, '2019-06-20', '', '', 'dummy doctor', 'Private Doctor', '', 76, '09 - 10 am', 0, 0, 'Mulanje', '0999188282'),
('peter wachenjera', 39, 0, '2019-06-17', 'chanco', 'headache', 'Chancellor College Clinic', 'Health Facility', '0888781764', 77, '10 - 11 am', 0, 0, 'Zomba', '013244322'),
('peter wachenjera', 36, 1, '2019-06-30', 'chanco', 'stomacheache', 'KuChawe Clinic', 'Health Facility', '0888781764', 78, '04 - 05 pm', 0, 0, 'Zomba', '088264377'),
('peter wachenjera', 36, 0, '2019-06-23', 'chanco', 'help', 'doctor Another', 'Private Doctor', '0888781764', 79, '11 - 12 am', 0, 0, 'Kasungu', '+265 88712345'),
('alene mendb', 425, 1, '2019-06-20', 'vhs', 'vdhd', 'Kamuzu Central Hospital', 'Health Facility', '0486498', 80, '03 - 04 pm', 0, 0, 'Lilongwe', '01983743764'),
('alene mendb', 14, 0, '2019-06-25', 'Mbumba', 'Hell Yeah', 'Kamuzu Central Hospital', 'Health Facility', '0993329457', 81, '04 - 05 pm', 0, 0, 'Lilongwe', '01983743764'),
('mwengo', 82, 0, '2019-06-22', 'Zomba', 'ahaa', 'dummy doctor', 'Private Doctor', '0996355885', 82, '09 - 10 am', 0, 0, 'Mulanje', '0999188282'),
('JosÃ© Lekeni', 30, 0, '2019-06-28', 'Zomba', 'Hello', 'Kamuzu Central Hospital', 'Health Facility', '0881067349', 83, '09 - 10 am', 0, 0, 'Lilongwe', '01983743764'),
('JosÃ© Lekeni', 96, 0, '2019-06-12', 'gddj', 'hhgyh', 'doc1 doc2', 'Private Doctor', '5265', 84, '03 - 04 pm', 0, 0, 'Lilongwe', '0881004199');

-- --------------------------------------------------------

--
-- Table structure for table `emergencies`
--

CREATE TABLE IF NOT EXISTS `emergencies` (
`emergency_id` int(10) NOT NULL,
  `genderofpatient` varchar(7) NOT NULL,
  `health_centre` varchar(50) NOT NULL,
  `date_submitted` date NOT NULL,
  `ambulance_operator_id` varchar(30) NOT NULL,
  `image_submitted` varchar(20) NOT NULL,
  `comments_submitted` varchar(500) NOT NULL,
  `emergency_location` varchar(100) NOT NULL,
  `emergency_sender` varchar(100) NOT NULL,
  `emerg_address` varchar(100) NOT NULL,
  `emerg_city` varchar(100) NOT NULL,
  `emerg_known_name` varchar(100) NOT NULL,
  `emergency_time` varchar(30) NOT NULL,
  `emergency_state` varchar(5) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `emergencies`
--

INSERT INTO `emergencies` (`emergency_id`, `genderofpatient`, `health_centre`, `date_submitted`, `ambulance_operator_id`, `image_submitted`, `comments_submitted`, `emergency_location`, `emergency_sender`, `emerg_address`, `emerg_city`, `emerg_known_name`, `emergency_time`, `emergency_state`) VALUES
(6, 'Male', 'Zomba, Banja la Mtsogolo', '2019-06-16', '', '', 'jhc', '', 'Alexis Mwengo', '', '', '', '04:04:59 AM', ''),
(7, '', 'Zomba Central Hospital', '2019-06-17', '', '', '', '', 'SENDER: (ANONYMOUS)', '', '', '', '04:24:32 PM', ''),
(8, '', 'Zomba Central Hospital', '2019-06-18', '', '', '', '', 'SENDER: (ANONYMOUS)', '', '', '', '03:50:19 PM', ''),
(9, '', 'Zomba Central Hospital', '2019-06-19', '', '', '', '', 'Alexis Mwengo', '', '', '', '09:25:29 PM', ''),
(10, 'Female', 'Zomba, Banja la Mtsogolo', '2019-06-20', '', '', 'gfev gr', '', 'Alexis Mwengo', 'P.O. Box 280 Chancellor College, Zomba, Malawi', 'Zomba', 'P.O. Box 280', '06:32:42 PM', '');

-- --------------------------------------------------------

--
-- Table structure for table `health_centres`
--

CREATE TABLE IF NOT EXISTS `health_centres` (
  `hospital_name` varchar(150) NOT NULL,
  `city` varchar(40) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `quick_description` varchar(700) NOT NULL,
  `days_open` varchar(50) NOT NULL,
  `hours_open` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `health_centres`
--

INSERT INTO `health_centres` (`hospital_name`, `city`, `phone`, `email`, `quick_description`, `days_open`, `hours_open`) VALUES
('Chancellor College Clinic', 'Zomba', '013244322', 'clinic@cc.ac.mw', 'Official Clinic of the mighty University of Malawi, Chancellor College.', 'Mon - Fri', '7am - 4pm'),
('Chitawira (Pvt) Clinic', 'Blantyre', '0873647433', 'chitawira@chit.gmail.com', 'Experienced Personnel. You won''t regret working with us ', 'Mon - Th', '9am - 3pm'),
('Kamuzu Central Hospital', 'Lilongwe', '01983743764', 'kamuzu.ka@hfndnd', 'One of the oldest (established in 1904) and most trusted in the country. Located at the heart of Blantyre', 'Mon - Sunday', '8am - 7pm'),
('KuChawe Clinic', 'Zomba', '088264377', 'kuchawe@kuchawe', 'Highest reputation. On the hill. Fresh air all day; 24/7', 'Tue - Fri', '9am - 7pm'),
('Mlambe Catholic (Pvt) Hospital', 'Lunzu, Blantyre', '0938774743', 'mlambe@hfjdm', 'Catholic values!', 'Mon - Sun', '7am - 6pm'),
('Mpondabwino (Pvt) Clinic', 'Zomba', '0999274364', '--', 'Acredited by the Medical Association of Malawi under Health Care Delivery Rule Section 1, Subsection 7. Great services', 'Mon - Wed', '7am - 12pm'),
('Nsanje District Hospital', 'Nsanje ', '0992737373', 'clinic.nsanje@clinic.mw', 'Health Centre in Nsanje, blah blah', 'Mon - Fri', '8am - 6pm');

-- --------------------------------------------------------

--
-- Table structure for table `hospital_administrators`
--

CREATE TABLE IF NOT EXISTS `hospital_administrators` (
  `firstname` varchar(20) NOT NULL,
  `lastname` varchar(25) NOT NULL,
  `username` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `phone` char(15) NOT NULL,
  `password` varchar(30) NOT NULL,
  `role` varchar(50) NOT NULL,
`admin_id` int(100) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `hospital_administrators`
--

INSERT INTO `hospital_administrators` (`firstname`, `lastname`, `username`, `gender`, `phone`, `password`, `role`, `admin_id`) VALUES
('peter', 'wachenjera', 'peter wachenjera', 'male', '', 'peter', 'admin', 1);

-- --------------------------------------------------------

--
-- Table structure for table `private_doctors`
--

CREATE TABLE IF NOT EXISTS `private_doctors` (
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `proffession` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `doc_info` varchar(500) NOT NULL,
  `message` varchar(150) NOT NULL,
  `second_phone` varchar(15) NOT NULL,
  `days_available` varchar(20) NOT NULL,
  `hours` varchar(20) NOT NULL,
  `payment_state` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `private_doctors`
--

INSERT INTO `private_doctors` (`firstname`, `lastname`, `proffession`, `city`, `phone`, `email`, `doc_info`, `message`, `second_phone`, `days_available`, `hours`, `payment_state`) VALUES
('Blazaman', 'Biggy', 'General Doc', 'Zomba', '0998727334', 'gem@gem.com', 'Got a phD in nothing in particular', '', '092832739393', 'Sat - Sun', '10 am - 12pm', 0),
('doc1', 'doc2', 'Dentist', 'Lilongwe', '0881004199', 'doc1@gmail.com', 'Experience of 4 years. Well known and Trusted.', '', '', 'Fri - Sun', '2 pm - 4 pm', 0),
('doc1', 'Surname', 'doctor', 'BT', '09991872832', 'hisSurname@gmail.com', 'hfgu  dsv nbvcgyu eucn bcvvdm dndhd dwuw', '', '0999181223', 'Fri', '5 pm - 7 pm', 0),
('doc3', 'doooo', 'surgion', 'zomba', '0998263738', 'o@cc.com', 'hdg tiw yuwiosjb dbv e', '', '', 'Sun', '3 pm - 5 pm', 0),
('doctor', 'Another', 'Pediatrist', 'Kasungu', '+265 88712345', 'y@mailto.com', 'ikj', 'uy', 't', 'Mon - Fri', '6 am - 4 pm', 0),
('dummy', 'doctor', 'Witch doctor', 'Mulanje', '0999188282', 'wizard@gmail.com', 'Specialist in wichcraft and wizardry. Went to Hogwarts. Tutored by Albus Dumbledore, the Greatest wizard that''s ever walked on Earth.', '', '08881028273', 'Tue - Wed', '8 am - 2 pm', 0),
('Humbler', 'Wister', 'Wamaso', 'Rumphi', '09992832733', 'wister@cc.k', '', '', '', 'Mon - Fri', '12 pm - 4 pm', 0);

-- --------------------------------------------------------

--
-- Table structure for table `the_tips`
--

CREATE TABLE IF NOT EXISTS `the_tips` (
`tip_no` int(20) NOT NULL,
  `tip_string` varchar(3000) NOT NULL,
  `tip_title` varchar(400) NOT NULL,
  `user` varchar(30) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=34 ;

--
-- Dumping data for table `the_tips`
--

INSERT INTO `the_tips` (`tip_no`, `tip_string`, `tip_title`, `user`) VALUES
(15, 'Women below the age of 50 need twice the amount of iron per day as men of the same age.', 'Iron', 'Alexis Mwengo'),
(20, 'Laughing is good for the heart and can increase blood flow by 20 percent.', 'Laugh a little', 'Alexis Mwengo'),
(23, 'Chewing gum makes you more alert, relieves stress and reduces anxiety levels.', 'Chewing Gum', 'Alexis Mwengo'),
(25, 'Always look on the bright side: being an optimist can help you live longer.', 'Be Optimistic', 'Alexis Mwengo'),
(26, '', '', 'Alexis Mwengo'),
(27, 'Maintaining good relationships with family and friends is good for your health, memory and longevity.', 'Maintain Good Relations, Always', 'Alexis Mwengo'),
(28, 'Yoga can boost your cognitive function and lowers stress.', 'Yoga is nice', 'Alexis Mwengo'),
(29, '', '', ''),
(30, 'An apple a day does keep the doctor away. Apples can reduce levels of bad cholesterol to keep your heart healthy.', 'Fruits', 'Alexis Mwengo'),
(31, 'Drinking at least five glasses of water a day can reduce your chances of suffering from a heart attack by 40%.', 'Water is Essential', 'Alexis Mwengo'),
(32, 'Eating oatmeal provides a serotonin boost to calm the brain and improve your mood.', 'Oatmeal', 'peter wachenjera'),
(33, 'Learning a new language or playing a musical instrument gives your brain a boost.', 'Always Keep Learning', 'Alexis Mwengo');

-- --------------------------------------------------------

--
-- Table structure for table `tips`
--

CREATE TABLE IF NOT EXISTS `tips` (
`num` int(12) NOT NULL,
  `title` varchar(100) NOT NULL,
  `text` varchar(500) NOT NULL,
  `image` varchar(230) NOT NULL,
  `tip_type` varchar(10) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=25 ;

--
-- Dumping data for table `tips`
--

INSERT INTO `tips` (`num`, `title`, `text`, `image`, `tip_type`) VALUES
(1, 'Almonds, avocados and arugula', 'Almonds, avocados and arugula (the three A''s) can boost your sex drive and improve fertility.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Almonds-Avocados-and-Arugula-Can-Boost-Your-Sex-Drive-and-Improve-Fertility.png', 'Food'),
(2, 'Rosemary', 'Smelling rosemary may increase alertness and improve memory so catch a whiff before a test or important meeting.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Smelling-Rosemary-Can-Increase-Alertness-and-Improve-Memory.png', 'General'),
(3, 'Fruits', 'An apple a day does keep the doctor away. Apples can reduce levels of bad cholesterol to keep your heart healthy.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/An-Apple-a-Day-Can-Reduce-Levels-of-Bad-Cholesterol.png', 'Food'),
(4, 'Be Optimistic', 'Always look on the bright side: being an optimist can help you live longer.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Always-Look-on-the-Bright-Side-Being-an-Optimist-Can-Help-You-Live-Longer.png', 'General'),
(5, 'Coffee is Good', 'Drinking coffee can reduce the risk of depression, especially in women.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Drinking-Coffee-Can-Reduce-the-Risk-of-Depression.png', 'Food'),
(6, 'Water is Essential', 'Drinking at least five glasses of water a day can reduce your chances of suffering from a heart attack by 40%.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Five-Glasses-of-Water-a-Day-Can.png', 'Food'),
(7, 'Exercise is Essential', 'Exercise will give you more energy, even when you''re tired. A lack of exercise now causes as many deaths as smoking.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Exercise-Will-Give-You-More-Energy-Even-When-Youre-Tired.png', 'Fitness'),
(8, 'Yoga is nice', 'Yoga can boost your cognitive function and lowers stress.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Yoga-Can-Boost-Your-Cognitive-Function-and-Lower-Stress.png', 'Fitness'),
(9, 'Read a Book', 'Feeling stressed? Read. Getting lost in a book can lower levels of cortisol, or other unhealthy stress hormones, by 67 percent.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Reading-Can-Lower-Levels-of-Unhealthy-Stress-Hormones.png', 'General'),
(10, 'Fitness', 'There are five main components of fitness: the body''s ability to use oxygen, muscular strength, endurance, flexibility and body composition.', 'https://www.thegoodbody.com/wp-content/uploads/2018/04/The-Five-Main-Components-of-Fitness.png', 'Fitness'),
(12, 'Maintain Good Relations, Always', 'Maintaining good relationships with family and friends is good for your health, memory and longevity.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Staying-in-Touch-With-Family-and-Friends-Is-Good-for-Your-Health-Memory-and-Longevity.png', 'General'),
(13, 'Chewing Gum', 'Chewing gum makes you more alert, relieves stress and reduces anxiety levels.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Chewing-Gum-Makes-You-More-Alert-Relieves-Stress-and-Reduces-Anxiety-Levels.png', 'Food'),
(14, 'Iron', 'Women below the age of 50 need twice the amount of iron per day as men of the same age.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Women-below-the-Age-of-50-Need-Twice-the-Amount-of-Iron-per-Day-as-Men-of-the-Same-Age.png', 'Food'),
(15, 'Digestion', 'Although it only takes you a few minutes to eat a meal, it takes your body hours to completely digest the food.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/It-Takes-Your-Body-Hours-to-Completely-Digest-a-Meal.png', 'General'),
(16, 'Laugh a little', 'Laughing is good for the heart and can increase blood flow by 20 percent.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Laughing-Can-Increase-Blood-Flow-by-20-percent.png', 'General'),
(17, 'Always Keep Learning', 'Learning a new language or playing a musical instrument gives your brain a boost.', 'https://www.thegoodbody.com/wp-content/uploads/2017/08/Your-Brain-Gets-a-Boost-When-You-Learn-a-New-Language-or-Musical-Instrument.png', 'General'),
(18, 'Oatmeal', 'Eating oatmeal provides a serotonin boost to calm the brain and improve your mood.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Eating-Oatmeal-Provides-a-Serotonin-Boost-Which-Calms-the-Brain-and-Improves-Mood.png', 'Food'),
(19, 'Running ', 'Running is good for you. People who run 12-18 miles a week have a stronger immune system and can increase their bone mineral density.', 'https://www.thegoodbody.com/wp-content/uploads/2018/04/People-Who-Run-12-18-Miles-a-Week-Have-a-Stronger-Immune-System.png', 'Fitness'),
(20, 'Moderation Essential', 'Sitting and sleeping are great in moderation, but too much can increase your chances of an early death.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Too-Much-Sitting-and-Sleeping-Can-Increase-Your-Chances-Of-an-Early-Death.png', 'General'),
(22, 'Vitamin D', 'Vitamin D is as important as calcium in determining bone health, and most people don''t get enough of it.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Vitamin-D-Is-as-Important-as-Calcium-in-Determining-Bone-Health.png', 'Food'),
(23, 'Three Hours of Walk is Great', 'Walking at a fast pace for three hours or more at least one time a week, you can reduce your risk of heart disease by up to 65.', 'https://www.thegoodbody.com/wp-content/uploads/2018/04/You-Can-Reduce-Your-Risk-of-Heart-Disease-By-Walking-at-a-Fast-pace-for-Three-Hours-or-More-at-Least-Once-a-Week.png', 'Fitness'),
(24, 'Take care of Your Skin', 'Not only is it the largest organ in the body, but it defends against disease and infection, regulates your temperature and aids in vitamin production.', 'https://www.thegoodbody.com/wp-content/uploads/2017/11/Skin-Is-the-Largest-Organ-in-the-Body-and-It-Defends-Against-Disease-and-Infection.png', 'General');

-- --------------------------------------------------------

--
-- Table structure for table `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
`id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `date_entered` date NOT NULL,
  `phone` int(10) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=81 ;

--
-- Dumping data for table `user_info`
--

INSERT INTO `user_info` (`id`, `name`, `password`, `email`, `date_entered`, `phone`) VALUES
(20, 'Alexis Mwengo', 'vannessa', 'alexismwengo@gmail.com', '0000-00-00', 0),
(21, 'mwengo', 'mwengo', 'mwengo@mwengo.com', '0000-00-00', 0),
(31, 'lexis', 'lexi', 'lexis@gmail.com', '2019-05-02', 0),
(32, 'chiyanjano Kamwendo', 'chia', 'chia@gmal.com', '2019-05-02', 0),
(33, 'alexis', 'fibre', 'alexis@gmaik.com', '2019-05-02', 0),
(34, 'fibre lawrence', 'ndiuzayni', 'lawrence@gmail.com', '2019-05-02', 0),
(35, 'celtic celtic', 'variable', 'alexismwengo@gmail.com', '2019-05-02', 0),
(36, 'ineyo', 'vess', 'ine@mama.com', '2019-05-02', 0),
(40, 'kaks', 'too', 'sjusj@nsje', '2019-05-02', 0),
(41, 'kaks', 'too', 'sjusj@nsje', '2019-05-02', 0),
(42, 'kaks', 'too', 'sjusj@nsje', '2019-05-02', 0),
(43, 'ale', 'vee', 'nsje@', '2019-05-02', 0),
(44, 'alez', 'he', 'lele@', '2019-05-02', 0),
(45, 'aleje', 'lll', 'jdks@jdi', '2019-05-02', 0),
(46, 'peter wachenjera', 'peter', 'peterwachenjera@gmail.com', '2019-05-02', 0),
(51, 'alsnd', 'vees', 'jemzk@hdkek', '2019-05-02', 0),
(52, 'alex', 'alex', 'alex', '2019-05-03', 0),
(53, 'hjhjdshjds', 'qqq', 'oskr', '2019-05-03', 0),
(55, 'joseph matola', 'matola', 'matola@gmail.com', '2019-05-04', 0),
(56, 'Joseph matola', 'matola', 'joseph@gmail.com', '2019-05-04', 0),
(57, 'lexis', 'van', 'lexis@gmail.com', '2019-05-04', 0),
(58, 'afuj', 'gg', 'hxrg', '2019-05-04', 0),
(66, 'sag', 'cccc', 'shj@gunxs', '0000-00-00', 0),
(67, 'fddj', 'ggg', 'amfc@gma.vko', '2019-05-05', 0),
(68, 'malvolo', 'com', '@hmIl', '2019-05-07', 0),
(69, 'kajsn', 'hey', 'hey@gmail.com', '2019-05-07', 0),
(70, 'binto', 'binto', 'binto@gmail.com', '2019-05-09', 0),
(71, 'meetff vg', 'bgg', 'dfh@gh.bf', '2019-05-31', 0),
(72, 'allk gtg', 'xx3', 'agtf@hght', '2019-06-06', 0),
(73, '', '', '', '0000-00-00', 0),
(74, 'Omega Zimba', 'zimba', 'zimba@gmail.com', '2019-06-16', 0),
(75, 'James', 'jame', 'james@gmail.com', '2019-06-17', 0),
(76, 'alene mendb', 'alexi', 'alexi@gma.com', '2019-06-17', 0),
(77, 'JosÃ© Lekeni', 'jose', 'jose@leken.com', '2019-06-20', 0),
(78, 'ghn', 'vvvv', 'fvh@gjn', '2019-06-20', 0),
(79, 'alexismwengooo', 'xcx', 'fvbht@fgj hut', '2019-06-20', 0),
(80, 'altr', 'ggg', 'gvf@fvh', '2019-06-20', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointments`
--
ALTER TABLE `appointments`
 ADD PRIMARY KEY (`appointment_id`);

--
-- Indexes for table `emergencies`
--
ALTER TABLE `emergencies`
 ADD PRIMARY KEY (`emergency_id`);

--
-- Indexes for table `health_centres`
--
ALTER TABLE `health_centres`
 ADD PRIMARY KEY (`hospital_name`);

--
-- Indexes for table `hospital_administrators`
--
ALTER TABLE `hospital_administrators`
 ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `private_doctors`
--
ALTER TABLE `private_doctors`
 ADD PRIMARY KEY (`firstname`,`lastname`,`email`);

--
-- Indexes for table `the_tips`
--
ALTER TABLE `the_tips`
 ADD PRIMARY KEY (`tip_no`);

--
-- Indexes for table `tips`
--
ALTER TABLE `tips`
 ADD PRIMARY KEY (`num`), ADD FULLTEXT KEY `title` (`title`);

--
-- Indexes for table `user_info`
--
ALTER TABLE `user_info`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `appointments`
--
ALTER TABLE `appointments`
MODIFY `appointment_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=85;
--
-- AUTO_INCREMENT for table `emergencies`
--
ALTER TABLE `emergencies`
MODIFY `emergency_id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `hospital_administrators`
--
ALTER TABLE `hospital_administrators`
MODIFY `admin_id` int(100) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `the_tips`
--
ALTER TABLE `the_tips`
MODIFY `tip_no` int(20) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=34;
--
-- AUTO_INCREMENT for table `tips`
--
ALTER TABLE `tips`
MODIFY `num` int(12) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=25;
--
-- AUTO_INCREMENT for table `user_info`
--
ALTER TABLE `user_info`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=81;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
