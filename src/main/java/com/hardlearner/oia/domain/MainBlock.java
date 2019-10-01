package com.hardlearner.oia.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "MAIN_BLOCK_ID"))
})
@NoArgsConstructor
@Getter
public class MainBlock extends Block {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MAIN_BLOCK_ID")
    private List<SubBlock> subBlocks = new LinkedList<>();

    @Builder
    public MainBlock(Long id, Integer sequenceId, Pointers pointers, List<SubBlock> subBlocks) {
        super(id, sequenceId, pointers);
        this.subBlocks = subBlocks;
    }

    public MainBlock(Long id, List<SubBlock> subBlocks) {
        this(id, null, null, subBlocks);
    }

    public static MainBlock getDefaultMainBlock(List<SubBlock> subBlocks) {
        return new MainBlock(null, 1, new Pointers(null, null), subBlocks);
    }

    @JsonIgnore
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + getId() + "\n");
        sb.append("seq-id: " + getSequenceId() + "\n");
        for (SubBlock subBlock : subBlocks) {
            sb.append(subBlock.toString()).append("\n");
        }
        return sb.toString();
    }
}
