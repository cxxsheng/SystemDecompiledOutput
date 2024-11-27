package android.media.tv.interactive;

/* loaded from: classes2.dex */
public interface ITvInteractiveAppManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.interactive.ITvInteractiveAppManager";

    void createBiInteractiveApp(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void createSession(android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException;

    void destroyBiInteractiveApp(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException;

    java.util.List<android.media.tv.interactive.AppLinkInfo> getAppLinkInfoList(int i) throws android.os.RemoteException;

    java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws android.os.RemoteException;

    void notifyAdBufferConsumed(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException;

    void notifyAdResponse(android.os.IBinder iBinder, android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException;

    void notifyBroadcastInfoResponse(android.os.IBinder iBinder, android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException;

    void notifyContentAllowed(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void notifyContentBlocked(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void notifyRecordingConnectionFailed(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void notifyRecordingDisconnected(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void notifyRecordingError(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void notifyRecordingScheduled(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void notifyRecordingStarted(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void notifyRecordingStopped(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void notifyRecordingTuned(android.os.IBinder iBinder, java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException;

    void notifySignalStrength(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void notifyTimeShiftCurrentPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException;

    void notifyTimeShiftPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException;

    void notifyTimeShiftStartPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException;

    void notifyTimeShiftStatusChanged(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void notifyTrackSelected(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void notifyTracksChanged(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException;

    void notifyTuned(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException;

    void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    void notifyVideoAvailable(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void notifyVideoFreezeUpdated(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void notifyVideoUnavailable(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void registerAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException;

    void registerCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException;

    void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void resetInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void sendAvailableSpeeds(android.os.IBinder iBinder, float[] fArr, int i) throws android.os.RemoteException;

    void sendCertificate(android.os.IBinder iBinder, java.lang.String str, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException;

    void sendCurrentChannelLcn(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException;

    void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException;

    void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException;

    void sendSelectedTrackInfo(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException;

    void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException;

    void sendStreamVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException;

    void sendTimeShiftMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException;

    void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException;

    void sendTvRecordingInfo(android.os.IBinder iBinder, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException;

    void sendTvRecordingInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvRecordingInfo> list, int i) throws android.os.RemoteException;

    void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException;

    void setTeletextAppEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException;

    void startInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void stopInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException;

    void unregisterAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException;

    void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException;

    public static class Default implements android.media.tv.interactive.ITvInteractiveAppManager {
        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public java.util.List<android.media.tv.interactive.AppLinkInfo> getAppLinkInfoList(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void registerAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void unregisterAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void startInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void stopInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void resetInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createBiInteractiveApp(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void destroyBiInteractiveApp(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void setTeletextAppEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentChannelLcn(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendStreamVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTimeShiftMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendAvailableSpeeds(android.os.IBinder iBinder, float[] fArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendCertificate(android.os.IBinder iBinder, java.lang.String str, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTvRecordingInfo(android.os.IBinder iBinder, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendTvRecordingInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvRecordingInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftStatusChanged(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftStartPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTimeShiftCurrentPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingConnectionFailed(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingDisconnected(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingTuned(android.os.IBinder iBinder, java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingError(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingScheduled(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createSession(android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTuned(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTrackSelected(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTracksChanged(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoAvailable(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoUnavailable(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyVideoFreezeUpdated(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyContentAllowed(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyContentBlocked(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifySignalStrength(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingStarted(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyRecordingStopped(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyBroadcastInfoResponse(android.os.IBinder iBinder, android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyAdResponse(android.os.IBinder iBinder, android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void notifyAdBufferConsumed(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void sendSelectedTrackInfo(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void registerCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.interactive.ITvInteractiveAppManager
        public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.interactive.ITvInteractiveAppManager {
        static final int TRANSACTION_createBiInteractiveApp = 9;
        static final int TRANSACTION_createMediaView = 54;
        static final int TRANSACTION_createSession = 34;
        static final int TRANSACTION_destroyBiInteractiveApp = 10;
        static final int TRANSACTION_dispatchSurfaceChanged = 49;
        static final int TRANSACTION_getAppLinkInfoList = 2;
        static final int TRANSACTION_getTvInteractiveAppServiceList = 1;
        static final int TRANSACTION_notifyAdBufferConsumed = 52;
        static final int TRANSACTION_notifyAdResponse = 51;
        static final int TRANSACTION_notifyBroadcastInfoResponse = 50;
        static final int TRANSACTION_notifyContentAllowed = 42;
        static final int TRANSACTION_notifyContentBlocked = 43;
        static final int TRANSACTION_notifyError = 24;
        static final int TRANSACTION_notifyRecordingConnectionFailed = 29;
        static final int TRANSACTION_notifyRecordingDisconnected = 30;
        static final int TRANSACTION_notifyRecordingError = 32;
        static final int TRANSACTION_notifyRecordingScheduled = 33;
        static final int TRANSACTION_notifyRecordingStarted = 45;
        static final int TRANSACTION_notifyRecordingStopped = 46;
        static final int TRANSACTION_notifyRecordingTuned = 31;
        static final int TRANSACTION_notifySignalStrength = 44;
        static final int TRANSACTION_notifyTimeShiftCurrentPositionChanged = 28;
        static final int TRANSACTION_notifyTimeShiftPlaybackParams = 25;
        static final int TRANSACTION_notifyTimeShiftStartPositionChanged = 27;
        static final int TRANSACTION_notifyTimeShiftStatusChanged = 26;
        static final int TRANSACTION_notifyTrackSelected = 37;
        static final int TRANSACTION_notifyTracksChanged = 38;
        static final int TRANSACTION_notifyTuned = 36;
        static final int TRANSACTION_notifyTvMessage = 47;
        static final int TRANSACTION_notifyVideoAvailable = 39;
        static final int TRANSACTION_notifyVideoFreezeUpdated = 41;
        static final int TRANSACTION_notifyVideoUnavailable = 40;
        static final int TRANSACTION_registerAppLinkInfo = 3;
        static final int TRANSACTION_registerCallback = 57;
        static final int TRANSACTION_relayoutMediaView = 55;
        static final int TRANSACTION_releaseSession = 35;
        static final int TRANSACTION_removeMediaView = 56;
        static final int TRANSACTION_resetInteractiveApp = 8;
        static final int TRANSACTION_sendAppLinkCommand = 5;
        static final int TRANSACTION_sendAvailableSpeeds = 19;
        static final int TRANSACTION_sendCertificate = 21;
        static final int TRANSACTION_sendCurrentChannelLcn = 14;
        static final int TRANSACTION_sendCurrentChannelUri = 13;
        static final int TRANSACTION_sendCurrentTvInputId = 17;
        static final int TRANSACTION_sendCurrentVideoBounds = 12;
        static final int TRANSACTION_sendSelectedTrackInfo = 53;
        static final int TRANSACTION_sendSigningResult = 20;
        static final int TRANSACTION_sendStreamVolume = 15;
        static final int TRANSACTION_sendTimeShiftMode = 18;
        static final int TRANSACTION_sendTrackInfoList = 16;
        static final int TRANSACTION_sendTvRecordingInfo = 22;
        static final int TRANSACTION_sendTvRecordingInfoList = 23;
        static final int TRANSACTION_setSurface = 48;
        static final int TRANSACTION_setTeletextAppEnabled = 11;
        static final int TRANSACTION_startInteractiveApp = 6;
        static final int TRANSACTION_stopInteractiveApp = 7;
        static final int TRANSACTION_unregisterAppLinkInfo = 4;
        static final int TRANSACTION_unregisterCallback = 58;

        public Stub() {
            attachInterface(this, android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
        }

        public static android.media.tv.interactive.ITvInteractiveAppManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.interactive.ITvInteractiveAppManager)) {
                return (android.media.tv.interactive.ITvInteractiveAppManager) queryLocalInterface;
            }
            return new android.media.tv.interactive.ITvInteractiveAppManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getTvInteractiveAppServiceList";
                case 2:
                    return "getAppLinkInfoList";
                case 3:
                    return "registerAppLinkInfo";
                case 4:
                    return "unregisterAppLinkInfo";
                case 5:
                    return "sendAppLinkCommand";
                case 6:
                    return "startInteractiveApp";
                case 7:
                    return "stopInteractiveApp";
                case 8:
                    return "resetInteractiveApp";
                case 9:
                    return "createBiInteractiveApp";
                case 10:
                    return "destroyBiInteractiveApp";
                case 11:
                    return "setTeletextAppEnabled";
                case 12:
                    return "sendCurrentVideoBounds";
                case 13:
                    return "sendCurrentChannelUri";
                case 14:
                    return "sendCurrentChannelLcn";
                case 15:
                    return "sendStreamVolume";
                case 16:
                    return "sendTrackInfoList";
                case 17:
                    return "sendCurrentTvInputId";
                case 18:
                    return "sendTimeShiftMode";
                case 19:
                    return "sendAvailableSpeeds";
                case 20:
                    return "sendSigningResult";
                case 21:
                    return "sendCertificate";
                case 22:
                    return "sendTvRecordingInfo";
                case 23:
                    return "sendTvRecordingInfoList";
                case 24:
                    return "notifyError";
                case 25:
                    return "notifyTimeShiftPlaybackParams";
                case 26:
                    return "notifyTimeShiftStatusChanged";
                case 27:
                    return "notifyTimeShiftStartPositionChanged";
                case 28:
                    return "notifyTimeShiftCurrentPositionChanged";
                case 29:
                    return "notifyRecordingConnectionFailed";
                case 30:
                    return "notifyRecordingDisconnected";
                case 31:
                    return "notifyRecordingTuned";
                case 32:
                    return "notifyRecordingError";
                case 33:
                    return "notifyRecordingScheduled";
                case 34:
                    return "createSession";
                case 35:
                    return "releaseSession";
                case 36:
                    return "notifyTuned";
                case 37:
                    return "notifyTrackSelected";
                case 38:
                    return "notifyTracksChanged";
                case 39:
                    return "notifyVideoAvailable";
                case 40:
                    return "notifyVideoUnavailable";
                case 41:
                    return "notifyVideoFreezeUpdated";
                case 42:
                    return "notifyContentAllowed";
                case 43:
                    return "notifyContentBlocked";
                case 44:
                    return "notifySignalStrength";
                case 45:
                    return "notifyRecordingStarted";
                case 46:
                    return "notifyRecordingStopped";
                case 47:
                    return "notifyTvMessage";
                case 48:
                    return "setSurface";
                case 49:
                    return "dispatchSurfaceChanged";
                case 50:
                    return "notifyBroadcastInfoResponse";
                case 51:
                    return "notifyAdResponse";
                case 52:
                    return "notifyAdBufferConsumed";
                case 53:
                    return "sendSelectedTrackInfo";
                case 54:
                    return "createMediaView";
                case 55:
                    return "relayoutMediaView";
                case 56:
                    return "removeMediaView";
                case 57:
                    return "registerCallback";
                case 58:
                    return "unregisterCallback";
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
                parcel.enforceInterface(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> tvInteractiveAppServiceList = getTvInteractiveAppServiceList(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(tvInteractiveAppServiceList, 1);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.tv.interactive.AppLinkInfo> appLinkInfoList = getAppLinkInfoList(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(appLinkInfoList, 1);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    android.media.tv.interactive.AppLinkInfo appLinkInfo = (android.media.tv.interactive.AppLinkInfo) parcel.readTypedObject(android.media.tv.interactive.AppLinkInfo.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerAppLinkInfo(readString, appLinkInfo, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    android.media.tv.interactive.AppLinkInfo appLinkInfo2 = (android.media.tv.interactive.AppLinkInfo) parcel.readTypedObject(android.media.tv.interactive.AppLinkInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterAppLinkInfo(readString2, appLinkInfo2, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAppLinkCommand(readString3, bundle, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startInteractiveApp(readStrongBinder, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopInteractiveApp(readStrongBinder2, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    resetInteractiveApp(readStrongBinder3, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.os.IBinder readStrongBinder4 = parcel.readStrongBinder();
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createBiInteractiveApp(readStrongBinder4, uri, bundle2, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.os.IBinder readStrongBinder5 = parcel.readStrongBinder();
                    java.lang.String readString4 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    destroyBiInteractiveApp(readStrongBinder5, readString4, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.os.IBinder readStrongBinder6 = parcel.readStrongBinder();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTeletextAppEnabled(readStrongBinder6, readBoolean, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    android.os.IBinder readStrongBinder7 = parcel.readStrongBinder();
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentVideoBounds(readStrongBinder7, rect, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    android.os.IBinder readStrongBinder8 = parcel.readStrongBinder();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelUri(readStrongBinder8, uri2, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.os.IBinder readStrongBinder9 = parcel.readStrongBinder();
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentChannelLcn(readStrongBinder9, readInt14, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.os.IBinder readStrongBinder10 = parcel.readStrongBinder();
                    float readFloat = parcel.readFloat();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendStreamVolume(readStrongBinder10, readFloat, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.os.IBinder readStrongBinder11 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTrackInfoList(readStrongBinder11, createTypedArrayList, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.os.IBinder readStrongBinder12 = parcel.readStrongBinder();
                    java.lang.String readString5 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCurrentTvInputId(readStrongBinder12, readString5, readInt18);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.os.IBinder readStrongBinder13 = parcel.readStrongBinder();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTimeShiftMode(readStrongBinder13, readInt19, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.os.IBinder readStrongBinder14 = parcel.readStrongBinder();
                    float[] createFloatArray = parcel.createFloatArray();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendAvailableSpeeds(readStrongBinder14, createFloatArray, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.os.IBinder readStrongBinder15 = parcel.readStrongBinder();
                    java.lang.String readString6 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSigningResult(readStrongBinder15, readString6, createByteArray, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.os.IBinder readStrongBinder16 = parcel.readStrongBinder();
                    java.lang.String readString7 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendCertificate(readStrongBinder16, readString7, readInt23, bundle3, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.os.IBinder readStrongBinder17 = parcel.readStrongBinder();
                    android.media.tv.TvRecordingInfo tvRecordingInfo = (android.media.tv.TvRecordingInfo) parcel.readTypedObject(android.media.tv.TvRecordingInfo.CREATOR);
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfo(readStrongBinder17, tvRecordingInfo, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.os.IBinder readStrongBinder18 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList2 = parcel.createTypedArrayList(android.media.tv.TvRecordingInfo.CREATOR);
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendTvRecordingInfoList(readStrongBinder18, createTypedArrayList2, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.os.IBinder readStrongBinder19 = parcel.readStrongBinder();
                    java.lang.String readString8 = parcel.readString();
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyError(readStrongBinder19, readString8, bundle4, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.os.IBinder readStrongBinder20 = parcel.readStrongBinder();
                    android.media.PlaybackParams playbackParams = (android.media.PlaybackParams) parcel.readTypedObject(android.media.PlaybackParams.CREATOR);
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftPlaybackParams(readStrongBinder20, playbackParams, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.os.IBinder readStrongBinder21 = parcel.readStrongBinder();
                    java.lang.String readString9 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStatusChanged(readStrongBinder21, readString9, readInt29, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder22 = parcel.readStrongBinder();
                    java.lang.String readString10 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftStartPositionChanged(readStrongBinder22, readString10, readLong, readInt31);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder23 = parcel.readStrongBinder();
                    java.lang.String readString11 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTimeShiftCurrentPositionChanged(readStrongBinder23, readString11, readLong2, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder24 = parcel.readStrongBinder();
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingConnectionFailed(readStrongBinder24, readString12, readString13, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.os.IBinder readStrongBinder25 = parcel.readStrongBinder();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingDisconnected(readStrongBinder25, readString14, readString15, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.os.IBinder readStrongBinder26 = parcel.readStrongBinder();
                    java.lang.String readString16 = parcel.readString();
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingTuned(readStrongBinder26, readString16, uri3, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.os.IBinder readStrongBinder27 = parcel.readStrongBinder();
                    java.lang.String readString17 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingError(readStrongBinder27, readString17, readInt36, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.os.IBinder readStrongBinder28 = parcel.readStrongBinder();
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingScheduled(readStrongBinder28, readString18, readString19, readInt38);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.media.tv.interactive.ITvInteractiveAppClient asInterface = android.media.tv.interactive.ITvInteractiveAppClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString20 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createSession(asInterface, readString20, readInt39, readInt40, readInt41);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.os.IBinder readStrongBinder29 = parcel.readStrongBinder();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseSession(readStrongBinder29, readInt42);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.os.IBinder readStrongBinder30 = parcel.readStrongBinder();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTuned(readStrongBinder30, uri4, readInt43);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.os.IBinder readStrongBinder31 = parcel.readStrongBinder();
                    int readInt44 = parcel.readInt();
                    java.lang.String readString21 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTrackSelected(readStrongBinder31, readInt44, readString21, readInt45);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.os.IBinder readStrongBinder32 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList3 = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTracksChanged(readStrongBinder32, createTypedArrayList3, readInt46);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    android.os.IBinder readStrongBinder33 = parcel.readStrongBinder();
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoAvailable(readStrongBinder33, readInt47);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    android.os.IBinder readStrongBinder34 = parcel.readStrongBinder();
                    int readInt48 = parcel.readInt();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoUnavailable(readStrongBinder34, readInt48, readInt49);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    android.os.IBinder readStrongBinder35 = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVideoFreezeUpdated(readStrongBinder35, readBoolean2, readInt50);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    android.os.IBinder readStrongBinder36 = parcel.readStrongBinder();
                    int readInt51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyContentAllowed(readStrongBinder36, readInt51);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    android.os.IBinder readStrongBinder37 = parcel.readStrongBinder();
                    java.lang.String readString22 = parcel.readString();
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyContentBlocked(readStrongBinder37, readString22, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    android.os.IBinder readStrongBinder38 = parcel.readStrongBinder();
                    int readInt53 = parcel.readInt();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifySignalStrength(readStrongBinder38, readInt53, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    android.os.IBinder readStrongBinder39 = parcel.readStrongBinder();
                    java.lang.String readString23 = parcel.readString();
                    java.lang.String readString24 = parcel.readString();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStarted(readStrongBinder39, readString23, readString24, readInt55);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    android.os.IBinder readStrongBinder40 = parcel.readStrongBinder();
                    java.lang.String readString25 = parcel.readString();
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyRecordingStopped(readStrongBinder40, readString25, readInt56);
                    parcel2.writeNoException();
                    return true;
                case 47:
                    android.os.IBinder readStrongBinder41 = parcel.readStrongBinder();
                    int readInt57 = parcel.readInt();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyTvMessage(readStrongBinder41, readInt57, bundle5, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    android.os.IBinder readStrongBinder42 = parcel.readStrongBinder();
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSurface(readStrongBinder42, surface, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    android.os.IBinder readStrongBinder43 = parcel.readStrongBinder();
                    int readInt60 = parcel.readInt();
                    int readInt61 = parcel.readInt();
                    int readInt62 = parcel.readInt();
                    int readInt63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispatchSurfaceChanged(readStrongBinder43, readInt60, readInt61, readInt62, readInt63);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    android.os.IBinder readStrongBinder44 = parcel.readStrongBinder();
                    android.media.tv.BroadcastInfoResponse broadcastInfoResponse = (android.media.tv.BroadcastInfoResponse) parcel.readTypedObject(android.media.tv.BroadcastInfoResponse.CREATOR);
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyBroadcastInfoResponse(readStrongBinder44, broadcastInfoResponse, readInt64);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    android.os.IBinder readStrongBinder45 = parcel.readStrongBinder();
                    android.media.tv.AdResponse adResponse = (android.media.tv.AdResponse) parcel.readTypedObject(android.media.tv.AdResponse.CREATOR);
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdResponse(readStrongBinder45, adResponse, readInt65);
                    parcel2.writeNoException();
                    return true;
                case 52:
                    android.os.IBinder readStrongBinder46 = parcel.readStrongBinder();
                    android.media.tv.AdBuffer adBuffer = (android.media.tv.AdBuffer) parcel.readTypedObject(android.media.tv.AdBuffer.CREATOR);
                    int readInt66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAdBufferConsumed(readStrongBinder46, adBuffer, readInt66);
                    parcel2.writeNoException();
                    return true;
                case 53:
                    android.os.IBinder readStrongBinder47 = parcel.readStrongBinder();
                    java.util.ArrayList createTypedArrayList4 = parcel.createTypedArrayList(android.media.tv.TvTrackInfo.CREATOR);
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSelectedTrackInfo(readStrongBinder47, createTypedArrayList4, readInt67);
                    parcel2.writeNoException();
                    return true;
                case 54:
                    android.os.IBinder readStrongBinder48 = parcel.readStrongBinder();
                    android.os.IBinder readStrongBinder49 = parcel.readStrongBinder();
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createMediaView(readStrongBinder48, readStrongBinder49, rect2, readInt68);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    android.os.IBinder readStrongBinder50 = parcel.readStrongBinder();
                    android.graphics.Rect rect3 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    int readInt69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    relayoutMediaView(readStrongBinder50, rect3, readInt69);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    android.os.IBinder readStrongBinder51 = parcel.readStrongBinder();
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeMediaView(readStrongBinder51, readInt70);
                    parcel2.writeNoException();
                    return true;
                case 57:
                    android.media.tv.interactive.ITvInteractiveAppManagerCallback asInterface2 = android.media.tv.interactive.ITvInteractiveAppManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface2, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 58:
                    android.media.tv.interactive.ITvInteractiveAppManagerCallback asInterface3 = android.media.tv.interactive.ITvInteractiveAppManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterCallback(asInterface3, readInt72);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.interactive.ITvInteractiveAppManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR;
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public java.util.List<android.media.tv.interactive.TvInteractiveAppServiceInfo> getTvInteractiveAppServiceList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.interactive.TvInteractiveAppServiceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public java.util.List<android.media.tv.interactive.AppLinkInfo> getAppLinkInfoList(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.tv.interactive.AppLinkInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void registerAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(appLinkInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void unregisterAppLinkInfo(java.lang.String str, android.media.tv.interactive.AppLinkInfo appLinkInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(appLinkInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendAppLinkCommand(java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void startInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void stopInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void resetInteractiveApp(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createBiInteractiveApp(android.os.IBinder iBinder, android.net.Uri uri, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void destroyBiInteractiveApp(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void setTeletextAppEnabled(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentVideoBounds(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentChannelUri(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentChannelLcn(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendStreamVolume(android.os.IBinder iBinder, float f, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloat(f);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTrackInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCurrentTvInputId(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTimeShiftMode(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendAvailableSpeeds(android.os.IBinder iBinder, float[] fArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeFloatArray(fArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendSigningResult(android.os.IBinder iBinder, java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendCertificate(android.os.IBinder iBinder, java.lang.String str, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTvRecordingInfo(android.os.IBinder iBinder, android.media.tv.TvRecordingInfo tvRecordingInfo, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(tvRecordingInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendTvRecordingInfoList(android.os.IBinder iBinder, java.util.List<android.media.tv.TvRecordingInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyError(android.os.IBinder iBinder, java.lang.String str, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftPlaybackParams(android.os.IBinder iBinder, android.media.PlaybackParams playbackParams, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(playbackParams, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftStatusChanged(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftStartPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTimeShiftCurrentPositionChanged(android.os.IBinder iBinder, java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingConnectionFailed(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingDisconnected(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingTuned(android.os.IBinder iBinder, java.lang.String str, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingError(android.os.IBinder iBinder, java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingScheduled(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createSession(android.media.tv.interactive.ITvInteractiveAppClient iTvInteractiveAppClient, java.lang.String str, int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void releaseSession(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTuned(android.os.IBinder iBinder, android.net.Uri uri, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTrackSelected(android.os.IBinder iBinder, int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTracksChanged(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoAvailable(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoUnavailable(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyVideoFreezeUpdated(android.os.IBinder iBinder, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyContentAllowed(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyContentBlocked(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifySignalStrength(android.os.IBinder iBinder, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingStarted(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyRecordingStopped(android.os.IBinder iBinder, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyTvMessage(android.os.IBinder iBinder, int i, android.os.Bundle bundle, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void setSurface(android.os.IBinder iBinder, android.view.Surface surface, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(surface, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void dispatchSurfaceChanged(android.os.IBinder iBinder, int i, int i2, int i3, int i4) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyBroadcastInfoResponse(android.os.IBinder iBinder, android.media.tv.BroadcastInfoResponse broadcastInfoResponse, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(broadcastInfoResponse, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyAdResponse(android.os.IBinder iBinder, android.media.tv.AdResponse adResponse, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adResponse, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void notifyAdBufferConsumed(android.os.IBinder iBinder, android.media.tv.AdBuffer adBuffer, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(adBuffer, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void sendSelectedTrackInfo(android.os.IBinder iBinder, java.util.List<android.media.tv.TvTrackInfo> list, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void createMediaView(android.os.IBinder iBinder, android.os.IBinder iBinder2, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void relayoutMediaView(android.os.IBinder iBinder, android.graphics.Rect rect, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void removeMediaView(android.os.IBinder iBinder, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void registerCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.interactive.ITvInteractiveAppManager
            public void unregisterCallback(android.media.tv.interactive.ITvInteractiveAppManagerCallback iTvInteractiveAppManagerCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.interactive.ITvInteractiveAppManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvInteractiveAppManagerCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 57;
        }
    }
}
