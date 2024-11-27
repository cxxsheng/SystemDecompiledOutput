package android.view.animation;

/* loaded from: classes4.dex */
public class ScaleAnimation extends android.view.animation.Animation {
    private float mFromX;
    private int mFromXData;
    private int mFromXType;
    private float mFromY;
    private int mFromYData;
    private int mFromYType;
    private float mPivotX;
    private int mPivotXType;
    private float mPivotXValue;
    private float mPivotY;
    private int mPivotYType;
    private float mPivotYValue;
    private final android.content.res.Resources mResources;
    private float mToX;
    private int mToXData;
    private int mToXType;
    private float mToY;
    private int mToYData;
    private int mToYType;

    public ScaleAnimation(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = context.getResources();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ScaleAnimation);
        android.util.TypedValue peekValue = obtainStyledAttributes.peekValue(2);
        this.mFromX = 0.0f;
        if (peekValue != null) {
            if (peekValue.type == 4) {
                this.mFromX = peekValue.getFloat();
            } else {
                this.mFromXType = peekValue.type;
                this.mFromXData = peekValue.data;
            }
        }
        android.util.TypedValue peekValue2 = obtainStyledAttributes.peekValue(3);
        this.mToX = 0.0f;
        if (peekValue2 != null) {
            if (peekValue2.type == 4) {
                this.mToX = peekValue2.getFloat();
            } else {
                this.mToXType = peekValue2.type;
                this.mToXData = peekValue2.data;
            }
        }
        android.util.TypedValue peekValue3 = obtainStyledAttributes.peekValue(4);
        this.mFromY = 0.0f;
        if (peekValue3 != null) {
            if (peekValue3.type == 4) {
                this.mFromY = peekValue3.getFloat();
            } else {
                this.mFromYType = peekValue3.type;
                this.mFromYData = peekValue3.data;
            }
        }
        android.util.TypedValue peekValue4 = obtainStyledAttributes.peekValue(5);
        this.mToY = 0.0f;
        if (peekValue4 != null) {
            if (peekValue4.type == 4) {
                this.mToY = peekValue4.getFloat();
            } else {
                this.mToYType = peekValue4.type;
                this.mToYData = peekValue4.data;
            }
        }
        android.view.animation.Animation.Description parseValue = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mPivotXType = parseValue.type;
        this.mPivotXValue = parseValue.value;
        android.view.animation.Animation.Description parseValue2 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mPivotYType = parseValue2.type;
        this.mPivotYValue = parseValue2.value;
        obtainStyledAttributes.recycle();
        initializePivotPoint();
    }

    public ScaleAnimation(float f, float f2, float f3, float f4) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotX = 0.0f;
        this.mPivotY = 0.0f;
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, float f5, float f6) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = f5;
        this.mPivotYValue = f6;
        initializePivotPoint();
    }

    public ScaleAnimation(float f, float f2, float f3, float f4, int i, float f5, int i2, float f6) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXData = 0;
        this.mToXData = 0;
        this.mFromYData = 0;
        this.mToYData = 0;
        this.mPivotXType = 0;
        this.mPivotYType = 0;
        this.mPivotXValue = 0.0f;
        this.mPivotYValue = 0.0f;
        this.mResources = null;
        this.mFromX = f;
        this.mToX = f2;
        this.mFromY = f3;
        this.mToY = f4;
        this.mPivotXValue = f5;
        this.mPivotXType = i;
        this.mPivotYValue = f6;
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
        float scaleFactor = getScaleFactor();
        float f2 = (this.mFromX == 1.0f && this.mToX == 1.0f) ? 1.0f : this.mFromX + ((this.mToX - this.mFromX) * f);
        float f3 = (this.mFromY == 1.0f && this.mToY == 1.0f) ? 1.0f : this.mFromY + ((this.mToY - this.mFromY) * f);
        if (this.mPivotX == 0.0f && this.mPivotY == 0.0f) {
            transformation.getMatrix().setScale(f2, f3);
        } else {
            transformation.getMatrix().setScale(f2, f3, this.mPivotX * scaleFactor, scaleFactor * this.mPivotY);
        }
    }

    float resolveScale(float f, int i, int i2, int i3, int i4) {
        float complexToDimension;
        if (i == 6) {
            complexToDimension = android.util.TypedValue.complexToFraction(i2, i3, i4);
        } else if (i == 5) {
            complexToDimension = android.util.TypedValue.complexToDimension(i2, this.mResources.getDisplayMetrics());
        } else {
            return f;
        }
        if (i3 == 0) {
            return 1.0f;
        }
        return complexToDimension / i3;
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromX = resolveScale(this.mFromX, this.mFromXType, this.mFromXData, i, i3);
        this.mToX = resolveScale(this.mToX, this.mToXType, this.mToXData, i, i3);
        this.mFromY = resolveScale(this.mFromY, this.mFromYType, this.mFromYData, i2, i4);
        this.mToY = resolveScale(this.mToY, this.mToYType, this.mToYData, i2, i4);
        this.mPivotX = resolveSize(this.mPivotXType, this.mPivotXValue, i, i3);
        this.mPivotY = resolveSize(this.mPivotYType, this.mPivotYValue, i2, i4);
    }
}
