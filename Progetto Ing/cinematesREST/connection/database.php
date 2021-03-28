<?php
$host = "cinemates-mysql.cxnubr8ec2ra.eu-central-1.rds.amazonaws.com:3306";
$database = "cinemates";
$user = "root";
$password = "unina2021";

$conn = new mysqli($host, $user, $password, $database);

// Check connection
if ($conn->connect_error) {
  die("Connessione fallita: " . $conn->connect_error);
}
?>
