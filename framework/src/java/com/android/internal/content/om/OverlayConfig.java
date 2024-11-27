package com.android.internal.content.om;

/* loaded from: classes4.dex */
public class OverlayConfig {
    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
    public static final java.lang.String PARTITION_ORDER_FILE_PATH = "/product/overlay/partition_order.xml";
    static final java.lang.String TAG = "OverlayConfig";
    private static com.android.internal.content.om.OverlayConfig sInstance;
    private static final java.util.Comparator<com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration> sStaticOverlayComparator = new java.util.Comparator() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda4
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            return com.android.internal.content.om.OverlayConfig.lambda$static$0((com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration) obj, (com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration) obj2);
        }
    };
    private final android.util.ArrayMap<java.lang.String, com.android.internal.content.om.OverlayConfig.Configuration> mConfigurations = new android.util.ArrayMap<>();
    private final boolean mIsDefaultPartitionOrder;
    private final java.lang.String mPartitionOrder;

    public interface PackageProvider {

        public interface Package {
            java.lang.String getBaseApkPath();

            int getOverlayPriority();

            java.lang.String getOverlayTarget();

            java.lang.String getPackageName();

            int getTargetSdkVersion();

            boolean isOverlayIsStatic();
        }

        void forEachPackage(com.android.internal.util.function.TriConsumer<com.android.internal.content.om.OverlayConfig.PackageProvider.Package, java.lang.Boolean, java.io.File> triConsumer);
    }

    private static native java.lang.String[] createIdmap(java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2, boolean z);

    public static final class Configuration {
        public final int configIndex;
        public final com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfig;

        public Configuration(com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfiguration, int i) {
            this.parsedConfig = parsedConfiguration;
            this.configIndex = i;
        }
    }

    static /* synthetic */ int lambda$static$0(com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfiguration, com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfiguration2) {
        com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedOverlayInfo = parsedConfiguration.parsedInfo;
        com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedOverlayInfo2 = parsedConfiguration2.parsedInfo;
        com.android.internal.util.Preconditions.checkArgument(parsedOverlayInfo.isStatic && parsedOverlayInfo2.isStatic, "attempted to sort non-static overlay");
        if (!parsedOverlayInfo.targetPackageName.equals(parsedOverlayInfo2.targetPackageName)) {
            return parsedOverlayInfo.targetPackageName.compareTo(parsedOverlayInfo2.targetPackageName);
        }
        int i = parsedOverlayInfo.priority - parsedOverlayInfo2.priority;
        return i == 0 ? parsedOverlayInfo.path.compareTo(parsedOverlayInfo2.path) : i;
    }

    public OverlayConfig(final java.io.File file, java.util.function.Supplier<com.android.internal.content.om.OverlayScanner> supplier, com.android.internal.content.om.OverlayConfig.PackageProvider packageProvider) {
        java.util.ArrayList arrayList;
        java.util.ArrayList arrayList2;
        int i = 1;
        com.android.internal.util.Preconditions.checkArgument((supplier == null) != (packageProvider == null), "scannerFactory and packageProvider cannot be both null or both non-null");
        if (file == null) {
            arrayList = new java.util.ArrayList(android.content.pm.PackagePartitions.getOrderedPartitions(new java.util.function.Function() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return new com.android.internal.content.om.OverlayConfigParser.OverlayPartition((android.content.pm.PackagePartitions.SystemPartition) obj);
                }
            }));
        } else {
            arrayList = new java.util.ArrayList(android.content.pm.PackagePartitions.getOrderedPartitions(new java.util.function.Function() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return com.android.internal.content.om.OverlayConfig.lambda$new$1(file, (android.content.pm.PackagePartitions.SystemPartition) obj);
                }
            }));
        }
        this.mIsDefaultPartitionOrder = !sortPartitions(PARTITION_ORDER_FILE_PATH, arrayList);
        this.mPartitionOrder = generatePartitionOrderString(arrayList);
        android.util.ArrayMap<java.lang.Integer, java.util.List<java.lang.String>> activeApexes = getActiveApexes(arrayList);
        java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> overlayPackageInfos = packageProvider == null ? null : getOverlayPackageInfos(packageProvider);
        java.util.ArrayList arrayList3 = new java.util.ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            com.android.internal.content.om.OverlayConfigParser.OverlayPartition overlayPartition = (com.android.internal.content.om.OverlayConfigParser.OverlayPartition) arrayList.get(i2);
            com.android.internal.content.om.OverlayScanner overlayScanner = supplier == null ? null : supplier.get();
            java.util.ArrayList<com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration> configurations = com.android.internal.content.om.OverlayConfigParser.getConfigurations(overlayPartition, overlayScanner, overlayPackageInfos, activeApexes.getOrDefault(java.lang.Integer.valueOf(overlayPartition.type), java.util.Collections.emptyList()));
            if (configurations != null) {
                arrayList3.addAll(configurations);
            } else {
                if (supplier != null) {
                    arrayList2 = new java.util.ArrayList(overlayScanner.getAllParsedInfos());
                } else {
                    arrayList2 = new java.util.ArrayList(overlayPackageInfos.values());
                    for (int size2 = arrayList2.size() - i; size2 >= 0; size2--) {
                        if (!overlayPartition.containsFile(((com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo) arrayList2.get(size2)).getOriginalPartitionPath())) {
                            arrayList2.remove(size2);
                        }
                    }
                }
                java.util.ArrayList arrayList4 = new java.util.ArrayList();
                int size3 = arrayList2.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo parsedOverlayInfo = (com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo) arrayList2.get(i3);
                    if (parsedOverlayInfo.isStatic) {
                        arrayList4.add(new com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration(parsedOverlayInfo.packageName, true, false, overlayPartition.policy, parsedOverlayInfo, null));
                    }
                }
                arrayList4.sort(sStaticOverlayComparator);
                arrayList3.addAll(arrayList4);
            }
            i2++;
            i = 1;
        }
        int size4 = arrayList3.size();
        for (int i4 = 0; i4 < size4; i4++) {
            com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration parsedConfiguration = (com.android.internal.content.om.OverlayConfigParser.ParsedConfiguration) arrayList3.get(i4);
            this.mConfigurations.put(parsedConfiguration.packageName, new com.android.internal.content.om.OverlayConfig.Configuration(parsedConfiguration, i4));
        }
    }

    static /* synthetic */ com.android.internal.content.om.OverlayConfigParser.OverlayPartition lambda$new$1(java.io.File file, android.content.pm.PackagePartitions.SystemPartition systemPartition) {
        return new com.android.internal.content.om.OverlayConfigParser.OverlayPartition(new java.io.File(file, systemPartition.getNonConicalFolder().getPath()), systemPartition);
    }

    private static java.lang.String generatePartitionOrderString(java.util.List<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> list) {
        if (list == null || list.size() == 0) {
            return "";
        }
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(list.get(0).getName());
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ").append(list.get(i).getName());
        }
        return sb.toString();
    }

    private static boolean parseAndValidatePartitionsOrderXml(java.lang.String str, java.util.Map<java.lang.String, java.lang.Integer> map, java.util.List<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> list) {
        try {
            java.io.File file = new java.io.File(str);
            if (!file.exists()) {
                android.util.Log.w(TAG, "partition_order.xml does not exist.");
                return false;
            }
            org.w3c.dom.Document parse = javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            parse.getDocumentElement().normalize();
            if (!parse.getDocumentElement().getNodeName().equals("partition-order")) {
                android.util.Log.w(TAG, "Invalid partition_order.xml, xml root element is not partition-order");
                return false;
            }
            org.w3c.dom.NodeList elementsByTagName = parse.getElementsByTagName("partition");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                org.w3c.dom.Node item = elementsByTagName.item(i);
                if (item.getNodeType() == 1) {
                    java.lang.String attribute = ((org.w3c.dom.Element) item).getAttribute("name");
                    if (map.containsKey(attribute)) {
                        android.util.Log.w(TAG, "Invalid partition_order.xml, it has duplicate partition: " + attribute);
                        return false;
                    }
                    map.put(attribute, java.lang.Integer.valueOf(i));
                }
            }
            if (map.keySet().size() != list.size()) {
                android.util.Log.w(TAG, "Invalid partition_order.xml, partition_order.xml has " + map.keySet().size() + " partitions, which is different from SYSTEM_PARTITIONS");
                return false;
            }
            for (int i2 = 0; i2 < list.size(); i2++) {
                if (!map.keySet().contains(list.get(i2).getName())) {
                    android.util.Log.w(TAG, "Invalid Parsing partition_order.xml, partition_order.xml does not have partition: " + list.get(i2).getName());
                    return false;
                }
            }
            android.util.Log.i(TAG, "Sorting partitions in the specified order from partitions_order.xml");
            return true;
        } catch (java.io.IOException | javax.xml.parsers.ParserConfigurationException | org.xml.sax.SAXException e) {
            android.util.Log.w(TAG, "Parsing or validating partition_order.xml failed, exception thrown: " + e.getMessage());
            return false;
        }
    }

    public static boolean sortPartitions(java.lang.String str, java.util.List<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> list) {
        final java.util.HashMap hashMap = new java.util.HashMap();
        if (!parseAndValidatePartitionsOrderXml(str, hashMap, list)) {
            return false;
        }
        java.util.Collections.sort(list, java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int intValue;
                intValue = ((java.lang.Integer) hashMap.get(((com.android.internal.content.om.OverlayConfigParser.OverlayPartition) obj).getName())).intValue();
                return intValue;
            }
        }));
        return true;
    }

    public static com.android.internal.content.om.OverlayConfig getZygoteInstance() {
        android.os.Trace.traceBegin(67108864L, "OverlayConfig#getZygoteInstance");
        try {
            return new com.android.internal.content.om.OverlayConfig(null, new java.util.function.Supplier() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    return new com.android.internal.content.om.OverlayScanner();
                }
            }, null);
        } finally {
            android.os.Trace.traceEnd(67108864L);
        }
    }

    public static com.android.internal.content.om.OverlayConfig initializeSystemInstance(com.android.internal.content.om.OverlayConfig.PackageProvider packageProvider) {
        android.os.Trace.traceBegin(67108864L, "OverlayConfig#initializeSystemInstance");
        try {
            sInstance = new com.android.internal.content.om.OverlayConfig(null, null, packageProvider);
            android.os.Trace.traceEnd(67108864L);
            return sInstance;
        } catch (java.lang.Throwable th) {
            android.os.Trace.traceEnd(67108864L);
            throw th;
        }
    }

    public static com.android.internal.content.om.OverlayConfig getSystemInstance() {
        if (sInstance == null) {
            throw new java.lang.IllegalStateException("System instance not initialized");
        }
        return sInstance;
    }

    public com.android.internal.content.om.OverlayConfig.Configuration getConfiguration(java.lang.String str) {
        return this.mConfigurations.get(str);
    }

    public boolean isEnabled(java.lang.String str) {
        com.android.internal.content.om.OverlayConfig.Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return false;
        }
        return configuration.parsedConfig.enabled;
    }

    public boolean isMutable(java.lang.String str) {
        com.android.internal.content.om.OverlayConfig.Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return true;
        }
        return configuration.parsedConfig.mutable;
    }

    public int getPriority(java.lang.String str) {
        com.android.internal.content.om.OverlayConfig.Configuration configuration = this.mConfigurations.get(str);
        if (configuration == null) {
            return Integer.MAX_VALUE;
        }
        return configuration.configIndex;
    }

    private java.util.ArrayList<com.android.internal.content.om.OverlayConfig.Configuration> getSortedOverlays() {
        java.util.ArrayList<com.android.internal.content.om.OverlayConfig.Configuration> arrayList = new java.util.ArrayList<>();
        int size = this.mConfigurations.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(this.mConfigurations.valueAt(i));
        }
        arrayList.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda5
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int i2;
                i2 = ((com.android.internal.content.om.OverlayConfig.Configuration) obj).configIndex;
                return i2;
            }
        }));
        return arrayList;
    }

    private static java.util.Map<java.lang.String, com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo> getOverlayPackageInfos(com.android.internal.content.om.OverlayConfig.PackageProvider packageProvider) {
        final java.util.HashMap hashMap = new java.util.HashMap();
        packageProvider.forEachPackage(new com.android.internal.util.function.TriConsumer() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda6
            @Override // com.android.internal.util.function.TriConsumer
            public final void accept(java.lang.Object obj, java.lang.Object obj2, java.lang.Object obj3) {
                com.android.internal.content.om.OverlayConfig.lambda$getOverlayPackageInfos$4(hashMap, (com.android.internal.content.om.OverlayConfig.PackageProvider.Package) obj, (java.lang.Boolean) obj2, (java.io.File) obj3);
            }
        });
        return hashMap;
    }

    static /* synthetic */ void lambda$getOverlayPackageInfos$4(java.util.HashMap hashMap, com.android.internal.content.om.OverlayConfig.PackageProvider.Package r10, java.lang.Boolean bool, java.io.File file) {
        if (r10.getOverlayTarget() != null && bool.booleanValue()) {
            hashMap.put(r10.getPackageName(), new com.android.internal.content.om.OverlayScanner.ParsedOverlayInfo(r10.getPackageName(), r10.getOverlayTarget(), r10.getTargetSdkVersion(), r10.isOverlayIsStatic(), r10.getOverlayPriority(), new java.io.File(r10.getBaseApkPath()), file));
        }
    }

    private static android.util.ArrayMap<java.lang.Integer, java.util.List<java.lang.String>> getActiveApexes(java.util.List<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> list) {
        android.util.ArrayMap<java.lang.Integer, java.util.List<java.lang.String>> arrayMap = new android.util.ArrayMap<>();
        java.util.Iterator<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> it = list.iterator();
        while (it.hasNext()) {
            arrayMap.put(java.lang.Integer.valueOf(it.next().type), new java.util.ArrayList());
        }
        java.io.File file = new java.io.File("/apex/apex-info-list.xml");
        if (file.exists() && file.canRead()) {
            try {
                java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
                try {
                    for (com.android.apex.ApexInfo apexInfo : com.android.apex.XmlParser.readApexInfoList(fileInputStream).getApexInfo()) {
                        if (apexInfo.getIsActive()) {
                            java.util.Iterator<com.android.internal.content.om.OverlayConfigParser.OverlayPartition> it2 = list.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    com.android.internal.content.om.OverlayConfigParser.OverlayPartition next = it2.next();
                                    if (next.containsPath(apexInfo.getPreinstalledModulePath())) {
                                        arrayMap.get(java.lang.Integer.valueOf(next.type)).add(apexInfo.getModuleName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    fileInputStream.close();
                } finally {
                }
            } catch (java.lang.Exception e) {
                android.util.Log.w(TAG, "Error reading apex-info-list: " + e);
            }
        }
        return arrayMap;
    }

    public static class IdmapInvocation {
        public final boolean enforceOverlayable;
        public final java.util.ArrayList<java.lang.String> overlayPaths = new java.util.ArrayList<>();
        public final java.lang.String policy;

        IdmapInvocation(boolean z, java.lang.String str) {
            this.enforceOverlayable = z;
            this.policy = str;
        }

        public java.lang.String toString() {
            return getClass().getSimpleName() + java.lang.String.format("{enforceOverlayable=%s, policy=%s, overlayPaths=[%s]}", java.lang.Boolean.valueOf(this.enforceOverlayable), this.policy, java.lang.String.join(", ", this.overlayPaths));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x005e, code lost:
    
        if (r7.policy.equals(r5.parsedConfig.policy) != false) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public java.util.ArrayList<com.android.internal.content.om.OverlayConfig.IdmapInvocation> getImmutableFrameworkOverlayIdmapInvocations(java.lang.String str) {
        com.android.internal.content.om.OverlayConfig.IdmapInvocation idmapInvocation;
        java.util.ArrayList<com.android.internal.content.om.OverlayConfig.IdmapInvocation> arrayList = new java.util.ArrayList<>();
        java.util.ArrayList<com.android.internal.content.om.OverlayConfig.Configuration> sortedOverlays = getSortedOverlays();
        int size = sortedOverlays.size();
        for (int i = 0; i < size; i++) {
            com.android.internal.content.om.OverlayConfig.Configuration configuration = sortedOverlays.get(i);
            if (!configuration.parsedConfig.mutable && configuration.parsedConfig.enabled && str.equals(configuration.parsedConfig.parsedInfo.targetPackageName)) {
                boolean z = configuration.parsedConfig.parsedInfo.targetSdkVersion >= 29;
                if (!arrayList.isEmpty()) {
                    idmapInvocation = arrayList.get(arrayList.size() - 1);
                    if (idmapInvocation.enforceOverlayable == z) {
                    }
                }
                idmapInvocation = null;
                if (idmapInvocation == null) {
                    idmapInvocation = new com.android.internal.content.om.OverlayConfig.IdmapInvocation(z, configuration.parsedConfig.policy);
                    arrayList.add(idmapInvocation);
                }
                idmapInvocation.overlayPaths.add(configuration.parsedConfig.parsedInfo.path.getAbsolutePath());
            }
        }
        return arrayList;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.internal.content.om.OverlayConfig$1] */
    public java.lang.String[] createImmutableFrameworkIdmapsInZygote() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry entry : new java.util.HashMap<java.lang.String, java.lang.String>() { // from class: com.android.internal.content.om.OverlayConfig.1
            {
                put("/system/framework/framework-res.apk", "android");
                put("/system/framework/org.lineageos.platform-res.apk", "lineageos.platform");
            }
        }.entrySet()) {
            java.lang.String str = (java.lang.String) entry.getKey();
            java.util.ArrayList<com.android.internal.content.om.OverlayConfig.IdmapInvocation> immutableFrameworkOverlayIdmapInvocations = getImmutableFrameworkOverlayIdmapInvocations((java.lang.String) entry.getValue());
            int size = immutableFrameworkOverlayIdmapInvocations.size();
            for (int i = 0; i < size; i++) {
                com.android.internal.content.om.OverlayConfig.IdmapInvocation idmapInvocation = immutableFrameworkOverlayIdmapInvocations.get(i);
                java.lang.String[] createIdmap = createIdmap(str, (java.lang.String[]) idmapInvocation.overlayPaths.toArray(new java.lang.String[0]), new java.lang.String[]{"public", idmapInvocation.policy}, idmapInvocation.enforceOverlayable);
                if (createIdmap == null) {
                    android.util.Log.w(TAG, "'idmap2 create-multiple' failed: no mutable=\"false\" overlays targeting \"android\" will be loaded");
                    return new java.lang.String[0];
                }
                arrayList.addAll(java.util.Arrays.asList(createIdmap));
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    public void dump(java.io.PrintWriter printWriter) {
        android.util.IndentingPrintWriter indentingPrintWriter = new android.util.IndentingPrintWriter(printWriter);
        indentingPrintWriter.println("Overlay configurations:");
        indentingPrintWriter.increaseIndent();
        java.util.ArrayList arrayList = new java.util.ArrayList(this.mConfigurations.values());
        arrayList.sort(java.util.Comparator.comparingInt(new java.util.function.ToIntFunction() { // from class: com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int i;
                i = ((com.android.internal.content.om.OverlayConfig.Configuration) obj).configIndex;
                return i;
            }
        }));
        for (int i = 0; i < arrayList.size(); i++) {
            com.android.internal.content.om.OverlayConfig.Configuration configuration = (com.android.internal.content.om.OverlayConfig.Configuration) arrayList.get(i);
            indentingPrintWriter.print(configuration.configIndex);
            indentingPrintWriter.print(", ");
            indentingPrintWriter.print(configuration.parsedConfig);
            indentingPrintWriter.println();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    public boolean isDefaultPartitionOrder() {
        return this.mIsDefaultPartitionOrder;
    }

    public java.lang.String getPartitionOrder() {
        return this.mPartitionOrder;
    }
}
