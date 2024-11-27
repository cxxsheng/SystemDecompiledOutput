package android.app.slice;

/* loaded from: classes.dex */
public final class SliceItem implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.slice.SliceItem> CREATOR = new android.os.Parcelable.Creator<android.app.slice.SliceItem>() { // from class: android.app.slice.SliceItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.SliceItem createFromParcel(android.os.Parcel parcel) {
            return new android.app.slice.SliceItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.SliceItem[] newArray(int i) {
            return new android.app.slice.SliceItem[i];
        }
    };
    public static final java.lang.String FORMAT_ACTION = "action";
    public static final java.lang.String FORMAT_BUNDLE = "bundle";
    public static final java.lang.String FORMAT_IMAGE = "image";
    public static final java.lang.String FORMAT_INT = "int";
    public static final java.lang.String FORMAT_LONG = "long";
    public static final java.lang.String FORMAT_REMOTE_INPUT = "input";
    public static final java.lang.String FORMAT_SLICE = "slice";
    public static final java.lang.String FORMAT_TEXT = "text";
    private static final java.lang.String TAG = "SliceItem";
    private final java.lang.String mFormat;
    protected java.lang.String[] mHints;
    private final java.lang.Object mObj;
    private final java.lang.String mSubType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SliceType {
    }

    public SliceItem(java.lang.Object obj, java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list) {
        this(obj, str, str2, (java.lang.String[]) list.toArray(new java.lang.String[list.size()]));
    }

    public SliceItem(java.lang.Object obj, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        this.mHints = strArr;
        this.mFormat = str;
        this.mSubType = str2;
        this.mObj = obj;
    }

    public SliceItem(android.app.PendingIntent pendingIntent, android.app.slice.Slice slice, java.lang.String str, java.lang.String str2, java.lang.String[] strArr) {
        this(new android.util.Pair(pendingIntent, slice), str, str2, strArr);
    }

    public java.util.List<java.lang.String> getHints() {
        return java.util.Arrays.asList(this.mHints);
    }

    public java.lang.String getFormat() {
        return this.mFormat;
    }

    public java.lang.String getSubType() {
        return this.mSubType;
    }

    public java.lang.CharSequence getText() {
        return (java.lang.CharSequence) this.mObj;
    }

    public android.os.Bundle getBundle() {
        return (android.os.Bundle) this.mObj;
    }

    public android.graphics.drawable.Icon getIcon() {
        return (android.graphics.drawable.Icon) this.mObj;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public android.app.PendingIntent getAction() {
        return (android.app.PendingIntent) ((android.util.Pair) this.mObj).first;
    }

    public android.widget.RemoteViews getRemoteView() {
        return (android.widget.RemoteViews) this.mObj;
    }

    public android.app.RemoteInput getRemoteInput() {
        return (android.app.RemoteInput) this.mObj;
    }

    public int getInt() {
        return ((java.lang.Integer) this.mObj).intValue();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public android.app.slice.Slice getSlice() {
        if ("action".equals(getFormat())) {
            return (android.app.slice.Slice) ((android.util.Pair) this.mObj).second;
        }
        return (android.app.slice.Slice) this.mObj;
    }

    public long getLong() {
        return ((java.lang.Long) this.mObj).longValue();
    }

    public boolean hasHint(java.lang.String str) {
        return com.android.internal.util.ArrayUtils.contains(this.mHints, str);
    }

    public SliceItem(android.os.Parcel parcel) {
        this.mHints = parcel.readStringArray();
        this.mFormat = parcel.readString();
        this.mSubType = parcel.readString();
        this.mObj = readObj(this.mFormat, parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringArray(this.mHints);
        parcel.writeString(this.mFormat);
        parcel.writeString(this.mSubType);
        writeObj(parcel, i, this.mObj, this.mFormat);
    }

    public boolean hasHints(java.lang.String[] strArr) {
        if (strArr == null) {
            return true;
        }
        for (java.lang.String str : strArr) {
            if (!android.text.TextUtils.isEmpty(str) && !com.android.internal.util.ArrayUtils.contains(this.mHints, str)) {
                return false;
            }
        }
        return true;
    }

    public boolean hasAnyHints(java.lang.String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (java.lang.String str : strArr) {
            if (com.android.internal.util.ArrayUtils.contains(this.mHints, str)) {
                return true;
            }
        }
        return false;
    }

    private static java.lang.String getBaseType(java.lang.String str) {
        int indexOf = str.indexOf(47);
        if (indexOf >= 0) {
            return str.substring(0, indexOf);
        }
        return str;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    private static void writeObj(android.os.Parcel parcel, int i, java.lang.Object obj, java.lang.String str) {
        char c;
        java.lang.String baseType = getBaseType(str);
        switch (baseType.hashCode()) {
            case -1422950858:
                if (baseType.equals("action")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1377881982:
                if (baseType.equals(FORMAT_BUNDLE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 104431:
                if (baseType.equals(FORMAT_INT)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 3327612:
                if (baseType.equals(FORMAT_LONG)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 3556653:
                if (baseType.equals("text")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 100313435:
                if (baseType.equals(FORMAT_IMAGE)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 100358090:
                if (baseType.equals("input")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 109526418:
                if (baseType.equals("slice")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
                ((android.os.Parcelable) obj).writeToParcel(parcel, i);
                break;
            case 4:
                android.util.Pair pair = (android.util.Pair) obj;
                ((android.app.PendingIntent) pair.first).writeToParcel(parcel, i);
                ((android.app.slice.Slice) pair.second).writeToParcel(parcel, i);
                break;
            case 5:
                android.text.TextUtils.writeToParcel((java.lang.CharSequence) obj, parcel, i);
                break;
            case 6:
                parcel.writeInt(((java.lang.Integer) obj).intValue());
                break;
            case 7:
                parcel.writeLong(((java.lang.Long) obj).longValue());
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static java.lang.Object readObj(java.lang.String str, android.os.Parcel parcel) {
        char c;
        java.lang.String baseType = getBaseType(str);
        switch (baseType.hashCode()) {
            case -1422950858:
                if (baseType.equals("action")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1377881982:
                if (baseType.equals(FORMAT_BUNDLE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 104431:
                if (baseType.equals(FORMAT_INT)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 3327612:
                if (baseType.equals(FORMAT_LONG)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 3556653:
                if (baseType.equals("text")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 100313435:
                if (baseType.equals(FORMAT_IMAGE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 100358090:
                if (baseType.equals("input")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 109526418:
                if (baseType.equals("slice")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return android.app.slice.Slice.CREATOR.createFromParcel(parcel);
            case 1:
                return android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            case 2:
                return android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
            case 3:
                return new android.util.Pair(android.app.PendingIntent.CREATOR.createFromParcel(parcel), android.app.slice.Slice.CREATOR.createFromParcel(parcel));
            case 4:
                return java.lang.Integer.valueOf(parcel.readInt());
            case 5:
                return java.lang.Long.valueOf(parcel.readLong());
            case 6:
                return android.app.RemoteInput.CREATOR.createFromParcel(parcel);
            case 7:
                return android.os.Bundle.CREATOR.createFromParcel(parcel);
            default:
                throw new java.lang.RuntimeException("Unsupported type " + str);
        }
    }
}
