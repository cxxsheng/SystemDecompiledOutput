package android.window;

/* loaded from: classes4.dex */
public class ScreenCapture {
    private static final int SCREENSHOT_WAIT_TIME_S = android.os.Build.HW_TIMEOUT_MULTIPLIER * 4;
    private static final java.lang.String TAG = "ScreenCapture";

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeListenerFinalizer();

    private static native int nativeCaptureDisplay(android.window.ScreenCapture.DisplayCaptureArgs displayCaptureArgs, long j);

    private static native int nativeCaptureLayers(android.window.ScreenCapture.LayerCaptureArgs layerCaptureArgs, long j, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateScreenCaptureListener(java.util.function.ObjIntConsumer<android.window.ScreenCapture.ScreenshotHardwareBuffer> objIntConsumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadListenerFromParcel(android.os.Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteListenerToParcel(long j, android.os.Parcel parcel);

    public static int captureDisplay(android.window.ScreenCapture.DisplayCaptureArgs displayCaptureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        return nativeCaptureDisplay(displayCaptureArgs, screenCaptureListener.mNativeObject);
    }

    public static android.window.ScreenCapture.ScreenshotHardwareBuffer captureDisplay(android.window.ScreenCapture.DisplayCaptureArgs displayCaptureArgs) {
        android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = createSyncCaptureListener();
        if (captureDisplay(displayCaptureArgs, createSyncCaptureListener) != 0) {
            return null;
        }
        try {
            return createSyncCaptureListener.getBuffer();
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, float f) {
        return captureLayers(surfaceControl, rect, f, 1);
    }

    public static android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, float f, int i) {
        return captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(f).setPixelFormat(i).build());
    }

    public static android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayers(android.window.ScreenCapture.LayerCaptureArgs layerCaptureArgs) {
        android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener = createSyncCaptureListener();
        if (nativeCaptureLayers(layerCaptureArgs, createSyncCaptureListener.mNativeObject, com.android.window.flags.Flags.syncScreenCapture()) != 0) {
            return null;
        }
        try {
            return createSyncCaptureListener.getBuffer();
        } catch (java.lang.Exception e) {
            return null;
        }
    }

    public static android.window.ScreenCapture.ScreenshotHardwareBuffer captureLayersExcluding(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, float f, int i, android.view.SurfaceControl[] surfaceControlArr) {
        return captureLayers(new android.window.ScreenCapture.LayerCaptureArgs.Builder(surfaceControl).setSourceCrop(rect).setFrameScale(f).setPixelFormat(i).setExcludeLayers(surfaceControlArr).build());
    }

    public static int captureLayers(android.window.ScreenCapture.LayerCaptureArgs layerCaptureArgs, android.window.ScreenCapture.ScreenCaptureListener screenCaptureListener) {
        return nativeCaptureLayers(layerCaptureArgs, screenCaptureListener.mNativeObject, false);
    }

    public static class ScreenshotHardwareBuffer {
        private final android.graphics.ColorSpace mColorSpace;
        private final boolean mContainsHdrLayers;
        private final boolean mContainsSecureLayers;
        private final android.hardware.HardwareBuffer mHardwareBuffer;

        public ScreenshotHardwareBuffer(android.hardware.HardwareBuffer hardwareBuffer, android.graphics.ColorSpace colorSpace, boolean z, boolean z2) {
            this.mHardwareBuffer = hardwareBuffer;
            this.mColorSpace = colorSpace;
            this.mContainsSecureLayers = z;
            this.mContainsHdrLayers = z2;
        }

        private static android.window.ScreenCapture.ScreenshotHardwareBuffer createFromNative(android.hardware.HardwareBuffer hardwareBuffer, int i, boolean z, boolean z2) {
            android.graphics.ColorSpace fromDataSpace = android.graphics.ColorSpace.getFromDataSpace(i);
            if (fromDataSpace == null) {
                fromDataSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
            }
            return new android.window.ScreenCapture.ScreenshotHardwareBuffer(hardwareBuffer, fromDataSpace, z, z2);
        }

        public android.graphics.ColorSpace getColorSpace() {
            return this.mColorSpace;
        }

        public android.hardware.HardwareBuffer getHardwareBuffer() {
            return this.mHardwareBuffer;
        }

        public boolean containsSecureLayers() {
            return this.mContainsSecureLayers;
        }

        public boolean containsHdrLayers() {
            return this.mContainsHdrLayers;
        }

        public android.graphics.Bitmap asBitmap() {
            if (this.mHardwareBuffer == null) {
                android.util.Log.w(android.window.ScreenCapture.TAG, "Failed to take screenshot. Null screenshot object");
                return null;
            }
            return android.graphics.Bitmap.wrapHardwareBuffer(this.mHardwareBuffer, this.mColorSpace);
        }
    }

    public static class CaptureArgs implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.window.ScreenCapture.CaptureArgs> CREATOR = new android.os.Parcelable.Creator<android.window.ScreenCapture.CaptureArgs>() { // from class: android.window.ScreenCapture.CaptureArgs.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.ScreenCapture.CaptureArgs createFromParcel(android.os.Parcel parcel) {
                return new android.window.ScreenCapture.CaptureArgs(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.ScreenCapture.CaptureArgs[] newArray(int i) {
                return new android.window.ScreenCapture.CaptureArgs[i];
            }
        };
        public final boolean mAllowProtected;
        public final boolean mCaptureSecureLayers;
        final android.view.SurfaceControl[] mExcludeLayers;
        public final float mFrameScaleX;
        public final float mFrameScaleY;
        public final boolean mGrayscale;
        public final boolean mHintForSeamlessTransition;
        public final int mPixelFormat;
        public final android.graphics.Rect mSourceCrop;
        public final long mUid;

        private CaptureArgs(android.window.ScreenCapture.CaptureArgs.Builder<? extends android.window.ScreenCapture.CaptureArgs.Builder<?>> builder) {
            this.mSourceCrop = new android.graphics.Rect();
            this.mPixelFormat = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mPixelFormat;
            this.mSourceCrop.set(((android.window.ScreenCapture.CaptureArgs.Builder) builder).mSourceCrop);
            this.mFrameScaleX = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mFrameScaleX;
            this.mFrameScaleY = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mFrameScaleY;
            this.mCaptureSecureLayers = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mCaptureSecureLayers;
            this.mAllowProtected = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mAllowProtected;
            this.mUid = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mUid;
            this.mGrayscale = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mGrayscale;
            this.mExcludeLayers = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mExcludeLayers;
            this.mHintForSeamlessTransition = ((android.window.ScreenCapture.CaptureArgs.Builder) builder).mHintForSeamlessTransition;
        }

        private CaptureArgs(android.os.Parcel parcel) {
            this.mSourceCrop = new android.graphics.Rect();
            this.mPixelFormat = parcel.readInt();
            this.mSourceCrop.readFromParcel(parcel);
            this.mFrameScaleX = parcel.readFloat();
            this.mFrameScaleY = parcel.readFloat();
            this.mCaptureSecureLayers = parcel.readBoolean();
            this.mAllowProtected = parcel.readBoolean();
            this.mUid = parcel.readLong();
            this.mGrayscale = parcel.readBoolean();
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.mExcludeLayers = new android.view.SurfaceControl[readInt];
                for (int i = 0; i < readInt; i++) {
                    this.mExcludeLayers[i] = android.view.SurfaceControl.CREATOR.createFromParcel(parcel);
                }
            } else {
                this.mExcludeLayers = null;
            }
            this.mHintForSeamlessTransition = parcel.readBoolean();
        }

        public void release() {
            if (this.mExcludeLayers == null || this.mExcludeLayers.length == 0) {
                return;
            }
            for (android.view.SurfaceControl surfaceControl : this.mExcludeLayers) {
                if (surfaceControl != null) {
                    surfaceControl.release();
                }
            }
        }

        private long[] getNativeExcludeLayers() {
            if (this.mExcludeLayers == null || this.mExcludeLayers.length == 0) {
                return new long[0];
            }
            long[] jArr = new long[this.mExcludeLayers.length];
            for (int i = 0; i < this.mExcludeLayers.length; i++) {
                jArr[i] = this.mExcludeLayers[i].mNativeObject;
            }
            return jArr;
        }

        public static class Builder<T extends android.window.ScreenCapture.CaptureArgs.Builder<T>> {
            private boolean mAllowProtected;
            private boolean mCaptureSecureLayers;
            private android.view.SurfaceControl[] mExcludeLayers;
            private boolean mGrayscale;
            private boolean mHintForSeamlessTransition;
            private int mPixelFormat = 1;
            private final android.graphics.Rect mSourceCrop = new android.graphics.Rect();
            private float mFrameScaleX = 1.0f;
            private float mFrameScaleY = 1.0f;
            private long mUid = -1;

            public android.window.ScreenCapture.CaptureArgs build() {
                return new android.window.ScreenCapture.CaptureArgs(this);
            }

            public T setPixelFormat(int i) {
                this.mPixelFormat = i;
                return getThis();
            }

            public T setSourceCrop(android.graphics.Rect rect) {
                if (rect == null) {
                    this.mSourceCrop.setEmpty();
                } else {
                    this.mSourceCrop.set(rect);
                }
                return getThis();
            }

            public T setFrameScale(float f) {
                this.mFrameScaleX = f;
                this.mFrameScaleY = f;
                return getThis();
            }

            public T setFrameScale(float f, float f2) {
                this.mFrameScaleX = f;
                this.mFrameScaleY = f2;
                return getThis();
            }

            public T setCaptureSecureLayers(boolean z) {
                this.mCaptureSecureLayers = z;
                return getThis();
            }

            public T setAllowProtected(boolean z) {
                this.mAllowProtected = z;
                return getThis();
            }

            public T setUid(long j) {
                this.mUid = j;
                return getThis();
            }

            public T setGrayscale(boolean z) {
                this.mGrayscale = z;
                return getThis();
            }

            public T setExcludeLayers(android.view.SurfaceControl[] surfaceControlArr) {
                this.mExcludeLayers = surfaceControlArr;
                return getThis();
            }

            public T setHintForSeamlessTransition(boolean z) {
                this.mHintForSeamlessTransition = z;
                return getThis();
            }

            T getThis() {
                return this;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mPixelFormat);
            this.mSourceCrop.writeToParcel(parcel, i);
            parcel.writeFloat(this.mFrameScaleX);
            parcel.writeFloat(this.mFrameScaleY);
            parcel.writeBoolean(this.mCaptureSecureLayers);
            parcel.writeBoolean(this.mAllowProtected);
            parcel.writeLong(this.mUid);
            parcel.writeBoolean(this.mGrayscale);
            if (this.mExcludeLayers != null) {
                parcel.writeInt(this.mExcludeLayers.length);
                for (android.view.SurfaceControl surfaceControl : this.mExcludeLayers) {
                    surfaceControl.writeToParcel(parcel, i);
                }
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBoolean(this.mHintForSeamlessTransition);
        }
    }

    public static class DisplayCaptureArgs extends android.window.ScreenCapture.CaptureArgs {
        private final android.os.IBinder mDisplayToken;
        private final int mHeight;
        private final int mWidth;

        private DisplayCaptureArgs(android.window.ScreenCapture.DisplayCaptureArgs.Builder builder) {
            super(builder);
            this.mDisplayToken = builder.mDisplayToken;
            this.mWidth = builder.mWidth;
            this.mHeight = builder.mHeight;
        }

        public static class Builder extends android.window.ScreenCapture.CaptureArgs.Builder<android.window.ScreenCapture.DisplayCaptureArgs.Builder> {
            private android.os.IBinder mDisplayToken;
            private int mHeight;
            private int mWidth;

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public android.window.ScreenCapture.DisplayCaptureArgs build() {
                if (this.mDisplayToken == null) {
                    throw new java.lang.IllegalStateException("Can't take screenshot with null display token");
                }
                return new android.window.ScreenCapture.DisplayCaptureArgs(this);
            }

            public Builder(android.os.IBinder iBinder) {
                setDisplayToken(iBinder);
            }

            public android.window.ScreenCapture.DisplayCaptureArgs.Builder setDisplayToken(android.os.IBinder iBinder) {
                this.mDisplayToken = iBinder;
                return this;
            }

            public android.window.ScreenCapture.DisplayCaptureArgs.Builder setSize(int i, int i2) {
                this.mWidth = i;
                this.mHeight = i2;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public android.window.ScreenCapture.DisplayCaptureArgs.Builder getThis() {
                return this;
            }
        }
    }

    public static class LayerCaptureArgs extends android.window.ScreenCapture.CaptureArgs {
        private final boolean mChildrenOnly;
        private final long mNativeLayer;

        private LayerCaptureArgs(android.window.ScreenCapture.LayerCaptureArgs.Builder builder) {
            super(builder);
            this.mChildrenOnly = builder.mChildrenOnly;
            this.mNativeLayer = builder.mLayer.mNativeObject;
        }

        public static class Builder extends android.window.ScreenCapture.CaptureArgs.Builder<android.window.ScreenCapture.LayerCaptureArgs.Builder> {
            private boolean mChildrenOnly = true;
            private android.view.SurfaceControl mLayer;

            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public android.window.ScreenCapture.LayerCaptureArgs build() {
                if (this.mLayer == null) {
                    throw new java.lang.IllegalStateException("Can't take screenshot with null layer");
                }
                return new android.window.ScreenCapture.LayerCaptureArgs(this);
            }

            public Builder(android.view.SurfaceControl surfaceControl, android.window.ScreenCapture.CaptureArgs captureArgs) {
                setLayer(surfaceControl);
                setPixelFormat(captureArgs.mPixelFormat);
                setSourceCrop(captureArgs.mSourceCrop);
                setFrameScale(captureArgs.mFrameScaleX, captureArgs.mFrameScaleY);
                setCaptureSecureLayers(captureArgs.mCaptureSecureLayers);
                setAllowProtected(captureArgs.mAllowProtected);
                setUid(captureArgs.mUid);
                setGrayscale(captureArgs.mGrayscale);
                setExcludeLayers(captureArgs.mExcludeLayers);
                setHintForSeamlessTransition(captureArgs.mHintForSeamlessTransition);
            }

            public Builder(android.view.SurfaceControl surfaceControl) {
                setLayer(surfaceControl);
            }

            public android.window.ScreenCapture.LayerCaptureArgs.Builder setLayer(android.view.SurfaceControl surfaceControl) {
                this.mLayer = surfaceControl;
                return this;
            }

            public android.window.ScreenCapture.LayerCaptureArgs.Builder setChildrenOnly(boolean z) {
                this.mChildrenOnly = z;
                return this;
            }

            /* JADX INFO: Access modifiers changed from: package-private */
            @Override // android.window.ScreenCapture.CaptureArgs.Builder
            public android.window.ScreenCapture.LayerCaptureArgs.Builder getThis() {
                return this;
            }
        }
    }

    public static class ScreenCaptureListener implements android.os.Parcelable {
        final long mNativeObject;
        private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.window.ScreenCapture.ScreenCaptureListener.class.getClassLoader(), android.window.ScreenCapture.getNativeListenerFinalizer());
        public static final android.os.Parcelable.Creator<android.window.ScreenCapture.ScreenCaptureListener> CREATOR = new android.os.Parcelable.Creator<android.window.ScreenCapture.ScreenCaptureListener>() { // from class: android.window.ScreenCapture.ScreenCaptureListener.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.ScreenCapture.ScreenCaptureListener createFromParcel(android.os.Parcel parcel) {
                return new android.window.ScreenCapture.ScreenCaptureListener(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.window.ScreenCapture.ScreenCaptureListener[] newArray(int i) {
                return new android.window.ScreenCapture.ScreenCaptureListener[0];
            }
        };

        public ScreenCaptureListener(java.util.function.ObjIntConsumer<android.window.ScreenCapture.ScreenshotHardwareBuffer> objIntConsumer) {
            this.mNativeObject = android.window.ScreenCapture.nativeCreateScreenCaptureListener(objIntConsumer);
            sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }

        private ScreenCaptureListener(android.os.Parcel parcel) {
            if (parcel.readBoolean()) {
                this.mNativeObject = android.window.ScreenCapture.nativeReadListenerFromParcel(parcel);
                sRegistry.registerNativeAllocation(this, this.mNativeObject);
            } else {
                this.mNativeObject = 0L;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mNativeObject == 0) {
                parcel.writeBoolean(false);
            } else {
                parcel.writeBoolean(true);
                android.window.ScreenCapture.nativeWriteListenerToParcel(this.mNativeObject, parcel);
            }
        }
    }

    public static android.window.ScreenCapture.SynchronousScreenCaptureListener createSyncCaptureListener() {
        final android.window.ScreenCapture.ScreenshotHardwareBuffer[] screenshotHardwareBufferArr = new android.window.ScreenCapture.ScreenshotHardwareBuffer[1];
        final java.util.concurrent.CountDownLatch countDownLatch = new java.util.concurrent.CountDownLatch(1);
        final java.util.function.ObjIntConsumer objIntConsumer = new java.util.function.ObjIntConsumer() { // from class: android.window.ScreenCapture$$ExternalSyntheticLambda0
            @Override // java.util.function.ObjIntConsumer
            public final void accept(java.lang.Object obj, int i) {
                android.window.ScreenCapture.lambda$createSyncCaptureListener$0(screenshotHardwareBufferArr, countDownLatch, (android.window.ScreenCapture.ScreenshotHardwareBuffer) obj, i);
            }
        };
        return new android.window.ScreenCapture.SynchronousScreenCaptureListener(objIntConsumer) { // from class: android.window.ScreenCapture.1
            private java.util.function.ObjIntConsumer<android.window.ScreenCapture.ScreenshotHardwareBuffer> mConsumer;

            {
                this.mConsumer = objIntConsumer;
            }

            @Override // android.window.ScreenCapture.SynchronousScreenCaptureListener
            public android.window.ScreenCapture.ScreenshotHardwareBuffer getBuffer() {
                try {
                    if (!countDownLatch.await(android.window.ScreenCapture.SCREENSHOT_WAIT_TIME_S, java.util.concurrent.TimeUnit.SECONDS)) {
                        android.util.Log.e(android.window.ScreenCapture.TAG, "Timed out waiting for screenshot results");
                        return null;
                    }
                    return screenshotHardwareBufferArr[0];
                } catch (java.lang.Exception e) {
                    android.util.Log.e(android.window.ScreenCapture.TAG, "Failed to wait for screen capture result", e);
                    return null;
                }
            }
        };
    }

    static /* synthetic */ void lambda$createSyncCaptureListener$0(android.window.ScreenCapture.ScreenshotHardwareBuffer[] screenshotHardwareBufferArr, java.util.concurrent.CountDownLatch countDownLatch, android.window.ScreenCapture.ScreenshotHardwareBuffer screenshotHardwareBuffer, int i) {
        if (i != 0) {
            screenshotHardwareBufferArr[0] = null;
            android.util.Log.e(TAG, "Failed to generate screen capture. Error code: " + i);
        } else {
            screenshotHardwareBufferArr[0] = screenshotHardwareBuffer;
        }
        countDownLatch.countDown();
    }

    public static abstract class SynchronousScreenCaptureListener extends android.window.ScreenCapture.ScreenCaptureListener {
        public abstract android.window.ScreenCapture.ScreenshotHardwareBuffer getBuffer();

        SynchronousScreenCaptureListener(java.util.function.ObjIntConsumer<android.window.ScreenCapture.ScreenshotHardwareBuffer> objIntConsumer) {
            super(objIntConsumer);
        }
    }
}
