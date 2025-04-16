<?php
$type = $_SESSION['type'];
$id_user = $_SESSION['id'] ?? null;

if ($type == "client") {
    echo "<h2>🏠 Liste des logements disponibles</h2>";
    ?>
    <form method="get" class="row g-3 mb-4">
        <input type="hidden" name="page" value="2">
        <div class="col-md-2">
            <label class="form-label">Prix max (€)</label>
            <input type="number" class="form-control" name="prix_max"
                value="<?= isset($_GET['prix_max']) ? htmlspecialchars($_GET['prix_max']) : '' ?>">
        </div>
        <div class="col-md-3">
            <label class="form-label">Adresse / Ville</label>
            <input type="text" class="form-control" name="adresse"
                value="<?= isset($_GET['adresse']) ? htmlspecialchars($_GET['adresse']) : '' ?>">
        </div>
        <div class="col-md-2">
            <label class="form-label">Disponibilité</label>
            <select name="disponible" class="form-select">
                <option value="">-- Tous --</option>
                <option value="1" <?= (isset($_GET['disponible']) && $_GET['disponible'] == "1") ? "selected" : "" ?>>Disponible</option>
                <option value="0" <?= (isset($_GET['disponible']) && $_GET['disponible'] == "0") ? "selected" : "" ?>>Indisponible</option>
            </select>
        </div>
        <div class="col-md-3">
            <label class="form-label">Recherche</label>
            <input type="text" class="form-control" name="recherche"
                value="<?= isset($_GET['recherche']) ? htmlspecialchars($_GET['recherche']) : '' ?>">
        </div>
        <div class="col-md-2 d-grid align-items-end">
            <button type="submit" class="btn btn-primary">Filtrer</button>
        </div>
    </form>
    <?php

    $prix_max = $_GET['prix_max'] ?? null;
    $adresse = $_GET['adresse'] ?? null;
    $disponible = $_GET['disponible'] ?? null;
    $recherche = $_GET['recherche'] ?? null;

    $logements = $unControleur->getLogements($prix_max, $adresse, $disponible, $recherche);

    foreach ($logements as $logement) {
        echo "<div class='card mb-3'>";
        echo "<div class='card-body'>";
        echo "<h5 class='card-title'>" . $logement['titre'] . "</h5>";
        echo "<p class='card-text'>" . $logement['description'] . "</p>";
        echo "<p class='card-text'><strong>Adresse :</strong> " . $logement['adresse'] . " (" . $logement['code_postal'] . ")</p>";
        echo "<p class='card-text'><strong>Type :</strong> " . $logement['type_logement'] . " | <strong>Chambres :</strong> " . $logement['nb_chambres'] . "</p>";
        echo "<p class='card-text'><strong>Prix :</strong> " . $logement['prix_par_nuit'] . " €/nuit</p>";
        echo "<form method='post'>
            <input type='hidden' name='logement_id' value='" . $logement['id'] . "'>
            <div class='row'>
                <div class='col'>
                    <label>Date début</label>
                    <input type='date' name='date_debut' class='form-control' required>
                </div>
                <div class='col'>
                    <label>Date fin</label>
                    <input type='date' name='date_fin' class='form-control' required>
                </div>
                <div class='col d-flex align-items-end'>
                    <input type='submit' name='reserver' value='Réserver' class='btn btn-success w-100'>
                </div>
            </div>
        </form>";
        echo "</div></div>";
    }

    if (isset($_POST['reserver'])) {
        $data = [
            "client_id" => $id_user,
            "logement_id" => $_POST['logement_id'],
            "date_debut" => $_POST['date_debut'],
            "date_fin" => $_POST['date_fin']
        ];
        $unControleur->insererReservation($data);
        echo "<div class='alert alert-success'>✅ Réservation envoyée !</div>";
    }

} elseif ($type == "proprietaire") {
    echo "<h2>🛠️ Gestion de vos logements</h2>";
    echo '<form method="post" class="mb-4">
        <h4>Ajouter un nouveau logement</h4>
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
                <input type="text" name="type_logement" placeholder="Type (F3, F4...)" class="form-control" required>
            </div>
            <div class="col-md-12">
                <textarea name="description" class="form-control" placeholder="Description" required></textarea>
            </div>
            <div class="col-md-2">
                <input type="number" name="prix_par_nuit" placeholder="Prix (€)" class="form-control" step="0.01" required>
            </div>
            <div class="col-md-2">
                <input type="submit" name="ajouterLogement" value="Ajouter" class="btn btn-success w-100">
            </div>
        </div>
    </form>';

    if (isset($_POST['ajouterLogement'])) {
        $data = [
            "proprietaire_id" => $id_user,
            "titre" => $_POST['titre'],
            "description" => $_POST['description'],
            "adresse" => $_POST['adresse'],
            "code_postal" => $_POST['code_postal'],
            "nb_chambres" => $_POST['nb_chambres'],
            "type_logement" => $_POST['type_logement'],
            "prix_par_nuit" => $_POST['prix_par_nuit']
        ];
        $unControleur->ajouterLogement($data);
        echo "<div class='alert alert-success'>✅ Logement ajouté avec succès.</div>";
        header("Refresh:0");
    }

    
if (isset($_POST['modifier'])) {
    $logementModif = $unControleur->getLogementById($_POST['logement_id']);
    ?>
    <form method="post" class="mb-4">
        <h4>Modifier le logement</h4>
        <input type="hidden" name="id" value="<?= $logementModif['id'] ?>">
        <div class="row g-3">
            <div class="col-md-3">
                <input type="text" name="titre" value="<?= $logementModif['titre'] ?>" class="form-control" required>
            </div>
            <div class="col-md-3">
                <input type="text" name="adresse" value="<?= $logementModif['adresse'] ?>" class="form-control" required>
            </div>
            <div class="col-md-2">
                <input type="text" name="code_postal" value="<?= $logementModif['code_postal'] ?>" class="form-control" required>
            </div>
            <div class="col-md-2">
                <input type="number" name="nb_chambres" value="<?= $logementModif['nb_chambres'] ?>" class="form-control" required>
            </div>
            <div class="col-md-2">
                <input type="text" name="type_logement" value="<?= $logementModif['type_logement'] ?>" class="form-control" required>
            </div>
            <div class="col-md-12">
                <textarea name="description" class="form-control" required><?= $logementModif['description'] ?></textarea>
            </div>
            <div class="col-md-2">
                <input type="number" name="prix_par_nuit" value="<?= $logementModif['prix_par_nuit'] ?>" class="form-control" step="0.01" required>
            </div>
            <div class="col-md-2">
                <div class="d-flex gap-2">
            <input type="submit" name="validerModification" value="Enregistrer" class="btn btn-primary w-50">
            <a href="index.php?page=2" class="btn btn-secondary w-50">Annuler</a>
        </div>
            </div>
        </div>
    </form>
    <?php
}
if (isset($_POST['validerModification'])) {
    $data = [
        "id" => $_POST['id'],
        "titre" => $_POST['titre'],
        "description" => $_POST['description'],
        "adresse" => $_POST['adresse'],
        "code_postal" => $_POST['code_postal'],
        "nb_chambres" => $_POST['nb_chambres'],
        "type_logement" => $_POST['type_logement'],
        "prix_par_nuit" => $_POST['prix_par_nuit']
    ];
    $unControleur->modifierLogement($data);
    echo "<div class='alert alert-info'>✅ Logement modifié avec succès.</div>";
    header("Refresh:0");
}

$logements = $unControleur->getLogementsDuProprio($id_user);
    foreach ($logements as $logement) {
        echo "<div class='card mb-3'>";
        echo "<div class='card-body'>";
        echo "<h5 class='card-title'>" . $logement['titre'] . "</h5>";
        echo "<p class='card-text'>" . $logement['description'] . "</p>";
        echo "<p class='card-text'><strong>Adresse :</strong> " . $logement['adresse'] . " (" . $logement['code_postal'] . ")</p>";
        echo "<p class='card-text'><strong>Type :</strong> " . $logement['type_logement'] . " | <strong>Chambres :</strong> " . $logement['nb_chambres'] . "</p>";
        echo "<p class='card-text'><strong>Prix :</strong> " . $logement['prix_par_nuit'] . " €/nuit</p>";
        echo "<form method='post' class='d-flex gap-2'>
                <input type='hidden' name='logement_id' value='" . $logement['id'] . "'>
                <form method='post' class='d-flex gap-2'>
    <input type='hidden' name='logement_id' value='" . $logement['id'] . "'>
    <button type='submit' name='modifier' class='btn btn-warning'>✏️ Modifier</button>
    <input type='submit' name='supprimer' value='Supprimer' class='btn btn-danger'>
</form>
              </form>";
        echo "</div></div>";
    }

    if (isset($_POST['supprimer'])) {
        $unControleur->supprimerLogement($_POST['logement_id']);
        echo "<div class='alert alert-danger'>❌ Logement supprimé.</div>";
        header("Refresh:0");
    }
}
?>
