package android.provider;

/* loaded from: classes3.dex */
public final class E2eeContactKeysManager {
    private static final int ARRAY_IS_NULL = -1;
    public static final java.lang.String AUTHORITY = "com.android.contactkeys.contactkeysprovider";
    public static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.contactkeys.contactkeysprovider");
    private static final int MAX_KEY_SIZE_BYTES = 5000;
    public static final int VERIFICATION_STATE_UNVERIFIED = 0;
    public static final int VERIFICATION_STATE_VERIFICATION_FAILED = 1;
    public static final int VERIFICATION_STATE_VERIFIED = 2;
    private final android.content.ContentResolver mContentResolver;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface VerificationState {
    }

    public E2eeContactKeysManager(android.content.Context context) {
        java.util.Objects.requireNonNull(context);
        this.mContentResolver = context.getContentResolver();
    }

    public void updateOrInsertE2eeContactKey(java.lang.String str, java.lang.String str2, java.lang.String str3, byte[] bArr) {
        validateKeyLength(bArr);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putByteArray(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_VALUE, (byte[]) java.util.Objects.requireNonNull(bArr));
        nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_OR_INSERT_CONTACT_KEY_METHOD, bundle);
    }

    public android.provider.E2eeContactKeysManager.E2eeContactKey getE2eeContactKey(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_CONTACT_KEY_METHOD, bundle);
        if (nullSafeCall == null) {
            return null;
        }
        return (android.provider.E2eeContactKeysManager.E2eeContactKey) nullSafeCall.getParcelable(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEY, android.provider.E2eeContactKeysManager.E2eeContactKey.class);
    }

    public java.util.List<android.provider.E2eeContactKeysManager.E2eeContactKey> getAllE2eeContactKeys(java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_ALL_CONTACT_KEYS_METHOD, bundle);
        if (nullSafeCall == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList parcelableArrayList = nullSafeCall.getParcelableArrayList(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEYS, android.provider.E2eeContactKeysManager.E2eeContactKey.class);
        if (parcelableArrayList == null) {
            return new java.util.ArrayList();
        }
        return parcelableArrayList;
    }

    public java.util.List<android.provider.E2eeContactKeysManager.E2eeContactKey> getOwnerE2eeContactKeys(java.lang.String str) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_OWNER_CONTACT_KEYS_METHOD, bundle);
        if (nullSafeCall == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList parcelableArrayList = nullSafeCall.getParcelableArrayList(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEYS, android.provider.E2eeContactKeysManager.E2eeContactKey.class);
        if (parcelableArrayList == null) {
            return new java.util.ArrayList();
        }
        return parcelableArrayList;
    }

    public boolean updateE2eeContactKeyLocalVerificationState(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.LOCAL_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @android.annotation.SystemApi
    public boolean updateE2eeContactKeyLocalVerificationState(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.OWNER_PACKAGE_NAME, (java.lang.String) java.util.Objects.requireNonNull(str4));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.LOCAL_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateE2eeContactKeyRemoteVerificationState(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @android.annotation.SystemApi
    public boolean updateE2eeContactKeyRemoteVerificationState(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.OWNER_PACKAGE_NAME, (java.lang.String) java.util.Objects.requireNonNull(str4));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateVerificationState(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Verification state value " + i + " is not supported");
        }
    }

    public boolean removeE2eeContactKey(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString("lookup", (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str3));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOVE_CONTACT_KEY_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public boolean updateOrInsertE2eeSelfKey(java.lang.String str, java.lang.String str2, byte[] bArr) {
        validateKeyLength(bArr);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putByteArray(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_VALUE, (byte[]) java.util.Objects.requireNonNull(bArr));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_OR_INSERT_SELF_KEY_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private static void validateKeyLength(byte[] bArr) {
        java.util.Objects.requireNonNull(bArr);
        if (bArr.length == 0 || bArr.length > getMaxKeySizeBytes()) {
            throw new java.lang.IllegalArgumentException("Key value length is " + bArr.length + ". Should be more than 0 and less than " + getMaxKeySizeBytes());
        }
    }

    public boolean updateE2eeSelfKeyRemoteVerificationState(java.lang.String str, java.lang.String str2, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    @android.annotation.SystemApi
    public boolean updateE2eeSelfKeyRemoteVerificationState(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) {
        validateVerificationState(i);
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str2));
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.OWNER_PACKAGE_NAME, (java.lang.String) java.util.Objects.requireNonNull(str3));
        bundle.putInt(android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOTE_VERIFICATION_STATE, i);
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    public static int getMaxKeySizeBytes() {
        return 5000;
    }

    public android.provider.E2eeContactKeysManager.E2eeSelfKey getE2eeSelfKey(java.lang.String str, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str2));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_SELF_KEY_METHOD, bundle);
        if (nullSafeCall == null) {
            return null;
        }
        return (android.provider.E2eeContactKeysManager.E2eeSelfKey) nullSafeCall.getParcelable(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEY, android.provider.E2eeContactKeysManager.E2eeSelfKey.class);
    }

    public java.util.List<android.provider.E2eeContactKeysManager.E2eeSelfKey> getAllE2eeSelfKeys() {
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_ALL_SELF_KEYS_METHOD, new android.os.Bundle());
        if (nullSafeCall == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList parcelableArrayList = nullSafeCall.getParcelableArrayList(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEYS, android.provider.E2eeContactKeysManager.E2eeSelfKey.class);
        if (parcelableArrayList == null) {
            return new java.util.ArrayList();
        }
        return parcelableArrayList;
    }

    public java.util.List<android.provider.E2eeContactKeysManager.E2eeSelfKey> getOwnerE2eeSelfKeys() {
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.GET_OWNER_SELF_KEYS_METHOD, new android.os.Bundle());
        if (nullSafeCall == null) {
            return new java.util.ArrayList();
        }
        java.util.ArrayList parcelableArrayList = nullSafeCall.getParcelableArrayList(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_CONTACT_KEYS, android.provider.E2eeContactKeysManager.E2eeSelfKey.class);
        if (parcelableArrayList == null) {
            return new java.util.ArrayList();
        }
        return parcelableArrayList;
    }

    public boolean removeE2eeSelfKey(java.lang.String str, java.lang.String str2) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putString(android.provider.E2eeContactKeysManager.E2eeContactKeys.DEVICE_ID, (java.lang.String) java.util.Objects.requireNonNull(str));
        bundle.putString("account_id", (java.lang.String) java.util.Objects.requireNonNull(str2));
        android.os.Bundle nullSafeCall = nullSafeCall(this.mContentResolver, android.provider.E2eeContactKeysManager.E2eeContactKeys.REMOVE_SELF_KEY_METHOD, bundle);
        return nullSafeCall != null && nullSafeCall.getBoolean(android.provider.E2eeContactKeysManager.E2eeContactKeys.KEY_UPDATED_ROWS);
    }

    private android.os.Bundle nullSafeCall(android.content.ContentResolver contentResolver, java.lang.String str, android.os.Bundle bundle) {
        try {
            android.content.ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(AUTHORITY_URI);
            try {
                android.os.Bundle call = acquireContentProviderClient.call(str, null, bundle);
                if (acquireContentProviderClient != null) {
                    acquireContentProviderClient.close();
                }
                return call;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public static final class E2eeContactKeys {
        public static final java.lang.String ACCOUNT_ID = "account_id";
        public static final java.lang.String DEVICE_ID = "device_id";
        public static final java.lang.String DISPLAY_NAME = "display_name";
        public static final java.lang.String EMAIL_ADDRESS = "address";
        public static final java.lang.String GET_ALL_CONTACT_KEYS_METHOD = "getAllContactKeys";
        public static final java.lang.String GET_ALL_SELF_KEYS_METHOD = "getAllSelfKeys";
        public static final java.lang.String GET_CONTACT_KEY_METHOD = "getContactKey";
        public static final java.lang.String GET_OWNER_CONTACT_KEYS_METHOD = "getOwnerContactKeys";
        public static final java.lang.String GET_OWNER_SELF_KEYS_METHOD = "getOwnerSelfKeys";
        public static final java.lang.String GET_SELF_KEY_METHOD = "getSelfKey";
        public static final java.lang.String KEY_CONTACT_KEY = "key_contact_key";
        public static final java.lang.String KEY_CONTACT_KEYS = "key_contact_keys";
        public static final java.lang.String KEY_UPDATED_ROWS = "key_updated_rows";
        public static final java.lang.String KEY_VALUE = "key_value";
        public static final java.lang.String LOCAL_VERIFICATION_STATE = "local_verification_state";
        public static final java.lang.String LOOKUP_KEY = "lookup";
        public static final java.lang.String OWNER_PACKAGE_NAME = "owner_package_name";
        public static final java.lang.String PHONE_NUMBER = "number";
        public static final java.lang.String REMOTE_VERIFICATION_STATE = "remote_verification_state";
        public static final java.lang.String REMOVE_CONTACT_KEY_METHOD = "removeContactKey";
        public static final java.lang.String REMOVE_SELF_KEY_METHOD = "removeSelfKey";
        public static final java.lang.String TIME_UPDATED = "time_updated";
        public static final java.lang.String UPDATE_CONTACT_KEY_LOCAL_VERIFICATION_STATE_METHOD = "updateContactKeyLocalVerificationState";
        public static final java.lang.String UPDATE_CONTACT_KEY_REMOTE_VERIFICATION_STATE_METHOD = "updateContactKeyRemoteVerificationState";
        public static final java.lang.String UPDATE_OR_INSERT_CONTACT_KEY_METHOD = "updateOrInsertContactKey";
        public static final java.lang.String UPDATE_OR_INSERT_SELF_KEY_METHOD = "updateOrInsertSelfKey";
        public static final java.lang.String UPDATE_SELF_KEY_REMOTE_VERIFICATION_STATE_METHOD = "updateSelfKeyRemoteVerificationState";

        private E2eeContactKeys() {
        }
    }

    public static final class E2eeContactKey extends android.provider.E2eeContactKeysManager.E2eeBaseKey implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.provider.E2eeContactKeysManager.E2eeContactKey> CREATOR = new android.os.Parcelable.Creator<android.provider.E2eeContactKeysManager.E2eeContactKey>() { // from class: android.provider.E2eeContactKeysManager.E2eeContactKey.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.E2eeContactKeysManager.E2eeContactKey createFromParcel(android.os.Parcel parcel) {
                byte[] bArr;
                java.lang.String readString8 = parcel.readString8();
                java.lang.String readString82 = parcel.readString8();
                java.lang.String readString83 = parcel.readString8();
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    byte[] bArr2 = new byte[readInt];
                    parcel.readByteArray(bArr2);
                    bArr = bArr2;
                } else {
                    bArr = null;
                }
                return new android.provider.E2eeContactKeysManager.E2eeContactKey(readString8, readString82, readString83, readLong, bArr, parcel.readInt(), parcel.readInt(), parcel.readString8(), parcel.readString8(), parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.E2eeContactKeysManager.E2eeContactKey[] newArray(int i) {
                return new android.provider.E2eeContactKeysManager.E2eeContactKey[i];
            }
        };
        private final java.lang.String mDisplayName;
        private final java.lang.String mEmailAddress;
        private final int mLocalVerificationState;
        private final java.lang.String mPhoneNumber;

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getAccountId() {
            return super.getAccountId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getDeviceId() {
            return super.getDeviceId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ byte[] getKeyValue() {
            return super.getKeyValue();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getOwnerPackageName() {
            return super.getOwnerPackageName();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ int getRemoteVerificationState() {
            return super.getRemoteVerificationState();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ long getTimeUpdated() {
            return super.getTimeUpdated();
        }

        public E2eeContactKey(java.lang.String str, java.lang.String str2, java.lang.String str3, long j, byte[] bArr, int i, int i2, java.lang.String str4, java.lang.String str5, java.lang.String str6) {
            super(str, str2, str3, j, bArr, i2);
            this.mLocalVerificationState = i;
            this.mDisplayName = str4;
            this.mPhoneNumber = str5;
            this.mEmailAddress = str6;
        }

        public int getLocalVerificationState() {
            return this.mLocalVerificationState;
        }

        public java.lang.String getDisplayName() {
            return this.mDisplayName;
        }

        public java.lang.String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public java.lang.String getEmailAddress() {
            return this.mEmailAddress;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mDeviceId, this.mAccountId, this.mOwnerPackageName, java.lang.Long.valueOf(this.mTimeUpdated), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mKeyValue)), java.lang.Integer.valueOf(this.mLocalVerificationState), java.lang.Integer.valueOf(this.mRemoteVerificationState), this.mDisplayName, this.mPhoneNumber, this.mEmailAddress);
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.provider.E2eeContactKeysManager.E2eeContactKey)) {
                return false;
            }
            android.provider.E2eeContactKeysManager.E2eeContactKey e2eeContactKey = (android.provider.E2eeContactKeysManager.E2eeContactKey) obj;
            if (!java.util.Objects.equals(this.mDeviceId, e2eeContactKey.mDeviceId) || !java.util.Objects.equals(this.mAccountId, e2eeContactKey.mAccountId) || !java.util.Objects.equals(this.mOwnerPackageName, e2eeContactKey.mOwnerPackageName) || this.mTimeUpdated != e2eeContactKey.mTimeUpdated || !java.util.Arrays.equals(this.mKeyValue, e2eeContactKey.mKeyValue) || this.mLocalVerificationState != e2eeContactKey.mLocalVerificationState || this.mRemoteVerificationState != e2eeContactKey.mRemoteVerificationState || !java.util.Objects.equals(this.mDisplayName, e2eeContactKey.mDisplayName) || !java.util.Objects.equals(this.mPhoneNumber, e2eeContactKey.mPhoneNumber) || !java.util.Objects.equals(this.mEmailAddress, e2eeContactKey.mEmailAddress)) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mDeviceId);
            parcel.writeString8(this.mAccountId);
            parcel.writeString8(this.mOwnerPackageName);
            parcel.writeLong(this.mTimeUpdated);
            parcel.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                parcel.writeByteArray(this.mKeyValue);
            }
            parcel.writeInt(this.mLocalVerificationState);
            parcel.writeInt(this.mRemoteVerificationState);
            parcel.writeString8(this.mDisplayName);
            parcel.writeString8(this.mPhoneNumber);
            parcel.writeString8(this.mEmailAddress);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class E2eeSelfKey extends android.provider.E2eeContactKeysManager.E2eeBaseKey implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.provider.E2eeContactKeysManager.E2eeSelfKey> CREATOR = new android.os.Parcelable.Creator<android.provider.E2eeContactKeysManager.E2eeSelfKey>() { // from class: android.provider.E2eeContactKeysManager.E2eeSelfKey.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.E2eeContactKeysManager.E2eeSelfKey createFromParcel(android.os.Parcel parcel) {
                byte[] bArr;
                java.lang.String readString8 = parcel.readString8();
                java.lang.String readString82 = parcel.readString8();
                java.lang.String readString83 = parcel.readString8();
                long readLong = parcel.readLong();
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    byte[] bArr2 = new byte[readInt];
                    parcel.readByteArray(bArr2);
                    bArr = bArr2;
                } else {
                    bArr = null;
                }
                return new android.provider.E2eeContactKeysManager.E2eeSelfKey(readString8, readString82, readString83, readLong, bArr, parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.E2eeContactKeysManager.E2eeSelfKey[] newArray(int i) {
                return new android.provider.E2eeContactKeysManager.E2eeSelfKey[i];
            }
        };

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getAccountId() {
            return super.getAccountId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getDeviceId() {
            return super.getDeviceId();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ byte[] getKeyValue() {
            return super.getKeyValue();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ java.lang.String getOwnerPackageName() {
            return super.getOwnerPackageName();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ int getRemoteVerificationState() {
            return super.getRemoteVerificationState();
        }

        @Override // android.provider.E2eeContactKeysManager.E2eeBaseKey
        public /* bridge */ /* synthetic */ long getTimeUpdated() {
            return super.getTimeUpdated();
        }

        public E2eeSelfKey(java.lang.String str, java.lang.String str2, java.lang.String str3, long j, byte[] bArr, int i) {
            super(str, str2, str3, j, bArr, i);
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mDeviceId, this.mAccountId, this.mOwnerPackageName, java.lang.Long.valueOf(this.mTimeUpdated), java.lang.Integer.valueOf(java.util.Arrays.hashCode(this.mKeyValue)), java.lang.Integer.valueOf(this.mRemoteVerificationState));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof android.provider.E2eeContactKeysManager.E2eeSelfKey)) {
                return false;
            }
            android.provider.E2eeContactKeysManager.E2eeSelfKey e2eeSelfKey = (android.provider.E2eeContactKeysManager.E2eeSelfKey) obj;
            if (!java.util.Objects.equals(this.mDeviceId, e2eeSelfKey.mDeviceId) || !java.util.Objects.equals(this.mAccountId, e2eeSelfKey.mAccountId) || !java.util.Objects.equals(this.mOwnerPackageName, e2eeSelfKey.mOwnerPackageName) || this.mTimeUpdated != e2eeSelfKey.mTimeUpdated || !java.util.Arrays.equals(this.mKeyValue, e2eeSelfKey.mKeyValue) || this.mRemoteVerificationState != e2eeSelfKey.mRemoteVerificationState) {
                return false;
            }
            return true;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString8(this.mDeviceId);
            parcel.writeString8(this.mAccountId);
            parcel.writeString8(this.mOwnerPackageName);
            parcel.writeLong(this.mTimeUpdated);
            parcel.writeInt(this.mKeyValue != null ? this.mKeyValue.length : -1);
            if (this.mKeyValue != null) {
                parcel.writeByteArray(this.mKeyValue);
            }
            parcel.writeInt(this.mRemoteVerificationState);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    static abstract class E2eeBaseKey {
        protected final java.lang.String mAccountId;
        protected final java.lang.String mDeviceId;
        protected final byte[] mKeyValue;
        protected final java.lang.String mOwnerPackageName;
        protected final int mRemoteVerificationState;
        protected final long mTimeUpdated;

        protected E2eeBaseKey(java.lang.String str, java.lang.String str2, java.lang.String str3, long j, byte[] bArr, int i) {
            this.mDeviceId = str;
            this.mAccountId = str2;
            this.mOwnerPackageName = str3;
            this.mTimeUpdated = j;
            this.mKeyValue = bArr == null ? null : java.util.Arrays.copyOf(bArr, bArr.length);
            this.mRemoteVerificationState = i;
        }

        public java.lang.String getDeviceId() {
            return this.mDeviceId;
        }

        public java.lang.String getAccountId() {
            return this.mAccountId;
        }

        public java.lang.String getOwnerPackageName() {
            return this.mOwnerPackageName;
        }

        public long getTimeUpdated() {
            return this.mTimeUpdated;
        }

        public byte[] getKeyValue() {
            if (this.mKeyValue == null) {
                return null;
            }
            return java.util.Arrays.copyOf(this.mKeyValue, this.mKeyValue.length);
        }

        public int getRemoteVerificationState() {
            return this.mRemoteVerificationState;
        }
    }
}
