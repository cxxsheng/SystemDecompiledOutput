package com.android.server.devicepolicy;

@java.lang.Deprecated
/* loaded from: classes.dex */
public final class FactoryResetter {
    private static final java.lang.String TAG = com.android.server.devicepolicy.FactoryResetter.class.getSimpleName();
    private final android.content.Context mContext;
    private final boolean mForce;

    @android.annotation.Nullable
    private final java.lang.String mReason;

    @android.annotation.Nullable
    private final android.app.admin.DevicePolicySafetyChecker mSafetyChecker;
    private final boolean mShutdown;
    private final boolean mWipeAdoptableStorage;
    private final boolean mWipeEuicc;
    private final boolean mWipeFactoryResetProtection;

    public boolean factoryReset() throws java.io.IOException {
        com.android.internal.util.Preconditions.checkCallAuthorization(this.mContext.checkCallingOrSelfPermission("android.permission.MASTER_CLEAR") == 0);
        com.android.server.FactoryResetter.setFactoryResetting(this.mContext);
        if (this.mSafetyChecker == null) {
            factoryResetInternalUnchecked();
            return true;
        }
        com.android.internal.os.IResultReceiver.Stub stub = new com.android.internal.os.IResultReceiver.Stub() { // from class: com.android.server.devicepolicy.FactoryResetter.1
            public void send(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                com.android.server.utils.Slogf.i(com.android.server.devicepolicy.FactoryResetter.TAG, "Factory reset confirmed by %s, proceeding", com.android.server.devicepolicy.FactoryResetter.this.mSafetyChecker);
                try {
                    com.android.server.devicepolicy.FactoryResetter.this.factoryResetInternalUnchecked();
                } catch (java.io.IOException e) {
                    com.android.server.utils.Slogf.wtf(com.android.server.devicepolicy.FactoryResetter.TAG, e, "IOException calling underlying systems", new java.lang.Object[0]);
                }
            }
        };
        com.android.server.utils.Slogf.i(TAG, "Delaying factory reset until %s confirms", this.mSafetyChecker);
        this.mSafetyChecker.onFactoryReset(stub);
        return false;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("FactoryResetter[");
        if (this.mReason == null) {
            sb.append("no_reason");
        } else {
            sb.append("reason='");
            sb.append(this.mReason);
            sb.append("'");
        }
        if (this.mSafetyChecker != null) {
            sb.append(",hasSafetyChecker");
        }
        if (this.mShutdown) {
            sb.append(",shutdown");
        }
        if (this.mForce) {
            sb.append(",force");
        }
        if (this.mWipeEuicc) {
            sb.append(",wipeEuicc");
        }
        if (this.mWipeAdoptableStorage) {
            sb.append(",wipeAdoptableStorage");
        }
        if (this.mWipeFactoryResetProtection) {
            sb.append(",ipeFactoryResetProtection");
        }
        sb.append(']');
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void factoryResetInternalUnchecked() throws java.io.IOException {
        com.android.server.utils.Slogf.i(TAG, "factoryReset(): reason=%s, shutdown=%b, force=%b, wipeEuicc=%b, wipeAdoptableStorage=%b, wipeFRP=%b", this.mReason, java.lang.Boolean.valueOf(this.mShutdown), java.lang.Boolean.valueOf(this.mForce), java.lang.Boolean.valueOf(this.mWipeEuicc), java.lang.Boolean.valueOf(this.mWipeAdoptableStorage), java.lang.Boolean.valueOf(this.mWipeFactoryResetProtection));
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        if (!this.mForce && userManager.hasUserRestriction("no_factory_reset")) {
            throw new java.lang.SecurityException("Factory reset is not allowed for this user.");
        }
        if (this.mWipeFactoryResetProtection) {
            android.service.persistentdata.PersistentDataBlockManager persistentDataBlockManager = (android.service.persistentdata.PersistentDataBlockManager) this.mContext.getSystemService(android.service.persistentdata.PersistentDataBlockManager.class);
            if (persistentDataBlockManager != null) {
                com.android.server.utils.Slogf.w(TAG, "Wiping factory reset protection");
                persistentDataBlockManager.wipe();
            } else {
                com.android.server.utils.Slogf.w(TAG, "No need to wipe factory reset protection");
            }
        }
        if (this.mWipeAdoptableStorage) {
            com.android.server.utils.Slogf.w(TAG, "Wiping adoptable storage");
            ((android.os.storage.StorageManager) this.mContext.getSystemService(android.os.storage.StorageManager.class)).wipeAdoptableDisks();
        }
        android.os.RecoverySystem.rebootWipeUserData(this.mContext, this.mShutdown, this.mReason, this.mForce, this.mWipeEuicc);
    }

    private FactoryResetter(com.android.server.devicepolicy.FactoryResetter.Builder builder) {
        this.mContext = builder.mContext;
        this.mSafetyChecker = builder.mSafetyChecker;
        this.mReason = builder.mReason;
        this.mShutdown = builder.mShutdown;
        this.mForce = builder.mForce;
        this.mWipeEuicc = builder.mWipeEuicc;
        this.mWipeAdoptableStorage = builder.mWipeAdoptableStorage;
        this.mWipeFactoryResetProtection = builder.mWipeFactoryResetProtection;
    }

    public static com.android.server.devicepolicy.FactoryResetter.Builder newBuilder(android.content.Context context) {
        return new com.android.server.devicepolicy.FactoryResetter.Builder(context);
    }

    public static final class Builder {
        private final android.content.Context mContext;
        private boolean mForce;

        @android.annotation.Nullable
        private java.lang.String mReason;

        @android.annotation.Nullable
        private android.app.admin.DevicePolicySafetyChecker mSafetyChecker;
        private boolean mShutdown;
        private boolean mWipeAdoptableStorage;
        private boolean mWipeEuicc;
        private boolean mWipeFactoryResetProtection;

        private Builder(android.content.Context context) {
            java.util.Objects.requireNonNull(context);
            this.mContext = context;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setSafetyChecker(@android.annotation.Nullable android.app.admin.DevicePolicySafetyChecker devicePolicySafetyChecker) {
            this.mSafetyChecker = devicePolicySafetyChecker;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setReason(java.lang.String str) {
            java.util.Objects.requireNonNull(str);
            this.mReason = str;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setShutdown(boolean z) {
            this.mShutdown = z;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setForce(boolean z) {
            this.mForce = z;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setWipeEuicc(boolean z) {
            this.mWipeEuicc = z;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setWipeAdoptableStorage(boolean z) {
            this.mWipeAdoptableStorage = z;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter.Builder setWipeFactoryResetProtection(boolean z) {
            this.mWipeFactoryResetProtection = z;
            return this;
        }

        public com.android.server.devicepolicy.FactoryResetter build() {
            return new com.android.server.devicepolicy.FactoryResetter(this);
        }
    }
}
