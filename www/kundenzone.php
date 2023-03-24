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

    $stmt = $mysqli->prepare("SELECT * FROM remind_key_temp WHERE `key` = ?");
    $stmt->bind_param("s", $key);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
      $row = $result->fetch_assoc();
      $expDate = strtotime($row['expDate']);
      if (time() <= $expDate) {
        // key is valid
        $email = $row['email'];
        $new_password = $_POST['Passwort'];
        if (!registerUser::checkPasswordStrength($new_password)) {
          // Password is not strong enough
          // Return an error message or redirect the user
          exit("Password too weak!");
        }
        // Hash the new password using the same salt as during registration
        $salt = $config['kuzologin']['salt'];
        $password_salted = hash_hmac("sha256", $new_password, $salt);
        $password_hash = password_hash($password_salted, PASSWORD_ARGON2ID);
        // Update the password hash for the user
        $stmt = $mysqli->prepare("UPDATE users SET password = ? WHERE email = ?");
        $stmt->bind_param("ss", $password_hash, $email);
        $stmt->execute();
        if ($stmt->affected_rows < 1) {
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
    $stmt = $mysqli->prepare("SELECT * from users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows == 0) {
      header("location: login.php?remind=user");
      exit;
    }
    $user = $result->fetch_assoc();
    $userid = $user['userid'];

    $stmt = $mysqli->prepare("SELECT * from usermeta WHERE userid = ?");
    $stmt->bind_param("i", $userid);
    $stmt->execute();
    $result = $stmt->get_result();

    $usermeta = $result->fetch_assoc();
    $email = $usermeta['email'];

    // check if key already exists for email address
    $stmt = $mysqli->prepare("SELECT * from remind_key_temp WHERE email = ?");
    $stmt->bind_param("s", $email);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
      // key already exists, update it instead of inserting new
      $key = bin2hex(random_bytes(16));
      $expDate = date('Y-m-d H:i:s', strtotime('+30 minutes'));
      $stmt = $mysqli->prepare("UPDATE `remind_key_temp` SET `key`=?, `expDate`=? WHERE `email`=?");
      $stmt->bind_param("sss", $key, $expDate, $email);
      $stmt->execute();
    } else {
      // generate new key and insert into database
      $key = bin2hex(random_bytes(16));
      $expDate = date('Y-m-d H:i:s', strtotime('+30 minutes'));
      $stmt = $mysqli->prepare("INSERT INTO `remind_key_temp` (`email`, `key`, `expDate`) VALUES (?, ?, ?)");
      $stmt->bind_param("sss", $email, $key, $expDate);
      $stmt->execute();
    }

    header("location: login.php?remind=1");
    exit;
  }


  private function updateData(){
    global $dbconf;

    $username = $_SESSION['user'];
    $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);

    $stmt = $mysqli->prepare("SELECT userid FROM users WHERE username = ?");
    $stmt->bind_param("s", $username);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows === 0) {
      // No user found
      // Return an error message or redirect the user
      exit("Error: user not found!");
    }

    $user = $result->fetch_assoc();
    $userid = $user['userid'];

    $fname = $_POST['fname'];
    $lname = $_POST['lname'];
    $tel = $_POST['phone'];
    $email = $_POST['email'];

    $stmt = $mysqli->prepare("UPDATE usermeta SET fname=?, lname=?, email=?, tel=? WHERE userid=?");
    $stmt->bind_param("ssssi", $fname, $lname, $email, $tel, $userid);
    $result = $stmt->execute();

    if (!$result) {
      // Error updating user data
      // Return an error message or redirect the user
      exit("Error updating user data!");
    }

    // User data updated successfully
    // Redirect the user or display a success message
    header("Location: kundenzone.php");
    exit;
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

    $stmt_user = $mysqli->prepare("SELECT * FROM users WHERE username = ?");
    $stmt_user->bind_param("s", $username);
    $stmt_user->execute();
    $user = $stmt_user->get_result()->fetch_assoc();
    $stmt_user->close();

    $userid = $user['userid'];

    $stmt_usermeta = $mysqli->prepare("SELECT * FROM usermeta WHERE userid = ?");
    $stmt_usermeta->bind_param("i", $userid);
    $stmt_usermeta->execute();
    $usermeta = $stmt_usermeta->get_result()->fetch_assoc();
    $stmt_usermeta->close();

    $plz = $usermeta['plz'];

    $stmt_townmeta = $mysqli->prepare("SELECT * FROM towns WHERE plz = ?");
    $stmt_townmeta->bind_param("s", $plz);
    $stmt_townmeta->execute();
    $townmeta = $stmt_townmeta->get_result()->fetch_assoc();
    $stmt_townmeta->close();

    include_once("html_kuzo.php");

    include_once("search_users_form.php");
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
