<?php
include_once '../connection/database.php';
require '../mail/PHPMailer.php';
require '../mail/SMTP.php';
require '../mail/Exception.php';

use PHPMailer\PHPMailer\PHPMailer;
use PHPMailer\PHPMailer\SMTP;
use PHPMailer\PHPMailer\Exception;

$email = $_POST['email'];

$stmt = $conn->prepare("SELECT nome, cognome, username, password FROM utente WHERE email = ?");
$stmt->bind_param("s", $email);
$stmt->execute();
$stmt->store_result();

if($stmt->num_rows > 0) {
    $stmt->bind_result($nome, $cognome, $username, $password);
    $stmt->fetch();

    $mail = new PHPMailer();

    //email config
    $mail->isSMTP();
    $mail->Host = "smtp.gmail.com";
    $mail->SMTPAuth = true;
    $mail->SMTPSecure = "tls";
    $mail->Port = "587";
    $mail->Username = "cinemates.noreply@gmail.com";
    $mail->Password = "unina2021";
    $mail->isHTML(true);

    //email content
    $mail->Subject = "Recupero credenziali Cinemates";
    $mail->setFrom('cinemates.noreply@gmail.com');
    $mail->FromName = "Cinemates";
    $mail->Body = "<h1>Ciao $nome $cognome!</h1></br>
                   <p>Ecco le tue credenziali per accedere a Cinemates:</p></br>
                   <p>Username: $username</p></br>
                   <p>Password: $password</p>";
    $mail->addAddress($email);

    if($mail->Send()) {
        $response['error'] = false;
        $response['message'] = 'Ti è stata inviata una e-mail con le tue credenziali';
    }
    else {
        echo 'Mailer Error: ' . $mail->ErrorInfo;
        $response['error'] = true;
        $response['message'] = 'Si è verificato un errore durante. Riprova';
    }

    //close connection
    $mail->smtpClose();
}
else {
    $response['error'] = true;
    $response['message'] = 'Ops! Questa e-mail non corrisponde a nessun utente';
}
$stmt->close();
echo json_encode($response);
?>