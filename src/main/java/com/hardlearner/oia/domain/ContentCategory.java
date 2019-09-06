package com.hardlearner.oia.domain;

public enum ContentCategory {
    OBSERVATION("본"),
    INTERPRETATION("깨"),
    APPLICATION("적");

    String alias;

    ContentCategory(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
