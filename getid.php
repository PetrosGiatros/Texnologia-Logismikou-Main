<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
    $loggedinUsername = $_POST["loggedinUsername"];
    $user_name = $_POST["username"];
    $user_password = $_POST["password"];
    $email = $_POST["email"];
    

  $sql = "select ID from login where username = '$loggedinUsername' ";
    
  $result = mysqli_query($con,$sql);
    $row=mysqli_fetch_array($result);
 
  if(!mysqli_num_rows($result)>0){
    echo "Invalid Username or Password Please Try Again";
  }
  else{
    echo $row[0];
  }
  
$isEmployedTo = $row[0];
 #$CheckSQL = "SELECT * FROM employer WHERE email='$email'";
 #$check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));

    $Sql_Query = "INSERT INTO login (username,password,email,isEmployedTo) values ('$user_name','$user_password','$email','$isEmployedTo')";

    if(mysqli_query($con,$Sql_Query))
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
