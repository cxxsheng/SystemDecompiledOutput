package com.android.server.input;

/* loaded from: classes2.dex */
class KeyboardLayoutManager implements android.hardware.input.InputManager.InputDeviceListener {
    private static final int MSG_RELOAD_KEYBOARD_LAYOUTS = 3;
    private static final int MSG_SWITCH_KEYBOARD_LAYOUT = 2;
    private static final int MSG_UPDATE_EXISTING_DEVICES = 1;
    private static final int MSG_UPDATE_KEYBOARD_LAYOUTS = 4;
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mImeInfoLock"})
    @android.annotation.Nullable
    private com.android.server.input.KeyboardLayoutManager.ImeInfo mCurrentImeInfo;

    @com.android.internal.annotations.GuardedBy({"mDataStore"})
    private final com.android.server.input.PersistentDataStore mDataStore;
    private final android.os.Handler mHandler;
    private final com.android.server.input.NativeInputManagerService mNative;
    private android.widget.Toast mSwitchedKeyboardLayoutToast;
    private static final java.lang.String TAG = "KeyboardLayoutManager";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private final android.util.SparseArray<com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration> mConfiguredKeyboards = new android.util.SparseArray<>();

    @com.android.internal.annotations.GuardedBy({"mKeyboardLayoutCache"})
    private final java.util.Map<java.lang.String, android.hardware.input.KeyboardLayoutSelectionResult> mKeyboardLayoutCache = new android.util.ArrayMap();
    private final java.lang.Object mImeInfoLock = new java.lang.Object();

    /* JADX INFO: Access modifiers changed from: private */
    interface KeyboardLayoutVisitor {
        void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout);
    }

    KeyboardLayoutManager(android.content.Context context, com.android.server.input.NativeInputManagerService nativeInputManagerService, com.android.server.input.PersistentDataStore persistentDataStore, android.os.Looper looper) {
        this.mContext = context;
        this.mNative = nativeInputManagerService;
        this.mDataStore = persistentDataStore;
        this.mHandler = new android.os.Handler(looper, new android.os.Handler.Callback() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda7
            @Override // android.os.Handler.Callback
            public final boolean handleMessage(android.os.Message message) {
                boolean handleMessage;
                handleMessage = com.android.server.input.KeyboardLayoutManager.this.handleMessage(message);
                return handleMessage;
            }
        }, true);
    }

    public void systemRunning() {
        android.content.IntentFilter intentFilter = new android.content.IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(new android.content.BroadcastReceiver() { // from class: com.android.server.input.KeyboardLayoutManager.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                com.android.server.input.KeyboardLayoutManager.this.updateKeyboardLayouts();
            }
        }, intentFilter, null, this.mHandler);
        this.mHandler.sendEmptyMessage(4);
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        java.util.Objects.requireNonNull(inputManager);
        inputManager.registerInputDeviceListener(this, this.mHandler);
        this.mHandler.sendMessage(android.os.Message.obtain(this.mHandler, 1, inputManager.getInputDeviceIds()));
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceAdded(int i) {
        onInputDeviceChangedInternal(i, true);
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceRemoved(int i) {
        this.mConfiguredKeyboards.remove(i);
        maybeUpdateNotification();
    }

    @Override // android.hardware.input.InputManager.InputDeviceListener
    public void onInputDeviceChanged(int i) {
        onInputDeviceChangedInternal(i, false);
    }

    private void onInputDeviceChangedInternal(int i, boolean z) {
        boolean z2;
        android.view.InputDevice inputDevice = getInputDevice(i);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        boolean z3 = false;
        com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDevice);
        com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration keyboardConfiguration = this.mConfiguredKeyboards.get(i);
        if (keyboardConfiguration == null) {
            keyboardConfiguration = new com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration(i);
            this.mConfiguredKeyboards.put(i, keyboardConfiguration);
        }
        boolean z4 = true;
        if (!useNewSettingsUi()) {
            synchronized (this.mDataStore) {
                try {
                    java.lang.String currentKeyboardLayoutForInputDevice = getCurrentKeyboardLayoutForInputDevice(inputDevice.getIdentifier());
                    if (currentKeyboardLayoutForInputDevice == null && (currentKeyboardLayoutForInputDevice = getDefaultKeyboardLayout(inputDevice)) != null) {
                        setCurrentKeyboardLayoutForInputDevice(inputDevice.getIdentifier(), currentKeyboardLayoutForInputDevice);
                    }
                    if (currentKeyboardLayoutForInputDevice != null) {
                        z4 = false;
                    }
                } finally {
                }
            }
        } else {
            java.util.HashSet hashSet = new java.util.HashSet();
            java.util.List<com.android.server.input.KeyboardLayoutManager.ImeInfo> imeInfoListForLayoutMapping = getImeInfoListForLayoutMapping();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<com.android.server.input.KeyboardLayoutManager.ImeInfo> it = imeInfoListForLayoutMapping.iterator();
            boolean z5 = false;
            while (it.hasNext()) {
                android.hardware.input.KeyboardLayoutSelectionResult keyboardLayoutForInputDeviceInternal = getKeyboardLayoutForInputDeviceInternal(keyboardIdentifier, it.next());
                boolean z6 = keyboardLayoutForInputDeviceInternal.getLayoutDescriptor() == null;
                if (!z6) {
                    hashSet.add(keyboardLayoutForInputDeviceInternal.getLayoutDescriptor());
                }
                arrayList.add(keyboardLayoutForInputDeviceInternal);
                z5 |= z6;
            }
            if (DEBUG) {
                android.util.Slog.d(TAG, "Layouts selected for input device: " + keyboardIdentifier + " -> selectedLayouts: " + hashSet);
            }
            if (z5) {
                hashSet.clear();
            }
            keyboardConfiguration.setConfiguredLayouts(hashSet);
            synchronized (this.mDataStore) {
                try {
                    java.lang.String keyboardIdentifier2 = keyboardIdentifier.toString();
                    if (!this.mDataStore.setSelectedKeyboardLayouts(keyboardIdentifier2, hashSet)) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z) {
                        if (this.mDataStore.hasInputDeviceEntry(keyboardIdentifier2)) {
                            z4 = false;
                        }
                        logKeyboardConfigurationEvent(inputDevice, imeInfoListForLayoutMapping, arrayList, z4);
                    }
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            }
            z4 = z2;
        }
        if (z4) {
            maybeUpdateNotification();
        }
    }

    private java.lang.String getDefaultKeyboardLayout(final android.view.InputDevice inputDevice) {
        final java.util.Locale locale = this.mContext.getResources().getConfiguration().locale;
        if (android.text.TextUtils.isEmpty(locale.getLanguage())) {
            return null;
        }
        final java.util.ArrayList<android.hardware.input.KeyboardLayout> arrayList = new java.util.ArrayList();
        visitAllKeyboardLayouts(new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda1
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                com.android.server.input.KeyboardLayoutManager.lambda$getDefaultKeyboardLayout$0(inputDevice, locale, arrayList, resources, i, keyboardLayout);
            }
        });
        if (arrayList.isEmpty()) {
            return null;
        }
        java.util.Collections.sort(arrayList);
        java.util.Iterator it = arrayList.iterator();
        while (true) {
            if (it.hasNext()) {
                android.hardware.input.KeyboardLayout keyboardLayout = (android.hardware.input.KeyboardLayout) it.next();
                android.os.LocaleList locales = keyboardLayout.getLocales();
                for (int i = 0; i < locales.size(); i++) {
                    java.util.Locale locale2 = locales.get(i);
                    if (locale2 != null && locale2.getCountry().equals(locale.getCountry()) && locale2.getVariant().equals(locale.getVariant())) {
                        return keyboardLayout.getDescriptor();
                    }
                }
            } else {
                for (android.hardware.input.KeyboardLayout keyboardLayout2 : arrayList) {
                    android.os.LocaleList locales2 = keyboardLayout2.getLocales();
                    for (int i2 = 0; i2 < locales2.size(); i2++) {
                        java.util.Locale locale3 = locales2.get(i2);
                        if (locale3 != null && locale3.getCountry().equals(locale.getCountry())) {
                            return keyboardLayout2.getDescriptor();
                        }
                    }
                }
                return ((android.hardware.input.KeyboardLayout) arrayList.get(0)).getDescriptor();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getDefaultKeyboardLayout$0(android.view.InputDevice inputDevice, java.util.Locale locale, java.util.List list, android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
        if (keyboardLayout.getVendorId() != inputDevice.getVendorId() || keyboardLayout.getProductId() != inputDevice.getProductId()) {
            return;
        }
        android.os.LocaleList locales = keyboardLayout.getLocales();
        for (int i2 = 0; i2 < locales.size(); i2++) {
            java.util.Locale locale2 = locales.get(i2);
            if (locale2 != null && isCompatibleLocale(locale, locale2)) {
                list.add(keyboardLayout);
                return;
            }
        }
    }

    private static boolean isCompatibleLocale(java.util.Locale locale, java.util.Locale locale2) {
        if (locale.getLanguage().equals(locale2.getLanguage())) {
            return android.text.TextUtils.isEmpty(locale.getCountry()) || android.text.TextUtils.isEmpty(locale2.getCountry()) || locale.getCountry().equals(locale2.getCountry());
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateKeyboardLayouts() {
        final java.util.HashSet hashSet = new java.util.HashSet();
        visitAllKeyboardLayouts(new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda8
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                com.android.server.input.KeyboardLayoutManager.lambda$updateKeyboardLayouts$1(hashSet, resources, i, keyboardLayout);
            }
        });
        synchronized (this.mDataStore) {
            try {
                this.mDataStore.removeUninstalledKeyboardLayouts(hashSet);
            } finally {
                this.mDataStore.saveIfNeeded();
            }
        }
        synchronized (this.mKeyboardLayoutCache) {
            this.mKeyboardLayoutCache.clear();
        }
        reloadKeyboardLayouts();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$updateKeyboardLayouts$1(java.util.HashSet hashSet, android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
        hashSet.add(keyboardLayout.getDescriptor());
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayouts() {
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        visitAllKeyboardLayouts(new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda4
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                arrayList.add(keyboardLayout);
            }
        });
        return (android.hardware.input.KeyboardLayout[]) arrayList.toArray(new android.hardware.input.KeyboardLayout[0]);
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutsForInputDevice(final android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        if (useNewSettingsUi()) {
            return getKeyboardLayouts();
        }
        final java.lang.String[] enabledKeyboardLayoutsForInputDevice = getEnabledKeyboardLayoutsForInputDevice(inputDeviceIdentifier);
        final java.util.ArrayList arrayList = new java.util.ArrayList(enabledKeyboardLayoutsForInputDevice.length);
        final java.util.ArrayList arrayList2 = new java.util.ArrayList();
        visitAllKeyboardLayouts(new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager.2
            boolean mHasSeenDeviceSpecificLayout;

            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                for (java.lang.String str : enabledKeyboardLayoutsForInputDevice) {
                    if (str != null && str.equals(keyboardLayout.getDescriptor())) {
                        arrayList.add(keyboardLayout);
                        return;
                    }
                }
                if (keyboardLayout.getVendorId() == inputDeviceIdentifier.getVendorId() && keyboardLayout.getProductId() == inputDeviceIdentifier.getProductId()) {
                    if (!this.mHasSeenDeviceSpecificLayout) {
                        this.mHasSeenDeviceSpecificLayout = true;
                        arrayList2.clear();
                    }
                    arrayList2.add(keyboardLayout);
                    return;
                }
                if (keyboardLayout.getVendorId() == -1 && keyboardLayout.getProductId() == -1 && !this.mHasSeenDeviceSpecificLayout) {
                    arrayList2.add(keyboardLayout);
                }
            }
        });
        return (android.hardware.input.KeyboardLayout[]) java.util.stream.Stream.concat(arrayList.stream(), arrayList2.stream()).toArray(new java.util.function.IntFunction() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda6
            @Override // java.util.function.IntFunction
            public final java.lang.Object apply(int i) {
                android.hardware.input.KeyboardLayout[] lambda$getKeyboardLayoutsForInputDevice$3;
                lambda$getKeyboardLayoutsForInputDevice$3 = com.android.server.input.KeyboardLayoutManager.lambda$getKeyboardLayoutsForInputDevice$3(i);
                return lambda$getKeyboardLayoutsForInputDevice$3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ android.hardware.input.KeyboardLayout[] lambda$getKeyboardLayoutsForInputDevice$3(int i) {
        return new android.hardware.input.KeyboardLayout[i];
    }

    @android.annotation.Nullable
    public android.hardware.input.KeyboardLayout getKeyboardLayout(@android.annotation.NonNull java.lang.String str) {
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        final android.hardware.input.KeyboardLayout[] keyboardLayoutArr = new android.hardware.input.KeyboardLayout[1];
        visitKeyboardLayout(str, new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda0
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                com.android.server.input.KeyboardLayoutManager.lambda$getKeyboardLayout$4(keyboardLayoutArr, resources, i, keyboardLayout);
            }
        });
        if (keyboardLayoutArr[0] == null) {
            android.util.Slog.w(TAG, "Could not get keyboard layout with descriptor '" + str + "'.");
        }
        return keyboardLayoutArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getKeyboardLayout$4(android.hardware.input.KeyboardLayout[] keyboardLayoutArr, android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
        keyboardLayoutArr[0] = keyboardLayout;
    }

    public android.view.KeyCharacterMap getKeyCharacterMap(@android.annotation.NonNull java.lang.String str) {
        final java.lang.String[] strArr = new java.lang.String[1];
        visitKeyboardLayout(str, new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda5
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                com.android.server.input.KeyboardLayoutManager.lambda$getKeyCharacterMap$5(strArr, resources, i, keyboardLayout);
            }
        });
        if (android.text.TextUtils.isEmpty(strArr[0])) {
            return android.view.KeyCharacterMap.load(-1);
        }
        return android.view.KeyCharacterMap.load(str, strArr[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getKeyCharacterMap$5(java.lang.String[] strArr, android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
        try {
            java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(resources.openRawResource(i));
            try {
                strArr[0] = libcore.io.Streams.readFully(inputStreamReader);
                inputStreamReader.close();
            } catch (java.lang.Throwable th) {
                try {
                    inputStreamReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (android.content.res.Resources.NotFoundException | java.io.IOException e) {
        }
    }

    private void visitAllKeyboardLayouts(com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor keyboardLayoutVisitor) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        for (android.content.pm.ResolveInfo resolveInfo : packageManager.queryBroadcastReceiversAsUser(new android.content.Intent("android.hardware.input.action.QUERY_KEYBOARD_LAYOUTS"), 786560, 0)) {
            visitKeyboardLayoutsInPackage(packageManager, resolveInfo.activityInfo, null, resolveInfo.priority, keyboardLayoutVisitor);
        }
    }

    private void visitKeyboardLayout(java.lang.String str, com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor keyboardLayoutVisitor) {
        com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor parse = com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor.parse(str);
        if (parse != null) {
            android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
            try {
                visitKeyboardLayoutsInPackage(packageManager, packageManager.getReceiverInfo(new android.content.ComponentName(parse.packageName, parse.receiverName), 786560), parse.keyboardLayoutName, 0, keyboardLayoutVisitor);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            }
        }
    }

    private void visitKeyboardLayoutsInPackage(android.content.pm.PackageManager packageManager, android.content.pm.ActivityInfo activityInfo, java.lang.String str, int i, com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor keyboardLayoutVisitor) {
        java.lang.String str2;
        int i2;
        int i3;
        android.content.res.TypedArray typedArray;
        java.lang.Object obj = str;
        android.os.Bundle bundle = activityInfo.metaData;
        if (bundle == null) {
            return;
        }
        int i4 = bundle.getInt("android.hardware.input.metadata.KEYBOARD_LAYOUTS");
        if (i4 == 0) {
            android.util.Slog.w(TAG, "Missing meta-data 'android.hardware.input.metadata.KEYBOARD_LAYOUTS' on receiver " + activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + activityInfo.name);
            return;
        }
        java.lang.CharSequence loadLabel = activityInfo.loadLabel(packageManager);
        java.lang.String charSequence = loadLabel != null ? loadLabel.toString() : "";
        int i5 = 1;
        int i6 = 0;
        int i7 = (activityInfo.applicationInfo.flags & 1) != 0 ? i : 0;
        try {
            android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(activityInfo.applicationInfo);
            android.content.res.XmlResourceParser xml = resourcesForApplication.getXml(i4);
            try {
                com.android.internal.util.XmlUtils.beginDocument(xml, "keyboard-layouts");
                while (true) {
                    com.android.internal.util.XmlUtils.nextElement(xml);
                    java.lang.String name = xml.getName();
                    if (name == null) {
                        xml.close();
                        return;
                    }
                    if (name.equals("keyboard-layout")) {
                        android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(xml, com.android.internal.R.styleable.KeyboardLayout);
                        try {
                            java.lang.String string = obtainAttributes.getString(i5);
                            java.lang.String string2 = obtainAttributes.getString(i6);
                            int resourceId = obtainAttributes.getResourceId(2, i6);
                            android.os.LocaleList localesFromLanguageTags = getLocalesFromLanguageTags(obtainAttributes.getString(3));
                            int i8 = obtainAttributes.getInt(4, i6);
                            int i9 = obtainAttributes.getInt(6, -1);
                            int i10 = obtainAttributes.getInt(5, -1);
                            try {
                                if (string == null || string2 == null) {
                                    str2 = charSequence;
                                    typedArray = obtainAttributes;
                                    i2 = i6;
                                    i3 = i5;
                                } else if (resourceId == 0) {
                                    str2 = charSequence;
                                    typedArray = obtainAttributes;
                                    i2 = i6;
                                    i3 = i5;
                                } else {
                                    java.lang.String format = com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor.format(activityInfo.packageName, activityInfo.name, string);
                                    if (obj == null || string.equals(obj)) {
                                        str2 = charSequence;
                                        typedArray = obtainAttributes;
                                        i2 = i6;
                                        i3 = i5;
                                        keyboardLayoutVisitor.visitKeyboardLayout(resourcesForApplication, resourceId, new android.hardware.input.KeyboardLayout(format, string2, charSequence, i7, localesFromLanguageTags, i8, i9, i10));
                                    } else {
                                        str2 = charSequence;
                                        typedArray = obtainAttributes;
                                        i2 = i6;
                                        i3 = i5;
                                    }
                                    typedArray.recycle();
                                }
                                android.util.Slog.w(TAG, "Missing required 'name', 'label' or 'keyboardLayout' attributes in keyboard layout resource from receiver " + activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + activityInfo.name);
                                typedArray.recycle();
                            } catch (java.lang.Throwable th) {
                                th = th;
                                typedArray.recycle();
                                throw th;
                            }
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            typedArray = obtainAttributes;
                        }
                    } else {
                        str2 = charSequence;
                        i2 = i6;
                        i3 = i5;
                        android.util.Slog.w(TAG, "Skipping unrecognized element '" + name + "' in keyboard layout resource from receiver " + activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + activityInfo.name);
                    }
                    charSequence = str2;
                    obj = str;
                    i6 = i2;
                    i5 = i3;
                }
            } finally {
            }
        } catch (java.lang.Exception e) {
            android.util.Slog.w(TAG, "Could not parse keyboard layout resource from receiver " + activityInfo.packageName + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + activityInfo.name, e);
        }
    }

    @android.annotation.NonNull
    private static android.os.LocaleList getLocalesFromLanguageTags(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return android.os.LocaleList.getEmptyLocaleList();
        }
        return android.os.LocaleList.forLanguageTags(str.replace('|', ','));
    }

    @android.annotation.Nullable
    public java.lang.String getCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        java.lang.String currentKeyboardLayout;
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "getCurrentKeyboardLayoutForInputDevice API not supported");
            return null;
        }
        java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier).toString();
        synchronized (this.mDataStore) {
            try {
                currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier);
                if (currentKeyboardLayout == null && !keyboardIdentifier.equals(inputDeviceIdentifier.getDescriptor())) {
                    currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(inputDeviceIdentifier.getDescriptor());
                }
                if (DEBUG) {
                    android.util.Slog.d(TAG, "getCurrentKeyboardLayoutForInputDevice() " + inputDeviceIdentifier.toString() + ": " + currentKeyboardLayout);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return currentKeyboardLayout;
    }

    public void setCurrentKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "setCurrentKeyboardLayoutForInputDevice API not supported");
            return;
        }
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier).toString();
        synchronized (this.mDataStore) {
            try {
                try {
                    if (this.mDataStore.setCurrentKeyboardLayout(keyboardIdentifier, str)) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "setCurrentKeyboardLayoutForInputDevice() " + inputDeviceIdentifier + " key: " + keyboardIdentifier + " keyboardLayoutDescriptor: " + str);
                        }
                        this.mHandler.sendEmptyMessage(3);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (java.lang.Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public java.lang.String[] getEnabledKeyboardLayoutsForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        java.lang.String[] keyboardLayouts;
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "getEnabledKeyboardLayoutsForInputDevice API not supported");
            return new java.lang.String[0];
        }
        java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier).toString();
        synchronized (this.mDataStore) {
            try {
                keyboardLayouts = this.mDataStore.getKeyboardLayouts(keyboardIdentifier);
                if ((keyboardLayouts == null || keyboardLayouts.length == 0) && !keyboardIdentifier.equals(inputDeviceIdentifier.getDescriptor())) {
                    keyboardLayouts = this.mDataStore.getKeyboardLayouts(inputDeviceIdentifier.getDescriptor());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return keyboardLayouts;
    }

    public void addKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "addKeyboardLayoutForInputDevice API not supported");
            return;
        }
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier).toString();
        synchronized (this.mDataStore) {
            try {
                try {
                    java.lang.String currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier);
                    if (currentKeyboardLayout == null && !keyboardIdentifier.equals(inputDeviceIdentifier.getDescriptor())) {
                        currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(inputDeviceIdentifier.getDescriptor());
                    }
                    if (this.mDataStore.addKeyboardLayout(keyboardIdentifier, str) && !java.util.Objects.equals(currentKeyboardLayout, this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier))) {
                        this.mHandler.sendEmptyMessage(3);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (java.lang.Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void removeKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str) {
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "removeKeyboardLayoutForInputDevice API not supported");
            return;
        }
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier).toString();
        synchronized (this.mDataStore) {
            try {
                try {
                    java.lang.String currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier);
                    if (currentKeyboardLayout == null && !keyboardIdentifier.equals(inputDeviceIdentifier.getDescriptor())) {
                        currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(inputDeviceIdentifier.getDescriptor());
                    }
                    boolean removeKeyboardLayout = this.mDataStore.removeKeyboardLayout(keyboardIdentifier, str);
                    if (!keyboardIdentifier.equals(inputDeviceIdentifier.getDescriptor())) {
                        removeKeyboardLayout |= this.mDataStore.removeKeyboardLayout(inputDeviceIdentifier.getDescriptor(), str);
                    }
                    if (removeKeyboardLayout && !java.util.Objects.equals(currentKeyboardLayout, this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier))) {
                        this.mHandler.sendEmptyMessage(3);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (java.lang.Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public void switchKeyboardLayout(int i, int i2) {
        if (useNewSettingsUi()) {
            android.util.Slog.e(TAG, "switchKeyboardLayout API not supported");
        } else {
            this.mHandler.obtainMessage(2, i, i2).sendToTarget();
        }
    }

    private void handleSwitchKeyboardLayout(int i, int i2) {
        boolean switchKeyboardLayout;
        java.lang.String currentKeyboardLayout;
        android.hardware.input.KeyboardLayout keyboardLayout;
        android.view.InputDevice inputDevice = getInputDevice(i);
        if (inputDevice != null) {
            java.lang.String keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDevice.getIdentifier()).toString();
            synchronized (this.mDataStore) {
                try {
                    switchKeyboardLayout = this.mDataStore.switchKeyboardLayout(keyboardIdentifier, i2);
                    currentKeyboardLayout = this.mDataStore.getCurrentKeyboardLayout(keyboardIdentifier);
                } finally {
                    this.mDataStore.saveIfNeeded();
                }
            }
            if (switchKeyboardLayout) {
                if (this.mSwitchedKeyboardLayoutToast != null) {
                    this.mSwitchedKeyboardLayoutToast.cancel();
                    this.mSwitchedKeyboardLayoutToast = null;
                }
                if (currentKeyboardLayout != null && (keyboardLayout = getKeyboardLayout(currentKeyboardLayout)) != null) {
                    this.mSwitchedKeyboardLayoutToast = android.widget.Toast.makeText(this.mContext, keyboardLayout.getLabel(), 0);
                    this.mSwitchedKeyboardLayoutToast.show();
                }
                reloadKeyboardLayouts();
            }
        }
    }

    @android.annotation.Nullable
    public java.lang.String[] getKeyboardLayoutOverlay(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, java.lang.String str, java.lang.String str2) {
        java.lang.String currentKeyboardLayoutForInputDevice;
        if (useNewSettingsUi()) {
            synchronized (this.mImeInfoLock) {
                currentKeyboardLayoutForInputDevice = getKeyboardLayoutForInputDeviceInternal(new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDeviceIdentifier, str, str2), this.mCurrentImeInfo).getLayoutDescriptor();
            }
        } else {
            currentKeyboardLayoutForInputDevice = getCurrentKeyboardLayoutForInputDevice(inputDeviceIdentifier);
        }
        if (currentKeyboardLayoutForInputDevice == null) {
            return null;
        }
        final java.lang.String[] strArr = new java.lang.String[2];
        visitKeyboardLayout(currentKeyboardLayoutForInputDevice, new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda2
            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public final void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
                com.android.server.input.KeyboardLayoutManager.lambda$getKeyboardLayoutOverlay$6(strArr, resources, i, keyboardLayout);
            }
        });
        if (strArr[0] == null) {
            android.util.Slog.w(TAG, "Could not get keyboard layout with descriptor '" + currentKeyboardLayoutForInputDevice + "'.");
            return null;
        }
        return strArr;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getKeyboardLayoutOverlay$6(java.lang.String[] strArr, android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout) {
        try {
            java.io.InputStreamReader inputStreamReader = new java.io.InputStreamReader(resources.openRawResource(i));
            try {
                strArr[0] = keyboardLayout.getDescriptor();
                strArr[1] = libcore.io.Streams.readFully(inputStreamReader);
                inputStreamReader.close();
            } catch (java.lang.Throwable th) {
                try {
                    inputStreamReader.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (android.content.res.Resources.NotFoundException | java.io.IOException e) {
        }
    }

    @android.annotation.NonNull
    public android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (!useNewSettingsUi()) {
            android.util.Slog.e(TAG, "getKeyboardLayoutForInputDevice() API not supported");
            return android.hardware.input.KeyboardLayoutSelectionResult.FAILED;
        }
        android.view.InputDevice inputDevice = getInputDevice(inputDeviceIdentifier);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return android.hardware.input.KeyboardLayoutSelectionResult.FAILED;
        }
        android.hardware.input.KeyboardLayoutSelectionResult keyboardLayoutForInputDeviceInternal = getKeyboardLayoutForInputDeviceInternal(new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDevice), new com.android.server.input.KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype));
        if (DEBUG) {
            android.util.Slog.d(TAG, "getKeyboardLayoutForInputDevice() " + inputDeviceIdentifier.toString() + ", userId : " + i + ", subtype = " + inputMethodSubtype + " -> " + keyboardLayoutForInputDeviceInternal);
        }
        return keyboardLayoutForInputDeviceInternal;
    }

    public void setKeyboardLayoutForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype, java.lang.String str) {
        if (!useNewSettingsUi()) {
            android.util.Slog.e(TAG, "setKeyboardLayoutForInputDevice() API not supported");
            return;
        }
        java.util.Objects.requireNonNull(str, "keyboardLayoutDescriptor must not be null");
        android.view.InputDevice inputDevice = getInputDevice(inputDeviceIdentifier);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return;
        }
        com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier = new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDevice);
        java.lang.String layoutKey = new com.android.server.input.KeyboardLayoutManager.LayoutKey(keyboardIdentifier, new com.android.server.input.KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype)).toString();
        synchronized (this.mDataStore) {
            try {
                try {
                    if (this.mDataStore.setKeyboardLayout(keyboardIdentifier.toString(), layoutKey, str)) {
                        if (DEBUG) {
                            android.util.Slog.d(TAG, "setKeyboardLayoutForInputDevice() " + inputDeviceIdentifier + " key: " + layoutKey + " keyboardLayoutDescriptor: " + str);
                        }
                        this.mHandler.sendEmptyMessage(3);
                    }
                    this.mDataStore.saveIfNeeded();
                } catch (java.lang.Throwable th) {
                    this.mDataStore.saveIfNeeded();
                    throw th;
                }
            } catch (java.lang.Throwable th2) {
                throw th2;
            }
        }
    }

    public android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (!useNewSettingsUi()) {
            android.util.Slog.e(TAG, "getKeyboardLayoutListForInputDevice() API not supported");
            return new android.hardware.input.KeyboardLayout[0];
        }
        android.view.InputDevice inputDevice = getInputDevice(inputDeviceIdentifier);
        if (inputDevice == null || inputDevice.isVirtual() || !inputDevice.isFullKeyboard()) {
            return new android.hardware.input.KeyboardLayout[0];
        }
        return getKeyboardLayoutListForInputDeviceInternal(new com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier(inputDevice), new com.android.server.input.KeyboardLayoutManager.ImeInfo(i, inputMethodInfo, inputMethodSubtype));
    }

    private android.hardware.input.KeyboardLayout[] getKeyboardLayoutListForInputDeviceInternal(final com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier, @android.annotation.Nullable com.android.server.input.KeyboardLayoutManager.ImeInfo imeInfo) {
        final java.lang.String keyboardLayout;
        final java.lang.String str;
        java.lang.String layoutKey = new com.android.server.input.KeyboardLayoutManager.LayoutKey(keyboardIdentifier, imeInfo).toString();
        synchronized (this.mDataStore) {
            keyboardLayout = this.mDataStore.getKeyboardLayout(keyboardIdentifier.toString(), layoutKey);
        }
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        if (imeInfo == null || imeInfo.mImeSubtype == null) {
            str = "";
        } else {
            android.icu.util.ULocale physicalKeyboardHintLanguageTag = imeInfo.mImeSubtype.getPhysicalKeyboardHintLanguageTag();
            str = physicalKeyboardHintLanguageTag != null ? physicalKeyboardHintLanguageTag.toLanguageTag() : imeInfo.mImeSubtype.getCanonicalizedLanguageTag();
        }
        visitAllKeyboardLayouts(new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor() { // from class: com.android.server.input.KeyboardLayoutManager.3
            boolean mDeviceSpecificLayoutAvailable;

            @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
            public void visitKeyboardLayout(android.content.res.Resources resources, int i, android.hardware.input.KeyboardLayout keyboardLayout2) {
                if (keyboardLayout2.getVendorId() == keyboardIdentifier.mIdentifier.getVendorId() && keyboardLayout2.getProductId() == keyboardIdentifier.mIdentifier.getProductId()) {
                    if (!this.mDeviceSpecificLayoutAvailable) {
                        this.mDeviceSpecificLayoutAvailable = true;
                        arrayList.clear();
                    }
                    arrayList.add(keyboardLayout2);
                    return;
                }
                if (keyboardLayout2.getVendorId() == -1 && keyboardLayout2.getProductId() == -1 && !this.mDeviceSpecificLayoutAvailable && com.android.server.input.KeyboardLayoutManager.isLayoutCompatibleWithLanguageTag(keyboardLayout2, str)) {
                    arrayList.add(keyboardLayout2);
                } else if (keyboardLayout2.getDescriptor().equals(keyboardLayout)) {
                    arrayList.add(keyboardLayout2);
                }
            }
        });
        java.util.Collections.sort(arrayList);
        return (android.hardware.input.KeyboardLayout[]) arrayList.toArray(new android.hardware.input.KeyboardLayout[0]);
    }

    public void onInputMethodSubtypeChanged(int i, @android.annotation.Nullable com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
        if (!useNewSettingsUi()) {
            android.util.Slog.e(TAG, "onInputMethodSubtypeChanged() API not supported");
            return;
        }
        if (inputMethodSubtypeHandle == null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "No InputMethod is running, ignoring change");
                return;
            }
            return;
        }
        synchronized (this.mImeInfoLock) {
            try {
                if (this.mCurrentImeInfo == null || !inputMethodSubtypeHandle.equals(this.mCurrentImeInfo.mImeSubtypeHandle) || this.mCurrentImeInfo.mUserId != i) {
                    this.mCurrentImeInfo = new com.android.server.input.KeyboardLayoutManager.ImeInfo(i, inputMethodSubtypeHandle, inputMethodSubtype);
                    this.mHandler.sendEmptyMessage(3);
                    if (DEBUG) {
                        android.util.Slog.d(TAG, "InputMethodSubtype changed: userId=" + i + " subtypeHandle=" + inputMethodSubtypeHandle);
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.Nullable
    private android.hardware.input.KeyboardLayoutSelectionResult getKeyboardLayoutForInputDeviceInternal(com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier, @android.annotation.Nullable com.android.server.input.KeyboardLayoutManager.ImeInfo imeInfo) {
        java.lang.String layoutKey = new com.android.server.input.KeyboardLayoutManager.LayoutKey(keyboardIdentifier, imeInfo).toString();
        synchronized (this.mDataStore) {
            try {
                java.lang.String keyboardLayout = this.mDataStore.getKeyboardLayout(keyboardIdentifier.toString(), layoutKey);
                if (keyboardLayout != null) {
                    return new android.hardware.input.KeyboardLayoutSelectionResult(keyboardLayout, 1);
                }
                synchronized (this.mKeyboardLayoutCache) {
                    try {
                        if (this.mKeyboardLayoutCache.containsKey(layoutKey)) {
                            return this.mKeyboardLayoutCache.get(layoutKey);
                        }
                        android.hardware.input.KeyboardLayoutSelectionResult defaultKeyboardLayoutBasedOnImeInfo = getDefaultKeyboardLayoutBasedOnImeInfo(keyboardIdentifier, imeInfo, getKeyboardLayoutListForInputDeviceInternal(keyboardIdentifier, imeInfo));
                        this.mKeyboardLayoutCache.put(layoutKey, defaultKeyboardLayoutBasedOnImeInfo);
                        return defaultKeyboardLayoutBasedOnImeInfo;
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    @android.annotation.NonNull
    private static android.hardware.input.KeyboardLayoutSelectionResult getDefaultKeyboardLayoutBasedOnImeInfo(com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier, @android.annotation.Nullable com.android.server.input.KeyboardLayoutManager.ImeInfo imeInfo, android.hardware.input.KeyboardLayout[] keyboardLayoutArr) {
        java.lang.String matchingLayoutForProvidedLanguageTagAndLayoutType;
        java.util.Arrays.sort(keyboardLayoutArr);
        for (android.hardware.input.KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            if (keyboardLayout.getVendorId() == keyboardIdentifier.mIdentifier.getVendorId() && keyboardLayout.getProductId() == keyboardIdentifier.mIdentifier.getProductId()) {
                if (DEBUG) {
                    android.util.Slog.d(TAG, "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on vendor and product Ids. " + keyboardIdentifier + " : " + keyboardLayout.getDescriptor());
                }
                return new android.hardware.input.KeyboardLayoutSelectionResult(keyboardLayout.getDescriptor(), 2);
            }
        }
        java.lang.String str = keyboardIdentifier.mLanguageTag;
        if (str != null && (matchingLayoutForProvidedLanguageTagAndLayoutType = getMatchingLayoutForProvidedLanguageTagAndLayoutType(keyboardLayoutArr, str, keyboardIdentifier.mLayoutType)) != null) {
            if (DEBUG) {
                android.util.Slog.d(TAG, "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on HW information (Language tag and Layout type). " + keyboardIdentifier + " : " + matchingLayoutForProvidedLanguageTagAndLayoutType);
            }
            return new android.hardware.input.KeyboardLayoutSelectionResult(matchingLayoutForProvidedLanguageTagAndLayoutType, 2);
        }
        if (imeInfo == null || imeInfo.mImeSubtypeHandle == null || imeInfo.mImeSubtype == null) {
            return android.hardware.input.KeyboardLayoutSelectionResult.FAILED;
        }
        android.view.inputmethod.InputMethodSubtype inputMethodSubtype = imeInfo.mImeSubtype;
        android.icu.util.ULocale physicalKeyboardHintLanguageTag = inputMethodSubtype.getPhysicalKeyboardHintLanguageTag();
        java.lang.String matchingLayoutForProvidedLanguageTagAndLayoutType2 = getMatchingLayoutForProvidedLanguageTagAndLayoutType(keyboardLayoutArr, physicalKeyboardHintLanguageTag != null ? physicalKeyboardHintLanguageTag.toLanguageTag() : inputMethodSubtype.getCanonicalizedLanguageTag(), inputMethodSubtype.getPhysicalKeyboardHintLayoutType());
        if (DEBUG) {
            android.util.Slog.d(TAG, "getDefaultKeyboardLayoutBasedOnImeInfo() : Layout found based on IME locale matching. " + keyboardIdentifier + " : " + matchingLayoutForProvidedLanguageTagAndLayoutType2);
        }
        if (matchingLayoutForProvidedLanguageTagAndLayoutType2 != null) {
            return new android.hardware.input.KeyboardLayoutSelectionResult(matchingLayoutForProvidedLanguageTagAndLayoutType2, 3);
        }
        return android.hardware.input.KeyboardLayoutSelectionResult.FAILED;
    }

    @android.annotation.Nullable
    private static java.lang.String getMatchingLayoutForProvidedLanguageTagAndLayoutType(android.hardware.input.KeyboardLayout[] keyboardLayoutArr, @android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
        if (str2 == null || !android.hardware.input.KeyboardLayout.isLayoutTypeValid(str2)) {
            str2 = "undefined";
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.hardware.input.KeyboardLayout keyboardLayout : keyboardLayoutArr) {
            if (keyboardLayout.getLayoutType().equals(str2)) {
                arrayList.add(keyboardLayout);
            }
        }
        java.lang.String matchingLayoutForProvidedLanguageTag = getMatchingLayoutForProvidedLanguageTag(arrayList, str);
        if (matchingLayoutForProvidedLanguageTag != null) {
            return matchingLayoutForProvidedLanguageTag;
        }
        return getMatchingLayoutForProvidedLanguageTag(java.util.Arrays.asList(keyboardLayoutArr), str);
    }

    @android.annotation.Nullable
    private static java.lang.String getMatchingLayoutForProvidedLanguageTag(java.util.List<android.hardware.input.KeyboardLayout> list, @android.annotation.NonNull java.lang.String str) {
        java.util.Locale forLanguageTag = java.util.Locale.forLanguageTag(str);
        java.lang.String str2 = null;
        java.lang.String str3 = null;
        for (android.hardware.input.KeyboardLayout keyboardLayout : list) {
            android.os.LocaleList locales = keyboardLayout.getLocales();
            for (int i = 0; i < locales.size(); i++) {
                java.util.Locale locale = locales.get(i);
                if (locale != null && locale.getLanguage().equals(forLanguageTag.getLanguage())) {
                    if (str3 == null) {
                        str3 = keyboardLayout.getDescriptor();
                    }
                    if (locale.getCountry().equals(forLanguageTag.getCountry())) {
                        if (str2 == null) {
                            str2 = keyboardLayout.getDescriptor();
                        }
                        if (locale.getVariant().equals(forLanguageTag.getVariant())) {
                            return keyboardLayout.getDescriptor();
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return str2 != null ? str2 : str3;
    }

    private void reloadKeyboardLayouts() {
        if (DEBUG) {
            android.util.Slog.d(TAG, "Reloading keyboard layouts.");
        }
        this.mNative.reloadKeyboardLayouts();
    }

    private void maybeUpdateNotification() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mConfiguredKeyboards.size(); i++) {
            int keyAt = this.mConfiguredKeyboards.keyAt(i);
            com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration valueAt = this.mConfiguredKeyboards.valueAt(i);
            if (!isVirtualDevice(keyAt)) {
                if (!valueAt.hasConfiguredLayouts()) {
                    showMissingKeyboardLayoutNotification();
                    return;
                }
                arrayList.add(valueAt);
            }
        }
        if (arrayList.size() == 0) {
            hideKeyboardLayoutNotification();
        } else {
            showConfiguredKeyboardLayoutNotification(arrayList);
        }
    }

    private void showMissingKeyboardLayoutNotification() {
        android.content.res.Resources resources = this.mContext.getResources();
        java.lang.String string = resources.getString(android.R.string.scCellularNetworkSecurityTitle);
        if (this.mConfiguredKeyboards.size() == 1) {
            android.view.InputDevice inputDevice = getInputDevice(this.mConfiguredKeyboards.keyAt(0));
            if (inputDevice == null) {
                return;
            }
            showKeyboardLayoutNotification(resources.getString(android.R.string.scIdentifierDisclosureIssueSummary, inputDevice.getName()), string, inputDevice);
            return;
        }
        showKeyboardLayoutNotification(resources.getString(android.R.string.scIdentifierDisclosureIssueTitle), string, null);
    }

    private void showKeyboardLayoutNotification(@android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.Nullable android.view.InputDevice inputDevice) {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        if (notificationManager == null) {
            return;
        }
        android.content.Intent intent = new android.content.Intent("android.settings.HARD_KEYBOARD_SETTINGS");
        if (inputDevice != null) {
            intent.putExtra("input_device_identifier", (android.os.Parcelable) inputDevice.getIdentifier());
            intent.putExtra("com.android.settings.inputmethod.EXTRA_ENTRYPOINT", 0);
        }
        intent.setFlags(337641472);
        notificationManager.notifyAsUser(null, 19, new android.app.Notification.Builder(this.mContext, com.android.internal.notification.SystemNotificationChannels.PHYSICAL_KEYBOARD).setContentTitle(str).setContentText(str2).setContentIntent(android.app.PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, null, android.os.UserHandle.CURRENT)).setSmallIcon(android.R.drawable.ic_settings_24dp).setColor(this.mContext.getColor(android.R.color.system_notification_accent_color)).setAutoCancel(true).build(), android.os.UserHandle.ALL);
    }

    private void hideKeyboardLayoutNotification() {
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) this.mContext.getSystemService(android.app.NotificationManager.class);
        if (notificationManager == null) {
            return;
        }
        notificationManager.cancelAsUser(null, 19, android.os.UserHandle.ALL);
    }

    private void showConfiguredKeyboardLayoutNotification(java.util.List<com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration> list) {
        android.content.res.Resources resources = this.mContext.getResources();
        if (list.size() != 1) {
            showKeyboardLayoutNotification(resources.getString(android.R.string.indeterminate_progress_58), resources.getString(android.R.string.indeterminate_progress_57), null);
            return;
        }
        com.android.server.input.KeyboardLayoutManager.KeyboardConfiguration keyboardConfiguration = list.get(0);
        android.view.InputDevice inputDevice = getInputDevice(keyboardConfiguration.getDeviceId());
        if (inputDevice == null || !keyboardConfiguration.hasConfiguredLayouts()) {
            return;
        }
        showKeyboardLayoutNotification(resources.getString(android.R.string.indeterminate_progress_60, inputDevice.getName()), createConfiguredNotificationText(this.mContext, keyboardConfiguration.getConfiguredLayouts()), inputDevice);
    }

    private java.lang.String createConfiguredNotificationText(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull java.util.Set<java.lang.String> set) {
        android.content.res.Resources resources = context.getResources();
        final java.util.ArrayList arrayList = new java.util.ArrayList();
        set.forEach(new java.util.function.Consumer() { // from class: com.android.server.input.KeyboardLayoutManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.input.KeyboardLayoutManager.this.lambda$createConfiguredNotificationText$7(arrayList, (java.lang.String) obj);
            }
        });
        java.util.Collections.sort(arrayList);
        switch (arrayList.size()) {
            case 1:
                return resources.getString(android.R.string.indeterminate_progress_59, arrayList.get(0));
            case 2:
                return resources.getString(android.R.string.inputMethod, arrayList.get(0), arrayList.get(1));
            case 3:
                return resources.getString(android.R.string.indeterminate_progress_background, arrayList.get(0), arrayList.get(1), arrayList.get(2));
            default:
                return resources.getString(android.R.string.indeterminate_progress_56, arrayList.get(0), arrayList.get(1), arrayList.get(2));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createConfiguredNotificationText$7(java.util.List list, java.lang.String str) {
        list.add(getKeyboardLayout(str).getLabel());
    }

    private void logKeyboardConfigurationEvent(@android.annotation.NonNull android.view.InputDevice inputDevice, @android.annotation.NonNull java.util.List<com.android.server.input.KeyboardLayoutManager.ImeInfo> list, @android.annotation.NonNull java.util.List<android.hardware.input.KeyboardLayoutSelectionResult> list2, boolean z) {
        int i;
        if (list.isEmpty() || list2.isEmpty()) {
            return;
        }
        com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent.Builder isFirstTimeConfiguration = new com.android.server.input.KeyboardMetricsCollector.KeyboardConfigurationEvent.Builder(inputDevice).setIsFirstTimeConfiguration(z);
        for (int i2 = 0; i2 < list.size(); i2++) {
            android.hardware.input.KeyboardLayoutSelectionResult keyboardLayoutSelectionResult = list2.get(i2);
            java.lang.String str = null;
            if (keyboardLayoutSelectionResult != null && keyboardLayoutSelectionResult.getLayoutDescriptor() != null) {
                i = keyboardLayoutSelectionResult.getSelectionCriteria();
                com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor parse = com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor.parse(keyboardLayoutSelectionResult.getLayoutDescriptor());
                if (parse != null) {
                    str = parse.keyboardLayoutName;
                }
            } else {
                i = 4;
            }
            isFirstTimeConfiguration.addLayoutSelection(list.get(i2).mImeSubtype, str, i);
        }
        com.android.server.input.KeyboardMetricsCollector.logKeyboardConfiguredAtom(isFirstTimeConfiguration.build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean handleMessage(android.os.Message message) {
        switch (message.what) {
            case 1:
                for (int i : (int[]) message.obj) {
                    onInputDeviceAdded(i);
                }
                return true;
            case 2:
                handleSwitchKeyboardLayout(message.arg1, message.arg2);
                return true;
            case 3:
                reloadKeyboardLayouts();
                return true;
            case 4:
                updateKeyboardLayouts();
                return true;
            default:
                return false;
        }
    }

    private boolean useNewSettingsUi() {
        return android.util.FeatureFlagUtils.isEnabled(this.mContext, "settings_new_keyboard_ui");
    }

    @android.annotation.Nullable
    private android.view.InputDevice getInputDevice(int i) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        if (inputManager != null) {
            return inputManager.getInputDevice(i);
        }
        return null;
    }

    @android.annotation.Nullable
    private android.view.InputDevice getInputDevice(android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
        android.hardware.input.InputManager inputManager = (android.hardware.input.InputManager) this.mContext.getSystemService(android.hardware.input.InputManager.class);
        if (inputManager != null) {
            return inputManager.getInputDeviceByDescriptor(inputDeviceIdentifier.getDescriptor());
        }
        return null;
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.SuppressLint({"MissingPermission"})
    public java.util.List<com.android.server.input.KeyboardLayoutManager.ImeInfo> getImeInfoListForLayoutMapping() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        android.os.UserManager userManager = (android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class);
        java.util.Objects.requireNonNull(userManager);
        android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager) this.mContext.getSystemService(android.view.inputmethod.InputMethodManager.class);
        java.util.Objects.requireNonNull(inputMethodManager);
        com.android.server.inputmethod.InputMethodManagerInternal inputMethodManagerInternal = com.android.server.inputmethod.InputMethodManagerInternal.get();
        java.util.Iterator it = userManager.getUserHandles(true).iterator();
        while (it.hasNext()) {
            int identifier = ((android.os.UserHandle) it.next()).getIdentifier();
            for (android.view.inputmethod.InputMethodInfo inputMethodInfo : inputMethodManagerInternal.getEnabledInputMethodListAsUser(identifier)) {
                for (android.view.inputmethod.InputMethodSubtype inputMethodSubtype : inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true)) {
                    if (inputMethodSubtype.isSuitableForPhysicalKeyboardLayoutMapping()) {
                        arrayList.add(new com.android.server.input.KeyboardLayoutManager.ImeInfo(identifier, inputMethodInfo, inputMethodSubtype));
                    }
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isLayoutCompatibleWithLanguageTag(android.hardware.input.KeyboardLayout keyboardLayout, @android.annotation.NonNull java.lang.String str) {
        android.os.LocaleList locales = keyboardLayout.getLocales();
        if (locales.isEmpty() || android.text.TextUtils.isEmpty(str)) {
            return true;
        }
        int[] scriptCodes = getScriptCodes(java.util.Locale.forLanguageTag(str));
        if (scriptCodes.length == 0) {
            return true;
        }
        for (int i = 0; i < locales.size(); i++) {
            if (haveCommonValue(getScriptCodes(locales.get(i)), scriptCodes)) {
                return true;
            }
        }
        return false;
    }

    @com.android.internal.annotations.VisibleForTesting
    public boolean isVirtualDevice(int i) {
        com.android.server.companion.virtual.VirtualDeviceManagerInternal virtualDeviceManagerInternal = (com.android.server.companion.virtual.VirtualDeviceManagerInternal) com.android.server.LocalServices.getService(com.android.server.companion.virtual.VirtualDeviceManagerInternal.class);
        return virtualDeviceManagerInternal != null && virtualDeviceManagerInternal.isInputDeviceOwnedByVirtualDevice(i);
    }

    private static int[] getScriptCodes(@android.annotation.Nullable java.util.Locale locale) {
        int codeFromName;
        if (locale == null) {
            return new int[0];
        }
        if (!android.text.TextUtils.isEmpty(locale.getScript()) && (codeFromName = android.icu.lang.UScript.getCodeFromName(locale.getScript())) != -1) {
            return new int[]{codeFromName};
        }
        int[] code = android.icu.lang.UScript.getCode(locale);
        if (code != null) {
            return code;
        }
        return new int[0];
    }

    private static boolean haveCommonValue(int[] iArr, int[] iArr2) {
        for (int i : iArr) {
            for (int i2 : iArr2) {
                if (i == i2) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final class KeyboardLayoutDescriptor {
        public java.lang.String keyboardLayoutName;
        public java.lang.String packageName;
        public java.lang.String receiverName;

        private KeyboardLayoutDescriptor() {
        }

        public static java.lang.String format(java.lang.String str, java.lang.String str2, java.lang.String str3) {
            return str + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str2 + com.android.server.slice.SliceClientPermissions.SliceAuthority.DELIMITER + str3;
        }

        public static com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor parse(java.lang.String str) {
            int i;
            int indexOf;
            int i2;
            int indexOf2 = str.indexOf(47);
            if (indexOf2 < 0 || (i = indexOf2 + 1) == str.length() || (indexOf = str.indexOf(47, i)) < indexOf2 + 2 || (i2 = indexOf + 1) == str.length()) {
                return null;
            }
            com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor keyboardLayoutDescriptor = new com.android.server.input.KeyboardLayoutManager.KeyboardLayoutDescriptor();
            keyboardLayoutDescriptor.packageName = str.substring(0, indexOf2);
            keyboardLayoutDescriptor.receiverName = str.substring(i, indexOf);
            keyboardLayoutDescriptor.keyboardLayoutName = str.substring(i2);
            return keyboardLayoutDescriptor;
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    public static class ImeInfo {

        @android.annotation.Nullable
        android.view.inputmethod.InputMethodSubtype mImeSubtype;

        @android.annotation.NonNull
        com.android.internal.inputmethod.InputMethodSubtypeHandle mImeSubtypeHandle;
        int mUserId;

        ImeInfo(int i, @android.annotation.NonNull com.android.internal.inputmethod.InputMethodSubtypeHandle inputMethodSubtypeHandle, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            this.mUserId = i;
            this.mImeSubtypeHandle = inputMethodSubtypeHandle;
            this.mImeSubtype = inputMethodSubtype;
        }

        ImeInfo(int i, @android.annotation.NonNull android.view.inputmethod.InputMethodInfo inputMethodInfo, @android.annotation.Nullable android.view.inputmethod.InputMethodSubtype inputMethodSubtype) {
            this(i, com.android.internal.inputmethod.InputMethodSubtypeHandle.of(inputMethodInfo, inputMethodSubtype), inputMethodSubtype);
        }
    }

    private static class KeyboardConfiguration {

        @android.annotation.Nullable
        private java.util.Set<java.lang.String> mConfiguredLayouts;
        private final int mDeviceId;

        private KeyboardConfiguration(int i) {
            this.mDeviceId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getDeviceId() {
            return this.mDeviceId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean hasConfiguredLayouts() {
            return (this.mConfiguredLayouts == null || this.mConfiguredLayouts.isEmpty()) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        @android.annotation.Nullable
        public java.util.Set<java.lang.String> getConfiguredLayouts() {
            return this.mConfiguredLayouts;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConfiguredLayouts(java.util.Set<java.lang.String> set) {
            this.mConfiguredLayouts = set;
        }
    }

    private static class KeyboardIdentifier {

        @android.annotation.NonNull
        private final android.hardware.input.InputDeviceIdentifier mIdentifier;

        @android.annotation.Nullable
        private final java.lang.String mLanguageTag;

        @android.annotation.Nullable
        private final java.lang.String mLayoutType;

        private KeyboardIdentifier(@android.annotation.NonNull android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier) {
            this(inputDeviceIdentifier, null, null);
        }

        private KeyboardIdentifier(@android.annotation.NonNull android.view.InputDevice inputDevice) {
            this(inputDevice.getIdentifier(), inputDevice.getKeyboardLanguageTag(), inputDevice.getKeyboardLayoutType());
        }

        private KeyboardIdentifier(@android.annotation.NonNull android.hardware.input.InputDeviceIdentifier inputDeviceIdentifier, @android.annotation.Nullable java.lang.String str, @android.annotation.Nullable java.lang.String str2) {
            java.util.Objects.requireNonNull(inputDeviceIdentifier, "identifier must not be null");
            java.util.Objects.requireNonNull(inputDeviceIdentifier.getDescriptor(), "descriptor must not be null");
            this.mIdentifier = inputDeviceIdentifier;
            this.mLanguageTag = str;
            this.mLayoutType = str2;
        }

        public int hashCode() {
            return java.util.Objects.hashCode(toString());
        }

        public java.lang.String toString() {
            if (this.mIdentifier.getVendorId() == 0 && this.mIdentifier.getProductId() == 0) {
                return this.mIdentifier.getDescriptor();
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("vendor:");
            sb.append(this.mIdentifier.getVendorId());
            sb.append(",product:");
            sb.append(this.mIdentifier.getProductId());
            if (!android.text.TextUtils.isEmpty(this.mLanguageTag)) {
                sb.append(",languageTag:");
                sb.append(this.mLanguageTag);
            }
            if (!android.text.TextUtils.isEmpty(this.mLayoutType)) {
                sb.append(",layoutType:");
                sb.append(this.mLayoutType);
            }
            return sb.toString();
        }
    }

    private static class LayoutKey {

        @android.annotation.Nullable
        private final com.android.server.input.KeyboardLayoutManager.ImeInfo mImeInfo;
        private final com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier mKeyboardIdentifier;

        private LayoutKey(com.android.server.input.KeyboardLayoutManager.KeyboardIdentifier keyboardIdentifier, @android.annotation.Nullable com.android.server.input.KeyboardLayoutManager.ImeInfo imeInfo) {
            this.mKeyboardIdentifier = keyboardIdentifier;
            this.mImeInfo = imeInfo;
        }

        public int hashCode() {
            return java.util.Objects.hashCode(toString());
        }

        public java.lang.String toString() {
            if (this.mImeInfo == null) {
                return this.mKeyboardIdentifier.toString();
            }
            java.util.Objects.requireNonNull(this.mImeInfo.mImeSubtypeHandle, "subtypeHandle must not be null");
            return "layoutDescriptor:" + this.mKeyboardIdentifier + ",userId:" + this.mImeInfo.mUserId + ",subtypeHandle:" + this.mImeInfo.mImeSubtypeHandle.toStringHandle();
        }
    }
}
