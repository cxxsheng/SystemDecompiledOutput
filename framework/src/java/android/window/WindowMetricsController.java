package android.window;

/* loaded from: classes4.dex */
public final class WindowMetricsController {
    private final android.content.Context mContext;

    public WindowMetricsController(android.content.Context context) {
        this.mContext = context;
    }

    public android.view.WindowMetrics getCurrentWindowMetrics() {
        return getWindowMetricsInternal(false);
    }

    public android.view.WindowMetrics getMaximumWindowMetrics() {
        return getWindowMetricsInternal(true);
    }

    private android.view.WindowMetrics getWindowMetricsInternal(boolean z) {
        android.graphics.Rect maxBounds;
        float f;
        final boolean isScreenRound;
        final int activityType;
        synchronized (android.app.ResourcesManager.getInstance()) {
            android.content.res.Configuration configuration = this.mContext.getResources().getConfiguration();
            android.app.WindowConfiguration windowConfiguration = configuration.windowConfiguration;
            maxBounds = z ? windowConfiguration.getMaxBounds() : windowConfiguration.getBounds();
            f = configuration.densityDpi * 0.00625f;
            isScreenRound = configuration.isScreenRound();
            activityType = windowConfiguration.getActivityType();
        }
        final android.os.IBinder token = android.content.Context.getToken(this.mContext);
        final android.graphics.Rect rect = maxBounds;
        return new android.view.WindowMetrics(new android.graphics.Rect(maxBounds), (java.util.function.Supplier<android.view.WindowInsets>) new java.util.function.Supplier() { // from class: android.window.WindowMetricsController$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final java.lang.Object get() {
                android.view.WindowInsets lambda$getWindowMetricsInternal$0;
                lambda$getWindowMetricsInternal$0 = android.window.WindowMetricsController.this.lambda$getWindowMetricsInternal$0(token, rect, isScreenRound, activityType);
                return lambda$getWindowMetricsInternal$0;
            }
        }, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.WindowInsets lambda$getWindowMetricsInternal$0(android.os.IBinder iBinder, android.graphics.Rect rect, boolean z, int i) {
        return getWindowInsetsFromServerForDisplay(this.mContext.getDisplayId(), iBinder, rect, z, i);
    }

    private static android.view.WindowInsets getWindowInsetsFromServerForDisplay(int i, android.os.IBinder iBinder, android.graphics.Rect rect, boolean z, int i2) {
        try {
            android.view.InsetsState insetsState = new android.view.InsetsState();
            android.view.WindowManagerGlobal.getWindowManagerService().getWindowInsets(i, iBinder, insetsState);
            float overrideInvertedScale = android.content.res.CompatibilityInfo.getOverrideInvertedScale();
            if (overrideInvertedScale != 1.0f) {
                insetsState.scale(overrideInvertedScale);
            }
            return insetsState.calculateInsets(rect, null, z, 48, 0, 0, -1, i2, null);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.Set<android.view.WindowMetrics> getPossibleMaximumWindowMetrics(int i) {
        try {
            java.util.List<android.view.DisplayInfo> possibleDisplayInfo = android.view.WindowManagerGlobal.getWindowManagerService().getPossibleDisplayInfo(i);
            java.util.HashSet hashSet = new java.util.HashSet();
            for (int i2 = 0; i2 < possibleDisplayInfo.size(); i2++) {
                android.view.DisplayInfo displayInfo = possibleDisplayInfo.get(i2);
                android.graphics.Rect rect = new android.graphics.Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
                android.view.WindowInsets windowInsetsFromServerForDisplay = getWindowInsetsFromServerForDisplay(displayInfo.displayId, null, new android.graphics.Rect(0, 0, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight()), (displayInfo.flags & 16) != 0, 0);
                android.view.DisplayCutout displayCutout = displayInfo.displayCutout;
                if (displayCutout != null && displayInfo.rotation != 0) {
                    displayCutout = displayCutout.getRotated(displayInfo.logicalWidth, displayInfo.logicalHeight, displayInfo.rotation, 0);
                }
                hashSet.add(new android.view.WindowMetrics(rect, new android.view.WindowInsets.Builder(windowInsetsFromServerForDisplay).setRoundedCorners(displayInfo.roundedCorners).setDisplayCutout(displayCutout).build(), displayInfo.logicalDensityDpi * 0.00625f));
            }
            return hashSet;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
