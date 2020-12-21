<?php
//This file currently does nothing. No need to touch it until statistics are implemented. 
if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
  $user_name = $_POST["username"];
  
  $sql = "SELECT ID FROM login WHERE username = '$user_name'";
  $result = mysqli_query($con,$sql);
  $resultArray=mysqli_fetch_array($result);
  $admin_id = $resultArray[0];
  
  $sql2 ="DELETE FROM userStatistics WHERE isAssignedTo = '$admin_id'";
  $result2 = mysqli_query($con,$sql2);

  
  
 }
mysqli_close($con);

?>