package com.android.server.permission.jarjar.kotlin.collections;

/* compiled from: Maps.kt */
/* loaded from: classes2.dex */
final class EmptyMap implements java.util.Map, java.io.Serializable {

    @org.jetbrains.annotations.NotNull
    public static final com.android.server.permission.jarjar.kotlin.collections.EmptyMap INSTANCE = new com.android.server.permission.jarjar.kotlin.collections.EmptyMap();
    private static final long serialVersionUID = 8246714829545688274L;

    @Override // java.util.Map
    public void clear() {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public /* bridge */ /* synthetic */ java.lang.Object put(java.lang.Object obj, java.lang.Object obj2) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void putAll(java.util.Map map) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public java.lang.Void remove(java.lang.Object obj) {
        throw new java.lang.UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    private EmptyMap() {
    }

    @Override // java.util.Map
    public final /* bridge */ boolean containsValue(java.lang.Object value) {
        if (value instanceof java.lang.Void) {
            return containsValue((java.lang.Void) value);
        }
        return false;
    }

    @Override // java.util.Map
    public final /* bridge */ java.util.Set<java.util.Map.Entry> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final /* bridge */ java.util.Set<java.lang.Object> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final /* bridge */ java.util.Collection values() {
        return getValues();
    }

    @Override // java.util.Map
    public boolean equals(@org.jetbrains.annotations.Nullable java.lang.Object other) {
        return (other instanceof java.util.Map) && ((java.util.Map) other).isEmpty();
    }

    @Override // java.util.Map
    public int hashCode() {
        return 0;
    }

    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return "{}";
    }

    public int getSize() {
        return 0;
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return true;
    }

    @Override // java.util.Map
    public boolean containsKey(@org.jetbrains.annotations.Nullable java.lang.Object key) {
        return false;
    }

    public boolean containsValue(@org.jetbrains.annotations.NotNull java.lang.Void value) {
        com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics.checkNotNullParameter(value, "value");
        return false;
    }

    @Override // java.util.Map
    @org.jetbrains.annotations.Nullable
    public java.lang.Void get(@org.jetbrains.annotations.Nullable java.lang.Object key) {
        return null;
    }

    @org.jetbrains.annotations.NotNull
    public java.util.Set<java.util.Map.Entry> getEntries() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptySet.INSTANCE;
    }

    @org.jetbrains.annotations.NotNull
    public java.util.Set<java.lang.Object> getKeys() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptySet.INSTANCE;
    }

    @org.jetbrains.annotations.NotNull
    public java.util.Collection getValues() {
        return com.android.server.permission.jarjar.kotlin.collections.EmptyList.INSTANCE;
    }

    private final java.lang.Object readResolve() {
        return INSTANCE;
    }
}
