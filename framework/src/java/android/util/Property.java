package android.util;

/* loaded from: classes3.dex */
public abstract class Property<T, V> {
    private final java.lang.String mName;
    private final java.lang.Class<V> mType;

    public abstract V get(T t);

    public static <T, V> android.util.Property<T, V> of(java.lang.Class<T> cls, java.lang.Class<V> cls2, java.lang.String str) {
        return new android.util.ReflectiveProperty(cls, cls2, str);
    }

    public Property(java.lang.Class<V> cls, java.lang.String str) {
        this.mName = str;
        this.mType = cls;
    }

    public boolean isReadOnly() {
        return false;
    }

    public void set(T t, V v) {
        throw new java.lang.UnsupportedOperationException("Property " + getName() + " is read-only");
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public java.lang.Class<V> getType() {
        return this.mType;
    }
}
