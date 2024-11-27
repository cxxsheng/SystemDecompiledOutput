package android.content.pm;

/* loaded from: classes.dex */
public abstract class RegisteredServicesCache<V> {
    private static final boolean DEBUG = false;
    protected static final java.lang.String REGISTERED_SERVICES_DIR = "registered_services";
    private static final java.lang.String TAG = "PackageManager";
    private final java.lang.String mAttributesName;
    public final android.content.Context mContext;
    private android.os.Handler mHandler;
    private final java.lang.String mInterfaceName;
    private android.content.pm.RegisteredServicesCacheListener<V> mListener;
    private final java.lang.String mMetaDataName;
    private final android.content.pm.XmlSerializerAndParser<V> mSerializerAndParser;
    protected final java.lang.Object mServicesLock = new java.lang.Object();
    private final android.util.SparseArray<android.content.pm.RegisteredServicesCache.UserServices<V>> mUserServices = new android.util.SparseArray<>(2);
    private final android.content.BroadcastReceiver mPackageReceiver = new android.content.BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            int intExtra = intent.getIntExtra(android.content.Intent.EXTRA_UID, -1);
            if (intExtra != -1) {
                android.content.pm.RegisteredServicesCache.this.handlePackageEvent(intent, android.os.UserHandle.getUserId(intExtra));
            }
        }
    };
    private final android.content.BroadcastReceiver mExternalReceiver = new android.content.BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.content.pm.RegisteredServicesCache.this.handlePackageEvent(intent, 0);
        }
    };
    private final android.content.BroadcastReceiver mUserRemovedReceiver = new android.content.BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.content.pm.RegisteredServicesCache.this.onUserRemoved(intent.getIntExtra(android.content.Intent.EXTRA_USER_HANDLE, -1));
        }
    };

    public abstract V parseServiceAttributes(android.content.res.Resources resources, java.lang.String str, android.util.AttributeSet attributeSet);

    private static class UserServices<V> {
        boolean mBindInstantServiceAllowed;
        boolean mPersistentServicesFileDidNotExist;
        final java.util.Map<V, java.lang.Integer> persistentServices;
        java.util.Map<V, android.content.pm.RegisteredServicesCache.ServiceInfo<V>> services;

        private UserServices() {
            this.persistentServices = com.google.android.collect.Maps.newHashMap();
            this.services = null;
            this.mPersistentServicesFileDidNotExist = true;
            this.mBindInstantServiceAllowed = false;
        }
    }

    private android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked(int i) {
        return findOrCreateUserLocked(i, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [android.content.pm.RegisteredServicesCache$UserServices-IA] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v5 */
    private android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked(int i, boolean z) {
        android.content.pm.UserInfo user;
        java.lang.AutoCloseable autoCloseable;
        android.content.pm.RegisteredServicesCache.UserServices<V> userServices = this.mUserServices.get(i);
        if (userServices == null) {
            ?? r1 = 0;
            r1 = 0;
            userServices = new android.content.pm.RegisteredServicesCache.UserServices<>();
            this.mUserServices.put(i, userServices);
            if (z && this.mSerializerAndParser != null && (user = getUser(i)) != null) {
                android.util.AtomicFile createFileForUser = createFileForUser(user.id);
                if (createFileForUser.getBaseFile().exists()) {
                    try {
                        try {
                            r1 = createFileForUser.openRead();
                            readPersistentServicesLocked(r1);
                            autoCloseable = r1;
                        } catch (java.lang.Exception e) {
                            android.util.Log.w(TAG, "Error reading persistent services for user " + user.id, e);
                            autoCloseable = r1;
                        }
                    } finally {
                        libcore.io.IoUtils.closeQuietly((java.lang.AutoCloseable) r1);
                    }
                }
            }
        }
        return userServices;
    }

    public RegisteredServicesCache(android.content.Context context, java.lang.String str, java.lang.String str2, java.lang.String str3, android.content.pm.XmlSerializerAndParser<V> xmlSerializerAndParser) {
        this.mContext = context;
        this.mInterfaceName = str;
        this.mMetaDataName = str2;
        this.mAttributesName = str3;
        this.mSerializerAndParser = xmlSerializerAndParser;
        migrateIfNecessaryLocked();
        boolean isCore = android.os.UserHandle.isCore(android.os.Process.myUid());
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.content.Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(android.content.Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction(android.content.Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        if (isCore) {
            intentFilter.setPriority(1000);
        }
        android.os.Handler handler = com.android.internal.os.BackgroundThread.getHandler();
        this.mContext.registerReceiverAsUser(this.mPackageReceiver, android.os.UserHandle.ALL, intentFilter, null, handler);
        android.content.IntentFilter intentFilter2 = new android.content.IntentFilter();
        intentFilter2.addAction(android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        intentFilter2.addAction(android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        if (isCore) {
            intentFilter2.setPriority(1000);
        }
        this.mContext.registerReceiver(this.mExternalReceiver, intentFilter2, null, handler);
        android.content.IntentFilter intentFilter3 = new android.content.IntentFilter();
        intentFilter3.addAction(android.content.Intent.ACTION_USER_REMOVED);
        if (isCore) {
            intentFilter3.setPriority(1000);
        }
        this.mContext.registerReceiver(this.mUserRemovedReceiver, intentFilter3, null, handler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageEvent(android.content.Intent intent, int i) {
        int[] intArrayExtra;
        java.lang.String action = intent.getAction();
        boolean z = android.content.Intent.ACTION_PACKAGE_REMOVED.equals(action) || android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action);
        boolean booleanExtra = intent.getBooleanExtra(android.content.Intent.EXTRA_REPLACING, false);
        if (!z || !booleanExtra) {
            if (android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action) || android.content.Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
                intArrayExtra = intent.getIntArrayExtra(android.content.Intent.EXTRA_CHANGED_UID_LIST);
            } else {
                int intExtra = intent.getIntExtra(android.content.Intent.EXTRA_UID, -1);
                if (intExtra <= 0) {
                    intArrayExtra = null;
                } else {
                    intArrayExtra = new int[]{intExtra};
                }
            }
            generateServicesMap(intArrayExtra, i);
        }
    }

    public void invalidateCache(int i) {
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(i).services = null;
            onServicesChangedLocked(i);
        }
    }

    public void dump(java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr, int i) {
        synchronized (this.mServicesLock) {
            android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services != null) {
                printWriter.println("RegisteredServicesCache: " + findOrCreateUserLocked.services.size() + " services");
                java.util.Iterator<android.content.pm.RegisteredServicesCache.ServiceInfo<V>> it = findOrCreateUserLocked.services.values().iterator();
                while (it.hasNext()) {
                    printWriter.println("  " + it.next());
                }
            } else {
                printWriter.println("RegisteredServicesCache: services not loaded");
            }
        }
    }

    public android.content.pm.RegisteredServicesCacheListener<V> getListener() {
        android.content.pm.RegisteredServicesCacheListener<V> registeredServicesCacheListener;
        synchronized (this) {
            registeredServicesCacheListener = this.mListener;
        }
        return registeredServicesCacheListener;
    }

    public void setListener(android.content.pm.RegisteredServicesCacheListener<V> registeredServicesCacheListener, android.os.Handler handler) {
        if (handler == null) {
            handler = com.android.internal.os.BackgroundThread.getHandler();
        }
        synchronized (this) {
            this.mHandler = handler;
            this.mListener = registeredServicesCacheListener;
        }
    }

    private void notifyListener(final V v, final int i, final boolean z) {
        final android.content.pm.RegisteredServicesCacheListener<V> registeredServicesCacheListener;
        android.os.Handler handler;
        synchronized (this) {
            registeredServicesCacheListener = this.mListener;
            handler = this.mHandler;
        }
        if (registeredServicesCacheListener == null) {
            return;
        }
        handler.post(new java.lang.Runnable() { // from class: android.content.pm.RegisteredServicesCache$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                android.content.pm.RegisteredServicesCache.lambda$notifyListener$0(android.content.pm.RegisteredServicesCacheListener.this, v, i, z);
            }
        });
    }

    static /* synthetic */ void lambda$notifyListener$0(android.content.pm.RegisteredServicesCacheListener registeredServicesCacheListener, java.lang.Object obj, int i, boolean z) {
        try {
            registeredServicesCacheListener.onServiceChanged(obj, i, z);
        } catch (java.lang.Throwable th) {
            android.util.Slog.wtf(TAG, "Exception from onServiceChanged", th);
        }
    }

    public static class ServiceInfo<V> {
        public final android.content.pm.ComponentInfo componentInfo;
        public final android.content.ComponentName componentName;
        public final V type;
        public final int uid;

        public ServiceInfo(V v, android.content.pm.ComponentInfo componentInfo, android.content.ComponentName componentName) {
            this.type = v;
            this.componentInfo = componentInfo;
            this.componentName = componentName;
            this.uid = componentInfo != null ? componentInfo.applicationInfo.uid : -1;
        }

        public java.lang.String toString() {
            return "ServiceInfo: " + this.type + ", " + this.componentName + ", uid " + this.uid;
        }
    }

    public android.content.pm.RegisteredServicesCache.ServiceInfo<V> getServiceInfo(V v, int i) {
        android.content.pm.RegisteredServicesCache.ServiceInfo<V> serviceInfo;
        synchronized (this.mServicesLock) {
            android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            serviceInfo = findOrCreateUserLocked.services.get(v);
        }
        return serviceInfo;
    }

    public java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<V>> getAllServices(int i) {
        java.util.Collection<android.content.pm.RegisteredServicesCache.ServiceInfo<V>> unmodifiableCollection;
        synchronized (this.mServicesLock) {
            android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            unmodifiableCollection = java.util.Collections.unmodifiableCollection(new java.util.ArrayList(findOrCreateUserLocked.services.values()));
        }
        return unmodifiableCollection;
    }

    public void updateServices(int i) {
        android.content.pm.ApplicationInfo applicationInfo;
        synchronized (this.mServicesLock) {
            android.content.pm.RegisteredServicesCache.UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                return;
            }
            android.util.IntArray intArray = null;
            for (android.content.pm.RegisteredServicesCache.ServiceInfo serviceInfo : new java.util.ArrayList(findOrCreateUserLocked.services.values())) {
                long j = serviceInfo.componentInfo.applicationInfo.versionCode;
                try {
                    applicationInfo = this.mContext.getPackageManager().getApplicationInfoAsUser(serviceInfo.componentInfo.packageName, 0, i);
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    applicationInfo = null;
                }
                if (applicationInfo == null || applicationInfo.versionCode != j) {
                    if (intArray == null) {
                        intArray = new android.util.IntArray();
                    }
                    intArray.add(serviceInfo.uid);
                }
            }
            if (intArray != null && intArray.size() > 0) {
                generateServicesMap(intArray.toArray(), i);
            }
        }
    }

    public boolean getBindInstantServiceAllowed(int i) {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "getBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            z = findOrCreateUserLocked(i).mBindInstantServiceAllowed;
        }
        return z;
    }

    public void setBindInstantServiceAllowed(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(android.Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "setBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(i).mBindInstantServiceAllowed = z;
        }
    }

    protected boolean inSystemImage(int i) {
        java.lang.String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null) {
            for (java.lang.String str : packagesForUid) {
                try {
                    if ((this.mContext.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0) {
                        return true;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    return false;
                }
            }
        }
        return false;
    }

    protected java.util.List<android.content.pm.ResolveInfo> queryIntentServices(int i) {
        int i2;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        synchronized (this.mServicesLock) {
            if (!findOrCreateUserLocked(i).mBindInstantServiceAllowed) {
                i2 = 786560;
            } else {
                i2 = 9175168;
            }
        }
        return packageManager.queryIntentServicesAsUser(new android.content.Intent(this.mInterfaceName), i2, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void generateServicesMap(int[] iArr, int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.content.pm.ResolveInfo resolveInfo : queryIntentServices(i)) {
            try {
                android.content.pm.RegisteredServicesCache.ServiceInfo parseServiceInfo = parseServiceInfo(resolveInfo);
                if (parseServiceInfo == null) {
                    android.util.Log.w(TAG, "Unable to load service info " + resolveInfo.toString());
                } else {
                    arrayList.add(parseServiceInfo);
                }
            } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                android.util.Log.w(TAG, "Unable to load service info " + resolveInfo.toString(), e);
            }
        }
        synchronized (this.mServicesLock) {
            android.content.pm.RegisteredServicesCache.UserServices findOrCreateUserLocked = findOrCreateUserLocked(i);
            boolean z = findOrCreateUserLocked.services == null;
            if (z) {
                findOrCreateUserLocked.services = com.google.android.collect.Maps.newHashMap();
            }
            new java.lang.StringBuilder();
            java.util.Iterator it = arrayList.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                android.content.pm.RegisteredServicesCache.ServiceInfo<V> serviceInfo = (android.content.pm.RegisteredServicesCache.ServiceInfo) it.next();
                java.lang.Integer num = findOrCreateUserLocked.persistentServices.get(serviceInfo.type);
                if (num == null) {
                    findOrCreateUserLocked.services.put(serviceInfo.type, serviceInfo);
                    findOrCreateUserLocked.persistentServices.put(serviceInfo.type, java.lang.Integer.valueOf(serviceInfo.uid));
                    if (!findOrCreateUserLocked.mPersistentServicesFileDidNotExist || !z) {
                        notifyListener(serviceInfo.type, i, false);
                    }
                    z2 = true;
                } else if (num.intValue() == serviceInfo.uid) {
                    findOrCreateUserLocked.services.put(serviceInfo.type, serviceInfo);
                } else if (inSystemImage(serviceInfo.uid) || !containsTypeAndUid(arrayList, serviceInfo.type, num.intValue())) {
                    findOrCreateUserLocked.services.put(serviceInfo.type, serviceInfo);
                    findOrCreateUserLocked.persistentServices.put(serviceInfo.type, java.lang.Integer.valueOf(serviceInfo.uid));
                    notifyListener(serviceInfo.type, i, false);
                    z2 = true;
                }
            }
            java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
            for (V v : findOrCreateUserLocked.persistentServices.keySet()) {
                if (!containsType(arrayList, v) && containsUid(iArr, findOrCreateUserLocked.persistentServices.get(v).intValue())) {
                    newArrayList.add(v);
                }
            }
            java.util.Iterator it2 = newArrayList.iterator();
            while (it2.hasNext()) {
                java.lang.Object next = it2.next();
                findOrCreateUserLocked.persistentServices.remove(next);
                findOrCreateUserLocked.services.remove(next);
                notifyListener(next, i, true);
                z2 = true;
            }
            if (z2) {
                onServicesChangedLocked(i);
                writePersistentServicesLocked(findOrCreateUserLocked, i);
            }
        }
    }

    protected void onServicesChangedLocked(int i) {
    }

    private boolean containsUid(int[] iArr, int i) {
        return iArr == null || com.android.internal.util.ArrayUtils.contains(iArr, i);
    }

    private boolean containsType(java.util.ArrayList<android.content.pm.RegisteredServicesCache.ServiceInfo<V>> arrayList, V v) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).type.equals(v)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsTypeAndUid(java.util.ArrayList<android.content.pm.RegisteredServicesCache.ServiceInfo<V>> arrayList, V v, int i) {
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            android.content.pm.RegisteredServicesCache.ServiceInfo<V> serviceInfo = arrayList.get(i2);
            if (serviceInfo.type.equals(v) && serviceInfo.uid == i) {
                return true;
            }
        }
        return false;
    }

    protected android.content.pm.RegisteredServicesCache.ServiceInfo<V> parseServiceInfo(android.content.pm.ResolveInfo resolveInfo) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        int next;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        android.content.ComponentName componentName = new android.content.ComponentName(serviceInfo.packageName, serviceInfo.name);
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                android.content.res.XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, this.mMetaDataName);
                try {
                    if (loadXmlMetaData == null) {
                        throw new org.xmlpull.v1.XmlPullParserException("No " + this.mMetaDataName + " meta-data");
                    }
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!this.mAttributesName.equals(loadXmlMetaData.getName())) {
                        throw new org.xmlpull.v1.XmlPullParserException("Meta-data does not start with " + this.mAttributesName + " tag");
                    }
                    V parseServiceAttributes = parseServiceAttributes(packageManager.getResourcesForApplication(serviceInfo.applicationInfo), serviceInfo.packageName, asAttributeSet);
                    if (parseServiceAttributes == null) {
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    android.content.pm.RegisteredServicesCache.ServiceInfo<V> serviceInfo2 = new android.content.pm.RegisteredServicesCache.ServiceInfo<>(parseServiceAttributes, resolveInfo.serviceInfo, componentName);
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return serviceInfo2;
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    throw new org.xmlpull.v1.XmlPullParserException("Unable to load resources for pacakge " + serviceInfo.packageName);
                } catch (java.lang.Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e2) {
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private void readPersistentServicesLocked(java.io.InputStream inputStream) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
        for (int eventType = resolvePullParser.getEventType(); eventType != 2 && eventType != 1; eventType = resolvePullParser.next()) {
        }
        if ("services".equals(resolvePullParser.getName())) {
            int next = resolvePullParser.next();
            do {
                if (next == 2 && resolvePullParser.getDepth() == 2 && "service".equals(resolvePullParser.getName())) {
                    V createFromXml = this.mSerializerAndParser.createFromXml(resolvePullParser);
                    if (createFromXml != null) {
                        int attributeInt = resolvePullParser.getAttributeInt(null, "uid");
                        findOrCreateUserLocked(android.os.UserHandle.getUserId(attributeInt), false).persistentServices.put(createFromXml, java.lang.Integer.valueOf(attributeInt));
                    } else {
                        return;
                    }
                }
                next = resolvePullParser.next();
            } while (next != 1);
        }
    }

    private void migrateIfNecessaryLocked() {
        if (this.mSerializerAndParser == null) {
            return;
        }
        java.io.File file = new java.io.File(new java.io.File(getDataDirectory(), "system"), REGISTERED_SERVICES_DIR);
        android.util.AtomicFile atomicFile = new android.util.AtomicFile(new java.io.File(file, this.mInterfaceName + ".xml"));
        if (atomicFile.getBaseFile().exists()) {
            java.io.File file2 = new java.io.File(file, this.mInterfaceName + ".xml.migrated");
            if (!file2.exists()) {
                java.io.FileInputStream fileInputStream = null;
                try {
                    try {
                        fileInputStream = atomicFile.openRead();
                        this.mUserServices.clear();
                        readPersistentServicesLocked(fileInputStream);
                    } catch (java.lang.Exception e) {
                        android.util.Log.w(TAG, "Error reading persistent services, starting from scratch", e);
                    }
                    try {
                        for (android.content.pm.UserInfo userInfo : getUsers()) {
                            android.content.pm.RegisteredServicesCache.UserServices<V> userServices = this.mUserServices.get(userInfo.id);
                            if (userServices != null) {
                                writePersistentServicesLocked(userServices, userInfo.id);
                            }
                        }
                        file2.createNewFile();
                    } catch (java.lang.Exception e2) {
                        android.util.Log.w(TAG, "Migration failed", e2);
                    }
                    this.mUserServices.clear();
                } finally {
                    libcore.io.IoUtils.closeQuietly(fileInputStream);
                }
            }
        }
    }

    private void writePersistentServicesLocked(android.content.pm.RegisteredServicesCache.UserServices<V> userServices, int i) {
        java.io.FileOutputStream startWrite;
        if (this.mSerializerAndParser == null) {
            return;
        }
        android.util.AtomicFile createFileForUser = createFileForUser(i);
        java.io.FileOutputStream fileOutputStream = null;
        try {
            startWrite = createFileForUser.startWrite();
        } catch (java.io.IOException e) {
            e = e;
        }
        try {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument(null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag(null, "services");
            for (java.util.Map.Entry<V, java.lang.Integer> entry : userServices.persistentServices.entrySet()) {
                resolveSerializer.startTag(null, "service");
                resolveSerializer.attributeInt(null, "uid", entry.getValue().intValue());
                this.mSerializerAndParser.writeAsXml((android.content.pm.XmlSerializerAndParser<V>) entry.getKey(), resolveSerializer);
                resolveSerializer.endTag(null, "service");
            }
            resolveSerializer.endTag(null, "services");
            resolveSerializer.endDocument();
            createFileForUser.finishWrite(startWrite);
        } catch (java.io.IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            android.util.Log.w(TAG, "Error writing accounts", e);
            if (fileOutputStream != null) {
                createFileForUser.failWrite(fileOutputStream);
            }
        }
    }

    protected void onUserRemoved(int i) {
        synchronized (this.mServicesLock) {
            this.mUserServices.remove(i);
        }
    }

    protected java.util.List<android.content.pm.UserInfo> getUsers() {
        return android.os.UserManager.get(this.mContext).getAliveUsers();
    }

    protected android.content.pm.UserInfo getUser(int i) {
        return android.os.UserManager.get(this.mContext).getUserInfo(i);
    }

    private android.util.AtomicFile createFileForUser(int i) {
        return new android.util.AtomicFile(new java.io.File(getUserSystemDirectory(i), "registered_services/" + this.mInterfaceName + ".xml"));
    }

    protected java.io.File getUserSystemDirectory(int i) {
        return android.os.Environment.getUserSystemDirectory(i);
    }

    protected java.io.File getDataDirectory() {
        return android.os.Environment.getDataDirectory();
    }

    protected java.util.Map<V, java.lang.Integer> getPersistentServices(int i) {
        return findOrCreateUserLocked(i).persistentServices;
    }

    public void unregisterReceivers() {
        this.mContext.unregisterReceiver(this.mPackageReceiver);
        this.mContext.unregisterReceiver(this.mExternalReceiver);
        this.mContext.unregisterReceiver(this.mUserRemovedReceiver);
    }
}
