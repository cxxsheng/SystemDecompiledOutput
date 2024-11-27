package android.view.animation;

/* loaded from: classes4.dex */
public class AnimationSet extends android.view.animation.Animation {
    private static final int PROPERTY_CHANGE_BOUNDS_MASK = 128;
    private static final int PROPERTY_DURATION_MASK = 32;
    private static final int PROPERTY_FILL_AFTER_MASK = 1;
    private static final int PROPERTY_FILL_BEFORE_MASK = 2;
    private static final int PROPERTY_MORPH_MATRIX_MASK = 64;
    private static final int PROPERTY_REPEAT_MODE_MASK = 4;
    private static final int PROPERTY_SHARE_INTERPOLATOR_MASK = 16;
    private static final int PROPERTY_START_OFFSET_MASK = 8;
    private java.util.ArrayList<android.view.animation.Animation> mAnimations;
    private boolean mDirty;
    private int mFlags;
    private boolean mHasAlpha;
    private long mLastEnd;
    private long[] mStoredOffsets;
    private android.view.animation.Transformation mTempTransformation;

    public AnimationSet(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFlags = 0;
        this.mAnimations = new java.util.ArrayList<>();
        this.mTempTransformation = new android.view.animation.Transformation();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.AnimationSet);
        setFlag(16, obtainStyledAttributes.getBoolean(1, true));
        init();
        if (context.getApplicationInfo().targetSdkVersion >= 14) {
            if (obtainStyledAttributes.hasValue(0)) {
                this.mFlags |= 32;
            }
            if (obtainStyledAttributes.hasValue(2)) {
                this.mFlags = 2 | this.mFlags;
            }
            if (obtainStyledAttributes.hasValue(3)) {
                this.mFlags |= 1;
            }
            if (obtainStyledAttributes.hasValue(5)) {
                this.mFlags |= 4;
            }
            if (obtainStyledAttributes.hasValue(4)) {
                this.mFlags |= 8;
            }
        }
        obtainStyledAttributes.recycle();
    }

    public AnimationSet(boolean z) {
        this.mFlags = 0;
        this.mAnimations = new java.util.ArrayList<>();
        this.mTempTransformation = new android.view.animation.Transformation();
        setFlag(16, z);
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.animation.Animation
    /* renamed from: clone */
    public android.view.animation.AnimationSet mo5398clone() throws java.lang.CloneNotSupportedException {
        android.view.animation.AnimationSet animationSet = (android.view.animation.AnimationSet) super.mo5398clone();
        animationSet.mTempTransformation = new android.view.animation.Transformation();
        animationSet.mAnimations = new java.util.ArrayList<>();
        int size = this.mAnimations.size();
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        for (int i = 0; i < size; i++) {
            animationSet.mAnimations.add(arrayList.get(i).mo5398clone());
        }
        return animationSet;
    }

    private void setFlag(int i, boolean z) {
        if (z) {
            this.mFlags = i | this.mFlags;
        } else {
            this.mFlags = (~i) & this.mFlags;
        }
    }

    private void init() {
        this.mStartTime = 0L;
    }

    @Override // android.view.animation.Animation
    public void setFillAfter(boolean z) {
        this.mFlags |= 1;
        super.setFillAfter(z);
    }

    @Override // android.view.animation.Animation
    public void setFillBefore(boolean z) {
        this.mFlags |= 2;
        super.setFillBefore(z);
    }

    @Override // android.view.animation.Animation
    public void setRepeatMode(int i) {
        this.mFlags |= 4;
        super.setRepeatMode(i);
    }

    @Override // android.view.animation.Animation
    public void setStartOffset(long j) {
        this.mFlags |= 8;
        super.setStartOffset(j);
    }

    @Override // android.view.animation.Animation
    public boolean hasAlpha() {
        if (this.mDirty) {
            int i = 0;
            this.mHasAlpha = false;
            this.mDirty = false;
            int size = this.mAnimations.size();
            java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
            while (true) {
                if (i >= size) {
                    break;
                }
                if (!arrayList.get(i).hasAlpha()) {
                    i++;
                } else {
                    this.mHasAlpha = true;
                    break;
                }
            }
        }
        return this.mHasAlpha;
    }

    @Override // android.view.animation.Animation
    public void setDuration(long j) {
        this.mFlags |= 32;
        super.setDuration(j);
        this.mLastEnd = this.mStartOffset + this.mDuration;
    }

    public void addAnimation(android.view.animation.Animation animation) {
        this.mAnimations.add(animation);
        if (((this.mFlags & 64) == 0) && animation.willChangeTransformationMatrix()) {
            this.mFlags |= 64;
        }
        if (((this.mFlags & 128) == 0) && animation.willChangeBounds()) {
            this.mFlags |= 128;
        }
        if ((this.mFlags & 32) == 32) {
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else if (this.mAnimations.size() == 1) {
            this.mDuration = animation.getStartOffset() + animation.getDuration();
            this.mLastEnd = this.mStartOffset + this.mDuration;
        } else {
            this.mLastEnd = java.lang.Math.max(this.mLastEnd, this.mStartOffset + animation.getStartOffset() + animation.getDuration());
            this.mDuration = this.mLastEnd - this.mStartOffset;
        }
        this.mDirty = true;
    }

    @Override // android.view.animation.Animation
    public void setStartTime(long j) {
        super.setStartTime(j);
        int size = this.mAnimations.size();
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        for (int i = 0; i < size; i++) {
            arrayList.get(i).setStartTime(j);
        }
    }

    @Override // android.view.animation.Animation
    public long getStartTime() {
        int size = this.mAnimations.size();
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        long j = Long.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            j = java.lang.Math.min(j, arrayList.get(i).getStartTime());
        }
        return j;
    }

    @Override // android.view.animation.Animation
    public void restrictDuration(long j) {
        super.restrictDuration(j);
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).restrictDuration(j);
        }
    }

    @Override // android.view.animation.Animation
    public long getDuration() {
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        if ((this.mFlags & 32) == 32) {
            return this.mDuration;
        }
        long j = 0;
        for (int i = 0; i < size; i++) {
            j = java.lang.Math.max(j, arrayList.get(i).getDuration());
        }
        return j;
    }

    @Override // android.view.animation.Animation
    public long computeDurationHint() {
        int size = this.mAnimations.size();
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        long j = 0;
        for (int i = size - 1; i >= 0; i--) {
            long computeDurationHint = arrayList.get(i).computeDurationHint();
            if (computeDurationHint > j) {
                j = computeDurationHint;
            }
        }
        return j;
    }

    @Override // android.view.animation.Animation
    public void initializeInvalidateRegion(int i, int i2, int i3, int i4) {
        android.graphics.RectF rectF = this.mPreviousRegion;
        rectF.set(i, i2, i3, i4);
        rectF.inset(-1.0f, -1.0f);
        if (this.mFillBefore) {
            int size = this.mAnimations.size();
            java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
            android.view.animation.Transformation transformation = this.mTempTransformation;
            android.view.animation.Transformation transformation2 = this.mPreviousTransformation;
            for (int i5 = size - 1; i5 >= 0; i5--) {
                android.view.animation.Animation animation = arrayList.get(i5);
                if (!animation.isFillEnabled() || animation.getFillBefore() || animation.getStartOffset() == 0) {
                    transformation.clear();
                    android.view.animation.Interpolator interpolator = animation.mInterpolator;
                    animation.applyTransformation(interpolator != null ? interpolator.getInterpolation(0.0f) : 0.0f, transformation);
                    transformation2.compose(transformation);
                }
            }
        }
    }

    @Override // android.view.animation.Animation
    public void getTransformationAt(float f, android.view.animation.Transformation transformation) {
        android.view.animation.Transformation transformation2 = this.mTempTransformation;
        for (int size = this.mAnimations.size() - 1; size >= 0; size--) {
            android.view.animation.Animation animation = this.mAnimations.get(size);
            transformation2.clear();
            animation.getTransformationAt(f, transformation);
            transformation.compose(transformation2);
        }
    }

    @Override // android.view.animation.Animation
    public boolean getTransformation(long j, android.view.animation.Transformation transformation) {
        int size = this.mAnimations.size();
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        android.view.animation.Transformation transformation2 = this.mTempTransformation;
        transformation.clear();
        boolean z = true;
        boolean z2 = false;
        boolean z3 = false;
        for (int i = size - 1; i >= 0; i--) {
            android.view.animation.Animation animation = arrayList.get(i);
            transformation2.clear();
            z3 = animation.getTransformation(j, transformation2, getScaleFactor()) || z3;
            transformation.compose(transformation2);
            z2 = z2 || animation.hasStarted();
            z = animation.hasEnded() && z;
        }
        if (z2 && !this.mStarted) {
            dispatchAnimationStart();
            this.mStarted = true;
        }
        if (z != this.mEnded) {
            dispatchAnimationEnd();
            this.mEnded = z;
        }
        return z3;
    }

    @Override // android.view.animation.Animation
    public void scaleCurrentDuration(float f) {
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).scaleCurrentDuration(f);
        }
    }

    @Override // android.view.animation.Animation
    public void initialize(int i, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        super.initialize(i, i2, i3, i4);
        boolean z3 = (this.mFlags & 32) == 32;
        boolean z4 = (this.mFlags & 1) == 1;
        boolean z5 = (this.mFlags & 2) == 2;
        boolean z6 = (this.mFlags & 4) == 4;
        boolean z7 = (this.mFlags & 16) == 16;
        boolean z8 = (this.mFlags & 8) == 8;
        if (z7) {
            ensureInterpolator();
        }
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        long j = this.mDuration;
        boolean z9 = this.mFillAfter;
        boolean z10 = this.mFillBefore;
        int i5 = this.mRepeatMode;
        android.view.animation.Interpolator interpolator = this.mInterpolator;
        boolean z11 = z8;
        long j2 = this.mStartOffset;
        long[] jArr = this.mStoredOffsets;
        if (z11) {
            if (jArr == null || jArr.length != size) {
                jArr = new long[size];
                this.mStoredOffsets = jArr;
            }
        } else if (jArr != null) {
            jArr = null;
            this.mStoredOffsets = null;
        }
        int i6 = 0;
        while (i6 < size) {
            android.view.animation.Animation animation = arrayList.get(i6);
            if (z3) {
                animation.setDuration(j);
            }
            if (z4) {
                animation.setFillAfter(z9);
            }
            if (z5) {
                animation.setFillBefore(z10);
            }
            if (z6) {
                animation.setRepeatMode(i5);
            }
            if (z7) {
                animation.setInterpolator(interpolator);
            }
            if (!z11) {
                z = z3;
                z2 = z4;
            } else {
                long startOffset = animation.getStartOffset();
                z = z3;
                z2 = z4;
                animation.setStartOffset(startOffset + j2);
                jArr[i6] = startOffset;
            }
            animation.initialize(i, i2, i3, i4);
            i6++;
            jArr = jArr;
            z3 = z;
            z5 = z5;
            z4 = z2;
        }
    }

    @Override // android.view.animation.Animation
    public void reset() {
        super.reset();
        restoreChildrenStartOffset();
    }

    void restoreChildrenStartOffset() {
        long[] jArr = this.mStoredOffsets;
        if (jArr == null) {
            return;
        }
        java.util.ArrayList<android.view.animation.Animation> arrayList = this.mAnimations;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).setStartOffset(jArr[i]);
        }
    }

    public java.util.List<android.view.animation.Animation> getAnimations() {
        return this.mAnimations;
    }

    @Override // android.view.animation.Animation
    public boolean willChangeTransformationMatrix() {
        return (this.mFlags & 64) == 64;
    }

    @Override // android.view.animation.Animation
    public boolean willChangeBounds() {
        return (this.mFlags & 128) == 128;
    }

    @Override // android.view.animation.Animation
    public boolean hasExtension() {
        java.util.Iterator<android.view.animation.Animation> it = this.mAnimations.iterator();
        while (it.hasNext()) {
            if (it.next().hasExtension()) {
                return true;
            }
        }
        return false;
    }
}
