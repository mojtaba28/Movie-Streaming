<?php

require_once 'connection.php';
$header = apache_request_headers();
if (isset($header['token'])){

    $token = $header['token'];

    $query = "SELECT * FROM users WHERE token='$token'";

    $result = mysqli_query($connection,$query);

    $response=array();
    if($result!=""){


        $row=mysqli_fetch_assoc($result);


            $response['image'] = $row ['image'];
            if (empty($row['image'])){
                $response['status']="fail";
            }else{
                $response['status']="successful";
            }


    }else{

        $response['status']="fail";

    }

    echo json_encode($response);

    mysqli_close($connection);


}else{
    echo 'please set token';
}
?>