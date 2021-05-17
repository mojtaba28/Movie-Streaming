<?php

require_once 'connection.php';

$query = " SELECT * FROM genre ";

$result = mysqli_query($connection,$query);


if($result){
	
	
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		
		
		$genre = array();
		
		$genre['id'] = $row ['id'];
		$genre['name'] = $row ['name'];
		$genre['image_name'] = $row ['image_name'];
	
		
		array_push($response,$genre);
		
	}
	
	
}else{
	
	echo "Failed";
	
}

echo json_encode($response);

mysqli_close($connection);



?>