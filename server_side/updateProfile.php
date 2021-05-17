<?php

require_once 'connection.php';

	$name=$_REQUEST['name'];
	$last_name=$_REQUEST['last_name'];
	$user_id=$_REQUEST['user_id'];
	 
	 
	 $query = "UPDATE users SET name = '$name', last_name = '$last_name' WHERE id = $user_id ";
	 
	 $result = mysqli_query($connection,$query);
	 
	 if($result != ""){
			$response['status']='successful';
			
		}else{
			$response['status']='fail';
		}
		
		
		mysqli_close($connection);
		echo (json_encode($response));
	  
	  


?>