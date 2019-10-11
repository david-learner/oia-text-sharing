package com.hardlearner.oia.configuration;

import com.hardlearner.oia.domain.PageInfo;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandlebarsCustomHelper {
    public static int offset(int page) {
        return page-1;
    }

    public static boolean isFirst(PageInfo pageInfo) {
        System.out.println("Pages : " + pageInfo.isFirst());
        return pageInfo.isFirst();
    }

//    public static boolean isLast(PageInfo pageInfo) {
//        return pageInfo.getCurrentBlockLastPage();
//    }
}
