package android.view.animation;

/* loaded from: classes4.dex */
public class ExtendAnimation extends android.view.animation.Animation {
    private int mFromBottomType;
    private float mFromBottomValue;
    protected android.graphics.Insets mFromInsets;
    private int mFromLeftType;
    private float mFromLeftValue;
    private int mFromRightType;
    private float mFromRightValue;
    private int mFromTopType;
    private float mFromTopValue;
    private int mToBottomType;
    private float mToBottomValue;
    protected android.graphics.Insets mToInsets;
    private int mToLeftType;
    private float mToLeftValue;
    private int mToRightType;
    private float mToRightValue;
    private int mToTopType;
    private float mToTopValue;

    public ExtendAnimation(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFromInsets = android.graphics.Insets.NONE;
        this.mToInsets = android.graphics.Insets.NONE;
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ExtendAnimation);
        android.view.animation.Animation.Description parseValue = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(0), context);
        this.mFromLeftType = parseValue.type;
        this.mFromLeftValue = parseValue.value;
        android.view.animation.Animation.Description parseValue2 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(1), context);
        this.mFromTopType = parseValue2.type;
        this.mFromTopValue = parseValue2.value;
        android.view.animation.Animation.Description parseValue3 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(2), context);
        this.mFromRightType = parseValue3.type;
        this.mFromRightValue = parseValue3.value;
        android.view.animation.Animation.Description parseValue4 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(3), context);
        this.mFromBottomType = parseValue4.type;
        this.mFromBottomValue = parseValue4.value;
        android.view.animation.Animation.Description parseValue5 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(4), context);
        this.mToLeftType = parseValue5.type;
        this.mToLeftValue = parseValue5.value;
        android.view.animation.Animation.Description parseValue6 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(5), context);
        this.mToTopType = parseValue6.type;
        this.mToTopValue = parseValue6.value;
        android.view.animation.Animation.Description parseValue7 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(6), context);
        this.mToRightType = parseValue7.type;
        this.mToRightValue = parseValue7.value;
        android.view.animation.Animation.Description parseValue8 = android.view.animation.Animation.Description.parseValue(obtainStyledAttributes.peekValue(7), context);
        this.mToBottomType = parseValue8.type;
        this.mToBottomValue = parseValue8.value;
        obtainStyledAttributes.recycle();
    }

    public ExtendAnimation(android.graphics.Insets insets, android.graphics.Insets insets2) {
        this.mFromInsets = android.graphics.Insets.NONE;
        this.mToInsets = android.graphics.Insets.NONE;
        this.mFromLeftType = 0;
        this.mFromTopType = 0;
        this.mFromRightType = 0;
        this.mFromBottomType = 0;
        this.mToLeftType = 0;
        this.mToTopType = 0;
        this.mToRightType = 0;
        this.mToBottomType = 0;
        if (insets == null || insets2 == null) {
            throw new java.lang.RuntimeException("Expected non-null animation outsets");
        }
        this.mFromLeftValue = -insets.left;
        this.mFromTopValue = -insets.top;
        this.mFromRightValue = -insets.right;
        this.mFromBottomValue = -insets.bottom;
        this.mToLeftValue = -insets2.left;
        this.mToTopValue = -insets2.top;
        this.mToRightValue = -insets2.right;
        this.mToBottomValue = -insets2.bottom;
    }

    public ExtendAnimation(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        this(android.graphics.Insets.of(-i, -i2, -i3, -i4), android.graphics.Insets.of(-i5, -i6, -i7, -i8));
    }

    @Override // android.view.animation.Animation
    protected void applyTransformation(float f, android.view.animation.Transformation transformation) {
        transformation.setInsets(this.mFromInsets.left + ((int) ((this.mToInsets.left - this.mFromInsets.left) * f)), this.mFromInsets.top + ((int) ((this.mToInsets.top - this.mFromInsets.top) * f)), this.mFromInsets.right + ((int) ((this.mToInsets.right - this.mFromInsets.right) * f)), this.mFromInsets.bottom + ((int) ((this.mToInsets.bottom - this.mFromInsets.bottom) * f)));
    }

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return false;
    }

    @Override // android.view.animation.Animation
    public boolean hasExtension() {
        return this.mFromInsets.left < 0 || this.mFromInsets.top < 0 || this.mFromInsets.right < 0 || this.mFromInsets.bottom < 0;
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        super.initialize(i, i2, i3, i4);
        this.mFromInsets = android.graphics.Insets.min(android.graphics.Insets.of(-((int) resolveSize(this.mFromLeftType, this.mFromLeftValue, i, i3)), -((int) resolveSize(this.mFromTopType, this.mFromTopValue, i2, i4)), -((int) resolveSize(this.mFromRightType, this.mFromRightValue, i, i3)), -((int) resolveSize(this.mFromBottomType, this.mFromBottomValue, i2, i4))), android.graphics.Insets.NONE);
        this.mToInsets = android.graphics.Insets.min(android.graphics.Insets.of(-((int) resolveSize(this.mToLeftType, this.mToLeftValue, i, i3)), -((int) resolveSize(this.mToTopType, this.mToTopValue, i2, i4)), -((int) resolveSize(this.mToRightType, this.mToRightValue, i, i3)), -((int) resolveSize(this.mToBottomType, this.mToBottomValue, i2, i4))), android.graphics.Insets.NONE);
    }
}
