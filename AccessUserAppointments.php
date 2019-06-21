<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   $user_name = $_POST["user_name"];
   //$user_name = "alexis Mwengo";
   
   $mysql_query = "SELECT* FROM appointments WHERE user_name = '".$user_name."'";
   
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
   }
   else{
	   echo "No Appointments for such user";
   }
   mysqli_close($con);
