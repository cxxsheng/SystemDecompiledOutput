package com.android.server.firewall;

/* loaded from: classes.dex */
interface Filter {
    boolean matches(com.android.server.firewall.IntentFirewall intentFirewall, android.content.ComponentName componentName, android.content.Intent intent, int i, int i2, java.lang.String str, int i3);
}
