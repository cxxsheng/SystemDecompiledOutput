package android.graphics;

/* loaded from: classes.dex */
public final class RenderNode {
    public static final int USAGE_BACKGROUND = 1;
    public static final int USAGE_UNKNOWN = 0;
    private final android.graphics.RenderNode.AnimationHost mAnimationHost;
    private android.graphics.RenderNode.CompositePositionUpdateListener mCompositePositionUpdateListener;
    private android.graphics.RecordingCanvas mCurrentRecordingCanvas;
    public final long mNativeRenderNode;

    public interface AnimationHost {
        boolean isAttached();

        void registerAnimatingRenderNode(android.graphics.RenderNode renderNode);

        void registerVectorDrawableAnimator(android.view.NativeVectorDrawableAnimator nativeVectorDrawableAnimator);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface UsageHint {
    }

    private static native void nAddAnimator(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nClearStretch(long j);

    private static native long nCreate(java.lang.String str);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nDiscardDisplayList(long j);

    private static native void nEndAllAnimators(long j);

    private static native void nForceEndAnimators(long j);

    private static native int nGetAllocatedSize(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetAllowForceDark(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetAlpha(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetAmbientShadowColor(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetAnimationMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetBottom(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetCameraDistance(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetClipToBounds(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nGetClipToOutline(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetElevation(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetHeight(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nGetInverseTransformMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetLayerType(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetLeft(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetNativeFinalizer();

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetPivotX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetPivotY(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetRight(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetRotation(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetRotationX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetRotationY(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetScaleX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetScaleY(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetSpotShadowColor(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetTop(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nGetTransformMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTranslationX(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTranslationY(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native float nGetTranslationZ(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native long nGetUniqueId(long j);

    private static native int nGetUsageSize(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native int nGetWidth(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nHasIdentityMatrix(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nHasOverlappingRendering(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nHasShadow(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsPivotExplicitlySet(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nIsValid(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nOffsetLeftAndRight(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nOffsetTopAndBottom(long j, int i);

    private static native void nOutput(long j);

    private static native void nRequestPositionUpdates(long j, java.lang.ref.WeakReference<android.graphics.RenderNode.PositionUpdateListener> weakReference);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nResetPivot(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetAllowForceDark(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetAlpha(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetAmbientShadowColor(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetAnimationMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetBackdropRenderEffect(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetBottom(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetCameraDistance(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetClipBounds(long j, int i, int i2, int i3, int i4);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetClipBoundsEmpty(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetClipToBounds(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetClipToOutline(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetElevation(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetHasOverlappingRendering(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetIsTextureView(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetLayerPaint(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetLayerType(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetLeft(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetLeftTopRightBottom(long j, int i, int i2, int i3, int i4);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetOutlineEmpty(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetOutlineNone(long j);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetOutlinePath(long j, long j2, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetOutlineRoundRect(long j, int i, int i2, int i3, int i4, float f, float f2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetPivotX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetPivotY(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetProjectBackwards(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetProjectionReceiver(long j, boolean z);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRenderEffect(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRevealClip(long j, boolean z, float f, float f2, float f3);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRight(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRotation(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRotationX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetRotationY(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetScaleX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetScaleY(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetSpotShadowColor(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetStaticMatrix(long j, long j2);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetTop(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetTranslationX(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetTranslationY(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nSetTranslationZ(long j, float f);

    @dalvik.annotation.optimization.CriticalNative
    private static native void nSetUsageHint(long j, int i);

    @dalvik.annotation.optimization.CriticalNative
    private static native boolean nStretch(long j, float f, float f2, float f3, float f4);

    private static class NoImagePreloadHolder {
        public static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.graphics.RenderNode.class.getClassLoader(), android.graphics.RenderNode.nGetNativeFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    public RenderNode(java.lang.String str) {
        this(str, null);
    }

    private RenderNode(java.lang.String str, android.graphics.RenderNode.AnimationHost animationHost) {
        this.mNativeRenderNode = nCreate(str);
        android.graphics.RenderNode.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeRenderNode);
        this.mAnimationHost = animationHost;
    }

    private RenderNode(long j) {
        this.mNativeRenderNode = j;
        android.graphics.RenderNode.NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, this.mNativeRenderNode);
        this.mAnimationHost = null;
    }

    public static android.graphics.RenderNode create(java.lang.String str, android.graphics.RenderNode.AnimationHost animationHost) {
        return new android.graphics.RenderNode(str, animationHost);
    }

    public static android.graphics.RenderNode adopt(long j) {
        return new android.graphics.RenderNode(j);
    }

    public interface PositionUpdateListener {
        void positionChanged(long j, int i, int i2, int i3, int i4);

        void positionLost(long j);

        default void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            positionChanged(j, i, i2, i3, i4);
        }

        static boolean callPositionChanged(java.lang.ref.WeakReference<android.graphics.RenderNode.PositionUpdateListener> weakReference, long j, int i, int i2, int i3, int i4) {
            android.graphics.RenderNode.PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener != null) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4);
                return true;
            }
            return false;
        }

        static boolean callPositionChanged2(java.lang.ref.WeakReference<android.graphics.RenderNode.PositionUpdateListener> weakReference, long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            android.graphics.RenderNode.PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener != null) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4, i5, i6, i7, i8);
                return true;
            }
            return false;
        }

        default void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        }

        static boolean callApplyStretch(java.lang.ref.WeakReference<android.graphics.RenderNode.PositionUpdateListener> weakReference, long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            android.graphics.RenderNode.PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener != null) {
                positionUpdateListener.applyStretch(j, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
                return true;
            }
            return false;
        }

        static boolean callPositionLost(java.lang.ref.WeakReference<android.graphics.RenderNode.PositionUpdateListener> weakReference, long j) {
            android.graphics.RenderNode.PositionUpdateListener positionUpdateListener = weakReference.get();
            if (positionUpdateListener != null) {
                positionUpdateListener.positionLost(j);
                return true;
            }
            return false;
        }
    }

    private static final class CompositePositionUpdateListener implements android.graphics.RenderNode.PositionUpdateListener {
        private static final android.graphics.RenderNode.PositionUpdateListener[] sEmpty = new android.graphics.RenderNode.PositionUpdateListener[0];
        private final android.graphics.RenderNode.PositionUpdateListener[] mListeners;

        CompositePositionUpdateListener(android.graphics.RenderNode.PositionUpdateListener... positionUpdateListenerArr) {
            this.mListeners = positionUpdateListenerArr == null ? sEmpty : positionUpdateListenerArr;
        }

        public android.graphics.RenderNode.CompositePositionUpdateListener with(android.graphics.RenderNode.PositionUpdateListener positionUpdateListener) {
            return new android.graphics.RenderNode.CompositePositionUpdateListener((android.graphics.RenderNode.PositionUpdateListener[]) com.android.internal.util.ArrayUtils.appendElement(android.graphics.RenderNode.PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }

        public android.graphics.RenderNode.CompositePositionUpdateListener without(android.graphics.RenderNode.PositionUpdateListener positionUpdateListener) {
            return new android.graphics.RenderNode.CompositePositionUpdateListener((android.graphics.RenderNode.PositionUpdateListener[]) com.android.internal.util.ArrayUtils.removeElement(android.graphics.RenderNode.PositionUpdateListener.class, this.mListeners, positionUpdateListener));
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4) {
            for (android.graphics.RenderNode.PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionChanged(long j, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            for (android.graphics.RenderNode.PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionChanged(j, i, i2, i3, i4, i5, i6, i7, i8);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void positionLost(long j) {
            for (android.graphics.RenderNode.PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.positionLost(j);
            }
        }

        @Override // android.graphics.RenderNode.PositionUpdateListener
        public void applyStretch(long j, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            for (android.graphics.RenderNode.PositionUpdateListener positionUpdateListener : this.mListeners) {
                positionUpdateListener.applyStretch(j, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            }
        }
    }

    public void addPositionUpdateListener(android.graphics.RenderNode.PositionUpdateListener positionUpdateListener) {
        android.graphics.RenderNode.CompositePositionUpdateListener with;
        android.graphics.RenderNode.CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        if (compositePositionUpdateListener == null) {
            with = new android.graphics.RenderNode.CompositePositionUpdateListener(positionUpdateListener);
        } else {
            with = compositePositionUpdateListener.with(positionUpdateListener);
        }
        this.mCompositePositionUpdateListener = with;
        nRequestPositionUpdates(this.mNativeRenderNode, new java.lang.ref.WeakReference(with));
    }

    public void removePositionUpdateListener(android.graphics.RenderNode.PositionUpdateListener positionUpdateListener) {
        android.graphics.RenderNode.CompositePositionUpdateListener compositePositionUpdateListener = this.mCompositePositionUpdateListener;
        if (compositePositionUpdateListener != null) {
            android.graphics.RenderNode.CompositePositionUpdateListener without = compositePositionUpdateListener.without(positionUpdateListener);
            this.mCompositePositionUpdateListener = without;
            nRequestPositionUpdates(this.mNativeRenderNode, new java.lang.ref.WeakReference(without));
        }
    }

    public android.graphics.RecordingCanvas beginRecording(int i, int i2) {
        if (this.mCurrentRecordingCanvas != null) {
            throw new java.lang.IllegalStateException("Recording currently in progress - missing #endRecording() call?");
        }
        this.mCurrentRecordingCanvas = android.graphics.RecordingCanvas.obtain(this, i, i2);
        return this.mCurrentRecordingCanvas;
    }

    public android.graphics.RecordingCanvas beginRecording() {
        return beginRecording(nGetWidth(this.mNativeRenderNode), nGetHeight(this.mNativeRenderNode));
    }

    public void endRecording() {
        if (this.mCurrentRecordingCanvas == null) {
            throw new java.lang.IllegalStateException("No recording in progress, forgot to call #beginRecording()?");
        }
        android.graphics.RecordingCanvas recordingCanvas = this.mCurrentRecordingCanvas;
        this.mCurrentRecordingCanvas = null;
        recordingCanvas.finishRecording(this);
        recordingCanvas.recycle();
    }

    @java.lang.Deprecated
    public android.graphics.RecordingCanvas start(int i, int i2) {
        return beginRecording(i, i2);
    }

    @java.lang.Deprecated
    public void end(android.graphics.RecordingCanvas recordingCanvas) {
        if (recordingCanvas != this.mCurrentRecordingCanvas) {
            throw new java.lang.IllegalArgumentException("Wrong canvas");
        }
        endRecording();
    }

    public void discardDisplayList() {
        nDiscardDisplayList(this.mNativeRenderNode);
    }

    public boolean hasDisplayList() {
        return nIsValid(this.mNativeRenderNode);
    }

    public boolean hasIdentityMatrix() {
        return nHasIdentityMatrix(this.mNativeRenderNode);
    }

    public void getMatrix(android.graphics.Matrix matrix) {
        nGetTransformMatrix(this.mNativeRenderNode, matrix.ni());
    }

    public void getInverseMatrix(android.graphics.Matrix matrix) {
        nGetInverseTransformMatrix(this.mNativeRenderNode, matrix.ni());
    }

    @java.lang.Deprecated
    public boolean setLayerType(int i) {
        return nSetLayerType(this.mNativeRenderNode, i);
    }

    @java.lang.Deprecated
    public boolean setLayerPaint(android.graphics.Paint paint) {
        return nSetLayerPaint(this.mNativeRenderNode, paint != null ? paint.getNativeInstance() : 0L);
    }

    public boolean setUseCompositingLayer(boolean z, android.graphics.Paint paint) {
        return nSetLayerType(this.mNativeRenderNode, z ? 2 : 0) | nSetLayerPaint(this.mNativeRenderNode, paint != null ? paint.getNativeInstance() : 0L);
    }

    public boolean getUseCompositingLayer() {
        return nGetLayerType(this.mNativeRenderNode) != 0;
    }

    public boolean setClipRect(android.graphics.Rect rect) {
        if (rect == null) {
            return nSetClipBoundsEmpty(this.mNativeRenderNode);
        }
        return nSetClipBounds(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean setClipToBounds(boolean z) {
        return nSetClipToBounds(this.mNativeRenderNode, z);
    }

    public boolean getClipToBounds() {
        return nGetClipToBounds(this.mNativeRenderNode);
    }

    public boolean setProjectBackwards(boolean z) {
        return nSetProjectBackwards(this.mNativeRenderNode, z);
    }

    public boolean setProjectionReceiver(boolean z) {
        return nSetProjectionReceiver(this.mNativeRenderNode, z);
    }

    public boolean setOutline(android.graphics.Outline outline) {
        if (outline == null) {
            return nSetOutlineNone(this.mNativeRenderNode);
        }
        switch (outline.mMode) {
            case 0:
                return nSetOutlineEmpty(this.mNativeRenderNode);
            case 1:
                return nSetOutlineRoundRect(this.mNativeRenderNode, outline.mRect.left, outline.mRect.top, outline.mRect.right, outline.mRect.bottom, outline.mRadius, outline.mAlpha);
            case 2:
                return nSetOutlinePath(this.mNativeRenderNode, outline.mPath.mNativePath, outline.mAlpha);
            default:
                throw new java.lang.IllegalArgumentException("Unrecognized outline?");
        }
    }

    public boolean clearStretch() {
        return nClearStretch(this.mNativeRenderNode);
    }

    public boolean stretch(float f, float f2, float f3, float f4) {
        if (java.lang.Float.isInfinite(f) || java.lang.Float.isNaN(f)) {
            throw new java.lang.IllegalArgumentException("vecX must be a finite, non-NaN value " + f);
        }
        if (java.lang.Float.isInfinite(f2) || java.lang.Float.isNaN(f2)) {
            throw new java.lang.IllegalArgumentException("vecY must be a finite, non-NaN value " + f2);
        }
        if (f3 <= 0.0f) {
            throw new java.lang.IllegalArgumentException("The max horizontal stretch amount must be >0, got " + f3);
        }
        if (f4 <= 0.0f) {
            throw new java.lang.IllegalArgumentException("The max vertical stretch amount must be >0, got " + f4);
        }
        return nStretch(this.mNativeRenderNode, f, f2, f3, f4);
    }

    public boolean hasShadow() {
        return nHasShadow(this.mNativeRenderNode);
    }

    public boolean setSpotShadowColor(int i) {
        return nSetSpotShadowColor(this.mNativeRenderNode, i);
    }

    public int getSpotShadowColor() {
        return nGetSpotShadowColor(this.mNativeRenderNode);
    }

    public boolean setAmbientShadowColor(int i) {
        return nSetAmbientShadowColor(this.mNativeRenderNode, i);
    }

    public int getAmbientShadowColor() {
        return nGetAmbientShadowColor(this.mNativeRenderNode);
    }

    public boolean setClipToOutline(boolean z) {
        return nSetClipToOutline(this.mNativeRenderNode, z);
    }

    public boolean getClipToOutline() {
        return nGetClipToOutline(this.mNativeRenderNode);
    }

    public boolean setRevealClip(boolean z, float f, float f2, float f3) {
        return nSetRevealClip(this.mNativeRenderNode, z, f, f2, f3);
    }

    public boolean setStaticMatrix(android.graphics.Matrix matrix) {
        return nSetStaticMatrix(this.mNativeRenderNode, matrix.ni());
    }

    public boolean setAnimationMatrix(android.graphics.Matrix matrix) {
        return nSetAnimationMatrix(this.mNativeRenderNode, matrix != null ? matrix.ni() : 0L);
    }

    public android.graphics.Matrix getAnimationMatrix() {
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        if (nGetAnimationMatrix(this.mNativeRenderNode, matrix.ni())) {
            return matrix;
        }
        return null;
    }

    public boolean setAlpha(float f) {
        return nSetAlpha(this.mNativeRenderNode, f);
    }

    public boolean setRenderEffect(android.graphics.RenderEffect renderEffect) {
        return nSetRenderEffect(this.mNativeRenderNode, renderEffect != null ? renderEffect.getNativeInstance() : 0L);
    }

    public boolean setBackdropRenderEffect(android.graphics.RenderEffect renderEffect) {
        return nSetBackdropRenderEffect(this.mNativeRenderNode, renderEffect != null ? renderEffect.getNativeInstance() : 0L);
    }

    public float getAlpha() {
        return nGetAlpha(this.mNativeRenderNode);
    }

    public boolean setHasOverlappingRendering(boolean z) {
        return nSetHasOverlappingRendering(this.mNativeRenderNode, z);
    }

    public void setUsageHint(int i) {
        nSetUsageHint(this.mNativeRenderNode, i);
    }

    public boolean hasOverlappingRendering() {
        return nHasOverlappingRendering(this.mNativeRenderNode);
    }

    public boolean setElevation(float f) {
        return nSetElevation(this.mNativeRenderNode, f);
    }

    public float getElevation() {
        return nGetElevation(this.mNativeRenderNode);
    }

    public boolean setTranslationX(float f) {
        return nSetTranslationX(this.mNativeRenderNode, f);
    }

    public float getTranslationX() {
        return nGetTranslationX(this.mNativeRenderNode);
    }

    public boolean setTranslationY(float f) {
        return nSetTranslationY(this.mNativeRenderNode, f);
    }

    public float getTranslationY() {
        return nGetTranslationY(this.mNativeRenderNode);
    }

    public boolean setTranslationZ(float f) {
        return nSetTranslationZ(this.mNativeRenderNode, f);
    }

    public float getTranslationZ() {
        return nGetTranslationZ(this.mNativeRenderNode);
    }

    public boolean setRotationZ(float f) {
        return nSetRotation(this.mNativeRenderNode, f);
    }

    public float getRotationZ() {
        return nGetRotation(this.mNativeRenderNode);
    }

    public boolean setRotationX(float f) {
        return nSetRotationX(this.mNativeRenderNode, f);
    }

    public float getRotationX() {
        return nGetRotationX(this.mNativeRenderNode);
    }

    public boolean setRotationY(float f) {
        return nSetRotationY(this.mNativeRenderNode, f);
    }

    public float getRotationY() {
        return nGetRotationY(this.mNativeRenderNode);
    }

    public boolean setScaleX(float f) {
        return nSetScaleX(this.mNativeRenderNode, f);
    }

    public float getScaleX() {
        return nGetScaleX(this.mNativeRenderNode);
    }

    public boolean setScaleY(float f) {
        return nSetScaleY(this.mNativeRenderNode, f);
    }

    public float getScaleY() {
        return nGetScaleY(this.mNativeRenderNode);
    }

    public boolean setPivotX(float f) {
        return nSetPivotX(this.mNativeRenderNode, f);
    }

    public float getPivotX() {
        return nGetPivotX(this.mNativeRenderNode);
    }

    public boolean setPivotY(float f) {
        return nSetPivotY(this.mNativeRenderNode, f);
    }

    public float getPivotY() {
        return nGetPivotY(this.mNativeRenderNode);
    }

    public boolean isPivotExplicitlySet() {
        return nIsPivotExplicitlySet(this.mNativeRenderNode);
    }

    public boolean resetPivot() {
        return nResetPivot(this.mNativeRenderNode);
    }

    public boolean setCameraDistance(float f) {
        if (!java.lang.Float.isFinite(f) || f < 0.0f) {
            throw new java.lang.IllegalArgumentException("distance must be finite & positive, given=" + f);
        }
        return nSetCameraDistance(this.mNativeRenderNode, -f);
    }

    public float getCameraDistance() {
        return -nGetCameraDistance(this.mNativeRenderNode);
    }

    public boolean setLeft(int i) {
        return nSetLeft(this.mNativeRenderNode, i);
    }

    public boolean setTop(int i) {
        return nSetTop(this.mNativeRenderNode, i);
    }

    public boolean setRight(int i) {
        return nSetRight(this.mNativeRenderNode, i);
    }

    public boolean setBottom(int i) {
        return nSetBottom(this.mNativeRenderNode, i);
    }

    public int getLeft() {
        return nGetLeft(this.mNativeRenderNode);
    }

    public int getTop() {
        return nGetTop(this.mNativeRenderNode);
    }

    public int getRight() {
        return nGetRight(this.mNativeRenderNode);
    }

    public int getBottom() {
        return nGetBottom(this.mNativeRenderNode);
    }

    public int getWidth() {
        return nGetWidth(this.mNativeRenderNode);
    }

    public int getHeight() {
        return nGetHeight(this.mNativeRenderNode);
    }

    public boolean setLeftTopRightBottom(int i, int i2, int i3, int i4) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, i, i2, i3, i4);
    }

    public boolean setPosition(int i, int i2, int i3, int i4) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, i, i2, i3, i4);
    }

    public boolean setPosition(android.graphics.Rect rect) {
        return nSetLeftTopRightBottom(this.mNativeRenderNode, rect.left, rect.top, rect.right, rect.bottom);
    }

    public boolean offsetLeftAndRight(int i) {
        return nOffsetLeftAndRight(this.mNativeRenderNode, i);
    }

    public boolean offsetTopAndBottom(int i) {
        return nOffsetTopAndBottom(this.mNativeRenderNode, i);
    }

    public void output() {
        nOutput(this.mNativeRenderNode);
    }

    public long computeApproximateMemoryUsage() {
        return nGetUsageSize(this.mNativeRenderNode);
    }

    public long computeApproximateMemoryAllocated() {
        return nGetAllocatedSize(this.mNativeRenderNode);
    }

    public boolean setForceDarkAllowed(boolean z) {
        return nSetAllowForceDark(this.mNativeRenderNode, z);
    }

    public boolean isForceDarkAllowed() {
        return nGetAllowForceDark(this.mNativeRenderNode);
    }

    public long getUniqueId() {
        return nGetUniqueId(this.mNativeRenderNode);
    }

    public void setIsTextureView() {
        nSetIsTextureView(this.mNativeRenderNode);
    }

    public void addAnimator(android.graphics.animation.RenderNodeAnimator renderNodeAnimator) {
        if (!isAttached()) {
            throw new java.lang.IllegalStateException("Cannot start this animator on a detached view!");
        }
        nAddAnimator(this.mNativeRenderNode, renderNodeAnimator.getNativeAnimator());
        this.mAnimationHost.registerAnimatingRenderNode(this);
    }

    public boolean isAttached() {
        return this.mAnimationHost != null && this.mAnimationHost.isAttached();
    }

    public void registerVectorDrawableAnimator(android.view.NativeVectorDrawableAnimator nativeVectorDrawableAnimator) {
        if (!isAttached()) {
            throw new java.lang.IllegalStateException("Cannot start this animator on a detached view!");
        }
        this.mAnimationHost.registerVectorDrawableAnimator(nativeVectorDrawableAnimator);
    }

    public void endAllAnimators() {
        nEndAllAnimators(this.mNativeRenderNode);
    }

    public void forceEndAnimators() {
        nForceEndAnimators(this.mNativeRenderNode);
    }
}
