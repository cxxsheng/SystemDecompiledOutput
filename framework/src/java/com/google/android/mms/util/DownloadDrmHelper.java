package com.google.android.mms.util;

/* loaded from: classes5.dex */
public class DownloadDrmHelper {
    public static final java.lang.String EXTENSION_DRM_MESSAGE = ".dm";
    public static final java.lang.String EXTENSION_INTERNAL_FWDL = ".fl";
    public static final java.lang.String MIMETYPE_DRM_MESSAGE = "application/vnd.oma.drm.message";
    private static final java.lang.String TAG = "DownloadDrmHelper";

    public static boolean isDrmMimeType(android.content.Context context, java.lang.String str) {
        if (context == null) {
            return false;
        }
        try {
            android.drm.DrmManagerClient drmManagerClient = new android.drm.DrmManagerClient(context);
            if (str == null || str.length() <= 0) {
                return false;
            }
            return drmManagerClient.canHandle("", str);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "DrmManagerClient instance could not be created, context is Illegal.");
            return false;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.w(TAG, "DrmManagerClient didn't initialize properly.");
            return false;
        }
    }

    public static boolean isDrmConvertNeeded(java.lang.String str) {
        return "application/vnd.oma.drm.message".equals(str);
    }

    public static java.lang.String modifyDrmFwLockFileExtension(java.lang.String str) {
        if (str != null) {
            int lastIndexOf = str.lastIndexOf(android.media.MediaMetrics.SEPARATOR);
            if (lastIndexOf != -1) {
                str = str.substring(0, lastIndexOf);
            }
            return str.concat(EXTENSION_INTERNAL_FWDL);
        }
        return str;
    }

    public static java.lang.String getOriginalMimeType(android.content.Context context, java.lang.String str, java.lang.String str2) {
        android.drm.DrmManagerClient drmManagerClient = new android.drm.DrmManagerClient(context);
        try {
            if (drmManagerClient.canHandle(str, (java.lang.String) null)) {
                return drmManagerClient.getOriginalMimeType(str);
            }
            return str2;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "Can't get original mime type since path is null or empty string.");
            return str2;
        } catch (java.lang.IllegalStateException e2) {
            android.util.Log.w(TAG, "DrmManagerClient didn't initialize properly.");
            return str2;
        }
    }
}
