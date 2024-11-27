package com.android.server.updates;

/* loaded from: classes2.dex */
public class SmsShortCodesInstallReceiver extends com.android.server.updates.ConfigUpdateInstallReceiver {
    public SmsShortCodesInstallReceiver() {
        super("/data/misc/sms/", "codes", "metadata/", "version");
    }
}
