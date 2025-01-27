package com.sik.footy.helpers;

public class FootyHelper {
    public String abbreviatedName(String name) {
        String[] words = name.split(" ");
        if (words.length > 2) {
            return words[0].substring(0, 1).toUpperCase() + words[1].substring(0,1).toUpperCase() + words[2].substring(0,1).toUpperCase();
        } else if (words.length > 1) {
            return words[0].substring(0, 2).toUpperCase() + words[1].substring(0,1).toUpperCase();
        } else {
            return words[0].substring(0,3).toUpperCase();
        }
    }
}
