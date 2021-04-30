<?php
include_once '../connection/database.php';

$response = array();
$usernameSender = $_POST['sender'];
$usernameReceiver = $_POST['receiver'];

$stmt = $conn->prepare("SELECT *
                              FROM notifica
                              WHERE (((mittente = ?) AND (destinatario = ?)) OR ((mittente = ?) AND (destinatario = ?))) AND (tipo = 'Collegamento')");
$stmt->bind_param("ssss", $usernameSender, $usernameReceiver, $usernameReceiver, $usernameSender);
$stmt->execute();
$stmt->store_result();

//if follow request already sent
if ($stmt->num_rows == 0) {
    $stmt = $conn->prepare("INSERT INTO notifica (titolo, tipo, descrizione, mittente, destinatario)
                                  VALUES ('Richiesta di collegamento', 'Collegamento', '$usernameSender vuole aggiungerti ai suoi amici', ?, ?)");
    $stmt->bind_param("ss", $usernameSender, $usernameReceiver);


    //if the notification is successfully added to the database
    if($stmt->execute()) {
        $response['error'] = false;
        $response['message'] = 'Richiesta inviata correttamente';
    }
    else {
        $response['error'] = true;
        $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
    }
}
else {
    $response['error'] = true;
    $response['message'] = 'Hai già inoltrato una richiesta di collegamento a questo utente';
}
$stmt->close();
echo json_encode($response);
?>