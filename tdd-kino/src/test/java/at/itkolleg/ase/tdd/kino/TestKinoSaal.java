package at.itkolleg.ase.tdd.kino;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class TestKinoSaal {

    @Mock
    private KinoSaal kinosaalMock; //Mocking Stub zum Testen

    private KinoSaal kinosaalOriginal;

    @BeforeEach
    void setup() {
        //Saal anlegen
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 10);
        map.put('B', 10);
        map.put('C', 15);
        kinosaalOriginal = new KinoSaal("KS2", map);
    }

    @Test
    void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void testKinosaalName() {
        assertEquals("KS2", kinosaalOriginal.getName());
    }

    @Test
    void testKinoSaalPruefePlatz() {
        assertTrue(kinosaalOriginal.pruefePlatz('A', 10));
        assertTrue(kinosaalOriginal.pruefePlatz('B', 9));
        assertFalse(kinosaalOriginal.pruefePlatz('X', 9));
        assertFalse(kinosaalOriginal.pruefePlatz('C', 20));
    }

    @Test
    void testKinosaalEquals(){
        assertTrue(kinosaalOriginal.equals(kinosaalOriginal));
        assertFalse(kinosaalOriginal.equals(new Object()));
    }


}
