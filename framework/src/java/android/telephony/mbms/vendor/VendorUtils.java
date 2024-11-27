package android.telephony.mbms.vendor;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class VendorUtils {
    public static final java.lang.String ACTION_CLEANUP = "android.telephony.mbms.action.CLEANUP";
    public static final java.lang.String ACTION_DOWNLOAD_RESULT_INTERNAL = "android.telephony.mbms.action.DOWNLOAD_RESULT_INTERNAL";
    public static final java.lang.String ACTION_FILE_DESCRIPTOR_REQUEST = "android.telephony.mbms.action.FILE_DESCRIPTOR_REQUEST";
    public static final java.lang.String EXTRA_FD_COUNT = "android.telephony.mbms.extra.FD_COUNT";
    public static final java.lang.String EXTRA_FINAL_URI = "android.telephony.mbms.extra.FINAL_URI";
    public static final java.lang.String EXTRA_FREE_URI_LIST = "android.telephony.mbms.extra.FREE_URI_LIST";
    public static final java.lang.String EXTRA_PAUSED_LIST = "android.telephony.mbms.extra.PAUSED_LIST";
    public static final java.lang.String EXTRA_PAUSED_URI_LIST = "android.telephony.mbms.extra.PAUSED_URI_LIST";
    public static final java.lang.String EXTRA_SERVICE_ID = "android.telephony.mbms.extra.SERVICE_ID";
    public static final java.lang.String EXTRA_TEMP_FILES_IN_USE = "android.telephony.mbms.extra.TEMP_FILES_IN_USE";
    public static final java.lang.String EXTRA_TEMP_FILE_ROOT = "android.telephony.mbms.extra.TEMP_FILE_ROOT";
    public static final java.lang.String EXTRA_TEMP_LIST = "android.telephony.mbms.extra.TEMP_LIST";

    public static android.content.ComponentName getAppReceiverFromPackageName(android.content.Context context, java.lang.String str) {
        android.content.ComponentName componentName = new android.content.ComponentName(str, android.telephony.mbms.MbmsDownloadReceiver.class.getCanonicalName());
        android.content.Intent intent = new android.content.Intent();
        intent.setComponent(componentName);
        java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers = context.getPackageManager().queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() > 0) {
            return componentName;
        }
        return null;
    }
}
