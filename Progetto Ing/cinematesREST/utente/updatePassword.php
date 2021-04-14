<?php
include_once '../connection/database.php';

// getting values
$username = $_POST['username'];
$newpassword = $_POST['newpassword'];

// updating the password
$stmt = $conn->prepare("UPDATE utente SET password = ? WHERE username = ?");
$stmt->bind_param("ss", $newpassword, $username);

// if the query returns true
if ($stmt->execute()) {
  $response['error'] = false;
  $response['message'] = 'La password è stata aggiornata correttamente';
}
else {
  $response['error'] = true;
  $response['message'] = 'Ops! Qualcosa è andato storto. Riprova';
}
$stmt->close();
echo json_encode($response);
?>
