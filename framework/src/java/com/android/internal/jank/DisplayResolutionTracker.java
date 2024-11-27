package com.android.internal.jank;

/* loaded from: classes4.dex */
public class DisplayResolutionTracker {
    public static final int RESOLUTION_FHD = 3;
    public static final int RESOLUTION_HD = 2;
    public static final int RESOLUTION_QHD = 4;
    public static final int RESOLUTION_SD = 1;
    public static final int RESOLUTION_UNKNOWN = 0;
    private static final java.lang.String TAG = com.android.internal.jank.DisplayResolutionTracker.class.getSimpleName();
    private final java.lang.Object mLock;
    private final com.android.internal.jank.DisplayResolutionTracker.DisplayInterface mManager;
    private final android.util.SparseArray<java.lang.Integer> mResolutions;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Resolution {
    }

    public DisplayResolutionTracker(android.os.Handler handler) {
        this(com.android.internal.jank.DisplayResolutionTracker.DisplayInterface.getDefault(handler));
    }

    public DisplayResolutionTracker(com.android.internal.jank.DisplayResolutionTracker.DisplayInterface displayInterface) {
        this.mResolutions = new android.util.SparseArray<>();
        this.mLock = new java.lang.Object();
        this.mManager = displayInterface;
        this.mManager.registerDisplayListener(new android.hardware.display.DisplayManager.DisplayListener() { // from class: com.android.internal.jank.DisplayResolutionTracker.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
                com.android.internal.jank.DisplayResolutionTracker.this.updateDisplay(i);
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                com.android.internal.jank.DisplayResolutionTracker.this.updateDisplay(i);
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDisplay(int i) {
        android.view.DisplayInfo displayInfo = this.mManager.getDisplayInfo(i);
        if (displayInfo == null) {
            return;
        }
        int resolution = getResolution(displayInfo);
        synchronized (this.mLock) {
            this.mResolutions.put(i, java.lang.Integer.valueOf(resolution));
        }
    }

    public int getResolution(int i) {
        return this.mResolutions.get(i, 0).intValue();
    }

    public static int getResolution(android.view.DisplayInfo displayInfo) {
        int min = java.lang.Math.min(displayInfo.logicalWidth, displayInfo.logicalHeight);
        int max = java.lang.Math.max(displayInfo.logicalWidth, displayInfo.logicalHeight);
        if (min < 720 || max < 1280) {
            return 1;
        }
        if (min < 1080 || max < 1920) {
            return 2;
        }
        if (min < 1440 || max < 2560) {
            return 3;
        }
        return 4;
    }

    public interface DisplayInterface {
        android.view.DisplayInfo getDisplayInfo(int i);

        void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener);

        static com.android.internal.jank.DisplayResolutionTracker.DisplayInterface getDefault(final android.os.Handler handler) {
            final android.hardware.display.DisplayManagerGlobal displayManagerGlobal = android.hardware.display.DisplayManagerGlobal.getInstance();
            return new com.android.internal.jank.DisplayResolutionTracker.DisplayInterface() { // from class: com.android.internal.jank.DisplayResolutionTracker.DisplayInterface.1
                @Override // com.android.internal.jank.DisplayResolutionTracker.DisplayInterface
                public void registerDisplayListener(android.hardware.display.DisplayManager.DisplayListener displayListener) {
                    android.hardware.display.DisplayManagerGlobal.this.registerDisplayListener(displayListener, handler, 5L, android.app.ActivityThread.currentPackageName());
                }

                @Override // com.android.internal.jank.DisplayResolutionTracker.DisplayInterface
                public android.view.DisplayInfo getDisplayInfo(int i) {
                    return android.hardware.display.DisplayManagerGlobal.this.getDisplayInfo(i);
                }
            };
        }
    }
}
