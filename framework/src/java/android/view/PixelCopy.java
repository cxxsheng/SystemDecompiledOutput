package android.view;

/* loaded from: classes4.dex */
public final class PixelCopy {
    public static final int ERROR_DESTINATION_INVALID = 5;
    public static final int ERROR_SOURCE_INVALID = 4;
    public static final int ERROR_SOURCE_NO_DATA = 3;
    public static final int ERROR_TIMEOUT = 2;
    public static final int ERROR_UNKNOWN = 1;
    public static final int SUCCESS = 0;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CopyResultStatus {
    }

    public interface OnPixelCopyFinishedListener {
        void onPixelCopyFinished(int i);
    }

    public static void request(android.view.SurfaceView surfaceView, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        request(surfaceView.getHolder().getSurface(), bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(android.view.SurfaceView surfaceView, android.graphics.Rect rect, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        request(surfaceView.getHolder().getSurface(), rect, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(android.view.Surface surface, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        request(surface, (android.graphics.Rect) null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(android.view.Surface surface, android.graphics.Rect rect, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        validateBitmapDest(bitmap);
        if (!surface.isValid()) {
            throw new java.lang.IllegalArgumentException("Surface isn't valid, source.isValid() == false");
        }
        if (rect != null && rect.isEmpty()) {
            throw new java.lang.IllegalArgumentException("sourceRect is empty");
        }
        android.graphics.HardwareRenderer.copySurfaceInto(surface, new android.view.PixelCopy.AnonymousClass1(rect, bitmap, handler, onPixelCopyFinishedListener));
    }

    /* renamed from: android.view.PixelCopy$1, reason: invalid class name */
    class AnonymousClass1 extends android.graphics.HardwareRenderer.CopyRequest {
        final /* synthetic */ android.view.PixelCopy.OnPixelCopyFinishedListener val$listener;
        final /* synthetic */ android.os.Handler val$listenerThread;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass1(android.graphics.Rect rect, android.graphics.Bitmap bitmap, android.os.Handler handler, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener) {
            super(rect, bitmap);
            this.val$listenerThread = handler;
            this.val$listener = onPixelCopyFinishedListener;
        }

        @Override // android.graphics.HardwareRenderer.CopyRequest
        public void onCopyFinished(final int i) {
            android.os.Handler handler = this.val$listenerThread;
            final android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener = this.val$listener;
            handler.post(new java.lang.Runnable() { // from class: android.view.PixelCopy$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.PixelCopy.OnPixelCopyFinishedListener.this.onPixelCopyFinished(i);
                }
            });
        }
    }

    public static void request(android.view.Window window, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        request(window, (android.graphics.Rect) null, bitmap, onPixelCopyFinishedListener, handler);
    }

    public static void request(android.view.Window window, android.graphics.Rect rect, android.graphics.Bitmap bitmap, android.view.PixelCopy.OnPixelCopyFinishedListener onPixelCopyFinishedListener, android.os.Handler handler) {
        validateBitmapDest(bitmap);
        android.graphics.Rect rect2 = new android.graphics.Rect();
        request(sourceForWindow(window, rect2), adjustSourceRectForInsets(rect2, rect), bitmap, onPixelCopyFinishedListener, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void validateBitmapDest(android.graphics.Bitmap bitmap) {
        if (bitmap == null) {
            throw new java.lang.IllegalArgumentException("Bitmap cannot be null");
        }
        if (bitmap.isRecycled()) {
            throw new java.lang.IllegalArgumentException("Bitmap is recycled");
        }
        if (!bitmap.isMutable()) {
            throw new java.lang.IllegalArgumentException("Bitmap is immutable");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.view.Surface sourceForWindow(android.view.Window window, android.graphics.Rect rect) {
        android.view.Surface surface;
        if (window == null) {
            throw new java.lang.IllegalArgumentException("source is null");
        }
        if (window.peekDecorView() == null) {
            throw new java.lang.IllegalArgumentException("Only able to copy windows with decor views");
        }
        android.view.ViewRootImpl viewRootImpl = window.peekDecorView().getViewRootImpl();
        if (viewRootImpl == null) {
            surface = null;
        } else {
            surface = viewRootImpl.mSurface;
            android.graphics.Rect rect2 = viewRootImpl.mWindowAttributes.surfaceInsets;
            rect.set(rect2.left, rect2.top, viewRootImpl.mWidth + rect2.left, viewRootImpl.mHeight + rect2.top);
        }
        if (surface == null || !surface.isValid()) {
            throw new java.lang.IllegalArgumentException("Window doesn't have a backing surface!");
        }
        return surface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.graphics.Rect adjustSourceRectForInsets(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect2 == null) {
            return rect;
        }
        if (rect != null) {
            rect2.offset(rect.left, rect.top);
        }
        return rect2;
    }

    public static final class Result {
        private android.graphics.Bitmap mBitmap;
        private int mStatus;

        private Result(int i, android.graphics.Bitmap bitmap) {
            this.mStatus = i;
            this.mBitmap = bitmap;
        }

        public int getStatus() {
            return this.mStatus;
        }

        private void validateStatus() {
            if (this.mStatus != 0) {
                throw new java.lang.IllegalStateException("Copy request didn't succeed, status = " + this.mStatus);
            }
        }

        public android.graphics.Bitmap getBitmap() {
            validateStatus();
            return this.mBitmap;
        }
    }

    public static final class Request {
        private android.graphics.Bitmap mDest;
        private final android.view.Surface mSource;
        private final android.graphics.Rect mSourceInsets;
        private android.graphics.Rect mSrcRect;

        private Request(android.view.Surface surface, android.graphics.Rect rect) {
            this.mSource = surface;
            this.mSourceInsets = rect;
        }

        public static final class Builder {
            private android.view.PixelCopy.Request mRequest;

            private Builder(android.view.PixelCopy.Request request) {
                this.mRequest = request;
            }

            public static android.view.PixelCopy.Request.Builder ofWindow(android.view.Window window) {
                android.graphics.Rect rect = new android.graphics.Rect();
                return new android.view.PixelCopy.Request.Builder(new android.view.PixelCopy.Request(android.view.PixelCopy.sourceForWindow(window, rect), rect));
            }

            public static android.view.PixelCopy.Request.Builder ofWindow(android.view.View view) {
                android.view.Surface surface;
                if (view == null || !view.isAttachedToWindow()) {
                    throw new java.lang.IllegalArgumentException("View must not be null & must be attached to window");
                }
                android.graphics.Rect rect = new android.graphics.Rect();
                android.view.ViewRootImpl viewRootImpl = view.getViewRootImpl();
                if (viewRootImpl == null) {
                    surface = null;
                } else {
                    surface = viewRootImpl.mSurface;
                    rect.set(viewRootImpl.mWindowAttributes.surfaceInsets);
                }
                if (surface == null || !surface.isValid()) {
                    throw new java.lang.IllegalArgumentException("Window doesn't have a backing surface!");
                }
                return new android.view.PixelCopy.Request.Builder(new android.view.PixelCopy.Request(surface, rect));
            }

            public static android.view.PixelCopy.Request.Builder ofSurface(android.view.Surface surface) {
                if (surface == null || !surface.isValid()) {
                    throw new java.lang.IllegalArgumentException("Source must not be null & must be valid");
                }
                return new android.view.PixelCopy.Request.Builder(new android.view.PixelCopy.Request(surface, null));
            }

            public static android.view.PixelCopy.Request.Builder ofSurface(android.view.SurfaceView surfaceView) {
                return ofSurface(surfaceView.getHolder().getSurface());
            }

            private void requireNotBuilt() {
                if (this.mRequest == null) {
                    throw new java.lang.IllegalStateException("build() already called on this builder");
                }
            }

            public android.view.PixelCopy.Request.Builder setSourceRect(android.graphics.Rect rect) {
                requireNotBuilt();
                this.mRequest.mSrcRect = rect;
                return this;
            }

            public android.view.PixelCopy.Request.Builder setDestinationBitmap(android.graphics.Bitmap bitmap) {
                requireNotBuilt();
                if (bitmap != null) {
                    android.view.PixelCopy.validateBitmapDest(bitmap);
                }
                this.mRequest.mDest = bitmap;
                return this;
            }

            public android.view.PixelCopy.Request build() {
                requireNotBuilt();
                android.view.PixelCopy.Request request = this.mRequest;
                this.mRequest = null;
                return request;
            }
        }

        public android.graphics.Bitmap getDestinationBitmap() {
            return this.mDest;
        }

        public android.graphics.Rect getSourceRect() {
            return this.mSrcRect;
        }

        public void request(java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.PixelCopy.Result> consumer) {
            if (!this.mSource.isValid()) {
                executor.execute(new java.lang.Runnable() { // from class: android.view.PixelCopy$Request$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(new android.view.PixelCopy.Result(4, null));
                    }
                });
            } else {
                android.graphics.HardwareRenderer.copySurfaceInto(this.mSource, new android.view.PixelCopy.Request.AnonymousClass1(android.view.PixelCopy.adjustSourceRectForInsets(this.mSourceInsets, this.mSrcRect), this.mDest, executor, consumer));
            }
        }

        /* renamed from: android.view.PixelCopy$Request$1, reason: invalid class name */
        class AnonymousClass1 extends android.graphics.HardwareRenderer.CopyRequest {
            final /* synthetic */ java.util.concurrent.Executor val$callbackExecutor;
            final /* synthetic */ java.util.function.Consumer val$listener;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(android.graphics.Rect rect, android.graphics.Bitmap bitmap, java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
                super(rect, bitmap);
                this.val$callbackExecutor = executor;
                this.val$listener = consumer;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCopyFinished$0(java.util.function.Consumer consumer, int i) {
                consumer.accept(new android.view.PixelCopy.Result(i, this.mDestinationBitmap));
            }

            @Override // android.graphics.HardwareRenderer.CopyRequest
            public void onCopyFinished(final int i) {
                java.util.concurrent.Executor executor = this.val$callbackExecutor;
                final java.util.function.Consumer consumer = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.view.PixelCopy$Request$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.view.PixelCopy.Request.AnonymousClass1.this.lambda$onCopyFinished$0(consumer, i);
                    }
                });
            }
        }
    }

    public static void request(android.view.PixelCopy.Request request, java.util.concurrent.Executor executor, java.util.function.Consumer<android.view.PixelCopy.Result> consumer) {
        request.request(executor, consumer);
    }

    private PixelCopy() {
    }
}
