package com.android.server.locksettings;

/* loaded from: classes2.dex */
class WeaverHidlAdapter implements android.hardware.weaver.IWeaver {
    private static final java.lang.String TAG = "WeaverHidlAdapter";
    private final android.hardware.weaver.V1_0.IWeaver mImpl;

    WeaverHidlAdapter(android.hardware.weaver.V1_0.IWeaver iWeaver) {
        this.mImpl = iWeaver;
    }

    @Override // android.hardware.weaver.IWeaver
    public android.hardware.weaver.WeaverConfig getConfig() throws android.os.RemoteException {
        final android.hardware.weaver.WeaverConfig[] weaverConfigArr = new android.hardware.weaver.WeaverConfig[1];
        this.mImpl.getConfig(new android.hardware.weaver.V1_0.IWeaver.getConfigCallback() { // from class: com.android.server.locksettings.WeaverHidlAdapter$$ExternalSyntheticLambda0
            @Override // android.hardware.weaver.V1_0.IWeaver.getConfigCallback
            public final void onValues(int i, android.hardware.weaver.V1_0.WeaverConfig weaverConfig) {
                com.android.server.locksettings.WeaverHidlAdapter.lambda$getConfig$0(weaverConfigArr, i, weaverConfig);
            }
        });
        return weaverConfigArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getConfig$0(android.hardware.weaver.WeaverConfig[] weaverConfigArr, int i, android.hardware.weaver.V1_0.WeaverConfig weaverConfig) {
        if (i == 0) {
            android.hardware.weaver.WeaverConfig weaverConfig2 = new android.hardware.weaver.WeaverConfig();
            weaverConfig2.slots = weaverConfig.slots;
            weaverConfig2.keySize = weaverConfig.keySize;
            weaverConfig2.valueSize = weaverConfig.valueSize;
            weaverConfigArr[0] = weaverConfig2;
            return;
        }
        android.util.Slog.e(TAG, "Failed to get HIDL weaver config. status: " + i + ", slots: " + weaverConfig.slots);
    }

    @Override // android.hardware.weaver.IWeaver
    public android.hardware.weaver.WeaverReadResponse read(int i, byte[] bArr) throws android.os.RemoteException {
        final android.hardware.weaver.WeaverReadResponse[] weaverReadResponseArr = new android.hardware.weaver.WeaverReadResponse[1];
        this.mImpl.read(i, toByteArrayList(bArr), new android.hardware.weaver.V1_0.IWeaver.readCallback() { // from class: com.android.server.locksettings.WeaverHidlAdapter$$ExternalSyntheticLambda1
            @Override // android.hardware.weaver.V1_0.IWeaver.readCallback
            public final void onValues(int i2, android.hardware.weaver.V1_0.WeaverReadResponse weaverReadResponse) {
                com.android.server.locksettings.WeaverHidlAdapter.lambda$read$1(weaverReadResponseArr, i2, weaverReadResponse);
            }
        });
        return weaverReadResponseArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$read$1(android.hardware.weaver.WeaverReadResponse[] weaverReadResponseArr, int i, android.hardware.weaver.V1_0.WeaverReadResponse weaverReadResponse) {
        android.hardware.weaver.WeaverReadResponse weaverReadResponse2 = new android.hardware.weaver.WeaverReadResponse();
        switch (i) {
            case 0:
                weaverReadResponse2.status = 0;
                break;
            case 1:
                weaverReadResponse2.status = 1;
                break;
            case 2:
                weaverReadResponse2.status = 2;
                break;
            case 3:
                weaverReadResponse2.status = 3;
                break;
            default:
                android.util.Slog.e(TAG, "Unexpected status in read: " + i);
                weaverReadResponse2.status = 1;
                break;
        }
        weaverReadResponse2.timeout = weaverReadResponse.timeout;
        weaverReadResponse2.value = fromByteArrayList(weaverReadResponse.value);
        weaverReadResponseArr[0] = weaverReadResponse2;
    }

    @Override // android.hardware.weaver.IWeaver
    public void write(int i, byte[] bArr, byte[] bArr2) throws android.os.RemoteException {
        int write = this.mImpl.write(i, toByteArrayList(bArr), toByteArrayList(bArr2));
        if (write != 0) {
            throw new android.os.ServiceSpecificException(1, "Failed IWeaver.write call, status: " + write);
        }
    }

    @Override // android.hardware.weaver.IWeaver
    public java.lang.String getInterfaceHash() {
        throw new java.lang.UnsupportedOperationException("WeaverHidlAdapter does not support getInterfaceHash");
    }

    @Override // android.hardware.weaver.IWeaver
    public int getInterfaceVersion() {
        return 2;
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        throw new java.lang.UnsupportedOperationException("WeaverHidlAdapter does not support asBinder");
    }

    private static java.util.ArrayList<java.lang.Byte> toByteArrayList(byte[] bArr) {
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>(bArr.length);
        for (byte b : bArr) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        return arrayList;
    }

    private static byte[] fromByteArrayList(java.util.ArrayList<java.lang.Byte> arrayList) {
        byte[] bArr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            bArr[i] = arrayList.get(i).byteValue();
        }
        return bArr;
    }
}
