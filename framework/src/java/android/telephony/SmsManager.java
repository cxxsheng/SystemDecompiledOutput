package android.telephony;

/* loaded from: classes3.dex */
public final class SmsManager {
    public static final int CDMA_SMS_RECORD_LENGTH = 255;
    public static final java.lang.String EXTRA_MMS_DATA = "android.telephony.extra.MMS_DATA";
    public static final java.lang.String EXTRA_MMS_HTTP_STATUS = "android.telephony.extra.MMS_HTTP_STATUS";
    public static final java.lang.String EXTRA_SIM_SUBSCRIPTION_ID = "android.telephony.extra.SIM_SUBSCRIPTION_ID";
    public static final java.lang.String EXTRA_SMS_MESSAGE = "android.telephony.extra.SMS_MESSAGE";
    public static final java.lang.String EXTRA_STATUS = "android.telephony.extra.STATUS";
    private static final long GET_TARGET_SDK_VERSION_CODE_CHANGE = 145147528;
    public static final java.lang.String MMS_CONFIG_ALIAS_ENABLED = "aliasEnabled";
    public static final java.lang.String MMS_CONFIG_ALIAS_MAX_CHARS = "aliasMaxChars";
    public static final java.lang.String MMS_CONFIG_ALIAS_MIN_CHARS = "aliasMinChars";
    public static final java.lang.String MMS_CONFIG_ALLOW_ATTACH_AUDIO = "allowAttachAudio";
    public static final java.lang.String MMS_CONFIG_APPEND_TRANSACTION_ID = "enabledTransID";
    public static final java.lang.String MMS_CONFIG_CLOSE_CONNECTION = "mmsCloseConnection";
    public static final java.lang.String MMS_CONFIG_EMAIL_GATEWAY_NUMBER = "emailGatewayNumber";
    public static final java.lang.String MMS_CONFIG_GROUP_MMS_ENABLED = "enableGroupMms";
    public static final java.lang.String MMS_CONFIG_HTTP_PARAMS = "httpParams";
    public static final java.lang.String MMS_CONFIG_HTTP_SOCKET_TIMEOUT = "httpSocketTimeout";
    public static final java.lang.String MMS_CONFIG_MAX_IMAGE_HEIGHT = "maxImageHeight";
    public static final java.lang.String MMS_CONFIG_MAX_IMAGE_WIDTH = "maxImageWidth";
    public static final java.lang.String MMS_CONFIG_MAX_MESSAGE_SIZE = "maxMessageSize";
    public static final java.lang.String MMS_CONFIG_MESSAGE_TEXT_MAX_SIZE = "maxMessageTextSize";
    public static final java.lang.String MMS_CONFIG_MMS_DELIVERY_REPORT_ENABLED = "enableMMSDeliveryReports";
    public static final java.lang.String MMS_CONFIG_MMS_ENABLED = "enabledMMS";
    public static final java.lang.String MMS_CONFIG_MMS_READ_REPORT_ENABLED = "enableMMSReadReports";
    public static final java.lang.String MMS_CONFIG_MULTIPART_SMS_ENABLED = "enableMultipartSMS";
    public static final java.lang.String MMS_CONFIG_NAI_SUFFIX = "naiSuffix";
    public static final java.lang.String MMS_CONFIG_NOTIFY_WAP_MMSC_ENABLED = "enabledNotifyWapMMSC";
    public static final java.lang.String MMS_CONFIG_RECIPIENT_LIMIT = "recipientLimit";
    public static final java.lang.String MMS_CONFIG_SEND_MULTIPART_SMS_AS_SEPARATE_MESSAGES = "sendMultipartSmsAsSeparateMessages";
    public static final java.lang.String MMS_CONFIG_SHOW_CELL_BROADCAST_APP_LINKS = "config_cellBroadcastAppLinks";
    public static final java.lang.String MMS_CONFIG_SMS_DELIVERY_REPORT_ENABLED = "enableSMSDeliveryReports";
    public static final java.lang.String MMS_CONFIG_SMS_TO_MMS_TEXT_LENGTH_THRESHOLD = "smsToMmsTextLengthThreshold";
    public static final java.lang.String MMS_CONFIG_SMS_TO_MMS_TEXT_THRESHOLD = "smsToMmsTextThreshold";
    public static final java.lang.String MMS_CONFIG_SUBJECT_MAX_LENGTH = "maxSubjectLength";
    public static final java.lang.String MMS_CONFIG_SUPPORT_HTTP_CHARSET_HEADER = "supportHttpCharsetHeader";
    public static final java.lang.String MMS_CONFIG_SUPPORT_MMS_CONTENT_DISPOSITION = "supportMmsContentDisposition";
    public static final java.lang.String MMS_CONFIG_UA_PROF_TAG_NAME = "uaProfTagName";
    public static final java.lang.String MMS_CONFIG_UA_PROF_URL = "uaProfUrl";
    public static final java.lang.String MMS_CONFIG_USER_AGENT = "userAgent";
    public static final int MMS_ERROR_CONFIGURATION_ERROR = 7;
    public static final int MMS_ERROR_DATA_DISABLED = 11;
    public static final int MMS_ERROR_HTTP_FAILURE = 4;
    public static final int MMS_ERROR_INACTIVE_SUBSCRIPTION = 10;
    public static final int MMS_ERROR_INVALID_APN = 2;
    public static final int MMS_ERROR_INVALID_SUBSCRIPTION_ID = 9;
    public static final int MMS_ERROR_IO_ERROR = 5;
    public static final int MMS_ERROR_MMS_DISABLED_BY_CARRIER = 12;
    public static final int MMS_ERROR_NO_DATA_NETWORK = 8;
    public static final int MMS_ERROR_RETRY = 6;
    public static final int MMS_ERROR_TOO_LARGE_FOR_TRANSPORT = 13;
    public static final int MMS_ERROR_UNABLE_CONNECT_MMS = 3;
    public static final int MMS_ERROR_UNSPECIFIED = 1;

    @android.annotation.SystemApi
    public static final int PREMIUM_SMS_CONSENT_ALWAYS_ALLOW = 3;

    @android.annotation.SystemApi
    public static final int PREMIUM_SMS_CONSENT_ASK_USER = 1;

    @android.annotation.SystemApi
    public static final int PREMIUM_SMS_CONSENT_NEVER_ALLOW = 2;

    @android.annotation.SystemApi
    public static final int PREMIUM_SMS_CONSENT_UNKNOWN = 0;
    public static final java.lang.String REGEX_PREFIX_DELIMITER = ",";
    public static final int RESULT_BLUETOOTH_DISCONNECTED = 27;
    public static final int RESULT_CANCELLED = 23;
    public static final int RESULT_ENCODING_ERROR = 18;
    public static final int RESULT_ERROR_FDN_CHECK_FAILURE = 6;
    public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
    public static final int RESULT_ERROR_LIMIT_EXCEEDED = 5;
    public static final int RESULT_ERROR_NONE = 0;
    public static final int RESULT_ERROR_NO_SERVICE = 4;
    public static final int RESULT_ERROR_NULL_PDU = 3;
    public static final int RESULT_ERROR_RADIO_OFF = 2;
    public static final int RESULT_ERROR_SHORT_CODE_NEVER_ALLOWED = 8;
    public static final int RESULT_ERROR_SHORT_CODE_NOT_ALLOWED = 7;
    public static final int RESULT_INTERNAL_ERROR = 21;
    public static final int RESULT_INVALID_ARGUMENTS = 11;
    public static final int RESULT_INVALID_BLUETOOTH_ADDRESS = 26;
    public static final int RESULT_INVALID_SMSC_ADDRESS = 19;
    public static final int RESULT_INVALID_SMS_FORMAT = 14;
    public static final int RESULT_INVALID_STATE = 12;
    public static final int RESULT_MODEM_ERROR = 16;
    public static final int RESULT_NETWORK_ERROR = 17;
    public static final int RESULT_NETWORK_REJECT = 10;
    public static final int RESULT_NO_BLUETOOTH_SERVICE = 25;
    public static final int RESULT_NO_DEFAULT_SMS_APP = 32;
    public static final int RESULT_NO_MEMORY = 13;
    public static final int RESULT_NO_RESOURCES = 22;
    public static final int RESULT_OPERATION_NOT_ALLOWED = 20;
    public static final int RESULT_RADIO_NOT_AVAILABLE = 9;
    public static final int RESULT_RECEIVE_DISPATCH_FAILURE = 500;
    public static final int RESULT_RECEIVE_INJECTED_NULL_PDU = 501;
    public static final int RESULT_RECEIVE_NULL_MESSAGE_FROM_RIL = 503;
    public static final int RESULT_RECEIVE_RUNTIME_EXCEPTION = 502;
    public static final int RESULT_RECEIVE_SQL_EXCEPTION = 505;
    public static final int RESULT_RECEIVE_URI_EXCEPTION = 506;
    public static final int RESULT_RECEIVE_WHILE_ENCRYPTED = 504;
    public static final int RESULT_REMOTE_EXCEPTION = 31;
    public static final int RESULT_REQUEST_NOT_SUPPORTED = 24;
    public static final int RESULT_RIL_ABORTED = 137;
    public static final int RESULT_RIL_ACCESS_BARRED = 122;
    public static final int RESULT_RIL_BLOCKED_DUE_TO_CALL = 123;
    public static final int RESULT_RIL_CANCELLED = 119;
    public static final int RESULT_RIL_DEVICE_IN_USE = 136;
    public static final int RESULT_RIL_ENCODING_ERR = 109;
    public static final int RESULT_RIL_GENERIC_ERROR = 124;
    public static final int RESULT_RIL_INTERNAL_ERR = 113;
    public static final int RESULT_RIL_INVALID_ARGUMENTS = 104;
    public static final int RESULT_RIL_INVALID_MODEM_STATE = 115;
    public static final int RESULT_RIL_INVALID_RESPONSE = 125;
    public static final int RESULT_RIL_INVALID_SIM_STATE = 130;
    public static final int RESULT_RIL_INVALID_SMSC_ADDRESS = 110;
    public static final int RESULT_RIL_INVALID_SMS_FORMAT = 107;
    public static final int RESULT_RIL_INVALID_STATE = 103;
    public static final int RESULT_RIL_MODEM_ERR = 111;
    public static final int RESULT_RIL_NETWORK_ERR = 112;
    public static final int RESULT_RIL_NETWORK_NOT_READY = 116;
    public static final int RESULT_RIL_NETWORK_REJECT = 102;
    public static final int RESULT_RIL_NO_MEMORY = 105;
    public static final int RESULT_RIL_NO_NETWORK_FOUND = 135;
    public static final int RESULT_RIL_NO_RESOURCES = 118;
    public static final int RESULT_RIL_NO_SMS_TO_ACK = 131;
    public static final int RESULT_RIL_NO_SUBSCRIPTION = 134;
    public static final int RESULT_RIL_OPERATION_NOT_ALLOWED = 117;
    public static final int RESULT_RIL_RADIO_NOT_AVAILABLE = 100;
    public static final int RESULT_RIL_REQUEST_NOT_SUPPORTED = 114;
    public static final int RESULT_RIL_REQUEST_RATE_LIMITED = 106;
    public static final int RESULT_RIL_SIMULTANEOUS_SMS_AND_CALL_NOT_ALLOWED = 121;
    public static final int RESULT_RIL_SIM_ABSENT = 120;
    public static final int RESULT_RIL_SIM_BUSY = 132;
    public static final int RESULT_RIL_SIM_ERROR = 129;
    public static final int RESULT_RIL_SIM_FULL = 133;
    public static final int RESULT_RIL_SIM_PIN2 = 126;
    public static final int RESULT_RIL_SIM_PUK2 = 127;
    public static final int RESULT_RIL_SMS_SEND_FAIL_RETRY = 101;
    public static final int RESULT_RIL_SUBSCRIPTION_NOT_AVAILABLE = 128;
    public static final int RESULT_RIL_SYSTEM_ERR = 108;
    public static final int RESULT_SMS_BLOCKED_DURING_EMERGENCY = 29;
    public static final int RESULT_SMS_SEND_RETRY_FAILED = 30;
    public static final int RESULT_STATUS_SUCCESS = 0;
    public static final int RESULT_STATUS_TIMEOUT = 1;
    public static final int RESULT_SYSTEM_ERROR = 15;
    public static final int RESULT_UNEXPECTED_EVENT_STOP_SENDING = 28;
    public static final int RESULT_USER_NOT_ALLOWED = 33;
    public static final int SMS_CATEGORY_FREE_SHORT_CODE = 1;
    public static final int SMS_CATEGORY_NOT_SHORT_CODE = 0;
    public static final int SMS_CATEGORY_POSSIBLE_PREMIUM_SHORT_CODE = 3;
    public static final int SMS_CATEGORY_PREMIUM_SHORT_CODE = 4;
    public static final int SMS_CATEGORY_STANDARD_SHORT_CODE = 2;
    public static final int SMS_MESSAGE_PERIOD_NOT_SPECIFIED = -1;
    public static final int SMS_MESSAGE_PRIORITY_NOT_SPECIFIED = -1;
    public static final int SMS_RECORD_LENGTH = 176;
    public static final int SMS_RP_CAUSE_CALL_BARRING = 10;
    public static final int SMS_RP_CAUSE_CONGESTION = 42;
    public static final int SMS_RP_CAUSE_DESTINATION_OUT_OF_ORDER = 27;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_IMPLEMENTED = 69;
    public static final int SMS_RP_CAUSE_FACILITY_NOT_SUBSCRIBED = 50;
    public static final int SMS_RP_CAUSE_FACILITY_REJECTED = 29;
    public static final int SMS_RP_CAUSE_INFORMATION_ELEMENT_NON_EXISTENT = 99;
    public static final int SMS_RP_CAUSE_INTERWORKING_UNSPECIFIED = 127;
    public static final int SMS_RP_CAUSE_INVALID_MANDATORY_INFORMATION = 96;
    public static final int SMS_RP_CAUSE_INVALID_MESSAGE_REFERENCE_VALUE = 81;
    public static final int SMS_RP_CAUSE_MESSAGE_INCOMPATIBLE_WITH_PROTOCOL_STATE = 98;
    public static final int SMS_RP_CAUSE_MESSAGE_TYPE_NON_EXISTENT = 97;
    public static final int SMS_RP_CAUSE_NETWORK_OUT_OF_ORDER = 38;
    public static final int SMS_RP_CAUSE_OPERATOR_DETERMINED_BARRING = 8;
    public static final int SMS_RP_CAUSE_PROTOCOL_ERROR = 111;
    public static final int SMS_RP_CAUSE_RESERVED = 11;
    public static final int SMS_RP_CAUSE_RESOURCES_UNAVAILABLE = 47;
    public static final int SMS_RP_CAUSE_SEMANTICALLY_INCORRECT_MESSAGE = 95;
    public static final int SMS_RP_CAUSE_SHORT_MESSAGE_TRANSFER_REJECTED = 21;
    public static final int SMS_RP_CAUSE_TEMPORARY_FAILURE = 41;
    public static final int SMS_RP_CAUSE_UNALLOCATED_NUMBER = 1;
    public static final int SMS_RP_CAUSE_UNIDENTIFIED_SUBSCRIBER = 28;
    public static final int SMS_RP_CAUSE_UNKNOWN_SUBSCRIBER = 30;
    public static final int STATUS_ON_ICC_FREE = 0;
    public static final int STATUS_ON_ICC_READ = 1;
    public static final int STATUS_ON_ICC_SENT = 5;
    public static final int STATUS_ON_ICC_UNREAD = 3;
    public static final int STATUS_ON_ICC_UNSENT = 7;
    private static final java.lang.String TAG = "SmsManager";
    private final android.content.Context mContext;
    private int mSubId;
    private static final java.lang.Object sLockObject = new java.lang.Object();
    private static final java.util.Map<android.util.Pair<android.content.Context, java.lang.Integer>, android.telephony.SmsManager> sSubInstances = new android.util.ArrayMap();
    private static final android.telephony.SmsManager DEFAULT_INSTANCE = getSmsManagerForContextAndSubscriptionId(null, Integer.MAX_VALUE);

    public static abstract class FinancialSmsCallback {
        public abstract void onFinancialSmsMessages(android.database.CursorWindow cursorWindow);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PremiumSmsConsent {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Result {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SMS_RP_CAUSE {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SmsShortCodeCategory {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StatusOnIcc {
    }

    private interface SubscriptionResolverResult {
        void onFailure();

        void onSuccess(int i);
    }

    private java.lang.String getOpPackageName() {
        if (this.mContext == null) {
            return null;
        }
        return this.mContext.getOpPackageName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.String getAttributionTag() {
        if (this.mContext == null) {
            return null;
        }
        return this.mContext.getAttributionTag();
    }

    public void sendTextMessage(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendTextMessage(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, long j) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, getOpPackageName(), getAttributionTag(), j);
    }

    public void sendTextMessage(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, int i, boolean z, int i2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, true, i, z, i2);
    }

    private void sendTextMessageInternal(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final android.app.PendingIntent pendingIntent, final android.app.PendingIntent pendingIntent2, final boolean z, final java.lang.String str4, final java.lang.String str5, final long j) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Invalid destinationAddress");
        }
        if (android.text.TextUtils.isEmpty(str3)) {
            throw new java.lang.IllegalArgumentException("Invalid message body");
        }
        if (z) {
            resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.1
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    try {
                        android.telephony.SmsManager.getISmsServiceOrThrow().sendTextForSubscriber(i, str4, str5, str, str2, str3, pendingIntent, pendingIntent2, z, j);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.telephony.SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage() + " " + android.telephony.SmsManager.formatCrossStackMessageId(j));
                        android.telephony.SmsManager.notifySmsError(pendingIntent, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    android.telephony.SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
            return;
        }
        try {
            getISmsServiceOrThrow().sendTextForSubscriber(getSubscriptionId(), str4, str5, str, str2, str3, pendingIntent, pendingIntent2, z, j);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendTextMessageInternal (no persist): Couldn't send SMS, exception - " + e.getMessage() + " " + formatCrossStackMessageId(j));
            notifySmsError(pendingIntent, 31);
        }
    }

    public void sendTextMessageWithoutPersisting(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
        sendTextMessageInternal(str, str2, str3, pendingIntent, pendingIntent2, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    private void sendTextMessageInternal(final java.lang.String str, final java.lang.String str2, final java.lang.String str3, final android.app.PendingIntent pendingIntent, final android.app.PendingIntent pendingIntent2, final boolean z, int i, final boolean z2, int i2) {
        int i3;
        int i4;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Invalid destinationAddress");
        }
        if (!android.text.TextUtils.isEmpty(str3)) {
            if (i < 0 || i > 3) {
                android.util.Log.e(TAG, "Invalid Priority " + i);
                i3 = -1;
            } else {
                i3 = i;
            }
            if (i2 < 5 || i2 > 635040) {
                android.util.Log.e(TAG, "Invalid Validity Period " + i2);
                i4 = -1;
            } else {
                i4 = i2;
            }
            if (z) {
                final int i5 = i3;
                final int i6 = i4;
                resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.2
                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onSuccess(int i7) {
                        try {
                            com.android.internal.telephony.ISms iSmsServiceOrThrow = android.telephony.SmsManager.getISmsServiceOrThrow();
                            if (iSmsServiceOrThrow != null) {
                                iSmsServiceOrThrow.sendTextForSubscriberWithOptions(i7, null, android.telephony.SmsManager.this.getAttributionTag(), str, str2, str3, pendingIntent, pendingIntent2, z, i5, z2, i6);
                            }
                        } catch (android.os.RemoteException e) {
                            android.util.Log.e(android.telephony.SmsManager.TAG, "sendTextMessageInternal: Couldn't send SMS, exception - " + e.getMessage());
                            android.telephony.SmsManager.notifySmsError(pendingIntent, 31);
                        }
                    }

                    @Override // android.telephony.SmsManager.SubscriptionResolverResult
                    public void onFailure() {
                        android.telephony.SmsManager.notifySmsError(pendingIntent, 32);
                    }
                });
                return;
            }
            try {
                com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
                if (iSmsServiceOrThrow != null) {
                    iSmsServiceOrThrow.sendTextForSubscriberWithOptions(getSubscriptionId(), null, getAttributionTag(), str, str2, str3, pendingIntent, pendingIntent2, z, i3, z2, i4);
                    return;
                }
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "sendTextMessageInternal(no persist): Couldn't send SMS, exception - " + e.getMessage());
                notifySmsError(pendingIntent, 31);
                return;
            }
        }
        throw new java.lang.IllegalArgumentException("Invalid message body");
    }

    public void injectSmsPdu(byte[] bArr, java.lang.String str, android.app.PendingIntent pendingIntent) {
        if (!str.equals("3gpp") && !str.equals("3gpp2")) {
            throw new java.lang.IllegalArgumentException("Invalid pdu format. format must be either 3gpp or 3gpp2");
        }
        try {
            com.android.internal.telephony.ISms smsService = android.telephony.TelephonyManager.getSmsService();
            if (smsService != null) {
                smsService.injectSmsPduForSubscriber(getSubscriptionId(), bArr, str, pendingIntent);
            }
        } catch (android.os.RemoteException e) {
            if (pendingIntent != null) {
                try {
                    pendingIntent.send(31);
                } catch (android.app.PendingIntent.CanceledException e2) {
                }
            }
        }
    }

    public java.util.ArrayList<java.lang.String> divideMessage(java.lang.String str) {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("text is null");
        }
        return android.telephony.SmsMessage.fragmentText(str, getSubscriptionId());
    }

    public void sendMultipartTextMessage(java.lang.String str, java.lang.String str2, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<android.app.PendingIntent> arrayList2, java.util.ArrayList<android.app.PendingIntent> arrayList3) {
        sendMultipartTextMessageInternal(str, str2, (java.util.List<java.lang.String>) arrayList, (java.util.List<android.app.PendingIntent>) arrayList2, (java.util.List<android.app.PendingIntent>) arrayList3, true, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, long j) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, true, getOpPackageName(), getAttributionTag(), j);
    }

    public void sendMultipartTextMessage(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3, java.lang.String str3, java.lang.String str4) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, true, str3, str4, 0L);
    }

    private void sendMultipartTextMessageInternal(final java.lang.String str, final java.lang.String str2, final java.util.List<java.lang.String> list, final java.util.List<android.app.PendingIntent> list2, final java.util.List<android.app.PendingIntent> list3, final boolean z, final java.lang.String str3, final java.lang.String str4, final long j) {
        android.app.PendingIntent pendingIntent;
        android.app.PendingIntent pendingIntent2;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Invalid destinationAddress");
        }
        if (list == null || list.size() < 1) {
            throw new java.lang.IllegalArgumentException("Invalid message body");
        }
        if (list.size() <= 1) {
            if (list2 != null && list2.size() > 0) {
                pendingIntent = list2.get(0);
            } else {
                pendingIntent = null;
            }
            if (list3 != null && list3.size() > 0) {
                pendingIntent2 = list3.get(0);
            } else {
                pendingIntent2 = null;
            }
            sendTextMessageInternal(str, str2, list.get(0), pendingIntent, pendingIntent2, true, str3, str4, j);
            return;
        }
        if (z) {
            resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.3
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    try {
                        android.telephony.SmsManager.getISmsServiceOrThrow().sendMultipartTextForSubscriber(i, str3, str4, str, str2, list, list2, list3, z, j);
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.telephony.SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + android.telephony.SmsManager.formatCrossStackMessageId(j));
                        android.telephony.SmsManager.notifySmsError((java.util.List<android.app.PendingIntent>) list2, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    android.telephony.SmsManager.notifySmsError((java.util.List<android.app.PendingIntent>) list2, 32);
                }
            });
            return;
        }
        try {
            com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.sendMultipartTextForSubscriber(getSubscriptionId(), str3, str4, str, str2, list, list2, list3, z, j);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage() + " " + formatCrossStackMessageId(j));
            notifySmsError(list2, 31);
        }
    }

    @android.annotation.SystemApi
    public void sendMultipartTextMessageWithoutPersisting(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, java.util.List<android.app.PendingIntent> list2, java.util.List<android.app.PendingIntent> list3) {
        sendMultipartTextMessageInternal(str, str2, list, list2, list3, false, getOpPackageName(), getAttributionTag(), 0L);
    }

    public void sendMultipartTextMessage(java.lang.String str, java.lang.String str2, java.util.ArrayList<java.lang.String> arrayList, java.util.ArrayList<android.app.PendingIntent> arrayList2, java.util.ArrayList<android.app.PendingIntent> arrayList3, int i, boolean z, int i2) {
        sendMultipartTextMessageInternal(str, str2, (java.util.List<java.lang.String>) arrayList, (java.util.List<android.app.PendingIntent>) arrayList2, (java.util.List<android.app.PendingIntent>) arrayList3, true, i, z, i2);
    }

    private void sendMultipartTextMessageInternal(final java.lang.String str, final java.lang.String str2, final java.util.List<java.lang.String> list, final java.util.List<android.app.PendingIntent> list2, final java.util.List<android.app.PendingIntent> list3, final boolean z, int i, final boolean z2, int i2) {
        int i3;
        int i4;
        android.app.PendingIntent pendingIntent;
        android.app.PendingIntent pendingIntent2;
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Invalid destinationAddress");
        }
        if (list == null || list.size() < 1) {
            throw new java.lang.IllegalArgumentException("Invalid message body");
        }
        if (i < 0 || i > 3) {
            android.util.Log.e(TAG, "Invalid Priority " + i);
            i3 = -1;
        } else {
            i3 = i;
        }
        if (i2 < 5 || i2 > 635040) {
            android.util.Log.e(TAG, "Invalid Validity Period " + i2);
            i4 = -1;
        } else {
            i4 = i2;
        }
        if (list.size() <= 1) {
            if (list2 != null && list2.size() > 0) {
                pendingIntent = list2.get(0);
            } else {
                pendingIntent = null;
            }
            if (list3 != null && list3.size() > 0) {
                pendingIntent2 = list3.get(0);
            } else {
                pendingIntent2 = null;
            }
            sendTextMessageInternal(str, str2, list.get(0), pendingIntent, pendingIntent2, z, i3, z2, i4);
            return;
        }
        if (z) {
            final int i5 = i3;
            final int i6 = i4;
            resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.4
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i7) {
                    try {
                        com.android.internal.telephony.ISms iSmsServiceOrThrow = android.telephony.SmsManager.getISmsServiceOrThrow();
                        if (iSmsServiceOrThrow != null) {
                            iSmsServiceOrThrow.sendMultipartTextForSubscriberWithOptions(i7, null, null, str, str2, list, list2, list3, z, i5, z2, i6);
                        }
                    } catch (android.os.RemoteException e) {
                        android.util.Log.e(android.telephony.SmsManager.TAG, "sendMultipartTextMessageInternal: Couldn't send SMS - " + e.getMessage());
                        android.telephony.SmsManager.notifySmsError((java.util.List<android.app.PendingIntent>) list2, 31);
                    }
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    android.telephony.SmsManager.notifySmsError((java.util.List<android.app.PendingIntent>) list2, 32);
                }
            });
            return;
        }
        try {
            com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.sendMultipartTextForSubscriberWithOptions(getSubscriptionId(), null, null, str, str2, list, list2, list3, z, i3, z2, i4);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "sendMultipartTextMessageInternal (no persist): Couldn't send SMS - " + e.getMessage());
            notifySmsError(list2, 31);
        }
    }

    public void sendDataMessage(final java.lang.String str, final java.lang.String str2, final short s, final byte[] bArr, final android.app.PendingIntent pendingIntent, final android.app.PendingIntent pendingIntent2) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Invalid destinationAddress");
        }
        if (bArr == null || bArr.length == 0) {
            throw new java.lang.IllegalArgumentException("Invalid message data");
        }
        resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.5
            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onSuccess(int i) {
                try {
                    android.telephony.SmsManager.getISmsServiceOrThrow().sendDataForSubscriber(i, null, android.telephony.SmsManager.this.getAttributionTag(), str, str2, s & 65535, bArr, pendingIntent, pendingIntent2);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(android.telephony.SmsManager.TAG, "sendDataMessage: Couldn't send SMS - Exception: " + e.getMessage());
                    android.telephony.SmsManager.notifySmsError(pendingIntent, 31);
                }
            }

            @Override // android.telephony.SmsManager.SubscriptionResolverResult
            public void onFailure() {
                android.telephony.SmsManager.notifySmsError(pendingIntent, 32);
            }
        });
    }

    @java.lang.Deprecated
    public static android.telephony.SmsManager getDefault() {
        return DEFAULT_INSTANCE;
    }

    public static android.telephony.SmsManager getSmsManagerForContextAndSubscriptionId(android.content.Context context, int i) {
        android.telephony.SmsManager smsManager;
        synchronized (sLockObject) {
            android.util.Pair<android.content.Context, java.lang.Integer> pair = new android.util.Pair<>(context, java.lang.Integer.valueOf(i));
            smsManager = sSubInstances.get(pair);
            if (smsManager == null) {
                smsManager = new android.telephony.SmsManager(context, i);
                sSubInstances.put(pair, smsManager);
            }
        }
        return smsManager;
    }

    @java.lang.Deprecated
    public static android.telephony.SmsManager getSmsManagerForSubscriptionId(int i) {
        return getSmsManagerForContextAndSubscriptionId(null, i);
    }

    public android.telephony.SmsManager createForSubscriptionId(int i) {
        return getSmsManagerForContextAndSubscriptionId(this.mContext, i);
    }

    private SmsManager(android.content.Context context, int i) {
        this.mContext = context;
        this.mSubId = i;
    }

    public int getSubscriptionId() {
        try {
            return this.mSubId == Integer.MAX_VALUE ? getISmsServiceOrThrow().getPreferredSmsSubscription() : this.mSubId;
        } catch (android.os.RemoteException e) {
            return -1;
        }
    }

    private void resolveSubscriptionForOperation(final android.telephony.SmsManager.SubscriptionResolverResult subscriptionResolverResult) {
        boolean z;
        int subscriptionId = getSubscriptionId();
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                z = false;
            } else {
                z = iSmsService.isSmsSimPickActivityNeeded(subscriptionId);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "resolveSubscriptionForOperation", e);
            z = false;
        }
        if (z) {
            android.util.Log.d(TAG, "resolveSubscriptionForOperation isSmsSimPickActivityNeeded is true for calling package. ");
            try {
                getITelephony().enqueueSmsPickResult(null, null, new com.android.internal.telephony.IIntegerConsumer.Stub() { // from class: android.telephony.SmsManager.6
                    @Override // com.android.internal.telephony.IIntegerConsumer
                    public void accept(int i) {
                        android.telephony.SmsManager.this.sendResolverResult(subscriptionResolverResult, i, true);
                    }
                });
                return;
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(TAG, "Unable to launch activity", e2);
                sendResolverResult(subscriptionResolverResult, subscriptionId, true);
                return;
            }
        }
        sendResolverResult(subscriptionResolverResult, subscriptionId, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendResolverResult(android.telephony.SmsManager.SubscriptionResolverResult subscriptionResolverResult, int i, boolean z) {
        if (android.telephony.SubscriptionManager.isValidSubscriptionId(i)) {
            subscriptionResolverResult.onSuccess(i);
        } else if (!android.compat.Compatibility.isChangeEnabled(GET_TARGET_SDK_VERSION_CODE_CHANGE) && !z) {
            subscriptionResolverResult.onSuccess(i);
        } else {
            subscriptionResolverResult.onFailure();
        }
    }

    private static com.android.internal.telephony.ITelephony getITelephony() {
        com.android.internal.telephony.ITelephony asInterface = com.android.internal.telephony.ITelephony.Stub.asInterface(android.telephony.TelephonyFrameworkInitializer.getTelephonyServiceManager().getTelephonyServiceRegisterer().get());
        if (asInterface == null) {
            throw new java.lang.RuntimeException("Could not find Telephony Service.");
        }
        return asInterface;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifySmsError(android.app.PendingIntent pendingIntent, int i) {
        if (pendingIntent != null) {
            try {
                pendingIntent.send(i);
            } catch (android.app.PendingIntent.CanceledException e) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void notifySmsError(java.util.List<android.app.PendingIntent> list, int i) {
        if (list != null) {
            java.util.Iterator<android.app.PendingIntent> it = list.iterator();
            while (it.hasNext()) {
                notifySmsError(it.next(), i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static com.android.internal.telephony.ISms getISmsServiceOrThrow() {
        com.android.internal.telephony.ISms smsService = android.telephony.TelephonyManager.getSmsService();
        if (smsService == null) {
            throw new java.lang.UnsupportedOperationException("Sms is not supported");
        }
        return smsService;
    }

    private static com.android.internal.telephony.ISms getISmsService() {
        return android.telephony.TelephonyManager.getSmsService();
    }

    public boolean copyMessageToIcc(byte[] bArr, byte[] bArr2, int i) {
        if (bArr2 == null) {
            throw new java.lang.IllegalArgumentException("pdu is null");
        }
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return false;
            }
            return iSmsService.copyMessageToIccEfForSubscriber(getSubscriptionId(), null, i, bArr2, bArr);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean deleteMessageFromIcc(int i) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return false;
            }
            return iSmsService.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, i, 0, null);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public boolean updateMessageOnIcc(int i, int i2, byte[] bArr) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return false;
            }
            return iSmsService.updateMessageOnIccEfForSubscriber(getSubscriptionId(), null, i, i2, bArr);
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public java.util.List<android.telephony.SmsMessage> getMessagesFromIcc() {
        return getAllMessagesFromIcc();
    }

    public java.util.ArrayList<android.telephony.SmsMessage> getAllMessagesFromIcc() {
        java.util.List<com.android.internal.telephony.SmsRawData> list = null;
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                list = iSmsService.getAllMessagesFromIccEfForSubscriber(getSubscriptionId(), null);
            }
        } catch (android.os.RemoteException e) {
        }
        return createMessageListFromRawRecords(list);
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean enableCellBroadcastRange(int i, int i2, int i3) {
        if (i2 < i) {
            throw new java.lang.IllegalArgumentException("endMessageId < startMessageId");
        }
        boolean z = false;
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                int subscriptionId = getSubscriptionId();
                z = iSmsService.enableCellBroadcastRangeForSubscriber(subscriptionId, i, i2, i3);
                com.android.telephony.Rlog.d(TAG, "enableCellBroadcastRange: " + (z ? "succeeded" : "failed") + " at calling enableCellBroadcastRangeForSubscriber. subId = " + subscriptionId);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "enableCellBroadcastRange: ", e);
        }
        return z;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean disableCellBroadcastRange(int i, int i2, int i3) {
        if (i2 < i) {
            throw new java.lang.IllegalArgumentException("endMessageId < startMessageId");
        }
        boolean z = false;
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                int subscriptionId = getSubscriptionId();
                z = iSmsService.disableCellBroadcastRangeForSubscriber(subscriptionId, i, i2, i3);
                com.android.telephony.Rlog.d(TAG, "disableCellBroadcastRange: " + (z ? "succeeded" : "failed") + " at calling disableCellBroadcastRangeForSubscriber. subId = " + subscriptionId);
            }
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.d(TAG, "disableCellBroadcastRange: ", e);
        }
        return z;
    }

    private java.util.ArrayList<android.telephony.SmsMessage> createMessageListFromRawRecords(java.util.List<com.android.internal.telephony.SmsRawData> list) {
        android.telephony.SmsMessage createFromEfRecord;
        java.util.ArrayList<android.telephony.SmsMessage> arrayList = new java.util.ArrayList<>();
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.telephony.SmsRawData smsRawData = list.get(i);
                if (smsRawData != null && (createFromEfRecord = android.telephony.SmsMessage.createFromEfRecord(i + 1, smsRawData.getBytes(), getSubscriptionId())) != null) {
                    arrayList.add(createFromEfRecord);
                }
            }
        }
        return arrayList;
    }

    public boolean isImsSmsSupported() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return false;
            }
            return iSmsService.isImsSmsSupportedForSubscriber(getSubscriptionId());
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public java.lang.String getImsSmsFormat() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return "unknown";
            }
            return iSmsService.getImsSmsFormatForSubscriber(getSubscriptionId());
        } catch (android.os.RemoteException e) {
            return "unknown";
        }
    }

    public static int getDefaultSmsSubscriptionId() {
        try {
            return getISmsService().getPreferredSmsSubscription();
        } catch (android.os.RemoteException e) {
            return -1;
        } catch (java.lang.NullPointerException e2) {
            return -1;
        }
    }

    public boolean isSMSPromptEnabled() {
        try {
            return android.telephony.TelephonyManager.getSmsService().isSMSPromptEnabled();
        } catch (android.os.RemoteException e) {
            return false;
        } catch (java.lang.NullPointerException e2) {
            return false;
        }
    }

    public int getSmsCapacityOnIcc() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return 0;
            }
            return iSmsService.getSmsCapacityOnIccForSubscriber(getSubscriptionId());
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getSmsCapacityOnIcc() RemoteException", e);
            return 0;
        }
    }

    public void sendMultimediaMessage(android.content.Context context, android.net.Uri uri, java.lang.String str, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) {
        sendMultimediaMessage(context, uri, str, bundle, pendingIntent, 0L);
    }

    public void sendMultimediaMessage(android.content.Context context, final android.net.Uri uri, final java.lang.String str, final android.os.Bundle bundle, final android.app.PendingIntent pendingIntent, final long j) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Uri contentUri null");
        }
        final android.telephony.MmsManager mmsManager = (android.telephony.MmsManager) context.getSystemService("mms");
        if (mmsManager != null) {
            resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.7
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    mmsManager.sendMultimediaMessage(i, uri, str, bundle, pendingIntent, j);
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    android.telephony.SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
        }
    }

    public void downloadMultimediaMessage(android.content.Context context, java.lang.String str, android.net.Uri uri, android.os.Bundle bundle, android.app.PendingIntent pendingIntent) {
        downloadMultimediaMessage(context, str, uri, bundle, pendingIntent, 0L);
    }

    public void downloadMultimediaMessage(android.content.Context context, final java.lang.String str, final android.net.Uri uri, final android.os.Bundle bundle, final android.app.PendingIntent pendingIntent, final long j) {
        if (android.text.TextUtils.isEmpty(str)) {
            throw new java.lang.IllegalArgumentException("Empty MMS location URL");
        }
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("Uri contentUri null");
        }
        final android.telephony.MmsManager mmsManager = (android.telephony.MmsManager) context.getSystemService("mms");
        if (mmsManager != null) {
            resolveSubscriptionForOperation(new android.telephony.SmsManager.SubscriptionResolverResult() { // from class: android.telephony.SmsManager.8
                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onSuccess(int i) {
                    mmsManager.downloadMultimediaMessage(i, str, uri, bundle, pendingIntent, j);
                }

                @Override // android.telephony.SmsManager.SubscriptionResolverResult
                public void onFailure() {
                    android.telephony.SmsManager.notifySmsError(pendingIntent, 32);
                }
            });
        }
    }

    public android.os.Bundle getCarrierConfigValues() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getCarrierConfigValuesForSubscriber(getSubscriptionId());
            }
        } catch (android.os.RemoteException e) {
        }
        return new android.os.Bundle();
    }

    public java.lang.String createAppSpecificSmsToken(android.app.PendingIntent pendingIntent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsToken(getSubscriptionId(), null, pendingIntent);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public void getSmsMessagesForFinancialApp(android.os.Bundle bundle, java.util.concurrent.Executor executor, android.telephony.SmsManager.FinancialSmsCallback financialSmsCallback) {
    }

    public java.lang.String createAppSpecificSmsTokenWithPackageInfo(java.lang.String str, android.app.PendingIntent pendingIntent) {
        try {
            return getISmsServiceOrThrow().createAppSpecificSmsTokenWithPackageInfo(getSubscriptionId(), null, str, pendingIntent);
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public void setStorageMonitorMemoryStatusOverride(boolean z) {
        try {
            com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.setStorageMonitorMemoryStatusOverride(getSubscriptionId(), z);
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public void clearStorageMonitorMemoryStatusOverride() {
        try {
            com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                iSmsServiceOrThrow.clearStorageMonitorMemoryStatusOverride(getSubscriptionId());
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    public int checkSmsShortCodeDestination(java.lang.String str, java.lang.String str2) {
        try {
            com.android.internal.telephony.ISms iSmsServiceOrThrow = getISmsServiceOrThrow();
            if (iSmsServiceOrThrow != null) {
                return iSmsServiceOrThrow.checkSmsShortCodeDestination(getSubscriptionId(), null, null, str, str2);
            }
            return 0;
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "checkSmsShortCodeDestination() RemoteException", e);
            return 0;
        }
    }

    public java.lang.String getSmscAddress() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return null;
            }
            return iSmsService.getSmscAddressFromIccEfForSubscriber(getSubscriptionId(), null);
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    public boolean setSmscAddress(java.lang.String str) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.setSmscAddressOnIccEfForSubscriber(str, getSubscriptionId(), null);
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    @android.annotation.SystemApi
    public int getPremiumSmsConsent(java.lang.String str) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService == null) {
                return 0;
            }
            return iSmsService.getPremiumSmsPermission(str);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "getPremiumSmsPermission() RemoteException", e);
            return 0;
        }
    }

    @android.annotation.SystemApi
    public void setPremiumSmsConsent(java.lang.String str, int i) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                iSmsService.setPremiumSmsPermission(str, i);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "setPremiumSmsPermission() RemoteException", e);
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public void resetAllCellBroadcastRanges() {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                iSmsService.resetAllCellBroadcastRanges(getSubscriptionId());
            }
        } catch (android.os.RemoteException e) {
            e.rethrowFromSystemServer();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String formatCrossStackMessageId(long j) {
        return "{x-message-id:" + j + "}";
    }

    @android.annotation.SystemApi
    public android.net.Uri getSmscIdentity() {
        com.android.internal.telephony.IPhoneSubInfo subscriberInfoService;
        android.net.Uri uri = android.net.Uri.EMPTY;
        try {
            subscriberInfoService = android.telephony.TelephonyManager.getSubscriberInfoService();
        } catch (android.os.RemoteException e) {
            com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): Exception : " + e);
            e.rethrowAsRuntimeException();
        }
        if (subscriberInfoService == null) {
            com.android.telephony.Rlog.e(TAG, "getSmscIdentity(): IPhoneSubInfo instance is NULL");
            throw new java.lang.IllegalStateException("Telephony service is not available");
        }
        uri = subscriberInfoService.getSmscIdentity(getSubscriptionId(), 5);
        if (android.net.Uri.EMPTY.equals(uri)) {
            uri = subscriberInfoService.getSmscIdentity(getSubscriptionId(), 2);
        }
        return uri;
    }

    public long getWapMessageSize(java.lang.String str) {
        try {
            com.android.internal.telephony.ISms iSmsService = getISmsService();
            if (iSmsService != null) {
                return iSmsService.getWapMessageSize(str);
            }
            throw new java.lang.RuntimeException("Could not acquire ISms service.");
        } catch (android.os.RemoteException e) {
            throw new java.lang.RuntimeException(e);
        }
    }
}
