<?php
include_once '../connection/database.php';

$username = $_POST['username'];

// query
$stmt = $conn->prepare("DELETE FROM utente WHERE username = ?");
$stmt->bind_param("s", $username);

// if the query returns true
if ($stmt->execute()) {
    $response['error'] = false;
    $response['message'] = 'Il tuo account è stato cancellato definitivamente';
}
else {
    $response['error'] = true;
    $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
}
$stmt->close();
echo json_encode($response);
?>
