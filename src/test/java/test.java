import io.github.divios.jtext.JText;
import io.github.divios.jtext.wrappers.Template;
import org.junit.Assert;
import org.junit.Test;

public class test {

    @Test
    public void test() {
        String toParse = "Something <item>";
        String expected = "Something dirt";
        String actual = JText.parse(toParse, Template.of("item", "dirt"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test2() {
        String toParse = "Something <item>, most something <shop>";
        String expected = "Something dirt, most something drops";
        String actual = JText.parse(toParse, Template.of("item", "dirt"),
                Template.of("shop", "drops"));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void test3() {
        String toParse = "Something {item}, most something {shop}";
        String expected = "Something dirt, most something drops";
        String actual = JText.builder()
                .withTag("\\{", "\\}")
                .withTemplate(Template.of("item", "dirt"))
                .withTemplate(Template.of("shop", "drops"))
                .parse(toParse);

        Assert.assertEquals(expected, actual);
    }

}
