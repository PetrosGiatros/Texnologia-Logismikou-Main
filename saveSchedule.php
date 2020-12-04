<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
    require 'init.php';
    

    $shiftName = $_POST["shiftName"];
    
    $textMorning=$_POST["textMorning"];
    $textAfternoon=$_POST["textAfternoon"];
    $textMidnight=$_POST["textMidnight"];
    
    $hasMorning=$_POST["hasMorning"];
    $hasAfternoon=$_POST["hasAfternoon"];
    $hasMidnight=$_POST["hasMidnight"];
    
    if($hasMorning=="yes" && $hasAfternoon=="yes" && $hasMidnight=="yes"){
        $sql= "INSERT INTO schedule (Morning,Afternoon,Midnight) VALUES ('$textMorning','$textAfternoon','$textMidnight')";
        mysqli_query($con,$sql);
        echo "Succesfully added morning,afternoon,midnight";
    }elseif($hasMorning=="yes" && $hasAfternoon=="yes" && $hasMidnight==""){
        $sql= "INSERT INTO schedule (Morning,Afternoon) VALUES ('$textMorning','$textAfternoon')";
        mysqli_query($con,$sql);
        echo "Succesfully added morning,afternoon";
    }elseif($hasMorning=="yes" && $hasAfternoon=="" && $hasMidnight==""){
        $sql= "INSERT INTO schedule (Morning) VALUES ('$textMorning')";
        mysqli_query($con,$sql);
        echo "Succesfully added morning";
    }else{
        echo "SFALMA ADELFE MOY STO textToSend.";
    }
      
    
   

     
  
    
    
    
        
    
    mysqli_close($con);
}

?>
