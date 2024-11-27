package android.view.animation;

@android.graphics.animation.HasNativeInterpolator
/* loaded from: classes4.dex */
public class PathInterpolator extends android.view.animation.BaseInterpolator implements android.graphics.animation.NativeInterpolator {
    private static final float PRECISION = 0.002f;
    private float[] mX;
    private float[] mY;

    public PathInterpolator(android.graphics.Path path) {
        initPath(path);
    }

    public PathInterpolator(float f, float f2) {
        initQuad(f, f2);
    }

    public PathInterpolator(float f, float f2, float f3, float f4) {
        initCubic(f, f2, f3, f4);
    }

    public PathInterpolator(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public PathInterpolator(android.content.res.Resources resources, android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes;
        if (theme != null) {
            obtainAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PathInterpolator, 0, 0);
        } else {
            obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.PathInterpolator);
        }
        parseInterpolatorFromTypeArray(obtainAttributes);
        setChangingConfiguration(obtainAttributes.getChangingConfigurations());
        obtainAttributes.recycle();
    }

    private void parseInterpolatorFromTypeArray(android.content.res.TypedArray typedArray) {
        if (typedArray.hasValue(4)) {
            java.lang.String string = typedArray.getString(4);
            android.graphics.Path createPathFromPathData = android.util.PathParser.createPathFromPathData(string);
            if (createPathFromPathData == null) {
                throw new android.view.InflateException("The path is null, which is created from " + string);
            }
            initPath(createPathFromPathData);
            return;
        }
        if (!typedArray.hasValue(0)) {
            throw new android.view.InflateException("pathInterpolator requires the controlX1 attribute");
        }
        if (typedArray.hasValue(1)) {
            float f = typedArray.getFloat(0, 0.0f);
            float f2 = typedArray.getFloat(1, 0.0f);
            boolean hasValue = typedArray.hasValue(2);
            if (hasValue != typedArray.hasValue(3)) {
                throw new android.view.InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
            }
            if (!hasValue) {
                initQuad(f, f2);
                return;
            } else {
                initCubic(f, f2, typedArray.getFloat(2, 0.0f), typedArray.getFloat(3, 0.0f));
                return;
            }
        }
        throw new android.view.InflateException("pathInterpolator requires the controlY1 attribute");
    }

    private void initQuad(float f, float f2) {
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        initPath(path);
    }

    private void initCubic(float f, float f2, float f3, float f4) {
        android.graphics.Path path = new android.graphics.Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(path);
    }

    private void initPath(android.graphics.Path path) {
        float[] approximate = path.approximate(0.002f);
        int length = approximate.length / 3;
        float f = 0.0f;
        if (approximate[1] != 0.0f || approximate[2] != 0.0f || approximate[approximate.length - 2] != 1.0f || approximate[approximate.length - 1] != 1.0f) {
            throw new java.lang.IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
        }
        this.mX = new float[length];
        this.mY = new float[length];
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        while (i < length) {
            int i3 = i2 + 1;
            float f3 = approximate[i2];
            int i4 = i3 + 1;
            float f4 = approximate[i3];
            int i5 = i4 + 1;
            float f5 = approximate[i4];
            if (f3 == f && f4 != f2) {
                throw new java.lang.IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
            }
            if (f4 < f2) {
                throw new java.lang.IllegalArgumentException("The Path cannot loop back on itself.");
            }
            this.mX[i] = f4;
            this.mY[i] = f5;
            i++;
            f = f3;
            f2 = f4;
            i2 = i5;
        }
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int length = this.mX.length - 1;
        int i = 0;
        while (length - i > 1) {
            int i2 = (i + length) / 2;
            if (f < this.mX[i2]) {
                length = i2;
            } else {
                i = i2;
            }
        }
        float f2 = this.mX[length] - this.mX[i];
        if (f2 == 0.0f) {
            return this.mY[i];
        }
        float f3 = (f - this.mX[i]) / f2;
        float f4 = this.mY[i];
        return f4 + (f3 * (this.mY[length] - f4));
    }

    @Override // android.graphics.animation.NativeInterpolator
    public long createNativeInterpolator() {
        return android.graphics.animation.NativeInterpolatorFactory.createPathInterpolator(this.mX, this.mY);
    }
}
