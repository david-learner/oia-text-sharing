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
    private Pages pages = Pages.getDefaultPage();
    private boolean share;
    @Enumerated(EnumType.STRING)
    private ContentCategory category;
    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public SubBlock(Long id, Integer sequenceId, Pointers pointers, Pages pages, boolean share, ContentCategory category, String content) {
        super(id, sequenceId, pointers);
        this.pages = pages;
        this.share = share;
        this.category = category;
        this.content = content;
    }

    public SubBlock(Pages pages, boolean share, ContentCategory category, String content) {
        this(null, null, null, pages, share, category, content);
    }

    public static List<SubBlock> getDefaultSubBlocks() {
        return Arrays.asList(
                new SubBlock(null, 2, new Pointers(null, 3), Pages.getDefaultPage(), false, ContentCategory.OBSERVATION, "관찰"),
                new SubBlock(null, 3, new Pointers(2, 4), Pages.getDefaultPage(), false, ContentCategory.INTERPRETATION, "해석"),
                new SubBlock(null, 4, new Pointers(3, null), Pages.getDefaultPage(), false, ContentCategory.APPLICATION, "적용")
        );
    }

    public String getContentCategory() {
        return category.getAlias();
    }

    public boolean canShare() {
        return share;
    }

    @Override
    public String toString() {
        return "SubBlock{" +
                "pages=" + pages +
                ", canShare=" + share +
                ", category=" + category +
                ", content='" + content + '\'' +
                '}';
    }
}
