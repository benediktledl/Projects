<?php
require("includes.php");
class kundenzone
{
  static $kunde = array();

  public function __construct(){
    if (session_status() === PHP_SESSION_NONE) {
      session_start();
    }
    if(isset($_POST['action'])){
      switch($_POST['action']) {
        case "updateData":
          self::updateData();
          break;
      }
    }

    self::initKundenzone();
  }

  private function updateData(){
      global $dbconf;

      $username = $_SESSION['user'];

      $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);
      $user = $mysqli->query("SELECT * from users WHERE username =\"$username\"");
      $user = $user->fetch_assoc();

      $userid = $user['userid'];
      $fname = $_POST['fname'];
      $lname = $_POST['lname'];
      $tel = $_POST['phone'];
      $email = $_POST['email'];
      $alter = $mysqli->query("UPDATE usermeta SET fname='$fname', lname='$lname', email='$email', tel='$tel'  WHERE userid='$userid'");
  }

  private function initKundenzone(){
    if(!self::checkSession()){
      return;
    }


    include_once("html_head.html");


    ?>

    <body>
    <?php
    include_once("html_kuzobar.php");


    self::renderKuzo($_SESSION['user']);

  }

  private function renderKuzo($username){
    global $dbconf;
    $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);
    $user = $mysqli->query("SELECT * from users WHERE username =\"$username\"");
    $user = $user->fetch_assoc();

    $userid = $user['userid'];

    $usermeta = $mysqli->query("SELECT * from usermeta WHERE userid =\"$userid\"");
    $usermeta = $usermeta->fetch_assoc();

    $plz = $usermeta['plz'];

    $townmeta = $mysqli->query("SELECT * from towns WHERE plz =\"$plz\"");
    $townmeta = $townmeta->fetch_assoc();

    include_once("html_kuzo.php");

  }

  private function checkSession(){
    if(!isset($_SESSION['user'])){
      header("location: login.php?session=expired");
      return false;
    }
    return true;
  }
}
if(isset($_POST['action'])){
  session_start();



}
$kuzo = new kundenzone;
