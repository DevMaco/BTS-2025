<?php
// Vérifier que la session est démarrée et que le contrôleur existe
if (session_status() === PHP_SESSION_NONE) {
    session_start();
}
require_once("controleur/controleur.class.php");
$unControleur = new Controleur();

// Récupère tous les logements (sans filtre)
$logements = $unControleur->getLogements();
?>

<div class="container my-5">
  <div class="text-center mb-4">
    <h1>Bienvenue sur Neige <span class="snowflake">❄️</span></h1>
    <p class="lead">La plateforme de réservation de logements entre clients et propriétaires.</p>
    <hr>
  </div>

  <div class="d-flex justify-content-center mb-4">
    <a href="index.php?page=2" class="btn btn-outline-primary me-2">Réserver un logement</a>
    <a href="index.php?page=3" class="btn btn-outline-secondary">Mes réservations</a>
  </div>

  <div class="row">
    <?php if (empty($logements)): ?>
      <div class="col-12">
        <div class="alert alert-info text-center">Aucun logement disponible pour le moment.</div>
      </div>
    <?php else: ?>
      <?php foreach ($logements as $logement): ?>
        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <?php if (!empty($logement['image'] ?? '')): ?>
              <img
                src="uploads/<?= htmlspecialchars($logement['image'] ?? '') ?>"
                class="card-img-top"
                alt="Image <?= htmlspecialchars($logement['titre'] ?? '') ?>"
              >
            <?php endif; ?>

            <div class="card-body d-flex flex-column">
              <h5 class="card-title"><?= htmlspecialchars($logement['titre'] ?? '') ?></h5>
              <p class="card-text text-truncate"><?= htmlspecialchars($logement['description'] ?? '') ?></p>

              <ul class="list-unstyled small mb-3">
                <li><strong>Adresse :</strong> <?= htmlspecialchars($logement['adresse'] ?? '') ?> (<?= htmlspecialchars($logement['code_postal'] ?? '') ?>)</li>
                <li><strong>Type :</strong> <?= htmlspecialchars($logement['type_logement'] ?? '') ?></li>
                <li><strong>Chambres :</strong> <?= (int)($logement['nb_chambres'] ?? 0) ?></li>
              </ul>

              <div class="mt-auto">
                <span class="fw-bold"><?= number_format((float)($logement['prix_par_nuit'] ?? 0), 2) ?> € / nuit</span>
                <a href="index.php?page=2" class="btn btn-primary btn-sm float-end">Voir & Réserver</a>
              </div>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    <?php endif; ?>
  </div>
</div>
