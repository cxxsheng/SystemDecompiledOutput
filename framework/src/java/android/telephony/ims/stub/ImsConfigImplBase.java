package android.telephony.ims.stub;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsConfigImplBase {
    public static final int CONFIG_RESULT_FAILED = 1;
    public static final int CONFIG_RESULT_SUCCESS = 0;
    public static final int CONFIG_RESULT_UNKNOWN = -1;
    private static final java.lang.String TAG = "ImsConfigImplBase";
    private final com.android.internal.telephony.util.RemoteCallbackListExt<android.telephony.ims.aidl.IImsConfigCallback> mCallbacks;
    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub mImsConfigStub;
    private final com.android.internal.telephony.util.RemoteCallbackListExt<android.telephony.ims.aidl.IRcsConfigCallback> mRcsCallbacks;
    private byte[] mRcsConfigData;
    private final java.lang.Object mRcsConfigDataLock;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SetConfigResult {
    }

    public static class ImsConfigStub extends android.telephony.ims.aidl.IImsConfig.Stub {
        private java.util.concurrent.Executor mExecutor;
        java.lang.ref.WeakReference<android.telephony.ims.stub.ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private java.util.HashMap<java.lang.Integer, java.lang.Integer> mProvisionedIntValue = new java.util.HashMap<>();
        private java.util.HashMap<java.lang.Integer, java.lang.String> mProvisionedStringValue = new java.util.HashMap<>();
        private final java.lang.Object mLock = new java.lang.Object();

        public ImsConfigStub(android.telephony.ims.stub.ImsConfigImplBase imsConfigImplBase, java.util.concurrent.Executor executor) {
            this.mExecutor = executor;
            this.mImsConfigImplBaseWeakReference = new java.lang.ref.WeakReference<>(imsConfigImplBase);
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addImsConfigCallback(final android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$addImsConfigCallback$0(iImsConfigCallback, atomicReference);
                }
            }, "addImsConfigCallback");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception addImsConfigCallback");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addImsConfigCallback$0(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().addImsConfigCallback(iImsConfigCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeImsConfigCallback(final android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$removeImsConfigCallback$1(iImsConfigCallback, atomicReference);
                }
            }, "removeImsConfigCallback");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception removeImsConfigCallback");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeImsConfigCallback$1(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().removeImsConfigCallback(iImsConfigCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int getConfigInt(final int i) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            int intValue = ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$getConfigInt$2;
                    lambda$getConfigInt$2 = android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$getConfigInt$2(i, atomicReference);
                    return lambda$getConfigInt$2;
                }
            }, "getConfigInt")).intValue();
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception getConfigString");
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return intValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$getConfigInt$2(int i, java.util.concurrent.atomic.AtomicReference atomicReference) {
            synchronized (this.mLock) {
                if (this.mProvisionedIntValue.containsKey(java.lang.Integer.valueOf(i))) {
                    return this.mProvisionedIntValue.get(java.lang.Integer.valueOf(i));
                }
                int i2 = -1;
                try {
                    int configInt = getImsConfigImpl().getConfigInt(i);
                    if (configInt != -1) {
                        try {
                            this.mProvisionedIntValue.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(configInt));
                        } catch (android.os.RemoteException e) {
                            e = e;
                            i2 = configInt;
                            atomicReference.set(e);
                            return java.lang.Integer.valueOf(i2);
                        }
                    }
                    return java.lang.Integer.valueOf(configInt);
                } catch (android.os.RemoteException e2) {
                    e = e2;
                }
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public java.lang.String getConfigString(final int i) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            java.lang.String str = (java.lang.String) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.String lambda$getConfigString$3;
                    lambda$getConfigString$3 = android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$getConfigString$3(i, atomicReference);
                    return lambda$getConfigString$3;
                }
            }, "getConfigString");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception getConfigString");
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.String lambda$getConfigString$3(int i, java.util.concurrent.atomic.AtomicReference atomicReference) {
            java.lang.String str;
            synchronized (this.mLock) {
                if (this.mProvisionedStringValue.containsKey(java.lang.Integer.valueOf(i))) {
                    str = this.mProvisionedStringValue.get(java.lang.Integer.valueOf(i));
                } else {
                    java.lang.String str2 = null;
                    try {
                        str2 = getImsConfigImpl().getConfigString(i);
                        if (str2 != null) {
                            this.mProvisionedStringValue.put(java.lang.Integer.valueOf(i), str2);
                        }
                        str = str2;
                    } catch (android.os.RemoteException e) {
                        atomicReference.set(e);
                        return str2;
                    }
                }
            }
            return str;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigInt(final int i, final int i2) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            int intValue = ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$setConfigInt$4;
                    lambda$setConfigInt$4 = android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$setConfigInt$4(i, i2, atomicReference);
                    return lambda$setConfigInt$4;
                }
            }, "setConfigInt")).intValue();
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setConfigInt");
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return intValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$setConfigInt$4(int i, int i2, java.util.concurrent.atomic.AtomicReference atomicReference) {
            int i3 = -1;
            try {
                synchronized (this.mLock) {
                    this.mProvisionedIntValue.remove(java.lang.Integer.valueOf(i));
                    i3 = getImsConfigImpl().setConfig(i, i2);
                    if (i3 == 0) {
                        this.mProvisionedIntValue.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
                    } else {
                        android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "Set provision value of " + i + " to " + i2 + " failed with error code " + i3);
                    }
                }
                notifyImsConfigChanged(i, i2);
                return java.lang.Integer.valueOf(i3);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return java.lang.Integer.valueOf(i3);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public int setConfigString(final int i, final java.lang.String str) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            int intValue = ((java.lang.Integer) executeMethodAsyncForResult(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    java.lang.Integer lambda$setConfigString$5;
                    lambda$setConfigString$5 = android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$setConfigString$5(i, str, atomicReference);
                    return lambda$setConfigString$5;
                }
            }, "setConfigString")).intValue();
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setConfigInt");
                throw ((android.os.RemoteException) atomicReference.get());
            }
            return intValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ java.lang.Integer lambda$setConfigString$5(int i, java.lang.String str, java.util.concurrent.atomic.AtomicReference atomicReference) {
            int i2 = -1;
            try {
                synchronized (this.mLock) {
                    this.mProvisionedStringValue.remove(java.lang.Integer.valueOf(i));
                    i2 = getImsConfigImpl().setConfig(i, str);
                    if (i2 == 0) {
                        this.mProvisionedStringValue.put(java.lang.Integer.valueOf(i), str);
                    }
                }
                notifyImsConfigChanged(i, str);
                return java.lang.Integer.valueOf(i2);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
                return java.lang.Integer.valueOf(i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void updateImsCarrierConfigs(final android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$updateImsCarrierConfigs$6(persistableBundle, atomicReference);
                }
            }, "updateImsCarrierConfigs");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception updateImsCarrierConfigs");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateImsCarrierConfigs$6(android.os.PersistableBundle persistableBundle, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().updateImsCarrierConfigs(persistableBundle);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        private android.telephony.ims.stub.ImsConfigImplBase getImsConfigImpl() throws android.os.RemoteException {
            android.telephony.ims.stub.ImsConfigImplBase imsConfigImplBase = this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase == null) {
                throw new android.os.RemoteException("Fail to get ImsConfigImpl");
            }
            return imsConfigImplBase;
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationReceived(final byte[] bArr, final boolean z) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$notifyRcsAutoConfigurationReceived$7(bArr, z, atomicReference);
                }
            }, "notifyRcsAutoConfigurationReceived");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyRcsAutoConfigurationReceived");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRcsAutoConfigurationReceived$7(byte[] bArr, boolean z, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().onNotifyRcsAutoConfigurationReceived(bArr, z);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyRcsAutoConfigurationRemoved() throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$notifyRcsAutoConfigurationRemoved$8(atomicReference);
                }
            }, "notifyRcsAutoConfigurationRemoved");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyRcsAutoConfigurationRemoved");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyRcsAutoConfigurationRemoved$8(java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().onNotifyRcsAutoConfigurationRemoved();
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyImsConfigChanged(int i, int i2) throws android.os.RemoteException {
            getImsConfigImpl().notifyConfigChanged(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyImsConfigChanged(int i, java.lang.String str) throws android.os.RemoteException {
            getImsConfigImpl().notifyConfigChanged(i, str);
        }

        protected void updateCachedValue(int i, int i2) {
            synchronized (this.mLock) {
                this.mProvisionedIntValue.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            }
        }

        protected void updateCachedValue(int i, java.lang.String str) {
            synchronized (this.mLock) {
                this.mProvisionedStringValue.put(java.lang.Integer.valueOf(i), str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void addRcsConfigCallback(final android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$addRcsConfigCallback$9(iRcsConfigCallback, atomicReference);
                }
            }, "addRcsConfigCallback");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception addRcsConfigCallback");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addRcsConfigCallback$9(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().addRcsConfigCallback(iRcsConfigCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void removeRcsConfigCallback(final android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$removeRcsConfigCallback$10(iRcsConfigCallback, atomicReference);
                }
            }, "removeRcsConfigCallback");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception removeRcsConfigCallback");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeRcsConfigCallback$10(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().removeRcsConfigCallback(iRcsConfigCallback);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void triggerRcsReconfiguration() throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$triggerRcsReconfiguration$11(atomicReference);
                }
            }, "triggerRcsReconfiguration");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception triggerRcsReconfiguration");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerRcsReconfiguration$11(java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().triggerAutoConfiguration();
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void setRcsClientConfiguration(final android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$setRcsClientConfiguration$12(rcsClientConfiguration, atomicReference);
                }
            }, "setRcsClientConfiguration");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception setRcsClientConfiguration");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setRcsClientConfiguration$12(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                getImsConfigImpl().setRcsClientConfiguration(rcsClientConfiguration);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyIntImsConfigChanged(final int i, final int i2) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$notifyIntImsConfigChanged$13(i, i2, atomicReference);
                }
            }, "notifyIntImsConfigChanged");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyIntImsConfigChanged");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyIntImsConfigChanged$13(int i, int i2, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                notifyImsConfigChanged(i, i2);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsConfig
        public void notifyStringImsConfigChanged(final int i, final java.lang.String str) throws android.os.RemoteException {
            final java.util.concurrent.atomic.AtomicReference atomicReference = new java.util.concurrent.atomic.AtomicReference();
            executeMethodAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub.this.lambda$notifyStringImsConfigChanged$14(i, str, atomicReference);
                }
            }, "notifyStringImsConfigChanged");
            if (atomicReference.get() != null) {
                android.util.Log.d(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Exception notifyStringImsConfigChanged");
                throw ((android.os.RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyStringImsConfigChanged$14(int i, java.lang.String str, java.util.concurrent.atomic.AtomicReference atomicReference) {
            try {
                notifyImsConfigChanged(i, str);
            } catch (android.os.RemoteException e) {
                atomicReference.set(e);
            }
        }

        public void clearCachedValue() {
            android.util.Log.i(android.telephony.ims.stub.ImsConfigImplBase.TAG, "clearCachedValue");
            synchronized (this.mLock) {
                this.mProvisionedIntValue.clear();
                this.mProvisionedStringValue.clear();
            }
        }

        private void executeMethodAsync(final java.lang.Runnable runnable, java.lang.String str) throws android.os.RemoteException {
            try {
                java.util.concurrent.CompletableFuture.runAsync(new java.lang.Runnable() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, this.mExecutor).join();
            } catch (java.util.concurrent.CancellationException | java.util.concurrent.CompletionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final java.util.function.Supplier<T> supplier, java.lang.String str) throws android.os.RemoteException {
            try {
                return (T) java.util.concurrent.CompletableFuture.supplyAsync(new java.util.function.Supplier() { // from class: android.telephony.ims.stub.ImsConfigImplBase$ImsConfigStub$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        java.lang.Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = com.android.internal.telephony.util.TelephonyUtils.runWithCleanCallingIdentity((java.util.function.Supplier<java.lang.Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, this.mExecutor).get();
            } catch (java.lang.InterruptedException | java.util.concurrent.ExecutionException e) {
                android.util.Log.w(android.telephony.ims.stub.ImsConfigImplBase.TAG, "ImsConfigImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new android.os.RemoteException(e.getMessage());
            }
        }
    }

    public ImsConfigImplBase(java.util.concurrent.Executor executor) {
        this.mCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new java.lang.Object();
        this.mImsConfigStub = new android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub(this, executor);
    }

    public ImsConfigImplBase(android.content.Context context) {
        this.mCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new java.lang.Object();
        this.mImsConfigStub = new android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub(this, null);
    }

    public ImsConfigImplBase() {
        this.mCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsCallbacks = new com.android.internal.telephony.util.RemoteCallbackListExt<>();
        this.mRcsConfigDataLock = new java.lang.Object();
        this.mImsConfigStub = new android.telephony.ims.stub.ImsConfigImplBase.ImsConfigStub(this, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.register(iImsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsConfigCallback(android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) {
        this.mCallbacks.unregister(iImsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyConfigChanged(final int i, final int i2) {
        if (this.mCallbacks == null) {
            return;
        }
        synchronized (this.mCallbacks) {
            this.mCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.stub.ImsConfigImplBase.lambda$notifyConfigChanged$0(i, i2, (android.telephony.ims.aidl.IImsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyConfigChanged$0(int i, int i2, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onIntConfigChanged(i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "notifyConfigChanged(int): dead binder in notify, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyConfigChanged(final int i, final java.lang.String str) {
        if (this.mCallbacks == null) {
            return;
        }
        synchronized (this.mCallbacks) {
            this.mCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.stub.ImsConfigImplBase.lambda$notifyConfigChanged$1(i, str, (android.telephony.ims.aidl.IImsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyConfigChanged$1(int i, java.lang.String str, android.telephony.ims.aidl.IImsConfigCallback iImsConfigCallback) {
        try {
            iImsConfigCallback.onStringConfigChanged(i, str);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "notifyConfigChanged(string): dead binder in notify, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        this.mRcsCallbacks.register(iRcsConfigCallback);
        synchronized (this.mRcsConfigDataLock) {
            if (this.mRcsConfigData == null) {
                return;
            }
            byte[] bArr = (byte[]) this.mRcsConfigData.clone();
            try {
                iRcsConfigCallback.onConfigurationChanged(bArr);
            } catch (android.os.RemoteException e) {
                android.util.Log.w(TAG, "dead binder to call onConfigurationChanged, skipping.");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRcsConfigCallback(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        this.mRcsCallbacks.unregister(iRcsConfigCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) {
        final byte[] decompressGzip = z ? android.telephony.ims.RcsConfig.decompressGzip(bArr) : bArr;
        synchronized (this.mRcsConfigDataLock) {
            if (java.util.Arrays.equals(this.mRcsConfigData, bArr)) {
                return;
            }
            this.mRcsConfigData = decompressGzip;
            if (this.mRcsCallbacks != null) {
                synchronized (this.mRcsCallbacks) {
                    this.mRcsCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(java.lang.Object obj) {
                            android.telephony.ims.stub.ImsConfigImplBase.lambda$onNotifyRcsAutoConfigurationReceived$2(decompressGzip, (android.telephony.ims.aidl.IRcsConfigCallback) obj);
                        }
                    });
                }
            }
            notifyRcsAutoConfigurationReceived(bArr, z);
        }
    }

    static /* synthetic */ void lambda$onNotifyRcsAutoConfigurationReceived$2(byte[] bArr, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onConfigurationChanged((byte[]) bArr.clone());
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "dead binder in notifyRcsAutoConfigurationReceived, skipping.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifyRcsAutoConfigurationRemoved() {
        synchronized (this.mRcsConfigDataLock) {
            this.mRcsConfigData = null;
        }
        if (this.mRcsCallbacks != null) {
            synchronized (this.mRcsCallbacks) {
                this.mRcsCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.telephony.ims.stub.ImsConfigImplBase.lambda$onNotifyRcsAutoConfigurationRemoved$3((android.telephony.ims.aidl.IRcsConfigCallback) obj);
                    }
                });
            }
        }
        notifyRcsAutoConfigurationRemoved();
    }

    static /* synthetic */ void lambda$onNotifyRcsAutoConfigurationRemoved$3(android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onConfigurationReset();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "dead binder in notifyRcsAutoConfigurationRemoved, skipping.");
        }
    }

    public android.telephony.ims.aidl.IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int i, int i2) {
        this.mImsConfigStub.updateCachedValue(i, i2);
        try {
            this.mImsConfigStub.notifyImsConfigChanged(i, i2);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "notifyProvisionedValueChanged(int): Framework connection is dead.");
        }
    }

    public final void notifyProvisionedValueChanged(int i, java.lang.String str) {
        this.mImsConfigStub.updateCachedValue(i, str);
        try {
            this.mImsConfigStub.notifyImsConfigChanged(i, str);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "notifyProvisionedValueChanged(string): Framework connection is dead.");
        }
    }

    public void notifyRcsAutoConfigurationReceived(byte[] bArr, boolean z) {
    }

    public void notifyRcsAutoConfigurationRemoved() {
    }

    public int setConfig(int i, int i2) {
        return 1;
    }

    public int setConfig(int i, java.lang.String str) {
        return 1;
    }

    public int getConfigInt(int i) {
        return -1;
    }

    public java.lang.String getConfigString(int i) {
        return null;
    }

    public void updateImsCarrierConfigs(android.os.PersistableBundle persistableBundle) {
    }

    public void setRcsClientConfiguration(android.telephony.ims.RcsClientConfiguration rcsClientConfiguration) {
    }

    public void triggerAutoConfiguration() {
    }

    public final void notifyAutoConfigurationErrorReceived(final int i, final java.lang.String str) {
        if (this.mRcsCallbacks == null) {
            return;
        }
        synchronized (this.mRcsCallbacks) {
            this.mRcsCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.stub.ImsConfigImplBase.lambda$notifyAutoConfigurationErrorReceived$4(i, str, (android.telephony.ims.aidl.IRcsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyAutoConfigurationErrorReceived$4(int i, java.lang.String str, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onAutoConfigurationErrorReceived(i, str);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "dead binder in notifyAutoConfigurationErrorReceived, skipping.");
        }
    }

    public final void notifyPreProvisioningReceived(final byte[] bArr) {
        if (this.mRcsCallbacks == null) {
            return;
        }
        synchronized (this.mRcsCallbacks) {
            this.mRcsCallbacks.broadcastAction(new java.util.function.Consumer() { // from class: android.telephony.ims.stub.ImsConfigImplBase$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    android.telephony.ims.stub.ImsConfigImplBase.lambda$notifyPreProvisioningReceived$5(bArr, (android.telephony.ims.aidl.IRcsConfigCallback) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$notifyPreProvisioningReceived$5(byte[] bArr, android.telephony.ims.aidl.IRcsConfigCallback iRcsConfigCallback) {
        try {
            iRcsConfigCallback.onPreProvisioningReceived(bArr);
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "dead binder in notifyPreProvisioningReceived, skipping.");
        }
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mImsConfigStub.mExecutor == null) {
            this.mImsConfigStub.mExecutor = executor;
        }
    }

    public final void clearConfigurationCache() {
        this.mImsConfigStub.clearCachedValue();
        synchronized (this.mRcsConfigDataLock) {
            this.mRcsConfigData = null;
        }
    }
}
