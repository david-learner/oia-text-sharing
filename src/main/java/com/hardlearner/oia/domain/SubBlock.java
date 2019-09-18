package com.hardlearner.oia.domain;

import javax.persistence.*;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SUB_BLOCK_ID"))
})
public class SubBlock extends Block {
    @ManyToOne
    @JoinColumn(name = "MAIN_BLOCK_ID")
    private MainBlock mainBlock;
    @Embedded
    Pages pages;
    boolean canShare;
    @Enumerated(EnumType.STRING)
    ContentCategory category;
    String content;

    public SubBlock(Long id, Integer sequenceId, Pointers pointers, MainBlock mainBlock, Pages pages, boolean canShare, ContentCategory category, String content) {
        super(id, sequenceId, pointers);
        this.mainBlock = mainBlock;
        this.pages = pages;
        this.canShare = canShare;
        this.category = category;
        this.content = content;
    }

    public SubBlock(Pages pages, boolean canShare, ContentCategory category, String content) {
        this(null, null, null, null, pages, canShare, category, content);
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
