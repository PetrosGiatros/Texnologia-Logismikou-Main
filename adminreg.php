<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
    $user_name = $_POST["username"];
    $user_password = $_POST["password"];
    $email = $_POST["email"];
    

  $sql = "INSERT INTO login (username, password, email) values ('$user_name','$user_password','$email')";
    
  $result = mysqli_query($con,$sql);
 
    if(mysqli_query($con,$sql))
        {
         echo "Registration Successful";
        }
    else
        {
         echo "Something went wrong";
        }
}
  

mysqli_close($con);

?>
