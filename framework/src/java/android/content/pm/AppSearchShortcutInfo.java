package android.content.pm;

/* loaded from: classes.dex */
public class AppSearchShortcutInfo extends android.app.appsearch.GenericDocument {
    public static final java.lang.String IS_DISABLED = "Dis";
    public static final java.lang.String IS_DYNAMIC = "Dyn";
    public static final java.lang.String IS_IMMUTABLE = "Im";
    public static final java.lang.String IS_MANIFEST = "Man";
    public static final java.lang.String KEY_ACTIVITY = "activity";
    public static final java.lang.String KEY_FLAGS = "flags";
    public static final java.lang.String KEY_ICON_RES_ID = "iconResId";
    public static final java.lang.String KEY_ICON_RES_NAME = "iconResName";
    public static final java.lang.String KEY_PERSON = "person";
    public static final java.lang.String NOT_DISABLED = "nDis";
    public static final java.lang.String NOT_DYNAMIC = "nDyn";
    public static final java.lang.String NOT_IMMUTABLE = "nIm";
    public static final java.lang.String NOT_MANIFEST = "nMan";
    public static final int SCHEMA_VERSION = 3;
    public static final long SHORTCUT_TTL = java.util.concurrent.TimeUnit.DAYS.toMillis(90);
    public static final java.lang.String SCHEMA_TYPE = "Shortcut";
    public static final java.lang.String KEY_SHORT_LABEL = "shortLabel";
    public static final java.lang.String KEY_LONG_LABEL = "longLabel";
    public static final java.lang.String KEY_DISABLED_MESSAGE = "disabledMessage";
    public static final java.lang.String KEY_CATEGORIES = "categories";
    public static final java.lang.String KEY_INTENTS = "intents";
    public static final java.lang.String KEY_INTENT_PERSISTABLE_EXTRAS = "intentPersistableExtras";
    public static final java.lang.String KEY_LOCUS_ID = "locusId";
    public static final java.lang.String KEY_EXTRAS = "extras";
    public static final java.lang.String KEY_ICON_URI = "iconUri";
    public static final java.lang.String KEY_DISABLED_REASON = "disabledReason";
    public static final java.lang.String KEY_CAPABILITY = "capability";
    public static final java.lang.String KEY_CAPABILITY_BINDINGS = "capabilityBindings";
    public static final android.app.appsearch.AppSearchSchema SCHEMA = new android.app.appsearch.AppSearchSchema.Builder(SCHEMA_TYPE).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder("activity").setCardinality(2).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_SHORT_LABEL).setCardinality(2).setTokenizerType(1).setIndexingType(2).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_LONG_LABEL).setCardinality(2).setTokenizerType(1).setIndexingType(2).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_DISABLED_MESSAGE).setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_CATEGORIES).setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_INTENTS).setCardinality(1).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.BytesPropertyConfig.Builder(KEY_INTENT_PERSISTABLE_EXTRAS).setCardinality(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.DocumentPropertyConfig.Builder("person", android.content.pm.AppSearchShortcutPerson.SCHEMA_TYPE).setCardinality(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_LOCUS_ID).setCardinality(2).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.BytesPropertyConfig.Builder(KEY_EXTRAS).setCardinality(2).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder("flags").setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.LongPropertyConfig.Builder("iconResId").setCardinality(2).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder("iconResName").setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_ICON_URI).setCardinality(2).setTokenizerType(0).setIndexingType(0).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_DISABLED_REASON).setCardinality(3).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_CAPABILITY).setCardinality(1).setTokenizerType(1).setIndexingType(1).build()).addProperty(new android.app.appsearch.AppSearchSchema.StringPropertyConfig.Builder(KEY_CAPABILITY_BINDINGS).setCardinality(1).setTokenizerType(1).setIndexingType(2).build()).build();

    public AppSearchShortcutInfo(android.app.appsearch.GenericDocument genericDocument) {
        super(genericDocument);
    }

    public static android.content.pm.AppSearchShortcutInfo instance(android.content.pm.ShortcutInfo shortcutInfo) {
        java.util.Objects.requireNonNull(shortcutInfo);
        return new android.content.pm.AppSearchShortcutInfo.Builder(shortcutInfo.getPackage(), shortcutInfo.getId()).setActivity(shortcutInfo.getActivity()).setShortLabel(shortcutInfo.getShortLabel()).setLongLabel(shortcutInfo.getLongLabel()).setDisabledMessage(shortcutInfo.getDisabledMessage()).setCategories(shortcutInfo.getCategories()).setIntents(shortcutInfo.getIntents()).setExtras(shortcutInfo.getExtras()).setCreationTimestampMillis(shortcutInfo.getLastChangedTimestamp()).setFlags(shortcutInfo.getFlags()).setIconResId(shortcutInfo.getIconResourceId()).setIconResName(shortcutInfo.getIconResName()).setIconUri(shortcutInfo.getIconUri()).setDisabledReason(shortcutInfo.getDisabledReason()).setPersons(shortcutInfo.getPersons()).setLocusId(shortcutInfo.getLocusId()).setCapabilityBindings(shortcutInfo.getCapabilityBindingsInternal()).setTtlMillis(SHORTCUT_TTL).build();
    }

    public android.content.pm.ShortcutInfo toShortcutInfo(int i) {
        android.content.Intent[] intentArr;
        android.os.Bundle[] bundleArr;
        int i2;
        java.lang.String namespace = getNamespace();
        java.lang.String propertyString = getPropertyString("activity");
        android.content.ComponentName unflattenFromString = propertyString == null ? null : android.content.ComponentName.unflattenFromString(propertyString);
        java.lang.String propertyString2 = getPropertyString(KEY_SHORT_LABEL);
        java.lang.String propertyString3 = getPropertyString(KEY_LONG_LABEL);
        java.lang.String propertyString4 = getPropertyString(KEY_DISABLED_MESSAGE);
        java.lang.String[] propertyStringArray = getPropertyStringArray(KEY_CATEGORIES);
        android.util.ArraySet arraySet = propertyStringArray == null ? null : new android.util.ArraySet(java.util.Arrays.asList(propertyStringArray));
        java.lang.String[] propertyStringArray2 = getPropertyStringArray(KEY_INTENTS);
        if (propertyStringArray2 == null) {
            intentArr = new android.content.Intent[0];
        } else {
            intentArr = (android.content.Intent[]) java.util.Arrays.stream(propertyStringArray2).map(new java.util.function.Function() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    return android.content.pm.AppSearchShortcutInfo.lambda$toShortcutInfo$0((java.lang.String) obj);
                }
            }).toArray(new java.util.function.IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda2
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i3) {
                    return android.content.pm.AppSearchShortcutInfo.lambda$toShortcutInfo$1(i3);
                }
            });
        }
        byte[][] propertyBytesArray = getPropertyBytesArray(KEY_INTENT_PERSISTABLE_EXTRAS);
        if (propertyBytesArray == null) {
            bundleArr = null;
        } else {
            bundleArr = (android.os.Bundle[]) java.util.Arrays.stream(propertyBytesArray).map(new java.util.function.Function() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    android.os.Bundle transformToBundle;
                    transformToBundle = android.content.pm.AppSearchShortcutInfo.this.transformToBundle((byte[]) obj);
                    return transformToBundle;
                }
            }).toArray(new java.util.function.IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda4
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i3) {
                    return android.content.pm.AppSearchShortcutInfo.lambda$toShortcutInfo$2(i3);
                }
            });
        }
        if (intentArr != null) {
            for (int i3 = 0; i3 < intentArr.length; i3++) {
                android.content.Intent intent = intentArr[i3];
                if (intent != null && bundleArr != null && bundleArr.length > i3 && bundleArr[i3] != null && bundleArr[i3].size() != 0) {
                    intent.replaceExtras(bundleArr[i3]);
                }
            }
        }
        android.app.Person[] parsePerson = parsePerson(getPropertyDocumentArray("person"));
        java.lang.String propertyString5 = getPropertyString(KEY_LOCUS_ID);
        android.content.LocusId locusId = propertyString5 != null ? new android.content.LocusId(propertyString5) : null;
        android.os.PersistableBundle transformToPersistableBundle = transformToPersistableBundle(getPropertyBytes(KEY_EXTRAS));
        int parseFlags = parseFlags(getPropertyStringArray("flags"));
        int propertyLong = (int) getPropertyLong("iconResId");
        java.lang.String propertyString6 = getPropertyString("iconResName");
        java.lang.String propertyString7 = getPropertyString(KEY_ICON_URI);
        if (!android.text.TextUtils.isEmpty(getPropertyString(KEY_DISABLED_REASON))) {
            i2 = java.lang.Integer.parseInt(getPropertyString(KEY_DISABLED_REASON));
        } else {
            i2 = 0;
        }
        return new android.content.pm.ShortcutInfo(i, getId(), namespace, unflattenFromString, null, propertyString2, 0, null, propertyString3, 0, null, propertyString4, 0, null, arraySet, intentArr, Integer.MAX_VALUE, transformToPersistableBundle, getCreationTimestampMillis(), parseFlags, propertyLong, propertyString6, null, propertyString7, i2, parsePerson, locusId, null, parseCapabilityBindings(getPropertyStringArray(KEY_CAPABILITY_BINDINGS)));
    }

    static /* synthetic */ android.content.Intent lambda$toShortcutInfo$0(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return new android.content.Intent("android.intent.action.VIEW");
        }
        try {
            return android.content.Intent.parseUri(str, 0);
        } catch (java.net.URISyntaxException e) {
            return null;
        }
    }

    static /* synthetic */ android.content.Intent[] lambda$toShortcutInfo$1(int i) {
        return new android.content.Intent[i];
    }

    static /* synthetic */ android.os.Bundle[] lambda$toShortcutInfo$2(int i) {
        return new android.os.Bundle[i];
    }

    public static java.util.List<android.app.appsearch.GenericDocument> toGenericDocuments(java.util.Collection<android.content.pm.ShortcutInfo> collection) {
        java.util.ArrayList arrayList = new java.util.ArrayList(collection.size());
        java.util.Iterator<android.content.pm.ShortcutInfo> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(instance(it.next()));
        }
        return arrayList;
    }

    public static class Builder extends android.app.appsearch.GenericDocument.Builder<android.content.pm.AppSearchShortcutInfo.Builder> {
        private final java.util.List<java.lang.String> mFlags;

        public Builder(java.lang.String str, java.lang.String str2) {
            super(str, str2, android.content.pm.AppSearchShortcutInfo.SCHEMA_TYPE);
            this.mFlags = new java.util.ArrayList(1);
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setLocusId(android.content.LocusId locusId) {
            if (locusId != null) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_LOCUS_ID, locusId.getId());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setActivity(android.content.ComponentName componentName) {
            if (componentName != null) {
                setPropertyString("activity", componentName.flattenToShortString());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setShortLabel(java.lang.CharSequence charSequence) {
            if (!android.text.TextUtils.isEmpty(charSequence)) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_SHORT_LABEL, com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "shortLabel cannot be empty").toString());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setLongLabel(java.lang.CharSequence charSequence) {
            if (!android.text.TextUtils.isEmpty(charSequence)) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_LONG_LABEL, com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "longLabel cannot be empty").toString());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setDisabledMessage(java.lang.CharSequence charSequence) {
            if (!android.text.TextUtils.isEmpty(charSequence)) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_DISABLED_MESSAGE, com.android.internal.util.Preconditions.checkStringNotEmpty(charSequence, "disabledMessage cannot be empty").toString());
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setCategories(java.util.Set<java.lang.String> set) {
            if (set != null && !set.isEmpty()) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_CATEGORIES, (java.lang.String[]) set.stream().toArray(new java.util.function.IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda2
                    @Override // java.util.function.IntFunction
                    public final java.lang.Object apply(int i) {
                        return android.content.pm.AppSearchShortcutInfo.Builder.lambda$setCategories$0(i);
                    }
                }));
            }
            return this;
        }

        static /* synthetic */ java.lang.String[] lambda$setCategories$0(int i) {
            return new java.lang.String[i];
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setIntent(android.content.Intent intent) {
            return intent == null ? this : setIntents(new android.content.Intent[]{intent});
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setIntents(android.content.Intent[] intentArr) {
            if (intentArr == null || intentArr.length == 0) {
                return this;
            }
            for (android.content.Intent intent : intentArr) {
                java.util.Objects.requireNonNull(intent, "intents cannot contain null");
                java.util.Objects.requireNonNull(intent.getAction(), "intent's action must be set");
            }
            byte[][] bArr = new byte[intentArr.length][];
            for (int i = 0; i < intentArr.length; i++) {
                android.os.Bundle extras = intentArr[i].getExtras();
                bArr[i] = extras == null ? new byte[0] : android.content.pm.AppSearchShortcutInfo.transformToByteArray(new android.os.PersistableBundle(extras));
            }
            setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_INTENTS, (java.lang.String[]) java.util.Arrays.stream(intentArr).map(new java.util.function.Function() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.lang.String uri;
                    uri = ((android.content.Intent) obj).toUri(0);
                    return uri;
                }
            }).toArray(new java.util.function.IntFunction() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.IntFunction
                public final java.lang.Object apply(int i2) {
                    return android.content.pm.AppSearchShortcutInfo.Builder.lambda$setIntents$2(i2);
                }
            }));
            setPropertyBytes(android.content.pm.AppSearchShortcutInfo.KEY_INTENT_PERSISTABLE_EXTRAS, bArr);
            return this;
        }

        static /* synthetic */ java.lang.String[] lambda$setIntents$2(int i) {
            return new java.lang.String[i];
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setPerson(android.app.Person person) {
            return person == null ? this : setPersons(new android.app.Person[]{person});
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setPersons(android.app.Person[] personArr) {
            if (personArr == null || personArr.length == 0) {
                return this;
            }
            android.app.appsearch.GenericDocument[] genericDocumentArr = new android.app.appsearch.GenericDocument[personArr.length];
            for (int i = 0; i < personArr.length; i++) {
                android.app.Person person = personArr[i];
                if (person != null) {
                    genericDocumentArr[i] = android.content.pm.AppSearchShortcutPerson.instance(person);
                }
            }
            setPropertyDocument("person", genericDocumentArr);
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setExtras(android.os.PersistableBundle persistableBundle) {
            if (persistableBundle != null) {
                setPropertyBytes(android.content.pm.AppSearchShortcutInfo.KEY_EXTRAS, android.content.pm.AppSearchShortcutInfo.transformToByteArray(persistableBundle));
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setFlags(int i) {
            java.lang.String[] flattenFlags = android.content.pm.AppSearchShortcutInfo.flattenFlags(i);
            if (flattenFlags != null && flattenFlags.length > 0) {
                this.mFlags.addAll(java.util.Arrays.asList(flattenFlags));
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setIconResId(int i) {
            setPropertyLong("iconResId", i);
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setIconResName(java.lang.String str) {
            if (!android.text.TextUtils.isEmpty(str)) {
                setPropertyString("iconResName", str);
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setIconUri(java.lang.String str) {
            if (!android.text.TextUtils.isEmpty(str)) {
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_ICON_URI, str);
            }
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setDisabledReason(int i) {
            setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_DISABLED_REASON, java.lang.String.valueOf(i));
            return this;
        }

        public android.content.pm.AppSearchShortcutInfo.Builder setCapabilityBindings(java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> map) {
            if (map != null && !map.isEmpty()) {
                java.util.Set<java.lang.String> keySet = map.keySet();
                final android.util.ArraySet arraySet = new android.util.ArraySet(1);
                for (final java.lang.String str : keySet) {
                    java.util.Map<java.lang.String, java.util.List<java.lang.String>> map2 = map.get(str);
                    for (final java.lang.String str2 : map2.keySet()) {
                        java.util.stream.Stream<R> map3 = map2.get(str2).stream().map(new java.util.function.Function() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda3
                            @Override // java.util.function.Function
                            public final java.lang.Object apply(java.lang.Object obj) {
                                return android.content.pm.AppSearchShortcutInfo.Builder.lambda$setCapabilityBindings$3(str, str2, (java.lang.String) obj);
                            }
                        });
                        java.util.Objects.requireNonNull(arraySet);
                        map3.forEach(new java.util.function.Consumer() { // from class: android.content.pm.AppSearchShortcutInfo$Builder$$ExternalSyntheticLambda4
                            @Override // java.util.function.Consumer
                            public final void accept(java.lang.Object obj) {
                                arraySet.add((java.lang.String) obj);
                            }
                        });
                    }
                }
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_CAPABILITY, (java.lang.String[]) keySet.toArray(new java.lang.String[0]));
                setPropertyString(android.content.pm.AppSearchShortcutInfo.KEY_CAPABILITY_BINDINGS, (java.lang.String[]) arraySet.toArray(new java.lang.String[0]));
            }
            return this;
        }

        static /* synthetic */ java.lang.String lambda$setCapabilityBindings$3(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            return str + "/" + str2 + "/" + str3;
        }

        @Override // android.app.appsearch.GenericDocument.Builder
        public android.content.pm.AppSearchShortcutInfo build() {
            setPropertyString("flags", (java.lang.String[]) this.mFlags.toArray(new java.lang.String[0]));
            return new android.content.pm.AppSearchShortcutInfo(super.build());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static byte[] transformToByteArray(android.os.PersistableBundle persistableBundle) {
        java.util.Objects.requireNonNull(persistableBundle);
        try {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            try {
                new android.os.PersistableBundle(persistableBundle).writeToStream(byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public android.os.Bundle transformToBundle(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        java.util.Objects.requireNonNull(bArr);
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putAll(android.os.PersistableBundle.readFromStream(byteArrayInputStream));
                byteArrayInputStream.close();
                return bundle;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    private android.os.PersistableBundle transformToPersistableBundle(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        try {
            java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(bArr);
            try {
                android.os.PersistableBundle readFromStream = android.os.PersistableBundle.readFromStream(byteArrayInputStream);
                byteArrayInputStream.close();
                return readFromStream;
            } finally {
            }
        } catch (java.io.IOException e) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static java.lang.String[] flattenFlags(int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i2 = 0; i2 < 31; i2++) {
            java.lang.String flagToString = flagToString(i, 1 << i2);
            if (flagToString != null) {
                arrayList.add(flagToString);
            }
        }
        return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
    }

    private static java.lang.String flagToString(int i, int i2) {
        switch (i2) {
            case 1:
                return (i & i2) != 0 ? IS_DYNAMIC : NOT_DYNAMIC;
            case 32:
                return (i & i2) != 0 ? IS_MANIFEST : NOT_MANIFEST;
            case 64:
                return (i & i2) != 0 ? IS_DISABLED : NOT_DISABLED;
            case 256:
                return (i & i2) != 0 ? IS_IMMUTABLE : NOT_IMMUTABLE;
            default:
                return null;
        }
    }

    private static int parseFlags(java.lang.String[] strArr) {
        if (strArr == null) {
            return 0;
        }
        int i = 0;
        for (java.lang.String str : strArr) {
            i |= parseFlag(str);
        }
        return i;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int parseFlag(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case 2372:
                if (str.equals(IS_IMMUTABLE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 68718:
                if (str.equals(IS_DISABLED)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 69209:
                if (str.equals(IS_DYNAMIC)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 77114:
                if (str.equals(IS_MANIFEST)) {
                    c = 1;
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
                return 1;
            case 1:
                return 32;
            case 2:
                return 64;
            case 3:
                return 256;
            default:
                return 0;
        }
    }

    private static android.app.Person[] parsePerson(android.app.appsearch.GenericDocument[] genericDocumentArr) {
        if (genericDocumentArr == null) {
            return new android.app.Person[0];
        }
        android.app.Person[] personArr = new android.app.Person[genericDocumentArr.length];
        for (int i = 0; i < genericDocumentArr.length; i++) {
            android.app.appsearch.GenericDocument genericDocument = genericDocumentArr[i];
            if (genericDocument != null) {
                personArr[i] = new android.content.pm.AppSearchShortcutPerson(genericDocument).toPerson();
            }
        }
        return personArr;
    }

    private static java.util.Map<java.lang.String, java.util.Map<java.lang.String, java.util.List<java.lang.String>>> parseCapabilityBindings(java.lang.String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        final android.util.ArrayMap arrayMap = new android.util.ArrayMap(1);
        java.util.Arrays.stream(strArr).forEach(new java.util.function.Consumer() { // from class: android.content.pm.AppSearchShortcutInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                android.content.pm.AppSearchShortcutInfo.lambda$parseCapabilityBindings$3(arrayMap, (java.lang.String) obj);
            }
        });
        return arrayMap;
    }

    static /* synthetic */ void lambda$parseCapabilityBindings$3(java.util.Map map, java.lang.String str) {
        int indexOf;
        if (android.text.TextUtils.isEmpty(str) || (indexOf = str.indexOf("/")) == -1 || indexOf == str.length() - 1) {
            return;
        }
        java.lang.String substring = str.substring(0, indexOf);
        int i = indexOf + 1;
        int indexOf2 = str.indexOf("/", i);
        if (indexOf2 == -1 || indexOf2 == str.length() - 1) {
            return;
        }
        java.lang.String substring2 = str.substring(i, indexOf2);
        java.lang.String substring3 = str.substring(indexOf2 + 1);
        if (!map.containsKey(substring)) {
            map.put(substring, new android.util.ArrayMap(1));
        }
        java.util.Map map2 = (java.util.Map) map.get(substring);
        if (!map2.containsKey(substring2)) {
            map2.put(substring2, new java.util.ArrayList(1));
        }
        ((java.util.List) map2.get(substring2)).add(substring3);
    }
}
