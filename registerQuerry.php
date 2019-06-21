<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   $name = $_POST["name"];
   $password = $_POST["password"];
   $email = $_POST["email"];
   $date = $_POST["date"];
   
   $mysql_query = "INSERT INTO user_info (name, password, email, date_entered) VALUES ('$name', '$password', '$email', '$date')";
   
   if(mysqli_query($con, $mysql_query)){
	   echo "Registration Successful.";
   }
   else{
	   echo "Error! Registration Failed.";
   }
   mysqli_close($con);

