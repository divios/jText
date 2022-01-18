package parsers;

import io.github.divios.jtext.parsers.HexColorParser;
import io.github.divios.jtext.parsers.legacyColorParser;
import org.junit.Assert;
import org.junit.Test;

public class HexColorParserTest {

    @Test
    public void testHex() {
        String hex = "#fb1239 This is an example #fc1245";
        String parsed = new HexColorParser().parse(hex);
        String expected = new legacyColorParser().parse("&x&f&b&1&2&3&9 This is an example &x&f&c&1&2&4&5");

        Assert.assertEquals(expected, parsed);
    }



}
