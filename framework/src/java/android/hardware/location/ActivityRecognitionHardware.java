package android.hardware.location;

/* loaded from: classes2.dex */
public class ActivityRecognitionHardware extends android.hardware.location.IActivityRecognitionHardware.Stub {
    private static final java.lang.String ENFORCE_HW_PERMISSION_MESSAGE = "Permission 'android.permission.LOCATION_HARDWARE' not granted to access ActivityRecognitionHardware";
    private static final int EVENT_TYPE_COUNT = 3;
    private static final int EVENT_TYPE_DISABLED = 0;
    private static final int EVENT_TYPE_ENABLED = 1;
    private static final java.lang.String HARDWARE_PERMISSION = "android.permission.LOCATION_HARDWARE";
    private static final int INVALID_ACTIVITY_TYPE = -1;
    private static final int NATIVE_SUCCESS_RESULT = 0;
    private static android.hardware.location.ActivityRecognitionHardware sSingletonInstance;
    private final android.content.Context mContext;
    private final android.hardware.location.ActivityRecognitionHardware.SinkList mSinks = new android.hardware.location.ActivityRecognitionHardware.SinkList();
    private final java.lang.String[] mSupportedActivities;
    private final int mSupportedActivitiesCount;
    private final int[][] mSupportedActivitiesEnabledEvents;
    private static final java.lang.String TAG = "ActivityRecognitionHW";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final java.lang.Object sSingletonInstanceLock = new java.lang.Object();

    private static native void nativeClassInit();

    /* JADX INFO: Access modifiers changed from: private */
    public native int nativeDisableActivityEvent(int i, int i2);

    private native int nativeEnableActivityEvent(int i, int i2, long j);

    private native int nativeFlush();

    private native java.lang.String[] nativeGetSupportedActivities();

    private native void nativeInitialize();

    private static native boolean nativeIsSupported();

    private native void nativeRelease();

    static {
        nativeClassInit();
    }

    private static class Event {
        public int activity;
        public long timestamp;
        public int type;

        private Event() {
        }
    }

    private ActivityRecognitionHardware(android.content.Context context) {
        nativeInitialize();
        this.mContext = context;
        this.mSupportedActivities = fetchSupportedActivities();
        this.mSupportedActivitiesCount = this.mSupportedActivities.length;
        this.mSupportedActivitiesEnabledEvents = (int[][]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) java.lang.Integer.TYPE, this.mSupportedActivitiesCount, 3);
    }

    public static android.hardware.location.ActivityRecognitionHardware getInstance(android.content.Context context) {
        android.hardware.location.ActivityRecognitionHardware activityRecognitionHardware;
        synchronized (sSingletonInstanceLock) {
            if (sSingletonInstance == null) {
                sSingletonInstance = new android.hardware.location.ActivityRecognitionHardware(context);
            }
            activityRecognitionHardware = sSingletonInstance;
        }
        return activityRecognitionHardware;
    }

    public static boolean isSupported() {
        return nativeIsSupported();
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public java.lang.String[] getSupportedActivities() {
        super.getSupportedActivities_enforcePermission();
        return this.mSupportedActivities;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean isActivitySupported(java.lang.String str) {
        super.isActivitySupported_enforcePermission();
        return getActivityType(str) != -1;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean registerSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
        super.registerSink_enforcePermission();
        return this.mSinks.register(iActivityRecognitionHardwareSink);
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean unregisterSink(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
        super.unregisterSink_enforcePermission();
        return this.mSinks.unregister(iActivityRecognitionHardwareSink);
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean enableActivityEvent(java.lang.String str, int i, long j) {
        super.enableActivityEvent_enforcePermission();
        int activityType = getActivityType(str);
        if (activityType == -1 || nativeEnableActivityEvent(activityType, i, j) != 0) {
            return false;
        }
        this.mSupportedActivitiesEnabledEvents[activityType][i] = 1;
        return true;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean disableActivityEvent(java.lang.String str, int i) {
        super.disableActivityEvent_enforcePermission();
        int activityType = getActivityType(str);
        if (activityType == -1 || nativeDisableActivityEvent(activityType, i) != 0) {
            return false;
        }
        this.mSupportedActivitiesEnabledEvents[activityType][i] = 0;
        return true;
    }

    @Override // android.hardware.location.IActivityRecognitionHardware
    public boolean flush() {
        super.flush_enforcePermission();
        return nativeFlush() == 0;
    }

    private void onActivityChanged(android.hardware.location.ActivityRecognitionHardware.Event[] eventArr) {
        if (eventArr == null || eventArr.length == 0) {
            if (DEBUG) {
                android.util.Log.d(TAG, "No events to broadcast for onActivityChanged.");
                return;
            }
            return;
        }
        int length = eventArr.length;
        android.hardware.location.ActivityRecognitionEvent[] activityRecognitionEventArr = new android.hardware.location.ActivityRecognitionEvent[length];
        for (int i = 0; i < length; i++) {
            android.hardware.location.ActivityRecognitionHardware.Event event = eventArr[i];
            activityRecognitionEventArr[i] = new android.hardware.location.ActivityRecognitionEvent(getActivityName(event.activity), event.type, event.timestamp);
        }
        android.hardware.location.ActivityChangedEvent activityChangedEvent = new android.hardware.location.ActivityChangedEvent(activityRecognitionEventArr);
        int beginBroadcast = this.mSinks.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                this.mSinks.getBroadcastItem(i2).onActivityChanged(activityChangedEvent);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error delivering activity changed event.", e);
            }
        }
        this.mSinks.finishBroadcast();
    }

    private java.lang.String getActivityName(int i) {
        if (i < 0 || i >= this.mSupportedActivities.length) {
            android.util.Log.e(TAG, java.lang.String.format("Invalid ActivityType: %d, SupportedActivities: %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(this.mSupportedActivities.length)));
            return null;
        }
        return this.mSupportedActivities[i];
    }

    private int getActivityType(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return -1;
        }
        int length = this.mSupportedActivities.length;
        for (int i = 0; i < length; i++) {
            if (str.equals(this.mSupportedActivities[i])) {
                return i;
            }
        }
        return -1;
    }

    private void checkPermissions() {
        this.mContext.enforceCallingPermission("android.permission.LOCATION_HARDWARE", ENFORCE_HW_PERMISSION_MESSAGE);
    }

    private java.lang.String[] fetchSupportedActivities() {
        java.lang.String[] nativeGetSupportedActivities = nativeGetSupportedActivities();
        if (nativeGetSupportedActivities != null) {
            return nativeGetSupportedActivities;
        }
        return new java.lang.String[0];
    }

    private class SinkList extends android.os.RemoteCallbackList<android.hardware.location.IActivityRecognitionHardwareSink> {
        private SinkList() {
        }

        @Override // android.os.RemoteCallbackList
        public void onCallbackDied(android.hardware.location.IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
            int registeredCallbackCount = android.hardware.location.ActivityRecognitionHardware.this.mSinks.getRegisteredCallbackCount();
            if (android.hardware.location.ActivityRecognitionHardware.DEBUG) {
                android.util.Log.d(android.hardware.location.ActivityRecognitionHardware.TAG, "RegisteredCallbackCount: " + registeredCallbackCount);
            }
            if (registeredCallbackCount != 0) {
                return;
            }
            for (int i = 0; i < android.hardware.location.ActivityRecognitionHardware.this.mSupportedActivitiesCount; i++) {
                for (int i2 = 0; i2 < 3; i2++) {
                    disableActivityEventIfEnabled(i, i2);
                }
            }
        }

        private void disableActivityEventIfEnabled(int i, int i2) {
            if (android.hardware.location.ActivityRecognitionHardware.this.mSupportedActivitiesEnabledEvents[i][i2] != 1) {
                return;
            }
            int nativeDisableActivityEvent = android.hardware.location.ActivityRecognitionHardware.this.nativeDisableActivityEvent(i, i2);
            android.hardware.location.ActivityRecognitionHardware.this.mSupportedActivitiesEnabledEvents[i][i2] = 0;
            android.util.Log.e(android.hardware.location.ActivityRecognitionHardware.TAG, java.lang.String.format("DisableActivityEvent: activityType=%d, eventType=%d, result=%d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2), java.lang.Integer.valueOf(nativeDisableActivityEvent)));
        }
    }
}
