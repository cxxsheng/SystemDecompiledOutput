package android.content;

/* loaded from: classes.dex */
public final class UriRelativeFilterGroup {
    public static final int ACTION_ALLOW = 0;
    public static final int ACTION_BLOCK = 1;
    private static final java.lang.String ALLOW_STR = "allow";
    private static final java.lang.String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    private final int mAction;
    private final android.util.ArraySet<android.content.UriRelativeFilter> mUriRelativeFilters = new android.util.ArraySet<>();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Action {
    }

    public static boolean matchGroupsToUri(java.util.List<android.content.UriRelativeFilterGroup> list, android.net.Uri uri) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).matchData(uri)) {
                return list.get(i).getAction() == 0;
            }
        }
        return false;
    }

    public static java.util.List<android.content.UriRelativeFilterGroup> parcelsToGroups(java.util.List<android.content.UriRelativeFilterGroupParcel> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(new android.content.UriRelativeFilterGroup(list.get(i)));
            }
        }
        return arrayList;
    }

    public static java.util.List<android.content.UriRelativeFilterGroupParcel> groupsToParcels(java.util.List<android.content.UriRelativeFilterGroup> list) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(list.get(i).toParcel());
            }
        }
        return arrayList;
    }

    public UriRelativeFilterGroup(int i) {
        this.mAction = i;
    }

    public UriRelativeFilterGroup(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        this.mAction = java.lang.Integer.parseInt(xmlPullParser.getAttributeValue(null, ALLOW_STR));
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next != 1) {
                if (next != 3 || xmlPullParser.getDepth() > depth) {
                    if (next != 3 && next != 4) {
                        java.lang.String name = xmlPullParser.getName();
                        if (name.equals("uriRelativeFilter")) {
                            addUriRelativeFilter(new android.content.UriRelativeFilter(xmlPullParser));
                        } else {
                            android.util.Log.w("IntentFilter", "Unknown tag parsing IntentFilter: " + name);
                        }
                        com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public int getAction() {
        return this.mAction;
    }

    public void addUriRelativeFilter(android.content.UriRelativeFilter uriRelativeFilter) {
        java.util.Objects.requireNonNull(uriRelativeFilter);
        if (!com.android.internal.util.CollectionUtils.contains(this.mUriRelativeFilters, uriRelativeFilter)) {
            this.mUriRelativeFilters.add(uriRelativeFilter);
        }
    }

    public java.util.Collection<android.content.UriRelativeFilter> getUriRelativeFilters() {
        return java.util.Collections.unmodifiableCollection(this.mUriRelativeFilters);
    }

    public boolean matchData(android.net.Uri uri) {
        if (this.mUriRelativeFilters.size() == 0) {
            return false;
        }
        java.util.Iterator<android.content.UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            if (!it.next().matchData(uri)) {
                return false;
            }
        }
        return true;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mAction);
        java.util.Iterator<android.content.UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start);
    }

    public void writeToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        xmlSerializer.startTag(null, URI_RELATIVE_FILTER_GROUP_STR);
        xmlSerializer.attribute(null, ALLOW_STR, java.lang.Integer.toString(this.mAction));
        java.util.Iterator<android.content.UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            it.next().writeToXml(xmlSerializer);
        }
        xmlSerializer.endTag(null, URI_RELATIVE_FILTER_GROUP_STR);
    }

    public java.lang.String toString() {
        return "UriRelativeFilterGroup { allow = " + this.mAction + ", uri_filters = " + this.mUriRelativeFilters + ",  }";
    }

    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        int size = this.mUriRelativeFilters.size();
        if (size > 0) {
            parcel.writeInt(size);
            java.util.Iterator<android.content.UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.UriRelativeFilterGroup uriRelativeFilterGroup = (android.content.UriRelativeFilterGroup) obj;
        if (this.mAction != uriRelativeFilterGroup.mAction) {
            return false;
        }
        return this.mUriRelativeFilters.equals(uriRelativeFilterGroup.mUriRelativeFilters);
    }

    public int hashCode() {
        return ((0 + this.mAction) * 31) + java.util.Objects.hashCode(this.mUriRelativeFilters);
    }

    public android.content.UriRelativeFilterGroupParcel toParcel() {
        android.content.UriRelativeFilterGroupParcel uriRelativeFilterGroupParcel = new android.content.UriRelativeFilterGroupParcel();
        uriRelativeFilterGroupParcel.action = this.mAction;
        uriRelativeFilterGroupParcel.filters = new java.util.ArrayList();
        java.util.Iterator<android.content.UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            uriRelativeFilterGroupParcel.filters.add(it.next().toParcel());
        }
        return uriRelativeFilterGroupParcel;
    }

    UriRelativeFilterGroup(android.os.Parcel parcel) {
        this.mAction = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mUriRelativeFilters.add(new android.content.UriRelativeFilter(parcel));
        }
    }

    public UriRelativeFilterGroup(android.content.UriRelativeFilterGroupParcel uriRelativeFilterGroupParcel) {
        this.mAction = uriRelativeFilterGroupParcel.action;
        for (int i = 0; i < uriRelativeFilterGroupParcel.filters.size(); i++) {
            this.mUriRelativeFilters.add(new android.content.UriRelativeFilter(uriRelativeFilterGroupParcel.filters.get(i)));
        }
    }
}
