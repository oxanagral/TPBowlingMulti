package bowling;

import java.util.Map;
import java.util.TreeMap;

public class PartieMultiJoueurs implements IPartieMultiJoueurs {
	private TreeMap<String, PartieMonoJoueur> partieMonoJoueurs;

	/**
	 * Démarre une nouvelle partie pour un groupe de joueurs
	 *
	 * @param nomsDesJoueurs un tableau des noms de joueurs (il faut au moins un joueur)
	 * @return une chaîne de caractères indiquant le prochain joueur,
	 * de la forme "Prochain tir : joueur Bastide, tour n° 1, boule n° 1"
	 * @throws IllegalArgumentException si le tableau est vide ou null
	 */
	@Override
	public String demarreNouvellePartie(String[] nomsDesJoueurs) throws IllegalArgumentException {
		if (nomsDesJoueurs == null || nomsDesJoueurs.length == 0) {
			throw new IllegalArgumentException("La liste des joueurs est vide ou nulle");
		}
		partieMonoJoueurs = new TreeMap<>();
		for (String nom : nomsDesJoueurs) {
			partieMonoJoueurs.put(nom, new PartieMonoJoueur());
		}

		return getProchainLanceEtBoulePour(partieMonoJoueurs.firstKey());
	}

	/**
	 * Enregistre le nombre de quilles abattues pour le joueur courant, dans le tour courant, pour la boule courante
	 *
	 * @param nombreDeQuillesAbattues : nombre de quilles abattues à ce lancer
	 * @return une chaîne de caractères indiquant le prochain joueur,
	 * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 2",
	 * ou bien "Partie terminée" si la partie est terminée.
	 * @throws IllegalStateException si la partie n'est pas démarrée.
	 */
	@Override
	public String enregistreLancer(int nombreDeQuillesAbattues) throws IllegalStateException {
		if (partieMonoJoueurs == null) {
			throw new IllegalArgumentException("Impossible d'enregistrer un lancer : aucune partie n'est démarrée");
		}

		if (lesPartiesSontTerminees()) {
			return "Partie terminée";
		}

		String joueurCourant = getProchainJoueur();
		partieMonoJoueurs.get(joueurCourant).enregistreLancer(nombreDeQuillesAbattues);

		return getProchainLanceEtBoulePour(getProchainJoueur());
	}

	/**
	 * Donne le score pour le joueur playerName
	 *
	 * @param nomDuJoueur le nom du joueur recherché
	 * @return le score pour ce joueur
	 * @throws IllegalArgumentException si nomDuJoueur ne joue pas dans cette partie
	 */
	@Override
	public int scorePour(String nomDuJoueur) throws IllegalArgumentException {
		PartieMonoJoueur partie = partieMonoJoueurs.get(nomDuJoueur);
		if (partie == null) {
			throw new IllegalArgumentException("Le joueur " + nomDuJoueur + " n'existe pas dans cette partie");
		}
		return partie.score();
	}

	public String getProchainJoueur() {
		String prochain = partieMonoJoueurs.firstKey();
		for (Map.Entry<String, PartieMonoJoueur> entry : partieMonoJoueurs.entrySet()) {
			if (entry.getValue().numeroTourCourant() < partieMonoJoueurs.get(prochain).numeroTourCourant()) {
				prochain = entry.getKey();
			}
		}
		return prochain;
	}

	public String getProchainLanceEtBoulePour(String joueur) {
		return "Prochain tir : joueur " + joueur
			+ ", tour n° " + partieMonoJoueurs.get(joueur).numeroTourCourant()
			+ ", boule n° " + partieMonoJoueurs.get(joueur).numeroProchainLancer();
	}

	public Boolean lesPartiesSontTerminees() {
		for (PartieMonoJoueur partie : partieMonoJoueurs.values()) {
			if (!partie.estTerminee()) {
				return false;
			}
		}
		return true;
	}
}
