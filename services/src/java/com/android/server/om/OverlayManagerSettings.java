package com.android.server.om;

/* loaded from: classes2.dex */
final class OverlayManagerSettings {
    private final java.util.ArrayList<com.android.server.om.OverlayManagerSettings.SettingsItem> mItems = new java.util.ArrayList<>();

    OverlayManagerSettings() {
    }

    @android.annotation.NonNull
    android.content.om.OverlayInfo init(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3, boolean z, boolean z2, int i2, @android.annotation.Nullable java.lang.String str4, boolean z3) {
        remove(overlayIdentifier, i);
        com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = new com.android.server.om.OverlayManagerSettings.SettingsItem(overlayIdentifier, i, str, str2, str3, -1, z2, z, i2, str4, z3);
        insert(settingsItem);
        return settingsItem.getOverlayInfo();
    }

    boolean remove(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            return false;
        }
        this.mItems.remove(select);
        return true;
    }

    @android.annotation.NonNull
    android.content.om.OverlayInfo getOverlayInfo(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).getOverlayInfo();
    }

    @android.annotation.Nullable
    android.content.om.OverlayInfo getNullableOverlayInfo(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            return null;
        }
        return this.mItems.get(select).getOverlayInfo();
    }

    boolean setBaseCodePath(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, @android.annotation.NonNull java.lang.String str) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).setBaseCodePath(str);
    }

    boolean setCategory(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, @android.annotation.Nullable java.lang.String str) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).setCategory(str);
    }

    boolean getEnabled(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).isEnabled();
    }

    boolean setEnabled(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, boolean z) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).setEnabled(z);
    }

    int getState(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).getState();
    }

    boolean setState(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, int i2) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        return this.mItems.get(select).setState(i2);
    }

    java.util.List<android.content.om.OverlayInfo> getOverlaysForTarget(@android.annotation.NonNull java.lang.String str, int i) {
        return com.android.internal.util.CollectionUtils.map(selectWhereTarget(str, i), new java.util.function.Function() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final java.lang.Object apply(java.lang.Object obj) {
                android.content.om.OverlayInfo overlayInfo;
                overlayInfo = ((com.android.server.om.OverlayManagerSettings.SettingsItem) obj).getOverlayInfo();
                return overlayInfo;
            }
        });
    }

    void forEachMatching(int i, java.lang.String str, java.lang.String str2, @android.annotation.NonNull java.util.function.Consumer<android.content.om.OverlayInfo> consumer) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(i2);
            if (settingsItem.getUserId() == i && ((str == null || settingsItem.mOverlay.getPackageName().equals(str)) && (str2 == null || settingsItem.mTargetPackageName.equals(str2)))) {
                consumer.accept(settingsItem.getOverlayInfo());
            }
        }
    }

    android.util.ArrayMap<java.lang.String, java.util.List<android.content.om.OverlayInfo>> getOverlaysForUser(int i) {
        java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereUser = selectWhereUser(i);
        android.util.ArrayMap<java.lang.String, java.util.List<android.content.om.OverlayInfo>> arrayMap = new android.util.ArrayMap<>();
        int size = selectWhereUser.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = selectWhereUser.get(i2);
            arrayMap.computeIfAbsent(settingsItem.mTargetPackageName, new java.util.function.Function() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda13
                @Override // java.util.function.Function
                public final java.lang.Object apply(java.lang.Object obj) {
                    java.util.List lambda$getOverlaysForUser$0;
                    lambda$getOverlaysForUser$0 = com.android.server.om.OverlayManagerSettings.lambda$getOverlaysForUser$0((java.lang.String) obj);
                    return lambda$getOverlaysForUser$0;
                }
            }).add(settingsItem.getOverlayInfo());
        }
        return arrayMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ java.util.List lambda$getOverlaysForUser$0(java.lang.String str) {
        return new java.util.ArrayList();
    }

    java.util.Set<java.lang.String> getAllBaseCodePaths() {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        this.mItems.forEach(new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.om.OverlayManagerSettings.lambda$getAllBaseCodePaths$1(arraySet, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
            }
        });
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAllBaseCodePaths$1(java.util.Set set, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        set.add(settingsItem.mBaseCodePath);
    }

    java.util.Set<android.util.Pair<android.content.om.OverlayIdentifier, java.lang.String>> getAllIdentifiersAndBaseCodePaths() {
        final android.util.ArraySet arraySet = new android.util.ArraySet();
        this.mItems.forEach(new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.om.OverlayManagerSettings.lambda$getAllIdentifiersAndBaseCodePaths$2(arraySet, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
            }
        });
        return arraySet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAllIdentifiersAndBaseCodePaths$2(java.util.Set set, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        set.add(new android.util.Pair(settingsItem.mOverlay, settingsItem.mBaseCodePath));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeIf$3(java.util.function.Predicate predicate, int i, android.content.om.OverlayInfo overlayInfo) {
        return predicate.test(overlayInfo) && overlayInfo.userId == i;
    }

    @android.annotation.NonNull
    java.util.List<android.content.om.OverlayInfo> removeIf(@android.annotation.NonNull final java.util.function.Predicate<android.content.om.OverlayInfo> predicate, final int i) {
        return removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeIf$3;
                lambda$removeIf$3 = com.android.server.om.OverlayManagerSettings.lambda$removeIf$3(predicate, i, (android.content.om.OverlayInfo) obj);
                return lambda$removeIf$3;
            }
        });
    }

    @android.annotation.NonNull
    java.util.List<android.content.om.OverlayInfo> removeIf(@android.annotation.NonNull java.util.function.Predicate<android.content.om.OverlayInfo> predicate) {
        java.util.List list = null;
        for (int size = this.mItems.size() - 1; size >= 0; size--) {
            android.content.om.OverlayInfo overlayInfo = this.mItems.get(size).getOverlayInfo();
            if (predicate.test(overlayInfo)) {
                this.mItems.remove(size);
                list = com.android.internal.util.CollectionUtils.add(list, overlayInfo);
            }
        }
        return com.android.internal.util.CollectionUtils.emptyIfNull(list);
    }

    int[] getUsers() {
        return this.mItems.stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda11
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                int userId;
                userId = ((com.android.server.om.OverlayManagerSettings.SettingsItem) obj).getUserId();
                return userId;
            }
        }).distinct().toArray();
    }

    boolean removeUser(final int i) {
        return this.mItems.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$removeUser$4;
                lambda$removeUser$4 = com.android.server.om.OverlayManagerSettings.lambda$removeUser$4(i, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                return lambda$removeUser$4;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$removeUser$4(int i, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        if (settingsItem.getUserId() == i) {
            return true;
        }
        return false;
    }

    void setPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, int i2) throws com.android.server.om.OverlayManagerSettings.BadKeyException {
        int select = select(overlayIdentifier, i);
        if (select < 0) {
            throw new com.android.server.om.OverlayManagerSettings.BadKeyException(overlayIdentifier, i);
        }
        com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(select);
        this.mItems.remove(select);
        settingsItem.setPriority(i2);
        insert(settingsItem);
    }

    boolean setPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, @android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier2, int i) {
        int select;
        int select2;
        if (overlayIdentifier.equals(overlayIdentifier2) || (select = select(overlayIdentifier, i)) < 0 || (select2 = select(overlayIdentifier2, i)) < 0) {
            return false;
        }
        com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(select);
        if (!settingsItem.getTargetPackageName().equals(this.mItems.get(select2).getTargetPackageName())) {
            return false;
        }
        this.mItems.remove(select);
        int select3 = select(overlayIdentifier2, i) + 1;
        this.mItems.add(select3, settingsItem);
        return select != select3;
    }

    boolean setLowestPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        int select = select(overlayIdentifier, i);
        if (select <= 0) {
            return false;
        }
        com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(select);
        this.mItems.remove(settingsItem);
        this.mItems.add(0, settingsItem);
        return true;
    }

    boolean setHighestPriority(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        int select = select(overlayIdentifier, i);
        if (select < 0 || select == this.mItems.size() - 1) {
            return false;
        }
        com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(select);
        this.mItems.remove(select);
        this.mItems.add(settingsItem);
        return true;
    }

    private void insert(@android.annotation.NonNull com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        int size = this.mItems.size() - 1;
        while (size >= 0 && this.mItems.get(size).mPriority > settingsItem.getPriority()) {
            size--;
        }
        this.mItems.add(size + 1, settingsItem);
    }

    void dump(@android.annotation.NonNull java.io.PrintWriter printWriter, @android.annotation.NonNull final com.android.server.om.DumpState dumpState) {
        java.util.stream.Stream stream = this.mItems.stream();
        if (dumpState.getUserId() != -1) {
            stream = stream.filter(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$dump$5;
                    lambda$dump$5 = com.android.server.om.OverlayManagerSettings.lambda$dump$5(com.android.server.om.DumpState.this, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                    return lambda$dump$5;
                }
            });
        }
        if (dumpState.getPackageName() != null) {
            stream = stream.filter(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$dump$6;
                    lambda$dump$6 = com.android.server.om.OverlayManagerSettings.lambda$dump$6(com.android.server.om.DumpState.this, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                    return lambda$dump$6;
                }
            });
        }
        if (dumpState.getOverlayName() != null) {
            stream = stream.filter(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda5
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean lambda$dump$7;
                    lambda$dump$7 = com.android.server.om.OverlayManagerSettings.lambda$dump$7(com.android.server.om.DumpState.this, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                    return lambda$dump$7;
                }
            });
        }
        final com.android.internal.util.IndentingPrintWriter indentingPrintWriter = new com.android.internal.util.IndentingPrintWriter(printWriter, "  ");
        if (dumpState.getField() != null) {
            stream.forEach(new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.om.OverlayManagerSettings.this.lambda$dump$8(indentingPrintWriter, dumpState, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                }
            });
        } else {
            stream.forEach(new java.util.function.Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.om.OverlayManagerSettings.this.lambda$dump$9(indentingPrintWriter, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dump$5(com.android.server.om.DumpState dumpState, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return settingsItem.mUserId == dumpState.getUserId();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dump$6(com.android.server.om.DumpState dumpState, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return settingsItem.mOverlay.getPackageName().equals(dumpState.getPackageName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$dump$7(com.android.server.om.DumpState dumpState, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return settingsItem.mOverlay.getOverlayName().equals(dumpState.getOverlayName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dump$8(com.android.internal.util.IndentingPrintWriter indentingPrintWriter, com.android.server.om.DumpState dumpState, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        dumpSettingsItemField(indentingPrintWriter, settingsItem, dumpState.getField());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: dumpSettingsItem, reason: merged with bridge method [inline-methods] */
    public void lambda$dump$9(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        indentingPrintWriter.println(settingsItem.mOverlay + ":" + settingsItem.getUserId() + " {");
        indentingPrintWriter.increaseIndent();
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("mPackageName...........: ");
        sb.append(settingsItem.mOverlay.getPackageName());
        indentingPrintWriter.println(sb.toString());
        indentingPrintWriter.println("mOverlayName...........: " + settingsItem.mOverlay.getOverlayName());
        indentingPrintWriter.println("mUserId................: " + settingsItem.getUserId());
        indentingPrintWriter.println("mTargetPackageName.....: " + settingsItem.getTargetPackageName());
        indentingPrintWriter.println("mTargetOverlayableName.: " + settingsItem.getTargetOverlayableName());
        indentingPrintWriter.println("mBaseCodePath..........: " + settingsItem.getBaseCodePath());
        indentingPrintWriter.println("mState.................: " + android.content.om.OverlayInfo.stateToString(settingsItem.getState()));
        indentingPrintWriter.println("mIsEnabled.............: " + settingsItem.isEnabled());
        indentingPrintWriter.println("mIsMutable.............: " + settingsItem.isMutable());
        indentingPrintWriter.println("mPriority..............: " + settingsItem.mPriority);
        indentingPrintWriter.println("mCategory..............: " + settingsItem.mCategory);
        indentingPrintWriter.println("mIsFabricated..........: " + settingsItem.mIsFabricated);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("}");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private void dumpSettingsItemField(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter, @android.annotation.NonNull com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem, @android.annotation.NonNull java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1750736508:
                if (str.equals("targetoverlayablename")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1248283232:
                if (str.equals("targetpackagename")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1165461084:
                if (str.equals("priority")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -836029914:
                if (str.equals("userid")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -831052100:
                if (str.equals("ismutable")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -405989669:
                if (str.equals("overlayname")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 109757585:
                if (str.equals("state")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 440941271:
                if (str.equals("isenabled")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 909712337:
                if (str.equals("packagename")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1693907299:
                if (str.equals("basecodepath")) {
                    c = 5;
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
                indentingPrintWriter.println(settingsItem.mOverlay.getPackageName());
                break;
            case 1:
                indentingPrintWriter.println(settingsItem.mOverlay.getOverlayName());
                break;
            case 2:
                indentingPrintWriter.println(settingsItem.mUserId);
                break;
            case 3:
                indentingPrintWriter.println(settingsItem.mTargetPackageName);
                break;
            case 4:
                indentingPrintWriter.println(settingsItem.mTargetOverlayableName);
                break;
            case 5:
                indentingPrintWriter.println(settingsItem.mBaseCodePath);
                break;
            case 6:
                indentingPrintWriter.println(android.content.om.OverlayInfo.stateToString(settingsItem.mState));
                break;
            case 7:
                indentingPrintWriter.println(settingsItem.mIsEnabled);
                break;
            case '\b':
                indentingPrintWriter.println(settingsItem.mIsMutable);
                break;
            case '\t':
                indentingPrintWriter.println(settingsItem.mPriority);
                break;
            case '\n':
                indentingPrintWriter.println(settingsItem.mCategory);
                break;
        }
    }

    void restore(@android.annotation.NonNull java.io.InputStream inputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.om.OverlayManagerSettings.Serializer.restore(this.mItems, inputStream);
    }

    void persist(@android.annotation.NonNull java.io.OutputStream outputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        com.android.server.om.OverlayManagerSettings.Serializer.persist(this.mItems, outputStream);
    }

    @com.android.internal.annotations.VisibleForTesting
    static final class Serializer {
        private static final java.lang.String ATTR_BASE_CODE_PATH = "baseCodePath";
        private static final java.lang.String ATTR_CATEGORY = "category";
        private static final java.lang.String ATTR_IS_ENABLED = "isEnabled";
        private static final java.lang.String ATTR_IS_FABRICATED = "fabricated";
        private static final java.lang.String ATTR_IS_STATIC = "isStatic";
        private static final java.lang.String ATTR_OVERLAY_NAME = "overlayName";
        private static final java.lang.String ATTR_PACKAGE_NAME = "packageName";
        private static final java.lang.String ATTR_PRIORITY = "priority";
        private static final java.lang.String ATTR_STATE = "state";
        private static final java.lang.String ATTR_TARGET_OVERLAYABLE_NAME = "targetOverlayableName";
        private static final java.lang.String ATTR_TARGET_PACKAGE_NAME = "targetPackageName";
        private static final java.lang.String ATTR_USER_ID = "userId";
        private static final java.lang.String ATTR_VERSION = "version";

        @com.android.internal.annotations.VisibleForTesting
        static final int CURRENT_VERSION = 4;
        private static final java.lang.String TAG_ITEM = "item";
        private static final java.lang.String TAG_OVERLAYS = "overlays";

        Serializer() {
        }

        public static void restore(@android.annotation.NonNull java.util.ArrayList<com.android.server.om.OverlayManagerSettings.SettingsItem> arrayList, @android.annotation.NonNull java.io.InputStream inputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            arrayList.clear();
            com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(inputStream);
            com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, TAG_OVERLAYS);
            int attributeInt = resolvePullParser.getAttributeInt((java.lang.String) null, ATTR_VERSION);
            if (attributeInt != 4) {
                upgrade(attributeInt);
            }
            int depth = resolvePullParser.getDepth();
            while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                if ("item".equals(resolvePullParser.getName())) {
                    arrayList.add(restoreRow(resolvePullParser, depth + 1));
                }
            }
        }

        private static void upgrade(int i) throws org.xmlpull.v1.XmlPullParserException {
            switch (i) {
                case 0:
                case 1:
                case 2:
                    throw new org.xmlpull.v1.XmlPullParserException("old version " + i + "; ignoring");
                case 3:
                    return;
                default:
                    throw new org.xmlpull.v1.XmlPullParserException("unrecognized version " + i);
            }
        }

        private static com.android.server.om.OverlayManagerSettings.SettingsItem restoreRow(@android.annotation.NonNull com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, int i) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            return new com.android.server.om.OverlayManagerSettings.SettingsItem(new android.content.om.OverlayIdentifier(com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, "packageName"), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_OVERLAY_NAME)), typedXmlPullParser.getAttributeInt((java.lang.String) null, "userId"), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_TARGET_PACKAGE_NAME), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_TARGET_OVERLAYABLE_NAME), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_BASE_CODE_PATH), typedXmlPullParser.getAttributeInt((java.lang.String) null, "state"), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_ENABLED, false), !typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_STATIC, false), typedXmlPullParser.getAttributeInt((java.lang.String) null, ATTR_PRIORITY), com.android.internal.util.XmlUtils.readStringAttribute(typedXmlPullParser, ATTR_CATEGORY), typedXmlPullParser.getAttributeBoolean((java.lang.String) null, ATTR_IS_FABRICATED, false));
        }

        public static void persist(@android.annotation.NonNull java.util.ArrayList<com.android.server.om.OverlayManagerSettings.SettingsItem> arrayList, @android.annotation.NonNull java.io.OutputStream outputStream) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(outputStream);
            resolveSerializer.startDocument((java.lang.String) null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((java.lang.String) null, TAG_OVERLAYS);
            resolveSerializer.attributeInt((java.lang.String) null, ATTR_VERSION, 4);
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                persistRow(resolveSerializer, arrayList.get(i));
            }
            resolveSerializer.endTag((java.lang.String) null, TAG_OVERLAYS);
            resolveSerializer.endDocument();
        }

        private static void persistRow(@android.annotation.NonNull com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, @android.annotation.NonNull com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) throws java.io.IOException {
            typedXmlSerializer.startTag((java.lang.String) null, "item");
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, "packageName", settingsItem.mOverlay.getPackageName());
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_OVERLAY_NAME, settingsItem.mOverlay.getOverlayName());
            typedXmlSerializer.attributeInt((java.lang.String) null, "userId", settingsItem.mUserId);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_TARGET_PACKAGE_NAME, settingsItem.mTargetPackageName);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_TARGET_OVERLAYABLE_NAME, settingsItem.mTargetOverlayableName);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_BASE_CODE_PATH, settingsItem.mBaseCodePath);
            typedXmlSerializer.attributeInt((java.lang.String) null, "state", settingsItem.mState);
            com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_ENABLED, settingsItem.mIsEnabled);
            com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_STATIC, !settingsItem.mIsMutable);
            typedXmlSerializer.attributeInt((java.lang.String) null, ATTR_PRIORITY, settingsItem.mPriority);
            com.android.internal.util.XmlUtils.writeStringAttribute(typedXmlSerializer, ATTR_CATEGORY, settingsItem.mCategory);
            com.android.internal.util.XmlUtils.writeBooleanAttribute(typedXmlSerializer, ATTR_IS_FABRICATED, settingsItem.mIsFabricated);
            typedXmlSerializer.endTag((java.lang.String) null, "item");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class SettingsItem {
        private java.lang.String mBaseCodePath;
        private android.content.om.OverlayInfo mCache = null;
        private java.lang.String mCategory;
        private boolean mIsEnabled;
        private boolean mIsFabricated;
        private boolean mIsMutable;
        private final android.content.om.OverlayIdentifier mOverlay;
        private int mPriority;
        private int mState;
        private final java.lang.String mTargetOverlayableName;
        private final java.lang.String mTargetPackageName;
        private final int mUserId;

        SettingsItem(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2, @android.annotation.NonNull java.lang.String str3, int i2, boolean z, boolean z2, int i3, @android.annotation.Nullable java.lang.String str4, boolean z3) {
            this.mOverlay = overlayIdentifier;
            this.mUserId = i;
            this.mTargetPackageName = str;
            this.mTargetOverlayableName = str2;
            this.mBaseCodePath = str3;
            this.mState = i2;
            this.mIsEnabled = z;
            this.mCategory = str4;
            this.mIsMutable = z2;
            this.mPriority = i3;
            this.mIsFabricated = z3;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getTargetPackageName() {
            return this.mTargetPackageName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getTargetOverlayableName() {
            return this.mTargetOverlayableName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getUserId() {
            return this.mUserId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.String getBaseCodePath() {
            return this.mBaseCodePath;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setBaseCodePath(@android.annotation.NonNull java.lang.String str) {
            if (!this.mBaseCodePath.equals(str)) {
                this.mBaseCodePath = str;
                invalidateCache();
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getState() {
            return this.mState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setState(int i) {
            if (this.mState != i) {
                this.mState = i;
                invalidateCache();
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isEnabled() {
            return this.mIsEnabled;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setEnabled(boolean z) {
            if (!this.mIsMutable || this.mIsEnabled == z) {
                return false;
            }
            this.mIsEnabled = z;
            invalidateCache();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean setCategory(java.lang.String str) {
            if (!java.util.Objects.equals(this.mCategory, str)) {
                this.mCategory = str == null ? null : str.intern();
                invalidateCache();
                return true;
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.content.om.OverlayInfo getOverlayInfo() {
            if (this.mCache == null) {
                this.mCache = new android.content.om.OverlayInfo(this.mOverlay.getPackageName(), this.mOverlay.getOverlayName(), this.mTargetPackageName, this.mTargetOverlayableName, this.mCategory, this.mBaseCodePath, this.mState, this.mUserId, this.mPriority, this.mIsMutable, this.mIsFabricated);
            }
            return this.mCache;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setPriority(int i) {
            this.mPriority = i;
            invalidateCache();
        }

        private void invalidateCache() {
            this.mCache = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isMutable() {
            return this.mIsMutable;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getPriority() {
            return this.mPriority;
        }
    }

    private int select(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
        int size = this.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem = this.mItems.get(i2);
            if (settingsItem.mUserId == i && settingsItem.mOverlay.equals(overlayIdentifier)) {
                return i2;
            }
        }
        return -1;
    }

    private java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereUser(final int i) {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        com.android.internal.util.CollectionUtils.addIf(this.mItems, arrayList, new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda14
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$selectWhereUser$10;
                lambda$selectWhereUser$10 = com.android.server.om.OverlayManagerSettings.lambda$selectWhereUser$10(i, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                return lambda$selectWhereUser$10;
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$selectWhereUser$10(int i, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return settingsItem.mUserId == i;
    }

    private java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereOverlay(@android.annotation.NonNull final java.lang.String str, int i) {
        java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereUser = selectWhereUser(i);
        selectWhereUser.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda9
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$selectWhereOverlay$11;
                lambda$selectWhereOverlay$11 = com.android.server.om.OverlayManagerSettings.lambda$selectWhereOverlay$11(str, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                return lambda$selectWhereOverlay$11;
            }
        });
        return selectWhereUser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$selectWhereOverlay$11(java.lang.String str, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return !settingsItem.mOverlay.getPackageName().equals(str);
    }

    private java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereTarget(@android.annotation.NonNull final java.lang.String str, int i) {
        java.util.List<com.android.server.om.OverlayManagerSettings.SettingsItem> selectWhereUser = selectWhereUser(i);
        selectWhereUser.removeIf(new java.util.function.Predicate() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$selectWhereTarget$12;
                lambda$selectWhereTarget$12 = com.android.server.om.OverlayManagerSettings.lambda$selectWhereTarget$12(str, (com.android.server.om.OverlayManagerSettings.SettingsItem) obj);
                return lambda$selectWhereTarget$12;
            }
        });
        return selectWhereUser;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$selectWhereTarget$12(java.lang.String str, com.android.server.om.OverlayManagerSettings.SettingsItem settingsItem) {
        return !settingsItem.getTargetPackageName().equals(str);
    }

    static final class BadKeyException extends java.lang.Exception {
        BadKeyException(@android.annotation.NonNull android.content.om.OverlayIdentifier overlayIdentifier, int i) {
            super("Bad key '" + overlayIdentifier + "' for user " + i);
        }
    }
}
