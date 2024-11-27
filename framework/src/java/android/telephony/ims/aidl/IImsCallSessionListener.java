package android.telephony.ims.aidl;

/* loaded from: classes3.dex */
public interface IImsCallSessionListener extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.ims.aidl.IImsCallSessionListener";

    void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException;

    void callSessionConferenceExtendFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionConferenceStateUpdated(android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException;

    void callSessionDtmfReceived(char c) throws android.os.RemoteException;

    void callSessionHandover(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHandoverFailed(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHeld(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionHoldFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHoldReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionInitiated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionInitiatedFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionInitiating(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionInitiatingFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionInviteParticipantsRequestDelivered() throws android.os.RemoteException;

    void callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionMayHandover(int i, int i2) throws android.os.RemoteException;

    void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException;

    void callSessionMergeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionMultipartyStateChanged(boolean z) throws android.os.RemoteException;

    void callSessionProgressing(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void callSessionRemoveParticipantsRequestDelivered() throws android.os.RemoteException;

    void callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionResumeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionResumeReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionResumed(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionRtpHeaderExtensionsReceived(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException;

    void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException;

    void callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException;

    void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException;

    void callSessionSuppServiceReceived(android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException;

    void callSessionTerminated(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionTransferred() throws android.os.RemoteException;

    void callSessionTtyModeReceived(int i) throws android.os.RemoteException;

    void callSessionUpdateFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionUpdateReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionUpdated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionUssdMessageReceived(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.telephony.ims.aidl.IImsCallSessionListener {
        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiating(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatingFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionProgressing(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatedFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTerminated(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHeld(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumed(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUssdMessageReceived(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandover(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandoverFailed(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMayHandover(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTtyModeReceived(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSuppServiceReceived(android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferred() throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionDtmfReceived(char c) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRtpHeaderExtensionsReceived(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.ims.aidl.IImsCallSessionListener {
        static final int TRANSACTION_callQualityChanged = 41;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 20;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 21;
        static final int TRANSACTION_callSessionConferenceExtended = 19;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 26;
        static final int TRANSACTION_callSessionDtmfReceived = 40;
        static final int TRANSACTION_callSessionHandover = 28;
        static final int TRANSACTION_callSessionHandoverFailed = 29;
        static final int TRANSACTION_callSessionHeld = 7;
        static final int TRANSACTION_callSessionHoldFailed = 8;
        static final int TRANSACTION_callSessionHoldReceived = 9;
        static final int TRANSACTION_callSessionInitiated = 4;
        static final int TRANSACTION_callSessionInitiatedFailed = 5;
        static final int TRANSACTION_callSessionInitiating = 1;
        static final int TRANSACTION_callSessionInitiatingFailed = 2;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionMayHandover = 30;
        static final int TRANSACTION_callSessionMergeComplete = 14;
        static final int TRANSACTION_callSessionMergeFailed = 15;
        static final int TRANSACTION_callSessionMergeStarted = 13;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 32;
        static final int TRANSACTION_callSessionProgressing = 3;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 24;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 25;
        static final int TRANSACTION_callSessionResumeFailed = 11;
        static final int TRANSACTION_callSessionResumeReceived = 12;
        static final int TRANSACTION_callSessionResumed = 10;
        static final int TRANSACTION_callSessionRtpHeaderExtensionsReceived = 42;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 37;
        static final int TRANSACTION_callSessionRttMessageReceived = 36;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 34;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 35;
        static final int TRANSACTION_callSessionSendAnbrQuery = 43;
        static final int TRANSACTION_callSessionSuppServiceReceived = 33;
        static final int TRANSACTION_callSessionTerminated = 6;
        static final int TRANSACTION_callSessionTransferFailed = 39;
        static final int TRANSACTION_callSessionTransferred = 38;
        static final int TRANSACTION_callSessionTtyModeReceived = 31;
        static final int TRANSACTION_callSessionUpdateFailed = 17;
        static final int TRANSACTION_callSessionUpdateReceived = 18;
        static final int TRANSACTION_callSessionUpdated = 16;
        static final int TRANSACTION_callSessionUssdMessageReceived = 27;

        public Stub() {
            attachInterface(this, android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
        }

        public static android.telephony.ims.aidl.IImsCallSessionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.ims.aidl.IImsCallSessionListener)) {
                return (android.telephony.ims.aidl.IImsCallSessionListener) queryLocalInterface;
            }
            return new android.telephony.ims.aidl.IImsCallSessionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "callSessionInitiating";
                case 2:
                    return "callSessionInitiatingFailed";
                case 3:
                    return "callSessionProgressing";
                case 4:
                    return "callSessionInitiated";
                case 5:
                    return "callSessionInitiatedFailed";
                case 6:
                    return "callSessionTerminated";
                case 7:
                    return "callSessionHeld";
                case 8:
                    return "callSessionHoldFailed";
                case 9:
                    return "callSessionHoldReceived";
                case 10:
                    return "callSessionResumed";
                case 11:
                    return "callSessionResumeFailed";
                case 12:
                    return "callSessionResumeReceived";
                case 13:
                    return "callSessionMergeStarted";
                case 14:
                    return "callSessionMergeComplete";
                case 15:
                    return "callSessionMergeFailed";
                case 16:
                    return "callSessionUpdated";
                case 17:
                    return "callSessionUpdateFailed";
                case 18:
                    return "callSessionUpdateReceived";
                case 19:
                    return "callSessionConferenceExtended";
                case 20:
                    return "callSessionConferenceExtendFailed";
                case 21:
                    return "callSessionConferenceExtendReceived";
                case 22:
                    return "callSessionInviteParticipantsRequestDelivered";
                case 23:
                    return "callSessionInviteParticipantsRequestFailed";
                case 24:
                    return "callSessionRemoveParticipantsRequestDelivered";
                case 25:
                    return "callSessionRemoveParticipantsRequestFailed";
                case 26:
                    return "callSessionConferenceStateUpdated";
                case 27:
                    return "callSessionUssdMessageReceived";
                case 28:
                    return "callSessionHandover";
                case 29:
                    return "callSessionHandoverFailed";
                case 30:
                    return "callSessionMayHandover";
                case 31:
                    return "callSessionTtyModeReceived";
                case 32:
                    return "callSessionMultipartyStateChanged";
                case 33:
                    return "callSessionSuppServiceReceived";
                case 34:
                    return "callSessionRttModifyRequestReceived";
                case 35:
                    return "callSessionRttModifyResponseReceived";
                case 36:
                    return "callSessionRttMessageReceived";
                case 37:
                    return "callSessionRttAudioIndicatorChanged";
                case 38:
                    return "callSessionTransferred";
                case 39:
                    return "callSessionTransferFailed";
                case 40:
                    return "callSessionDtmfReceived";
                case 41:
                    return "callQualityChanged";
                case 42:
                    return "callSessionRtpHeaderExtensionsReceived";
                case 43:
                    return "callSessionSendAnbrQuery";
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
                parcel.enforceInterface(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiating(imsCallProfile);
                    return true;
                case 2:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiatingFailed(imsReasonInfo);
                    return true;
                case 3:
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionProgressing(imsStreamMediaProfile);
                    return true;
                case 4:
                    android.telephony.ims.ImsCallProfile imsCallProfile2 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiated(imsCallProfile2);
                    return true;
                case 5:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo2 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiatedFailed(imsReasonInfo2);
                    return true;
                case 6:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo3 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTerminated(imsReasonInfo3);
                    return true;
                case 7:
                    android.telephony.ims.ImsCallProfile imsCallProfile3 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHeld(imsCallProfile3);
                    return true;
                case 8:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo4 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldFailed(imsReasonInfo4);
                    return true;
                case 9:
                    android.telephony.ims.ImsCallProfile imsCallProfile4 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldReceived(imsCallProfile4);
                    return true;
                case 10:
                    android.telephony.ims.ImsCallProfile imsCallProfile5 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumed(imsCallProfile5);
                    return true;
                case 11:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo5 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeFailed(imsReasonInfo5);
                    return true;
                case 12:
                    android.telephony.ims.ImsCallProfile imsCallProfile6 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeReceived(imsCallProfile6);
                    return true;
                case 13:
                    com.android.ims.internal.IImsCallSession asInterface = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile7 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeStarted(asInterface, imsCallProfile7);
                    return true;
                case 14:
                    com.android.ims.internal.IImsCallSession asInterface2 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionMergeComplete(asInterface2);
                    return true;
                case 15:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo6 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeFailed(imsReasonInfo6);
                    return true;
                case 16:
                    android.telephony.ims.ImsCallProfile imsCallProfile8 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdated(imsCallProfile8);
                    return true;
                case 17:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo7 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateFailed(imsReasonInfo7);
                    return true;
                case 18:
                    android.telephony.ims.ImsCallProfile imsCallProfile9 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateReceived(imsCallProfile9);
                    return true;
                case 19:
                    com.android.ims.internal.IImsCallSession asInterface3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile10 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtended(asInterface3, imsCallProfile10);
                    return true;
                case 20:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo8 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendFailed(imsReasonInfo8);
                    return true;
                case 21:
                    com.android.ims.internal.IImsCallSession asInterface4 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile11 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendReceived(asInterface4, imsCallProfile11);
                    return true;
                case 22:
                    callSessionInviteParticipantsRequestDelivered();
                    return true;
                case 23:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo9 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestFailed(imsReasonInfo9);
                    return true;
                case 24:
                    callSessionRemoveParticipantsRequestDelivered();
                    return true;
                case 25:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo10 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestFailed(imsReasonInfo10);
                    return true;
                case 26:
                    android.telephony.ims.ImsConferenceState imsConferenceState = (android.telephony.ims.ImsConferenceState) parcel.readTypedObject(android.telephony.ims.ImsConferenceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceStateUpdated(imsConferenceState);
                    return true;
                case 27:
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionUssdMessageReceived(readInt, readString);
                    return true;
                case 28:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo11 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandover(readInt2, readInt3, imsReasonInfo11);
                    return true;
                case 29:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo12 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandoverFailed(readInt4, readInt5, imsReasonInfo12);
                    return true;
                case 30:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionMayHandover(readInt6, readInt7);
                    return true;
                case 31:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionTtyModeReceived(readInt8);
                    return true;
                case 32:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    callSessionMultipartyStateChanged(readBoolean);
                    return true;
                case 33:
                    android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification = (android.telephony.ims.ImsSuppServiceNotification) parcel.readTypedObject(android.telephony.ims.ImsSuppServiceNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionSuppServiceReceived(imsSuppServiceNotification);
                    return true;
                case 34:
                    android.telephony.ims.ImsCallProfile imsCallProfile12 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyRequestReceived(imsCallProfile12);
                    return true;
                case 35:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyResponseReceived(readInt9);
                    return true;
                case 36:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionRttMessageReceived(readString2);
                    return true;
                case 37:
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile2 = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttAudioIndicatorChanged(imsStreamMediaProfile2);
                    return true;
                case 38:
                    callSessionTransferred();
                    return true;
                case 39:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo13 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTransferFailed(imsReasonInfo13);
                    return true;
                case 40:
                    char readInt10 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionDtmfReceived(readInt10);
                    return true;
                case 41:
                    android.telephony.CallQuality callQuality = (android.telephony.CallQuality) parcel.readTypedObject(android.telephony.CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(callQuality);
                    return true;
                case 42:
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.ims.RtpHeaderExtension.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRtpHeaderExtensionsReceived(createTypedArrayList);
                    return true;
                case 43:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionSendAnbrQuery(readInt11, readInt12, readInt13);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.ims.aidl.IImsCallSessionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiating(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiatingFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionProgressing(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiatedFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTerminated(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHeld(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHoldFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHoldReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumed(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumeReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdated(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdateFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdateReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtendFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceStateUpdated(android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsConferenceState, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUssdMessageReceived(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHandover(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHandoverFailed(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMayHandover(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTtyModeReceived(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionSuppServiceReceived(android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsSuppServiceNotification, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTransferred() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionDtmfReceived(char c) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(c);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRtpHeaderExtensionsReceived(java.util.List<android.telephony.ims.RtpHeaderExtension> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.ims.aidl.IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }
    }
}
