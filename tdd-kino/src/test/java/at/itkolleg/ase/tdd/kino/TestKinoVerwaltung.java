package at.itkolleg.ase.tdd.kino;


import static at.itkolleg.ase.tdd.kino.Zeitfenster.ABEND;
import static at.itkolleg.ase.tdd.kino.Zeitfenster.NACHT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestKinoVerwaltung {

    @Mock
    private KinoSaal kinosaalMock; //Mocking Stub zum Testen
    private KinoSaal kinosaalOriginal;
    private LocalDate localDateOriginal;
    private Vorstellung vorstellungOriginal;
    private KinoVerwaltung kinoVerwaltungOriginal;

    @BeforeEach
    void setup() {
        //Saal anlegen
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 10);
        map.put('B', 10);
        map.put('C', 15);
        kinosaalOriginal = new KinoSaal("KS2", map);

        //Vorstellung anlegen
        localDateOriginal = LocalDate.now();
        vorstellungOriginal = new Vorstellung(kinosaalOriginal, NACHT, localDateOriginal, "Iron Man", 13.50F);

        //KinoVerwaltung anlegen
        kinoVerwaltungOriginal = new KinoVerwaltung();
        kinoVerwaltungOriginal.einplanenVorstellung(vorstellungOriginal);
    }

    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void testExceptionBereitsEingeplant(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            kinoVerwaltungOriginal.einplanenVorstellung(vorstellungOriginal);
        });
        assertEquals("Die Vorstellung ist bereits eingeplant", exception.getMessage());
    }

    @Test
    void testEinplanenVorstellung(){
        List<Vorstellung> vorstellungenList = new LinkedList<>();
        vorstellungenList.add(vorstellungOriginal);
        assertEquals(vorstellungenList, kinoVerwaltungOriginal.getVorstellungen());
    }

    @Test
    void testEinplanenVorstellungMehrere(){
        ArrayList<Throwable> al = new ArrayList<>();
        List<Vorstellung> vorstellungenList = new LinkedList<>();
        Vorstellung vorstellungOriginal2 = new Vorstellung(kinosaalOriginal, ABEND, localDateOriginal, "Iron Man 2", 23.50F);

        vorstellungenList.add(vorstellungOriginal);
        vorstellungenList.add(vorstellungOriginal2);
        kinoVerwaltungOriginal.einplanenVorstellung(vorstellungOriginal2);

        try {
            assertEquals(vorstellungenList.get(0), kinoVerwaltungOriginal.getVorstellungen().get(0));
        } catch (AssertionError error) {
            al.add(error);
        }

        try {
            assertEquals(vorstellungenList.get(1), kinoVerwaltungOriginal.getVorstellungen().get(1));
        } catch (AssertionError error) {
            al.add(error);
        }

        if(al.size() > 0){
            for (Throwable error : al) {
                System.out.println(error.getMessage());
            }
            fail();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "B, 3, 100",
            "B, 2, 50",
            "A, 1, 20",
            "A, 4, 20",
            "C, 5, 50",
            "C, 6, 100"
             })
    void paramatertestKaufeTicket(char reihe, int platz, float geld) {
        Ticket ticket = new Ticket(kinosaalOriginal.getName(), NACHT, localDateOriginal, reihe, platz);
        Ticket vorstellungTicket = vorstellungOriginal.kaufeTicket(reihe, platz, geld);

        assertAll("tickets",
                () -> assertEquals(ticket.getSaal(), vorstellungTicket.getSaal()),
                () -> assertEquals(ticket.getZeitfenster(), vorstellungTicket.getZeitfenster()),
                () -> assertEquals(ticket.getDatum(), vorstellungTicket.getDatum()),
                () -> assertEquals(ticket.getReihe(), vorstellungTicket.getReihe()),
                () -> assertEquals(ticket.getPlatz(), vorstellungTicket.getPlatz())
        );
    }

}
