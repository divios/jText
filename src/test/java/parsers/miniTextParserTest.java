package parsers;

import io.github.divios.jtext.parsers.miniTextParser;
import org.junit.Assert;
import org.junit.Test;

public class miniTextParserTest {

    @Test
    public void testDefaultMarkdown() {
        String str = "Renovated <yellow>items of shop <green><bold>drops";
        String expected = "Renovated &eitems of shop &a&ldrops";
        String actual = new miniTextParser().parse(str);

        Assert.assertEquals(expected, actual);
    }

}
