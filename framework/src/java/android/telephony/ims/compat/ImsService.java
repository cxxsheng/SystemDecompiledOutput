package android.telephony.ims.compat;

/* loaded from: classes3.dex */
public class ImsService extends android.app.Service {
    private static final java.lang.String LOG_TAG = "ImsService(Compat)";
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.ims.compat.ImsService";
    private final android.util.SparseArray<android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature>> mFeaturesBySlot = new android.util.SparseArray<>();
    protected final android.os.IBinder mImsServiceController = new com.android.ims.internal.IImsServiceController.Stub() { // from class: android.telephony.ims.compat.ImsService.1
        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeature(int i) {
            return android.telephony.ims.compat.ImsService.this.createEmergencyMMTelFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsMMTelFeature createMMTelFeature(int i) {
            return android.telephony.ims.compat.ImsService.this.createMMTelFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public com.android.ims.internal.IImsRcsFeature createRcsFeature(int i) {
            return android.telephony.ims.compat.ImsService.this.createRcsFeatureInternal(i);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeImsFeature(int i, int i2) {
            android.telephony.ims.compat.ImsService.this.removeImsFeature(i, i2);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void addFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.compat.ImsService.this.addImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // com.android.ims.internal.IImsServiceController
        public void removeFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            android.telephony.ims.compat.ImsService.this.removeImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }
    };

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            android.util.Log.i(LOG_TAG, "ImsService(Compat) Bound.");
            return this.mImsServiceController;
        }
        return null;
    }

    public android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature> getFeatures(int i) {
        return this.mFeaturesBySlot.get(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.ims.internal.IImsMMTelFeature createEmergencyMMTelFeatureInternal(int i) {
        android.telephony.ims.compat.feature.MMTelFeature onCreateEmergencyMMTelImsFeature = onCreateEmergencyMMTelImsFeature(i);
        if (onCreateEmergencyMMTelImsFeature != null) {
            setupFeature(onCreateEmergencyMMTelImsFeature, i, 0);
            return onCreateEmergencyMMTelImsFeature.getBinder();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.ims.internal.IImsMMTelFeature createMMTelFeatureInternal(int i) {
        android.telephony.ims.compat.feature.MMTelFeature onCreateMMTelImsFeature = onCreateMMTelImsFeature(i);
        if (onCreateMMTelImsFeature != null) {
            setupFeature(onCreateMMTelImsFeature, i, 1);
            return onCreateMMTelImsFeature.getBinder();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.ims.internal.IImsRcsFeature createRcsFeatureInternal(int i) {
        android.telephony.ims.compat.feature.RcsFeature onCreateRcsFeature = onCreateRcsFeature(i);
        if (onCreateRcsFeature != null) {
            setupFeature(onCreateRcsFeature, i, 2);
            return onCreateRcsFeature.getBinder();
        }
        return null;
    }

    private void setupFeature(android.telephony.ims.compat.feature.ImsFeature imsFeature, int i, int i2) {
        imsFeature.setContext(this);
        imsFeature.setSlotId(i);
        addImsFeature(i, i2, imsFeature);
        imsFeature.onFeatureReady();
    }

    private void addImsFeature(int i, int i2, android.telephony.ims.compat.feature.ImsFeature imsFeature) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                sparseArray = new android.util.SparseArray<>();
                this.mFeaturesBySlot.put(i, sparseArray);
            }
            sparseArray.put(i2, imsFeature);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not add ImsFeatureStatusCallback. No ImsFeatures exist on slot " + i);
                return;
            }
            android.telephony.ims.compat.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature != null) {
                imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeatureStatusCallback(int i, int i2, com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeatureStatusCallback. No ImsFeatures exist on slot " + i);
                return;
            }
            android.telephony.ims.compat.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature != null) {
                imsFeature.removeImsFeatureStatusCallback(iImsFeatureStatusCallback);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeature(int i, int i2) {
        synchronized (this.mFeaturesBySlot) {
            android.util.SparseArray<android.telephony.ims.compat.feature.ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeature. No ImsFeatures exist on slot " + i);
                return;
            }
            android.telephony.ims.compat.feature.ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature == null) {
                android.util.Log.w(LOG_TAG, "Can not remove ImsFeature. No feature with type " + i2 + " exists on slot " + i);
            } else {
                imsFeature.onFeatureRemoved();
                sparseArray.remove(i2);
            }
        }
    }

    public android.telephony.ims.compat.feature.MMTelFeature onCreateEmergencyMMTelImsFeature(int i) {
        return null;
    }

    public android.telephony.ims.compat.feature.MMTelFeature onCreateMMTelImsFeature(int i) {
        return null;
    }

    public android.telephony.ims.compat.feature.RcsFeature onCreateRcsFeature(int i) {
        return null;
    }
}
