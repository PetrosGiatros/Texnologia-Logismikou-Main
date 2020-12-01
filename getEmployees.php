<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  $user_name = $_POST["loggedinUsername"];

  
  $sql = "select ID from login where username = '$user_name' ";
    
  $result = mysqli_query($con,$sql);
  $row=mysqli_fetch_array($result);

  $employerID = $row[0];
  
  $sql2="select username,email from login where isEmployedTo = '$employerID'";
  
  $result2=mysqli_query($con,$sql2);
  $row2=mysqli_fetch_array($result2);
 
 echo $row2['username']." ";
 echo $row2['email']." ";

 while($row2= $result2->fetch_array())
   {
       echo $row2['username']." ".$row2['email']." ";
   }
  
  
 # if(!mysqli_num_rows($result2)>0){
 #   echo "Error";
 # }
 # else{
 #  echo $row2['username'];
 # }
}

mysqli_close($con);

?>
