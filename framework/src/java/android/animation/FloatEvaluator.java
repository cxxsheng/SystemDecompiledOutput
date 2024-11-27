package android.animation;

/* loaded from: classes.dex */
public class FloatEvaluator implements android.animation.TypeEvaluator<java.lang.Number> {
    @Override // android.animation.TypeEvaluator
    public java.lang.Float evaluate(float f, java.lang.Number number, java.lang.Number number2) {
        float floatValue = number.floatValue();
        return java.lang.Float.valueOf(floatValue + (f * (number2.floatValue() - floatValue)));
    }
}
