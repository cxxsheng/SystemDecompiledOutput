package com.android.server.am;

/* loaded from: classes.dex */
public class BroadcastRetryException extends com.android.server.am.BroadcastDeliveryFailedException {
    public BroadcastRetryException(java.lang.String str) {
        super(str);
    }

    public BroadcastRetryException(java.lang.Exception exc) {
        super(exc);
    }
}
