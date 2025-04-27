
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

<div class="d-flex justify-content-center align-items-center" style="height: 100vh;">
    <div class="form-wrapper" style="width: 100%; max-width: 600px;">
        <h3 class="text-center mb-4">Inscription à Neige ❄️</h3>
        <form method="post">
            <div class="mb-3">
                <label class="form-label">Nom complet</label>
                <input type="text" name="nom" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mot de passe</label>
                <input type="password" name="mot_de_passe" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Confirmer mot de passe</label>
                <input type="password" name="mot_de_passe_confirm" class="form-control" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Type</label>
                <select name="type" class="form-select" required>
                    <option value="client">Client</option>
                    <option value="proprietaire">Propriétaire</option>
                </select>
            </div>
            <div class="d-grid">
                <input type="submit" name="Inscription" value="S'inscrire" class="btn btn-success">
            </div>
        </form>
        <p class="mt-3 text-center">Déjà inscrit ? <a href="index.php">Se connecter</a></p>
    </div>
</div>
