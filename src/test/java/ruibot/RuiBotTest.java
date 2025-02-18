package ruibot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuiBotTest {
    @Test
    public void getResponse_emptyTaskException_success() {
        RuiBot ruiBot = new RuiBot("./data/ruibot.txt");
        assertEquals("OOPS!! Task cannot be empty.", ruiBot.getResponse("todo "));
    }
}
