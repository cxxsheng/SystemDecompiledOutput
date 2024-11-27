package android.os.image;

/* loaded from: classes3.dex */
public class DynamicSystemManager {
    private static final java.lang.String TAG = "DynamicSystemManager";
    private final android.os.image.IDynamicSystemService mService;

    public DynamicSystemManager(android.os.image.IDynamicSystemService iDynamicSystemService) {
        this.mService = iDynamicSystemService;
    }

    public class Session {
        private Session() {
        }

        public boolean setAshmem(android.os.ParcelFileDescriptor parcelFileDescriptor, long j) {
            try {
                return android.os.image.DynamicSystemManager.this.mService.setAshmem(parcelFileDescriptor, j);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }

        public boolean submitFromAshmem(int i) {
            try {
                return android.os.image.DynamicSystemManager.this.mService.submitFromAshmem(i);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }

        public boolean getAvbPublicKey(android.gsi.AvbPublicKey avbPublicKey) {
            try {
                return android.os.image.DynamicSystemManager.this.mService.getAvbPublicKey(avbPublicKey);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }

        public boolean commit() {
            try {
                return android.os.image.DynamicSystemManager.this.mService.setEnable(true, true);
            } catch (android.os.RemoteException e) {
                throw new java.lang.RuntimeException(e.toString());
            }
        }
    }

    public boolean startInstallation(java.lang.String str) {
        try {
            return this.mService.startInstallation(str);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public android.util.Pair<java.lang.Integer, android.os.image.DynamicSystemManager.Session> createPartition(java.lang.String str, long j, boolean z) {
        try {
            int createPartition = this.mService.createPartition(str, j, z);
            if (createPartition == 0) {
                return new android.util.Pair<>(java.lang.Integer.valueOf(createPartition), new android.os.image.DynamicSystemManager.Session());
            }
            return new android.util.Pair<>(java.lang.Integer.valueOf(createPartition), null);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean closePartition() {
        try {
            return this.mService.closePartition();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean finishInstallation() {
        try {
            return this.mService.finishInstallation();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public android.gsi.GsiProgress getInstallationProgress() {
        try {
            return this.mService.getInstallationProgress();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean abort() {
        try {
            return this.mService.abort();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean isInUse() {
        try {
            return this.mService.isInUse();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean isInstalled() {
        try {
            return this.mService.isInstalled();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean isEnabled() {
        try {
            return this.mService.isEnabled();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean remove() {
        try {
            return this.mService.remove();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public boolean setEnable(boolean z, boolean z2) {
        try {
            return this.mService.setEnable(z, z2);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public long suggestScratchSize() {
        try {
            return this.mService.suggestScratchSize();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }

    public java.lang.String getActiveDsuSlot() {
        try {
            return this.mService.getActiveDsuSlot();
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e.toString());
        }
    }
}
