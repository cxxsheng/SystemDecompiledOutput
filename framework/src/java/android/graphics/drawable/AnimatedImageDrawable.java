package android.graphics.drawable;

/* loaded from: classes.dex */
public class AnimatedImageDrawable extends android.graphics.drawable.Drawable implements android.graphics.drawable.Animatable2 {
    private static final int FINISHED = -1;

    @java.lang.Deprecated
    public static final int LOOP_INFINITE = -1;
    public static final int REPEAT_INFINITE = -1;
    private static final int REPEAT_UNDEFINED = -2;
    private java.util.ArrayList<android.graphics.drawable.Animatable2.AnimationCallback> mAnimationCallbacks;
    private android.graphics.ColorFilter mColorFilter;
    private android.os.Handler mHandler;
    private int mIntrinsicHeight;
    private int mIntrinsicWidth;
    private java.lang.Runnable mRunnable;
    private boolean mStarting;
    private android.graphics.drawable.AnimatedImageDrawable.State mState;

    private static native long nCreate(long j, android.graphics.ImageDecoder imageDecoder, int i, int i2, long j2, boolean z, android.graphics.Rect rect) throws java.io.IOException;

    private static native long nDraw(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native int nGetAlpha(long j);

    @dalvik.annotation.optimization.FastNative
    private static native long nGetNativeFinalizer();

    @dalvik.annotation.optimization.FastNative
    private static native int nGetRepeatCount(long j);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nIsRunning(long j);

    @dalvik.annotation.optimization.FastNative
    private static native long nNativeByteSize(long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetAlpha(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetBounds(long j, android.graphics.Rect rect);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetColorFilter(long j, long j2);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetMirrored(long j, boolean z);

    private static native void nSetOnAnimationEndListener(long j, java.lang.ref.WeakReference<android.graphics.drawable.AnimatedImageDrawable> weakReference);

    @dalvik.annotation.optimization.FastNative
    private static native void nSetRepeatCount(long j, int i);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nStart(long j);

    @dalvik.annotation.optimization.FastNative
    private static native boolean nStop(long j);

    private class State {
        private final android.content.res.AssetFileDescriptor mAssetFd;
        private final java.io.InputStream mInputStream;
        final long mNativePtr;
        int[] mThemeAttrs = null;
        boolean mAutoMirrored = false;
        int mRepeatCount = -2;

        State(long j, java.io.InputStream inputStream, android.content.res.AssetFileDescriptor assetFileDescriptor) {
            this.mNativePtr = j;
            this.mInputStream = inputStream;
            this.mAssetFd = assetFileDescriptor;
        }
    }

    public void setRepeatCount(int i) {
        if (i < -1) {
            throw new java.lang.IllegalArgumentException("invalid value passed to setRepeatCount" + i);
        }
        if (this.mState.mRepeatCount != i) {
            this.mState.mRepeatCount = i;
            if (this.mState.mNativePtr != 0) {
                nSetRepeatCount(this.mState.mNativePtr, i);
            }
        }
    }

    @java.lang.Deprecated
    public void setLoopCount(int i) {
        setRepeatCount(i);
    }

    public int getRepeatCount() {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called getRepeatCount on empty AnimatedImageDrawable");
        }
        if (this.mState.mRepeatCount == -2) {
            this.mState.mRepeatCount = nGetRepeatCount(this.mState.mNativePtr);
        }
        return this.mState.mRepeatCount;
    }

    @java.lang.Deprecated
    public int getLoopCount(int i) {
        return getRepeatCount();
    }

    public AnimatedImageDrawable() {
        this.mAnimationCallbacks = null;
        this.mState = new android.graphics.drawable.AnimatedImageDrawable.State(0L, null, null);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(android.content.res.Resources resources, org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.content.res.Resources.Theme theme) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(obtainAttributes(resources, theme, attributeSet, com.android.internal.R.styleable.AnimatedImageDrawable), this.mSrcDensityOverride);
    }

    private void updateStateFromTypedArray(android.content.res.TypedArray typedArray, int i) throws org.xmlpull.v1.XmlPullParserException {
        int i2;
        android.graphics.drawable.AnimatedImageDrawable.State state = this.mState;
        android.content.res.Resources resources = typedArray.getResources();
        int resourceId = typedArray.getResourceId(0, 0);
        if (resourceId != 0) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            resources.getValueForDensity(resourceId, i, typedValue, true);
            if (i > 0 && typedValue.density > 0 && typedValue.density != 65535) {
                if (typedValue.density == i) {
                    typedValue.density = resources.getDisplayMetrics().densityDpi;
                } else {
                    typedValue.density = (typedValue.density * resources.getDisplayMetrics().densityDpi) / i;
                }
            }
            if (typedValue.density != 0) {
                if (typedValue.density == 65535) {
                    i2 = 0;
                } else {
                    i2 = typedValue.density;
                }
            } else {
                i2 = 160;
            }
            try {
                android.graphics.drawable.Drawable decodeDrawable = android.graphics.ImageDecoder.decodeDrawable(android.graphics.ImageDecoder.createSource(resources, resources.openRawResource(resourceId, typedValue), i2), new android.graphics.ImageDecoder.OnHeaderDecodedListener() { // from class: android.graphics.drawable.AnimatedImageDrawable$$ExternalSyntheticLambda3
                    @Override // android.graphics.ImageDecoder.OnHeaderDecodedListener
                    public final void onHeaderDecoded(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
                        android.graphics.drawable.AnimatedImageDrawable.lambda$updateStateFromTypedArray$0(imageDecoder, imageInfo, source);
                    }
                });
                if (!(decodeDrawable instanceof android.graphics.drawable.AnimatedImageDrawable)) {
                    throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <animated-image> did not decode animated");
                }
                int i3 = this.mState.mRepeatCount;
                android.graphics.drawable.AnimatedImageDrawable animatedImageDrawable = (android.graphics.drawable.AnimatedImageDrawable) decodeDrawable;
                this.mState = animatedImageDrawable.mState;
                animatedImageDrawable.mState = null;
                this.mIntrinsicWidth = animatedImageDrawable.mIntrinsicWidth;
                this.mIntrinsicHeight = animatedImageDrawable.mIntrinsicHeight;
                if (i3 != -2) {
                    setRepeatCount(i3);
                }
            } catch (java.io.IOException e) {
                throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <animated-image> requires a valid 'src' attribute", null, e);
            }
        }
        this.mState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (this.mState.mNativePtr == 0 && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[0] == 0)) {
            throw new org.xmlpull.v1.XmlPullParserException(typedArray.getPositionDescription() + ": <animated-image> requires a valid 'src' attribute");
        }
        this.mState.mAutoMirrored = typedArray.getBoolean(3, state.mAutoMirrored);
        int i4 = typedArray.getInt(1, -2);
        if (i4 != -2) {
            setRepeatCount(i4);
        }
        if (typedArray.getBoolean(2, false) && this.mState.mNativePtr != 0) {
            start();
        }
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$0(android.graphics.ImageDecoder imageDecoder, android.graphics.ImageDecoder.ImageInfo imageInfo, android.graphics.ImageDecoder.Source source) {
        if (!imageInfo.isAnimated()) {
            throw new java.lang.IllegalArgumentException("image is not animated");
        }
    }

    public AnimatedImageDrawable(long j, android.graphics.ImageDecoder imageDecoder, int i, int i2, long j2, boolean z, int i3, int i4, android.graphics.Rect rect, java.io.InputStream inputStream, android.content.res.AssetFileDescriptor assetFileDescriptor) throws java.io.IOException {
        this.mAnimationCallbacks = null;
        int scaleFromDensity = android.graphics.Bitmap.scaleFromDensity(i, i3, i4);
        int scaleFromDensity2 = android.graphics.Bitmap.scaleFromDensity(i2, i3, i4);
        if (rect == null) {
            this.mIntrinsicWidth = scaleFromDensity;
            this.mIntrinsicHeight = scaleFromDensity2;
        } else {
            rect.set(android.graphics.Bitmap.scaleFromDensity(rect.left, i3, i4), android.graphics.Bitmap.scaleFromDensity(rect.top, i3, i4), android.graphics.Bitmap.scaleFromDensity(rect.right, i3, i4), android.graphics.Bitmap.scaleFromDensity(rect.bottom, i3, i4));
            this.mIntrinsicWidth = rect.width();
            this.mIntrinsicHeight = rect.height();
        }
        this.mState = new android.graphics.drawable.AnimatedImageDrawable.State(nCreate(j, imageDecoder, scaleFromDensity, scaleFromDensity2, j2, z, rect), inputStream, assetFileDescriptor);
        libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.drawable.AnimatedImageDrawable.class.getClassLoader(), nGetNativeFinalizer(), nNativeByteSize(this.mState.mNativePtr)).registerNativeAllocation(this.mState, this.mState.mNativePtr);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(android.graphics.Canvas canvas) {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called draw on empty AnimatedImageDrawable");
        }
        if (this.mStarting) {
            this.mStarting = false;
            postOnAnimationStart();
        }
        long nDraw = nDraw(this.mState.mNativePtr, canvas.getNativeCanvasWrapper());
        if (nDraw > 0) {
            if (this.mRunnable == null) {
                this.mRunnable = new java.lang.Runnable() { // from class: android.graphics.drawable.AnimatedImageDrawable$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.graphics.drawable.AnimatedImageDrawable.this.invalidateSelf();
                    }
                };
            }
            scheduleSelf(this.mRunnable, nDraw + android.os.SystemClock.uptimeMillis());
        } else if (nDraw == -1) {
            postOnAnimationEnd();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i < 0 || i > 255) {
            throw new java.lang.IllegalArgumentException("Alpha must be between 0 and 255! provided " + i);
        }
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called setAlpha on empty AnimatedImageDrawable");
        }
        nSetAlpha(this.mState.mNativePtr, i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called getAlpha on empty AnimatedImageDrawable");
        }
        return nGetAlpha(this.mState.mNativePtr);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called setColorFilter on empty AnimatedImageDrawable");
        }
        if (colorFilter != this.mColorFilter) {
            this.mColorFilter = colorFilter;
            nSetColorFilter(this.mState.mNativePtr, colorFilter != null ? colorFilter.getNativeInstance() : 0L);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public android.graphics.ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAutoMirrored(boolean z) {
        if (this.mState.mAutoMirrored != z) {
            this.mState.mAutoMirrored = z;
            if (getLayoutDirection() == 1 && this.mState.mNativePtr != 0) {
                nSetMirrored(this.mState.mNativePtr, z);
                invalidateSelf();
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLayoutDirectionChanged(int i) {
        if (!this.mState.mAutoMirrored || this.mState.mNativePtr == 0) {
            return false;
        }
        nSetMirrored(this.mState.mNativePtr, i == 1);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public final boolean isAutoMirrored() {
        return this.mState.mAutoMirrored;
    }

    @Override // android.graphics.drawable.Animatable
    public boolean isRunning() {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called isRunning on empty AnimatedImageDrawable");
        }
        return nIsRunning(this.mState.mNativePtr);
    }

    @Override // android.graphics.drawable.Animatable
    public void start() {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called start on empty AnimatedImageDrawable");
        }
        if (nStart(this.mState.mNativePtr)) {
            this.mStarting = true;
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Animatable
    public void stop() {
        if (this.mState.mNativePtr == 0) {
            throw new java.lang.IllegalStateException("called stop on empty AnimatedImageDrawable");
        }
        if (nStop(this.mState.mNativePtr)) {
            postOnAnimationEnd();
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public void registerAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null) {
            return;
        }
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new java.util.ArrayList<>();
            nSetOnAnimationEndListener(this.mState.mNativePtr, new java.lang.ref.WeakReference(this));
        }
        if (!this.mAnimationCallbacks.contains(animationCallback)) {
            this.mAnimationCallbacks.add(animationCallback);
        }
    }

    @Override // android.graphics.drawable.Animatable2
    public boolean unregisterAnimationCallback(android.graphics.drawable.Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null || this.mAnimationCallbacks == null || !this.mAnimationCallbacks.remove(animationCallback)) {
            return false;
        }
        if (this.mAnimationCallbacks.isEmpty()) {
            clearAnimationCallbacks();
            return true;
        }
        return true;
    }

    @Override // android.graphics.drawable.Animatable2
    public void clearAnimationCallbacks() {
        if (this.mAnimationCallbacks != null) {
            this.mAnimationCallbacks = null;
            nSetOnAnimationEndListener(this.mState.mNativePtr, null);
        }
    }

    private void postOnAnimationStart() {
        if (this.mAnimationCallbacks == null) {
            return;
        }
        getHandler().post(new java.lang.Runnable() { // from class: android.graphics.drawable.AnimatedImageDrawable$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                android.graphics.drawable.AnimatedImageDrawable.this.lambda$postOnAnimationStart$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postOnAnimationStart$1() {
        java.util.Iterator<android.graphics.drawable.Animatable2.AnimationCallback> it = this.mAnimationCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onAnimationStart(this);
        }
    }

    private void postOnAnimationEnd() {
        if (this.mAnimationCallbacks == null) {
            return;
        }
        getHandler().post(new java.lang.Runnable() { // from class: android.graphics.drawable.AnimatedImageDrawable$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                android.graphics.drawable.AnimatedImageDrawable.this.lambda$postOnAnimationEnd$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$postOnAnimationEnd$2() {
        java.util.Iterator<android.graphics.drawable.Animatable2.AnimationCallback> it = this.mAnimationCallbacks.iterator();
        while (it.hasNext()) {
            it.next().onAnimationEnd(this);
        }
    }

    private android.os.Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new android.os.Handler(android.os.Looper.getMainLooper());
        }
        return this.mHandler;
    }

    private static void callOnAnimationEnd(java.lang.ref.WeakReference<android.graphics.drawable.AnimatedImageDrawable> weakReference) {
        android.graphics.drawable.AnimatedImageDrawable animatedImageDrawable = weakReference.get();
        if (animatedImageDrawable != null) {
            animatedImageDrawable.onAnimationEnd();
        }
    }

    private void onAnimationEnd() {
        if (this.mAnimationCallbacks != null) {
            java.util.Iterator<android.graphics.drawable.Animatable2.AnimationCallback> it = this.mAnimationCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onAnimationEnd(this);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(android.graphics.Rect rect) {
        if (this.mState.mNativePtr != 0) {
            nSetBounds(this.mState.mNativePtr, rect);
        }
    }
}
