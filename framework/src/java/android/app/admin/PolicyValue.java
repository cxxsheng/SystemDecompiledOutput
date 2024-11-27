package android.app.admin;

/* loaded from: classes.dex */
public abstract class PolicyValue<V> implements android.os.Parcelable {
    private V mValue;

    public PolicyValue(V v) {
        this.mValue = (V) java.util.Objects.requireNonNull(v);
    }

    PolicyValue() {
    }

    public V getValue() {
        return this.mValue;
    }

    void setValue(V v) {
        this.mValue = v;
    }
}
