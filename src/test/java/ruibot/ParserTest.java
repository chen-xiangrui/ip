package ruibot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void read_deadlineInput_success() {
        assertEquals("deadline", Parser.read("deadline return book /by 2024-02-01 1800"));
    }
}
