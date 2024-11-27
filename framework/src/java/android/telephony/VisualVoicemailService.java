package android.telephony;

/* loaded from: classes3.dex */
public abstract class VisualVoicemailService extends android.app.Service {
    public static final java.lang.String DATA_PHONE_ACCOUNT_HANDLE = "data_phone_account_handle";
    public static final java.lang.String DATA_SMS = "data_sms";
    public static final int MSG_ON_CELL_SERVICE_CONNECTED = 1;
    public static final int MSG_ON_SIM_REMOVED = 3;
    public static final int MSG_ON_SMS_RECEIVED = 2;
    public static final int MSG_TASK_ENDED = 4;
    public static final int MSG_TASK_STOPPED = 5;
    public static final java.lang.String SERVICE_INTERFACE = "android.telephony.VisualVoicemailService";
    private static final java.lang.String TAG = "VvmService";
    private final android.os.Messenger mMessenger = new android.os.Messenger(new android.os.Handler() { // from class: android.telephony.VisualVoicemailService.1
        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            android.telecom.PhoneAccountHandle phoneAccountHandle = (android.telecom.PhoneAccountHandle) message.getData().getParcelable(android.telephony.VisualVoicemailService.DATA_PHONE_ACCOUNT_HANDLE, android.telecom.PhoneAccountHandle.class);
            android.telephony.VisualVoicemailService.VisualVoicemailTask visualVoicemailTask = new android.telephony.VisualVoicemailService.VisualVoicemailTask(message.replyTo, message.arg1);
            switch (message.what) {
                case 1:
                    android.telephony.VisualVoicemailService.this.onCellServiceConnected(visualVoicemailTask, phoneAccountHandle);
                    break;
                case 2:
                    android.telephony.VisualVoicemailService.this.onSmsReceived(visualVoicemailTask, (android.telephony.VisualVoicemailSms) message.getData().getParcelable(android.telephony.VisualVoicemailService.DATA_SMS, android.telephony.VisualVoicemailSms.class));
                    break;
                case 3:
                    android.telephony.VisualVoicemailService.this.onSimRemoved(visualVoicemailTask, phoneAccountHandle);
                    break;
                case 4:
                default:
                    super.handleMessage(message);
                    break;
                case 5:
                    android.telephony.VisualVoicemailService.this.onStopped(visualVoicemailTask);
                    break;
            }
        }
    });

    public abstract void onCellServiceConnected(android.telephony.VisualVoicemailService.VisualVoicemailTask visualVoicemailTask, android.telecom.PhoneAccountHandle phoneAccountHandle);

    public abstract void onSimRemoved(android.telephony.VisualVoicemailService.VisualVoicemailTask visualVoicemailTask, android.telecom.PhoneAccountHandle phoneAccountHandle);

    public abstract void onSmsReceived(android.telephony.VisualVoicemailService.VisualVoicemailTask visualVoicemailTask, android.telephony.VisualVoicemailSms visualVoicemailSms);

    public abstract void onStopped(android.telephony.VisualVoicemailService.VisualVoicemailTask visualVoicemailTask);

    public static class VisualVoicemailTask {
        private final android.os.Messenger mReplyTo;
        private final int mTaskId;

        private VisualVoicemailTask(android.os.Messenger messenger, int i) {
            this.mTaskId = i;
            this.mReplyTo = messenger;
        }

        public final void finish() {
            android.os.Message obtain = android.os.Message.obtain();
            try {
                obtain.what = 4;
                obtain.arg1 = this.mTaskId;
                this.mReplyTo.send(obtain);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.telephony.VisualVoicemailService.TAG, "Cannot send MSG_TASK_ENDED, remote handler no longer exist");
            }
        }

        public boolean equals(java.lang.Object obj) {
            return (obj instanceof android.telephony.VisualVoicemailService.VisualVoicemailTask) && this.mTaskId == ((android.telephony.VisualVoicemailService.VisualVoicemailTask) obj).mTaskId;
        }

        public int hashCode() {
            return this.mTaskId;
        }
    }

    @Override // android.app.Service
    public android.os.IBinder onBind(android.content.Intent intent) {
        return this.mMessenger.getBinder();
    }

    @android.annotation.SystemApi
    public static final void setSmsFilterSettings(android.content.Context context, android.telecom.PhoneAccountHandle phoneAccountHandle, android.telephony.VisualVoicemailSmsFilterSettings visualVoicemailSmsFilterSettings) {
        android.telephony.TelephonyManager telephonyManager = (android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class);
        int subId = getSubId(context, phoneAccountHandle);
        if (visualVoicemailSmsFilterSettings == null) {
            telephonyManager.disableVisualVoicemailSmsFilter(subId);
        } else {
            telephonyManager.enableVisualVoicemailSmsFilter(subId, visualVoicemailSmsFilterSettings);
        }
    }

    @android.annotation.SystemApi
    public static final void sendVisualVoicemailSms(android.content.Context context, android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str, short s, java.lang.String str2, android.app.PendingIntent pendingIntent) {
        ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).sendVisualVoicemailSmsForSubscriber(getSubId(context, phoneAccountHandle), str, s, str2, pendingIntent);
    }

    private static int getSubId(android.content.Context context, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        return ((android.telephony.TelephonyManager) context.getSystemService(android.telephony.TelephonyManager.class)).getSubIdForPhoneAccount(((android.telecom.TelecomManager) context.getSystemService(android.telecom.TelecomManager.class)).getPhoneAccount(phoneAccountHandle));
    }
}
