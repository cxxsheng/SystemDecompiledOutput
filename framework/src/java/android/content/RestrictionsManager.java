package android.content;

/* loaded from: classes.dex */
public class RestrictionsManager {
    public static final java.lang.String ACTION_PERMISSION_RESPONSE_RECEIVED = "android.content.action.PERMISSION_RESPONSE_RECEIVED";
    public static final java.lang.String ACTION_REQUEST_LOCAL_APPROVAL = "android.content.action.REQUEST_LOCAL_APPROVAL";
    public static final java.lang.String ACTION_REQUEST_PERMISSION = "android.content.action.REQUEST_PERMISSION";
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.content.extra.PACKAGE_NAME";
    public static final java.lang.String EXTRA_REQUEST_BUNDLE = "android.content.extra.REQUEST_BUNDLE";
    public static final java.lang.String EXTRA_REQUEST_ID = "android.content.extra.REQUEST_ID";
    public static final java.lang.String EXTRA_REQUEST_TYPE = "android.content.extra.REQUEST_TYPE";
    public static final java.lang.String EXTRA_RESPONSE_BUNDLE = "android.content.extra.RESPONSE_BUNDLE";
    public static final java.lang.String META_DATA_APP_RESTRICTIONS = "android.content.APP_RESTRICTIONS";
    public static final java.lang.String REQUEST_KEY_APPROVE_LABEL = "android.request.approve_label";
    public static final java.lang.String REQUEST_KEY_DATA = "android.request.data";
    public static final java.lang.String REQUEST_KEY_DENY_LABEL = "android.request.deny_label";
    public static final java.lang.String REQUEST_KEY_ICON = "android.request.icon";
    public static final java.lang.String REQUEST_KEY_ID = "android.request.id";
    public static final java.lang.String REQUEST_KEY_MESSAGE = "android.request.mesg";
    public static final java.lang.String REQUEST_KEY_NEW_REQUEST = "android.request.new_request";
    public static final java.lang.String REQUEST_KEY_TITLE = "android.request.title";
    public static final java.lang.String REQUEST_TYPE_APPROVAL = "android.request.type.approval";
    public static final java.lang.String RESPONSE_KEY_ERROR_CODE = "android.response.errorcode";
    public static final java.lang.String RESPONSE_KEY_MESSAGE = "android.response.msg";
    public static final java.lang.String RESPONSE_KEY_RESPONSE_TIMESTAMP = "android.response.timestamp";
    public static final java.lang.String RESPONSE_KEY_RESULT = "android.response.result";
    public static final int RESULT_APPROVED = 1;
    public static final int RESULT_DENIED = 2;
    public static final int RESULT_ERROR = 5;
    public static final int RESULT_ERROR_BAD_REQUEST = 1;
    public static final int RESULT_ERROR_INTERNAL = 3;
    public static final int RESULT_ERROR_NETWORK = 2;
    public static final int RESULT_NO_RESPONSE = 3;
    public static final int RESULT_UNKNOWN_REQUEST = 4;
    private static final java.lang.String TAG = "RestrictionsManager";
    private static final java.lang.String TAG_RESTRICTION = "restriction";
    private final android.content.Context mContext;
    private final android.content.IRestrictionsManager mService;

    public RestrictionsManager(android.content.Context context, android.content.IRestrictionsManager iRestrictionsManager) {
        this.mContext = context;
        this.mService = iRestrictionsManager;
    }

    public android.os.Bundle getApplicationRestrictions() {
        try {
            if (this.mService != null) {
                return this.mService.getApplicationRestrictions(this.mContext.getPackageName());
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.os.Bundle> getApplicationRestrictionsPerAdmin() {
        try {
            if (this.mService != null) {
                return this.mService.getApplicationRestrictionsPerAdminForUser(this.mContext.getUserId(), this.mContext.getPackageName());
            }
            return null;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean hasRestrictionsProvider() {
        try {
            if (this.mService != null) {
                return this.mService.hasRestrictionsProvider();
            }
            return false;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void requestPermission(java.lang.String str, java.lang.String str2, android.os.PersistableBundle persistableBundle) {
        if (str == null) {
            throw new java.lang.NullPointerException("requestType cannot be null");
        }
        if (str2 == null) {
            throw new java.lang.NullPointerException("requestId cannot be null");
        }
        if (persistableBundle == null) {
            throw new java.lang.NullPointerException("request cannot be null");
        }
        try {
            if (this.mService != null) {
                this.mService.requestPermission(this.mContext.getPackageName(), str, str2, persistableBundle);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.Intent createLocalApprovalIntent() {
        try {
            if (this.mService == null) {
                return null;
            }
            android.content.Intent createLocalApprovalIntent = this.mService.createLocalApprovalIntent();
            if (createLocalApprovalIntent != null) {
                createLocalApprovalIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                return createLocalApprovalIntent;
            }
            return createLocalApprovalIntent;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void notifyPermissionResponse(java.lang.String str, android.os.PersistableBundle persistableBundle) {
        if (str == null) {
            throw new java.lang.NullPointerException("packageName cannot be null");
        }
        if (persistableBundle == null) {
            throw new java.lang.NullPointerException("request cannot be null");
        }
        if (!persistableBundle.containsKey(REQUEST_KEY_ID)) {
            throw new java.lang.IllegalArgumentException("REQUEST_KEY_ID must be specified");
        }
        if (!persistableBundle.containsKey(RESPONSE_KEY_RESULT)) {
            throw new java.lang.IllegalArgumentException("RESPONSE_KEY_RESULT must be specified");
        }
        try {
            if (this.mService != null) {
                this.mService.notifyPermissionResponse(str, persistableBundle);
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.RestrictionEntry> getManifestRestrictions(java.lang.String str) {
        try {
            android.content.pm.ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 128);
            if (applicationInfo == null || !applicationInfo.metaData.containsKey(META_DATA_APP_RESTRICTIONS)) {
                return null;
            }
            return loadManifestRestrictions(str, applicationInfo.loadXmlMetaData(this.mContext.getPackageManager(), META_DATA_APP_RESTRICTIONS));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            throw new java.lang.IllegalArgumentException("No such package " + str);
        }
    }

    private java.util.List<android.content.RestrictionEntry> loadManifestRestrictions(java.lang.String str, android.content.res.XmlResourceParser xmlResourceParser) {
        try {
            android.content.Context createPackageContext = this.mContext.createPackageContext(str, 0);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            try {
                int next = xmlResourceParser.next();
                while (next != 1) {
                    if (next == 2) {
                        android.content.RestrictionEntry loadRestrictionElement = loadRestrictionElement(createPackageContext, xmlResourceParser);
                        if (loadRestrictionElement != null) {
                            arrayList.add(loadRestrictionElement);
                        }
                    }
                    next = xmlResourceParser.next();
                }
                return arrayList;
            } catch (java.io.IOException e) {
                android.util.Log.w(TAG, "Reading restriction metadata for " + str, e);
                return null;
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                android.util.Log.w(TAG, "Reading restriction metadata for " + str, e2);
                return null;
            }
        } catch (android.content.pm.PackageManager.NameNotFoundException e3) {
            return null;
        }
    }

    private android.content.RestrictionEntry loadRestrictionElement(android.content.Context context, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        android.util.AttributeSet asAttributeSet;
        if (xmlResourceParser.getName().equals(TAG_RESTRICTION) && (asAttributeSet = android.util.Xml.asAttributeSet(xmlResourceParser)) != null) {
            return loadRestriction(context, context.obtainStyledAttributes(asAttributeSet, com.android.internal.R.styleable.RestrictionEntry), xmlResourceParser);
        }
        return null;
    }

    private android.content.RestrictionEntry loadRestriction(android.content.Context context, android.content.res.TypedArray typedArray, android.content.res.XmlResourceParser xmlResourceParser) throws java.io.IOException, org.xmlpull.v1.XmlPullParserException {
        java.lang.String string = typedArray.getString(3);
        int i = typedArray.getInt(6, -1);
        java.lang.String string2 = typedArray.getString(2);
        java.lang.String string3 = typedArray.getString(0);
        int resourceId = typedArray.getResourceId(1, 0);
        int resourceId2 = typedArray.getResourceId(5, 0);
        if (i == -1) {
            android.util.Log.w(TAG, "restrictionType cannot be omitted");
            return null;
        }
        if (string == null) {
            android.util.Log.w(TAG, "key cannot be omitted");
            return null;
        }
        android.content.RestrictionEntry restrictionEntry = new android.content.RestrictionEntry(i, string);
        restrictionEntry.setTitle(string2);
        restrictionEntry.setDescription(string3);
        if (resourceId != 0) {
            restrictionEntry.setChoiceEntries(context, resourceId);
        }
        if (resourceId2 != 0) {
            restrictionEntry.setChoiceValues(context, resourceId2);
        }
        switch (i) {
            case 0:
            case 2:
            case 6:
                restrictionEntry.setSelectedString(typedArray.getString(4));
                return restrictionEntry;
            case 1:
                restrictionEntry.setSelectedState(typedArray.getBoolean(4, false));
                return restrictionEntry;
            case 3:
            default:
                android.util.Log.w(TAG, "Unknown restriction type " + i);
                return restrictionEntry;
            case 4:
                int resourceId3 = typedArray.getResourceId(4, 0);
                if (resourceId3 != 0) {
                    restrictionEntry.setAllSelectedStrings(context.getResources().getStringArray(resourceId3));
                }
                return restrictionEntry;
            case 5:
                restrictionEntry.setIntValue(typedArray.getInt(4, 0));
                return restrictionEntry;
            case 7:
            case 8:
                int depth = xmlResourceParser.getDepth();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                while (com.android.internal.util.XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
                    android.content.RestrictionEntry loadRestrictionElement = loadRestrictionElement(context, xmlResourceParser);
                    if (loadRestrictionElement == null) {
                        android.util.Log.w(TAG, "Child entry cannot be loaded for bundle restriction " + string);
                    } else {
                        arrayList.add(loadRestrictionElement);
                        if (i == 8 && loadRestrictionElement.getType() != 7) {
                            android.util.Log.w(TAG, "bundle_array " + string + " can only contain entries of type bundle");
                        }
                    }
                }
                restrictionEntry.setRestrictions((android.content.RestrictionEntry[]) arrayList.toArray(new android.content.RestrictionEntry[arrayList.size()]));
                return restrictionEntry;
        }
    }

    public static android.os.Bundle convertRestrictionsToBundle(java.util.List<android.content.RestrictionEntry> list) {
        android.os.Bundle bundle = new android.os.Bundle();
        java.util.Iterator<android.content.RestrictionEntry> it = list.iterator();
        while (it.hasNext()) {
            addRestrictionToBundle(bundle, it.next());
        }
        return bundle;
    }

    private static android.os.Bundle addRestrictionToBundle(android.os.Bundle bundle, android.content.RestrictionEntry restrictionEntry) {
        switch (restrictionEntry.getType()) {
            case 0:
            case 6:
                bundle.putString(restrictionEntry.getKey(), restrictionEntry.getSelectedString());
                return bundle;
            case 1:
                bundle.putBoolean(restrictionEntry.getKey(), restrictionEntry.getSelectedState());
                return bundle;
            case 2:
            case 3:
            case 4:
                bundle.putStringArray(restrictionEntry.getKey(), restrictionEntry.getAllSelectedStrings());
                return bundle;
            case 5:
                bundle.putInt(restrictionEntry.getKey(), restrictionEntry.getIntValue());
                return bundle;
            case 7:
                bundle.putBundle(restrictionEntry.getKey(), convertRestrictionsToBundle(java.util.Arrays.asList(restrictionEntry.getRestrictions())));
                return bundle;
            case 8:
                android.content.RestrictionEntry[] restrictions = restrictionEntry.getRestrictions();
                android.os.Bundle[] bundleArr = new android.os.Bundle[restrictions.length];
                for (int i = 0; i < restrictions.length; i++) {
                    android.content.RestrictionEntry[] restrictions2 = restrictions[i].getRestrictions();
                    if (restrictions2 == null) {
                        android.util.Log.w(TAG, "addRestrictionToBundle: Non-bundle entry found in bundle array");
                        bundleArr[i] = new android.os.Bundle();
                    } else {
                        bundleArr[i] = convertRestrictionsToBundle(java.util.Arrays.asList(restrictions2));
                    }
                }
                bundle.putParcelableArray(restrictionEntry.getKey(), bundleArr);
                return bundle;
            default:
                throw new java.lang.IllegalArgumentException("Unsupported restrictionEntry type: " + restrictionEntry.getType());
        }
    }
}
