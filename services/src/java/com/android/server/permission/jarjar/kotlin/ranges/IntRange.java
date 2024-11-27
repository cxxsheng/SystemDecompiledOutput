package com.android.server.permission.jarjar.kotlin.ranges;

/* compiled from: PrimitiveRanges.kt */
/* loaded from: classes2.dex */
public final class IntRange extends com.android.server.permission.jarjar.kotlin.ranges.IntProgression {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.jarjar.kotlin.ranges.IntRange.Companion Companion = new com.android.server.permission.jarjar.kotlin.ranges.IntRange.Companion(null);

    @org.jetbrains.annotations.NotNull
    private static final com.android.server.permission.jarjar.kotlin.ranges.IntRange EMPTY = new com.android.server.permission.jarjar.kotlin.ranges.IntRange(1, 0);

    public IntRange(int start, int endInclusive) {
        super(start, endInclusive, 1);
    }

    public boolean contains(int value) {
        return getFirst() <= value && value <= getLast();
    }

    public boolean isEmpty() {
        return getFirst() > getLast();
    }

    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object other) {
        return (other instanceof com.android.server.permission.jarjar.kotlin.ranges.IntRange) && ((isEmpty() && ((com.android.server.permission.jarjar.kotlin.ranges.IntRange) other).isEmpty()) || (getFirst() == ((com.android.server.permission.jarjar.kotlin.ranges.IntRange) other).getFirst() && getLast() == ((com.android.server.permission.jarjar.kotlin.ranges.IntRange) other).getLast()));
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (getFirst() * 31) + getLast();
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return getFirst() + ".." + getLast();
    }

    /* compiled from: PrimitiveRanges.kt */
    public static final class Companion {
        public /* synthetic */ Companion(com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
