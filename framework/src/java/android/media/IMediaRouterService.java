package android.media;

/* loaded from: classes2.dex */
public interface IMediaRouterService extends android.os.IInterface {
    void deselectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException;

    void deselectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException;

    java.util.List<android.media.RoutingSessionInfo> getRemoteSessions(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException;

    android.media.MediaRouterClientState getState(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException;

    java.util.List<android.media.MediaRoute2Info> getSystemRoutes() throws android.os.RemoteException;

    android.media.RoutingSessionInfo getSystemSessionInfo() throws android.os.RemoteException;

    android.media.RoutingSessionInfo getSystemSessionInfoForPackage(java.lang.String str) throws android.os.RemoteException;

    boolean isPlaybackActive(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException;

    void registerClientAsUser(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException;

    void registerClientGroupId(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str) throws android.os.RemoteException;

    void registerManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str) throws android.os.RemoteException;

    void registerProxyRouter(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void registerRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException;

    void releaseSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str) throws android.os.RemoteException;

    void releaseSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException;

    void requestCreateSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    void requestCreateSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i, long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.Bundle bundle, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException;

    void requestSetVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException;

    void requestUpdateVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException;

    void selectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException;

    void selectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException;

    void setBluetoothA2dpOn(android.media.IMediaRouterClient iMediaRouterClient, boolean z) throws android.os.RemoteException;

    void setDiscoveryRequest(android.media.IMediaRouterClient iMediaRouterClient, int i, boolean z) throws android.os.RemoteException;

    void setDiscoveryRequestWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException;

    void setRouteListingPreference(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException;

    void setRouteVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.MediaRoute2Info mediaRoute2Info, int i2) throws android.os.RemoteException;

    void setRouteVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.MediaRoute2Info mediaRoute2Info, int i) throws android.os.RemoteException;

    void setSelectedRoute(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, boolean z) throws android.os.RemoteException;

    void setSessionVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, int i2) throws android.os.RemoteException;

    void setSessionVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, int i) throws android.os.RemoteException;

    boolean showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException;

    void transferToRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException;

    void transferToRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException;

    void unregisterClient(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException;

    void unregisterManager(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException;

    void unregisterRouter2(android.media.IMediaRouter2 iMediaRouter2) throws android.os.RemoteException;

    void updateScanningState(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) throws android.os.RemoteException;

    void updateScanningStateWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i) throws android.os.RemoteException;

    public static class Default implements android.media.IMediaRouterService {
        @Override // android.media.IMediaRouterService
        public void registerClientAsUser(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterClient(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerClientGroupId(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public android.media.MediaRouterClientState getState(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public boolean isPlaybackActive(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public void setBluetoothA2dpOn(android.media.IMediaRouterClient iMediaRouterClient, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequest(android.media.IMediaRouterClient iMediaRouterClient, int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSelectedRoute(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, boolean z) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestSetVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestUpdateVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public java.util.List<android.media.MediaRoute2Info> getSystemRoutes() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public android.media.RoutingSessionInfo getSystemSessionInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public void registerRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterRouter2(android.media.IMediaRouter2 iMediaRouter2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void updateScanningStateWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequestWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteListingPreference(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.MediaRoute2Info mediaRoute2Info, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i, long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.Bundle bundle, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void selectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public java.util.List<android.media.RoutingSessionInfo> getRemoteSessions(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public android.media.RoutingSessionInfo getSystemSessionInfoForPackage(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public void registerManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerProxyRouter(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterManager(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.MediaRoute2Info mediaRoute2Info, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void updateScanningState(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void selectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public boolean showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.IMediaRouterService {
        public static final java.lang.String DESCRIPTOR = "android.media.IMediaRouterService";
        static final int TRANSACTION_deselectRouteWithManager = 34;
        static final int TRANSACTION_deselectRouteWithRouter2 = 21;
        static final int TRANSACTION_getRemoteSessions = 25;
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_getSystemRoutes = 11;
        static final int TRANSACTION_getSystemSessionInfo = 12;
        static final int TRANSACTION_getSystemSessionInfoForPackage = 26;
        static final int TRANSACTION_isPlaybackActive = 5;
        static final int TRANSACTION_registerClientAsUser = 1;
        static final int TRANSACTION_registerClientGroupId = 3;
        static final int TRANSACTION_registerManager = 27;
        static final int TRANSACTION_registerProxyRouter = 28;
        static final int TRANSACTION_registerRouter2 = 13;
        static final int TRANSACTION_releaseSessionWithManager = 37;
        static final int TRANSACTION_releaseSessionWithRouter2 = 24;
        static final int TRANSACTION_requestCreateSessionWithManager = 32;
        static final int TRANSACTION_requestCreateSessionWithRouter2 = 19;
        static final int TRANSACTION_requestSetVolume = 9;
        static final int TRANSACTION_requestUpdateVolume = 10;
        static final int TRANSACTION_selectRouteWithManager = 33;
        static final int TRANSACTION_selectRouteWithRouter2 = 20;
        static final int TRANSACTION_setBluetoothA2dpOn = 6;
        static final int TRANSACTION_setDiscoveryRequest = 7;
        static final int TRANSACTION_setDiscoveryRequestWithRouter2 = 16;
        static final int TRANSACTION_setRouteListingPreference = 17;
        static final int TRANSACTION_setRouteVolumeWithManager = 30;
        static final int TRANSACTION_setRouteVolumeWithRouter2 = 18;
        static final int TRANSACTION_setSelectedRoute = 8;
        static final int TRANSACTION_setSessionVolumeWithManager = 36;
        static final int TRANSACTION_setSessionVolumeWithRouter2 = 23;
        static final int TRANSACTION_showMediaOutputSwitcher = 38;
        static final int TRANSACTION_transferToRouteWithManager = 35;
        static final int TRANSACTION_transferToRouteWithRouter2 = 22;
        static final int TRANSACTION_unregisterClient = 2;
        static final int TRANSACTION_unregisterManager = 29;
        static final int TRANSACTION_unregisterRouter2 = 14;
        static final int TRANSACTION_updateScanningState = 31;
        static final int TRANSACTION_updateScanningStateWithRouter2 = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.media.IMediaRouterService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.IMediaRouterService)) {
                return (android.media.IMediaRouterService) queryLocalInterface;
            }
            return new android.media.IMediaRouterService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerClientAsUser";
                case 2:
                    return "unregisterClient";
                case 3:
                    return "registerClientGroupId";
                case 4:
                    return "getState";
                case 5:
                    return "isPlaybackActive";
                case 6:
                    return "setBluetoothA2dpOn";
                case 7:
                    return "setDiscoveryRequest";
                case 8:
                    return "setSelectedRoute";
                case 9:
                    return "requestSetVolume";
                case 10:
                    return "requestUpdateVolume";
                case 11:
                    return "getSystemRoutes";
                case 12:
                    return "getSystemSessionInfo";
                case 13:
                    return "registerRouter2";
                case 14:
                    return "unregisterRouter2";
                case 15:
                    return "updateScanningStateWithRouter2";
                case 16:
                    return "setDiscoveryRequestWithRouter2";
                case 17:
                    return "setRouteListingPreference";
                case 18:
                    return "setRouteVolumeWithRouter2";
                case 19:
                    return "requestCreateSessionWithRouter2";
                case 20:
                    return "selectRouteWithRouter2";
                case 21:
                    return "deselectRouteWithRouter2";
                case 22:
                    return "transferToRouteWithRouter2";
                case 23:
                    return "setSessionVolumeWithRouter2";
                case 24:
                    return "releaseSessionWithRouter2";
                case 25:
                    return "getRemoteSessions";
                case 26:
                    return "getSystemSessionInfoForPackage";
                case 27:
                    return "registerManager";
                case 28:
                    return "registerProxyRouter";
                case 29:
                    return "unregisterManager";
                case 30:
                    return "setRouteVolumeWithManager";
                case 31:
                    return "updateScanningState";
                case 32:
                    return "requestCreateSessionWithManager";
                case 33:
                    return "selectRouteWithManager";
                case 34:
                    return "deselectRouteWithManager";
                case 35:
                    return "transferToRouteWithManager";
                case 36:
                    return "setSessionVolumeWithManager";
                case 37:
                    return "releaseSessionWithManager";
                case 38:
                    return "showMediaOutputSwitcher";
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
                    android.media.IMediaRouterClient asInterface = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerClientAsUser(asInterface, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.media.IMediaRouterClient asInterface2 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterClient(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.media.IMediaRouterClient asInterface3 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerClientGroupId(asInterface3, readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.media.IMediaRouterClient asInterface4 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.media.MediaRouterClientState state = getState(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 5:
                    android.media.IMediaRouterClient asInterface5 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isPlaybackActive = isPlaybackActive(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPlaybackActive);
                    return true;
                case 6:
                    android.media.IMediaRouterClient asInterface6 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothA2dpOn(asInterface6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.media.IMediaRouterClient asInterface7 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequest(asInterface7, readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.media.IMediaRouterClient asInterface8 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString3 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSelectedRoute(asInterface8, readString3, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.media.IMediaRouterClient asInterface9 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSetVolume(asInterface9, readString4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.IMediaRouterClient asInterface10 = android.media.IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestUpdateVolume(asInterface10, readString5, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    java.util.List<android.media.MediaRoute2Info> systemRoutes = getSystemRoutes();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemRoutes, 1);
                    return true;
                case 12:
                    android.media.RoutingSessionInfo systemSessionInfo = getSystemSessionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfo, 1);
                    return true;
                case 13:
                    android.media.IMediaRouter2 asInterface11 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerRouter2(asInterface11, readString6);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.media.IMediaRouter2 asInterface12 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRouter2(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    android.media.IMediaRouter2 asInterface13 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningStateWithRouter2(asInterface13, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    android.media.IMediaRouter2 asInterface14 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    android.media.RouteDiscoveryPreference routeDiscoveryPreference = (android.media.RouteDiscoveryPreference) parcel.readTypedObject(android.media.RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequestWithRouter2(asInterface14, routeDiscoveryPreference);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.media.IMediaRouter2 asInterface15 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    android.media.RouteListingPreference routeListingPreference = (android.media.RouteListingPreference) parcel.readTypedObject(android.media.RouteListingPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRouteListingPreference(asInterface15, routeListingPreference);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.media.IMediaRouter2 asInterface16 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    android.media.MediaRoute2Info mediaRoute2Info = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithRouter2(asInterface16, mediaRoute2Info, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    android.media.IMediaRouter2 asInterface17 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    long readLong = parcel.readLong();
                    android.media.RoutingSessionInfo routingSessionInfo = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    android.media.MediaRoute2Info mediaRoute2Info2 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithRouter2(asInterface17, readInt7, readLong, routingSessionInfo, mediaRoute2Info2, bundle, userHandle, readString7);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.media.IMediaRouter2 asInterface18 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString8 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info3 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithRouter2(asInterface18, readString8, mediaRoute2Info3);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.media.IMediaRouter2 asInterface19 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString9 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info4 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithRouter2(asInterface19, readString9, mediaRoute2Info4);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    android.media.IMediaRouter2 asInterface20 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString10 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info5 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferToRouteWithRouter2(asInterface20, readString10, mediaRoute2Info5);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    android.media.IMediaRouter2 asInterface21 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithRouter2(asInterface21, readString11, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    android.media.IMediaRouter2 asInterface22 = android.media.IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithRouter2(asInterface22, readString12);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    android.media.IMediaRouter2Manager asInterface23 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    java.util.List<android.media.RoutingSessionInfo> remoteSessions = getRemoteSessions(asInterface23);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(remoteSessions, 1);
                    return true;
                case 26:
                    java.lang.String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.media.RoutingSessionInfo systemSessionInfoForPackage = getSystemSessionInfoForPackage(readString13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfoForPackage, 1);
                    return true;
                case 27:
                    android.media.IMediaRouter2Manager asInterface24 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerManager(asInterface24, readString14);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.media.IMediaRouter2Manager asInterface25 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString15 = parcel.readString();
                    java.lang.String readString16 = parcel.readString();
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerProxyRouter(asInterface25, readString15, readString16, userHandle2);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.media.IMediaRouter2Manager asInterface26 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterManager(asInterface26);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    android.media.IMediaRouter2Manager asInterface27 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    android.media.MediaRoute2Info mediaRoute2Info6 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithManager(asInterface27, readInt9, mediaRoute2Info6, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    android.media.IMediaRouter2Manager asInterface28 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningState(asInterface28, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    android.media.IMediaRouter2Manager asInterface29 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    android.media.RoutingSessionInfo routingSessionInfo2 = (android.media.RoutingSessionInfo) parcel.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                    android.media.MediaRoute2Info mediaRoute2Info7 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithManager(asInterface29, readInt12, routingSessionInfo2, mediaRoute2Info7, userHandle3, readString17);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    android.media.IMediaRouter2Manager asInterface30 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    java.lang.String readString18 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info8 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithManager(asInterface30, readInt13, readString18, mediaRoute2Info8);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    android.media.IMediaRouter2Manager asInterface31 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    java.lang.String readString19 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info9 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithManager(asInterface31, readInt14, readString19, mediaRoute2Info9);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    android.media.IMediaRouter2Manager asInterface32 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    java.lang.String readString20 = parcel.readString();
                    android.media.MediaRoute2Info mediaRoute2Info10 = (android.media.MediaRoute2Info) parcel.readTypedObject(android.media.MediaRoute2Info.CREATOR);
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    java.lang.String readString21 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRouteWithManager(asInterface32, readInt15, readString20, mediaRoute2Info10, userHandle4, readString21);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    android.media.IMediaRouter2Manager asInterface33 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    java.lang.String readString22 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithManager(asInterface33, readInt16, readString22, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    android.media.IMediaRouter2Manager asInterface34 = android.media.IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithManager(asInterface34, readInt18, readString23);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean showMediaOutputSwitcher = showMediaOutputSwitcher(readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showMediaOutputSwitcher);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.IMediaRouterService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.IMediaRouterService.Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouterService
            public void registerClientAsUser(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterClient(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerClientGroupId(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public android.media.MediaRouterClientState getState(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.MediaRouterClientState) obtain2.readTypedObject(android.media.MediaRouterClientState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean isPlaybackActive(android.media.IMediaRouterClient iMediaRouterClient) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setBluetoothA2dpOn(android.media.IMediaRouterClient iMediaRouterClient, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequest(android.media.IMediaRouterClient iMediaRouterClient, int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSelectedRoute(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestSetVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestUpdateVolume(android.media.IMediaRouterClient iMediaRouterClient, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public java.util.List<android.media.MediaRoute2Info> getSystemRoutes() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.MediaRoute2Info.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public android.media.RoutingSessionInfo getSystemSessionInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.RoutingSessionInfo) obtain2.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterRouter2(android.media.IMediaRouter2 iMediaRouter2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningStateWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequestWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteDiscoveryPreference routeDiscoveryPreference) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteListingPreference(android.media.IMediaRouter2 iMediaRouter2, android.media.RouteListingPreference routeListingPreference) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, android.media.MediaRoute2Info mediaRoute2Info, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, int i, long j, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.Bundle bundle, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithRouter2(android.media.IMediaRouter2 iMediaRouter2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public java.util.List<android.media.RoutingSessionInfo> getRemoteSessions(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.media.RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public android.media.RoutingSessionInfo getSystemSessionInfoForPackage(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.media.RoutingSessionInfo) obtain2.readTypedObject(android.media.RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerProxyRouter(android.media.IMediaRouter2Manager iMediaRouter2Manager, java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterManager(android.media.IMediaRouter2Manager iMediaRouter2Manager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.MediaRoute2Info mediaRoute2Info, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningState(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, android.media.RoutingSessionInfo routingSessionInfo, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, android.media.MediaRoute2Info mediaRoute2Info, android.os.UserHandle userHandle, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithManager(android.media.IMediaRouter2Manager iMediaRouter2Manager, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcher(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.IMediaRouterService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 37;
        }
    }
}
