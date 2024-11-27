package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class NewUserRequest {
    private final java.lang.String mAccountName;
    private final android.os.PersistableBundle mAccountOptions;
    private final java.lang.String mAccountType;
    private final boolean mAdmin;
    private final boolean mEphemeral;
    private final java.lang.String mName;
    private final android.graphics.Bitmap mUserIcon;
    private final java.lang.String mUserType;

    private NewUserRequest(android.os.NewUserRequest.Builder builder) {
        this.mName = builder.mName;
        this.mAdmin = builder.mAdmin;
        this.mEphemeral = builder.mEphemeral;
        this.mUserType = builder.mUserType;
        this.mUserIcon = builder.mUserIcon;
        this.mAccountName = builder.mAccountName;
        this.mAccountType = builder.mAccountType;
        this.mAccountOptions = builder.mAccountOptions;
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public boolean isEphemeral() {
        return this.mEphemeral;
    }

    public boolean isAdmin() {
        return this.mAdmin;
    }

    int getFlags() {
        int i = isAdmin() ? 2 : 0;
        return isEphemeral() ? i | 256 : i;
    }

    public java.lang.String getUserType() {
        return this.mUserType;
    }

    public android.graphics.Bitmap getUserIcon() {
        return this.mUserIcon;
    }

    public java.lang.String getAccountName() {
        return this.mAccountName;
    }

    public java.lang.String getAccountType() {
        return this.mAccountType;
    }

    public android.os.PersistableBundle getAccountOptions() {
        return this.mAccountOptions;
    }

    public java.lang.String toString() {
        return "NewUserRequest{mName='" + this.mName + android.text.format.DateFormat.QUOTE + ", mAdmin=" + this.mAdmin + ", mEphemeral=" + this.mEphemeral + ", mUserType='" + this.mUserType + android.text.format.DateFormat.QUOTE + ", mAccountName='" + this.mAccountName + android.text.format.DateFormat.QUOTE + ", mAccountType='" + this.mAccountType + android.text.format.DateFormat.QUOTE + ", mAccountOptions=" + this.mAccountOptions + '}';
    }

    public static final class Builder {
        private java.lang.String mAccountName;
        private android.os.PersistableBundle mAccountOptions;
        private java.lang.String mAccountType;
        private boolean mAdmin;
        private boolean mEphemeral;
        private java.lang.String mName;
        private android.graphics.Bitmap mUserIcon;
        private java.lang.String mUserType = android.os.UserManager.USER_TYPE_FULL_SECONDARY;

        public android.os.NewUserRequest.Builder setName(java.lang.String str) {
            this.mName = str;
            return this;
        }

        public android.os.NewUserRequest.Builder setAdmin() {
            this.mAdmin = true;
            return this;
        }

        public android.os.NewUserRequest.Builder setEphemeral() {
            this.mEphemeral = true;
            return this;
        }

        public android.os.NewUserRequest.Builder setUserType(java.lang.String str) {
            this.mUserType = str;
            return this;
        }

        public android.os.NewUserRequest.Builder setUserIcon(android.graphics.Bitmap bitmap) {
            this.mUserIcon = bitmap;
            return this;
        }

        public android.os.NewUserRequest.Builder setAccountName(java.lang.String str) {
            this.mAccountName = str;
            return this;
        }

        public android.os.NewUserRequest.Builder setAccountType(java.lang.String str) {
            this.mAccountType = str;
            return this;
        }

        public android.os.NewUserRequest.Builder setAccountOptions(android.os.PersistableBundle persistableBundle) {
            this.mAccountOptions = persistableBundle;
            return this;
        }

        public android.os.NewUserRequest build() {
            checkIfPropertiesAreCompatible();
            return new android.os.NewUserRequest(this);
        }

        private void checkIfPropertiesAreCompatible() {
            if (this.mUserType == null) {
                throw new java.lang.IllegalStateException("Usertype cannot be null");
            }
            if (this.mAdmin && !this.mUserType.equals(android.os.UserManager.USER_TYPE_FULL_SECONDARY)) {
                throw new java.lang.IllegalStateException("Admin user can't be of type: " + this.mUserType);
            }
            if (android.text.TextUtils.isEmpty(this.mAccountName) != android.text.TextUtils.isEmpty(this.mAccountType)) {
                throw new java.lang.IllegalStateException("Account name and account type should be provided together.");
            }
        }
    }
}
