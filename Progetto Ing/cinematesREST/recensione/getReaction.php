<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$review = $_POST['review'];

// checking if the user has already reacted
$stmt = $conn->prepare("SELECT valutazione FROM valutazione WHERE utente = ? AND recensione = ?");
$stmt->bind_param("ss", $username, $review);
$stmt->execute();
$stmt->store_result();

if($stmt->num_rows > 0) {
  $stmt->bind_result($reaction);
  $stmt->fetch();

  $response['error'] = false;
  $response['message'] = 'Reazione caricata correttamente';
  $response['reazione'] = $reaction;
}
else {
  $response['error'] = true;
  $response['message'] = 'Non hai reagito a questa recensione';
}
$stmt->close();
echo json_encode($response);
?>
