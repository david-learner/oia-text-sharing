package com.hardlearner.oia.configuration;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandlebarsCustomHelper {
    public static int offset(int page) {
        return page-1;
    }
}
