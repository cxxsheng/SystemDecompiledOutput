package android.animation;

/* loaded from: classes.dex */
public interface Keyframes extends java.lang.Cloneable {

    public interface FloatKeyframes extends android.animation.Keyframes {
        float getFloatValue(float f);
    }

    public interface IntKeyframes extends android.animation.Keyframes {
        int getIntValue(float f);
    }

    android.animation.Keyframes clone();

    java.util.List<android.animation.Keyframe> getKeyframes();

    java.lang.Class getType();

    java.lang.Object getValue(float f);

    void setEvaluator(android.animation.TypeEvaluator typeEvaluator);
}
