package android.transition;

/* loaded from: classes3.dex */
public class ChangeTransform extends android.transition.Transition {
    private static final java.lang.String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
    private static final java.lang.String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
    private static final java.lang.String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final java.lang.String TAG = "ChangeTransform";
    private boolean mReparent;
    private android.graphics.Matrix mTempMatrix;
    private boolean mUseOverlay;
    private static final java.lang.String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final java.lang.String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final java.lang.String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_MATRIX, PROPNAME_TRANSFORMS, PROPNAME_PARENT_MATRIX};
    private static final android.util.Property<android.transition.ChangeTransform.PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY = new android.util.Property<android.transition.ChangeTransform.PathAnimatorMatrix, float[]>(float[].class, "nonTranslations") { // from class: android.transition.ChangeTransform.1
        @Override // android.util.Property
        public float[] get(android.transition.ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix) {
            return null;
        }

        @Override // android.util.Property
        public void set(android.transition.ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix, float[] fArr) {
            pathAnimatorMatrix.setValues(fArr);
        }
    };
    private static final android.util.Property<android.transition.ChangeTransform.PathAnimatorMatrix, android.graphics.PointF> TRANSLATIONS_PROPERTY = new android.util.Property<android.transition.ChangeTransform.PathAnimatorMatrix, android.graphics.PointF>(android.graphics.PointF.class, "translations") { // from class: android.transition.ChangeTransform.2
        @Override // android.util.Property
        public android.graphics.PointF get(android.transition.ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix) {
            return null;
        }

        @Override // android.util.Property
        public void set(android.transition.ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix, android.graphics.PointF pointF) {
            pathAnimatorMatrix.setTranslation(pointF);
        }
    };

    public ChangeTransform() {
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new android.graphics.Matrix();
    }

    public ChangeTransform(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mUseOverlay = true;
        this.mReparent = true;
        this.mTempMatrix = new android.graphics.Matrix();
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ChangeTransform);
        this.mUseOverlay = obtainStyledAttributes.getBoolean(1, true);
        this.mReparent = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
    }

    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    public void setReparentWithOverlay(boolean z) {
        this.mUseOverlay = z;
    }

    public boolean getReparent() {
        return this.mReparent;
    }

    public void setReparent(boolean z) {
        this.mReparent = z;
    }

    @Override // android.transition.Transition
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.graphics.Matrix matrix;
        android.view.View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put(PROPNAME_PARENT, view.getParent());
        transitionValues.values.put(PROPNAME_TRANSFORMS, new android.transition.ChangeTransform.Transforms(view));
        android.graphics.Matrix matrix2 = view.getMatrix();
        if (matrix2 == null || matrix2.isIdentity()) {
            matrix = null;
        } else {
            matrix = new android.graphics.Matrix(matrix2);
        }
        transitionValues.values.put(PROPNAME_MATRIX, matrix);
        if (this.mReparent) {
            android.graphics.Matrix matrix3 = new android.graphics.Matrix();
            ((android.view.ViewGroup) view.getParent()).transformMatrixToGlobal(matrix3);
            matrix3.preTranslate(-r2.getScrollX(), -r2.getScrollY());
            transitionValues.values.put(PROPNAME_PARENT_MATRIX, matrix3);
            transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(com.android.internal.R.id.transitionTransform));
            transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(com.android.internal.R.id.parentMatrix));
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public void captureEndValues(android.transition.TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null || !transitionValues.values.containsKey(PROPNAME_PARENT) || !transitionValues2.values.containsKey(PROPNAME_PARENT)) {
            return null;
        }
        boolean z = this.mReparent && !parentsMatch((android.view.ViewGroup) transitionValues.values.get(PROPNAME_PARENT), (android.view.ViewGroup) transitionValues2.values.get(PROPNAME_PARENT));
        android.graphics.Matrix matrix = (android.graphics.Matrix) transitionValues.values.get(PROPNAME_INTERMEDIATE_MATRIX);
        if (matrix != null) {
            transitionValues.values.put(PROPNAME_MATRIX, matrix);
        }
        android.graphics.Matrix matrix2 = (android.graphics.Matrix) transitionValues.values.get(PROPNAME_INTERMEDIATE_PARENT_MATRIX);
        if (matrix2 != null) {
            transitionValues.values.put(PROPNAME_PARENT_MATRIX, matrix2);
        }
        if (z) {
            setMatricesForParent(transitionValues, transitionValues2);
        }
        android.animation.ObjectAnimator createTransformAnimator = createTransformAnimator(transitionValues, transitionValues2, z);
        if (z && createTransformAnimator != null && this.mUseOverlay) {
            createGhostView(viewGroup, transitionValues, transitionValues2);
        }
        return createTransformAnimator;
    }

    private android.animation.ObjectAnimator createTransformAnimator(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2, final boolean z) {
        final android.graphics.Matrix matrix;
        android.graphics.Matrix matrix2 = (android.graphics.Matrix) transitionValues.values.get(PROPNAME_MATRIX);
        android.graphics.Matrix matrix3 = (android.graphics.Matrix) transitionValues2.values.get(PROPNAME_MATRIX);
        if (matrix2 == null) {
            matrix2 = android.graphics.Matrix.IDENTITY_MATRIX;
        }
        if (matrix3 != null) {
            matrix = matrix3;
        } else {
            matrix = android.graphics.Matrix.IDENTITY_MATRIX;
        }
        if (matrix2.equals(matrix)) {
            return null;
        }
        final android.transition.ChangeTransform.Transforms transforms = (android.transition.ChangeTransform.Transforms) transitionValues2.values.get(PROPNAME_TRANSFORMS);
        final android.view.View view = transitionValues2.view;
        setIdentityTransforms(view);
        float[] fArr = new float[9];
        matrix2.getValues(fArr);
        float[] fArr2 = new float[9];
        matrix.getValues(fArr2);
        final android.transition.ChangeTransform.PathAnimatorMatrix pathAnimatorMatrix = new android.transition.ChangeTransform.PathAnimatorMatrix(view, fArr);
        android.animation.ObjectAnimator ofPropertyValuesHolder = android.animation.ObjectAnimator.ofPropertyValuesHolder(pathAnimatorMatrix, android.animation.PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, new android.animation.FloatArrayEvaluator(new float[9]), fArr, fArr2), android.animation.PropertyValuesHolder.ofObject(TRANSLATIONS_PROPERTY, (android.animation.TypeConverter) null, getPathMotion().getPath(fArr[2], fArr[5], fArr2[2], fArr2[5])));
        android.animation.AnimatorListenerAdapter animatorListenerAdapter = new android.animation.AnimatorListenerAdapter() { // from class: android.transition.ChangeTransform.3
            private boolean mIsCanceled;
            private android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator) {
                this.mIsCanceled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator) {
                if (!this.mIsCanceled) {
                    if (z && android.transition.ChangeTransform.this.mUseOverlay) {
                        setCurrentMatrix(matrix);
                    } else {
                        view.setTagInternal(com.android.internal.R.id.transitionTransform, null);
                        view.setTagInternal(com.android.internal.R.id.parentMatrix, null);
                    }
                }
                view.setAnimationMatrix(null);
                transforms.restore(view);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationPause(android.animation.Animator animator) {
                setCurrentMatrix(pathAnimatorMatrix.getMatrix());
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorPauseListener
            public void onAnimationResume(android.animation.Animator animator) {
                android.transition.ChangeTransform.setIdentityTransforms(view);
            }

            private void setCurrentMatrix(android.graphics.Matrix matrix4) {
                this.mTempMatrix.set(matrix4);
                view.setTagInternal(com.android.internal.R.id.transitionTransform, this.mTempMatrix);
                transforms.restore(view);
            }
        };
        ofPropertyValuesHolder.addListener(animatorListenerAdapter);
        ofPropertyValuesHolder.addPauseListener(animatorListenerAdapter);
        return ofPropertyValuesHolder;
    }

    private boolean parentsMatch(android.view.ViewGroup viewGroup, android.view.ViewGroup viewGroup2) {
        if (!isValidTarget(viewGroup) || !isValidTarget(viewGroup2)) {
            return viewGroup == viewGroup2;
        }
        android.transition.TransitionValues matchedTransitionValues = getMatchedTransitionValues(viewGroup, true);
        if (matchedTransitionValues != null) {
            return viewGroup2 == matchedTransitionValues.view;
        }
        return false;
    }

    private void createGhostView(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.view.View view = transitionValues2.view;
        android.graphics.Matrix matrix = new android.graphics.Matrix((android.graphics.Matrix) transitionValues2.values.get(PROPNAME_PARENT_MATRIX));
        viewGroup.transformMatrixToLocal(matrix);
        android.view.GhostView addGhost = android.view.GhostView.addGhost(view, viewGroup, matrix);
        android.transition.Transition transition = this;
        while (transition.mParent != null) {
            transition = transition.mParent;
        }
        transition.addListener(new android.transition.ChangeTransform.GhostListener(view, transitionValues.view, addGhost));
        if (transitionValues.view != transitionValues2.view) {
            transitionValues.view.setTransitionAlpha(0.0f);
        }
        view.setTransitionAlpha(1.0f);
    }

    private void setMatricesForParent(android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        android.graphics.Matrix matrix = (android.graphics.Matrix) transitionValues2.values.get(PROPNAME_PARENT_MATRIX);
        transitionValues2.view.setTagInternal(com.android.internal.R.id.parentMatrix, matrix);
        android.graphics.Matrix matrix2 = this.mTempMatrix;
        matrix2.reset();
        matrix.invert(matrix2);
        android.graphics.Matrix matrix3 = (android.graphics.Matrix) transitionValues.values.get(PROPNAME_MATRIX);
        if (matrix3 == null) {
            matrix3 = new android.graphics.Matrix();
            transitionValues.values.put(PROPNAME_MATRIX, matrix3);
        }
        matrix3.postConcat((android.graphics.Matrix) transitionValues.values.get(PROPNAME_PARENT_MATRIX));
        matrix3.postConcat(matrix2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setIdentityTransforms(android.view.View view) {
        setTransforms(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setTransforms(android.view.View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.setTranslationZ(f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
    }

    private static class Transforms {
        public final float rotationX;
        public final float rotationY;
        public final float rotationZ;
        public final float scaleX;
        public final float scaleY;
        public final float translationX;
        public final float translationY;
        public final float translationZ;

        public Transforms(android.view.View view) {
            this.translationX = view.getTranslationX();
            this.translationY = view.getTranslationY();
            this.translationZ = view.getTranslationZ();
            this.scaleX = view.getScaleX();
            this.scaleY = view.getScaleY();
            this.rotationX = view.getRotationX();
            this.rotationY = view.getRotationY();
            this.rotationZ = view.getRotation();
        }

        public void restore(android.view.View view) {
            android.transition.ChangeTransform.setTransforms(view, this.translationX, this.translationY, this.translationZ, this.scaleX, this.scaleY, this.rotationX, this.rotationY, this.rotationZ);
        }

        public boolean equals(java.lang.Object obj) {
            if (!(obj instanceof android.transition.ChangeTransform.Transforms)) {
                return false;
            }
            android.transition.ChangeTransform.Transforms transforms = (android.transition.ChangeTransform.Transforms) obj;
            return transforms.translationX == this.translationX && transforms.translationY == this.translationY && transforms.translationZ == this.translationZ && transforms.scaleX == this.scaleX && transforms.scaleY == this.scaleY && transforms.rotationX == this.rotationX && transforms.rotationY == this.rotationY && transforms.rotationZ == this.rotationZ;
        }
    }

    private static class GhostListener extends android.transition.TransitionListenerAdapter {
        private android.view.GhostView mGhostView;
        private android.view.View mStartView;
        private android.view.View mView;

        public GhostListener(android.view.View view, android.view.View view2, android.view.GhostView ghostView) {
            this.mView = view;
            this.mStartView = view2;
            this.mGhostView = ghostView;
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            transition.removeListener(this);
            android.view.GhostView.removeGhost(this.mView);
            this.mView.setTagInternal(com.android.internal.R.id.transitionTransform, null);
            this.mView.setTagInternal(com.android.internal.R.id.parentMatrix, null);
            this.mStartView.setTransitionAlpha(1.0f);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionPause(android.transition.Transition transition) {
            this.mGhostView.setVisibility(4);
        }

        @Override // android.transition.TransitionListenerAdapter, android.transition.Transition.TransitionListener
        public void onTransitionResume(android.transition.Transition transition) {
            this.mGhostView.setVisibility(0);
        }
    }

    private static class PathAnimatorMatrix {
        private final android.graphics.Matrix mMatrix = new android.graphics.Matrix();
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final android.view.View mView;

        public PathAnimatorMatrix(android.view.View view, float[] fArr) {
            this.mView = view;
            this.mValues = (float[]) fArr.clone();
            this.mTranslationX = this.mValues[2];
            this.mTranslationY = this.mValues[5];
            setAnimationMatrix();
        }

        public void setValues(float[] fArr) {
            java.lang.System.arraycopy(fArr, 0, this.mValues, 0, fArr.length);
            setAnimationMatrix();
        }

        public void setTranslation(android.graphics.PointF pointF) {
            this.mTranslationX = pointF.x;
            this.mTranslationY = pointF.y;
            setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            this.mValues[2] = this.mTranslationX;
            this.mValues[5] = this.mTranslationY;
            this.mMatrix.setValues(this.mValues);
            this.mView.setAnimationMatrix(this.mMatrix);
        }

        public android.graphics.Matrix getMatrix() {
            return this.mMatrix;
        }
    }
}
