package com.hardlearner.oia.domain;

public class Pages {
    int start;
    int end;

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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return start + " ~ " + end;
    }
}
