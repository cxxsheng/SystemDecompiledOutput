package com.android.server.updates;

/* loaded from: classes2.dex */
public class IntentFirewallInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public IntentFirewallInstallReceiver() {
        super(com.android.server.firewall.IntentFirewall.getRulesDir().getAbsolutePath(), "ifw.xml", "metadata/", "gservices.version");
    }
}
