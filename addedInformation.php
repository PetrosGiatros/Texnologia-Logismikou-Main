<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
    $firstName = $_POST["firstName"];
    $lastName = $_POST["lastName"];
    $profession = $_POST["profession"];
    $hours = $_POST["hours"];
	$user_name = $_POST["username"];
    

	$sql = "select ID from login where username = '$user_name'";
    
	$result = mysqli_query($con,$sql);
    $row=mysqli_fetch_array($result);
  
	$employeeID = $row[0];
	
    $Sql_Query = "INSERT INTO users (employeeID,firstName,lastName,profession,hoursWorking) values ('$employeeID','$firstName','$lastName','$profession','$hours')";
    
     if(mysqli_query($con,$Sql_Query))
        {
         echo "Additional Information added";
        }
    else
        {
         echo "Something went wrong";
        }
  
mysqli_close($con);
}
?>