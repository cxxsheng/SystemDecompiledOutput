package android.view.animation;

/* loaded from: classes4.dex */
public class TranslateAnimation extends android.view.animation.Animation {
    protected float mFromXDelta;
    private int mFromXType;
    protected float mFromXValue;
    protected float mFromYDelta;
    private int mFromYType;
    protected float mFromYValue;
    private int mParentWidth;
    protected float mToXDelta;
    private int mToXType;
    protected float mToXValue;
    protected float mToYDelta;
    private int mToYType;
    protected float mToYValue;
    private int mWidth;

    public TranslateAnimation(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXValue = 0.0f;
        this.mToXValue = 0.0f;
        this.mFromYValue = 0.0f;
        this.mToYValue = 0.0f;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TranslateAnimation);
        android.view.animation.Animation.Description parseValue = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mFromXType = parseValue.type;
        this.mFromXValue = parseValue.value;
        android.view.animation.Animation.Description parseValue2 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mToXType = parseValue2.type;
        this.mToXValue = parseValue2.value;
        android.view.animation.Animation.Description parseValue3 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(2), context);
        this.mFromYType = parseValue3.type;
        this.mFromYValue = parseValue3.value;
        android.view.animation.Animation.Description parseValue4 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(3), context);
        this.mToYType = parseValue4.type;
        this.mToYValue = parseValue4.value;
        obtainStyledAttributes.recycle();
    }

    public TranslateAnimation(float f, float f2, float f3, float f4) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXValue = 0.0f;
        this.mToXValue = 0.0f;
        this.mFromYValue = 0.0f;
        this.mToYValue = 0.0f;
        this.mFromXValue = f;
        this.mToXValue = f2;
        this.mFromYValue = f3;
        this.mToYValue = f4;
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
    }

    public TranslateAnimation(int i, float f, int i2, float f2, int i3, float f3, int i4, float f4) {
        this.mFromXType = 0;
        this.mToXType = 0;
        this.mFromYType = 0;
        this.mToYType = 0;
        this.mFromXValue = 0.0f;
        this.mToXValue = 0.0f;
        this.mFromYValue = 0.0f;
        this.mToYValue = 0.0f;
        this.mFromXValue = f;
        this.mToXValue = f2;
        this.mFromYValue = f3;
        this.mToYValue = f4;
        this.mFromXType = i;
        this.mToXType = i2;
        this.mFromYType = i3;
        this.mToYType = i4;
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        float f2 = this.mFromXDelta;
        float f3 = this.mFromYDelta;
        if (this.mFromXDelta != this.mToXDelta) {
            f2 = this.mFromXDelta + ((this.mToXDelta - this.mFromXDelta) * f);
        }
        if (this.mFromYDelta != this.mToYDelta) {
            f3 = this.mFromYDelta + ((this.mToYDelta - this.mFromYDelta) * f);
        }
        transformation.getMatrix().setTranslate(f2, f3);
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromXDelta = resolveSize(this.mFromXType, this.mFromXValue, i, i3);
        this.mToXDelta = resolveSize(this.mToXType, this.mToXValue, i, i3);
        this.mFromYDelta = resolveSize(this.mFromYType, this.mFromYValue, i2, i4);
        this.mToYDelta = resolveSize(this.mToYType, this.mToYValue, i2, i4);
        this.mWidth = i;
        this.mParentWidth = i3;
    }

    public boolean isXAxisTransition() {
        return this.mFromXDelta - this.mToXDelta != 0.0f && this.mFromYDelta - this.mToYDelta == 0.0f;
    }

    public boolean isFullWidthTranslate() {
        return this.mWidth == this.mParentWidth && (isSlideInLeft() || isSlideOutRight() || isSlideInRight() || isSlideOutLeft());
    }

    private boolean isSlideInLeft() {
        return ((this.mFromXDelta > ((float) (-this.mWidth)) ? 1 : (this.mFromXDelta == ((float) (-this.mWidth)) ? 0 : -1)) <= 0) && endsXEnclosedWithinParent();
    }

    private boolean isSlideOutRight() {
        return startsXEnclosedWithinParent() && ((this.mToXDelta > ((float) this.mParentWidth) ? 1 : (this.mToXDelta == ((float) this.mParentWidth) ? 0 : -1)) >= 0);
    }

    private boolean isSlideInRight() {
        return ((this.mFromXDelta > ((float) this.mParentWidth) ? 1 : (this.mFromXDelta == ((float) this.mParentWidth) ? 0 : -1)) >= 0) && endsXEnclosedWithinParent();
    }

    private boolean isSlideOutLeft() {
        return startsXEnclosedWithinParent() && ((this.mToXDelta > ((float) (-this.mWidth)) ? 1 : (this.mToXDelta == ((float) (-this.mWidth)) ? 0 : -1)) <= 0);
    }

    private boolean endsXEnclosedWithinParent() {
        return this.mWidth <= this.mParentWidth && this.mToXDelta + ((float) this.mWidth) <= ((float) this.mParentWidth) && this.mToXDelta >= 0.0f;
    }

    private boolean startsXEnclosedWithinParent() {
        return this.mWidth <= this.mParentWidth && this.mFromXDelta + ((float) this.mWidth) <= ((float) this.mParentWidth) && this.mFromXDelta >= 0.0f;
    }
}
