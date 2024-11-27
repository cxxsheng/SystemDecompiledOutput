package android.telephony.mbms.vendor;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class MbmsGroupCallServiceBase extends android.app.Service {
    private final android.os.IBinder mInterface = new android.telephony.mbms.vendor.IMbmsGroupCallService.Stub() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1
        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int initialize(final android.telephony.mbms.IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, final int i) throws android.os.RemoteException {
            if (iMbmsGroupCallSessionCallback == null) {
                throw new java.lang.NullPointerException("Callback must not be null");
            }
            final int callingUid = android.os.Binder.getCallingUid();
            int initialize = android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.initialize(new android.telephony.mbms.MbmsGroupCallSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.1
                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onError(int i2, java.lang.String str) {
                    try {
                        if (i2 == -1) {
                            throw new java.lang.IllegalArgumentException("Middleware cannot send an unknown error.");
                        }
                        iMbmsGroupCallSessionCallback.onError(i2, str);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onAvailableSaisUpdated(java.util.List list, java.util.List list2) {
                    try {
                        iMbmsGroupCallSessionCallback.onAvailableSaisUpdated(list, list2);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onServiceInterfaceAvailable(java.lang.String str, int i2) {
                    try {
                        iMbmsGroupCallSessionCallback.onServiceInterfaceAvailable(str, i2);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.MbmsGroupCallSessionCallback
                public void onMiddlewareReady() {
                    try {
                        iMbmsGroupCallSessionCallback.onMiddlewareReady();
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }
            }, i);
            if (initialize == 0) {
                iMbmsGroupCallSessionCallback.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.2
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }, 0);
            }
            return initialize;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void stopGroupCall(int i, long j) {
            android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.stopGroupCall(i, j);
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void updateGroupCall(int i, long j, java.util.List list, java.util.List list2) {
            android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.updateGroupCall(i, j, list, list2);
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int startGroupCall(final int i, long j, java.util.List list, java.util.List list2, final android.telephony.mbms.IGroupCallCallback iGroupCallCallback) throws android.os.RemoteException {
            if (iGroupCallCallback == null) {
                throw new java.lang.NullPointerException("Callback must not be null");
            }
            final int callingUid = android.os.Binder.getCallingUid();
            int startGroupCall = android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.startGroupCall(i, j, list, list2, new android.telephony.mbms.GroupCallCallback() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.3
                @Override // android.telephony.mbms.GroupCallCallback
                public void onError(int i2, java.lang.String str) {
                    try {
                        if (i2 == -1) {
                            throw new java.lang.IllegalArgumentException("Middleware cannot send an unknown error.");
                        }
                        iGroupCallCallback.onError(i2, str);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.GroupCallCallback
                public void onGroupCallStateChanged(int i2, int i3) {
                    try {
                        iGroupCallCallback.onGroupCallStateChanged(i2, i3);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }

                @Override // android.telephony.mbms.GroupCallCallback
                public void onBroadcastSignalStrengthUpdated(int i2) {
                    try {
                        iGroupCallCallback.onBroadcastSignalStrengthUpdated(i2);
                    } catch (android.os.RemoteException e) {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }
            });
            if (startGroupCall == 0) {
                iGroupCallCallback.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsGroupCallServiceBase.1.4
                    @Override // android.os.IBinder.DeathRecipient
                    public void binderDied() {
                        android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.onAppCallbackDied(callingUid, i);
                    }
                }, 0);
            }
            return startGroupCall;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void dispose(int i) throws android.os.RemoteException {
            android.telephony.mbms.vendor.MbmsGroupCallServiceBase.this.dispose(i);
        }
    };

    public int initialize(android.telephony.mbms.MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, int i) throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    public int startGroupCall(int i, long j, java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2, android.telephony.mbms.GroupCallCallback groupCallCallback) {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    public void stopGroupCall(int i, long j) {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    public void updateGroupCall(int i, long j, java.util.List<java.lang.Integer> list, java.util.List<java.lang.Integer> list2) {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    public void dispose(int i) throws android.os.RemoteException {
        throw new java.lang.UnsupportedOperationException("Not implemented");
    }

    public void onAppCallbackDied(int i, int i2) {
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mInterface;
    }
}
