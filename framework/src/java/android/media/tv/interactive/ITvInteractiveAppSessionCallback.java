package android.media.tv.interactive;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppSessionCallback extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppSessionCallback";

    void onAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException;

    void onAdRequest(android.media.tv.AdRequest adRequest) throws android.os.RemoteException;

    void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException;

    void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException;

    void onCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException;

    void onRemoveBroadcastInfo(int i) throws android.os.RemoteException;

    void onRequestAvailableSpeeds() throws android.os.RemoteException;

    void onRequestCertificate(java.lang.String str, int i) throws android.os.RemoteException;

    void onRequestCurrentChannelLcn() throws android.os.RemoteException;

    void onRequestCurrentChannelUri() throws android.os.RemoteException;

    void onRequestCurrentTvInputId() throws android.os.RemoteException;

    void onRequestCurrentVideoBounds() throws android.os.RemoteException;

    void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void onRequestSelectedTrackInfo() throws android.os.RemoteException;

    void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException;

    void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) throws android.os.RemoteException;

    void onRequestStartRecording(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException;

    void onRequestStopRecording(java.lang.String str) throws android.os.RemoteException;

    void onRequestStreamVolume() throws android.os.RemoteException;

    void onRequestTimeShiftMode() throws android.os.RemoteException;

    void onRequestTrackInfoList() throws android.os.RemoteException;

    void onRequestTvRecordingInfo(java.lang.String str) throws android.os.RemoteException;

    void onRequestTvRecordingInfoList(int i) throws android.os.RemoteException;

    void onSessionCreated(android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession) throws android.os.RemoteException;

    void onSessionStateChanged(int i, int i2) throws android.os.RemoteException;

    void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) throws android.os.RemoteException;

    void onSetVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException;

    void onTeletextAppStateChanged(int i) throws android.os.RemoteException;

    void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    public static class Default implements android.media.tv.interactive.ITvInteractiveAppSessionCallback {
        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionCreated(android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRemoveBroadcastInfo(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSessionStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTeletextAppStateChanged(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentVideoBounds() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelUri() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentChannelLcn() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStreamVolume() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTrackInfoList() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCurrentTvInputId() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTimeShiftMode() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestAvailableSpeeds() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSelectedTrackInfo() throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStartRecording(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestStopRecording(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfo(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestTvRecordingInfoList(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onRequestCertificate(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
        public void onAdRequest(android.media.tv.AdRequest adRequest) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.interactive.ITvInteractiveAppSessionCallback {
        static final int TRANSACTION_onAdBufferReady = 8;
        static final int TRANSACTION_onAdRequest = 31;
        static final int TRANSACTION_onBiInteractiveAppCreated = 6;
        static final int TRANSACTION_onBroadcastInfoRequest = 3;
        static final int TRANSACTION_onCommandRequest = 9;
        static final int TRANSACTION_onLayoutSurface = 2;
        static final int TRANSACTION_onRemoveBroadcastInfo = 4;
        static final int TRANSACTION_onRequestAvailableSpeeds = 19;
        static final int TRANSACTION_onRequestCertificate = 30;
        static final int TRANSACTION_onRequestCurrentChannelLcn = 14;
        static final int TRANSACTION_onRequestCurrentChannelUri = 13;
        static final int TRANSACTION_onRequestCurrentTvInputId = 17;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 12;
        static final int TRANSACTION_onRequestScheduleRecording = 23;
        static final int TRANSACTION_onRequestScheduleRecording2 = 24;
        static final int TRANSACTION_onRequestSelectedTrackInfo = 20;
        static final int TRANSACTION_onRequestSigning = 28;
        static final int TRANSACTION_onRequestSigning2 = 29;
        static final int TRANSACTION_onRequestStartRecording = 21;
        static final int TRANSACTION_onRequestStopRecording = 22;
        static final int TRANSACTION_onRequestStreamVolume = 15;
        static final int TRANSACTION_onRequestTimeShiftMode = 18;
        static final int TRANSACTION_onRequestTrackInfoList = 16;
        static final int TRANSACTION_onRequestTvRecordingInfo = 26;
        static final int TRANSACTION_onRequestTvRecordingInfoList = 27;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionStateChanged = 5;
        static final int TRANSACTION_onSetTvRecordingInfo = 25;
        static final int TRANSACTION_onSetVideoBounds = 11;
        static final int TRANSACTION_onTeletextAppStateChanged = 7;
        static final int TRANSACTION_onTimeShiftCommandRequest = 10;

        public Stub() {
            attachInterface(this, android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
        }

        public static android.media.tv.interactive.ITvInteractiveAppSessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.interactive.ITvInteractiveAppSessionCallback)) {
                return (android.media.tv.interactive.ITvInteractiveAppSessionCallback) queryLocalInterface;
            }
            return new android.media.tv.interactive.ITvInteractiveAppSessionCallback.Stub.Proxy(iBinder);
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
                    return "onLayoutSurface";
                case 3:
                    return "onBroadcastInfoRequest";
                case 4:
                    return "onRemoveBroadcastInfo";
                case 5:
                    return "onSessionStateChanged";
                case 6:
                    return "onBiInteractiveAppCreated";
                case 7:
                    return "onTeletextAppStateChanged";
                case 8:
                    return "onAdBufferReady";
                case 9:
                    return "onCommandRequest";
                case 10:
                    return "onTimeShiftCommandRequest";
                case 11:
                    return "onSetVideoBounds";
                case 12:
                    return "onRequestCurrentVideoBounds";
                case 13:
                    return "onRequestCurrentChannelUri";
                case 14:
                    return "onRequestCurrentChannelLcn";
                case 15:
                    return "onRequestStreamVolume";
                case 16:
                    return "onRequestTrackInfoList";
                case 17:
                    return "onRequestCurrentTvInputId";
                case 18:
                    return "onRequestTimeShiftMode";
                case 19:
                    return "onRequestAvailableSpeeds";
                case 20:
                    return "onRequestSelectedTrackInfo";
                case 21:
                    return "onRequestStartRecording";
                case 22:
                    return "onRequestStopRecording";
                case 23:
                    return "onRequestScheduleRecording";
                case 24:
                    return "onRequestScheduleRecording2";
                case 25:
                    return "onSetTvRecordingInfo";
                case 26:
                    return "onRequestTvRecordingInfo";
                case 27:
                    return "onRequestTvRecordingInfoList";
                case 28:
                    return "onRequestSigning";
                case 29:
                    return "onRequestSigning2";
                case 30:
                    return "onRequestCertificate";
                case 31:
                    return "onAdRequest";
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
                parcel.enforceInterface(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.media.tv.interactive.ITvInteractiveAppSession asInterface = android.media.tv.interactive.ITvInteractiveAppSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSessionCreated(asInterface);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt, readInt2, readInt3, readInt4);
                    return true;
                case 3:
                    android.media.tv.BroadcastInfoRequest broadcastInfoRequest = (android.media.tv.BroadcastInfoRequest) parcel.readTypedObject(android.media.tv.BroadcastInfoRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoRequest(broadcastInfoRequest);
                    return true;
                case 4:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoveBroadcastInfo(readInt5);
                    return true;
                case 5:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStateChanged(readInt6, readInt7);
                    return true;
                case 6:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onBiInteractiveAppCreated(uri, readString);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTeletextAppStateChanged(readInt8);
                    return true;
                case 8:
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdBufferReady(adBuffer);
                    return true;
                case 9:
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCommandRequest(readString2, bundle);
                    return true;
                case 10:
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTimeShiftCommandRequest(readString3, bundle2);
                    return true;
                case 11:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetVideoBounds(rect);
                    return true;
                case 12:
                    onRequestCurrentVideoBounds();
                    return true;
                case 13:
                    onRequestCurrentChannelUri();
                    return true;
                case 14:
                    onRequestCurrentChannelLcn();
                    return true;
                case 15:
                    onRequestStreamVolume();
                    return true;
                case 16:
                    onRequestTrackInfoList();
                    return true;
                case 17:
                    onRequestCurrentTvInputId();
                    return true;
                case 18:
                    onRequestTimeShiftMode();
                    return true;
                case 19:
                    onRequestAvailableSpeeds();
                    return true;
                case 20:
                    onRequestSelectedTrackInfo();
                    return true;
                case 21:
                    java.lang.String readString4 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestStartRecording(readString4, uri2);
                    return true;
                case 22:
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRequestStopRecording(readString5);
                    return true;
                case 23:
                    java.lang.String readString6 = parcel.readString();
                    java.lang.String readString7 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording(readString6, readString7, uri3, uri4, bundle3);
                    return true;
                case 24:
                    java.lang.String readString8 = parcel.readString();
                    java.lang.String readString9 = parcel.readString();
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt9 = parcel.readInt();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording2(readString8, readString9, uri5, readLong, readLong2, readInt9, bundle4);
                    return true;
                case 25:
                    java.lang.String readString10 = parcel.readString();
                    android.media.tv.TvRecordingInfo tvRecordingInfo = (android.media.tv.TvRecordingInfo) parcel.readTypedObject(android.media.tv.TvRecordingInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetTvRecordingInfo(readString10, tvRecordingInfo);
                    return true;
                case 26:
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfo(readString11);
                    return true;
                case 27:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(readInt10);
                    return true;
                case 28:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString12, readString13, readString14, createByteArray);
                    return true;
                case 29:
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    onRequestSigning2(readString15, readString16, readString17, readInt11, createByteArray2);
                    return true;
                case 30:
                    java.lang.String readString18 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCertificate(readString18, readInt12);
                    return true;
                case 31:
                    android.media.tv.AdRequest adRequest = (android.media.tv.AdRequest) parcel.readTypedObject(android.media.tv.AdRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAdRequest(adRequest);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.interactive.ITvInteractiveAppSessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionCreated(android.media.tv.interactive.ITvInteractiveAppSession iTvInteractiveAppSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppSession);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onLayoutSurface(int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRemoveBroadcastInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSessionStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTeletextAppStateChanged(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetVideoBounds(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentVideoBounds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelUri() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentChannelLcn() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStreamVolume() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTrackInfoList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCurrentTvInputId() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTimeShiftMode() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestAvailableSpeeds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSelectedTrackInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStartRecording(java.lang.String str, android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestStopRecording(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfo(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestTvRecordingInfoList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onRequestCertificate(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppSessionCallback
            public void onAdRequest(android.media.tv.AdRequest adRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppSessionCallback.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 30;
        }
    }
}
