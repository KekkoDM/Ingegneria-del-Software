<?php
include_once '../connection/database.php';

// getting values
$username = $_POST['username'];

//creating the query
$stmt = $conn->prepare("SELECT * FROM notifica WHERE destinatario = ? AND tipo = 'Collegamento' ORDER BY idnotifica DESC");
$stmt->bind_param("s",$username);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($id, $titolo, $tipo, $descrizione, $mittente, $destinatario, $amministratore);

    $notifications = array();
    while ($stmt->fetch()) {
        $notifications[] = array(
            'id' => $id,
            'titolo' => $titolo,
            'descrizione' => $descrizione,
            'tipo' => $tipo,
            'mittente' => $mittente,
            'destinatario' => $destinatario,
            'amministratore' => $amministratore);
    }

    $response['error'] = false;
    $response['message'] = 'Notifiche caricate correttamente';
    $response['notifica'] = $notifications;
}
else {
    //if friends list is empty
    $response['error'] = true;
    $response['message'] = 'Non hai nessuna nuova notifica';
}
$stmt->close();
echo json_encode($response);
?>
