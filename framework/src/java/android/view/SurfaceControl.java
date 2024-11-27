package android.view;

/* loaded from: classes4.dex */
public final class SurfaceControl implements android.os.Parcelable {
    public static final int BUFFER_TRANSFORM_IDENTITY = 0;
    public static final int BUFFER_TRANSFORM_MIRROR_HORIZONTAL = 1;
    public static final int BUFFER_TRANSFORM_MIRROR_VERTICAL = 2;
    public static final int BUFFER_TRANSFORM_ROTATE_180 = 3;
    public static final int BUFFER_TRANSFORM_ROTATE_270 = 7;
    public static final int BUFFER_TRANSFORM_ROTATE_90 = 4;
    public static final int CACHING_DISABLED = 0;
    public static final int CACHING_ENABLED = 1;
    public static final int CAN_OCCLUDE_PRESENTATION = 4096;
    public static final int CURSOR_WINDOW = 8192;
    public static final int DISPLAY_DECORATION = 512;
    public static final int DISPLAY_RECEIVES_INPUT = 1;
    public static final int ENABLE_BACKPRESSURE = 256;
    public static final int FRAME_RATE_SELECTION_STRATEGY_OVERRIDE_CHILDREN = 1;
    public static final int FRAME_RATE_SELECTION_STRATEGY_PROPAGATE = 0;
    public static final int FRAME_RATE_SELECTION_STRATEGY_SELF = 2;
    public static final int FX_SURFACE_BLAST = 262144;
    public static final int FX_SURFACE_CONTAINER = 524288;
    public static final int FX_SURFACE_EFFECT = 131072;
    public static final int FX_SURFACE_MASK = 983040;
    public static final int FX_SURFACE_NORMAL = 0;
    public static final int HIDDEN = 4;
    public static final int IGNORE_DESTINATION_FRAME = 1024;
    public static final int LAYER_IS_REFRESH_RATE_INDICATOR = 2048;
    public static final int METADATA_ACCESSIBILITY_ID = 5;
    public static final int METADATA_GAME_MODE = 8;
    public static final int METADATA_MOUSE_CURSOR = 4;
    public static final int METADATA_OWNER_PID = 6;
    public static final int METADATA_OWNER_UID = 1;
    public static final int METADATA_TASK_ID = 3;
    public static final int METADATA_WINDOW_TYPE = 2;
    public static final int NON_PREMULTIPLIED = 256;
    public static final int NO_COLOR_FILL = 16384;
    public static final int OPAQUE = 1024;
    public static final int POWER_MODE_DOZE = 1;
    public static final int POWER_MODE_DOZE_SUSPEND = 3;
    public static final int POWER_MODE_NORMAL = 2;
    public static final int POWER_MODE_OFF = 0;
    public static final int POWER_MODE_ON_SUSPEND = 4;
    public static final int PROTECTED_APP = 2048;
    public static final int SECURE = 128;
    public static final int SKIP_SCREENSHOT = 64;
    private static final int SURFACE_HIDDEN = 1;
    private static final int SURFACE_OPAQUE = 2;
    private static final java.lang.String TAG = "SurfaceControl";
    private java.lang.String mCallsite;
    private android.view.Choreographer mChoreographer;
    private final java.lang.Object mChoreographerLock;
    private final dalvik.system.CloseGuard mCloseGuard;
    private java.lang.Runnable mFreeNativeResources;
    private int mHeight;
    private java.lang.ref.WeakReference<android.view.View> mLocalOwnerView;
    private final java.lang.Object mLock;
    private java.lang.String mName;
    private long mNativeHandle;
    public long mNativeObject;
    private java.lang.Throwable mReleaseStack;
    private java.util.ArrayList<android.view.SurfaceControl.OnReparentListener> mReparentListeners;
    private android.view.SurfaceControl.TrustedPresentationCallback mTrustedPresentationCallback;
    private int mWidth;
    private static volatile boolean sDebugUsageAfterRelease = false;
    private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.view.SurfaceControl.class.getClassLoader(), nativeGetNativeSurfaceControlFinalizer());
    public static final android.os.Parcelable.Creator<android.view.SurfaceControl> CREATOR = new android.os.Parcelable.Creator<android.view.SurfaceControl>() { // from class: android.view.SurfaceControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.SurfaceControl createFromParcel(android.os.Parcel parcel) {
            return new android.view.SurfaceControl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.SurfaceControl[] newArray(int i) {
            return new android.view.SurfaceControl[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BufferTransform {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CachingHint {
    }

    public static final class CieXyz {
        public float X;
        public float Y;
        public float Z;
    }

    public static final class DisplayPrimaries {
        public android.view.SurfaceControl.CieXyz blue;
        public android.view.SurfaceControl.CieXyz green;
        public android.view.SurfaceControl.CieXyz red;
        public android.view.SurfaceControl.CieXyz white;
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FrameRateSelectionStrategy {
    }

    public static abstract class OnJankDataListener {
        private final com.android.internal.util.VirtualRefBasePtr mNativePtr = new com.android.internal.util.VirtualRefBasePtr(android.view.SurfaceControl.nativeCreateJankDataListenerWrapper(this));

        public abstract void onJankDataAvailable(android.view.SurfaceControl.JankData[] jankDataArr);
    }

    public interface OnReparentListener {
        void onReparent(android.view.SurfaceControl.Transaction transaction, android.view.SurfaceControl surfaceControl);
    }

    public interface TransactionCommittedListener {
        void onTransactionCommitted();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native long getNativeTrustedPresentationCallbackFinalizer();

    private static native void nativeAddJankDataListener(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCommittedListener(long j, android.view.SurfaceControl.TransactionCommittedListener transactionCommittedListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddTransactionCompletedListener(long j, java.util.function.Consumer<android.view.SurfaceControl.TransactionStats> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeAddWindowInfosReportedListener(long j, java.lang.Runnable runnable);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeApplyTransaction(long j, boolean z, boolean z2);

    private static native boolean nativeBootFinished();

    private static native boolean nativeClearAnimationFrameStats();

    private static native void nativeClearBootDisplayMode(android.os.IBinder iBinder);

    private static native boolean nativeClearContentFrameStats(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTransaction(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeClearTrustedPresentationCallback(long j, long j2);

    private static native long nativeCopyFromSurfaceControl(long j);

    private static native long nativeCreate(android.view.SurfaceSession surfaceSession, java.lang.String str, int i, int i2, int i3, int i4, long j, android.os.Parcel parcel) throws android.view.Surface.OutOfResourcesException;

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateJankDataListenerWrapper(android.view.SurfaceControl.OnJankDataListener onJankDataListener);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTpc(android.view.SurfaceControl.TrustedPresentationCallback trustedPresentationCallback);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeCreateTransaction();

    private static native void nativeDisconnect(long j);

    private static native boolean nativeGetAnimationFrameStats(android.view.WindowAnimationFrameStats windowAnimationFrameStats);

    private static native boolean nativeGetBootDisplayModeSupport();

    private static native int[] nativeGetCompositionDataspaces();

    private static native boolean nativeGetContentFrameStats(long j, android.view.WindowContentFrameStats windowContentFrameStats);

    /* JADX INFO: Access modifiers changed from: private */
    public static native android.os.IBinder nativeGetDefaultApplyToken();

    private static native android.view.SurfaceControl.DesiredDisplayModeSpecs nativeGetDesiredDisplayModeSpecs(android.os.IBinder iBinder);

    private static native boolean nativeGetDisplayBrightnessSupport(android.os.IBinder iBinder);

    private static native android.hardware.graphics.common.DisplayDecorationSupport nativeGetDisplayDecorationSupport(android.os.IBinder iBinder);

    private static native android.view.SurfaceControl.DisplayPrimaries nativeGetDisplayNativePrimaries(android.os.IBinder iBinder);

    private static native android.hardware.display.DisplayedContentSample nativeGetDisplayedContentSample(android.os.IBinder iBinder, long j, long j2);

    private static native android.hardware.display.DisplayedContentSamplingAttributes nativeGetDisplayedContentSamplingAttributes(android.os.IBinder iBinder);

    private static native android.view.SurfaceControl.DynamicDisplayInfo nativeGetDynamicDisplayInfo(long j);

    private static native int nativeGetGPUContextPriority();

    private static native long nativeGetHandle(long j);

    private static native int nativeGetLayerId(long j);

    private static native long nativeGetNativeSurfaceControlFinalizer();

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetNativeTransactionFinalizer();

    private static native android.hardware.OverlayProperties nativeGetOverlaySupport();

    private static native boolean nativeGetProtectedContentSupport();

    private static native android.gui.StalledTransactionInfo nativeGetStalledTransactionInfo(int i);

    private static native android.view.SurfaceControl.StaticDisplayInfo nativeGetStaticDisplayInfo(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeGetTransactionId(long j);

    private static native int nativeGetTransformHint(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeMergeTransaction(long j, long j2);

    private static native long nativeMirrorSurface(long j);

    private static native long nativeReadFromParcel(android.os.Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nativeReadTransactionFromParcel(android.os.Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeRemoveCurrentInputFocus(long j, int i);

    private static native void nativeRemoveJankDataListener(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeReparent(long j, long j2, long j3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSanitize(long j, int i, int i2);

    private static native boolean nativeSetActiveColorMode(android.os.IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAlpha(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetAnimationTransaction(long j);

    private static native void nativeSetAutoLowLatencyMode(android.os.IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBackgroundBlurRadius(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBlurRegions(long j, long j2, float[][] fArr, int i);

    private static native void nativeSetBootDisplayMode(android.os.IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBuffer(long j, long j2, android.hardware.HardwareBuffer hardwareBuffer, long j3, java.util.function.Consumer<android.hardware.SyncFence> consumer);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetBufferTransform(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCachingHint(long j, long j2, int i);

    private static native void nativeSetCanOccludePresentation(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColor(long j, long j2, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorSpaceAgnostic(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetColorTransform(long j, long j2, float[] fArr, float[] fArr2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetCornerRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDamageRegion(long j, long j2, android.graphics.Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDataSpace(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultApplyToken(android.os.IBinder iBinder);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDefaultFrameRateCompatibility(long j, long j2, int i);

    private static native boolean nativeSetDesiredDisplayModeSpecs(android.os.IBinder iBinder, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredHdrHeadroom(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDesiredPresentTimeNanos(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDestinationFrame(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDimmingEnabled(long j, long j2, boolean z);

    private static native boolean nativeSetDisplayBrightness(android.os.IBinder iBinder, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayFlags(long j, android.os.IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayLayerStack(long j, android.os.IBinder iBinder, int i);

    private static native void nativeSetDisplayPowerMode(android.os.IBinder iBinder, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplayProjection(long j, android.os.IBinder iBinder, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySize(long j, android.os.IBinder iBinder, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDisplaySurface(long j, android.os.IBinder iBinder, long j2);

    private static native boolean nativeSetDisplayedContentSamplingEnabled(android.os.IBinder iBinder, boolean z, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetDropInputMode(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupEnd(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetEarlyWakeupStart(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetExtendedRangeBrightness(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFixedTransformHint(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFlags(long j, long j2, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFocusedWindow(long j, android.os.IBinder iBinder, java.lang.String str, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRate(long j, long j2, float f, int i, int i2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateCategory(long j, long j2, int i, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionPriority(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameRateSelectionStrategy(long j, long j2, int i);

    private static native void nativeSetFrameTimeline(long j, long j2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetFrameTimelineVsync(long j, long j2);

    private static native void nativeSetGameContentType(android.os.IBinder iBinder, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetGeometry(long j, long j2, android.graphics.Rect rect, android.graphics.Rect rect2, long j3);

    private static native void nativeSetGlobalShadowSettings(float[] fArr, float[] fArr2, float f, float f2, float f3);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetInputWindowInfo(long j, long j2, android.view.InputWindowHandle inputWindowHandle);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayer(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetLayerStack(long j, long j2, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMatrix(long j, long j2, float f, float f2, float f3, float f4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetMetadata(long j, long j2, int i, android.os.Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetPosition(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetRelativeLayer(long j, long j2, long j3, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetScale(long j, long j2, float f, float f2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetShadowRadius(long j, long j2, float f);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetStretchEffect(long j, long j2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10);

    private static native void nativeSetTransformHint(long j, int i);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTransparentRegionHint(long j, long j2, android.graphics.Region region);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedOverlay(long j, long j2, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetTrustedPresentationCallback(long j, long j2, long j3, android.view.SurfaceControl.TrustedPresentationThresholds trustedPresentationThresholds);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSetWindowCrop(long j, long j2, int i, int i2, int i3, int i4);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeSurfaceFlushJankData(long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeUnsetBuffer(long j, long j2);

    private static native void nativeUpdateDefaultBufferSize(long j, int i, int i2);

    private static native void nativeWriteToParcel(long j, android.os.Parcel parcel);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeWriteTransactionToParcel(long j, android.os.Parcel parcel);

    public static int rotationToBufferTransform(int i) {
        switch (i) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                android.util.Log.e(TAG, "Trying to convert unknown rotation=" + i);
                break;
        }
        return 0;
    }

    public static class JankData {
        public static final int BUFFER_STUFFING = 64;
        public static final int DISPLAY_HAL = 1;
        public static final int JANK_APP_DEADLINE_MISSED = 8;
        public static final int JANK_NONE = 0;
        public static final int JANK_SURFACEFLINGER_DEADLINE_MISSED = 2;
        public static final int JANK_SURFACEFLINGER_GPU_DEADLINE_MISSED = 4;
        public static final int PREDICTION_ERROR = 16;
        public static final int SURFACE_FLINGER_SCHEDULING = 32;
        public static final int UNKNOWN = 128;
        public final long frameIntervalNs;
        public final long frameVsyncId;
        public final int jankType;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface JankType {
        }

        public JankData(long j, int i, long j2) {
            this.frameVsyncId = j;
            this.jankType = i;
            this.frameIntervalNs = j2;
        }
    }

    public boolean addOnReparentListener(android.view.SurfaceControl.OnReparentListener onReparentListener) {
        boolean add;
        synchronized (this.mLock) {
            if (this.mReparentListeners == null) {
                this.mReparentListeners = new java.util.ArrayList<>(1);
            }
            add = this.mReparentListeners.add(onReparentListener);
        }
        return add;
    }

    public boolean removeOnReparentListener(android.view.SurfaceControl.OnReparentListener onReparentListener) {
        boolean remove;
        synchronized (this.mLock) {
            remove = this.mReparentListeners.remove(onReparentListener);
            if (this.mReparentListeners.isEmpty()) {
                this.mReparentListeners = null;
            }
        }
        return remove;
    }

    private void assignNativeObject(long j, java.lang.String str) {
        if (this.mNativeObject != 0) {
            release();
        }
        if (j != 0) {
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, j);
        }
        this.mNativeObject = j;
        this.mNativeHandle = this.mNativeObject != 0 ? nativeGetHandle(j) : 0L;
        if (sDebugUsageAfterRelease && this.mNativeObject == 0) {
            this.mReleaseStack = new java.lang.Throwable("Assigned invalid nativeObject");
        } else {
            this.mReleaseStack = null;
        }
        setUnreleasedWarningCallSite(str);
        if (j != 0) {
            android.view.SurfaceControlRegistry.getProcessInstance().add(this);
        }
    }

    public void copyFrom(android.view.SurfaceControl surfaceControl, java.lang.String str) {
        this.mName = surfaceControl.mName;
        this.mWidth = surfaceControl.mWidth;
        this.mHeight = surfaceControl.mHeight;
        this.mLocalOwnerView = surfaceControl.mLocalOwnerView;
        assignNativeObject(nativeCopyFromSurfaceControl(surfaceControl.mNativeObject), str);
    }

    public static class Builder {
        private int mHeight;
        private java.lang.ref.WeakReference<android.view.View> mLocalOwnerView;
        private android.util.SparseIntArray mMetadata;
        private java.lang.String mName;
        private android.view.SurfaceControl mParent;
        private android.view.SurfaceSession mSession;
        private int mWidth;
        private int mFlags = 4;
        private int mFormat = -1;
        private java.lang.String mCallsite = "SurfaceControl.Builder";

        public Builder(android.view.SurfaceSession surfaceSession) {
            this.mSession = surfaceSession;
        }

        public Builder() {
        }

        public android.view.SurfaceControl build() {
            if (this.mWidth < 0 || this.mHeight < 0) {
                throw new java.lang.IllegalStateException("width and height must be positive or unset");
            }
            if ((this.mWidth > 0 || this.mHeight > 0) && (isEffectLayer() || isContainerLayer())) {
                throw new java.lang.IllegalStateException("Only buffer layers can set a valid buffer size.");
            }
            if (this.mName == null) {
                android.util.Log.w(android.view.SurfaceControl.TAG, "Missing name for SurfaceControl", new java.lang.Throwable());
            }
            if ((this.mFlags & android.view.SurfaceControl.FX_SURFACE_MASK) == 0) {
                setBLASTLayer();
            }
            return new android.view.SurfaceControl(this.mSession, this.mName, this.mWidth, this.mHeight, this.mFormat, this.mFlags, this.mParent, this.mMetadata, this.mLocalOwnerView, this.mCallsite);
        }

        public android.view.SurfaceControl.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.view.SurfaceControl.Builder setLocalOwnerView(android.view.View view) {
            this.mLocalOwnerView = new java.lang.ref.WeakReference<>(view);
            return this;
        }

        public android.view.SurfaceControl.Builder setBufferSize(int i, int i2) {
            if (i < 0 || i2 < 0) {
                throw new java.lang.IllegalArgumentException("width and height must be positive");
            }
            this.mWidth = i;
            this.mHeight = i2;
            return setFlags(0, android.view.SurfaceControl.FX_SURFACE_MASK);
        }

        private void unsetBufferSize() {
            this.mWidth = 0;
            this.mHeight = 0;
        }

        public android.view.SurfaceControl.Builder setFormat(int i) {
            this.mFormat = i;
            return this;
        }

        public android.view.SurfaceControl.Builder setProtected(boolean z) {
            if (z) {
                this.mFlags |= 2048;
            } else {
                this.mFlags &= -2049;
            }
            return this;
        }

        public android.view.SurfaceControl.Builder setSecure(boolean z) {
            if (z) {
                this.mFlags |= 128;
            } else {
                this.mFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            return this;
        }

        public android.view.SurfaceControl.Builder setOpaque(boolean z) {
            if (z) {
                this.mFlags |= 1024;
            } else {
                this.mFlags &= -1025;
            }
            return this;
        }

        public android.view.SurfaceControl.Builder setHidden(boolean z) {
            if (z) {
                this.mFlags |= 4;
            } else {
                this.mFlags &= -5;
            }
            return this;
        }

        public android.view.SurfaceControl.Builder setParent(android.view.SurfaceControl surfaceControl) {
            this.mParent = surfaceControl;
            return this;
        }

        public android.view.SurfaceControl.Builder setMetadata(int i, int i2) {
            if (this.mMetadata == null) {
                this.mMetadata = new android.util.SparseIntArray();
            }
            this.mMetadata.put(i, i2);
            return this;
        }

        public android.view.SurfaceControl.Builder setEffectLayer() {
            this.mFlags |= 16384;
            unsetBufferSize();
            return setFlags(131072, android.view.SurfaceControl.FX_SURFACE_MASK);
        }

        public android.view.SurfaceControl.Builder setColorLayer() {
            unsetBufferSize();
            return setFlags(131072, android.view.SurfaceControl.FX_SURFACE_MASK);
        }

        private boolean isEffectLayer() {
            return (this.mFlags & 131072) == 131072;
        }

        public android.view.SurfaceControl.Builder setBLASTLayer() {
            return setFlags(262144, android.view.SurfaceControl.FX_SURFACE_MASK);
        }

        public android.view.SurfaceControl.Builder setContainerLayer() {
            unsetBufferSize();
            return setFlags(524288, android.view.SurfaceControl.FX_SURFACE_MASK);
        }

        private boolean isContainerLayer() {
            return (this.mFlags & 524288) == 524288;
        }

        public android.view.SurfaceControl.Builder setFlags(int i) {
            this.mFlags = i;
            return this;
        }

        public android.view.SurfaceControl.Builder setCallsite(java.lang.String str) {
            this.mCallsite = str;
            return this;
        }

        private android.view.SurfaceControl.Builder setFlags(int i, int i2) {
            this.mFlags = i | ((~i2) & this.mFlags);
            return this;
        }
    }

    private SurfaceControl(android.view.SurfaceSession surfaceSession, java.lang.String str, int i, int i2, int i3, int i4, android.view.SurfaceControl surfaceControl, android.util.SparseIntArray sparseIntArray, java.lang.ref.WeakReference<android.view.View> weakReference, java.lang.String str2) throws android.view.Surface.OutOfResourcesException, java.lang.IllegalArgumentException {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mChoreographerLock = new java.lang.Object();
        this.mLock = new java.lang.Object();
        this.mReleaseStack = null;
        if (str == null) {
            throw new java.lang.IllegalArgumentException("name must not be null");
        }
        this.mName = str;
        this.mWidth = i;
        this.mHeight = i2;
        this.mLocalOwnerView = weakReference;
        android.os.Parcel obtain = android.os.Parcel.obtain();
        if (sparseIntArray != null) {
            try {
                if (sparseIntArray.size() > 0) {
                    obtain.writeInt(sparseIntArray.size());
                    for (int i5 = 0; i5 < sparseIntArray.size(); i5++) {
                        obtain.writeInt(sparseIntArray.keyAt(i5));
                        obtain.writeByteArray(java.nio.ByteBuffer.allocate(4).order(java.nio.ByteOrder.nativeOrder()).putInt(sparseIntArray.valueAt(i5)).array());
                    }
                    obtain.setDataPosition(0);
                }
            } catch (java.lang.Throwable th) {
                obtain.recycle();
                throw th;
            }
        }
        long nativeCreate = nativeCreate(surfaceSession, str, i, i2, i3, i4, surfaceControl != null ? surfaceControl.mNativeObject : 0L, obtain);
        obtain.recycle();
        if (nativeCreate == 0) {
            throw new android.view.Surface.OutOfResourcesException("Couldn't allocate SurfaceControl native object");
        }
        assignNativeObject(nativeCreate, str2);
    }

    public SurfaceControl(android.view.SurfaceControl surfaceControl, java.lang.String str) {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mChoreographerLock = new java.lang.Object();
        this.mLock = new java.lang.Object();
        this.mReleaseStack = null;
        copyFrom(surfaceControl, str);
    }

    private SurfaceControl(android.os.Parcel parcel) {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mChoreographerLock = new java.lang.Object();
        this.mLock = new java.lang.Object();
        this.mReleaseStack = null;
        readFromParcel(parcel);
    }

    public SurfaceControl() {
        this.mCloseGuard = dalvik.system.CloseGuard.get();
        this.mChoreographerLock = new java.lang.Object();
        this.mLock = new java.lang.Object();
        this.mReleaseStack = null;
    }

    public void readFromParcel(android.os.Parcel parcel) {
        long j;
        if (parcel == null) {
            throw new java.lang.IllegalArgumentException("source must not be null");
        }
        this.mName = parcel.readString8();
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        if (parcel.readInt() == 0) {
            j = 0;
        } else {
            j = nativeReadFromParcel(parcel);
        }
        assignNativeObject(j, "readFromParcel");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (sDebugUsageAfterRelease) {
            checkNotReleased();
        }
        parcel.writeString8(this.mName);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        if (this.mNativeObject == 0) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
        }
        nativeWriteToParcel(this.mNativeObject, parcel);
        if ((i & 1) != 0) {
            release();
        }
    }

    public static void setDebugUsageAfterRelease(boolean z) {
        if (!android.os.Build.isDebuggable()) {
            return;
        }
        sDebugUsageAfterRelease = z;
    }

    public void setUnreleasedWarningCallSite(java.lang.String str) {
        if (!isValid()) {
            return;
        }
        this.mCloseGuard.openWithCallSite("release", str);
        this.mCallsite = str;
    }

    java.lang.String getCallsite() {
        return this.mCallsite;
    }

    java.lang.String getName() {
        return this.mName;
    }

    public boolean isSameSurface(android.view.SurfaceControl surfaceControl) {
        return surfaceControl.mNativeHandle == this.mNativeHandle;
    }

    public android.view.Choreographer getChoreographer() {
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            if (this.mChoreographer == null) {
                return getChoreographer(android.os.Looper.myLooper());
            }
            return this.mChoreographer;
        }
    }

    public android.view.Choreographer getChoreographer(android.os.Looper looper) {
        android.view.Choreographer choreographer;
        checkNotReleased();
        synchronized (this.mChoreographerLock) {
            if (this.mChoreographer == null) {
                this.mChoreographer = android.view.Choreographer.getInstanceForSurfaceControl(this.mNativeHandle, looper);
            } else if (!this.mChoreographer.isTheLooperSame(looper)) {
                throw new java.lang.IllegalStateException("Choreographer already exists with a different looper");
            }
            choreographer = this.mChoreographer;
        }
        return choreographer;
    }

    public boolean hasChoreographer() {
        boolean z;
        synchronized (this.mChoreographerLock) {
            z = this.mChoreographer != null;
        }
        return z;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, java.lang.System.identityHashCode(this));
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1120986464259L, getLayerId());
        protoOutputStream.end(start);
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            android.view.SurfaceControlRegistry.getProcessInstance().remove(this);
        } finally {
            super.finalize();
        }
    }

    public void release() {
        if (this.mNativeObject != 0) {
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("release", null, this, null);
            }
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
            this.mNativeHandle = 0L;
            if (sDebugUsageAfterRelease) {
                this.mReleaseStack = new java.lang.Throwable("Released");
            }
            this.mCloseGuard.close();
            synchronized (this.mChoreographerLock) {
                if (this.mChoreographer != null) {
                    this.mChoreographer.invalidate();
                    this.mChoreographer = null;
                }
            }
            android.view.SurfaceControlRegistry.getProcessInstance().remove(this);
        }
    }

    public void disconnect() {
        if (this.mNativeObject != 0) {
            nativeDisconnect(this.mNativeObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkNotReleased() {
        if (this.mNativeObject == 0) {
            if (this.mReleaseStack != null) {
                throw new java.lang.IllegalStateException("Invalid usage after release of " + this, this.mReleaseStack);
            }
            throw new java.lang.NullPointerException("mNativeObject of " + this + " is null. Have you called release() already?");
        }
    }

    public boolean isValid() {
        return this.mNativeObject != 0;
    }

    @java.lang.Deprecated
    public static void openTransaction() {
    }

    @java.lang.Deprecated
    public static void closeTransaction() {
    }

    public boolean clearContentFrameStats() {
        checkNotReleased();
        return nativeClearContentFrameStats(this.mNativeObject);
    }

    public boolean getContentFrameStats(android.view.WindowContentFrameStats windowContentFrameStats) {
        checkNotReleased();
        return nativeGetContentFrameStats(this.mNativeObject, windowContentFrameStats);
    }

    public static boolean clearAnimationFrameStats() {
        return nativeClearAnimationFrameStats();
    }

    public static boolean getAnimationFrameStats(android.view.WindowAnimationFrameStats windowAnimationFrameStats) {
        return nativeGetAnimationFrameStats(windowAnimationFrameStats);
    }

    public int getWidth() {
        int i;
        synchronized (this.mLock) {
            i = this.mWidth;
        }
        return i;
    }

    public int getHeight() {
        int i;
        synchronized (this.mLock) {
            i = this.mHeight;
        }
        return i;
    }

    public android.view.View getLocalOwnerView() {
        if (this.mLocalOwnerView != null) {
            return this.mLocalOwnerView.get();
        }
        return null;
    }

    public java.lang.String toString() {
        return "Surface(name=" + this.mName + ")/@0x" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this));
    }

    public static final class StaticDisplayInfo {
        public float density;
        public android.hardware.display.DeviceProductInfo deviceProductInfo;
        public int installOrientation;
        public boolean isInternal;
        public boolean secure;

        public java.lang.String toString() {
            return "StaticDisplayInfo{isInternal=" + this.isInternal + ", density=" + this.density + ", secure=" + this.secure + ", deviceProductInfo=" + this.deviceProductInfo + ", installOrientation=" + this.installOrientation + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.view.SurfaceControl.StaticDisplayInfo staticDisplayInfo = (android.view.SurfaceControl.StaticDisplayInfo) obj;
            if (this.isInternal == staticDisplayInfo.isInternal && this.density == staticDisplayInfo.density && this.secure == staticDisplayInfo.secure && java.util.Objects.equals(this.deviceProductInfo, staticDisplayInfo.deviceProductInfo) && this.installOrientation == staticDisplayInfo.installOrientation) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Boolean.valueOf(this.isInternal), java.lang.Float.valueOf(this.density), java.lang.Boolean.valueOf(this.secure), this.deviceProductInfo, java.lang.Integer.valueOf(this.installOrientation));
        }
    }

    public static final class DynamicDisplayInfo {
        public int activeColorMode;
        public int activeDisplayModeId;
        public boolean autoLowLatencyModeSupported;
        public boolean gameContentTypeSupported;
        public android.view.Display.HdrCapabilities hdrCapabilities;
        public int preferredBootDisplayMode;
        public float renderFrameRate;
        public int[] supportedColorModes;
        public android.view.SurfaceControl.DisplayMode[] supportedDisplayModes;

        public java.lang.String toString() {
            return "DynamicDisplayInfo{supportedDisplayModes=" + java.util.Arrays.toString(this.supportedDisplayModes) + ", activeDisplayModeId=" + this.activeDisplayModeId + ", renderFrameRate=" + this.renderFrameRate + ", supportedColorModes=" + java.util.Arrays.toString(this.supportedColorModes) + ", activeColorMode=" + this.activeColorMode + ", hdrCapabilities=" + this.hdrCapabilities + ", autoLowLatencyModeSupported=" + this.autoLowLatencyModeSupported + ", gameContentTypeSupported" + this.gameContentTypeSupported + ", preferredBootDisplayMode" + this.preferredBootDisplayMode + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.view.SurfaceControl.DynamicDisplayInfo dynamicDisplayInfo = (android.view.SurfaceControl.DynamicDisplayInfo) obj;
            if (java.util.Arrays.equals(this.supportedDisplayModes, dynamicDisplayInfo.supportedDisplayModes) && this.activeDisplayModeId == dynamicDisplayInfo.activeDisplayModeId && this.renderFrameRate == dynamicDisplayInfo.renderFrameRate && java.util.Arrays.equals(this.supportedColorModes, dynamicDisplayInfo.supportedColorModes) && this.activeColorMode == dynamicDisplayInfo.activeColorMode && java.util.Objects.equals(this.hdrCapabilities, dynamicDisplayInfo.hdrCapabilities) && this.preferredBootDisplayMode == dynamicDisplayInfo.preferredBootDisplayMode) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.supportedDisplayModes)), java.lang.Integer.valueOf(this.activeDisplayModeId), java.lang.Float.valueOf(this.renderFrameRate), java.lang.Integer.valueOf(this.activeColorMode), this.hdrCapabilities);
        }
    }

    public static final class DisplayMode {
        public long appVsyncOffsetNanos;
        public int group;
        public int height;
        public int id;
        public float peakRefreshRate;
        public long presentationDeadlineNanos;
        public int[] supportedHdrTypes;
        public float vsyncRate;
        public int width;
        public float xDpi;
        public float yDpi;

        public java.lang.String toString() {
            return "DisplayMode{id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", xDpi=" + this.xDpi + ", yDpi=" + this.yDpi + ", peakRefreshRate=" + this.peakRefreshRate + ", vsyncRate=" + this.vsyncRate + ", appVsyncOffsetNanos=" + this.appVsyncOffsetNanos + ", presentationDeadlineNanos=" + this.presentationDeadlineNanos + ", supportedHdrTypes=" + java.util.Arrays.toString(this.supportedHdrTypes) + ", group=" + this.group + "}";
        }

        public boolean equals(java.lang.Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            android.view.SurfaceControl.DisplayMode displayMode = (android.view.SurfaceControl.DisplayMode) obj;
            if (this.id == displayMode.id && this.width == displayMode.width && this.height == displayMode.height && java.lang.Float.compare(displayMode.xDpi, this.xDpi) == 0 && java.lang.Float.compare(displayMode.yDpi, this.yDpi) == 0 && java.lang.Float.compare(displayMode.peakRefreshRate, this.peakRefreshRate) == 0 && java.lang.Float.compare(displayMode.vsyncRate, this.vsyncRate) == 0 && this.appVsyncOffsetNanos == displayMode.appVsyncOffsetNanos && this.presentationDeadlineNanos == displayMode.presentationDeadlineNanos && java.util.Arrays.equals(this.supportedHdrTypes, displayMode.supportedHdrTypes) && this.group == displayMode.group) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Integer.valueOf(this.id), java.lang.Integer.valueOf(this.width), java.lang.Integer.valueOf(this.height), java.lang.Float.valueOf(this.xDpi), java.lang.Float.valueOf(this.yDpi), java.lang.Float.valueOf(this.peakRefreshRate), java.lang.Float.valueOf(this.vsyncRate), java.lang.Long.valueOf(this.appVsyncOffsetNanos), java.lang.Long.valueOf(this.presentationDeadlineNanos), java.lang.Integer.valueOf(this.group), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.supportedHdrTypes)));
        }
    }

    public static void setDisplayPowerMode(android.os.IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeSetDisplayPowerMode(iBinder, i);
    }

    public static android.view.SurfaceControl.StaticDisplayInfo getStaticDisplayInfo(long j) {
        return nativeGetStaticDisplayInfo(j);
    }

    public static android.view.SurfaceControl.DynamicDisplayInfo getDynamicDisplayInfo(long j) {
        return nativeGetDynamicDisplayInfo(j);
    }

    public static android.hardware.display.DisplayedContentSamplingAttributes getDisplayedContentSamplingAttributes(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSamplingAttributes(iBinder);
    }

    public static boolean setDisplayedContentSamplingEnabled(android.os.IBinder iBinder, boolean z, int i, int i2) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        if ((i >> 4) != 0) {
            throw new java.lang.IllegalArgumentException("invalid componentMask when enabling sampling");
        }
        return nativeSetDisplayedContentSamplingEnabled(iBinder, z, i, i2);
    }

    public static android.hardware.display.DisplayedContentSample getDisplayedContentSample(android.os.IBinder iBinder, long j, long j2) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayedContentSample(iBinder, j, j2);
    }

    public static final class RefreshRateRange implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.view.SurfaceControl.RefreshRateRange> CREATOR = new android.os.Parcelable.Creator<android.view.SurfaceControl.RefreshRateRange>() { // from class: android.view.SurfaceControl.RefreshRateRange.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControl.RefreshRateRange createFromParcel(android.os.Parcel parcel) {
                return new android.view.SurfaceControl.RefreshRateRange(parcel.readFloat(), parcel.readFloat());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControl.RefreshRateRange[] newArray(int i) {
                return new android.view.SurfaceControl.RefreshRateRange[i];
            }
        };
        public static final float FLOAT_TOLERANCE = 0.01f;
        public static final java.lang.String TAG = "RefreshRateRange";
        public float max;
        public float min;

        public RefreshRateRange() {
        }

        public RefreshRateRange(float f, float f2) {
            if (f < 0.0f || f2 < 0.0f || f > 0.01f + f2) {
                android.util.Slog.e(TAG, "Wrong values for min and max when initializing RefreshRateRange : " + f + " " + f2);
                this.max = 0.0f;
                this.min = 0.0f;
            } else {
                if (f > f2) {
                    f2 = f;
                    f = f2;
                }
                this.min = f;
                this.max = f2;
            }
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.view.SurfaceControl.RefreshRateRange)) {
                return false;
            }
            android.view.SurfaceControl.RefreshRateRange refreshRateRange = (android.view.SurfaceControl.RefreshRateRange) obj;
            return this.min == refreshRateRange.min && this.max == refreshRateRange.max;
        }

        public int hashCode() {
            return java.util.Objects.hash(java.lang.Float.valueOf(this.min), java.lang.Float.valueOf(this.max));
        }

        public java.lang.String toString() {
            return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + this.min + " " + this.max + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        public void copyFrom(android.view.SurfaceControl.RefreshRateRange refreshRateRange) {
            this.min = refreshRateRange.min;
            this.max = refreshRateRange.max;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeFloat(this.min);
            parcel.writeFloat(this.max);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class RefreshRateRanges {
        public static final java.lang.String TAG = "RefreshRateRanges";
        public final android.view.SurfaceControl.RefreshRateRange physical;
        public final android.view.SurfaceControl.RefreshRateRange render;

        public RefreshRateRanges() {
            this.physical = new android.view.SurfaceControl.RefreshRateRange();
            this.render = new android.view.SurfaceControl.RefreshRateRange();
        }

        public RefreshRateRanges(android.view.SurfaceControl.RefreshRateRange refreshRateRange, android.view.SurfaceControl.RefreshRateRange refreshRateRange2) {
            this.physical = new android.view.SurfaceControl.RefreshRateRange(refreshRateRange.min, refreshRateRange.max);
            this.render = new android.view.SurfaceControl.RefreshRateRange(refreshRateRange2.min, refreshRateRange2.max);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.view.SurfaceControl.RefreshRateRanges)) {
                return false;
            }
            android.view.SurfaceControl.RefreshRateRanges refreshRateRanges = (android.view.SurfaceControl.RefreshRateRanges) obj;
            return this.physical.equals(refreshRateRanges.physical) && this.render.equals(refreshRateRanges.render);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.physical, this.render);
        }

        public java.lang.String toString() {
            return "physical: " + this.physical + " render:  " + this.render;
        }

        public void copyFrom(android.view.SurfaceControl.RefreshRateRanges refreshRateRanges) {
            this.physical.copyFrom(refreshRateRanges.physical);
            this.render.copyFrom(refreshRateRanges.render);
        }
    }

    public static final class DesiredDisplayModeSpecs {
        public boolean allowGroupSwitching;
        public final android.view.SurfaceControl.RefreshRateRanges appRequestRanges;
        public int defaultMode;
        public final android.view.SurfaceControl.RefreshRateRanges primaryRanges;

        public DesiredDisplayModeSpecs() {
            this.primaryRanges = new android.view.SurfaceControl.RefreshRateRanges();
            this.appRequestRanges = new android.view.SurfaceControl.RefreshRateRanges();
        }

        public DesiredDisplayModeSpecs(android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            this.primaryRanges = new android.view.SurfaceControl.RefreshRateRanges();
            this.appRequestRanges = new android.view.SurfaceControl.RefreshRateRanges();
            copyFrom(desiredDisplayModeSpecs);
        }

        public DesiredDisplayModeSpecs(int i, boolean z, android.view.SurfaceControl.RefreshRateRanges refreshRateRanges, android.view.SurfaceControl.RefreshRateRanges refreshRateRanges2) {
            this.defaultMode = i;
            this.allowGroupSwitching = z;
            this.primaryRanges = new android.view.SurfaceControl.RefreshRateRanges(refreshRateRanges.physical, refreshRateRanges.render);
            this.appRequestRanges = new android.view.SurfaceControl.RefreshRateRanges(refreshRateRanges2.physical, refreshRateRanges2.render);
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.view.SurfaceControl.DesiredDisplayModeSpecs) && equals((android.view.SurfaceControl.DesiredDisplayModeSpecs) obj);
        }

        public boolean equals(android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            return desiredDisplayModeSpecs != null && this.defaultMode == desiredDisplayModeSpecs.defaultMode && this.allowGroupSwitching == desiredDisplayModeSpecs.allowGroupSwitching && this.primaryRanges.equals(desiredDisplayModeSpecs.primaryRanges) && this.appRequestRanges.equals(desiredDisplayModeSpecs.appRequestRanges);
        }

        public int hashCode() {
            return 0;
        }

        public void copyFrom(android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
            this.defaultMode = desiredDisplayModeSpecs.defaultMode;
            this.allowGroupSwitching = desiredDisplayModeSpecs.allowGroupSwitching;
            this.primaryRanges.copyFrom(desiredDisplayModeSpecs.primaryRanges);
            this.appRequestRanges.copyFrom(desiredDisplayModeSpecs.appRequestRanges);
        }

        public java.lang.String toString() {
            return "defaultMode=" + this.defaultMode + " allowGroupSwitching=" + this.allowGroupSwitching + " primaryRanges=" + this.primaryRanges + " appRequestRanges=" + this.appRequestRanges;
        }
    }

    public static boolean setDesiredDisplayModeSpecs(android.os.IBinder iBinder, android.view.SurfaceControl.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        if (desiredDisplayModeSpecs == null) {
            throw new java.lang.IllegalArgumentException("desiredDisplayModeSpecs must not be null");
        }
        if (desiredDisplayModeSpecs.defaultMode < 0) {
            throw new java.lang.IllegalArgumentException("defaultMode must be non-negative");
        }
        return nativeSetDesiredDisplayModeSpecs(iBinder, desiredDisplayModeSpecs);
    }

    public static android.view.SurfaceControl.DesiredDisplayModeSpecs getDesiredDisplayModeSpecs(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDesiredDisplayModeSpecs(iBinder);
    }

    public static android.view.SurfaceControl.DisplayPrimaries getDisplayNativePrimaries(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        return nativeGetDisplayNativePrimaries(iBinder);
    }

    public static boolean setActiveColorMode(android.os.IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        return nativeSetActiveColorMode(iBinder, i);
    }

    public static android.graphics.ColorSpace[] getCompositionColorSpaces() {
        int[] nativeGetCompositionDataspaces = nativeGetCompositionDataspaces();
        android.graphics.ColorSpace colorSpace = android.graphics.ColorSpace.get(android.graphics.ColorSpace.Named.SRGB);
        android.graphics.ColorSpace[] colorSpaceArr = new android.graphics.ColorSpace[2];
        colorSpaceArr[0] = colorSpace;
        colorSpaceArr[1] = colorSpace;
        if (nativeGetCompositionDataspaces.length == 2) {
            for (int i = 0; i < 2; i++) {
                android.graphics.ColorSpace fromDataSpace = android.graphics.ColorSpace.getFromDataSpace(nativeGetCompositionDataspaces[i]);
                if (fromDataSpace != null) {
                    colorSpaceArr[i] = fromDataSpace;
                }
            }
        }
        return colorSpaceArr;
    }

    public static android.hardware.OverlayProperties getOverlaySupport() {
        return nativeGetOverlaySupport();
    }

    public static boolean getBootDisplayModeSupport() {
        return nativeGetBootDisplayModeSupport();
    }

    public static void setBootDisplayMode(android.os.IBinder iBinder, int i) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeSetBootDisplayMode(iBinder, i);
    }

    public static void clearBootDisplayMode(android.os.IBinder iBinder) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeClearBootDisplayMode(iBinder);
    }

    public static void setAutoLowLatencyMode(android.os.IBinder iBinder, boolean z) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeSetAutoLowLatencyMode(iBinder, z);
    }

    public static void setGameContentType(android.os.IBinder iBinder, boolean z) {
        if (iBinder == null) {
            throw new java.lang.IllegalArgumentException("displayToken must not be null");
        }
        nativeSetGameContentType(iBinder, z);
    }

    public static boolean getProtectedContentSupport() {
        return nativeGetProtectedContentSupport();
    }

    public static boolean getDisplayBrightnessSupport(android.os.IBinder iBinder) {
        return nativeGetDisplayBrightnessSupport(iBinder);
    }

    public static boolean setDisplayBrightness(android.os.IBinder iBinder, float f) {
        return setDisplayBrightness(iBinder, f, -1.0f, f, -1.0f);
    }

    public static boolean setDisplayBrightness(android.os.IBinder iBinder, float f, float f2, float f3, float f4) {
        java.util.Objects.requireNonNull(iBinder);
        if (java.lang.Float.isNaN(f3) || f3 > 1.0f || (f3 < 0.0f && f3 != -1.0f)) {
            throw new java.lang.IllegalArgumentException("displayBrightness must be a number between 0.0f  and 1.0f, or -1 to turn the backlight off: " + f3);
        }
        if (java.lang.Float.isNaN(f) || f > 1.0f || (f < 0.0f && f != -1.0f)) {
            throw new java.lang.IllegalArgumentException("sdrBrightness must be a number between 0.0f and 1.0f, or -1 to turn the backlight off: " + f);
        }
        return nativeSetDisplayBrightness(iBinder, f, f2, f3, f4);
    }

    public static android.view.SurfaceControl mirrorSurface(android.view.SurfaceControl surfaceControl) {
        long nativeMirrorSurface = nativeMirrorSurface(surfaceControl.mNativeObject);
        android.view.SurfaceControl surfaceControl2 = new android.view.SurfaceControl();
        surfaceControl2.mName = surfaceControl.mName + " (mirror)";
        surfaceControl2.assignNativeObject(nativeMirrorSurface, "mirrorSurface");
        return surfaceControl2;
    }

    private static void validateColorArg(float[] fArr) {
        if (fArr.length != 4) {
            throw new java.lang.IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
        }
        for (float f : fArr) {
            if (f < 0.0f || f > 1.0f) {
                throw new java.lang.IllegalArgumentException("Color must be specified as a float array with four values to represent r, g, b, a in range [0..1]");
            }
        }
    }

    public static void setGlobalShadowSettings(float[] fArr, float[] fArr2, float f, float f2, float f3) {
        validateColorArg(fArr);
        validateColorArg(fArr2);
        nativeSetGlobalShadowSettings(fArr, fArr2, f, f2, f3);
    }

    public static android.hardware.graphics.common.DisplayDecorationSupport getDisplayDecorationSupport(android.os.IBinder iBinder) {
        return nativeGetDisplayDecorationSupport(iBinder);
    }

    public static void addJankDataListener(android.view.SurfaceControl.OnJankDataListener onJankDataListener, android.view.SurfaceControl surfaceControl) {
        nativeAddJankDataListener(onJankDataListener.mNativePtr.get(), surfaceControl.mNativeObject);
    }

    public static void removeJankDataListener(android.view.SurfaceControl.OnJankDataListener onJankDataListener) {
        nativeRemoveJankDataListener(onJankDataListener.mNativePtr.get());
    }

    public static int getGPUContextPriority() {
        return nativeGetGPUContextPriority();
    }

    public static boolean bootFinished() {
        return nativeBootFinished();
    }

    public static final class TransactionStats {
        private long mLatchTimeNanos;
        private android.hardware.SyncFence mSyncFence;

        private TransactionStats(long j, long j2) {
            this.mLatchTimeNanos = j;
            this.mSyncFence = new android.hardware.SyncFence(j2);
        }

        public void close() {
            this.mSyncFence.close();
        }

        public long getLatchTimeNanos() {
            return this.mLatchTimeNanos;
        }

        public android.hardware.SyncFence getPresentFence() {
            return new android.hardware.SyncFence(this.mSyncFence);
        }
    }

    public static final class TrustedPresentationThresholds {
        private final float mMinAlpha;
        private final float mMinFractionRendered;
        private final int mStabilityRequirementMs;

        public TrustedPresentationThresholds(float f, float f2, int i) {
            this.mMinAlpha = f;
            this.mMinFractionRendered = f2;
            this.mStabilityRequirementMs = i;
            checkValid();
        }

        private void checkValid() {
            if (this.mMinAlpha <= 0.0f || this.mMinFractionRendered <= 0.0f || this.mStabilityRequirementMs < 1) {
                throw new java.lang.IllegalArgumentException("TrustedPresentationThresholds values are invalid");
            }
        }
    }

    public static abstract class TrustedPresentationCallback {
        private static final libcore.util.NativeAllocationRegistry sRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.view.SurfaceControl.TrustedPresentationCallback.class.getClassLoader(), android.view.SurfaceControl.getNativeTrustedPresentationCallbackFinalizer());
        private final java.lang.Runnable mFreeNativeResources;
        private final long mNativeObject;

        public abstract void onTrustedPresentationChanged(boolean z);

        private TrustedPresentationCallback() {
            this.mNativeObject = android.view.SurfaceControl.nativeCreateTpc(this);
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
        }
    }

    public static class Transaction implements java.io.Closeable, android.os.Parcelable {
        java.lang.Runnable mFreeNativeResources;
        public long mNativeObject;
        private final android.util.ArrayMap<android.view.SurfaceControl, android.view.SurfaceControl> mReparentedSurfaces;
        private final android.util.ArrayMap<android.view.SurfaceControl, android.graphics.Point> mResizedSurfaces;
        public static final libcore.util.NativeAllocationRegistry sRegistry = new libcore.util.NativeAllocationRegistry(android.view.SurfaceControl.Transaction.class.getClassLoader(), android.view.SurfaceControl.nativeGetNativeTransactionFinalizer(), 512);
        private static final float[] INVALID_COLOR = {-1.0f, -1.0f, -1.0f};
        public static final android.os.Parcelable.Creator<android.view.SurfaceControl.Transaction> CREATOR = new android.os.Parcelable.Creator<android.view.SurfaceControl.Transaction>() { // from class: android.view.SurfaceControl.Transaction.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControl.Transaction createFromParcel(android.os.Parcel parcel) {
                return new android.view.SurfaceControl.Transaction(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.view.SurfaceControl.Transaction[] newArray(int i) {
                return new android.view.SurfaceControl.Transaction[i];
            }
        };

        protected void checkPreconditions(android.view.SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
        }

        public Transaction() {
            this(android.view.SurfaceControl.nativeCreateTransaction());
        }

        private Transaction(long j) {
            this.mResizedSurfaces = new android.util.ArrayMap<>();
            this.mReparentedSurfaces = new android.util.ArrayMap<>();
            this.mNativeObject = j;
            this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
            if (!android.view.SurfaceControlRegistry.sCallStackDebuggingInitialized) {
                android.view.SurfaceControlRegistry.initializeCallStackDebugging();
            }
        }

        private Transaction(android.os.Parcel parcel) {
            this.mResizedSurfaces = new android.util.ArrayMap<>();
            this.mReparentedSurfaces = new android.util.ArrayMap<>();
            readFromParcel(parcel);
        }

        public static void setDefaultApplyToken(android.os.IBinder iBinder) {
            android.view.SurfaceControl.nativeSetDefaultApplyToken(iBinder);
        }

        public static android.os.IBinder getDefaultApplyToken() {
            return android.view.SurfaceControl.nativeGetDefaultApplyToken();
        }

        public void apply() {
            apply(false);
        }

        public void applyAsyncUnsafe() {
            apply(false, true);
        }

        public void clear() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            if (this.mNativeObject != 0) {
                android.view.SurfaceControl.nativeClearTransaction(this.mNativeObject);
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mResizedSurfaces.clear();
            this.mReparentedSurfaces.clear();
            this.mFreeNativeResources.run();
            this.mNativeObject = 0L;
        }

        public void apply(boolean z) {
            apply(z, false);
        }

        private void apply(boolean z, boolean z2) {
            applyResizedSurfaces();
            notifyReparentedSurfaces();
            android.view.SurfaceControl.nativeApplyTransaction(this.mNativeObject, z, z2);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("apply", this, null, null);
            }
        }

        protected void applyResizedSurfaces() {
            for (int size = this.mResizedSurfaces.size() - 1; size >= 0; size--) {
                android.graphics.Point valueAt = this.mResizedSurfaces.valueAt(size);
                android.view.SurfaceControl keyAt = this.mResizedSurfaces.keyAt(size);
                synchronized (keyAt.mLock) {
                    keyAt.resize(valueAt.x, valueAt.y);
                }
            }
            this.mResizedSurfaces.clear();
        }

        protected void notifyReparentedSurfaces() {
            for (int size = this.mReparentedSurfaces.size() - 1; size >= 0; size--) {
                android.view.SurfaceControl keyAt = this.mReparentedSurfaces.keyAt(size);
                synchronized (keyAt.mLock) {
                    int size2 = keyAt.mReparentListeners != null ? keyAt.mReparentListeners.size() : 0;
                    for (int i = 0; i < size2; i++) {
                        ((android.view.SurfaceControl.OnReparentListener) keyAt.mReparentListeners.get(i)).onReparent(this, this.mReparentedSurfaces.valueAt(size));
                    }
                    this.mReparentedSurfaces.removeAt(size);
                }
            }
        }

        public android.view.SurfaceControl.Transaction setVisibility(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                return show(surfaceControl);
            }
            return hide(surfaceControl);
        }

        public android.view.SurfaceControl.Transaction setFrameRateSelectionPriority(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFrameRateSelectionPriority(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction show(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging(android.view.ThreadedRenderer.OVERDRAW_PROPERTY_SHOW, this, surfaceControl, null);
            }
            android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 1);
            return this;
        }

        public android.view.SurfaceControl.Transaction hide(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("hide", this, surfaceControl, null);
            }
            android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 1, 1);
            return this;
        }

        public android.view.SurfaceControl.Transaction setPosition(android.view.SurfaceControl surfaceControl, float f, float f2) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setPosition", this, surfaceControl, "x=" + f + " y=" + f2);
            }
            android.view.SurfaceControl.nativeSetPosition(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setScale(android.view.SurfaceControl surfaceControl, float f, float f2) {
            checkPreconditions(surfaceControl);
            com.android.internal.util.Preconditions.checkArgument(f >= 0.0f, "Negative value passed in for scaleX");
            com.android.internal.util.Preconditions.checkArgument(f2 >= 0.0f, "Negative value passed in for scaleY");
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setScale", this, surfaceControl, "sx=" + f + " sy=" + f2);
            }
            android.view.SurfaceControl.nativeSetScale(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setBufferSize(android.view.SurfaceControl surfaceControl, int i, int i2) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBufferSize", this, surfaceControl, "w=" + i + " h=" + i2);
            }
            this.mResizedSurfaces.put(surfaceControl, new android.graphics.Point(i, i2));
            return this;
        }

        public android.view.SurfaceControl.Transaction setFixedTransformHint(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setFixedTransformHint", this, surfaceControl, "hint=" + i);
            }
            android.view.SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction unsetFixedTransformHint(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, surfaceControl, null);
            }
            android.view.SurfaceControl.nativeSetFixedTransformHint(this.mNativeObject, surfaceControl.mNativeObject, -1);
            return this;
        }

        public android.view.SurfaceControl.Transaction setLayer(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setLayer", this, surfaceControl, "z=" + i);
            }
            android.view.SurfaceControl.nativeSetLayer(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setRelativeLayer(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl surfaceControl2, int i) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setRelativeLayer", this, surfaceControl, "relTo=" + surfaceControl2 + " z=" + i);
            }
            android.view.SurfaceControl.nativeSetRelativeLayer(this.mNativeObject, surfaceControl.mNativeObject, surfaceControl2.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setTransparentRegionHint(android.view.SurfaceControl surfaceControl, android.graphics.Region region) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetFixedTransformHint", this, surfaceControl, "region=" + region);
            }
            android.view.SurfaceControl.nativeSetTransparentRegionHint(this.mNativeObject, surfaceControl.mNativeObject, region);
            return this;
        }

        public android.view.SurfaceControl.Transaction setAlpha(android.view.SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setAlpha", this, surfaceControl, "alpha=" + f);
            }
            android.view.SurfaceControl.nativeSetAlpha(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public android.view.SurfaceControl.Transaction setInputWindowInfo(android.view.SurfaceControl surfaceControl, android.view.InputWindowHandle inputWindowHandle) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetInputWindowInfo(this.mNativeObject, surfaceControl.mNativeObject, inputWindowHandle);
            return this;
        }

        public android.view.SurfaceControl.Transaction addWindowInfosReportedListener(java.lang.Runnable runnable) {
            android.view.SurfaceControl.nativeAddWindowInfosReportedListener(this.mNativeObject, runnable);
            return this;
        }

        public android.view.SurfaceControl.Transaction setGeometry(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect, android.graphics.Rect rect2, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetGeometry(this.mNativeObject, surfaceControl.mNativeObject, rect, rect2, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setMatrix(android.view.SurfaceControl surfaceControl, float f, float f2, float f3, float f4) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setMatrix", this, surfaceControl, "dsdx=" + f + " dtdx=" + f2 + " dtdy=" + f3 + " dsdy=" + f4);
            }
            android.view.SurfaceControl.nativeSetMatrix(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f4);
            return this;
        }

        public android.view.SurfaceControl.Transaction setMatrix(android.view.SurfaceControl surfaceControl, android.graphics.Matrix matrix, float[] fArr) {
            matrix.getValues(fArr);
            setMatrix(surfaceControl, fArr[0], fArr[3], fArr[1], fArr[4]);
            setPosition(surfaceControl, fArr[2], fArr[5]);
            return this;
        }

        public android.view.SurfaceControl.Transaction setColorTransform(android.view.SurfaceControl surfaceControl, float[] fArr, float[] fArr2) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetColorTransform(this.mNativeObject, surfaceControl.mNativeObject, fArr, fArr2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setColorSpaceAgnostic(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetColorSpaceAgnostic(this.mNativeObject, surfaceControl.mNativeObject, z);
            return this;
        }

        @java.lang.Deprecated
        public android.view.SurfaceControl.Transaction setWindowCrop(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", this, surfaceControl, "crop=" + rect);
            }
            if (rect != null) {
                android.view.SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
            } else {
                android.view.SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, 0, 0);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setCrop(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCrop", this, surfaceControl, "crop=" + rect);
            }
            if (rect != null) {
                com.android.internal.util.Preconditions.checkArgument(rect.isValid(), "Crop " + rect + " isn't valid");
                android.view.SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
            } else {
                android.view.SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, 0, 0);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setWindowCrop(android.view.SurfaceControl surfaceControl, int i, int i2) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setWindowCrop", this, surfaceControl, "w=" + i + " h=" + i2);
            }
            android.view.SurfaceControl.nativeSetWindowCrop(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, i, i2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setCornerRadius(android.view.SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setCornerRadius", this, surfaceControl, "cornerRadius=" + f);
            }
            android.view.SurfaceControl.nativeSetCornerRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public android.view.SurfaceControl.Transaction setBackgroundBlurRadius(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setBackgroundBlurRadius", this, surfaceControl, "radius=" + i);
            }
            android.view.SurfaceControl.nativeSetBackgroundBlurRadius(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setBlurRegions(android.view.SurfaceControl surfaceControl, float[][] fArr) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetBlurRegions(this.mNativeObject, surfaceControl.mNativeObject, fArr, fArr.length);
            return this;
        }

        public android.view.SurfaceControl.Transaction setStretchEffect(android.view.SurfaceControl surfaceControl, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetStretchEffect(this.mNativeObject, surfaceControl.mNativeObject, f, f2, f3, f4, f5, f6, f7, f8, f9, f10);
            return this;
        }

        public android.view.SurfaceControl.Transaction setLayerStack(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetLayerStack(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction reparent(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl surfaceControl2) {
            long j;
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("reparent", this, surfaceControl, "newParent=" + surfaceControl2);
            }
            if (surfaceControl2 == null) {
                j = 0;
            } else {
                surfaceControl2.checkNotReleased();
                j = surfaceControl2.mNativeObject;
            }
            android.view.SurfaceControl.nativeReparent(this.mNativeObject, surfaceControl.mNativeObject, j);
            this.mReparentedSurfaces.put(surfaceControl, surfaceControl2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setColor(android.view.SurfaceControl surfaceControl, float[] fArr) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("reparent", this, surfaceControl, "r=" + fArr[0] + " g=" + fArr[1] + " b=" + fArr[2]);
            }
            android.view.SurfaceControl.nativeSetColor(this.mNativeObject, surfaceControl.mNativeObject, fArr);
            return this;
        }

        public android.view.SurfaceControl.Transaction unsetColor(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("unsetColor", this, surfaceControl, null);
            }
            android.view.SurfaceControl.nativeSetColor(this.mNativeObject, surfaceControl.mNativeObject, INVALID_COLOR);
            return this;
        }

        public android.view.SurfaceControl.Transaction setSecure(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setSecure", this, surfaceControl, "secure=" + z);
            }
            if (z) {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 128, 128);
            } else {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 128);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplayDecoration(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 512, 512);
            } else {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 512);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setOpaque(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setOpaque", this, surfaceControl, "opaque=" + z);
            }
            if (z) {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 2, 2);
            } else {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 2);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplaySurface(android.os.IBinder iBinder, android.view.Surface surface) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("displayToken must not be null");
            }
            if (surface != null) {
                synchronized (surface.mLock) {
                    android.view.SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, surface.mNativeObject);
                }
            } else {
                android.view.SurfaceControl.nativeSetDisplaySurface(this.mNativeObject, iBinder, 0L);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplayLayerStack(android.os.IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("displayToken must not be null");
            }
            android.view.SurfaceControl.nativeSetDisplayLayerStack(this.mNativeObject, iBinder, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplayFlags(android.os.IBinder iBinder, int i) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("displayToken must not be null");
            }
            android.view.SurfaceControl.nativeSetDisplayFlags(this.mNativeObject, iBinder, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplayProjection(android.os.IBinder iBinder, int i, android.graphics.Rect rect, android.graphics.Rect rect2) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("displayToken must not be null");
            }
            if (rect == null) {
                throw new java.lang.IllegalArgumentException("layerStackRect must not be null");
            }
            if (rect2 == null) {
                throw new java.lang.IllegalArgumentException("displayRect must not be null");
            }
            android.view.SurfaceControl.nativeSetDisplayProjection(this.mNativeObject, iBinder, i, rect.left, rect.top, rect.right, rect.bottom, rect2.left, rect2.top, rect2.right, rect2.bottom);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDisplaySize(android.os.IBinder iBinder, int i, int i2) {
            if (iBinder == null) {
                throw new java.lang.IllegalArgumentException("displayToken must not be null");
            }
            if (i <= 0 || i2 <= 0) {
                throw new java.lang.IllegalArgumentException("width and height must be positive");
            }
            android.view.SurfaceControl.nativeSetDisplaySize(this.mNativeObject, iBinder, i, i2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setAnimationTransaction() {
            android.view.SurfaceControl.nativeSetAnimationTransaction(this.mNativeObject);
            return this;
        }

        public android.view.SurfaceControl.Transaction setEarlyWakeupStart() {
            android.view.SurfaceControl.nativeSetEarlyWakeupStart(this.mNativeObject);
            return this;
        }

        public android.view.SurfaceControl.Transaction setEarlyWakeupEnd() {
            android.view.SurfaceControl.nativeSetEarlyWakeupEnd(this.mNativeObject);
            return this;
        }

        public long getId() {
            return android.view.SurfaceControl.nativeGetTransactionId(this.mNativeObject);
        }

        public android.view.SurfaceControl.Transaction setMetadata(android.view.SurfaceControl surfaceControl, int i, int i2) {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            obtain.writeInt(i2);
            try {
                setMetadata(surfaceControl, i, obtain);
                return this;
            } finally {
                obtain.recycle();
            }
        }

        public android.view.SurfaceControl.Transaction setMetadata(android.view.SurfaceControl surfaceControl, int i, android.os.Parcel parcel) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetMetadata(this.mNativeObject, surfaceControl.mNativeObject, i, parcel);
            return this;
        }

        public android.view.SurfaceControl.Transaction setShadowRadius(android.view.SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (android.view.SurfaceControlRegistry.sCallStackDebuggingEnabled) {
                android.view.SurfaceControlRegistry.getProcessInstance().checkCallStackDebugging("setShadowRadius", this, surfaceControl, "radius=" + f);
            }
            android.view.SurfaceControl.nativeSetShadowRadius(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public android.view.SurfaceControl.Transaction setFrameRate(android.view.SurfaceControl surfaceControl, float f, int i) {
            return setFrameRate(surfaceControl, f, i, 0);
        }

        public android.view.SurfaceControl.Transaction setFrameRate(android.view.SurfaceControl surfaceControl, float f, int i, int i2) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFrameRate(this.mNativeObject, surfaceControl.mNativeObject, f, i, i2);
            return this;
        }

        public android.view.SurfaceControl.Transaction clearFrameRate(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFrameRate(this.mNativeObject, surfaceControl.mNativeObject, 0.0f, 0, 1);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDefaultFrameRateCompatibility(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDefaultFrameRateCompatibility(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setFrameRateCategory(android.view.SurfaceControl surfaceControl, int i, boolean z) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFrameRateCategory(this.mNativeObject, surfaceControl.mNativeObject, i, z);
            return this;
        }

        public android.view.SurfaceControl.Transaction setFrameRateSelectionStrategy(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFrameRateSelectionStrategy(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setFocusedWindow(android.os.IBinder iBinder, java.lang.String str, int i) {
            android.view.SurfaceControl.nativeSetFocusedWindow(this.mNativeObject, iBinder, str, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction removeCurrentInputFocus(int i) {
            android.view.SurfaceControl.nativeRemoveCurrentInputFocus(this.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setSkipScreenshot(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            if (z) {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 64, 64);
            } else {
                android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, 0, 64);
            }
            return this;
        }

        @java.lang.Deprecated
        public android.view.SurfaceControl.Transaction setBuffer(android.view.SurfaceControl surfaceControl, android.graphics.GraphicBuffer graphicBuffer) {
            return setBuffer(surfaceControl, android.hardware.HardwareBuffer.createFromGraphicBuffer(graphicBuffer));
        }

        public android.view.SurfaceControl.Transaction setBuffer(android.view.SurfaceControl surfaceControl, android.hardware.HardwareBuffer hardwareBuffer) {
            return setBuffer(surfaceControl, hardwareBuffer, null);
        }

        public android.view.SurfaceControl.Transaction unsetBuffer(android.view.SurfaceControl surfaceControl) {
            android.view.SurfaceControl.nativeUnsetBuffer(this.mNativeObject, surfaceControl.mNativeObject);
            return this;
        }

        public android.view.SurfaceControl.Transaction setBuffer(android.view.SurfaceControl surfaceControl, android.hardware.HardwareBuffer hardwareBuffer, android.hardware.SyncFence syncFence) {
            return setBuffer(surfaceControl, hardwareBuffer, syncFence, null);
        }

        public android.view.SurfaceControl.Transaction setBuffer(android.view.SurfaceControl surfaceControl, android.hardware.HardwareBuffer hardwareBuffer, android.hardware.SyncFence syncFence, java.util.function.Consumer<android.hardware.SyncFence> consumer) {
            checkPreconditions(surfaceControl);
            if (syncFence != null) {
                synchronized (syncFence.getLock()) {
                    android.view.SurfaceControl.nativeSetBuffer(this.mNativeObject, surfaceControl.mNativeObject, hardwareBuffer, syncFence.getNativeFence(), consumer);
                }
            } else {
                android.view.SurfaceControl.nativeSetBuffer(this.mNativeObject, surfaceControl.mNativeObject, hardwareBuffer, 0L, consumer);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setBufferTransform(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetBufferTransform(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDamageRegion(android.view.SurfaceControl surfaceControl, android.graphics.Region region) {
            android.view.SurfaceControl.nativeSetDamageRegion(this.mNativeObject, surfaceControl.mNativeObject, region);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDimmingEnabled(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDimmingEnabled(this.mNativeObject, surfaceControl.mNativeObject, z);
            return this;
        }

        @java.lang.Deprecated
        public android.view.SurfaceControl.Transaction setColorSpace(android.view.SurfaceControl surfaceControl, android.graphics.ColorSpace colorSpace) {
            checkPreconditions(surfaceControl);
            if (colorSpace.getId() == android.graphics.ColorSpace.Named.DISPLAY_P3.ordinal()) {
                setDataSpace(surfaceControl, 143261696);
            } else {
                setDataSpace(surfaceControl, 142671872);
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setDataSpace(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDataSpace(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setExtendedRangeBrightness(android.view.SurfaceControl surfaceControl, float f, float f2) {
            checkPreconditions(surfaceControl);
            if (!java.lang.Float.isFinite(f) || f < 1.0f) {
                throw new java.lang.IllegalArgumentException("currentBufferRatio must be finite && >= 1.0f; got " + f);
            }
            if (!java.lang.Float.isFinite(f2) || f2 < 1.0f) {
                throw new java.lang.IllegalArgumentException("desiredRatio must be finite && >= 1.0f; got " + f2);
            }
            android.view.SurfaceControl.nativeSetExtendedRangeBrightness(this.mNativeObject, surfaceControl.mNativeObject, f, f2);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDesiredHdrHeadroom(android.view.SurfaceControl surfaceControl, float f) {
            checkPreconditions(surfaceControl);
            if (!java.lang.Float.isFinite(f) || (f != 0.0f && f < 1.0f)) {
                throw new java.lang.IllegalArgumentException("desiredRatio must be finite && >= 1.0f or 0; got " + f);
            }
            android.view.SurfaceControl.nativeSetDesiredHdrHeadroom(this.mNativeObject, surfaceControl.mNativeObject, f);
            return this;
        }

        public android.view.SurfaceControl.Transaction setCachingHint(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetCachingHint(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setTrustedOverlay(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetTrustedOverlay(this.mNativeObject, surfaceControl.mNativeObject, z);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDropInputMode(android.view.SurfaceControl surfaceControl, int i) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDropInputMode(this.mNativeObject, surfaceControl.mNativeObject, i);
            return this;
        }

        public android.view.SurfaceControl.Transaction setCanOccludePresentation(android.view.SurfaceControl surfaceControl, boolean z) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetFlags(this.mNativeObject, surfaceControl.mNativeObject, z ? 4096 : 0, 4096);
            return this;
        }

        public static void sendSurfaceFlushJankData(android.view.SurfaceControl surfaceControl) {
            surfaceControl.checkNotReleased();
            android.view.SurfaceControl.nativeSurfaceFlushJankData(surfaceControl.mNativeObject);
        }

        public void sanitize(int i, int i2) {
            android.view.SurfaceControl.nativeSanitize(this.mNativeObject, i, i2);
        }

        public android.view.SurfaceControl.Transaction setDesintationFrame(android.view.SurfaceControl surfaceControl, android.graphics.Rect rect) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, surfaceControl.mNativeObject, rect.left, rect.top, rect.right, rect.bottom);
            return this;
        }

        public android.view.SurfaceControl.Transaction setDesintationFrame(android.view.SurfaceControl surfaceControl, int i, int i2) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeSetDestinationFrame(this.mNativeObject, surfaceControl.mNativeObject, 0, 0, i, i2);
            return this;
        }

        public android.view.SurfaceControl.Transaction merge(android.view.SurfaceControl.Transaction transaction) {
            if (this == transaction) {
                return this;
            }
            this.mResizedSurfaces.putAll((android.util.ArrayMap<? extends android.view.SurfaceControl, ? extends android.graphics.Point>) transaction.mResizedSurfaces);
            transaction.mResizedSurfaces.clear();
            this.mReparentedSurfaces.putAll((android.util.ArrayMap<? extends android.view.SurfaceControl, ? extends android.view.SurfaceControl>) transaction.mReparentedSurfaces);
            transaction.mReparentedSurfaces.clear();
            android.view.SurfaceControl.nativeMergeTransaction(this.mNativeObject, transaction.mNativeObject);
            return this;
        }

        public android.view.SurfaceControl.Transaction remove(android.view.SurfaceControl surfaceControl) {
            reparent(surfaceControl, null);
            surfaceControl.release();
            return this;
        }

        public android.view.SurfaceControl.Transaction setFrameTimeline(long j) {
            if (!com.android.window.flags.Flags.sdkDesiredPresentTime()) {
                android.util.Log.w(android.view.SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            android.view.SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, j);
            return this;
        }

        public android.view.SurfaceControl.Transaction setFrameTimelineVsync(long j) {
            android.view.SurfaceControl.nativeSetFrameTimelineVsync(this.mNativeObject, j);
            return this;
        }

        public android.view.SurfaceControl.Transaction addTransactionCommittedListener(final java.util.concurrent.Executor executor, final android.view.SurfaceControl.TransactionCommittedListener transactionCommittedListener) {
            android.view.SurfaceControl.nativeAddTransactionCommittedListener(this.mNativeObject, new android.view.SurfaceControl.TransactionCommittedListener() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda2
                @Override // android.view.SurfaceControl.TransactionCommittedListener
                public final void onTransactionCommitted() {
                    android.view.SurfaceControl.Transaction.lambda$addTransactionCommittedListener$0(executor, transactionCommittedListener);
                }
            });
            return this;
        }

        static /* synthetic */ void lambda$addTransactionCommittedListener$0(java.util.concurrent.Executor executor, final android.view.SurfaceControl.TransactionCommittedListener transactionCommittedListener) {
            java.util.Objects.requireNonNull(transactionCommittedListener);
            executor.execute(new java.lang.Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.SurfaceControl.TransactionCommittedListener.this.onTransactionCommitted();
                }
            });
        }

        public android.view.SurfaceControl.Transaction addTransactionCompletedListener(final java.util.concurrent.Executor executor, final java.util.function.Consumer<android.view.SurfaceControl.TransactionStats> consumer) {
            if (!com.android.window.flags.Flags.sdkDesiredPresentTime()) {
                android.util.Log.w(android.view.SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            android.view.SurfaceControl.nativeAddTransactionCompletedListener(this.mNativeObject, new java.util.function.Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    executor.execute(new java.lang.Runnable() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            r1.andThen(new java.util.function.Consumer() { // from class: android.view.SurfaceControl$Transaction$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(java.lang.Object obj2) {
                                    ((android.view.SurfaceControl.TransactionStats) obj2).close();
                                }
                            }).accept(r2);
                        }
                    });
                }
            });
            return this;
        }

        /* renamed from: android.view.SurfaceControl$Transaction$1, reason: invalid class name */
        class AnonymousClass1 extends android.view.SurfaceControl.TrustedPresentationCallback {
            final /* synthetic */ java.util.concurrent.Executor val$executor;
            final /* synthetic */ java.util.function.Consumer val$listener;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(java.util.concurrent.Executor executor, java.util.function.Consumer consumer) {
                super();
                this.val$executor = executor;
                this.val$listener = consumer;
            }

            @Override // android.view.SurfaceControl.TrustedPresentationCallback
            public void onTrustedPresentationChanged(final boolean z) {
                java.util.concurrent.Executor executor = this.val$executor;
                final java.util.function.Consumer consumer = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.view.SurfaceControl$Transaction$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        consumer.accept(java.lang.Boolean.valueOf(z));
                    }
                });
            }
        }

        public android.view.SurfaceControl.Transaction setTrustedPresentationCallback(android.view.SurfaceControl surfaceControl, android.view.SurfaceControl.TrustedPresentationThresholds trustedPresentationThresholds, java.util.concurrent.Executor executor, java.util.function.Consumer<java.lang.Boolean> consumer) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.Transaction.AnonymousClass1 anonymousClass1 = new android.view.SurfaceControl.Transaction.AnonymousClass1(executor, consumer);
            if (surfaceControl.mTrustedPresentationCallback != null) {
                surfaceControl.mTrustedPresentationCallback.mFreeNativeResources.run();
            }
            android.view.SurfaceControl.nativeSetTrustedPresentationCallback(this.mNativeObject, surfaceControl.mNativeObject, ((android.view.SurfaceControl.TrustedPresentationCallback) anonymousClass1).mNativeObject, trustedPresentationThresholds);
            surfaceControl.mTrustedPresentationCallback = anonymousClass1;
            return this;
        }

        public android.view.SurfaceControl.Transaction clearTrustedPresentationCallback(android.view.SurfaceControl surfaceControl) {
            checkPreconditions(surfaceControl);
            android.view.SurfaceControl.nativeClearTrustedPresentationCallback(this.mNativeObject, surfaceControl.mNativeObject);
            if (surfaceControl.mTrustedPresentationCallback != null) {
                surfaceControl.mTrustedPresentationCallback.mFreeNativeResources.run();
                surfaceControl.mTrustedPresentationCallback = null;
            }
            return this;
        }

        public android.view.SurfaceControl.Transaction setDesiredPresentTimeNanos(long j) {
            if (!com.android.window.flags.Flags.sdkDesiredPresentTime()) {
                android.util.Log.w(android.view.SurfaceControl.TAG, "addTransactionCompletedListener was called but flag is disabled");
                return this;
            }
            android.view.SurfaceControl.nativeSetDesiredPresentTimeNanos(this.mNativeObject, j);
            return this;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            if (this.mNativeObject == 0) {
                parcel.writeInt(0);
                return;
            }
            parcel.writeInt(1);
            android.view.SurfaceControl.nativeWriteTransactionToParcel(this.mNativeObject, parcel);
            if ((i & 1) != 0) {
                android.view.SurfaceControl.nativeClearTransaction(this.mNativeObject);
            }
        }

        private void readFromParcel(android.os.Parcel parcel) {
            this.mNativeObject = 0L;
            if (parcel.readInt() != 0) {
                this.mNativeObject = android.view.SurfaceControl.nativeReadTransactionFromParcel(parcel);
                this.mFreeNativeResources = sRegistry.registerNativeAllocation(this, this.mNativeObject);
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class LockDebuggingTransaction extends android.view.SurfaceControl.Transaction {
        java.lang.Object mMonitor;

        public LockDebuggingTransaction(java.lang.Object obj) {
            this.mMonitor = obj;
        }

        @Override // android.view.SurfaceControl.Transaction
        protected void checkPreconditions(android.view.SurfaceControl surfaceControl) {
            super.checkPreconditions(surfaceControl);
            if (!java.lang.Thread.holdsLock(this.mMonitor)) {
                throw new java.lang.RuntimeException("Unlocked access to synchronized SurfaceControl.Transaction");
            }
        }
    }

    public void resize(int i, int i2) {
        this.mWidth = i;
        this.mHeight = i2;
        nativeUpdateDefaultBufferSize(this.mNativeObject, i, i2);
    }

    public int getTransformHint() {
        checkNotReleased();
        return nativeGetTransformHint(this.mNativeObject);
    }

    public void setTransformHint(int i) {
        nativeSetTransformHint(this.mNativeObject, i);
    }

    public int getLayerId() {
        if (this.mNativeObject != 0) {
            return nativeGetLayerId(this.mNativeObject);
        }
        return -1;
    }

    private static void invokeReleaseCallback(java.util.function.Consumer<android.hardware.SyncFence> consumer, long j) {
        consumer.accept(new android.hardware.SyncFence(j));
    }

    public static android.gui.StalledTransactionInfo getStalledTransactionInfo(int i) {
        return nativeGetStalledTransactionInfo(i);
    }
}
