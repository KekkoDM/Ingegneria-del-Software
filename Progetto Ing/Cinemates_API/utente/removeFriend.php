<?php
include_once '../connection/database.php';

$response = array();
$username = $_POST['username'];
$friend = $_POST['friend'];

$stmt = $conn->prepare("DELETE FROM amicizia WHERE (utente1 = ? AND utente2 = ?) OR (utente1 = ? AND utente2 = ?)");
$stmt->bind_param("ssss", $username, $friend, $friend, $username);

//if the friend is successfully removed from the friends list
if($stmt->execute()) {
    $response['error'] = false;
    $response['message'] = 'Rimosso dalla lista amici';
}
else {
    $response['error'] = true;
    $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
}
$stmt->close();
echo json_encode($response);
?>