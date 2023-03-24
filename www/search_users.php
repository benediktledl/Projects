<?php

require("includes.php");

global $dbconf;

include_once("html_head.html");


?>

<body>

<?php
include_once("html_kuzobar.php");

?>

<button onclick="history.back()">Zurück</button>

<?php

// Check if form was submitted
if(isset($_POST['username'])) {
  // Get the search query from the form
  $username = '%' . $_POST['username'] . '%'; // add wildcard to search term

  // Connect to the database
  $mysqli = new mysqli($dbconf['hostname'], $dbconf['username'], $dbconf['password'], $dbconf['dbname']);

  // Prepare the statement for retrieving user information
  $stmt_user = $mysqli->prepare("SELECT * FROM users WHERE username LIKE ?");
  $stmt_user->bind_param("s", $username);
  $stmt_user->execute();

  // Get the user's information
  $result = $stmt_user->get_result();
  $stmt_user->close();

  // If one or more users were found, display their information
  if ($result->num_rows > 0) {
    echo "<div class='container flex-wrap search'>";
    while ($user = $result->fetch_assoc()) {
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

      echo "<div class='card'>";
      // Output the user's information
      echo "<p>Username: " . $user['username'] . "</p>";
      echo "<p>Email: " . $usermeta['email'] . "</p>";
      echo "<p>First Name: " . $usermeta['fname'] . "</p>";
      echo "<p>Last Name: " . $usermeta['lname'] . "</p>";
      echo "<p>Telephone: " . $usermeta['tel'] . "</p>";
      echo "<p>Street: " . $usermeta['street'] . "</p>";
      echo "<p>Town: " . $townmeta['town'] . "</p>";
      echo "<p>City: " . $townmeta['city'] . "</p>";
      echo "<p>Postal Code: " . $plz . "</p>";
      echo "</div>";
    }
    echo "</div>";
  } else {
    // No user found with the given username
    echo "<p>No users found with the username: " . $username . "</p>";
  }
}
?>
