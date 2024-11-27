package com.android.server.permission.jarjar.kotlin.ranges;

/* compiled from: Progressions.kt */
/* loaded from: classes2.dex */
public class IntProgression implements java.lang.Iterable<java.lang.Integer> {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.jarjar.kotlin.ranges.IntProgression.Companion Companion = new com.android.server.permission.jarjar.kotlin.ranges.IntProgression.Companion(null);
    private final int first;
    private final int last;
    private final int step;

    public IntProgression(int start, int endInclusive, int step) {
        if (step == 0) {
            throw new java.lang.IllegalArgumentException("Step must be non-zero.");
        }
        if (step == Integer.MIN_VALUE) {
            throw new java.lang.IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
        this.first = start;
        this.last = com.android.server.permission.jarjar.kotlin.internal.ProgressionUtilKt.getProgressionLastElement(start, endInclusive, step);
        this.step = step;
    }

    public final int getFirst() {
        return this.first;
    }

    public final int getLast() {
        return this.last;
    }

    @Override // java.lang.Iterable
    @org.jetbrains.annotations.NotNull
    /* renamed from: iterator, reason: merged with bridge method [inline-methods] */
    public java.util.Iterator<java.lang.Integer> iterator2() {
        return new com.android.server.permission.jarjar.kotlin.ranges.IntProgressionIterator(this.first, this.last, this.step);
    }

    /* compiled from: Progressions.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
