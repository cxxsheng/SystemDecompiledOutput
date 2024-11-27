package android.transition;

/* loaded from: classes3.dex */
public class PatternPathMotion extends android.transition.PathMotion {
    private android.graphics.Path mOriginalPatternPath;
    private final android.graphics.Path mPatternPath = new android.graphics.Path();
    private final android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();

    public PatternPathMotion() {
        this.mPatternPath.lineTo(1.0f, 0.0f);
        this.mOriginalPatternPath = this.mPatternPath;
    }

    public PatternPathMotion(android.content.Context context, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.PatternPathMotion);
        try {
            java.lang.String string = obtainStyledAttributes.getString(0);
            if (string == null) {
                throw new java.lang.RuntimeException("pathData must be supplied for patternPathMotion");
            }
            setPatternPath(android.util.PathParser.createPathFromPathData(string));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public PatternPathMotion(android.graphics.Path path) {
        setPatternPath(path);
    }

    public android.graphics.Path getPatternPath() {
        return this.mOriginalPatternPath;
    }

    public void setPatternPath(android.graphics.Path path) {
        android.graphics.PathMeasure pathMeasure = new android.graphics.PathMeasure(path, false);
        float[] fArr = new float[2];
        pathMeasure.getPosTan(pathMeasure.getLength(), fArr, null);
        float f = fArr[0];
        float f2 = fArr[1];
        pathMeasure.getPosTan(0.0f, fArr, null);
        float f3 = fArr[0];
        float f4 = fArr[1];
        if (f3 == f && f4 == f2) {
            throw new java.lang.IllegalArgumentException("pattern must not end at the starting point");
        }
        this.mTempMatrix.setTranslate(-f3, -f4);
        double d = f - f3;
        double d2 = f2 - f4;
        float hypot = 1.0f / ((float) java.lang.Math.hypot(d, d2));
        this.mTempMatrix.postScale(hypot, hypot);
        this.mTempMatrix.postRotate((float) java.lang.Math.toDegrees(-java.lang.Math.atan2(d2, d)));
        path.transform(this.mTempMatrix, this.mPatternPath);
        this.mOriginalPatternPath = path;
    }

    @Override // android.transition.PathMotion
    public android.graphics.Path getPath(float f, float f2, float f3, float f4) {
        double d = f3 - f;
        double d2 = f4 - f2;
        float hypot = (float) java.lang.Math.hypot(d, d2);
        double atan2 = java.lang.Math.atan2(d2, d);
        this.mTempMatrix.setScale(hypot, hypot);
        this.mTempMatrix.postRotate((float) java.lang.Math.toDegrees(atan2));
        this.mTempMatrix.postTranslate(f, f2);
        android.graphics.Path path = new android.graphics.Path();
        this.mPatternPath.transform(this.mTempMatrix, path);
        return path;
    }
}
