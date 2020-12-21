<?php
//This file currently does nothing. No need to touch it until statistics are implemented. 
if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  
  $user_name = $_POST["username"];
  
  $sql = "SELECT ID FROM login WHERE username = '$user_name'";
  $result = mysqli_query($con,$sql);
  $resultArray=mysqli_fetch_array($result);
  $admin_id = $resultArray[0];
  
  $sql2 ="SELECT scheduleType,businessType,peoplePerShift,programmerHours,analystHours,managerHours FROM scheduleStatistics WHERE isAssignedTo = '$admin_id'";
  $result2 = mysqli_query($con,$sql2);
  
  $sql3 = "SELECT COUNT(profession) FROM users,login WHERE isEmployedTo = '$admin_id' AND users.employeeID = login.ID AND users.profession='Programmer'";
  $result3 = mysqli_query($con,$sql3);
  $row3=mysqli_fetch_array($result3);
  
  $sql4 = "SELECT COUNT(profession) FROM users,login WHERE isEmployedTo = '$admin_id' AND users.employeeID = login.ID AND users.profession='Analyst'";
  $result4 = mysqli_query($con,$sql4);
  $row4=mysqli_fetch_array($result4);
  
  $sql5 = "SELECT COUNT(profession) FROM users,login WHERE isEmployedTo = '$admin_id' AND users.employeeID = login.ID AND users.profession='Manager'";;
  $result5 = mysqli_query($con,$sql5);
  $row5=mysqli_fetch_array($result5);
 

  while($row2= $result2->fetch_array())
   {
       echo $row2[0]." ";
       echo $row2[1]." ";
       echo $row2[2]." ";
       echo $row2[3]." ";
       echo $row2[4]." ";
       echo $row2[5]." ";
       echo $row3[0]." ";
       echo $row4[0]." ";
       echo $row5[0]." ";
   }
  
 }
mysqli_close($con);

?>