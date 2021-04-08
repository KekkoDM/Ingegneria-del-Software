<?php
include_once '../connection/database.php';
include_once '../function/function.php';

if (checkParameters(array('username', 'password'))) {
	// getting values
    $username = $_POST['username'];
    $password = $_POST['password'];

    //creating the query
    $stmt = $conn->prepare("SELECT * FROM utente WHERE username = ? AND password = ?");
    $stmt->bind_param("ss",$username, $password);
    $stmt->execute();
    $stmt->store_result();

    //if the user exist with given credentials
    if ($stmt->num_rows > 0) {
        $stmt->bind_result($username, $nome, $cognome, $email, $password);
        $stmt->fetch();

        $user = array(
            'username' => $username,
            'nome' => $nome,
            'cognome' => $cognome,
            'email' => $email,
            'password' => $password
        );

        $response['error'] = false;
        $response['message'] = 'Login riuscito';
        $response['utente'] = $user;
    }
    else {
        //if the user not found
        $response['error'] = true;
        $response['message'] = 'Username o password errati';
    }
}
$stmt->close();
echo json_encode($response);
?>
