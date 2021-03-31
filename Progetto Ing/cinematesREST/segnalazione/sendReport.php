<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$type = $_POST['type']; //Recensione or Commento
$item = $_POST['item']; //ids
$reason = $_POST['reason']; //report reason

$stmt = $conn->prepare("SELECT *
                              FROM segnalazione
                              WHERE tipo = ? AND utente = ? AND ".$type." = ?");
$stmt->bind_param("ssi", $type, $username, $item);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows == 0) {
    $stmt = $conn->prepare("INSERT INTO segnalazione (motivo, tipo, ".$type.", utente)
                                  VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $reason, $type, $item, $username);


    //if the report is successfully added to the database
    if($stmt->execute()) {
        $response['error'] = false;
        $response['message'] = 'Segnalazione inviata correttamente';
    }
    else {
        $response['error'] = true;
        $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
    }
}
else {
    $response['error'] = true;
    $response['message'] = 'Hai già inoltrato una segnalazione per questo contenuto';
}
$stmt->close();
echo json_encode($response);
?>