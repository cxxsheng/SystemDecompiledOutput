package com.android.server.media;

/* loaded from: classes2.dex */
final class MediaButtonReceiverHolder {
    private static final java.lang.String COMPONENT_NAME_USER_ID_DELIM = ",";
    public static final int COMPONENT_TYPE_ACTIVITY = 2;
    public static final int COMPONENT_TYPE_BROADCAST = 1;
    public static final int COMPONENT_TYPE_INVALID = 0;
    public static final int COMPONENT_TYPE_SERVICE = 3;
    private static final boolean DEBUG_KEY_EVENT = true;
    private static final int PACKAGE_MANAGER_COMMON_FLAGS = 786432;
    private static final java.lang.String TAG = "PendingIntentHolder";
    private final android.content.ComponentName mComponentName;
    private final int mComponentType;
    private final java.lang.String mPackageName;
    private final android.app.PendingIntent mPendingIntent;
    private final int mUserId;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ComponentType {
    }

    public static com.android.server.media.MediaButtonReceiverHolder unflattenFromString(android.content.Context context, java.lang.String str) {
        java.lang.String[] split;
        android.content.ComponentName unflattenFromString;
        int componentType;
        if (android.text.TextUtils.isEmpty(str) || (split = str.split(COMPONENT_NAME_USER_ID_DELIM)) == null || ((split.length != 2 && split.length != 3) || (unflattenFromString = android.content.ComponentName.unflattenFromString(split[0])) == null)) {
            return null;
        }
        int parseInt = java.lang.Integer.parseInt(split[1]);
        if (split.length == 3) {
            componentType = java.lang.Integer.parseInt(split[2]);
        } else {
            componentType = getComponentType(context, unflattenFromString);
        }
        return new com.android.server.media.MediaButtonReceiverHolder(parseInt, null, unflattenFromString, componentType);
    }

    public static com.android.server.media.MediaButtonReceiverHolder create(int i, @android.annotation.Nullable android.app.PendingIntent pendingIntent, java.lang.String str) {
        if (pendingIntent == null) {
            return null;
        }
        int componentType = getComponentType(pendingIntent);
        android.content.ComponentName componentName = getComponentName(pendingIntent, componentType);
        if (componentName != null) {
            return new com.android.server.media.MediaButtonReceiverHolder(i, pendingIntent, componentName, componentType);
        }
        android.util.Log.w(TAG, "Unresolvable implicit intent is set, pi=" + pendingIntent);
        return new com.android.server.media.MediaButtonReceiverHolder(i, pendingIntent, str);
    }

    public static com.android.server.media.MediaButtonReceiverHolder create(int i, android.content.ComponentName componentName) {
        return new com.android.server.media.MediaButtonReceiverHolder(i, null, componentName, 1);
    }

    private MediaButtonReceiverHolder(int i, android.app.PendingIntent pendingIntent, android.content.ComponentName componentName, int i2) {
        this.mUserId = i;
        this.mPendingIntent = pendingIntent;
        this.mComponentName = componentName;
        this.mPackageName = componentName.getPackageName();
        this.mComponentType = i2;
    }

    private MediaButtonReceiverHolder(int i, android.app.PendingIntent pendingIntent, java.lang.String str) {
        this.mUserId = i;
        this.mPendingIntent = pendingIntent;
        this.mComponentName = null;
        this.mPackageName = str;
        this.mComponentType = 0;
    }

    public int getUserId() {
        return this.mUserId;
    }

    @android.annotation.NonNull
    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public boolean send(android.content.Context context, android.view.KeyEvent keyEvent, java.lang.String str, int i, android.app.PendingIntent.OnFinished onFinished, android.os.Handler handler, long j) {
        android.content.Intent intent = new android.content.Intent("android.intent.action.MEDIA_BUTTON");
        intent.addFlags(268435456);
        intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        android.app.BroadcastOptions makeBasic = android.app.BroadcastOptions.makeBasic();
        makeBasic.setTemporaryAppAllowlist(j, 0, com.android.internal.util.FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_MEDIA_BUTTON, "");
        makeBasic.setBackgroundActivityStartsAllowed(true);
        if (this.mPendingIntent != null) {
            android.util.Log.d(TAG, "Sending " + keyEvent + " to the last known PendingIntent " + this.mPendingIntent);
            try {
                this.mPendingIntent.send(context, i, intent, onFinished, handler, null, makeBasic.toBundle());
            } catch (android.app.PendingIntent.CanceledException e) {
                android.util.Log.w(TAG, "Error sending key event to media button receiver " + this.mPendingIntent, e);
                return false;
            }
        } else if (this.mComponentName != null) {
            android.util.Log.d(TAG, "Sending " + keyEvent + " to the restored intent " + this.mComponentName + ", type=" + this.mComponentType);
            intent.setComponent(this.mComponentName);
            android.os.UserHandle of = android.os.UserHandle.of(this.mUserId);
            try {
                switch (this.mComponentType) {
                    case 2:
                        context.startActivityAsUser(intent, of);
                        break;
                    case 3:
                        context.createContextAsUser(of, 0).startForegroundService(intent);
                        break;
                    default:
                        context.sendBroadcastAsUser(intent, of, null, makeBasic.toBundle());
                        break;
                }
            } catch (java.lang.Exception e2) {
                android.util.Log.w(TAG, "Error sending media button to the restored intent " + this.mComponentName + ", type=" + this.mComponentType, e2);
                return false;
            }
        } else {
            android.util.Log.e(TAG, "Shouldn't be happen -- pending intent or component name must be set");
            return false;
        }
        return true;
    }

    public java.lang.String toString() {
        return "MBR {pi=" + this.mPendingIntent + ", componentName=" + this.mComponentName + ", type=" + this.mComponentType + ", pkg=" + this.mPackageName + "}";
    }

    public java.lang.String flattenToString() {
        if (this.mComponentName == null) {
            return "";
        }
        return java.lang.String.join(COMPONENT_NAME_USER_ID_DELIM, this.mComponentName.flattenToString(), java.lang.String.valueOf(this.mUserId), java.lang.String.valueOf(this.mComponentType));
    }

    public android.content.ComponentName getComponentName() {
        return this.mComponentName;
    }

    private static int getComponentType(android.app.PendingIntent pendingIntent) {
        if (pendingIntent.isBroadcast()) {
            return 1;
        }
        if (pendingIntent.isActivity()) {
            return 2;
        }
        if (pendingIntent.isForegroundService() || pendingIntent.isService()) {
            return 3;
        }
        return 0;
    }

    private static int getComponentType(android.content.Context context, android.content.ComponentName componentName) {
        if (componentName == null) {
            return 0;
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        try {
            if (packageManager.getActivityInfo(componentName, 786433) != null) {
                return 2;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
        }
        try {
            if (packageManager.getServiceInfo(componentName, 786436) != null) {
                return 3;
            }
            return 1;
        } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            return 1;
        }
    }

    private static android.content.ComponentName getComponentName(android.app.PendingIntent pendingIntent, int i) {
        java.util.List emptyList = java.util.Collections.emptyList();
        switch (i) {
            case 1:
                emptyList = pendingIntent.queryIntentComponents(786434);
                break;
            case 2:
                emptyList = pendingIntent.queryIntentComponents(851969);
                break;
            case 3:
                emptyList = pendingIntent.queryIntentComponents(786436);
                break;
        }
        java.util.Iterator it = emptyList.iterator();
        while (it.hasNext()) {
            android.content.pm.ComponentInfo componentInfo = getComponentInfo((android.content.pm.ResolveInfo) it.next());
            if (componentInfo != null && android.text.TextUtils.equals(componentInfo.packageName, pendingIntent.getCreatorPackage()) && componentInfo.packageName != null && componentInfo.name != null) {
                return new android.content.ComponentName(componentInfo.packageName, componentInfo.name);
            }
        }
        return null;
    }

    private static android.content.pm.ComponentInfo getComponentInfo(@android.annotation.NonNull android.content.pm.ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo;
        }
        if (resolveInfo.serviceInfo != null) {
            return resolveInfo.serviceInfo;
        }
        return null;
    }
}
