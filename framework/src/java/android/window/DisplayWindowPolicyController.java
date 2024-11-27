package android.window;

/* loaded from: classes4.dex */
public abstract class DisplayWindowPolicyController {
    private final java.util.Set<java.lang.Integer> mSupportedWindowingModes = new android.util.ArraySet();
    private int mSystemWindowFlags;
    private int mWindowFlags;

    public abstract boolean canActivityBeLaunched(android.content.pm.ActivityInfo activityInfo, android.content.Intent intent, int i, int i2, boolean z);

    protected abstract boolean canContainActivity(android.content.pm.ActivityInfo activityInfo, int i, int i2, boolean z);

    public abstract boolean canShowTasksInHostDeviceRecents();

    public abstract android.content.ComponentName getCustomHomeComponent();

    public abstract boolean keepActivityOnWindowFlagsChanged(android.content.pm.ActivityInfo activityInfo, int i, int i2);

    public DisplayWindowPolicyController() {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.add(1);
        }
    }

    public final boolean isInterestedWindowFlags(int i, int i2) {
        return ((i & this.mWindowFlags) == 0 && (this.mSystemWindowFlags & i2) == 0) ? false : true;
    }

    public final void setInterestedWindowFlags(int i, int i2) {
        this.mWindowFlags = i;
        this.mSystemWindowFlags = i2;
    }

    public final boolean isWindowingModeSupported(int i) {
        boolean contains;
        synchronized (this.mSupportedWindowingModes) {
            contains = this.mSupportedWindowingModes.contains(java.lang.Integer.valueOf(i));
        }
        return contains;
    }

    public final void setSupportedWindowingModes(java.util.Set<java.lang.Integer> set) {
        synchronized (this.mSupportedWindowingModes) {
            this.mSupportedWindowingModes.clear();
            this.mSupportedWindowingModes.addAll(set);
        }
    }

    public boolean canContainActivities(java.util.List<android.content.pm.ActivityInfo> list, int i) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (!canContainActivity(list.get(i2), i, -1, false)) {
                return false;
            }
        }
        return true;
    }

    public void onTopActivityChanged(android.content.ComponentName componentName, int i, int i2) {
    }

    public void onRunningAppsChanged(android.util.ArraySet<java.lang.Integer> arraySet) {
    }

    public boolean isEnteringPipAllowed(int i) {
        return isWindowingModeSupported(2);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + "DisplayWindowPolicyController{" + super.toString() + "}");
        printWriter.println(str + "  mWindowFlags=" + this.mWindowFlags);
        printWriter.println(str + "  mSystemWindowFlags=" + this.mSystemWindowFlags);
    }
}
