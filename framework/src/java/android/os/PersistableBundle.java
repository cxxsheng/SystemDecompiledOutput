package android.os;

/* loaded from: classes3.dex */
public final class PersistableBundle extends android.os.BaseBundle implements java.lang.Cloneable, android.os.Parcelable, com.android.internal.util.XmlUtils.WriteMapCallback {
    public static final android.os.Parcelable.Creator<android.os.PersistableBundle> CREATOR;
    public static final android.os.PersistableBundle EMPTY = new android.os.PersistableBundle();
    private static final java.lang.String TAG = "PersistableBundle";
    private static final java.lang.String TAG_PERSISTABLEMAP = "pbundle_as_map";

    static {
        EMPTY.mMap = android.util.ArrayMap.EMPTY;
        CREATOR = new android.os.Parcelable.Creator<android.os.PersistableBundle>() { // from class: android.os.PersistableBundle.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.PersistableBundle createFromParcel(android.os.Parcel parcel) {
                return parcel.readPersistableBundle();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.os.PersistableBundle[] newArray(int i) {
                return new android.os.PersistableBundle[i];
            }
        };
    }

    public static boolean isValidType(java.lang.Object obj) {
        return (obj instanceof java.lang.Integer) || (obj instanceof java.lang.Long) || (obj instanceof java.lang.Double) || (obj instanceof java.lang.String) || (obj instanceof int[]) || (obj instanceof long[]) || (obj instanceof double[]) || (obj instanceof java.lang.String[]) || (obj instanceof android.os.PersistableBundle) || obj == null || (obj instanceof java.lang.Boolean) || (obj instanceof boolean[]);
    }

    public PersistableBundle() {
        this.mFlags = 1;
    }

    public PersistableBundle(int i) {
        super(i);
        this.mFlags = 1;
    }

    public PersistableBundle(android.os.PersistableBundle persistableBundle) {
        super(persistableBundle);
        this.mFlags = persistableBundle.mFlags;
    }

    public PersistableBundle(android.os.Bundle bundle) {
        this(bundle, true);
    }

    private PersistableBundle(android.os.Bundle bundle, boolean z) {
        this(bundle.getItemwiseMap(), z);
    }

    private PersistableBundle(android.util.ArrayMap<java.lang.String, java.lang.Object> arrayMap, boolean z) {
        this.mFlags = 1;
        putAll(arrayMap);
        for (int size = this.mMap.size() - 1; size >= 0; size--) {
            java.lang.Object valueAt = this.mMap.valueAt(size);
            if (valueAt instanceof android.util.ArrayMap) {
                this.mMap.setValueAt(size, new android.os.PersistableBundle((android.util.ArrayMap<java.lang.String, java.lang.Object>) valueAt, z));
            } else if (valueAt instanceof android.os.Bundle) {
                this.mMap.setValueAt(size, new android.os.PersistableBundle((android.os.Bundle) valueAt, z));
            } else if (isValidType(valueAt)) {
                continue;
            } else {
                java.lang.String str = "Bad value in PersistableBundle key=" + this.mMap.keyAt(size) + " value=" + valueAt;
                if (z) {
                    throw new java.lang.IllegalArgumentException(str);
                }
                android.util.Slog.wtfStack(TAG, str);
                this.mMap.removeAt(size);
            }
        }
    }

    PersistableBundle(android.os.Parcel parcel, int i) {
        super(parcel, i);
        this.mFlags = 1;
    }

    PersistableBundle(android.os.PersistableBundle persistableBundle, boolean z) {
        super(persistableBundle, z);
    }

    public static android.os.PersistableBundle forPair(java.lang.String str, java.lang.String str2) {
        android.os.PersistableBundle persistableBundle = new android.os.PersistableBundle(1);
        persistableBundle.putString(str, str2);
        return persistableBundle;
    }

    public java.lang.Object clone() {
        return new android.os.PersistableBundle(this);
    }

    public android.os.PersistableBundle deepCopy() {
        return new android.os.PersistableBundle(this, true);
    }

    public void putPersistableBundle(java.lang.String str, android.os.PersistableBundle persistableBundle) {
        unparcel();
        this.mMap.put(str, persistableBundle);
    }

    public android.os.PersistableBundle getPersistableBundle(java.lang.String str) {
        unparcel();
        java.lang.Object obj = this.mMap.get(str);
        if (obj == null) {
            return null;
        }
        try {
            return (android.os.PersistableBundle) obj;
        } catch (java.lang.ClassCastException e) {
            typeWarning(str, obj, "Bundle", e);
            return null;
        }
    }

    @Override // com.android.internal.util.XmlUtils.WriteMapCallback
    public void writeUnknownObject(java.lang.Object obj, java.lang.String str, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        if (obj instanceof android.os.PersistableBundle) {
            typedXmlSerializer.startTag(null, TAG_PERSISTABLEMAP);
            typedXmlSerializer.attribute(null, "name", str);
            ((android.os.PersistableBundle) obj).saveToXml(typedXmlSerializer);
            typedXmlSerializer.endTag(null, TAG_PERSISTABLEMAP);
            return;
        }
        throw new org.xmlpull.v1.XmlPullParserException("Unknown Object o=" + obj);
    }

    public void saveToXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        saveToXml(com.android.internal.util.XmlUtils.makeTyped(xmlSerializer));
    }

    public void saveToXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        unparcel();
        for (int size = this.mMap.size() - 1; size >= 0; size--) {
            java.lang.Object valueAt = this.mMap.valueAt(size);
            if (!isValidType(valueAt)) {
                android.util.Slog.e(TAG, "Dropping bad data before persisting: " + this.mMap.keyAt(size) + "=" + valueAt);
                this.mMap.removeAt(size);
            }
        }
        com.android.internal.util.XmlUtils.writeMapXml(this.mMap, typedXmlSerializer, this);
    }

    public boolean isBundleContentsWithinLengthLimit(int i) {
        unparcel();
        if (this.mMap == null) {
            return true;
        }
        for (int i2 = 0; i2 < this.mMap.size(); i2++) {
            if (this.mMap.keyAt(i2) != null && this.mMap.keyAt(i2).length() > i) {
                return false;
            }
            java.lang.Object valueAt = this.mMap.valueAt(i2);
            if ((valueAt instanceof java.lang.String) && ((java.lang.String) valueAt).length() > i) {
                return false;
            }
            if (valueAt instanceof java.lang.String[]) {
                java.lang.String[] strArr = (java.lang.String[]) valueAt;
                for (int i3 = 0; i3 < strArr.length; i3++) {
                    if (strArr[i3] != null && strArr[i3].length() > i) {
                        return false;
                    }
                }
            } else if ((valueAt instanceof android.os.PersistableBundle) && !((android.os.PersistableBundle) valueAt).isBundleContentsWithinLengthLimit(i)) {
                return false;
            }
        }
        return true;
    }

    static class MyReadMapCallback implements com.android.internal.util.XmlUtils.ReadMapCallback {
        MyReadMapCallback() {
        }

        @Override // com.android.internal.util.XmlUtils.ReadMapCallback
        public java.lang.Object readThisUnknownObjectXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            if (android.os.PersistableBundle.TAG_PERSISTABLEMAP.equals(str)) {
                return android.os.PersistableBundle.restoreFromXml(typedXmlPullParser);
            }
            throw new org.xmlpull.v1.XmlPullParserException("Unknown tag=" + str);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean pushAllowFds = parcel.pushAllowFds(false);
        try {
            writeToParcelInner(parcel, i);
        } finally {
            parcel.restoreAllowFds(pushAllowFds);
        }
    }

    public static android.os.PersistableBundle restoreFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        return restoreFromXml(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser));
    }

    public static android.os.PersistableBundle restoreFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        int next;
        int depth = typedXmlPullParser.getDepth();
        java.lang.String name = typedXmlPullParser.getName();
        java.lang.String[] strArr = new java.lang.String[1];
        do {
            next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() >= depth)) {
                return new android.os.PersistableBundle();
            }
        } while (next != 2);
        return new android.os.PersistableBundle((android.util.ArrayMap<java.lang.String, java.lang.Object>) com.android.internal.util.XmlUtils.readThisArrayMapXml(typedXmlPullParser, name, strArr, new android.os.PersistableBundle.MyReadMapCallback()), false);
    }

    public synchronized java.lang.String toString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "PersistableBundle[EMPTY_PARCEL]";
            }
            return "PersistableBundle[mParcelledData.dataSize=" + this.mParcelledData.dataSize() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
        }
        return "PersistableBundle[" + this.mMap.toString() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }

    public synchronized java.lang.String toShortString() {
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                return "EMPTY_PARCEL";
            }
            return "mParcelledData.dataSize=" + this.mParcelledData.dataSize();
        }
        return this.mMap.toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (this.mParcelledData != null) {
            if (isEmptyParcel()) {
                protoOutputStream.write(1120986464257L, 0);
            } else {
                protoOutputStream.write(1120986464257L, this.mParcelledData.dataSize());
            }
        } else {
            protoOutputStream.write(1138166333442L, this.mMap.toString());
        }
        protoOutputStream.end(start);
    }

    public void writeToStream(java.io.OutputStream outputStream) throws java.io.IOException {
        com.android.modules.utils.TypedXmlSerializer newFastSerializer = android.util.Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, java.nio.charset.StandardCharsets.UTF_8.name());
        newFastSerializer.startTag(null, android.app.slice.SliceItem.FORMAT_BUNDLE);
        try {
            saveToXml(newFastSerializer);
            newFastSerializer.endTag(null, android.app.slice.SliceItem.FORMAT_BUNDLE);
            newFastSerializer.flush();
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.io.IOException(e);
        }
    }

    public static android.os.PersistableBundle readFromStream(java.io.InputStream inputStream) throws java.io.IOException {
        try {
            com.android.modules.utils.TypedXmlPullParser newFastPullParser = android.util.Xml.newFastPullParser();
            newFastPullParser.setInput(inputStream, java.nio.charset.StandardCharsets.UTF_8.name());
            newFastPullParser.next();
            return restoreFromXml(newFastPullParser);
        } catch (org.xmlpull.v1.XmlPullParserException e) {
            throw new java.io.IOException(e);
        }
    }
}
