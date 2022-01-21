package parsers;

import io.github.divios.jtext.parsers.gradientColorParser;
import org.junit.Assert;
import org.junit.Test;

public class GradientColorParserTest {

    @Test
    public void testGradient() {
        gradientColorParser parser = new gradientColorParser();
        String toParse = "<gradient:#DAF7A6:#581845>test</gradient>";
        String expected = "§x§d§a§f§7§a§6t§x§a§f§a§d§8§6e§x§8§3§6§2§6§5s§x§5§8§1§8§4§5t";
        String actual = parser.parse(toParse);

        Assert.assertEquals(expected, actual);
    }

}
