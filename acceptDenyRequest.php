<?php

if($_SERVER['REQUEST_METHOD']=='POST'){
  require 'init.php';
  $user_name = $_POST["selectedUsername"];

  $sql2 = "select isEmployedTo,ID from login where username = '$user_name'";
  $result2 = mysqli_query($con,$sql2);
  $row2 = mysqli_fetch_array($result2);
  $admin_id=$row2[0];
  $employeeID=$row2[1];
  
  
  $sqldays = "select daysToLeave from Leaves where UserID = '$employeeID'";
  $resultdays = mysqli_query($con,$sqldays);
  $rowdays = mysqli_fetch_array($resultdays);
  
  $sqlStartingDate="Select StartingDate from Leaves where UserID=$employeeID";
    $resultStartingDate = mysqli_query($con,$sqlStartingDate);
    $rowStartingDate = mysqli_fetch_array($resultStartingDate);
    $startingDate=$rowStartingDate[0];
  
  
  $sqlRem = "Select remainingLeaves from users where employeeID='$employeeID'";
    $resultRem = mysqli_query($con,$sqlRem);
    $rowRem=mysqli_fetch_array($resultRem);
    $remainingLeaves=$rowRem[0];
    
    $datesArray= array_fill(0,$rowdays[0],'0');
    $countdays=0;
    $sqlDates="Select scheduleDate from newschedule where employeeID = '$employeeID'";
    $resultDates = mysqli_query($con,$sqlDates);
    $rowDates= mysqli_fetch_array($resultDates);
    
    $sqlcount="Select count(scheduleDate) from newschedule where employeeID = '$employeeID'";
    $resultcount = mysqli_query($con,$sqlcount);
    $rowcount= mysqli_fetch_array($resultcount);
    
    
    
    $datesArray[$countdays]=$rowDates['0'];
    $countdays++;
    if($rowDates['0']==$startingDate){
            $temp=$countdays;
        }
    while($rowDates= $resultDates->fetch_array()){
        $datesArray[$countdays]=$rowDates['0'];
        
        if($rowDates['0']==$startingDate){
            $temp=$countdays;
        }
    $countdays++;
    }
    
    
  echo "rwo dates: ".$rowdays[0];
  $countdays=$temp;
for($i=0;$i<$rowdays[0];$i++){
    $flag=0;
    
    echo "rwo dates mesa for: ".$rowdays[0];
    echo "i: ".$i;
    echo "mphka for";

    $leaveDays=$rowdays[0];

    $sql3= "SELECT MIN(hours) AS minHours FROM userStatistics where isAssignedTo= '$admin_id' and employeeID != '$employeeID' ";
        $result3 = mysqli_query($con,$sql3);
        $row3= mysqli_fetch_array($result3);
        $minHours=$row3[0];
        echo "min hours: ".$minHours;
        $sql5="Select employeeID from userStatistics where hours='$minHours' and employeeID!='$employeeID'";
        $result5 = mysqli_query($con,$sql5);
        $row5= mysqli_fetch_array($result5);
        $workingID=$row5['employeeID'];
        echo "Workin id(willing  id to replace: ".$workingID;

if ($leaveDays<=$remainingLeaves & $workingID != null)
   {
        $sql6="Select employeeID from newschedule where scheduleDate='$datesArray[$countdays]' and AssignedTo= '$admin_id' and employeeID='$workingID'";
        $result6 = mysqli_query($con,$sql6);
        $row6= mysqli_fetch_array($result6);
        echo " 1h if result6:";
        echo "row6[0] ".$row6[0];
        
        if(!$row6[0]>0)
        {
                        echo "ID TO REPLACE: ".$workingID;
                        $date = $datesArray[$countdays];
                        echo "date: ".$date;
                          $sql = "UPDATE newschedule SET employeeID='$workingID' WHERE employeeID='$employeeID' and AssignedTo='$admin_id' and scheduleDate='$date'";
                          $result = mysqli_query($con,$sql);
                          

                          $sqlUPRem = "UPDATE users SET remainingLeaves=remainingLeaves-1 WHERE employeeID='$employeeID'";
                          $resultUPRem = mysqli_query($con,$sqlUPRem);
                         
                          
                           $sql7 = "UPDATE userStatistics SET hours=hours-8 WHERE employeeID='$employeeID' and isAssignedTo='$admin_id'";
                          $result7 = mysqli_query($con,$sql7);
                          
                        
                            $sql8 = "UPDATE userStatistics SET hours=hours+8 WHERE employeeID='$workingID' and isAssignedTo='$admin_id'";
                          $result8 = mysqli_query($con,$sql8);
                          
                          $flag=1;

                          $sqlDel = "delete from Leaves where UserID='$employeeID'";
                          $resultDel=mysqli_query($con,$sqlDel);
                          
                          echo "Succesfully accepted Leave!.";
        }
        else{
            $flag=0;
            echo "Flag = 0 1h if!.";
        }

    while($row5= $result5->fetch_array())
           {
               echo "mphka while!.";
               if($flag==0){
                   
               $workingID=$row5['employeeID'];
               echo "mphka if flag ==0 !. me id to replace";
               echo $workingID;
               $sql6="Select employeeID from newschedule where scheduleDate='$datesArray[$countdays]' and AssignedTo= '$admin_id' and employeeID='$workingID'";
                $result6 = mysqli_query($con,$sql6);
                $row6= mysqli_fetch_array($result6);
                echo "row6[0]: ".$row6[0];
                   if(!$row6[0]>0)
                    {
                          $date = $datesArray[$countdays];
                          echo "date2: ".$date;
                        echo "after delted date:->";
                          $sql10 = "UPDATE newschedule SET employeeID='$workingID' WHERE employeeID='$employeeID' and AssignedTo='$admin_id' and scheduleDate='$date'";
                          $result10 = mysqli_query($con,$sql10);
                          
                          echo "1st update.";

                          $sqlUPRem = "UPDATE users SET remainingLeaves=remainingLeaves-1 WHERE employeeID='$employeeID'";
                          $resultUPRem = mysqli_query($con,$sqlUPRem);
                          
                          
                           $sql7 = "UPDATE userStatistics SET hours=hours-8 WHERE employeeID='$employeeID' and isAssignedTo='$admin_id'";
                          $result7 = mysqli_query($con,$sql7);
                          
                        
                            $sql8 = "UPDATE userStatistics SET hours=hours+8 WHERE employeeID='$workingID' and isAssignedTo='$admin_id'";
                          $result8 = mysqli_query($con,$sql8);
                          
                          
                          echo "b4 succ.";
                          $flag=1;
                          echo "Succesfully accepted Leave!.";
                          echo "fater succ.";

                          $sqlDel = "delete from Leaves where UserID='$employeeID'";
                          $resultDel=mysqli_query($con,$sqlDel);
                           
                        
                    }
                    else{
                        $flag=0;
                        echo "2h else flag=0";
                    }
                }
                
            }
            
      if($flag==0){
          
          echo "Cant find anyone to replace.";
      }
   }
   else{
       if($workingID==null){
           echo "den mporeis na antikatastiseis ton eauto sou se adeia.";
       }
        echo "Leave not available.Not enough remaining leaves or you are not working at that day.";
   }    

$countdays++;
}
}


mysqli_close($con);

?>
