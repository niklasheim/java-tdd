package at.itkolleg.ase.tdd.kino;

import static at.itkolleg.ase.tdd.kino.Zeitfenster.ABEND;
import static at.itkolleg.ase.tdd.kino.Zeitfenster.NACHT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class testTestFactory {

    @Mock
    private KinoSaal kinosaalMock; //Mocking Stub zum Testen
    private KinoSaal kinosaalOriginal;
    private LocalDate localDateOriginal;
    private Vorstellung vorstellungOriginal;
    private Vorstellung vorstellungOriginal2;
    private KinoVerwaltung kinoVerwaltungOriginal;

    @BeforeEach
    void setup() {
        //Saal anlegen
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 20);
        map.put('B', 10);
        map.put('C', 15);
        kinosaalOriginal = new KinoSaal("KS2", map);

        //Vorsellung anlegen
        localDateOriginal = LocalDate.now();
        vorstellungOriginal = new Vorstellung(kinosaalOriginal, NACHT, localDateOriginal, "Iron Man", 10.50F);
        vorstellungOriginal2 = new Vorstellung(kinosaalOriginal, ABEND, localDateOriginal, "Iron Man 2", 10.50F);

        //KinoVerwaltung anlegen
        kinoVerwaltungOriginal = new KinoVerwaltung();
        kinoVerwaltungOriginal.einplanenVorstellung(vorstellungOriginal);
        kinoVerwaltungOriginal.einplanenVorstellung(vorstellungOriginal2);
    }


    @TestFactory
    public Collection<DynamicTest> kaufeTicketCollection() {

        List<DynamicTest> testListe = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            Vorstellung vorstellung = kinoVerwaltungOriginal.getVorstellungen().get(i % 2);
            char reihe = (char) ((i % 3) + 65);
            int platz = i % 12;
            int geld = i;

            testListe.add(dynamicTest(vorstellung.getFilm() + ", " + reihe + platz + ", " + geld + "€",
                    () -> assertDoesNotThrow(() -> {
                        try {
                            kinoVerwaltungOriginal.kaufeTicket(vorstellung, reihe, platz, geld);
                            System.out.println("Erfolgreich!");
                        } catch (IllegalArgumentException e) {
                            boolean errGeld = "Nicht ausreichend Geld.".equals(e.getMessage());
                            boolean errPlatz = e.getMessage().contains("existiert nicht");
                            assertTrue(errGeld || errPlatz);
                        } catch (IllegalStateException e) {
                            assertTrue(e.getMessage().contains("ist bereits belegt."));
                        }
                    })));
        }

        // for (DynamicTest dynamicTest : testListe) {
        //     System.out.println(dynamicTest.getDisplayName());
        // }

        return testListe;
    }

}