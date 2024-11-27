package com.android.server.broadcastradio.aidl;

/* loaded from: classes.dex */
final class ConversionUtils {
    public static final long RADIO_U_VERSION_REQUIRED = 261770108;
    public static final long RADIO_V_VERSION_REQUIRED = 302589903;
    private static final java.lang.String TAG = "BcRadioAidlSrv.convert";

    private ConversionUtils() {
        throw new java.lang.UnsupportedOperationException("ConversionUtils class is noninstantiable");
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    static boolean isAtLeastU(int i) {
        return android.app.compat.CompatChanges.isChangeEnabled(RADIO_U_VERSION_REQUIRED, i);
    }

    @android.annotation.SuppressLint({"AndroidFrameworkRequiresPermission"})
    static boolean isAtLeastV(int i) {
        return android.app.compat.CompatChanges.isChangeEnabled(RADIO_V_VERSION_REQUIRED, i);
    }

    static java.lang.RuntimeException throwOnError(java.lang.RuntimeException runtimeException, java.lang.String str) {
        if (!(runtimeException instanceof android.os.ServiceSpecificException)) {
            return new android.os.ParcelableException(new java.lang.RuntimeException(str + ": unknown error"));
        }
        int i = ((android.os.ServiceSpecificException) runtimeException).errorCode;
        switch (i) {
            case 1:
                return new android.os.ParcelableException(new java.lang.RuntimeException(str + ": INTERNAL_ERROR"));
            case 2:
                return new java.lang.IllegalArgumentException(str + ": INVALID_ARGUMENTS");
            case 3:
                return new java.lang.IllegalStateException(str + ": INVALID_STATE");
            case 4:
                return new java.lang.UnsupportedOperationException(str + ": NOT_SUPPORTED");
            case 5:
                return new android.os.ParcelableException(new java.lang.RuntimeException(str + ": TIMEOUT"));
            case 6:
                return new java.lang.IllegalStateException(str + ": CANCELED");
            case 7:
                return new android.os.ParcelableException(new java.lang.RuntimeException(str + ": UNKNOWN_ERROR"));
            default:
                return new android.os.ParcelableException(new java.lang.RuntimeException(str + ": unknown error (" + i + ")"));
        }
    }

    static int halResultToTunerResult(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return 7;
        }
    }

    static android.hardware.broadcastradio.VendorKeyValue[] vendorInfoToHalVendorKeyValues(@android.annotation.Nullable java.util.Map<java.lang.String, java.lang.String> map) {
        if (map == null) {
            return new android.hardware.broadcastradio.VendorKeyValue[0];
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            android.hardware.broadcastradio.VendorKeyValue vendorKeyValue = new android.hardware.broadcastradio.VendorKeyValue();
            vendorKeyValue.key = entry.getKey();
            vendorKeyValue.value = entry.getValue();
            if (vendorKeyValue.key == null || vendorKeyValue.value == null) {
                com.android.server.utils.Slogf.w(TAG, "VendorKeyValue contains invalid entry: key = %s, value = %s", vendorKeyValue.key, vendorKeyValue.value);
            } else {
                arrayList.add(vendorKeyValue);
            }
        }
        return (android.hardware.broadcastradio.VendorKeyValue[]) arrayList.toArray(new java.util.function.IntFunction() { // from class: com.android.server.broadcastradio.aidl.ConversionUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                android.hardware.broadcastradio.VendorKeyValue[] lambda$vendorInfoToHalVendorKeyValues$0;
                lambda$vendorInfoToHalVendorKeyValues$0 = com.android.server.broadcastradio.aidl.ConversionUtils.lambda$vendorInfoToHalVendorKeyValues$0(i);
                return lambda$vendorInfoToHalVendorKeyValues$0;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.broadcastradio.VendorKeyValue[] lambda$vendorInfoToHalVendorKeyValues$0(int i) {
        return new android.hardware.broadcastradio.VendorKeyValue[i];
    }

    static java.util.Map<java.lang.String, java.lang.String> vendorInfoFromHalVendorKeyValues(@android.annotation.Nullable android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) {
        if (vendorKeyValueArr == null) {
            return java.util.Collections.emptyMap();
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (android.hardware.broadcastradio.VendorKeyValue vendorKeyValue : vendorKeyValueArr) {
            if (vendorKeyValue.key == null || vendorKeyValue.value == null) {
                com.android.server.utils.Slogf.w(TAG, "VendorKeyValue contains invalid entry: key = %s, value = %s", vendorKeyValue.key, vendorKeyValue.value);
            } else {
                arrayMap.put(vendorKeyValue.key, vendorKeyValue.value);
            }
        }
        return arrayMap;
    }

    private static int identifierTypeToProgramType(int i) {
        switch (i) {
            case 1:
            case 2:
                return 2;
            case 3:
            case com.android.internal.util.FrameworkStatsLog.KERNEL_WAKELOCK /* 10004 */:
                return 4;
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
            default:
                if (android.hardware.radio.Flags.hdRadioImproved() && i == 15) {
                    return 4;
                }
                if (i >= 1000 && i <= 1999) {
                    return i;
                }
                return 0;
        }
    }

    private static int[] identifierTypesToProgramTypes(int[] iArr) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        int i = 0;
        for (int i2 : iArr) {
            int identifierTypeToProgramType = identifierTypeToProgramType(i2);
            if (identifierTypeToProgramType != 0) {
                arraySet.add(java.lang.Integer.valueOf(identifierTypeToProgramType));
                if (identifierTypeToProgramType == 2) {
                    arraySet.add(1);
                }
                if (identifierTypeToProgramType == 4) {
                    arraySet.add(3);
                }
            }
        }
        int[] iArr2 = new int[arraySet.size()];
        java.util.Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            iArr2[i] = ((java.lang.Integer) it.next()).intValue();
            i++;
        }
        return iArr2;
    }

    private static android.hardware.radio.RadioManager.BandDescriptor[] amfmConfigToBands(@android.annotation.Nullable android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig) {
        if (amFmRegionConfig == null) {
            return new android.hardware.radio.RadioManager.BandDescriptor[0];
        }
        int length = amFmRegionConfig.ranges.length;
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < length; i++) {
            com.android.server.broadcastradio.aidl.Utils.FrequencyBand band = com.android.server.broadcastradio.aidl.Utils.getBand(amFmRegionConfig.ranges[i].lowerBound);
            if (band == com.android.server.broadcastradio.aidl.Utils.FrequencyBand.UNKNOWN) {
                com.android.server.utils.Slogf.e(TAG, "Unknown frequency band at %d kHz", java.lang.Integer.valueOf(amFmRegionConfig.ranges[i].lowerBound));
            } else if (band == com.android.server.broadcastradio.aidl.Utils.FrequencyBand.FM) {
                arrayList.add(new android.hardware.radio.RadioManager.FmBandDescriptor(0, 1, amFmRegionConfig.ranges[i].lowerBound, amFmRegionConfig.ranges[i].upperBound, amFmRegionConfig.ranges[i].spacing, true, true, true, true, true));
            } else {
                arrayList.add(new android.hardware.radio.RadioManager.AmBandDescriptor(0, 0, amFmRegionConfig.ranges[i].lowerBound, amFmRegionConfig.ranges[i].upperBound, amFmRegionConfig.ranges[i].spacing, true));
            }
        }
        return (android.hardware.radio.RadioManager.BandDescriptor[]) arrayList.toArray(new java.util.function.IntFunction() { // from class: com.android.server.broadcastradio.aidl.ConversionUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                android.hardware.radio.RadioManager.BandDescriptor[] lambda$amfmConfigToBands$1;
                lambda$amfmConfigToBands$1 = com.android.server.broadcastradio.aidl.ConversionUtils.lambda$amfmConfigToBands$1(i2);
                return lambda$amfmConfigToBands$1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.radio.RadioManager.BandDescriptor[] lambda$amfmConfigToBands$1(int i) {
        return new android.hardware.radio.RadioManager.BandDescriptor[i];
    }

    @android.annotation.Nullable
    private static java.util.Map<java.lang.String, java.lang.Integer> dabConfigFromHalDabTableEntries(@android.annotation.Nullable android.hardware.broadcastradio.DabTableEntry[] dabTableEntryArr) {
        if (dabTableEntryArr == null) {
            return null;
        }
        android.util.ArrayMap arrayMap = new android.util.ArrayMap();
        for (int i = 0; i < dabTableEntryArr.length; i++) {
            arrayMap.put(dabTableEntryArr[i].label, java.lang.Integer.valueOf(dabTableEntryArr[i].frequencyKhz));
        }
        return arrayMap;
    }

    static android.hardware.radio.RadioManager.ModuleProperties propertiesFromHalProperties(int i, java.lang.String str, android.hardware.broadcastradio.Properties properties, @android.annotation.Nullable android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig, @android.annotation.Nullable android.hardware.broadcastradio.DabTableEntry[] dabTableEntryArr) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(properties);
        return new android.hardware.radio.RadioManager.ModuleProperties(i, str, 0, properties.maker, properties.product, properties.version, properties.serial, 1, 1, false, false, amfmConfigToBands(amFmRegionConfig), true, identifierTypesToProgramTypes(properties.supportedIdentifierTypes), properties.supportedIdentifierTypes, dabConfigFromHalDabTableEntries(dabTableEntryArr), vendorInfoFromHalVendorKeyValues(properties.vendorInfo));
    }

    static android.hardware.broadcastradio.ProgramIdentifier identifierToHalProgramIdentifier(android.hardware.radio.ProgramSelector.Identifier identifier) {
        android.hardware.broadcastradio.ProgramIdentifier programIdentifier = new android.hardware.broadcastradio.ProgramIdentifier();
        if (identifier.getType() == 14) {
            programIdentifier.type = 5;
        } else if (android.hardware.radio.Flags.hdRadioImproved()) {
            if (identifier.getType() == 15) {
                programIdentifier.type = 14;
            } else {
                programIdentifier.type = identifier.getType();
            }
        } else {
            programIdentifier.type = identifier.getType();
        }
        long value = identifier.getValue();
        if (identifier.getType() == 5) {
            programIdentifier.value = (65535 & value) | ((value >>> 16) << 32);
        } else {
            programIdentifier.value = value;
        }
        return programIdentifier;
    }

    @android.annotation.Nullable
    static android.hardware.radio.ProgramSelector.Identifier identifierFromHalProgramIdentifier(android.hardware.broadcastradio.ProgramIdentifier programIdentifier) {
        if (programIdentifier.type == 0) {
            return null;
        }
        int i = 14;
        if (programIdentifier.type != 5) {
            if (programIdentifier.type == 14) {
                if (!android.hardware.radio.Flags.hdRadioImproved()) {
                    return null;
                }
                i = 15;
            } else {
                i = programIdentifier.type;
            }
        }
        return new android.hardware.radio.ProgramSelector.Identifier(i, programIdentifier.value);
    }

    private static boolean isVendorIdentifierType(int i) {
        return i >= 1000 && i <= 1999;
    }

    private static boolean isValidHalProgramSelector(android.hardware.broadcastradio.ProgramSelector programSelector) {
        return programSelector.primaryId.type == 1 || programSelector.primaryId.type == 2 || programSelector.primaryId.type == 3 || programSelector.primaryId.type == 5 || programSelector.primaryId.type == 9 || programSelector.primaryId.type == 12 || isVendorIdentifierType(programSelector.primaryId.type);
    }

    @android.annotation.Nullable
    static android.hardware.broadcastradio.ProgramSelector programSelectorToHalProgramSelector(android.hardware.radio.ProgramSelector programSelector) {
        android.hardware.broadcastradio.ProgramSelector programSelector2 = new android.hardware.broadcastradio.ProgramSelector();
        programSelector2.primaryId = identifierToHalProgramIdentifier(programSelector.getPrimaryId());
        android.hardware.radio.ProgramSelector.Identifier[] secondaryIds = programSelector.getSecondaryIds();
        java.util.ArrayList arrayList = new java.util.ArrayList(secondaryIds.length);
        for (int i = 0; i < secondaryIds.length; i++) {
            android.hardware.broadcastradio.ProgramIdentifier identifierToHalProgramIdentifier = identifierToHalProgramIdentifier(secondaryIds[i]);
            if (identifierToHalProgramIdentifier.type != 0) {
                arrayList.add(identifierToHalProgramIdentifier);
            } else {
                com.android.server.utils.Slogf.w(TAG, "Invalid secondary id: %s", secondaryIds[i]);
            }
        }
        programSelector2.secondaryIds = (android.hardware.broadcastradio.ProgramIdentifier[]) arrayList.toArray(new java.util.function.IntFunction() { // from class: com.android.server.broadcastradio.aidl.ConversionUtils$$ExternalSyntheticLambda2
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i2) {
                android.hardware.broadcastradio.ProgramIdentifier[] lambda$programSelectorToHalProgramSelector$2;
                lambda$programSelectorToHalProgramSelector$2 = com.android.server.broadcastradio.aidl.ConversionUtils.lambda$programSelectorToHalProgramSelector$2(i2);
                return lambda$programSelectorToHalProgramSelector$2;
            }
        });
        if (!isValidHalProgramSelector(programSelector2)) {
            return null;
        }
        return programSelector2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.broadcastradio.ProgramIdentifier[] lambda$programSelectorToHalProgramSelector$2(int i) {
        return new android.hardware.broadcastradio.ProgramIdentifier[i];
    }

    private static boolean isEmpty(android.hardware.broadcastradio.ProgramSelector programSelector) {
        return programSelector.primaryId.type == 0 && programSelector.primaryId.value == 0 && programSelector.secondaryIds.length == 0;
    }

    @android.annotation.Nullable
    static android.hardware.radio.ProgramSelector programSelectorFromHalProgramSelector(android.hardware.broadcastradio.ProgramSelector programSelector) {
        if (isEmpty(programSelector) || !isValidHalProgramSelector(programSelector)) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < programSelector.secondaryIds.length; i++) {
            if (programSelector.secondaryIds[i] != null) {
                android.hardware.radio.ProgramSelector.Identifier identifierFromHalProgramIdentifier = identifierFromHalProgramIdentifier(programSelector.secondaryIds[i]);
                if (identifierFromHalProgramIdentifier == null) {
                    com.android.server.utils.Slogf.e(TAG, "invalid secondary id: %s", programSelector.secondaryIds[i]);
                }
                arrayList.add(identifierFromHalProgramIdentifier);
            }
        }
        int identifierTypeToProgramType = identifierTypeToProgramType(programSelector.primaryId.type);
        android.hardware.radio.ProgramSelector.Identifier identifierFromHalProgramIdentifier2 = identifierFromHalProgramIdentifier(programSelector.primaryId);
        java.util.Objects.requireNonNull(identifierFromHalProgramIdentifier2);
        return new android.hardware.radio.ProgramSelector(identifierTypeToProgramType, identifierFromHalProgramIdentifier2, (android.hardware.radio.ProgramSelector.Identifier[]) arrayList.toArray(new android.hardware.radio.ProgramSelector.Identifier[0]), (long[]) null);
    }

    @com.android.internal.annotations.VisibleForTesting
    static android.hardware.radio.RadioMetadata radioMetadataFromHalMetadata(android.hardware.broadcastradio.Metadata[] metadataArr) {
        android.hardware.radio.RadioMetadata.Builder builder = new android.hardware.radio.RadioMetadata.Builder();
        for (int i = 0; i < metadataArr.length; i++) {
            int tag = metadataArr[i].getTag();
            switch (tag) {
                case 0:
                    builder.putString("android.hardware.radio.metadata.RDS_PS", metadataArr[i].getRdsPs());
                    break;
                case 1:
                    builder.putInt("android.hardware.radio.metadata.RDS_PTY", metadataArr[i].getRdsPty());
                    break;
                case 2:
                    builder.putInt("android.hardware.radio.metadata.RBDS_PTY", metadataArr[i].getRbdsPty());
                    break;
                case 3:
                    builder.putString("android.hardware.radio.metadata.RDS_RT", metadataArr[i].getRdsRt());
                    break;
                case 4:
                    builder.putString("android.hardware.radio.metadata.TITLE", metadataArr[i].getSongTitle());
                    break;
                case 5:
                    builder.putString("android.hardware.radio.metadata.ARTIST", metadataArr[i].getSongArtist());
                    break;
                case 6:
                    builder.putString("android.hardware.radio.metadata.ALBUM", metadataArr[i].getSongAlbum());
                    break;
                case 7:
                    builder.putInt("android.hardware.radio.metadata.ICON", metadataArr[i].getStationIcon());
                    break;
                case 8:
                    builder.putInt("android.hardware.radio.metadata.ART", metadataArr[i].getAlbumArt());
                    break;
                case 9:
                    builder.putString("android.hardware.radio.metadata.PROGRAM_NAME", metadataArr[i].getProgramName());
                    break;
                case 10:
                    builder.putString("android.hardware.radio.metadata.DAB_ENSEMBLE_NAME", metadataArr[i].getDabEnsembleName());
                    break;
                case 11:
                    builder.putString("android.hardware.radio.metadata.DAB_ENSEMBLE_NAME_SHORT", metadataArr[i].getDabEnsembleNameShort());
                    break;
                case 12:
                    builder.putString("android.hardware.radio.metadata.DAB_SERVICE_NAME", metadataArr[i].getDabServiceName());
                    break;
                case 13:
                    builder.putString("android.hardware.radio.metadata.DAB_SERVICE_NAME_SHORT", metadataArr[i].getDabServiceNameShort());
                    break;
                case 14:
                    builder.putString("android.hardware.radio.metadata.DAB_COMPONENT_NAME", metadataArr[i].getDabComponentName());
                    break;
                case 15:
                    builder.putString("android.hardware.radio.metadata.DAB_COMPONENT_NAME_SHORT", metadataArr[i].getDabComponentNameShort());
                    break;
                default:
                    if (android.hardware.radio.Flags.hdRadioImproved()) {
                        switch (tag) {
                            case 16:
                                builder.putString("android.hardware.radio.metadata.GENRE", metadataArr[i].getGenre());
                                break;
                            case 17:
                                builder.putString("android.hardware.radio.metadata.COMMENT_SHORT_DESCRIPTION", metadataArr[i].getCommentShortDescription());
                                break;
                            case 18:
                                builder.putString("android.hardware.radio.metadata.COMMENT_ACTUAL_TEXT", metadataArr[i].getCommentActualText());
                                break;
                            case 19:
                                builder.putString("android.hardware.radio.metadata.COMMERCIAL", metadataArr[i].getCommercial());
                                break;
                            case 20:
                                builder.putStringArray("android.hardware.radio.metadata.UFIDS", metadataArr[i].getUfids());
                                break;
                            case 21:
                                builder.putString("android.hardware.radio.metadata.HD_STATION_NAME_SHORT", metadataArr[i].getHdStationNameShort());
                                break;
                            case 22:
                                builder.putString("android.hardware.radio.metadata.HD_STATION_NAME_LONG", metadataArr[i].getHdStationNameLong());
                                break;
                            case 23:
                                builder.putInt("android.hardware.radio.metadata.HD_SUBCHANNELS_AVAILABLE", metadataArr[i].getHdSubChannelsAvailable());
                                break;
                            default:
                                com.android.server.utils.Slogf.w(TAG, "Ignored unknown metadata entry: %s with HD radio flag enabled", metadataArr[i]);
                                break;
                        }
                    } else {
                        com.android.server.utils.Slogf.w(TAG, "Ignored unknown metadata entry: %s with HD radio flag disabled", metadataArr[i]);
                        break;
                    }
            }
        }
        return builder.build();
    }

    private static boolean isValidLogicallyTunedTo(android.hardware.broadcastradio.ProgramIdentifier programIdentifier) {
        return programIdentifier.type == 1 || programIdentifier.type == 2 || programIdentifier.type == 3 || programIdentifier.type == 5 || programIdentifier.type == 9 || programIdentifier.type == 12 || isVendorIdentifierType(programIdentifier.type);
    }

    private static boolean isValidPhysicallyTunedTo(android.hardware.broadcastradio.ProgramIdentifier programIdentifier) {
        return programIdentifier.type == 1 || programIdentifier.type == 8 || programIdentifier.type == 10 || programIdentifier.type == 13 || isVendorIdentifierType(programIdentifier.type);
    }

    private static boolean isValidHalProgramInfo(android.hardware.broadcastradio.ProgramInfo programInfo) {
        return isValidHalProgramSelector(programInfo.selector) && isValidLogicallyTunedTo(programInfo.logicallyTunedTo) && isValidPhysicallyTunedTo(programInfo.physicallyTunedTo);
    }

    @android.annotation.Nullable
    static android.hardware.radio.RadioManager.ProgramInfo programInfoFromHalProgramInfo(android.hardware.broadcastradio.ProgramInfo programInfo) {
        if (!isValidHalProgramInfo(programInfo)) {
            return null;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (programInfo.relatedContent != null) {
            for (int i = 0; i < programInfo.relatedContent.length; i++) {
                android.hardware.radio.ProgramSelector.Identifier identifierFromHalProgramIdentifier = identifierFromHalProgramIdentifier(programInfo.relatedContent[i]);
                if (identifierFromHalProgramIdentifier != null) {
                    arrayList.add(identifierFromHalProgramIdentifier);
                }
            }
        }
        android.hardware.radio.ProgramSelector programSelectorFromHalProgramSelector = programSelectorFromHalProgramSelector(programInfo.selector);
        java.util.Objects.requireNonNull(programSelectorFromHalProgramSelector);
        return new android.hardware.radio.RadioManager.ProgramInfo(programSelectorFromHalProgramSelector, identifierFromHalProgramIdentifier(programInfo.logicallyTunedTo), identifierFromHalProgramIdentifier(programInfo.physicallyTunedTo), arrayList, programInfo.infoFlags, programInfo.signalQuality, radioMetadataFromHalMetadata(programInfo.metadata), vendorInfoFromHalVendorKeyValues(programInfo.vendorInfo));
    }

    static android.hardware.broadcastradio.ProgramFilter filterToHalProgramFilter(@android.annotation.Nullable android.hardware.radio.ProgramList.Filter filter) {
        if (filter == null) {
            filter = new android.hardware.radio.ProgramList.Filter();
        }
        android.hardware.broadcastradio.ProgramFilter programFilter = new android.hardware.broadcastradio.ProgramFilter();
        android.util.IntArray intArray = new android.util.IntArray(filter.getIdentifierTypes().size());
        java.util.ArrayList arrayList = new java.util.ArrayList();
        java.util.Iterator it = filter.getIdentifierTypes().iterator();
        while (it.hasNext()) {
            intArray.add(((java.lang.Integer) it.next()).intValue());
        }
        for (android.hardware.radio.ProgramSelector.Identifier identifier : filter.getIdentifiers()) {
            android.hardware.broadcastradio.ProgramIdentifier identifierToHalProgramIdentifier = identifierToHalProgramIdentifier(identifier);
            if (identifierToHalProgramIdentifier.type != 0) {
                arrayList.add(identifierToHalProgramIdentifier);
            } else {
                com.android.server.utils.Slogf.w(TAG, "Invalid identifiers: %s", identifier);
            }
        }
        programFilter.identifierTypes = intArray.toArray();
        programFilter.identifiers = (android.hardware.broadcastradio.ProgramIdentifier[]) arrayList.toArray(new java.util.function.IntFunction() { // from class: com.android.server.broadcastradio.aidl.ConversionUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                android.hardware.broadcastradio.ProgramIdentifier[] lambda$filterToHalProgramFilter$3;
                lambda$filterToHalProgramFilter$3 = com.android.server.broadcastradio.aidl.ConversionUtils.lambda$filterToHalProgramFilter$3(i);
                return lambda$filterToHalProgramFilter$3;
            }
        });
        programFilter.includeCategories = filter.areCategoriesIncluded();
        programFilter.excludeModifications = filter.areModificationsExcluded();
        return programFilter;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.broadcastradio.ProgramIdentifier[] lambda$filterToHalProgramFilter$3(int i) {
        return new android.hardware.broadcastradio.ProgramIdentifier[i];
    }

    private static boolean identifierMeetsSdkVersionRequirement(android.hardware.radio.ProgramSelector.Identifier identifier, int i) {
        if (android.hardware.radio.Flags.hdRadioImproved() && !isAtLeastV(i) && identifier.getType() == 15) {
            return false;
        }
        return isAtLeastU(i) || identifier.getType() != 14;
    }

    static boolean programSelectorMeetsSdkVersionRequirement(android.hardware.radio.ProgramSelector programSelector, int i) {
        if (!identifierMeetsSdkVersionRequirement(programSelector.getPrimaryId(), i)) {
            return false;
        }
        for (android.hardware.radio.ProgramSelector.Identifier identifier : programSelector.getSecondaryIds()) {
            if (!identifierMeetsSdkVersionRequirement(identifier, i)) {
                return false;
            }
        }
        return true;
    }

    static boolean programInfoMeetsSdkVersionRequirement(android.hardware.radio.RadioManager.ProgramInfo programInfo, int i) {
        if (!programSelectorMeetsSdkVersionRequirement(programInfo.getSelector(), i) || !identifierMeetsSdkVersionRequirement(programInfo.getLogicallyTunedTo(), i) || !identifierMeetsSdkVersionRequirement(programInfo.getPhysicallyTunedTo(), i)) {
            return false;
        }
        if (programInfo.getRelatedContent() == null) {
            return true;
        }
        java.util.Iterator it = programInfo.getRelatedContent().iterator();
        while (it.hasNext()) {
            if (!identifierMeetsSdkVersionRequirement((android.hardware.radio.ProgramSelector.Identifier) it.next(), i)) {
                return false;
            }
        }
        return true;
    }

    static android.hardware.radio.ProgramList.Chunk convertChunkToTargetSdkVersion(android.hardware.radio.ProgramList.Chunk chunk, int i) {
        android.util.ArraySet arraySet = new android.util.ArraySet();
        for (android.hardware.radio.RadioManager.ProgramInfo programInfo : chunk.getModified()) {
            if (programInfoMeetsSdkVersionRequirement(programInfo, i)) {
                arraySet.add(programInfo);
            }
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (android.hardware.radio.UniqueProgramIdentifier uniqueProgramIdentifier : chunk.getRemoved()) {
            if (identifierMeetsSdkVersionRequirement(uniqueProgramIdentifier.getPrimaryId(), i)) {
                arraySet2.add(uniqueProgramIdentifier);
            }
        }
        return new android.hardware.radio.ProgramList.Chunk(chunk.isPurge(), chunk.isComplete(), arraySet, arraySet2);
    }

    static boolean configFlagMeetsSdkVersionRequirement(int i, int i2) {
        if (android.hardware.radio.Flags.hdRadioImproved() && isAtLeastV(i2)) {
            return true;
        }
        return (i == 11 || i == 10) ? false : true;
    }

    public static android.hardware.radio.Announcement announcementFromHalAnnouncement(android.hardware.broadcastradio.Announcement announcement) {
        android.hardware.radio.ProgramSelector programSelectorFromHalProgramSelector = programSelectorFromHalProgramSelector(announcement.selector);
        java.util.Objects.requireNonNull(programSelectorFromHalProgramSelector, "Program selector can not be null");
        return new android.hardware.radio.Announcement(programSelectorFromHalProgramSelector, announcement.type, vendorInfoFromHalVendorKeyValues(announcement.vendorInfo));
    }
}
