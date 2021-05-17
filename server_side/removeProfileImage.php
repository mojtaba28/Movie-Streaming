<?php
require_once 'connection.php';
require_once 'functions.php';
$header = apache_request_headers();

if (isset($header['token'])){

    $token=$header['token'];
    $sql="SELECT * FROM users where token='$token'";
    $result=mysqli_query($connection,$sql);

    $response=array();

    if ($result!=""){

        $row=mysqli_fetch_assoc($result);

        $userId=$row['id'];

        $dir="content/users/user".$userId;
       $isDeleted= delDir($dir);
        if ($isDeleted!=""){
            $response['status']="successful";
            $removeSql="UPDATE users SET image = '' WHERE id = $userId ";
            $removeResult=mysqli_query($connection,$removeSql);
        }else{
            $response['status']="fail in delete";
        }




    }else{
        $response['status']="token is invalid or user does not exist";
    }

    echo json_encode($response);


}else{
    echo 'please set token first';
}



?>