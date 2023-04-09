<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   $genderofpatient = $_POST["genderofpatient"];
   $health_centre= $_POST["health_centre"];
   $date_submitted = $_POST["date_submitted"];
   $time_submitted = $_POST["time_submitted"];
   $ambulance_operator_id = $_POST["ambulance_operator_id"];
   $image_submitted= $_POST["image_submitted"];
   $comments_submitted = $_POST["comments_submitted"];
   
   $emergency_sender = $_POST["emergency_sender"];
   
   $emerg_address= $_POST["emerg_address"];
   $emerg_city = $_POST["emerg_city"];
   $emerg_known_name = $_POST["emerg_known_name"];
   
   /*$genderofpatient = "genderofpatient";
   $health_centre= "health_centre";
   $date_submitted = "date_submitted";
   $ambulance_operator_id = "ambulance_operator_id";
   $image_submitted= "image_submitted";
   $comments_submitted = "comments_submitted";*/
   
   $mysql_query = "INSERT INTO emergencies (genderofpatient, health_centre, date_submitted, ambulance_operator_id, image_submitted, comments_submitted, emergency_sender, emerg_address, emerg_city, emerg_known_name, emergency_time) VALUES ('$genderofpatient', '$health_centre', '$date_submitted', '$ambulance_operator_id', '$image_submitted', '$comments_submitted', '$emergency_sender', '$emerg_address', '$emerg_city', '$emerg_known_name', '$time_submitted')";
   
   if(mysqli_query($con, $mysql_query)){
	   echo "Emergency Sent...";
   }
   else{
	   echo "Emergency Sending Failed";
   }
   mysqli_close($con);
