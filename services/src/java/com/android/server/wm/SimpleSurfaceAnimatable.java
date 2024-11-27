package com.android.server.wm;

/* loaded from: classes3.dex */
public class SimpleSurfaceAnimatable implements com.android.server.wm.SurfaceAnimator.Animatable {
    private final java.util.function.Supplier<android.view.SurfaceControl.Builder> mAnimationLeashFactory;
    private final android.view.SurfaceControl mAnimationLeashParent;
    private final java.lang.Runnable mCommitTransactionRunnable;
    private final int mHeight;
    private final java.util.function.Consumer<java.lang.Runnable> mOnAnimationFinished;
    private final java.util.function.BiConsumer<android.view.SurfaceControl.Transaction, android.view.SurfaceControl> mOnAnimationLeashCreated;
    private final java.util.function.Consumer<android.view.SurfaceControl.Transaction> mOnAnimationLeashLost;
    private final android.view.SurfaceControl mParentSurfaceControl;
    private final java.util.function.Supplier<android.view.SurfaceControl.Transaction> mPendingTransaction;
    private final boolean mShouldDeferAnimationFinish;
    private final android.view.SurfaceControl mSurfaceControl;
    private final java.util.function.Supplier<android.view.SurfaceControl.Transaction> mSyncTransaction;
    private final int mWidth;

    private SimpleSurfaceAnimatable(com.android.server.wm.SimpleSurfaceAnimatable.Builder builder) {
        this.mWidth = builder.mWidth;
        this.mHeight = builder.mHeight;
        this.mShouldDeferAnimationFinish = builder.mShouldDeferAnimationFinish;
        this.mAnimationLeashParent = builder.mAnimationLeashParent;
        this.mSurfaceControl = builder.mSurfaceControl;
        this.mParentSurfaceControl = builder.mParentSurfaceControl;
        this.mCommitTransactionRunnable = builder.mCommitTransactionRunnable;
        this.mAnimationLeashFactory = builder.mAnimationLeashFactory;
        this.mOnAnimationLeashCreated = builder.mOnAnimationLeashCreated;
        this.mOnAnimationLeashLost = builder.mOnAnimationLeashLost;
        this.mSyncTransaction = builder.mSyncTransactionSupplier;
        this.mPendingTransaction = builder.mPendingTransactionSupplier;
        this.mOnAnimationFinished = builder.mOnAnimationFinished;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl.Transaction getSyncTransaction() {
        return this.mSyncTransaction.get();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    @android.annotation.NonNull
    public android.view.SurfaceControl.Transaction getPendingTransaction() {
        return this.mPendingTransaction.get();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void commitPendingTransaction() {
        this.mCommitTransactionRunnable.run();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashCreated(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl) {
        if (this.mOnAnimationLeashCreated != null) {
            this.mOnAnimationLeashCreated.accept(transaction, surfaceControl);
        }
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public void onAnimationLeashLost(android.view.SurfaceControl.Transaction transaction) {
        if (this.mOnAnimationLeashLost != null) {
            this.mOnAnimationLeashLost.accept(transaction);
        }
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    @android.annotation.NonNull
    public android.view.SurfaceControl.Builder makeAnimationLeash() {
        return this.mAnimationLeashFactory.get();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getAnimationLeashParent() {
        return this.mAnimationLeashParent;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    @android.annotation.Nullable
    public android.view.SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public android.view.SurfaceControl getParentSurfaceControl() {
        return this.mParentSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceWidth() {
        return this.mWidth;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public int getSurfaceHeight() {
        return this.mHeight;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public boolean shouldDeferAnimationFinish(java.lang.Runnable runnable) {
        if (this.mOnAnimationFinished != null) {
            this.mOnAnimationFinished.accept(runnable);
        }
        return this.mShouldDeferAnimationFinish;
    }

    static class Builder {

        @android.annotation.NonNull
        private java.util.function.Supplier<android.view.SurfaceControl.Builder> mAnimationLeashFactory;
        private java.lang.Runnable mCommitTransactionRunnable;

        @android.annotation.NonNull
        private java.util.function.Supplier<android.view.SurfaceControl.Transaction> mPendingTransactionSupplier;

        @android.annotation.NonNull
        private java.util.function.Supplier<android.view.SurfaceControl.Transaction> mSyncTransactionSupplier;
        private int mWidth = -1;
        private int mHeight = -1;
        private boolean mShouldDeferAnimationFinish = false;

        @android.annotation.Nullable
        private android.view.SurfaceControl mAnimationLeashParent = null;

        @android.annotation.Nullable
        private android.view.SurfaceControl mSurfaceControl = null;

        @android.annotation.Nullable
        private android.view.SurfaceControl mParentSurfaceControl = null;

        @android.annotation.Nullable
        private java.util.function.BiConsumer<android.view.SurfaceControl.Transaction, android.view.SurfaceControl> mOnAnimationLeashCreated = null;

        @android.annotation.Nullable
        private java.util.function.Consumer<android.view.SurfaceControl.Transaction> mOnAnimationLeashLost = null;

        @android.annotation.Nullable
        private java.util.function.Consumer<java.lang.Runnable> mOnAnimationFinished = null;

        Builder() {
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setCommitTransactionRunnable(@android.annotation.NonNull java.lang.Runnable runnable) {
            this.mCommitTransactionRunnable = runnable;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setOnAnimationLeashCreated(@android.annotation.Nullable java.util.function.BiConsumer<android.view.SurfaceControl.Transaction, android.view.SurfaceControl> biConsumer) {
            this.mOnAnimationLeashCreated = biConsumer;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setOnAnimationLeashLost(@android.annotation.Nullable java.util.function.Consumer<android.view.SurfaceControl.Transaction> consumer) {
            this.mOnAnimationLeashLost = consumer;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setSyncTransactionSupplier(@android.annotation.NonNull java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier) {
            this.mSyncTransactionSupplier = supplier;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setPendingTransactionSupplier(@android.annotation.NonNull java.util.function.Supplier<android.view.SurfaceControl.Transaction> supplier) {
            this.mPendingTransactionSupplier = supplier;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setAnimationLeashSupplier(@android.annotation.NonNull java.util.function.Supplier<android.view.SurfaceControl.Builder> supplier) {
            this.mAnimationLeashFactory = supplier;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setAnimationLeashParent(android.view.SurfaceControl surfaceControl) {
            this.mAnimationLeashParent = surfaceControl;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setSurfaceControl(@android.annotation.NonNull android.view.SurfaceControl surfaceControl) {
            this.mSurfaceControl = surfaceControl;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setParentSurfaceControl(android.view.SurfaceControl surfaceControl) {
            this.mParentSurfaceControl = surfaceControl;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setWidth(int i) {
            this.mWidth = i;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setHeight(int i) {
            this.mHeight = i;
            return this;
        }

        public com.android.server.wm.SimpleSurfaceAnimatable.Builder setShouldDeferAnimationFinish(boolean z, @android.annotation.Nullable java.util.function.Consumer<java.lang.Runnable> consumer) {
            this.mShouldDeferAnimationFinish = z;
            this.mOnAnimationFinished = consumer;
            return this;
        }

        public com.android.server.wm.SurfaceAnimator.Animatable build() {
            if (this.mSyncTransactionSupplier == null) {
                throw new java.lang.IllegalArgumentException("mSyncTransactionSupplier cannot be null");
            }
            if (this.mPendingTransactionSupplier == null) {
                throw new java.lang.IllegalArgumentException("mPendingTransactionSupplier cannot be null");
            }
            if (this.mAnimationLeashFactory == null) {
                throw new java.lang.IllegalArgumentException("mAnimationLeashFactory cannot be null");
            }
            if (this.mCommitTransactionRunnable == null) {
                throw new java.lang.IllegalArgumentException("mCommitTransactionRunnable cannot be null");
            }
            if (this.mSurfaceControl == null) {
                throw new java.lang.IllegalArgumentException("mSurfaceControl cannot be null");
            }
            return new com.android.server.wm.SimpleSurfaceAnimatable(this);
        }
    }
}
