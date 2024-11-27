package android.accessibilityservice;

/* loaded from: classes.dex */
public interface AccessibilityTrace {
    public static final long FLAGS_ACCESSIBILITY_INTERACTION_CLIENT = 262144;
    public static final long FLAGS_ACCESSIBILITY_INTERACTION_CONNECTION = 16;
    public static final long FLAGS_ACCESSIBILITY_INTERACTION_CONNECTION_CALLBACK = 32;
    public static final long FLAGS_ACCESSIBILITY_MANAGER = 4;
    public static final long FLAGS_ACCESSIBILITY_MANAGER_CLIENT = 8;
    public static final long FLAGS_ACCESSIBILITY_MANAGER_CLIENT_STATES = 278576;
    public static final long FLAGS_ACCESSIBILITY_SERVICE = 16384;
    public static final long FLAGS_ACCESSIBILITY_SERVICE_CLIENT = 2;
    public static final long FLAGS_ACCESSIBILITY_SERVICE_CONNECTION = 1;
    public static final long FLAGS_FINGERPRINT = 131072;
    public static final long FLAGS_GESTURE = 8192;
    public static final long FLAGS_INPUT_FILTER = 4096;
    public static final long FLAGS_LOGGING_ALL = -1;
    public static final long FLAGS_LOGGING_NONE = 0;
    public static final long FLAGS_MAGNIFICATION_CALLBACK = 2048;
    public static final long FLAGS_MAGNIFICATION_CONNECTION = 128;
    public static final long FLAGS_MAGNIFICATION_CONNECTION_CALLBACK = 256;
    public static final long FLAGS_PACKAGE_BROADCAST_RECEIVER = 32768;
    public static final long FLAGS_REMOTE_MAGNIFICATION_ANIMATION_CALLBACK = 64;
    public static final long FLAGS_USER_BROADCAST_RECEIVER = 65536;
    public static final long FLAGS_WINDOWS_FOR_ACCESSIBILITY_CALLBACK = 1024;
    public static final long FLAGS_WINDOW_MANAGER_INTERNAL = 512;
    public static final java.lang.String NAME_ACCESSIBILITY_SERVICE_CONNECTION = "IAccessibilityServiceConnection";
    public static final java.lang.String NAME_ACCESSIBILITY_SERVICE_CLIENT = "IAccessibilityServiceClient";
    public static final java.lang.String NAME_ACCESSIBILITY_MANAGER = "IAccessibilityManager";
    public static final java.lang.String NAME_ACCESSIBILITY_MANAGER_CLIENT = "IAccessibilityManagerClient";
    public static final java.lang.String NAME_ACCESSIBILITY_INTERACTION_CONNECTION = "IAccessibilityInteractionConnection";
    public static final java.lang.String NAME_ACCESSIBILITY_INTERACTION_CONNECTION_CALLBACK = "IAccessibilityInteractionConnectionCallback";
    public static final java.lang.String NAME_REMOTE_MAGNIFICATION_ANIMATION_CALLBACK = "IRemoteMagnificationAnimationCallback";
    public static final java.lang.String NAME_MAGNIFICATION_CONNECTION = "IMagnificationConnection";
    public static final java.lang.String NAME_MAGNIFICATION_CONNECTION_CALLBACK = "IMagnificationConnectionCallback";
    public static final java.lang.String NAME_WINDOW_MANAGER_INTERNAL = "WindowManagerInternal";
    public static final java.lang.String NAME_WINDOWS_FOR_ACCESSIBILITY_CALLBACK = "WindowsForAccessibilityCallback";
    public static final java.lang.String NAME_MAGNIFICATION_CALLBACK = "MagnificationCallbacks";
    public static final java.lang.String NAME_INPUT_FILTER = "InputFilter";
    public static final java.lang.String NAME_GESTURE = "Gesture";
    public static final java.lang.String NAME_ACCESSIBILITY_SERVICE = "AccessibilityService";
    public static final java.lang.String NAME_PACKAGE_BROADCAST_RECEIVER = "PMBroadcastReceiver";
    public static final java.lang.String NAME_USER_BROADCAST_RECEIVER = "UserBroadcastReceiver";
    public static final java.lang.String NAME_FINGERPRINT = "FingerprintGesture";
    public static final java.lang.String NAME_ACCESSIBILITY_INTERACTION_CLIENT = "AccessibilityInteractionClient";
    public static final java.lang.String NAME_NONE = "None";
    public static final java.lang.String NAME_ALL_LOGGINGS = "AllLoggings";
    public static final java.util.Map<java.lang.String, java.lang.Long> sNamesToFlags = java.util.Map.ofEntries(new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_SERVICE_CONNECTION, 1L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_SERVICE_CLIENT, 2L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_MANAGER, 4L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_MANAGER_CLIENT, 8L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_INTERACTION_CONNECTION, 16L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_INTERACTION_CONNECTION_CALLBACK, 32L), new java.util.AbstractMap.SimpleEntry(NAME_REMOTE_MAGNIFICATION_ANIMATION_CALLBACK, 64L), new java.util.AbstractMap.SimpleEntry(NAME_MAGNIFICATION_CONNECTION, 128L), new java.util.AbstractMap.SimpleEntry(NAME_MAGNIFICATION_CONNECTION_CALLBACK, 256L), new java.util.AbstractMap.SimpleEntry(NAME_WINDOW_MANAGER_INTERNAL, 512L), new java.util.AbstractMap.SimpleEntry(NAME_WINDOWS_FOR_ACCESSIBILITY_CALLBACK, 1024L), new java.util.AbstractMap.SimpleEntry(NAME_MAGNIFICATION_CALLBACK, 2048L), new java.util.AbstractMap.SimpleEntry(NAME_INPUT_FILTER, 4096L), new java.util.AbstractMap.SimpleEntry(NAME_GESTURE, 8192L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_SERVICE, 16384L), new java.util.AbstractMap.SimpleEntry(NAME_PACKAGE_BROADCAST_RECEIVER, 32768L), new java.util.AbstractMap.SimpleEntry(NAME_USER_BROADCAST_RECEIVER, 65536L), new java.util.AbstractMap.SimpleEntry(NAME_FINGERPRINT, 131072L), new java.util.AbstractMap.SimpleEntry(NAME_ACCESSIBILITY_INTERACTION_CLIENT, 262144L), new java.util.AbstractMap.SimpleEntry(NAME_NONE, 0L), new java.util.AbstractMap.SimpleEntry(NAME_ALL_LOGGINGS, -1L));

    int getTraceStateForAccessibilityManagerClientState();

    boolean isA11yTracingEnabled();

    boolean isA11yTracingEnabledForTypes(long j);

    void logTrace(long j, java.lang.String str, long j2, java.lang.String str2, int i, long j3, int i2, java.lang.StackTraceElement[] stackTraceElementArr, java.util.Set<java.lang.String> set);

    void logTrace(java.lang.String str, long j);

    void logTrace(java.lang.String str, long j, java.lang.String str2);

    void startTrace(long j);

    void stopTrace();

    static long getLoggingFlagsFromNames(java.util.List<java.lang.String> list) {
        java.util.Iterator<java.lang.String> it = list.iterator();
        long j = 0;
        while (it.hasNext()) {
            j |= sNamesToFlags.get(it.next()).longValue();
        }
        return j;
    }

    static java.util.List<java.lang.String> getNamesOfLoggingTypes(long j) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.String, java.lang.Long> entry : sNamesToFlags.entrySet()) {
            if ((entry.getValue().longValue() & j) != 0) {
                arrayList.add(entry.getKey());
            }
        }
        return arrayList;
    }
}
