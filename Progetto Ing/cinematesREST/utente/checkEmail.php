<?php
include_once '../connection/database.php';

$email = $_POST['email'];

//creating the query
$stmt = $conn->prepare("SELECT * FROM utente WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

//if the user exists with given email
if ($stmt->num_rows > 0) {
    $stmt->bind_result($username, $nome, $cognome, $email, $password);
    $stmt->fetch();

    $user = array(
        'username' => $username,
        'nome' => $nome,
        'cognome' => $cognome,
        'email' => $email,
        'password' => $password);

    $response['error'] = false;
    $response['message'] = 'Login riuscito';
    $response['utente'] = $user;
}
else {
    //if the user not found
    $response['error'] = true;
    $response['message'] = 'Nessun utente corrisponde a questa e-mail';
}
$stmt->close();
echo json_encode($response);
?>
