package bowling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MultiPlayerGameTest {
	PartieMultiJoueurs partieMulti;
	String[] joueurs = {"j1", "j2", "j3"};

	@BeforeEach
	public void setUp() {
		partieMulti = new PartieMultiJoueurs();
		partieMulti.demarreNouvellePartie(joueurs);
	}

	@Test
	void testPartieNonInitialisee() {
		PartieMultiJoueurs partieNonInit = new PartieMultiJoueurs();
		try {
			partieNonInit.enregistreLancer(7);
			fail("Aucune exception levée lorsqu'un lancer est effectué sans démarrer une partie");
		} catch (IllegalArgumentException e) {
			// Exception attendue
		}
	}

	@Test
	void testDemarrerAvecTableauVide() {
		PartieMultiJoueurs partieVide = new PartieMultiJoueurs();
		String[] joueursVides = {};
		try {
			partieVide.demarreNouvellePartie(joueursVides);
			fail("Aucune exception levée lors du démarrage d'une partie avec un tableau vide");
		} catch (IllegalArgumentException e) {
			// Exception attendue
		}
	}

	@Test
	void testInitialisationPartie() {
		PartieMultiJoueurs nouvellePartie = new PartieMultiJoueurs();
		assertEquals("Prochain tir : joueur j1, tour n° 1, boule n° 1",
			partieMulti.demarreNouvellePartie(joueurs),
			"La partie n'a pas été correctement initialisée");
	}

	@Test
	void testLancerApresStrike() {
		assertEquals("Prochain tir : joueur j2, tour n° 1, boule n° 1",
			partieMulti.enregistreLancer(10));
	}

	@Test
	void testScoreJoueurInconnu() {
		try {
			partieMulti.scorePour("j4");
			fail("Aucune exception levée pour un joueur inconnu");
		} catch (IllegalArgumentException e) {
			// Exception attendue
		}
	}

	@Test
	void testScorePartieSimple() {
		partieMulti.enregistreLancer(5); // j1
		partieMulti.enregistreLancer(3); // j1
		partieMulti.enregistreLancer(10); // j2
		partieMulti.enregistreLancer(10); // j3
		partieMulti.enregistreLancer(7); // j1
		partieMulti.enregistreLancer(3); // j1
		partieMulti.enregistreLancer(5); // j2
		partieMulti.enregistreLancer(2); // j2
		partieMulti.enregistreLancer(10); // j3
		partieMulti.enregistreLancer(3); // j1
		partieMulti.enregistreLancer(4); // j1
		partieMulti.enregistreLancer(0); // j2
		partieMulti.enregistreLancer(0); // j2
		partieMulti.enregistreLancer(7); // j3

		assertEquals(28, partieMulti.scorePour("j1"));
		assertEquals(24, partieMulti.scorePour("j2"));
		assertEquals(51, partieMulti.scorePour("j3"));
	}

	@Test
	void testPartieComplete() {
		int compteur = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				partieMulti.enregistreLancer(1);
			}
		}
		assertEquals("Partie terminée", partieMulti.enregistreLancer(1));
	}
}
