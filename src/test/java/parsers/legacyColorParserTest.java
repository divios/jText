package parsers;

import io.github.divios.jtext.parsers.legacyColorParser;
import org.bukkit.ChatColor;
import org.junit.Assert;
import org.junit.Test;

public class legacyColorParserTest {

    @Test
    public void testParse() {
        String toParse = "&7Hello";
        String parsed = new legacyColorParser().parse(toParse);
        String expected = ChatColor.COLOR_CHAR +  "7Hello";

        Assert.assertEquals(expected, parsed);
    }

    @Test
    public void testUnParse() {
        String toParse = ChatColor.COLOR_CHAR +  "7Hello";
        String parsed = new legacyColorParser().unparse(toParse);
        String expected = "&7Hello";

        Assert.assertEquals(expected, parsed);
    }

}
