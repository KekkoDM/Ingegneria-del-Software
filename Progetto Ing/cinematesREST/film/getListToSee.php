<?php
include_once '../connection/database.php';

$response = array();
$user = $_POST['username'];

$stmt = $conn->prepare("SELECT film, tipo FROM listafilm WHERE (nome = 'Da vedere' AND utente = ?)");
$stmt->bind_param("s",$user);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($item, $type);

    $favorites = array();
    while ($stmt->fetch()) {
        $tosee[] = array('id' => $item, 'type' => $type);
    }

    $response['error'] = false;
    $response['message'] = 'Lista da vedere caricata correttamente';
    $response['list'] = $tosee;
}
else {
    $response['error'] = true;
    $response['message'] = 'La tua lista film da vedere è vuota';
}
$stmt->close();
echo json_encode($response);
?>