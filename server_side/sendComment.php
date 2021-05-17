<?php

require_once 'connection.php';

$header = apache_request_headers();

if (isset($header['token']) and isset($_REQUEST['movie_id']) and isset($_REQUEST['comment']) and isset($_REQUEST['spoil'])) {


    $movie_id = $_REQUEST['movie_id'];
    $comment = $_REQUEST['comment'];
    $spoil = $_REQUEST['spoil'];

    $date = date("Y-m-d");
    $token = $header['token'];

    $tokenSql = "SELECT * FROM users WHERE token='$token'";
    $tokenResult = mysqli_query($connection, $tokenSql);

    if ($tokenResult != "") {

        $row=mysqli_fetch_assoc($tokenResult);
        $user_id=$row['id'];

        $query = "INSERT INTO comment (movie_id , user_id , comment , spoil , date ) VALUES ('$movie_id','$user_id', '$comment','$spoil','$date' ) ";

        $result = mysqli_query($connection, $query);

        if ($result != "") {
            $response['status'] = 'successful';

        } else {
            $response['status'] = 'fail';
        }
    } else {
        $response['status'] = 'invalid token';
    }


    mysqli_close($connection);
    echo(json_encode($response));


} else {

    echo 'please enter token,movie_id,comment,spoil';

}


?>