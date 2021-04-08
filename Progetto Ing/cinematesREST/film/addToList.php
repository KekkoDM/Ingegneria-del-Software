<?php
include_once '../connection/database.php';

$response = array();
$list = $_POST['list'];
$user = $_POST['username'];
$item = $_POST['item'];
$type = $_POST['type'];

$stmt = $conn->prepare("SELECT *
                              FROM listafilm
                              WHERE (nome = ? AND utente = ? AND film = ?)");
$stmt->bind_param("sss", $list, $user, $item);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows == 0) {
    $stmt = $conn->prepare("INSERT INTO listafilm VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $list, $user, $item, $type);

    if($stmt->execute()) {
        $response['error'] = false;
        $response['message'] = 'Elemento aggiunto correttamente alla lista';
    }
    else {
        $response['error'] = true;
        $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
    }
}
else {
    $response['error'] = true;
    $response['message'] = 'Questo elemento è già presente nella lista selezionata';
}
$stmt->close();
echo json_encode($response);
?>