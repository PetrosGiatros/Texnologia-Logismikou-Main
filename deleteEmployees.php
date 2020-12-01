<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  $user_name = $_POST["selectedUsername"];

  $sqlUsersDelete = "Select ID from login where username='$user_name'";
  $resultUsersDelete=mysqli_query($con,$sqlUsersDelete);
  $row2=mysqli_fetch_array($resultUsersDelete);
  $employeeID = $row2[0];
  $sqlUsersConfirmedDelete = "DELETE FROM users WHERE employeeID='$employeeID'";
  $result4=mysqli_query($con,$sqlUsersConfirmedDelete);
  
  $sql = "DELETE FROM login WHERE username='$user_name'";
  $sql2 = "Select username from login where username='$user_name'";
  
  
  $result = mysqli_query($con,$sql);
  $result2=mysqli_query($con,$sql2);
  $row=mysqli_fetch_array($result);

  if(!mysqli_num_rows($result2)>0)
     {
       echo "Succesfully deleted user.";
     }
     else{
       echo $row[0];
     }
        

}


mysqli_close($con);

?>
