<?php
require("includes.php");
  class registerUser{
    static $result = array();
    public function registerUser($username, $password){

      if(!$this->checkUsernameAvailability($username)){
        self::$result['status'] = false;
        return self::$result;
      }
      if(!$this->checkPasswordStrength($password)){
        self::$result['status'] = false;
        return self::$result;
      }
      if(!$this->addUserToDatabase($username, $password)){
        self::$result['status'] = false;
        return self::$result;
      }

      self::$result['status'] = true;
      self::$result['message'] = "username was added";
      return self::$result;

    }
    private function checkUsernameAvailability($username){
      global $dbconf;
      $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);
      $users = $mysqli->query("SELECT * from users WHERE username =\"$username\"");
      if( $users->num_rows === 0 ){
        return true;
      }
      else {
        self::$result['message'] = "Username already taken!";
      }
      return false;
    }
    private function checkPasswordStrength($password){
      $password_regex = "/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/";
      if(!preg_match($password_regex, $password)){
        self::$result['message'] = "Password too weak!";
        return false;
      }
      return true;
    }
    private function addUserToDatabase($username, $password){
      global $config;
      global $dbconf;
      $salt = $config['kuzologin']['salt'];
      $password_salted = hash_hmac("sha256", $password, $salt);
      $password_hash = password_hash($password_salted, PASSWORD_ARGON2ID);
      $fname = $_POST['fname'];
      $lname = $_POST['lname'];
      $telephone = $_POST['telephone'];
      $street = $_POST['street'];
      $town = $_POST['town'];
      $zip = $_POST['zip'];
      $state = $_POST['state'];
      $email = $_POST['email'];
      $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);
      return $mysqli->query("INSERT INTO `users` (`userid`, `username`, `password`, `fname`, `lname`, `telephone`, `street`, `town`, `zip`, `state`, `email`) VALUES (NULL, '$username', '$password_hash', '$fname', '$lname', '$telephone', '$street', '$town', '$zip', '$state', '$email') ");
    }
  }
  $user = new registerUser;
  $result = $user -> registerUser($_POST['username'], $_POST['password']);
  echo json_encode($result);
