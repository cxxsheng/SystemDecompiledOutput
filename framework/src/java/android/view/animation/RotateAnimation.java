package android.view.animation;

/* loaded from: classes4.dex */
public class RotateAnimation extends android.view.animation.Animation {
    private float mFromDegrees;
    private float mPivotX;
    private int mPivotXType;
    private float mPivotXValue;
    private float mPivotY;
    private int mPivotYType;
    private float mPivotYValue;
    private float mToDegrees;

    public RotateAnimation(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.RotateAnimation);
        this.mFromDegrees = obtainStyledAttributes.getFloat(0, 0.0f);
        this.mToDegrees = obtainStyledAttributes.getFloat(1, 0.0f);
        android.view.animation.Animation.Description parseValue = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(2), context);
        this.mPivotXType = parseValue.type;
        this.mPivotXValue = parseValue.value;
        android.view.animation.Animation.Description parseValue2 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(3), context);
        this.mPivotYType = parseValue2.type;
        this.mPivotYValue = parseValue2.value;
        obtainStyledAttributes.recycle();
        initializePivotPoint();
    }

    public RotateAnimation(float f, float f2) {
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public RotateAnimation(float f, float f2, float f3, float f4) {
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = f3;
        this.mPivotYValue = f4;
        initializePivotPoint();
    }

    public RotateAnimation(float f, float f2, int i, float f3, int i2, float f4) {
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mFromDegrees = f;
        this.mToDegrees = f2;
        this.mPivotXValue = f3;
        this.mPivotXType = i;
        this.mPivotYValue = f4;
        this.mPivotYType = i2;
        initializePivotPoint();
    }

    private void initializePivotPoint() {
        if (this.mPivotXType == 0) {
            this.mPivotX = this.mPivotXValue;
        }
        if (this.mPivotYType == 0) {
            this.mPivotY = this.mPivotYValue;
        }
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        float f2 = this.mFromDegrees + ((this.mToDegrees - this.mFromDegrees) * f);
        float scaleFactor = getScaleFactor();
        if (this.mPivotX == 0.0f && this.mPivotY == 0.0f) {
            transformation.getMatrix().setRotate(f2);
        } else {
            transformation.getMatrix().setRotate(f2, this.mPivotX * scaleFactor, this.mPivotY * scaleFactor);
        }
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mPivotX = resolveSize(this.mPivotXType, this.mPivotXValue, i, i3);
        this.mPivotY = resolveSize(this.mPivotYType, this.mPivotYValue, i2, i4);
    }
}
