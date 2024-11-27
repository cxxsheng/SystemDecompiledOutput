package android.view;

/* loaded from: classes4.dex */
public class SyncRtSurfaceTransactionApplier {
    public static final int FLAG_ALL = -1;
    public static final int FLAG_ALPHA = 1;
    public static final int FLAG_BACKGROUND_BLUR_RADIUS = 32;
    public static final int FLAG_CORNER_RADIUS = 16;
    public static final int FLAG_LAYER = 8;
    public static final int FLAG_MATRIX = 2;
    public static final int FLAG_TRANSACTION = 128;
    public static final int FLAG_VISIBILITY = 64;
    public static final int FLAG_WINDOW_CROP = 4;
    private android.view.SurfaceControl mTargetSc;
    private final android.view.ViewRootImpl mTargetViewRootImpl;
    private final float[] mTmpFloat9 = new float[9];

    public SyncRtSurfaceTransactionApplier(android.view.View view) {
        this.mTargetViewRootImpl = view != null ? view.getViewRootImpl() : null;
    }

    public void scheduleApply(android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        if (this.mTargetViewRootImpl == null) {
            return;
        }
        this.mTargetSc = this.mTargetViewRootImpl.getSurfaceControl();
        final android.view.SurfaceControl.Transaction transaction = new android.view.SurfaceControl.Transaction();
        applyParams(transaction, surfaceParamsArr);
        this.mTargetViewRootImpl.registerRtFrameCallback(new android.graphics.HardwareRenderer.FrameDrawingCallback() { // from class: android.view.SyncRtSurfaceTransactionApplier$$ExternalSyntheticLambda0
            @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
            public final void onFrameDraw(long j) {
                android.view.SyncRtSurfaceTransactionApplier.this.lambda$scheduleApply$0(transaction, j);
            }
        });
        this.mTargetViewRootImpl.getView().invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scheduleApply$0(android.view.SurfaceControl.Transaction transaction, long j) {
        if (this.mTargetSc != null && this.mTargetSc.isValid()) {
            applyTransaction(transaction, j);
        }
        transaction.close();
    }

    void applyParams(android.view.SurfaceControl.Transaction transaction, android.view.SyncRtSurfaceTransactionApplier.SurfaceParams... surfaceParamsArr) {
        for (int length = surfaceParamsArr.length - 1; length >= 0; length--) {
            android.view.SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = surfaceParamsArr[length];
            android.view.SurfaceControl surfaceControl = surfaceParams.surface;
            applyParams(transaction, surfaceParams, this.mTmpFloat9);
        }
    }

    void applyTransaction(android.view.SurfaceControl.Transaction transaction, long j) {
        if (this.mTargetViewRootImpl != null) {
            this.mTargetViewRootImpl.mergeWithNextTransaction(transaction, j);
        } else {
            transaction.apply();
        }
    }

    public static void applyParams(android.view.SurfaceControl.Transaction transaction, android.view.SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams, float[] fArr) {
        if ((surfaceParams.flags & 128) != 0) {
            transaction.merge(surfaceParams.mergeTransaction);
        }
        if ((surfaceParams.flags & 2) != 0) {
            transaction.setMatrix(surfaceParams.surface, surfaceParams.matrix, fArr);
        }
        if ((surfaceParams.flags & 4) != 0) {
            transaction.setWindowCrop(surfaceParams.surface, surfaceParams.windowCrop);
        }
        if ((surfaceParams.flags & 1) != 0) {
            transaction.setAlpha(surfaceParams.surface, surfaceParams.alpha);
        }
        if ((surfaceParams.flags & 8) != 0) {
            transaction.setLayer(surfaceParams.surface, surfaceParams.layer);
        }
        if ((surfaceParams.flags & 16) != 0) {
            transaction.setCornerRadius(surfaceParams.surface, surfaceParams.cornerRadius);
        }
        if ((surfaceParams.flags & 32) != 0) {
            transaction.setBackgroundBlurRadius(surfaceParams.surface, surfaceParams.backgroundBlurRadius);
        }
        if ((surfaceParams.flags & 64) != 0) {
            if (surfaceParams.visible) {
                transaction.show(surfaceParams.surface);
            } else {
                transaction.hide(surfaceParams.surface);
            }
        }
    }

    public static void create(final android.view.View view, final java.util.function.Consumer<android.view.SyncRtSurfaceTransactionApplier> consumer) {
        if (view == null) {
            consumer.accept(null);
        } else if (view.getViewRootImpl() != null) {
            consumer.accept(new android.view.SyncRtSurfaceTransactionApplier(view));
        } else {
            view.addOnAttachStateChangeListener(new android.view.View.OnAttachStateChangeListener() { // from class: android.view.SyncRtSurfaceTransactionApplier.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(android.view.View view2) {
                    android.view.View.this.removeOnAttachStateChangeListener(this);
                    consumer.accept(new android.view.SyncRtSurfaceTransactionApplier(android.view.View.this));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(android.view.View view2) {
                }
            });
        }
    }

    public static class SurfaceParams {
        public final float alpha;
        public final int backgroundBlurRadius;
        public final float cornerRadius;
        private final int flags;
        public final int layer;
        public final android.graphics.Matrix matrix;
        public final android.view.SurfaceControl.Transaction mergeTransaction;
        public final android.view.SurfaceControl surface;
        public final boolean visible;
        public final android.graphics.Rect windowCrop;

        public static class Builder {
            float alpha;
            int backgroundBlurRadius;
            float cornerRadius;
            int flags;
            int layer;
            android.graphics.Matrix matrix;
            android.view.SurfaceControl.Transaction mergeTransaction;
            final android.view.SurfaceControl surface;
            boolean visible;
            android.graphics.Rect windowCrop;

            public Builder(android.view.SurfaceControl surfaceControl) {
                this.surface = surfaceControl;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlpha(float f) {
                this.alpha = f;
                this.flags |= 1;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withMatrix(android.graphics.Matrix matrix) {
                this.matrix = new android.graphics.Matrix(matrix);
                this.flags |= 2;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withWindowCrop(android.graphics.Rect rect) {
                this.windowCrop = new android.graphics.Rect(rect);
                this.flags |= 4;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withLayer(int i) {
                this.layer = i;
                this.flags |= 8;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withCornerRadius(float f) {
                this.cornerRadius = f;
                this.flags |= 16;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withBackgroundBlur(int i) {
                this.backgroundBlurRadius = i;
                this.flags |= 32;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withVisibility(boolean z) {
                this.visible = z;
                this.flags |= 64;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withMergeTransaction(android.view.SurfaceControl.Transaction transaction) {
                this.mergeTransaction = transaction;
                this.flags |= 128;
                return this;
            }

            public android.view.SyncRtSurfaceTransactionApplier.SurfaceParams build() {
                return new android.view.SyncRtSurfaceTransactionApplier.SurfaceParams(this.surface, this.flags, this.alpha, this.matrix, this.windowCrop, this.layer, this.cornerRadius, this.backgroundBlurRadius, this.visible, this.mergeTransaction);
            }
        }

        private SurfaceParams(android.view.SurfaceControl surfaceControl, int i, float f, android.graphics.Matrix matrix, android.graphics.Rect rect, int i2, float f2, int i3, boolean z, android.view.SurfaceControl.Transaction transaction) {
            this.flags = i;
            this.surface = surfaceControl;
            this.alpha = f;
            this.matrix = matrix;
            this.windowCrop = rect;
            this.layer = i2;
            this.cornerRadius = f2;
            this.backgroundBlurRadius = i3;
            this.visible = z;
            this.mergeTransaction = transaction;
        }
    }
}
