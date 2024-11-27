package android.provider;

/* loaded from: classes3.dex */
public class BlockedNumberContract {
    public static final java.lang.String AUTHORITY = "com.android.blockednumber";
    public static final java.lang.String EXTRA_CALL_PRESENTATION = "extra_call_presentation";
    public static final java.lang.String EXTRA_CONTACT_EXIST = "extra_contact_exist";
    public static final java.lang.String EXTRA_ENHANCED_SETTING_KEY = "extra_enhanced_setting_key";
    public static final java.lang.String EXTRA_ENHANCED_SETTING_VALUE = "extra_enhanced_setting_value";
    public static final java.lang.String METHOD_CAN_CURRENT_USER_BLOCK_NUMBERS = "can_current_user_block_numbers";
    public static final java.lang.String METHOD_IS_BLOCKED = "is_blocked";
    public static final java.lang.String METHOD_UNBLOCK = "unblock";
    public static final java.lang.String RES_BLOCK_STATUS = "block_status";
    public static final java.lang.String RES_CAN_BLOCK_NUMBERS = "can_block";
    public static final java.lang.String RES_ENHANCED_SETTING_IS_ENABLED = "enhanced_setting_enabled";
    public static final java.lang.String RES_NUMBER_IS_BLOCKED = "blocked";
    public static final java.lang.String RES_NUM_ROWS_DELETED = "num_deleted";
    public static final java.lang.String RES_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";
    public static final int STATUS_BLOCKED_IN_LIST = 1;
    public static final int STATUS_BLOCKED_NOT_IN_CONTACTS = 5;
    public static final int STATUS_BLOCKED_PAYPHONE = 4;
    public static final int STATUS_BLOCKED_RESTRICTED = 2;
    public static final int STATUS_BLOCKED_UNAVAILABLE = 6;
    public static final int STATUS_BLOCKED_UNKNOWN_NUMBER = 3;
    public static final int STATUS_NOT_BLOCKED = 0;
    public static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.blockednumber");
    private static final java.lang.String LOG_TAG = android.provider.BlockedNumberContract.class.getSimpleName();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface BlockStatus {
    }

    private BlockedNumberContract() {
    }

    public static class BlockedNumbers {

        @android.annotation.SystemApi
        public static final java.lang.String ACTION_BLOCK_SUPPRESSION_STATE_CHANGED = "android.provider.action.BLOCK_SUPPRESSION_STATE_CHANGED";
        public static final java.lang.String COLUMN_E164_NUMBER = "e164_number";
        public static final java.lang.String COLUMN_ID = "_id";
        public static final java.lang.String COLUMN_ORIGINAL_NUMBER = "original_number";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/blocked_number";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/blocked_number";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.RES_NUMBER_IS_BLOCKED);

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_PAYPHONE = "block_payphone_calls_setting";

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_PRIVATE = "block_private_number_calls_setting";

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNAVAILABLE = "block_unavailable_calls_setting";

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNKNOWN = "block_unknown_calls_setting";

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNREGISTERED = "block_numbers_not_in_contacts_setting";

        @android.annotation.SystemApi
        public static final java.lang.String ENHANCED_SETTING_KEY_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";

        private BlockedNumbers() {
        }

        @android.annotation.SystemApi
        public static void notifyEmergencyContact(android.content.Context context) {
            verifyBlockedNumbersPermission(context);
            try {
                android.telecom.Log.i(android.provider.BlockedNumberContract.LOG_TAG, "notifyEmergencyContact; caller=%s", context.getOpPackageName());
                context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_NOTIFY_EMERGENCY_CONTACT, (java.lang.String) null, (android.os.Bundle) null);
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "notifyEmergencyContact: provider not ready.", new java.lang.Object[0]);
            }
        }

        @android.annotation.SystemApi
        public static void endBlockSuppression(android.content.Context context) {
            verifyBlockedNumbersPermission(context);
            android.telecom.Log.i(android.provider.BlockedNumberContract.LOG_TAG, "endBlockSuppression: caller=%s", context.getOpPackageName());
            context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_END_BLOCK_SUPPRESSION, (java.lang.String) null, (android.os.Bundle) null);
        }

        @android.annotation.SystemApi
        public static int shouldSystemBlockNumber(android.content.Context context, java.lang.String str, int i, boolean z) {
            int i2;
            verifyBlockedNumbersPermission(context);
            try {
                java.lang.String opPackageName = context.getOpPackageName();
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putInt(android.provider.BlockedNumberContract.EXTRA_CALL_PRESENTATION, i);
                bundle.putBoolean(android.provider.BlockedNumberContract.EXTRA_CONTACT_EXIST, z);
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_SHOULD_SYSTEM_BLOCK_NUMBER, str, bundle);
                if (call != null) {
                    i2 = call.getInt(android.provider.BlockedNumberContract.RES_BLOCK_STATUS, 0);
                } else {
                    i2 = 0;
                }
                android.telecom.Log.d(android.provider.BlockedNumberContract.LOG_TAG, "shouldSystemBlockNumber: number=%s, caller=%s, result=%s", android.telecom.Log.piiHandle(str), opPackageName, android.provider.BlockedNumberContract.SystemContract.blockStatusToString(i2));
                return i2;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "shouldSystemBlockNumber: provider not ready.", new java.lang.Object[0]);
                return 0;
            }
        }

        @android.annotation.SystemApi
        public static android.provider.BlockedNumberContract.BlockedNumbers.BlockSuppressionStatus getBlockSuppressionStatus(android.content.Context context) {
            verifyBlockedNumbersPermission(context);
            android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_GET_BLOCK_SUPPRESSION_STATUS, (java.lang.String) null, (android.os.Bundle) null);
            android.provider.BlockedNumberContract.BlockedNumbers.BlockSuppressionStatus blockSuppressionStatus = new android.provider.BlockedNumberContract.BlockedNumbers.BlockSuppressionStatus(call.getBoolean(android.provider.BlockedNumberContract.SystemContract.RES_IS_BLOCKING_SUPPRESSED, false), call.getLong(android.provider.BlockedNumberContract.SystemContract.RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP, 0L));
            android.telecom.Log.d(android.provider.BlockedNumberContract.LOG_TAG, "getBlockSuppressionStatus: caller=%s, status=%s", context.getOpPackageName(), blockSuppressionStatus);
            return blockSuppressionStatus;
        }

        @android.annotation.SystemApi
        public static boolean shouldShowEmergencyCallNotification(android.content.Context context) {
            verifyBlockedNumbersPermission(context);
            try {
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION, (java.lang.String) null, (android.os.Bundle) null);
                if (call != null) {
                    return call.getBoolean("show_emergency_call_notification", false);
                }
                return false;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "shouldShowEmergencyCallNotification: provider not ready.", new java.lang.Object[0]);
                return false;
            }
        }

        @android.annotation.SystemApi
        public static boolean getBlockedNumberSetting(android.content.Context context, java.lang.String str) {
            verifyBlockedNumbersPermission(context);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
            try {
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_GET_ENHANCED_BLOCK_SETTING, (java.lang.String) null, bundle);
                if (call != null) {
                    return call.getBoolean(android.provider.BlockedNumberContract.RES_ENHANCED_SETTING_IS_ENABLED, false);
                }
                return false;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "getEnhancedBlockSetting: provider not ready.", new java.lang.Object[0]);
                return false;
            }
        }

        @android.annotation.SystemApi
        public static void setBlockedNumberSetting(android.content.Context context, java.lang.String str, boolean z) {
            verifyBlockedNumbersPermission(context);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
            bundle.putBoolean(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_VALUE, z);
            context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, android.provider.BlockedNumberContract.SystemContract.METHOD_SET_ENHANCED_BLOCK_SETTING, (java.lang.String) null, bundle);
        }

        @android.annotation.SystemApi
        public static final class BlockSuppressionStatus {
            private boolean mIsSuppressed;
            private long mUntilTimestampMillis;

            public BlockSuppressionStatus(boolean z, long j) {
                this.mIsSuppressed = z;
                this.mUntilTimestampMillis = j;
            }

            public java.lang.String toString() {
                return "[BlockSuppressionStatus; isSuppressed=" + this.mIsSuppressed + ", until=" + this.mUntilTimestampMillis + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
            }

            public boolean getIsSuppressed() {
                return this.mIsSuppressed;
            }

            public long getUntilTimestampMillis() {
                return this.mUntilTimestampMillis;
            }
        }

        private static void verifyBlockedNumbersPermission(android.content.Context context) {
            context.enforceCallingOrSelfPermission(android.Manifest.permission.READ_BLOCKED_NUMBERS, "Caller does not have the android.permission.READ_BLOCKED_NUMBERS permission");
            context.enforceCallingOrSelfPermission(android.Manifest.permission.WRITE_BLOCKED_NUMBERS, "Caller does not have the android.permission.WRITE_BLOCKED_NUMBERS permission");
        }
    }

    public static boolean isBlocked(android.content.Context context, java.lang.String str) {
        try {
            android.os.Bundle call = context.getContentResolver().call(AUTHORITY_URI, METHOD_IS_BLOCKED, str, (android.os.Bundle) null);
            boolean z = call != null && call.getBoolean(RES_NUMBER_IS_BLOCKED, false);
            android.telecom.Log.d(LOG_TAG, "isBlocked: phoneNumber=%s, isBlocked=%b", android.telecom.Log.piiHandle(str), java.lang.Boolean.valueOf(z));
            return z;
        } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
            android.telecom.Log.w((java.lang.String) null, "isBlocked: provider not ready.", new java.lang.Object[0]);
            return false;
        }
    }

    public static int unblock(android.content.Context context, java.lang.String str) {
        android.telecom.Log.d(LOG_TAG, "unblock: phoneNumber=%s", android.telecom.Log.piiHandle(str));
        return context.getContentResolver().call(AUTHORITY_URI, METHOD_UNBLOCK, str, (android.os.Bundle) null).getInt(RES_NUM_ROWS_DELETED, 0);
    }

    public static boolean canCurrentUserBlockNumbers(android.content.Context context) {
        try {
            android.os.Bundle call = context.getContentResolver().call(AUTHORITY_URI, METHOD_CAN_CURRENT_USER_BLOCK_NUMBERS, (java.lang.String) null, (android.os.Bundle) null);
            if (call != null) {
                return call.getBoolean(RES_CAN_BLOCK_NUMBERS, false);
            }
            return false;
        } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
            android.telecom.Log.w((java.lang.String) null, "canCurrentUserBlockNumbers: provider not ready.", new java.lang.Object[0]);
            return false;
        }
    }

    public static class SystemContract {
        public static final java.lang.String ACTION_BLOCK_SUPPRESSION_STATE_CHANGED = "android.provider.action.BLOCK_SUPPRESSION_STATE_CHANGED";
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_PAYPHONE = "block_payphone_calls_setting";
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_PRIVATE = "block_private_number_calls_setting";
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNAVAILABLE = "block_unavailable_calls_setting";
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNKNOWN = "block_unknown_calls_setting";
        public static final java.lang.String ENHANCED_SETTING_KEY_BLOCK_UNREGISTERED = "block_numbers_not_in_contacts_setting";
        public static final java.lang.String ENHANCED_SETTING_KEY_SHOW_EMERGENCY_CALL_NOTIFICATION = "show_emergency_call_notification";
        public static final java.lang.String METHOD_END_BLOCK_SUPPRESSION = "end_block_suppression";
        public static final java.lang.String METHOD_GET_BLOCK_SUPPRESSION_STATUS = "get_block_suppression_status";
        public static final java.lang.String METHOD_GET_ENHANCED_BLOCK_SETTING = "get_enhanced_block_setting";
        public static final java.lang.String METHOD_NOTIFY_EMERGENCY_CONTACT = "notify_emergency_contact";
        public static final java.lang.String METHOD_SET_ENHANCED_BLOCK_SETTING = "set_enhanced_block_setting";
        public static final java.lang.String METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION = "should_show_emergency_call_notification";
        public static final java.lang.String METHOD_SHOULD_SYSTEM_BLOCK_NUMBER = "should_system_block_number";
        public static final java.lang.String RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP = "blocking_suppressed_until_timestamp";
        public static final java.lang.String RES_IS_BLOCKING_SUPPRESSED = "blocking_suppressed";

        public static void notifyEmergencyContact(android.content.Context context) {
            try {
                android.telecom.Log.i(android.provider.BlockedNumberContract.LOG_TAG, "notifyEmergencyContact; caller=%s", context.getOpPackageName());
                context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_NOTIFY_EMERGENCY_CONTACT, (java.lang.String) null, (android.os.Bundle) null);
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "notifyEmergencyContact: provider not ready.", new java.lang.Object[0]);
            }
        }

        public static void endBlockSuppression(android.content.Context context) {
            android.telecom.Log.i(android.provider.BlockedNumberContract.LOG_TAG, "endBlockSuppression: caller=%s", context.getOpPackageName());
            context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_END_BLOCK_SUPPRESSION, (java.lang.String) null, (android.os.Bundle) null);
        }

        public static int shouldSystemBlockNumber(android.content.Context context, java.lang.String str, android.os.Bundle bundle) {
            int i;
            try {
                java.lang.String opPackageName = context.getOpPackageName();
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_SHOULD_SYSTEM_BLOCK_NUMBER, str, bundle);
                if (call != null) {
                    i = call.getInt(android.provider.BlockedNumberContract.RES_BLOCK_STATUS, 0);
                } else {
                    i = 0;
                }
                android.telecom.Log.d(android.provider.BlockedNumberContract.LOG_TAG, "shouldSystemBlockNumber: number=%s, caller=%s, result=%s", android.telecom.Log.piiHandle(str), opPackageName, blockStatusToString(i));
                return i;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "shouldSystemBlockNumber: provider not ready.", new java.lang.Object[0]);
                return 0;
            }
        }

        public static android.provider.BlockedNumberContract.SystemContract.BlockSuppressionStatus getBlockSuppressionStatus(android.content.Context context) {
            android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_GET_BLOCK_SUPPRESSION_STATUS, (java.lang.String) null, (android.os.Bundle) null);
            android.provider.BlockedNumberContract.SystemContract.BlockSuppressionStatus blockSuppressionStatus = new android.provider.BlockedNumberContract.SystemContract.BlockSuppressionStatus(call.getBoolean(RES_IS_BLOCKING_SUPPRESSED, false), call.getLong(RES_BLOCKING_SUPPRESSED_UNTIL_TIMESTAMP, 0L));
            android.telecom.Log.d(android.provider.BlockedNumberContract.LOG_TAG, "getBlockSuppressionStatus: caller=%s, status=%s", context.getOpPackageName(), blockSuppressionStatus);
            return blockSuppressionStatus;
        }

        public static boolean shouldShowEmergencyCallNotification(android.content.Context context) {
            try {
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_SHOULD_SHOW_EMERGENCY_CALL_NOTIFICATION, (java.lang.String) null, (android.os.Bundle) null);
                if (call != null) {
                    return call.getBoolean("show_emergency_call_notification", false);
                }
                return false;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "shouldShowEmergencyCallNotification: provider not ready.", new java.lang.Object[0]);
                return false;
            }
        }

        public static boolean getEnhancedBlockSetting(android.content.Context context, java.lang.String str) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
            try {
                android.os.Bundle call = context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_GET_ENHANCED_BLOCK_SETTING, (java.lang.String) null, bundle);
                if (call != null) {
                    return call.getBoolean(android.provider.BlockedNumberContract.RES_ENHANCED_SETTING_IS_ENABLED, false);
                }
                return false;
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                android.telecom.Log.w((java.lang.String) null, "getEnhancedBlockSetting: provider not ready.", new java.lang.Object[0]);
                return false;
            }
        }

        public static void setEnhancedBlockSetting(android.content.Context context, java.lang.String str, boolean z) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_KEY, str);
            bundle.putBoolean(android.provider.BlockedNumberContract.EXTRA_ENHANCED_SETTING_VALUE, z);
            context.getContentResolver().call(android.provider.BlockedNumberContract.AUTHORITY_URI, METHOD_SET_ENHANCED_BLOCK_SETTING, (java.lang.String) null, bundle);
        }

        public static java.lang.String blockStatusToString(int i) {
            switch (i) {
                case 0:
                    return "not blocked";
                case 1:
                    return "blocked - in list";
                case 2:
                    return "blocked - restricted";
                case 3:
                    return "blocked - unknown";
                case 4:
                    return "blocked - payphone";
                case 5:
                    return "blocked - not in contacts";
                case 6:
                    return "blocked - unavailable";
                default:
                    return "unknown";
            }
        }

        public static class BlockSuppressionStatus {
            public final boolean isSuppressed;
            public final long untilTimestampMillis;

            public BlockSuppressionStatus(boolean z, long j) {
                this.isSuppressed = z;
                this.untilTimestampMillis = j;
            }

            public java.lang.String toString() {
                return "[BlockSuppressionStatus; isSuppressed=" + this.isSuppressed + ", until=" + this.untilTimestampMillis + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
            }
        }
    }
}
