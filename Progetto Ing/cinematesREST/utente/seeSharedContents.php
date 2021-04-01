<?php
include_once '../connection/database.php';

$username = $_POST['username'];
$friend = $_POST['friend'];

//creating the query
$stmt = $conn->prepare("SELECT l1.film, l1.tipo
                              FROM listafilm AS l1 JOIN listafilm as l2
                              ON l1.film = l2.film
                              WHERE l1.utente = ? AND l2.utente = ?
                              GROUP BY l1.film");
$stmt->bind_param("ss",$username, $friend);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($item, $type);

    $content = array();
    while ($stmt->fetch()) {
        $content[] = array('item' => $item,
                           'type' => $type);
    }

    $response['error'] = false;
    $response['message'] = 'Contenuto in comune caricato correttamente';
    $response['comune'] = $content;
}
else {
    //if list is empty
    $response['error'] = true;
    $response['message'] = 'Non hai nessun contenuto in comune con questo amico';
}
$stmt->close();
echo json_encode($response);
?>