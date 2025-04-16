<?php
$type = $_SESSION['type'];
$id_user = $_SESSION['id'] ?? null;

if ($type == "client") {
    echo "<h2>📅 Mes réservations</h2>";
    $reservations = $unControleur->getReservationsClient($id_user);
    if ($reservations) {
        foreach ($reservations as $res) {
            echo "<div style='border:1px solid #ddd; padding:10px; margin:10px;'>";
            echo "<p><strong>Logement :</strong> " . $res['logement_id'] . "</p>";
            echo "<p><strong>Du</strong> " . $res['date_debut'] . " <strong>au</strong> " . $res['date_fin'] . "</p>";
            echo "<p><strong>Statut :</strong> " . $res['statut'] . "</p>";
            echo "</div>";
        }
    } else {
        echo "<p>❌ Aucune réservation trouvée.</p>";
    }
} elseif ($type == "proprietaire") {
    echo "<h2>📩 Réservations reçues</h2>";
    $reservations = $unControleur->getReservationsProprietaire($id_user);
    if ($reservations) {
        foreach ($reservations as $res) {
            echo "<div style='border:1px solid #ddd; padding:10px; margin:10px;'>";
            echo "<p><strong>Client ID :</strong> " . $res['client_id'] . "</p>";
            echo "<p><strong>Logement :</strong> " . $res['logement_id'] . "</p>";
            echo "<p><strong>Du</strong> " . $res['date_debut'] . " <strong>au</strong> " . $res['date_fin'] . "</p>";
            echo "<p><strong>Statut :</strong> " . $res['statut'] . "</p>";
            if ($res['statut'] == "en_attente") {
                echo "<form method='post'>
                    <input type='hidden' name='id_reservation' value='" . $res['id'] . "'>
                    <input type='submit' name='accepter' value='✅ Accepter'>
                    <input type='submit' name='annuler' value='❌ Annuler'>
                </form>";
            }
            echo "</div>";
        }
        if (isset($_POST['accepter']) || isset($_POST['annuler'])) {
            $statut = isset($_POST['accepter']) ? "acceptee" : "annulee";
            $unControleur->majStatutReservation($_POST['id_reservation'], $statut);
            echo "<p style='color:green;'>✅ Statut mis à jour.</p>";
            header("Refresh:0");
        }
    } else {
        echo "<p>❌ Aucune réservation.</p>";
    }
}
?>
