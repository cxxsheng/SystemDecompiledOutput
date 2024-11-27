package android.transition;

/* loaded from: classes3.dex */
public class TransitionUtils {
    private static int MAX_IMAGE_SIZE = 1048576;

    static android.animation.Animator mergeAnimators(android.animation.Animator animator, android.animation.Animator animator2) {
        if (animator == null) {
            return animator2;
        }
        if (animator2 == null) {
            return animator;
        }
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(animator, animator2);
        return animatorSet;
    }

    public static android.transition.Transition mergeTransitions(android.transition.Transition... transitionArr) {
        int i = -1;
        int i2 = 0;
        for (int i3 = 0; i3 < transitionArr.length; i3++) {
            if (transitionArr[i3] != null) {
                i2++;
                i = i3;
            }
        }
        if (i2 == 0) {
            return null;
        }
        if (i2 == 1) {
            return transitionArr[i];
        }
        android.transition.TransitionSet transitionSet = new android.transition.TransitionSet();
        for (int i4 = 0; i4 < transitionArr.length; i4++) {
            if (transitionArr[i4] != null) {
                transitionSet.addTransition(transitionArr[i4]);
            }
        }
        return transitionSet;
    }

    public static android.view.View copyViewImage(android.view.ViewGroup viewGroup, android.view.View view, android.view.View view2) {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        matrix.setTranslate(-view2.getScrollX(), -view2.getScrollY());
        view.transformMatrixToGlobal(matrix);
        viewGroup.transformMatrixToLocal(matrix);
        android.graphics.RectF rectF = new android.graphics.RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        matrix.mapRect(rectF);
        int round = java.lang.Math.round(rectF.left);
        int round2 = java.lang.Math.round(rectF.top);
        int round3 = java.lang.Math.round(rectF.right);
        int round4 = java.lang.Math.round(rectF.bottom);
        android.widget.ImageView imageView = new android.widget.ImageView(view.getContext());
        imageView.setScaleType(android.widget.ImageView.ScaleType.CENTER_CROP);
        android.graphics.Bitmap createViewBitmap = createViewBitmap(view, matrix, rectF, viewGroup);
        if (createViewBitmap != null) {
            imageView.setImageBitmap(createViewBitmap);
        }
        imageView.measure(android.view.View.MeasureSpec.makeMeasureSpec(round3 - round, 1073741824), android.view.View.MeasureSpec.makeMeasureSpec(round4 - round2, 1073741824));
        imageView.layout(round, round2, round3, round4);
        return imageView;
    }

    public static android.graphics.Bitmap createDrawableBitmap(android.graphics.drawable.Drawable drawable, android.view.View view) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null;
        }
        float min = java.lang.Math.min(1.0f, MAX_IMAGE_SIZE / (intrinsicWidth * intrinsicHeight));
        if ((drawable instanceof android.graphics.drawable.BitmapDrawable) && min == 1.0f) {
            return ((android.graphics.drawable.BitmapDrawable) drawable).getBitmap();
        }
        int i = (int) (intrinsicWidth * min);
        int i2 = (int) (intrinsicHeight * min);
        android.graphics.Picture picture = new android.graphics.Picture();
        android.graphics.Canvas beginRecording = picture.beginRecording(intrinsicWidth, intrinsicHeight);
        android.graphics.Rect bounds = drawable.getBounds();
        int i3 = bounds.left;
        int i4 = bounds.top;
        int i5 = bounds.right;
        int i6 = bounds.bottom;
        drawable.setBounds(0, 0, i, i2);
        drawable.draw(beginRecording);
        drawable.setBounds(i3, i4, i5, i6);
        picture.endRecording();
        return android.graphics.Bitmap.createBitmap(picture);
    }

    public static android.graphics.Bitmap createViewBitmap(android.view.View view, android.graphics.Matrix matrix, android.graphics.RectF rectF, android.view.ViewGroup viewGroup) {
        int i;
        android.view.ViewGroup viewGroup2;
        boolean z = !view.isAttachedToWindow();
        android.graphics.Bitmap bitmap = null;
        if (!z) {
            i = 0;
            viewGroup2 = null;
        } else {
            if (viewGroup == null || !viewGroup.isAttachedToWindow()) {
                return null;
            }
            viewGroup2 = (android.view.ViewGroup) view.getParent();
            i = viewGroup2.indexOfChild(view);
            viewGroup.getOverlay().add(view);
        }
        int round = java.lang.Math.round(rectF.width());
        int round2 = java.lang.Math.round(rectF.height());
        if (round > 0 && round2 > 0) {
            float min = java.lang.Math.min(1.0f, MAX_IMAGE_SIZE / (round * round2));
            matrix.postTranslate(-rectF.left, -rectF.top);
            matrix.postScale(min, min);
            android.graphics.Picture picture = new android.graphics.Picture();
            android.graphics.Canvas beginRecording = picture.beginRecording((int) (round * min), (int) (round2 * min));
            beginRecording.concat(matrix);
            view.draw(beginRecording);
            picture.endRecording();
            bitmap = android.graphics.Bitmap.createBitmap(picture);
        }
        if (z) {
            viewGroup.getOverlay().remove(view);
            viewGroup2.addView(view, i);
        }
        return bitmap;
    }

    public static class MatrixEvaluator implements android.animation.TypeEvaluator<android.graphics.Matrix> {
        float[] mTempStartValues = new float[9];
        float[] mTempEndValues = new float[9];
        android.graphics.Matrix mTempMatrix = new android.graphics.Matrix();

        @Override // android.animation.TypeEvaluator
        public android.graphics.Matrix evaluate(float f, android.graphics.Matrix matrix, android.graphics.Matrix matrix2) {
            matrix.getValues(this.mTempStartValues);
            matrix2.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; i++) {
                this.mTempEndValues[i] = this.mTempStartValues[i] + ((this.mTempEndValues[i] - this.mTempStartValues[i]) * f);
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }
}
