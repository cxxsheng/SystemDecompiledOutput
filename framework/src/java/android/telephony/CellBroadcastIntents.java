package android.telephony;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public class CellBroadcastIntents {
    public static final java.lang.String ACTION_AREA_INFO_UPDATED = "android.telephony.action.AREA_INFO_UPDATED";
    private static final java.lang.String EXTRA_MESSAGE = "message";
    private static final java.lang.String LOG_TAG = "CellBroadcastIntents";

    private CellBroadcastIntents() {
    }

    public static void sendSmsCbReceivedBroadcast(android.content.Context context, android.os.UserHandle userHandle, android.telephony.SmsCbMessage smsCbMessage, android.content.BroadcastReceiver broadcastReceiver, android.os.Handler handler, int i, int i2) {
        android.content.Intent intent = new android.content.Intent(android.provider.Telephony.Sms.Intents.SMS_CB_RECEIVED_ACTION);
        intent.putExtra("message", smsCbMessage);
        putPhoneIdAndSubIdExtra(context, intent, i2);
        if (userHandle != null) {
            context.createContextAsUser(userHandle, 0).sendOrderedBroadcast(intent, android.Manifest.permission.RECEIVE_SMS, android.app.AppOpsManager.OPSTR_RECEIVE_SMS, broadcastReceiver, handler, i, (java.lang.String) null, (android.os.Bundle) null);
        } else {
            context.sendOrderedBroadcast(intent, android.Manifest.permission.RECEIVE_SMS, android.app.AppOpsManager.OPSTR_RECEIVE_SMS, broadcastReceiver, handler, i, (java.lang.String) null, (android.os.Bundle) null);
        }
    }

    private static void putPhoneIdAndSubIdExtra(android.content.Context context, android.content.Intent intent, int i) {
        int subscriptionId = android.telephony.SubscriptionManager.getSubscriptionId(i);
        if (subscriptionId != -1) {
            intent.putExtra(com.android.internal.telephony.PhoneConstants.SUBSCRIPTION_KEY, subscriptionId);
            intent.putExtra("android.telephony.extra.SUBSCRIPTION_INDEX", subscriptionId);
        }
        intent.putExtra("phone", i);
        intent.putExtra("android.telephony.extra.SLOT_INDEX", i);
    }
}
