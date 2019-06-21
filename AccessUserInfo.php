<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   $user_email = $_POST["email"];
   $user_password = $_POST["password"];
   //$user_email = "alexismwengo@gmail.com";
   //$user_password ="vannessa";
   
   $mysql_query = "SELECT name FROM user_info WHERE  email = '".$user_email."' AND password = '".$user_password."'";
   
   $result = mysqli_query($con, $mysql_query);
   
   /**/
   
   if(mysqli_num_rows($result)>0){
	   if($result) {
			$emparray = array();
			while($row =mysqli_fetch_assoc($result)){
				$emparray[] = $row;
			}
			echo(json_encode($emparray));      
		}
		
		/*$res = mysqli_fetch_assoc($result);
		echo $res;*/
   }
   else{
	   echo "Can't find list of doctors";
   }
   mysqli_close($con);
