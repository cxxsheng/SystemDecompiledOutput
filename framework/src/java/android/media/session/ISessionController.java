package android.media.session;

/* loaded from: classes2.dex */
public interface ISessionController extends android.os.IInterface {
    void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void fastForward(java.lang.String str) throws android.os.RemoteException;

    android.os.Bundle getExtras() throws android.os.RemoteException;

    long getFlags() throws android.os.RemoteException;

    android.app.PendingIntent getLaunchPendingIntent() throws android.os.RemoteException;

    android.media.MediaMetadata getMetadata() throws android.os.RemoteException;

    java.lang.String getPackageName() throws android.os.RemoteException;

    android.media.session.PlaybackState getPlaybackState() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getQueue() throws android.os.RemoteException;

    java.lang.CharSequence getQueueTitle() throws android.os.RemoteException;

    int getRatingType() throws android.os.RemoteException;

    android.os.Bundle getSessionInfo() throws android.os.RemoteException;

    java.lang.String getTag() throws android.os.RemoteException;

    android.media.session.MediaController.PlaybackInfo getVolumeAttributes() throws android.os.RemoteException;

    void next(java.lang.String str) throws android.os.RemoteException;

    void pause(java.lang.String str) throws android.os.RemoteException;

    void play(java.lang.String str) throws android.os.RemoteException;

    void playFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void playFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void playFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void prepare(java.lang.String str) throws android.os.RemoteException;

    void prepareFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void prepareFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    void prepareFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void previous(java.lang.String str) throws android.os.RemoteException;

    void rate(java.lang.String str, android.media.Rating rating) throws android.os.RemoteException;

    void registerCallback(java.lang.String str, android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException;

    void rewind(java.lang.String str) throws android.os.RemoteException;

    void seekTo(java.lang.String str, long j) throws android.os.RemoteException;

    void sendCommand(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException;

    void sendCustomAction(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException;

    boolean sendMediaButton(java.lang.String str, android.view.KeyEvent keyEvent) throws android.os.RemoteException;

    void setPlaybackSpeed(java.lang.String str, float f) throws android.os.RemoteException;

    void setVolumeTo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException;

    void skipToQueueItem(java.lang.String str, long j) throws android.os.RemoteException;

    void stop(java.lang.String str) throws android.os.RemoteException;

    void unregisterCallback(android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException;

    public static class Default implements android.media.session.ISessionController {
        @Override // android.media.session.ISessionController
        public void sendCommand(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public boolean sendMediaButton(java.lang.String str, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.session.ISessionController
        public void registerCallback(java.lang.String str, android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void unregisterCallback(android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public java.lang.String getPackageName() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public java.lang.String getTag() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public android.os.Bundle getSessionInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public android.app.PendingIntent getLaunchPendingIntent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public long getFlags() throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.media.session.ISessionController
        public android.media.session.MediaController.PlaybackInfo getVolumeAttributes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void setVolumeTo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepare(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void prepareFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void play(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void playFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void skipToQueueItem(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void pause(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void stop(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void next(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void previous(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void fastForward(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void rewind(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void seekTo(java.lang.String str, long j) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void rate(java.lang.String str, android.media.Rating rating) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void setPlaybackSpeed(java.lang.String str, float f) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public void sendCustomAction(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.session.ISessionController
        public android.media.MediaMetadata getMetadata() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public android.media.session.PlaybackState getPlaybackState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public android.content.pm.ParceledListSlice getQueue() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public java.lang.CharSequence getQueueTitle() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public android.os.Bundle getExtras() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.session.ISessionController
        public int getRatingType() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.session.ISessionController {
        public static final java.lang.String DESCRIPTOR = "android.media.session.ISessionController";
        static final int TRANSACTION_adjustVolume = 11;
        static final int TRANSACTION_fastForward = 26;
        static final int TRANSACTION_getExtras = 36;
        static final int TRANSACTION_getFlags = 9;
        static final int TRANSACTION_getLaunchPendingIntent = 8;
        static final int TRANSACTION_getMetadata = 32;
        static final int TRANSACTION_getPackageName = 5;
        static final int TRANSACTION_getPlaybackState = 33;
        static final int TRANSACTION_getQueue = 34;
        static final int TRANSACTION_getQueueTitle = 35;
        static final int TRANSACTION_getRatingType = 37;
        static final int TRANSACTION_getSessionInfo = 7;
        static final int TRANSACTION_getTag = 6;
        static final int TRANSACTION_getVolumeAttributes = 10;
        static final int TRANSACTION_next = 24;
        static final int TRANSACTION_pause = 22;
        static final int TRANSACTION_play = 17;
        static final int TRANSACTION_playFromMediaId = 18;
        static final int TRANSACTION_playFromSearch = 19;
        static final int TRANSACTION_playFromUri = 20;
        static final int TRANSACTION_prepare = 13;
        static final int TRANSACTION_prepareFromMediaId = 14;
        static final int TRANSACTION_prepareFromSearch = 15;
        static final int TRANSACTION_prepareFromUri = 16;
        static final int TRANSACTION_previous = 25;
        static final int TRANSACTION_rate = 29;
        static final int TRANSACTION_registerCallback = 3;
        static final int TRANSACTION_rewind = 27;
        static final int TRANSACTION_seekTo = 28;
        static final int TRANSACTION_sendCommand = 1;
        static final int TRANSACTION_sendCustomAction = 31;
        static final int TRANSACTION_sendMediaButton = 2;
        static final int TRANSACTION_setPlaybackSpeed = 30;
        static final int TRANSACTION_setVolumeTo = 12;
        static final int TRANSACTION_skipToQueueItem = 21;
        static final int TRANSACTION_stop = 23;
        static final int TRANSACTION_unregisterCallback = 4;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.session.ISessionController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.session.ISessionController)) {
                return (android.media.session.ISessionController) queryLocalInterface;
            }
            return new android.media.session.ISessionController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendCommand";
                case 2:
                    return "sendMediaButton";
                case 3:
                    return "registerCallback";
                case 4:
                    return "unregisterCallback";
                case 5:
                    return "getPackageName";
                case 6:
                    return "getTag";
                case 7:
                    return "getSessionInfo";
                case 8:
                    return "getLaunchPendingIntent";
                case 9:
                    return "getFlags";
                case 10:
                    return "getVolumeAttributes";
                case 11:
                    return "adjustVolume";
                case 12:
                    return "setVolumeTo";
                case 13:
                    return "prepare";
                case 14:
                    return "prepareFromMediaId";
                case 15:
                    return "prepareFromSearch";
                case 16:
                    return "prepareFromUri";
                case 17:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PLAY;
                case 18:
                    return "playFromMediaId";
                case 19:
                    return "playFromSearch";
                case 20:
                    return "playFromUri";
                case 21:
                    return "skipToQueueItem";
                case 22:
                    return android.media.tv.interactive.TvInteractiveAppService.TIME_SHIFT_COMMAND_TYPE_PAUSE;
                case 23:
                    return "stop";
                case 24:
                    return "next";
                case 25:
                    return "previous";
                case 26:
                    return "fastForward";
                case 27:
                    return "rewind";
                case 28:
                    return "seekTo";
                case 29:
                    return android.speech.tts.TextToSpeech.Engine.KEY_PARAM_RATE;
                case 30:
                    return "setPlaybackSpeed";
                case 31:
                    return "sendCustomAction";
                case 32:
                    return "getMetadata";
                case 33:
                    return "getPlaybackState";
                case 34:
                    return "getQueue";
                case 35:
                    return "getQueueTitle";
                case 36:
                    return "getExtras";
                case 37:
                    return "getRatingType";
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
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.ResultReceiver resultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCommand(readString, readString2, bundle, resultReceiver);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString3 = parcel.readString();
                    android.view.KeyEvent keyEvent = (android.view.KeyEvent) parcel.readTypedObject(android.view.KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean sendMediaButton = sendMediaButton(readString3, keyEvent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendMediaButton);
                    return true;
                case 3:
                    java.lang.String readString4 = parcel.readString();
                    android.media.session.ISessionControllerCallback asInterface = android.media.session.ISessionControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(readString4, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.media.session.ISessionControllerCallback asInterface2 = android.media.session.ISessionControllerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String packageName = getPackageName();
                    parcel2.writeNoException();
                    parcel2.writeString(packageName);
                    return true;
                case 6:
                    java.lang.String tag = getTag();
                    parcel2.writeNoException();
                    parcel2.writeString(tag);
                    return true;
                case 7:
                    android.os.Bundle sessionInfo = getSessionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sessionInfo, 1);
                    return true;
                case 8:
                    android.app.PendingIntent launchPendingIntent = getLaunchPendingIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launchPendingIntent, 1);
                    return true;
                case 9:
                    long flags = getFlags();
                    parcel2.writeNoException();
                    parcel2.writeLong(flags);
                    return true;
                case 10:
                    android.media.session.MediaController.PlaybackInfo volumeAttributes = getVolumeAttributes();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(volumeAttributes, 1);
                    return true;
                case 11:
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    adjustVolume(readString5, readString6, readInt, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolumeTo(readString7, readString8, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    prepare(readString9);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromMediaId(readString10, readString11, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromSearch(readString12, readString13, bundle3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString14 = parcel.readString();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    prepareFromUri(readString14, uri, bundle4);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    play(readString15);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromMediaId(readString16, readString17, bundle5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromSearch(readString18, readString19, bundle6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.lang.String readString20 = parcel.readString();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    playFromUri(readString20, uri2, bundle7);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString21 = parcel.readString();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    skipToQueueItem(readString21, readLong);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    java.lang.String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    pause(readString22);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stop(readString23);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    next(readString24);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    previous(readString25);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    fastForward(readString26);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    rewind(readString27);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    java.lang.String readString28 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    seekTo(readString28, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    java.lang.String readString29 = parcel.readString();
                    android.media.Rating rating = (android.media.Rating) parcel.readTypedObject(android.media.Rating.CREATOR);
                    parcel.enforceNoDataAvail();
                    rate(readString29, rating);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.lang.String readString30 = parcel.readString();
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setPlaybackSpeed(readString30, readFloat);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    java.lang.String readString31 = parcel.readString();
                    java.lang.String readString32 = parcel.readString();
                    android.os.Bundle bundle8 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCustomAction(readString31, readString32, bundle8);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.media.MediaMetadata metadata = getMetadata();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(metadata, 1);
                    return true;
                case 33:
                    android.media.session.PlaybackState playbackState = getPlaybackState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(playbackState, 1);
                    return true;
                case 34:
                    android.content.pm.ParceledListSlice queue = getQueue();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queue, 1);
                    return true;
                case 35:
                    java.lang.CharSequence queueTitle = getQueueTitle();
                    parcel2.writeNoException();
                    if (queueTitle != null) {
                        parcel2.writeInt(1);
                        android.text.TextUtils.writeToParcel(queueTitle, parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 36:
                    android.os.Bundle extras = getExtras();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(extras, 1);
                    return true;
                case 37:
                    int ratingType = getRatingType();
                    parcel2.writeNoException();
                    parcel2.writeInt(ratingType);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.session.ISessionController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.session.ISessionController.Stub.DESCRIPTOR;
            }

            @Override // android.media.session.ISessionController
            public void sendCommand(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, android.os.ResultReceiver resultReceiver) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public boolean sendMediaButton(java.lang.String str, android.view.KeyEvent keyEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void registerCallback(java.lang.String str, android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSessionControllerCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void unregisterCallback(android.media.session.ISessionControllerCallback iSessionControllerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iSessionControllerCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public java.lang.String getPackageName() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public java.lang.String getTag() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.os.Bundle getSessionInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.app.PendingIntent getLaunchPendingIntent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public long getFlags() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.media.session.MediaController.PlaybackInfo getVolumeAttributes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.session.MediaController.PlaybackInfo) obtain2.readTypedObject(android.media.session.MediaController.PlaybackInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void adjustVolume(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void setVolumeTo(java.lang.String str, java.lang.String str2, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepare(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void prepareFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void play(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromMediaId(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromSearch(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void playFromUri(java.lang.String str, android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void skipToQueueItem(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void pause(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void stop(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void next(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void previous(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void fastForward(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void rewind(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void seekTo(java.lang.String str, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void rate(java.lang.String str, android.media.Rating rating) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(rating, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void setPlaybackSpeed(java.lang.String str, float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeFloat(f);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public void sendCustomAction(java.lang.String str, java.lang.String str2, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.media.MediaMetadata getMetadata() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.MediaMetadata) obtain2.readTypedObject(android.media.MediaMetadata.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.media.session.PlaybackState getPlaybackState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.session.PlaybackState) obtain2.readTypedObject(android.media.session.PlaybackState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.content.pm.ParceledListSlice getQueue() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public java.lang.CharSequence getQueueTitle() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (java.lang.CharSequence) obtain2.readTypedObject(android.text.TextUtils.CHAR_SEQUENCE_CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public android.os.Bundle getExtras() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.session.ISessionController
            public int getRatingType() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.session.ISessionController.Stub.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 36;
        }
    }
}
