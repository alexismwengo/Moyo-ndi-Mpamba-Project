<?php

   define('hostname', 'localhost');
   define('user', 'root');
   define('password', '');
   define('databaseName', 'moyondimpamba');

   $con = mysqli_connect(hostname, user, password, databaseName) or die('Unable to connect to database.');
   
   if(mysqli_connect_error($con)){
	   echo "Failed to connect to database".mysqli_connect_error();
   }
   
   $mysql_query = "SELECT* FROM health_centres";
   
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
	   echo "Can't find list of health_centres";
   }
   mysqli_close($con);
