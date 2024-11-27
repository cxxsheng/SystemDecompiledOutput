package android.hardware.radio.V1_3;

/* loaded from: classes2.dex */
public interface IRadioResponse extends android.hardware.radio.V1_2.IRadioResponse {
    public static final java.lang.String kInterfaceName = "android.hardware.radio@1.3::IRadioResponse";

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    android.os.IHwBinder asBinder();

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException;

    void enableModemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException;

    void getModemStackStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    java.lang.String interfaceDescriptor() throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void ping() throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws android.os.RemoteException;

    void setSystemSelectionChannelsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException;

    @Override // android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException;

    static android.hardware.radio.V1_3.IRadioResponse asInterface(android.os.IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        android.os.IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.V1_3.IRadioResponse)) {
            return (android.hardware.radio.V1_3.IRadioResponse) queryLocalInterface;
        }
        android.hardware.radio.V1_3.IRadioResponse.Proxy proxy = new android.hardware.radio.V1_3.IRadioResponse.Proxy(iHwBinder);
        try {
            java.util.Iterator<java.lang.String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (android.os.RemoteException e) {
        }
        return null;
    }

    static android.hardware.radio.V1_3.IRadioResponse castFrom(android.os.IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static android.hardware.radio.V1_3.IRadioResponse getService(java.lang.String str, boolean z) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str, z));
    }

    static android.hardware.radio.V1_3.IRadioResponse getService(boolean z) throws android.os.RemoteException {
        return getService("default", z);
    }

    @java.lang.Deprecated
    static android.hardware.radio.V1_3.IRadioResponse getService(java.lang.String str) throws android.os.RemoteException {
        return asInterface(android.os.HwBinder.getService(kInterfaceName, str));
    }

    @java.lang.Deprecated
    static android.hardware.radio.V1_3.IRadioResponse getService() throws android.os.RemoteException {
        return getService("default");
    }

    public static final class Proxy implements android.hardware.radio.V1_3.IRadioResponse {
        private android.os.IHwBinder mRemote;

        public Proxy(android.os.IHwBinder iHwBinder) {
            this.mRemote = (android.os.IHwBinder) java.util.Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this.mRemote;
        }

        public java.lang.String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (android.os.RemoteException e) {
                return "[class or subclass of android.hardware.radio@1.3::IRadioResponse]@Proxy";
            }
        }

        public final boolean equals(java.lang.Object obj) {
            return android.os.HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getIccCardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.CardStatus cardStatus) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPukForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyIccPuk2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void changeIccPinForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void changeIccPin2ForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void supplyNetworkDepersonalizationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCurrentCallsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.Call> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.Call.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void dialResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getIMSIForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupWaitingOrBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void hangupForegroundResumeBackgroundResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void switchWaitingOrHoldingAndActiveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void conferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void rejectCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getLastCallFailCauseResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.LastCallFailCauseInfo lastCallFailCauseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lastCallFailCauseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getSignalStrengthResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SignalStrength signalStrength) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getVoiceRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDataRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getOperatorResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setRadioPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SendSmsResult sendSmsResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendSMSExpectMoreResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SendSmsResult sendSmsResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setupDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccIOForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void cancelPendingUssdResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getClirResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i, int i2) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setClirResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCallForwardStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.CallForwardInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.CallForwardInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCallForwardResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCallWaitingResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeLastIncomingGsmSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acceptCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deactivateDataCallResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setFacilityLockForAppResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setBarringPasswordResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getNetworkSelectionModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setNetworkSelectionModeAutomaticResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setNetworkSelectionModeManualResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAvailableNetworksResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.OperatorInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.OperatorInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(46, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void startDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(47, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void stopDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(48, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getBasebandVersionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(49, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void separateConnectionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(50, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(51, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getMuteResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(52, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getClipResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(53, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDataCallListResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.SetupDataCallResult> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSuppServiceNotificationsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(55, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void writeSmsToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(56, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deleteSmsOnSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(57, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setBandModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(58, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAvailableBandModesResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<java.lang.Integer> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32Vector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(59, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendEnvelopeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(60, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendTerminalResponseToSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(61, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void handleStkCallSetupRequestFromSimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(62, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void explicitCallTransferResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(63, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(64, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getPreferredNetworkTypeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(65, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getNeighboringCidsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.NeighboringCell> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.NeighboringCell.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(66, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setLocationUpdatesResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(67, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(68, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(69, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaRoamingPreferenceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(70, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(71, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getTTYModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(72, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(73, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getPreferredVoicePrivacyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(74, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendCDMAFeatureCodeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(75, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendBurstDtmfResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(76, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SendSmsResult sendSmsResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(77, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeLastIncomingCdmaSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(78, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(79, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setGsmBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(80, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setGsmBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(81, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(82, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaBroadcastConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(83, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCdmaBroadcastActivationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(84, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCDMASubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, java.lang.String str5) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            hwParcel.writeString(str4);
            hwParcel.writeString(str5);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(85, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void writeSmsToRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(86, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void deleteSmsOnRuimResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(87, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getDeviceIdentityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeString(str3);
            hwParcel.writeString(str4);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(88, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void exitEmergencyCallbackModeResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(89, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(90, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSmscAddressResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(91, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void reportSmsMemoryStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(92, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void reportStkServiceIsRunningResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(93, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCdmaSubscriptionSourceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(94, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestIsimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(95, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeIncomingGsmSmsWithPduResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(96, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendEnvelopeWithStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(97, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getVoiceRadioTechnologyResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(98, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getCellInfoListResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.CellInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(99, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setCellInfoListRateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(100, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setInitialAttachApnResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(101, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getImsRegistrationStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(102, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendImsSmsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SendSmsResult sendSmsResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(103, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccTransmitApduBasicChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(104, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccOpenLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i, java.util.ArrayList<java.lang.Byte> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(105, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccCloseLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(106, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void iccTransmitApduLogicalChannelResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(107, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvReadItemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.lang.String str) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeString(str);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(108, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvWriteItemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(109, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvWriteCdmaPrlResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(110, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void nvResetConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(111, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setUiccSubscriptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(112, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setDataAllowedResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(113, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getHardwareConfigResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_0.HardwareConfig> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_0.HardwareConfig.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(114, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestIccSimAuthenticationResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(115, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setDataProfileResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(116, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void requestShutdownResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(117, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.RadioCapability radioCapability) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(118, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setRadioCapabilityResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.RadioCapability radioCapability) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(119, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void startLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.LceStatusInfo lceStatusInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(120, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void stopLceServiceResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.LceStatusInfo lceStatusInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(121, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void pullLceDataResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.LceDataInfo lceDataInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            lceDataInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(122, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getModemActivityInfoResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.ActivityStatsInfo activityStatsInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            activityStatsInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(123, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(124, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void getAllowedCarriersResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z, android.hardware.radio.V1_0.CarrierRestrictions carrierRestrictions) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            carrierRestrictions.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(125, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void sendDeviceStateResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(126, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setIndicationFilterResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(127, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void setSimCardPowerResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(128, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_0.IRadioResponse
        public void acknowledgeRequest(int i) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
            hwParcel.writeInt32(i);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(129, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void setCarrierInfoForImsiEncryptionResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void setSimCardPowerResponse_1_1(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(131, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void startNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(132, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void stopNetworkScanResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(133, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void startKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_1.KeepaliveStatus keepaliveStatus) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            keepaliveStatus.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(134, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_1.IRadioResponse
        public void stopKeepaliveResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(135, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getCellInfoListResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_2.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getIccCardStatusResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.CardStatus cardStatus) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(137, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void setSignalStrengthReportingCriteriaResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(138, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void setLinkCapacityReportingCriteriaResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(139, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getCurrentCallsResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, java.util.ArrayList<android.hardware.radio.V1_2.Call> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.hardware.radio.V1_2.Call.writeVectorToParcel(hwParcel, arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(140, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getSignalStrengthResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.SignalStrength signalStrength) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(141, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getVoiceRegistrationStateResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(142, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_2.IRadioResponse
        public void getDataRegistrationStateResponse_1_2(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.DataRegStateResult dataRegStateResult) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(143, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void setSystemSelectionChannelsResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(144, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void enableModemResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(145, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse
        public void getModemStackStatusResponse(android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo, boolean z) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            hwParcel.writeBool(z);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(146, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public java.util.ArrayList<java.lang.String> interfaceChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public java.lang.String interfaceDescriptor() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public java.util.ArrayList<byte[]> getHashChain() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                java.util.ArrayList<byte[]> arrayList = new java.util.ArrayList<>();
                android.os.HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                android.os.HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) throws android.os.RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void ping() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws android.os.RemoteException {
            android.os.HwParcel hwParcel = new android.os.HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
            android.os.HwParcel hwParcel2 = new android.os.HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) throws android.os.RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends android.os.HwBinder implements android.hardware.radio.V1_3.IRadioResponse {
        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public android.os.IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<java.lang.String> interfaceChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName, android.hardware.radio.V1_2.IRadioResponse.kInterfaceName, android.hardware.radio.V1_1.IRadioResponse.kInterfaceName, android.hardware.radio.V1_0.IRadioResponse.kInterfaceName, android.internal.hidl.base.V1_0.IBase.kInterfaceName));
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public void debug(android.os.NativeHandle nativeHandle, java.util.ArrayList<java.lang.String> arrayList) {
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final java.lang.String interfaceDescriptor() {
            return android.hardware.radio.V1_3.IRadioResponse.kInterfaceName;
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final java.util.ArrayList<byte[]> getHashChain() {
            return new java.util.ArrayList<>(java.util.Arrays.asList(new byte[]{-11, -5, -28, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, -118, -98, 52, 107, -29, 96, 99, -20, -92, -26, -56, 100, 17, 74, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, 111, -74, 72, -124, -37, 3, -3, -40, 37, 121, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, -39, -72}, new byte[]{-38, -116, 106, -23, -111, -58, -92, -78, -124, -52, 110, 68, 83, 50, com.android.internal.midi.MidiConstants.STATUS_PITCH_BEND, 100, -30, -114, -24, com.android.internal.midi.MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -108, -126, -19, 90, -1, -7, -47, 89, -20, 102, -108, -73}, new byte[]{0, 54, 107, 47, -120, -7, -20, 36, 88, 1, 73, 114, -109, -126, 112, -56, 65, 61, 74, -77, 3, 33, -114, 55, -65, 58, -35, 43, -114, 107, -126, -102}, new byte[]{-68, 60, -116, 35, 48, -123, -4, -93, -121, -99, -57, 75, 73, 11, -98, 91, -63, 6, 50, 88, 71, 13, 59, 76, android.hardware.biometrics.face.AcquiredInfo.TILT_TOO_EXTREME, -9, -89, 75, com.android.internal.midi.MidiConstants.STATUS_SONG_POSITION, android.hardware.biometrics.face.AcquiredInfo.START, -53, -67}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, com.android.internal.midi.MidiConstants.STATUS_CHANNEL_PRESSURE, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, com.android.internal.midi.MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, android.hardware.biometrics.face.AcquiredInfo.ROLL_TOO_EXTREME, -109, 36, -72, 59, android.hardware.biometrics.face.AcquiredInfo.FIRST_FRAME_RECEIVED, -54, 76}));
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final android.internal.hidl.base.V1_0.DebugInfo getDebugInfo() {
            android.internal.hidl.base.V1_0.DebugInfo debugInfo = new android.internal.hidl.base.V1_0.DebugInfo();
            debugInfo.pid = android.os.HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.radio.V1_3.IRadioResponse, android.hardware.radio.V1_2.IRadioResponse, android.hardware.radio.V1_1.IRadioResponse, android.hardware.radio.V1_0.IRadioResponse, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            android.os.HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(android.os.IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.os.IHwBinder
        public android.os.IHwInterface queryLocalInterface(java.lang.String str) {
            if (android.hardware.radio.V1_3.IRadioResponse.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(java.lang.String str) throws android.os.RemoteException {
            registerService(str);
        }

        public java.lang.String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int i, android.os.HwParcel hwParcel, android.os.HwParcel hwParcel2, int i2) throws android.os.RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.CardStatus cardStatus = new android.hardware.radio.V1_0.CardStatus();
                    cardStatus.readFromParcel(hwParcel);
                    getIccCardStatusResponse(radioResponseInfo, cardStatus);
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo2 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo2.readFromParcel(hwParcel);
                    supplyIccPinForAppResponse(radioResponseInfo2, hwParcel.readInt32());
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo3 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo3.readFromParcel(hwParcel);
                    supplyIccPukForAppResponse(radioResponseInfo3, hwParcel.readInt32());
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo4 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo4.readFromParcel(hwParcel);
                    supplyIccPin2ForAppResponse(radioResponseInfo4, hwParcel.readInt32());
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo5 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo5.readFromParcel(hwParcel);
                    supplyIccPuk2ForAppResponse(radioResponseInfo5, hwParcel.readInt32());
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo6 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo6.readFromParcel(hwParcel);
                    changeIccPinForAppResponse(radioResponseInfo6, hwParcel.readInt32());
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo7 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo7.readFromParcel(hwParcel);
                    changeIccPin2ForAppResponse(radioResponseInfo7, hwParcel.readInt32());
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo8 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo8.readFromParcel(hwParcel);
                    supplyNetworkDepersonalizationResponse(radioResponseInfo8, hwParcel.readInt32());
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo9 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo9.readFromParcel(hwParcel);
                    getCurrentCallsResponse(radioResponseInfo9, android.hardware.radio.V1_0.Call.readVectorFromParcel(hwParcel));
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo10 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo10.readFromParcel(hwParcel);
                    dialResponse(radioResponseInfo10);
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo11 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo11.readFromParcel(hwParcel);
                    getIMSIForAppResponse(radioResponseInfo11, hwParcel.readString());
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo12 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo12.readFromParcel(hwParcel);
                    hangupConnectionResponse(radioResponseInfo12);
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo13 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo13.readFromParcel(hwParcel);
                    hangupWaitingOrBackgroundResponse(radioResponseInfo13);
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo14 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo14.readFromParcel(hwParcel);
                    hangupForegroundResumeBackgroundResponse(radioResponseInfo14);
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo15 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo15.readFromParcel(hwParcel);
                    switchWaitingOrHoldingAndActiveResponse(radioResponseInfo15);
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo16 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo16.readFromParcel(hwParcel);
                    conferenceResponse(radioResponseInfo16);
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo17 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo17.readFromParcel(hwParcel);
                    rejectCallResponse(radioResponseInfo17);
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo18 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo18.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.LastCallFailCauseInfo lastCallFailCauseInfo = new android.hardware.radio.V1_0.LastCallFailCauseInfo();
                    lastCallFailCauseInfo.readFromParcel(hwParcel);
                    getLastCallFailCauseResponse(radioResponseInfo18, lastCallFailCauseInfo);
                    return;
                case 19:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo19 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo19.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SignalStrength signalStrength = new android.hardware.radio.V1_0.SignalStrength();
                    signalStrength.readFromParcel(hwParcel);
                    getSignalStrengthResponse(radioResponseInfo19, signalStrength);
                    return;
                case 20:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo20 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo20.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult = new android.hardware.radio.V1_0.VoiceRegStateResult();
                    voiceRegStateResult.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse(radioResponseInfo20, voiceRegStateResult);
                    return;
                case 21:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo21 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo21.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult = new android.hardware.radio.V1_0.DataRegStateResult();
                    dataRegStateResult.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse(radioResponseInfo21, dataRegStateResult);
                    return;
                case 22:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo22 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo22.readFromParcel(hwParcel);
                    getOperatorResponse(radioResponseInfo22, hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 23:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo23 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo23.readFromParcel(hwParcel);
                    setRadioPowerResponse(radioResponseInfo23);
                    return;
                case 24:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo24 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo24.readFromParcel(hwParcel);
                    sendDtmfResponse(radioResponseInfo24);
                    return;
                case 25:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo25 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo25.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SendSmsResult sendSmsResult = new android.hardware.radio.V1_0.SendSmsResult();
                    sendSmsResult.readFromParcel(hwParcel);
                    sendSmsResponse(radioResponseInfo25, sendSmsResult);
                    return;
                case 26:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo26 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo26.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SendSmsResult sendSmsResult2 = new android.hardware.radio.V1_0.SendSmsResult();
                    sendSmsResult2.readFromParcel(hwParcel);
                    sendSMSExpectMoreResponse(radioResponseInfo26, sendSmsResult2);
                    return;
                case 27:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo27 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo27.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SetupDataCallResult setupDataCallResult = new android.hardware.radio.V1_0.SetupDataCallResult();
                    setupDataCallResult.readFromParcel(hwParcel);
                    setupDataCallResponse(radioResponseInfo27, setupDataCallResult);
                    return;
                case 28:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo28 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo28.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.IccIoResult iccIoResult = new android.hardware.radio.V1_0.IccIoResult();
                    iccIoResult.readFromParcel(hwParcel);
                    iccIOForAppResponse(radioResponseInfo28, iccIoResult);
                    return;
                case 29:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo29 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo29.readFromParcel(hwParcel);
                    sendUssdResponse(radioResponseInfo29);
                    return;
                case 30:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo30 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo30.readFromParcel(hwParcel);
                    cancelPendingUssdResponse(radioResponseInfo30);
                    return;
                case 31:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo31 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo31.readFromParcel(hwParcel);
                    getClirResponse(radioResponseInfo31, hwParcel.readInt32(), hwParcel.readInt32());
                    return;
                case 32:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo32 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo32.readFromParcel(hwParcel);
                    setClirResponse(radioResponseInfo32);
                    return;
                case 33:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo33 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo33.readFromParcel(hwParcel);
                    getCallForwardStatusResponse(radioResponseInfo33, android.hardware.radio.V1_0.CallForwardInfo.readVectorFromParcel(hwParcel));
                    return;
                case 34:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo34 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo34.readFromParcel(hwParcel);
                    setCallForwardResponse(radioResponseInfo34);
                    return;
                case 35:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo35 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo35.readFromParcel(hwParcel);
                    getCallWaitingResponse(radioResponseInfo35, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 36:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo36 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo36.readFromParcel(hwParcel);
                    setCallWaitingResponse(radioResponseInfo36);
                    return;
                case 37:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo37 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo37.readFromParcel(hwParcel);
                    acknowledgeLastIncomingGsmSmsResponse(radioResponseInfo37);
                    return;
                case 38:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo38 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo38.readFromParcel(hwParcel);
                    acceptCallResponse(radioResponseInfo38);
                    return;
                case 39:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo39 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo39.readFromParcel(hwParcel);
                    deactivateDataCallResponse(radioResponseInfo39);
                    return;
                case 40:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo40 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo40.readFromParcel(hwParcel);
                    getFacilityLockForAppResponse(radioResponseInfo40, hwParcel.readInt32());
                    return;
                case 41:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo41 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo41.readFromParcel(hwParcel);
                    setFacilityLockForAppResponse(radioResponseInfo41, hwParcel.readInt32());
                    return;
                case 42:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo42 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo42.readFromParcel(hwParcel);
                    setBarringPasswordResponse(radioResponseInfo42);
                    return;
                case 43:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo43 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo43.readFromParcel(hwParcel);
                    getNetworkSelectionModeResponse(radioResponseInfo43, hwParcel.readBool());
                    return;
                case 44:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo44 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo44.readFromParcel(hwParcel);
                    setNetworkSelectionModeAutomaticResponse(radioResponseInfo44);
                    return;
                case 45:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo45 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo45.readFromParcel(hwParcel);
                    setNetworkSelectionModeManualResponse(radioResponseInfo45);
                    return;
                case 46:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo46 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo46.readFromParcel(hwParcel);
                    getAvailableNetworksResponse(radioResponseInfo46, android.hardware.radio.V1_0.OperatorInfo.readVectorFromParcel(hwParcel));
                    return;
                case 47:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo47 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo47.readFromParcel(hwParcel);
                    startDtmfResponse(radioResponseInfo47);
                    return;
                case 48:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo48 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo48.readFromParcel(hwParcel);
                    stopDtmfResponse(radioResponseInfo48);
                    return;
                case 49:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo49 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo49.readFromParcel(hwParcel);
                    getBasebandVersionResponse(radioResponseInfo49, hwParcel.readString());
                    return;
                case 50:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo50 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo50.readFromParcel(hwParcel);
                    separateConnectionResponse(radioResponseInfo50);
                    return;
                case 51:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo51 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo51.readFromParcel(hwParcel);
                    setMuteResponse(radioResponseInfo51);
                    return;
                case 52:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo52 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo52.readFromParcel(hwParcel);
                    getMuteResponse(radioResponseInfo52, hwParcel.readBool());
                    return;
                case 53:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo53 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo53.readFromParcel(hwParcel);
                    getClipResponse(radioResponseInfo53, hwParcel.readInt32());
                    return;
                case 54:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo54 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo54.readFromParcel(hwParcel);
                    getDataCallListResponse(radioResponseInfo54, android.hardware.radio.V1_0.SetupDataCallResult.readVectorFromParcel(hwParcel));
                    return;
                case 55:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo55 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo55.readFromParcel(hwParcel);
                    setSuppServiceNotificationsResponse(radioResponseInfo55);
                    return;
                case 56:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo56 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo56.readFromParcel(hwParcel);
                    writeSmsToSimResponse(radioResponseInfo56, hwParcel.readInt32());
                    return;
                case 57:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo57 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo57.readFromParcel(hwParcel);
                    deleteSmsOnSimResponse(radioResponseInfo57);
                    return;
                case 58:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo58 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo58.readFromParcel(hwParcel);
                    setBandModeResponse(radioResponseInfo58);
                    return;
                case 59:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo59 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo59.readFromParcel(hwParcel);
                    getAvailableBandModesResponse(radioResponseInfo59, hwParcel.readInt32Vector());
                    return;
                case 60:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo60 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo60.readFromParcel(hwParcel);
                    sendEnvelopeResponse(radioResponseInfo60, hwParcel.readString());
                    return;
                case 61:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo61 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo61.readFromParcel(hwParcel);
                    sendTerminalResponseToSimResponse(radioResponseInfo61);
                    return;
                case 62:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo62 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo62.readFromParcel(hwParcel);
                    handleStkCallSetupRequestFromSimResponse(radioResponseInfo62);
                    return;
                case 63:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo63 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo63.readFromParcel(hwParcel);
                    explicitCallTransferResponse(radioResponseInfo63);
                    return;
                case 64:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo64 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo64.readFromParcel(hwParcel);
                    setPreferredNetworkTypeResponse(radioResponseInfo64);
                    return;
                case 65:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo65 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo65.readFromParcel(hwParcel);
                    getPreferredNetworkTypeResponse(radioResponseInfo65, hwParcel.readInt32());
                    return;
                case 66:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo66 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo66.readFromParcel(hwParcel);
                    getNeighboringCidsResponse(radioResponseInfo66, android.hardware.radio.V1_0.NeighboringCell.readVectorFromParcel(hwParcel));
                    return;
                case 67:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo67 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo67.readFromParcel(hwParcel);
                    setLocationUpdatesResponse(radioResponseInfo67);
                    return;
                case 68:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo68 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo68.readFromParcel(hwParcel);
                    setCdmaSubscriptionSourceResponse(radioResponseInfo68);
                    return;
                case 69:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo69 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo69.readFromParcel(hwParcel);
                    setCdmaRoamingPreferenceResponse(radioResponseInfo69);
                    return;
                case 70:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo70 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo70.readFromParcel(hwParcel);
                    getCdmaRoamingPreferenceResponse(radioResponseInfo70, hwParcel.readInt32());
                    return;
                case 71:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo71 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo71.readFromParcel(hwParcel);
                    setTTYModeResponse(radioResponseInfo71);
                    return;
                case 72:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo72 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo72.readFromParcel(hwParcel);
                    getTTYModeResponse(radioResponseInfo72, hwParcel.readInt32());
                    return;
                case 73:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo73 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo73.readFromParcel(hwParcel);
                    setPreferredVoicePrivacyResponse(radioResponseInfo73);
                    return;
                case 74:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo74 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo74.readFromParcel(hwParcel);
                    getPreferredVoicePrivacyResponse(radioResponseInfo74, hwParcel.readBool());
                    return;
                case 75:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo75 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo75.readFromParcel(hwParcel);
                    sendCDMAFeatureCodeResponse(radioResponseInfo75);
                    return;
                case 76:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo76 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo76.readFromParcel(hwParcel);
                    sendBurstDtmfResponse(radioResponseInfo76);
                    return;
                case 77:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo77 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo77.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SendSmsResult sendSmsResult3 = new android.hardware.radio.V1_0.SendSmsResult();
                    sendSmsResult3.readFromParcel(hwParcel);
                    sendCdmaSmsResponse(radioResponseInfo77, sendSmsResult3);
                    return;
                case 78:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo78 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo78.readFromParcel(hwParcel);
                    acknowledgeLastIncomingCdmaSmsResponse(radioResponseInfo78);
                    return;
                case 79:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo79 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo79.readFromParcel(hwParcel);
                    getGsmBroadcastConfigResponse(radioResponseInfo79, android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 80:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo80 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo80.readFromParcel(hwParcel);
                    setGsmBroadcastConfigResponse(radioResponseInfo80);
                    return;
                case 81:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo81 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo81.readFromParcel(hwParcel);
                    setGsmBroadcastActivationResponse(radioResponseInfo81);
                    return;
                case 82:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo82 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo82.readFromParcel(hwParcel);
                    getCdmaBroadcastConfigResponse(radioResponseInfo82, android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo.readVectorFromParcel(hwParcel));
                    return;
                case 83:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo83 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo83.readFromParcel(hwParcel);
                    setCdmaBroadcastConfigResponse(radioResponseInfo83);
                    return;
                case 84:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo84 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo84.readFromParcel(hwParcel);
                    setCdmaBroadcastActivationResponse(radioResponseInfo84);
                    return;
                case 85:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo85 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo85.readFromParcel(hwParcel);
                    getCDMASubscriptionResponse(radioResponseInfo85, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 86:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo86 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo86.readFromParcel(hwParcel);
                    writeSmsToRuimResponse(radioResponseInfo86, hwParcel.readInt32());
                    return;
                case 87:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo87 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo87.readFromParcel(hwParcel);
                    deleteSmsOnRuimResponse(radioResponseInfo87);
                    return;
                case 88:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo88 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo88.readFromParcel(hwParcel);
                    getDeviceIdentityResponse(radioResponseInfo88, hwParcel.readString(), hwParcel.readString(), hwParcel.readString(), hwParcel.readString());
                    return;
                case 89:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo89 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo89.readFromParcel(hwParcel);
                    exitEmergencyCallbackModeResponse(radioResponseInfo89);
                    return;
                case 90:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo90 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo90.readFromParcel(hwParcel);
                    getSmscAddressResponse(radioResponseInfo90, hwParcel.readString());
                    return;
                case 91:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo91 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo91.readFromParcel(hwParcel);
                    setSmscAddressResponse(radioResponseInfo91);
                    return;
                case 92:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo92 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo92.readFromParcel(hwParcel);
                    reportSmsMemoryStatusResponse(radioResponseInfo92);
                    return;
                case 93:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo93 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo93.readFromParcel(hwParcel);
                    reportStkServiceIsRunningResponse(radioResponseInfo93);
                    return;
                case 94:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo94 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo94.readFromParcel(hwParcel);
                    getCdmaSubscriptionSourceResponse(radioResponseInfo94, hwParcel.readInt32());
                    return;
                case 95:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo95 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo95.readFromParcel(hwParcel);
                    requestIsimAuthenticationResponse(radioResponseInfo95, hwParcel.readString());
                    return;
                case 96:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo96 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo96.readFromParcel(hwParcel);
                    acknowledgeIncomingGsmSmsWithPduResponse(radioResponseInfo96);
                    return;
                case 97:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo97 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo97.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.IccIoResult iccIoResult2 = new android.hardware.radio.V1_0.IccIoResult();
                    iccIoResult2.readFromParcel(hwParcel);
                    sendEnvelopeWithStatusResponse(radioResponseInfo97, iccIoResult2);
                    return;
                case 98:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo98 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo98.readFromParcel(hwParcel);
                    getVoiceRadioTechnologyResponse(radioResponseInfo98, hwParcel.readInt32());
                    return;
                case 99:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo99 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo99.readFromParcel(hwParcel);
                    getCellInfoListResponse(radioResponseInfo99, android.hardware.radio.V1_0.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 100:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo100 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo100.readFromParcel(hwParcel);
                    setCellInfoListRateResponse(radioResponseInfo100);
                    return;
                case 101:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo101 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo101.readFromParcel(hwParcel);
                    setInitialAttachApnResponse(radioResponseInfo101);
                    return;
                case 102:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo102 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo102.readFromParcel(hwParcel);
                    getImsRegistrationStateResponse(radioResponseInfo102, hwParcel.readBool(), hwParcel.readInt32());
                    return;
                case 103:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo103 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo103.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.SendSmsResult sendSmsResult4 = new android.hardware.radio.V1_0.SendSmsResult();
                    sendSmsResult4.readFromParcel(hwParcel);
                    sendImsSmsResponse(radioResponseInfo103, sendSmsResult4);
                    return;
                case 104:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo104 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo104.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.IccIoResult iccIoResult3 = new android.hardware.radio.V1_0.IccIoResult();
                    iccIoResult3.readFromParcel(hwParcel);
                    iccTransmitApduBasicChannelResponse(radioResponseInfo104, iccIoResult3);
                    return;
                case 105:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo105 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo105.readFromParcel(hwParcel);
                    iccOpenLogicalChannelResponse(radioResponseInfo105, hwParcel.readInt32(), hwParcel.readInt8Vector());
                    return;
                case 106:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo106 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo106.readFromParcel(hwParcel);
                    iccCloseLogicalChannelResponse(radioResponseInfo106);
                    return;
                case 107:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo107 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo107.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.IccIoResult iccIoResult4 = new android.hardware.radio.V1_0.IccIoResult();
                    iccIoResult4.readFromParcel(hwParcel);
                    iccTransmitApduLogicalChannelResponse(radioResponseInfo107, iccIoResult4);
                    return;
                case 108:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo108 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo108.readFromParcel(hwParcel);
                    nvReadItemResponse(radioResponseInfo108, hwParcel.readString());
                    return;
                case 109:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo109 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo109.readFromParcel(hwParcel);
                    nvWriteItemResponse(radioResponseInfo109);
                    return;
                case 110:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo110 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo110.readFromParcel(hwParcel);
                    nvWriteCdmaPrlResponse(radioResponseInfo110);
                    return;
                case 111:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo111 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo111.readFromParcel(hwParcel);
                    nvResetConfigResponse(radioResponseInfo111);
                    return;
                case 112:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo112 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo112.readFromParcel(hwParcel);
                    setUiccSubscriptionResponse(radioResponseInfo112);
                    return;
                case 113:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo113 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo113.readFromParcel(hwParcel);
                    setDataAllowedResponse(radioResponseInfo113);
                    return;
                case 114:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo114 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo114.readFromParcel(hwParcel);
                    getHardwareConfigResponse(radioResponseInfo114, android.hardware.radio.V1_0.HardwareConfig.readVectorFromParcel(hwParcel));
                    return;
                case 115:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo115 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo115.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.IccIoResult iccIoResult5 = new android.hardware.radio.V1_0.IccIoResult();
                    iccIoResult5.readFromParcel(hwParcel);
                    requestIccSimAuthenticationResponse(radioResponseInfo115, iccIoResult5);
                    return;
                case 116:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo116 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo116.readFromParcel(hwParcel);
                    setDataProfileResponse(radioResponseInfo116);
                    return;
                case 117:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo117 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo117.readFromParcel(hwParcel);
                    requestShutdownResponse(radioResponseInfo117);
                    return;
                case 118:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo118 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo118.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.RadioCapability radioCapability = new android.hardware.radio.V1_0.RadioCapability();
                    radioCapability.readFromParcel(hwParcel);
                    getRadioCapabilityResponse(radioResponseInfo118, radioCapability);
                    return;
                case 119:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo119 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo119.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.RadioCapability radioCapability2 = new android.hardware.radio.V1_0.RadioCapability();
                    radioCapability2.readFromParcel(hwParcel);
                    setRadioCapabilityResponse(radioResponseInfo119, radioCapability2);
                    return;
                case 120:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo120 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo120.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.LceStatusInfo lceStatusInfo = new android.hardware.radio.V1_0.LceStatusInfo();
                    lceStatusInfo.readFromParcel(hwParcel);
                    startLceServiceResponse(radioResponseInfo120, lceStatusInfo);
                    return;
                case 121:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo121 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo121.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.LceStatusInfo lceStatusInfo2 = new android.hardware.radio.V1_0.LceStatusInfo();
                    lceStatusInfo2.readFromParcel(hwParcel);
                    stopLceServiceResponse(radioResponseInfo121, lceStatusInfo2);
                    return;
                case 122:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo122 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo122.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.LceDataInfo lceDataInfo = new android.hardware.radio.V1_0.LceDataInfo();
                    lceDataInfo.readFromParcel(hwParcel);
                    pullLceDataResponse(radioResponseInfo122, lceDataInfo);
                    return;
                case 123:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo123 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo123.readFromParcel(hwParcel);
                    android.hardware.radio.V1_0.ActivityStatsInfo activityStatsInfo = new android.hardware.radio.V1_0.ActivityStatsInfo();
                    activityStatsInfo.readFromParcel(hwParcel);
                    getModemActivityInfoResponse(radioResponseInfo123, activityStatsInfo);
                    return;
                case 124:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo124 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo124.readFromParcel(hwParcel);
                    setAllowedCarriersResponse(radioResponseInfo124, hwParcel.readInt32());
                    return;
                case 125:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo125 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo125.readFromParcel(hwParcel);
                    boolean readBool = hwParcel.readBool();
                    android.hardware.radio.V1_0.CarrierRestrictions carrierRestrictions = new android.hardware.radio.V1_0.CarrierRestrictions();
                    carrierRestrictions.readFromParcel(hwParcel);
                    getAllowedCarriersResponse(radioResponseInfo125, readBool, carrierRestrictions);
                    return;
                case 126:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo126 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo126.readFromParcel(hwParcel);
                    sendDeviceStateResponse(radioResponseInfo126);
                    return;
                case 127:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo127 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo127.readFromParcel(hwParcel);
                    setIndicationFilterResponse(radioResponseInfo127);
                    return;
                case 128:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo128 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo128.readFromParcel(hwParcel);
                    setSimCardPowerResponse(radioResponseInfo128);
                    return;
                case 129:
                    hwParcel.enforceInterface(android.hardware.radio.V1_0.IRadioResponse.kInterfaceName);
                    acknowledgeRequest(hwParcel.readInt32());
                    return;
                case 130:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo129 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo129.readFromParcel(hwParcel);
                    setCarrierInfoForImsiEncryptionResponse(radioResponseInfo129);
                    return;
                case 131:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo130 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo130.readFromParcel(hwParcel);
                    setSimCardPowerResponse_1_1(radioResponseInfo130);
                    return;
                case 132:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo131 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo131.readFromParcel(hwParcel);
                    startNetworkScanResponse(radioResponseInfo131);
                    return;
                case 133:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo132 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo132.readFromParcel(hwParcel);
                    stopNetworkScanResponse(radioResponseInfo132);
                    return;
                case 134:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo133 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo133.readFromParcel(hwParcel);
                    android.hardware.radio.V1_1.KeepaliveStatus keepaliveStatus = new android.hardware.radio.V1_1.KeepaliveStatus();
                    keepaliveStatus.readFromParcel(hwParcel);
                    startKeepaliveResponse(radioResponseInfo133, keepaliveStatus);
                    return;
                case 135:
                    hwParcel.enforceInterface(android.hardware.radio.V1_1.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo134 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo134.readFromParcel(hwParcel);
                    stopKeepaliveResponse(radioResponseInfo134);
                    return;
                case 136:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo135 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo135.readFromParcel(hwParcel);
                    getCellInfoListResponse_1_2(radioResponseInfo135, android.hardware.radio.V1_2.CellInfo.readVectorFromParcel(hwParcel));
                    return;
                case 137:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo136 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo136.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.CardStatus cardStatus2 = new android.hardware.radio.V1_2.CardStatus();
                    cardStatus2.readFromParcel(hwParcel);
                    getIccCardStatusResponse_1_2(radioResponseInfo136, cardStatus2);
                    return;
                case 138:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo137 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo137.readFromParcel(hwParcel);
                    setSignalStrengthReportingCriteriaResponse(radioResponseInfo137);
                    return;
                case 139:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo138 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo138.readFromParcel(hwParcel);
                    setLinkCapacityReportingCriteriaResponse(radioResponseInfo138);
                    return;
                case 140:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo139 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo139.readFromParcel(hwParcel);
                    getCurrentCallsResponse_1_2(radioResponseInfo139, android.hardware.radio.V1_2.Call.readVectorFromParcel(hwParcel));
                    return;
                case 141:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo140 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo140.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.SignalStrength signalStrength2 = new android.hardware.radio.V1_2.SignalStrength();
                    signalStrength2.readFromParcel(hwParcel);
                    getSignalStrengthResponse_1_2(radioResponseInfo140, signalStrength2);
                    return;
                case 142:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo141 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo141.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.VoiceRegStateResult voiceRegStateResult2 = new android.hardware.radio.V1_2.VoiceRegStateResult();
                    voiceRegStateResult2.readFromParcel(hwParcel);
                    getVoiceRegistrationStateResponse_1_2(radioResponseInfo141, voiceRegStateResult2);
                    return;
                case 143:
                    hwParcel.enforceInterface(android.hardware.radio.V1_2.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo142 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo142.readFromParcel(hwParcel);
                    android.hardware.radio.V1_2.DataRegStateResult dataRegStateResult2 = new android.hardware.radio.V1_2.DataRegStateResult();
                    dataRegStateResult2.readFromParcel(hwParcel);
                    getDataRegistrationStateResponse_1_2(radioResponseInfo142, dataRegStateResult2);
                    return;
                case 144:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo143 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo143.readFromParcel(hwParcel);
                    setSystemSelectionChannelsResponse(radioResponseInfo143);
                    return;
                case 145:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo144 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo144.readFromParcel(hwParcel);
                    enableModemResponse(radioResponseInfo144);
                    return;
                case 146:
                    hwParcel.enforceInterface(android.hardware.radio.V1_3.IRadioResponse.kInterfaceName);
                    android.hardware.radio.V1_0.RadioResponseInfo radioResponseInfo145 = new android.hardware.radio.V1_0.RadioResponseInfo();
                    radioResponseInfo145.readFromParcel(hwParcel);
                    getModemStackStatusResponse(radioResponseInfo145, hwParcel.readBool());
                    return;
                case 256067662:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<java.lang.String> interfaceChain = interfaceChain();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(interfaceChain);
                    hwParcel2.send();
                    return;
                case 256131655:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 256136003:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.lang.String interfaceDescriptor = interfaceDescriptor();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(interfaceDescriptor);
                    hwParcel2.send();
                    return;
                case 256398152:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    java.util.ArrayList<byte[]> hashChain = getHashChain();
                    hwParcel2.writeStatus(0);
                    android.os.HwBlob hwBlob = new android.os.HwBlob(16);
                    int size = hashChain.size();
                    hwBlob.putInt32(8L, size);
                    hwBlob.putBool(12L, false);
                    android.os.HwBlob hwBlob2 = new android.os.HwBlob(size * 32);
                    for (int i3 = 0; i3 < size; i3++) {
                        long j = i3 * 32;
                        byte[] bArr = hashChain.get(i3);
                        if (bArr == null || bArr.length != 32) {
                            throw new java.lang.IllegalArgumentException("Array element is not of the expected length");
                        }
                        hwBlob2.putInt8Array(j, bArr);
                    }
                    hwBlob.putBlob(0L, hwBlob2);
                    hwParcel2.writeBuffer(hwBlob);
                    hwParcel2.send();
                    return;
                case 256462420:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    setHALInstrumentation();
                    return;
                case 256660548:
                default:
                    return;
                case 256921159:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    ping();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 257049926:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    android.internal.hidl.base.V1_0.DebugInfo debugInfo = getDebugInfo();
                    hwParcel2.writeStatus(0);
                    debugInfo.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 257120595:
                    hwParcel.enforceInterface(android.internal.hidl.base.V1_0.IBase.kInterfaceName);
                    notifySyspropsChanged();
                    return;
            }
        }
    }
}
