package com.android.server.updates;

/* loaded from: classes2.dex */
public class CertPinInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public CertPinInstallReceiver() {
        super("/data/misc/keychain/", "pins", "metadata/", "version");
    }
}
