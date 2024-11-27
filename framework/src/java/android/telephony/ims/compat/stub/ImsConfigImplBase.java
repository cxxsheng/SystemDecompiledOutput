package android.telephony.ims.compat.stub;

/* loaded from: classes3.dex */
public class ImsConfigImplBase {
    private static final java.lang.String TAG = "ImsConfigImplBase";
    android.telephony.ims.compat.stub.ImsConfigImplBase.ImsConfigStub mImsConfigStub;

    public ImsConfigImplBase(android.content.Context context) {
        this.mImsConfigStub = new android.telephony.ims.compat.stub.ImsConfigImplBase.ImsConfigStub(this, context);
    }

    public int getProvisionedValue(int i) throws android.os.RemoteException {
        return -1;
    }

    public java.lang.String getProvisionedStringValue(int i) throws android.os.RemoteException {
        return null;
    }

    public int setProvisionedValue(int i, int i2) throws android.os.RemoteException {
        return 1;
    }

    public int setProvisionedStringValue(int i, java.lang.String str) throws android.os.RemoteException {
        return 1;
    }

    public void getFeatureValue(int i, int i2, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
    }

    public void setFeatureValue(int i, int i2, int i3, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
    }

    public boolean getVolteProvisioned() throws android.os.RemoteException {
        return false;
    }

    public void getVideoQuality(com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
    }

    public void setVideoQuality(int i, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
    }

    public com.android.ims.internal.IImsConfig getIImsConfig() {
        return this.mImsConfigStub;
    }

    public final void notifyProvisionedValueChanged(int i, int i2) {
        this.mImsConfigStub.updateCachedValue(i, i2, true);
    }

    public final void notifyProvisionedValueChanged(int i, java.lang.String str) {
        this.mImsConfigStub.updateCachedValue(i, str, true);
    }

    public static class ImsConfigStub extends com.android.ims.internal.IImsConfig.Stub {
        android.content.Context mContext;
        java.lang.ref.WeakReference<android.telephony.ims.compat.stub.ImsConfigImplBase> mImsConfigImplBaseWeakReference;
        private java.util.HashMap<java.lang.Integer, java.lang.Integer> mProvisionedIntValue = new java.util.HashMap<>();
        private java.util.HashMap<java.lang.Integer, java.lang.String> mProvisionedStringValue = new java.util.HashMap<>();

        public ImsConfigStub(android.telephony.ims.compat.stub.ImsConfigImplBase imsConfigImplBase, android.content.Context context) {
            this.mContext = context;
            this.mImsConfigImplBaseWeakReference = new java.lang.ref.WeakReference<>(imsConfigImplBase);
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int getProvisionedValue(int i) throws android.os.RemoteException {
            if (this.mProvisionedIntValue.containsKey(java.lang.Integer.valueOf(i))) {
                return this.mProvisionedIntValue.get(java.lang.Integer.valueOf(i)).intValue();
            }
            int provisionedValue = getImsConfigImpl().getProvisionedValue(i);
            if (provisionedValue != -1) {
                updateCachedValue(i, provisionedValue, false);
            }
            return provisionedValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized java.lang.String getProvisionedStringValue(int i) throws android.os.RemoteException {
            if (this.mProvisionedIntValue.containsKey(java.lang.Integer.valueOf(i))) {
                return this.mProvisionedStringValue.get(java.lang.Integer.valueOf(i));
            }
            java.lang.String provisionedStringValue = getImsConfigImpl().getProvisionedStringValue(i);
            if (provisionedStringValue != null) {
                updateCachedValue(i, provisionedStringValue, false);
            }
            return provisionedStringValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int setProvisionedValue(int i, int i2) throws android.os.RemoteException {
            int provisionedValue;
            this.mProvisionedIntValue.remove(java.lang.Integer.valueOf(i));
            provisionedValue = getImsConfigImpl().setProvisionedValue(i, i2);
            if (provisionedValue == 0) {
                updateCachedValue(i, i2, true);
            } else {
                android.util.Log.d(android.telephony.ims.compat.stub.ImsConfigImplBase.TAG, "Set provision value of " + i + " to " + i2 + " failed with error code " + provisionedValue);
            }
            return provisionedValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public synchronized int setProvisionedStringValue(int i, java.lang.String str) throws android.os.RemoteException {
            int provisionedStringValue;
            this.mProvisionedStringValue.remove(java.lang.Integer.valueOf(i));
            provisionedStringValue = getImsConfigImpl().setProvisionedStringValue(i, str);
            if (provisionedStringValue == 0) {
                updateCachedValue(i, str, true);
            }
            return provisionedStringValue;
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getFeatureValue(int i, int i2, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
            getImsConfigImpl().getFeatureValue(i, i2, imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setFeatureValue(int i, int i2, int i3, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
            getImsConfigImpl().setFeatureValue(i, i2, i3, imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public boolean getVolteProvisioned() throws android.os.RemoteException {
            return getImsConfigImpl().getVolteProvisioned();
        }

        @Override // com.android.ims.internal.IImsConfig
        public void getVideoQuality(com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
            getImsConfigImpl().getVideoQuality(imsConfigListener);
        }

        @Override // com.android.ims.internal.IImsConfig
        public void setVideoQuality(int i, com.android.ims.ImsConfigListener imsConfigListener) throws android.os.RemoteException {
            getImsConfigImpl().setVideoQuality(i, imsConfigListener);
        }

        private android.telephony.ims.compat.stub.ImsConfigImplBase getImsConfigImpl() throws android.os.RemoteException {
            android.telephony.ims.compat.stub.ImsConfigImplBase imsConfigImplBase = this.mImsConfigImplBaseWeakReference.get();
            if (imsConfigImplBase == null) {
                throw new android.os.RemoteException("Fail to get ImsConfigImpl");
            }
            return imsConfigImplBase;
        }

        private void sendImsConfigChangedIntent(int i, int i2) {
            sendImsConfigChangedIntent(i, java.lang.Integer.toString(i2));
        }

        private void sendImsConfigChangedIntent(int i, java.lang.String str) {
            android.content.Intent intent = new android.content.Intent(com.android.ims.ImsConfig.ACTION_IMS_CONFIG_CHANGED);
            intent.putExtra(com.android.ims.ImsConfig.EXTRA_CHANGED_ITEM, i);
            intent.putExtra("value", str);
            if (this.mContext != null) {
                this.mContext.sendBroadcast(intent);
            }
        }

        protected synchronized void updateCachedValue(int i, int i2, boolean z) {
            this.mProvisionedIntValue.put(java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(i2));
            if (z) {
                sendImsConfigChangedIntent(i, i2);
            }
        }

        protected synchronized void updateCachedValue(int i, java.lang.String str, boolean z) {
            this.mProvisionedStringValue.put(java.lang.Integer.valueOf(i), str);
            if (z) {
                sendImsConfigChangedIntent(i, str);
            }
        }
    }
}
