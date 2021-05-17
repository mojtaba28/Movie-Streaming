<?php include ("connection.php");?>

<?php
	if(isset($_REQUEST['phone']) and isset($_REQUEST['password'])){

		
		$phone = $_REQUEST['phone'];
		$password = md5($_REQUEST['password']);
		
		$query = "SELECT * FROM users WHERE phone='$phone' AND password='$password'";

		
		//executing query
    $result = mysqli_query($connection,$query);

    //fetching result
    $check = mysqli_fetch_array($result);
    
    if(isset($check)){
		
			
			
	function getToken($length){
		
	$key = '';
    $keys = array_merge(range(0, 9), range('a', 'z'));

    for ($i = 0; $i < $length; $i++) {
        $key .= $keys[array_rand($keys)];
    }

    return $key;
	
	}
	
	$token=getToken(32);
		
		$query2 = "UPDATE users SET token = '$token' WHERE phone='$phone' AND password='$password'";
		$result2 = mysqli_query($connection,$query2);
		
		
		if($result2){
		
		$response['status']='successful';
		$response['token']=$token;
		$response['id'] = $check['id'];
		$response['name'] = $check['name'];	
		$response['last_name'] = $check['last_name'];	
		
	
	
}
           
    }else {
	
	$response['status'] = 'fail';
	
}
	
	mysqli_close($connection);
	echo (json_encode($response));
	
	}else{
		echo'please set user phone and password';
	}

?>