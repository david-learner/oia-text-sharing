package com.hardlearner.oia.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "MAIN_BLOCK_ID"))
})
public class MainBlock extends Block {
    @ManyToOne
    @JoinColumn(name = "ARTICLE_ID")
    Article article;
    @OneToMany(mappedBy = "mainBlock")
    private List<SubBlock> subBlocks = new LinkedList<>();

    public MainBlock(Long id, List<SubBlock> subBlocks, Pointers pointers) {
        super(id, pointers);
        this.subBlocks = subBlocks;
    }

    public MainBlock(Long id, List<SubBlock> subBlocks) {
        this(id, subBlocks, null);
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        for (SubBlock subBlock : subBlocks) {
            sb.append(subBlock.print());
        }
        return sb.toString();
    }

    public MainBlock getShareAllowed() {
        // 공유용 블록들은 읽기 전용, 그렇기 때문에 포인터는 신경쓰지 않아도 괜찮다
        // 다만, 공동협업을 위해 공유한다면 포인터의 순서도 신경써야 한다.
        // 지금 구조로 협업을 하려면 협업용 글을 따로 만드는 수 밖에 없다. 이를 위해 시스템에서 글 자체를 사본으로 만들어줄 수 있어야 한다
        List<SubBlock> shareAllowedSubBlocks = this.subBlocks.stream().filter(subBlock -> subBlock.canShare).collect(Collectors.toList());
        return new MainBlock(super.getId(), shareAllowedSubBlocks);
    }

    public boolean isSame(MainBlock otherBlock) {
        return this.equals(otherBlock);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainBlock mainBlock = (MainBlock) o;
        return Objects.equals(super.getId(), mainBlock.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }
}
