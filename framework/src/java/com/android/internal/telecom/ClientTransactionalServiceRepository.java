package com.android.internal.telecom;

/* loaded from: classes5.dex */
public class ClientTransactionalServiceRepository {
    private static final java.util.Map<android.telecom.PhoneAccountHandle, com.android.internal.telecom.ClientTransactionalServiceWrapper> LOOKUP_TABLE = new java.util.concurrent.ConcurrentHashMap();

    public com.android.internal.telecom.ClientTransactionalServiceWrapper addNewCallForTransactionalServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ClientTransactionalServiceWrapper transactionalServiceWrapper;
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            transactionalServiceWrapper = new com.android.internal.telecom.ClientTransactionalServiceWrapper(phoneAccountHandle, this);
        } else {
            transactionalServiceWrapper = getTransactionalServiceWrapper(phoneAccountHandle);
        }
        LOOKUP_TABLE.put(phoneAccountHandle, transactionalServiceWrapper);
        return transactionalServiceWrapper;
    }

    private com.android.internal.telecom.ClientTransactionalServiceWrapper getTransactionalServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return LOOKUP_TABLE.get(phoneAccountHandle);
    }

    private boolean hasExistingServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return LOOKUP_TABLE.containsKey(phoneAccountHandle);
    }

    public boolean removeServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            return false;
        }
        LOOKUP_TABLE.remove(phoneAccountHandle);
        return true;
    }

    public boolean removeCallFromServiceWrapper(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) {
        if (!hasExistingServiceWrapper(phoneAccountHandle)) {
            return false;
        }
        LOOKUP_TABLE.get(phoneAccountHandle).untrackCall(str);
        return true;
    }
}
