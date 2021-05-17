<?php
require_once 'connection.php';

$header=apache_request_headers();
if (isset($header['token']) and isset($_FILES['image'])){

    $token=$header['token'];
    $image=$_FILES['image'];
   // $image_name=$_FILES['image']['name'];
    $image_name="profile.jpg";

    $query="SELECT * FROM users WHERE token='$token'";
    $res=mysqli_query($connection,$query);
    if ($res!=""){
        $row=mysqli_fetch_assoc($res);
        $user_id=$row['id'];

        $dir="content/users/user$user_id/";
        if (!is_dir($dir)){
            mkdir($dir, 0777,true);
        }

        $from = $_FILES['image']['tmp_name'];
        $to = $dir . "/" . $image_name;
        move_uploaded_file($from, $to);
        $image_path=$dir.$image_name;

        $sql="UPDATE users SET image = '$image_path' WHERE id = $user_id ";
        $result=mysqli_query($connection,$sql);
        $response=array();

        if ($result!=""){
            $response['status']="successful";
        }else{
            $response['status']="fail in update";
        }




    }else{
        $response['status']="invalid token or user does not exist";
    }

    mysqli_close($connection);
    echo json_encode($response);


}else{

    echo 'please set token and image';
}






?>