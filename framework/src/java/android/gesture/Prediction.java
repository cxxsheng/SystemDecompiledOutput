package android.gesture;

/* loaded from: classes.dex */
public class Prediction {
    public final java.lang.String name;
    public double score;

    Prediction(java.lang.String str, double d) {
        this.name = str;
        this.score = d;
    }

    public java.lang.String toString() {
        return this.name;
    }
}
