package android.view;

/* loaded from: classes4.dex */
public abstract class DisplayEventReceiver {
    public static final int EVENT_REGISTRATION_FRAME_RATE_OVERRIDE_FLAG = 2;
    public static final int EVENT_REGISTRATION_MODE_CHANGED_FLAG = 1;
    private static final java.lang.String TAG = "DisplayEventReceiver";
    public static final int VSYNC_SOURCE_APP = 0;
    public static final int VSYNC_SOURCE_SURFACE_FLINGER = 1;
    private static final libcore.util.NativeAllocationRegistry sNativeAllocationRegistry = libcore.util.NativeAllocationRegistry.createMalloced(android.view.DisplayEventReceiver.class.getClassLoader(), nativeGetDisplayEventReceiverFinalizer());
    private java.lang.Runnable mFreeNativeResources;
    private android.os.MessageQueue mMessageQueue;
    private long mReceiverPtr;
    private final android.view.DisplayEventReceiver.VsyncEventData mVsyncEventData;

    private static native long nativeGetDisplayEventReceiverFinalizer();

    private static native android.view.DisplayEventReceiver.VsyncEventData nativeGetLatestVsyncEventData(long j);

    private static native long nativeInit(java.lang.ref.WeakReference<android.view.DisplayEventReceiver> weakReference, java.lang.ref.WeakReference<android.view.DisplayEventReceiver.VsyncEventData> weakReference2, android.os.MessageQueue messageQueue, int i, int i2, long j);

    @dalvik.annotation.optimization.FastNative
    private static native void nativeScheduleVsync(long j);

    public DisplayEventReceiver(android.os.Looper looper) {
        this(looper, 0, 0, 0L);
    }

    public DisplayEventReceiver(android.os.Looper looper, int i, int i2) {
        this(looper, i, i2, 0L);
    }

    public DisplayEventReceiver(android.os.Looper looper, int i, int i2, long j) {
        this.mVsyncEventData = new android.view.DisplayEventReceiver.VsyncEventData();
        if (looper == null) {
            throw new java.lang.IllegalArgumentException("looper must not be null");
        }
        this.mMessageQueue = looper.getQueue();
        this.mReceiverPtr = nativeInit(new java.lang.ref.WeakReference(this), new java.lang.ref.WeakReference(this.mVsyncEventData), this.mMessageQueue, i, i2, j);
        this.mFreeNativeResources = sNativeAllocationRegistry.registerNativeAllocation(this, this.mReceiverPtr);
    }

    public void dispose() {
        if (this.mReceiverPtr != 0) {
            this.mFreeNativeResources.run();
            this.mReceiverPtr = 0L;
        }
        this.mMessageQueue = null;
    }

    public static final class VsyncEventData {
        static final int FRAME_TIMELINES_CAPACITY = 7;
        public long frameInterval;
        public final android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline[] frameTimelines;
        public int frameTimelinesLength;
        public int preferredFrameTimelineIndex;

        public static class FrameTimeline {
            public long deadline;
            public long expectedPresentationTime;
            public long vsyncId;

            FrameTimeline() {
                this.vsyncId = -1L;
                this.deadline = java.lang.System.nanoTime() + android.app.tare.EconomyManager.DEFAULT_AM_REWARD_TOP_ACTIVITY_ONGOING_CAKES;
                this.expectedPresentationTime = this.deadline + android.app.tare.EconomyManager.DEFAULT_AM_REWARD_TOP_ACTIVITY_ONGOING_CAKES;
            }

            FrameTimeline(long j, long j2, long j3) {
                this.vsyncId = -1L;
                this.vsyncId = j;
                this.expectedPresentationTime = j2;
                this.deadline = j3;
            }

            void copyFrom(android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline frameTimeline) {
                this.vsyncId = frameTimeline.vsyncId;
                this.expectedPresentationTime = frameTimeline.expectedPresentationTime;
                this.deadline = frameTimeline.deadline;
            }
        }

        VsyncEventData() {
            this.frameInterval = -1L;
            this.preferredFrameTimelineIndex = 0;
            this.frameTimelinesLength = 1;
            this.frameTimelines = new android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline[7];
            for (int i = 0; i < this.frameTimelines.length; i++) {
                this.frameTimelines[i] = new android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline();
            }
        }

        VsyncEventData(android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline[] frameTimelineArr, int i, int i2, long j) {
            this.frameInterval = -1L;
            this.preferredFrameTimelineIndex = 0;
            this.frameTimelinesLength = 1;
            this.frameTimelines = frameTimelineArr;
            this.preferredFrameTimelineIndex = i;
            this.frameTimelinesLength = i2;
            this.frameInterval = j;
        }

        void copyFrom(android.view.DisplayEventReceiver.VsyncEventData vsyncEventData) {
            this.preferredFrameTimelineIndex = vsyncEventData.preferredFrameTimelineIndex;
            this.frameTimelinesLength = vsyncEventData.frameTimelinesLength;
            this.frameInterval = vsyncEventData.frameInterval;
            for (int i = 0; i < this.frameTimelines.length; i++) {
                this.frameTimelines[i].copyFrom(vsyncEventData.frameTimelines[i]);
            }
        }

        public android.view.DisplayEventReceiver.VsyncEventData.FrameTimeline preferredFrameTimeline() {
            return this.frameTimelines[this.preferredFrameTimelineIndex];
        }
    }

    public void onVsync(long j, long j2, int i, android.view.DisplayEventReceiver.VsyncEventData vsyncEventData) {
    }

    public void onHotplug(long j, long j2, boolean z) {
    }

    public void onHotplugConnectionError(long j, int i) {
    }

    public void onModeChanged(long j, long j2, int i, long j3) {
    }

    public void onHdcpLevelsChanged(long j, int i, int i2) {
    }

    public static class FrameRateOverride {
        public final float frameRateHz;
        public final int uid;

        public FrameRateOverride(int i, float f) {
            this.uid = i;
            this.frameRateHz = f;
        }

        public java.lang.String toString() {
            return "{uid=" + this.uid + " frameRateHz=" + this.frameRateHz + "}";
        }
    }

    public void onFrameRateOverridesChanged(long j, long j2, android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
    }

    public void scheduleVsync() {
        if (this.mReceiverPtr == 0) {
            android.util.Log.w(TAG, "Attempted to schedule a vertical sync pulse but the display event receiver has already been disposed.");
        } else {
            nativeScheduleVsync(this.mReceiverPtr);
        }
    }

    android.view.DisplayEventReceiver.VsyncEventData getLatestVsyncEventData() {
        return nativeGetLatestVsyncEventData(this.mReceiverPtr);
    }

    private void dispatchVsync(long j, long j2, int i) {
        onVsync(j, j2, i, this.mVsyncEventData);
    }

    private void dispatchHotplug(long j, long j2, boolean z) {
        onHotplug(j, j2, z);
    }

    private void dispatchHotplugConnectionError(long j, int i) {
        onHotplugConnectionError(j, i);
    }

    private void dispatchModeChanged(long j, long j2, int i, long j3) {
        onModeChanged(j, j2, i, j3);
    }

    private void dispatchFrameRateOverrides(long j, long j2, android.view.DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr) {
        onFrameRateOverridesChanged(j, j2, frameRateOverrideArr);
    }

    private void dispatchHdcpLevelsChanged(long j, int i, int i2) {
        onHdcpLevelsChanged(j, i, i2);
    }
}
