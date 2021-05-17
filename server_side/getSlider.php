<?php

require_once 'connection.php';

$query = " SELECT * FROM slider ";

$result = mysqli_query($connection,$query);


if($result){
	
	
	$response= array();

	while($row = mysqli_fetch_array($result)){

		$slider = array();
        $slider['id'] = $row ['id'];
        $slider['slider_poster'] = $row ['poster'];
        $slider['movie_id'] = $row ['movie_id'];

        $query2 = " SELECT * FROM movies WHERE id = '$row[movie_id]' ";
        $result2 = mysqli_query($connection,$query2);
        
        while($row2=mysqli_fetch_array($result2)){

            $slider['name'] = $row2 ['name'];
            $slider['image_name'] = $row2 ['image_name'];
            $slider['time'] = $row2 ['time'];
            $slider['published'] = $row2 ['published'];
            $slider['category'] = $row2 ['category'];
            $slider['rate'] = $row2 ['rate'];
            $slider['imdb_rank'] = $row2 ['imdb_rank'];
            $slider['description'] = $row2 ['description'];
            $slider['director'] = $row2 ['director'];
            $slider['box_office'] = $row2 ['box_office'];
            $slider['budget'] = $row2 ['budget'];
            $slider['genre'] = $row2 ['genre'];
            $slider['movie_preview'] = $row2 ['movie_preview'];
            $slider['movie_poster'] = $row2 ['movie_poster'];
            $slider['popular'] = $row2 ['popular'];

        }




		
		array_push($response,$slider);
		
	}
	
	
}

echo json_encode($response);

mysqli_close($connection);



?>