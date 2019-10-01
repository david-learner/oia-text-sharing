package com.hardlearner.oia.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "SUB_BLOCK_ID"))
})
@NoArgsConstructor
@Getter
public class SubBlock extends Block {
    @Embedded
    Pages pages = Pages.getDefaultPage();
    boolean canShare;
    @Enumerated(EnumType.STRING)
    ContentCategory category;
    String content;

    @Builder
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
                new SubBlock(null, 0, new Pointers(null, 1), Pages.getDefaultPage(), false, ContentCategory.OBSERVATION, "관찰"),
                new SubBlock(null, 1, new Pointers(0, 2), Pages.getDefaultPage(), false, ContentCategory.INTERPRETATION, "해석"),
                new SubBlock(null, 2, new Pointers(1, null), Pages.getDefaultPage(), false, ContentCategory.APPLICATION, "적용")
        );
    }

    public String getContentCategory() {
        return category.getAlias();
    }

    @Override
    public String toString() {
        return "SubBlock{" +
//                "pages=" + pages.toString() +
                ", canShare=" + canShare +
//                ", category=" + category.getAlias() +
                ", content='" + content + '\'' +
                '}';
    }
}
