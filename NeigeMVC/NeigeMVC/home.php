<div class="text-center my-5">
    <h1 class="display-4 fw-bold">Bienvenue sur Neige ❄️</h1>
    <p class="lead">La plateforme de réservation de logements entre clients et propriétaires.</p>
    <hr class="my-4">

    <?php if ($_SESSION['type'] === "client"): ?>
        <p class="mb-3">Vous êtes connecté en tant que <span class="badge bg-primary">Client</span></p>
        <a href="index.php?page=2" class="btn btn-outline-primary btn-lg m-2">Réserver un logement</a>
        <a href="index.php?page=3" class="btn btn-outline-secondary btn-lg m-2">Mes réservations</a>
    <?php elseif ($_SESSION['type'] === "proprietaire"): ?>
        <p class="mb-3">Vous êtes connecté en tant que <span class="badge bg-success">Propriétaire</span></p>
        <a href="index.php?page=2" class="btn btn-outline-success btn-lg m-2">Gérer mes logements</a>
        <a href="index.php?page=3" class="btn btn-outline-secondary btn-lg m-2">Réservations reçues</a>
    <?php endif; ?>
</div>
