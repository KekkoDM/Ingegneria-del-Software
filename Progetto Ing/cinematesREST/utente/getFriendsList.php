<?php
include_once '../connection/database.php';

// getting values
$username = $_POST['username'];

//creating the query
$stmt = $conn->prepare("(SELECT username, nome, cognome
                              FROM utente LEFT JOIN amicizia ON username = utente2
                              WHERE utente1 = ?)
                              UNION
                              (SELECT username, nome, cognome
                              FROM utente LEFT JOIN amicizia ON username = utente1
                              WHERE utente2 = ?)");
$stmt->bind_param("ss",$username, $username);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows > 0) {
  $stmt->bind_result($username, $nome, $cognome);

  $user = array();
  while ($stmt->fetch()) {
    $user[] = array('username' => $username,
                    'nome' => $nome,
                    'cognome' => $cognome);
  }

$response['error'] = false;
$response['message'] = 'Lista amici caricata correttamente';
$response['utente'] = $user;
}
else {
  //if friends list is empty
  $response['error'] = true;
  $response['message'] = 'Non hai nessun amico';
}
$stmt->close();
echo json_encode($response);
?>
