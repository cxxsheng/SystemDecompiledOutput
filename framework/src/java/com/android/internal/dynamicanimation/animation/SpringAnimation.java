package com.android.internal.dynamicanimation.animation;

/* loaded from: classes4.dex */
public final class SpringAnimation extends com.android.internal.dynamicanimation.animation.DynamicAnimation<com.android.internal.dynamicanimation.animation.SpringAnimation> {
    private static final float UNSET = Float.MAX_VALUE;
    private boolean mEndRequested;
    private float mPendingPosition;
    private com.android.internal.dynamicanimation.animation.SpringForce mSpring;

    public SpringAnimation(com.android.internal.dynamicanimation.animation.FloatValueHolder floatValueHolder) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public SpringAnimation(com.android.internal.dynamicanimation.animation.FloatValueHolder floatValueHolder, float f) {
        super(floatValueHolder);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new com.android.internal.dynamicanimation.animation.SpringForce(f);
    }

    public <K> SpringAnimation(K k, android.util.FloatProperty<K> floatProperty) {
        super(k, floatProperty);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
    }

    public <K> SpringAnimation(K k, android.util.FloatProperty<K> floatProperty, float f) {
        super(k, floatProperty);
        this.mSpring = null;
        this.mPendingPosition = Float.MAX_VALUE;
        this.mEndRequested = false;
        this.mSpring = new com.android.internal.dynamicanimation.animation.SpringForce(f);
    }

    public com.android.internal.dynamicanimation.animation.SpringForce getSpring() {
        return this.mSpring;
    }

    public com.android.internal.dynamicanimation.animation.SpringAnimation setSpring(com.android.internal.dynamicanimation.animation.SpringForce springForce) {
        this.mSpring = springForce;
        return this;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    public void start() {
        sanityCheck();
        this.mSpring.setValueThreshold(getValueThreshold());
        super.start();
    }

    public void animateToFinalPosition(float f) {
        if (isRunning()) {
            this.mPendingPosition = f;
            return;
        }
        if (this.mSpring == null) {
            this.mSpring = new com.android.internal.dynamicanimation.animation.SpringForce(f);
        }
        this.mSpring.setFinalPosition(f);
        start();
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    public void cancel() {
        super.cancel();
        if (this.mPendingPosition != Float.MAX_VALUE) {
            if (this.mSpring == null) {
                this.mSpring = new com.android.internal.dynamicanimation.animation.SpringForce(this.mPendingPosition);
            } else {
                this.mSpring.setFinalPosition(this.mPendingPosition);
            }
            this.mPendingPosition = Float.MAX_VALUE;
        }
    }

    public void skipToEnd() {
        if (!canSkipToEnd()) {
            throw new java.lang.UnsupportedOperationException("Spring animations can only come to an end when there is damping");
        }
        if (!isCurrentThread()) {
            throw new android.util.AndroidRuntimeException("Animations may only be started on the same thread as the animation handler");
        }
        if (this.mRunning) {
            this.mEndRequested = true;
        }
    }

    public boolean canSkipToEnd() {
        return this.mSpring.mDampingRatio > 0.0d;
    }

    private void sanityCheck() {
        if (this.mSpring == null) {
            throw new java.lang.UnsupportedOperationException("Incomplete SpringAnimation: Either final position or a spring force needs to be set.");
        }
        double finalPosition = this.mSpring.getFinalPosition();
        if (finalPosition > this.mMaxValue) {
            throw new java.lang.UnsupportedOperationException("Final position of the spring cannot be greater than the max value.");
        }
        if (finalPosition < this.mMinValue) {
            throw new java.lang.UnsupportedOperationException("Final position of the spring cannot be less than the min value.");
        }
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    boolean updateValueAndVelocity(long j) {
        if (this.mEndRequested) {
            if (this.mPendingPosition != Float.MAX_VALUE) {
                this.mSpring.setFinalPosition(this.mPendingPosition);
                this.mPendingPosition = Float.MAX_VALUE;
            }
            this.mValue = this.mSpring.getFinalPosition();
            this.mVelocity = 0.0f;
            this.mEndRequested = false;
            return true;
        }
        if (this.mPendingPosition != Float.MAX_VALUE) {
            long j2 = j / 2;
            com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState updateValues = this.mSpring.updateValues(this.mValue, this.mVelocity, j2);
            this.mSpring.setFinalPosition(this.mPendingPosition);
            this.mPendingPosition = Float.MAX_VALUE;
            com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState updateValues2 = this.mSpring.updateValues(updateValues.mValue, updateValues.mVelocity, j2);
            this.mValue = updateValues2.mValue;
            this.mVelocity = updateValues2.mVelocity;
        } else {
            com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState updateValues3 = this.mSpring.updateValues(this.mValue, this.mVelocity, j);
            this.mValue = updateValues3.mValue;
            this.mVelocity = updateValues3.mVelocity;
        }
        this.mValue = java.lang.Math.max(this.mValue, this.mMinValue);
        this.mValue = java.lang.Math.min(this.mValue, this.mMaxValue);
        if (!isAtEquilibrium(this.mValue, this.mVelocity)) {
            return false;
        }
        this.mValue = this.mSpring.getFinalPosition();
        this.mVelocity = 0.0f;
        return true;
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    float getAcceleration(float f, float f2) {
        return this.mSpring.getAcceleration(f, f2);
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    boolean isAtEquilibrium(float f, float f2) {
        return this.mSpring.isAtEquilibrium(f, f2);
    }

    @Override // com.android.internal.dynamicanimation.animation.DynamicAnimation
    void setValueThreshold(float f) {
    }
}
