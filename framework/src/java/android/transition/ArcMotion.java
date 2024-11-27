package android.transition;

/* loaded from: classes3.dex */
public class ArcMotion extends android.transition.PathMotion {
    private static final float DEFAULT_MAX_ANGLE_DEGREES = 70.0f;
    private static final float DEFAULT_MAX_TANGENT = (float) java.lang.Math.tan(java.lang.Math.toRadians(35.0d));
    private static final float DEFAULT_MIN_ANGLE_DEGREES = 0.0f;
    private float mMaximumAngle;
    private float mMaximumTangent;
    private float mMinimumHorizontalAngle;
    private float mMinimumHorizontalTangent;
    private float mMinimumVerticalAngle;
    private float mMinimumVerticalTangent;

    public ArcMotion() {
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
    }

    public ArcMotion(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMinimumHorizontalAngle = 0.0f;
        this.mMinimumVerticalAngle = 0.0f;
        this.mMaximumAngle = DEFAULT_MAX_ANGLE_DEGREES;
        this.mMinimumHorizontalTangent = 0.0f;
        this.mMinimumVerticalTangent = 0.0f;
        this.mMaximumTangent = DEFAULT_MAX_TANGENT;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ArcMotion);
        setMinimumVerticalAngle(obtainStyledAttributes.getFloat(1, 0.0f));
        setMinimumHorizontalAngle(obtainStyledAttributes.getFloat(0, 0.0f));
        setMaximumAngle(obtainStyledAttributes.getFloat(2, DEFAULT_MAX_ANGLE_DEGREES));
        obtainStyledAttributes.recycle();
    }

    public void setMinimumHorizontalAngle(float f) {
        this.mMinimumHorizontalAngle = f;
        this.mMinimumHorizontalTangent = toTangent(f);
    }

    public float getMinimumHorizontalAngle() {
        return this.mMinimumHorizontalAngle;
    }

    public void setMinimumVerticalAngle(float f) {
        this.mMinimumVerticalAngle = f;
        this.mMinimumVerticalTangent = toTangent(f);
    }

    public float getMinimumVerticalAngle() {
        return this.mMinimumVerticalAngle;
    }

    public void setMaximumAngle(float f) {
        this.mMaximumAngle = f;
        this.mMaximumTangent = toTangent(f);
    }

    public float getMaximumAngle() {
        return this.mMaximumAngle;
    }

    private static float toTangent(float f) {
        if (f < 0.0f || f > 90.0f) {
            throw new java.lang.IllegalArgumentException("Arc must be between 0 and 90 degrees");
        }
        return (float) java.lang.Math.tan(java.lang.Math.toRadians(f / 2.0f));
    }

    @Override // android.transition.PathMotion
    public android.graphics.Path getPath(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        float f7;
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(f, f2);
        float f8 = f3 - f;
        float f9 = f4 - f2;
        float f10 = (f8 * f8) + (f9 * f9);
        float f11 = (f + f3) / 2.0f;
        float f12 = (f2 + f4) / 2.0f;
        float f13 = 0.25f * f10;
        boolean z = f2 > f4;
        if (f9 == 0.0f) {
            f6 = (java.lang.Math.abs(f8) * 0.5f * this.mMinimumHorizontalTangent) + f12;
            f5 = f11;
            f7 = 0.0f;
        } else if (f8 == 0.0f) {
            f5 = (java.lang.Math.abs(f9) * 0.5f * this.mMinimumVerticalTangent) + f11;
            f6 = f12;
            f7 = 0.0f;
        } else if (java.lang.Math.abs(f8) < java.lang.Math.abs(f9)) {
            float abs = java.lang.Math.abs(f10 / (f9 * 2.0f));
            if (z) {
                f6 = f4 + abs;
                f5 = f3;
            } else {
                f6 = abs + f2;
                f5 = f;
            }
            f7 = this.mMinimumVerticalTangent * f13 * this.mMinimumVerticalTangent;
        } else {
            float f14 = f10 / (f8 * 2.0f);
            if (z) {
                f5 = f + f14;
                f6 = f2;
            } else {
                f5 = f3 - f14;
                f6 = f4;
            }
            f7 = this.mMinimumHorizontalTangent * f13 * this.mMinimumHorizontalTangent;
        }
        float f15 = f11 - f5;
        float f16 = f12 - f6;
        float f17 = (f15 * f15) + (f16 * f16);
        float f18 = f13 * this.mMaximumTangent * this.mMaximumTangent;
        if (f17 == 0.0f || f17 >= f7) {
            if (f17 <= f18) {
                f7 = 0.0f;
            } else {
                f7 = f18;
            }
        }
        if (f7 != 0.0f) {
            float sqrt = (float) java.lang.Math.sqrt(f7 / f17);
            f5 = ((f5 - f11) * sqrt) + f11;
            f6 = f12 + (sqrt * (f6 - f12));
        }
        path.cubicTo((f + f5) / 2.0f, (f2 + f6) / 2.0f, (f5 + f3) / 2.0f, (f6 + f4) / 2.0f, f3, f4);
        return path;
    }
}
