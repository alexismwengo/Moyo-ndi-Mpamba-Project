<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   $patient_age = $_POST["patient_age"];
   $patient_name = $_POST["patient_name"];
   $patient_address = $_POST["patient_address"];
   $patient_phone = $_POST["patient_phone"];
   $patient_message = $_POST["patient_message"];
   $date = $_POST["date"];
   $name = $_POST["name"];
   $city = $_POST["city"];
   $appointment_time = $_POST["appointment_time"];
   $phone = $_POST["phone"];
   
   
   $sent_to = $_POST["sent_to"];
   
   $mysql_query = "INSERT INTO appointments (user_name, user_age, date, user_address, user_message, user_phone, name, city, appointment_time, phone, sent_to)VALUES ('$patient_name', '$patient_age', '$date', '$patient_address', '$patient_message', '$patient_phone', '$name', '$city', '$appointment_time', '$phone', '$sent_to')";
   
   if(mysqli_query($con, $mysql_query)){
	   echo "Booking Successful";

   }
   else{
	   echo "Booking Unsuccessful";
   }
   mysqli_close($con);
