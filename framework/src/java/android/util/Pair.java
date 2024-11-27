package android.util;

/* loaded from: classes3.dex */
public class Pair<F, S> {
    public final F first;
    public final S second;

    public Pair(F f, S s) {
        this.first = f;
        this.second = s;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.util.Pair)) {
            return false;
        }
        android.util.Pair pair = (android.util.Pair) obj;
        return java.util.Objects.equals(pair.first, this.first) && java.util.Objects.equals(pair.second, this.second);
    }

    public int hashCode() {
        return (this.first == null ? 0 : this.first.hashCode()) ^ (this.second != null ? this.second.hashCode() : 0);
    }

    public java.lang.String toString() {
        return "Pair{" + java.lang.String.valueOf(this.first) + " " + java.lang.String.valueOf(this.second) + "}";
    }

    public static <A, B> android.util.Pair<A, B> create(A a, B b) {
        return new android.util.Pair<>(a, b);
    }
}
