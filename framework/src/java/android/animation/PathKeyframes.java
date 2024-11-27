package android.animation;

/* loaded from: classes.dex */
public class PathKeyframes implements android.animation.Keyframes {
    private static final java.util.ArrayList<android.animation.Keyframe> EMPTY_KEYFRAMES = new java.util.ArrayList<>();
    private static final int FRACTION_OFFSET = 0;
    private static final int NUM_COMPONENTS = 3;
    private static final int X_OFFSET = 1;
    private static final int Y_OFFSET = 2;
    private float[] mKeyframeData;
    private android.graphics.PointF mTempPointF;

    public PathKeyframes(android.graphics.Path path) {
        this(path, 0.5f);
    }

    public PathKeyframes(android.graphics.Path path, float f) {
        this.mTempPointF = new android.graphics.PointF();
        if (path == null || path.isEmpty()) {
            throw new java.lang.IllegalArgumentException("The path must not be null or empty");
        }
        this.mKeyframeData = path.approximate(f);
    }

    @Override // android.animation.Keyframes
    public java.util.ArrayList<android.animation.Keyframe> getKeyframes() {
        return EMPTY_KEYFRAMES;
    }

    @Override // android.animation.Keyframes
    public java.lang.Object getValue(float f) {
        int length = this.mKeyframeData.length / 3;
        if (f < 0.0f) {
            return interpolateInRange(f, 0, 1);
        }
        if (f > 1.0f) {
            return interpolateInRange(f, length - 2, length - 1);
        }
        if (f == 0.0f) {
            return pointForIndex(0);
        }
        if (f == 1.0f) {
            return pointForIndex(length - 1);
        }
        int i = length - 1;
        int i2 = 0;
        while (i2 <= i) {
            int i3 = (i2 + i) / 2;
            float f2 = this.mKeyframeData[(i3 * 3) + 0];
            if (f < f2) {
                i = i3 - 1;
            } else if (f > f2) {
                i2 = i3 + 1;
            } else {
                return pointForIndex(i3);
            }
        }
        return interpolateInRange(f, i, i2);
    }

    private android.graphics.PointF interpolateInRange(float f, int i, int i2) {
        int i3 = i * 3;
        int i4 = i2 * 3;
        float f2 = this.mKeyframeData[i3 + 0];
        float f3 = (f - f2) / (this.mKeyframeData[i4 + 0] - f2);
        float f4 = this.mKeyframeData[i3 + 1];
        float f5 = this.mKeyframeData[i4 + 1];
        float f6 = this.mKeyframeData[i3 + 2];
        float f7 = this.mKeyframeData[i4 + 2];
        this.mTempPointF.set(interpolate(f3, f4, f5), interpolate(f3, f6, f7));
        return this.mTempPointF;
    }

    @Override // android.animation.Keyframes
    public void setEvaluator(android.animation.TypeEvaluator typeEvaluator) {
    }

    @Override // android.animation.Keyframes
    public java.lang.Class getType() {
        return android.graphics.PointF.class;
    }

    @Override // android.animation.Keyframes
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.animation.Keyframes m108clone() {
        try {
            return (android.animation.Keyframes) super.clone();
        } catch (java.lang.CloneNotSupportedException e) {
            return null;
        }
    }

    private android.graphics.PointF pointForIndex(int i) {
        int i2 = i * 3;
        this.mTempPointF.set(this.mKeyframeData[i2 + 1], this.mKeyframeData[i2 + 2]);
        return this.mTempPointF;
    }

    private static float interpolate(float f, float f2, float f3) {
        return f2 + ((f3 - f2) * f);
    }

    public android.animation.Keyframes.FloatKeyframes createXFloatKeyframes() {
        return new android.animation.PathKeyframes.FloatKeyframesBase() { // from class: android.animation.PathKeyframes.1
            @Override // android.animation.Keyframes.FloatKeyframes
            public float getFloatValue(float f) {
                return ((android.graphics.PointF) android.animation.PathKeyframes.this.getValue(f)).x;
            }
        };
    }

    public android.animation.Keyframes.FloatKeyframes createYFloatKeyframes() {
        return new android.animation.PathKeyframes.FloatKeyframesBase() { // from class: android.animation.PathKeyframes.2
            @Override // android.animation.Keyframes.FloatKeyframes
            public float getFloatValue(float f) {
                return ((android.graphics.PointF) android.animation.PathKeyframes.this.getValue(f)).y;
            }
        };
    }

    public android.animation.Keyframes.IntKeyframes createXIntKeyframes() {
        return new android.animation.PathKeyframes.IntKeyframesBase() { // from class: android.animation.PathKeyframes.3
            @Override // android.animation.Keyframes.IntKeyframes
            public int getIntValue(float f) {
                return java.lang.Math.round(((android.graphics.PointF) android.animation.PathKeyframes.this.getValue(f)).x);
            }
        };
    }

    public android.animation.Keyframes.IntKeyframes createYIntKeyframes() {
        return new android.animation.PathKeyframes.IntKeyframesBase() { // from class: android.animation.PathKeyframes.4
            @Override // android.animation.Keyframes.IntKeyframes
            public int getIntValue(float f) {
                return java.lang.Math.round(((android.graphics.PointF) android.animation.PathKeyframes.this.getValue(f)).y);
            }
        };
    }

    private static abstract class SimpleKeyframes implements android.animation.Keyframes {
        private SimpleKeyframes() {
        }

        @Override // android.animation.Keyframes
        public void setEvaluator(android.animation.TypeEvaluator typeEvaluator) {
        }

        @Override // android.animation.Keyframes
        public java.util.ArrayList<android.animation.Keyframe> getKeyframes() {
            return android.animation.PathKeyframes.EMPTY_KEYFRAMES;
        }

        @Override // android.animation.Keyframes
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.animation.Keyframes m109clone() {
            try {
                return (android.animation.Keyframes) super.clone();
            } catch (java.lang.CloneNotSupportedException e) {
                return null;
            }
        }
    }

    static abstract class IntKeyframesBase extends android.animation.PathKeyframes.SimpleKeyframes implements android.animation.Keyframes.IntKeyframes {
        IntKeyframesBase() {
            super();
        }

        @Override // android.animation.Keyframes
        public java.lang.Class getType() {
            return java.lang.Integer.class;
        }

        @Override // android.animation.Keyframes
        public java.lang.Object getValue(float f) {
            return java.lang.Integer.valueOf(getIntValue(f));
        }
    }

    static abstract class FloatKeyframesBase extends android.animation.PathKeyframes.SimpleKeyframes implements android.animation.Keyframes.FloatKeyframes {
        FloatKeyframesBase() {
            super();
        }

        @Override // android.animation.Keyframes
        public java.lang.Class getType() {
            return java.lang.Float.class;
        }

        @Override // android.animation.Keyframes
        public java.lang.Object getValue(float f) {
            return java.lang.Float.valueOf(getFloatValue(f));
        }
    }
}
