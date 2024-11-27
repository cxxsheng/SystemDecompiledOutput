package android.provider;

/* loaded from: classes3.dex */
public class CallLog {
    public static final java.lang.String AUTHORITY = "call_log";
    private static final java.lang.String LOG_TAG = "CallLog";
    private static final boolean VERBOSE_LOG = false;
    public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://call_log");
    public static final java.lang.String CALL_COMPOSER_SEGMENT = "call_composer";
    public static final android.net.Uri CALL_COMPOSER_PICTURE_URI = CONTENT_URI.buildUpon().appendPath(CALL_COMPOSER_SEGMENT).build();
    public static final java.lang.String SHADOW_AUTHORITY = "call_log_shadow";
    public static final android.net.Uri SHADOW_CALL_COMPOSER_PICTURE_URI = CALL_COMPOSER_PICTURE_URI.buildUpon().authority(SHADOW_AUTHORITY).build();

    @android.annotation.SystemApi
    public static class CallComposerLoggingException extends java.lang.Throwable {
        public static final int ERROR_INPUT_CLOSED = 3;
        public static final int ERROR_REMOTE_END_CLOSED = 1;
        public static final int ERROR_STORAGE_FULL = 2;
        public static final int ERROR_UNKNOWN = 0;
        private final int mErrorCode;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallComposerLoggingError {
        }

        public CallComposerLoggingException(int i) {
            this.mErrorCode = i;
        }

        public int getErrorCode() {
            return this.mErrorCode;
        }

        @Override // java.lang.Throwable
        public java.lang.String toString() {
            java.lang.String str;
            switch (this.mErrorCode) {
                case 0:
                    str = "UNKNOWN";
                    break;
                case 1:
                    str = "REMOTE_END_CLOSED";
                    break;
                case 2:
                    str = "STORAGE_FULL";
                    break;
                case 3:
                    str = "INPUT_CLOSED";
                    break;
                default:
                    str = "[[" + this.mErrorCode + "]]";
                    break;
            }
            return "CallComposerLoggingException: " + str;
        }
    }

    @android.annotation.SystemApi
    public static void storeCallComposerPicture(final android.content.Context context, final java.io.InputStream inputStream, java.util.concurrent.Executor executor, final android.os.OutcomeReceiver<android.net.Uri, android.provider.CallLog.CallComposerLoggingException> outcomeReceiver) {
        java.util.Objects.requireNonNull(context);
        java.util.Objects.requireNonNull(inputStream);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        executor.execute(new java.lang.Runnable() { // from class: android.provider.CallLog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.provider.CallLog.lambda$storeCallComposerPicture$0(inputStream, outcomeReceiver, context);
            }
        });
    }

    static /* synthetic */ void lambda$storeCallComposerPicture$0(java.io.InputStream inputStream, android.os.OutcomeReceiver outcomeReceiver, android.content.Context context) {
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            try {
                int read = inputStream.read(bArr);
                if (read < 0) {
                    break;
                } else {
                    byteArrayOutputStream.write(bArr, 0, read);
                }
            } catch (java.io.IOException e) {
                android.util.Log.e(LOG_TAG, "IOException while reading call composer pic from input: " + e);
                outcomeReceiver.onError(new android.provider.CallLog.CallComposerLoggingException(3));
                return;
            }
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
        android.os.UserHandle user = context.getUser();
        if (android.os.UserHandle.CURRENT.equals(user)) {
            user = android.os.Process.myUserHandle();
        }
        if (user != android.os.UserHandle.ALL) {
            android.net.Uri maybeAddUserId = android.content.ContentProvider.maybeAddUserId(userManager.isUserUnlocked(user) ? CALL_COMPOSER_PICTURE_URI : SHADOW_CALL_COMPOSER_PICTURE_URI, user.getIdentifier());
            android.util.Log.i(LOG_TAG, "Inserting call composer for single user at " + maybeAddUserId);
            try {
                outcomeReceiver.onResult(storeCallComposerPictureAtUri(context, maybeAddUserId, false, byteArray));
                return;
            } catch (android.provider.CallLog.CallComposerLoggingException e2) {
                outcomeReceiver.onError(e2);
                return;
            }
        }
        if (!userManager.isUserUnlocked(android.os.UserHandle.SYSTEM)) {
            android.net.Uri maybeAddUserId2 = android.content.ContentProvider.maybeAddUserId(SHADOW_CALL_COMPOSER_PICTURE_URI, android.os.UserHandle.SYSTEM.getIdentifier());
            android.util.Log.i(LOG_TAG, "Inserting call composer for all users, but system locked at " + maybeAddUserId2);
            try {
                outcomeReceiver.onResult(storeCallComposerPictureAtUri(context, maybeAddUserId2, true, byteArray));
                return;
            } catch (android.provider.CallLog.CallComposerLoggingException e3) {
                outcomeReceiver.onError(e3);
                return;
            }
        }
        try {
            android.net.Uri storeCallComposerPictureAtUri = storeCallComposerPictureAtUri(context, android.content.ContentProvider.maybeAddUserId(CALL_COMPOSER_PICTURE_URI, android.os.UserHandle.SYSTEM.getIdentifier()), true, byteArray);
            android.util.Log.i(LOG_TAG, "Inserting call composer for all users, succeeded with system, result is " + storeCallComposerPictureAtUri);
            android.net.Uri uriWithoutUserId = android.content.ContentProvider.getUriWithoutUserId(storeCallComposerPictureAtUri);
            java.util.Iterator<android.content.pm.UserInfo> it = userManager.getAliveUsers().iterator();
            while (it.hasNext()) {
                android.os.UserHandle userHandle = it.next().getUserHandle();
                if (!userHandle.isSystem() && android.provider.CallLog.Calls.shouldHaveSharedCallLogEntries(context, userManager, userHandle.getIdentifier()) && userManager.isUserRunning(userHandle) && userManager.isUserUnlocked(userHandle)) {
                    android.net.Uri maybeAddUserId3 = android.content.ContentProvider.maybeAddUserId(uriWithoutUserId, userHandle.getIdentifier());
                    android.util.Log.i(LOG_TAG, "Inserting call composer for all users, now on user " + userHandle + " inserting at " + maybeAddUserId3);
                    try {
                        storeCallComposerPictureAtUri(context, maybeAddUserId3, false, byteArray);
                    } catch (android.provider.CallLog.CallComposerLoggingException e4) {
                        android.util.Log.e(LOG_TAG, "Error writing for user " + userHandle.getIdentifier() + ": " + e4);
                    }
                }
            }
            outcomeReceiver.onResult(uriWithoutUserId);
        } catch (android.provider.CallLog.CallComposerLoggingException e5) {
            outcomeReceiver.onError(e5);
        }
    }

    private static android.net.Uri storeCallComposerPictureAtUri(android.content.Context context, android.net.Uri uri, boolean z, byte[] bArr) throws android.provider.CallLog.CallComposerLoggingException {
        android.os.ParcelFileDescriptor openFileDescriptor;
        try {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put(android.provider.CallLog.Calls.ADD_FOR_ALL_USERS, java.lang.Integer.valueOf(z ? 1 : 0));
            android.net.Uri insert = context.getContentResolver().insert(uri, contentValues);
            if (insert == null) {
                throw new android.provider.CallLog.CallComposerLoggingException(2);
            }
            try {
                openFileDescriptor = context.getContentResolver().openFileDescriptor(insert, "w");
            } catch (java.io.FileNotFoundException e) {
                throw new android.provider.CallLog.CallComposerLoggingException(0);
            } catch (java.io.IOException e2) {
                android.util.Log.e(LOG_TAG, "Got IOException closing remote descriptor: " + e2);
            }
            try {
                try {
                    new java.io.FileOutputStream(openFileDescriptor.getFileDescriptor()).write(bArr);
                    if (openFileDescriptor != null) {
                        openFileDescriptor.close();
                    }
                    return insert;
                } catch (java.io.IOException e3) {
                    android.util.Log.e(LOG_TAG, "Got IOException writing to remote end: " + e3);
                    context.getContentResolver().delete(insert, null);
                    throw new android.provider.CallLog.CallComposerLoggingException(1);
                }
            } finally {
            }
        } catch (android.os.ParcelableException e4) {
            throw new android.provider.CallLog.CallComposerLoggingException(0);
        }
    }

    private static void sendCallComposerError(android.os.OutcomeReceiver<?, android.provider.CallLog.CallComposerLoggingException> outcomeReceiver, int i) {
        outcomeReceiver.onError(new android.provider.CallLog.CallComposerLoggingException(i));
    }

    public static class AddCallParams {
        private android.telecom.PhoneAccountHandle mAccountHandle;
        private boolean mAddForAllUsers;
        private java.lang.String mAssertedDisplayName;
        private int mCallBlockReason;
        private java.lang.CharSequence mCallScreeningAppName;
        private java.lang.String mCallScreeningComponentName;
        private int mCallType;
        private android.telecom.CallerInfo mCallerInfo;
        private long mDataUsage;
        private int mDuration;
        private int mFeatures;
        private boolean mIsBusinessCall;
        private int mIsPhoneAccountMigrationPending;
        private boolean mIsRead;
        private double mLatitude;
        private double mLongitude;
        private long mMissedReason;
        private java.lang.String mNumber;
        private android.net.Uri mPictureUri;
        private java.lang.String mPostDialDigits;
        private int mPresentation;
        private int mPriority;
        private long mStart;
        private java.lang.String mSubject;
        private android.os.UserHandle mUserToBeInsertedTo;
        private java.lang.String mViaNumber;

        public static final class AddCallParametersBuilder {
            public static final int MAX_NUMBER_OF_CHARACTERS = 256;
            private android.telecom.PhoneAccountHandle mAccountHandle;
            private boolean mAddForAllUsers;
            private java.lang.String mAssertedDisplayName;
            private java.lang.CharSequence mCallScreeningAppName;
            private java.lang.String mCallScreeningComponentName;
            private android.telecom.CallerInfo mCallerInfo;
            private int mDuration;
            private int mFeatures;
            private boolean mIsBusinessCall;
            private int mIsPhoneAccountMigrationPending;
            private boolean mIsRead;
            private java.lang.String mNumber;
            private android.net.Uri mPictureUri;
            private java.lang.String mPostDialDigits;
            private long mStart;
            private java.lang.String mSubject;
            private android.os.UserHandle mUserToBeInsertedTo;
            private java.lang.String mViaNumber;
            private int mPresentation = 3;
            private int mCallType = 1;
            private java.lang.Long mDataUsage = Long.MIN_VALUE;
            private int mCallBlockReason = 0;
            private long mMissedReason = 0;
            private int mPriority = 0;
            private double mLatitude = Double.NaN;
            private double mLongitude = Double.NaN;

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setCallerInfo(android.telecom.CallerInfo callerInfo) {
                this.mCallerInfo = callerInfo;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setNumber(java.lang.String str) {
                this.mNumber = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setPostDialDigits(java.lang.String str) {
                this.mPostDialDigits = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setViaNumber(java.lang.String str) {
                this.mViaNumber = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setPresentation(int i) {
                this.mPresentation = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setCallType(int i) {
                this.mCallType = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setFeatures(int i) {
                this.mFeatures = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setAccountHandle(android.telecom.PhoneAccountHandle phoneAccountHandle) {
                this.mAccountHandle = phoneAccountHandle;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setStart(long j) {
                this.mStart = j;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setDuration(int i) {
                this.mDuration = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setDataUsage(long j) {
                this.mDataUsage = java.lang.Long.valueOf(j);
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setAddForAllUsers(boolean z) {
                this.mAddForAllUsers = z;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setUserToBeInsertedTo(android.os.UserHandle userHandle) {
                this.mUserToBeInsertedTo = userHandle;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setIsRead(boolean z) {
                this.mIsRead = z;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setCallBlockReason(int i) {
                this.mCallBlockReason = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setCallScreeningAppName(java.lang.CharSequence charSequence) {
                this.mCallScreeningAppName = charSequence;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setCallScreeningComponentName(java.lang.String str) {
                this.mCallScreeningComponentName = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setMissedReason(long j) {
                this.mMissedReason = j;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setPriority(int i) {
                this.mPriority = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setSubject(java.lang.String str) {
                this.mSubject = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setLatitude(double d) {
                this.mLatitude = d;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setLongitude(double d) {
                this.mLongitude = d;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setPictureUri(android.net.Uri uri) {
                this.mPictureUri = uri;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setIsPhoneAccountMigrationPending(int i) {
                this.mIsPhoneAccountMigrationPending = i;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setIsBusinessCall(boolean z) {
                this.mIsBusinessCall = z;
                return this;
            }

            public android.provider.CallLog.AddCallParams.AddCallParametersBuilder setAssertedDisplayName(java.lang.String str) {
                if (str != null && str.length() > 256) {
                    throw new java.lang.IllegalArgumentException("assertedDisplayName exceeds the character limit of 256.");
                }
                this.mAssertedDisplayName = str;
                return this;
            }

            public android.provider.CallLog.AddCallParams build() {
                if (com.android.server.telecom.flags.Flags.businessCallComposer()) {
                    return new android.provider.CallLog.AddCallParams(this.mCallerInfo, this.mNumber, this.mPostDialDigits, this.mViaNumber, this.mPresentation, this.mCallType, this.mFeatures, this.mAccountHandle, this.mStart, this.mDuration, this.mDataUsage.longValue(), this.mAddForAllUsers, this.mUserToBeInsertedTo, this.mIsRead, this.mCallBlockReason, this.mCallScreeningAppName, this.mCallScreeningComponentName, this.mMissedReason, this.mPriority, this.mSubject, this.mLatitude, this.mLongitude, this.mPictureUri, this.mIsPhoneAccountMigrationPending, this.mIsBusinessCall, this.mAssertedDisplayName);
                }
                return new android.provider.CallLog.AddCallParams(this.mCallerInfo, this.mNumber, this.mPostDialDigits, this.mViaNumber, this.mPresentation, this.mCallType, this.mFeatures, this.mAccountHandle, this.mStart, this.mDuration, this.mDataUsage.longValue(), this.mAddForAllUsers, this.mUserToBeInsertedTo, this.mIsRead, this.mCallBlockReason, this.mCallScreeningAppName, this.mCallScreeningComponentName, this.mMissedReason, this.mPriority, this.mSubject, this.mLatitude, this.mLongitude, this.mPictureUri, this.mIsPhoneAccountMigrationPending);
            }
        }

        private AddCallParams(android.telecom.CallerInfo callerInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, long j, int i4, long j2, boolean z, android.os.UserHandle userHandle, boolean z2, int i5, java.lang.CharSequence charSequence, java.lang.String str4, long j3, int i6, java.lang.String str5, double d, double d2, android.net.Uri uri, int i7) {
            this.mLatitude = Double.NaN;
            this.mLongitude = Double.NaN;
            this.mCallerInfo = callerInfo;
            this.mNumber = str;
            this.mPostDialDigits = str2;
            this.mViaNumber = str3;
            this.mPresentation = i;
            this.mCallType = i2;
            this.mFeatures = i3;
            this.mAccountHandle = phoneAccountHandle;
            this.mStart = j;
            this.mDuration = i4;
            this.mDataUsage = j2;
            this.mAddForAllUsers = z;
            this.mUserToBeInsertedTo = userHandle;
            this.mIsRead = z2;
            this.mCallBlockReason = i5;
            this.mCallScreeningAppName = charSequence;
            this.mCallScreeningComponentName = str4;
            this.mMissedReason = j3;
            this.mPriority = i6;
            this.mSubject = str5;
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mPictureUri = uri;
            this.mIsPhoneAccountMigrationPending = i7;
        }

        private AddCallParams(android.telecom.CallerInfo callerInfo, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, long j, int i4, long j2, boolean z, android.os.UserHandle userHandle, boolean z2, int i5, java.lang.CharSequence charSequence, java.lang.String str4, long j3, int i6, java.lang.String str5, double d, double d2, android.net.Uri uri, int i7, boolean z3, java.lang.String str6) {
            this.mLatitude = Double.NaN;
            this.mLongitude = Double.NaN;
            this.mCallerInfo = callerInfo;
            this.mNumber = str;
            this.mPostDialDigits = str2;
            this.mViaNumber = str3;
            this.mPresentation = i;
            this.mCallType = i2;
            this.mFeatures = i3;
            this.mAccountHandle = phoneAccountHandle;
            this.mStart = j;
            this.mDuration = i4;
            this.mDataUsage = j2;
            this.mAddForAllUsers = z;
            this.mUserToBeInsertedTo = userHandle;
            this.mIsRead = z2;
            this.mCallBlockReason = i5;
            this.mCallScreeningAppName = charSequence;
            this.mCallScreeningComponentName = str4;
            this.mMissedReason = j3;
            this.mPriority = i6;
            this.mSubject = str5;
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mPictureUri = uri;
            this.mIsPhoneAccountMigrationPending = i7;
            this.mIsBusinessCall = z3;
            this.mAssertedDisplayName = str6;
        }
    }

    public static class Calls implements android.provider.BaseColumns {
        public static final java.lang.String ADD_FOR_ALL_USERS = "add_for_all_users";
        public static final int ANSWERED_EXTERNALLY_TYPE = 7;
        public static final java.lang.String ASSERTED_DISPLAY_NAME = "asserted_display_name";
        public static final long AUTO_MISSED_EMERGENCY_CALL = 1;
        public static final long AUTO_MISSED_MAXIMUM_DIALING = 4;
        public static final long AUTO_MISSED_MAXIMUM_RINGING = 2;
        public static final int BLOCKED_TYPE = 6;
        public static final java.lang.String BLOCK_REASON = "block_reason";
        public static final int BLOCK_REASON_BLOCKED_NUMBER = 3;
        public static final int BLOCK_REASON_CALL_SCREENING_SERVICE = 1;
        public static final int BLOCK_REASON_DIRECT_TO_VOICEMAIL = 2;
        public static final int BLOCK_REASON_NOT_BLOCKED = 0;
        public static final int BLOCK_REASON_NOT_IN_CONTACTS = 7;
        public static final int BLOCK_REASON_PAY_PHONE = 6;
        public static final int BLOCK_REASON_RESTRICTED_NUMBER = 5;
        public static final int BLOCK_REASON_UNKNOWN_NUMBER = 4;
        public static final java.lang.String CACHED_FORMATTED_NUMBER = "formatted_number";
        public static final java.lang.String CACHED_LOOKUP_URI = "lookup_uri";
        public static final java.lang.String CACHED_MATCHED_NUMBER = "matched_number";
        public static final java.lang.String CACHED_NAME = "name";
        public static final java.lang.String CACHED_NORMALIZED_NUMBER = "normalized_number";
        public static final java.lang.String CACHED_NUMBER_LABEL = "numberlabel";
        public static final java.lang.String CACHED_NUMBER_TYPE = "numbertype";
        public static final java.lang.String CACHED_PHOTO_ID = "photo_id";
        public static final java.lang.String CACHED_PHOTO_URI = "photo_uri";
        public static final java.lang.String CALL_SCREENING_APP_NAME = "call_screening_app_name";
        public static final java.lang.String CALL_SCREENING_COMPONENT_NAME = "call_screening_component_name";
        public static final java.lang.String COMPOSER_PHOTO_URI = "composer_photo_uri";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/calls";
        public static final java.lang.String COUNTRY_ISO = "countryiso";
        public static final java.lang.String DATA_USAGE = "data_usage";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String DEFAULT_SORT_ORDER = "date DESC";
        public static final java.lang.String DURATION = "duration";
        public static final java.lang.String EXTRA_CALL_TYPE_FILTER = "android.provider.extra.CALL_TYPE_FILTER";
        public static final java.lang.String FEATURES = "features";
        public static final int FEATURES_ASSISTED_DIALING_USED = 16;
        public static final int FEATURES_HD_CALL = 4;
        public static final int FEATURES_PULLED_EXTERNALLY = 2;
        public static final int FEATURES_RTT = 32;
        public static final int FEATURES_VIDEO = 1;
        public static final int FEATURES_VOLTE = 64;
        public static final int FEATURES_WIFI = 8;
        public static final java.lang.String GEOCODED_LOCATION = "geocoded_location";
        public static final int INCOMING_TYPE = 1;
        public static final java.lang.String IS_BUSINESS_CALL = "is_business_call";
        public static final java.lang.String IS_PHONE_ACCOUNT_MIGRATION_PENDING = "is_call_log_phone_account_migration_pending";
        public static final java.lang.String IS_READ = "is_read";
        public static final java.lang.String LAST_MODIFIED = "last_modified";
        public static final java.lang.String LIMIT_PARAM_KEY = "limit";
        public static final java.lang.String LOCATION = "location";
        public static final int LOW_RING_VOLUME = 0;
        private static final int MIN_DURATION_FOR_NORMALIZED_NUMBER_UPDATE_MS = 10000;
        public static final java.lang.String MISSED_REASON = "missed_reason";
        public static final long MISSED_REASON_NOT_MISSED = 0;
        public static final int MISSED_TYPE = 3;
        public static final java.lang.String NEW = "new";
        public static final java.lang.String NUMBER = "number";
        public static final java.lang.String NUMBER_PRESENTATION = "presentation";
        public static final java.lang.String OFFSET_PARAM_KEY = "offset";
        public static final int OUTGOING_TYPE = 2;
        public static final java.lang.String PHONE_ACCOUNT_ADDRESS = "phone_account_address";
        public static final java.lang.String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final java.lang.String PHONE_ACCOUNT_HIDDEN = "phone_account_hidden";
        public static final java.lang.String PHONE_ACCOUNT_ID = "subscription_id";
        public static final java.lang.String POST_DIAL_DIGITS = "post_dial_digits";
        public static final int PRESENTATION_ALLOWED = 1;
        public static final int PRESENTATION_PAYPHONE = 4;
        public static final int PRESENTATION_RESTRICTED = 2;
        public static final int PRESENTATION_UNAVAILABLE = 5;
        public static final int PRESENTATION_UNKNOWN = 3;
        public static final java.lang.String PRIORITY = "priority";
        public static final int PRIORITY_NORMAL = 0;
        public static final int PRIORITY_URGENT = 1;
        public static final int REJECTED_TYPE = 5;
        public static final long SHORT_RING_THRESHOLD = 5000;
        public static final java.lang.String SUBJECT = "subject";
        public static final java.lang.String SUB_ID = "sub_id";
        public static final java.lang.String TRANSCRIPTION = "transcription";
        public static final java.lang.String TRANSCRIPTION_STATE = "transcription_state";
        public static final java.lang.String TYPE = "type";
        public static final long USER_MISSED_CALL_FILTERS_TIMEOUT = 4194304;
        public static final long USER_MISSED_CALL_SCREENING_SERVICE_SILENCED = 2097152;
        public static final long USER_MISSED_DND_MODE = 262144;
        public static final long USER_MISSED_LOW_RING_VOLUME = 524288;
        public static final long USER_MISSED_NEVER_RANG = 8388608;
        public static final long USER_MISSED_NOT_RUNNING = 16777216;
        public static final long USER_MISSED_NO_ANSWER = 65536;
        public static final long USER_MISSED_NO_VIBRATE = 1048576;
        public static final long USER_MISSED_SHORT_RING = 131072;
        public static final java.lang.String VIA_NUMBER = "via_number";
        public static final int VOICEMAIL_TYPE = 4;
        public static final java.lang.String VOICEMAIL_URI = "voicemail_uri";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://call_log/calls");
        public static final android.net.Uri SHADOW_CONTENT_URI = android.net.Uri.parse("content://call_log_shadow/calls");
        public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.parse("content://call_log/calls/filter");
        private static final android.net.Uri CONTENT_URI_LIMIT_1 = CONTENT_URI.buildUpon().appendQueryParameter("limit", "1").build();
        public static final java.lang.String ALLOW_VOICEMAILS_PARAM_KEY = "allow_voicemails";
        public static final android.net.Uri CONTENT_URI_WITH_VOICEMAIL = CONTENT_URI.buildUpon().appendQueryParameter(ALLOW_VOICEMAILS_PARAM_KEY, "true").build();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface MissedReason {
        }

        public static android.net.Uri addCall(android.telecom.CallerInfo callerInfo, android.content.Context context, java.lang.String str, int i, int i2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, long j, int i4, java.lang.Long l, long j2, int i5) {
            return addCall(callerInfo, context, str, "", "", i, i2, i3, phoneAccountHandle, j, i4, l, false, null, false, 0, null, null, j2, i5);
        }

        public static android.net.Uri addCall(android.telecom.CallerInfo callerInfo, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, long j, int i4, java.lang.Long l, boolean z, android.os.UserHandle userHandle, long j2, int i5) {
            return addCall(callerInfo, context, str, str2, str3, i, i2, i3, phoneAccountHandle, j, i4, l, z, userHandle, false, 0, null, null, j2, i5);
        }

        public static android.net.Uri addCall(android.telecom.CallerInfo callerInfo, android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, android.telecom.PhoneAccountHandle phoneAccountHandle, long j, int i4, java.lang.Long l, boolean z, android.os.UserHandle userHandle, boolean z2, int i5, java.lang.CharSequence charSequence, java.lang.String str4, long j2, int i6) {
            android.provider.CallLog.AddCallParams.AddCallParametersBuilder addCallParametersBuilder = new android.provider.CallLog.AddCallParams.AddCallParametersBuilder();
            addCallParametersBuilder.setCallerInfo(callerInfo);
            addCallParametersBuilder.setNumber(str);
            addCallParametersBuilder.setPostDialDigits(str2);
            addCallParametersBuilder.setViaNumber(str3);
            addCallParametersBuilder.setPresentation(i);
            addCallParametersBuilder.setCallType(i2);
            addCallParametersBuilder.setFeatures(i3);
            addCallParametersBuilder.setAccountHandle(phoneAccountHandle);
            addCallParametersBuilder.setStart(j);
            addCallParametersBuilder.setDuration(i4);
            addCallParametersBuilder.setDataUsage(l == null ? Long.MIN_VALUE : l.longValue());
            addCallParametersBuilder.setAddForAllUsers(z);
            addCallParametersBuilder.setUserToBeInsertedTo(userHandle);
            addCallParametersBuilder.setIsRead(z2);
            addCallParametersBuilder.setCallBlockReason(i5);
            addCallParametersBuilder.setCallScreeningAppName(charSequence);
            addCallParametersBuilder.setCallScreeningComponentName(str4);
            addCallParametersBuilder.setMissedReason(j2);
            addCallParametersBuilder.setIsPhoneAccountMigrationPending(i6);
            return addCall(context, addCallParametersBuilder.build());
        }

        /* JADX WARN: Code restructure failed: missing block: B:7:0x0034, code lost:
        
            if (r14.mCallerInfo != null) goto L12;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static android.net.Uri addCall(android.content.Context context, android.provider.CallLog.AddCallParams addCallParams) {
            java.lang.String str;
            java.lang.String str2;
            android.os.UserHandle of;
            android.net.Uri maybeInsertLocation;
            android.database.Cursor query;
            android.content.ContentResolver contentResolver = context.getContentResolver();
            java.lang.String logAccountAddress = getLogAccountAddress(context, addCallParams.mAccountHandle);
            int logNumberPresentation = getLogNumberPresentation(addCallParams.mNumber, addCallParams.mPresentation);
            java.lang.String str3 = "";
            java.lang.String name = addCallParams.mCallerInfo != null ? addCallParams.mCallerInfo.getName() : "";
            if (logNumberPresentation != 1) {
                addCallParams.mNumber = "";
            }
            str3 = name;
            if (addCallParams.mAccountHandle == null) {
                str = null;
                str2 = null;
            } else {
                str = addCallParams.mAccountHandle.getComponentName().flattenToString();
                str2 = addCallParams.mAccountHandle.getId();
            }
            android.content.ContentValues contentValues = new android.content.ContentValues(14);
            contentValues.put("number", addCallParams.mNumber);
            contentValues.put(POST_DIAL_DIGITS, addCallParams.mPostDialDigits);
            contentValues.put(VIA_NUMBER, addCallParams.mViaNumber);
            contentValues.put(NUMBER_PRESENTATION, java.lang.Integer.valueOf(logNumberPresentation));
            contentValues.put("type", java.lang.Integer.valueOf(addCallParams.mCallType));
            contentValues.put(FEATURES, java.lang.Integer.valueOf(addCallParams.mFeatures));
            contentValues.put("date", java.lang.Long.valueOf(addCallParams.mStart));
            contentValues.put("duration", java.lang.Long.valueOf(addCallParams.mDuration));
            if (addCallParams.mDataUsage != Long.MIN_VALUE) {
                contentValues.put(DATA_USAGE, java.lang.Long.valueOf(addCallParams.mDataUsage));
            }
            contentValues.put("subscription_component_name", str);
            contentValues.put("subscription_id", str2);
            contentValues.put(PHONE_ACCOUNT_ADDRESS, logAccountAddress);
            contentValues.put("new", (java.lang.Integer) 1);
            contentValues.put("name", str3);
            contentValues.put(ADD_FOR_ALL_USERS, java.lang.Integer.valueOf(addCallParams.mAddForAllUsers ? 1 : 0));
            if (addCallParams.mCallType == 3) {
                contentValues.put("is_read", java.lang.Integer.valueOf(addCallParams.mIsRead ? 1 : 0));
            }
            contentValues.put(BLOCK_REASON, java.lang.Integer.valueOf(addCallParams.mCallBlockReason));
            contentValues.put(CALL_SCREENING_APP_NAME, charSequenceToString(addCallParams.mCallScreeningAppName));
            contentValues.put(CALL_SCREENING_COMPONENT_NAME, addCallParams.mCallScreeningComponentName);
            contentValues.put(MISSED_REASON, java.lang.Long.valueOf(addCallParams.mMissedReason));
            contentValues.put("priority", java.lang.Integer.valueOf(addCallParams.mPriority));
            contentValues.put("subject", addCallParams.mSubject);
            if (addCallParams.mPictureUri != null) {
                contentValues.put(COMPOSER_PHOTO_URI, addCallParams.mPictureUri.toString());
            }
            contentValues.put(IS_PHONE_ACCOUNT_MIGRATION_PENDING, java.lang.Integer.valueOf(addCallParams.mIsPhoneAccountMigrationPending));
            if (com.android.server.telecom.flags.Flags.businessCallComposer()) {
                contentValues.put(IS_BUSINESS_CALL, java.lang.Integer.valueOf(addCallParams.mIsBusinessCall ? 1 : 0));
                contentValues.put(ASSERTED_DISPLAY_NAME, addCallParams.mAssertedDisplayName);
            }
            if (addCallParams.mCallerInfo != null && addCallParams.mCallerInfo.getContactId() > 0) {
                if (addCallParams.mCallerInfo.normalizedNumber != null) {
                    query = contentResolver.query(android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new java.lang.String[]{"_id"}, "contact_id =? AND data4 =?", new java.lang.String[]{java.lang.String.valueOf(addCallParams.mCallerInfo.getContactId()), addCallParams.mCallerInfo.normalizedNumber}, null);
                } else {
                    query = contentResolver.query(android.net.Uri.withAppendedPath(android.provider.ContactsContract.CommonDataKinds.Callable.CONTENT_FILTER_URI, android.net.Uri.encode(addCallParams.mCallerInfo.getPhoneNumber() != null ? addCallParams.mCallerInfo.getPhoneNumber() : addCallParams.mNumber)), new java.lang.String[]{"_id"}, "contact_id =?", new java.lang.String[]{java.lang.String.valueOf(addCallParams.mCallerInfo.getContactId())}, null);
                }
                if (query != null) {
                    try {
                        if (query.getCount() > 0 && query.moveToFirst()) {
                            java.lang.String string = query.getString(0);
                            updateDataUsageStatForData(contentResolver, string);
                            if (addCallParams.mDuration >= 10000 && addCallParams.mCallType == 2 && android.text.TextUtils.isEmpty(addCallParams.mCallerInfo.normalizedNumber)) {
                                updateNormalizedNumber(context, contentResolver, string, addCallParams.mNumber);
                            }
                        }
                    } finally {
                        query.close();
                    }
                }
            }
            android.os.UserManager userManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
            int processUserId = userManager.getProcessUserId();
            if (addCallParams.mAddForAllUsers) {
                if (userManager.isUserUnlocked(android.os.UserHandle.SYSTEM) && (maybeInsertLocation = maybeInsertLocation(addCallParams, contentResolver, android.os.UserHandle.SYSTEM)) != null) {
                    contentValues.put("location", maybeInsertLocation.toString());
                }
                android.net.Uri addEntryAndRemoveExpiredEntries = addEntryAndRemoveExpiredEntries(context, userManager, android.os.UserHandle.SYSTEM, contentValues);
                if (addEntryAndRemoveExpiredEntries == null || android.provider.CallLog.SHADOW_AUTHORITY.equals(addEntryAndRemoveExpiredEntries.getAuthority())) {
                    return null;
                }
                if (processUserId != 0) {
                    addEntryAndRemoveExpiredEntries = null;
                }
                java.util.List<android.content.pm.UserInfo> aliveUsers = userManager.getAliveUsers();
                int size = aliveUsers.size();
                for (int i = 0; i < size; i++) {
                    android.os.UserHandle userHandle = aliveUsers.get(i).getUserHandle();
                    int identifier = userHandle.getIdentifier();
                    if (!userHandle.isSystem() && shouldHaveSharedCallLogEntries(context, userManager, identifier) && userManager.isUserRunning(userHandle) && userManager.isUserUnlocked(userHandle)) {
                        android.net.Uri maybeInsertLocation2 = maybeInsertLocation(addCallParams, contentResolver, userHandle);
                        if (maybeInsertLocation2 != null) {
                            contentValues.put("location", maybeInsertLocation2.toString());
                        } else {
                            contentValues.put("location", (java.lang.String) null);
                        }
                        android.net.Uri addEntryAndRemoveExpiredEntries2 = addEntryAndRemoveExpiredEntries(context, userManager, userHandle, contentValues);
                        if (identifier == processUserId) {
                            addEntryAndRemoveExpiredEntries = addEntryAndRemoveExpiredEntries2;
                        }
                    }
                }
                return addEntryAndRemoveExpiredEntries;
            }
            if (addCallParams.mUserToBeInsertedTo != null) {
                of = addCallParams.mUserToBeInsertedTo;
            } else {
                of = android.os.UserHandle.of(processUserId);
            }
            if (userManager.isUserRunning(of) && userManager.isUserUnlocked(of)) {
                android.net.Uri maybeInsertLocation3 = maybeInsertLocation(addCallParams, contentResolver, of);
                if (maybeInsertLocation3 != null) {
                    contentValues.put("location", maybeInsertLocation3.toString());
                } else {
                    contentValues.put("location", (java.lang.String) null);
                }
            }
            return addEntryAndRemoveExpiredEntries(context, userManager, of, contentValues);
        }

        private static java.lang.String charSequenceToString(java.lang.CharSequence charSequence) {
            if (charSequence == null) {
                return null;
            }
            return charSequence.toString();
        }

        public static boolean shouldHaveSharedCallLogEntries(android.content.Context context, android.os.UserManager userManager, int i) {
            android.content.pm.UserInfo userInfo;
            return (userManager.hasUserRestriction(android.os.UserManager.DISALLOW_OUTGOING_CALLS, android.os.UserHandle.of(i)) || (userInfo = userManager.getUserInfo(i)) == null || userInfo.isManagedProfile()) ? false : true;
        }

        public static java.lang.String getLastOutgoingCall(android.content.Context context) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            android.database.Cursor cursor = null;
            try {
                cursor = contentResolver.query(CONTENT_URI_LIMIT_1, new java.lang.String[]{"number"}, "type = 2", null, "date DESC");
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
                return "";
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private static android.net.Uri addEntryAndRemoveExpiredEntries(android.content.Context context, android.os.UserManager userManager, android.os.UserHandle userHandle, android.content.ContentValues contentValues) {
            int delete;
            android.content.ContentResolver contentResolver = context.getContentResolver();
            android.net.Uri maybeAddUserId = android.content.ContentProvider.maybeAddUserId(userManager.isUserUnlocked(userHandle) ? CONTENT_URI : SHADOW_CONTENT_URI, userHandle.getIdentifier());
            android.util.Log.i(android.provider.CallLog.LOG_TAG, java.lang.String.format(java.util.Locale.getDefault(), "addEntryAndRemoveExpiredEntries: provider uri=%s", maybeAddUserId));
            try {
                android.net.Uri insert = contentResolver.insert(maybeAddUserId, contentValues);
                if (insert != null) {
                    java.lang.String lastPathSegment = insert.getLastPathSegment();
                    if (lastPathSegment != null && lastPathSegment.equals(android.media.AudioSystem.LEGACY_REMOTE_SUBMIX_ADDRESS)) {
                        android.util.Log.w(android.provider.CallLog.LOG_TAG, "Failed to insert into call log due to appops denial; resultUri=" + insert);
                    }
                } else {
                    android.util.Log.w(android.provider.CallLog.LOG_TAG, "Failed to insert into call log; null result uri.");
                }
                if (!contentValues.containsKey("subscription_id") || android.text.TextUtils.isEmpty(contentValues.getAsString("subscription_id")) || !contentValues.containsKey("subscription_component_name") || android.text.TextUtils.isEmpty(contentValues.getAsString("subscription_component_name"))) {
                    delete = contentResolver.delete(maybeAddUserId, "_id IN (SELECT _id FROM calls ORDER BY date DESC LIMIT -1 OFFSET 500)", null);
                } else {
                    delete = contentResolver.delete(maybeAddUserId, "_id IN (SELECT _id FROM calls WHERE subscription_component_name = ? AND subscription_id = ? ORDER BY date DESC LIMIT -1 OFFSET 500)", new java.lang.String[]{contentValues.getAsString("subscription_component_name"), contentValues.getAsString("subscription_id")});
                }
                android.util.Log.i(android.provider.CallLog.LOG_TAG, "addEntry: cleaned up " + delete + " old entries");
                return insert;
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(android.provider.CallLog.LOG_TAG, "Failed to insert calllog", e);
                return null;
            }
        }

        private static android.net.Uri maybeInsertLocation(android.provider.CallLog.AddCallParams addCallParams, android.content.ContentResolver contentResolver, android.os.UserHandle userHandle) {
            if (java.lang.Double.isNaN(addCallParams.mLatitude) || java.lang.Double.isNaN(addCallParams.mLongitude)) {
                return null;
            }
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put(android.provider.CallLog.Locations.LATITUDE, java.lang.Double.valueOf(addCallParams.mLatitude));
            contentValues.put(android.provider.CallLog.Locations.LONGITUDE, java.lang.Double.valueOf(addCallParams.mLongitude));
            try {
                return contentResolver.insert(android.content.ContentProvider.maybeAddUserId(android.provider.CallLog.Locations.CONTENT_URI, userHandle.getIdentifier()), contentValues);
            } catch (java.lang.SecurityException e) {
                android.util.Log.w(android.provider.CallLog.LOG_TAG, "Skipping inserting location because caller lacks ACCESS_FINE_LOCATION.");
                return null;
            }
        }

        private static void updateDataUsageStatForData(android.content.ContentResolver contentResolver, java.lang.String str) {
            contentResolver.update(android.provider.ContactsContract.DataUsageFeedback.FEEDBACK_URI.buildUpon().appendPath(str).appendQueryParameter("type", "call").build(), new android.content.ContentValues(), null, null);
        }

        private static void updateNormalizedNumber(android.content.Context context, android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2) {
            if (android.text.TextUtils.isEmpty(str2) || android.text.TextUtils.isEmpty(str)) {
                return;
            }
            java.lang.String currentCountryIso = getCurrentCountryIso(context);
            if (android.text.TextUtils.isEmpty(currentCountryIso)) {
                return;
            }
            java.lang.String formatNumberToE164 = android.telephony.PhoneNumberUtils.formatNumberToE164(str2, currentCountryIso);
            if (android.text.TextUtils.isEmpty(formatNumberToE164)) {
                return;
            }
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("data4", formatNumberToE164);
            contentResolver.update(android.provider.ContactsContract.Data.CONTENT_URI, contentValues, "_id=?", new java.lang.String[]{str});
        }

        private static int getLogNumberPresentation(java.lang.String str, int i) {
            if (i == 2) {
                return i;
            }
            if (i == 4) {
                return i;
            }
            if (i == 5) {
                return 5;
            }
            return (android.text.TextUtils.isEmpty(str) || i == 3) ? 3 : 1;
        }

        private static java.lang.String getLogAccountAddress(android.content.Context context, android.telecom.PhoneAccountHandle phoneAccountHandle) {
            android.telecom.TelecomManager telecomManager;
            android.telecom.PhoneAccount phoneAccount;
            android.net.Uri subscriptionAddress;
            try {
                telecomManager = (android.telecom.TelecomManager) context.getSystemService(android.telecom.TelecomManager.class);
            } catch (java.lang.UnsupportedOperationException e) {
                telecomManager = null;
            }
            if (telecomManager == null || phoneAccountHandle == null || (phoneAccount = telecomManager.getPhoneAccount(phoneAccountHandle)) == null || (subscriptionAddress = phoneAccount.getSubscriptionAddress()) == null) {
                return null;
            }
            return subscriptionAddress.getSchemeSpecificPart();
        }

        private static java.lang.String getCurrentCountryIso(android.content.Context context) {
            android.location.Country detectCountry;
            android.location.CountryDetector countryDetector = (android.location.CountryDetector) context.getSystemService(android.content.Context.COUNTRY_DETECTOR);
            if (countryDetector != null && (detectCountry = countryDetector.detectCountry()) != null) {
                return detectCountry.getCountryIso();
            }
            return null;
        }

        public static boolean isUserMissed(long j) {
            return j >= 65536;
        }
    }

    public static class Locations implements android.provider.BaseColumns {
        public static final java.lang.String AUTHORITY = "call_composer_locations";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/call_composer_location";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/call_composer_location";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://call_composer_locations");
        public static final java.lang.String LATITUDE = "latitude";
        public static final java.lang.String LONGITUDE = "longitude";

        private Locations() {
        }
    }
}
