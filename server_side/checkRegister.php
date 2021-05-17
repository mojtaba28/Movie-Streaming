<?php

require_once 'connection.php';

if(isset($_REQUEST['phone'])){

 $phone = $_REQUEST['phone'];
 
 
 $query = "SELECT * FROM users WHERE phone = '$phone' ";

	$result = mysqli_query($connection,$query);
 
	$check = mysqli_fetch_array($result);
	$response=array();
 
	if(isset($check)){
		$response['status']="exist";
	}else{
		$response['status']="not exist";;
	}
 
	echo json_encode($response);
	mysqli_close($connection);
 }else{
	 echo 'please set phone number';
 }

?>