
<?php

require_once 'connection.php';

if (isset($_REQUEST['filter']) and isset($_REQUEST['search'])) {

    $filter = $_REQUEST['filter'];
    $search = $_REQUEST['search'];

    $response ['movies'] = array();

    if ($search == "") {
        echo json_encode($response);
    } else if ($filter == "movie_name") {
        $query = "SELECT * FROM movies WHERE name LIKE '%$search%'";

    } else if ($filter == "director") {
        $query = "SELECT * FROM movies WHERE director LIKE '%$search%'";

    } else if ($filter == "genre") {

        $query = "SELECT * FROM movies WHERE genre LIKE '%$search%'";
    }
    else if ($filter == "year") {

        $query = "SELECT * FROM movies WHERE published LIKE '%$search%'";
    }

    if ($search!=null){
        $result = mysqli_query($connection, $query);

        if ($result) {

            //$response['status'] = "successful";


            while ($row = mysqli_fetch_array($result)) {

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

                array_push($response ['movies'], $movies);


            }


        } else {

            $response['status'] = "fail";

        }

        echo json_encode($response);
        mysqli_close($connection);
    }



} else {

    echo 'please set filter and search query';
}


?>