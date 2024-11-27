package android.transition;

/* loaded from: classes3.dex */
public class ChangeImageTransform extends android.transition.Transition {
    private static final java.lang.String TAG = "ChangeImageTransform";
    private static final java.lang.String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final java.lang.String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final java.lang.String[] sTransitionProperties = {PROPNAME_MATRIX, PROPNAME_BOUNDS};
    private static android.animation.TypeEvaluator<android.graphics.Matrix> NULL_MATRIX_EVALUATOR = new android.animation.TypeEvaluator<android.graphics.Matrix>() { // from class: android.transition.ChangeImageTransform.1
        @Override // android.animation.TypeEvaluator
        public android.graphics.Matrix evaluate(float f, android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
            return null;
        }
    };
    private static android.util.Property<android.widget.ImageView, android.graphics.Matrix> ANIMATED_TRANSFORM_PROPERTY = new android.util.Property<android.widget.ImageView, android.graphics.Matrix>(android.graphics.Matrix.class, "animatedTransform") { // from class: android.transition.ChangeImageTransform.2
        @Override // android.util.Property
        public void set(android.widget.ImageView imageView, android.graphics.Matrix matrix) {
            imageView.animateTransform(matrix);
        }

        @Override // android.util.Property
        public android.graphics.Matrix get(android.widget.ImageView imageView) {
            return null;
        }
    };

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(android.content.Context context, android.util.AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(android.transition.TransitionValues transitionValues) {
        android.widget.ImageView imageView;
        android.graphics.drawable.Drawable drawable;
        android.graphics.Matrix matrix;
        android.view.View view = transitionValues.view;
        if (!(view instanceof android.widget.ImageView) || view.getVisibility() != 0 || (drawable = (imageView = (android.widget.ImageView) view).getDrawable()) == null) {
            return;
        }
        java.util.Map<java.lang.String, java.lang.Object> map = transitionValues.values;
        map.put(PROPNAME_BOUNDS, new android.graphics.Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        android.widget.ImageView.ScaleType scaleType = imageView.getScaleType();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (scaleType == android.widget.ImageView.ScaleType.FIT_XY && intrinsicWidth > 0 && intrinsicHeight > 0) {
            float height = r6.height() / intrinsicHeight;
            matrix = new android.graphics.Matrix();
            matrix.setScale(r6.width() / intrinsicWidth, height);
        } else {
            matrix = new android.graphics.Matrix(imageView.getImageMatrix());
        }
        map.put(PROPNAME_MATRIX, matrix);
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
    public java.lang.String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override // android.transition.Transition
    public android.animation.Animator createAnimator(android.view.ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        android.graphics.Rect rect = (android.graphics.Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        android.graphics.Rect rect2 = (android.graphics.Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        android.graphics.Matrix matrix = (android.graphics.Matrix) transitionValues.values.get(PROPNAME_MATRIX);
        android.graphics.Matrix matrix2 = (android.graphics.Matrix) transitionValues2.values.get(PROPNAME_MATRIX);
        if (rect == null || rect2 == null || matrix == null || matrix2 == null) {
            return null;
        }
        if (rect.equals(rect2) && matrix.equals(matrix2)) {
            return null;
        }
        android.widget.ImageView imageView = (android.widget.ImageView) transitionValues2.view;
        android.graphics.drawable.Drawable drawable = imageView.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return createNullAnimator(imageView);
        }
        ANIMATED_TRANSFORM_PROPERTY.set(imageView, matrix);
        return createMatrixAnimator(imageView, matrix, matrix2);
    }

    private android.animation.ObjectAnimator createNullAnimator(android.widget.ImageView imageView) {
        return android.animation.ObjectAnimator.ofObject(imageView, (android.util.Property<android.widget.ImageView, V>) ANIMATED_TRANSFORM_PROPERTY, (android.animation.TypeEvaluator) NULL_MATRIX_EVALUATOR, (java.lang.Object[]) new android.graphics.Matrix[]{android.graphics.Matrix.IDENTITY_MATRIX, android.graphics.Matrix.IDENTITY_MATRIX});
    }

    private android.animation.ObjectAnimator createMatrixAnimator(android.widget.ImageView imageView, android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
        return android.animation.ObjectAnimator.ofObject(imageView, (android.util.Property<android.widget.ImageView, V>) ANIMATED_TRANSFORM_PROPERTY, (android.animation.TypeEvaluator) new android.transition.TransitionUtils.MatrixEvaluator(), (java.lang.Object[]) new android.graphics.Matrix[]{matrix, matrix2});
    }
}
