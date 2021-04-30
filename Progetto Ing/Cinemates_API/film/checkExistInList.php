<?php
include_once '../connection/database.php';

$response = array();
$list = $_POST['list'];
$user = $_POST['username'];
$item = $_POST['item'];

$stmt = $conn->prepare("SELECT * FROM listafilm WHERE (utente = ? AND nome = ? AND film = ?)");
$stmt->bind_param("sss",$user, $list, $item);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $response['error'] = true;
    $response['message'] = 'Elemento presente nella lista';
}
else {
    $response['error'] = false;
    $response['message'] = 'Elemento non presente lista';
}
$stmt->close();
echo json_encode($response);
?>