package android.content.res;

/* loaded from: classes.dex */
public class Element {
    private static final java.lang.String BAD_COMPONENT_NAME_CHARS = ";,[](){}:?%^*|/\\";
    private static final int DEFAULT_MAX_STRING_ATTR_LENGTH = 32768;
    private static final int MAX_ATTR_LEN_MIMETYPE = 255;
    private static final int MAX_ATTR_LEN_NAME = 1024;
    private static final int MAX_ATTR_LEN_PACKAGE = 256;
    private static final int MAX_ATTR_LEN_PATH = 4000;
    private static final int MAX_ATTR_LEN_PERMISSION_GROUP = 256;
    private static final int MAX_ATTR_LEN_URL_COMPONENT = 256;
    private static final int MAX_ATTR_LEN_VALUE = 32768;
    private static final int MAX_POOL_SIZE = 128;
    private static final int MAX_TOTAL_META_DATA_SIZE = 262144;
    private static final java.lang.String TAG = "PackageParsing";
    protected static final java.lang.String TAG_ACTION = "action";
    protected static final java.lang.String TAG_ACTIVITY = "activity";
    protected static final java.lang.String TAG_ACTIVITY_ALIAS = "activity-alias";
    protected static final java.lang.String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    protected static final java.lang.String TAG_APPLICATION = "application";
    protected static final java.lang.String TAG_ATTRIBUTION = "attribution";
    protected static final java.lang.String TAG_ATTR_BACKUP_AGENT = "backupAgent";
    protected static final java.lang.String TAG_ATTR_CATEGORY = "category";
    protected static final java.lang.String TAG_ATTR_FRAGMENT = "fragment";
    protected static final java.lang.String TAG_ATTR_FRAGMENT_ADVANCED_PATTERN = "fragmentAdvancedPattern";
    protected static final java.lang.String TAG_ATTR_FRAGMENT_PATTERN = "fragmentPattern";
    protected static final java.lang.String TAG_ATTR_FRAGMENT_PREFIX = "fragmentPrefix";
    protected static final java.lang.String TAG_ATTR_FRAGMENT_SUFFIX = "fragmentSuffix";
    protected static final java.lang.String TAG_ATTR_HOST = "host";
    protected static final java.lang.String TAG_ATTR_MANAGE_SPACE_ACTIVITY = "manageSpaceActivity";
    protected static final java.lang.String TAG_ATTR_MIMEGROUP = "mimeGroup";
    protected static final java.lang.String TAG_ATTR_MIMETYPE = "mimeType";
    protected static final java.lang.String TAG_ATTR_NAME = "name";
    protected static final java.lang.String TAG_ATTR_PACKAGE = "package";
    protected static final java.lang.String TAG_ATTR_PARENT_ACTIVITY_NAME = "parentActivityName";
    protected static final java.lang.String TAG_ATTR_PATH = "path";
    protected static final java.lang.String TAG_ATTR_PATH_ADVANCED_PATTERN = "pathAdvancedPattern";
    protected static final java.lang.String TAG_ATTR_PATH_PATTERN = "pathPattern";
    protected static final java.lang.String TAG_ATTR_PATH_PREFIX = "pathPrefix";
    protected static final java.lang.String TAG_ATTR_PATH_SUFFIX = "pathSuffix";
    protected static final java.lang.String TAG_ATTR_PERMISSION = "permission";
    protected static final java.lang.String TAG_ATTR_PERMISSION_GROUP = "permissionGroup";
    protected static final java.lang.String TAG_ATTR_PORT = "port";
    protected static final java.lang.String TAG_ATTR_PROCESS = "process";
    protected static final java.lang.String TAG_ATTR_QUERY = "query";
    protected static final java.lang.String TAG_ATTR_QUERY_ADVANCED_PATTERN = "queryAdvancedPattern";
    protected static final java.lang.String TAG_ATTR_QUERY_PATTERN = "queryPattern";
    protected static final java.lang.String TAG_ATTR_QUERY_PREFIX = "queryPrefix";
    protected static final java.lang.String TAG_ATTR_QUERY_SUFFIX = "querySuffix";
    protected static final java.lang.String TAG_ATTR_READ_PERMISSION = "readPermission";
    protected static final java.lang.String TAG_ATTR_REQUIRED_ACCOUNT_TYPE = "requiredAccountType";
    protected static final java.lang.String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME = "requiredSystemPropertyName";
    protected static final java.lang.String TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE = "requiredSystemPropertyValue";
    protected static final java.lang.String TAG_ATTR_RESTRICTED_ACCOUNT_TYPE = "restrictedAccountType";
    protected static final java.lang.String TAG_ATTR_SCHEME = "scheme";
    protected static final java.lang.String TAG_ATTR_SHARED_USER_ID = "sharedUserId";
    protected static final java.lang.String TAG_ATTR_TARGET_ACTIVITY = "targetActivity";
    protected static final java.lang.String TAG_ATTR_TARGET_NAME = "targetName";
    protected static final java.lang.String TAG_ATTR_TARGET_PACKAGE = "targetPackage";
    protected static final java.lang.String TAG_ATTR_TARGET_PROCESSES = "targetProcesses";
    protected static final java.lang.String TAG_ATTR_TASK_AFFINITY = "taskAffinity";
    protected static final java.lang.String TAG_ATTR_VALUE = "value";
    protected static final java.lang.String TAG_ATTR_VERSION_NAME = "versionName";
    protected static final java.lang.String TAG_ATTR_WRITE_PERMISSION = "writePermission";
    protected static final java.lang.String TAG_ATTR_ZYGOTE_PRELOAD_NAME = "zygotePreloadName";
    protected static final java.lang.String TAG_CATEGORY = "category";
    protected static final java.lang.String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    protected static final java.lang.String TAG_DATA = "data";
    protected static final java.lang.String TAG_EAT_COMMENT = "eat-comment";
    protected static final java.lang.String TAG_FEATURE_GROUP = "feature-group";
    protected static final java.lang.String TAG_GRANT_URI_PERMISSION = "grant-uri-permission";
    protected static final java.lang.String TAG_INSTRUMENTATION = "instrumentation";
    protected static final java.lang.String TAG_INTENT = "intent";
    protected static final java.lang.String TAG_INTENT_FILTER = "intent-filter";
    protected static final java.lang.String TAG_KEY_SETS = "key-sets";
    protected static final java.lang.String TAG_LAYOUT = "layout";
    protected static final java.lang.String TAG_MANIFEST = "manifest";
    protected static final java.lang.String TAG_META_DATA = "meta-data";
    protected static final java.lang.String TAG_ORIGINAL_PACKAGE = "original-package";
    protected static final java.lang.String TAG_OVERLAY = "overlay";
    protected static final java.lang.String TAG_PACKAGE = "package";
    protected static final java.lang.String TAG_PACKAGE_VERIFIER = "package-verifier";
    protected static final java.lang.String TAG_PATH_PERMISSION = "path-permission";
    protected static final java.lang.String TAG_PERMISSION = "permission";
    protected static final java.lang.String TAG_PERMISSION_GROUP = "permission-group";
    protected static final java.lang.String TAG_PERMISSION_TREE = "permission-tree";
    protected static final java.lang.String TAG_PROFILEABLE = "profileable";
    protected static final java.lang.String TAG_PROPERTY = "property";
    protected static final java.lang.String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    protected static final java.lang.String TAG_PROVIDER = "provider";
    protected static final java.lang.String TAG_QUERIES = "queries";
    protected static final java.lang.String TAG_RECEIVER = "receiver";
    protected static final java.lang.String TAG_RESTRICT_UPDATE = "restrict-update";
    protected static final java.lang.String TAG_SCREEN = "screen";
    protected static final java.lang.String TAG_SERVICE = "service";
    protected static final java.lang.String TAG_SUPPORTS_GL_TEXTURE = "supports-gl-texture";
    protected static final java.lang.String TAG_SUPPORTS_INPUT = "supports-input";
    protected static final java.lang.String TAG_SUPPORTS_SCREENS = "supports-screens";
    protected static final java.lang.String TAG_SUPPORT_SCREENS = "supports-screens";
    protected static final java.lang.String TAG_URI_RELATIVE_FILTER_GROUP = "uri-relative-filter-group";
    protected static final java.lang.String TAG_USES_CONFIGURATION = "uses-configuration";
    protected static final java.lang.String TAG_USES_FEATURE = "uses-feature";
    protected static final java.lang.String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    protected static final java.lang.String TAG_USES_LIBRARY = "uses-library";
    protected static final java.lang.String TAG_USES_NATIVE_LIBRARY = "uses-native-library";
    protected static final java.lang.String TAG_USES_PERMISSION = "uses-permission";
    protected static final java.lang.String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    protected static final java.lang.String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    protected static final java.lang.String TAG_USES_SDK = "uses-sdk";
    protected static final java.lang.String TAG_USES_SPLIT = "uses-split";
    private static final java.lang.ThreadLocal<android.util.Pools.SimplePool<android.content.res.Element>> sPool = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.content.res.Element$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return android.content.res.Element.lambda$static$0();
        }
    });
    java.lang.String mTag;
    private final android.content.res.TagCounter[] mTagCounters = new android.content.res.TagCounter[35];
    private long mChildTagMask = 0;
    private int mTotalComponentMetadataSize = 0;

    static /* synthetic */ android.util.Pools.SimplePool lambda$static$0() {
        return new android.util.Pools.SimplePool(128);
    }

    static android.content.res.Element obtain(java.lang.String str) {
        android.content.res.Element acquire = sPool.get().acquire();
        if (acquire == null) {
            acquire = new android.content.res.Element();
        }
        acquire.init(str);
        return acquire;
    }

    void recycle() {
        this.mTag = null;
        sPool.get().release(this);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getCounterIdx(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1814617695:
                if (str.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1773650763:
                if (str.equals("uses-configuration")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (str.equals("permission-tree")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (str.equals("activity")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (str.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (str.equals("action")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals(TAG_USES_LIBRARY)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1194267734:
                if (str.equals(TAG_URI_RELATIVE_FILTER_GROUP)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case -1183762788:
                if (str.equals("intent")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals(TAG_META_DATA)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1109722326:
                if (str.equals("layout")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (str.equals("overlay")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1029793847:
                if (str.equals(TAG_INTENT_FILTER)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals(TAG_PROVIDER)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (str.equals("package")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (str.equals("attribution")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (str.equals("uses-sdk")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (str.equals("permission-group")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (str.equals("data")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (str.equals("profileable")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (str.equals("uses-permission")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (str.equals(TAG_PATH_PERMISSION)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (str.equals("queries")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (str.equals("supports-screens")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 941426460:
                if (str.equals(TAG_SUPPORTS_GL_TEXTURE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (str.equals("uses-permission-sdk-23")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (str.equals("uses-permission-sdk-m")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (str.equals("uses-feature")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (str.equals("compatible-screens")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 6;
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
            case 7:
                return 7;
            case '\b':
                return 8;
            case '\t':
                return 9;
            case '\n':
                return 10;
            case 11:
                return 11;
            case '\f':
                return 12;
            case '\r':
                return 13;
            case 14:
                return 14;
            case 15:
                return 15;
            case 16:
                return 16;
            case 17:
                return 17;
            case 18:
                return 18;
            case 19:
                return 19;
            case 20:
                return 20;
            case 21:
                return 21;
            case 22:
                return 22;
            case 23:
                return 23;
            case 24:
                return 24;
            case 25:
                return 25;
            case 26:
                return 26;
            case 27:
                return 27;
            case 28:
            case 29:
            case 30:
                return 28;
            case 31:
                return 29;
            case ' ':
                return 30;
            case '!':
                return 31;
            case '\"':
                return 32;
            case '#':
                return 33;
            default:
                return 34;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    static boolean shouldValidate(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1814617695:
                if (str.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -1773650763:
                if (str.equals("uses-configuration")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (str.equals("permission-tree")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (str.equals("activity")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (str.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (str.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals(TAG_USES_LIBRARY)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -1194267734:
                if (str.equals(TAG_URI_RELATIVE_FILTER_GROUP)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1183762788:
                if (str.equals("intent")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals(TAG_META_DATA)) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1109722326:
                if (str.equals("layout")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (str.equals("overlay")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -1029793847:
                if (str.equals(TAG_INTENT_FILTER)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (str.equals(TAG_PROPERTY)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals(TAG_PROVIDER)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -907689876:
                if (str.equals(TAG_SCREEN)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (str.equals("package")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -309882753:
                if (str.equals("attribution")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -266709319:
                if (str.equals("uses-sdk")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (str.equals("permission-group")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (str.equals("data")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 130625071:
                if (str.equals("manifest")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 178070147:
                if (str.equals("profileable")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (str.equals("uses-permission")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (str.equals(TAG_PATH_PERMISSION)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (str.equals("queries")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 896788286:
                if (str.equals("supports-screens")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 941426460:
                if (str.equals(TAG_SUPPORTS_GL_TEXTURE)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (str.equals("uses-permission-sdk-23")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (str.equals("uses-permission-sdk-m")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (str.equals("uses-feature")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (str.equals("compatible-screens")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 27;
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
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0095, code lost:
    
        if (r17.equals("activity") != false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void init(java.lang.String str) {
        this.mTag = str;
        this.mChildTagMask = 0L;
        char c = 0;
        this.mTotalComponentMetadataSize = 0;
        switch (str.hashCode()) {
            case -1655966961:
                break;
            case -1194267734:
                if (str.equals(TAG_URI_RELATIVE_FILTER_GROUP)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1183762788:
                if (str.equals("intent")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1029793847:
                if (str.equals(TAG_INTENT_FILTER)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals(TAG_PROVIDER)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 130625071:
                if (str.equals("manifest")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 655087462:
                if (str.equals("queries")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1818228622:
                if (str.equals("compatible-screens")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 3;
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
                initializeCounter("layout", 1000);
                initializeCounter(TAG_META_DATA, 1000);
                initializeCounter(TAG_INTENT_FILTER, 20000);
                break;
            case 1:
            case 2:
            case 3:
                initializeCounter(TAG_META_DATA, 1000);
                initializeCounter(TAG_INTENT_FILTER, 20000);
                break;
            case 4:
                initializeCounter("profileable", 100);
                initializeCounter(TAG_USES_NATIVE_LIBRARY, 100);
                initializeCounter("receiver", 1000);
                initializeCounter("service", 1000);
                initializeCounter(TAG_META_DATA, 1000);
                initializeCounter(TAG_USES_LIBRARY, 1000);
                initializeCounter(TAG_ACTIVITY_ALIAS, 4000);
                initializeCounter(TAG_PROVIDER, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION);
                initializeCounter("activity", 30000);
                break;
            case 5:
                initializeCounter(TAG_SCREEN, 4000);
                break;
            case 6:
            case 7:
                initializeCounter(TAG_URI_RELATIVE_FILTER_GROUP, 100);
                initializeCounter("action", 20000);
                initializeCounter("category", 40000);
                initializeCounter("data", 40000);
                break;
            case '\b':
                initializeCounter("application", 100);
                initializeCounter("overlay", 100);
                initializeCounter("instrumentation", 100);
                initializeCounter("permission-group", 100);
                initializeCounter("permission-tree", 100);
                initializeCounter(TAG_SUPPORTS_GL_TEXTURE, 100);
                initializeCounter("supports-screens", 100);
                initializeCounter("uses-configuration", 100);
                initializeCounter("uses-sdk", 100);
                initializeCounter("compatible-screens", 200);
                initializeCounter("queries", 200);
                initializeCounter("attribution", 400);
                initializeCounter("uses-feature", 400);
                initializeCounter("permission", 2000);
                initializeCounter("uses-permission", 20000);
                break;
            case '\t':
                initializeCounter(TAG_GRANT_URI_PERMISSION, 100);
                initializeCounter(TAG_PATH_PERMISSION, 100);
                initializeCounter(TAG_META_DATA, 1000);
                initializeCounter(TAG_INTENT_FILTER, 20000);
                break;
            case '\n':
                initializeCounter("package", 1000);
                initializeCounter("intent", 2000);
                initializeCounter(TAG_PROVIDER, com.android.internal.widget.remotecompose.core.operations.BitmapData.MAX_IMAGE_DIMENSION);
                break;
            case 11:
                initializeCounter("data", 100);
                break;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static int getAttrStrMaxLen(java.lang.String str) {
        char c;
        switch (str.hashCode()) {
            case -1674942688:
                if (str.equals(TAG_ATTR_FRAGMENT_PATTERN)) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1650269616:
                if (str.equals(TAG_ATTR_FRAGMENT)) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1643738640:
                if (str.equals(TAG_ATTR_PERMISSION_GROUP)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1392120434:
                if (str.equals(TAG_ATTR_MIMETYPE)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1349642324:
                if (str.equals(TAG_ATTR_RESTRICTED_ACCOUNT_TYPE)) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case -1349189254:
                if (str.equals(TAG_ATTR_QUERY_PREFIX)) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case -1285716734:
                if (str.equals(TAG_ATTR_FRAGMENT_PREFIX)) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case -1260501447:
                if (str.equals(TAG_ATTR_QUERY_SUFFIX)) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -1197028927:
                if (str.equals(TAG_ATTR_FRAGMENT_SUFFIX)) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case -1070534898:
                if (str.equals(TAG_ATTR_WRITE_PERMISSION)) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case -995070515:
                if (str.equals(TAG_ATTR_TASK_AFFINITY)) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case -984013045:
                if (str.equals(TAG_ATTR_SHARED_USER_ID)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -907987547:
                if (str.equals(TAG_ATTR_SCHEME)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -807062458:
                if (str.equals("package")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -309518737:
                if (str.equals(TAG_ATTR_PROCESS)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -218275157:
                if (str.equals(TAG_ATTR_MIMEGROUP)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -197982426:
                if (str.equals(TAG_ATTR_QUERY_ADVANCED_PATTERN)) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -134872600:
                if (str.equals(TAG_ATTR_REQUIRED_ACCOUNT_TYPE)) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 3208616:
                if (str.equals(TAG_ATTR_HOST)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 3373707:
                if (str.equals("name")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case 3433509:
                if (str.equals(TAG_ATTR_PATH)) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 3446913:
                if (str.equals("port")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 37711876:
                if (str.equals(TAG_ATTR_PARENT_ACTIVITY_NAME)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case 107944136:
                if (str.equals("query")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case 111972721:
                if (str.equals("value")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case 161722318:
                if (str.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_NAME)) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 223476894:
                if (str.equals(TAG_ATTR_FRAGMENT_ADVANCED_PATTERN)) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 437417989:
                if (str.equals(TAG_ATTR_READ_PERMISSION)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 486420412:
                if (str.equals(TAG_ATTR_TARGET_NAME)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 652376488:
                if (str.equals(TAG_ATTR_QUERY_PATTERN)) {
                    c = android.text.format.DateFormat.QUOTE;
                    break;
                }
                c = 65535;
                break;
            case 658841808:
                if (str.equals(TAG_ATTR_MANAGE_SPACE_ACTIVITY)) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 688906115:
                if (str.equals(TAG_ATTR_VERSION_NAME)) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 725812366:
                if (str.equals(TAG_ATTR_REQUIRED_SYSTEM_PROPERTY_VALUE)) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 748262167:
                if (str.equals(TAG_ATTR_PATH_PREFIX)) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 836949974:
                if (str.equals(TAG_ATTR_PATH_SUFFIX)) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case 1046915008:
                if (str.equals(TAG_ATTR_TARGET_ACTIVITY)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1090202828:
                if (str.equals(TAG_ATTR_TARGET_PROCESSES)) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 1091642979:
                if (str.equals(TAG_ATTR_BACKUP_AGENT)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1170994729:
                if (str.equals(TAG_ATTR_PATH_ADVANCED_PATTERN)) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1248861099:
                if (str.equals(TAG_ATTR_PATH_PATTERN)) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case 1496884597:
                if (str.equals(TAG_ATTR_TARGET_PACKAGE)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 2130173948:
                if (str.equals(TAG_ATTR_ZYGOTE_PRELOAD_NAME)) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
        }
        return 32768;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private int getResStrMaxLen(int i) {
        char c;
        java.lang.String str = this.mTag;
        switch (str.hashCode()) {
            case -1814617695:
                if (str.equals(TAG_GRANT_URI_PERMISSION)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1667688228:
                if (str.equals("permission-tree")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1655966961:
                if (str.equals("activity")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1608941274:
                if (str.equals(TAG_USES_NATIVE_LIBRARY)) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case -1422950858:
                if (str.equals("action")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1356765254:
                if (str.equals(TAG_USES_LIBRARY)) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case -1115949454:
                if (str.equals(TAG_META_DATA)) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1091287984:
                if (str.equals("overlay")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -993141291:
                if (str.equals(TAG_PROPERTY)) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals(TAG_PROVIDER)) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case -517618225:
                if (str.equals("permission")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -170723071:
                if (str.equals("permission-group")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 3076010:
                if (str.equals("data")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 50511102:
                if (str.equals("category")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 130625071:
                if (str.equals("manifest")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 599862896:
                if (str.equals("uses-permission")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 636171383:
                if (str.equals(TAG_PATH_PERMISSION)) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1343942321:
                if (str.equals("uses-permission-sdk-23")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1705921021:
                if (str.equals("uses-permission-sdk-m")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 1792785909:
                if (str.equals("uses-feature")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 18;
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
                return getActionResStrMaxLen(i);
            case 1:
                return getActivityResStrMaxLen(i);
            case 2:
                return getActivityAliasResStrMaxLen(i);
            case 3:
                return getApplicationResStrMaxLen(i);
            case 4:
                return getDataResStrMaxLen(i);
            case 5:
                return getCategoryResStrMaxLen(i);
            case 6:
                return getGrantUriPermissionResStrMaxLen(i);
            case 7:
                return getInstrumentationResStrMaxLen(i);
            case '\b':
                return getManifestResStrMaxLen(i);
            case '\t':
                return getMetaDataResStrMaxLen(i);
            case '\n':
                return getOverlayResStrMaxLen(i);
            case 11:
                return getPathPermissionResStrMaxLen(i);
            case '\f':
                return getPermissionResStrMaxLen(i);
            case '\r':
                return getPermissionGroupResStrMaxLen(i);
            case 14:
                return getPermissionTreeResStrMaxLen(i);
            case 15:
                return getPropertyResStrMaxLen(i);
            case 16:
                return getProviderResStrMaxLen(i);
            case 17:
                return getReceiverResStrMaxLen(i);
            case 18:
                return getServiceResStrMaxLen(i);
            case 19:
                return getUsesFeatureResStrMaxLen(i);
            case 20:
                return getUsesLibraryResStrMaxLen(i);
            case 21:
                return getUsesNativeLibraryResStrMaxLen(i);
            case 22:
            case 23:
            case 24:
                return getUsesPermissionResStrMaxLen(i);
            default:
                return 32768;
        }
    }

    private static int getActionResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getActivityResStrMaxLen(int i) {
        switch (i) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 27:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getActivityAliasResStrMaxLen(int i) {
        switch (i) {
            case 2:
            case 3:
            case 7:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getApplicationResStrMaxLen(int i) {
        switch (i) {
            case 3:
            case 4:
            case 6:
            case 11:
            case 12:
            case 16:
            case 28:
            case 29:
            case 52:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getCategoryResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getDataResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 255;
            case 1:
            case 2:
            case 3:
                return 256;
            case 4:
            case 5:
            case 6:
            case 7:
            case 12:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
                return 4000;
            case 8:
            case 9:
            case 10:
            case 13:
            case 15:
            default:
                return 32768;
            case 11:
                return 1024;
        }
    }

    private static int getGrantUriPermissionResStrMaxLen(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                return 4000;
            default:
                return 32768;
        }
    }

    private static int getInstrumentationResStrMaxLen(int i) {
        switch (i) {
            case 2:
            case 9:
                return 1024;
            case 3:
                return 256;
            default:
                return 32768;
        }
    }

    private static int getManifestResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 256;
            case 1:
            default:
                return 32768;
            case 2:
                return 1024;
        }
    }

    private static int getMetaDataResStrMaxLen(int i) {
        switch (i) {
        }
        return 32768;
    }

    private static int getOverlayResStrMaxLen(int i) {
        switch (i) {
            case 1:
                return 256;
            case 2:
            case 3:
            case 5:
                return 1024;
            case 4:
            default:
                return 32768;
            case 6:
                return 91;
        }
    }

    private static int getPathPermissionResStrMaxLen(int i) {
        switch (i) {
            case 0:
            case 1:
            case 2:
                return 1024;
            case 3:
            case 4:
            case 5:
                return 4000;
            default:
                return 32768;
        }
    }

    private static int getPermissionResStrMaxLen(int i) {
        switch (i) {
            case 2:
                return 1024;
            case 3:
            default:
                return 32768;
            case 4:
                return 256;
        }
    }

    private static int getPermissionGroupResStrMaxLen(int i) {
        switch (i) {
            case 2:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getPermissionTreeResStrMaxLen(int i) {
        switch (i) {
            case 2:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getPropertyResStrMaxLen(int i) {
        switch (i) {
        }
        return 32768;
    }

    private static int getProviderResStrMaxLen(int i) {
        switch (i) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
                return 1024;
            case 6:
            case 7:
            default:
                return 32768;
        }
    }

    private static int getReceiverResStrMaxLen(int i) {
        switch (i) {
            case 2:
            case 3:
            case 6:
                return 1024;
            case 4:
            case 5:
            default:
                return 32768;
        }
    }

    private static int getServiceResStrMaxLen(int i) {
        switch (i) {
            case 2:
            case 3:
            case 6:
                return 1024;
            case 4:
            case 5:
            default:
                return 32768;
        }
    }

    private static int getUsesFeatureResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesLibraryResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesNativeLibraryResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private static int getUsesPermissionResStrMaxLen(int i) {
        switch (i) {
            case 0:
                return 1024;
            default:
                return 32768;
        }
    }

    private void initializeCounter(java.lang.String str, int i) {
        int counterIdx = getCounterIdx(str);
        if (this.mTagCounters[counterIdx] == null) {
            this.mTagCounters[counterIdx] = new android.content.res.TagCounter();
        }
        this.mTagCounters[counterIdx].reset(i);
        this.mChildTagMask = (1 << counterIdx) | this.mChildTagMask;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0082, code lost:
    
        if (r7.equals(android.content.res.Element.TAG_ATTR_ZYGOTE_PRELOAD_NAME) != false) goto L52;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean isComponentNameAttr(java.lang.String str) {
        char c;
        java.lang.String str2 = this.mTag;
        char c2 = 2;
        char c3 = 65535;
        switch (str2.hashCode()) {
            case -1655966961:
                if (str2.equals("activity")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str2.equals(TAG_PROVIDER)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str2.equals("receiver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str2.equals("instrumentation")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str2.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str2.equals("application")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str2.equals("service")) {
                    c = 6;
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
                switch (str.hashCode()) {
                    case 3373707:
                        if (str.equals("name")) {
                            c3 = 0;
                            break;
                        }
                        break;
                    case 37711876:
                        if (str.equals(TAG_ATTR_PARENT_ACTIVITY_NAME)) {
                            c3 = 1;
                            break;
                        }
                        break;
                }
                switch (c3) {
                }
            case 1:
                switch (str.hashCode()) {
                    case 1046915008:
                        if (str.equals(TAG_ATTR_TARGET_ACTIVITY)) {
                            c3 = 0;
                            break;
                        }
                        break;
                }
                switch (c3) {
                }
            case 2:
                switch (str.hashCode()) {
                    case 3373707:
                        if (str.equals("name")) {
                            c2 = 1;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 1091642979:
                        if (str.equals(TAG_ATTR_BACKUP_AGENT)) {
                            c2 = 0;
                            break;
                        }
                        c2 = 65535;
                        break;
                    case 2130173948:
                        break;
                    default:
                        c2 = 65535;
                        break;
                }
                switch (c2) {
                }
            case 3:
            case 4:
            case 5:
            case 6:
                switch (str.hashCode()) {
                    case 3373707:
                        if (str.equals("name")) {
                            c3 = 0;
                            break;
                        }
                        break;
                }
                switch (c3) {
                }
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean isComponentNameAttr(int i) {
        char c;
        java.lang.String str = this.mTag;
        switch (str.hashCode()) {
            case -1655966961:
                if (str.equals("activity")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -987494927:
                if (str.equals(TAG_PROVIDER)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -808719889:
                if (str.equals("receiver")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 544550766:
                if (str.equals("instrumentation")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 790287890:
                if (str.equals(TAG_ACTIVITY_ALIAS)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1554253136:
                if (str.equals("application")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 1984153269:
                if (str.equals("service")) {
                    c = 6;
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
                if (i != 3 && i != 27) {
                    break;
                }
                break;
            case 1:
                if (i != 7) {
                    break;
                }
                break;
            case 2:
                if (i != 16 && i != 3 && i != 52) {
                    break;
                }
                break;
            case 3:
                if (i != 2) {
                    break;
                }
                break;
            case 4:
                if (i != 2) {
                    break;
                }
                break;
            case 5:
                if (i != 2) {
                    break;
                }
                break;
            case 6:
                if (i != 2) {
                    break;
                }
                break;
        }
        return false;
    }

    boolean hasChild(java.lang.String str) {
        return (this.mChildTagMask & ((long) (1 << getCounterIdx(str)))) != 0;
    }

    void validateComponentName(java.lang.CharSequence charSequence) {
        for (int i = 0; i < charSequence.length(); i++) {
            if (BAD_COMPONENT_NAME_CHARS.indexOf(charSequence.charAt(i)) >= 0) {
                android.util.Slog.e("PackageParsing", ((java.lang.Object) charSequence) + " is not a valid Java class name");
                throw new java.lang.SecurityException(((java.lang.Object) charSequence) + " is not a valid Java class name");
            }
        }
    }

    void validateStrAttr(java.lang.String str, java.lang.String str2) {
        if (str2 != null && str2.length() > getAttrStrMaxLen(str)) {
            throw new java.lang.SecurityException("String length limit exceeded for attribute " + str + " in " + this.mTag);
        }
        if (isComponentNameAttr(str)) {
            validateComponentName(str2);
        }
    }

    void validateResStrAttr(int i, java.lang.CharSequence charSequence) {
        if (charSequence != null && charSequence.length() > getResStrMaxLen(i)) {
            throw new java.lang.SecurityException("String length limit exceeded for attribute in " + this.mTag);
        }
        if (isComponentNameAttr(i)) {
            validateComponentName(charSequence);
        }
    }

    void validateComponentMetadata(java.lang.String str) {
        this.mTotalComponentMetadataSize += str.length();
        if (this.mTotalComponentMetadataSize > 262144) {
            throw new java.lang.SecurityException("Max total meta data size limit exceeded for " + this.mTag);
        }
    }

    void seen(android.content.res.Element element) {
        android.content.res.TagCounter tagCounter = this.mTagCounters[getCounterIdx(element.mTag)];
        if (tagCounter != null) {
            tagCounter.increment();
            if (!tagCounter.isValid()) {
                throw new java.lang.SecurityException("The number of child " + element.mTag + " elements exceeded the max allowed in " + this.mTag);
            }
        }
    }
}
