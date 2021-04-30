<?php
include_once '../connection/database.php';

$response = array();
$review = $_POST['review'];

//creating the query
$stmt = $conn->prepare("SELECT c.idcommento, c.testo, c.utente, r.num_segnalazioni
                              FROM commento AS c LEFT JOIN commenti_report AS r ON c.idcommento = r.commento
                              WHERE c.recensione = ?
                              GROUP BY c.idcommento");
$stmt->bind_param("s", $review);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
  $stmt->bind_result($id, $text, $username, $reports);

  $comment = array();
  while ($stmt->fetch()) {

    if ($reports == null) {
      $comment[] = array('id' => $id,
          'username' => $username,
          'commento' => $text,
          'report' => 0);
    }
    else {
      $comment[] = array('id' => $id,
          'username' => $username,
          'commento' => $text,
          'report' => $reports);
    }
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
