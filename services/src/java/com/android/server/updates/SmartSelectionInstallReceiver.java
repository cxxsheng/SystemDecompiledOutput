package com.android.server.updates;

/* loaded from: classes2.dex */
public class SmartSelectionInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public SmartSelectionInstallReceiver() {
        super("/data/misc/textclassifier/", "textclassifier.model", "metadata/classification", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected boolean verifyVersion(int i, int i2) {
        return true;
    }
}
