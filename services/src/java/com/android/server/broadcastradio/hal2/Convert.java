package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
class Convert {
    private static final java.lang.String TAG = "BcRadio2Srv.convert";
    private static final java.util.Map<java.lang.Integer, com.android.server.broadcastradio.hal2.Convert.MetadataDef> metadataKeys = new java.util.HashMap();

    private enum MetadataType {
        INT,
        STRING
    }

    private Convert() {
        throw new java.lang.UnsupportedOperationException("Convert class is noninstantiable");
    }

    static void throwOnError(java.lang.String str, int i) {
        java.lang.String str2 = str + ": " + android.hardware.broadcastradio.V2_0.Result.toString(i);
        switch (i) {
            case 0:
                return;
            case 1:
            case 2:
            case 6:
                throw new android.os.ParcelableException(new java.lang.RuntimeException(str2));
            case 3:
                throw new java.lang.IllegalArgumentException(str2);
            case 4:
                throw new java.lang.IllegalStateException(str2);
            case 5:
                throw new java.lang.UnsupportedOperationException(str2);
            default:
                throw new android.os.ParcelableException(new java.lang.RuntimeException(str + ": unknown error (" + i + ")"));
        }
    }

    static int halResultToTunerResult(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
            default:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
        }
    }

    @android.annotation.NonNull
    static java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> vendorInfoToHal(@android.annotation.Nullable java.util.Map<java.lang.String, java.lang.String> map) {
        if (map == null) {
            return new java.util.ArrayList<>();
        }
        java.util.ArrayList<android.hardware.broadcastradio.V2_0.VendorKeyValue> arrayList = new java.util.ArrayList<>();
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.V2_0.VendorKeyValue();
            vendorKeyValue.key = entry.getKey();
            vendorKeyValue.value = entry.getValue();
            if (vendorKeyValue.key == null || vendorKeyValue.value == null) {
                android.util.Slog.w(TAG, "VendorKeyValue contains null pointers");
            } else {
                arrayList.add(vendorKeyValue);
            }
        }
        return arrayList;
    }

    @android.annotation.NonNull
    static java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHal(@android.annotation.Nullable java.util.List<android.hardware.broadcastradio.V2_0.VendorKeyValue> list) {
        if (list == null) {
            return java.util.Collections.emptyMap();
        }
        java.util.HashMap hashMap = new java.util.HashMap();
        for (android.hardware.broadcastradio.V2_0.VendorKeyValue vendorKeyValue : list) {
            if (vendorKeyValue.key == null || vendorKeyValue.value == null) {
                android.util.Slog.w(TAG, "VendorKeyValue contains null pointers");
            } else {
                hashMap.put(vendorKeyValue.key, vendorKeyValue.value);
            }
        }
        return hashMap;
    }

    private static int identifierTypeToProgramType(int i) {
        switch (i) {
            case 1:
            case 2:
                return 2;
            case 3:
                return 4;
            case 4:
            case 11:
            default:
                if (i >= 1000 && i <= 1999) {
                    return i;
                }
                return 0;
            case 5:
            case 6:
            case 7:
            case 8:
            case 14:
                return 5;
            case 9:
            case 10:
                return 6;
            case 12:
            case 13:
                return 7;
        }
    }

    @android.annotation.NonNull
    private static int[] identifierTypesToProgramTypes(@android.annotation.NonNull int[] iArr) {
        java.util.HashSet hashSet = new java.util.HashSet();
        for (int i : iArr) {
            int identifierTypeToProgramType = identifierTypeToProgramType(i);
            if (identifierTypeToProgramType != 0) {
                hashSet.add(java.lang.Integer.valueOf(identifierTypeToProgramType));
                if (identifierTypeToProgramType == 2) {
                    hashSet.add(1);
                }
                if (identifierTypeToProgramType == 4) {
                    hashSet.add(3);
                }
            }
        }
        return hashSet.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    @android.annotation.NonNull
    private static android.hardware.radio.RadioManager.BandDescriptor[] amfmConfigToBands(@android.annotation.Nullable android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig) {
        if (amFmRegionConfig == null) {
            return new android.hardware.radio.RadioManager.BandDescriptor[0];
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(amFmRegionConfig.ranges.size());
        java.util.Iterator<android.hardware.broadcastradio.V2_0.AmFmBandRange> it = amFmRegionConfig.ranges.iterator();
        while (it.hasNext()) {
            android.hardware.broadcastradio.V2_0.AmFmBandRange next = it.next();
            com.android.server.broadcastradio.hal2.FrequencyBand band = com.android.server.broadcastradio.hal2.Utils.getBand(next.lowerBound);
            if (band == com.android.server.broadcastradio.hal2.FrequencyBand.UNKNOWN) {
                android.util.Slog.e(TAG, "Unknown frequency band at " + next.lowerBound + "kHz");
            } else if (band == com.android.server.broadcastradio.hal2.FrequencyBand.FM) {
                arrayList.add(new android.hardware.radio.RadioManager.FmBandDescriptor(0, 1, next.lowerBound, next.upperBound, next.spacing, true, true, true, true, true));
            } else {
                arrayList.add(new android.hardware.radio.RadioManager.AmBandDescriptor(0, 0, next.lowerBound, next.upperBound, next.spacing, true));
            }
        }
        return (android.hardware.radio.RadioManager.BandDescriptor[]) arrayList.toArray(new android.hardware.radio.RadioManager.BandDescriptor[arrayList.size()]);
    }

    @android.annotation.Nullable
    private static java.util.Map<java.lang.String, java.lang.Integer> dabConfigFromHal(@android.annotation.Nullable java.util.List<android.hardware.broadcastradio.V2_0.DabTableEntry> list) {
        if (list == null) {
            return null;
        }
        return (java.util.Map) list.stream().collect(java.util.stream.Collectors.toMap(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.String str;
                str = ((android.hardware.broadcastradio.V2_0.DabTableEntry) obj).label;
                return str;
            }
        }, new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                java.lang.Integer lambda$dabConfigFromHal$1;
                lambda$dabConfigFromHal$1 = com.android.server.broadcastradio.hal2.Convert.lambda$dabConfigFromHal$1((android.hardware.broadcastradio.V2_0.DabTableEntry) obj);
                return lambda$dabConfigFromHal$1;
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.lang.Integer lambda$dabConfigFromHal$1(android.hardware.broadcastradio.V2_0.DabTableEntry dabTableEntry) {
        return java.lang.Integer.valueOf(dabTableEntry.frequency);
    }

    @android.annotation.NonNull
    static android.hardware.radio.RadioManager.ModuleProperties propertiesFromHal(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull android.hardware.broadcastradio.V2_0.Properties properties, @android.annotation.Nullable android.hardware.broadcastradio.V2_0.AmFmRegionConfig amFmRegionConfig, @android.annotation.Nullable java.util.List<android.hardware.broadcastradio.V2_0.DabTableEntry> list) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(properties);
        int[] array = properties.supportedIdentifierTypes.stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
        return new android.hardware.radio.RadioManager.ModuleProperties(i, str, 0, properties.maker, properties.product, properties.version, properties.serial, 1, 1, false, false, amfmConfigToBands(amFmRegionConfig), true, identifierTypesToProgramTypes(array), array, dabConfigFromHal(list), vendorInfoFromHal(properties.vendorInfo));
    }

    static void programIdentifierToHal(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier, @android.annotation.NonNull android.hardware.radio.ProgramSelector.Identifier identifier) {
        programIdentifier.type = identifier.getType();
        programIdentifier.value = identifier.getValue();
    }

    @android.annotation.NonNull
    static android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifierToHal(@android.annotation.NonNull android.hardware.radio.ProgramSelector.Identifier identifier) {
        android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.V2_0.ProgramIdentifier();
        programIdentifierToHal(programIdentifier, identifier);
        return programIdentifier;
    }

    @android.annotation.Nullable
    static android.hardware.radio.ProgramSelector.Identifier programIdentifierFromHal(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier) {
        if (programIdentifier.type == 0) {
            return null;
        }
        return new android.hardware.radio.ProgramSelector.Identifier(programIdentifier.type, programIdentifier.value);
    }

    @android.annotation.NonNull
    static android.hardware.broadcastradio.V2_0.ProgramSelector programSelectorToHal(@android.annotation.NonNull android.hardware.radio.ProgramSelector programSelector) {
        android.hardware.broadcastradio.V2_0.ProgramSelector programSelector2 = new android.hardware.broadcastradio.V2_0.ProgramSelector();
        programIdentifierToHal(programSelector2.primaryId, programSelector.getPrimaryId());
        java.util.stream.Stream map = java.util.Arrays.stream(programSelector.getSecondaryIds()).map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.broadcastradio.hal2.Convert.programIdentifierToHal((android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        });
        final java.util.ArrayList<android.hardware.broadcastradio.V2_0.ProgramIdentifier> arrayList = programSelector2.secondaryIds;
        java.util.Objects.requireNonNull(arrayList);
        map.forEachOrdered(new java.util.function.Consumer() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((android.hardware.broadcastradio.V2_0.ProgramIdentifier) obj);
            }
        });
        return programSelector2;
    }

    private static boolean isEmpty(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.ProgramSelector programSelector) {
        return programSelector.primaryId.type == 0 && programSelector.primaryId.value == 0 && programSelector.secondaryIds.size() == 0;
    }

    @android.annotation.Nullable
    static android.hardware.radio.ProgramSelector programSelectorFromHal(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.ProgramSelector programSelector) {
        if (isEmpty(programSelector)) {
            return null;
        }
        android.hardware.radio.ProgramSelector.Identifier[] identifierArr = (android.hardware.radio.ProgramSelector.Identifier[]) programSelector.secondaryIds.stream().map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                return com.android.server.broadcastradio.hal2.Convert.programIdentifierFromHal((android.hardware.broadcastradio.V2_0.ProgramIdentifier) obj);
            }
        }).map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.radio.ProgramSelector.Identifier identifier = (android.hardware.radio.ProgramSelector.Identifier) obj;
                java.util.Objects.requireNonNull(identifier);
                return identifier;
            }
        }).toArray(new java.util.function.IntFunction() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                android.hardware.radio.ProgramSelector.Identifier[] lambda$programSelectorFromHal$2;
                lambda$programSelectorFromHal$2 = com.android.server.broadcastradio.hal2.Convert.lambda$programSelectorFromHal$2(i);
                return lambda$programSelectorFromHal$2;
            }
        });
        int identifierTypeToProgramType = identifierTypeToProgramType(programSelector.primaryId.type);
        android.hardware.radio.ProgramSelector.Identifier programIdentifierFromHal = programIdentifierFromHal(programSelector.primaryId);
        java.util.Objects.requireNonNull(programIdentifierFromHal);
        return new android.hardware.radio.ProgramSelector(identifierTypeToProgramType, programIdentifierFromHal, identifierArr, (long[]) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.radio.ProgramSelector.Identifier[] lambda$programSelectorFromHal$2(int i) {
        return new android.hardware.radio.ProgramSelector.Identifier[i];
    }

    private static class MetadataDef {
        private java.lang.String key;
        private com.android.server.broadcastradio.hal2.Convert.MetadataType type;

        private MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType metadataType, java.lang.String str) {
            this.type = metadataType;
            this.key = str;
        }
    }

    static {
        metadataKeys.put(1, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.RDS_PS"));
        metadataKeys.put(2, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.INT, "android.hardware.radio.metadata.RDS_PTY"));
        metadataKeys.put(3, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.INT, "android.hardware.radio.metadata.RBDS_PTY"));
        metadataKeys.put(4, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.RDS_RT"));
        metadataKeys.put(5, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.TITLE"));
        metadataKeys.put(6, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.ARTIST"));
        metadataKeys.put(7, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.ALBUM"));
        metadataKeys.put(8, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.INT, "android.hardware.radio.metadata.ICON"));
        metadataKeys.put(9, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.INT, "android.hardware.radio.metadata.ART"));
        metadataKeys.put(10, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.PROGRAM_NAME"));
        metadataKeys.put(11, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME"));
        metadataKeys.put(12, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT"));
        metadataKeys.put(13, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_SERVICE_NAME"));
        metadataKeys.put(14, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT"));
        metadataKeys.put(15, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_COMPONENT_NAME"));
        metadataKeys.put(16, new com.android.server.broadcastradio.hal2.Convert.MetadataDef(com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING, "android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT"));
    }

    @android.annotation.NonNull
    private static android.hardware.radio.RadioMetadata metadataFromHal(@android.annotation.NonNull java.util.ArrayList<android.hardware.broadcastradio.V2_0.Metadata> arrayList) {
        android.hardware.radio.RadioMetadata.Builder builder = new android.hardware.radio.RadioMetadata.Builder();
        java.util.Iterator<android.hardware.broadcastradio.V2_0.Metadata> it = arrayList.iterator();
        while (it.hasNext()) {
            android.hardware.broadcastradio.V2_0.Metadata next = it.next();
            com.android.server.broadcastradio.hal2.Convert.MetadataDef metadataDef = metadataKeys.get(java.lang.Integer.valueOf(next.key));
            if (metadataDef == null) {
                android.util.Slog.i(TAG, "Ignored unknown metadata entry: " + android.hardware.broadcastradio.V2_0.MetadataKey.toString(next.key));
            } else if (metadataDef.type == com.android.server.broadcastradio.hal2.Convert.MetadataType.STRING) {
                builder.putString(metadataDef.key, next.stringValue);
            } else {
                builder.putInt(metadataDef.key, (int) next.intValue);
            }
        }
        return builder.build();
    }

    @android.annotation.NonNull
    static android.hardware.radio.RadioManager.ProgramInfo programInfoFromHal(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.ProgramInfo programInfo) {
        java.util.Collection collection = (java.util.Collection) programInfo.relatedContent.stream().map(new java.util.function.Function() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.hardware.radio.ProgramSelector.Identifier lambda$programInfoFromHal$3;
                lambda$programInfoFromHal$3 = com.android.server.broadcastradio.hal2.Convert.lambda$programInfoFromHal$3((android.hardware.broadcastradio.V2_0.ProgramIdentifier) obj);
                return lambda$programInfoFromHal$3;
            }
        }).collect(java.util.stream.Collectors.toList());
        android.hardware.radio.ProgramSelector programSelectorFromHal = programSelectorFromHal(programInfo.selector);
        java.util.Objects.requireNonNull(programSelectorFromHal);
        return new android.hardware.radio.RadioManager.ProgramInfo(programSelectorFromHal, programIdentifierFromHal(programInfo.logicallyTunedTo), programIdentifierFromHal(programInfo.physicallyTunedTo), collection, programInfo.infoFlags, programInfo.signalQuality, metadataFromHal(programInfo.metadata), vendorInfoFromHal(programInfo.vendorInfo));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.radio.ProgramSelector.Identifier lambda$programInfoFromHal$3(android.hardware.broadcastradio.V2_0.ProgramIdentifier programIdentifier) {
        android.hardware.radio.ProgramSelector.Identifier programIdentifierFromHal = programIdentifierFromHal(programIdentifier);
        java.util.Objects.requireNonNull(programIdentifierFromHal);
        return programIdentifierFromHal;
    }

    @android.annotation.NonNull
    static android.hardware.broadcastradio.V2_0.ProgramFilter programFilterToHal(@android.annotation.Nullable android.hardware.radio.ProgramList.Filter filter) {
        if (filter == null) {
            filter = new android.hardware.radio.ProgramList.Filter();
        }
        final android.hardware.broadcastradio.V2_0.ProgramFilter programFilter = new android.hardware.broadcastradio.V2_0.ProgramFilter();
        java.util.stream.Stream stream = filter.getIdentifierTypes().stream();
        final java.util.ArrayList<java.lang.Integer> arrayList = programFilter.identifierTypes;
        java.util.Objects.requireNonNull(arrayList);
        stream.forEachOrdered(new java.util.function.Consumer() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                arrayList.add((java.lang.Integer) obj);
            }
        });
        filter.getIdentifiers().stream().forEachOrdered(new java.util.function.Consumer() { // from class: com.android.server.broadcastradio.hal2.Convert$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.broadcastradio.hal2.Convert.lambda$programFilterToHal$4(android.hardware.broadcastradio.V2_0.ProgramFilter.this, (android.hardware.radio.ProgramSelector.Identifier) obj);
            }
        });
        programFilter.includeCategories = filter.areCategoriesIncluded();
        programFilter.excludeModifications = filter.areModificationsExcluded();
        return programFilter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$programFilterToHal$4(android.hardware.broadcastradio.V2_0.ProgramFilter programFilter, android.hardware.radio.ProgramSelector.Identifier identifier) {
        programFilter.identifiers.add(programIdentifierToHal(identifier));
    }

    @android.annotation.NonNull
    public static android.hardware.radio.Announcement announcementFromHal(@android.annotation.NonNull android.hardware.broadcastradio.V2_0.Announcement announcement) {
        android.hardware.radio.ProgramSelector programSelectorFromHal = programSelectorFromHal(announcement.selector);
        java.util.Objects.requireNonNull(programSelectorFromHal);
        return new android.hardware.radio.Announcement(programSelectorFromHal, announcement.type, vendorInfoFromHal(announcement.vendorInfo));
    }

    @android.annotation.Nullable
    static <T> java.util.ArrayList<T> listToArrayList(@android.annotation.Nullable java.util.List<T> list) {
        if (list == null) {
            return null;
        }
        return list instanceof java.util.ArrayList ? (java.util.ArrayList) list : new java.util.ArrayList<>(list);
    }
}
