<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$comment = $_POST['comment'];
$review = $_POST['review'];

$stmt = $conn->prepare("INSERT INTO commento(testo, utente, recensione) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $comment, $username, $review);

if($stmt->execute()) {
  $response['error'] = false;
  $response['message'] = 'Commento inserito correttamente';
}
else {
  $response['error'] = true;
  $response['message'] = 'Errore durante inserimento del commento';
}

$stmt->close();
echo json_encode($response);
?>
