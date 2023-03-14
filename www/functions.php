<?php
function isLoggedIn(){
  if(isset($_SESSION['user'])){
    return true;
  }
  return false;
}
