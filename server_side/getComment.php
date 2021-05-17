<?php

require_once 'connection.php';

if(isset($_REQUEST['movie_id'])){
	
	$movie_id=$_REQUEST['movie_id'];
	$query = " SELECT * FROM comment WHERE movie_id = '$movie_id' AND confirmation=1 ";

	$result = mysqli_query($connection,$query);


	if($result){
	
	
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		
		
		$comment = array();
		$query2 = " SELECT * FROM users WHERE id = '$row[user_id]' ";
		$result2 = mysqli_query($connection,$query2);
		while($row2=mysqli_fetch_array($result2)){
			
			$comment['image']=$row2['image'];
			$comment['name']=$row2['name'];
			
		}
		
		$comment['id'] = $row ['id'];
		$comment['movie_id'] = $row ['movie_id'];
		$comment['user_id'] = $row ['user_id'];
		$comment['comment'] = $row ['comment'];
		$comment['date'] = $row ['date'];
		$comment['spoil'] = $row ['spoil'];
		
		
	
		
		array_push($response,$comment);
		
	}
	
	
}else{
	
	echo "failed";
	
}

echo json_encode($response);

mysqli_close($connection);
	
}else echo 'please first set movie_id';




?>