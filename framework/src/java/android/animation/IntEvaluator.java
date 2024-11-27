package android.animation;

/* loaded from: classes.dex */
public class IntEvaluator implements android.animation.TypeEvaluator<java.lang.Integer> {
    @Override // android.animation.TypeEvaluator
    public java.lang.Integer evaluate(float f, java.lang.Integer num, java.lang.Integer num2) {
        return java.lang.Integer.valueOf((int) (num.intValue() + (f * (num2.intValue() - r3))));
    }
}
