package android.telephony.mbms.vendor;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class MbmsStreamingServiceBase extends android.telephony.mbms.vendor.IMbmsStreamingService.Stub {
    public int initialize(android.telephony.mbms.MbmsStreamingSessionCallback mbmsStreamingSessionCallback, int i) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public final int initialize(final android.telephony.mbms.IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, final int i) throws android.os.RemoteException {
        if (iMbmsStreamingSessionCallback == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        final int callingUid = android.os.Binder.getCallingUid();
        int initialize = initialize(new android.telephony.mbms.MbmsStreamingSessionCallback() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.1
            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onError(int i2, java.lang.String str) {
                try {
                    if (i2 == -1) {
                        throw new java.lang.IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iMbmsStreamingSessionCallback.onError(i2, str);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onStreamingServicesUpdated(java.util.List<android.telephony.mbms.StreamingServiceInfo> list) {
                try {
                    iMbmsStreamingSessionCallback.onStreamingServicesUpdated(list);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.MbmsStreamingSessionCallback
            public void onMiddlewareReady() {
                try {
                    iMbmsStreamingSessionCallback.onMiddlewareReady();
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        }, i);
        if (initialize == 0) {
            iMbmsStreamingSessionCallback.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.2
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return initialize;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public int requestUpdateStreamingServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
        return 0;
    }

    public int startStreaming(int i, java.lang.String str, android.telephony.mbms.StreamingServiceCallback streamingServiceCallback) throws android.os.RemoteException {
        return 0;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public int startStreaming(final int i, java.lang.String str, final android.telephony.mbms.IStreamingServiceCallback iStreamingServiceCallback) throws android.os.RemoteException {
        if (iStreamingServiceCallback == null) {
            throw new java.lang.NullPointerException("Callback must not be null");
        }
        final int callingUid = android.os.Binder.getCallingUid();
        int startStreaming = startStreaming(i, str, new android.telephony.mbms.StreamingServiceCallback() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.3
            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onError(int i2, java.lang.String str2) {
                try {
                    if (i2 == -1) {
                        throw new java.lang.IllegalArgumentException("Middleware cannot send an unknown error.");
                    }
                    iStreamingServiceCallback.onError(i2, str2);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onStreamStateUpdated(int i2, int i3) {
                try {
                    iStreamingServiceCallback.onStreamStateUpdated(i2, i3);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onMediaDescriptionUpdated() {
                try {
                    iStreamingServiceCallback.onMediaDescriptionUpdated();
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onBroadcastSignalStrengthUpdated(int i2) {
                try {
                    iStreamingServiceCallback.onBroadcastSignalStrengthUpdated(i2);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }

            @Override // android.telephony.mbms.StreamingServiceCallback
            public void onStreamMethodUpdated(int i2) {
                try {
                    iStreamingServiceCallback.onStreamMethodUpdated(i2);
                } catch (android.os.RemoteException e) {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }
        });
        if (startStreaming == 0) {
            iStreamingServiceCallback.asBinder().linkToDeath(new android.os.IBinder.DeathRecipient() { // from class: android.telephony.mbms.vendor.MbmsStreamingServiceBase.4
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    android.telephony.mbms.vendor.MbmsStreamingServiceBase.this.onAppCallbackDied(callingUid, i);
                }
            }, 0);
        }
        return startStreaming;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public android.net.Uri getPlaybackUri(int i, java.lang.String str) throws android.os.RemoteException {
        return null;
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public void stopStreaming(int i, java.lang.String str) throws android.os.RemoteException {
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService
    public void dispose(int i) throws android.os.RemoteException {
    }

    public void onAppCallbackDied(int i, int i2) {
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService.Stub, android.os.IInterface
    @android.annotation.SystemApi
    public android.os.IBinder asBinder() {
        return super.asBinder();
    }

    @Override // android.telephony.mbms.vendor.IMbmsStreamingService.Stub, android.os.Binder
    @android.annotation.SystemApi
    public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
        return super.onTransact(i, parcel, parcel2, i2);
    }
}
