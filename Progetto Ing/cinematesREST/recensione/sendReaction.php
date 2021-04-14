<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$review = $_POST['review'];
$reaction = $_POST['reaction'];

// checking if the user has already reacted
$stmt = $conn->prepare("SELECT valutazione FROM valutazione WHERE utente = ? AND recensione = ?");
$stmt->bind_param("ss", $username, $review);
$stmt->execute();
$stmt->store_result();

if($stmt->num_rows > 0) {
  $stmt->bind_result($current);
  $stmt->fetch();

  if ($current == $reaction) {
    $stmt = $conn->prepare("DELETE FROM valutazione WHERE utente = ? AND recensione = ?");
    $stmt->bind_param("ss", $username, $review);

    if ($stmt->execute()) {
      $response['error'] = false;
      $response['message'] = 'Reazione eliminata correttamente';
    }
    else {
      $response['error'] = true;
      $response['message'] = 'Errore durante cancellazione della reazione';
    }
  }
  else {
    $stmt = $conn->prepare("UPDATE valutazione SET valutazione = ? WHERE utente = ? AND recensione = ?");
    $stmt->bind_param("sss", $reaction, $username, $review);

    if ($stmt->execute()) {
      $response['error'] = false;
      $response['message'] = 'Reazione aggiornata correttamente';
    }
    else {
      $response['error'] = true;
      $response['message'] = 'Errore durante aggiornamento della reazione';
    }
  }
}
else {
  $stmt = $conn->prepare("INSERT INTO valutazione VALUES (?, ?, ?)");
  $stmt->bind_param("sss", $username, $review, $reaction);

  //if the reaction is successfully added to the database
  if($stmt->execute()) {
    $response['error'] = false;
    $response['message'] = 'Reazione inserita correttamente';
  }
  else {
    $response['error'] = true;
    $response['message'] = 'Errore durante inserimento della reazione';
  }
}
$stmt->close();
echo json_encode($response);
?>
