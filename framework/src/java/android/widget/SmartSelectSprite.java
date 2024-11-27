package android.widget;

/* loaded from: classes4.dex */
final class SmartSelectSprite {
    private static final int EXPAND_DURATION = 200;
    static final java.util.Comparator<android.graphics.RectF> RECTANGLE_COMPARATOR = java.util.Comparator.comparingDouble(new java.util.function.ToDoubleFunction() { // from class: android.widget.SmartSelectSprite$$ExternalSyntheticLambda0
        /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
              (r0v0 double) from 0x0006: RETURN (r0v0 double)
            	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
            	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
            	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
            	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
            	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
            	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
            */
        @Override // java.util.function.ToDoubleFunction
        public final double applyAsDouble(java.lang.Object r3) {
            /*
                r2 = this;
                android.graphics.RectF r3 = (android.graphics.RectF) r3
                double r0 = android.widget.SmartSelectSprite.lambda$static$0(r3)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.SmartSelectSprite$$ExternalSyntheticLambda0.applyAsDouble(java.lang.Object):double");
        }
    }).thenComparingDouble(new java.util.function.ToDoubleFunction() { // from class: android.widget.SmartSelectSprite$$ExternalSyntheticLambda1
        /*  JADX ERROR: JadxRuntimeException in pass: ModVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r0v0 double, still in use, count: 1, list:
              (r0v0 double) from 0x0006: RETURN (r0v0 double)
            	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
            	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
            	at jadx.core.utils.InsnRemover.unbindInsn(InsnRemover.java:91)
            	at jadx.core.utils.InsnRemover.addAndUnbind(InsnRemover.java:57)
            	at jadx.core.dex.visitors.ModVisitor.removeStep(ModVisitor.java:452)
            	at jadx.core.dex.visitors.ModVisitor.visit(ModVisitor.java:96)
            */
        @Override // java.util.function.ToDoubleFunction
        public final double applyAsDouble(java.lang.Object r3) {
            /*
                r2 = this;
                android.graphics.RectF r3 = (android.graphics.RectF) r3
                double r0 = android.widget.SmartSelectSprite.lambda$static$1(r3)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.SmartSelectSprite$$ExternalSyntheticLambda1.applyAsDouble(java.lang.Object):double");
        }
    });
    private android.animation.Animator mActiveAnimator = null;
    private android.graphics.drawable.Drawable mExistingDrawable = null;
    private android.widget.SmartSelectSprite.RectangleList mExistingRectangleList = null;
    private final android.view.animation.Interpolator mExpandInterpolator;
    private final int mFillColor;
    private final java.lang.Runnable mInvalidator;

    static final class RectangleWithTextSelectionLayout {
        private final android.graphics.RectF mRectangle;
        private final int mTextSelectionLayout;

        RectangleWithTextSelectionLayout(android.graphics.RectF rectF, int i) {
            this.mRectangle = (android.graphics.RectF) java.util.Objects.requireNonNull(rectF);
            this.mTextSelectionLayout = i;
        }

        public android.graphics.RectF getRectangle() {
            return this.mRectangle;
        }

        public int getTextSelectionLayout() {
            return this.mTextSelectionLayout;
        }
    }

    private static final class RoundedRectangleShape extends android.graphics.drawable.shapes.Shape {
        private static final java.lang.String PROPERTY_ROUND_RATIO = "roundRatio";
        private final android.graphics.RectF mBoundingRectangle;
        private final float mBoundingWidth;
        private final android.graphics.Path mClipPath;
        private final android.graphics.RectF mDrawRect;
        private final int mExpansionDirection;
        private final boolean mInverted;
        private float mLeftBoundary;
        private float mRightBoundary;
        private float mRoundRatio;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface ExpansionDirection {
            public static final int CENTER = 0;
            public static final int LEFT = -1;
            public static final int RIGHT = 1;
        }

        private static int invert(int i) {
            return i * (-1);
        }

        private RoundedRectangleShape(android.graphics.RectF rectF, int i, boolean z) {
            this.mRoundRatio = 1.0f;
            this.mDrawRect = new android.graphics.RectF();
            this.mClipPath = new android.graphics.Path();
            this.mLeftBoundary = 0.0f;
            this.mRightBoundary = 0.0f;
            this.mBoundingRectangle = new android.graphics.RectF(rectF);
            this.mBoundingWidth = rectF.width();
            this.mInverted = z && i != 0;
            if (z) {
                this.mExpansionDirection = invert(i);
            } else {
                this.mExpansionDirection = i;
            }
            if (rectF.height() > rectF.width()) {
                setRoundRatio(0.0f);
            } else {
                setRoundRatio(1.0f);
            }
        }

        @Override // android.graphics.drawable.shapes.Shape
        public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
            if (this.mLeftBoundary == this.mRightBoundary) {
                return;
            }
            float cornerRadius = getCornerRadius();
            float adjustedCornerRadius = getAdjustedCornerRadius();
            this.mDrawRect.set(this.mBoundingRectangle);
            float f = cornerRadius / 2.0f;
            this.mDrawRect.left = (this.mBoundingRectangle.left + this.mLeftBoundary) - f;
            this.mDrawRect.right = this.mBoundingRectangle.left + this.mRightBoundary + f;
            canvas.save();
            this.mClipPath.reset();
            this.mClipPath.addRoundRect(this.mDrawRect, adjustedCornerRadius, adjustedCornerRadius, android.graphics.Path.Direction.CW);
            canvas.clipPath(this.mClipPath);
            canvas.drawRect(this.mBoundingRectangle, paint);
            canvas.restore();
        }

        void setRoundRatio(float f) {
            this.mRoundRatio = f;
        }

        float getRoundRatio() {
            return this.mRoundRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStartBoundary(float f) {
            if (this.mInverted) {
                this.mRightBoundary = this.mBoundingWidth - f;
            } else {
                this.mLeftBoundary = f;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setEndBoundary(float f) {
            if (this.mInverted) {
                this.mLeftBoundary = this.mBoundingWidth - f;
            } else {
                this.mRightBoundary = f;
            }
        }

        private float getCornerRadius() {
            return java.lang.Math.min(this.mBoundingRectangle.width(), this.mBoundingRectangle.height());
        }

        private float getAdjustedCornerRadius() {
            return getCornerRadius() * this.mRoundRatio;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getBoundingWidth() {
            return (int) (this.mBoundingRectangle.width() + getCornerRadius());
        }
    }

    private static final class RectangleList extends android.graphics.drawable.shapes.Shape {
        private static final java.lang.String PROPERTY_LEFT_BOUNDARY = "leftBoundary";
        private static final java.lang.String PROPERTY_RIGHT_BOUNDARY = "rightBoundary";
        private int mDisplayType;
        private final android.graphics.Path mOutlinePolygonPath;
        private final java.util.List<android.widget.SmartSelectSprite.RoundedRectangleShape> mRectangles;
        private final java.util.List<android.widget.SmartSelectSprite.RoundedRectangleShape> mReversedRectangles;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        private @interface DisplayType {
            public static final int POLYGON = 1;
            public static final int RECTANGLES = 0;
        }

        private RectangleList(java.util.List<android.widget.SmartSelectSprite.RoundedRectangleShape> list) {
            this.mDisplayType = 0;
            this.mRectangles = new java.util.ArrayList(list);
            this.mReversedRectangles = new java.util.ArrayList(list);
            java.util.Collections.reverse(this.mReversedRectangles);
            this.mOutlinePolygonPath = generateOutlinePolygonPath(list);
        }

        private void setLeftBoundary(float f) {
            float totalWidth = getTotalWidth();
            for (android.widget.SmartSelectSprite.RoundedRectangleShape roundedRectangleShape : this.mReversedRectangles) {
                float boundingWidth = totalWidth - roundedRectangleShape.getBoundingWidth();
                if (f < boundingWidth) {
                    roundedRectangleShape.setStartBoundary(0.0f);
                } else if (f > totalWidth) {
                    roundedRectangleShape.setStartBoundary(roundedRectangleShape.getBoundingWidth());
                } else {
                    roundedRectangleShape.setStartBoundary((roundedRectangleShape.getBoundingWidth() - totalWidth) + f);
                }
                totalWidth = boundingWidth;
            }
        }

        private void setRightBoundary(float f) {
            float f2 = 0.0f;
            for (android.widget.SmartSelectSprite.RoundedRectangleShape roundedRectangleShape : this.mRectangles) {
                float boundingWidth = roundedRectangleShape.getBoundingWidth() + f2;
                if (boundingWidth < f) {
                    roundedRectangleShape.setEndBoundary(roundedRectangleShape.getBoundingWidth());
                } else if (f2 > f) {
                    roundedRectangleShape.setEndBoundary(0.0f);
                } else {
                    roundedRectangleShape.setEndBoundary(f - f2);
                }
                f2 = boundingWidth;
            }
        }

        void setDisplayType(int i) {
            this.mDisplayType = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getTotalWidth() {
            java.util.Iterator<android.widget.SmartSelectSprite.RoundedRectangleShape> it = this.mRectangles.iterator();
            int i = 0;
            while (it.hasNext()) {
                i = (int) (i + it.next().getBoundingWidth());
            }
            return i;
        }

        @Override // android.graphics.drawable.shapes.Shape
        public void draw(android.graphics.Canvas canvas, android.graphics.Paint paint) {
            if (this.mDisplayType == 1) {
                drawPolygon(canvas, paint);
            } else {
                drawRectangles(canvas, paint);
            }
        }

        private void drawRectangles(android.graphics.Canvas canvas, android.graphics.Paint paint) {
            java.util.Iterator<android.widget.SmartSelectSprite.RoundedRectangleShape> it = this.mRectangles.iterator();
            while (it.hasNext()) {
                it.next().draw(canvas, paint);
            }
        }

        private void drawPolygon(android.graphics.Canvas canvas, android.graphics.Paint paint) {
            canvas.drawPath(this.mOutlinePolygonPath, paint);
        }

        private static android.graphics.Path generateOutlinePolygonPath(java.util.List<android.widget.SmartSelectSprite.RoundedRectangleShape> list) {
            android.graphics.Path path = new android.graphics.Path();
            for (android.widget.SmartSelectSprite.RoundedRectangleShape roundedRectangleShape : list) {
                android.graphics.Path path2 = new android.graphics.Path();
                path2.addRect(roundedRectangleShape.mBoundingRectangle, android.graphics.Path.Direction.CW);
                path.op(path2, android.graphics.Path.Op.UNION);
            }
            return path;
        }
    }

    SmartSelectSprite(android.content.Context context, int i, java.lang.Runnable runnable) {
        this.mExpandInterpolator = android.view.animation.AnimationUtils.loadInterpolator(context, 17563661);
        this.mFillColor = i;
        this.mInvalidator = (java.lang.Runnable) java.util.Objects.requireNonNull(runnable);
    }

    public void startAnimation(android.graphics.PointF pointF, java.util.List<android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout> list, java.lang.Runnable runnable) {
        android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout rectangleWithTextSelectionLayout;
        byte b;
        cancelAnimation();
        android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new android.animation.ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.SmartSelectSprite$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
                android.widget.SmartSelectSprite.this.lambda$startAnimation$2(valueAnimator);
            }
        };
        int size = list.size();
        java.util.ArrayList arrayList = new java.util.ArrayList(size);
        java.util.Iterator<android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout> it = list.iterator();
        int i = 0;
        while (true) {
            b = 0;
            if (!it.hasNext()) {
                rectangleWithTextSelectionLayout = null;
                break;
            }
            rectangleWithTextSelectionLayout = it.next();
            android.graphics.RectF rectangle = rectangleWithTextSelectionLayout.getRectangle();
            if (contains(rectangle, pointF)) {
                break;
            } else {
                i = (int) (i + rectangle.width());
            }
        }
        if (rectangleWithTextSelectionLayout == null) {
            throw new java.lang.IllegalArgumentException("Center point is not inside any of the rectangles!");
        }
        int i2 = (int) (i + (pointF.x - rectangleWithTextSelectionLayout.getRectangle().left));
        int[] generateDirections = generateDirections(rectangleWithTextSelectionLayout, list);
        for (int i3 = 0; i3 < size; i3++) {
            android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout rectangleWithTextSelectionLayout2 = list.get(i3);
            arrayList.add(new android.widget.SmartSelectSprite.RoundedRectangleShape(rectangleWithTextSelectionLayout2.getRectangle(), generateDirections[i3], rectangleWithTextSelectionLayout2.getTextSelectionLayout() == 0));
        }
        android.widget.SmartSelectSprite.RectangleList rectangleList = new android.widget.SmartSelectSprite.RectangleList(arrayList);
        android.graphics.drawable.ShapeDrawable shapeDrawable = new android.graphics.drawable.ShapeDrawable(rectangleList);
        android.graphics.Paint paint = shapeDrawable.getPaint();
        paint.setColor(this.mFillColor);
        paint.setStyle(android.graphics.Paint.Style.FILL);
        this.mExistingRectangleList = rectangleList;
        this.mExistingDrawable = shapeDrawable;
        float f = i2;
        this.mActiveAnimator = createAnimator(rectangleList, f, f, animatorUpdateListener, runnable);
        this.mActiveAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startAnimation$2(android.animation.ValueAnimator valueAnimator) {
        this.mInvalidator.run();
    }

    public boolean isAnimationActive() {
        return this.mActiveAnimator != null && this.mActiveAnimator.isRunning();
    }

    private android.animation.Animator createAnimator(android.widget.SmartSelectSprite.RectangleList rectangleList, float f, float f2, android.animation.ValueAnimator.AnimatorUpdateListener animatorUpdateListener, java.lang.Runnable runnable) {
        android.animation.ObjectAnimator ofFloat = android.animation.ObjectAnimator.ofFloat(rectangleList, "rightBoundary", f2, rectangleList.getTotalWidth());
        android.animation.ObjectAnimator ofFloat2 = android.animation.ObjectAnimator.ofFloat(rectangleList, "leftBoundary", f, 0.0f);
        ofFloat.setDuration(200L);
        ofFloat2.setDuration(200L);
        ofFloat.addUpdateListener(animatorUpdateListener);
        ofFloat2.addUpdateListener(animatorUpdateListener);
        ofFloat.setInterpolator(this.mExpandInterpolator);
        ofFloat2.setInterpolator(this.mExpandInterpolator);
        android.animation.AnimatorSet animatorSet = new android.animation.AnimatorSet();
        animatorSet.playTogether(ofFloat2, ofFloat);
        setUpAnimatorListener(animatorSet, runnable);
        return animatorSet;
    }

    private void setUpAnimatorListener(android.animation.Animator animator, final java.lang.Runnable runnable) {
        animator.addListener(new android.animation.Animator.AnimatorListener() { // from class: android.widget.SmartSelectSprite.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(android.animation.Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(android.animation.Animator animator2) {
                android.widget.SmartSelectSprite.this.mExistingRectangleList.setDisplayType(1);
                android.widget.SmartSelectSprite.this.mInvalidator.run();
                runnable.run();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(android.animation.Animator animator2) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(android.animation.Animator animator2) {
            }
        });
    }

    private static int[] generateDirections(android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout rectangleWithTextSelectionLayout, java.util.List<android.widget.SmartSelectSprite.RectangleWithTextSelectionLayout> list) {
        int size = list.size();
        int[] iArr = new int[size];
        int indexOf = list.indexOf(rectangleWithTextSelectionLayout);
        for (int i = 0; i < indexOf - 1; i++) {
            iArr[i] = -1;
        }
        if (list.size() == 1) {
            iArr[indexOf] = 0;
        } else if (indexOf == 0) {
            iArr[indexOf] = -1;
        } else if (indexOf == list.size() - 1) {
            iArr[indexOf] = 1;
        } else {
            iArr[indexOf] = 0;
        }
        for (int i2 = indexOf + 1; i2 < size; i2++) {
            iArr[i2] = 1;
        }
        return iArr;
    }

    private static boolean contains(android.graphics.RectF rectF, android.graphics.PointF pointF) {
        float f = pointF.x;
        float f2 = pointF.y;
        return f >= rectF.left && f <= rectF.right && f2 >= rectF.top && f2 <= rectF.bottom;
    }

    private void removeExistingDrawables() {
        this.mExistingDrawable = null;
        this.mExistingRectangleList = null;
        this.mInvalidator.run();
    }

    public void cancelAnimation() {
        if (this.mActiveAnimator != null) {
            this.mActiveAnimator.cancel();
            this.mActiveAnimator = null;
            removeExistingDrawables();
        }
    }

    public void draw(android.graphics.Canvas canvas) {
        if (this.mExistingDrawable != null) {
            this.mExistingDrawable.draw(canvas);
        }
    }
}
