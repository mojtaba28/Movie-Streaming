<?php 

   define('DB_HOST', "localhost");
   define('DB_NAME', "movie_streaming");
   define('DB_USER', "root");
   define('DB_PASS', "");
	
	$connection = mysqli_connect(DB_HOST,DB_USER,DB_PASS,DB_NAME) or die('Unable to oconnect');
	mysqli_query($connection,"SET CHARACTER SET utf8");

?>