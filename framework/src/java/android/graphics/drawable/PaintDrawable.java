package android.graphics.drawable;

/* loaded from: classes.dex */
public class PaintDrawable extends android.graphics.drawable.ShapeDrawable {
    public PaintDrawable() {
    }

    public PaintDrawable(int i) {
        getPaint().setColor(i);
    }

    public void setCornerRadius(float f) {
        float[] fArr;
        if (f <= 0.0f) {
            fArr = null;
        } else {
            fArr = new float[8];
            for (int i = 0; i < 8; i++) {
                fArr[i] = f;
            }
        }
        setCornerRadii(fArr);
    }

    public void setCornerRadii(float[] fArr) {
        if (fArr == null) {
            if (getShape() != null) {
                setShape(null);
            }
        } else {
            setShape(new android.graphics.drawable.shapes.RoundRectShape(fArr, null, null));
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.ShapeDrawable
    protected boolean inflateTag(java.lang.String str, android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet) {
        if (str.equals("corners")) {
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.DrawableCorners);
            int dimensionPixelSize = obtainAttributes.getDimensionPixelSize(0, 0);
            setCornerRadius(dimensionPixelSize);
            int dimensionPixelSize2 = obtainAttributes.getDimensionPixelSize(1, dimensionPixelSize);
            int dimensionPixelSize3 = obtainAttributes.getDimensionPixelSize(2, dimensionPixelSize);
            int dimensionPixelSize4 = obtainAttributes.getDimensionPixelSize(3, dimensionPixelSize);
            int dimensionPixelSize5 = obtainAttributes.getDimensionPixelSize(4, dimensionPixelSize);
            if (dimensionPixelSize2 != dimensionPixelSize || dimensionPixelSize3 != dimensionPixelSize || dimensionPixelSize4 != dimensionPixelSize || dimensionPixelSize5 != dimensionPixelSize) {
                float f = dimensionPixelSize2;
                float f2 = dimensionPixelSize3;
                float f3 = dimensionPixelSize4;
                float f4 = dimensionPixelSize5;
                setCornerRadii(new float[]{f, f, f2, f2, f3, f3, f4, f4});
            }
            obtainAttributes.recycle();
            return true;
        }
        return super.inflateTag(str, resources, xmlPullParser, attributeSet);
    }
}
