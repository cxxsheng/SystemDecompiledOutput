package com.android.internal.dynamicanimation.animation;

/* loaded from: classes4.dex */
public abstract class DynamicAnimation<T extends com.android.internal.dynamicanimation.animation.DynamicAnimation<T>> implements android.animation.AnimationHandler.AnimationFrameCallback {
    public static final float MIN_VISIBLE_CHANGE_ALPHA = 0.00390625f;
    public static final float MIN_VISIBLE_CHANGE_PIXELS = 1.0f;
    public static final float MIN_VISIBLE_CHANGE_ROTATION_DEGREES = 0.1f;
    public static final float MIN_VISIBLE_CHANGE_SCALE = 0.002f;
    private static final float THRESHOLD_MULTIPLIER = 0.75f;
    private static final float UNSET = Float.MAX_VALUE;
    private android.animation.AnimationHandler mAnimationHandler;
    private final java.util.ArrayList<com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener> mEndListeners;
    private long mLastFrameTime;
    float mMaxValue;
    float mMinValue;
    private float mMinVisibleChange;
    final android.util.FloatProperty mProperty;
    boolean mRunning;
    boolean mStartValueIsSet;
    final java.lang.Object mTarget;
    private final java.util.ArrayList<com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener> mUpdateListeners;
    float mValue;
    float mVelocity;
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty TRANSLATION_X = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("translationX") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.1
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationX());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty TRANSLATION_Y = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("translationY") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.2
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationY());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty TRANSLATION_Z = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("translationZ") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.3
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationZ(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationZ());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty SCALE_X = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("scaleX") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.4
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScaleX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScaleX());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty SCALE_Y = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("scaleY") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.5
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScaleY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScaleY());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty ROTATION = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("rotation") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.6
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotation(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotation());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty ROTATION_X = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("rotationX") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.7
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotationX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotationX());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty ROTATION_Y = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("rotationY") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.8
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotationY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotationY());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty X = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("x") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.9
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getX());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty Y = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("y") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.10
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getY());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty Z = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("z") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.11
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setZ(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getZ());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty ALPHA = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("alpha") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.12
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setAlpha(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getAlpha());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty SCROLL_X = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("scrollX") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.13
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScrollX((int) f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScrollX());
        }
    };
    public static final com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty SCROLL_Y = new com.android.internal.dynamicanimation.animation.DynamicAnimation.ViewProperty("scrollY") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.14
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScrollY((int) f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScrollY());
        }
    };

    public interface OnAnimationEndListener {
        void onAnimationEnd(com.android.internal.dynamicanimation.animation.DynamicAnimation dynamicAnimation, boolean z, float f, float f2);
    }

    public interface OnAnimationUpdateListener {
        void onAnimationUpdate(com.android.internal.dynamicanimation.animation.DynamicAnimation dynamicAnimation, float f, float f2);
    }

    abstract float getAcceleration(float f, float f2);

    abstract boolean isAtEquilibrium(float f, float f2);

    abstract void setValueThreshold(float f);

    abstract boolean updateValueAndVelocity(long j);

    public static abstract class ViewProperty extends android.util.FloatProperty<android.view.View> {
        private ViewProperty(java.lang.String str) {
            super(str);
        }
    }

    static class MassState {
        float mValue;
        float mVelocity;

        MassState() {
        }
    }

    DynamicAnimation(final com.android.internal.dynamicanimation.animation.FloatValueHolder floatValueHolder) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -this.mMaxValue;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new java.util.ArrayList<>();
        this.mUpdateListeners = new java.util.ArrayList<>();
        this.mTarget = null;
        this.mProperty = new android.util.FloatProperty("FloatValueHolder") { // from class: com.android.internal.dynamicanimation.animation.DynamicAnimation.15
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.util.Property
            public java.lang.Float get(java.lang.Object obj) {
                return java.lang.Float.valueOf(floatValueHolder.getValue());
            }

            @Override // android.util.FloatProperty
            public void setValue(java.lang.Object obj, float f) {
                floatValueHolder.setValue(f);
            }
        };
        this.mMinVisibleChange = 1.0f;
    }

    <K> DynamicAnimation(K k, android.util.FloatProperty<K> floatProperty) {
        this.mVelocity = 0.0f;
        this.mValue = Float.MAX_VALUE;
        this.mStartValueIsSet = false;
        this.mRunning = false;
        this.mMaxValue = Float.MAX_VALUE;
        this.mMinValue = -this.mMaxValue;
        this.mLastFrameTime = 0L;
        this.mEndListeners = new java.util.ArrayList<>();
        this.mUpdateListeners = new java.util.ArrayList<>();
        this.mTarget = k;
        this.mProperty = floatProperty;
        if (this.mProperty == ROTATION || this.mProperty == ROTATION_X || this.mProperty == ROTATION_Y) {
            this.mMinVisibleChange = 0.1f;
            return;
        }
        if (this.mProperty == ALPHA) {
            this.mMinVisibleChange = 0.00390625f;
        } else if (this.mProperty == SCALE_X || this.mProperty == SCALE_Y) {
            this.mMinVisibleChange = 0.002f;
        } else {
            this.mMinVisibleChange = 1.0f;
        }
    }

    public T setStartValue(float f) {
        this.mValue = f;
        this.mStartValueIsSet = true;
        return this;
    }

    public T setStartVelocity(float f) {
        this.mVelocity = f;
        return this;
    }

    public T setMaxValue(float f) {
        this.mMaxValue = f;
        return this;
    }

    public T setMinValue(float f) {
        this.mMinValue = f;
        return this;
    }

    public T addEndListener(com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener onAnimationEndListener) {
        if (!this.mEndListeners.contains(onAnimationEndListener)) {
            this.mEndListeners.add(onAnimationEndListener);
        }
        return this;
    }

    public void removeEndListener(com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener onAnimationEndListener) {
        removeEntry(this.mEndListeners, onAnimationEndListener);
    }

    public T addUpdateListener(com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener) {
        if (isRunning()) {
            throw new java.lang.UnsupportedOperationException("Error: Update listeners must be added beforethe animation.");
        }
        if (!this.mUpdateListeners.contains(onAnimationUpdateListener)) {
            this.mUpdateListeners.add(onAnimationUpdateListener);
        }
        return this;
    }

    public void removeUpdateListener(com.android.internal.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener onAnimationUpdateListener) {
        removeEntry(this.mUpdateListeners, onAnimationUpdateListener);
    }

    public T setMinimumVisibleChange(float f) {
        if (f <= 0.0f) {
            throw new java.lang.IllegalArgumentException("Minimum visible change must be positive.");
        }
        this.mMinVisibleChange = f;
        setValueThreshold(f * 0.75f);
        return this;
    }

    public float getMinimumVisibleChange() {
        return this.mMinVisibleChange;
    }

    private static <T> void removeNullEntries(java.util.ArrayList<T> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (arrayList.get(size) == null) {
                arrayList.remove(size);
            }
        }
    }

    private static <T> void removeEntry(java.util.ArrayList<T> arrayList, T t) {
        int indexOf = arrayList.indexOf(t);
        if (indexOf >= 0) {
            arrayList.set(indexOf, null);
        }
    }

    public void start() {
        if (!isCurrentThread()) {
            throw new android.util.AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        if (!this.mRunning) {
            startAnimationInternal();
        }
    }

    boolean isCurrentThread() {
        return java.lang.Thread.currentThread() == android.os.Looper.myLooper().getThread();
    }

    public void cancel() {
        if (!isCurrentThread()) {
            throw new android.util.AndroidRuntimeException("Animations may only be canceled from the same thread as the animation handler");
        }
        if (this.mRunning) {
            endAnimationInternal(true);
        }
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    private void startAnimationInternal() {
        if (!this.mRunning) {
            this.mRunning = true;
            if (!this.mStartValueIsSet) {
                this.mValue = getPropertyValue();
            }
            if (this.mValue > this.mMaxValue || this.mValue < this.mMinValue) {
                throw new java.lang.IllegalArgumentException("Starting value need to be in between min value and max value");
            }
            getAnimationHandler().addAnimationFrameCallback(this, 0L);
        }
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public boolean doAnimationFrame(long j) {
        if (this.mLastFrameTime == 0) {
            this.mLastFrameTime = j;
            setPropertyValue(this.mValue);
            return false;
        }
        long j2 = j - this.mLastFrameTime;
        this.mLastFrameTime = j;
        float durationScale = android.animation.ValueAnimator.getDurationScale();
        boolean updateValueAndVelocity = updateValueAndVelocity(durationScale == 0.0f ? 2147483647L : (long) (j2 / durationScale));
        this.mValue = java.lang.Math.min(this.mValue, this.mMaxValue);
        this.mValue = java.lang.Math.max(this.mValue, this.mMinValue);
        setPropertyValue(this.mValue);
        if (updateValueAndVelocity) {
            endAnimationInternal(false);
        }
        return updateValueAndVelocity;
    }

    @Override // android.animation.AnimationHandler.AnimationFrameCallback
    public void commitAnimationFrame(long j) {
        doAnimationFrame(j);
    }

    private void endAnimationInternal(boolean z) {
        this.mRunning = false;
        getAnimationHandler().removeCallback(this);
        this.mLastFrameTime = 0L;
        this.mStartValueIsSet = false;
        for (int i = 0; i < this.mEndListeners.size(); i++) {
            if (this.mEndListeners.get(i) != null) {
                this.mEndListeners.get(i).onAnimationEnd(this, z, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mEndListeners);
    }

    void setPropertyValue(float f) {
        this.mProperty.setValue(this.mTarget, f);
        for (int i = 0; i < this.mUpdateListeners.size(); i++) {
            if (this.mUpdateListeners.get(i) != null) {
                this.mUpdateListeners.get(i).onAnimationUpdate(this, this.mValue, this.mVelocity);
            }
        }
        removeNullEntries(this.mUpdateListeners);
    }

    float getValueThreshold() {
        return this.mMinVisibleChange * 0.75f;
    }

    private float getPropertyValue() {
        return this.mProperty.get(this.mTarget).floatValue();
    }

    public android.animation.AnimationHandler getAnimationHandler() {
        return this.mAnimationHandler != null ? this.mAnimationHandler : android.animation.AnimationHandler.getInstance();
    }
}
