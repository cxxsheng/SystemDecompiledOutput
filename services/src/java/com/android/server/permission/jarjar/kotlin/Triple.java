package com.android.server.permission.jarjar.kotlin;

/* compiled from: Tuples.kt */
/* loaded from: classes2.dex */
public final class Triple<A, B, C> implements java.io.Serializable {
    private final A first;
    private final B second;
    private final C third;

    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof com.android.server.permission.jarjar.kotlin.Triple)) {
            return false;
        }
        com.android.server.permission.jarjar.kotlin.Triple triple = (com.android.server.permission.jarjar.kotlin.Triple) obj;
        return com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.first, triple.first) && com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.second, triple.second) && com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.third, triple.third);
    }

    public int hashCode() {
        return ((((this.first == null ? 0 : this.first.hashCode()) * 31) + (this.second == null ? 0 : this.second.hashCode())) * 31) + (this.third != null ? this.third.hashCode() : 0);
    }

    public Triple(A a, B b, C c) {
        this.first = a;
        this.second = b;
        this.third = c;
    }

    public final A getFirst() {
        return this.first;
    }

    public final B getSecond() {
        return this.second;
    }

    public final C getThird() {
        return this.third;
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return '(' + this.first + ", " + this.second + ", " + this.third + ')';
    }
}
