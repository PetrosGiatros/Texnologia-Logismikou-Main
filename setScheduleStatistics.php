<?php
//This file currently does nothing. No need to touch it until statistics are implemented. 
if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
  $user_name = $_POST["username"];
  $schedule_type = $_POST["scheduleType"];
  $business_type = $_POST["businessType"];
  $peoplePerShift = $_POST["peoplePerShift"];
  $Programmer = $_POST["Programmer"];
  $Analyst = $_POST["Analyst"];
  $Manager = $_POST["Manager"];
  
  $sql = "SELECT ID FROM login WHERE username = '$user_name'";
  $result = mysqli_query($con,$sql);
  $resultArray=mysqli_fetch_array($result);
  $admin_id = $resultArray[0];
  
  $sql2 ="DELETE FROM scheduleStatistics WHERE isAssignedTo = '$admin_id'";
  $result2 = mysqli_query($con,$sql2);
  
  $sql3 = "INSERT INTO scheduleStatistics (scheduleType,businesstype,peoplePerShift,isAssignedTo,programmerHours,analystHours,managerHours) VALUES ('$schedule_type','$business_type','$peoplePerShift','$admin_id','$Programmer','$Analyst','$Manager')";
   $result3 = mysqli_query($con,$sql3);
  
echo $Manager;
  
 }
mysqli_close($con);

?>