package android.telephony.data;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class DataServiceCallback {
    private static final boolean DBG = true;
    public static final int RESULT_ERROR_BUSY = 3;
    public static final int RESULT_ERROR_ILLEGAL_STATE = 4;
    public static final int RESULT_ERROR_INVALID_ARG = 2;
    public static final int RESULT_ERROR_TEMPORARILY_UNAVAILABLE = 5;
    public static final int RESULT_ERROR_UNSUPPORTED = 1;
    public static final int RESULT_SUCCESS = 0;
    private static final java.lang.String TAG = android.telephony.data.DataServiceCallback.class.getSimpleName();
    private final android.telephony.data.IDataServiceCallback mCallback;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResultCode {
    }

    public DataServiceCallback(android.telephony.data.IDataServiceCallback iDataServiceCallback) {
        this.mCallback = iDataServiceCallback;
    }

    public void onSetupDataCallComplete(int i, android.telephony.data.DataCallResponse dataCallResponse) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onSetupDataCallComplete");
                this.mCallback.onSetupDataCallComplete(i, dataCallResponse);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onSetupDataCallComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onSetupDataCallComplete: callback is null!");
    }

    public void onDeactivateDataCallComplete(int i) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onDeactivateDataCallComplete");
                this.mCallback.onDeactivateDataCallComplete(i);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onDeactivateDataCallComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onDeactivateDataCallComplete: callback is null!");
    }

    public void onSetInitialAttachApnComplete(int i) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onSetInitialAttachApnComplete(i);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onSetInitialAttachApnComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onSetInitialAttachApnComplete: callback is null!");
    }

    public void onSetDataProfileComplete(int i) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onSetDataProfileComplete(i);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onSetDataProfileComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onSetDataProfileComplete: callback is null!");
    }

    public void onRequestDataCallListComplete(int i, java.util.List<android.telephony.data.DataCallResponse> list) {
        if (this.mCallback != null) {
            try {
                this.mCallback.onRequestDataCallListComplete(i, list);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onRequestDataCallListComplete on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onRequestDataCallListComplete: callback is null!");
    }

    public void onDataCallListChanged(java.util.List<android.telephony.data.DataCallResponse> list) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onDataCallListChanged");
                this.mCallback.onDataCallListChanged(list);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onDataCallListChanged on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onDataCallListChanged: callback is null!");
    }

    public void onHandoverStarted(int i) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onHandoverStarted");
                this.mCallback.onHandoverStarted(i);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onHandoverStarted on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onHandoverStarted: callback is null!");
    }

    public void onHandoverCancelled(int i) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onHandoverCancelled");
                this.mCallback.onHandoverCancelled(i);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "Failed to onHandoverCancelled on the remote");
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onHandoverCancelled: callback is null!");
    }

    public static java.lang.String resultCodeToString(int i) {
        switch (i) {
            case 0:
                return "RESULT_SUCCESS";
            case 1:
                return "RESULT_ERROR_UNSUPPORTED";
            case 2:
                return "RESULT_ERROR_INVALID_ARG";
            case 3:
                return "RESULT_ERROR_BUSY";
            case 4:
                return "RESULT_ERROR_ILLEGAL_STATE";
            case 5:
                return "RESULT_ERROR_TEMPORARILY_UNAVAILABLE";
            default:
                return "Unknown(" + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public void onApnUnthrottled(java.lang.String str) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onApnUnthrottled");
                this.mCallback.onApnUnthrottled(str);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "onApnUnthrottled: remote exception", e);
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onApnUnthrottled: callback is null!");
    }

    public void onDataProfileUnthrottled(android.telephony.data.DataProfile dataProfile) {
        if (this.mCallback != null) {
            try {
                com.android.telephony.Rlog.d(TAG, "onDataProfileUnthrottled");
                this.mCallback.onDataProfileUnthrottled(dataProfile);
                return;
            } catch (android.os.RemoteException e) {
                com.android.telephony.Rlog.e(TAG, "onDataProfileUnthrottled: remote exception", e);
                return;
            }
        }
        com.android.telephony.Rlog.e(TAG, "onDataProfileUnthrottled: callback is null!");
    }
}
