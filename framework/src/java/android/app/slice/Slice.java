package android.app.slice;

/* loaded from: classes.dex */
public final class Slice implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.slice.Slice> CREATOR = new android.os.Parcelable.Creator<android.app.slice.Slice>() { // from class: android.app.slice.Slice.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.Slice createFromParcel(android.os.Parcel parcel) {
            return new android.app.slice.Slice(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.slice.Slice[] newArray(int i) {
            return new android.app.slice.Slice[i];
        }
    };
    public static final java.lang.String EXTRA_RANGE_VALUE = "android.app.slice.extra.RANGE_VALUE";
    public static final java.lang.String EXTRA_TOGGLE_STATE = "android.app.slice.extra.TOGGLE_STATE";
    public static final java.lang.String HINT_ACTIONS = "actions";
    public static final java.lang.String HINT_CALLER_NEEDED = "caller_needed";
    public static final java.lang.String HINT_ERROR = "error";
    public static final java.lang.String HINT_HORIZONTAL = "horizontal";
    public static final java.lang.String HINT_KEYWORDS = "keywords";
    public static final java.lang.String HINT_LARGE = "large";
    public static final java.lang.String HINT_LAST_UPDATED = "last_updated";
    public static final java.lang.String HINT_LIST = "list";
    public static final java.lang.String HINT_LIST_ITEM = "list_item";
    public static final java.lang.String HINT_NO_TINT = "no_tint";
    public static final java.lang.String HINT_PARTIAL = "partial";
    public static final java.lang.String HINT_PERMISSION_REQUEST = "permission_request";
    public static final java.lang.String HINT_SEE_MORE = "see_more";
    public static final java.lang.String HINT_SELECTED = "selected";
    public static final java.lang.String HINT_SHORTCUT = "shortcut";
    public static final java.lang.String HINT_SUMMARY = "summary";
    public static final java.lang.String HINT_TITLE = "title";
    public static final java.lang.String HINT_TOGGLE = "toggle";
    public static final java.lang.String HINT_TTL = "ttl";
    public static final java.lang.String SUBTYPE_COLOR = "color";
    public static final java.lang.String SUBTYPE_CONTENT_DESCRIPTION = "content_description";
    public static final java.lang.String SUBTYPE_LAYOUT_DIRECTION = "layout_direction";
    public static final java.lang.String SUBTYPE_MAX = "max";
    public static final java.lang.String SUBTYPE_MESSAGE = "message";
    public static final java.lang.String SUBTYPE_MILLIS = "millis";
    public static final java.lang.String SUBTYPE_PRIORITY = "priority";
    public static final java.lang.String SUBTYPE_RANGE = "range";
    public static final java.lang.String SUBTYPE_SOURCE = "source";
    public static final java.lang.String SUBTYPE_TOGGLE = "toggle";
    public static final java.lang.String SUBTYPE_VALUE = "value";
    private final java.lang.String[] mHints;
    private final android.app.slice.SliceItem[] mItems;
    private android.app.slice.SliceSpec mSpec;
    private android.net.Uri mUri;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SliceHint {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SliceSubtype {
    }

    Slice(java.util.ArrayList<android.app.slice.SliceItem> arrayList, java.lang.String[] strArr, android.net.Uri uri, android.app.slice.SliceSpec sliceSpec) {
        this.mHints = strArr;
        this.mItems = (android.app.slice.SliceItem[]) arrayList.toArray(new android.app.slice.SliceItem[arrayList.size()]);
        this.mUri = uri;
        this.mSpec = sliceSpec;
    }

    protected Slice(android.os.Parcel parcel) {
        this.mHints = parcel.readStringArray();
        int readInt = parcel.readInt();
        this.mItems = new android.app.slice.SliceItem[readInt];
        for (int i = 0; i < readInt; i++) {
            this.mItems[i] = android.app.slice.SliceItem.CREATOR.createFromParcel(parcel);
        }
        this.mUri = android.net.Uri.CREATOR.createFromParcel(parcel);
        this.mSpec = (android.app.slice.SliceSpec) parcel.readTypedObject(android.app.slice.SliceSpec.CREATOR);
    }

    public android.app.slice.SliceSpec getSpec() {
        return this.mSpec;
    }

    public android.net.Uri getUri() {
        return this.mUri;
    }

    public java.util.List<android.app.slice.SliceItem> getItems() {
        return java.util.Arrays.asList(this.mItems);
    }

    public java.util.List<java.lang.String> getHints() {
        return java.util.Arrays.asList(this.mHints);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStringArray(this.mHints);
        parcel.writeInt(this.mItems.length);
        for (int i2 = 0; i2 < this.mItems.length; i2++) {
            this.mItems[i2].writeToParcel(parcel, i);
        }
        this.mUri.writeToParcel(parcel, 0);
        parcel.writeTypedObject(this.mSpec, i);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean hasHint(java.lang.String str) {
        return com.android.internal.util.ArrayUtils.contains(this.mHints, str);
    }

    public boolean isCallerNeeded() {
        return hasHint(HINT_CALLER_NEEDED);
    }

    public static class Builder {
        private android.app.slice.SliceSpec mSpec;
        private final android.net.Uri mUri;
        private java.util.ArrayList<android.app.slice.SliceItem> mItems = new java.util.ArrayList<>();
        private java.util.ArrayList<java.lang.String> mHints = new java.util.ArrayList<>();

        public Builder(android.net.Uri uri, android.app.slice.SliceSpec sliceSpec) {
            this.mUri = uri;
            this.mSpec = sliceSpec;
        }

        public Builder(android.app.slice.Slice.Builder builder) {
            this.mUri = builder.mUri.buildUpon().appendPath("_gen").appendPath(java.lang.String.valueOf(this.mItems.size())).build();
        }

        public android.app.slice.Slice.Builder setCallerNeeded(boolean z) {
            if (z) {
                this.mHints.add(android.app.slice.Slice.HINT_CALLER_NEEDED);
            } else {
                this.mHints.remove(android.app.slice.Slice.HINT_CALLER_NEEDED);
            }
            return this;
        }

        public android.app.slice.Slice.Builder addHints(java.util.List<java.lang.String> list) {
            this.mHints.addAll(list);
            return this;
        }

        public android.app.slice.Slice.Builder addSubSlice(android.app.slice.Slice slice, java.lang.String str) {
            java.util.Objects.requireNonNull(slice);
            this.mItems.add(new android.app.slice.SliceItem(slice, "slice", str, (java.lang.String[]) slice.getHints().toArray(new java.lang.String[slice.getHints().size()])));
            return this;
        }

        public android.app.slice.Slice.Builder addAction(android.app.PendingIntent pendingIntent, android.app.slice.Slice slice, java.lang.String str) {
            java.util.Objects.requireNonNull(pendingIntent);
            java.util.Objects.requireNonNull(slice);
            java.util.List<java.lang.String> hints = slice.getHints();
            slice.mSpec = null;
            this.mItems.add(new android.app.slice.SliceItem(pendingIntent, slice, "action", str, (java.lang.String[]) hints.toArray(new java.lang.String[hints.size()])));
            return this;
        }

        public android.app.slice.Slice.Builder addText(java.lang.CharSequence charSequence, java.lang.String str, java.util.List<java.lang.String> list) {
            this.mItems.add(new android.app.slice.SliceItem(charSequence, "text", str, list));
            return this;
        }

        public android.app.slice.Slice.Builder addIcon(android.graphics.drawable.Icon icon, java.lang.String str, java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(icon);
            this.mItems.add(new android.app.slice.SliceItem(icon, android.app.slice.SliceItem.FORMAT_IMAGE, str, list));
            return this;
        }

        public android.app.slice.Slice.Builder addRemoteInput(android.app.RemoteInput remoteInput, java.lang.String str, java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(remoteInput);
            this.mItems.add(new android.app.slice.SliceItem(remoteInput, "input", str, list));
            return this;
        }

        public android.app.slice.Slice.Builder addInt(int i, java.lang.String str, java.util.List<java.lang.String> list) {
            this.mItems.add(new android.app.slice.SliceItem(java.lang.Integer.valueOf(i), android.app.slice.SliceItem.FORMAT_INT, str, list));
            return this;
        }

        public android.app.slice.Slice.Builder addLong(long j, java.lang.String str, java.util.List<java.lang.String> list) {
            this.mItems.add(new android.app.slice.SliceItem(java.lang.Long.valueOf(j), android.app.slice.SliceItem.FORMAT_LONG, str, (java.lang.String[]) list.toArray(new java.lang.String[list.size()])));
            return this;
        }

        public android.app.slice.Slice.Builder addBundle(android.os.Bundle bundle, java.lang.String str, java.util.List<java.lang.String> list) {
            java.util.Objects.requireNonNull(bundle);
            this.mItems.add(new android.app.slice.SliceItem(bundle, android.app.slice.SliceItem.FORMAT_BUNDLE, str, list));
            return this;
        }

        public android.app.slice.Slice build() {
            return new android.app.slice.Slice(this.mItems, (java.lang.String[]) this.mHints.toArray(new java.lang.String[this.mHints.size()]), this.mUri, this.mSpec);
        }
    }

    public java.lang.String toString() {
        return toString("");
    }

    private java.lang.String toString(java.lang.String str) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < this.mItems.length; i++) {
            sb.append(str);
            if (java.util.Objects.equals(this.mItems[i].getFormat(), "slice")) {
                sb.append("slice:\n");
                sb.append(this.mItems[i].getSlice().toString(str + "   "));
            } else if (java.util.Objects.equals(this.mItems[i].getFormat(), "text")) {
                sb.append("text: ");
                sb.append(this.mItems[i].getText());
                sb.append("\n");
            } else {
                sb.append(this.mItems[i].getFormat());
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
