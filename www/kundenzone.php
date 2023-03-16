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
        case "remind":
          self::remind();
          break;
        case "reset":
          self::checkRemindKey();
          break;
      }
    }

    self::initKundenzone();
  }

  private function checkRemindKey(){
    global $dbconf;
    global $config;

    require_once("registration.php");

    $key = $_POST['key'];
    $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);

    $result = $mysqli->query("SELECT * from remind_key_temp WHERE `key`='$key'");
    if ($result->num_rows > 0) {
      $row = $result->fetch_assoc();
      $expDate = strtotime($row['expDate']);
      if (time() <= $expDate) {
        // key is valid
        $email = $row['email'];
        $new_password = $_POST['Passwort'];
        if (!registerUser->checkPasswordStrength($new_password)) {
          // Password is not strong enough
          // Return an error message or redirect the user
          exit("Password too weak!");
        }
        // Hash the new password using the same salt as during registration
        $salt = $config['kuzologin']['salt'];
        $password_salted = hash_hmac("sha256", $new_password, $salt);
        $password_hash = password_hash($password_salted, PASSWORD_ARGON2ID);
        // Update the password hash for the user
        $update = $mysqli->query("UPDATE users SET password='$password_hash' WHERE email='$email'");
        if (!$update) {
          // Error updating the password
          // Return an error message or redirect the user
          exit("Error updating password!");
        }
        // Password updated successfully
        // Redirect the user or display a success message
        header("Location: login.php");
        exit;
      } else {
        // Key has expired
        // Return an error message or redirect the user
        exit("Key has expired!");
      }
    } else {
      // Key is not valid
      // Return an error message or redirect the user
      exit("Invalid key!");
    }
  }

  private function remind(){
    global $dbconf;
    $username = $_POST['Username'];
    $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);

    // check if username exists
    $user = $mysqli->query("SELECT * from users WHERE username =\"$username\"");
    if ($user->num_rows == 0) {
      header("location: login.php?remind=user");
      exit;
    }
    $user = $user->fetch_assoc();
    $userid = $user['userid'];

    $usermeta = $mysqli->query("SELECT * from usermeta WHERE userid =\"$userid\"");
    $usermeta = $usermeta->fetch_assoc();
    $email = $usermeta['email'];

    // check if key already exists for email address
    $result = $mysqli->query("SELECT * from remind_key_temp WHERE email ='$email'");
    if ($result->num_rows > 0) {
      // key already exists, update it instead of inserting new
      $key = bin2hex(random_bytes(16));
      $expDate = date('Y-m-d H:i:s', strtotime('+30 minutes'));
      $mysqli->query("UPDATE `remind_key_temp` SET `key`='$key', `expDate`='$expDate' WHERE `email`='$email'");
    } else {
      // generate new key and insert into database
      $key = bin2hex(random_bytes(16));
      $expDate = date('Y-m-d H:i:s', strtotime('+30 minutes'));
      $mysqli->query("INSERT INTO `remind_key_temp` (`email`, `key`, `expDate`) VALUES ('$email', '$key', '$expDate') ");
    }

    header("location: login.php?remind=1");
    exit;
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
