package com.hardlearner.oia.domain;

public class SubBlock {
    int page;
    boolean canShare;
    ContentCategory category;
    String content;

    public SubBlock(int page, boolean canShare, ContentCategory category, String content) {
        this.page = page;
        this.canShare = canShare;
        this.category = category;
        this.content = content;
    }

    public String getContentCategory() {
        return category.getAlias();
    }
}
