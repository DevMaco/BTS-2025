
<style>
body {
    background: url('bg_neige.jpg') no-repeat center center fixed;
    background-size: cover;
}
.form-wrapper {
    backdrop-filter: blur(10px);
    background-color: rgba(255, 255, 255, 0.8);
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 0 20px rgba(0,0,0,0.2);
}
</style>

<div class="d-flex justify-content-center align-items-center" style="height: 85vh;">
    <div class="form-wrapper" style="width: 100%; max-width: 500px;">
        <h3 class="text-center mb-4">Connexion à Neige ❄️</h3>
        <form method="post">
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mot de passe</label>
                <input type="password" name="mdp" class="form-control" required>
            </div>
            <div class="d-grid">
                <input type="submit" name="Connexion" value="Se connecter" class="btn btn-primary">
            </div>
        </form>
        <p class="mt-3 text-center">Pas encore inscrit ? <a href="index.php?action=inscription">Inscription</a></p>
    </div>
</div>

<?php include('footer.php'); ?>
