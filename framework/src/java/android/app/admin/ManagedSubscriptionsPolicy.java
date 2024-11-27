package android.app.admin;

/* loaded from: classes.dex */
public final class ManagedSubscriptionsPolicy implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.admin.ManagedSubscriptionsPolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.ManagedSubscriptionsPolicy>() { // from class: android.app.admin.ManagedSubscriptionsPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ManagedSubscriptionsPolicy createFromParcel(android.os.Parcel parcel) {
            return new android.app.admin.ManagedSubscriptionsPolicy(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.ManagedSubscriptionsPolicy[] newArray(int i) {
            return new android.app.admin.ManagedSubscriptionsPolicy[i];
        }
    };
    private static final java.lang.String KEY_POLICY_TYPE = "policy_type";
    private static final java.lang.String TAG = "ManagedSubscriptionsPolicy";
    public static final int TYPE_ALL_MANAGED_SUBSCRIPTIONS = 1;
    public static final int TYPE_ALL_PERSONAL_SUBSCRIPTIONS = 0;
    private final int mPolicyType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ManagedSubscriptionsPolicyType {
    }

    public ManagedSubscriptionsPolicy(int i) {
        if (i != 0 && i != 1) {
            throw new java.lang.IllegalArgumentException("Invalid policy type");
        }
        this.mPolicyType = i;
    }

    public int getPolicyType() {
        return this.mPolicyType;
    }

    public java.lang.String toString() {
        return android.text.TextUtils.formatSimple("ManagedSubscriptionsPolicy (type: %d)", java.lang.Integer.valueOf(this.mPolicyType));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mPolicyType);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof android.app.admin.ManagedSubscriptionsPolicy) && this.mPolicyType == ((android.app.admin.ManagedSubscriptionsPolicy) obj).mPolicyType;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPolicyType));
    }

    public static android.app.admin.ManagedSubscriptionsPolicy readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            return new android.app.admin.ManagedSubscriptionsPolicy(typedXmlPullParser.getAttributeInt(null, KEY_POLICY_TYPE, -1));
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "Load xml failed", e);
            return null;
        }
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeInt(null, KEY_POLICY_TYPE, this.mPolicyType);
    }
}
