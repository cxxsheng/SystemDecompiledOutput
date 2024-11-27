package com.android.server.permission.jarjar.kotlin;

/* compiled from: Tuples.kt */
/* loaded from: classes2.dex */
public final class Pair<A, B> implements java.io.Serializable {
    private final A first;
    private final B second;

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.permission.jarjar.kotlin.Pair)) {
            return false;
        }
        com.android.server.permission.jarjar.kotlin.Pair pair = (com.android.server.permission.jarjar.kotlin.Pair) obj;
        return com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.first, pair.first) && com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.second, pair.second);
    }

    public int hashCode() {
        return ((this.first == null ? 0 : this.first.hashCode()) * 31) + (this.second != null ? this.second.hashCode() : 0);
    }

    public Pair(A a, B b) {
        this.first = a;
        this.second = b;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return '(' + this.first + ", " + this.second + ')';
    }
}
