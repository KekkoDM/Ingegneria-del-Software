<?php
function checkParameters($params) {
  //traversing through all the parameters
  foreach($params as $param){
    //if the paramter is not available
    if(!isset($_POST[$param])){
      return false;
    }
  }
 return true;
}
?>
