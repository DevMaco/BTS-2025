<?php
// gestion_reservations.php
// Prérequis : session_start() + $unControleur instancié + Bootstrap CSS/JS inclus

$type    = $_SESSION['type'] ?? '';
$id_user = $_SESSION['id']   ?? null;
$messages = [];

// Traitement des actions du propriétaire
if ($type === 'proprietaire' && isset($_POST['actionReservation'], $_POST['reservation_id'])) {
    $action = $_POST['actionReservation'];
    $resId  = (int)$_POST['reservation_id'];
    if ($action === 'accepter') {
        $unControleur->majStatutReservation($resId, 'acceptee');
        $messages[] = ['success', "Réservation #$resId acceptée."];
    } elseif ($action === 'annuler') {
        $unControleur->majStatutReservation($resId, 'annulee');
        $messages[] = ['danger', "Réservation #$resId annulée."];
    }
}

// Récupération des réservations
if ($type === 'client') {
    $reservations = $unControleur->getReservationsClient($id_user);
} elseif ($type === 'proprietaire') {
    $reservations = $unControleur->getReservationsProprietaire($id_user);
} else {
    $reservations = [];
}
?>

<div class="container my-5">
  <h2 class="mb-4">
    <?= $type === 'client' ? 'Mes réservations' : 'Demandes de réservation' ?>
  </h2>

  <?php foreach ($messages as [$typeMsg, $text]): ?>
    <div class="alert alert-<?= $typeMsg ?>"><?= htmlspecialchars($text) ?></div>
  <?php endforeach; ?>

  <?php if (empty($reservations)): ?>
    <div class="alert alert-info">Aucune réservation à afficher.</div>
  <?php else: ?>
    <div class="list-group">
      <?php foreach ($reservations as $res): 
          $log = $unControleur->getLogementById($res['logement_id']);
      ?>
        <div class="list-group-item mb-3 shadow-sm">
          <div class="row">
            <div class="col-md-8">
              <h5><?= htmlspecialchars($log['titre'] ?? '') ?></h5>
              <p>
                <strong>Période :</strong>
                <?= htmlspecialchars($res['date_debut']) ?> → <?= htmlspecialchars($res['date_fin']) ?>
              </p>
              <p>
                <strong>Client :</strong>
                <?= htmlspecialchars($res['prenom'] ?? '') ?> <?= htmlspecialchars($res['nom_client'] ?? '') ?>
                &lt;<?= htmlspecialchars($res['email_client'] ?? '') ?>&gt;<br>
                <strong>Tél :</strong> <?= htmlspecialchars($res['telephone'] ?? '') ?>
              </p>
              <p>
                <strong>Personnes :</strong> <?= (int)($res['nb_personnes'] ?? 1) ?><br>
                <strong>Commentaire :</strong><br>
                <em><?= nl2br(htmlspecialchars($res['commentaire'] ?? '— Aucune remarque —')) ?></em>
              </p>
              <p><small><strong>Date de création :</strong> <?= htmlspecialchars($res['date_reservation']) ?></small></p>
            </div>
            <div class="col-md-4 text-end">
              <?php if ($type === 'proprietaire' && $res['statut'] === 'en_attente'): ?>
                <form method="post" class="d-inline">
                  <input type="hidden" name="reservation_id" value="<?= (int)$res['id'] ?>">
                  <button name="actionReservation" value="accepter" class="btn btn-sm btn-success">✅ Accepter</button>
                </form>
                <form method="post" class="d-inline">
                  <input type="hidden" name="reservation_id" value="<?= (int)$res['id'] ?>">
                  <button name="actionReservation" value="annuler" class="btn btn-sm btn-danger">❌ Annuler</button>
                </form>
              <?php else: ?>
                <?php if ($res['statut'] === 'acceptee'): ?>
                  <span class="badge bg-success">Acceptée</span>
                <?php elseif ($res['statut'] === 'annulee'): ?>
                  <span class="badge bg-danger">Annulée</span>
                <?php else: ?>
                  <span class="badge bg-warning text-dark">En attente</span>
                <?php endif; ?>
              <?php endif; ?>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    </div>
  <?php endif; ?>
</div>
