package com.android.server.pdb;

/* loaded from: classes2.dex */
public interface PersistentDataBlockManagerInternal {
    void clearTestHarnessModeData();

    boolean deactivateFactoryResetProtectionWithoutSecret();

    void forceOemUnlockEnabled(boolean z);

    int getAllowedUid();

    byte[] getFrpCredentialHandle();

    byte[] getTestHarnessModeData();

    void setFrpCredentialHandle(byte[] bArr);

    void setTestHarnessModeData(byte[] bArr);
}
