package com.android.server.resources;

/* loaded from: classes2.dex */
public class ResourcesManagerService extends com.android.server.SystemService {
    private com.android.server.am.ActivityManagerService mActivityManagerService;
    private final android.os.IBinder mService;

    public ResourcesManagerService(@android.annotation.NonNull android.content.Context context) {
        super(context);
        this.mService = new android.content.res.IResourcesManager.Stub() { // from class: com.android.server.resources.ResourcesManagerService.1
            public boolean dumpResources(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, android.os.RemoteCallback remoteCallback) throws android.os.RemoteException {
                int callingUid = android.os.Binder.getCallingUid();
                if (callingUid != 0 && callingUid != 2000) {
                    remoteCallback.sendResult((android.os.Bundle) null);
                    throw new java.lang.SecurityException("dump should only be called by shell");
                }
                return com.android.server.resources.ResourcesManagerService.this.mActivityManagerService.dumpResources(str, parcelFileDescriptor, remoteCallback);
            }

            protected void dump(@android.annotation.NonNull java.io.FileDescriptor fileDescriptor, @android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.Nullable java.lang.String[] strArr) {
                try {
                    android.os.ParcelFileDescriptor dup = android.os.ParcelFileDescriptor.dup(fileDescriptor);
                    try {
                        com.android.server.resources.ResourcesManagerService.this.mActivityManagerService.dumpAllResources(dup, printWriter);
                        if (dup != null) {
                            dup.close();
                        }
                    } finally {
                    }
                } catch (java.lang.Exception e) {
                    printWriter.println("Exception while trying to dump all resources: " + e.getMessage());
                    e.printStackTrace(printWriter);
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            public int handleShellCommand(@android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor2, @android.annotation.NonNull android.os.ParcelFileDescriptor parcelFileDescriptor3, @android.annotation.NonNull java.lang.String[] strArr) {
                return new com.android.server.resources.ResourcesManagerShellCommand(this).exec(this, parcelFileDescriptor.getFileDescriptor(), parcelFileDescriptor2.getFileDescriptor(), parcelFileDescriptor3.getFileDescriptor(), strArr);
            }
        };
        publishBinderService("resources", this.mService);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        android.content.res.ResourceTimer.start();
    }

    public void setActivityManagerService(com.android.server.am.ActivityManagerService activityManagerService) {
        this.mActivityManagerService = activityManagerService;
    }
}
