<?php

require_once 'connection.php';

 if(isset($_REQUEST['name']) and isset($_REQUEST['last_name']) and isset($_REQUEST['password']) and isset($_REQUEST['phone'])){
	 
	 
	 $name=$_REQUEST['name'];
	 $last_name=$_REQUEST['last_name'];
	 $password=md5($_REQUEST['password']);
	 $phone=$_REQUEST['phone'];
	 
	 $query = "INSERT INTO users (name , last_name , password , phone ) VALUES ('$name','$last_name','$password', '$phone' ) ";
	 
		
		$result = mysqli_query($connection,$query);
		$response=array();
		
		if($result != ""){
			$response['status']='successful';
		}else{
			$response['status']='fail';
		}
		
		mysqli_close($connection);
		echo json_encode($response,JSON_UNESCAPED_UNICODE);
	 
 }

else{
	
	 echo 'please enter name,last_name,password,phone';
	 
 }



?>