package com.hardlearner.oia.domain;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SUB_BLOCK_ID"))
})
public class SubBlock extends Block {
    @Embedded
    Pages pages;
    boolean canShare;
    @Enumerated(EnumType.STRING)
    ContentCategory category;
    String content;

    public SubBlock(Long id, Integer sequenceId, Pointers pointers, Pages pages, boolean canShare, ContentCategory category, String content) {
        super(id, sequenceId, pointers);
        this.pages = pages;
        this.canShare = canShare;
        this.category = category;
        this.content = content;
    }

    public SubBlock(Pages pages, boolean canShare, ContentCategory category, String content) {
        this(null, null, null, pages, canShare, category, content);
    }

    public static List<SubBlock> getDefaultSubBlocks() {
        return Arrays.asList(
            new SubBlock(null, 0, new Pointers(null, 1), null, false, ContentCategory.OBSERVATION, null),
            new SubBlock(null, 0, new Pointers(0, 2), null, false, ContentCategory.INTERPRETATION, null),
            new SubBlock(null, 0, new Pointers(1, null), null, false, ContentCategory.APPLICATION, null)
        );
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
