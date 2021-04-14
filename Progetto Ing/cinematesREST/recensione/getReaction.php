<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$review = $_POST['review'];

$stmt = $conn->prepare("SELECT COUNT(valutazione) FROM valutazione WHERE recensione = ?");
$stmt->bind_param("s", $review);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($cont);
$stmt->fetch();

// checking if the user has already reacted
$stmt = $conn->prepare("SELECT valutazione FROM valutazione WHERE utente = ? AND recensione = ?");
$stmt->bind_param("ss", $username, $review);
$stmt->execute();
$stmt->store_result();
$stmt->bind_result($reaction);
$stmt->fetch();

if($stmt->num_rows > 0) {
  $response['error'] = false;
  $response['message'] = 'Reazione caricata correttamente';
  $response['reazione'] = $reaction;
  $response['contatore'] = $cont;
}
else {
  $response['error'] = true;
  $response['message'] = 'Non hai reagito a questa recensione';
  $response['reazione'] = $reaction;
  $response['contatore'] = $cont;
}
$stmt->close();
echo json_encode($response);
?>
