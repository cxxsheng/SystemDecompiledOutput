package android.app;

/* loaded from: classes.dex */
public class LocaleConfig implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.LocaleConfig> CREATOR = new android.os.Parcelable.Creator<android.app.LocaleConfig>() { // from class: android.app.LocaleConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.LocaleConfig createFromParcel(android.os.Parcel parcel) {
            return new android.app.LocaleConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.LocaleConfig[] newArray(int i) {
            return new android.app.LocaleConfig[i];
        }
    };
    public static final int STATUS_NOT_SPECIFIED = 1;
    public static final int STATUS_PARSING_FAILED = 2;
    public static final int STATUS_SUCCESS = 0;
    private static final java.lang.String TAG = "LocaleConfig";
    public static final java.lang.String TAG_LOCALE = "locale";
    public static final java.lang.String TAG_LOCALE_CONFIG = "locale-config";
    private java.util.Locale mDefaultLocale;
    private android.os.LocaleList mLocales;
    private int mStatus;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Status {
    }

    public LocaleConfig(android.content.Context context) {
        this(context, true);
    }

    public static android.app.LocaleConfig fromContextIgnoringOverride(android.content.Context context) {
        return new android.app.LocaleConfig(context, false);
    }

    private LocaleConfig(android.content.Context context, boolean z) {
        this.mStatus = 1;
        if (z) {
            android.app.LocaleManager localeManager = (android.app.LocaleManager) context.getSystemService(android.app.LocaleManager.class);
            if (localeManager == null) {
                android.util.Slog.w(TAG, "LocaleManager is null, cannot get the override LocaleConfig");
                this.mStatus = 1;
                return;
            }
            android.app.LocaleConfig overrideLocaleConfig = localeManager.getOverrideLocaleConfig();
            if (overrideLocaleConfig != null) {
                android.util.Slog.d(TAG, "Has the override LocaleConfig");
                this.mStatus = overrideLocaleConfig.getStatus();
                this.mLocales = overrideLocaleConfig.getSupportedLocales();
                return;
            }
        }
        android.content.res.Resources resources = context.getResources();
        int localeConfigRes = context.getApplicationInfo().getLocaleConfigRes();
        if (localeConfigRes == 0) {
            this.mStatus = 1;
            return;
        }
        try {
            parseLocaleConfig(resources.getXml(localeConfigRes), resources);
        } catch (android.content.res.Resources.NotFoundException e) {
            android.util.Slog.w(TAG, "The resource file pointed to by the given resource ID isn't found.");
            this.mStatus = 1;
        } catch (java.io.IOException | org.xmlpull.v1.XmlPullParserException e2) {
            android.util.Slog.w(TAG, "Failed to parse XML configuration from " + resources.getResourceEntryName(localeConfigRes), e2);
            this.mStatus = 2;
        }
    }

    public LocaleConfig(android.os.LocaleList localeList) {
        this.mStatus = 1;
        this.mStatus = 0;
        this.mLocales = localeList;
    }

    private LocaleConfig(android.os.Parcel parcel) {
        this.mStatus = 1;
        this.mStatus = parcel.readInt();
        this.mLocales = (android.os.LocaleList) parcel.readTypedObject(android.os.LocaleList.CREATOR);
    }

    private void parseLocaleConfig(android.content.res.XmlResourceParser xmlResourceParser, android.content.res.Resources resources) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String str;
        com.android.internal.util.XmlUtils.beginDocument(xmlResourceParser, TAG_LOCALE_CONFIG);
        int depth = xmlResourceParser.getDepth();
        android.util.AttributeSet asAttributeSet = android.util.Xml.asAttributeSet(xmlResourceParser);
        if (!android.content.res.Flags.defaultLocale()) {
            str = null;
        } else {
            android.content.res.TypedArray obtainAttributes = resources.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.LocaleConfig);
            str = obtainAttributes.getString(0);
            obtainAttributes.recycle();
        }
        java.util.HashSet hashSet = new java.util.HashSet();
        while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if ("locale".equals(xmlResourceParser.getName())) {
                android.content.res.TypedArray obtainAttributes2 = resources.obtainAttributes(asAttributeSet, com.android.internal.R.styleable.LocaleConfig_Locale);
                hashSet.add(obtainAttributes2.getString(0));
                obtainAttributes2.recycle();
            } else {
                com.android.internal.util.XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        this.mStatus = 0;
        this.mLocales = android.os.LocaleList.forLanguageTags(java.lang.String.join(",", hashSet));
        if (str != null) {
            if (hashSet.contains(str)) {
                this.mDefaultLocale = java.util.Locale.forLanguageTag(str);
            } else {
                android.util.Slog.w(TAG, "Default locale specified that is not contained in the list: " + str);
                this.mStatus = 2;
            }
        }
    }

    public android.os.LocaleList getSupportedLocales() {
        return this.mLocales;
    }

    public java.util.Locale getDefaultLocale() {
        return this.mDefaultLocale;
    }

    public int getStatus() {
        return this.mStatus;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStatus);
        parcel.writeTypedObject(this.mLocales, i);
    }

    public boolean isSameLocaleConfig(android.app.LocaleConfig localeConfig) {
        if (localeConfig == this) {
            return true;
        }
        if (localeConfig == null || this.mStatus != localeConfig.mStatus) {
            return false;
        }
        android.os.LocaleList localeList = localeConfig.mLocales;
        if (this.mLocales == null && localeList == null) {
            return true;
        }
        if (this.mLocales != null && localeList != null) {
            java.util.List asList = java.util.Arrays.asList(this.mLocales.toLanguageTags().split(","));
            java.util.List asList2 = java.util.Arrays.asList(localeList.toLanguageTags().split(","));
            java.util.Collections.sort(asList);
            java.util.Collections.sort(asList2);
            return asList.equals(asList2);
        }
        return false;
    }

    public boolean containsLocale(java.util.Locale locale) {
        if (this.mLocales == null) {
            return false;
        }
        for (int i = 0; i < this.mLocales.size(); i++) {
            if (android.os.LocaleList.matchesLanguageAndScript(this.mLocales.get(i), locale)) {
                return true;
            }
        }
        return false;
    }
}
