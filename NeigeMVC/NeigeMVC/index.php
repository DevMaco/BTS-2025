<?php
session_start(); 
require_once("controleur/controleur.class.php");
$unControleur = new Controleur(); 

// Traitement de l'inscription
if (isset($_POST['Inscription'])) {
    if ($_POST['mot_de_passe'] != $_POST['mot_de_passe_confirm']) {
        $erreur = "Les mots de passe ne correspondent pas.";
    } else {
        $data = [
            "nom" => $_POST['nom'],
            "email" => $_POST['email'],
            "mot_de_passe" => password_hash($_POST['mot_de_passe'], PASSWORD_DEFAULT),
            "type" => $_POST['type']
        ];
        if ($unControleur->inscrireUser($data)) {
            $success = "Inscription réussie ! Vous pouvez vous connecter.";
            header("Refresh:2; url=index.php");
        } else {
            $erreur = "Erreur lors de l'inscription. Email déjà utilisé ?";
        }
    }
}

// Traitement de la connexion
if (isset($_POST['Connexion'])) {
    $email = $_POST['email'];
    $mdp = $_POST['mdp'];
    $unUser = $unControleur->verifConnexion($email, $mdp);
    if ($unUser != false) {
        $_SESSION['id'] = $unUser['id'];
        $_SESSION['email'] = $unUser['email'];
        $_SESSION['nom'] = $unUser['nom'];
        $_SESSION['prenom'] = $unUser['prenom'] ?? '';
        $_SESSION['type'] = $unUser['type'];
        header("Location: index.php?page=1");
        exit;
    } else {
        $erreur = "❌ Email ou mot de passe incorrect.";
    }
}
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Projet Neige</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container my-5">

<?php
// Si utilisateur non connecté
if (!isset($_SESSION['email'])) {
    if (isset($erreur)) {
        echo "<div class='alert alert-danger text-center'>$erreur</div>";
    }
    if (isset($success)) {
        echo "<div class='alert alert-success text-center'>$success</div>";
    }

    if (isset($_GET['action']) && $_GET['action'] == 'inscription') {
        require_once("vue/vue_inscription.php");
    } else {
        require_once("vue/vue_connexion.php");
    }

} else {
    // Si utilisateur connecté
    echo '
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary rounded mb-4 px-4">
            <a class="navbar-brand" href="index.php?page=1">🏔️ Neige</a>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class="nav-link" href="index.php?page=1">Accueil</a></li>
                    <li class="nav-item"><a class="nav-link" href="index.php?page=2">Logements</a></li>
                    <li class="nav-item"><a class="nav-link" href="index.php?page=3">Réservations</a></li>
                </ul>
                <span class="navbar-text text-white me-3">'.$_SESSION['email'].'</span>
                <a class="btn btn-outline-light" href="index.php?page=4">Déconnexion</a>
            </div>
        </nav>
    ';

    $page = $_GET['page'] ?? 1;
    switch ($page) {
        case 1: require_once("home.php"); break;
        case 2: require_once("gestion_logements.php"); break;
        case 3: require_once("gestion_reservations.php"); break;
        case 4:
            session_destroy();
            header("Location: index.php");
            break;
        default:
            echo "<p class='text-danger'>Page introuvable</p>";
    }
}
?>

</div>
<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
