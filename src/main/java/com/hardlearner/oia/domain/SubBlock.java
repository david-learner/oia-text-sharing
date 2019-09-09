package com.hardlearner.oia.domain;

public class SubBlock {
    Pages pages;
    boolean canShare;
    ContentCategory category;
    String content;

    public SubBlock(Pages pages, boolean canShare, ContentCategory category, String content) {
        this.pages = pages;
        this.canShare = canShare;
        this.category = category;
        this.content = content;
    }

    public String getContentCategory() {
        return category.getAlias();
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        sb.append("pages : " + pages.toString() + '\n');
        sb.append("share : " + canShare  + '\n');
        sb.append("category : " + category.getAlias() + '\n');
        sb.append("content : " + content + '\n');
        return sb.toString();
    }
}
