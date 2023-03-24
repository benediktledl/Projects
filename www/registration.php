<?php
require("includes.php");
  class registerUser
  {
    static $result = array();

    public function registerUser($username, $password)
    {

      if (!$this->checkUsernameAvailability($username)) {
        self::$result['status'] = false;
        return self::$result;
      }
      if (!$this->checkPasswordStrength($password)) {
        self::$result['status'] = false;
        return self::$result;
      }
      if (!$this->addUserToDatabase($username, $password)) {
        self::$result['status'] = false;
        return self::$result;
      }

      self::$result['status'] = true;
      self::$result['message'] = "User was added";
      return self::$result;

    }

    private function checkUsernameAvailability($username)
    {
      global $dbconf;
      $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);

      // Prepare the query using a prepared statement
      $stmt = $mysqli->prepare("SELECT * from users WHERE username = ?");
      $stmt->bind_param("s", $username);
      $stmt->execute();

      // Get the results and check if the username is available
      $users = $stmt->get_result();
      if ($users->num_rows === 0) {
        return true;
      } else {
        self::$result['message'] = "Username already taken!";
      }
      return false;
    }

    public static function checkPasswordStrength($password)
    {
      $password_regex = "/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/";
      if (!preg_match($password_regex, $password)) {
        self::$result['message'] = "Password too weak!";
        return false;
      }
      return true;
    }

    private function addUserToDatabase($username, $password)
    {
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

      $stmt = $mysqli->prepare("INSERT INTO `users` (`userid`, `username`, `password`) VALUES (NULL, ?, ?)");
      $stmt->bind_param("ss", $username, $password_hash);
      if (!$stmt->execute()) {
        return false;
      }
      $user_id = $mysqli->insert_id;
      $pic = bin2hex(file_get_contents($_FILES['pic']['tmp_name']));
      unlink($_FILES['pic']['tmp_name']);
      $stmt = $mysqli->prepare("INSERT INTO `usermeta` (`userid`, `email`, `fname`, `lname`, `tel`, `street`, `plz`, `countrycode`, `pic`) VALUES (?, ?, ?, ?, ?, ?, ?, 'AUT', ?)");
      $stmt->bind_param("isssssss", $user_id, $email, $fname, $lname, $telephone, $street, $zip, $pic);
      if(!$stmt->execute()){
        return false;
      }

      $stmt = $mysqli->prepare("SELECT * from towns WHERE plz = ?");
      $stmt->bind_param("s", $zip);
      if(!$stmt->execute()){
        return false;
      }
      $towns = $stmt->get_result();
      if ($towns->num_rows === 0) {
        $stmt = $mysqli->prepare("INSERT INTO `towns` (`plz`, `town`, `city`, `countrycode`) VALUES (?, ?, ?)");
        $stmt->bind_param("sss", $zip, $town);
        $stmt->execute();
      }
      return true;
    }
  }

    #if(isset($_POST['action']) && $_POST['action'] == "register"){
    $user = new registerUser;
    $result = $user -> registerUser($_POST['username'], $_POST['password']);
    echo json_encode($result);
  #}
