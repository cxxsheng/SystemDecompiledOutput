package com.android.server.permission.jarjar.kotlin.ranges;

/* compiled from: ProgressionIterators.kt */
/* loaded from: classes2.dex */
public final class IntProgressionIterator extends com.android.server.permission.jarjar.kotlin.collections.IntIterator {
    private final int finalElement;
    private boolean hasNext;
    private int next;
    private final int step;

    public IntProgressionIterator(int first, int last, int step) {
        this.step = step;
        this.finalElement = last;
        boolean z = false;
        if (this.step <= 0 ? first >= last : first <= last) {
            z = true;
        }
        this.hasNext = z;
        this.next = this.hasNext ? first : this.finalElement;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override // com.android.server.permission.jarjar.kotlin.collections.IntIterator
    public int nextInt() {
        int value = this.next;
        if (value == this.finalElement) {
            if (!this.hasNext) {
                throw new java.util.NoSuchElementException();
            }
            this.hasNext = false;
        } else {
            this.next += this.step;
        }
        return value;
    }
}
