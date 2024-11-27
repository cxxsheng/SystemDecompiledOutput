package android.animation;

/* loaded from: classes.dex */
public abstract class TypeConverter<T, V> {
    private java.lang.Class<T> mFromClass;
    private java.lang.Class<V> mToClass;

    public abstract V convert(T t);

    public TypeConverter(java.lang.Class<T> cls, java.lang.Class<V> cls2) {
        this.mFromClass = cls;
        this.mToClass = cls2;
    }

    java.lang.Class<V> getTargetType() {
        return this.mToClass;
    }

    java.lang.Class<T> getSourceType() {
        return this.mFromClass;
    }
}
