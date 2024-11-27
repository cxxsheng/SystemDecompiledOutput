package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputSessionCallback extends android.os.IInterface {
    void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException;

    void onAdResponse(android.media.tv.AdResponse adResponse) throws android.os.RemoteException;

    void onAitInfoUpdated(android.media.tv.AitInfo aitInfo) throws android.os.RemoteException;

    void onAudioPresentationSelected(int i, int i2) throws android.os.RemoteException;

    void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list) throws android.os.RemoteException;

    void onAvailableSpeeds(float[] fArr) throws android.os.RemoteException;

    void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) throws android.os.RemoteException;

    void onChannelRetuned(android.net.Uri uri) throws android.os.RemoteException;

    void onContentAllowed() throws android.os.RemoteException;

    void onContentBlocked(java.lang.String str) throws android.os.RemoteException;

    void onCueingMessageAvailability(boolean z) throws android.os.RemoteException;

    void onError(int i) throws android.os.RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void onRecordingStopped(android.net.Uri uri) throws android.os.RemoteException;

    void onSessionCreated(android.media.tv.ITvInputSession iTvInputSession, android.os.IBinder iBinder) throws android.os.RemoteException;

    void onSessionEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onSignalStrength(int i) throws android.os.RemoteException;

    void onTimeShiftCurrentPositionChanged(long j) throws android.os.RemoteException;

    void onTimeShiftMode(int i) throws android.os.RemoteException;

    void onTimeShiftStartPositionChanged(long j) throws android.os.RemoteException;

    void onTimeShiftStatusChanged(int i) throws android.os.RemoteException;

    void onTrackSelected(int i, java.lang.String str) throws android.os.RemoteException;

    void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException;

    void onTuned(android.net.Uri uri) throws android.os.RemoteException;

    void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void onVideoAvailable() throws android.os.RemoteException;

    void onVideoFreezeUpdated(boolean z) throws android.os.RemoteException;

    void onVideoUnavailable(int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputSessionCallback {
        @Override // android.media.tv.ITvInputSessionCallback
        public void onSessionCreated(android.media.tv.ITvInputSession iTvInputSession, android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onChannelRetuned(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAudioPresentationSelected(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTrackSelected(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoAvailable() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoUnavailable(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onVideoFreezeUpdated(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onContentAllowed() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onContentBlocked(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftStatusChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftStartPositionChanged(long j) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftCurrentPositionChanged(long j) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onSignalStrength(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onCueingMessageAvailability(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTimeShiftMode(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAvailableSpeeds(float[] fArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTuned(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onRecordingStopped(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAdResponse(android.media.tv.AdResponse adResponse) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSessionCallback
        public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputSessionCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputSessionCallback";
        static final int TRANSACTION_onAdBufferConsumed = 27;
        static final int TRANSACTION_onAdResponse = 26;
        static final int TRANSACTION_onAitInfoUpdated = 17;
        static final int TRANSACTION_onAudioPresentationSelected = 5;
        static final int TRANSACTION_onAudioPresentationsChanged = 4;
        static final int TRANSACTION_onAvailableSpeeds = 21;
        static final int TRANSACTION_onBroadcastInfoResponse = 25;
        static final int TRANSACTION_onChannelRetuned = 3;
        static final int TRANSACTION_onContentAllowed = 11;
        static final int TRANSACTION_onContentBlocked = 12;
        static final int TRANSACTION_onCueingMessageAvailability = 19;
        static final int TRANSACTION_onError = 24;
        static final int TRANSACTION_onLayoutSurface = 13;
        static final int TRANSACTION_onRecordingStopped = 23;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 2;
        static final int TRANSACTION_onSignalStrength = 18;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 16;
        static final int TRANSACTION_onTimeShiftMode = 20;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 15;
        static final int TRANSACTION_onTimeShiftStatusChanged = 14;
        static final int TRANSACTION_onTrackSelected = 7;
        static final int TRANSACTION_onTracksChanged = 6;
        static final int TRANSACTION_onTuned = 22;
        static final int TRANSACTION_onTvInputSessionData = 29;
        static final int TRANSACTION_onTvMessage = 28;
        static final int TRANSACTION_onVideoAvailable = 8;
        static final int TRANSACTION_onVideoFreezeUpdated = 10;
        static final int TRANSACTION_onVideoUnavailable = 9;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputSessionCallback)) {
                return (android.media.tv.ITvInputSessionCallback) queryLocalInterface;
            }
            return new android.media.tv.ITvInputSessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSessionCreated";
                case 2:
                    return "onSessionEvent";
                case 3:
                    return "onChannelRetuned";
                case 4:
                    return "onAudioPresentationsChanged";
                case 5:
                    return "onAudioPresentationSelected";
                case 6:
                    return "onTracksChanged";
                case 7:
                    return "onTrackSelected";
                case 8:
                    return "onVideoAvailable";
                case 9:
                    return "onVideoUnavailable";
                case 10:
                    return "onVideoFreezeUpdated";
                case 11:
                    return "onContentAllowed";
                case 12:
                    return "onContentBlocked";
                case 13:
                    return "onLayoutSurface";
                case 14:
                    return "onTimeShiftStatusChanged";
                case 15:
                    return "onTimeShiftStartPositionChanged";
                case 16:
                    return "onTimeShiftCurrentPositionChanged";
                case 17:
                    return "onAitInfoUpdated";
                case 18:
                    return "onSignalStrength";
                case 19:
                    return "onCueingMessageAvailability";
                case 20:
                    return "onTimeShiftMode";
                case 21:
                    return "onAvailableSpeeds";
                case 22:
                    return "onTuned";
                case 23:
                    return "onRecordingStopped";
                case 24:
                    return "onError";
                case 25:
                    return "onBroadcastInfoResponse";
                case 26:
                    return "onAdResponse";
                case 27:
                    return "onAdBufferConsumed";
                case 28:
                    return "onTvMessage";
                case 29:
                    return "onTvInputSessionData";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.tv.ITvInputSession asInterface = android.media.tv.ITvInputSession.Stub.asInterface(parcel.readStrongBinder());
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(asInterface, readStrongBinder);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSessionEvent(readString, bundle);
                    return true;
                case 3:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onChannelRetuned(uri);
                    return true;
                case 4:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioPresentation.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAudioPresentationsChanged(createTypedArrayList);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationSelected(readInt, readInt2);
                    return true;
                case 6:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTracksChanged(createTypedArrayList2);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onTrackSelected(readInt3, readString2);
                    return true;
                case 8:
                    onVideoAvailable();
                    return true;
                case 9:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoUnavailable(readInt4);
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onVideoFreezeUpdated(readBoolean);
                    return true;
                case 11:
                    onContentAllowed();
                    return true;
                case 12:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onContentBlocked(readString3);
                    return true;
                case 13:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt5, readInt6, readInt7, readInt8);
                    return true;
                case 14:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStatusChanged(readInt9);
                    return true;
                case 15:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStartPositionChanged(readLong);
                    return true;
                case 16:
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCurrentPositionChanged(readLong2);
                    return true;
                case 17:
                    android.media.tv.AitInfo aitInfo = (android.media.tv.AitInfo) parcel.readTypedObject(android.media.tv.AitInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAitInfoUpdated(aitInfo);
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrength(readInt10);
                    return true;
                case 19:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onCueingMessageAvailability(readBoolean2);
                    return true;
                case 20:
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftMode(readInt11);
                    return true;
                case 21:
                    float[] createFloatArray = parcel.createFloatArray();
                    parcel.enforceNoDataAvail();
                    onAvailableSpeeds(createFloatArray);
                    return true;
                case 22:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTuned(uri2);
                    return true;
                case 23:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRecordingStopped(uri3);
                    return true;
                case 24:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt12);
                    return true;
                case 25:
                    android.media.tv.BroadcastInfoResponse broadcastInfoResponse = (android.media.tv.BroadcastInfoResponse) parcel.readTypedObject(android.media.tv.BroadcastInfoResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoResponse(broadcastInfoResponse);
                    return true;
                case 26:
                    android.media.tv.AdResponse adResponse = (android.media.tv.AdResponse) parcel.readTypedObject(android.media.tv.AdResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdResponse(adResponse);
                    return true;
                case 27:
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdBufferConsumed(adBuffer);
                    return true;
                case 28:
                    int readInt13 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvMessage(readInt13, bundle2);
                    return true;
                case 29:
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTvInputSessionData(readString4, bundle3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSessionCreated(android.media.tv.ITvInputSession iTvInputSession, android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputSession);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSessionEvent(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onChannelRetuned(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAudioPresentationSelected(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTrackSelected(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoAvailable() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoUnavailable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onVideoFreezeUpdated(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onContentAllowed() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onContentBlocked(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftStatusChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftStartPositionChanged(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftCurrentPositionChanged(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(aitInfo, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onSignalStrength(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onCueingMessageAvailability(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTimeShiftMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAvailableSpeeds(float[] fArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTuned(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onRecordingStopped(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoResponse, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAdResponse(android.media.tv.AdResponse adResponse) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adResponse, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSessionCallback
            public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }
    }
}
