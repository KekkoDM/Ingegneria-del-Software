<?php
include_once '../connection/database.php';

$response = array();
$list = $_POST['list'];
$user = $_POST['username'];
$item = $_POST['item'];

$stmt = $conn->prepare("DELETE FROM listafilm WHERE (nome = ? AND utente = ? AND film = ?)");
$stmt->bind_param("sss", $list, $user, $item);

if($stmt->execute()) {
    $response['error'] = false;
    $response['message'] = 'Elemento rimosso dalla lista';
}
else {
    $response['error'] = true;
    $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
}
$stmt->close();
echo json_encode($response);
?>