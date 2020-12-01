<?php

 if($_SERVER['REQUEST_METHOD']=='POST'){
   require 'init.php';
   $user_name = $_POST["username"];
   $user_password = $_POST["password"];



   $sql = "select username,ID,isEmployedTo from login where username = '$user_name' and password = '$user_password'";

   $result = mysqli_query($con,$sql);
   $row = mysqli_fetch_array($result);
   
   if(!mysqli_num_rows($result)>0)
   {
     echo "Invalid Username or Password Please Try Again";
   }
   else
   {
       if (($row[1] == $row[2]) || ($row[2]==null))
       {
           #echo "Data Matched";
           echo "Welcome Admin";
       }
       else
       {
           echo "Welcome User";
       }
   }
   
 }
mysqli_close($con);

?>
