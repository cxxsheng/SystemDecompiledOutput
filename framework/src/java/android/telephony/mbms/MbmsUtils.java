package android.telephony.mbms;

/* loaded from: classes3.dex */
public class MbmsUtils {
    private static final java.lang.String LOG_TAG = "MbmsUtils";

    public static boolean isContainedIn(java.io.File file, java.io.File file2) {
        try {
            return file2.getCanonicalPath().startsWith(file.getCanonicalPath());
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException("Failed to resolve canonical paths: " + e);
        }
    }

    public static android.content.ComponentName toComponentName(android.content.pm.ComponentInfo componentInfo) {
        return new android.content.ComponentName(componentInfo.packageName, componentInfo.name);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static android.content.ComponentName getOverrideServiceName(android.content.Context context, java.lang.String str) {
        char c;
        java.lang.String str2;
        java.lang.String string;
        switch (str.hashCode()) {
            case -1374878107:
                if (str.equals(android.telephony.MbmsStreamingSession.MBMS_STREAMING_SERVICE_ACTION)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -407466459:
                if (str.equals(android.telephony.MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_ACTION)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1752202112:
                if (str.equals(android.telephony.MbmsGroupCallSession.MBMS_GROUP_CALL_SERVICE_ACTION)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                str2 = android.telephony.MbmsDownloadSession.MBMS_DOWNLOAD_SERVICE_OVERRIDE_METADATA;
                break;
            case 1:
                str2 = android.telephony.MbmsStreamingSession.MBMS_STREAMING_SERVICE_OVERRIDE_METADATA;
                break;
            case 2:
                str2 = android.telephony.MbmsGroupCallSession.MBMS_GROUP_CALL_SERVICE_OVERRIDE_METADATA;
                break;
            default:
                str2 = null;
                break;
        }
        if (str2 == null) {
            return null;
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo.metaData == null || (string = applicationInfo.metaData.getString(str2)) == null) {
                return null;
            }
            return android.content.ComponentName.unflattenFromString(string);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static android.content.pm.ServiceInfo getMiddlewareServiceInfo(android.content.Context context, java.lang.String str) {
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices;
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        android.content.Intent intent = new android.content.Intent();
        intent.setAction(str);
        android.content.ComponentName overrideServiceName = getOverrideServiceName(context, str);
        if (overrideServiceName == null) {
            queryIntentServices = packageManager.queryIntentServices(intent, 1048576);
        } else {
            intent.setComponent(overrideServiceName);
            queryIntentServices = packageManager.queryIntentServices(intent, 131072);
        }
        if (queryIntentServices == null || queryIntentServices.size() == 0) {
            android.util.Log.w(LOG_TAG, "No MBMS services found, cannot get service info");
            return null;
        }
        if (queryIntentServices.size() > 1) {
            android.util.Log.w(LOG_TAG, "More than one MBMS service found, cannot get unique service");
            return null;
        }
        return queryIntentServices.get(0).serviceInfo;
    }

    public static int startBinding(android.content.Context context, java.lang.String str, android.content.ServiceConnection serviceConnection) {
        android.content.Intent intent = new android.content.Intent();
        android.content.pm.ServiceInfo middlewareServiceInfo = getMiddlewareServiceInfo(context, str);
        if (middlewareServiceInfo == null) {
            return 1;
        }
        intent.setComponent(toComponentName(middlewareServiceInfo));
        context.bindService(intent, serviceConnection, 1);
        return 0;
    }

    public static java.io.File getEmbmsTempFileDirForService(android.content.Context context, java.lang.String str) {
        return new java.io.File(android.telephony.mbms.MbmsTempFileProvider.getEmbmsTempFileDir(context), str.replaceAll("[^a-zA-Z0-9_]", android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD));
    }
}
