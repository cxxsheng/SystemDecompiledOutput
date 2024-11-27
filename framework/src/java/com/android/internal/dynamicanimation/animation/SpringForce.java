package com.android.internal.dynamicanimation.animation;

/* loaded from: classes4.dex */
public final class SpringForce implements com.android.internal.dynamicanimation.animation.Force {
    public static final float DAMPING_RATIO_HIGH_BOUNCY = 0.2f;
    public static final float DAMPING_RATIO_LOW_BOUNCY = 0.75f;
    public static final float DAMPING_RATIO_MEDIUM_BOUNCY = 0.5f;
    public static final float DAMPING_RATIO_NO_BOUNCY = 1.0f;
    public static final float STIFFNESS_HIGH = 10000.0f;
    public static final float STIFFNESS_LOW = 200.0f;
    public static final float STIFFNESS_MEDIUM = 1500.0f;
    public static final float STIFFNESS_VERY_LOW = 50.0f;
    private static final double UNSET = Double.MAX_VALUE;
    private static final double VELOCITY_THRESHOLD_MULTIPLIER = 62.5d;
    private double mDampedFreq;
    double mDampingRatio;
    private double mFinalPosition;
    private double mGammaMinus;
    private double mGammaPlus;
    private boolean mInitialized;
    private final com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState mMassState;
    double mNaturalFreq;
    private double mValueThreshold;
    private double mVelocityThreshold;

    public SpringForce() {
        this.mNaturalFreq = java.lang.Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = UNSET;
        this.mMassState = new com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState();
    }

    public SpringForce(float f) {
        this.mNaturalFreq = java.lang.Math.sqrt(1500.0d);
        this.mDampingRatio = 0.5d;
        this.mInitialized = false;
        this.mFinalPosition = UNSET;
        this.mMassState = new com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState();
        this.mFinalPosition = f;
    }

    public com.android.internal.dynamicanimation.animation.SpringForce setStiffness(float f) {
        if (f <= 0.0f) {
            throw new java.lang.IllegalArgumentException("Spring stiffness constant must be positive.");
        }
        this.mNaturalFreq = java.lang.Math.sqrt(f);
        this.mInitialized = false;
        return this;
    }

    public float getStiffness() {
        return (float) (this.mNaturalFreq * this.mNaturalFreq);
    }

    public com.android.internal.dynamicanimation.animation.SpringForce setDampingRatio(float f) {
        if (f < 0.0f) {
            throw new java.lang.IllegalArgumentException("Damping ratio must be non-negative");
        }
        this.mDampingRatio = f;
        this.mInitialized = false;
        return this;
    }

    public float getDampingRatio() {
        return (float) this.mDampingRatio;
    }

    public com.android.internal.dynamicanimation.animation.SpringForce setFinalPosition(float f) {
        this.mFinalPosition = f;
        return this;
    }

    public float getFinalPosition() {
        return (float) this.mFinalPosition;
    }

    @Override // com.android.internal.dynamicanimation.animation.Force
    public float getAcceleration(float f, float f2) {
        return (float) (((-(this.mNaturalFreq * this.mNaturalFreq)) * (f - getFinalPosition())) - (((this.mNaturalFreq * 2.0d) * this.mDampingRatio) * f2));
    }

    @Override // com.android.internal.dynamicanimation.animation.Force
    public boolean isAtEquilibrium(float f, float f2) {
        if (java.lang.Math.abs(f2) < this.mVelocityThreshold && java.lang.Math.abs(f - getFinalPosition()) < this.mValueThreshold) {
            return true;
        }
        return false;
    }

    private void init() {
        if (this.mInitialized) {
            return;
        }
        if (this.mFinalPosition == UNSET) {
            throw new java.lang.IllegalStateException("Error: Final position of the spring must be set before the animation starts");
        }
        if (this.mDampingRatio > 1.0d) {
            this.mGammaPlus = ((-this.mDampingRatio) * this.mNaturalFreq) + (this.mNaturalFreq * java.lang.Math.sqrt((this.mDampingRatio * this.mDampingRatio) - 1.0d));
            this.mGammaMinus = ((-this.mDampingRatio) * this.mNaturalFreq) - (this.mNaturalFreq * java.lang.Math.sqrt((this.mDampingRatio * this.mDampingRatio) - 1.0d));
        } else if (this.mDampingRatio >= 0.0d && this.mDampingRatio < 1.0d) {
            this.mDampedFreq = this.mNaturalFreq * java.lang.Math.sqrt(1.0d - (this.mDampingRatio * this.mDampingRatio));
        }
        this.mInitialized = true;
    }

    com.android.internal.dynamicanimation.animation.DynamicAnimation.MassState updateValues(double d, double d2, long j) {
        double d3;
        double d4;
        init();
        double d5 = j / 1000.0d;
        double d6 = d - this.mFinalPosition;
        if (this.mDampingRatio > 1.0d) {
            double d7 = d6 - (((this.mGammaMinus * d6) - d2) / (this.mGammaMinus - this.mGammaPlus));
            double d8 = ((this.mGammaMinus * d6) - d2) / (this.mGammaMinus - this.mGammaPlus);
            d3 = (java.lang.Math.pow(2.718281828459045d, this.mGammaMinus * d5) * d7) + (java.lang.Math.pow(2.718281828459045d, this.mGammaPlus * d5) * d8);
            d4 = (d7 * this.mGammaMinus * java.lang.Math.pow(2.718281828459045d, this.mGammaMinus * d5)) + (d8 * this.mGammaPlus * java.lang.Math.pow(2.718281828459045d, this.mGammaPlus * d5));
        } else if (this.mDampingRatio != 1.0d) {
            double d9 = (1.0d / this.mDampedFreq) * ((this.mDampingRatio * this.mNaturalFreq * d6) + d2);
            double pow = java.lang.Math.pow(2.718281828459045d, (-this.mDampingRatio) * this.mNaturalFreq * d5) * ((java.lang.Math.cos(this.mDampedFreq * d5) * d6) + (java.lang.Math.sin(this.mDampedFreq * d5) * d9));
            double pow2 = ((-this.mNaturalFreq) * pow * this.mDampingRatio) + (java.lang.Math.pow(2.718281828459045d, (-this.mDampingRatio) * this.mNaturalFreq * d5) * (((-this.mDampedFreq) * d6 * java.lang.Math.sin(this.mDampedFreq * d5)) + (this.mDampedFreq * d9 * java.lang.Math.cos(this.mDampedFreq * d5))));
            d3 = pow;
            d4 = pow2;
        } else {
            double d10 = d2 + (this.mNaturalFreq * d6);
            double d11 = d6 + (d10 * d5);
            double pow3 = java.lang.Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d5) * d11;
            d4 = (d10 * java.lang.Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d5)) + (d11 * java.lang.Math.pow(2.718281828459045d, (-this.mNaturalFreq) * d5) * (-this.mNaturalFreq));
            d3 = pow3;
        }
        this.mMassState.mValue = (float) (d3 + this.mFinalPosition);
        this.mMassState.mVelocity = (float) d4;
        return this.mMassState;
    }

    void setValueThreshold(double d) {
        this.mValueThreshold = java.lang.Math.abs(d);
        this.mVelocityThreshold = this.mValueThreshold * VELOCITY_THRESHOLD_MULTIPLIER;
    }
}
