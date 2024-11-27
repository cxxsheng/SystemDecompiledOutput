package android.media.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public class TunerResourceManager {
    public static final int INVALID_OWNER_ID = -1;
    public static final int INVALID_RESOURCE_HANDLE = -1;
    public static final int TUNER_RESOURCE_TYPE_CAS_SESSION = 4;
    public static final int TUNER_RESOURCE_TYPE_DEMUX = 1;
    public static final int TUNER_RESOURCE_TYPE_DESCRAMBLER = 2;
    public static final int TUNER_RESOURCE_TYPE_FRONTEND = 0;
    public static final int TUNER_RESOURCE_TYPE_FRONTEND_CICAM = 5;
    public static final int TUNER_RESOURCE_TYPE_LNB = 3;
    public static final int TUNER_RESOURCE_TYPE_MAX = 6;
    private final android.media.tv.tunerresourcemanager.ITunerResourceManager mService;
    private final int mUserId;
    private static final java.lang.String TAG = "TunerResourceManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);

    public static abstract class ResourcesReclaimListener {
        public abstract void onReclaimResources();
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TunerResourceType {
    }

    public TunerResourceManager(android.media.tv.tunerresourcemanager.ITunerResourceManager iTunerResourceManager, int i) {
        this.mService = iTunerResourceManager;
        this.mUserId = i;
    }

    /* renamed from: android.media.tv.tunerresourcemanager.TunerResourceManager$1, reason: invalid class name */
    class AnonymousClass1 extends android.media.tv.tunerresourcemanager.IResourcesReclaimListener.Stub {
        final /* synthetic */ java.util.concurrent.Executor val$executor;
        final /* synthetic */ android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener val$listener;

        AnonymousClass1(java.util.concurrent.Executor executor, android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener resourcesReclaimListener) {
            this.val$executor = executor;
            this.val$listener = resourcesReclaimListener;
        }

        @Override // android.media.tv.tunerresourcemanager.IResourcesReclaimListener
        public void onReclaimResources() {
            long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
            try {
                java.util.concurrent.Executor executor = this.val$executor;
                final android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener resourcesReclaimListener = this.val$listener;
                executor.execute(new java.lang.Runnable() { // from class: android.media.tv.tunerresourcemanager.TunerResourceManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener.this.onReclaimResources();
                    }
                });
            } finally {
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void registerClientProfile(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, java.util.concurrent.Executor executor, android.media.tv.tunerresourcemanager.TunerResourceManager.ResourcesReclaimListener resourcesReclaimListener, int[] iArr) {
        try {
            this.mService.registerClientProfile(resourceClientProfile, new android.media.tv.tunerresourcemanager.TunerResourceManager.AnonymousClass1(executor, resourcesReclaimListener), iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterClientProfile(int i) {
        try {
            this.mService.unregisterClientProfile(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateClientPriority(int i, int i2, int i3) {
        try {
            return this.mService.updateClientPriority(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasUnusedFrontend(int i) {
        try {
            return this.mService.hasUnusedFrontend(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isLowestPriority(int i, int i2) {
        try {
            return this.mService.isLowestPriority(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void storeResourceMap(int i) {
        try {
            this.mService.storeResourceMap(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearResourceMap(int i) {
        try {
            this.mService.clearResourceMap(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void restoreResourceMap(int i) {
        try {
            this.mService.restoreResourceMap(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setFrontendInfoList(android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) {
        try {
            this.mService.setFrontendInfoList(tunerFrontendInfoArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setDemuxInfoList(android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) {
        try {
            this.mService.setDemuxInfoList(tunerDemuxInfoArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateCasInfo(int i, int i2) {
        try {
            this.mService.updateCasInfo(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void setLnbInfoList(int[] iArr) {
        try {
            this.mService.setLnbInfoList(iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean acquireLock(int i) {
        try {
            return this.mService.acquireLock(i, java.lang.Thread.currentThread().getId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean releaseLock(int i) {
        try {
            return this.mService.releaseLock(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestFrontend(android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, int[] iArr) {
        try {
            return this.mService.requestFrontend(tunerFrontendRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean setMaxNumberOfFrontends(int i, int i2) {
        try {
            return this.mService.setMaxNumberOfFrontends(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxNumberOfFrontends(int i) {
        try {
            return this.mService.getMaxNumberOfFrontends(i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void shareFrontend(int i, int i2) {
        try {
            this.mService.shareFrontend(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean transferOwner(int i, int i2, int i3) {
        try {
            return this.mService.transferOwner(i, i2, i3);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestDemux(android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, int[] iArr) {
        try {
            return this.mService.requestDemux(tunerDemuxRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestDescrambler(android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) {
        try {
            return this.mService.requestDescrambler(tunerDescramblerRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestCasSession(android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, int[] iArr) {
        try {
            return this.mService.requestCasSession(casSessionRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestCiCam(android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, int[] iArr) {
        try {
            return this.mService.requestCiCam(tunerCiCamRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestLnb(android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, int[] iArr) {
        try {
            return this.mService.requestLnb(tunerLnbRequest, iArr);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseFrontend(int i, int i2) {
        try {
            this.mService.releaseFrontend(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseDemux(int i, int i2) {
        try {
            this.mService.releaseDemux(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseDescrambler(int i, int i2) {
        try {
            this.mService.releaseDescrambler(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseCasSession(int i, int i2) {
        try {
            this.mService.releaseCasSession(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseCiCam(int i, int i2) {
        try {
            this.mService.releaseCiCam(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void releaseLnb(int i, int i2) {
        try {
            this.mService.releaseLnb(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isHigherPriority(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) {
        try {
            return this.mService.isHigherPriority(resourceClientProfile, resourceClientProfile2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getClientPriority(int i, int i2) {
        try {
            return this.mService.getClientPriority(i, i2);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getConfigPriority(int i, boolean z) {
        try {
            return this.mService.getConfigPriority(i, z);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
