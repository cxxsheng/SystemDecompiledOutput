package android.telephony.ims;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class ImsCallSessionListener {
    private static final java.lang.String TAG = "ImsCallSessionListener";
    private java.util.concurrent.Executor mExecutor = null;
    private final android.telephony.ims.aidl.IImsCallSessionListener mListener;

    public ImsCallSessionListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) {
        this.mListener = iImsCallSessionListener;
    }

    public void callSessionInitiating(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionInitiating(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInitiatingFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInitiatingFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionProgressing(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionProgressing(imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInitiated(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionInitiated(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void callSessionInitiatedFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInitiatedFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTerminated(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionTerminated(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHeld(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHeld(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHoldFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHoldFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionHoldReceived(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHoldReceived(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumed(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumed(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionResumeFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionResumeReceived(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumeReceived(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeStarted(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                if (this.mExecutor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionMergeStarted(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionMergeStarted(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionMergeStarted(iImsCallSession, imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeComplete(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase) {
        if (imsCallSessionImplBase != null) {
            try {
                if (this.mExecutor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionMergeComplete(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null);
    }

    public void callSessionMergeComplete(com.android.ims.internal.IImsCallSession iImsCallSession) {
        try {
            this.mListener.callSessionMergeComplete(iImsCallSession);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMergeFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionMergeFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdated(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdated(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdateFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionUpdateFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUpdateReceived(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdateReceived(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtended(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                if (this.mExecutor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionConferenceExtended(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionConferenceExtended(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtended(iImsCallSession, imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtendFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionConferenceExtendFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceExtendReceived(android.telephony.ims.stub.ImsCallSessionImplBase imsCallSessionImplBase, android.telephony.ims.ImsCallProfile imsCallProfile) {
        if (imsCallSessionImplBase != null) {
            try {
                if (this.mExecutor != null) {
                    imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
                }
            } catch (android.os.RemoteException e) {
                e.rethrowFromSystemServer();
                return;
            }
        }
        this.mListener.callSessionConferenceExtendReceived(imsCallSessionImplBase != null ? imsCallSessionImplBase.getServiceImpl() : null, imsCallProfile);
    }

    public void callSessionConferenceExtendReceived(com.android.ims.internal.IImsCallSession iImsCallSession, android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtendReceived(iImsCallSession, imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInviteParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionInviteParticipantsRequestDelivered();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionInviteParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRemoveParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionRemoveParticipantsRequestDelivered();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRemoveParticipantsRequestFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionConferenceStateUpdated(android.telephony.ims.ImsConferenceState imsConferenceState) {
        try {
            this.mListener.callSessionConferenceStateUpdated(imsConferenceState);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionUssdMessageReceived(int i, java.lang.String str) {
        try {
            this.mListener.callSessionUssdMessageReceived(i, str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void callSessionMayHandover(int i, int i2) {
        onMayHandover(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2));
    }

    public void onMayHandover(int i, int i2) {
        try {
            this.mListener.callSessionMayHandover(i, i2);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void callSessionHandover(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        onHandover(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
    }

    public void onHandover(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandover(i, i2, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    @java.lang.Deprecated
    public void callSessionHandoverFailed(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        onHandoverFailed(android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i), android.telephony.ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
    }

    public void onHandoverFailed(int i, int i2, android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandoverFailed(i, i2, imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTtyModeReceived(int i) {
        try {
            this.mListener.callSessionTtyModeReceived(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionMultipartyStateChanged(boolean z) {
        try {
            this.mListener.callSessionMultipartyStateChanged(z);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionSuppServiceReceived(android.telephony.ims.ImsSuppServiceNotification imsSuppServiceNotification) {
        try {
            this.mListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttModifyRequestReceived(android.telephony.ims.ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionRttModifyRequestReceived(imsCallProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttModifyResponseReceived(int i) {
        try {
            this.mListener.callSessionRttModifyResponseReceived(i);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttMessageReceived(java.lang.String str) {
        try {
            this.mListener.callSessionRttMessageReceived(str);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRttAudioIndicatorChanged(android.telephony.ims.ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callQualityChanged(android.telephony.CallQuality callQuality) {
        try {
            this.mListener.callQualityChanged(callQuality);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionDtmfReceived(char c) {
        if ((c < '0' || c > '9') && ((c < 'A' || c > 'D') && ((c < 'a' || c > 'd') && c != '*' && c != '#'))) {
            throw new java.lang.IllegalArgumentException("DTMF digit must be 0-9, *, #, A, B, C, D");
        }
        try {
            this.mListener.callSessionDtmfReceived(java.lang.Character.toUpperCase(c));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionRtpHeaderExtensionsReceived(java.util.Set<android.telephony.ims.RtpHeaderExtension> set) {
        java.util.Objects.requireNonNull(set, "extensions are required.");
        try {
            this.mListener.callSessionRtpHeaderExtensionsReceived(new java.util.ArrayList(set));
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTransferred() {
        try {
            this.mListener.callSessionTransferred();
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void callSessionTransferFailed(android.telephony.ims.ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionTransferFailed(imsReasonInfo);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void callSessionSendAnbrQuery(int i, int i2, int i3) {
        android.util.Log.d(TAG, "callSessionSendAnbrQuery in imscallsessonListener");
        try {
            this.mListener.callSessionSendAnbrQuery(i, i2, i3);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public final void setDefaultExecutor(java.util.concurrent.Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }
}
