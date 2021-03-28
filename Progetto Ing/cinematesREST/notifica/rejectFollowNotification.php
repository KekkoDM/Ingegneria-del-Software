<?php
include_once '../connection/database.php';

$response = array();
$notificationId = $_POST['idnotification'];

$stmt = $conn->prepare("DELETE FROM notifica WHERE idnotifica = ?");
$stmt->bind_param("i", $notificationId);

//if the notification is successfully removed from the database
if($stmt->execute()) {
    $response['error'] = false;
    $response['message'] = 'Richiesta rifiutata correttamente';
}
else {
    $response['error'] = true;
    $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
}
$stmt->close();
echo json_encode($response);
?>