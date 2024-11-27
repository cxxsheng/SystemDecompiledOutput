package android.media.tv;

/* loaded from: classes2.dex */
public interface ITvInputManager extends android.os.IInterface {
    android.media.tv.ITvInputHardware acquireTvInputHardware(int i, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, int i2, java.lang.String str, int i3) throws android.os.RemoteException;

    void addBlockedRating(java.lang.String str, int i) throws android.os.RemoteException;

    void addHardwareDevice(int i) throws android.os.RemoteException;

    boolean captureFrame(java.lang.String str, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig, int i) throws android.os.RemoteException;

    void createOverlayView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void createSession(android.media.tv.ITvInputClient iTvInputClient, java.lang.String str, android.content.AttributionSource attributionSource, boolean z, int i, int i2) throws android.os.RemoteException;

    void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    java.util.List<java.lang.String> getAvailableExtensionInterfaceNames(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<java.lang.String> getBlockedRatings(int i) throws android.os.RemoteException;

    int getClientPid(java.lang.String str) throws android.os.RemoteException;

    int getClientPriority(int i, java.lang.String str) throws android.os.RemoteException;

    java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfos(int i) throws android.os.RemoteException;

    java.util.List<android.media.tv.DvbDeviceInfo> getDvbDeviceList() throws android.os.RemoteException;

    android.os.IBinder getExtensionInterface(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() throws android.os.RemoteException;

    java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws android.os.RemoteException;

    android.media.tv.TvInputInfo getTvInputInfo(java.lang.String str, int i) throws android.os.RemoteException;

    java.util.List<android.media.tv.TvInputInfo> getTvInputList(int i) throws android.os.RemoteException;

    int getTvInputState(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isParentalControlsEnabled(int i) throws android.os.RemoteException;

    boolean isRatingBlocked(java.lang.String str, int i) throws android.os.RemoteException;

    boolean isSingleSessionActive(int i) throws android.os.RemoteException;

    void notifyAdBufferReady(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException;

    void notifyTvAdSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor openDvbDevice(android.media.tv.DvbDeviceInfo dvbDeviceInfo, int i) throws android.os.RemoteException;

    void pauseRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void registerCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException;

    void relayoutOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void releaseTvInputHardware(int i, android.media.tv.ITvInputHardware iTvInputHardware, int i2) throws android.os.RemoteException;

    void removeBlockedRating(java.lang.String str, int i) throws android.os.RemoteException;

    void removeBroadcastInfo(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void removeHardwareDevice(int i) throws android.os.RemoteException;

    void removeOverlayView(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void requestAd(android.os.IBinder iBinder, android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException;

    void requestBroadcastInfo(android.os.IBinder iBinder, android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException;

    void requestChannelBrowsable(android.net.Uri uri, int i) throws android.os.RemoteException;

    void resumePlayback(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void resumeRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void selectAudioPresentation(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException;

    void selectTrack(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void sendAppPrivateCommand(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void sendTvInputNotifyIntent(android.content.Intent intent, int i) throws android.os.RemoteException;

    void setCaptionEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void setInteractiveAppNotificationEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void setMainSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void setParentalControlsEnabled(boolean z, int i) throws android.os.RemoteException;

    void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException;

    void setTvMessageEnabled(android.os.IBinder iBinder, int i, boolean z, int i2) throws android.os.RemoteException;

    void setVideoFrozen(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void setVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException;

    void startRecording(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void stopPlayback(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void stopRecording(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void timeShiftEnablePositionTracking(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void timeShiftPause(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void timeShiftPlay(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException;

    void timeShiftResume(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void timeShiftSeekTo(android.os.IBinder iBinder, long j, int i) throws android.os.RemoteException;

    void timeShiftSetMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void timeShiftSetPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException;

    void tune(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void unblockContent(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException;

    void updateTvInputInfo(android.media.tv.TvInputInfo tvInputInfo, int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.ITvInputManager {
        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.TvInputInfo> getTvInputList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public android.media.tv.TvInputInfo getTvInputInfo(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void updateTvInputInfo(android.media.tv.TvInputInfo tvInputInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public int getTvInputState(java.lang.String str, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public android.os.IBinder getExtensionInterface(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void registerCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void unregisterCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isParentalControlsEnabled(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public void setParentalControlsEnabled(boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isRatingBlocked(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<java.lang.String> getBlockedRatings(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void addBlockedRating(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeBlockedRating(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void createSession(android.media.tv.ITvInputClient iTvInputClient, java.lang.String str, android.content.AttributionSource attributionSource, boolean z, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public int getClientPid(java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public int getClientPriority(int i, java.lang.String str) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.tv.ITvInputManager
        public void setMainSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void tune(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setCaptionEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void selectTrack(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void selectAudioPresentation(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setInteractiveAppNotificationEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void sendAppPrivateCommand(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void createOverlayView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void relayoutOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeOverlayView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void unblockContent(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftPlay(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftPause(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftResume(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSeekTo(android.os.IBinder iBinder, long j, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSetPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftSetMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void timeShiftEnablePositionTracking(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfos(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void startRecording(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void stopRecording(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void pauseRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void resumeRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void resumePlayback(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void stopPlayback(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestBroadcastInfo(android.os.IBinder iBinder, android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeBroadcastInfo(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestAd(android.os.IBinder iBinder, android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyAdBufferReady(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setTvMessageEnabled(android.os.IBinder iBinder, int i, boolean z, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public android.media.tv.ITvInputHardware acquireTvInputHardware(int i, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void releaseTvInputHardware(int i, android.media.tv.ITvInputHardware iTvInputHardware, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean captureFrame(java.lang.String str, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public boolean isSingleSessionActive(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.ITvInputManager
        public java.util.List<android.media.tv.DvbDeviceInfo> getDvbDeviceList() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public android.os.ParcelFileDescriptor openDvbDevice(android.media.tv.DvbDeviceInfo dvbDeviceInfo, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.ITvInputManager
        public void sendTvInputNotifyIntent(android.content.Intent intent, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void requestChannelBrowsable(android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void addHardwareDevice(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void removeHardwareDevice(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void setVideoFrozen(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.ITvInputManager
        public void notifyTvAdSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.ITvInputManager {
        public static final java.lang.String DESCRIPTOR = "android.media.tv.ITvInputManager";
        static final int TRANSACTION_acquireTvInputHardware = 55;
        static final int TRANSACTION_addBlockedRating = 14;
        static final int TRANSACTION_addHardwareDevice = 64;
        static final int TRANSACTION_captureFrame = 58;
        static final int TRANSACTION_createOverlayView = 30;
        static final int TRANSACTION_createSession = 16;
        static final int TRANSACTION_dispatchSurfaceChanged = 22;
        static final int TRANSACTION_getAvailableExtensionInterfaceNames = 5;
        static final int TRANSACTION_getAvailableTvStreamConfigList = 57;
        static final int TRANSACTION_getBlockedRatings = 13;
        static final int TRANSACTION_getClientPid = 18;
        static final int TRANSACTION_getClientPriority = 19;
        static final int TRANSACTION_getCurrentTunedInfos = 41;
        static final int TRANSACTION_getDvbDeviceList = 60;
        static final int TRANSACTION_getExtensionInterface = 6;
        static final int TRANSACTION_getHardwareList = 54;
        static final int TRANSACTION_getTvContentRatingSystemList = 7;
        static final int TRANSACTION_getTvInputInfo = 2;
        static final int TRANSACTION_getTvInputList = 1;
        static final int TRANSACTION_getTvInputState = 4;
        static final int TRANSACTION_isParentalControlsEnabled = 10;
        static final int TRANSACTION_isRatingBlocked = 12;
        static final int TRANSACTION_isSingleSessionActive = 59;
        static final int TRANSACTION_notifyAdBufferReady = 51;
        static final int TRANSACTION_notifyTvAdSessionData = 67;
        static final int TRANSACTION_notifyTvMessage = 52;
        static final int TRANSACTION_openDvbDevice = 61;
        static final int TRANSACTION_pauseRecording = 44;
        static final int TRANSACTION_registerCallback = 8;
        static final int TRANSACTION_relayoutOverlayView = 31;
        static final int TRANSACTION_releaseSession = 17;
        static final int TRANSACTION_releaseTvInputHardware = 56;
        static final int TRANSACTION_removeBlockedRating = 15;
        static final int TRANSACTION_removeBroadcastInfo = 49;
        static final int TRANSACTION_removeHardwareDevice = 65;
        static final int TRANSACTION_removeOverlayView = 32;
        static final int TRANSACTION_requestAd = 50;
        static final int TRANSACTION_requestBroadcastInfo = 48;
        static final int TRANSACTION_requestChannelBrowsable = 63;
        static final int TRANSACTION_resumePlayback = 46;
        static final int TRANSACTION_resumeRecording = 45;
        static final int TRANSACTION_selectAudioPresentation = 27;
        static final int TRANSACTION_selectTrack = 26;
        static final int TRANSACTION_sendAppPrivateCommand = 29;
        static final int TRANSACTION_sendTvInputNotifyIntent = 62;
        static final int TRANSACTION_setCaptionEnabled = 25;
        static final int TRANSACTION_setInteractiveAppNotificationEnabled = 28;
        static final int TRANSACTION_setMainSession = 20;
        static final int TRANSACTION_setParentalControlsEnabled = 11;
        static final int TRANSACTION_setSurface = 21;
        static final int TRANSACTION_setTvMessageEnabled = 53;
        static final int TRANSACTION_setVideoFrozen = 66;
        static final int TRANSACTION_setVolume = 23;
        static final int TRANSACTION_startRecording = 42;
        static final int TRANSACTION_stopPlayback = 47;
        static final int TRANSACTION_stopRecording = 43;
        static final int TRANSACTION_timeShiftEnablePositionTracking = 40;
        static final int TRANSACTION_timeShiftPause = 35;
        static final int TRANSACTION_timeShiftPlay = 34;
        static final int TRANSACTION_timeShiftResume = 36;
        static final int TRANSACTION_timeShiftSeekTo = 37;
        static final int TRANSACTION_timeShiftSetMode = 39;
        static final int TRANSACTION_timeShiftSetPlaybackParams = 38;
        static final int TRANSACTION_tune = 24;
        static final int TRANSACTION_unblockContent = 33;
        static final int TRANSACTION_unregisterCallback = 9;
        static final int TRANSACTION_updateTvInputInfo = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.tv.ITvInputManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.ITvInputManager)) {
                return (android.media.tv.ITvInputManager) queryLocalInterface;
            }
            return new android.media.tv.ITvInputManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvInputList";
                case 2:
                    return "getTvInputInfo";
                case 3:
                    return "updateTvInputInfo";
                case 4:
                    return "getTvInputState";
                case 5:
                    return "getAvailableExtensionInterfaceNames";
                case 6:
                    return "getExtensionInterface";
                case 7:
                    return "getTvContentRatingSystemList";
                case 8:
                    return "registerCallback";
                case 9:
                    return "unregisterCallback";
                case 10:
                    return "isParentalControlsEnabled";
                case 11:
                    return "setParentalControlsEnabled";
                case 12:
                    return "isRatingBlocked";
                case 13:
                    return "getBlockedRatings";
                case 14:
                    return "addBlockedRating";
                case 15:
                    return "removeBlockedRating";
                case 16:
                    return "createSession";
                case 17:
                    return "releaseSession";
                case 18:
                    return "getClientPid";
                case 19:
                    return "getClientPriority";
                case 20:
                    return "setMainSession";
                case 21:
                    return "setSurface";
                case 22:
                    return "dispatchSurfaceChanged";
                case 23:
                    return "setVolume";
                case 24:
                    return android.media.tv.interactive.TvInteractiveAppService.PLAYBACK_COMMAND_TYPE_TUNE;
                case 25:
                    return "setCaptionEnabled";
                case 26:
                    return "selectTrack";
                case 27:
                    return "selectAudioPresentation";
                case 28:
                    return "setInteractiveAppNotificationEnabled";
                case 29:
                    return "sendAppPrivateCommand";
                case 30:
                    return "createOverlayView";
                case 31:
                    return "relayoutOverlayView";
                case 32:
                    return "removeOverlayView";
                case 33:
                    return "unblockContent";
                case 34:
                    return "timeShiftPlay";
                case 35:
                    return "timeShiftPause";
                case 36:
                    return "timeShiftResume";
                case 37:
                    return "timeShiftSeekTo";
                case 38:
                    return "timeShiftSetPlaybackParams";
                case 39:
                    return "timeShiftSetMode";
                case 40:
                    return "timeShiftEnablePositionTracking";
                case 41:
                    return "getCurrentTunedInfos";
                case 42:
                    return "startRecording";
                case 43:
                    return "stopRecording";
                case 44:
                    return "pauseRecording";
                case 45:
                    return "resumeRecording";
                case 46:
                    return "resumePlayback";
                case 47:
                    return "stopPlayback";
                case 48:
                    return "requestBroadcastInfo";
                case 49:
                    return "removeBroadcastInfo";
                case 50:
                    return "requestAd";
                case 51:
                    return "notifyAdBufferReady";
                case 52:
                    return "notifyTvMessage";
                case 53:
                    return "setTvMessageEnabled";
                case 54:
                    return "getHardwareList";
                case 55:
                    return "acquireTvInputHardware";
                case 56:
                    return "releaseTvInputHardware";
                case 57:
                    return "getAvailableTvStreamConfigList";
                case 58:
                    return "captureFrame";
                case 59:
                    return "isSingleSessionActive";
                case 60:
                    return "getDvbDeviceList";
                case 61:
                    return "openDvbDevice";
                case 62:
                    return "sendTvInputNotifyIntent";
                case 63:
                    return "requestChannelBrowsable";
                case 64:
                    return "addHardwareDevice";
                case 65:
                    return "removeHardwareDevice";
                case 66:
                    return "setVideoFrozen";
                case 67:
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.TvInputInfo> tvInputList = getTvInputList(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvInputList, 1);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.tv.TvInputInfo tvInputInfo = getTvInputInfo(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(tvInputInfo, 1);
                    return true;
                case 3:
                    android.media.tv.TvInputInfo tvInputInfo2 = (android.media.tv.TvInputInfo) parcel.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateTvInputInfo(tvInputInfo2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int tvInputState = getTvInputState(readString2, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(tvInputState);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> availableExtensionInterfaceNames = getAvailableExtensionInterfaceNames(readString3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeStringList(availableExtensionInterfaceNames);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.IBinder extensionInterface = getExtensionInterface(readString4, readString5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(extensionInterface);
                    return true;
                case 7:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.TvContentRatingSystemInfo> tvContentRatingSystemList = getTvContentRatingSystemList(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvContentRatingSystemList, 1);
                    return true;
                case 8:
                    android.media.tv.ITvInputManagerCallback asInterface = android.media.tv.ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.media.tv.ITvInputManagerCallback asInterface2 = android.media.tv.ITvInputManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface2, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isParentalControlsEnabled = isParentalControlsEnabled(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isParentalControlsEnabled);
                    return true;
                case 11:
                    boolean readBoolean = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setParentalControlsEnabled(readBoolean, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    java.lang.String readString6 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRatingBlocked = isRatingBlocked(readString6, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRatingBlocked);
                    return true;
                case 13:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> blockedRatings = getBlockedRatings(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockedRatings);
                    return true;
                case 14:
                    java.lang.String readString7 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addBlockedRating(readString7, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    java.lang.String readString8 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBlockedRating(readString8, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.media.tv.ITvInputClient asInterface3 = android.media.tv.ITvInputClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString9 = parcel.readString();
                    android.content.AttributionSource attributionSource = (android.content.AttributionSource) parcel.readTypedObject(android.content.AttributionSource.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(asInterface3, readString9, attributionSource, readBoolean2, readInt16, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(readStrongBinder, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPid = getClientPid(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPid);
                    return true;
                case 19:
                    int readInt19 = parcel.readInt();
                    java.lang.String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(readInt19, readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setMainSession(readStrongBinder2, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(readStrongBinder3, surface, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readStrongBinder4, readInt22, readInt23, readInt24, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    float readFloat = parcel.readFloat();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVolume(readStrongBinder5, readFloat, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    tune(readStrongBinder6, uri, bundle, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCaptionEnabled(readStrongBinder7, readBoolean3, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    int readInt29 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectTrack(readStrongBinder8, readInt29, readString12, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    selectAudioPresentation(readStrongBinder9, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractiveAppNotificationEnabled(readStrongBinder10, readBoolean4, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    java.lang.String readString13 = parcel.readString();
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppPrivateCommand(readStrongBinder11, readString13, bundle2, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createOverlayView(readStrongBinder12, readStrongBinder13, rect, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutOverlayView(readStrongBinder14, rect2, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeOverlayView(readStrongBinder15, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    java.lang.String readString14 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unblockContent(readStrongBinder16, readString14, readInt39);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPlay(readStrongBinder17, uri2, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftPause(readStrongBinder18, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftResume(readStrongBinder19, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    long readLong = parcel.readLong();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSeekTo(readStrongBinder20, readLong, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    android.media.PlaybackParams playbackParams = (android.media.PlaybackParams) parcel.readTypedObject(android.media.PlaybackParams.CREATOR);
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetPlaybackParams(readStrongBinder21, playbackParams, readInt44);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.os.IBinder readStrongBinder22 = parcel.readStrongBinder();
                    int readInt45 = parcel.readInt();
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftSetMode(readStrongBinder22, readInt45, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.os.IBinder readStrongBinder23 = parcel.readStrongBinder();
                    boolean readBoolean5 = parcel.readBoolean();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    timeShiftEnablePositionTracking(readStrongBinder23, readBoolean5, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.TunedInfo> currentTunedInfos = getCurrentTunedInfos(readInt48);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(currentTunedInfos, 1);
                    return true;
                case 42:
                    android.os.IBinder readStrongBinder24 = parcel.readStrongBinder();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startRecording(readStrongBinder24, uri3, bundle3, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    android.os.IBinder readStrongBinder25 = parcel.readStrongBinder();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopRecording(readStrongBinder25, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.os.IBinder readStrongBinder26 = parcel.readStrongBinder();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    pauseRecording(readStrongBinder26, bundle4, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    android.os.IBinder readStrongBinder27 = parcel.readStrongBinder();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumeRecording(readStrongBinder27, bundle5, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.os.IBinder readStrongBinder28 = parcel.readStrongBinder();
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resumePlayback(readStrongBinder28, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.os.IBinder readStrongBinder29 = parcel.readStrongBinder();
                    int readInt54 = parcel.readInt();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopPlayback(readStrongBinder29, readInt54, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    android.os.IBinder readStrongBinder30 = parcel.readStrongBinder();
                    android.media.tv.BroadcastInfoRequest broadcastInfoRequest = (android.media.tv.BroadcastInfoRequest) parcel.readTypedObject(android.media.tv.BroadcastInfoRequest.CREATOR);
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestBroadcastInfo(readStrongBinder30, broadcastInfoRequest, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.os.IBinder readStrongBinder31 = parcel.readStrongBinder();
                    int readInt57 = parcel.readInt();
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeBroadcastInfo(readStrongBinder31, readInt57, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    android.os.IBinder readStrongBinder32 = parcel.readStrongBinder();
                    android.media.tv.AdRequest adRequest = (android.media.tv.AdRequest) parcel.readTypedObject(android.media.tv.AdRequest.CREATOR);
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAd(readStrongBinder32, adRequest, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.os.IBinder readStrongBinder33 = parcel.readStrongBinder();
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    int readInt60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdBufferReady(readStrongBinder33, adBuffer, readInt60);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    android.os.IBinder readStrongBinder34 = parcel.readStrongBinder();
                    int readInt61 = parcel.readInt();
                    android.os.Bundle bundle6 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readStrongBinder34, readInt61, bundle6, readInt62);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    android.os.IBinder readStrongBinder35 = parcel.readStrongBinder();
                    int readInt63 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTvMessageEnabled(readStrongBinder35, readInt63, readBoolean6, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    java.util.List<android.media.tv.TvInputHardwareInfo> hardwareList = getHardwareList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(hardwareList, 1);
                    return true;
                case 55:
                    int readInt65 = parcel.readInt();
                    android.media.tv.ITvInputHardwareCallback asInterface4 = android.media.tv.ITvInputHardwareCallback.Stub.asInterface(parcel.readStrongBinder());
                    android.media.tv.TvInputInfo tvInputInfo3 = (android.media.tv.TvInputInfo) parcel.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                    int readInt66 = parcel.readInt();
                    java.lang.String readString15 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.media.tv.ITvInputHardware acquireTvInputHardware = acquireTvInputHardware(readInt65, asInterface4, tvInputInfo3, readInt66, readString15, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(acquireTvInputHardware);
                    return true;
                case 56:
                    int readInt68 = parcel.readInt();
                    android.media.tv.ITvInputHardware asInterface5 = android.media.tv.ITvInputHardware.Stub.asInterface(parcel.readStrongBinder());
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseTvInputHardware(readInt68, asInterface5, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    java.lang.String readString16 = parcel.readString();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.TvStreamConfig> availableTvStreamConfigList = getAvailableTvStreamConfigList(readString16, readInt70);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(availableTvStreamConfigList, 1);
                    return true;
                case 58:
                    java.lang.String readString17 = parcel.readString();
                    android.view.Surface surface2 = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    android.media.tv.TvStreamConfig tvStreamConfig = (android.media.tv.TvStreamConfig) parcel.readTypedObject(android.media.tv.TvStreamConfig.CREATOR);
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean captureFrame = captureFrame(readString17, surface2, tvStreamConfig, readInt71);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(captureFrame);
                    return true;
                case 59:
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSingleSessionActive = isSingleSessionActive(readInt72);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSingleSessionActive);
                    return true;
                case 60:
                    java.util.List<android.media.tv.DvbDeviceInfo> dvbDeviceList = getDvbDeviceList();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(dvbDeviceList, 1);
                    return true;
                case 61:
                    android.media.tv.DvbDeviceInfo dvbDeviceInfo = (android.media.tv.DvbDeviceInfo) parcel.readTypedObject(android.media.tv.DvbDeviceInfo.CREATOR);
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor openDvbDevice = openDvbDevice(dvbDeviceInfo, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(openDvbDevice, 1);
                    return true;
                case 62:
                    android.content.Intent intent = (android.content.Intent) parcel.readTypedObject(android.content.Intent.CREATOR);
                    int readInt74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvInputNotifyIntent(intent, readInt74);
                    parcel2.writeNoException();
                    return true;
                case 63:
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestChannelBrowsable(uri4, readInt75);
                    parcel2.writeNoException();
                    return true;
                case 64:
                    int readInt76 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addHardwareDevice(readInt76);
                    parcel2.writeNoException();
                    return true;
                case 65:
                    int readInt77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeHardwareDevice(readInt77);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    android.os.IBinder readStrongBinder36 = parcel.readStrongBinder();
                    boolean readBoolean7 = parcel.readBoolean();
                    int readInt78 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setVideoFrozen(readStrongBinder36, readBoolean7, readInt78);
                    parcel2.writeNoException();
                    return true;
                case 67:
                    android.os.IBinder readStrongBinder37 = parcel.readStrongBinder();
                    java.lang.String readString18 = parcel.readString();
                    android.os.Bundle bundle7 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvAdSessionData(readStrongBinder37, readString18, bundle7, readInt79);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.ITvInputManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.ITvInputManager.Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.TvInputInfo> getTvInputList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.TvInputInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public android.media.tv.TvInputInfo getTvInputInfo(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.tv.TvInputInfo) obtain2.readTypedObject(android.media.tv.TvInputInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void updateTvInputInfo(android.media.tv.TvInputInfo tvInputInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getTvInputState(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<java.lang.String> getAvailableExtensionInterfaceNames(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public android.os.IBinder getExtensionInterface(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readStrongBinder();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.TvContentRatingSystemInfo> getTvContentRatingSystemList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.TvContentRatingSystemInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void registerCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unregisterCallback(android.media.tv.ITvInputManagerCallback iTvInputManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isParentalControlsEnabled(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setParentalControlsEnabled(boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isRatingBlocked(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<java.lang.String> getBlockedRatings(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addBlockedRating(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBlockedRating(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createSession(android.media.tv.ITvInputClient iTvInputClient, java.lang.String str, android.content.AttributionSource attributionSource, boolean z, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInputClient);
                    obtain.writeString(str);
                    obtain.writeTypedObject(attributionSource, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPid(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public int getClientPriority(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setMainSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void tune(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setCaptionEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectTrack(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void selectAudioPresentation(android.os.IBinder iBinder, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setInteractiveAppNotificationEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendAppPrivateCommand(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void createOverlayView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void relayoutOverlayView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeOverlayView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void unblockContent(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPlay(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftPause(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftResume(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSeekTo(android.os.IBinder iBinder, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(playbackParams, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftSetMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void timeShiftEnablePositionTracking(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.TunedInfo> getCurrentTunedInfos(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.TunedInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void startRecording(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopRecording(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void pauseRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumeRecording(android.os.IBinder iBinder, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void resumePlayback(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void stopPlayback(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestBroadcastInfo(android.os.IBinder iBinder, android.media.tv.BroadcastInfoRequest broadcastInfoRequest, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(broadcastInfoRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeBroadcastInfo(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestAd(android.os.IBinder iBinder, android.media.tv.AdRequest adRequest, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adRequest, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyAdBufferReady(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setTvMessageEnabled(android.os.IBinder iBinder, int i, boolean z, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.TvInputHardwareInfo> getHardwareList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.TvInputHardwareInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public android.media.tv.ITvInputHardware acquireTvInputHardware(int i, android.media.tv.ITvInputHardwareCallback iTvInputHardwareCallback, android.media.tv.TvInputInfo tvInputInfo, int i2, java.lang.String str, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTvInputHardwareCallback);
                    obtain.writeTypedObject(tvInputInfo, 0);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.media.tv.ITvInputHardware.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void releaseTvInputHardware(int i, android.media.tv.ITvInputHardware iTvInputHardware, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iTvInputHardware);
                    obtain.writeInt(i2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.TvStreamConfig> getAvailableTvStreamConfigList(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.TvStreamConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean captureFrame(java.lang.String str, android.view.Surface surface, android.media.tv.TvStreamConfig tvStreamConfig, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeTypedObject(tvStreamConfig, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public boolean isSingleSessionActive(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public java.util.List<android.media.tv.DvbDeviceInfo> getDvbDeviceList() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.DvbDeviceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public android.os.ParcelFileDescriptor openDvbDevice(android.media.tv.DvbDeviceInfo dvbDeviceInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(dvbDeviceInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void sendTvInputNotifyIntent(android.content.Intent intent, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void requestChannelBrowsable(android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void addHardwareDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void removeHardwareDevice(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void setVideoFrozen(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvInputManager
            public void notifyTvAdSessionData(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.ITvInputManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 66;
        }
    }
}
