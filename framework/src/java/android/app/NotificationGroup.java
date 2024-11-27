package android.app;

/* loaded from: classes.dex */
public class NotificationGroup implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.NotificationGroup> CREATOR = new android.os.Parcelable.Creator<android.app.NotificationGroup>() { // from class: android.app.NotificationGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationGroup createFromParcel(android.os.Parcel parcel) {
            return new android.app.NotificationGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationGroup[] newArray(int i) {
            return new android.app.NotificationGroup[i];
        }
    };
    private static final java.lang.String TAG = "NotificationGroup";
    private boolean mDirty;
    private java.lang.String mName;
    private int mNameResId;
    private java.util.Set<java.lang.String> mPackages;
    private java.util.UUID mUuid;

    public NotificationGroup(java.lang.String str) {
        this(str, -1, null);
    }

    public NotificationGroup(java.lang.String str, int i, java.util.UUID uuid) {
        this.mPackages = new java.util.HashSet();
        this.mName = str;
        this.mNameResId = i;
        this.mUuid = uuid != null ? uuid : java.util.UUID.randomUUID();
        this.mDirty = uuid == null;
    }

    private NotificationGroup(android.os.Parcel parcel) {
        this.mPackages = new java.util.HashSet();
        readFromParcel(parcel);
    }

    public java.lang.String toString() {
        return getName();
    }

    public java.lang.String getName() {
        return this.mName;
    }

    public void setName(java.lang.String str) {
        this.mName = str;
        this.mNameResId = -1;
        this.mDirty = true;
    }

    public java.util.UUID getUuid() {
        return this.mUuid;
    }

    public void addPackage(java.lang.String str) {
        this.mPackages.add(str);
        this.mDirty = true;
    }

    public java.lang.String[] getPackages() {
        return (java.lang.String[]) this.mPackages.toArray(new java.lang.String[this.mPackages.size()]);
    }

    public void removePackage(java.lang.String str) {
        this.mPackages.remove(str);
        this.mDirty = true;
    }

    public boolean hasPackage(java.lang.String str) {
        return this.mPackages.contains(str);
    }

    public boolean isDirty() {
        return this.mDirty;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeInt(this.mNameResId);
        parcel.writeInt(this.mDirty ? 1 : 0);
        new android.os.ParcelUuid(this.mUuid).writeToParcel(parcel, 0);
        parcel.writeStringArray(getPackages());
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mName = parcel.readString();
        this.mNameResId = parcel.readInt();
        this.mDirty = parcel.readInt() != 0;
        this.mUuid = android.os.ParcelUuid.CREATOR.createFromParcel(parcel).getUuid();
        this.mPackages.addAll(java.util.Arrays.asList(parcel.readStringArray()));
    }

    public void getXmlString(java.lang.StringBuilder sb, android.content.Context context) {
        sb.append("<notificationGroup ");
        if (this.mNameResId > 0) {
            sb.append("nameres=\"");
            sb.append(context.getResources().getResourceEntryName(this.mNameResId));
        } else {
            sb.append("name=\"");
            sb.append(android.text.TextUtils.htmlEncode(getName()));
        }
        sb.append("\" uuid=\"");
        sb.append(android.text.TextUtils.htmlEncode(getUuid().toString()));
        sb.append("\">\n");
        java.util.Iterator<java.lang.String> it = this.mPackages.iterator();
        while (it.hasNext()) {
            sb.append("<package>" + android.text.TextUtils.htmlEncode(it.next()) + "</package>\n");
        }
        sb.append("</notificationGroup>\n");
        this.mDirty = false;
    }

    public static android.app.NotificationGroup fromXml(org.xmlpull.v1.XmlPullParser xmlPullParser, android.content.Context context) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int i;
        java.lang.String str;
        java.util.UUID uuid = null;
        java.lang.String attributeValue = xmlPullParser.getAttributeValue(null, "nameres");
        if (attributeValue == null) {
            i = -1;
            str = null;
        } else {
            i = context.getResources().getIdentifier(attributeValue, "string", "android");
            if (i <= 0) {
                str = null;
            } else {
                str = context.getResources().getString(i);
            }
        }
        if (str == null) {
            str = xmlPullParser.getAttributeValue(null, "name");
        }
        java.lang.String attributeValue2 = xmlPullParser.getAttributeValue(null, "uuid");
        if (attributeValue2 != null) {
            try {
                uuid = java.util.UUID.fromString(attributeValue2);
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.w(TAG, "UUID not recognized for " + str + ", using new one.");
            }
        }
        android.app.NotificationGroup notificationGroup = new android.app.NotificationGroup(str, i, uuid);
        int next = xmlPullParser.next();
        while (true) {
            if (next != 3 || !xmlPullParser.getName().equals("notificationGroup")) {
                if (next == 2 && xmlPullParser.getName().equals("package")) {
                    notificationGroup.addPackage(xmlPullParser.nextText());
                }
                next = xmlPullParser.next();
            } else {
                notificationGroup.mDirty = false;
                return notificationGroup;
            }
        }
    }
}
