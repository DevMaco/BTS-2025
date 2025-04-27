<?php
// gestion_logements.php
// Prérequis : session_start(), $unControleur instancié dans index.php, Bootstrap CSS/JS inclus

$type           = $_SESSION['type'] ?? '';
$id_user        = $_SESSION['id']   ?? null;
$messageSuccess = '';

// 1) Traitement de la confirmation de réservation (modal)
if (isset($_POST['confirmReservation'])) {
    $data = [
        "client_id"    => $id_user,
        "nom_client"   => trim($_POST['nom_client']   ?? ''),
        "prenom"       => trim($_POST['prenom']       ?? ''),
        "email_client" => trim($_POST['email_client'] ?? ''),
        "telephone"    => trim($_POST['telephone']    ?? ''),
        "logement_id"  => (int)$_POST['logement_id'],
        "date_debut"   => $_POST['date_debut']        ?? '',
        "date_fin"     => $_POST['date_fin']          ?? '',
        "nb_personnes" => (int)($_POST['nb_personnes'] ?? 1),
        "commentaire"  => trim($_POST['commentaire']  ?? ''),
    ];
    $unControleur->insererReservation($data);
    $messageSuccess = "✅ Votre réservation a bien été envoyée au propriétaire.";
}

// 2) Récupération des logements (client ou proprio)
if ($type === 'client') {
    $logements = $unControleur->getLogements();
} else {
    $logements = $unControleur->getLogementsDuProprio($id_user);
}

// 3) CRUD propriétaire
if ($type === 'proprietaire') {
    // Ajout
    if (isset($_POST['ajouterLogement'])) {
        $data = [
            "proprietaire_id" => $id_user,
            "titre"           => $_POST['titre']        ?? '',
            "description"     => $_POST['description']  ?? '',
            "adresse"         => $_POST['adresse']      ?? '',
            "code_postal"     => $_POST['code_postal']  ?? '',
            "nb_chambres"     => $_POST['nb_chambres']  ?? 0,
            "type_logement"   => $_POST['type_logement']?? '',
            "prix_par_nuit"   => $_POST['prix_par_nuit']?? 0,
        ];
        $unControleur->ajouterLogement($data);
        header("Location: index.php?page=2");
        exit;
    }

    // Préparation de la modification
    if (isset($_POST['modifier'])) {
        $logementModif = $unControleur->getLogementById((int)$_POST['logement_id']);
    }

    // Validation de la modification
    if (isset($_POST['validerModification'])) {
        $data = [
            "id"            => (int)$_POST['id'],
            "titre"         => $_POST['titre']         ?? '',
            "description"   => $_POST['description']   ?? '',
            "adresse"       => $_POST['adresse']       ?? '',
            "code_postal"   => $_POST['code_postal']   ?? '',
            "nb_chambres"   => $_POST['nb_chambres']   ?? 0,
            "type_logement" => $_POST['type_logement'] ?? '',
            "prix_par_nuit" => $_POST['prix_par_nuit'] ?? 0,
        ];
        $unControleur->modifierLogement($data);
        header("Location: index.php?page=2");
        exit;
    }

    // Suppression
    if (isset($_POST['supprimer'])) {
        $unControleur->supprimerLogement((int)$_POST['logement_id']);
        header("Location: index.php?page=2");
        exit;
    }
}
?>

<div class="container my-5">

  <!-- Message de succès -->
  <?php if ($messageSuccess): ?>
    <div class="alert alert-success text-center"><?= htmlspecialchars($messageSuccess) ?></div>
  <?php endif; ?>

  <!-- AFFICHAGE CLIENT -->
  <?php if ($type === 'client'): ?>
    <h2>🏠 Logements disponibles</h2>
    <div class="row">
      <?php foreach ($logements as $logement): ?>
        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title"><?= htmlspecialchars($logement['titre']) ?></h5>
              <p class="card-text text-truncate"><?= htmlspecialchars($logement['description']) ?></p>
              <p class="small mb-2">
                <strong>Adresse :</strong> <?= htmlspecialchars($logement['adresse']) ?><br>
                <strong>Chambres :</strong> <?= (int)$logement['nb_chambres'] ?><br>
                <strong>Prix :</strong> <?= number_format($logement['prix_par_nuit'],2) ?>€
              </p>
              <div class="mt-auto text-end">
                <button
                  class="btn btn-success"
                  data-bs-toggle="modal"
                  data-bs-target="#reservationModal"
                  data-id="<?= $logement['id'] ?>"
                  data-titre="<?= htmlspecialchars($logement['titre'], ENT_QUOTES) ?>"
                  data-prix="<?= number_format($logement['prix_par_nuit'],2) ?>"
                >
                  Réserver
                </button>
              </div>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    </div>

  <!-- AFFICHAGE PROPRIÉTAIRE -->
  <?php else: ?>
    <h2>🛠️ Gestion de vos logements</h2>

    <!-- Formulaire d’ajout -->
    <form method="post" class="mb-4">
      <div class="row g-3">
        <div class="col-md-3">
          <input type="text" name="titre" placeholder="Titre" class="form-control" required>
        </div>
        <div class="col-md-3">
          <input type="text" name="adresse" placeholder="Adresse" class="form-control" required>
        </div>
        <div class="col-md-2">
          <input type="text" name="code_postal" placeholder="Code postal" class="form-control" required>
        </div>
        <div class="col-md-2">
          <input type="number" name="nb_chambres" placeholder="Chambres" class="form-control" required>
        </div>
        <div class="col-md-2">
          <input type="text" name="type_logement" placeholder="Type" class="form-control" required>
        </div>
        <div class="col-md-12">
          <textarea name="description" class="form-control" placeholder="Description" required></textarea>
        </div>
        <div class="col-md-2">
          <input type="number" name="prix_par_nuit" placeholder="Prix (€)" class="form-control" step="0.01" required>
        </div>
        <div class="col-md-2 d-grid">
          <button type="submit" name="ajouterLogement" class="btn btn-success">Ajouter</button>
        </div>
      </div>
    </form>

    <!-- Formulaire de modification -->
    <?php if (isset($logementModif)): ?>
      <form method="post" class="mb-4">
        <input type="hidden" name="id" value="<?= (int)$logementModif['id'] ?>">
        <div class="row g-3">
          <div class="col-md-3">
            <input type="text" name="titre" value="<?= htmlspecialchars($logementModif['titre']) ?>" class="form-control" required>
          </div>
          <div class="col-md-3">
            <input type="text" name="adresse" value="<?= htmlspecialchars($logementModif['adresse']) ?>" class="form-control" required>
          </div>
          <div class="col-md-2">
            <input type="text" name="code_postal" value="<?= htmlspecialchars($logementModif['code_postal']) ?>" class="form-control" required>
          </div>
          <div class="col-md-2">
            <input type="number" name="nb_chambres" value="<?= (int)$logementModif['nb_chambres'] ?>" class="form-control" required>
          </div>
          <div class="col-md-2">
            <input type="text" name="type_logement" value="<?= htmlspecialchars($logementModif['type_logement']) ?>" class="form-control" required>
          </div>
          <div class="col-md-12">
            <textarea name="description" class="form-control" required><?= htmlspecialchars($logementModif['description']) ?></textarea>
          </div>
          <div class="col-md-2">
            <input type="number" name="prix_par_nuit" value="<?= number_format($logementModif['prix_par_nuit'],2) ?>" class="form-control" step="0.01" required>
          </div>
          <div class="col-md-2 d-grid">
            <button type="submit" name="validerModification" class="btn btn-primary">Enregistrer</button>
          </div>
          <div class="col-md-2 d-grid">
            <a href="index.php?page=2" class="btn btn-secondary">Annuler</a>
          </div>
        </div>
      </form>
    <?php endif; ?>

    <!-- Liste des logements du proprio -->
    <div class="row">
      <?php foreach ($logements as $logement): ?>
        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body">
              <h5 class="card-title"><?= htmlspecialchars($logement['titre']) ?></h5>
              <p class="card-text"><?= nl2br(htmlspecialchars($logement['description'])) ?></p>
              <p><strong>Adresse :</strong> <?= htmlspecialchars($logement['adresse']) ?> (<?= htmlspecialchars($logement['code_postal']) ?>)</p>
              <p><strong>Chambres :</strong> <?= (int)$logement['nb_chambres'] ?> • <strong>Type :</strong> <?= htmlspecialchars($logement['type_logement']) ?></p>
              <p><strong>Prix :</strong> <?= number_format($logement['prix_par_nuit'],2) ?>€/nuit</p>
              <form method="post" class="d-flex gap-2">
                <input type="hidden" name="logement_id" value="<?= (int)$logement['id'] ?>">
                <button type="submit" name="modifier"   class="btn btn-warning">✏️ Modifier</button>
                <button type="submit" name="supprimer"  class="btn btn-danger">❌ Supprimer</button>
              </form>
            </div>
          </div>
        </div>
      <?php endforeach; ?>
    </div>
  <?php endif; ?>

</div>

<!-- Modal de réservation (client) -->
<div class="modal fade" id="reservationModal" tabindex="-1" aria-labelledby="resModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg modal-dialog-centered">
    <div class="modal-content">
      <form method="post">
        <div class="modal-header">
          <h5 class="modal-title" id="resModalLabel">Formulaire de réservation</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fermer"></button>
        </div>
        <div class="modal-body">
          <input type="hidden" name="logement_id" id="modal_logement_id">

          <div class="mb-3">
            <label class="form-label">Logement</label>
            <input type="text" id="modal_titre" class="form-control" disabled>
          </div>
          <div class="mb-3">
            <label class="form-label">Prix par nuit</label>
            <input type="text" id="modal_prix" class="form-control" disabled>
          </div>

          <div class="row g-3">
            <div class="col-md-6">
              <label class="form-label">Nom</label>
              <input type="text" name="nom_client" class="form-control" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Prénom</label>
              <input type="text" name="prenom" class="form-control" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Email</label>
              <input type="email" name="email_client" class="form-control" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Téléphone</label>
              <input type="tel" name="telephone" class="form-control" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Date début</label>
              <input type="date" name="date_debut" class="form-control" required>
            </div>
            <div class="col-md-6">
              <label class="form-label">Date fin</label>
              <input type="date" name="date_fin" class="form-control" required>
            </div>
            <div class="col-md-4">
              <label class="form-label">Personnes</label>
              <input type="number" name="nb_personnes" class="form-control" value="1" min="1" required>
            </div>
            <div class="col-md-8">
              <label class="form-label">Commentaire / Demande spéciale</label>
              <textarea name="commentaire" class="form-control" rows="3"></textarea>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
          <button type="submit" name="confirmReservation" class="btn btn-primary">Confirmer la réservation</button>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
// Pré-remplit la modal avec les data-* du bouton
var reservationModal = document.getElementById('reservationModal');
reservationModal.addEventListener('show.bs.modal', function (event) {
    var btn = event.relatedTarget;
    this.querySelector('#modal_logement_id').value = btn.getAttribute('data-id');
    this.querySelector('#modal_titre').value       = btn.getAttribute('data-titre');
    this.querySelector('#modal_prix').value        = btn.getAttribute('data-prix') + ' €/nuit';
});
</script>
