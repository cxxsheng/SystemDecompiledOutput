package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputSession extends android.os.IInterface {
    void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void createOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException;

    void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException;

    void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException;

    void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException;

    void pauseRecording(android.os.Bundle bundle) throws android.os.RemoteException;

    void relayoutOverlayView(android.graphics.Rect rect) throws android.os.RemoteException;

    void release() throws android.os.RemoteException;

    void removeBroadcastInfo(int i) throws android.os.RemoteException;

    void removeOverlayView() throws android.os.RemoteException;

    void requestAd(android.media.tv.AdRequest adRequest) throws android.os.RemoteException;

    void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException;

    void resumePlayback() throws android.os.RemoteException;

    void resumeRecording(android.os.Bundle bundle) throws android.os.RemoteException;

    void selectAudioPresentation(int i, int i2) throws android.os.RemoteException;

    void selectTrack(int i, java.lang.String str) throws android.os.RemoteException;

    void setCaptionEnabled(boolean z) throws android.os.RemoteException;

    void setInteractiveAppNotificationEnabled(boolean z) throws android.os.RemoteException;

    void setMain(boolean z) throws android.os.RemoteException;

    void setSurface(android.view.Surface surface) throws android.os.RemoteException;

    void setTvMessageEnabled(int i, boolean z) throws android.os.RemoteException;

    void setVideoFrozen(boolean z) throws android.os.RemoteException;

    void setVolume(float f) throws android.os.RemoteException;

    void startRecording(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void stopPlayback(int i) throws android.os.RemoteException;

    void stopRecording() throws android.os.RemoteException;

    void timeShiftEnablePositionTracking(boolean z) throws android.os.RemoteException;

    void timeShiftPause() throws android.os.RemoteException;

    void timeShiftPlay(android.net.Uri uri) throws android.os.RemoteException;

    void timeShiftResume() throws android.os.RemoteException;

    void timeShiftSeekTo(long j) throws android.os.RemoteException;

    void timeShiftSetMode(int i) throws android.os.RemoteException;

    void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) throws android.os.RemoteException;

    void tune(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException;

    void unblockContent(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputSession {
        @Override // android.media.tv.ITvInputSession
        public void release() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setMain(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setSurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setVolume(float f) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void tune(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setCaptionEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void selectAudioPresentation(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void selectTrack(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setInteractiveAppNotificationEnabled(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void createOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void relayoutOverlayView(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void removeOverlayView() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void unblockContent(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftPlay(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftPause() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftResume() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSeekTo(long j) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftSetMode(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void timeShiftEnablePositionTracking(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void resumePlayback() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void stopPlayback(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void startRecording(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void stopRecording() throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void pauseRecording(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void resumeRecording(android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void removeBroadcastInfo(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void requestAd(android.media.tv.AdRequest adRequest) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setTvMessageEnabled(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void setVideoFrozen(boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputSession
        public void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputSession {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputSession";
        static final int TRANSACTION_appPrivateCommand = 11;
        static final int TRANSACTION_createOverlayView = 12;
        static final int TRANSACTION_dispatchSurfaceChanged = 4;
        static final int TRANSACTION_notifyAdBufferReady = 32;
        static final int TRANSACTION_notifyTvAdSessionData = 36;
        static final int TRANSACTION_notifyTvMessage = 33;
        static final int TRANSACTION_pauseRecording = 27;
        static final int TRANSACTION_relayoutOverlayView = 13;
        static final int TRANSACTION_release = 1;
        static final int TRANSACTION_removeBroadcastInfo = 30;
        static final int TRANSACTION_removeOverlayView = 14;
        static final int TRANSACTION_requestAd = 31;
        static final int TRANSACTION_requestBroadcastInfo = 29;
        static final int TRANSACTION_resumePlayback = 23;
        static final int TRANSACTION_resumeRecording = 28;
        static final int TRANSACTION_selectAudioPresentation = 8;
        static final int TRANSACTION_selectTrack = 9;
        static final int TRANSACTION_setCaptionEnabled = 7;
        static final int TRANSACTION_setInteractiveAppNotificationEnabled = 10;
        static final int TRANSACTION_setMain = 2;
        static final int TRANSACTION_setSurface = 3;
        static final int TRANSACTION_setTvMessageEnabled = 34;
        static final int TRANSACTION_setVideoFrozen = 35;
        static final int TRANSACTION_setVolume = 5;
        static final int TRANSACTION_startRecording = 25;
        static final int TRANSACTION_stopPlayback = 24;
        static final int TRANSACTION_stopRecording = 26;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 22;
        static final int TRANSACTION_timeShiftPause = 17;
        static final int TRANSACTION_timeShiftPlay = 16;
        static final int TRANSACTION_timeShiftResume = 18;
        static final int TRANSACTION_timeShiftSeekTo = 19;
        static final int TRANSACTION_timeShiftSetMode = 21;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 20;
        static final int TRANSACTION_tune = 6;
        static final int TRANSACTION_unblockContent = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputSession asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputSession)) {
                return (android.media.tv.ITvInputSession) queryLocalInterface;
            }
            return new android.media.tv.ITvInputSession.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "release";
                case 2:
                    return "setMain";
                case 3:
                    return "setSurface";
                case 4:
                    return "dispatchSurfaceChanged";
                case 5:
                    return "setVolume";
                case 6:
                    return android.media.tv.interactive.TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 7:
                    return "setCaptionEnabled";
                case 8:
                    return "selectAudioPresentation";
                case 9:
                    return "selectTrack";
                case 10:
                    return "setInteractiveAppNotificationEnabled";
                case 11:
                    return "appPrivateCommand";
                case 12:
                    return "createOverlayView";
                case 13:
                    return "relayoutOverlayView";
                case 14:
                    return "removeOverlayView";
                case 15:
                    return "unblockContent";
                case 16:
                    return "timeShiftPlay";
                case 17:
                    return "timeShiftPause";
                case 18:
                    return "timeShiftResume";
                case 19:
                    return "timeShiftSeekTo";
                case 20:
                    return "timeShiftSetPlaybackParams";
                case 21:
                    return "timeShiftSetMode";
                case 22:
                    return "timeShiftEnablePositionTracking";
                case 23:
                    return "resumePlayback";
                case 24:
                    return "stopPlayback";
                case 25:
                    return "startRecording";
                case 26:
                    return "stopRecording";
                case 27:
                    return "pauseRecording";
                case 28:
                    return "resumeRecording";
                case 29:
                    return "requestBroadcastInfo";
                case 30:
                    return "removeBroadcastInfo";
                case 31:
                    return "requestAd";
                case 32:
                    return "notifyAdBufferReady";
                case 33:
                    return "notifyTvMessage";
                case 34:
                    return "setTvMessageEnabled";
                case 35:
                    return "setVideoFrozen";
                case 36:
                    return "notifyTvAdSessionData";
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
                    release();
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setMain(readBoolean);
                    return true;
                case 3:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSurface(surface);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readInt, readInt2, readInt3);
                    return true;
                case 5:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setVolume(readFloat);
                    return true;
                case 6:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(uri, bundle);
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCaptionEnabled(readBoolean2);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectAudioPresentation(readInt4, readInt5);
                    return true;
                case 9:
                    int readInt6 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    selectTrack(readInt6, readString);
                    return true;
                case 10:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInteractiveAppNotificationEnabled(readBoolean3);
                    return true;
                case 11:
                    java.lang.String readString2 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    appPrivateCommand(readString2, bundle2);
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    createOverlayView(readStrongBinder, rect);
                    return true;
                case 13:
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    relayoutOverlayView(rect2);
                    return true;
                case 14:
                    removeOverlayView();
                    return true;
                case 15:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unblockContent(readString3);
                    return true;
                case 16:
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    timeShiftPlay(uri2);
                    return true;
                case 17:
                    timeShiftPause();
                    return true;
                case 18:
                    timeShiftResume();
                    return true;
                case 19:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    timeShiftSeekTo(readLong);
                    return true;
                case 20:
                    android.media.PlaybackParams playbackParams = (android.media.PlaybackParams) parcel.readTypedObject(android.media.PlaybackParams.CREATOR);
                    parcel.enforceNoDataAvail();
                    timeShiftSetPlaybackParams(playbackParams);
                    return true;
                case 21:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetMode(readInt7);
                    return true;
                case 22:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    timeShiftEnablePositionTracking(readBoolean4);
                    return true;
                case 23:
                    resumePlayback();
                    return true;
                case 24:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPlayback(readInt8);
                    return true;
                case 25:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRecording(uri3, bundle3);
                    return true;
                case 26:
                    stopRecording();
                    return true;
                case 27:
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    pauseRecording(bundle4);
                    return true;
                case 28:
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    resumeRecording(bundle5);
                    return true;
                case 29:
                    android.media.tv.BroadcastInfoRequest broadcastInfoRequest = (android.media.tv.BroadcastInfoRequest) parcel.readTypedObject(android.media.tv.BroadcastInfoRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBroadcastInfo(broadcastInfoRequest);
                    return true;
                case 30:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBroadcastInfo(readInt9);
                    return true;
                case 31:
                    android.media.tv.AdRequest adRequest = (android.media.tv.AdRequest) parcel.readTypedObject(android.media.tv.AdRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestAd(adRequest);
                    return true;
                case 32:
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyAdBufferReady(adBuffer);
                    return true;
                case 33:
                    int readInt10 = parcel.readInt();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readInt10, bundle6);
                    return true;
                case 34:
                    int readInt11 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTvMessageEnabled(readInt11, readBoolean5);
                    return true;
                case 35:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoFrozen(readBoolean6);
                    return true;
                case 36:
                    java.lang.String readString4 = parcel.readString();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyTvAdSessionData(readString4, bundle7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputSession {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputSession.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputSession
            public void release() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setMain(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setSurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void dispatchSurfaceChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setVolume(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void tune(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setCaptionEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void selectAudioPresentation(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void selectTrack(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setInteractiveAppNotificationEnabled(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void appPrivateCommand(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void createOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void relayoutOverlayView(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void removeOverlayView() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void unblockContent(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftPlay(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftPause() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftResume() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSeekTo(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSetPlaybackParams(android.media.PlaybackParams playbackParams) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(playbackParams, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftSetMode(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void timeShiftEnablePositionTracking(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void resumePlayback() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void stopPlayback(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void startRecording(android.net.Uri uri, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void stopRecording() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void pauseRecording(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void resumeRecording(android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void requestBroadcastInfo(android.media.tv.BroadcastInfoRequest broadcastInfoRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void removeBroadcastInfo(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void requestAd(android.media.tv.AdRequest adRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adRequest, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyAdBufferReady(android.media.tv.AdBuffer adBuffer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(adBuffer, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyTvMessage(int i, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setTvMessageEnabled(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void setVideoFrozen(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputSession
            public void notifyTvAdSessionData(java.lang.String str, android.os.Bundle bundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputSession.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
