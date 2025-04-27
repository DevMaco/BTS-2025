
CREATE DATABASE IF NOT EXISTS neige;
USE neige;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    mot_de_passe VARCHAR(255),
    type ENUM('client', 'proprietaire') NOT NULL
);

CREATE TABLE logements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    proprietaire_id INT,
    titre VARCHAR(255),
    description TEXT,
    adresse VARCHAR(255),
    prix_par_nuit DECIMAL(10, 2),
    disponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (proprietaire_id) REFERENCES user(id)
);

CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    logement_id INT,
    date_debut DATE,
    date_fin DATE,
    statut ENUM('en_attente', 'acceptee', 'annulee') DEFAULT 'en_attente',
    date_reservation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (client_id) REFERENCES user(id),
    FOREIGN KEY (logement_id) REFERENCES logements(id)
);


-- Insertion d'utilisateurs (clients et propriétaires)
INSERT INTO user (nom, email, mot_de_passe, type) VALUES
('Alice Martin', 'alice@mail.com', '1234', 'client'),
('Bob Dupont', 'bob@mail.com', '1234', 'client'),
('Claire Proprio', 'claire@mail.com', '1234', 'proprietaire'),
('David Logeur', 'david@mail.com', '1234', 'proprietaire');

-- Insertion de logements
INSERT INTO logements (proprietaire_id, titre, description, adresse, prix_par_nuit, disponible) VALUES
(3, 'Chalet à la montagne', 'Magnifique chalet avec vue sur les Alpes', '123 Rue des Neiges', 120.00, TRUE),
(4, 'Studio cosy', 'Petit studio chaleureux pour deux personnes', '456 Avenue du Froid', 75.00, TRUE);

-- Insertion de réservations
INSERT INTO reservations (client_id, logement_id, date_debut, date_fin, statut) VALUES
(1, 1, '2025-02-01', '2025-02-05', 'en_attente'),
(2, 2, '2025-03-10', '2025-03-15', 'acceptee');
