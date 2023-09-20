package org.example;

public class Tuple {
    private Long count;
    private Long value;

    public Tuple(Long count, Long average) {
        this.count = count;
        this.value = average;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
