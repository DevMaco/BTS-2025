<?php
class Modele {
    private $pdo;

    public function __construct() {
        try {
            $this->pdo = new PDO("mysql:host=localhost;dbname=neige", "root", "");
            $this->pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        } catch (PDOException $exp) {
            echo "Erreur de connexion à la BDD : " . $exp->getMessage();
        }
    }

    public function selectWhereUser($email, $mdp) {
        $stmt = $this->pdo->prepare("SELECT * FROM user WHERE email = :email");
        $stmt->execute([":email" => $email]);
        $user = $stmt->fetch();
        if ($user && password_verify($mdp, $user['mot_de_passe'])) {
            return $user;
        }
        return false;
    }

    public function insertUser($data) {
        $stmt = $this->pdo->prepare("INSERT INTO user (nom, email, mot_de_passe, type) VALUES (:nom, :email, :mot_de_passe, :type)");
        return $stmt->execute($data);
    }

    public function selectAllLogements($prix_max = null, $adresse = null, $disponible = null, $recherche = null) {
        $sql = "SELECT * FROM logements WHERE 1=1";
        $params = [];
    
        // Prix max : on teste si ce n'est pas null ET pas vide
        if (!is_null($prix_max) && $prix_max !== '') {
            $sql .= " AND prix_par_nuit <= :prix";
            $params[":prix"] = $prix_max;
        }
    
        if ($adresse) {
            $sql .= " AND adresse LIKE :adresse";
            $params[":adresse"] = "%" . $adresse . "%";
        }
    
        if ($disponible !== null && $disponible !== "") {
            $sql .= " AND disponible = :disponible";
            $params[":disponible"] = $disponible;
        }
    
        if ($recherche) {
            $sql .= " AND (titre LIKE :rech OR description LIKE :rech)";
            $params[":rech"] = "%" . $recherche . "%";
        }
    
        $stmt = $this->pdo->prepare($sql);
        $stmt->execute($params);
        return $stmt->fetchAll();
    }

    public function insertLogement($data) {
        $stmt = $this->pdo->prepare("INSERT INTO logements (proprietaire_id, titre, description, adresse, prix_par_nuit, disponible, code_postal, nb_chambres, type_logement)
                                     VALUES (:proprietaire_id, :titre, :description, :adresse, :prix_par_nuit, 1, :code_postal, :nb_chambres, :type_logement)");
        $stmt->execute($data);
    }

    public function deleteLogement($id) {
        $stmt = $this->pdo->prepare("DELETE FROM logements WHERE id = :id");
        $stmt->execute([":id" => $id]);
    }

    public function selectLogementById($id) {
        $stmt = $this->pdo->prepare("SELECT * FROM logements WHERE id = :id");
        $stmt->execute([":id" => $id]);
        return $stmt->fetch();
    }

    public function updateLogement($data) {
        $stmt = $this->pdo->prepare("UPDATE logements SET titre = :titre, description = :description, adresse = :adresse, prix_par_nuit = :prix_par_nuit WHERE id = :id");
        $stmt->execute($data);
    }

    public function insertReservation($data) {
        $stmt = $this->pdo->prepare("INSERT INTO reservations (client_id, logement_id, date_debut, date_fin, statut)
                                     VALUES (:client_id, :logement_id, :date_debut, :date_fin, 'en_attente')");
        $stmt->execute($data);
    }

    public function selectReservationsByClient($client_id) {
        $stmt = $this->pdo->prepare("SELECT * FROM reservations WHERE client_id = :client_id");
        $stmt->execute([":client_id" => $client_id]);
        return $stmt->fetchAll();
    }

    public function selectReservationsByProprietaire($proprio_id) {
        $stmt = $this->pdo->prepare("SELECT r.* FROM reservations r JOIN logements l ON r.logement_id = l.id WHERE l.proprietaire_id = :proprio_id");
        $stmt->execute([":proprio_id" => $proprio_id]);
        return $stmt->fetchAll();
    }

    public function updateReservationStatus($id, $statut) {
        $stmt = $this->pdo->prepare("UPDATE reservations SET statut = :statut WHERE id = :id");
        $stmt->execute([":statut" => $statut, ":id" => $id]);
    }
    
public function selectLogementsByProprietaire($id_proprio) {
    $stmt = $this->pdo->prepare("SELECT * FROM logements WHERE proprietaire_id = :id");
    $stmt->execute([":id" => $id_proprio]);
    return $stmt->fetchAll();
}
}

?>

