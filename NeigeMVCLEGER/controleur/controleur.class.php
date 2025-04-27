<?php
require_once("modele/modele.class.php");

class Controleur {
    private $unModele;

    public function __construct() {
        $this->unModele = new Modele();
    }

    public function verifConnexion($email, $mdp) {
        return $this->unModele->selectWhereUser($email, $mdp);
    }

    public function inscrireUser($data) {
        return $this->unModele->insertUser($data);
    }

    public function getLogementsDuProprio($id_proprio) {
        return $this->unModele->selectLogementsByProprietaire($id_proprio);
    }

    public function getLogements() {
        return $this->unModele->selectAllLogements();
    }

    public function ajouterLogement($data) {
        $this->unModele->insertLogement($data);
    }

    public function supprimerLogement($id) {
        $this->unModele->deleteLogement($id);
    }

    public function getLogementById($id) {
        return $this->unModele->selectLogementById($id);
    }

    public function modifierLogement($data) {
        $this->unModele->updateLogement($data);
    }

    public function insererReservation($data) {
        $this->unModele->insertReservation($data);
    }

    public function getReservationsClient($client_id) {
        return $this->unModele->selectReservationsByClient($client_id);
    }

    public function getReservationsProprietaire($proprio_id) {
        return $this->unModele->selectReservationsByProprietaire($proprio_id);
    }

    public function majStatutReservation($id, $statut) {
        $this->unModele->updateReservationStatus($id, $statut);
    }
}
?>
