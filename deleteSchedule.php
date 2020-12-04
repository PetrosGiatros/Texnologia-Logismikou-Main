<?php
if($_SERVER['REQUEST_METHOD']=='POST'){
    require 'init.php';
    
    $sql ="TRUNCATE TABLE schedule; ";
 
    $result = mysqli_query($con,$sql);
   

    mysqli_close($con);

}
?>
