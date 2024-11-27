package android.hardware.soundtrigger;

/* loaded from: classes2.dex */
public class KeyphraseEnrollmentInfo {
    public static final java.lang.String ACTION_MANAGE_VOICE_KEYPHRASES = "com.android.intent.action.MANAGE_VOICE_KEYPHRASES";
    public static final java.lang.String EXTRA_VOICE_KEYPHRASE_ACTION = "com.android.intent.extra.VOICE_KEYPHRASE_ACTION";
    public static final java.lang.String EXTRA_VOICE_KEYPHRASE_HINT_TEXT = "com.android.intent.extra.VOICE_KEYPHRASE_HINT_TEXT";
    public static final java.lang.String EXTRA_VOICE_KEYPHRASE_LOCALE = "com.android.intent.extra.VOICE_KEYPHRASE_LOCALE";
    public static final int MANAGE_ACTION_ENROLL = 0;
    public static final int MANAGE_ACTION_RE_ENROLL = 1;
    public static final int MANAGE_ACTION_UN_ENROLL = 2;
    private static final java.lang.String TAG = "KeyphraseEnrollmentInfo";
    private static final java.lang.String VOICE_KEYPHRASE_META_DATA = "android.voice_enrollment";
    private final java.util.Map<android.hardware.soundtrigger.KeyphraseMetadata, java.lang.String> mKeyphrasePackageMap;
    private final android.hardware.soundtrigger.KeyphraseMetadata[] mKeyphrases;
    private java.lang.String mParseError;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ManageActions {
    }

    public KeyphraseEnrollmentInfo(android.content.pm.PackageManager packageManager) {
        java.util.Objects.requireNonNull(packageManager);
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new android.content.Intent(ACTION_MANAGE_VOICE_KEYPHRASES), 65536);
        if (queryIntentServices == null || queryIntentServices.isEmpty()) {
            this.mParseError = "No enrollment applications found";
            this.mKeyphrasePackageMap = java.util.Collections.emptyMap();
            this.mKeyphrases = null;
            return;
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        this.mKeyphrasePackageMap = new java.util.HashMap();
        for (android.content.pm.ResolveInfo resolveInfo : queryIntentServices) {
            try {
                android.content.pm.ApplicationInfo applicationInfo = packageManager.getApplicationInfo(resolveInfo.serviceInfo.packageName, 128);
                if ((applicationInfo.privateFlags & 8) == 0) {
                    android.util.Slog.w(TAG, applicationInfo.packageName + " is not a privileged system app");
                } else if (!android.Manifest.permission.MANAGE_VOICE_KEYPHRASES.equals(applicationInfo.permission)) {
                    android.util.Slog.w(TAG, applicationInfo.packageName + " does not require MANAGE_VOICE_KEYPHRASES");
                } else {
                    android.hardware.soundtrigger.KeyphraseMetadata keyphraseMetadataFromApplicationInfo = getKeyphraseMetadataFromApplicationInfo(packageManager, applicationInfo, arrayList);
                    if (keyphraseMetadataFromApplicationInfo != null) {
                        this.mKeyphrasePackageMap.put(keyphraseMetadataFromApplicationInfo, applicationInfo.packageName);
                    }
                }
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                java.lang.String str = "error parsing voice enrollment meta-data for " + resolveInfo.serviceInfo.packageName;
                arrayList.add(str + ": " + e);
                android.util.Slog.w(TAG, str, e);
            }
        }
        if (this.mKeyphrasePackageMap.isEmpty()) {
            arrayList.add("No suitable enrollment application found");
            android.util.Slog.w(TAG, "No suitable enrollment application found");
            this.mKeyphrases = null;
        } else {
            this.mKeyphrases = (android.hardware.soundtrigger.KeyphraseMetadata[]) this.mKeyphrasePackageMap.keySet().toArray(new android.hardware.soundtrigger.KeyphraseMetadata[0]);
        }
        if (!arrayList.isEmpty()) {
            this.mParseError = android.text.TextUtils.join("\n", arrayList);
        }
    }

    private android.hardware.soundtrigger.KeyphraseMetadata getKeyphraseMetadataFromApplicationInfo(android.content.pm.PackageManager packageManager, android.content.pm.ApplicationInfo applicationInfo, java.util.List<java.lang.String> list) {
        android.hardware.soundtrigger.KeyphraseMetadata keyphraseMetadata;
        android.content.res.XmlResourceParser loadXmlMetaData;
        int next;
        java.lang.String str = applicationInfo.packageName;
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                loadXmlMetaData = applicationInfo.loadXmlMetaData(packageManager, VOICE_KEYPHRASE_META_DATA);
            } catch (java.lang.Throwable th) {
                th = th;
            }
            try {
                try {
                    if (loadXmlMetaData == null) {
                        java.lang.String str2 = "No android.voice_enrollment meta-data for " + str;
                        list.add(str2);
                        android.util.Slog.w(TAG, str2);
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(applicationInfo);
                    android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"voice-enrollment-application".equals(loadXmlMetaData.getName())) {
                        java.lang.String str3 = "Meta-data does not start with voice-enrollment-application tag for " + str;
                        list.add(str3);
                        android.util.Slog.w(TAG, str3);
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.VoiceEnrollmentApplication);
                    android.hardware.soundtrigger.KeyphraseMetadata keyphraseFromTypedArray = getKeyphraseFromTypedArray(obtainAttributes, str, list);
                    obtainAttributes.recycle();
                    if (loadXmlMetaData == null) {
                        return keyphraseFromTypedArray;
                    }
                    loadXmlMetaData.close();
                    return keyphraseFromTypedArray;
                } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e) {
                    e = e;
                    keyphraseMetadata = null;
                    xmlResourceParser = loadXmlMetaData;
                    java.lang.String str4 = "Error parsing keyphrase enrollment meta-data for " + str;
                    list.add(str4 + ": " + e);
                    android.util.Slog.w(TAG, str4, e);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return keyphraseMetadata;
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                xmlResourceParser = loadXmlMetaData;
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                throw th;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException | java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            e = e2;
            keyphraseMetadata = null;
        }
    }

    private android.hardware.soundtrigger.KeyphraseMetadata getKeyphraseFromTypedArray(android.content.res.TypedArray typedArray, java.lang.String str, java.util.List<java.lang.String> list) {
        int i = typedArray.getInt(0, -1);
        if (i <= 0) {
            java.lang.String str2 = "No valid searchKeyphraseId specified in meta-data for " + str;
            list.add(str2);
            android.util.Slog.w(TAG, str2);
            return null;
        }
        java.lang.String string = typedArray.getString(1);
        if (string == null) {
            java.lang.String str3 = "No valid searchKeyphrase specified in meta-data for " + str;
            list.add(str3);
            android.util.Slog.w(TAG, str3);
            return null;
        }
        java.lang.String string2 = typedArray.getString(2);
        if (string2 == null) {
            java.lang.String str4 = "No valid searchKeyphraseSupportedLocales specified in meta-data for " + str;
            list.add(str4);
            android.util.Slog.w(TAG, str4);
            return null;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        if (!android.text.TextUtils.isEmpty(string2)) {
            try {
                for (java.lang.String str5 : string2.split(",")) {
                    arraySet.add(java.util.Locale.forLanguageTag(str5));
                }
            } catch (java.lang.Exception e) {
                java.lang.String str6 = "Error reading searchKeyphraseSupportedLocales from meta-data for " + str;
                list.add(str6);
                android.util.Slog.w(TAG, str6);
                return null;
            }
        }
        int i2 = typedArray.getInt(3, -1);
        if (i2 < 0) {
            java.lang.String str7 = "No valid searchKeyphraseRecognitionFlags specified in meta-data for " + str;
            list.add(str7);
            android.util.Slog.w(TAG, str7);
            return null;
        }
        return new android.hardware.soundtrigger.KeyphraseMetadata(i, string, arraySet, i2);
    }

    public java.lang.String getParseError() {
        return this.mParseError;
    }

    public java.util.Collection<android.hardware.soundtrigger.KeyphraseMetadata> listKeyphraseMetadata() {
        return java.util.Arrays.asList(this.mKeyphrases);
    }

    public android.content.Intent getManageKeyphraseIntent(int i, java.lang.String str, java.util.Locale locale) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        if (this.mKeyphrasePackageMap == null || this.mKeyphrasePackageMap.isEmpty()) {
            android.util.Slog.w(TAG, "No enrollment application exists");
            return null;
        }
        android.hardware.soundtrigger.KeyphraseMetadata keyphraseMetadata = getKeyphraseMetadata(str, locale);
        if (keyphraseMetadata != null) {
            return new android.content.Intent(ACTION_MANAGE_VOICE_KEYPHRASES).setPackage(this.mKeyphrasePackageMap.get(keyphraseMetadata)).putExtra(EXTRA_VOICE_KEYPHRASE_HINT_TEXT, str).putExtra(EXTRA_VOICE_KEYPHRASE_LOCALE, locale.toLanguageTag()).putExtra(EXTRA_VOICE_KEYPHRASE_ACTION, i);
        }
        return null;
    }

    public android.hardware.soundtrigger.KeyphraseMetadata getKeyphraseMetadata(java.lang.String str, java.util.Locale locale) {
        java.util.Objects.requireNonNull(str);
        java.util.Objects.requireNonNull(locale);
        if (this.mKeyphrases != null && this.mKeyphrases.length > 0) {
            for (android.hardware.soundtrigger.KeyphraseMetadata keyphraseMetadata : this.mKeyphrases) {
                if (keyphraseMetadata.supportsPhrase(str) && keyphraseMetadata.supportsLocale(locale)) {
                    return keyphraseMetadata;
                }
            }
        }
        android.util.Slog.w(TAG, "No enrollment application supports the given keyphrase/locale: '" + str + "'/" + locale);
        return null;
    }

    public java.lang.String toString() {
        return "KeyphraseEnrollmentInfo [KeyphrasePackageMap=" + this.mKeyphrasePackageMap.toString() + ", ParseError=" + this.mParseError + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END;
    }
}
