# jText [![](https://jitpack.io/v/divios/JText.svg)](https://jitpack.io/#divios/JText)

A Spigot library parse/send/manipulate text

jText gives an API to replace text between given tags in a very efficiency and optimized way. It also comes with support
for legacy ampersand minecraft colors, visual components to style your text and support for hex colors and gradients.

Here is an example of the API;

```java
JTextBuilder builder = JText.builder()                // By default, the tag <> is enabled
        .withTag("\\{", "\\}")                       // Add your custom tag
        .withTemplate(Template.of("shop", "drops"))  // The first value is the text to replace
        .withTemplate("item", "dirt")                // and the second is the replacer
        .parseHexColors();                          // Enable hex colors parse

        String toParse = "You bought 1 of {item} of {shop}"
        String parseString = builder.parse(toParse);   // Expected: You bought 1 of dirt of drops
```

## PlaceholderAPI

PlaceholderApi is supported natively, you just have to call `parsePlaceholderAPI()` on the builder and pass a player on
the `parse()` function, just like this:

```java
JTextBuilder builder = JText.builder()
        .parsePlaceholderAPI();

        builder.parse(str, parse);
```

## Components

Components are a more visual way to style your text. Everything you do will be defined with tags. Tags have a start tag
and an end tag (example: `<bold>Hello`).

All the values of the legacy style can be represented with components, you can see all the values here. Bukkit's colors
are also supported and Java colors too. Examples:

```yaml
    not_enough_money: '<gray>Ey, You dont have <red><bold>enough money <gold>to buy this item!'
    inventory_full: '<gray>Ey, You don't have <magic>enough space <dark_green>in your inventory!'
    not_enough_items: '<pink><italic>You dont have this item or the proper amount to sell it'
```

### Hex colors

Hex colors are also supported via components, for example `<#ffc0db>This is hex colors!`

![](https://i.imgur.com/Swu0njx.png)

### Gradient
