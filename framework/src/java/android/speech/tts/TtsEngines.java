package android.speech.tts;

/* loaded from: classes3.dex */
public class TtsEngines {
    private static final boolean DBG = false;
    private static final java.lang.String LOCALE_DELIMITER_NEW = "_";
    private static final java.lang.String LOCALE_DELIMITER_OLD = "-";
    private static final java.lang.String TAG = "TtsEngines";
    private static final java.lang.String XML_TAG_NAME = "tts-engine";
    private static final java.util.Map<java.lang.String, java.lang.String> sNormalizeCountry;
    private static final java.util.Map<java.lang.String, java.lang.String> sNormalizeLanguage;
    private final android.content.Context mContext;

    static {
        java.util.HashMap hashMap = new java.util.HashMap();
        for (java.lang.String str : java.util.Locale.getISOLanguages()) {
            try {
                hashMap.put(new java.util.Locale(str).getISO3Language(), str);
            } catch (java.util.MissingResourceException e) {
            }
        }
        sNormalizeLanguage = java.util.Collections.unmodifiableMap(hashMap);
        java.util.HashMap hashMap2 = new java.util.HashMap();
        for (java.lang.String str2 : java.util.Locale.getISOCountries()) {
            try {
                hashMap2.put(new java.util.Locale("", str2).getISO3Country(), str2);
            } catch (java.util.MissingResourceException e2) {
            }
        }
        sNormalizeCountry = java.util.Collections.unmodifiableMap(hashMap2);
    }

    public TtsEngines(android.content.Context context) {
        this.mContext = context;
    }

    public java.lang.String getDefaultEngine() {
        java.lang.String string = android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings.Secure.TTS_DEFAULT_SYNTH);
        return isEngineInstalled(string) ? string : getHighestRankedEngineName();
    }

    public java.lang.String getHighestRankedEngineName() {
        java.util.List<android.speech.tts.TextToSpeech.EngineInfo> engines = getEngines();
        if (engines.size() > 0 && engines.get(0).system) {
            return engines.get(0).name;
        }
        return null;
    }

    public android.speech.tts.TextToSpeech.EngineInfo getEngineInfo(java.lang.String str) {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent intent = new android.content.Intent(android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 65536);
        if (queryIntentServices != null && queryIntentServices.size() == 1) {
            return getEngineInfo(queryIntentServices.get(0), packageManager);
        }
        return null;
    }

    public java.util.List<android.speech.tts.TextToSpeech.EngineInfo> getEngines() {
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(new android.content.Intent(android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE), 65536);
        if (queryIntentServices == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(queryIntentServices.size());
        java.util.Iterator<android.content.pm.ResolveInfo> it = queryIntentServices.iterator();
        while (it.hasNext()) {
            android.speech.tts.TextToSpeech.EngineInfo engineInfo = getEngineInfo(it.next(), packageManager);
            if (engineInfo != null) {
                arrayList.add(engineInfo);
            }
        }
        java.util.Collections.sort(arrayList, android.speech.tts.TtsEngines.EngineInfoComparator.INSTANCE);
        return arrayList;
    }

    private boolean isSystemEngine(android.content.pm.ServiceInfo serviceInfo) {
        android.content.pm.ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        return (applicationInfo == null || (applicationInfo.flags & 1) == 0) ? false : true;
    }

    public boolean isEngineInstalled(java.lang.String str) {
        return (str == null || getEngineInfo(str) == null) ? false : true;
    }

    public android.content.Intent getSettingsIntent(java.lang.String str) {
        android.content.pm.ServiceInfo serviceInfo;
        java.lang.String str2;
        android.content.pm.PackageManager packageManager = this.mContext.getPackageManager();
        android.content.Intent intent = new android.content.Intent(android.speech.tts.TextToSpeech.Engine.INTENT_ACTION_TTS_SERVICE);
        intent.setPackage(str);
        java.util.List<android.content.pm.ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 65664);
        if (queryIntentServices != null && queryIntentServices.size() == 1 && (serviceInfo = queryIntentServices.get(0).serviceInfo) != null && (str2 = settingsActivityFromServiceInfo(serviceInfo, packageManager)) != null) {
            android.content.Intent intent2 = new android.content.Intent();
            intent2.setClassName(str, str2);
            return intent2;
        }
        return null;
    }

    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0106: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:49:0x0106 */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0109  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String settingsActivityFromServiceInfo(android.content.pm.ServiceInfo serviceInfo, android.content.pm.PackageManager packageManager) {
        android.content.res.XmlResourceParser xmlResourceParser;
        android.content.res.XmlResourceParser xmlResourceParser2;
        int next;
        android.content.res.XmlResourceParser xmlResourceParser3 = null;
        try {
            try {
                xmlResourceParser = serviceInfo.loadXmlMetaData(packageManager, android.speech.tts.TextToSpeech.Engine.SERVICE_META_DATA);
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                xmlResourceParser = null;
            } catch (java.io.IOException e2) {
                e = e2;
                xmlResourceParser = null;
            } catch (org.xmlpull.v1.XmlPullParserException e3) {
                e = e3;
                xmlResourceParser = null;
            } catch (java.lang.Throwable th) {
                th = th;
                if (xmlResourceParser3 != null) {
                }
                throw th;
            }
            try {
                if (xmlResourceParser == null) {
                    android.util.Log.w(TAG, "No meta-data found for :" + serviceInfo);
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return null;
                }
                android.content.res.Resources resourcesForApplication = packageManager.getResourcesForApplication(serviceInfo.applicationInfo);
                do {
                    next = xmlResourceParser.next();
                    if (next == 1) {
                        if (xmlResourceParser != null) {
                            xmlResourceParser.close();
                        }
                        return null;
                    }
                } while (next != 2);
                if (!XML_TAG_NAME.equals(xmlResourceParser.getName())) {
                    android.util.Log.w(TAG, "Package " + serviceInfo + " uses unknown tag :" + xmlResourceParser.getName());
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    return null;
                }
                android.content.res.TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(android.util.Xml.asAttributeSet(xmlResourceParser), com.android.internal.R.styleable.TextToSpeechEngine);
                java.lang.String string = obtainAttributes.getString(0);
                obtainAttributes.recycle();
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return string;
            } catch (android.content.pm.PackageManager.NameNotFoundException e4) {
                android.util.Log.w(TAG, "Could not load resources for : " + serviceInfo);
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return null;
            } catch (java.io.IOException e5) {
                e = e5;
                android.util.Log.w(TAG, "Error parsing metadata for " + serviceInfo + ":" + e);
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return null;
            } catch (org.xmlpull.v1.XmlPullParserException e6) {
                e = e6;
                android.util.Log.w(TAG, "Error parsing metadata for " + serviceInfo + ":" + e);
                if (xmlResourceParser != null) {
                    xmlResourceParser.close();
                }
                return null;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
            xmlResourceParser3 = xmlResourceParser2;
            if (xmlResourceParser3 != null) {
                xmlResourceParser3.close();
            }
            throw th;
        }
    }

    private android.speech.tts.TextToSpeech.EngineInfo getEngineInfo(android.content.pm.ResolveInfo resolveInfo, android.content.pm.PackageManager packageManager) {
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        if (serviceInfo != null) {
            android.speech.tts.TextToSpeech.EngineInfo engineInfo = new android.speech.tts.TextToSpeech.EngineInfo();
            engineInfo.name = serviceInfo.packageName;
            java.lang.CharSequence loadLabel = serviceInfo.loadLabel(packageManager);
            engineInfo.label = android.text.TextUtils.isEmpty(loadLabel) ? engineInfo.name : loadLabel.toString();
            engineInfo.icon = serviceInfo.getIconResource();
            engineInfo.priority = resolveInfo.priority;
            engineInfo.system = isSystemEngine(serviceInfo);
            return engineInfo;
        }
        return null;
    }

    private static class EngineInfoComparator implements java.util.Comparator<android.speech.tts.TextToSpeech.EngineInfo> {
        static android.speech.tts.TtsEngines.EngineInfoComparator INSTANCE = new android.speech.tts.TtsEngines.EngineInfoComparator();

        private EngineInfoComparator() {
        }

        @Override // java.util.Comparator
        public int compare(android.speech.tts.TextToSpeech.EngineInfo engineInfo, android.speech.tts.TextToSpeech.EngineInfo engineInfo2) {
            if (engineInfo.system && !engineInfo2.system) {
                return -1;
            }
            if (engineInfo2.system && !engineInfo.system) {
                return 1;
            }
            return engineInfo2.priority - engineInfo.priority;
        }
    }

    public java.util.Locale getLocalePrefForEngine(java.lang.String str) {
        return getLocalePrefForEngine(str, android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings.Secure.TTS_DEFAULT_LOCALE));
    }

    public java.util.Locale getLocalePrefForEngine(java.lang.String str, java.lang.String str2) {
        java.lang.String parseEnginePrefFromList = parseEnginePrefFromList(str2, str);
        if (android.text.TextUtils.isEmpty(parseEnginePrefFromList)) {
            return java.util.Locale.getDefault();
        }
        java.util.Locale parseLocaleString = parseLocaleString(parseEnginePrefFromList);
        if (parseLocaleString == null) {
            android.util.Log.w(TAG, "Failed to parse locale " + parseEnginePrefFromList + ", returning en_US instead");
            return java.util.Locale.US;
        }
        return parseLocaleString;
    }

    public boolean isLocaleSetToDefaultForEngine(java.lang.String str) {
        return android.text.TextUtils.isEmpty(parseEnginePrefFromList(android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings.Secure.TTS_DEFAULT_LOCALE), str));
    }

    public java.util.Locale parseLocaleString(java.lang.String str) {
        java.lang.String str2;
        java.lang.String str3;
        java.lang.String str4 = "";
        if (android.text.TextUtils.isEmpty(str)) {
            str2 = "";
            str3 = str2;
        } else {
            java.lang.String[] split = str.split("[-_]");
            java.lang.String lowerCase = split[0].toLowerCase();
            if (split.length == 0) {
                android.util.Log.w(TAG, "Failed to convert " + str + " to a valid Locale object. Only separators");
                return null;
            }
            if (split.length > 3) {
                android.util.Log.w(TAG, "Failed to convert " + str + " to a valid Locale object. Too many separators");
                return null;
            }
            if (split.length < 2) {
                str3 = "";
            } else {
                str3 = split[1].toUpperCase();
            }
            if (split.length < 3) {
                str2 = "";
                str4 = lowerCase;
            } else {
                str2 = split[2];
                str4 = lowerCase;
            }
        }
        java.lang.String str5 = sNormalizeLanguage.get(str4);
        if (str5 != null) {
            str4 = str5;
        }
        java.lang.String str6 = sNormalizeCountry.get(str3);
        if (str6 != null) {
            str3 = str6;
        }
        java.util.Locale locale = new java.util.Locale(str4, str3, str2);
        try {
            locale.getISO3Language();
            locale.getISO3Country();
            return locale;
        } catch (java.util.MissingResourceException e) {
            android.util.Log.w(TAG, "Failed to convert " + str + " to a valid Locale object.");
            return null;
        }
    }

    public static java.util.Locale normalizeTTSLocale(java.util.Locale locale) {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String language = locale.getLanguage();
        if (!android.text.TextUtils.isEmpty(language) && (str2 = sNormalizeLanguage.get(language)) != null) {
            language = str2;
        }
        java.lang.String country = locale.getCountry();
        if (!android.text.TextUtils.isEmpty(country) && (str = sNormalizeCountry.get(country)) != null) {
            country = str;
        }
        return new java.util.Locale(language, country, locale.getVariant());
    }

    public static java.lang.String[] toOldLocaleStringFormat(java.util.Locale locale) {
        java.lang.String[] strArr = {"", "", ""};
        try {
            strArr[0] = locale.getISO3Language();
            strArr[1] = locale.getISO3Country();
            strArr[2] = locale.getVariant();
            return strArr;
        } catch (java.util.MissingResourceException e) {
            return new java.lang.String[]{"eng", "USA", ""};
        }
    }

    private static java.lang.String parseEnginePrefFromList(java.lang.String str, java.lang.String str2) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        for (java.lang.String str3 : str.split(",")) {
            int indexOf = str3.indexOf(58);
            if (indexOf > 0 && str2.equals(str3.substring(0, indexOf))) {
                return str3.substring(indexOf + 1);
            }
        }
        return null;
    }

    public synchronized void updateLocalePrefForEngine(java.lang.String str, java.util.Locale locale) {
        android.provider.Settings.Secure.putString(this.mContext.getContentResolver(), android.provider.Settings.Secure.TTS_DEFAULT_LOCALE, updateValueInCommaSeparatedList(android.provider.Settings.Secure.getString(this.mContext.getContentResolver(), android.provider.Settings.Secure.TTS_DEFAULT_LOCALE), str, locale != null ? locale.toString() : "").toString());
    }

    private java.lang.String updateValueInCommaSeparatedList(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (android.text.TextUtils.isEmpty(str)) {
            sb.append(str2).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append(str3);
        } else {
            boolean z = true;
            boolean z2 = false;
            for (java.lang.String str4 : str.split(",")) {
                int indexOf = str4.indexOf(58);
                if (indexOf > 0) {
                    if (str2.equals(str4.substring(0, indexOf))) {
                        if (z) {
                            z = false;
                        } else {
                            sb.append(',');
                        }
                        sb.append(str2).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append(str3);
                        z2 = true;
                    } else {
                        if (z) {
                            z = false;
                        } else {
                            sb.append(',');
                        }
                        sb.append(str4);
                    }
                }
            }
            if (!z2) {
                sb.append(',');
                sb.append(str2).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append(str3);
            }
        }
        return sb.toString();
    }
}
