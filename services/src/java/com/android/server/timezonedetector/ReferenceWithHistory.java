package com.android.server.timezonedetector;

/* loaded from: classes2.dex */
public final class ReferenceWithHistory<V> {
    private final int mMaxHistorySize;
    private int mSetCount;

    @android.annotation.Nullable
    private java.util.ArrayDeque<android.os.TimestampedValue<V>> mValues;

    public ReferenceWithHistory(int i) {
        if (i < 1) {
            throw new java.lang.IllegalArgumentException("maxHistorySize < 1: " + i);
        }
        this.mMaxHistorySize = i;
    }

    @android.annotation.Nullable
    public V get() {
        if (this.mValues == null || this.mValues.isEmpty()) {
            return null;
        }
        return (V) this.mValues.getFirst().getValue();
    }

    @android.annotation.Nullable
    public V set(@android.annotation.Nullable V v) {
        if (this.mValues == null) {
            this.mValues = new java.util.ArrayDeque<>(this.mMaxHistorySize);
        }
        if (this.mValues.size() >= this.mMaxHistorySize) {
            this.mValues.removeLast();
        }
        V v2 = get();
        this.mValues.addFirst(new android.os.TimestampedValue<>(android.os.SystemClock.elapsedRealtime(), v));
        this.mSetCount++;
        return v2;
    }

    public void dump(@android.annotation.NonNull android.util.IndentingPrintWriter indentingPrintWriter) {
        if (this.mValues == null) {
            indentingPrintWriter.println("{Empty}");
        } else {
            int size = this.mSetCount - this.mValues.size();
            java.util.Iterator<android.os.TimestampedValue<V>> descendingIterator = this.mValues.descendingIterator();
            while (descendingIterator.hasNext()) {
                android.os.TimestampedValue<V> next = descendingIterator.next();
                indentingPrintWriter.print(size);
                indentingPrintWriter.print("@");
                indentingPrintWriter.print(java.time.Duration.ofMillis(next.getReferenceTimeMillis()).toString());
                indentingPrintWriter.print(": ");
                indentingPrintWriter.println(next.getValue());
                size++;
            }
        }
        indentingPrintWriter.flush();
    }

    public int getHistoryCount() {
        if (this.mValues == null) {
            return 0;
        }
        return this.mValues.size();
    }

    public java.lang.String toString() {
        return java.lang.String.valueOf(get());
    }
}
