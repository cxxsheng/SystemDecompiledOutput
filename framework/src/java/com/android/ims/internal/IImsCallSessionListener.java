package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsCallSessionListener extends android.os.IInterface {
    void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException;

    void callSessionConferenceExtendFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionConferenceStateUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException;

    void callSessionHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHandoverFailed(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHeld(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionHoldFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionHoldReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionInviteParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException;

    void callSessionInviteParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionMayHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2) throws android.os.RemoteException;

    void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException;

    void callSessionMergeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionMultipartyStateChanged(com.android.ims.internal.IImsCallSession iImsCallSession, boolean z) throws android.os.RemoteException;

    void callSessionProgressing(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void callSessionRemoveParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException;

    void callSessionRemoveParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionResumeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionResumeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionResumed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException;

    void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException;

    void callSessionRttModifyRequestReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException;

    void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException;

    void callSessionStartFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionSuppServiceReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException;

    void callSessionTerminated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionTransferred() throws android.os.RemoteException;

    void callSessionTtyModeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i) throws android.os.RemoteException;

    void callSessionUpdateFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException;

    void callSessionUpdateReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException;

    void callSessionUssdMessageReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsCallSessionListener {
        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionProgressing(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStartFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTerminated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHeld(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUssdMessageReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandoverFailed(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMayHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTtyModeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(com.android.ims.internal.IImsCallSession iImsCallSession, boolean z) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSuppServiceReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferred() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsCallSessionListener {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsCallSessionListener";
        static final int TRANSACTION_callQualityChanged = 38;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 18;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 19;
        static final int TRANSACTION_callSessionConferenceExtended = 17;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 24;
        static final int TRANSACTION_callSessionHandover = 26;
        static final int TRANSACTION_callSessionHandoverFailed = 27;
        static final int TRANSACTION_callSessionHeld = 5;
        static final int TRANSACTION_callSessionHoldFailed = 6;
        static final int TRANSACTION_callSessionHoldReceived = 7;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 20;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 21;
        static final int TRANSACTION_callSessionMayHandover = 28;
        static final int TRANSACTION_callSessionMergeComplete = 12;
        static final int TRANSACTION_callSessionMergeFailed = 13;
        static final int TRANSACTION_callSessionMergeStarted = 11;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 30;
        static final int TRANSACTION_callSessionProgressing = 1;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionResumeFailed = 9;
        static final int TRANSACTION_callSessionResumeReceived = 10;
        static final int TRANSACTION_callSessionResumed = 8;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 35;
        static final int TRANSACTION_callSessionRttMessageReceived = 34;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 32;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 33;
        static final int TRANSACTION_callSessionSendAnbrQuery = 39;
        static final int TRANSACTION_callSessionStartFailed = 3;
        static final int TRANSACTION_callSessionStarted = 2;
        static final int TRANSACTION_callSessionSuppServiceReceived = 31;
        static final int TRANSACTION_callSessionTerminated = 4;
        static final int TRANSACTION_callSessionTransferFailed = 37;
        static final int TRANSACTION_callSessionTransferred = 36;
        static final int TRANSACTION_callSessionTtyModeReceived = 29;
        static final int TRANSACTION_callSessionUpdateFailed = 15;
        static final int TRANSACTION_callSessionUpdateReceived = 16;
        static final int TRANSACTION_callSessionUpdated = 14;
        static final int TRANSACTION_callSessionUssdMessageReceived = 25;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsCallSessionListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsCallSessionListener)) {
                return (com.android.ims.internal.IImsCallSessionListener) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsCallSessionListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "callSessionProgressing";
                case 2:
                    return "callSessionStarted";
                case 3:
                    return "callSessionStartFailed";
                case 4:
                    return "callSessionTerminated";
                case 5:
                    return "callSessionHeld";
                case 6:
                    return "callSessionHoldFailed";
                case 7:
                    return "callSessionHoldReceived";
                case 8:
                    return "callSessionResumed";
                case 9:
                    return "callSessionResumeFailed";
                case 10:
                    return "callSessionResumeReceived";
                case 11:
                    return "callSessionMergeStarted";
                case 12:
                    return "callSessionMergeComplete";
                case 13:
                    return "callSessionMergeFailed";
                case 14:
                    return "callSessionUpdated";
                case 15:
                    return "callSessionUpdateFailed";
                case 16:
                    return "callSessionUpdateReceived";
                case 17:
                    return "callSessionConferenceExtended";
                case 18:
                    return "callSessionConferenceExtendFailed";
                case 19:
                    return "callSessionConferenceExtendReceived";
                case 20:
                    return "callSessionInviteParticipantsRequestDelivered";
                case 21:
                    return "callSessionInviteParticipantsRequestFailed";
                case 22:
                    return "callSessionRemoveParticipantsRequestDelivered";
                case 23:
                    return "callSessionRemoveParticipantsRequestFailed";
                case 24:
                    return "callSessionConferenceStateUpdated";
                case 25:
                    return "callSessionUssdMessageReceived";
                case 26:
                    return "callSessionHandover";
                case 27:
                    return "callSessionHandoverFailed";
                case 28:
                    return "callSessionMayHandover";
                case 29:
                    return "callSessionTtyModeReceived";
                case 30:
                    return "callSessionMultipartyStateChanged";
                case 31:
                    return "callSessionSuppServiceReceived";
                case 32:
                    return "callSessionRttModifyRequestReceived";
                case 33:
                    return "callSessionRttModifyResponseReceived";
                case 34:
                    return "callSessionRttMessageReceived";
                case 35:
                    return "callSessionRttAudioIndicatorChanged";
                case 36:
                    return "callSessionTransferred";
                case 37:
                    return "callSessionTransferFailed";
                case 38:
                    return "callQualityChanged";
                case 39:
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    com.android.ims.internal.IImsCallSession asInterface = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionProgressing(asInterface, imsStreamMediaProfile);
                    return true;
                case 2:
                    com.android.ims.internal.IImsCallSession asInterface2 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionStarted(asInterface2, imsCallProfile);
                    return true;
                case 3:
                    com.android.ims.internal.IImsCallSession asInterface3 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionStartFailed(asInterface3, imsReasonInfo);
                    return true;
                case 4:
                    com.android.ims.internal.IImsCallSession asInterface4 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo2 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTerminated(asInterface4, imsReasonInfo2);
                    return true;
                case 5:
                    com.android.ims.internal.IImsCallSession asInterface5 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile2 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHeld(asInterface5, imsCallProfile2);
                    return true;
                case 6:
                    com.android.ims.internal.IImsCallSession asInterface6 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo3 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldFailed(asInterface6, imsReasonInfo3);
                    return true;
                case 7:
                    com.android.ims.internal.IImsCallSession asInterface7 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile3 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldReceived(asInterface7, imsCallProfile3);
                    return true;
                case 8:
                    com.android.ims.internal.IImsCallSession asInterface8 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile4 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumed(asInterface8, imsCallProfile4);
                    return true;
                case 9:
                    com.android.ims.internal.IImsCallSession asInterface9 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo4 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeFailed(asInterface9, imsReasonInfo4);
                    return true;
                case 10:
                    com.android.ims.internal.IImsCallSession asInterface10 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile5 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeReceived(asInterface10, imsCallProfile5);
                    return true;
                case 11:
                    com.android.ims.internal.IImsCallSession asInterface11 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.IImsCallSession asInterface12 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile6 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeStarted(asInterface11, asInterface12, imsCallProfile6);
                    return true;
                case 12:
                    com.android.ims.internal.IImsCallSession asInterface13 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionMergeComplete(asInterface13);
                    return true;
                case 13:
                    com.android.ims.internal.IImsCallSession asInterface14 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo5 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeFailed(asInterface14, imsReasonInfo5);
                    return true;
                case 14:
                    com.android.ims.internal.IImsCallSession asInterface15 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile7 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdated(asInterface15, imsCallProfile7);
                    return true;
                case 15:
                    com.android.ims.internal.IImsCallSession asInterface16 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo6 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateFailed(asInterface16, imsReasonInfo6);
                    return true;
                case 16:
                    com.android.ims.internal.IImsCallSession asInterface17 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile8 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateReceived(asInterface17, imsCallProfile8);
                    return true;
                case 17:
                    com.android.ims.internal.IImsCallSession asInterface18 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.IImsCallSession asInterface19 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile9 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtended(asInterface18, asInterface19, imsCallProfile9);
                    return true;
                case 18:
                    com.android.ims.internal.IImsCallSession asInterface20 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo7 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendFailed(asInterface20, imsReasonInfo7);
                    return true;
                case 19:
                    com.android.ims.internal.IImsCallSession asInterface21 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    com.android.ims.internal.IImsCallSession asInterface22 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile10 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendReceived(asInterface21, asInterface22, imsCallProfile10);
                    return true;
                case 20:
                    com.android.ims.internal.IImsCallSession asInterface23 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestDelivered(asInterface23);
                    return true;
                case 21:
                    com.android.ims.internal.IImsCallSession asInterface24 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo8 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestFailed(asInterface24, imsReasonInfo8);
                    return true;
                case 22:
                    com.android.ims.internal.IImsCallSession asInterface25 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestDelivered(asInterface25);
                    return true;
                case 23:
                    com.android.ims.internal.IImsCallSession asInterface26 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsReasonInfo imsReasonInfo9 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestFailed(asInterface26, imsReasonInfo9);
                    return true;
                case 24:
                    com.android.ims.internal.IImsCallSession asInterface27 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsConferenceState imsConferenceState = (android.telephony.ims.ImsConferenceState) parcel.readTypedObject(android.telephony.ims.ImsConferenceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceStateUpdated(asInterface27, imsConferenceState);
                    return true;
                case 25:
                    com.android.ims.internal.IImsCallSession asInterface28 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionUssdMessageReceived(asInterface28, readInt, readString);
                    return true;
                case 26:
                    com.android.ims.internal.IImsCallSession asInterface29 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo10 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandover(asInterface29, readInt2, readInt3, imsReasonInfo10);
                    return true;
                case 27:
                    com.android.ims.internal.IImsCallSession asInterface30 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    android.telephony.ims.ImsReasonInfo imsReasonInfo11 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandoverFailed(asInterface30, readInt4, readInt5, imsReasonInfo11);
                    return true;
                case 28:
                    com.android.ims.internal.IImsCallSession asInterface31 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionMayHandover(asInterface31, readInt6, readInt7);
                    return true;
                case 29:
                    com.android.ims.internal.IImsCallSession asInterface32 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionTtyModeReceived(asInterface32, readInt8);
                    return true;
                case 30:
                    com.android.ims.internal.IImsCallSession asInterface33 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    callSessionMultipartyStateChanged(asInterface33, readBoolean);
                    return true;
                case 31:
                    com.android.ims.internal.IImsCallSession asInterface34 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification = (android.telephony.ims.ImsSuppServiceNotification) parcel.readTypedObject(android.telephony.ims.ImsSuppServiceNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionSuppServiceReceived(asInterface34, imsSuppServiceNotification);
                    return true;
                case 32:
                    com.android.ims.internal.IImsCallSession asInterface35 = com.android.ims.internal.IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    android.telephony.ims.ImsCallProfile imsCallProfile11 = (android.telephony.ims.ImsCallProfile) parcel.readTypedObject(android.telephony.ims.ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyRequestReceived(asInterface35, imsCallProfile11);
                    return true;
                case 33:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyResponseReceived(readInt9);
                    return true;
                case 34:
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionRttMessageReceived(readString2);
                    return true;
                case 35:
                    android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile2 = (android.telephony.ims.ImsStreamMediaProfile) parcel.readTypedObject(android.telephony.ims.ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttAudioIndicatorChanged(imsStreamMediaProfile2);
                    return true;
                case 36:
                    callSessionTransferred();
                    return true;
                case 37:
                    android.telephony.ims.ImsReasonInfo imsReasonInfo12 = (android.telephony.ims.ImsReasonInfo) parcel.readTypedObject(android.telephony.ims.ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTransferFailed(imsReasonInfo12);
                    return true;
                case 38:
                    android.telephony.CallQuality callQuality = (android.telephony.CallQuality) parcel.readTypedObject(android.telephony.CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(callQuality);
                    return true;
                case 39:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionSendAnbrQuery(readInt10, readInt11, readInt12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsCallSessionListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionProgressing(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionStartFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTerminated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHeld(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHoldFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHoldReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdateFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdateReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtendFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, com.android.ims.internal.IImsCallSession iImsCallSession2, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered(com.android.ims.internal.IImsCallSession iImsCallSession) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestFailed(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceStateUpdated(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsConferenceState imsConferenceState) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsConferenceState, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUssdMessageReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHandoverFailed(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMayHandover(com.android.ims.internal.IImsCallSession iImsCallSession, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTtyModeReceived(com.android.ims.internal.IImsCallSession iImsCallSession, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(com.android.ims.internal.IImsCallSession iImsCallSession, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionSuppServiceReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsSuppServiceNotification, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttModifyRequestReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttModifyResponseReceived(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttMessageReceived(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTransferred() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callQualityChanged(android.telephony.CallQuality callQuality) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionSendAnbrQuery(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsCallSessionListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
