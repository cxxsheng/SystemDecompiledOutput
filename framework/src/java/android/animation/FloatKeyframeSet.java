package android.animation;

/* loaded from: classes.dex */
class FloatKeyframeSet extends android.animation.KeyframeSet implements android.animation.Keyframes.FloatKeyframes {
    public FloatKeyframeSet(android.animation.Keyframe.FloatKeyframe... floatKeyframeArr) {
        super(floatKeyframeArr);
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public java.lang.Object getValue(float f) {
        return java.lang.Float.valueOf(getFloatValue(f));
    }

    @Override // android.animation.KeyframeSet
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.animation.FloatKeyframeSet mo84clone() {
        java.util.List<android.animation.Keyframe> list = this.mKeyframes;
        int size = this.mKeyframes.size();
        android.animation.Keyframe.FloatKeyframe[] floatKeyframeArr = new android.animation.Keyframe.FloatKeyframe[size];
        for (int i = 0; i < size; i++) {
            floatKeyframeArr[i] = (android.animation.Keyframe.FloatKeyframe) list.get(i).mo85clone();
        }
        return new android.animation.FloatKeyframeSet(floatKeyframeArr);
    }

    @Override // android.animation.Keyframes.FloatKeyframes
    public float getFloatValue(float f) {
        if (f <= 0.0f) {
            android.animation.Keyframe.FloatKeyframe floatKeyframe = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(0);
            android.animation.Keyframe.FloatKeyframe floatKeyframe2 = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(1);
            float floatValue = floatKeyframe.getFloatValue();
            float floatValue2 = floatKeyframe2.getFloatValue();
            float fraction = floatKeyframe.getFraction();
            float fraction2 = floatKeyframe2.getFraction();
            android.animation.TimeInterpolator interpolator = floatKeyframe2.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float f2 = (f - fraction) / (fraction2 - fraction);
            if (this.mEvaluator == null) {
                return floatValue + (f2 * (floatValue2 - floatValue));
            }
            return ((java.lang.Number) this.mEvaluator.evaluate(f2, java.lang.Float.valueOf(floatValue), java.lang.Float.valueOf(floatValue2))).floatValue();
        }
        if (f >= 1.0f) {
            android.animation.Keyframe.FloatKeyframe floatKeyframe3 = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 2);
            android.animation.Keyframe.FloatKeyframe floatKeyframe4 = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(this.mNumKeyframes - 1);
            float floatValue3 = floatKeyframe3.getFloatValue();
            float floatValue4 = floatKeyframe4.getFloatValue();
            float fraction3 = floatKeyframe3.getFraction();
            float fraction4 = floatKeyframe4.getFraction();
            android.animation.TimeInterpolator interpolator2 = floatKeyframe4.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float f3 = (f - fraction3) / (fraction4 - fraction3);
            if (this.mEvaluator == null) {
                return floatValue3 + (f3 * (floatValue4 - floatValue3));
            }
            return ((java.lang.Number) this.mEvaluator.evaluate(f3, java.lang.Float.valueOf(floatValue3), java.lang.Float.valueOf(floatValue4))).floatValue();
        }
        android.animation.Keyframe.FloatKeyframe floatKeyframe5 = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(0);
        int i = 1;
        while (i < this.mNumKeyframes) {
            android.animation.Keyframe.FloatKeyframe floatKeyframe6 = (android.animation.Keyframe.FloatKeyframe) this.mKeyframes.get(i);
            if (f >= floatKeyframe6.getFraction()) {
                i++;
                floatKeyframe5 = floatKeyframe6;
            } else {
                android.animation.TimeInterpolator interpolator3 = floatKeyframe6.getInterpolator();
                float fraction5 = (f - floatKeyframe5.getFraction()) / (floatKeyframe6.getFraction() - floatKeyframe5.getFraction());
                float floatValue5 = floatKeyframe5.getFloatValue();
                float floatValue6 = floatKeyframe6.getFloatValue();
                if (interpolator3 != null) {
                    fraction5 = interpolator3.getInterpolation(fraction5);
                }
                if (this.mEvaluator == null) {
                    return floatValue5 + (fraction5 * (floatValue6 - floatValue5));
                }
                return ((java.lang.Number) this.mEvaluator.evaluate(fraction5, java.lang.Float.valueOf(floatValue5), java.lang.Float.valueOf(floatValue6))).floatValue();
            }
        }
        return ((java.lang.Number) this.mKeyframes.get(this.mNumKeyframes - 1).getValue()).floatValue();
    }

    @Override // android.animation.KeyframeSet, android.animation.Keyframes
    public java.lang.Class getType() {
        return java.lang.Float.class;
    }
}
