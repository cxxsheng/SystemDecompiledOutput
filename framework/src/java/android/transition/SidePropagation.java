package android.transition;

/* loaded from: classes3.dex */
public class SidePropagation extends android.transition.VisibilityPropagation {
    private static final java.lang.String TAG = "SlidePropagation";
    private float mPropagationSpeed = 3.0f;
    private int mSide = 80;

    public void setSide(int i) {
        this.mSide = i;
    }

    public void setPropagationSpeed(float f) {
        if (f == 0.0f) {
            throw new java.lang.IllegalArgumentException("propagationSpeed may not be 0");
        }
        this.mPropagationSpeed = f;
    }

    @Override // android.transition.TransitionPropagation
    public long getStartDelay(android.view.ViewGroup viewGroup, android.transition.Transition transition, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        int i;
        int i2;
        int i3;
        android.transition.TransitionValues transitionValues3 = transitionValues;
        if (transitionValues3 == null && transitionValues2 == null) {
            return 0L;
        }
        android.graphics.Rect epicenter = transition.getEpicenter();
        if (transitionValues2 == null || getViewVisibility(transitionValues3) == 0) {
            i = -1;
        } else {
            transitionValues3 = transitionValues2;
            i = 1;
        }
        int viewX = getViewX(transitionValues3);
        int viewY = getViewY(transitionValues3);
        int[] iArr = new int[2];
        viewGroup.getLocationOnScreen(iArr);
        int round = iArr[0] + java.lang.Math.round(viewGroup.getTranslationX());
        int round2 = iArr[1] + java.lang.Math.round(viewGroup.getTranslationY());
        int width = round + viewGroup.getWidth();
        int height = round2 + viewGroup.getHeight();
        if (epicenter == null) {
            i2 = (round + width) / 2;
            i3 = (round2 + height) / 2;
        } else {
            i2 = epicenter.centerX();
            i3 = epicenter.centerY();
        }
        float distance = distance(viewGroup, viewX, viewY, i2, i3, round, round2, width, height) / getMaxDistance(viewGroup);
        long duration = transition.getDuration();
        if (duration < 0) {
            duration = 300;
        }
        return java.lang.Math.round(((duration * i) / this.mPropagationSpeed) * distance);
    }

    private int distance(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = 5;
        if (this.mSide == 8388611) {
            if (!(view.getLayoutDirection() == 1)) {
                i9 = 3;
            }
        } else if (this.mSide == 8388613) {
            if (view.getLayoutDirection() == 1) {
                i9 = 3;
            }
        } else {
            i9 = this.mSide;
        }
        switch (i9) {
            case 3:
                return (i7 - i) + java.lang.Math.abs(i4 - i2);
            case 5:
                return (i - i5) + java.lang.Math.abs(i4 - i2);
            case 48:
                return (i8 - i2) + java.lang.Math.abs(i3 - i);
            case 80:
                return (i2 - i6) + java.lang.Math.abs(i3 - i);
            default:
                return 0;
        }
    }

    private int getMaxDistance(android.view.ViewGroup viewGroup) {
        switch (this.mSide) {
            case 3:
            case 5:
            case android.view.Gravity.START /* 8388611 */:
            case android.view.Gravity.END /* 8388613 */:
                return viewGroup.getWidth();
            default:
                return viewGroup.getHeight();
        }
    }
}
