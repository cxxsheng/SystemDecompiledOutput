package com.android.server.notification;

/* loaded from: classes2.dex */
public class ZenLog {
    private static final android.util.LocalLog INTERCEPTION_EVENTS;
    private static final int SIZE;
    private static final android.util.LocalLog STATE_CHANGES;
    private static final int TYPE_ALERT_ON_UPDATED_INTERCEPT = 21;
    private static final int TYPE_CHECK_REPEAT_CALLER = 20;
    private static final int TYPE_CONFIG = 11;
    private static final int TYPE_DISABLE_EFFECTS = 13;
    private static final int TYPE_INTERCEPTED = 1;
    private static final int TYPE_LISTENER_HINTS_CHANGED = 15;
    private static final int TYPE_MATCHES_CALL_FILTER = 18;
    private static final int TYPE_NOT_INTERCEPTED = 12;
    private static final int TYPE_RECORD_CALLER = 19;
    private static final int TYPE_SET_CONSOLIDATED_ZEN_POLICY = 17;
    private static final int TYPE_SET_NOTIFICATION_POLICY = 16;
    private static final int TYPE_SET_RINGER_MODE_EXTERNAL = 3;
    private static final int TYPE_SET_RINGER_MODE_INTERNAL = 4;
    private static final int TYPE_SET_ZEN_MODE = 6;
    private static final int TYPE_SUBSCRIBE = 9;
    private static final int TYPE_SUPPRESSOR_CHANGED = 14;
    private static final int TYPE_UNSUBSCRIBE = 10;

    static {
        SIZE = android.os.Build.IS_DEBUGGABLE ? 200 : 100;
        STATE_CHANGES = new android.util.LocalLog(SIZE);
        INTERCEPTION_EVENTS = new android.util.LocalLog(SIZE);
    }

    public static void traceIntercepted(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str) {
        append(1, notificationRecord.getKey() + "," + str);
    }

    public static void traceNotIntercepted(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str) {
        append(12, notificationRecord.getKey() + "," + str);
    }

    public static void traceAlertOnUpdatedIntercept(com.android.server.notification.NotificationRecord notificationRecord) {
        append(21, notificationRecord.getKey());
    }

    public static void traceSetRingerModeExternal(int i, int i2, java.lang.String str, int i3, int i4) {
        append(3, str + ",e:" + ringerModeToString(i) + "->" + ringerModeToString(i2) + ",i:" + ringerModeToString(i3) + "->" + ringerModeToString(i4));
    }

    public static void traceSetRingerModeInternal(int i, int i2, java.lang.String str, int i3, int i4) {
        append(4, str + ",i:" + ringerModeToString(i) + "->" + ringerModeToString(i2) + ",e:" + ringerModeToString(i3) + "->" + ringerModeToString(i4));
    }

    public static void traceSetZenMode(int i, java.lang.String str) {
        append(6, zenModeToString(i) + "," + str);
    }

    public static void traceSetConsolidatedZenPolicy(android.app.NotificationManager.Policy policy, java.lang.String str) {
        append(17, policy.toString() + "," + str);
    }

    public static void traceSetNotificationPolicy(java.lang.String str, int i, android.app.NotificationManager.Policy policy) {
        append(16, "pkg=" + str + " targetSdk=" + i + " NotificationPolicy=" + policy.toString());
    }

    public static void traceSubscribe(android.net.Uri uri, android.service.notification.IConditionProvider iConditionProvider, android.os.RemoteException remoteException) {
        append(9, uri + "," + subscribeResult(iConditionProvider, remoteException));
    }

    public static void traceUnsubscribe(android.net.Uri uri, android.service.notification.IConditionProvider iConditionProvider, android.os.RemoteException remoteException) {
        append(10, uri + "," + subscribeResult(iConditionProvider, remoteException));
    }

    public static void traceConfig(java.lang.String str, android.content.ComponentName componentName, android.service.notification.ZenModeConfig zenModeConfig, android.service.notification.ZenModeConfig zenModeConfig2, int i) {
        android.service.notification.ZenModeDiff.ConfigDiff configDiff = new android.service.notification.ZenModeDiff.ConfigDiff(zenModeConfig, zenModeConfig2);
        if (!configDiff.hasDiff()) {
            append(11, str + " no changes");
            return;
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str);
        sb.append(" - ");
        sb.append(componentName);
        sb.append(" : ");
        sb.append(i);
        sb.append(",\n");
        sb.append(zenModeConfig2 != null ? zenModeConfig2.toString() : null);
        sb.append(",\n");
        sb.append(configDiff);
        append(11, sb.toString());
    }

    public static void traceDisableEffects(com.android.server.notification.NotificationRecord notificationRecord, java.lang.String str) {
        append(13, notificationRecord.getKey() + "," + str);
    }

    public static void traceEffectsSuppressorChanged(java.util.List<android.content.ComponentName> list, java.util.List<android.content.ComponentName> list2, long j) {
        append(14, "suppressed effects:" + j + "," + componentListToString(list) + "->" + componentListToString(list2));
    }

    public static void traceListenerHintsChanged(int i, int i2, int i3) {
        append(15, hintsToString(i) + "->" + hintsToString(i2) + ",listeners=" + i3);
    }

    public static void traceMatchesCallFilter(boolean z, java.lang.String str, int i) {
        append(18, "result=" + z + ", reason=" + str + ", calling uid=" + i);
    }

    public static void traceRecordCaller(boolean z, boolean z2) {
        append(19, "has phone number=" + z + ", has uri=" + z2);
    }

    public static void traceCheckRepeatCaller(boolean z, boolean z2, boolean z3) {
        append(20, "res=" + z + ", given phone number=" + z2 + ", given uri=" + z3);
    }

    private static java.lang.String subscribeResult(android.service.notification.IConditionProvider iConditionProvider, android.os.RemoteException remoteException) {
        return iConditionProvider == null ? "no provider" : remoteException != null ? remoteException.getMessage() : "ok";
    }

    private static java.lang.String typeToString(int i) {
        switch (i) {
            case 1:
                return "intercepted";
            case 2:
            case 5:
            case 7:
            case 8:
            default:
                return "unknown";
            case 3:
                return "set_ringer_mode_external";
            case 4:
                return "set_ringer_mode_internal";
            case 6:
                return "set_zen_mode";
            case 9:
                return "subscribe";
            case 10:
                return "unsubscribe";
            case 11:
                return "config";
            case 12:
                return "not_intercepted";
            case 13:
                return "disable_effects";
            case 14:
                return "suppressor_changed";
            case 15:
                return "listener_hints_changed";
            case 16:
                return "set_notification_policy";
            case 17:
                return "set_consolidated_policy";
            case 18:
                return "matches_call_filter";
            case 19:
                return "record_caller";
            case 20:
                return "check_repeat_caller";
            case 21:
                return "alert_on_updated_intercept";
        }
    }

    private static java.lang.String ringerModeToString(int i) {
        switch (i) {
            case 0:
                return "silent";
            case 1:
                return "vibrate";
            case 2:
                return "normal";
            default:
                return "unknown";
        }
    }

    private static java.lang.String zenModeToString(int i) {
        switch (i) {
            case 0:
                return "off";
            case 1:
                return "important_interruptions";
            case 2:
                return "no_interruptions";
            case 3:
                return "alarms";
            default:
                return "unknown";
        }
    }

    private static java.lang.String hintsToString(int i) {
        switch (i) {
            case 0:
                return "none";
            case 1:
                return "disable_effects";
            case 2:
                return "disable_notification_effects";
            case 3:
            default:
                return java.lang.Integer.toString(i);
            case 4:
                return "disable_call_effects";
        }
    }

    private static java.lang.String componentToString(android.content.ComponentName componentName) {
        if (componentName != null) {
            return componentName.toShortString();
        }
        return null;
    }

    private static java.lang.String componentListToString(java.util.List<android.content.ComponentName> list) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(componentToString(list.get(i)));
        }
        return sb.toString();
    }

    private static void append(int i, java.lang.String str) {
        if (i == 1 || i == 12 || i == 20 || i == 19 || i == 18 || i == 21) {
            synchronized (INTERCEPTION_EVENTS) {
                INTERCEPTION_EVENTS.log(typeToString(i) + ": " + str);
            }
            return;
        }
        synchronized (STATE_CHANGES) {
            STATE_CHANGES.log(typeToString(i) + ": " + str);
        }
    }

    public static void dump(java.io.PrintWriter printWriter, java.lang.String str) {
        synchronized (INTERCEPTION_EVENTS) {
            printWriter.printf(str + "Interception Events:\n", new java.lang.Object[0]);
            INTERCEPTION_EVENTS.dump(str, printWriter);
        }
        synchronized (STATE_CHANGES) {
            printWriter.printf(str + "State Changes:\n", new java.lang.Object[0]);
            STATE_CHANGES.dump(str, printWriter);
        }
    }
}
