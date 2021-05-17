<?php

require_once 'connection.php';

if(isset($_REQUEST['movie_id'])){
	
	$movie_id=$_REQUEST['movie_id'];
	$query = " SELECT * FROM actors WHERE movie_id = '$movie_id' ";

	$result = mysqli_query($connection,$query);


	if($result){
	
	
	$response ['actors'] = array();
	
	while($row = mysqli_fetch_array($result)){
		
		
		$actors = array();
		
		$actors['id'] = $row ['id'];
		$actors['movie_id'] = $row ['movie_id'];
		$actors['name'] = $row ['name'];
		$actors['image_name'] = $row ['image_name'];
	
		
		array_push($response ['actors'],$actors);
		
	}
	
	
}else{
	
	echo json_encode("Failed");
	
}

echo json_encode($response);

mysqli_close($connection);
	
}else echo 'please first set movie_id';




?>