package android.media.tv.interactive;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppClient extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppClient";

    void onAdBufferReady(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException;

    void onAdRequest(android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException;

    void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str, int i) throws android.os.RemoteException;

    void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException;

    void onCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException;

    void onRemoveBroadcastInfo(int i, int i2) throws android.os.RemoteException;

    void onRequestAvailableSpeeds(int i) throws android.os.RemoteException;

    void onRequestCertificate(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onRequestCurrentChannelLcn(int i) throws android.os.RemoteException;

    void onRequestCurrentChannelUri(int i) throws android.os.RemoteException;

    void onRequestCurrentTvInputId(int i) throws android.os.RemoteException;

    void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException;

    void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    void onRequestSelectedTrackInfo(int i) throws android.os.RemoteException;

    void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException;

    void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr, int i2) throws android.os.RemoteException;

    void onRequestStartRecording(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException;

    void onRequestStopRecording(java.lang.String str, int i) throws android.os.RemoteException;

    void onRequestStreamVolume(int i) throws android.os.RemoteException;

    void onRequestTimeShiftMode(int i) throws android.os.RemoteException;

    void onRequestTrackInfoList(int i) throws android.os.RemoteException;

    void onRequestTvRecordingInfo(java.lang.String str, int i) throws android.os.RemoteException;

    void onRequestTvRecordingInfoList(int i, int i2) throws android.os.RemoteException;

    void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException;

    void onSessionReleased(int i) throws android.os.RemoteException;

    void onSessionStateChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException;

    void onSetVideoBounds(android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void onTeletextAppStateChanged(int i, int i2) throws android.os.RemoteException;

    void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.interactive.ITvInteractiveAppClient {
        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionReleased(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRemoveBroadcastInfo(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSessionStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTeletextAppStateChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdBufferReady(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetVideoBounds(android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelUri(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentChannelLcn(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStreamVolume(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTrackInfoList(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSelectedTrackInfo(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCurrentTvInputId(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTimeShiftMode(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestAvailableSpeeds(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStartRecording(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestStopRecording(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfo(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestTvRecordingInfoList(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onRequestCertificate(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppClient
        public void onAdRequest(android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.interactive.ITvInteractiveAppClient {
        static final int TRANSACTION_onAdBufferReady = 9;
        static final int TRANSACTION_onAdRequest = 32;
        static final int TRANSACTION_onBiInteractiveAppCreated = 7;
        static final int TRANSACTION_onBroadcastInfoRequest = 4;
        static final int TRANSACTION_onCommandRequest = 10;
        static final int TRANSACTION_onLayoutSurface = 3;
        static final int TRANSACTION_onRemoveBroadcastInfo = 5;
        static final int TRANSACTION_onRequestAvailableSpeeds = 21;
        static final int TRANSACTION_onRequestCertificate = 31;
        static final int TRANSACTION_onRequestCurrentChannelLcn = 15;
        static final int TRANSACTION_onRequestCurrentChannelUri = 14;
        static final int TRANSACTION_onRequestCurrentTvInputId = 19;
        static final int TRANSACTION_onRequestCurrentVideoBounds = 13;
        static final int TRANSACTION_onRequestScheduleRecording = 24;
        static final int TRANSACTION_onRequestScheduleRecording2 = 25;
        static final int TRANSACTION_onRequestSelectedTrackInfo = 18;
        static final int TRANSACTION_onRequestSigning = 29;
        static final int TRANSACTION_onRequestSigning2 = 30;
        static final int TRANSACTION_onRequestStartRecording = 22;
        static final int TRANSACTION_onRequestStopRecording = 23;
        static final int TRANSACTION_onRequestStreamVolume = 16;
        static final int TRANSACTION_onRequestTimeShiftMode = 20;
        static final int TRANSACTION_onRequestTrackInfoList = 17;
        static final int TRANSACTION_onRequestTvRecordingInfo = 27;
        static final int TRANSACTION_onRequestTvRecordingInfoList = 28;
        static final int TRANSACTION_onSessionCreated = 1;
        static final int TRANSACTION_onSessionReleased = 2;
        static final int TRANSACTION_onSessionStateChanged = 6;
        static final int TRANSACTION_onSetTvRecordingInfo = 26;
        static final int TRANSACTION_onSetVideoBounds = 12;
        static final int TRANSACTION_onTeletextAppStateChanged = 8;
        static final int TRANSACTION_onTimeShiftCommandRequest = 11;

        public Stub() {
            attachInterface(this, android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
        }

        public static android.media.tv.interactive.ITvInteractiveAppClient asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.interactive.ITvInteractiveAppClient)) {
                return (android.media.tv.interactive.ITvInteractiveAppClient) queryLocalInterface;
            }
            return new android.media.tv.interactive.ITvInteractiveAppClient.Stub.Proxy(iBinder);
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
                    return "onLayoutSurface";
                case 4:
                    return "onBroadcastInfoRequest";
                case 5:
                    return "onRemoveBroadcastInfo";
                case 6:
                    return "onSessionStateChanged";
                case 7:
                    return "onBiInteractiveAppCreated";
                case 8:
                    return "onTeletextAppStateChanged";
                case 9:
                    return "onAdBufferReady";
                case 10:
                    return "onCommandRequest";
                case 11:
                    return "onTimeShiftCommandRequest";
                case 12:
                    return "onSetVideoBounds";
                case 13:
                    return "onRequestCurrentVideoBounds";
                case 14:
                    return "onRequestCurrentChannelUri";
                case 15:
                    return "onRequestCurrentChannelLcn";
                case 16:
                    return "onRequestStreamVolume";
                case 17:
                    return "onRequestTrackInfoList";
                case 18:
                    return "onRequestSelectedTrackInfo";
                case 19:
                    return "onRequestCurrentTvInputId";
                case 20:
                    return "onRequestTimeShiftMode";
                case 21:
                    return "onRequestAvailableSpeeds";
                case 22:
                    return "onRequestStartRecording";
                case 23:
                    return "onRequestStopRecording";
                case 24:
                    return "onRequestScheduleRecording";
                case 25:
                    return "onRequestScheduleRecording2";
                case 26:
                    return "onSetTvRecordingInfo";
                case 27:
                    return "onRequestTvRecordingInfo";
                case 28:
                    return "onRequestTvRecordingInfoList";
                case 29:
                    return "onRequestSigning";
                case 30:
                    return "onRequestSigning2";
                case 31:
                    return "onRequestCertificate";
                case 32:
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
                parcel.enforceInterface(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
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
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onLayoutSurface(readInt3, readInt4, readInt5, readInt6, readInt7);
                    return true;
                case 4:
                    android.media.tv.BroadcastInfoRequest broadcastInfoRequest = (android.media.tv.BroadcastInfoRequest) parcel.readTypedObject(android.media.tv.BroadcastInfoRequest.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBroadcastInfoRequest(broadcastInfoRequest, readInt8);
                    return true;
                case 5:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRemoveBroadcastInfo(readInt9, readInt10);
                    return true;
                case 6:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSessionStateChanged(readInt11, readInt12, readInt13);
                    return true;
                case 7:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    java.lang.String readString2 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBiInteractiveAppCreated(uri, readString2, readInt14);
                    return true;
                case 8:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTeletextAppStateChanged(readInt15, readInt16);
                    return true;
                case 9:
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdBufferReady(adBuffer, readInt17);
                    return true;
                case 10:
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCommandRequest(readString3, bundle, readInt18);
                    return true;
                case 11:
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onTimeShiftCommandRequest(readString4, bundle2, readInt19);
                    return true;
                case 12:
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVideoBounds(rect, readInt20);
                    return true;
                case 13:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentVideoBounds(readInt21);
                    return true;
                case 14:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelUri(readInt22);
                    return true;
                case 15:
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentChannelLcn(readInt23);
                    return true;
                case 16:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStreamVolume(readInt24);
                    return true;
                case 17:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTrackInfoList(readInt25);
                    return true;
                case 18:
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSelectedTrackInfo(readInt26);
                    return true;
                case 19:
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCurrentTvInputId(readInt27);
                    return true;
                case 20:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTimeShiftMode(readInt28);
                    return true;
                case 21:
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestAvailableSpeeds(readInt29);
                    return true;
                case 22:
                    java.lang.String readString5 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStartRecording(readString5, uri2, readInt30);
                    return true;
                case 23:
                    java.lang.String readString6 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestStopRecording(readString6, readInt31);
                    return true;
                case 24:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording(readString7, readString8, uri3, uri4, bundle3, readInt32);
                    return true;
                case 25:
                    java.lang.String readString9 = parcel.readString();
                    java.lang.String readString10 = parcel.readString();
                    android.net.Uri uri5 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt33 = parcel.readInt();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestScheduleRecording2(readString9, readString10, uri5, readLong, readLong2, readInt33, bundle4, readInt34);
                    return true;
                case 26:
                    java.lang.String readString11 = parcel.readString();
                    android.media.tv.TvRecordingInfo tvRecordingInfo = (android.media.tv.TvRecordingInfo) parcel.readTypedObject(android.media.tv.TvRecordingInfo.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetTvRecordingInfo(readString11, tvRecordingInfo, readInt35);
                    return true;
                case 27:
                    java.lang.String readString12 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfo(readString12, readInt36);
                    return true;
                case 28:
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestTvRecordingInfoList(readInt37, readInt38);
                    return true;
                case 29:
                    java.lang.String readString13 = parcel.readString();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning(readString13, readString14, readString15, createByteArray, readInt39);
                    return true;
                case 30:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    java.lang.String readString18 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestSigning2(readString16, readString17, readString18, readInt40, createByteArray2, readInt41);
                    return true;
                case 31:
                    java.lang.String readString19 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRequestCertificate(readString19, readInt42, readInt43);
                    return true;
                case 32:
                    android.media.tv.AdRequest adRequest = (android.media.tv.AdRequest) parcel.readTypedObject(android.media.tv.AdRequest.CREATOR);
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdRequest(adRequest, readInt44);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.interactive.ITvInteractiveAppClient {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionCreated(java.lang.String str, android.os.IBinder iBinder, android.view.InputChannel inputChannel, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(inputChannel, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionReleased(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onLayoutSurface(int i, int i2, int i3, int i4, int i5) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBroadcastInfoRequest(android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRemoveBroadcastInfo(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSessionStateChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onBiInteractiveAppCreated(android.net.Uri uri, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTeletextAppStateChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdBufferReady(android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onTimeShiftCommandRequest(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetVideoBounds(android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentVideoBounds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelUri(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentChannelLcn(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStreamVolume(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTrackInfoList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSelectedTrackInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCurrentTvInputId(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTimeShiftMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestAvailableSpeeds(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStartRecording(java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestStopRecording(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording(java.lang.String str, java.lang.String str2, android.net.Uri uri, android.net.Uri uri2, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestScheduleRecording2(java.lang.String str, java.lang.String str2, android.net.Uri uri, long j, long j2, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onSetTvRecordingInfo(java.lang.String str, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestTvRecordingInfoList(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestSigning2(java.lang.String str, java.lang.String str2, java.lang.String str3, int i, byte[] bArr, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onRequestCertificate(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppClient
            public void onAdRequest(android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppClient.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 31;
        }
    }
}
