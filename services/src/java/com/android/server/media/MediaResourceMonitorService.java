package com.android.server.media;

/* loaded from: classes2.dex */
public class MediaResourceMonitorService extends com.android.server.SystemService {
    private static final java.lang.String SERVICE_NAME = "media_resource_monitor";
    private final com.android.server.media.MediaResourceMonitorService.MediaResourceMonitorImpl mMediaResourceMonitorImpl;
    private static final java.lang.String TAG = "MediaResourceMonitor";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public MediaResourceMonitorService(android.content.Context context) {
        super(context);
        this.mMediaResourceMonitorImpl = new com.android.server.media.MediaResourceMonitorService.MediaResourceMonitorImpl();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService(SERVICE_NAME, this.mMediaResourceMonitorImpl);
    }

    class MediaResourceMonitorImpl extends android.media.IMediaResourceMonitor.Stub {
        MediaResourceMonitorImpl() {
        }

        public void notifyResourceGranted(int i, int i2) throws android.os.RemoteException {
            if (com.android.server.media.MediaResourceMonitorService.DEBUG) {
                android.util.Log.d(com.android.server.media.MediaResourceMonitorService.TAG, "notifyResourceGranted(pid=" + i + ", type=" + i2 + ")");
            }
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.lang.String[] packageNamesFromPid = getPackageNamesFromPid(i);
                if (packageNamesFromPid == null) {
                    return;
                }
                java.util.List enabledProfiles = ((android.os.UserManager) com.android.server.media.MediaResourceMonitorService.this.getContext().createContextAsUser(android.os.UserHandle.of(android.app.ActivityManager.getCurrentUser()), 0).getSystemService(android.os.UserManager.class)).getEnabledProfiles();
                if (enabledProfiles.isEmpty()) {
                    return;
                }
                android.content.Intent intent = new android.content.Intent("android.intent.action.MEDIA_RESOURCE_GRANTED");
                intent.putExtra("android.intent.extra.PACKAGES", packageNamesFromPid);
                intent.putExtra("android.intent.extra.MEDIA_RESOURCE_TYPE", i2);
                java.util.Iterator it = enabledProfiles.iterator();
                while (it.hasNext()) {
                    com.android.server.media.MediaResourceMonitorService.this.getContext().sendBroadcastAsUser(intent, (android.os.UserHandle) it.next(), "android.permission.RECEIVE_MEDIA_RESOURCE_USAGE");
                }
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        private java.lang.String[] getPackageNamesFromPid(int i) {
            for (android.app.ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((android.app.ActivityManager) com.android.server.media.MediaResourceMonitorService.this.getContext().getSystemService(android.app.ActivityManager.class)).getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == i) {
                    return runningAppProcessInfo.pkgList;
                }
            }
            return null;
        }
    }
}
