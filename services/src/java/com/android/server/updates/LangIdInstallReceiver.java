package com.android.server.updates;

/* loaded from: classes2.dex */
public class LangIdInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public LangIdInstallReceiver() {
        super("/data/misc/textclassifier/", "lang_id.model", "metadata/lang_id", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected boolean verifyVersion(int i, int i2) {
        return true;
    }
}
