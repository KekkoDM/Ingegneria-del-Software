<?php
include_once '../connection/database.php';

$response = array();
$usernameSender = $_POST['sender'];
$usernameReceiver = $_POST['receiver'];
$notificationId = $_POST['idnotification'];

//add friend to friends list
$stmt = $conn->prepare("INSERT INTO amicizia VALUES (?, ?)");
$stmt->bind_param("ss", $usernameSender, $usernameReceiver);

//if the friend is successfully added to the database
if($stmt->execute()) {
    //remove the follow notification
    $stmt = $conn->prepare("DELETE FROM notifica WHERE idnotifica = ?");
    $stmt->bind_param("i", $notificationId);

    if($stmt->execute()) {
        //send notification to sender user
        $stmt = $conn->prepare("INSERT INTO notifica (titolo, tipo, descrizione, destinatario)
                                      VALUES ('Richiesta di collegamento accettata', 'Segnalazione', '$usernameReceiver ha accettato la tua richiesta. Scopri i contenuti che avete in comune', ?)");
        $stmt->bind_param("s", $usernameSender);

        if ($stmt->execute()) {
            $response['error'] = false;
            $response['message'] = 'Richiesta accettata correttamente';
        }
        else {
            $response['error'] = true;
            $response['message'] = 'Errore durante inserimento in Notifica';
        }
    }
    else {
        $response['error'] = true;
        $response['message'] = 'Errore durante cancellazione in Notifica';
    }
}
else {
    $response['error'] = true;
    $response['message'] = 'Errore durante inserimento in Amicizia';
}
$stmt->close();
echo json_encode($response);
?>