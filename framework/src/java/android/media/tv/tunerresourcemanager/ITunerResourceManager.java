package android.media.tv.tunerresourcemanager;

/* loaded from: classes2.dex */
public interface ITunerResourceManager extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.tv.tunerresourcemanager.ITunerResourceManager";

    boolean acquireLock(int i, long j) throws android.os.RemoteException;

    void clearResourceMap(int i) throws android.os.RemoteException;

    int getClientPriority(int i, int i2) throws android.os.RemoteException;

    int getConfigPriority(int i, boolean z) throws android.os.RemoteException;

    int getMaxNumberOfFrontends(int i) throws android.os.RemoteException;

    boolean hasUnusedFrontend(int i) throws android.os.RemoteException;

    boolean isHigherPriority(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) throws android.os.RemoteException;

    boolean isLowestPriority(int i, int i2) throws android.os.RemoteException;

    void registerClientProfile(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws android.os.RemoteException;

    void releaseCasSession(int i, int i2) throws android.os.RemoteException;

    void releaseCiCam(int i, int i2) throws android.os.RemoteException;

    void releaseDemux(int i, int i2) throws android.os.RemoteException;

    void releaseDescrambler(int i, int i2) throws android.os.RemoteException;

    void releaseFrontend(int i, int i2) throws android.os.RemoteException;

    void releaseLnb(int i, int i2) throws android.os.RemoteException;

    boolean releaseLock(int i) throws android.os.RemoteException;

    boolean requestCasSession(android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, int[] iArr) throws android.os.RemoteException;

    boolean requestCiCam(android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, int[] iArr) throws android.os.RemoteException;

    boolean requestDemux(android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, int[] iArr) throws android.os.RemoteException;

    boolean requestDescrambler(android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) throws android.os.RemoteException;

    boolean requestFrontend(android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, int[] iArr) throws android.os.RemoteException;

    boolean requestLnb(android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, int[] iArr) throws android.os.RemoteException;

    void restoreResourceMap(int i) throws android.os.RemoteException;

    void setDemuxInfoList(android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) throws android.os.RemoteException;

    void setFrontendInfoList(android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) throws android.os.RemoteException;

    void setLnbInfoList(int[] iArr) throws android.os.RemoteException;

    boolean setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException;

    void shareFrontend(int i, int i2) throws android.os.RemoteException;

    void storeResourceMap(int i) throws android.os.RemoteException;

    boolean transferOwner(int i, int i2, int i3) throws android.os.RemoteException;

    void unregisterClientProfile(int i) throws android.os.RemoteException;

    void updateCasInfo(int i, int i2) throws android.os.RemoteException;

    boolean updateClientPriority(int i, int i2, int i3) throws android.os.RemoteException;

    public static class Default implements android.media.tv.tunerresourcemanager.ITunerResourceManager {
        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void registerClientProfile(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void unregisterClientProfile(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean updateClientPriority(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean hasUnusedFrontend(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean isLowestPriority(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setFrontendInfoList(android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void updateCasInfo(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setDemuxInfoList(android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void setLnbInfoList(int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestFrontend(android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getMaxNumberOfFrontends(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void shareFrontend(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean transferOwner(int i, int i2, int i3) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestDemux(android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestDescrambler(android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestCasSession(android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestCiCam(android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean requestLnb(android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, int[] iArr) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseFrontend(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseDemux(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseDescrambler(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseCasSession(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseCiCam(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void releaseLnb(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean isHigherPriority(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void storeResourceMap(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void clearResourceMap(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public void restoreResourceMap(int i) throws android.os.RemoteException {
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean acquireLock(int i, long j) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public boolean releaseLock(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getClientPriority(int i, int i2) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
        public int getConfigPriority(int i, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.tv.tunerresourcemanager.ITunerResourceManager {
        static final int TRANSACTION_acquireLock = 30;
        static final int TRANSACTION_clearResourceMap = 28;
        static final int TRANSACTION_getClientPriority = 32;
        static final int TRANSACTION_getConfigPriority = 33;
        static final int TRANSACTION_getMaxNumberOfFrontends = 12;
        static final int TRANSACTION_hasUnusedFrontend = 4;
        static final int TRANSACTION_isHigherPriority = 26;
        static final int TRANSACTION_isLowestPriority = 5;
        static final int TRANSACTION_registerClientProfile = 1;
        static final int TRANSACTION_releaseCasSession = 23;
        static final int TRANSACTION_releaseCiCam = 24;
        static final int TRANSACTION_releaseDemux = 21;
        static final int TRANSACTION_releaseDescrambler = 22;
        static final int TRANSACTION_releaseFrontend = 20;
        static final int TRANSACTION_releaseLnb = 25;
        static final int TRANSACTION_releaseLock = 31;
        static final int TRANSACTION_requestCasSession = 17;
        static final int TRANSACTION_requestCiCam = 18;
        static final int TRANSACTION_requestDemux = 15;
        static final int TRANSACTION_requestDescrambler = 16;
        static final int TRANSACTION_requestFrontend = 10;
        static final int TRANSACTION_requestLnb = 19;
        static final int TRANSACTION_restoreResourceMap = 29;
        static final int TRANSACTION_setDemuxInfoList = 8;
        static final int TRANSACTION_setFrontendInfoList = 6;
        static final int TRANSACTION_setLnbInfoList = 9;
        static final int TRANSACTION_setMaxNumberOfFrontends = 11;
        static final int TRANSACTION_shareFrontend = 13;
        static final int TRANSACTION_storeResourceMap = 27;
        static final int TRANSACTION_transferOwner = 14;
        static final int TRANSACTION_unregisterClientProfile = 2;
        static final int TRANSACTION_updateCasInfo = 7;
        static final int TRANSACTION_updateClientPriority = 3;

        public Stub() {
            attachInterface(this, android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
        }

        public static android.media.tv.tunerresourcemanager.ITunerResourceManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.tv.tunerresourcemanager.ITunerResourceManager)) {
                return (android.media.tv.tunerresourcemanager.ITunerResourceManager) queryLocalInterface;
            }
            return new android.media.tv.tunerresourcemanager.ITunerResourceManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                return true;
            }
            int[] iArr = null;
            switch (i) {
                case 1:
                    android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile = (android.media.tv.tunerresourcemanager.ResourceClientProfile) parcel.readTypedObject(android.media.tv.tunerresourcemanager.ResourceClientProfile.CREATOR);
                    android.media.tv.tunerresourcemanager.IResourcesReclaimListener asInterface = android.media.tv.tunerresourcemanager.IResourcesReclaimListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    if (readInt >= 0) {
                        iArr = new int[readInt];
                    }
                    parcel.enforceNoDataAvail();
                    registerClientProfile(resourceClientProfile, asInterface, iArr);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterClientProfile(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean updateClientPriority = updateClientPriority(readInt3, readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateClientPriority);
                    return true;
                case 4:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUnusedFrontend = hasUnusedFrontend(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUnusedFrontend);
                    return true;
                case 5:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isLowestPriority = isLowestPriority(readInt7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isLowestPriority);
                    return true;
                case 6:
                    android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr = (android.media.tv.tunerresourcemanager.TunerFrontendInfo[]) parcel.createTypedArray(android.media.tv.tunerresourcemanager.TunerFrontendInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setFrontendInfoList(tunerFrontendInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCasInfo(readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr = (android.media.tv.tunerresourcemanager.TunerDemuxInfo[]) parcel.createTypedArray(android.media.tv.tunerresourcemanager.TunerDemuxInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDemuxInfoList(tunerDemuxInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setLnbInfoList(createIntArray);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest = (android.media.tv.tunerresourcemanager.TunerFrontendRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.TunerFrontendRequest.CREATOR);
                    int readInt11 = parcel.readInt();
                    if (readInt11 >= 0) {
                        iArr = new int[readInt11];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestFrontend = requestFrontend(tunerFrontendRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestFrontend);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 11:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean maxNumberOfFrontends = setMaxNumberOfFrontends(readInt12, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(maxNumberOfFrontends);
                    return true;
                case 12:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int maxNumberOfFrontends2 = getMaxNumberOfFrontends(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(maxNumberOfFrontends2);
                    return true;
                case 13:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    shareFrontend(readInt15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean transferOwner = transferOwner(readInt17, readInt18, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(transferOwner);
                    return true;
                case 15:
                    android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest = (android.media.tv.tunerresourcemanager.TunerDemuxRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.TunerDemuxRequest.CREATOR);
                    int readInt20 = parcel.readInt();
                    if (readInt20 >= 0) {
                        iArr = new int[readInt20];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestDemux = requestDemux(tunerDemuxRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestDemux);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 16:
                    android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest = (android.media.tv.tunerresourcemanager.TunerDescramblerRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.TunerDescramblerRequest.CREATOR);
                    int readInt21 = parcel.readInt();
                    if (readInt21 >= 0) {
                        iArr = new int[readInt21];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestDescrambler = requestDescrambler(tunerDescramblerRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestDescrambler);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 17:
                    android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest = (android.media.tv.tunerresourcemanager.CasSessionRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.CasSessionRequest.CREATOR);
                    int readInt22 = parcel.readInt();
                    if (readInt22 >= 0) {
                        iArr = new int[readInt22];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestCasSession = requestCasSession(casSessionRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCasSession);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 18:
                    android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest = (android.media.tv.tunerresourcemanager.TunerCiCamRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.TunerCiCamRequest.CREATOR);
                    int readInt23 = parcel.readInt();
                    if (readInt23 >= 0) {
                        iArr = new int[readInt23];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestCiCam = requestCiCam(tunerCiCamRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestCiCam);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 19:
                    android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest = (android.media.tv.tunerresourcemanager.TunerLnbRequest) parcel.readTypedObject(android.media.tv.tunerresourcemanager.TunerLnbRequest.CREATOR);
                    int readInt24 = parcel.readInt();
                    if (readInt24 >= 0) {
                        iArr = new int[readInt24];
                    }
                    parcel.enforceNoDataAvail();
                    boolean requestLnb = requestLnb(tunerLnbRequest, iArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestLnb);
                    parcel2.writeIntArray(iArr);
                    return true;
                case 20:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseFrontend(readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDemux(readInt27, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt29 = parcel.readInt();
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseDescrambler(readInt29, readInt30);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCasSession(readInt31, readInt32);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt33 = parcel.readInt();
                    int readInt34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseCiCam(readInt33, readInt34);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    releaseLnb(readInt35, readInt36);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2 = (android.media.tv.tunerresourcemanager.ResourceClientProfile) parcel.readTypedObject(android.media.tv.tunerresourcemanager.ResourceClientProfile.CREATOR);
                    android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile3 = (android.media.tv.tunerresourcemanager.ResourceClientProfile) parcel.readTypedObject(android.media.tv.tunerresourcemanager.ResourceClientProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isHigherPriority = isHigherPriority(resourceClientProfile2, resourceClientProfile3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isHigherPriority);
                    return true;
                case 27:
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    storeResourceMap(readInt37);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearResourceMap(readInt38);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    restoreResourceMap(readInt39);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt40 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean acquireLock = acquireLock(readInt40, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(acquireLock);
                    return true;
                case 31:
                    int readInt41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean releaseLock = releaseLock(readInt41);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(releaseLock);
                    return true;
                case 32:
                    int readInt42 = parcel.readInt();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int clientPriority = getClientPriority(readInt42, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(clientPriority);
                    return true;
                case 33:
                    int readInt44 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int configPriority = getConfigPriority(readInt44, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(configPriority);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.tv.tunerresourcemanager.ITunerResourceManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR;
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void registerClientProfile(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.IResourcesReclaimListener iResourcesReclaimListener, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(resourceClientProfile, 0);
                    obtain.writeStrongInterface(iResourcesReclaimListener);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    obtain2.readIntArray(iArr);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void unregisterClientProfile(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean updateClientPriority(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean hasUnusedFrontend(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isLowestPriority(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setFrontendInfoList(android.media.tv.tunerresourcemanager.TunerFrontendInfo[] tunerFrontendInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedArray(tunerFrontendInfoArr, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void updateCasInfo(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setDemuxInfoList(android.media.tv.tunerresourcemanager.TunerDemuxInfo[] tunerDemuxInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedArray(tunerDemuxInfoArr, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void setLnbInfoList(int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestFrontend(android.media.tv.tunerresourcemanager.TunerFrontendRequest tunerFrontendRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerFrontendRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean setMaxNumberOfFrontends(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getMaxNumberOfFrontends(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void shareFrontend(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean transferOwner(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDemux(android.media.tv.tunerresourcemanager.TunerDemuxRequest tunerDemuxRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerDemuxRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestDescrambler(android.media.tv.tunerresourcemanager.TunerDescramblerRequest tunerDescramblerRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerDescramblerRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCasSession(android.media.tv.tunerresourcemanager.CasSessionRequest casSessionRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(casSessionRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestCiCam(android.media.tv.tunerresourcemanager.TunerCiCamRequest tunerCiCamRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerCiCamRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean requestLnb(android.media.tv.tunerresourcemanager.TunerLnbRequest tunerLnbRequest, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(tunerLnbRequest, 0);
                    obtain.writeInt(iArr.length);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    boolean readBoolean = obtain2.readBoolean();
                    obtain2.readIntArray(iArr);
                    return readBoolean;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseFrontend(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDemux(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseDescrambler(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCasSession(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseCiCam(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void releaseLnb(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean isHigherPriority(android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile, android.media.tv.tunerresourcemanager.ResourceClientProfile resourceClientProfile2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeTypedObject(resourceClientProfile, 0);
                    obtain.writeTypedObject(resourceClientProfile2, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void storeResourceMap(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void clearResourceMap(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public void restoreResourceMap(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean acquireLock(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public boolean releaseLock(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getClientPriority(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.tunerresourcemanager.ITunerResourceManager
            public int getConfigPriority(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.media.tv.tunerresourcemanager.ITunerResourceManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
