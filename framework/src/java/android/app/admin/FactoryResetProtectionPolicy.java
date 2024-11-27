package android.app.admin;

/* loaded from: classes.dex */
public final class FactoryResetProtectionPolicy implements android.os.Parcelable {
    private static final java.lang.String ATTR_VALUE = "value";
    public static final android.os.Parcelable.Creator<android.app.admin.FactoryResetProtectionPolicy> CREATOR = new android.os.Parcelable.Creator<android.app.admin.FactoryResetProtectionPolicy>() { // from class: android.app.admin.FactoryResetProtectionPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FactoryResetProtectionPolicy createFromParcel(android.os.Parcel parcel) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int readInt = parcel.readInt();
            for (int i = 0; i < readInt; i++) {
                arrayList.add(parcel.readString());
            }
            return new android.app.admin.FactoryResetProtectionPolicy(arrayList, parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.admin.FactoryResetProtectionPolicy[] newArray(int i) {
            return new android.app.admin.FactoryResetProtectionPolicy[i];
        }
    };
    private static final java.lang.String KEY_FACTORY_RESET_PROTECTION_ACCOUNT = "factory_reset_protection_account";
    private static final java.lang.String KEY_FACTORY_RESET_PROTECTION_ENABLED = "factory_reset_protection_enabled";
    private static final java.lang.String LOG_TAG = "FactoryResetProtectionPolicy";
    private final java.util.List<java.lang.String> mFactoryResetProtectionAccounts;
    private final boolean mFactoryResetProtectionEnabled;

    private FactoryResetProtectionPolicy(java.util.List<java.lang.String> list, boolean z) {
        this.mFactoryResetProtectionAccounts = list;
        this.mFactoryResetProtectionEnabled = z;
    }

    public java.util.List<java.lang.String> getFactoryResetProtectionAccounts() {
        return this.mFactoryResetProtectionAccounts;
    }

    public boolean isFactoryResetProtectionEnabled() {
        return this.mFactoryResetProtectionEnabled;
    }

    public static class Builder {
        private java.util.List<java.lang.String> mFactoryResetProtectionAccounts;
        private boolean mFactoryResetProtectionEnabled = true;

        public android.app.admin.FactoryResetProtectionPolicy.Builder setFactoryResetProtectionAccounts(java.util.List<java.lang.String> list) {
            this.mFactoryResetProtectionAccounts = new java.util.ArrayList(list);
            return this;
        }

        public android.app.admin.FactoryResetProtectionPolicy.Builder setFactoryResetProtectionEnabled(boolean z) {
            this.mFactoryResetProtectionEnabled = z;
            return this;
        }

        public android.app.admin.FactoryResetProtectionPolicy build() {
            return new android.app.admin.FactoryResetProtectionPolicy(this.mFactoryResetProtectionAccounts, this.mFactoryResetProtectionEnabled);
        }
    }

    public java.lang.String toString() {
        return "FactoryResetProtectionPolicy{mFactoryResetProtectionAccounts=" + this.mFactoryResetProtectionAccounts + ", mFactoryResetProtectionEnabled=" + this.mFactoryResetProtectionEnabled + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mFactoryResetProtectionAccounts.size());
        java.util.Iterator<java.lang.String> it = this.mFactoryResetProtectionAccounts.iterator();
        while (it.hasNext()) {
            parcel.writeString(it.next());
        }
        parcel.writeBoolean(this.mFactoryResetProtectionEnabled);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static android.app.admin.FactoryResetProtectionPolicy readFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        try {
            boolean attributeBoolean = typedXmlPullParser.getAttributeBoolean(null, KEY_FACTORY_RESET_PROTECTION_ENABLED, false);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            int depth = typedXmlPullParser.getDepth();
            while (true) {
                int next = typedXmlPullParser.next();
                if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                    break;
                }
                if (next != 3 && next != 4 && typedXmlPullParser.getName().equals(KEY_FACTORY_RESET_PROTECTION_ACCOUNT)) {
                    arrayList.add(typedXmlPullParser.getAttributeValue(null, "value"));
                }
            }
            return new android.app.admin.FactoryResetProtectionPolicy(arrayList, attributeBoolean);
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
            android.util.Log.w(LOG_TAG, "Reading from xml failed", e);
            return null;
        }
    }

    public void writeToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.attributeBoolean(null, KEY_FACTORY_RESET_PROTECTION_ENABLED, this.mFactoryResetProtectionEnabled);
        for (java.lang.String str : this.mFactoryResetProtectionAccounts) {
            typedXmlSerializer.startTag(null, KEY_FACTORY_RESET_PROTECTION_ACCOUNT);
            typedXmlSerializer.attribute(null, "value", str);
            typedXmlSerializer.endTag(null, KEY_FACTORY_RESET_PROTECTION_ACCOUNT);
        }
    }

    public boolean isNotEmpty() {
        return !this.mFactoryResetProtectionAccounts.isEmpty() && this.mFactoryResetProtectionEnabled;
    }

    public void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.print("factoryResetProtectionEnabled=");
        indentingPrintWriter.println(this.mFactoryResetProtectionEnabled);
        indentingPrintWriter.print("factoryResetProtectionAccounts=");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mFactoryResetProtectionAccounts.size(); i++) {
            indentingPrintWriter.println(this.mFactoryResetProtectionAccounts.get(i));
        }
        indentingPrintWriter.decreaseIndent();
    }
}
