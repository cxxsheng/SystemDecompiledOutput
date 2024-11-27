package android.animation;

/* loaded from: classes.dex */
public class KeyframeSet implements android.animation.Keyframes {
    android.animation.TypeEvaluator mEvaluator;
    android.animation.Keyframe mFirstKeyframe;
    android.animation.TimeInterpolator mInterpolator;
    java.util.List<android.animation.Keyframe> mKeyframes;
    android.animation.Keyframe mLastKeyframe;
    int mNumKeyframes;

    public KeyframeSet(android.animation.Keyframe... keyframeArr) {
        this.mNumKeyframes = keyframeArr.length;
        this.mKeyframes = java.util.Arrays.asList(keyframeArr);
        this.mFirstKeyframe = keyframeArr[0];
        this.mLastKeyframe = keyframeArr[this.mNumKeyframes - 1];
        this.mInterpolator = this.mLastKeyframe.getInterpolator();
    }

    @Override // android.animation.Keyframes
    public java.util.List<android.animation.Keyframe> getKeyframes() {
        return this.mKeyframes;
    }

    public static android.animation.KeyframeSet ofInt(int... iArr) {
        int length = iArr.length;
        android.animation.Keyframe.IntKeyframe[] intKeyframeArr = new android.animation.Keyframe.IntKeyframe[java.lang.Math.max(length, 2)];
        if (length != 1) {
            intKeyframeArr[0] = (android.animation.Keyframe.IntKeyframe) android.animation.Keyframe.ofInt(0.0f, iArr[0]);
            for (int i = 1; i < length; i++) {
                intKeyframeArr[i] = (android.animation.Keyframe.IntKeyframe) android.animation.Keyframe.ofInt(i / (length - 1), iArr[i]);
            }
        } else {
            intKeyframeArr[0] = (android.animation.Keyframe.IntKeyframe) android.animation.Keyframe.ofInt(0.0f);
            intKeyframeArr[1] = (android.animation.Keyframe.IntKeyframe) android.animation.Keyframe.ofInt(1.0f, iArr[0]);
        }
        return new android.animation.IntKeyframeSet(intKeyframeArr);
    }

    public static android.animation.KeyframeSet ofFloat(float... fArr) {
        int length = fArr.length;
        android.animation.Keyframe.FloatKeyframe[] floatKeyframeArr = new android.animation.Keyframe.FloatKeyframe[java.lang.Math.max(length, 2)];
        boolean z = false;
        if (length != 1) {
            floatKeyframeArr[0] = (android.animation.Keyframe.FloatKeyframe) android.animation.Keyframe.ofFloat(0.0f, fArr[0]);
            for (int i = 1; i < length; i++) {
                floatKeyframeArr[i] = (android.animation.Keyframe.FloatKeyframe) android.animation.Keyframe.ofFloat(i / (length - 1), fArr[i]);
                if (java.lang.Float.isNaN(fArr[i])) {
                    z = true;
                }
            }
        } else {
            floatKeyframeArr[0] = (android.animation.Keyframe.FloatKeyframe) android.animation.Keyframe.ofFloat(0.0f);
            floatKeyframeArr[1] = (android.animation.Keyframe.FloatKeyframe) android.animation.Keyframe.ofFloat(1.0f, fArr[0]);
            if (java.lang.Float.isNaN(fArr[0])) {
                z = true;
            }
        }
        if (z) {
            android.util.Log.w("Animator", "Bad value (NaN) in float animator");
        }
        return new android.animation.FloatKeyframeSet(floatKeyframeArr);
    }

    public static android.animation.KeyframeSet ofKeyframe(android.animation.Keyframe... keyframeArr) {
        int length = keyframeArr.length;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < length; i2++) {
            if (keyframeArr[i2] instanceof android.animation.Keyframe.FloatKeyframe) {
                z = true;
            } else if (keyframeArr[i2] instanceof android.animation.Keyframe.IntKeyframe) {
                z2 = true;
            } else {
                z3 = true;
            }
        }
        if (z && !z2 && !z3) {
            android.animation.Keyframe.FloatKeyframe[] floatKeyframeArr = new android.animation.Keyframe.FloatKeyframe[length];
            while (i < length) {
                floatKeyframeArr[i] = (android.animation.Keyframe.FloatKeyframe) keyframeArr[i];
                i++;
            }
            return new android.animation.FloatKeyframeSet(floatKeyframeArr);
        }
        if (z2 && !z && !z3) {
            android.animation.Keyframe.IntKeyframe[] intKeyframeArr = new android.animation.Keyframe.IntKeyframe[length];
            while (i < length) {
                intKeyframeArr[i] = (android.animation.Keyframe.IntKeyframe) keyframeArr[i];
                i++;
            }
            return new android.animation.IntKeyframeSet(intKeyframeArr);
        }
        return new android.animation.KeyframeSet(keyframeArr);
    }

    public static android.animation.KeyframeSet ofObject(java.lang.Object... objArr) {
        int length = objArr.length;
        android.animation.Keyframe.ObjectKeyframe[] objectKeyframeArr = new android.animation.Keyframe.ObjectKeyframe[java.lang.Math.max(length, 2)];
        if (length != 1) {
            objectKeyframeArr[0] = (android.animation.Keyframe.ObjectKeyframe) android.animation.Keyframe.ofObject(0.0f, objArr[0]);
            for (int i = 1; i < length; i++) {
                objectKeyframeArr[i] = (android.animation.Keyframe.ObjectKeyframe) android.animation.Keyframe.ofObject(i / (length - 1), objArr[i]);
            }
        } else {
            objectKeyframeArr[0] = (android.animation.Keyframe.ObjectKeyframe) android.animation.Keyframe.ofObject(0.0f);
            objectKeyframeArr[1] = (android.animation.Keyframe.ObjectKeyframe) android.animation.Keyframe.ofObject(1.0f, objArr[0]);
        }
        return new android.animation.KeyframeSet(objectKeyframeArr);
    }

    public static android.animation.PathKeyframes ofPath(android.graphics.Path path) {
        return new android.animation.PathKeyframes(path);
    }

    public static android.animation.PathKeyframes ofPath(android.graphics.Path path, float f) {
        return new android.animation.PathKeyframes(path, f);
    }

    @Override // android.animation.Keyframes
    public void setEvaluator(android.animation.TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
    }

    @Override // android.animation.Keyframes
    public java.lang.Class getType() {
        return this.mFirstKeyframe.getType();
    }

    @Override // 
    /* renamed from: clone */
    public android.animation.KeyframeSet mo84clone() {
        java.util.List<android.animation.Keyframe> list = this.mKeyframes;
        int size = this.mKeyframes.size();
        android.animation.Keyframe[] keyframeArr = new android.animation.Keyframe[size];
        for (int i = 0; i < size; i++) {
            keyframeArr[i] = list.get(i).mo85clone();
        }
        return new android.animation.KeyframeSet(keyframeArr);
    }

    @Override // android.animation.Keyframes
    public java.lang.Object getValue(float f) {
        if (this.mNumKeyframes == 2) {
            if (this.mInterpolator != null) {
                f = this.mInterpolator.getInterpolation(f);
            }
            return this.mEvaluator.evaluate(f, this.mFirstKeyframe.getValue(), this.mLastKeyframe.getValue());
        }
        int i = 1;
        if (f <= 0.0f) {
            android.animation.Keyframe keyframe = this.mKeyframes.get(1);
            android.animation.TimeInterpolator interpolator = keyframe.getInterpolator();
            if (interpolator != null) {
                f = interpolator.getInterpolation(f);
            }
            float fraction = this.mFirstKeyframe.getFraction();
            return this.mEvaluator.evaluate((f - fraction) / (keyframe.getFraction() - fraction), this.mFirstKeyframe.getValue(), keyframe.getValue());
        }
        if (f >= 1.0f) {
            android.animation.Keyframe keyframe2 = this.mKeyframes.get(this.mNumKeyframes - 2);
            android.animation.TimeInterpolator interpolator2 = this.mLastKeyframe.getInterpolator();
            if (interpolator2 != null) {
                f = interpolator2.getInterpolation(f);
            }
            float fraction2 = keyframe2.getFraction();
            return this.mEvaluator.evaluate((f - fraction2) / (this.mLastKeyframe.getFraction() - fraction2), keyframe2.getValue(), this.mLastKeyframe.getValue());
        }
        android.animation.Keyframe keyframe3 = this.mFirstKeyframe;
        while (i < this.mNumKeyframes) {
            android.animation.Keyframe keyframe4 = this.mKeyframes.get(i);
            if (f >= keyframe4.getFraction()) {
                i++;
                keyframe3 = keyframe4;
            } else {
                android.animation.TimeInterpolator interpolator3 = keyframe4.getInterpolator();
                float fraction3 = keyframe3.getFraction();
                float fraction4 = (f - fraction3) / (keyframe4.getFraction() - fraction3);
                if (interpolator3 != null) {
                    fraction4 = interpolator3.getInterpolation(fraction4);
                }
                return this.mEvaluator.evaluate(fraction4, keyframe3.getValue(), keyframe4.getValue());
            }
        }
        return this.mLastKeyframe.getValue();
    }

    public java.lang.String toString() {
        java.lang.String str = " ";
        for (int i = 0; i < this.mNumKeyframes; i++) {
            str = str + this.mKeyframes.get(i).getValue() + "  ";
        }
        return str;
    }
}
