<?php

require_once 'connection.php';

 if(isset($_REQUEST['category'])){
	 
	 $category=$_REQUEST['category'];
	 $query = " SELECT * FROM movies WHERE category = '$category' ";
	 
 }else if(isset($_REQUEST['popular'])){
	 
	 $popular=$_REQUEST['popular'];
	 $query = " SELECT * FROM movies WHERE popular = '$popular' ";
	 
	 
 }else if(isset($_REQUEST['genre'])){
	 
	 $genre=$_REQUEST['genre'];
	 $query="SELECT * FROM movies WHERE genre LIKE '%$genre%'";
	 
	 
 }else if(isset($_REQUEST['search'])){
	 
	 $search=$_REQUEST['search'];
	 if($search==''){
		 
		 $r['movies'] = array();
		 echo json_encode($r);
		 
	 }else{
		  $query="SELECT * FROM movies WHERE name LIKE '%$search%'";
	 }
	
	 
	 
 }





 if(isset($_REQUEST['category']) or isset($_REQUEST['popular']) or isset($_REQUEST['genre']) or isset($_REQUEST['search'])){
	 
   $result = mysqli_query($connection,$query);
   
   if($result){

	
	
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		
		
		$movies = array();
		
		$movies['id'] = $row ['id'];
		$movies['name'] = $row ['name'];
		$movies['image_name'] = $row ['image_name'];
		$movies['time'] = $row ['time'];
		$movies['published'] = $row ['published'];
		$movies['category'] = $row ['category'];
		$movies['rate'] = $row ['rate'];
		$movies['imdb_rank'] = $row ['imdb_rank'];
		$movies['description'] = $row ['description'];
		$movies['director'] = $row ['director'];
		$movies['box_office'] = $row ['box_office'];
		$movies['budget'] = $row ['budget'];
		$movies['genre'] = $row ['genre'];
		$movies['movie_preview'] = $row ['movie_preview'];
		$movies['movie_poster'] = $row ['movie_poster'];
		$movies['popular'] = $row ['popular'];
		
		array_push($response ,$movies);
		
	}
	
	
}else{
	
	echo "Failed";
	
}

echo json_encode($response);

mysqli_close($connection);

 }else{
	 echo 'please specify type of movie';
 }



?>