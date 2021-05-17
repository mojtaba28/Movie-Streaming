
<?php

require_once 'connection.php';

if (isset($_REQUEST['user_id']) and isset($_REQUEST['old_password']) and isset($_REQUEST['new_password'])){

    $userId=$_REQUEST['user_id'];
    $oldPassword=md5($_REQUEST['old_password']);
    $newPassword=md5($_REQUEST['new_password']);

    $query = "SELECT * FROM users WHERE id='$userId' AND password='$oldPassword'";

    $result = mysqli_query($connection,$query);

    if ($result!=""){

		$query2="UPDATE users SET password='$newPassword' WHERE id = $userId ";
        $result2 = mysqli_query($connection,$query2);
        if ($result2!=""){

		$response['status']='successful';
           

        }else{
			
             $response['status']='fail';
        }
        

    }else{
		
		$response['status']='old password is not correct';

        
    }

    mysqli_close($connection);
    echo (json_encode($response));




}else{
    echo 'please first set user_id,old_password,new_password';
}

?>