<?php
require("includes.php");

session_start();
session_destroy();

class kuzologin
{
  static $result = array(
    "status"  => false,
    "message" => ""
  );

  public static function kuzologin($username, $password){
    global $config;
    global $dbconf;
    $salt = $config['kuzologin']['salt'];
    $password_salted = hash_hmac("sha256", $password, $salt);
    unset($password);

    $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);
    // Retrieve the user record based on the username
    $user_query = $mysqli->query("SELECT * FROM users WHERE username = \"$username\"");

     if ($user_query->num_rows == 1) {
       // If the username is correct, retrieve the user's password hash
       $user = $user_query->fetch_assoc();
       $password = $user['password'];
        // Verify the password
       if (password_verify($password_salted, $password)) {
         // Password is correct, user is authenticated
         session_start();
         $_SESSION['user'] = $username;
         self::$result['status'] = true;
         self::$result['message'] = "successfully logged in";
       } else {
        // Password is incorrect
        self::$result['status'] = false;
        self::$result['message'] = "password incorrect!";
      }
      } else {
       self::$result['status'] = false;
       self::$result['message'] = "user does not exist!";
    }
    return self::$result;
  }
}

if(isset($_POST['action']) && $_POST['action'] == "login"){
  $result = kuzologin::kuzologin($_POST['Username'], $_POST['Passwort']);
  echo json_encode($result);
}
