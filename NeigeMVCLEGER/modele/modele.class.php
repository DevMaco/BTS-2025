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
        $stmt = $this->pdo->prepare("SELECT * FROM user WHERE email = :email AND mot_de_passe = :mdp");
        $stmt->execute([":email" => $email, ":mdp" => $mdp]);
        return $stmt->fetch();
    }
    

    public function insertUser($data) {
        $stmt = $this->pdo->prepare("INSERT INTO user (nom, email, mot_de_passe, type) VALUES (:nom, :email, :mot_de_passe, :type)");
        return $stmt->execute($data);
    }

    public function selectAllLogements($prix_max = null, $adresse = null, $disponible = null, $recherche = null) {
        $sql = "SELECT * FROM logements WHERE 1=1";
        $params = [];

        if ($prix_max) {
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

    public function insertLogement(array $data)
    {
        $sql = "
          INSERT INTO logements
            (proprietaire_id, titre, description, adresse,
             code_postal, nb_chambres, type_logement,
             prix_par_nuit, disponible)
          VALUES
            (:proprietaire_id, :titre, :description, :adresse,
             :code_postal, :nb_chambres, :type_logement,
             :prix_par_nuit, 1)
        ";
        $stmt = $this->pdo->prepare($sql);
        $stmt->execute([
            ':proprietaire_id' => $data['proprietaire_id'],
            ':titre'           => $data['titre'],
            ':description'     => $data['description'],
            ':adresse'         => $data['adresse'],
            ':code_postal'     => $data['code_postal'],
            ':nb_chambres'     => $data['nb_chambres'],
            ':type_logement'   => $data['type_logement'],
            ':prix_par_nuit'   => $data['prix_par_nuit'],
        ]);
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

    public function updateLogement(array $data)
    {
        $sql = "
          UPDATE logements SET
            titre         = :titre,
            description   = :description,
            adresse       = :adresse,
            code_postal   = :code_postal,
            nb_chambres   = :nb_chambres,
            type_logement = :type_logement,
            prix_par_nuit = :prix_par_nuit
          WHERE id = :id
        ";
        $stmt = $this->pdo->prepare($sql);
        $stmt->execute([
            ':id'            => $data['id'],
            ':titre'         => $data['titre'],
            ':description'   => $data['description'],
            ':adresse'       => $data['adresse'],
            ':code_postal'   => $data['code_postal'],
            ':nb_chambres'   => $data['nb_chambres'],
            ':type_logement' => $data['type_logement'],
            ':prix_par_nuit' => $data['prix_par_nuit'],
        ]);
    }

    public function insertReservation($data) {
        $sql = "
          INSERT INTO reservations
            (client_id, nom_client, prenom, email_client, telephone,
             logement_id, date_debut, date_fin, nb_personnes,
             commentaire, statut)
          VALUES
            (:client_id, :nom_client, :prenom, :email_client, :telephone,
             :logement_id, :date_debut, :date_fin, :nb_personnes,
             :commentaire, 'en_attente')
        ";
        $stmt = $this->pdo->prepare($sql);
        $stmt->execute([
          ":client_id"    => $data["client_id"],
          ":nom_client"   => $data["nom_client"],
          ":prenom"       => $data["prenom"],
          ":email_client" => $data["email_client"],
          ":telephone"    => $data["telephone"],
          ":logement_id"  => $data["logement_id"],
          ":date_debut"   => $data["date_debut"],
          ":date_fin"     => $data["date_fin"],
          ":nb_personnes" => $data["nb_personnes"],
          ":commentaire"  => $data["commentaire"],
        ]);
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
public function isLogementDispoAujourdHui(int $logement_id): bool
{
    $today = date('Y-m-d');
    $stmt = $this->pdo->prepare("
        SELECT COUNT(*) 
          FROM reservations
         WHERE logement_id = :id
           AND statut IN ('en_attente','acceptee')
           AND :today BETWEEN date_debut AND date_fin
    ");
    $stmt->execute([
      ':id'    => $logement_id,
      ':today' => $today,
    ]);
    return $stmt->fetchColumn() == 0;
}
}

?>

