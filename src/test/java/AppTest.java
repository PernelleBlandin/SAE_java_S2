import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vue.AppTerminal;

public class AppTest {
    @Test
    public void testsTruncate() {
        assertEquals("Bonjour", AppTerminal.truncate("Bonjour", 100));
        assertEquals("Bonjour", AppTerminal.truncate("Bonjour", 7));
        assertEquals("Bon...", AppTerminal.truncate("Bonjour", 6));
        assertEquals("Bo...", AppTerminal.truncate("Bonjour", 5));
    }
}
