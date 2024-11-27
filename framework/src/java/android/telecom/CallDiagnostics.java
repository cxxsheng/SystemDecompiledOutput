package android.telecom;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public abstract class CallDiagnostics {
    public static final int BATTERY_STATE_CHARGING = 3;
    public static final int BATTERY_STATE_GOOD = 2;
    public static final int BATTERY_STATE_LOW = 1;
    public static final int COVERAGE_GOOD = 2;
    public static final int COVERAGE_POOR = 1;
    public static final int MESSAGE_CALL_AUDIO_CODEC = 2;
    public static final int MESSAGE_CALL_NETWORK_TYPE = 1;
    public static final int MESSAGE_DEVICE_BATTERY_STATE = 3;
    public static final int MESSAGE_DEVICE_NETWORK_COVERAGE = 4;
    private java.lang.String mCallId;
    private android.telecom.CallDiagnostics.Listener mListener;

    public interface Listener {
        void onClearDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i);

        void onDisplayDiagnosticMessage(android.telecom.CallDiagnostics callDiagnostics, int i, java.lang.CharSequence charSequence);

        void onSendDeviceToDeviceMessage(android.telecom.CallDiagnostics callDiagnostics, int i, int i2);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface MessageType {
    }

    public abstract void onCallDetailsChanged(android.telecom.Call.Details details);

    public abstract java.lang.CharSequence onCallDisconnected(int i, int i2);

    public abstract java.lang.CharSequence onCallDisconnected(android.telephony.ims.ImsReasonInfo imsReasonInfo);

    public abstract void onCallQualityReceived(android.telephony.CallQuality callQuality);

    public abstract void onReceiveDeviceToDeviceMessage(int i, int i2);

    public void setListener(android.telecom.CallDiagnostics.Listener listener) {
        this.mListener = listener;
    }

    public void setCallId(java.lang.String str) {
        this.mCallId = str;
    }

    public java.lang.String getCallId() {
        return this.mCallId;
    }

    public final void sendDeviceToDeviceMessage(int i, int i2) {
        if (this.mListener != null) {
            this.mListener.onSendDeviceToDeviceMessage(this, i, i2);
        }
    }

    public final void displayDiagnosticMessage(int i, java.lang.CharSequence charSequence) {
        if (this.mListener != null) {
            this.mListener.onDisplayDiagnosticMessage(this, i, charSequence);
        }
    }

    public final void clearDiagnosticMessage(int i) {
        if (this.mListener != null) {
            this.mListener.onClearDiagnosticMessage(this, i);
        }
    }

    public void handleCallUpdated(android.telecom.Call.Details details) {
        onCallDetailsChanged(details);
    }
}
