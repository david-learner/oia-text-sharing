package com.hardlearner.oia.configuration;

import com.hardlearner.oia.domain.PageInfo;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandlebarsCustomHelper {
    public static boolean isFirst(PageInfo pageInfo) {
        return pageInfo.isFirst();
    }

    public static boolean isLast(PageInfo pageInfo) {
        return pageInfo.isLast();
    }
}
