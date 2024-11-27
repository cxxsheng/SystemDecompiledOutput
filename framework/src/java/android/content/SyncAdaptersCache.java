package android.content;

/* loaded from: classes.dex */
public class SyncAdaptersCache extends android.content.pm.RegisteredServicesCache<android.content.SyncAdapterType> {
    private static final java.lang.String ATTRIBUTES_NAME = "sync-adapter";
    private static final java.lang.String SERVICE_INTERFACE = "android.content.SyncAdapter";
    private static final java.lang.String SERVICE_META_DATA = "android.content.SyncAdapter";
    private static final java.lang.String TAG = "Account";
    private static final android.content.SyncAdaptersCache.MySerializer sSerializer = new android.content.SyncAdaptersCache.MySerializer();
    private android.util.SparseArray<android.util.ArrayMap<java.lang.String, java.lang.String[]>> mAuthorityToSyncAdapters;

    public SyncAdaptersCache(android.content.Context context) {
        super(context, "android.content.SyncAdapter", "android.content.SyncAdapter", ATTRIBUTES_NAME, sSerializer);
        this.mAuthorityToSyncAdapters = new android.util.SparseArray<>();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.content.pm.RegisteredServicesCache
    public android.content.SyncAdapterType parseServiceAttributes(android.content.res.Resources resources, java.lang.String str, android.util.AttributeSet attributeSet) {
        android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(attributeSet, com.android.internal.R.styleable.SyncAdapter);
        try {
            java.lang.String string = obtainAttributes.getString(2);
            java.lang.String string2 = obtainAttributes.getString(1);
            if (!android.text.TextUtils.isEmpty(string) && !android.text.TextUtils.isEmpty(string2)) {
                return new android.content.SyncAdapterType(string, string2, obtainAttributes.getBoolean(3, true), obtainAttributes.getBoolean(4, true), obtainAttributes.getBoolean(6, false), obtainAttributes.getBoolean(5, false), obtainAttributes.getString(0), str);
            }
            obtainAttributes.recycle();
            return null;
        } finally {
            obtainAttributes.recycle();
        }
    }

    @Override // android.content.pm.RegisteredServicesCache
    protected void onServicesChangedLocked(int i) {
        synchronized (this.mServicesLock) {
            android.util.ArrayMap<java.lang.String, java.lang.String[]> arrayMap = this.mAuthorityToSyncAdapters.get(i);
            if (arrayMap != null) {
                arrayMap.clear();
            }
        }
        super.onServicesChangedLocked(i);
    }

    public java.lang.String[] getSyncAdapterPackagesForAuthority(java.lang.String str, int i) {
        synchronized (this.mServicesLock) {
            android.util.ArrayMap<java.lang.String, java.lang.String[]> arrayMap = this.mAuthorityToSyncAdapters.get(i);
            if (arrayMap == null) {
                arrayMap = new android.util.ArrayMap<>();
                this.mAuthorityToSyncAdapters.put(i, arrayMap);
            }
            if (arrayMap.containsKey(str)) {
                return arrayMap.get(str);
            }
            java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType>> allServices = getAllServices(i);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.content.pm.RegisteredServicesCache.ServiceInfo<android.content.SyncAdapterType> serviceInfo : allServices) {
                if (str.equals(serviceInfo.type.authority) && serviceInfo.componentName != null) {
                    arrayList.add(serviceInfo.componentName.getPackageName());
                }
            }
            java.lang.String[] strArr = new java.lang.String[arrayList.size()];
            arrayList.toArray(strArr);
            arrayMap.put(str, strArr);
            return strArr;
        }
    }

    @Override // android.content.pm.RegisteredServicesCache
    protected void onUserRemoved(int i) {
        synchronized (this.mServicesLock) {
            this.mAuthorityToSyncAdapters.remove(i);
        }
        super.onUserRemoved(i);
    }

    static class MySerializer implements android.content.pm.XmlSerializerAndParser<android.content.SyncAdapterType> {
        MySerializer() {
        }

        @Override // android.content.pm.XmlSerializerAndParser
        public void writeAsXml(android.content.SyncAdapterType syncAdapterType, com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            typedXmlSerializer.attribute(null, android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY, syncAdapterType.authority);
            typedXmlSerializer.attribute(null, "accountType", syncAdapterType.accountType);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.content.pm.XmlSerializerAndParser
        public android.content.SyncAdapterType createFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return android.content.SyncAdapterType.newKey(typedXmlPullParser.getAttributeValue(null, android.provider.ContactsContract.Directory.DIRECTORY_AUTHORITY), typedXmlPullParser.getAttributeValue(null, "accountType"));
        }
    }
}
