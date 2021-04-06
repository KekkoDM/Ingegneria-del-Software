<?php
include_once '../connection/database.php';

$response = array();
$review = $_POST['review'];

//creating the query
$stmt = $conn->prepare("SELECT idcommento, testo, utente FROM commento WHERE recensione = ?");
$stmt->bind_param("s", $review);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
  $stmt->bind_result($id, $text, $username);

  $comment = array();
  while ($stmt->fetch()) {
    $comment[] = array('id' => $id,
                       'username' => $username,
                       'commento' => $text);
  }

  $response['error'] = false;
  $response['message'] = 'Commenti caricati correttamente';
  $response['commenti'] = $comment;
}
else {
  //if no comments
  $response['error'] = true;
  $response['message'] = 'Non ci sono ancora commenti';
}
$stmt->close();
echo json_encode($response);
?>
