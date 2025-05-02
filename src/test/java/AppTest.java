import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
    @Test
    public void testsTruncate() {
        assertEquals("Bonjour", App.truncate("Bonjour", 100));
        assertEquals("Bonjour", App.truncate("Bonjour", 7));
        assertEquals("Bon...", App.truncate("Bonjour", 6));
        assertEquals("Bo...", App.truncate("Bonjour", 5));
    }
}
