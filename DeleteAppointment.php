<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   //$appointment_id = $_POST["appointment_id"];
   //$user_email = "alexismwengo@gmail.com";
   $appointment_id ="50";
   
   $mysql_query = "DELETE FROM appointments WHERE appointment_id = $appointment_id";
   
   $result = mysqli_query($con, $mysql_query);
   
   /**/
   
   if($result){
		echo "Row successfully deleted.";
   }
   else{
	   echo "Couldn't delete the requested row.";
   }
   mysqli_close($con);
