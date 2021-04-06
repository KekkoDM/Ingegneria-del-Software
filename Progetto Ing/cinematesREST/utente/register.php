<?php
include_once '../connection/database.php';
include_once '../function/function.php';

$response = array();

if(checkParameters(array('username','nome','cognome','email','password'))) {
  $username = $_POST['username'];
  $nome = $_POST['nome'];
  $cognome = $_POST['cognome'];
  $email = $_POST['email'];
  $password = $_POST['password'];

  // checking if the user is already exist with this username or email
  $stmt = $conn->prepare("SELECT username FROM utente WHERE username = ? OR email = ?");
  $stmt->bind_param("ss", $username, $email);
  $stmt->execute();
  $stmt->store_result();

  //if the user already exist in the database
  if($stmt->num_rows > 0) {
    $response['error'] = true;
    $response['message'] = 'Utente già esistente con questo username o con questa email';
    $stmt->close();
  }
  else {
    //if user is new creating an insert query
    $stmt = $conn->prepare("INSERT INTO utente VALUES (?, ?, ?, ?, ?)");
    $stmt->bind_param("sssss", $username, $nome, $cognome, $email, $password);

    //if the user is successfully added to the database
    if($stmt->execute()) {
      //fetching the user back
      $stmt = $conn->prepare("SELECT * FROM utente WHERE username = ?");
      $stmt->bind_param("s",$username);
      $stmt->execute();
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
      $response['message'] = 'Utente registrato correttamente';
      $response['utente'] = $user;
    }
    else {
      $response['error'] = true;
      $response['message'] = 'Ops! Qualcosa è andato storto. Controlla i dati inseriti e riprova';
    }
  }
}
$stmt->close();
echo json_encode($response);
?>
