package android.util;

/* loaded from: classes3.dex */
public abstract class FloatProperty<T> extends android.util.Property<T, java.lang.Float> {
    public abstract void setValue(T t, float f);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.util.Property
    public /* bridge */ /* synthetic */ void set(java.lang.Object obj, java.lang.Float f) {
        set2((android.util.FloatProperty<T>) obj, f);
    }

    public FloatProperty(java.lang.String str) {
        super(java.lang.Float.class, str);
    }

    /* renamed from: set, reason: avoid collision after fix types in other method */
    public final void set2(T t, java.lang.Float f) {
        setValue(t, f.floatValue());
    }
}
