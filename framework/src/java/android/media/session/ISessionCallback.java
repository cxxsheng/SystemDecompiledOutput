package android.media.session;

/* loaded from: classes2.dex */
public interface ISessionCallback extends android.os.IInterface {
    void onAdjustVolume(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void onCommand(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onCustomAction(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onFastForward(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onMediaButton(java.lang.String str, int i, int i2, android.content.Intent intent, int i3, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void onMediaButtonFromController(java.lang.String str, int i, int i2, android.content.Intent intent) throws android.os.RemoteException;

    void onNext(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onPause(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onPlay(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onPlayFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPlayFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPlayFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPrepare(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onPrepareFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPrepareFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPrepareFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void onPrevious(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onRate(java.lang.String str, int i, int i2, android.media.Rating rating) throws android.os.RemoteException;

    void onRewind(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void onSeekTo(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException;

    void onSetPlaybackSpeed(java.lang.String str, int i, int i2, float f) throws android.os.RemoteException;

    void onSetVolumeTo(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void onSkipToTrack(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException;

    void onStop(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.media.session.ISessionCallback {
        @Override // android.media.session.ISessionCallback
        public void onCommand(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButton(java.lang.String str, int i, int i2, android.content.Intent intent, int i3, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onMediaButtonFromController(java.lang.String str, int i, int i2, android.content.Intent intent) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepare(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrepareFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlay(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPlayFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSkipToTrack(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPause(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onStop(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onNext(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onPrevious(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onFastForward(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onRewind(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSeekTo(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onRate(java.lang.String str, int i, int i2, android.media.Rating rating) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSetPlaybackSpeed(java.lang.String str, int i, int i2, float f) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onCustomAction(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onAdjustVolume(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionCallback
        public void onSetVolumeTo(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.ISessionCallback {
        public static final java.lang.String DESCRIPTOR = "android.media.session.ISessionCallback";
        static final int TRANSACTION_onAdjustVolume = 23;
        static final int TRANSACTION_onCommand = 1;
        static final int TRANSACTION_onCustomAction = 22;
        static final int TRANSACTION_onFastForward = 17;
        static final int TRANSACTION_onMediaButton = 2;
        static final int TRANSACTION_onMediaButtonFromController = 3;
        static final int TRANSACTION_onNext = 15;
        static final int TRANSACTION_onPause = 13;
        static final int TRANSACTION_onPlay = 8;
        static final int TRANSACTION_onPlayFromMediaId = 9;
        static final int TRANSACTION_onPlayFromSearch = 10;
        static final int TRANSACTION_onPlayFromUri = 11;
        static final int TRANSACTION_onPrepare = 4;
        static final int TRANSACTION_onPrepareFromMediaId = 5;
        static final int TRANSACTION_onPrepareFromSearch = 6;
        static final int TRANSACTION_onPrepareFromUri = 7;
        static final int TRANSACTION_onPrevious = 16;
        static final int TRANSACTION_onRate = 20;
        static final int TRANSACTION_onRewind = 18;
        static final int TRANSACTION_onSeekTo = 19;
        static final int TRANSACTION_onSetPlaybackSpeed = 21;
        static final int TRANSACTION_onSetVolumeTo = 24;
        static final int TRANSACTION_onSkipToTrack = 12;
        static final int TRANSACTION_onStop = 14;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.ISessionCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.ISessionCallback)) {
                return (android.media.session.ISessionCallback) queryLocalInterface;
            }
            return new android.media.session.ISessionCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onCommand";
                case 2:
                    return "onMediaButton";
                case 3:
                    return "onMediaButtonFromController";
                case 4:
                    return "onPrepare";
                case 5:
                    return "onPrepareFromMediaId";
                case 6:
                    return "onPrepareFromSearch";
                case 7:
                    return "onPrepareFromUri";
                case 8:
                    return "onPlay";
                case 9:
                    return "onPlayFromMediaId";
                case 10:
                    return "onPlayFromSearch";
                case 11:
                    return "onPlayFromUri";
                case 12:
                    return "onSkipToTrack";
                case 13:
                    return "onPause";
                case 14:
                    return "onStop";
                case 15:
                    return "onNext";
                case 16:
                    return "onPrevious";
                case 17:
                    return "onFastForward";
                case 18:
                    return "onRewind";
                case 19:
                    return "onSeekTo";
                case 20:
                    return "onRate";
                case 21:
                    return "onSetPlaybackSpeed";
                case 22:
                    return "onCustomAction";
                case 23:
                    return "onAdjustVolume";
                case 24:
                    return "onSetVolumeTo";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCommand(readString, readInt, readInt2, readString2, bundle, resultReceiver);
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt5 = parcel.readInt();
                    android.os.ResultReceiver resultReceiver2 = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaButton(readString3, readInt3, readInt4, intent, readInt5, resultReceiver2);
                    return true;
                case 3:
                    java.lang.String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.content.Intent intent2 = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMediaButtonFromController(readString4, readInt6, readInt7, intent2);
                    return true;
                case 4:
                    java.lang.String readString5 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrepare(readString5, readInt8, readInt9);
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromMediaId(readString6, readInt10, readInt11, readString7, bundle2);
                    return true;
                case 6:
                    java.lang.String readString8 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    java.lang.String readString9 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromSearch(readString8, readInt12, readInt13, readString9, bundle3);
                    return true;
                case 7:
                    java.lang.String readString10 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPrepareFromUri(readString10, readInt14, readInt15, uri, bundle4);
                    return true;
                case 8:
                    java.lang.String readString11 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPlay(readString11, readInt16, readInt17);
                    return true;
                case 9:
                    java.lang.String readString12 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromMediaId(readString12, readInt18, readInt19, readString13, bundle5);
                    return true;
                case 10:
                    java.lang.String readString14 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromSearch(readString14, readInt20, readInt21, readString15, bundle6);
                    return true;
                case 11:
                    java.lang.String readString16 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPlayFromUri(readString16, readInt22, readInt23, uri2, bundle7);
                    return true;
                case 12:
                    java.lang.String readString17 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onSkipToTrack(readString17, readInt24, readInt25, readLong);
                    return true;
                case 13:
                    java.lang.String readString18 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPause(readString18, readInt26, readInt27);
                    return true;
                case 14:
                    java.lang.String readString19 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onStop(readString19, readInt28, readInt29);
                    return true;
                case 15:
                    java.lang.String readString20 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onNext(readString20, readInt30, readInt31);
                    return true;
                case 16:
                    java.lang.String readString21 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onPrevious(readString21, readInt32, readInt33);
                    return true;
                case 17:
                    java.lang.String readString22 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onFastForward(readString22, readInt34, readInt35);
                    return true;
                case 18:
                    java.lang.String readString23 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onRewind(readString23, readInt36, readInt37);
                    return true;
                case 19:
                    java.lang.String readString24 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    int readInt39 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    onSeekTo(readString24, readInt38, readInt39, readLong2);
                    return true;
                case 20:
                    java.lang.String readString25 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    android.media.Rating rating = (android.media.Rating) parcel.readTypedObject(android.media.Rating.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRate(readString25, readInt40, readInt41, rating);
                    return true;
                case 21:
                    java.lang.String readString26 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    onSetPlaybackSpeed(readString26, readInt42, readInt43, readFloat);
                    return true;
                case 22:
                    java.lang.String readString27 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    int readInt45 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCustomAction(readString27, readInt44, readInt45, readString28, bundle8);
                    return true;
                case 23:
                    java.lang.String readString29 = parcel.readString();
                    int readInt46 = parcel.readInt();
                    int readInt47 = parcel.readInt();
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAdjustVolume(readString29, readInt46, readInt47, readInt48);
                    return true;
                case 24:
                    java.lang.String readString30 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    int readInt50 = parcel.readInt();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSetVolumeTo(readString30, readInt49, readInt50, readInt51);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.ISessionCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.ISessionCallback.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionCallback
            public void onCommand(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onMediaButton(java.lang.String str, int i, int i2, android.content.Intent intent, int i3, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onMediaButtonFromController(java.lang.String str, int i, int i2, android.content.Intent intent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepare(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrepareFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlay(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromMediaId(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromSearch(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPlayFromUri(java.lang.String str, int i, int i2, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSkipToTrack(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPause(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onStop(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onNext(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onPrevious(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onFastForward(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onRewind(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSeekTo(java.lang.String str, int i, int i2, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onRate(java.lang.String str, int i, int i2, android.media.Rating rating) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(rating, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSetPlaybackSpeed(java.lang.String str, int i, int i2, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onCustomAction(java.lang.String str, int i, int i2, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onAdjustVolume(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionCallback
            public void onSetVolumeTo(java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionCallback.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }
    }
}
