package com.android.server.integrity.parser;

/* loaded from: classes2.dex */
public class RuleIndexRange {
    private int mEndIndex;
    private int mStartIndex;

    public RuleIndexRange(int i, int i2) {
        this.mStartIndex = i;
        this.mEndIndex = i2;
    }

    public int getStartIndex() {
        return this.mStartIndex;
    }

    public int getEndIndex() {
        return this.mEndIndex;
    }

    public boolean equals(@android.annotation.Nullable java.lang.Object obj) {
        com.android.server.integrity.parser.RuleIndexRange ruleIndexRange = (com.android.server.integrity.parser.RuleIndexRange) obj;
        return this.mStartIndex == ruleIndexRange.getStartIndex() && this.mEndIndex == ruleIndexRange.getEndIndex();
    }

    public java.lang.String toString() {
        return java.lang.String.format("Range{%d, %d}", java.lang.Integer.valueOf(this.mStartIndex), java.lang.Integer.valueOf(this.mEndIndex));
    }
}
