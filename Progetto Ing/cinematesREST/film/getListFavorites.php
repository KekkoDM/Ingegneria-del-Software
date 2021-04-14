<?php
include_once '../connection/database.php';

$response = array();
$user = $_POST['username'];

$stmt = $conn->prepare("SELECT film, tipo FROM listafilm WHERE (nome = 'Preferiti' AND utente = ?)");
$stmt->bind_param("s",$user);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($item, $type);

    $favorites = array();
    while ($stmt->fetch()) {
        $favorites[] = array('id' => $item, 'type' => $type);
    }

    $response['error'] = false;
    $response['message'] = 'Lista preferiti caricata correttamente';
    $response['list'] = $favorites;
}
else {
    $response['error'] = true;
    $response['message'] = 'La tua lista preferita è vuota';
}
$stmt->close();
echo json_encode($response);
?>