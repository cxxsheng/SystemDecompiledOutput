package android.provider;

/* loaded from: classes3.dex */
public final class Telephony {
    private static final java.lang.String TAG = "Telephony";

    public interface BaseMmsColumns extends android.provider.BaseColumns {

        @java.lang.Deprecated
        public static final java.lang.String ADAPTATION_ALLOWED = "adp_a";

        @java.lang.Deprecated
        public static final java.lang.String APPLIC_ID = "apl_id";

        @java.lang.Deprecated
        public static final java.lang.String AUX_APPLIC_ID = "aux_apl_id";

        @java.lang.Deprecated
        public static final java.lang.String CANCEL_ID = "cl_id";

        @java.lang.Deprecated
        public static final java.lang.String CANCEL_STATUS = "cl_st";
        public static final java.lang.String CONTENT_CLASS = "ct_cls";
        public static final java.lang.String CONTENT_LOCATION = "ct_l";
        public static final java.lang.String CONTENT_TYPE = "ct_t";
        public static final java.lang.String CREATOR = "creator";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String DATE_SENT = "date_sent";
        public static final java.lang.String DELIVERY_REPORT = "d_rpt";
        public static final java.lang.String DELIVERY_TIME = "d_tm";

        @java.lang.Deprecated
        public static final java.lang.String DELIVERY_TIME_TOKEN = "d_tm_tok";

        @java.lang.Deprecated
        public static final java.lang.String DISTRIBUTION_INDICATOR = "d_ind";

        @java.lang.Deprecated
        public static final java.lang.String DRM_CONTENT = "drm_c";

        @java.lang.Deprecated
        public static final java.lang.String ELEMENT_DESCRIPTOR = "e_des";
        public static final java.lang.String EXPIRY = "exp";

        @java.lang.Deprecated
        public static final java.lang.String LIMIT = "limit";
        public static final java.lang.String LOCKED = "locked";

        @java.lang.Deprecated
        public static final java.lang.String MBOX_QUOTAS = "mb_qt";

        @java.lang.Deprecated
        public static final java.lang.String MBOX_QUOTAS_TOKEN = "mb_qt_tok";

        @java.lang.Deprecated
        public static final java.lang.String MBOX_TOTALS = "mb_t";

        @java.lang.Deprecated
        public static final java.lang.String MBOX_TOTALS_TOKEN = "mb_t_tok";
        public static final java.lang.String MESSAGE_BOX = "msg_box";
        public static final int MESSAGE_BOX_ALL = 0;
        public static final int MESSAGE_BOX_DRAFTS = 3;
        public static final int MESSAGE_BOX_FAILED = 5;
        public static final int MESSAGE_BOX_INBOX = 1;
        public static final int MESSAGE_BOX_OUTBOX = 4;
        public static final int MESSAGE_BOX_SENT = 2;
        public static final java.lang.String MESSAGE_CLASS = "m_cls";

        @java.lang.Deprecated
        public static final java.lang.String MESSAGE_COUNT = "m_cnt";
        public static final java.lang.String MESSAGE_ID = "m_id";
        public static final java.lang.String MESSAGE_SIZE = "m_size";
        public static final java.lang.String MESSAGE_TYPE = "m_type";
        public static final java.lang.String MMS_VERSION = "v";

        @java.lang.Deprecated
        public static final java.lang.String MM_FLAGS = "mm_flg";

        @java.lang.Deprecated
        public static final java.lang.String MM_FLAGS_TOKEN = "mm_flg_tok";

        @java.lang.Deprecated
        public static final java.lang.String MM_STATE = "mm_st";

        @java.lang.Deprecated
        public static final java.lang.String PREVIOUSLY_SENT_BY = "p_s_by";

        @java.lang.Deprecated
        public static final java.lang.String PREVIOUSLY_SENT_DATE = "p_s_d";
        public static final java.lang.String PRIORITY = "pri";

        @java.lang.Deprecated
        public static final java.lang.String QUOTAS = "qt";
        public static final java.lang.String READ = "read";
        public static final java.lang.String READ_REPORT = "rr";
        public static final java.lang.String READ_STATUS = "read_status";

        @java.lang.Deprecated
        public static final java.lang.String RECOMMENDED_RETRIEVAL_MODE = "r_r_mod";

        @java.lang.Deprecated
        public static final java.lang.String RECOMMENDED_RETRIEVAL_MODE_TEXT = "r_r_mod_txt";

        @java.lang.Deprecated
        public static final java.lang.String REPLACE_ID = "repl_id";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_APPLIC_ID = "r_apl_id";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_CHARGING = "r_chg";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_CHARGING_DEADLINE = "r_chg_dl";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_CHARGING_DEADLINE_TOKEN = "r_chg_dl_tok";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_CHARGING_ID = "r_chg_id";

        @java.lang.Deprecated
        public static final java.lang.String REPLY_CHARGING_SIZE = "r_chg_sz";
        public static final java.lang.String REPORT_ALLOWED = "rpt_a";
        public static final java.lang.String RESPONSE_STATUS = "resp_st";
        public static final java.lang.String RESPONSE_TEXT = "resp_txt";
        public static final java.lang.String RETRIEVE_STATUS = "retr_st";
        public static final java.lang.String RETRIEVE_TEXT = "retr_txt";
        public static final java.lang.String RETRIEVE_TEXT_CHARSET = "retr_txt_cs";
        public static final java.lang.String SEEN = "seen";

        @java.lang.Deprecated
        public static final java.lang.String SENDER_VISIBILITY = "s_vis";

        @java.lang.Deprecated
        public static final java.lang.String START = "start";
        public static final java.lang.String STATUS = "st";

        @java.lang.Deprecated
        public static final java.lang.String STATUS_TEXT = "st_txt";

        @java.lang.Deprecated
        public static final java.lang.String STORE = "store";

        @java.lang.Deprecated
        public static final java.lang.String STORED = "stored";

        @java.lang.Deprecated
        public static final java.lang.String STORE_STATUS = "store_st";

        @java.lang.Deprecated
        public static final java.lang.String STORE_STATUS_TEXT = "store_st_txt";
        public static final java.lang.String SUBJECT = "sub";
        public static final java.lang.String SUBJECT_CHARSET = "sub_cs";
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
        public static final java.lang.String TEXT_ONLY = "text_only";
        public static final java.lang.String THREAD_ID = "thread_id";

        @java.lang.Deprecated
        public static final java.lang.String TOTALS = "totals";
        public static final java.lang.String TRANSACTION_ID = "tr_id";
    }

    public interface CanonicalAddressesColumns extends android.provider.BaseColumns {
        public static final java.lang.String ADDRESS = "address";
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
    }

    public interface CarrierColumns extends android.provider.BaseColumns {
        public static final java.lang.String CARRIER_ID = "carrier_id";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://carrier_information/carrier");
        public static final java.lang.String EXPIRATION_TIME = "expiration_time";
        public static final java.lang.String KEY_IDENTIFIER = "key_identifier";
        public static final java.lang.String KEY_TYPE = "key_type";
        public static final java.lang.String LAST_MODIFIED = "last_modified";
        public static final java.lang.String MCC = "mcc";
        public static final java.lang.String MNC = "mnc";
        public static final java.lang.String PUBLIC_KEY = "public_key";
    }

    public interface TextBasedSmsChangesColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms-changes");
        public static final java.lang.String ID = "_id";
        public static final java.lang.String NEW_READ_STATUS = "new_read_status";
        public static final java.lang.String ORIG_ROW_ID = "orig_rowid";
        public static final java.lang.String SUB_ID = "sub_id";
        public static final java.lang.String TYPE = "type";
        public static final int TYPE_DELETE = 1;
        public static final int TYPE_UPDATE = 0;
    }

    public interface TextBasedSmsColumns {
        public static final java.lang.String ADDRESS = "address";
        public static final java.lang.String BODY = "body";
        public static final java.lang.String CREATOR = "creator";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String DATE_SENT = "date_sent";
        public static final java.lang.String ERROR_CODE = "error_code";
        public static final java.lang.String LOCKED = "locked";
        public static final int MESSAGE_TYPE_ALL = 0;
        public static final int MESSAGE_TYPE_DRAFT = 3;
        public static final int MESSAGE_TYPE_FAILED = 5;
        public static final int MESSAGE_TYPE_INBOX = 1;
        public static final int MESSAGE_TYPE_OUTBOX = 4;
        public static final int MESSAGE_TYPE_QUEUED = 6;
        public static final int MESSAGE_TYPE_SENT = 2;
        public static final java.lang.String MTU = "mtu";
        public static final java.lang.String PERSON = "person";
        public static final java.lang.String PROTOCOL = "protocol";
        public static final java.lang.String READ = "read";
        public static final java.lang.String REPLY_PATH_PRESENT = "reply_path_present";
        public static final java.lang.String SEEN = "seen";
        public static final java.lang.String SERVICE_CENTER = "service_center";
        public static final java.lang.String STATUS = "status";
        public static final int STATUS_COMPLETE = 0;
        public static final int STATUS_FAILED = 64;
        public static final int STATUS_NONE = -1;
        public static final int STATUS_PENDING = 32;
        public static final java.lang.String SUBJECT = "subject";
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
        public static final java.lang.String THREAD_ID = "thread_id";
        public static final java.lang.String TYPE = "type";
    }

    public interface ThreadsColumns extends android.provider.BaseColumns {
        public static final java.lang.String ARCHIVED = "archived";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String ERROR = "error";
        public static final java.lang.String HAS_ATTACHMENT = "has_attachment";
        public static final java.lang.String MESSAGE_COUNT = "message_count";
        public static final java.lang.String READ = "read";
        public static final java.lang.String RECIPIENT_IDS = "recipient_ids";
        public static final java.lang.String SNIPPET = "snippet";
        public static final java.lang.String SNIPPET_CHARSET = "snippet_cs";
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
        public static final java.lang.String TYPE = "type";
    }

    private Telephony() {
    }

    public static final class Sms implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms");
        public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

        private Sms() {
        }

        public static java.lang.String getDefaultSmsPackage(android.content.Context context) {
            android.content.ComponentName defaultSmsApplication = com.android.internal.telephony.SmsApplication.getDefaultSmsApplication(context, false);
            if (defaultSmsApplication != null) {
                return defaultSmsApplication.getPackageName();
            }
            return null;
        }

        public static android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr) {
            return contentResolver.query(CONTENT_URI, strArr, null, null, "date DESC");
        }

        public static android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr, java.lang.String str, java.lang.String str2) {
            android.net.Uri uri = CONTENT_URI;
            if (str2 == null) {
                str2 = "date DESC";
            }
            return contentResolver.query(uri, strArr, str, null, str2);
        }

        public static android.net.Uri addMessageToUri(android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, boolean z2) {
            return addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, uri, str, str2, str3, l, z, z2, -1L);
        }

        public static android.net.Uri addMessageToUri(int i, android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, boolean z2) {
            return addMessageToUri(i, contentResolver, uri, str, str2, str3, l, z, z2, -1L);
        }

        public static android.net.Uri addMessageToUri(android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, boolean z2, long j) {
            return addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, uri, str, str2, str3, l, z, z2, j);
        }

        public static android.net.Uri addMessageToUri(int i, android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, boolean z2, long j) {
            android.content.ContentValues contentValues = new android.content.ContentValues(8);
            android.telephony.Rlog.v(android.provider.Telephony.TAG, "Telephony addMessageToUri sub id: " + i);
            contentValues.put("sub_id", java.lang.Integer.valueOf(i));
            contentValues.put("address", str);
            if (l != null) {
                contentValues.put("date", l);
            }
            contentValues.put("read", java.lang.Integer.valueOf(z ? 1 : 0));
            contentValues.put("subject", str3);
            contentValues.put("body", str2);
            if (z2) {
                contentValues.put("status", (java.lang.Integer) 32);
            }
            if (j != -1) {
                contentValues.put("thread_id", java.lang.Long.valueOf(j));
            }
            return contentResolver.insert(uri, contentValues);
        }

        public static boolean moveMessageToFolder(android.content.Context context, android.net.Uri uri, int i, int i2) {
            boolean z;
            boolean z2;
            if (uri == null) {
                return false;
            }
            switch (i) {
                case 1:
                case 3:
                    z = false;
                    z2 = false;
                    break;
                case 2:
                case 4:
                    z = false;
                    z2 = true;
                    break;
                case 5:
                case 6:
                    z2 = false;
                    z = true;
                    break;
                default:
                    return false;
            }
            android.content.ContentValues contentValues = new android.content.ContentValues(3);
            contentValues.put("type", java.lang.Integer.valueOf(i));
            if (z) {
                contentValues.put("read", (java.lang.Integer) 0);
            } else if (z2) {
                contentValues.put("read", (java.lang.Integer) 1);
            }
            contentValues.put("error_code", java.lang.Integer.valueOf(i2));
            if (1 != android.database.sqlite.SqliteWrapper.update(context, context.getContentResolver(), uri, contentValues, null, null)) {
                return false;
            }
            return true;
        }

        public static boolean isOutgoingFolder(int i) {
            return i == 5 || i == 4 || i == 2 || i == 6;
        }

        public static final class Inbox implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms/inbox");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Inbox() {
            }

            public static android.net.Uri addMessage(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z) {
                return android.provider.Telephony.Sms.addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, str, str2, str3, l, z, false);
            }

            public static android.net.Uri addMessage(int i, android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z) {
                return android.provider.Telephony.Sms.addMessageToUri(i, contentResolver, CONTENT_URI, str, str2, str3, l, z, false);
            }
        }

        public static final class Sent implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms/sent");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Sent() {
            }

            public static android.net.Uri addMessage(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l) {
                return android.provider.Telephony.Sms.addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, str, str2, str3, l, true, false);
            }

            public static android.net.Uri addMessage(int i, android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l) {
                return android.provider.Telephony.Sms.addMessageToUri(i, contentResolver, CONTENT_URI, str, str2, str3, l, true, false);
            }
        }

        public static final class Draft implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms/draft");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Draft() {
            }

            public static android.net.Uri addMessage(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l) {
                return android.provider.Telephony.Sms.addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, str, str2, str3, l, true, false);
            }

            public static android.net.Uri addMessage(int i, android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l) {
                return android.provider.Telephony.Sms.addMessageToUri(i, contentResolver, CONTENT_URI, str, str2, str3, l, true, false);
            }
        }

        public static final class Outbox implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms/outbox");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Outbox() {
            }

            public static android.net.Uri addMessage(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, long j) {
                return android.provider.Telephony.Sms.addMessageToUri(android.telephony.SubscriptionManager.getDefaultSmsSubscriptionId(), contentResolver, CONTENT_URI, str, str2, str3, l, true, z, j);
            }

            public static android.net.Uri addMessage(int i, android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.Long l, boolean z, long j) {
                return android.provider.Telephony.Sms.addMessageToUri(i, contentResolver, CONTENT_URI, str, str2, str3, l, true, z, j);
            }
        }

        public static final class Conversations implements android.provider.BaseColumns, android.provider.Telephony.TextBasedSmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://sms/conversations");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";
            public static final java.lang.String MESSAGE_COUNT = "msg_count";
            public static final java.lang.String SNIPPET = "snippet";

            private Conversations() {
            }
        }

        public static final class Intents {
            public static final java.lang.String ACTION_CHANGE_DEFAULT = "android.provider.Telephony.ACTION_CHANGE_DEFAULT";
            public static final java.lang.String ACTION_DEFAULT_SMS_PACKAGE_CHANGED = "android.provider.action.DEFAULT_SMS_PACKAGE_CHANGED";
            public static final java.lang.String ACTION_EXTERNAL_PROVIDER_CHANGE = "android.provider.action.EXTERNAL_PROVIDER_CHANGE";

            @android.annotation.SystemApi
            public static final java.lang.String ACTION_SMS_EMERGENCY_CB_RECEIVED = "android.provider.action.SMS_EMERGENCY_CB_RECEIVED";
            public static final java.lang.String ACTION_SMS_MMS_DB_CREATED = "android.provider.action.SMS_MMS_DB_CREATED";
            public static final java.lang.String ACTION_SMS_MMS_DB_LOST = "android.provider.action.SMS_MMS_DB_LOST";
            public static final java.lang.String DATA_SMS_RECEIVED_ACTION = "android.intent.action.DATA_SMS_RECEIVED";
            public static final java.lang.String EXTRA_IS_CORRUPTED = "android.provider.extra.IS_CORRUPTED";
            public static final java.lang.String EXTRA_IS_DEFAULT_SMS_APP = "android.provider.extra.IS_DEFAULT_SMS_APP";
            public static final java.lang.String EXTRA_IS_INITIAL_CREATE = "android.provider.extra.IS_INITIAL_CREATE";
            public static final java.lang.String EXTRA_PACKAGE_NAME = "package";
            public static final java.lang.String MMS_DOWNLOADED_ACTION = "android.provider.Telephony.MMS_DOWNLOADED";
            public static final int RESULT_SMS_DATABASE_ERROR = 10;
            public static final int RESULT_SMS_DISPATCH_FAILURE = 6;
            public static final int RESULT_SMS_DUPLICATED = 5;
            public static final int RESULT_SMS_GENERIC_ERROR = 2;
            public static final int RESULT_SMS_HANDLED = 1;
            public static final int RESULT_SMS_INVALID_URI = 11;
            public static final int RESULT_SMS_NULL_MESSAGE = 8;
            public static final int RESULT_SMS_NULL_PDU = 7;
            public static final int RESULT_SMS_OUT_OF_MEMORY = 3;
            public static final int RESULT_SMS_RECEIVED_WHILE_ENCRYPTED = 9;
            public static final int RESULT_SMS_UNSUPPORTED = 4;

            @java.lang.Deprecated
            public static final java.lang.String SECRET_CODE_ACTION = "android.provider.Telephony.SECRET_CODE";
            public static final java.lang.String SIM_FULL_ACTION = "android.provider.Telephony.SIM_FULL";
            public static final java.lang.String SMS_CARRIER_PROVISION_ACTION = "android.provider.Telephony.SMS_CARRIER_PROVISION";
            public static final java.lang.String SMS_CB_RECEIVED_ACTION = "android.provider.Telephony.SMS_CB_RECEIVED";
            public static final java.lang.String SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER";
            public static final java.lang.String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
            public static final java.lang.String SMS_REJECTED_ACTION = "android.provider.Telephony.SMS_REJECTED";
            public static final java.lang.String SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED_ACTION = "android.provider.Telephony.SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED";
            public static final java.lang.String WAP_PUSH_DELIVER_ACTION = "android.provider.Telephony.WAP_PUSH_DELIVER";
            public static final java.lang.String WAP_PUSH_RECEIVED_ACTION = "android.provider.Telephony.WAP_PUSH_RECEIVED";

            private Intents() {
            }

            public static android.telephony.SmsMessage[] getMessagesFromIntent(android.content.Intent intent) {
                try {
                    java.lang.Object[] objArr = (java.lang.Object[]) intent.getSerializableExtra("pdus");
                    if (objArr == null) {
                        android.telephony.Rlog.e(android.provider.Telephony.TAG, "pdus does not exist in the intent");
                        return null;
                    }
                    java.lang.String stringExtra = intent.getStringExtra(android.provider.Telephony.CellBroadcasts.MESSAGE_FORMAT);
                    int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                    if (intExtra != -1) {
                        android.telephony.Rlog.v(android.provider.Telephony.TAG, "getMessagesFromIntent with valid subId : " + intExtra);
                    } else {
                        android.telephony.Rlog.v(android.provider.Telephony.TAG, "getMessagesFromIntent");
                    }
                    int length = objArr.length;
                    android.telephony.SmsMessage[] smsMessageArr = new android.telephony.SmsMessage[length];
                    for (int i = 0; i < length; i++) {
                        smsMessageArr[i] = android.telephony.SmsMessage.createFromPdu((byte[]) objArr[i], stringExtra);
                    }
                    return smsMessageArr;
                } catch (java.lang.ClassCastException e) {
                    android.telephony.Rlog.e(android.provider.Telephony.TAG, "getMessagesFromIntent: " + e);
                    return null;
                }
            }
        }
    }

    public static final class Threads implements android.provider.Telephony.ThreadsColumns {
        public static final int BROADCAST_THREAD = 1;
        public static final int COMMON_THREAD = 0;
        private static final java.lang.String[] ID_PROJECTION = {"_id"};
        private static final android.net.Uri THREAD_ID_CONTENT_URI = android.net.Uri.parse("content://mms-sms/threadID");
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.Telephony.MmsSms.CONTENT_URI, "conversations");
        public static final android.net.Uri OBSOLETE_THREADS_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "obsolete");

        private Threads() {
        }

        public static long getOrCreateThreadId(android.content.Context context, java.lang.String str) {
            java.util.HashSet hashSet = new java.util.HashSet();
            hashSet.add(str);
            return getOrCreateThreadId(context, hashSet);
        }

        public static long getOrCreateThreadId(android.content.Context context, java.util.Set<java.lang.String> set) {
            android.net.Uri.Builder buildUpon = THREAD_ID_CONTENT_URI.buildUpon();
            for (java.lang.String str : set) {
                if (android.provider.Telephony.Mms.isEmailAddress(str)) {
                    str = android.provider.Telephony.Mms.extractAddrSpec(str);
                }
                buildUpon.appendQueryParameter("recipient", str);
            }
            android.database.Cursor query = android.database.sqlite.SqliteWrapper.query(context, context.getContentResolver(), buildUpon.build(), ID_PROJECTION, null, null, null);
            if (query != null) {
                try {
                    if (!query.moveToFirst()) {
                        android.telephony.Rlog.e(android.provider.Telephony.TAG, "getOrCreateThreadId returned no rows!");
                    } else {
                        return query.getLong(0);
                    }
                } finally {
                    query.close();
                }
            }
            android.telephony.Rlog.e(android.provider.Telephony.TAG, "getOrCreateThreadId failed with " + set.size() + " recipients");
            throw new java.lang.IllegalArgumentException("Unable to find or allocate a thread ID.");
        }
    }

    public static final class Mms implements android.provider.Telephony.BaseMmsColumns {
        public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms");
        public static final android.net.Uri REPORT_REQUEST_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "report-request");
        public static final android.net.Uri REPORT_STATUS_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "report-status");
        public static final java.util.regex.Pattern NAME_ADDR_EMAIL_PATTERN = java.util.regex.Pattern.compile("\\s*(\"[^\"]*\"|[^<>\"]+)\\s*<([^<>]+)>\\s*");

        private Mms() {
        }

        public static android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr) {
            return contentResolver.query(CONTENT_URI, strArr, null, null, "date DESC");
        }

        public static android.database.Cursor query(android.content.ContentResolver contentResolver, java.lang.String[] strArr, java.lang.String str, java.lang.String str2) {
            android.net.Uri uri = CONTENT_URI;
            if (str2 == null) {
                str2 = "date DESC";
            }
            return contentResolver.query(uri, strArr, str, null, str2);
        }

        public static java.lang.String extractAddrSpec(java.lang.String str) {
            java.util.regex.Matcher matcher = NAME_ADDR_EMAIL_PATTERN.matcher(str);
            if (matcher.matches()) {
                return matcher.group(2);
            }
            return str;
        }

        public static boolean isEmailAddress(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return false;
            }
            return android.util.Patterns.EMAIL_ADDRESS.matcher(extractAddrSpec(str)).matches();
        }

        public static boolean isPhoneNumber(java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return false;
            }
            return android.util.Patterns.PHONE.matcher(str).matches();
        }

        public static final class Inbox implements android.provider.Telephony.BaseMmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms/inbox");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Inbox() {
            }
        }

        public static final class Sent implements android.provider.Telephony.BaseMmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms/sent");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Sent() {
            }
        }

        public static final class Draft implements android.provider.Telephony.BaseMmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms/drafts");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Draft() {
            }
        }

        public static final class Outbox implements android.provider.Telephony.BaseMmsColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms/outbox");
            public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";

            private Outbox() {
            }
        }

        public static final class Addr implements android.provider.BaseColumns {
            public static final java.lang.String ADDRESS = "address";
            public static final java.lang.String CHARSET = "charset";
            public static final java.lang.String CONTACT_ID = "contact_id";
            public static final java.lang.String MSG_ID = "msg_id";
            public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
            public static final java.lang.String TYPE = "type";

            private Addr() {
            }

            public static android.net.Uri getAddrUriForMessage(java.lang.String str) {
                return android.provider.Telephony.Mms.CONTENT_URI.buildUpon().appendPath(java.lang.String.valueOf(str)).appendPath("addr").build();
            }
        }

        public static final class Part implements android.provider.BaseColumns {
            public static final java.lang.String CHARSET = "chset";
            public static final java.lang.String CONTENT_DISPOSITION = "cd";
            public static final java.lang.String CONTENT_ID = "cid";
            public static final java.lang.String CONTENT_LOCATION = "cl";
            public static final java.lang.String CONTENT_TYPE = "ct";
            public static final java.lang.String CT_START = "ctt_s";
            public static final java.lang.String CT_TYPE = "ctt_t";
            public static final java.lang.String FILENAME = "fn";
            public static final java.lang.String MSG_ID = "mid";
            public static final java.lang.String NAME = "name";
            public static final java.lang.String SEQ = "seq";
            public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
            public static final java.lang.String TEXT = "text";
            public static final java.lang.String _DATA = "_data";
            private static final java.lang.String TABLE_PART = "part";
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.Telephony.Mms.CONTENT_URI, TABLE_PART);

            private Part() {
            }

            public static android.net.Uri getPartUriForMessage(java.lang.String str) {
                return android.provider.Telephony.Mms.CONTENT_URI.buildUpon().appendPath(java.lang.String.valueOf(str)).appendPath(TABLE_PART).build();
            }
        }

        public static final class Rate {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.Telephony.Mms.CONTENT_URI, android.speech.tts.TextToSpeech.Engine.KEY_PARAM_RATE);
            public static final java.lang.String SENT_TIME = "sent_time";
            public static final java.lang.String SUBSCRIPTION_ID = "sub_id";

            private Rate() {
            }
        }

        public static final class Intents {
            public static final java.lang.String CONTENT_CHANGED_ACTION = "android.intent.action.CONTENT_CHANGED";
            public static final java.lang.String DELETED_CONTENTS = "deleted_contents";

            private Intents() {
            }
        }
    }

    public static final class MmsSms implements android.provider.BaseColumns {
        public static final int ERR_TYPE_GENERIC = 1;
        public static final int ERR_TYPE_GENERIC_PERMANENT = 10;
        public static final int ERR_TYPE_MMS_PROTO_PERMANENT = 12;
        public static final int ERR_TYPE_MMS_PROTO_TRANSIENT = 3;
        public static final int ERR_TYPE_SMS_PROTO_PERMANENT = 11;
        public static final int ERR_TYPE_SMS_PROTO_TRANSIENT = 2;
        public static final int ERR_TYPE_TRANSPORT_FAILURE = 4;
        public static final int MMS_PROTO = 1;
        public static final int NO_ERROR = 0;
        public static final int SMS_PROTO = 0;
        public static final java.lang.String TYPE_DISCRIMINATOR_COLUMN = "transport_type";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://mms-sms/");
        public static final android.net.Uri CONTENT_CONVERSATIONS_URI = android.net.Uri.parse("content://mms-sms/conversations");
        public static final android.net.Uri CONTENT_FILTER_BYPHONE_URI = android.net.Uri.parse("content://mms-sms/messages/byphone");
        public static final android.net.Uri CONTENT_UNDELIVERED_URI = android.net.Uri.parse("content://mms-sms/undelivered");
        public static final android.net.Uri CONTENT_DRAFT_URI = android.net.Uri.parse("content://mms-sms/draft");
        public static final android.net.Uri CONTENT_LOCKED_URI = android.net.Uri.parse("content://mms-sms/locked");
        public static final android.net.Uri SEARCH_URI = android.net.Uri.parse("content://mms-sms/search");

        private MmsSms() {
        }

        public static final class PendingMessages implements android.provider.BaseColumns {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.Telephony.MmsSms.CONTENT_URI, android.telephony.ims.ImsConferenceState.STATUS_PENDING);
            public static final java.lang.String DUE_TIME = "due_time";
            public static final java.lang.String ERROR_CODE = "err_code";
            public static final java.lang.String ERROR_TYPE = "err_type";
            public static final java.lang.String LAST_TRY = "last_try";
            public static final java.lang.String MSG_ID = "msg_id";
            public static final java.lang.String MSG_TYPE = "msg_type";
            public static final java.lang.String PROTO_TYPE = "proto_type";
            public static final java.lang.String RETRY_INDEX = "retry_index";
            public static final java.lang.String SUBSCRIPTION_ID = "pending_sub_id";

            private PendingMessages() {
            }
        }

        public static final class WordsTable {
            public static final java.lang.String ID = "_id";
            public static final java.lang.String INDEXED_TEXT = "index_text";
            public static final java.lang.String SOURCE_ROW_ID = "source_id";
            public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
            public static final java.lang.String TABLE_ID = "table_to_use";

            private WordsTable() {
            }
        }
    }

    public static final class Carriers implements android.provider.BaseColumns {
        public static final java.lang.String ALWAYS_ON = "always_on";
        public static final java.lang.String APN = "apn";
        public static final java.lang.String APN_ID = "apn_id";
        public static final long APN_READING_PERMISSION_CHANGE_ID = 124107808;

        @android.annotation.SystemApi
        public static final java.lang.String APN_SET_ID = "apn_set_id";
        public static final java.lang.String AUTH_TYPE = "authtype";

        @java.lang.Deprecated
        public static final java.lang.String BEARER = "bearer";

        @java.lang.Deprecated
        public static final java.lang.String BEARER_BITMASK = "bearer_bitmask";
        public static final int CARRIER_DELETED = 5;
        public static final int CARRIER_DELETED_BUT_PRESENT_IN_XML = 6;

        @android.annotation.SystemApi
        public static final int CARRIER_EDITED = 4;
        public static final java.lang.String CARRIER_ENABLED = "carrier_enabled";
        public static final java.lang.String CARRIER_ID = "carrier_id";
        public static final java.lang.String CURRENT = "current";
        public static final java.lang.String DEFAULT_SORT_ORDER = "name ASC";

        @android.annotation.SystemApi
        public static final java.lang.String EDITED_STATUS = "edited";
        public static final java.lang.String ENFORCE_KEY = "enforced";
        public static final java.lang.String ESIM_BOOTSTRAP_PROVISIONING = "esim_bootstrap_provisioning";
        public static final java.lang.String INFRASTRUCTURE_BITMASK = "infrastructure_bitmask";
        public static final int INVALID_APN_ID = -1;
        public static final java.lang.String LINGERING_NETWORK_TYPE_BITMASK = "lingering_network_type_bitmask";

        @android.annotation.SystemApi
        public static final int MATCH_ALL_APN_SET_ID = -1;

        @android.annotation.SystemApi
        public static final java.lang.String MAX_CONNECTIONS = "max_conns";
        public static final java.lang.String MCC = "mcc";
        public static final java.lang.String MMSC = "mmsc";
        public static final java.lang.String MMSPORT = "mmsport";
        public static final java.lang.String MMSPROXY = "mmsproxy";
        public static final java.lang.String MNC = "mnc";

        @android.annotation.SystemApi
        public static final java.lang.String MODEM_PERSIST = "modem_cognitive";

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public static final java.lang.String MTU = "mtu";
        public static final java.lang.String MTU_V4 = "mtu_v4";
        public static final java.lang.String MTU_V6 = "mtu_v6";
        public static final java.lang.String MVNO_MATCH_DATA = "mvno_match_data";
        public static final java.lang.String MVNO_TYPE = "mvno_type";
        public static final java.lang.String NAME = "name";
        public static final java.lang.String NETWORK_TYPE_BITMASK = "network_type_bitmask";

        @android.annotation.SystemApi
        public static final int NO_APN_SET_ID = 0;
        public static final java.lang.String NUMERIC = "numeric";
        public static final java.lang.String OWNED_BY = "owned_by";
        public static final int OWNED_BY_DPC = 0;
        public static final int OWNED_BY_OTHERS = 1;
        public static final java.lang.String PASSWORD = "password";
        public static final java.lang.String PORT = "port";
        public static final java.lang.String PROFILE_ID = "profile_id";
        public static final java.lang.String PROTOCOL = "protocol";
        public static final java.lang.String PROXY = "proxy";
        public static final java.lang.String ROAMING_PROTOCOL = "roaming_protocol";
        public static final java.lang.String SERVER = "server";
        public static final java.lang.String SKIP_464XLAT = "skip_464xlat";
        public static final int SKIP_464XLAT_DEFAULT = -1;
        public static final int SKIP_464XLAT_DISABLE = 0;
        public static final int SKIP_464XLAT_ENABLE = 1;
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";

        @android.annotation.SystemApi
        public static final java.lang.String TIME_LIMIT_FOR_MAX_CONNECTIONS = "max_conns_time";
        public static final java.lang.String TYPE = "type";

        @android.annotation.SystemApi
        public static final int UNEDITED = 0;
        public static final java.lang.String USER = "user";

        @android.annotation.SystemApi
        public static final int USER_DELETED = 2;
        public static final int USER_DELETED_BUT_PRESENT_IN_XML = 3;
        public static final java.lang.String USER_EDITABLE = "user_editable";

        @android.annotation.SystemApi
        public static final int USER_EDITED = 1;
        public static final java.lang.String USER_VISIBLE = "user_visible";

        @android.annotation.SystemApi
        public static final java.lang.String WAIT_TIME_RETRY = "wait_time";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://telephony/carriers");
        public static final android.net.Uri SIM_APN_URI = android.net.Uri.parse("content://telephony/carriers/sim_apn_list");
        public static final android.net.Uri DPC_URI = android.net.Uri.parse("content://telephony/carriers/dpc");
        public static final android.net.Uri FILTERED_URI = android.net.Uri.parse("content://telephony/carriers/filtered");
        public static final android.net.Uri ENFORCE_MANAGED_URI = android.net.Uri.parse("content://telephony/carriers/enforce_managed");
        public static final android.net.Uri PREFERRED_APN_URI = android.net.Uri.parse("content://telephony/carriers/preferapn/subId");
        public static final android.net.Uri PREFERRED_APN_SET_URI = android.net.Uri.parse("content://telephony/carriers/preferapnset/subId");

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface EditStatus {
        }

        private Carriers() {
        }
    }

    @android.annotation.SystemApi
    public static final class CellBroadcasts implements android.provider.BaseColumns {

        @android.annotation.SystemApi
        public static final java.lang.String AUTHORITY_LEGACY = "cellbroadcast-legacy";

        @android.annotation.SystemApi
        public static final java.lang.String CALL_METHOD_GET_PREFERENCE = "get_preference";
        public static final java.lang.String CID = "cid";
        public static final java.lang.String DATA_CODING_SCHEME = "dcs";
        public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";
        public static final java.lang.String DELIVERY_TIME = "date";
        public static final java.lang.String ETWS_IS_PRIMARY = "etws_is_primary";
        public static final java.lang.String GEOMETRIES = "geometries";
        public static final java.lang.String LANGUAGE_CODE = "language";
        public static final java.lang.String LOCATION_CHECK_TIME = "location_check_time";
        public static final java.lang.String MAXIMUM_WAIT_TIME = "maximum_wait_time";
        public static final java.lang.String MESSAGE_BODY = "body";
        public static final java.lang.String MESSAGE_BROADCASTED = "message_broadcasted";
        public static final java.lang.String MESSAGE_DISPLAYED = "message_displayed";
        public static final java.lang.String MESSAGE_PRIORITY = "priority";
        public static final java.lang.String MESSAGE_READ = "read";
        public static final java.lang.String PLMN = "plmn";
        public static final java.lang.String RECEIVED_TIME = "received_time";
        public static final java.lang.String SERIAL_NUMBER = "serial_number";
        public static final java.lang.String SLOT_INDEX = "slot_index";
        public static final java.lang.String SUBSCRIPTION_ID = "sub_id";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://cellbroadcasts");
        public static final android.net.Uri MESSAGE_HISTORY_URI = android.net.Uri.parse("content://cellbroadcasts/history");

        @android.annotation.SystemApi
        public static final android.net.Uri AUTHORITY_LEGACY_URI = android.net.Uri.parse("content://cellbroadcast-legacy");
        public static final java.lang.String GEOGRAPHICAL_SCOPE = "geo_scope";
        public static final java.lang.String LAC = "lac";
        public static final java.lang.String SERVICE_CATEGORY = "service_category";
        public static final java.lang.String MESSAGE_FORMAT = "format";
        public static final java.lang.String ETWS_WARNING_TYPE = "etws_warning_type";
        public static final java.lang.String CMAS_MESSAGE_CLASS = "cmas_message_class";
        public static final java.lang.String CMAS_CATEGORY = "cmas_category";
        public static final java.lang.String CMAS_RESPONSE_TYPE = "cmas_response_type";
        public static final java.lang.String CMAS_SEVERITY = "cmas_severity";
        public static final java.lang.String CMAS_URGENCY = "cmas_urgency";
        public static final java.lang.String CMAS_CERTAINTY = "cmas_certainty";
        public static final java.lang.String[] QUERY_COLUMNS = {"_id", GEOGRAPHICAL_SCOPE, "plmn", LAC, "cid", "serial_number", SERVICE_CATEGORY, "language", "body", "date", "read", MESSAGE_FORMAT, "priority", ETWS_WARNING_TYPE, CMAS_MESSAGE_CLASS, CMAS_CATEGORY, CMAS_RESPONSE_TYPE, CMAS_SEVERITY, CMAS_URGENCY, CMAS_CERTAINTY};

        private CellBroadcasts() {
        }

        @android.annotation.SystemApi
        public static final class Preference {
            public static final java.lang.String ENABLE_ALERT_VIBRATION_PREF = "enable_alert_vibrate";
            public static final java.lang.String ENABLE_AREA_UPDATE_INFO_PREF = "enable_area_update_info_alerts";
            public static final java.lang.String ENABLE_CMAS_AMBER_PREF = "enable_cmas_amber_alerts";
            public static final java.lang.String ENABLE_CMAS_EXTREME_THREAT_PREF = "enable_cmas_extreme_threat_alerts";
            public static final java.lang.String ENABLE_CMAS_IN_SECOND_LANGUAGE_PREF = "receive_cmas_in_second_language";
            public static final java.lang.String ENABLE_CMAS_PRESIDENTIAL_PREF = "enable_cmas_presidential_alerts";
            public static final java.lang.String ENABLE_CMAS_SEVERE_THREAT_PREF = "enable_cmas_severe_threat_alerts";
            public static final java.lang.String ENABLE_EMERGENCY_PERF = "enable_emergency_alerts";
            public static final java.lang.String ENABLE_PUBLIC_SAFETY_PREF = "enable_public_safety_messages";
            public static final java.lang.String ENABLE_STATE_LOCAL_TEST_PREF = "enable_state_local_test_alerts";
            public static final java.lang.String ENABLE_TEST_ALERT_PREF = "enable_test_alerts";

            private Preference() {
            }
        }
    }

    public static final class ServiceStateTable {
        public static final java.lang.String AUTHORITY = "service-state";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://service-state/");
        public static final java.lang.String DATA_NETWORK_TYPE = "data_network_type";
        public static final java.lang.String DATA_REG_STATE = "data_reg_state";
        public static final java.lang.String DUPLEX_MODE = "duplex_mode";
        public static final java.lang.String IS_MANUAL_NETWORK_SELECTION = "is_manual_network_selection";
        public static final java.lang.String VOICE_OPERATOR_NUMERIC = "voice_operator_numeric";
        public static final java.lang.String VOICE_REG_STATE = "voice_reg_state";

        private ServiceStateTable() {
        }

        public static android.net.Uri getUriForSubscriptionIdAndField(int i, java.lang.String str) {
            return CONTENT_URI.buildUpon().appendEncodedPath(java.lang.String.valueOf(i)).appendEncodedPath(str).build();
        }

        public static android.net.Uri getUriForSubscriptionId(int i) {
            return CONTENT_URI.buildUpon().appendEncodedPath(java.lang.String.valueOf(i)).build();
        }
    }

    public static final class CarrierId implements android.provider.BaseColumns {
        public static final java.lang.String AUTHORITY = "carrier_id";
        public static final java.lang.String CARRIER_ID = "carrier_id";
        public static final java.lang.String CARRIER_NAME = "carrier_name";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://carrier_id");
        public static final java.lang.String PARENT_CARRIER_ID = "parent_carrier_id";
        public static final java.lang.String SPECIFIC_CARRIER_ID = "specific_carrier_id";
        public static final java.lang.String SPECIFIC_CARRIER_ID_NAME = "specific_carrier_id_name";

        private CarrierId() {
        }

        public static android.net.Uri getUriForSubscriptionId(int i) {
            return CONTENT_URI.buildUpon().appendEncodedPath(java.lang.String.valueOf(i)).build();
        }

        public static android.net.Uri getSpecificCarrierIdUriForSubscriptionId(int i) {
            return android.net.Uri.withAppendedPath(android.net.Uri.withAppendedPath(CONTENT_URI, "specific"), java.lang.String.valueOf(i));
        }

        public static final class All implements android.provider.BaseColumns {
            public static final java.lang.String APN = "apn";
            public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://carrier_id/all");
            public static final java.lang.String GID1 = "gid1";
            public static final java.lang.String GID2 = "gid2";
            public static final java.lang.String ICCID_PREFIX = "iccid_prefix";
            public static final java.lang.String IMSI_PREFIX_XPATTERN = "imsi_prefix_xpattern";
            public static final java.lang.String MCCMNC = "mccmnc";
            public static final java.lang.String PLMN = "plmn";
            public static final java.lang.String PRIVILEGE_ACCESS_RULE = "privilege_access_rule";
            public static final java.lang.String SPN = "spn";

            private All() {
            }
        }
    }

    public static final class SimInfo {
        public static final int COLOR_DEFAULT = 0;
        public static final java.lang.String COLUMN_ACCESS_RULES = "access_rules";
        public static final java.lang.String COLUMN_ACCESS_RULES_FROM_CARRIER_CONFIGS = "access_rules_from_carrier_configs";
        public static final java.lang.String COLUMN_ALLOWED_NETWORK_TYPES = "allowed_network_types";
        public static final java.lang.String COLUMN_ALLOWED_NETWORK_TYPES_FOR_REASONS = "allowed_network_types_for_reasons";
        public static final java.lang.String COLUMN_CARD_ID = "card_id";
        public static final java.lang.String COLUMN_CARRIER_ID = "carrier_id";
        public static final java.lang.String COLUMN_CARRIER_NAME = "carrier_name";
        public static final java.lang.String COLUMN_CB_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
        public static final java.lang.String COLUMN_CB_ALERT_SOUND_DURATION = "alert_sound_duration";
        public static final java.lang.String COLUMN_CB_ALERT_SPEECH = "enable_alert_speech";
        public static final java.lang.String COLUMN_CB_ALERT_VIBRATE = "enable_alert_vibrate";
        public static final java.lang.String COLUMN_CB_AMBER_ALERT = "enable_cmas_amber_alerts";
        public static final java.lang.String COLUMN_CB_CHANNEL_50_ALERT = "enable_channel_50_alerts";
        public static final java.lang.String COLUMN_CB_CMAS_TEST_ALERT = "enable_cmas_test_alerts";
        public static final java.lang.String COLUMN_CB_EMERGENCY_ALERT = "enable_emergency_alerts";
        public static final java.lang.String COLUMN_CB_ETWS_TEST_ALERT = "enable_etws_test_alerts";
        public static final java.lang.String COLUMN_CB_EXTREME_THREAT_ALERT = "enable_cmas_extreme_threat_alerts";
        public static final java.lang.String COLUMN_CB_OPT_OUT_DIALOG = "show_cmas_opt_out_dialog";
        public static final java.lang.String COLUMN_CB_SEVERE_THREAT_ALERT = "enable_cmas_severe_threat_alerts";
        public static final java.lang.String COLUMN_COLOR = "color";
        public static final java.lang.String COLUMN_CROSS_SIM_CALLING_ENABLED = "cross_sim_calling_enabled";
        public static final java.lang.String COLUMN_D2D_STATUS_SHARING = "d2d_sharing_status";
        public static final java.lang.String COLUMN_D2D_STATUS_SHARING_SELECTED_CONTACTS = "d2d_sharing_contacts";
        public static final java.lang.String COLUMN_DATA_ROAMING = "data_roaming";
        public static final java.lang.String COLUMN_DISPLAY_NAME = "display_name";
        public static final java.lang.String COLUMN_EHPLMNS = "ehplmns";
        public static final java.lang.String COLUMN_ENABLED_MOBILE_DATA_POLICIES = "enabled_mobile_data_policies";
        public static final java.lang.String COLUMN_ENHANCED_4G_MODE_ENABLED = "volte_vt_enabled";
        public static final java.lang.String COLUMN_GROUP_OWNER = "group_owner";
        public static final java.lang.String COLUMN_GROUP_UUID = "group_uuid";
        public static final java.lang.String COLUMN_HPLMNS = "hplmns";
        public static final java.lang.String COLUMN_ICC_ID = "icc_id";
        public static final java.lang.String COLUMN_IMSI = "imsi";
        public static final java.lang.String COLUMN_IMS_RCS_UCE_ENABLED = "ims_rcs_uce_enabled";
        public static final java.lang.String COLUMN_ISO_COUNTRY_CODE = "iso_country_code";
        public static final java.lang.String COLUMN_IS_EMBEDDED = "is_embedded";
        public static final java.lang.String COLUMN_IS_NTN = "is_ntn";
        public static final java.lang.String COLUMN_IS_OPPORTUNISTIC = "is_opportunistic";
        public static final java.lang.String COLUMN_IS_REMOVABLE = "is_removable";
        public static final java.lang.String COLUMN_MCC = "mcc";
        public static final java.lang.String COLUMN_MCC_STRING = "mcc_string";
        public static final java.lang.String COLUMN_MNC = "mnc";
        public static final java.lang.String COLUMN_MNC_STRING = "mnc_string";
        public static final java.lang.String COLUMN_NAME_SOURCE = "name_source";
        public static final java.lang.String COLUMN_NR_ADVANCED_CALLING_ENABLED = "nr_advanced_calling_enabled";
        public static final java.lang.String COLUMN_NUMBER = "number";
        public static final java.lang.String COLUMN_PORT_INDEX = "port_index";
        public static final java.lang.String COLUMN_PROFILE_CLASS = "profile_class";
        public static final java.lang.String COLUMN_SATELLITE_ATTACH_ENABLED_FOR_CARRIER = "satellite_attach_enabled_for_carrier";
        public static final java.lang.String COLUMN_SATELLITE_ENABLED = "satellite_enabled";
        public static final java.lang.String COLUMN_SATELLITE_ENTITLEMENT_PLMNS = "satellite_entitlement_plmns";
        public static final java.lang.String COLUMN_SATELLITE_ENTITLEMENT_STATUS = "satellite_entitlement_status";
        public static final java.lang.String COLUMN_SERVICE_CAPABILITIES = "service_capabilities";
        public static final java.lang.String COLUMN_SIM_SLOT_INDEX = "sim_id";
        public static final java.lang.String COLUMN_SUBSCRIPTION_TYPE = "subscription_type";
        public static final java.lang.String COLUMN_TP_MESSAGE_REF = "tp_message_ref";
        public static final java.lang.String COLUMN_TRANSFER_STATUS = "transfer_status";
        public static final java.lang.String COLUMN_UICC_APPLICATIONS_ENABLED = "uicc_applications_enabled";
        public static final java.lang.String COLUMN_UNIQUE_KEY_SUBSCRIPTION_ID = "_id";
        public static final java.lang.String COLUMN_USAGE_SETTING = "usage_setting";
        public static final java.lang.String COLUMN_USER_HANDLE = "user_handle";
        public static final java.lang.String COLUMN_VOIMS_OPT_IN_STATUS = "voims_opt_in_status";
        public static final java.lang.String COLUMN_VT_IMS_ENABLED = "vt_ims_enabled";
        public static final java.lang.String COLUMN_WFC_IMS_ENABLED = "wfc_ims_enabled";
        public static final java.lang.String COLUMN_WFC_IMS_MODE = "wfc_ims_mode";
        public static final java.lang.String COLUMN_WFC_IMS_ROAMING_ENABLED = "wfc_ims_roaming_enabled";
        public static final java.lang.String COLUMN_WFC_IMS_ROAMING_MODE = "wfc_ims_roaming_mode";
        public static final int DATA_ROAMING_DISABLE = 0;
        public static final int DATA_ROAMING_ENABLE = 1;
        public static final int DISPLAY_NUMBER_DEFAULT = 1;
        public static final int NAME_SOURCE_CARRIER = 3;
        public static final int NAME_SOURCE_CARRIER_ID = 0;
        public static final int NAME_SOURCE_SIM_PNN = 4;
        public static final int NAME_SOURCE_SIM_SPN = 1;
        public static final int NAME_SOURCE_UNKNOWN = -1;
        public static final int NAME_SOURCE_USER_INPUT = 2;
        public static final int PROFILE_CLASS_OPERATIONAL = 2;
        public static final int PROFILE_CLASS_PROVISIONING = 1;
        public static final int PROFILE_CLASS_TESTING = 0;
        public static final int PROFILE_CLASS_UNSET = -1;
        public static final int SIM_NOT_INSERTED = -1;
        public static final int SIM_PROVISIONED = 0;
        public static final int SUBSCRIPTION_TYPE_LOCAL_SIM = 0;
        public static final int SUBSCRIPTION_TYPE_REMOTE_SIM = 1;
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://telephony/siminfo");
        public static final java.lang.String COLUMN_DISPLAY_NUMBER_FORMAT = "display_number_format";
        public static final java.lang.String COLUMN_SIM_PROVISIONING_STATUS = "sim_provisioning_status";
        public static final java.lang.String COLUMN_IS_METERED = "is_metered";

        @java.lang.Deprecated
        public static final java.lang.String COLUMN_DATA_ENABLED_OVERRIDE_RULES = "data_enabled_override_rules";
        public static final java.lang.String COLUMN_RCS_CONFIG = "rcs_config";
        public static final java.lang.String COLUMN_PHONE_NUMBER_SOURCE_CARRIER = "phone_number_source_carrier";
        public static final java.lang.String COLUMN_PHONE_NUMBER_SOURCE_IMS = "phone_number_source_ims";
        private static final java.util.List<java.lang.String> ALL_COLUMNS = java.util.List.of((java.lang.Object[]) new java.lang.String[]{"_id", "icc_id", "sim_id", "display_name", "carrier_name", "name_source", "color", "number", COLUMN_DISPLAY_NUMBER_FORMAT, "data_roaming", "mcc", "mnc", "mcc_string", "mnc_string", "ehplmns", "hplmns", COLUMN_SIM_PROVISIONING_STATUS, "is_embedded", "card_id", "access_rules", "access_rules_from_carrier_configs", "is_removable", "enable_cmas_extreme_threat_alerts", "enable_cmas_severe_threat_alerts", "enable_cmas_amber_alerts", "enable_emergency_alerts", "alert_sound_duration", "alert_reminder_interval", "enable_alert_vibrate", "enable_alert_speech", "enable_etws_test_alerts", "enable_channel_50_alerts", "enable_cmas_test_alerts", "show_cmas_opt_out_dialog", "volte_vt_enabled", "vt_ims_enabled", "wfc_ims_enabled", "wfc_ims_mode", "wfc_ims_roaming_mode", "wfc_ims_roaming_enabled", "is_opportunistic", "group_uuid", COLUMN_IS_METERED, "iso_country_code", "carrier_id", "profile_class", "subscription_type", "group_owner", COLUMN_DATA_ENABLED_OVERRIDE_RULES, "enabled_mobile_data_policies", "imsi", "uicc_applications_enabled", "allowed_network_types", "ims_rcs_uce_enabled", "cross_sim_calling_enabled", COLUMN_RCS_CONFIG, "allowed_network_types_for_reasons", "d2d_sharing_status", "voims_opt_in_status", "d2d_sharing_contacts", "nr_advanced_calling_enabled", COLUMN_PHONE_NUMBER_SOURCE_CARRIER, COLUMN_PHONE_NUMBER_SOURCE_IMS, "port_index", "usage_setting", "tp_message_ref", "user_handle", "satellite_enabled", "satellite_attach_enabled_for_carrier", "is_ntn", "service_capabilities", "transfer_status", "satellite_entitlement_status", "satellite_entitlement_plmns"});

        private SimInfo() {
        }

        public static java.util.List<java.lang.String> getAllColumns() {
            return ALL_COLUMNS;
        }
    }

    public static final class SatelliteDatagrams {
        public static final java.lang.String PROVIDER_NAME = "satellite";
        public static final java.lang.String TABLE_NAME = "incoming_datagrams";
        private static final java.lang.String URL = "content://satellite/incoming_datagrams";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse(URL);
        public static final java.lang.String COLUMN_UNIQUE_KEY_DATAGRAM_ID = "datagram_id";
        public static final java.lang.String COLUMN_DATAGRAM = "datagram";
        private static final java.util.List<java.lang.String> ALL_COLUMNS = java.util.List.of(COLUMN_UNIQUE_KEY_DATAGRAM_ID, COLUMN_DATAGRAM);

        private SatelliteDatagrams() {
        }

        public static java.util.List<java.lang.String> getAllColumns() {
            return ALL_COLUMNS;
        }
    }
}
