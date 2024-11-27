package com.android.server.permission.access.immutable;

/* JADX WARN: Incorrect field signature: TM; */
/* compiled from: MutableReference.kt */
/* loaded from: classes2.dex */
public final class MutableReference<I extends com.android.server.permission.access.immutable.Immutable<M>, M extends I> {

    @org.jetbrains.annotations.NotNull
    private I immutable;

    @org.jetbrains.annotations.Nullable
    private com.android.server.permission.access.immutable.Immutable mutable;

    /* JADX WARN: Incorrect types in method signature: (TI;TM;)V */
    /* JADX WARN: Multi-variable type inference failed */
    private MutableReference(com.android.server.permission.access.immutable.Immutable immutable, com.android.server.permission.access.immutable.Immutable mutable) {
        this.immutable = immutable;
        this.mutable = mutable;
    }

    /* JADX WARN: Incorrect types in method signature: (TM;)V */
    public MutableReference(@org.jetbrains.annotations.NotNull com.android.server.permission.access.immutable.Immutable mutable) {
        this(mutable, mutable);
    }

    @org.jetbrains.annotations.NotNull
    public final I get() {
        return this.immutable;
    }

    /* JADX WARN: Incorrect return type in method signature: ()TM; */
    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.Immutable mutate() {
        com.android.server.permission.access.immutable.Immutable it = this.mutable;
        if (it != null) {
            return it;
        }
        java.lang.Object mutable = this.immutable.toMutable();
        I i = (I) mutable;
        this.immutable = i;
        this.mutable = i;
        return (com.android.server.permission.access.immutable.Immutable) mutable;
    }

    @org.jetbrains.annotations.NotNull
    public final com.android.server.permission.access.immutable.MutableReference<I, M> toImmutable() {
        return new com.android.server.permission.access.immutable.MutableReference<>(this.immutable, null);
    }

    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object other) {
        if (this == other) {
            return true;
        }
        if (!com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNull(other, "null cannot be cast to non-null type com.android.server.permission.access.immutable.MutableReference<*, *>");
        return com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.areEqual(this.immutable, ((com.android.server.permission.access.immutable.MutableReference) other).immutable);
    }

    public int hashCode() {
        return this.immutable.hashCode();
    }
}
