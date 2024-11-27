package com.android.server.backup.transport;

/* loaded from: classes.dex */
public class TransportNotRegisteredException extends android.util.AndroidException {
    public TransportNotRegisteredException(java.lang.String str) {
        super("Transport " + str + " not registered");
    }

    public TransportNotRegisteredException(android.content.ComponentName componentName) {
        super("Transport for host " + componentName + " not registered");
    }
}
