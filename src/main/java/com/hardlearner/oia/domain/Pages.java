package com.hardlearner.oia.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class Pages {
    private int start;
    private int end;

    public Pages() {
    }

    // page 정의는 1이상의 자연수
    public Pages(int start, int end) {
        if (start < 1 && end < 1) {
            throw new IllegalArgumentException("page is 1~n");
        }
        if ((end - start) < 0) {
            throw new IllegalArgumentException("end page is bigger than start");
        }
        this.start = start;
        this.end = end;
    }

    public static Pages getDefaultPage() {
        return new Pages(1, 1);
    }

    @Override
    public String toString() {
        return "Pages{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pages pages = (Pages) o;
        return start == pages.start &&
                end == pages.end;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
