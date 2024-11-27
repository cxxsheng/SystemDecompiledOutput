package android.service.notification;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class Adjustment implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.Adjustment> CREATOR = new android.os.Parcelable.Creator<android.service.notification.Adjustment>() { // from class: android.service.notification.Adjustment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.Adjustment createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.Adjustment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.Adjustment[] newArray(int i) {
            return new android.service.notification.Adjustment[i];
        }
    };
    public static final java.lang.String KEY_CONTEXTUAL_ACTIONS = "key_contextual_actions";
    public static final java.lang.String KEY_GROUP_KEY = "key_group_key";
    public static final java.lang.String KEY_IMPORTANCE = "key_importance";
    public static final java.lang.String KEY_IMPORTANCE_PROPOSAL = "key_importance_proposal";

    @android.annotation.SystemApi
    public static final java.lang.String KEY_NOT_CONVERSATION = "key_not_conversation";

    @android.annotation.SystemApi
    public static final java.lang.String KEY_PEOPLE = "key_people";
    public static final java.lang.String KEY_RANKING_SCORE = "key_ranking_score";
    public static final java.lang.String KEY_SENSITIVE_CONTENT = "key_sensitive_content";
    public static final java.lang.String KEY_SNOOZE_CRITERIA = "key_snooze_criteria";
    public static final java.lang.String KEY_TEXT_REPLIES = "key_text_replies";
    public static final java.lang.String KEY_USER_SENTIMENT = "key_user_sentiment";
    private final java.lang.CharSequence mExplanation;
    private java.lang.String mIssuer;
    private final java.lang.String mKey;
    private final java.lang.String mPackage;
    private final android.os.Bundle mSignals;
    private final int mUser;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Keys {
    }

    @android.annotation.SystemApi
    public Adjustment(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, java.lang.CharSequence charSequence, int i) {
        this.mPackage = str;
        this.mKey = str2;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = i;
    }

    public Adjustment(java.lang.String str, java.lang.String str2, android.os.Bundle bundle, java.lang.CharSequence charSequence, android.os.UserHandle userHandle) {
        this.mPackage = str;
        this.mKey = str2;
        this.mSignals = bundle;
        this.mExplanation = charSequence;
        this.mUser = userHandle.getIdentifier();
    }

    @android.annotation.SystemApi
    protected Adjustment(android.os.Parcel parcel) {
        if (parcel.readInt() == 1) {
            this.mPackage = parcel.readString();
        } else {
            this.mPackage = null;
        }
        if (parcel.readInt() == 1) {
            this.mKey = parcel.readString();
        } else {
            this.mKey = null;
        }
        if (parcel.readInt() == 1) {
            this.mExplanation = parcel.readCharSequence();
        } else {
            this.mExplanation = null;
        }
        this.mSignals = parcel.readBundle();
        this.mUser = parcel.readInt();
        this.mIssuer = parcel.readString();
    }

    public java.lang.String getPackage() {
        return this.mPackage;
    }

    public java.lang.String getKey() {
        return this.mKey;
    }

    public java.lang.CharSequence getExplanation() {
        return this.mExplanation;
    }

    public android.os.Bundle getSignals() {
        return this.mSignals;
    }

    @android.annotation.SystemApi
    public int getUser() {
        return this.mUser;
    }

    public android.os.UserHandle getUserHandle() {
        return android.os.UserHandle.of(this.mUser);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mPackage != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mPackage);
        } else {
            parcel.writeInt(0);
        }
        if (this.mKey != null) {
            parcel.writeInt(1);
            parcel.writeString(this.mKey);
        } else {
            parcel.writeInt(0);
        }
        if (this.mExplanation != null) {
            parcel.writeInt(1);
            parcel.writeCharSequence(this.mExplanation);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBundle(this.mSignals);
        parcel.writeInt(this.mUser);
        parcel.writeString(this.mIssuer);
    }

    public java.lang.String toString() {
        return "Adjustment{mSignals=" + this.mSignals + '}';
    }

    public void setIssuer(java.lang.String str) {
        this.mIssuer = str;
    }

    public java.lang.String getIssuer() {
        return this.mIssuer;
    }
}
