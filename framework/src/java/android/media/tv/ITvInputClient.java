package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputClient extends android.os.IInterface {
    void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException;

    void onAdResponse(android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException;

    void onAitInfoUpdated(android.media.tv.AitInfo aitInfo, int i) throws android.os.RemoteException;

    void onAudioPresentationSelected(int i, int i2, int i3) throws android.os.RemoteException;

    void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list, int i) throws android.os.RemoteException;

    void onAvailableSpeeds(float[] fArr, int i) throws android.os.RemoteException;

    void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException;

    void onChannelRetuned(android.net.Uri uri, int i) throws android.os.RemoteException;

    void onContentAllowed(int i) throws android.os.RemoteException;

    void onContentBlocked(java.lang.String str, int i) throws android.os.RemoteException;

    void onCueingMessageAvailability(boolean z, int i) throws android.os.RemoteException;

    void onError(int i, int i2) throws android.os.RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException;

    void onRecordingStopped(android.net.Uri uri, int i) throws android.os.RemoteException;

    void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException;

    void onSessionEvent(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void onSessionReleased(int i) throws android.os.RemoteException;

    void onSignalStrength(int i, int i2) throws android.os.RemoteException;

    void onTimeShiftCurrentPositionChanged(long j, int i) throws android.os.RemoteException;

    void onTimeShiftMode(int i, int i2) throws android.os.RemoteException;

    void onTimeShiftStartPositionChanged(long j, int i) throws android.os.RemoteException;

    void onTimeShiftStatusChanged(int i, int i2) throws android.os.RemoteException;

    void onTrackSelected(int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException;

    void onTuned(android.net.Uri uri, int i) throws android.os.RemoteException;

    void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void onTvMessage(int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    void onVideoAvailable(int i) throws android.os.RemoteException;

    void onVideoFreezeUpdated(boolean z, int i) throws android.os.RemoteException;

    void onVideoUnavailable(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputClient {
        @Override // android.media.tv.ITvInputClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionReleased(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSessionEvent(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onChannelRetuned(android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAudioPresentationSelected(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTrackSelected(int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoAvailable(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoUnavailable(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onVideoFreezeUpdated(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentAllowed(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onContentBlocked(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStatusChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftStartPositionChanged(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftCurrentPositionChanged(long j, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onSignalStrength(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onCueingMessageAvailability(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTimeShiftMode(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAvailableSpeeds(float[] fArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvMessage(int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTuned(android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onRecordingStopped(android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onError(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdResponse(android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputClient
        public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputClient {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputClient";
        static final int TRANSACTION_onAdBufferConsumed = 29;
        static final int TRANSACTION_onAdResponse = 28;
        static final int TRANSACTION_onAitInfoUpdated = 18;
        static final int TRANSACTION_onAudioPresentationSelected = 6;
        static final int TRANSACTION_onAudioPresentationsChanged = 5;
        static final int TRANSACTION_onAvailableSpeeds = 22;
        static final int TRANSACTION_onBroadcastInfoResponse = 27;
        static final int TRANSACTION_onChannelRetuned = 4;
        static final int TRANSACTION_onContentAllowed = 12;
        static final int TRANSACTION_onContentBlocked = 13;
        static final int TRANSACTION_onCueingMessageAvailability = 20;
        static final int TRANSACTION_onError = 26;
        static final int TRANSACTION_onLayoutSurface = 14;
        static final int TRANSACTION_onRecordingStopped = 25;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionEvent = 3;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSignalStrength = 19;
        static final int TRANSACTION_onTimeShiftCurrentPositionChanged = 17;
        static final int TRANSACTION_onTimeShiftMode = 21;
        static final int TRANSACTION_onTimeShiftStartPositionChanged = 16;
        static final int TRANSACTION_onTimeShiftStatusChanged = 15;
        static final int TRANSACTION_onTrackSelected = 8;
        static final int TRANSACTION_onTracksChanged = 7;
        static final int TRANSACTION_onTuned = 24;
        static final int TRANSACTION_onTvInputSessionData = 30;
        static final int TRANSACTION_onTvMessage = 23;
        static final int TRANSACTION_onVideoAvailable = 9;
        static final int TRANSACTION_onVideoFreezeUpdated = 11;
        static final int TRANSACTION_onVideoUnavailable = 10;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputClient)) {
                return (android.media.tv.ITvInputClient) queryLocalInterface;
            }
            return new android.media.tv.ITvInputClient.Stub.Proxy(iBinder);
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
                    return "onSessionReleased";
                case 3:
                    return "onSessionEvent";
                case 4:
                    return "onChannelRetuned";
                case 5:
                    return "onAudioPresentationsChanged";
                case 6:
                    return "onAudioPresentationSelected";
                case 7:
                    return "onTracksChanged";
                case 8:
                    return "onTrackSelected";
                case 9:
                    return "onVideoAvailable";
                case 10:
                    return "onVideoUnavailable";
                case 11:
                    return "onVideoFreezeUpdated";
                case 12:
                    return "onContentAllowed";
                case 13:
                    return "onContentBlocked";
                case 14:
                    return "onLayoutSurface";
                case 15:
                    return "onTimeShiftStatusChanged";
                case 16:
                    return "onTimeShiftStartPositionChanged";
                case 17:
                    return "onTimeShiftCurrentPositionChanged";
                case 18:
                    return "onAitInfoUpdated";
                case 19:
                    return "onSignalStrength";
                case 20:
                    return "onCueingMessageAvailability";
                case 21:
                    return "onTimeShiftMode";
                case 22:
                    return "onAvailableSpeeds";
                case 23:
                    return "onTvMessage";
                case 24:
                    return "onTuned";
                case 25:
                    return "onRecordingStopped";
                case 26:
                    return "onError";
                case 27:
                    return "onBroadcastInfoResponse";
                case 28:
                    return "onAdResponse";
                case 29:
                    return "onAdBufferConsumed";
                case 30:
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
                    java.lang.String readString = parcel.readString();
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.view.InputChannel inputChannel = (android.view.InputChannel) parcel.readTypedObject(android.view.InputChannel.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionCreated(readString, readStrongBinder, inputChannel, readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionReleased(readInt2);
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionEvent(readString2, bundle, readInt3);
                    return true;
                case 4:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onChannelRetuned(uri, readInt4);
                    return true;
                case 5:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.AudioPresentation.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationsChanged(createTypedArrayList, readInt5);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAudioPresentationSelected(readInt6, readInt7, readInt8);
                    return true;
                case 7:
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTracksChanged(createTypedArrayList2, readInt9);
                    return true;
                case 8:
                    int readInt10 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTrackSelected(readInt10, readString3, readInt11);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoAvailable(readInt12);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoUnavailable(readInt13, readInt14);
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoFreezeUpdated(readBoolean, readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContentAllowed(readInt16);
                    return true;
                case 13:
                    java.lang.String readString4 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onContentBlocked(readString4, readInt17);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt18, readInt19, readInt20, readInt21, readInt22);
                    return true;
                case 15:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStatusChanged(readInt23, readInt24);
                    return true;
                case 16:
                    long readLong = parcel.readLong();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftStartPositionChanged(readLong, readInt25);
                    return true;
                case 17:
                    long readLong2 = parcel.readLong();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCurrentPositionChanged(readLong2, readInt26);
                    return true;
                case 18:
                    android.media.tv.AitInfo aitInfo = (android.media.tv.AitInfo) parcel.readTypedObject(android.media.tv.AitInfo.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAitInfoUpdated(aitInfo, readInt27);
                    return true;
                case 19:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSignalStrength(readInt28, readInt29);
                    return true;
                case 20:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCueingMessageAvailability(readBoolean2, readInt30);
                    return true;
                case 21:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftMode(readInt31, readInt32);
                    return true;
                case 22:
                    float[] createFloatArray = parcel.createFloatArray();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAvailableSpeeds(createFloatArray, readInt33);
                    return true;
                case 23:
                    int readInt34 = parcel.readInt();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvMessage(readInt34, bundle2, readInt35);
                    return true;
                case 24:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTuned(uri2, readInt36);
                    return true;
                case 25:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRecordingStopped(uri3, readInt37);
                    return true;
                case 26:
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onError(readInt38, readInt39);
                    return true;
                case 27:
                    android.media.tv.BroadcastInfoResponse broadcastInfoResponse = (android.media.tv.BroadcastInfoResponse) parcel.readTypedObject(android.media.tv.BroadcastInfoResponse.CREATOR);
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoResponse(broadcastInfoResponse, readInt40);
                    return true;
                case 28:
                    android.media.tv.AdResponse adResponse = (android.media.tv.AdResponse) parcel.readTypedObject(android.media.tv.AdResponse.CREATOR);
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdResponse(adResponse, readInt41);
                    return true;
                case 29:
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdBufferConsumed(adBuffer, readInt42);
                    return true;
                case 30:
                    java.lang.String readString5 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTvInputSessionData(readString5, bundle3, readInt43);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputClient.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionReleased(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSessionEvent(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onChannelRetuned(android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationsChanged(java.util.List<android.media.AudioPresentation> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAudioPresentationSelected(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTracksChanged(java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTrackSelected(int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoAvailable(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoUnavailable(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onVideoFreezeUpdated(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentAllowed(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onContentBlocked(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStatusChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftStartPositionChanged(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftCurrentPositionChanged(long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAitInfoUpdated(android.media.tv.AitInfo aitInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(aitInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onSignalStrength(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onCueingMessageAvailability(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTimeShiftMode(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAvailableSpeeds(float[] fArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeFloatArray(fArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvMessage(int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTuned(android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onRecordingStopped(android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onError(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onBroadcastInfoResponse(android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoResponse, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdResponse(android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adResponse, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onAdBufferConsumed(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputClient
            public void onTvInputSessionData(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputClient.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 29;
        }
    }
}
