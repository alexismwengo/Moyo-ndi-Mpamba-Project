<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   //$email = "alexismwengo@gmail.com";
   //$password = "vannessa";
   
   $email = $_POST["email"];
   $password = $_POST["password"];
   
   $mysql_query = "SELECT email, password FROM user_info WHERE email = '".$email."' AND password = '".$password."'";
   
   $result = mysqli_query($con, $mysql_query);
   
   /**/
   
   if(mysqli_num_rows($result)>0){
	   
	   echo "Login Successful ";

   }
   else{
	   echo "Login Unsuccessful";
   }
   mysqli_close($con);