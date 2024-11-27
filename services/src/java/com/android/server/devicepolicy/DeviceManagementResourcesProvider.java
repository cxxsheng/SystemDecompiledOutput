package com.android.server.devicepolicy;

/* loaded from: classes.dex */
class DeviceManagementResourcesProvider {
    private static final java.lang.String ATTR_DRAWABLE_ID = "drawable-id";
    private static final java.lang.String ATTR_DRAWABLE_SOURCE = "drawable-source";
    private static final java.lang.String ATTR_DRAWABLE_STYLE = "drawable-style";
    private static final java.lang.String ATTR_SOURCE_ID = "source-id";
    private static final java.lang.String TAG = "DevicePolicyManagerService";
    private static final java.lang.String TAG_DRAWABLE_SOURCE_ENTRY = "drawable-source-entry";
    private static final java.lang.String TAG_DRAWABLE_STYLE_ENTRY = "drawable-style-entry";
    private static final java.lang.String TAG_ROOT = "root";
    private static final java.lang.String TAG_STRING_ENTRY = "string-entry";
    private static final java.lang.String UPDATED_RESOURCES_XML = "updated_resources.xml";
    private final com.android.server.devicepolicy.DeviceManagementResourcesProvider.Injector mInjector;
    private final java.lang.Object mLock;
    private final java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.Map<java.lang.String, android.app.admin.ParcelableResource>>> mUpdatedDrawablesForSource;
    private final java.util.Map<java.lang.String, java.util.Map<java.lang.String, android.app.admin.ParcelableResource>> mUpdatedDrawablesForStyle;
    private final java.util.Map<java.lang.String, android.app.admin.ParcelableResource> mUpdatedStrings;

    DeviceManagementResourcesProvider() {
        this(new com.android.server.devicepolicy.DeviceManagementResourcesProvider.Injector());
    }

    DeviceManagementResourcesProvider(com.android.server.devicepolicy.DeviceManagementResourcesProvider.Injector injector) {
        this.mUpdatedDrawablesForStyle = new java.util.HashMap();
        this.mUpdatedDrawablesForSource = new java.util.HashMap();
        this.mUpdatedStrings = new java.util.HashMap();
        this.mLock = new java.lang.Object();
        java.util.Objects.requireNonNull(injector);
        this.mInjector = injector;
    }

    boolean updateDrawables(@android.annotation.NonNull java.util.List<android.app.admin.DevicePolicyDrawableResource> list) {
        boolean updateDrawableForSource;
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            java.lang.String drawableId = list.get(i).getDrawableId();
            java.lang.String drawableStyle = list.get(i).getDrawableStyle();
            java.lang.String drawableSource = list.get(i).getDrawableSource();
            android.app.admin.ParcelableResource resource = list.get(i).getResource();
            java.util.Objects.requireNonNull(drawableId, "drawableId must be provided.");
            java.util.Objects.requireNonNull(drawableStyle, "drawableStyle must be provided.");
            java.util.Objects.requireNonNull(drawableSource, "drawableSource must be provided.");
            java.util.Objects.requireNonNull(resource, "ParcelableResource must be provided.");
            if ("UNDEFINED".equals(drawableSource)) {
                updateDrawableForSource = updateDrawable(drawableId, drawableStyle, resource);
            } else {
                updateDrawableForSource = updateDrawableForSource(drawableId, drawableSource, drawableStyle, resource);
            }
            z |= updateDrawableForSource;
        }
        if (!z) {
            return false;
        }
        synchronized (this.mLock) {
            write();
        }
        return true;
    }

    private boolean updateDrawable(java.lang.String str, java.lang.String str2, android.app.admin.ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            try {
                if (!this.mUpdatedDrawablesForStyle.containsKey(str)) {
                    this.mUpdatedDrawablesForStyle.put(str, new java.util.HashMap());
                }
                if (parcelableResource.equals(this.mUpdatedDrawablesForStyle.get(str).get(str2))) {
                    return false;
                }
                this.mUpdatedDrawablesForStyle.get(str).put(str2, parcelableResource);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private boolean updateDrawableForSource(java.lang.String str, java.lang.String str2, java.lang.String str3, android.app.admin.ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            try {
                if (!this.mUpdatedDrawablesForSource.containsKey(str)) {
                    this.mUpdatedDrawablesForSource.put(str, new java.util.HashMap());
                }
                java.util.Map<java.lang.String, java.util.Map<java.lang.String, android.app.admin.ParcelableResource>> map = this.mUpdatedDrawablesForSource.get(str);
                if (!map.containsKey(str2)) {
                    this.mUpdatedDrawablesForSource.get(str).put(str2, new java.util.HashMap());
                }
                if (parcelableResource.equals(map.get(str2).get(str3))) {
                    return false;
                }
                map.get(str2).put(str3, parcelableResource);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removeDrawables(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        synchronized (this.mLock) {
            int i = 0;
            boolean z = false;
            while (true) {
                try {
                    boolean z2 = true;
                    if (i >= list.size()) {
                        break;
                    }
                    java.lang.String str = list.get(i);
                    if (this.mUpdatedDrawablesForStyle.remove(str) == null && this.mUpdatedDrawablesForSource.remove(str) == null) {
                        z2 = false;
                    }
                    z |= z2;
                    i++;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!z) {
                return false;
            }
            write();
            return true;
        }
    }

    @android.annotation.Nullable
    android.app.admin.ParcelableResource getDrawable(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        synchronized (this.mLock) {
            try {
                android.app.admin.ParcelableResource drawableForSourceLocked = getDrawableForSourceLocked(str, str2, str3);
                if (drawableForSourceLocked != null) {
                    return drawableForSourceLocked;
                }
                if (!this.mUpdatedDrawablesForStyle.containsKey(str)) {
                    return null;
                }
                return this.mUpdatedDrawablesForStyle.get(str).get(str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    android.app.admin.ParcelableResource getDrawableForSourceLocked(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        if (this.mUpdatedDrawablesForSource.containsKey(str) && this.mUpdatedDrawablesForSource.get(str).containsKey(str3)) {
            return this.mUpdatedDrawablesForSource.get(str).get(str3).get(str2);
        }
        return null;
    }

    boolean updateStrings(@android.annotation.NonNull java.util.List<android.app.admin.DevicePolicyStringResource> list) {
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            java.lang.String stringId = list.get(i).getStringId();
            android.app.admin.ParcelableResource resource = list.get(i).getResource();
            java.util.Objects.requireNonNull(stringId, "stringId must be provided.");
            java.util.Objects.requireNonNull(resource, "ParcelableResource must be provided.");
            z |= updateString(stringId, resource);
        }
        if (!z) {
            return false;
        }
        synchronized (this.mLock) {
            write();
        }
        return true;
    }

    private boolean updateString(java.lang.String str, android.app.admin.ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            try {
                if (parcelableResource.equals(this.mUpdatedStrings.get(str))) {
                    return false;
                }
                this.mUpdatedStrings.put(str, parcelableResource);
                return true;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    boolean removeStrings(@android.annotation.NonNull java.util.List<java.lang.String> list) {
        synchronized (this.mLock) {
            int i = 0;
            boolean z = false;
            while (true) {
                try {
                    boolean z2 = true;
                    if (i >= list.size()) {
                        break;
                    }
                    if (this.mUpdatedStrings.remove(list.get(i)) == null) {
                        z2 = false;
                    }
                    z |= z2;
                    i++;
                } catch (java.lang.Throwable th) {
                    throw th;
                }
            }
            if (!z) {
                return false;
            }
            write();
            return true;
        }
    }

    @android.annotation.Nullable
    android.app.admin.ParcelableResource getString(java.lang.String str) {
        android.app.admin.ParcelableResource parcelableResource;
        synchronized (this.mLock) {
            parcelableResource = this.mUpdatedStrings.get(str);
        }
        return parcelableResource;
    }

    private void write() {
        android.util.Log.d(TAG, "Writing updated resources to file.");
        new com.android.server.devicepolicy.DeviceManagementResourcesProvider.ResourcesReaderWriter().writeToFileLocked();
    }

    void load() {
        synchronized (this.mLock) {
            new com.android.server.devicepolicy.DeviceManagementResourcesProvider.ResourcesReaderWriter().readFromFileLocked();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.io.File getResourcesFile() {
        return new java.io.File(this.mInjector.environmentGetDataSystemDirectory(), UPDATED_RESOURCES_XML);
    }

    private class ResourcesReaderWriter {
        private final java.io.File mFile;

        private ResourcesReaderWriter() {
            this.mFile = com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.getResourcesFile();
        }

        void writeToFileLocked() {
            java.io.FileOutputStream startWrite;
            android.util.Log.d(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Writing to " + this.mFile);
            android.util.AtomicFile atomicFile = new android.util.AtomicFile(this.mFile);
            java.io.FileOutputStream fileOutputStream = null;
            try {
                startWrite = atomicFile.startWrite();
            } catch (java.io.IOException e) {
                e = e;
            }
            try {
                com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((java.lang.String) null, true);
                resolveSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_ROOT);
                writeInner(resolveSerializer);
                resolveSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_ROOT);
                resolveSerializer.endDocument();
                resolveSerializer.flush();
                atomicFile.finishWrite(startWrite);
            } catch (java.io.IOException e2) {
                e = e2;
                fileOutputStream = startWrite;
                android.util.Log.e(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Exception when writing", e);
                if (fileOutputStream != null) {
                    atomicFile.failWrite(fileOutputStream);
                }
            }
        }

        void readFromFileLocked() {
            if (!this.mFile.exists()) {
                android.util.Log.d(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "" + this.mFile + " doesn't exist");
                return;
            }
            android.util.Log.d(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Reading from " + this.mFile);
            java.io.FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = new android.util.AtomicFile(this.mFile).openRead();
                    com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(fileInputStream);
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next != 1) {
                            switch (next) {
                                case 2:
                                    i++;
                                    java.lang.String name = resolvePullParser.getName();
                                    if (i == 1) {
                                        if (!com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_ROOT.equals(name)) {
                                            android.util.Log.e(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Invalid root tag: " + name);
                                            return;
                                        }
                                    } else if (!readInner(resolvePullParser, i, name)) {
                                        return;
                                    }
                                case 3:
                                    i--;
                            }
                        }
                    }
                } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    android.util.Log.e(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Error parsing resources file", e);
                }
            } finally {
                libcore.io.IoUtils.closeQuietly(fileInputStream);
            }
        }

        void writeInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            writeDrawablesForStylesInner(typedXmlSerializer);
            writeDrawablesForSourcesInner(typedXmlSerializer);
            writeStringsInner(typedXmlSerializer);
        }

        private void writeDrawablesForStylesInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle != null && !com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.isEmpty()) {
                for (java.util.Map.Entry entry : com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.entrySet()) {
                    for (java.util.Map.Entry entry2 : ((java.util.Map) entry.getValue()).entrySet()) {
                        typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_STYLE_ENTRY);
                        typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_ID, (java.lang.String) entry.getKey());
                        typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_STYLE, (java.lang.String) entry2.getKey());
                        ((android.app.admin.ParcelableResource) entry2.getValue()).writeToXmlFile(typedXmlSerializer);
                        typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_STYLE_ENTRY);
                    }
                }
            }
        }

        private void writeDrawablesForSourcesInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource != null && !com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.isEmpty()) {
                for (java.util.Map.Entry entry : com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.entrySet()) {
                    for (java.util.Map.Entry entry2 : ((java.util.Map) entry.getValue()).entrySet()) {
                        for (java.util.Map.Entry entry3 : ((java.util.Map) entry2.getValue()).entrySet()) {
                            typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_SOURCE_ENTRY);
                            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_ID, (java.lang.String) entry.getKey());
                            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_SOURCE, (java.lang.String) entry2.getKey());
                            typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_STYLE, (java.lang.String) entry3.getKey());
                            ((android.app.admin.ParcelableResource) entry3.getValue()).writeToXmlFile(typedXmlSerializer);
                            typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_SOURCE_ENTRY);
                        }
                    }
                }
            }
        }

        private void writeStringsInner(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
            if (com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedStrings != null && !com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedStrings.isEmpty()) {
                for (java.util.Map.Entry entry : com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedStrings.entrySet()) {
                    typedXmlSerializer.startTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_STRING_ENTRY);
                    typedXmlSerializer.attribute((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_SOURCE_ID, (java.lang.String) entry.getKey());
                    ((android.app.admin.ParcelableResource) entry.getValue()).writeToXmlFile(typedXmlSerializer);
                    typedXmlSerializer.endTag((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_STRING_ENTRY);
                }
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:31:0x0029, code lost:
        
            if (r8.equals(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_STRING_ENTRY) != false) goto L18;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private boolean readInner(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i, java.lang.String str) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
            char c = 2;
            if (i > 2) {
                return true;
            }
            switch (str.hashCode()) {
                case -1021023306:
                    break;
                case 1224071439:
                    if (str.equals(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_SOURCE_ENTRY)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1406273191:
                    if (str.equals(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG_DRAWABLE_STYLE_ENTRY)) {
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
                    java.lang.String attributeValue = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_ID);
                    java.lang.String attributeValue2 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_STYLE);
                    android.app.admin.ParcelableResource createFromXml = android.app.admin.ParcelableResource.createFromXml(typedXmlPullParser);
                    if (!com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.containsKey(attributeValue)) {
                        com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.put(attributeValue, new java.util.HashMap());
                    }
                    ((java.util.Map) com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.get(attributeValue)).put(attributeValue2, createFromXml);
                    return true;
                case 1:
                    java.lang.String attributeValue3 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_ID);
                    java.lang.String attributeValue4 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_SOURCE);
                    java.lang.String attributeValue5 = typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_DRAWABLE_STYLE);
                    android.app.admin.ParcelableResource createFromXml2 = android.app.admin.ParcelableResource.createFromXml(typedXmlPullParser);
                    if (!com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.containsKey(attributeValue3)) {
                        com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.put(attributeValue3, new java.util.HashMap());
                    }
                    if (!((java.util.Map) com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue3)).containsKey(attributeValue4)) {
                        ((java.util.Map) com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue3)).put(attributeValue4, new java.util.HashMap());
                    }
                    ((java.util.Map) ((java.util.Map) com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue3)).get(attributeValue4)).put(attributeValue5, createFromXml2);
                    return true;
                case 2:
                    com.android.server.devicepolicy.DeviceManagementResourcesProvider.this.mUpdatedStrings.put(typedXmlPullParser.getAttributeValue((java.lang.String) null, com.android.server.devicepolicy.DeviceManagementResourcesProvider.ATTR_SOURCE_ID), android.app.admin.ParcelableResource.createFromXml(typedXmlPullParser));
                    return true;
                default:
                    android.util.Log.e(com.android.server.devicepolicy.DeviceManagementResourcesProvider.TAG, "Unexpected tag: " + str);
                    return false;
            }
        }
    }

    public static class Injector {
        java.io.File environmentGetDataSystemDirectory() {
            return android.os.Environment.getDataSystemDirectory();
        }
    }
}
