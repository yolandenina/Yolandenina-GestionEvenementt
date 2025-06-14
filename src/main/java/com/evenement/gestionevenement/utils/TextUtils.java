package com.evenement.gestionevenement.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {
    private static final Pattern NAMED_PLACEHOLDER_PATTERN = Pattern.compile("\\{([a-zA-Z0-9_]+)\\}");

    public static String format(String message, Map<String, Object> args) {
        if (message == null || args == null || args.isEmpty()) {
            return message;
        }

        Matcher matcher = NAMED_PLACEHOLDER_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String placeholder = matcher.group(1);
            Object replacement = args.get(placeholder);
            matcher.appendReplacement(sb, replacement != null ? Matcher.quoteReplacement(replacement.toString()) : "{" + placeholder + "}");
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
