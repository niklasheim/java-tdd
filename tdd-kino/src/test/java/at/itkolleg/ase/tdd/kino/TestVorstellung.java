package at.itkolleg.ase.tdd.kino;


import static at.itkolleg.ase.tdd.kino.Zeitfenster.NACHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestVorstellung {

    @Mock
    private KinoSaal kinosaalMock; //Mocking Stub zum Testen
    private KinoSaal kinosaalOriginal;
    private LocalDate localDateOriginal;
    private Vorstellung vorstellungOriginal;

    @BeforeEach
    void setup() {
        //Saal anlegen
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 10);
        map.put('B', 10);
        map.put('C', 15);
        kinosaalOriginal = new KinoSaal("KS2", map);

        //Vorsellung anlegen
        localDateOriginal = LocalDate.now();
        vorstellungOriginal = new Vorstellung(kinosaalOriginal, NACHT, localDateOriginal, "Iron Man", 13.50F);
    }

    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    @DisplayName("Pruefe Film")
    void testVorstellungFilm() {
        assertEquals("Iron Man", vorstellungOriginal.getFilm(), "Falscher Film");
    }

    @Test
    @DisplayName("Pruefe Konosaal")
    void testVorstellungSaal() {
        assertEquals(kinosaalOriginal, vorstellungOriginal.getSaal(), "Falscher Kinosaal");
    }

    @Test
    @DisplayName("Pruefe Zeitfenster")
    void testVorstellungZeitfenster() {
        assertEquals(NACHT, vorstellungOriginal.getZeitfenster(), "Falsches Zeitfenster");
    }

    @Test
    @DisplayName("Pruefe Datum")
    void testVorstellungDatum() {
        assertEquals(localDateOriginal, vorstellungOriginal.getDatum(), "Falsches Datum");
    }



}
