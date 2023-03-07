<?php

class kundenzone
{
  static $kunde = array();
  public function renderKundenzone(){
    if(!self::checkSession()){
      return;
    }
    include_once("html_head.html");
    ?>

    <body>
    <?php
    include_once("html_kuzobar.php");
  }

  private function checkSession(){
    session_start();
    if(!isset($_SESSION['user'])){
      header("location: login.php?session=expired");
      return false;
    }
    return true;
  }
}

$kuzo = new kundenzone;
$kuzo -> renderKundenzone();
