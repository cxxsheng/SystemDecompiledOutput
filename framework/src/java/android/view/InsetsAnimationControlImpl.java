package android.view;

/* loaded from: classes4.dex */
public class InsetsAnimationControlImpl implements android.view.InternalInsetsAnimationController, android.view.InsetsAnimationControlRunner {
    private static final java.lang.String TAG = "InsetsAnimationCtrlImpl";
    private final android.view.WindowInsetsAnimation mAnimation;
    private final int mAnimationType;
    private boolean mCancelled;
    private final android.view.InsetsAnimationControlCallbacks mController;
    private int mControllingTypes;
    private final android.util.SparseArray<android.view.InsetsSourceControl> mControls;
    private android.graphics.Insets mCurrentInsets;
    private boolean mFinished;
    private final boolean mHasZeroInsetsIme;
    private final android.graphics.Insets mHiddenInsets;
    private final android.view.InsetsState mInitialInsetsState;
    private final int mLayoutInsetsDuringAnimation;
    private final android.view.WindowInsetsAnimationControlListener mListener;
    private float mPendingFraction;
    private android.graphics.Insets mPendingInsets;
    private java.lang.Boolean mPerceptible;
    private boolean mReadyDispatched;
    private final android.graphics.Insets mShownInsets;
    private boolean mShownOnFinish;
    private final android.view.inputmethod.ImeTracker.Token mStatsToken;
    private final android.content.res.CompatibilityInfo.Translator mTranslator;
    private final int mTypes;
    private final android.graphics.Rect mTmpFrame = new android.graphics.Rect();
    private final android.util.SparseSetArray<android.view.InsetsSourceControl> mSideControlsMap = new android.util.SparseSetArray<>();
    private final android.graphics.Matrix mTmpMatrix = new android.graphics.Matrix();
    private float mCurrentAlpha = 1.0f;
    private float mPendingAlpha = 1.0f;

    public InsetsAnimationControlImpl(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray, android.graphics.Rect rect, android.view.InsetsState insetsState, android.view.WindowInsetsAnimationControlListener windowInsetsAnimationControlListener, int i, android.view.InsetsAnimationControlCallbacks insetsAnimationControlCallbacks, long j, android.view.animation.Interpolator interpolator, int i2, int i3, android.content.res.CompatibilityInfo.Translator translator, android.view.inputmethod.ImeTracker.Token token) {
        this.mControls = sparseArray;
        this.mListener = windowInsetsAnimationControlListener;
        this.mTypes = i;
        this.mControllingTypes = i;
        this.mController = insetsAnimationControlCallbacks;
        this.mInitialInsetsState = new android.view.InsetsState(insetsState, true);
        if (rect == null) {
            this.mCurrentInsets = calculateInsets(this.mInitialInsetsState, sparseArray, true);
            this.mHiddenInsets = calculateInsets(null, sparseArray, false);
            this.mShownInsets = calculateInsets(null, sparseArray, true);
            this.mHasZeroInsetsIme = this.mShownInsets.bottom == 0 && controlsType(android.view.WindowInsets.Type.ime());
            buildSideControlsMap(this.mSideControlsMap, sparseArray);
        } else {
            android.util.SparseIntArray sparseIntArray = new android.util.SparseIntArray();
            this.mCurrentInsets = getInsetsFromState(this.mInitialInsetsState, rect, null);
            this.mHiddenInsets = calculateInsets(this.mInitialInsetsState, rect, sparseArray, false, null);
            this.mShownInsets = calculateInsets(this.mInitialInsetsState, rect, sparseArray, true, sparseIntArray);
            this.mHasZeroInsetsIme = this.mShownInsets.bottom == 0 && controlsType(android.view.WindowInsets.Type.ime());
            if (this.mHasZeroInsetsIme) {
                sparseIntArray.put(android.view.InsetsSource.ID_IME, 4);
            }
            buildSideControlsMap(sparseIntArray, this.mSideControlsMap, sparseArray);
        }
        this.mPendingInsets = this.mCurrentInsets;
        this.mAnimation = new android.view.WindowInsetsAnimation(this.mTypes, interpolator, j);
        this.mAnimation.setAlpha(getCurrentAlpha());
        this.mAnimationType = i2;
        this.mLayoutInsetsDuringAnimation = i3;
        this.mTranslator = translator;
        this.mStatsToken = token;
        if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY && (android.view.WindowInsets.Type.ime() & i) != 0) {
            android.util.EventLog.writeEvent(android.view.EventLogTags.IMF_IME_ANIM_START, this.mStatsToken != null ? this.mStatsToken.getTag() : android.view.inputmethod.ImeTracker.TOKEN_NONE, java.lang.Integer.valueOf(this.mAnimationType), java.lang.Float.valueOf(this.mCurrentAlpha), "Current:" + this.mCurrentInsets, "Shown:" + this.mShownInsets, "Hidden:" + this.mHiddenInsets);
        }
        this.mController.startAnimation(this, windowInsetsAnimationControlListener, i, this.mAnimation, new android.view.WindowInsetsAnimation.Bounds(this.mHiddenInsets, this.mShownInsets));
    }

    private boolean calculatePerceptible(android.graphics.Insets insets, float f) {
        return insets.left * 100 >= (this.mShownInsets.left - this.mHiddenInsets.left) * 5 && insets.top * 100 >= (this.mShownInsets.top - this.mHiddenInsets.top) * 5 && insets.right * 100 >= (this.mShownInsets.right - this.mHiddenInsets.right) * 5 && insets.bottom * 100 >= (this.mShownInsets.bottom - this.mHiddenInsets.bottom) * 5 && f >= 0.5f;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean hasZeroInsetsIme() {
        return this.mHasZeroInsetsIme;
    }

    @Override // android.view.InternalInsetsAnimationController
    public void setReadyDispatched(boolean z) {
        this.mReadyDispatched = z;
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getHiddenStateInsets() {
        return this.mHiddenInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getShownStateInsets() {
        return this.mShownInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public android.graphics.Insets getCurrentInsets() {
        return this.mCurrentInsets;
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentAlpha() {
        return this.mCurrentAlpha;
    }

    @Override // android.view.WindowInsetsAnimationController, android.view.InsetsAnimationControlRunner
    public int getTypes() {
        return this.mTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getControllingTypes() {
        return this.mControllingTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void notifyControlRevoked(int i) {
        this.mControllingTypes = (~i) & this.mControllingTypes;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void updateSurfacePosition(android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = sparseArray.valueAt(size);
            android.view.InsetsSourceControl insetsSourceControl = this.mControls.get(valueAt.getId());
            if (insetsSourceControl != null) {
                android.graphics.Point surfacePosition = valueAt.getSurfacePosition();
                insetsSourceControl.setSurfacePosition(surfacePosition.x, surfacePosition.y);
            }
        }
    }

    @Override // android.view.InsetsAnimationControlRunner
    public int getAnimationType() {
        return this.mAnimationType;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.inputmethod.ImeTracker.Token getStatsToken() {
        return this.mStatsToken;
    }

    @Override // android.view.WindowInsetsAnimationController
    public void setInsetsAndAlpha(android.graphics.Insets insets, float f, float f2) {
        setInsetsAndAlpha(insets, f, f2, false);
    }

    private void setInsetsAndAlpha(android.graphics.Insets insets, float f, float f2, boolean z) {
        if (!z && this.mFinished) {
            throw new java.lang.IllegalStateException("Can't change insets on an animation that is finished.");
        }
        if (this.mCancelled) {
            throw new java.lang.IllegalStateException("Can't change insets on an animation that is cancelled.");
        }
        this.mPendingFraction = sanitize(f2);
        this.mPendingInsets = sanitize(insets);
        this.mPendingAlpha = sanitize(f);
        this.mController.scheduleApplyChangeInsets(this);
        boolean calculatePerceptible = calculatePerceptible(this.mPendingInsets, this.mPendingAlpha);
        if (this.mPerceptible == null || calculatePerceptible != this.mPerceptible.booleanValue()) {
            this.mController.reportPerceptible(this.mTypes, calculatePerceptible);
            this.mPerceptible = java.lang.Boolean.valueOf(calculatePerceptible);
        }
    }

    @Override // android.view.InternalInsetsAnimationController
    public boolean applyChangeInsets(android.view.InsetsState insetsState) {
        if (this.mCancelled) {
            return false;
        }
        android.graphics.Insets subtract = android.graphics.Insets.subtract(this.mShownInsets, this.mPendingInsets);
        java.util.ArrayList<android.view.SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList = new java.util.ArrayList<>();
        updateLeashesForSide(1, subtract.left, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(2, subtract.top, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(3, subtract.right, arrayList, insetsState, this.mPendingAlpha);
        updateLeashesForSide(4, subtract.bottom, arrayList, insetsState, this.mPendingAlpha);
        this.mController.applySurfaceParams((android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[]) arrayList.toArray(new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams[arrayList.size()]));
        this.mCurrentInsets = this.mPendingInsets;
        this.mAnimation.setFraction(this.mPendingFraction);
        this.mCurrentAlpha = this.mPendingAlpha;
        this.mAnimation.setAlpha(this.mPendingAlpha);
        if (this.mFinished) {
            this.mController.notifyFinished(this, this.mShownOnFinish);
            releaseLeashes();
        }
        return this.mFinished;
    }

    private void releaseLeashes() {
        for (int size = this.mControls.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = this.mControls.valueAt(size);
            if (valueAt != null) {
                final android.view.InsetsAnimationControlCallbacks insetsAnimationControlCallbacks = this.mController;
                java.util.Objects.requireNonNull(insetsAnimationControlCallbacks);
                valueAt.release(new java.util.function.Consumer() { // from class: android.view.InsetsAnimationControlImpl$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.view.InsetsAnimationControlCallbacks.this.releaseSurfaceControlFromRt((android.view.SurfaceControl) obj);
                    }
                });
            }
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public void finish(boolean z) {
        if (this.mCancelled || this.mFinished) {
            return;
        }
        this.mShownOnFinish = z;
        this.mFinished = true;
        android.graphics.Insets insets = z ? this.mShownInsets : this.mHiddenInsets;
        setInsetsAndAlpha(insets, this.mPendingAlpha, 1.0f, true);
        this.mListener.onFinished(this);
        if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY && (this.mTypes & android.view.WindowInsets.Type.ime()) != 0) {
            android.util.EventLog.writeEvent(android.view.EventLogTags.IMF_IME_ANIM_FINISH, this.mStatsToken != null ? this.mStatsToken.getTag() : android.view.inputmethod.ImeTracker.TOKEN_NONE, java.lang.Integer.valueOf(this.mAnimationType), java.lang.Float.valueOf(this.mCurrentAlpha), java.lang.Integer.valueOf(z ? 1 : 0), java.util.Objects.toString(insets));
        }
    }

    @Override // android.view.WindowInsetsAnimationController
    public float getCurrentFraction() {
        return this.mAnimation.getFraction();
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void cancel() {
        if (this.mFinished) {
            return;
        }
        this.mPendingInsets = this.mLayoutInsetsDuringAnimation == 0 ? this.mShownInsets : this.mHiddenInsets;
        this.mPendingAlpha = 1.0f;
        applyChangeInsets(null);
        this.mCancelled = true;
        this.mListener.onCancelled(this.mReadyDispatched ? this : null);
        if (android.view.inputmethod.ImeTracker.DEBUG_IME_VISIBILITY && (this.mTypes & android.view.WindowInsets.Type.ime()) != 0) {
            android.util.EventLog.writeEvent(android.view.EventLogTags.IMF_IME_ANIM_CANCEL, this.mStatsToken != null ? this.mStatsToken.getTag() : android.view.inputmethod.ImeTracker.TOKEN_NONE, java.lang.Integer.valueOf(this.mAnimationType), java.util.Objects.toString(this.mPendingInsets));
        }
        releaseLeashes();
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isFinished() {
        return this.mFinished;
    }

    @Override // android.view.WindowInsetsAnimationController
    public boolean isCancelled() {
        return this.mCancelled;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public android.view.WindowInsetsAnimation getAnimation() {
        return this.mAnimation;
    }

    @Override // android.view.InsetsAnimationControlRunner
    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1133871366145L, this.mCancelled);
        protoOutputStream.write(1133871366146L, this.mFinished);
        protoOutputStream.write(1138166333443L, java.util.Objects.toString(this.mTmpMatrix));
        protoOutputStream.write(1138166333444L, java.util.Objects.toString(this.mPendingInsets));
        protoOutputStream.write(1108101562373L, this.mPendingFraction);
        protoOutputStream.write(1133871366150L, this.mShownOnFinish);
        protoOutputStream.write(1108101562375L, this.mCurrentAlpha);
        protoOutputStream.write(1108101562376L, this.mPendingAlpha);
        protoOutputStream.end(start);
    }

    android.util.SparseArray<android.view.InsetsSourceControl> getControls() {
        return this.mControls;
    }

    private android.graphics.Insets getInsetsFromState(android.view.InsetsState insetsState, android.graphics.Rect rect, android.util.SparseIntArray sparseIntArray) {
        return insetsState.calculateInsets(rect, null, false, 16, 0, 0, 2, 0, sparseIntArray).getInsets(this.mTypes);
    }

    private android.graphics.Insets calculateInsets(android.view.InsetsState insetsState, android.graphics.Rect rect, android.util.SparseArray<android.view.InsetsSourceControl> sparseArray, boolean z, android.util.SparseIntArray sparseIntArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null) {
                insetsState.setSourceVisible(valueAt.getId(), z);
            }
        }
        return getInsetsFromState(insetsState, rect, sparseIntArray);
    }

    private android.graphics.Insets calculateInsets(android.view.InsetsState insetsState, android.util.SparseArray<android.view.InsetsSourceControl> sparseArray, boolean z) {
        android.graphics.Insets insets = android.graphics.Insets.NONE;
        if (!z) {
            return insets;
        }
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null && (insetsState == null || insetsState.isSourceOrDefaultVisible(valueAt.getId(), valueAt.getType()))) {
                insets = android.graphics.Insets.max(insets, valueAt.getInsetsHint());
            }
        }
        return insets;
    }

    private android.graphics.Insets sanitize(android.graphics.Insets insets) {
        if (insets == null) {
            insets = getCurrentInsets();
        }
        if (hasZeroInsetsIme()) {
            return insets;
        }
        return android.graphics.Insets.max(android.graphics.Insets.min(insets, this.mShownInsets), this.mHiddenInsets);
    }

    private static float sanitize(float f) {
        float f2 = 1.0f;
        if (f < 1.0f) {
            f2 = 0.0f;
            if (f > 0.0f) {
                return f;
            }
        }
        return f2;
    }

    private void updateLeashesForSide(int i, int i2, java.util.ArrayList<android.view.SyncRtSurfaceTransactionApplier.SurfaceParams> arrayList, android.view.InsetsState insetsState, float f) {
        android.util.ArraySet<android.view.InsetsSourceControl> arraySet = this.mSideControlsMap.get(i);
        if (arraySet == null) {
            return;
        }
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = arraySet.valueAt(size);
            android.view.InsetsSource peekSource = this.mInitialInsetsState.peekSource(valueAt.getId());
            android.view.SurfaceControl leash = valueAt.getLeash();
            this.mTmpMatrix.setTranslate(valueAt.getSurfacePosition().x, valueAt.getSurfacePosition().y);
            if (peekSource != null) {
                this.mTmpFrame.set(peekSource.getFrame());
            }
            addTranslationToMatrix(i, i2, this.mTmpMatrix, this.mTmpFrame);
            boolean z = false;
            if (this.mPendingFraction == 0.0f) {
                if (this.mAnimationType != 0) {
                    z = true;
                }
            } else if (!this.mFinished || this.mShownOnFinish) {
                z = true;
            }
            if (insetsState != null && peekSource != null) {
                insetsState.addSource(new android.view.InsetsSource(peekSource).setVisible(z).setFrame(this.mTmpFrame));
            }
            if (leash != null) {
                arrayList.add(new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(leash).withAlpha(f).withMatrix(this.mTmpMatrix).withVisibility(z).build());
            }
        }
    }

    private void addTranslationToMatrix(int i, int i2, android.graphics.Matrix matrix, android.graphics.Rect rect) {
        float translateLengthInAppWindowToScreen = this.mTranslator != null ? this.mTranslator.translateLengthInAppWindowToScreen(i2) : i2;
        switch (i) {
            case 1:
                matrix.postTranslate(-translateLengthInAppWindowToScreen, 0.0f);
                rect.offset(-i2, 0);
                break;
            case 2:
                matrix.postTranslate(0.0f, -translateLengthInAppWindowToScreen);
                rect.offset(0, -i2);
                break;
            case 3:
                matrix.postTranslate(translateLengthInAppWindowToScreen, 0.0f);
                rect.offset(i2, 0);
                break;
            case 4:
                matrix.postTranslate(0.0f, translateLengthInAppWindowToScreen);
                rect.offset(0, i2);
                break;
        }
    }

    private static void buildSideControlsMap(android.util.SparseIntArray sparseIntArray, android.util.SparseSetArray<android.view.InsetsSourceControl> sparseSetArray, android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
        for (int size = sparseIntArray.size() - 1; size >= 0; size--) {
            int keyAt = sparseIntArray.keyAt(size);
            int valueAt = sparseIntArray.valueAt(size);
            android.view.InsetsSourceControl insetsSourceControl = sparseArray.get(keyAt);
            if (insetsSourceControl != null) {
                sparseSetArray.add(valueAt, insetsSourceControl);
            }
        }
    }

    private static void buildSideControlsMap(android.util.SparseSetArray<android.view.InsetsSourceControl> sparseSetArray, android.util.SparseArray<android.view.InsetsSourceControl> sparseArray) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            android.view.InsetsSourceControl valueAt = sparseArray.valueAt(size);
            if (valueAt != null) {
                int insetSide = android.view.InsetsSource.getInsetSide(valueAt.getInsetsHint());
                if (insetSide == 0 && valueAt.getType() == android.view.WindowInsets.Type.ime()) {
                    insetSide = 4;
                }
                sparseSetArray.add(insetSide, valueAt);
            }
        }
    }
}
