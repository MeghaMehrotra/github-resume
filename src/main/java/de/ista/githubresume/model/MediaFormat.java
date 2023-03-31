package de.ista.githubresume.model;

import java.util.Locale;

/**
 * @author mmehrotra
 */
public enum MediaFormat {
    json,
    xml;

    public static MediaFormat getMediaFormat(String value){
        return valueOf(value.toLowerCase());
    }
}
