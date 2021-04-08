<?php
include_once '../connection/database.php';

$response = array();
$type = $_POST['type']; //Recensione or Commento
$item = $_POST['item'];

//creating the query
$stmt = $conn->prepare("SELECT COUNT(motivo) FROM segnalazione WHERE ".$type." = ? AND motivo = 'Spoiler'");
$stmt->bind_param("s", $item);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
  $stmt->bind_result($reportcount);
  $stmt->fetch();

  if ($reportcount >= 3) {
    $response['error'] = true;
    $response['message'] = 'Questo contenuto è stato oscurato perchè contiene Spoiler';
  }
  else {
    $response['error'] = false;
    $response['message'] = 'Numero segnalazioni caricato correttamente';
  }

  $response['contatore'] = $reportcount;
}
else {
  //if no reports
  $response['error'] = true;
  $response['message'] = 'Non ci sono ancora segnalazioni';
}
$stmt->close();
echo json_encode($response);
?>
