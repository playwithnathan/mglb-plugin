package me.playwithnathan.util;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

    /**
     * The string to color
     * @param str the string with hex codes in it
     * @return the colored string
     */
    public static String color(String str) {
        Pattern HEX_PATTERN = Pattern.compile("&#(\\w{5}[0-9a-f])");
        Matcher matcher = HEX_PATTERN.matcher(str);
        StringBuffer buffer = new StringBuffer();

        while(matcher.find())
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());

        return ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString());
    }

    public static String normalizeColor(String str) {
        return str.replaceAll("§", "&");
    }

}