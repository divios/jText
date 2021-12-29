import io.github.divios.jtext.JText;
import io.github.divios.jtext.JTextBuilder;
import io.github.divios.jtext.wrappers.Template;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class jTextTest {

    @Test
    public void testBuilder() {
        JText.parse("", Template.of("", ""));
    }

    @Test
    public void tesRegister() {
        JTextBuilder builder = JText.builder()
                .parseHexColors();

        JText.register("custom", builder);
        Assert.assertEquals(JText.get("custom"), builder);
    }

    @Test
    public void testTemplate() {
        JTextBuilder builder = JText.builder()
                .withTemplate(Template.of("shop", "drops"));

        String str = "Renovated item of shop <shop>";
        String strExpected = "Renovated item of shop drops";

        Assert.assertEquals(strExpected, builder.parse(str, null));
    }

    @Test
    public void testTemplate2() {
        JTextBuilder builder = JText.builder()
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"));

        String str = "Renovated <item> of shop <shop>";
        String strExpected = "Renovated dirt of shop drops";

        Assert.assertEquals(strExpected, builder.parse(str, null));
    }

    @Test
    public void testTemplate3() {
        JTextBuilder builder = JText.builder()
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"));

        String str = "Renovated <shop> of shop <shop>";
        String strExpected = "Renovated drops of shop drops";

        Assert.assertEquals(strExpected, builder.parse(str, null));
    }

    @Test
    public void testTemplate4() {
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"));

        String str = "Renovated {item} of shop {shop}";
        String strExpected = "Renovated dirt of shop drops";

        Assert.assertEquals(strExpected, builder.parse(str, null));
    }

    @Test
    public void testTemplate5() {
        Timestamp timer = new Timestamp(System.currentTimeMillis());
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"));

        String str = "Renovated {item} of shop <shop>";
        String strExpected = "Renovated dirt of shop drops";
        String actual = builder.parse(str);

        System.out.printf("Time: %d nanos\n", (new Timestamp(System.currentTimeMillis()).getNanos() - timer.getNanos()));
        Assert.assertEquals(strExpected, actual);
    }

    @Test
    public void testTemplate6() {
        Timestamp timer = new Timestamp(System.currentTimeMillis());
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("player", "DiviosX")
                .withTemplate("block", "COBBLESTONE");

        List<String> str = Arrays.asList("Renovated {item} of shop <shop>", "{player} destroyed the block <block>");
        List<String> strExpected = Arrays.asList("Renovated dirt of shop drops", "DiviosX destroyed the block COBBLESTONE");
        List<String> actual = builder.parse(str);

        System.out.printf("Time: %d ms\n", (new Timestamp(System.currentTimeMillis()).getTime() - timer.getTime()));
        Assert.assertEquals(strExpected, actual);
    }

    @Test
    public void testTemplate7() {
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("player", "DiviosX")
                .withTemplate("block", "COBBLESTONE");

        List<String> str = Arrays.asList("Renovated {item} of shop <shop>", "{player} destroyed the block <block>");
        List<String> strExpected = Arrays.asList("Renovated dirt of shop drops", "DiviosX destroyed the block COBBLESTONE");
        List<String> actual = str.stream()
                .parallel()
                .map(builder::parse)
                .collect(Collectors.toList());

        Assert.assertEquals(strExpected, actual);
    }

    @Test
    public void testTemplate8() {
        Timestamp timer = new Timestamp(System.currentTimeMillis());
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("block", "COBBLESTONE");

        List<String> str = Arrays.asList("Renovated {item} of shop <shop>", "{player} destroyed the block <block>");
        List<String> strExpected = Arrays.asList("Renovated dirt of shop drops", "{player} destroyed the block COBBLESTONE");
        List<String> actual = builder.parse(str);

        System.out.printf("Time: %d ms\n", (new Timestamp(System.currentTimeMillis()).getTime() - timer.getTime()));
        Assert.assertEquals(strExpected, actual);
    }

    @Test
    public void testTemplate9() {
        Timestamp timer = new Timestamp(System.currentTimeMillis());
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("block", "COBBLESTONE");

        List<String> str = Arrays.asList("Renovated <yellow> {item} of shop <shop>", "{player} destroyed the block <block>");
        List<String> strExpected = Arrays.asList("Renovated §f§f§f§f§0§0 dirt of shop drops", "{player} destroyed the block COBBLESTONE");
        List<String> actual = builder.parse(str);

        System.out.printf("Time: %d ms\n", (new Timestamp(System.currentTimeMillis()).getTime() - timer.getTime()));
        Assert.assertEquals(strExpected, actual);
    }

    @Test
    public void testTemplate10() {
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("block", "COBBLESTONE")
                .parseHexColors();

        String str = "Renovated <#00FFD1>items of shop drops";
        String expected = "Renovated §0§0§f§f§d§1items of shop drops";
        String actual = builder.parse(str);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testTemplate11() {
        JTextBuilder builder = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("shop", "drops"))
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate("block", "COBBLESTONE")
                .parseHexColors()
                .parseWithMiniText();

        String str = "Renovated <#00FFD1>items of shop <yellow><bold>drops";
        String expected = "Renovated §0§0§f§f§d§1items of shop §f§f§f§f§0§0§ldrops";
        String actual = builder.parse(str);

        Assert.assertEquals(expected, actual);
    }

}
