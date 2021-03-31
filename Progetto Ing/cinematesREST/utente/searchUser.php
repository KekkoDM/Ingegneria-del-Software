<?php
include_once '../connection/database.php';

// getting values
$username = $_POST['username'];
$usersearched = $_POST['usersearched'];

// creating the query
$stmt = $conn->prepare("SELECT username, nome, cognome
                              FROM utente
                              WHERE ((username LIKE '%$usersearched%') OR (nome LIKE '%$usersearched%') OR (cognome LIKE '%$usersearched%')) AND (username != ?)");
$stmt->bind_param("s", $username);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
    $stmt->bind_result($username, $nome, $cognome);

    $users = array();
    while ($stmt->fetch()) {
        $users[] = array('username' => $username,
                         'nome' => $nome,
                         'cognome' => $cognome);
    }

    $response['error'] = false;
    $response['message'] = 'La ricerca ha prodotto risultati';
    $response['utente'] = $users;
}
else {
    // if no friends found
    $response['error'] = true;
    $response['message'] = 'La ricerca non ha prodotto risultati';
}
$stmt->close();
echo json_encode($response);
?>