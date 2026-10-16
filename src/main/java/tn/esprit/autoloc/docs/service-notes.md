# Notes - Atelier 4 : IoC, DI et couche Service

## A. Modes d'injection comparés (étape 2)

| Critère | Injection par constructeur | Injection par attribut (`@Autowired`) |
|---|---|---|
| Champ `final` possible ? | Oui | Non |
| Dépendance visible dans la signature de la classe ? | Oui (paramètre du constructeur) | Non (dépendance cachée dans un champ privé) |
| Utilisable avec `new` hors conteneur sans dépendance `null` ? | Oui : le constructeur impose le repository | Non : le champ reste `null` (NullPointerException) |
| Signalé par SonarQube for IDE ? | Non | Oui (règle S6813 - à confirmer dans le panneau) |

Conclusion : le service ne peut jamais exister dans un état incomplet avec le constructeur. C'est la convention AutoLoc.

## B. Services

| Service | Dépendances injectées | Mode | Justification |
|---|---|---|---|
| AgenceServiceImpl | IAgenceRepository | Constructeur (`@RequiredArgsConstructor`) | Champ `final`, dépendance typée par interface, service sans état |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Idem |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Idem ; vérifie aussi l'unicité de l'immatriculation |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Idem |
| ClientServiceImpl | IClientRepository | Constructeur | Idem ; vérifie l'unicité de l'e-mail et du permis |
| ReservationServiceImpl | IReservationRepository | Constructeur | Idem |
| ContratServiceImpl | IContratRepository | Constructeur | Idem ; rattache les paiements au contrat (cascade) |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule : les paiements passent par le Contrat |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Idem |

## C. Messages d'erreur du conteneur (étape 3)

**Message 1 - `NoSuchBeanDefinitionException`**
- Bean concerné : `ContratController` demande un bean de type `IContratService` introuvable.
- Causes probables côté `ContratServiceImpl` : (1) l'annotation `@Service` est oubliée ; (2) la classe est placée en dehors du package racine `tn.esprit.autoloc`, donc non détectée par le scan.
- Correction : ajouter `@Service` ; vérifier que la classe est dans un sous-package de `tn.esprit.autoloc`.

**Message 2 - `NoUniqueBeanDefinitionException`**
- Bean concerné : `NotificationController` ; deux beans du même type existent : `emailNotificateur` et `smsNotificateur`.
- Correction 1 : `@Primary` sur la **classe** du candidat par défaut (ex. `EmailNotificateur`).
- Correction 2 : `@Qualifier("emailNotificateur")` sur le **paramètre du constructeur** du contrôleur (avec Lombok, écrire le constructeur à la main ou configurer `lombok.copyableAnnotations`, car `@Qualifier` n'est pas recopié par défaut).

**Message 3 - dépendance circulaire** (`clientServiceImpl` <-> `reservationServiceImpl`)
- Avec l'injection par constructeur, pour créer A il faut déjà une instance de B, et inversement : le conteneur ne peut créer aucun des deux, le cycle est impossible à contourner (Spring Boot refuse d'ailleurs les cycles par défaut).
- Piste de conception : extraire la responsabilité commune dans un troisième service, ou faire dépendre un des deux services du repository de l'autre plutôt que de son service.

## D. Anomalies SonarQube for IDE

> Remplacer / compléter par ce que vous observez réellement dans votre panneau.

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| Injection par attribut `@Autowired` dans `ContratServiceImpl` (étape 2) | S6813 : dépendance cachée, champ non final, `null` hors conteneur | Retour à `@RequiredArgsConstructor` + champ `private final` |
| `throw new RuntimeException(...)` dans `findById` (expérience étape 4) | S112 : lever une exception générique empêche de la traiter précisément | `ResourceNotFoundException` (exception métier dédiée) |
| `LocalDate.now()` sans fuseau (ClientServiceImpl, CrudDemoRunner) | S8688 : résultat dépendant du fuseau de la machine | `LocalDate.now(ZoneId.of("Africa/Tunis"))` |
| Appel d'une méthode `@Transactional` via `this` (Atelier 3) | S6809 : l'appel direct contourne le proxy Spring | Méthode privée partagée / suppression de `@Transactional` inutile |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est pas écrit dans notre code : c'est le conteneur Spring (`ApplicationContext`) qui appelle le constructeur au démarrage, après avoir détecté la classe grâce à `@Service` et au scan des composants. Il lui fournit le proxy de `IContratRepository`.
2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?** Pour dépendre d'une abstraction : on peut changer ou remplacer l'implémentation (autre version, test avec une doublure) sans modifier le contrôleur. Le couplage reste faible.
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par tous les threads (requêtes simultanées). Exemple de bug : un attribut `contratCourant` modifié par la requête A est écrasé par la requête B ; A traite alors le mauvais contrat.
4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?** Passer directement l'objet reçu à `save` ferait un `merge` qui écraserait toutes les colonnes (les champs non envoyés deviendraient `null`) et, avec `orphanRemoval`, pourrait vider la collection de paiements. On charge l'existant, on recopie uniquement les attributs modifiables, puis on enregistre.
5. **`@Component` / `@Bean` ?** `@Component` se pose sur une classe que l'on écrit, détectée par le scan. `@Bean` se pose sur une méthode d'une classe `@Configuration` et construit explicitement un bean, utile pour une classe que l'on ne peut pas annoter (bibliothèque, JDK).
   **`@Primary` / `@Qualifier` ?** `@Primary` désigne sur la classe le candidat par défaut ; `@Qualifier("nom")` choisit explicitement un bean à l'endroit de l'injection.
   **Pourquoi `IPaiementService` est en lecture seule ?** Le paiement est une composition de `Contrat` : l'enfant n'est modifié qu'à travers son parent (cascade / `orphanRemoval`). Créer ou supprimer un paiement seul contournerait le contrat.
