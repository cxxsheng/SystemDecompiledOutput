package android.animation;

/* loaded from: classes.dex */
class IntKeyframeSet extends android.animation.KeyframeSet implements android.animation.Keyframes.IntKeyframes {
    public IntKeyframeSet(android.animation.Keyframe.IntKeyframe... intKeyframeArr) {
        super(intKeyframeArr);
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public java.lang.Object getValue(float f) {
        return java.lang.Integer.valueOf(getIntValue(f));
    }

    @Override // android.animation.KeyframeSet
    /* renamed from: clone */
    public android.animation.IntKeyframeSet mo84clone() {
        java.util.List<android.animation.Keyframe> list = this.mKeyframes;
        int size = this.mKeyframes.size();
        android.animation.Keyframe.IntKeyframe[] intKeyframeArr = new android.animation.Keyframe.IntKeyframe[size];
        for (int i = 0; i < size; i++) {
            intKeyframeArr[i] = (android.animation.Keyframe.IntKeyframe) list.get(i).mo85clone();
        }
        return new android.animation.IntKeyframeSet(intKeyframeArr);
    }

    @Override // android.animation.Keyframes.IntKeyframes
    public int getIntValue(float f) {
        if (f <= 0.0f) {
            android.animation.Keyframe.IntKeyframe intKeyframe = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(0);
            android.animation.Keyframe.IntKeyframe intKeyframe2 = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(1);
            int intValue = intKeyframe.getIntValue();
            int intValue2 = intKeyframe2.getIntValue();
            float fraction = intKeyframe.getFraction();
            float fraction2 = intKeyframe2.getFraction();
            android.animation.TimeInterpolator interpolator = intKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f2 = (f - fraction) / (fraction2 - fraction);
            if (this.mEvaluator == null) {
                return intValue + ((int) (f2 * (intValue2 - intValue)));
            }
            return ((java.lang.Number) this.mEvaluator.evaluate(f2, java.lang.Integer.valueOf(intValue), java.lang.Integer.valueOf(intValue2))).intValue();
        }
        if (f >= 1.0f) {
            android.animation.Keyframe.IntKeyframe intKeyframe3 = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            android.animation.Keyframe.IntKeyframe intKeyframe4 = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            int intValue3 = intKeyframe3.getIntValue();
            int intValue4 = intKeyframe4.getIntValue();
            float fraction3 = intKeyframe3.getFraction();
            float fraction4 = intKeyframe4.getFraction();
            android.animation.TimeInterpolator interpolator2 = intKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f3 = (f - fraction3) / (fraction4 - fraction3);
            if (this.mEvaluator == null) {
                return intValue3 + ((int) (f3 * (intValue4 - intValue3)));
            }
            return ((java.lang.Number) this.mEvaluator.evaluate(f3, java.lang.Integer.valueOf(intValue3), java.lang.Integer.valueOf(intValue4))).intValue();
        }
        android.animation.Keyframe.IntKeyframe intKeyframe5 = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(0);
        int i = 1;
        while (i < this.mNumKeyframes) {
            android.animation.Keyframe.IntKeyframe intKeyframe6 = (android.animation.Keyframe.IntKeyframe) this.mKeyframes.get(i);
            if (f >= intKeyframe6.getFraction()) {
                i++;
                intKeyframe5 = intKeyframe6;
            } else {
                android.animation.TimeInterpolator interpolator3 = intKeyframe6.getInterpolator();
                float fraction5 = (f - intKeyframe5.getFraction()) / (intKeyframe6.getFraction() - intKeyframe5.getFraction());
                int intValue5 = intKeyframe5.getIntValue();
                int intValue6 = intKeyframe6.getIntValue();
                if (interpolator3 != null) {
                    fraction5 = interpolator3.getInterpolation(fraction5);
                }
                if (this.mEvaluator == null) {
                    return intValue5 + ((int) (fraction5 * (intValue6 - intValue5)));
                }
                return ((java.lang.Number) this.mEvaluator.evaluate(fraction5, java.lang.Integer.valueOf(intValue5), java.lang.Integer.valueOf(intValue6))).intValue();
            }
        }
        return ((java.lang.Number) this.mKeyframes.get(this.mNumKeyframes - 1).getValue()).intValue();
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public java.lang.Class getType() {
        return java.lang.Integer.class;
    }
}
