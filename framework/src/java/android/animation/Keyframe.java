package android.animation;

/* loaded from: classes.dex */
public abstract class Keyframe implements java.lang.Cloneable {
    float mFraction;
    boolean mHasValue;
    private android.animation.TimeInterpolator mInterpolator = null;
    java.lang.Class mValueType;
    boolean mValueWasSetOnStart;

    @Override // 
    /* renamed from: clone */
    public abstract android.animation.Keyframe mo85clone();

    public abstract java.lang.Object getValue();

    public abstract void setValue(java.lang.Object obj);

    public static android.animation.Keyframe ofInt(float f, int i) {
        return new android.animation.Keyframe.IntKeyframe(f, i);
    }

    public static android.animation.Keyframe ofInt(float f) {
        return new android.animation.Keyframe.IntKeyframe(f);
    }

    public static android.animation.Keyframe ofFloat(float f, float f2) {
        return new android.animation.Keyframe.FloatKeyframe(f, f2);
    }

    public static android.animation.Keyframe ofFloat(float f) {
        return new android.animation.Keyframe.FloatKeyframe(f);
    }

    public static android.animation.Keyframe ofObject(float f, java.lang.Object obj) {
        return new android.animation.Keyframe.ObjectKeyframe(f, obj);
    }

    public static android.animation.Keyframe ofObject(float f) {
        return new android.animation.Keyframe.ObjectKeyframe(f, null);
    }

    public boolean hasValue() {
        return this.mHasValue;
    }

    boolean valueWasSetOnStart() {
        return this.mValueWasSetOnStart;
    }

    void setValueWasSetOnStart(boolean z) {
        this.mValueWasSetOnStart = z;
    }

    public float getFraction() {
        return this.mFraction;
    }

    public void setFraction(float f) {
        this.mFraction = f;
    }

    public android.animation.TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    public void setInterpolator(android.animation.TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
    }

    public java.lang.Class getType() {
        return this.mValueType;
    }

    static class ObjectKeyframe extends android.animation.Keyframe {
        java.lang.Object mValue;

        ObjectKeyframe(float f, java.lang.Object obj) {
            this.mFraction = f;
            this.mValue = obj;
            this.mHasValue = obj != null;
            this.mValueType = this.mHasValue ? obj.getClass() : java.lang.Object.class;
        }

        @Override // android.animation.Keyframe
        public java.lang.Object getValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public void setValue(java.lang.Object obj) {
            this.mValue = obj;
            this.mHasValue = obj != null;
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone */
        public android.animation.Keyframe.ObjectKeyframe mo85clone() {
            android.animation.Keyframe.ObjectKeyframe objectKeyframe = new android.animation.Keyframe.ObjectKeyframe(getFraction(), hasValue() ? this.mValue : null);
            objectKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            objectKeyframe.setInterpolator(getInterpolator());
            return objectKeyframe;
        }
    }

    static class IntKeyframe extends android.animation.Keyframe {
        int mValue;

        IntKeyframe(float f, int i) {
            this.mFraction = f;
            this.mValue = i;
            this.mValueType = java.lang.Integer.TYPE;
            this.mHasValue = true;
        }

        IntKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = java.lang.Integer.TYPE;
        }

        public int getIntValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public java.lang.Object getValue() {
            return java.lang.Integer.valueOf(this.mValue);
        }

        @Override // android.animation.Keyframe
        public void setValue(java.lang.Object obj) {
            if (obj != null && obj.getClass() == java.lang.Integer.class) {
                this.mValue = ((java.lang.Integer) obj).intValue();
                this.mHasValue = true;
            }
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone */
        public android.animation.Keyframe.IntKeyframe mo85clone() {
            android.animation.Keyframe.IntKeyframe intKeyframe;
            if (this.mHasValue) {
                intKeyframe = new android.animation.Keyframe.IntKeyframe(getFraction(), this.mValue);
            } else {
                intKeyframe = new android.animation.Keyframe.IntKeyframe(getFraction());
            }
            intKeyframe.setInterpolator(getInterpolator());
            intKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return intKeyframe;
        }
    }

    static class FloatKeyframe extends android.animation.Keyframe {
        float mValue;

        FloatKeyframe(float f, float f2) {
            this.mFraction = f;
            this.mValue = f2;
            this.mValueType = java.lang.Float.TYPE;
            this.mHasValue = true;
        }

        FloatKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = java.lang.Float.TYPE;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        @Override // android.animation.Keyframe
        public java.lang.Object getValue() {
            return java.lang.Float.valueOf(this.mValue);
        }

        @Override // android.animation.Keyframe
        public void setValue(java.lang.Object obj) {
            if (obj != null && obj.getClass() == java.lang.Float.class) {
                this.mValue = ((java.lang.Float) obj).floatValue();
                this.mHasValue = true;
            }
        }

        @Override // android.animation.Keyframe
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.animation.Keyframe.FloatKeyframe mo85clone() {
            android.animation.Keyframe.FloatKeyframe floatKeyframe;
            if (this.mHasValue) {
                floatKeyframe = new android.animation.Keyframe.FloatKeyframe(getFraction(), this.mValue);
            } else {
                floatKeyframe = new android.animation.Keyframe.FloatKeyframe(getFraction());
            }
            floatKeyframe.setInterpolator(getInterpolator());
            floatKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return floatKeyframe;
        }
    }
}
