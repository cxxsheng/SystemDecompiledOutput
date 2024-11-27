package com.android.server.locksettings;

/* loaded from: classes2.dex */
class AuthSecretHidlAdapter implements android.hardware.authsecret.IAuthSecret {
    private final android.hardware.authsecret.V1_0.IAuthSecret mImpl;

    AuthSecretHidlAdapter(android.hardware.authsecret.V1_0.IAuthSecret iAuthSecret) {
        this.mImpl = iAuthSecret;
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public void setPrimaryUserCredential(byte[] bArr) throws android.os.RemoteException {
        java.util.ArrayList<java.lang.Byte> arrayList = new java.util.ArrayList<>(bArr.length);
        for (byte b : bArr) {
            arrayList.add(java.lang.Byte.valueOf(b));
        }
        this.mImpl.primaryUserCredential(arrayList);
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public int getInterfaceVersion() throws android.os.RemoteException {
        return 1;
    }

    @Override // android.os.IInterface
    public android.os.IBinder asBinder() {
        throw new java.lang.UnsupportedOperationException("AuthSecretHidlAdapter does not support asBinder");
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public java.lang.String getInterfaceHash() throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException("AuthSecretHidlAdapter does not support getInterfaceHash");
    }
}
