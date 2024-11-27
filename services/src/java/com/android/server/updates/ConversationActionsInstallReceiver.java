package com.android.server.updates;

/* loaded from: classes2.dex */
public class ConversationActionsInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public ConversationActionsInstallReceiver() {
        super("/data/misc/textclassifier/", "actions_suggestions.model", "metadata/actions_suggestions", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    protected boolean verifyVersion(int i, int i2) {
        return true;
    }
}
