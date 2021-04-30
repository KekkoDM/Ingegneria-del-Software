<?php
include_once '../../parameters.php';

$conn = new mysqli($host, $user, $password, $database);

// Check connection
if ($conn->connect_error) {
  die("Connessione fallita: " . $conn->connect_error);
}
?>
