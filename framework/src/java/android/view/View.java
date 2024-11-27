package android.view;

/* loaded from: classes4.dex */
public class View implements android.graphics.drawable.Drawable.Callback, android.view.KeyEvent.Callback, android.view.accessibility.AccessibilityEventSource {
    public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED = -1;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_AUTO = 0;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_NO = 2;
    public static final int ACCESSIBILITY_DATA_SENSITIVE_YES = 1;
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    static final int ACCESSIBILITY_LIVE_REGION_DEFAULT = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    static final int ALL_RTL_PROPERTIES_RESOLVED = 1610678816;
    public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 1;
    public static final java.lang.String AUTOFILL_HINT_CREDENTIAL_MANAGER = "credential";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE = "creditCardExpirationDate";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY = "creditCardExpirationDay";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH = "creditCardExpirationMonth";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR = "creditCardExpirationYear";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final java.lang.String AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE = "creditCardSecurityCode";
    public static final java.lang.String AUTOFILL_HINT_EMAIL_ADDRESS = "emailAddress";
    public static final java.lang.String AUTOFILL_HINT_NAME = "name";
    public static final java.lang.String AUTOFILL_HINT_PASSWORD = "password";
    public static final java.lang.String AUTOFILL_HINT_PASSWORD_AUTO = "passwordAuto";
    public static final java.lang.String AUTOFILL_HINT_PHONE = "phone";
    public static final java.lang.String AUTOFILL_HINT_POSTAL_ADDRESS = "postalAddress";
    public static final java.lang.String AUTOFILL_HINT_POSTAL_CODE = "postalCode";
    public static final java.lang.String AUTOFILL_HINT_USERNAME = "username";
    private static final java.lang.String AUTOFILL_LOG_TAG = "View.Autofill";
    public static final int AUTOFILL_TYPE_DATE = 4;
    public static final int AUTOFILL_TYPE_LIST = 3;
    public static final int AUTOFILL_TYPE_NONE = 0;
    public static final int AUTOFILL_TYPE_TEXT = 1;
    public static final int AUTOFILL_TYPE_TOGGLE = 2;
    static final int CLICKABLE = 16384;
    private static final java.lang.String CONTENT_CAPTURE_LOG_TAG = "View.ContentCapture";
    public static final int CONTENT_SENSITIVITY_AUTO = 0;
    public static final int CONTENT_SENSITIVITY_NOT_SENSITIVE = 2;
    public static final int CONTENT_SENSITIVITY_SENSITIVE = 1;
    static final int CONTEXT_CLICKABLE = 8388608;
    private static final boolean DBG = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    static final int DEBUG_CORNERS_SIZE_DIP = 8;
    static final int DISABLED = 32;
    public static final int DRAG_FLAG_ACCESSIBILITY_ACTION = 1024;
    public static final int DRAG_FLAG_GLOBAL = 256;
    public static final int DRAG_FLAG_GLOBAL_PERSISTABLE_URI_PERMISSION = 64;
    public static final int DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION = 128;
    public static final int DRAG_FLAG_GLOBAL_SAME_APPLICATION = 4096;
    public static final int DRAG_FLAG_GLOBAL_URI_READ = 1;
    public static final int DRAG_FLAG_GLOBAL_URI_WRITE = 2;
    public static final int DRAG_FLAG_OPAQUE = 512;
    public static final int DRAG_FLAG_REQUEST_SURFACE_FOR_RETURN_ANIMATION = 2048;
    public static final int DRAG_FLAG_START_INTENT_SENDER_ON_UNHANDLED_DRAG = 8192;
    static final int DRAG_MASK = 3;
    static final int DRAWING_CACHE_ENABLED = 32768;

    @java.lang.Deprecated
    public static final int DRAWING_CACHE_QUALITY_AUTO = 0;

    @java.lang.Deprecated
    public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;

    @java.lang.Deprecated
    public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
    static final int DRAWING_CACHE_QUALITY_MASK = 1572864;
    static final int DRAW_MASK = 128;
    static final int DUPLICATE_PARENT_STATE = 4194304;
    static final int ENABLED = 0;
    static final int ENABLED_MASK = 32;
    static final int FADING_EDGE_HORIZONTAL = 4096;
    static final int FADING_EDGE_MASK = 12288;
    static final int FADING_EDGE_NONE = 0;
    static final int FADING_EDGE_VERTICAL = 8192;
    static final int FILTER_TOUCHES_WHEN_OBSCURED = 1024;
    public static final int FIND_VIEWS_WITH_ACCESSIBILITY_NODE_PROVIDERS = 4;
    public static final int FIND_VIEWS_WITH_CONTENT_DESCRIPTION = 2;
    public static final int FIND_VIEWS_WITH_TEXT = 1;
    private static final int FITS_SYSTEM_WINDOWS = 2;
    public static final int FOCUSABLE = 1;
    public static final int FOCUSABLES_ALL = 0;
    public static final int FOCUSABLES_TOUCH_MODE = 1;
    public static final int FOCUSABLE_AUTO = 16;
    static final int FOCUSABLE_IN_TOUCH_MODE = 262144;
    private static final int FOCUSABLE_MASK = 17;
    public static final int FOCUS_BACKWARD = 1;
    public static final int FOCUS_DOWN = 130;
    public static final int FOCUS_FORWARD = 2;
    public static final int FOCUS_LEFT = 17;
    public static final int FOCUS_RIGHT = 66;
    public static final int FOCUS_UP = 33;
    private static final float FRAME_RATE_SIZE_PERCENTAGE_THRESHOLD = 0.07f;
    public static final int GONE = 8;
    public static final int HAPTIC_FEEDBACK_ENABLED = 268435456;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    static final int IMPORTANT_FOR_ACCESSIBILITY_DEFAULT = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_AUTO = 0;
    public static final int IMPORTANT_FOR_AUTOFILL_NO = 2;
    public static final int IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_AUTOFILL_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_AUTO = 0;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_NO = 2;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_YES = 1;
    public static final int IMPORTANT_FOR_CONTENT_CAPTURE_YES_EXCLUDE_DESCENDANTS = 4;
    private static final int INFREQUENT_UPDATE_COUNTS = 2;
    private static final long INFREQUENT_UPDATE_INTERVAL_MILLIS = 100;
    public static final int INVISIBLE = 4;
    public static final int KEEP_SCREEN_ON = 67108864;
    public static final int LAST_APP_AUTOFILL_ID = 1073741823;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    private static final int LAYOUT_DIRECTION_DEFAULT = 2;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    static final int LAYOUT_DIRECTION_RESOLVED_DEFAULT = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int LAYOUT_DIRECTION_UNDEFINED = -1;
    static final int LONG_CLICKABLE = 2097152;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int NOT_FOCUSABLE = 0;
    public static final int NO_ID = -1;
    static final int OPTIONAL_FITS_SYSTEM_WINDOWS = 2048;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    static final int PARENT_SAVE_DISABLED = 536870912;
    static final int PARENT_SAVE_DISABLED_MASK = 536870912;
    static final int PFLAG2_ACCESSIBILITY_FOCUSED = 67108864;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_MASK = 25165824;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT = 23;
    static final int PFLAG2_DRAG_CAN_ACCEPT = 1;
    static final int PFLAG2_DRAG_HOVERED = 2;
    static final int PFLAG2_DRAWABLE_RESOLVED = 1073741824;
    static final int PFLAG2_HAS_TRANSIENT_STATE = Integer.MIN_VALUE;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK = 7340032;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT = 20;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK = 12;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT = 2;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED = 32;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK = 48;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL = 16;
    static final int PFLAG2_PADDING_RESOLVED = 536870912;
    static final int PFLAG2_SUBTREE_ACCESSIBILITY_STATE_CHANGED = 134217728;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK = 57344;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT = 13;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED = 65536;
    private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT = 131072;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK = 917504;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT = 17;
    static final int PFLAG2_TEXT_DIRECTION_MASK = 448;
    static final int PFLAG2_TEXT_DIRECTION_MASK_SHIFT = 6;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED = 512;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT = 1024;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK = 7168;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT = 10;
    static final int PFLAG2_VIEW_QUICK_REJECTED = 268435456;
    private static final int PFLAG3_ACCESSIBILITY_HEADING = Integer.MIN_VALUE;
    private static final int PFLAG3_AGGREGATED_VISIBLE = 536870912;
    static final int PFLAG3_APPLYING_INSETS = 32;
    static final int PFLAG3_ASSIST_BLOCKED = 16384;
    private static final int PFLAG3_AUTOFILLID_EXPLICITLY_SET = 1073741824;
    static final int PFLAG3_CALLED_SUPER = 16;
    private static final int PFLAG3_CLUSTER = 32768;
    private static final int PFLAG3_FINGER_DOWN = 131072;
    static final int PFLAG3_FITTING_SYSTEM_WINDOWS = 64;
    private static final int PFLAG3_FOCUSED_BY_DEFAULT = 262144;
    private static final int PFLAG3_HAS_OVERLAPPING_RENDERING_FORCED = 16777216;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK = 7864320;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT = 19;
    private static final int PFLAG3_IS_AUTOFILLED = 65536;
    static final int PFLAG3_IS_LAID_OUT = 4;
    static final int PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT = 8;
    static final int PFLAG3_NESTED_SCROLLING_ENABLED = 128;
    static final int PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT = 134217728;
    private static final int PFLAG3_NO_REVEAL_ON_FOCUS = 67108864;
    private static final int PFLAG3_OVERLAPPING_RENDERING_FORCED_VALUE = 8388608;
    private static final int PFLAG3_SCREEN_READER_FOCUSABLE = 268435456;
    static final int PFLAG3_SCROLL_INDICATOR_BOTTOM = 512;
    static final int PFLAG3_SCROLL_INDICATOR_END = 8192;
    static final int PFLAG3_SCROLL_INDICATOR_LEFT = 1024;
    static final int PFLAG3_SCROLL_INDICATOR_RIGHT = 2048;
    static final int PFLAG3_SCROLL_INDICATOR_START = 4096;
    static final int PFLAG3_SCROLL_INDICATOR_TOP = 256;
    static final int PFLAG3_TEMPORARY_DETACH = 33554432;
    static final int PFLAG3_VIEW_IS_ANIMATING_ALPHA = 2;
    static final int PFLAG3_VIEW_IS_ANIMATING_TRANSFORM = 1;
    private static final int PFLAG4_ALLOW_CLICK_WHEN_DISABLED = 4096;
    private static final int PFLAG4_AUTOFILL_HIDE_HIGHLIGHT = 512;
    private static final int PFLAG4_AUTO_HANDWRITING_ENABLED = 65536;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_CACHED_VALUE = 128;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_IS_CACHED = 64;
    private static final int PFLAG4_CONTENT_CAPTURE_IMPORTANCE_MASK = 192;
    private static final int PFLAG4_CONTENT_SENSITIVITY_MASK = 50331648;
    private static final int PFLAG4_CONTENT_SENSITIVITY_SHIFT = 24;
    private static final int PFLAG4_DETACHED = 8192;
    private static final int PFLAG4_DRAG_A11Y_STARTED = 32768;
    static final int PFLAG4_FRAMEWORK_OPTIONAL_FITS_SYSTEM_WINDOWS = 256;
    private static final int PFLAG4_HAS_TRANSLATION_TRANSIENT_STATE = 16384;
    private static final int PFLAG4_IMPORTANT_FOR_CONTENT_CAPTURE_MASK = 15;
    private static final int PFLAG4_IMPORTANT_FOR_CREDENTIAL_MANAGER = 131072;
    private static final int PFLAG4_IS_COUNTED_AS_SENSITIVE = 67108864;
    private static final int PFLAG4_NOTIFIED_CONTENT_CAPTURE_APPEARED = 16;
    private static final int PFLAG4_NOTIFIED_CONTENT_CAPTURE_DISAPPEARED = 32;
    private static final int PFLAG4_RELAYOUT_TRACING_ENABLED = 524288;
    private static final int PFLAG4_ROTARY_HAPTICS_DETERMINED = 1048576;
    private static final int PFLAG4_ROTARY_HAPTICS_ENABLED = 2097152;
    private static final int PFLAG4_ROTARY_HAPTICS_SCROLL_SINCE_LAST_ROTARY_INPUT = 4194304;
    private static final int PFLAG4_ROTARY_HAPTICS_WAITING_FOR_SCROLL_EVENT = 8388608;
    static final int PFLAG4_SCROLL_CAPTURE_HINT_MASK = 7168;
    static final int PFLAG4_SCROLL_CAPTURE_HINT_SHIFT = 10;
    private static final int PFLAG4_TRAVERSAL_TRACING_ENABLED = 262144;
    static final int PFLAG_ACTIVATED = 1073741824;
    static final int PFLAG_ALPHA_SET = 262144;
    static final int PFLAG_ANIMATION_STARTED = 65536;
    private static final int PFLAG_AWAKEN_SCROLL_BARS_ON_ATTACH = 134217728;
    static final int PFLAG_CANCEL_NEXT_UP_EVENT = 67108864;
    static final int PFLAG_DIRTY = 2097152;
    static final int PFLAG_DIRTY_MASK = 2097152;
    static final int PFLAG_DRAWABLE_STATE_DIRTY = 1024;
    static final int PFLAG_DRAWING_CACHE_VALID = 32768;
    static final int PFLAG_DRAWN = 32;
    static final int PFLAG_DRAW_ANIMATION = 64;
    static final int PFLAG_FOCUSED = 2;
    static final int PFLAG_FORCE_LAYOUT = 4096;
    static final int PFLAG_HAS_BOUNDS = 16;
    private static final int PFLAG_HOVERED = 268435456;
    static final int PFLAG_INVALIDATED = Integer.MIN_VALUE;
    static final int PFLAG_IS_ROOT_NAMESPACE = 8;
    static final int PFLAG_LAYOUT_REQUIRED = 8192;
    static final int PFLAG_MEASURED_DIMENSION_SET = 2048;
    private static final int PFLAG_NOTIFY_AUTOFILL_MANAGER_ON_CLICK = 536870912;
    static final int PFLAG_OPAQUE_BACKGROUND = 8388608;
    static final int PFLAG_OPAQUE_MASK = 25165824;
    static final int PFLAG_OPAQUE_SCROLLBARS = 16777216;
    private static final int PFLAG_PREPRESSED = 33554432;
    private static final int PFLAG_PRESSED = 16384;
    static final int PFLAG_REQUEST_TRANSPARENT_REGIONS = 512;
    private static final int PFLAG_SAVE_STATE_CALLED = 131072;
    static final int PFLAG_SCROLL_CONTAINER = 524288;
    static final int PFLAG_SCROLL_CONTAINER_ADDED = 1048576;
    static final int PFLAG_SELECTED = 4;
    static final int PFLAG_SKIP_DRAW = 128;
    static final int PFLAG_WANTS_FOCUS = 1;
    private static final int POPULATING_ACCESSIBILITY_EVENT_TYPES = 172479;
    private static final int PROVIDER_BACKGROUND = 0;
    private static final int PROVIDER_BOUNDS = 2;
    private static final int PROVIDER_NONE = 1;
    private static final int PROVIDER_PADDED_BOUNDS = 3;
    public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK = 16375;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_DEFAULT = Float.NaN;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_HIGH = -4.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_LOW = -2.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_NORMAL = -3.0f;
    public static final float REQUESTED_FRAME_RATE_CATEGORY_NO_PREFERENCE = -1.0f;
    static final int SAVE_DISABLED = 65536;
    static final int SAVE_DISABLED_MASK = 65536;
    public static final int SCREEN_STATE_OFF = 0;
    public static final int SCREEN_STATE_ON = 1;
    static final int SCROLLBARS_HORIZONTAL = 256;
    static final int SCROLLBARS_INSET_MASK = 16777216;
    public static final int SCROLLBARS_INSIDE_INSET = 16777216;
    public static final int SCROLLBARS_INSIDE_OVERLAY = 0;
    static final int SCROLLBARS_MASK = 768;
    static final int SCROLLBARS_NONE = 0;
    public static final int SCROLLBARS_OUTSIDE_INSET = 50331648;
    static final int SCROLLBARS_OUTSIDE_MASK = 33554432;
    public static final int SCROLLBARS_OUTSIDE_OVERLAY = 33554432;
    static final int SCROLLBARS_STYLE_MASK = 50331648;
    static final int SCROLLBARS_VERTICAL = 512;
    public static final int SCROLLBAR_POSITION_DEFAULT = 0;
    public static final int SCROLLBAR_POSITION_LEFT = 1;
    public static final int SCROLLBAR_POSITION_RIGHT = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_CAPTURE_HINT_AUTO = 0;
    public static final int SCROLL_CAPTURE_HINT_EXCLUDE = 1;
    public static final int SCROLL_CAPTURE_HINT_EXCLUDE_DESCENDANTS = 4;
    public static final int SCROLL_CAPTURE_HINT_INCLUDE = 2;
    static final int SCROLL_INDICATORS_NONE = 0;
    static final int SCROLL_INDICATORS_PFLAG3_MASK = 16128;
    static final int SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT = 8;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    public static final int SOUND_EFFECTS_ENABLED = 134217728;
    public static final int STATUS_BAR_DISABLE_BACK = 4194304;
    public static final int STATUS_BAR_DISABLE_CLOCK = 8388608;
    public static final int STATUS_BAR_DISABLE_EXPAND = 65536;
    public static final int STATUS_BAR_DISABLE_HOME = 2097152;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ICONS = 131072;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_TICKER = 524288;
    public static final int STATUS_BAR_DISABLE_ONGOING_CALL_CHIP = 67108864;
    public static final int STATUS_BAR_DISABLE_RECENT = 16777216;
    public static final int STATUS_BAR_DISABLE_SEARCH = 33554432;
    public static final int STATUS_BAR_DISABLE_SYSTEM_INFO = 1048576;

    @java.lang.Deprecated
    public static final int STATUS_BAR_HIDDEN = 1;

    @java.lang.Deprecated
    public static final int STATUS_BAR_VISIBLE = 0;
    public static final int SYSTEM_UI_CLEARABLE_FLAGS = 7;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_FULLSCREEN = 4;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 2;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_IMMERSIVE = 2048;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 4096;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 1024;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 512;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 256;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_LOW_PROFILE = 1;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_FLAG_VISIBLE = 0;

    @java.lang.Deprecated
    public static final int SYSTEM_UI_LAYOUT_FLAGS = 1536;
    private static final int SYSTEM_UI_RESERVED_LEGACY1 = 16384;
    private static final int SYSTEM_UI_RESERVED_LEGACY2 = 65536;
    public static final int TEXT_ALIGNMENT_CENTER = 4;
    private static final int TEXT_ALIGNMENT_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_GRAVITY = 1;
    public static final int TEXT_ALIGNMENT_INHERIT = 0;
    static final int TEXT_ALIGNMENT_RESOLVED_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_TEXT_END = 3;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    public static final int TEXT_ALIGNMENT_VIEW_END = 6;
    public static final int TEXT_ALIGNMENT_VIEW_START = 5;
    public static final int TEXT_DIRECTION_ANY_RTL = 2;
    private static final int TEXT_DIRECTION_DEFAULT = 0;
    public static final int TEXT_DIRECTION_FIRST_STRONG = 1;
    public static final int TEXT_DIRECTION_FIRST_STRONG_LTR = 6;
    public static final int TEXT_DIRECTION_FIRST_STRONG_RTL = 7;
    public static final int TEXT_DIRECTION_INHERIT = 0;
    public static final int TEXT_DIRECTION_LOCALE = 5;
    public static final int TEXT_DIRECTION_LTR = 3;
    static final int TEXT_DIRECTION_RESOLVED_DEFAULT = 1;
    public static final int TEXT_DIRECTION_RTL = 4;
    static final int TOOLTIP = 1073741824;
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    protected static final java.lang.String VIEW_LOG_TAG = "View";
    protected static final int VIEW_STRUCTURE_FOR_ASSIST = 0;
    protected static final int VIEW_STRUCTURE_FOR_AUTOFILL = 1;
    protected static final int VIEW_STRUCTURE_FOR_CONTENT_CAPTURE = 2;
    static final int VISIBILITY_MASK = 12;
    public static final int VISIBLE = 0;
    static final int WILL_NOT_CACHE_DRAWING = 131072;
    static final int WILL_NOT_DRAW = 128;
    private static android.util.SparseArray<java.lang.String> mAttributeMap;
    private static boolean sAcceptZeroSizeDragShadow;
    private static boolean sAlwaysAssignFocus;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
    static boolean sBrokenInsetsDispatch;
    protected static boolean sBrokenWindowBackground;
    private static boolean sCanFocusZeroSized;
    static boolean sCascadedDragDrop;
    private static android.graphics.Paint sDebugPaint;
    public static java.lang.String sDebugViewAttributesApplicationPackage;
    static boolean sForceLayoutWhenInsetsChanged;
    static boolean sHasFocusableExcludeAutoFocusable;
    private static int sNextAccessibilityViewId;
    protected static boolean sPreserveMarginParamsInLayoutParamConversion;
    private static boolean sThrowOnInvalidFloatProperties;
    private static boolean sTraceLayoutSteps;
    private static java.lang.String sTraceRequestLayoutClass;
    private static boolean sUseDefaultFocusHighlight;
    private int mAccessibilityCursorPosition;
    android.view.View.AccessibilityDelegate mAccessibilityDelegate;
    private java.lang.CharSequence mAccessibilityPaneTitle;
    private int mAccessibilityTraversalAfterId;
    private int mAccessibilityTraversalBeforeId;
    private int mAccessibilityViewId;
    private java.lang.String mAllowedHandwritingDelegatePackageName;
    private java.lang.String mAllowedHandwritingDelegatorPackageName;
    private float mAmbiguousGestureMultiplier;
    private android.view.ViewPropertyAnimator mAnimator;
    android.view.View.AttachInfo mAttachInfo;
    private android.util.SparseArray<int[]> mAttributeResolutionStacks;
    private android.util.SparseIntArray mAttributeSourceResId;

    @android.view.ViewDebug.ExportedProperty(category = "attributes", hasAdjacentMapping = true)
    public java.lang.String[] mAttributes;
    private java.lang.String[] mAutofillHints;
    private android.view.autofill.AutofillId mAutofillId;
    private int mAutofillViewId;

    @android.view.ViewDebug.ExportedProperty(deepExport = true, prefix = "bg_")
    private android.graphics.drawable.Drawable mBackground;
    android.graphics.RenderNode mBackgroundRenderNode;
    private int mBackgroundResource;
    private boolean mBackgroundSizeChanged;
    private android.view.View.TintInfo mBackgroundTint;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    protected int mBottom;
    public boolean mCachingFailed;

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    android.graphics.Rect mClipBounds;
    private android.view.contentcapture.ContentCaptureSession mContentCaptureSession;
    private boolean mContentCaptureSessionCached;
    private java.lang.CharSequence mContentDescription;

    @android.view.ViewDebug.ExportedProperty(deepExport = true)
    protected android.content.Context mContext;
    protected android.view.animation.Animation mCurrentAnimation;
    private android.graphics.drawable.Drawable mDefaultFocusHighlight;
    private android.graphics.drawable.Drawable mDefaultFocusHighlightCache;
    boolean mDefaultFocusHighlightEnabled;
    private boolean mDefaultFocusHighlightSizeChanged;
    private int[] mDrawableState;
    private android.graphics.Bitmap mDrawingCache;
    private int mDrawingCacheBackgroundColor;
    private int mExplicitAccessibilityDataSensitive;
    private int mExplicitStyle;
    private android.view.ViewTreeObserver mFloatingTreeObserver;

    @android.view.ViewDebug.ExportedProperty(deepExport = true, prefix = "fg_")
    private android.view.View.ForegroundInfo mForegroundInfo;
    private float mFrameContentVelocity;
    private java.util.ArrayList<android.view.FrameMetricsObserver> mFrameMetricsObservers;
    int mFrameRateCompatibility;
    android.view.GhostView mGhostView;
    private float mHandwritingBoundsOffsetBottom;
    private float mHandwritingBoundsOffsetLeft;
    private float mHandwritingBoundsOffsetRight;
    private float mHandwritingBoundsOffsetTop;
    private int mHandwritingDelegateFlags;
    private java.lang.Runnable mHandwritingDelegatorCallback;
    private boolean mHasPerformedLongPress;
    private boolean mHoveringTouchDelegate;

    @android.view.ViewDebug.ExportedProperty(resolveId = true)
    int mID;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextButtonPress;
    private int mInferredAccessibilityDataSensitive;
    private int mInfrequentUpdateCount;
    protected final android.view.InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    private boolean mIsHandwritingDelegate;
    private android.util.SparseArray<java.lang.Object> mKeyedTags;
    private int mLabelForId;
    private int mLastFrameRateCategory;
    private boolean mLastIsOpaque;
    private long mLastUpdateTimeMillis;
    android.graphics.Paint mLayerPaint;
    int mLayerType;
    private android.graphics.Insets mLayoutInsets;
    protected android.view.ViewGroup.LayoutParams mLayoutParams;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    protected int mLeft;
    private boolean mLeftPaddingDefined;
    android.view.View.ListenerInfo mListenerInfo;
    private float mLongClickX;
    private float mLongClickY;
    private android.view.View.MatchIdPredicate mMatchIdPredicate;
    private android.view.View.MatchLabelForPredicate mMatchLabelForPredicate;
    private android.util.LongSparseLongArray mMeasureCache;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredHeight;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    int mMeasuredWidth;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    private int mMinHeight;

    @android.view.ViewDebug.ExportedProperty(category = "measurement")
    private int mMinWidth;
    protected long mMinusOneFrameIntervalMillis;
    protected long mMinusTwoFrameIntervalMillis;
    private android.view.PointerIcon mMousePointerIcon;
    private android.view.ViewParent mNestedScrollingParent;
    int mNextClusterForwardId;
    private int mNextFocusDownId;
    int mNextFocusForwardId;
    private int mNextFocusLeftId;
    private int mNextFocusRightId;
    private int mNextFocusUpId;
    int mOldHeightMeasureSpec;
    int mOldWidthMeasureSpec;
    android.view.ViewOutlineProvider mOutlineProvider;
    private int mOverScrollMode;
    android.view.ViewOverlay mOverlay;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingBottom;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingLeft;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingRight;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mPaddingTop;
    protected android.view.ViewParent mParent;
    private android.view.View.CheckForLongPress mPendingCheckForLongPress;
    private android.view.View.CheckForTap mPendingCheckForTap;
    private android.view.View.PerformClick mPerformClick;
    private float mPreferredFrameRate;

    @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "FORCE_LAYOUT"), @android.view.ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LAYOUT_REQUIRED"), @android.view.ViewDebug.FlagToString(equals = 32768, mask = 32768, name = "DRAWING_CACHE_INVALID", outputIf = false), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "DRAWN", outputIf = true), @android.view.ViewDebug.FlagToString(equals = 32, mask = 32, name = "NOT_DRAWN", outputIf = false), @android.view.ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "DIRTY")}, formatToHexString = true)
    public int mPrivateFlags;
    int mPrivateFlags2;
    int mPrivateFlags3;
    private int mPrivateFlags4;
    private java.lang.String[] mReceiveContentMimeTypes;
    boolean mRecreateDisplayList;
    final android.graphics.RenderNode mRenderNode;
    private final android.content.res.Resources mResources;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    protected int mRight;
    private boolean mRightPaddingDefined;
    private android.view.RoundScrollbarRenderer mRoundScrollbarRenderer;
    private android.view.HandlerActionQueue mRunQueue;
    private android.view.View.ScrollabilityCache mScrollCache;
    public android.view.HapticScrollFeedbackProvider mScrollFeedbackProvider;
    private android.graphics.drawable.Drawable mScrollIndicatorDrawable;

    @android.view.ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollX;

    @android.view.ViewDebug.ExportedProperty(category = "scrolling")
    protected int mScrollY;
    private android.view.View.SendAccessibilityEventThrottle mSendStateChangedAccessibilityEvent;
    private android.view.View.SendViewScrolledAccessibilityEvent mSendViewScrolledAccessibilityEvent;
    private boolean mSendingHoverAccessibilityEvents;
    private boolean mShouldFakeFocus;
    private int mSourceLayoutId;
    java.lang.String mStartActivityRequestWho;
    private java.lang.CharSequence mStateDescription;
    private android.animation.StateListAnimator mStateListAnimator;

    @android.view.ViewDebug.ExportedProperty(flagMapping = {@android.view.ViewDebug.FlagToString(equals = 1, mask = 1, name = "LOW_PROFILE"), @android.view.ViewDebug.FlagToString(equals = 2, mask = 2, name = "HIDE_NAVIGATION"), @android.view.ViewDebug.FlagToString(equals = 4, mask = 4, name = "FULLSCREEN"), @android.view.ViewDebug.FlagToString(equals = 256, mask = 256, name = "LAYOUT_STABLE"), @android.view.ViewDebug.FlagToString(equals = 512, mask = 512, name = "LAYOUT_HIDE_NAVIGATION"), @android.view.ViewDebug.FlagToString(equals = 1024, mask = 1024, name = "LAYOUT_FULLSCREEN"), @android.view.ViewDebug.FlagToString(equals = 2048, mask = 2048, name = "IMMERSIVE"), @android.view.ViewDebug.FlagToString(equals = 4096, mask = 4096, name = "IMMERSIVE_STICKY"), @android.view.ViewDebug.FlagToString(equals = 8192, mask = 8192, name = "LIGHT_STATUS_BAR"), @android.view.ViewDebug.FlagToString(equals = 16, mask = 16, name = "LIGHT_NAVIGATION_BAR"), @android.view.ViewDebug.FlagToString(equals = 65536, mask = 65536, name = "STATUS_BAR_DISABLE_EXPAND"), @android.view.ViewDebug.FlagToString(equals = 131072, mask = 131072, name = "STATUS_BAR_DISABLE_NOTIFICATION_ICONS"), @android.view.ViewDebug.FlagToString(equals = 262144, mask = 262144, name = "STATUS_BAR_DISABLE_NOTIFICATION_ALERTS"), @android.view.ViewDebug.FlagToString(equals = 524288, mask = 524288, name = "STATUS_BAR_DISABLE_NOTIFICATION_TICKER"), @android.view.ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "STATUS_BAR_DISABLE_SYSTEM_INFO"), @android.view.ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "STATUS_BAR_DISABLE_HOME"), @android.view.ViewDebug.FlagToString(equals = 4194304, mask = 4194304, name = "STATUS_BAR_DISABLE_BACK"), @android.view.ViewDebug.FlagToString(equals = 8388608, mask = 8388608, name = "STATUS_BAR_DISABLE_CLOCK"), @android.view.ViewDebug.FlagToString(equals = 16777216, mask = 16777216, name = "STATUS_BAR_DISABLE_RECENT"), @android.view.ViewDebug.FlagToString(equals = 33554432, mask = 33554432, name = "STATUS_BAR_DISABLE_SEARCH"), @android.view.ViewDebug.FlagToString(equals = 67108864, mask = 67108864, name = "STATUS_BAR_DISABLE_ONGOING_CALL_CHIP")}, formatToHexString = true)
    int mSystemUiVisibility;
    protected java.lang.Object mTag;
    private int[] mTempNestedScrollConsumed;
    android.view.View.TooltipInfo mTooltipInfo;

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    protected int mTop;
    private android.view.TouchDelegate mTouchDelegate;
    private int mTouchSlop;
    private android.view.ViewTraversalTracingStrings mTracingStrings;
    public android.view.View.TransformationInfo mTransformationInfo;
    int mTransientStateCount;
    private java.lang.String mTransitionName;
    int mUnbufferedInputSource;
    private android.graphics.Bitmap mUnscaledDrawingCache;
    private android.view.View.UnsetPressedState mUnsetPressedState;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingBottom;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingEnd;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingLeft;
    int mUserPaddingLeftInitial;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    protected int mUserPaddingRight;
    int mUserPaddingRightInitial;

    @android.view.ViewDebug.ExportedProperty(category = "padding")
    int mUserPaddingStart;
    private float mVerticalScrollFactor;
    private int mVerticalScrollbarPosition;
    private android.os.Vibrator mVibrator;
    private android.view.ViewCredentialHandler mViewCredentialHandler;

    @android.view.ViewDebug.ExportedProperty(formatToHexString = true)
    int mViewFlags;
    private android.view.translation.ViewTranslationCallback mViewTranslationCallback;
    private android.view.translation.ViewTranslationResponse mViewTranslationResponse;
    private android.os.Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;
    public static boolean DEBUG_DRAW = false;
    public static boolean sDebugViewAttributes = false;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR = {16844136};
    private static boolean sCompatibilityDone = false;
    private static boolean sUseBrokenMakeMeasureSpec = false;
    static boolean sUseZeroUnspecifiedMeasureSpec = false;
    private static boolean sIgnoreMeasureCache = false;
    private static boolean sAlwaysRemeasureExactly = false;
    static boolean sTextureViewIgnoresDrawableSetters = false;
    private static final int[] VISIBILITY_FLAGS = {0, 4, 8};
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS = {0, 524288, 1048576};
    protected static final int[] EMPTY_STATE_SET = android.util.StateSet.get(0);
    protected static final int[] WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(1);
    protected static final int[] SELECTED_STATE_SET = android.util.StateSet.get(2);
    protected static final int[] SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(3);
    protected static final int[] FOCUSED_STATE_SET = android.util.StateSet.get(4);
    protected static final int[] FOCUSED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(5);
    protected static final int[] FOCUSED_SELECTED_STATE_SET = android.util.StateSet.get(6);
    protected static final int[] FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(7);
    protected static final int[] ENABLED_STATE_SET = android.util.StateSet.get(8);
    protected static final int[] ENABLED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(9);
    protected static final int[] ENABLED_SELECTED_STATE_SET = android.util.StateSet.get(10);
    protected static final int[] ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(11);
    protected static final int[] ENABLED_FOCUSED_STATE_SET = android.util.StateSet.get(12);
    protected static final int[] ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(13);
    protected static final int[] ENABLED_FOCUSED_SELECTED_STATE_SET = android.util.StateSet.get(14);
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(15);
    protected static final int[] PRESSED_STATE_SET = android.util.StateSet.get(16);
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(17);
    protected static final int[] PRESSED_SELECTED_STATE_SET = android.util.StateSet.get(18);
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(19);
    protected static final int[] PRESSED_FOCUSED_STATE_SET = android.util.StateSet.get(20);
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(21);
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET = android.util.StateSet.get(22);
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(23);
    protected static final int[] PRESSED_ENABLED_STATE_SET = android.util.StateSet.get(24);
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(25);
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET = android.util.StateSet.get(26);
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(27);
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET = android.util.StateSet.get(28);
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(29);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = android.util.StateSet.get(30);
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = android.util.StateSet.get(31);
    private static boolean sToolkitSetFrameRateReadOnlyFlagValue = android.view.flags.Flags.toolkitSetFrameRateReadOnly();
    private static boolean sToolkitMetricsForFrameRateDecisionFlagValue = android.view.flags.Flags.toolkitMetricsForFrameRateDecision();
    private static boolean sUseMeasureCacheDuringForceLayoutFlagValue = android.view.flags.Flags.enableUseMeasureCacheDuringForceLayout();
    static final int DEBUG_CORNERS_COLOR = android.graphics.Color.rgb(63, 127, 255);
    static final java.lang.ThreadLocal<android.graphics.Rect> sThreadLocal = java.lang.ThreadLocal.withInitial(new java.util.function.Supplier() { // from class: android.view.View$$ExternalSyntheticLambda3
        @Override // java.util.function.Supplier
        public final java.lang.Object get() {
            return new android.graphics.Rect();
        }
    });
    private static final int[] LAYOUT_DIRECTION_FLAGS = {0, 1, 2, 3};
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS = {0, 64, 128, 192, 256, 320, 384, 448};
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS = {0, 8192, 16384, 24576, 32768, android.hardware.usb.UsbManager.USB_DATA_TRANSFER_RATE_40G, android.media.audio.common.AudioChannelLayout.VOICE_CALL_MONO};
    private static final java.util.concurrent.atomic.AtomicInteger sNextGeneratedId = new java.util.concurrent.atomic.AtomicInteger(1);
    public static final android.util.Property<android.view.View, java.lang.Float> ALPHA = new android.util.FloatProperty<android.view.View>("alpha") { // from class: android.view.View.2
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setAlpha(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getAlpha());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> TRANSLATION_X = new android.util.FloatProperty<android.view.View>("translationX") { // from class: android.view.View.3
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationX());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> TRANSLATION_Y = new android.util.FloatProperty<android.view.View>("translationY") { // from class: android.view.View.4
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationY());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> TRANSLATION_Z = new android.util.FloatProperty<android.view.View>("translationZ") { // from class: android.view.View.5
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setTranslationZ(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getTranslationZ());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> X = new android.util.FloatProperty<android.view.View>("x") { // from class: android.view.View.6
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getX());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> Y = new android.util.FloatProperty<android.view.View>("y") { // from class: android.view.View.7
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getY());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> Z = new android.util.FloatProperty<android.view.View>("z") { // from class: android.view.View.8
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setZ(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getZ());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> ROTATION = new android.util.FloatProperty<android.view.View>("rotation") { // from class: android.view.View.9
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotation(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotation());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> ROTATION_X = new android.util.FloatProperty<android.view.View>("rotationX") { // from class: android.view.View.10
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotationX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotationX());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> ROTATION_Y = new android.util.FloatProperty<android.view.View>("rotationY") { // from class: android.view.View.11
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setRotationY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getRotationY());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> SCALE_X = new android.util.FloatProperty<android.view.View>("scaleX") { // from class: android.view.View.12
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScaleX(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScaleX());
        }
    };
    public static final android.util.Property<android.view.View, java.lang.Float> SCALE_Y = new android.util.FloatProperty<android.view.View>("scaleY") { // from class: android.view.View.13
        @Override // android.util.FloatProperty
        public void setValue(android.view.View view, float f) {
            view.setScaleY(f);
        }

        @Override // android.util.Property
        public java.lang.Float get(android.view.View view) {
            return java.lang.Float.valueOf(view.getScaleY());
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AccessibilityDataSensitive {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutofillFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutofillImportance {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutofillType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentCaptureImportance {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ContentSensitivity {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface DrawingCacheQuality {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FindViewFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusRealDirection {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Focusable {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface FocusableMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LayerType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LayoutDir {
    }

    public interface OnApplyWindowInsetsListener {
        android.view.WindowInsets onApplyWindowInsets(android.view.View view, android.view.WindowInsets windowInsets);
    }

    public interface OnAttachStateChangeListener {
        void onViewAttachedToWindow(android.view.View view);

        void onViewDetachedFromWindow(android.view.View view);
    }

    public interface OnCapturedPointerListener {
        boolean onCapturedPointer(android.view.View view, android.view.MotionEvent motionEvent);
    }

    public interface OnClickListener {
        void onClick(android.view.View view);
    }

    public interface OnContextClickListener {
        boolean onContextClick(android.view.View view);
    }

    public interface OnCreateContextMenuListener {
        void onCreateContextMenu(android.view.ContextMenu contextMenu, android.view.View view, android.view.ContextMenu.ContextMenuInfo contextMenuInfo);
    }

    public interface OnDragListener {
        boolean onDrag(android.view.View view, android.view.DragEvent dragEvent);
    }

    public interface OnFocusChangeListener {
        void onFocusChange(android.view.View view, boolean z);
    }

    public interface OnGenericMotionListener {
        boolean onGenericMotion(android.view.View view, android.view.MotionEvent motionEvent);
    }

    public interface OnHoverListener {
        boolean onHover(android.view.View view, android.view.MotionEvent motionEvent);
    }

    public interface OnKeyListener {
        boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent);
    }

    public interface OnLayoutChangeListener {
        void onLayoutChange(android.view.View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
    }

    public interface OnScrollChangeListener {
        void onScrollChange(android.view.View view, int i, int i2, int i3, int i4);
    }

    @java.lang.Deprecated
    public interface OnSystemUiVisibilityChangeListener {
        void onSystemUiVisibilityChange(int i);
    }

    public interface OnTouchListener {
        boolean onTouch(android.view.View view, android.view.MotionEvent motionEvent);
    }

    public interface OnUnhandledKeyEventListener {
        boolean onUnhandledKeyEvent(android.view.View view, android.view.KeyEvent keyEvent);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ResolvedLayoutDir {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScrollBarStyle {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScrollCaptureHint {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface SystemUiVisibility {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TextAlignment {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ViewStructureType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.view.View> {
        private int mAccessibilityFocusedId;
        private int mAccessibilityHeadingId;
        private int mAccessibilityLiveRegionId;
        private int mAccessibilityPaneTitleId;
        private int mAccessibilityTraversalAfterId;
        private int mAccessibilityTraversalBeforeId;
        private int mActivatedId;
        private int mAlphaId;
        private int mAutofillHintsId;
        private int mBackgroundId;
        private int mBackgroundTintId;
        private int mBackgroundTintModeId;
        private int mBaselineId;
        private int mClickableId;
        private int mContentDescriptionId;
        private int mContextClickableId;
        private int mDefaultFocusHighlightEnabledId;
        private int mDrawingCacheQualityId;
        private int mDuplicateParentStateId;
        private int mElevationId;
        private int mEnabledId;
        private int mFadingEdgeLengthId;
        private int mFilterTouchesWhenObscuredId;
        private int mFitsSystemWindowsId;
        private int mFocusableId;
        private int mFocusableInTouchModeId;
        private int mFocusedByDefaultId;
        private int mFocusedId;
        private int mForceDarkAllowedId;
        private int mForegroundGravityId;
        private int mForegroundId;
        private int mForegroundTintId;
        private int mForegroundTintModeId;
        private int mHapticFeedbackEnabledId;
        private int mIdId;
        private int mImportantForAccessibilityId;
        private int mImportantForAutofillId;
        private int mImportantForContentCaptureId;
        private int mIsScrollContainerId;
        private int mKeepScreenOnId;
        private int mKeyboardNavigationClusterId;
        private int mLabelForId;
        private int mLayerTypeId;
        private int mLayoutDirectionId;
        private int mLongClickableId;
        private int mMinHeightId;
        private int mMinWidthId;
        private int mNestedScrollingEnabledId;
        private int mNextClusterForwardId;
        private int mNextFocusDownId;
        private int mNextFocusForwardId;
        private int mNextFocusLeftId;
        private int mNextFocusRightId;
        private int mNextFocusUpId;
        private int mOutlineAmbientShadowColorId;
        private int mOutlineProviderId;
        private int mOutlineSpotShadowColorId;
        private int mOverScrollModeId;
        private int mPaddingBottomId;
        private int mPaddingLeftId;
        private int mPaddingRightId;
        private int mPaddingTopId;
        private int mPointerIconId;
        private int mPressedId;
        private boolean mPropertiesMapped = false;
        private int mRawLayoutDirectionId;
        private int mRawTextAlignmentId;
        private int mRawTextDirectionId;
        private int mRequiresFadingEdgeId;
        private int mRotationId;
        private int mRotationXId;
        private int mRotationYId;
        private int mSaveEnabledId;
        private int mScaleXId;
        private int mScaleYId;
        private int mScreenReaderFocusableId;
        private int mScrollIndicatorsId;
        private int mScrollXId;
        private int mScrollYId;
        private int mScrollbarDefaultDelayBeforeFadeId;
        private int mScrollbarFadeDurationId;
        private int mScrollbarSizeId;
        private int mScrollbarStyleId;
        private int mSelectedId;
        private int mSolidColorId;
        private int mSoundEffectsEnabledId;
        private int mStateListAnimatorId;
        private int mTagId;
        private int mTextAlignmentId;
        private int mTextDirectionId;
        private int mTooltipTextId;
        private int mTransformPivotXId;
        private int mTransformPivotYId;
        private int mTransitionNameId;
        private int mTranslationXId;
        private int mTranslationYId;
        private int mTranslationZId;
        private int mVisibilityId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            this.mAccessibilityFocusedId = propertyMapper.mapBoolean("accessibilityFocused", 0);
            this.mAccessibilityHeadingId = propertyMapper.mapBoolean("accessibilityHeading", 16844160);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "polite");
            sparseArray.put(2, "assertive");
            java.util.Objects.requireNonNull(sparseArray);
            this.mAccessibilityLiveRegionId = propertyMapper.mapIntEnum("accessibilityLiveRegion", 16843758, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            this.mAccessibilityPaneTitleId = propertyMapper.mapObject("accessibilityPaneTitle", 16844156);
            this.mAccessibilityTraversalAfterId = propertyMapper.mapResourceId("accessibilityTraversalAfter", 16843986);
            this.mAccessibilityTraversalBeforeId = propertyMapper.mapResourceId("accessibilityTraversalBefore", 16843985);
            this.mActivatedId = propertyMapper.mapBoolean("activated", 0);
            this.mAlphaId = propertyMapper.mapFloat("alpha", 16843551);
            this.mAutofillHintsId = propertyMapper.mapObject("autofillHints", 16844118);
            this.mBackgroundId = propertyMapper.mapObject(android.net.NetworkPolicyManager.FIREWALL_CHAIN_NAME_BACKGROUND, 16842964);
            this.mBackgroundTintId = propertyMapper.mapObject("backgroundTint", 16843883);
            this.mBackgroundTintModeId = propertyMapper.mapObject("backgroundTintMode", 16843884);
            this.mBaselineId = propertyMapper.mapInt("baseline", 16843548);
            this.mClickableId = propertyMapper.mapBoolean("clickable", 16842981);
            this.mContentDescriptionId = propertyMapper.mapObject("contentDescription", 16843379);
            this.mContextClickableId = propertyMapper.mapBoolean("contextClickable", 16844007);
            this.mDefaultFocusHighlightEnabledId = propertyMapper.mapBoolean("defaultFocusHighlightEnabled", 16844130);
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            sparseArray2.put(0, "auto");
            sparseArray2.put(524288, "low");
            sparseArray2.put(1048576, "high");
            java.util.Objects.requireNonNull(sparseArray2);
            this.mDrawingCacheQualityId = propertyMapper.mapIntEnum("drawingCacheQuality", 16842984, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mDuplicateParentStateId = propertyMapper.mapBoolean("duplicateParentState", 16842985);
            this.mElevationId = propertyMapper.mapFloat("elevation", 16843840);
            this.mEnabledId = propertyMapper.mapBoolean("enabled", 16842766);
            this.mFadingEdgeLengthId = propertyMapper.mapInt("fadingEdgeLength", 16842976);
            this.mFilterTouchesWhenObscuredId = propertyMapper.mapBoolean("filterTouchesWhenObscured", 16843460);
            this.mFitsSystemWindowsId = propertyMapper.mapBoolean("fitsSystemWindows", 16842973);
            android.util.SparseArray sparseArray3 = new android.util.SparseArray();
            sparseArray3.put(0, "false");
            sparseArray3.put(1, "true");
            sparseArray3.put(16, "auto");
            java.util.Objects.requireNonNull(sparseArray3);
            this.mFocusableId = propertyMapper.mapIntEnum("focusable", 16842970, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mFocusableInTouchModeId = propertyMapper.mapBoolean("focusableInTouchMode", 16842971);
            this.mFocusedId = propertyMapper.mapBoolean("focused", 0);
            this.mFocusedByDefaultId = propertyMapper.mapBoolean("focusedByDefault", 16844100);
            this.mForceDarkAllowedId = propertyMapper.mapBoolean("forceDarkAllowed", 16844172);
            this.mForegroundId = propertyMapper.mapObject("foreground", 16843017);
            this.mForegroundGravityId = propertyMapper.mapGravity("foregroundGravity", 16843264);
            this.mForegroundTintId = propertyMapper.mapObject("foregroundTint", 16843885);
            this.mForegroundTintModeId = propertyMapper.mapObject("foregroundTintMode", 16843886);
            this.mHapticFeedbackEnabledId = propertyMapper.mapBoolean("hapticFeedbackEnabled", 16843358);
            this.mIdId = propertyMapper.mapResourceId("id", 16842960);
            android.util.SparseArray sparseArray4 = new android.util.SparseArray();
            sparseArray4.put(0, "auto");
            sparseArray4.put(1, android.media.MediaMetrics.Value.YES);
            sparseArray4.put(2, android.media.MediaMetrics.Value.NO);
            sparseArray4.put(4, "noHideDescendants");
            java.util.Objects.requireNonNull(sparseArray4);
            this.mImportantForAccessibilityId = propertyMapper.mapIntEnum("importantForAccessibility", 16843690, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray4));
            android.util.SparseArray sparseArray5 = new android.util.SparseArray();
            sparseArray5.put(0, "auto");
            sparseArray5.put(1, android.media.MediaMetrics.Value.YES);
            sparseArray5.put(2, android.media.MediaMetrics.Value.NO);
            sparseArray5.put(4, "yesExcludeDescendants");
            sparseArray5.put(8, "noExcludeDescendants");
            java.util.Objects.requireNonNull(sparseArray5);
            this.mImportantForAutofillId = propertyMapper.mapIntEnum("importantForAutofill", 16844120, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray5));
            android.util.SparseArray sparseArray6 = new android.util.SparseArray();
            sparseArray6.put(0, "auto");
            sparseArray6.put(1, android.media.MediaMetrics.Value.YES);
            sparseArray6.put(2, android.media.MediaMetrics.Value.NO);
            sparseArray6.put(4, "yesExcludeDescendants");
            sparseArray6.put(8, "noExcludeDescendants");
            java.util.Objects.requireNonNull(sparseArray6);
            this.mImportantForContentCaptureId = propertyMapper.mapIntEnum("importantForContentCapture", 16844295, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray6));
            this.mIsScrollContainerId = propertyMapper.mapBoolean("isScrollContainer", 16843342);
            this.mKeepScreenOnId = propertyMapper.mapBoolean("keepScreenOn", 16843286);
            this.mKeyboardNavigationClusterId = propertyMapper.mapBoolean("keyboardNavigationCluster", 16844096);
            this.mLabelForId = propertyMapper.mapResourceId("labelFor", 16843718);
            android.util.SparseArray sparseArray7 = new android.util.SparseArray();
            sparseArray7.put(0, "none");
            sparseArray7.put(1, "software");
            sparseArray7.put(2, "hardware");
            java.util.Objects.requireNonNull(sparseArray7);
            this.mLayerTypeId = propertyMapper.mapIntEnum("layerType", 16843604, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray7));
            android.util.SparseArray sparseArray8 = new android.util.SparseArray();
            sparseArray8.put(0, "ltr");
            sparseArray8.put(1, "rtl");
            java.util.Objects.requireNonNull(sparseArray8);
            this.mLayoutDirectionId = propertyMapper.mapIntEnum("layoutDirection", 16843698, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray8));
            this.mLongClickableId = propertyMapper.mapBoolean("longClickable", 16842982);
            this.mMinHeightId = propertyMapper.mapInt("minHeight", 16843072);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mNestedScrollingEnabledId = propertyMapper.mapBoolean("nestedScrollingEnabled", 16843830);
            this.mNextClusterForwardId = propertyMapper.mapResourceId("nextClusterForward", 16844098);
            this.mNextFocusDownId = propertyMapper.mapResourceId("nextFocusDown", 16842980);
            this.mNextFocusForwardId = propertyMapper.mapResourceId("nextFocusForward", 16843580);
            this.mNextFocusLeftId = propertyMapper.mapResourceId("nextFocusLeft", 16842977);
            this.mNextFocusRightId = propertyMapper.mapResourceId("nextFocusRight", 16842978);
            this.mNextFocusUpId = propertyMapper.mapResourceId("nextFocusUp", 16842979);
            this.mOutlineAmbientShadowColorId = propertyMapper.mapColor("outlineAmbientShadowColor", 16844162);
            this.mOutlineProviderId = propertyMapper.mapObject("outlineProvider", 16843960);
            this.mOutlineSpotShadowColorId = propertyMapper.mapColor("outlineSpotShadowColor", 16844161);
            android.util.SparseArray sparseArray9 = new android.util.SparseArray();
            sparseArray9.put(0, "always");
            sparseArray9.put(1, "ifContentScrolls");
            sparseArray9.put(2, "never");
            java.util.Objects.requireNonNull(sparseArray9);
            this.mOverScrollModeId = propertyMapper.mapIntEnum("overScrollMode", 16843457, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray9));
            this.mPaddingBottomId = propertyMapper.mapInt("paddingBottom", 16842969);
            this.mPaddingLeftId = propertyMapper.mapInt("paddingLeft", 16842966);
            this.mPaddingRightId = propertyMapper.mapInt("paddingRight", 16842968);
            this.mPaddingTopId = propertyMapper.mapInt("paddingTop", 16842967);
            this.mPointerIconId = propertyMapper.mapObject("pointerIcon", 16844041);
            this.mPressedId = propertyMapper.mapBoolean("pressed", 0);
            android.util.SparseArray sparseArray10 = new android.util.SparseArray();
            sparseArray10.put(0, "ltr");
            sparseArray10.put(1, "rtl");
            sparseArray10.put(2, "inherit");
            sparseArray10.put(3, "locale");
            java.util.Objects.requireNonNull(sparseArray10);
            this.mRawLayoutDirectionId = propertyMapper.mapIntEnum("rawLayoutDirection", 0, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray10));
            android.util.SparseArray sparseArray11 = new android.util.SparseArray();
            sparseArray11.put(0, "inherit");
            sparseArray11.put(1, "gravity");
            sparseArray11.put(2, "textStart");
            sparseArray11.put(3, "textEnd");
            sparseArray11.put(4, "center");
            sparseArray11.put(5, "viewStart");
            sparseArray11.put(6, "viewEnd");
            java.util.Objects.requireNonNull(sparseArray11);
            this.mRawTextAlignmentId = propertyMapper.mapIntEnum("rawTextAlignment", 0, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray11));
            android.util.SparseArray sparseArray12 = new android.util.SparseArray();
            sparseArray12.put(0, "inherit");
            sparseArray12.put(1, "firstStrong");
            sparseArray12.put(2, "anyRtl");
            sparseArray12.put(3, "ltr");
            sparseArray12.put(4, "rtl");
            sparseArray12.put(5, "locale");
            sparseArray12.put(6, "firstStrongLtr");
            sparseArray12.put(7, "firstStrongRtl");
            java.util.Objects.requireNonNull(sparseArray12);
            this.mRawTextDirectionId = propertyMapper.mapIntEnum("rawTextDirection", 0, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray12));
            android.view.inspector.IntFlagMapping intFlagMapping = new android.view.inspector.IntFlagMapping();
            intFlagMapping.add(4096, 4096, android.app.slice.Slice.HINT_HORIZONTAL);
            intFlagMapping.add(12288, 0, "none");
            intFlagMapping.add(8192, 8192, "vertical");
            java.util.Objects.requireNonNull(intFlagMapping);
            this.mRequiresFadingEdgeId = propertyMapper.mapIntFlag("requiresFadingEdge", 16843685, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping));
            this.mRotationId = propertyMapper.mapFloat("rotation", 16843558);
            this.mRotationXId = propertyMapper.mapFloat("rotationX", 16843559);
            this.mRotationYId = propertyMapper.mapFloat("rotationY", 16843560);
            this.mSaveEnabledId = propertyMapper.mapBoolean("saveEnabled", 16842983);
            this.mScaleXId = propertyMapper.mapFloat("scaleX", 16843556);
            this.mScaleYId = propertyMapper.mapFloat("scaleY", 16843557);
            this.mScreenReaderFocusableId = propertyMapper.mapBoolean("screenReaderFocusable", 16844148);
            android.view.inspector.IntFlagMapping intFlagMapping2 = new android.view.inspector.IntFlagMapping();
            intFlagMapping2.add(2, 2, "bottom");
            intFlagMapping2.add(32, 32, "end");
            intFlagMapping2.add(4, 4, android.inputmethodservice.navigationbar.NavigationBarInflaterView.LEFT);
            intFlagMapping2.add(-1, 0, "none");
            intFlagMapping2.add(8, 8, android.inputmethodservice.navigationbar.NavigationBarInflaterView.RIGHT);
            intFlagMapping2.add(16, 16, "start");
            intFlagMapping2.add(1, 1, "top");
            java.util.Objects.requireNonNull(intFlagMapping2);
            this.mScrollIndicatorsId = propertyMapper.mapIntFlag("scrollIndicators", 16844006, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping2));
            this.mScrollXId = propertyMapper.mapInt("scrollX", 16842962);
            this.mScrollYId = propertyMapper.mapInt("scrollY", 16842963);
            this.mScrollbarDefaultDelayBeforeFadeId = propertyMapper.mapInt("scrollbarDefaultDelayBeforeFade", 16843433);
            this.mScrollbarFadeDurationId = propertyMapper.mapInt("scrollbarFadeDuration", 16843432);
            this.mScrollbarSizeId = propertyMapper.mapInt("scrollbarSize", 16842851);
            android.util.SparseArray sparseArray13 = new android.util.SparseArray();
            sparseArray13.put(0, "insideOverlay");
            sparseArray13.put(16777216, "insideInset");
            sparseArray13.put(33554432, "outsideOverlay");
            sparseArray13.put(50331648, "outsideInset");
            java.util.Objects.requireNonNull(sparseArray13);
            this.mScrollbarStyleId = propertyMapper.mapIntEnum("scrollbarStyle", 16842879, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray13));
            this.mSelectedId = propertyMapper.mapBoolean(android.app.slice.Slice.HINT_SELECTED, 0);
            this.mSolidColorId = propertyMapper.mapColor("solidColor", 16843594);
            this.mSoundEffectsEnabledId = propertyMapper.mapBoolean("soundEffectsEnabled", 16843285);
            this.mStateListAnimatorId = propertyMapper.mapObject("stateListAnimator", 16843848);
            this.mTagId = propertyMapper.mapObject("tag", 16842961);
            android.util.SparseArray sparseArray14 = new android.util.SparseArray();
            sparseArray14.put(1, "gravity");
            sparseArray14.put(2, "textStart");
            sparseArray14.put(3, "textEnd");
            sparseArray14.put(4, "center");
            sparseArray14.put(5, "viewStart");
            sparseArray14.put(6, "viewEnd");
            java.util.Objects.requireNonNull(sparseArray14);
            this.mTextAlignmentId = propertyMapper.mapIntEnum("textAlignment", 16843697, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray14));
            android.util.SparseArray sparseArray15 = new android.util.SparseArray();
            sparseArray15.put(1, "firstStrong");
            sparseArray15.put(2, "anyRtl");
            sparseArray15.put(3, "ltr");
            sparseArray15.put(4, "rtl");
            sparseArray15.put(5, "locale");
            sparseArray15.put(6, "firstStrongLtr");
            sparseArray15.put(7, "firstStrongRtl");
            java.util.Objects.requireNonNull(sparseArray15);
            this.mTextDirectionId = propertyMapper.mapIntEnum("textDirection", 0, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray15));
            this.mTooltipTextId = propertyMapper.mapObject("tooltipText", 16844084);
            this.mTransformPivotXId = propertyMapper.mapFloat("transformPivotX", 16843552);
            this.mTransformPivotYId = propertyMapper.mapFloat("transformPivotY", 16843553);
            this.mTransitionNameId = propertyMapper.mapObject("transitionName", 16843776);
            this.mTranslationXId = propertyMapper.mapFloat("translationX", 16843554);
            this.mTranslationYId = propertyMapper.mapFloat("translationY", 16843555);
            this.mTranslationZId = propertyMapper.mapFloat("translationZ", 16843770);
            android.util.SparseArray sparseArray16 = new android.util.SparseArray();
            sparseArray16.put(0, android.provider.CalendarContract.CalendarColumns.VISIBLE);
            sparseArray16.put(4, "invisible");
            sparseArray16.put(8, "gone");
            java.util.Objects.requireNonNull(sparseArray16);
            this.mVisibilityId = propertyMapper.mapIntEnum(android.provider.Downloads.Impl.COLUMN_VISIBILITY, 16842972, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray16));
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.view.View view, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAccessibilityFocusedId, view.isAccessibilityFocused());
            propertyReader.readBoolean(this.mAccessibilityHeadingId, view.isAccessibilityHeading());
            propertyReader.readIntEnum(this.mAccessibilityLiveRegionId, view.getAccessibilityLiveRegion());
            propertyReader.readObject(this.mAccessibilityPaneTitleId, view.getAccessibilityPaneTitle());
            propertyReader.readResourceId(this.mAccessibilityTraversalAfterId, view.getAccessibilityTraversalAfter());
            propertyReader.readResourceId(this.mAccessibilityTraversalBeforeId, view.getAccessibilityTraversalBefore());
            propertyReader.readBoolean(this.mActivatedId, view.isActivated());
            propertyReader.readFloat(this.mAlphaId, view.getAlpha());
            propertyReader.readObject(this.mAutofillHintsId, view.getAutofillHints());
            propertyReader.readObject(this.mBackgroundId, view.getBackground());
            propertyReader.readObject(this.mBackgroundTintId, view.getBackgroundTintList());
            propertyReader.readObject(this.mBackgroundTintModeId, view.getBackgroundTintMode());
            propertyReader.readInt(this.mBaselineId, view.getBaseline());
            propertyReader.readBoolean(this.mClickableId, view.isClickable());
            propertyReader.readObject(this.mContentDescriptionId, view.getContentDescription());
            propertyReader.readBoolean(this.mContextClickableId, view.isContextClickable());
            propertyReader.readBoolean(this.mDefaultFocusHighlightEnabledId, view.getDefaultFocusHighlightEnabled());
            propertyReader.readIntEnum(this.mDrawingCacheQualityId, view.getDrawingCacheQuality());
            propertyReader.readBoolean(this.mDuplicateParentStateId, view.isDuplicateParentStateEnabled());
            propertyReader.readFloat(this.mElevationId, view.getElevation());
            propertyReader.readBoolean(this.mEnabledId, view.isEnabled());
            propertyReader.readInt(this.mFadingEdgeLengthId, view.getFadingEdgeLength());
            propertyReader.readBoolean(this.mFilterTouchesWhenObscuredId, view.getFilterTouchesWhenObscured());
            propertyReader.readBoolean(this.mFitsSystemWindowsId, view.getFitsSystemWindows());
            propertyReader.readIntEnum(this.mFocusableId, view.getFocusable());
            propertyReader.readBoolean(this.mFocusableInTouchModeId, view.isFocusableInTouchMode());
            propertyReader.readBoolean(this.mFocusedId, view.isFocused());
            propertyReader.readBoolean(this.mFocusedByDefaultId, view.isFocusedByDefault());
            propertyReader.readBoolean(this.mForceDarkAllowedId, view.isForceDarkAllowed());
            propertyReader.readObject(this.mForegroundId, view.getForeground());
            propertyReader.readGravity(this.mForegroundGravityId, view.getForegroundGravity());
            propertyReader.readObject(this.mForegroundTintId, view.getForegroundTintList());
            propertyReader.readObject(this.mForegroundTintModeId, view.getForegroundTintMode());
            propertyReader.readBoolean(this.mHapticFeedbackEnabledId, view.isHapticFeedbackEnabled());
            propertyReader.readResourceId(this.mIdId, view.getId());
            propertyReader.readIntEnum(this.mImportantForAccessibilityId, view.getImportantForAccessibility());
            propertyReader.readIntEnum(this.mImportantForAutofillId, view.getImportantForAutofill());
            propertyReader.readIntEnum(this.mImportantForContentCaptureId, view.getImportantForContentCapture());
            propertyReader.readBoolean(this.mIsScrollContainerId, view.isScrollContainer());
            propertyReader.readBoolean(this.mKeepScreenOnId, view.getKeepScreenOn());
            propertyReader.readBoolean(this.mKeyboardNavigationClusterId, view.isKeyboardNavigationCluster());
            propertyReader.readResourceId(this.mLabelForId, view.getLabelFor());
            propertyReader.readIntEnum(this.mLayerTypeId, view.getLayerType());
            propertyReader.readIntEnum(this.mLayoutDirectionId, view.getLayoutDirection());
            propertyReader.readBoolean(this.mLongClickableId, view.isLongClickable());
            propertyReader.readInt(this.mMinHeightId, view.getMinimumHeight());
            propertyReader.readInt(this.mMinWidthId, view.getMinimumWidth());
            propertyReader.readBoolean(this.mNestedScrollingEnabledId, view.isNestedScrollingEnabled());
            propertyReader.readResourceId(this.mNextClusterForwardId, view.getNextClusterForwardId());
            propertyReader.readResourceId(this.mNextFocusDownId, view.getNextFocusDownId());
            propertyReader.readResourceId(this.mNextFocusForwardId, view.getNextFocusForwardId());
            propertyReader.readResourceId(this.mNextFocusLeftId, view.getNextFocusLeftId());
            propertyReader.readResourceId(this.mNextFocusRightId, view.getNextFocusRightId());
            propertyReader.readResourceId(this.mNextFocusUpId, view.getNextFocusUpId());
            propertyReader.readColor(this.mOutlineAmbientShadowColorId, view.getOutlineAmbientShadowColor());
            propertyReader.readObject(this.mOutlineProviderId, view.getOutlineProvider());
            propertyReader.readColor(this.mOutlineSpotShadowColorId, view.getOutlineSpotShadowColor());
            propertyReader.readIntEnum(this.mOverScrollModeId, view.getOverScrollMode());
            propertyReader.readInt(this.mPaddingBottomId, view.getPaddingBottom());
            propertyReader.readInt(this.mPaddingLeftId, view.getPaddingLeft());
            propertyReader.readInt(this.mPaddingRightId, view.getPaddingRight());
            propertyReader.readInt(this.mPaddingTopId, view.getPaddingTop());
            propertyReader.readObject(this.mPointerIconId, view.getPointerIcon());
            propertyReader.readBoolean(this.mPressedId, view.isPressed());
            propertyReader.readIntEnum(this.mRawLayoutDirectionId, view.getRawLayoutDirection());
            propertyReader.readIntEnum(this.mRawTextAlignmentId, view.getRawTextAlignment());
            propertyReader.readIntEnum(this.mRawTextDirectionId, view.getRawTextDirection());
            propertyReader.readIntFlag(this.mRequiresFadingEdgeId, view.getFadingEdge());
            propertyReader.readFloat(this.mRotationId, view.getRotation());
            propertyReader.readFloat(this.mRotationXId, view.getRotationX());
            propertyReader.readFloat(this.mRotationYId, view.getRotationY());
            propertyReader.readBoolean(this.mSaveEnabledId, view.isSaveEnabled());
            propertyReader.readFloat(this.mScaleXId, view.getScaleX());
            propertyReader.readFloat(this.mScaleYId, view.getScaleY());
            propertyReader.readBoolean(this.mScreenReaderFocusableId, view.isScreenReaderFocusable());
            propertyReader.readIntFlag(this.mScrollIndicatorsId, view.getScrollIndicators());
            propertyReader.readInt(this.mScrollXId, view.getScrollX());
            propertyReader.readInt(this.mScrollYId, view.getScrollY());
            propertyReader.readInt(this.mScrollbarDefaultDelayBeforeFadeId, view.getScrollBarDefaultDelayBeforeFade());
            propertyReader.readInt(this.mScrollbarFadeDurationId, view.getScrollBarFadeDuration());
            propertyReader.readInt(this.mScrollbarSizeId, view.getScrollBarSize());
            propertyReader.readIntEnum(this.mScrollbarStyleId, view.getScrollBarStyle());
            propertyReader.readBoolean(this.mSelectedId, view.isSelected());
            propertyReader.readColor(this.mSolidColorId, view.getSolidColor());
            propertyReader.readBoolean(this.mSoundEffectsEnabledId, view.isSoundEffectsEnabled());
            propertyReader.readObject(this.mStateListAnimatorId, view.getStateListAnimator());
            propertyReader.readObject(this.mTagId, view.getTag());
            propertyReader.readIntEnum(this.mTextAlignmentId, view.getTextAlignment());
            propertyReader.readIntEnum(this.mTextDirectionId, view.getTextDirection());
            propertyReader.readObject(this.mTooltipTextId, view.getTooltipText());
            propertyReader.readFloat(this.mTransformPivotXId, view.getPivotX());
            propertyReader.readFloat(this.mTransformPivotYId, view.getPivotY());
            propertyReader.readObject(this.mTransitionNameId, view.getTransitionName());
            propertyReader.readFloat(this.mTranslationXId, view.getTranslationX());
            propertyReader.readFloat(this.mTranslationYId, view.getTranslationY());
            propertyReader.readFloat(this.mTranslationZId, view.getTranslationZ());
            propertyReader.readIntEnum(this.mVisibilityId, view.getVisibility());
        }
    }

    static class TransformationInfo {
        private android.graphics.Matrix mInverseMatrix;
        private final android.graphics.Matrix mMatrix = new android.graphics.Matrix();

        @android.view.ViewDebug.ExportedProperty
        private float mAlpha = 1.0f;
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {
        }
    }

    static class TintInfo {
        android.graphics.BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        android.content.res.ColorStateList mTintList;

        TintInfo() {
        }
    }

    private static class ForegroundInfo {
        private boolean mBoundsChanged;
        private android.graphics.drawable.Drawable mDrawable;
        private int mGravity;
        private boolean mInsidePadding;
        private final android.graphics.Rect mOverlayBounds;
        private final android.graphics.Rect mSelfBounds;
        private android.view.View.TintInfo mTintInfo;

        private ForegroundInfo() {
            this.mGravity = 119;
            this.mInsidePadding = true;
            this.mBoundsChanged = true;
            this.mSelfBounds = new android.graphics.Rect();
            this.mOverlayBounds = new android.graphics.Rect();
        }
    }

    static class ListenerInfo {
        android.view.View.OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
        private java.util.concurrent.CopyOnWriteArrayList<android.view.View.OnAttachStateChangeListener> mOnAttachStateChangeListeners;
        android.view.View.OnCapturedPointerListener mOnCapturedPointerListener;
        public android.view.View.OnClickListener mOnClickListener;
        protected android.view.View.OnContextClickListener mOnContextClickListener;
        protected android.view.View.OnCreateContextMenuListener mOnCreateContextMenuListener;
        private android.view.View.OnDragListener mOnDragListener;
        protected android.view.View.OnFocusChangeListener mOnFocusChangeListener;
        private android.view.View.OnGenericMotionListener mOnGenericMotionListener;
        private android.view.View.OnHoverListener mOnHoverListener;
        private android.view.View.OnKeyListener mOnKeyListener;
        private java.util.ArrayList<android.view.View.OnLayoutChangeListener> mOnLayoutChangeListeners;
        protected android.view.View.OnLongClickListener mOnLongClickListener;
        private android.view.OnReceiveContentListener mOnReceiveContentListener;
        protected android.view.View.OnScrollChangeListener mOnScrollChangeListener;
        private android.view.View.OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener;
        private android.view.View.OnTouchListener mOnTouchListener;
        private java.lang.Runnable mPositionChangedUpdate;
        public android.graphics.RenderNode.PositionUpdateListener mPositionUpdateListener;
        android.view.ScrollCaptureCallback mScrollCaptureCallback;
        private java.util.ArrayList<android.view.View.OnUnhandledKeyEventListener> mUnhandledKeyListeners;
        android.view.WindowInsetsAnimation.Callback mWindowInsetsAnimationCallback;
        private java.util.List<android.graphics.Rect> mSystemGestureExclusionRects = null;
        private java.util.List<android.graphics.Rect> mKeepClearRects = null;
        private java.util.List<android.graphics.Rect> mUnrestrictedKeepClearRects = null;
        private boolean mPreferKeepClear = false;
        private android.graphics.Rect mHandwritingArea = null;

        ListenerInfo() {
        }
    }

    private static class TooltipInfo {
        int mAnchorX;
        int mAnchorY;
        java.lang.Runnable mHideTooltipRunnable;
        int mHoverSlop;
        java.lang.Runnable mShowTooltipRunnable;
        boolean mTooltipFromLongClick;
        com.android.internal.view.TooltipPopup mTooltipPopup;
        java.lang.CharSequence mTooltipText;

        private TooltipInfo() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean updateAnchorPos(android.view.MotionEvent motionEvent) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (java.lang.Math.abs(x - this.mAnchorX) <= this.mHoverSlop && java.lang.Math.abs(y - this.mAnchorY) <= this.mHoverSlop) {
                return false;
            }
            this.mAnchorX = x;
            this.mAnchorY = y;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }
    }

    public View(android.content.Context context) {
        android.view.InputEventConsistencyVerifier inputEventConsistencyVerifier;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        boolean z16;
        this.mScrollFeedbackProvider = null;
        this.mFrameRateCompatibility = 1;
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mExplicitAccessibilityDataSensitive = 0;
        this.mInferredAccessibilityDataSensitive = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = android.view.ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        if (!android.view.InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new android.view.InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mSourceLayoutId = 0;
        this.mUnbufferedInputSource = 0;
        this.mFrameContentVelocity = 0.0f;
        this.mPreferredFrameRate = Float.NaN;
        this.mInfrequentUpdateCount = 0;
        this.mLastUpdateTimeMillis = 0L;
        this.mMinusOneFrameIntervalMillis = 0L;
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameRateCategory = 1;
        this.mShouldFakeFocus = false;
        this.mContext = context;
        this.mResources = context != null ? context.getResources() : null;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        android.view.ViewConfiguration viewConfiguration = android.view.ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mAmbiguousGestureMultiplier = viewConfiguration.getScaledAmbiguousGestureMultiplier();
        setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode = android.graphics.RenderNode.create(getClass().getName(), new android.view.ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int i = context.getApplicationInfo().targetSdkVersion;
            if (i <= 17) {
                z = true;
            } else {
                z = false;
            }
            sUseBrokenMakeMeasureSpec = z;
            if (i < 19) {
                z2 = true;
            } else {
                z2 = false;
            }
            sIgnoreMeasureCache = z2;
            if (i < 23) {
                z3 = true;
            } else {
                z3 = false;
            }
            sUseZeroUnspecifiedMeasureSpec = z3;
            if (i <= 23) {
                z4 = true;
            } else {
                z4 = false;
            }
            sAlwaysRemeasureExactly = z4;
            if (i <= 23) {
                z5 = true;
            } else {
                z5 = false;
            }
            sTextureViewIgnoresDrawableSetters = z5;
            if (i >= 24) {
                z6 = true;
            } else {
                z6 = false;
            }
            sPreserveMarginParamsInLayoutParamConversion = z6;
            if (i < 24) {
                z7 = true;
            } else {
                z7 = false;
            }
            sCascadedDragDrop = z7;
            if (i < 26) {
                z8 = true;
            } else {
                z8 = false;
            }
            sHasFocusableExcludeAutoFocusable = z8;
            if (i < 26) {
                z9 = true;
            } else {
                z9 = false;
            }
            sAutoFocusableOffUIThreadWontNotifyParents = z9;
            sUseDefaultFocusHighlight = context.getResources().getBoolean(com.android.internal.R.bool.config_useDefaultFocusHighlight);
            if (i >= 28) {
                z10 = true;
            } else {
                z10 = false;
            }
            sThrowOnInvalidFloatProperties = z10;
            if (i < 28) {
                z11 = true;
            } else {
                z11 = false;
            }
            sCanFocusZeroSized = z11;
            if (i < 28) {
                z12 = true;
            } else {
                z12 = false;
            }
            sAlwaysAssignFocus = z12;
            if (i < 28) {
                z13 = true;
            } else {
                z13 = false;
            }
            sAcceptZeroSizeDragShadow = z13;
            if (i < 30) {
                z14 = true;
            } else {
                z14 = false;
            }
            sBrokenInsetsDispatch = z14;
            if (i < 29) {
                z15 = true;
            } else {
                z15 = false;
            }
            sBrokenWindowBackground = z15;
            if (i >= 29) {
                z16 = true;
            } else {
                z16 = false;
            }
            android.graphics.drawable.GradientDrawable.sWrapNegativeAngleMeasurements = z16;
            sForceLayoutWhenInsetsChanged = i < 30;
            sCompatibilityDone = true;
        }
    }

    public View(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public View(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r1v14 int, still in use, count: 2, list:
          (r1v14 int) from 0x09e4: IF  (r1v14 int) != (0 int)  -> B:478:0x09e9 A[HIDDEN]
          (r1v14 int) from 0x09e9: PHI (r1v12 int) = (r1v11 int), (r1v14 int) binds: [B:479:0x09e7, B:469:0x09e4] A[DONT_GENERATE, DONT_INLINE]
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:114)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public View(android.content.Context r48, android.util.AttributeSet r49, int r50, int r51) {
        /*
            Method dump skipped, instructions count: 2832
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.view.View.<init>(android.content.Context, android.util.AttributeSet, int, int):void");
    }

    public int[] getAttributeResolutionStack(int i) {
        int i2;
        if (!sDebugViewAttributes || this.mAttributeResolutionStacks == null || this.mAttributeResolutionStacks.get(i) == null) {
            return new int[0];
        }
        int[] iArr = this.mAttributeResolutionStacks.get(i);
        int length = iArr.length;
        if (this.mSourceLayoutId != 0) {
            length++;
        }
        int[] iArr2 = new int[length];
        if (this.mSourceLayoutId == 0) {
            i2 = 0;
        } else {
            iArr2[0] = this.mSourceLayoutId;
            i2 = 1;
        }
        for (int i3 : iArr) {
            iArr2[i2] = i3;
            i2++;
        }
        return iArr2;
    }

    public java.util.Map<java.lang.Integer, java.lang.Integer> getAttributeSourceResourceMap() {
        java.util.HashMap hashMap = new java.util.HashMap();
        if (!sDebugViewAttributes || this.mAttributeSourceResId == null) {
            return hashMap;
        }
        for (int i = 0; i < this.mAttributeSourceResId.size(); i++) {
            hashMap.put(java.lang.Integer.valueOf(this.mAttributeSourceResId.keyAt(i)), java.lang.Integer.valueOf(this.mAttributeSourceResId.valueAt(i)));
        }
        return hashMap;
    }

    public int getExplicitStyle() {
        if (!sDebugViewAttributes) {
            return 0;
        }
        return this.mExplicitStyle;
    }

    private static class DeclaredOnClickListener implements android.view.View.OnClickListener {
        private final android.view.View mHostView;
        private final java.lang.String mMethodName;
        private android.content.Context mResolvedContext;
        private java.lang.reflect.Method mResolvedMethod;

        public DeclaredOnClickListener(android.view.View view, java.lang.String str) {
            this.mHostView = view;
            this.mMethodName = str;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(android.view.View view) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
            } catch (java.lang.IllegalAccessException e) {
                throw new java.lang.IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (java.lang.reflect.InvocationTargetException e2) {
                throw new java.lang.IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }

        private void resolveMethod(android.content.Context context, java.lang.String str) {
            java.lang.reflect.Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, android.view.View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (java.lang.NoSuchMethodException e) {
                }
                if (context instanceof android.content.ContextWrapper) {
                    context = ((android.content.ContextWrapper) context).getBaseContext();
                } else {
                    context = null;
                }
            }
            int id = this.mHostView.getId();
            throw new java.lang.IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick attribute defined on view " + this.mHostView.getClass() + (id == -1 ? "" : " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(id) + "'"));
        }
    }

    View() {
        android.view.InputEventConsistencyVerifier inputEventConsistencyVerifier;
        this.mScrollFeedbackProvider = null;
        this.mFrameRateCompatibility = 1;
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mExplicitAccessibilityDataSensitive = 0;
        this.mInferredAccessibilityDataSensitive = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = android.view.ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        if (!android.view.InputEventConsistencyVerifier.isInstrumentationEnabled()) {
            inputEventConsistencyVerifier = null;
        } else {
            inputEventConsistencyVerifier = new android.view.InputEventConsistencyVerifier(this, 0);
        }
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mSourceLayoutId = 0;
        this.mUnbufferedInputSource = 0;
        this.mFrameContentVelocity = 0.0f;
        this.mPreferredFrameRate = Float.NaN;
        this.mInfrequentUpdateCount = 0;
        this.mLastUpdateTimeMillis = 0L;
        this.mMinusOneFrameIntervalMillis = 0L;
        this.mMinusTwoFrameIntervalMillis = 0L;
        this.mLastFrameRateCategory = 1;
        this.mShouldFakeFocus = false;
        this.mResources = null;
        this.mRenderNode = android.graphics.RenderNode.create(getClass().getName(), new android.view.ViewAnimationHostBridge(this));
    }

    public final boolean isShowingLayoutBounds() {
        return DEBUG_DRAW || (this.mAttachInfo != null && this.mAttachInfo.mDebugLayout);
    }

    public final void setShowingLayoutBounds(boolean z) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mDebugLayout = z;
        }
    }

    private static android.util.SparseArray<java.lang.String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new android.util.SparseArray<>();
        }
        return mAttributeMap;
    }

    private void retrieveExplicitStyle(android.content.res.Resources.Theme theme, android.util.AttributeSet attributeSet) {
        if (!sDebugViewAttributes) {
            return;
        }
        this.mExplicitStyle = theme.getExplicitStyle(attributeSet);
    }

    public final void saveAttributeDataForStyleable(android.content.Context context, int[] iArr, android.util.AttributeSet attributeSet, android.content.res.TypedArray typedArray, int i, int i2) {
        if (!sDebugViewAttributes) {
            return;
        }
        int[] attributeResolutionStack = context.getTheme().getAttributeResolutionStack(i, i2, this.mExplicitStyle);
        if (this.mAttributeResolutionStacks == null) {
            this.mAttributeResolutionStacks = new android.util.SparseArray<>();
        }
        if (this.mAttributeSourceResId == null) {
            this.mAttributeSourceResId = new android.util.SparseIntArray();
        }
        int indexCount = typedArray.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = typedArray.getIndex(i3);
            this.mAttributeSourceResId.append(iArr[index], typedArray.getSourceResourceId(index, 0));
            this.mAttributeResolutionStacks.append(iArr[index], attributeResolutionStack);
        }
    }

    private void saveAttributeData(android.util.AttributeSet attributeSet, android.content.res.TypedArray typedArray) {
        int resourceId;
        int attributeCount = attributeSet == null ? 0 : attributeSet.getAttributeCount();
        int indexCount = typedArray.getIndexCount();
        java.lang.String[] strArr = new java.lang.String[(attributeCount + indexCount) * 2];
        int i = 0;
        for (int i2 = 0; i2 < attributeCount; i2++) {
            strArr[i] = attributeSet.getAttributeName(i2);
            strArr[i + 1] = attributeSet.getAttributeValue(i2);
            i += 2;
        }
        android.content.res.Resources resources = typedArray.getResources();
        android.util.SparseArray<java.lang.String> attributeMap = getAttributeMap();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = typedArray.getIndex(i3);
            if (typedArray.hasValueOrEmpty(index) && (resourceId = typedArray.getResourceId(index, 0)) != 0) {
                java.lang.String str = attributeMap.get(resourceId);
                if (str == null) {
                    try {
                        str = resources.getResourceName(resourceId);
                    } catch (android.content.res.Resources.NotFoundException e) {
                        str = "0x" + java.lang.Integer.toHexString(resourceId);
                    }
                    attributeMap.put(resourceId, str);
                }
                strArr[i] = str;
                strArr[i + 1] = typedArray.getString(index);
                i += 2;
            }
        }
        java.lang.String[] strArr2 = new java.lang.String[i];
        java.lang.System.arraycopy(strArr, 0, strArr2, 0, i);
        this.mAttributes = strArr2;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(256);
        sb.append(getClass().getName());
        sb.append('{');
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(' ');
        switch (this.mViewFlags & 12) {
            case 0:
                sb.append('V');
                break;
            case 4:
                sb.append('I');
                break;
            case 8:
                sb.append('G');
                break;
            default:
                sb.append('.');
                break;
        }
        sb.append((this.mViewFlags & 1) == 1 ? 'F' : '.');
        sb.append((this.mViewFlags & 32) == 0 ? android.text.format.DateFormat.DAY : '.');
        sb.append((this.mViewFlags & 128) == 128 ? '.' : 'D');
        sb.append((256 & this.mViewFlags) != 0 ? 'H' : '.');
        sb.append((this.mViewFlags & 512) == 0 ? '.' : 'V');
        sb.append((this.mViewFlags & 16384) != 0 ? 'C' : '.');
        sb.append((this.mViewFlags & 2097152) != 0 ? android.text.format.DateFormat.STANDALONE_MONTH : '.');
        sb.append((this.mViewFlags & 8388608) != 0 ? 'X' : '.');
        sb.append(' ');
        sb.append((this.mPrivateFlags & 8) != 0 ? 'R' : '.');
        sb.append((this.mPrivateFlags & 2) == 0 ? '.' : 'F');
        sb.append((this.mPrivateFlags & 4) != 0 ? 'S' : '.');
        if ((this.mPrivateFlags & 33554432) != 0) {
            sb.append('p');
        } else {
            sb.append((this.mPrivateFlags & 16384) != 0 ? 'P' : '.');
        }
        sb.append((this.mPrivateFlags & 268435456) == 0 ? '.' : 'H');
        sb.append((this.mPrivateFlags & 1073741824) != 0 ? android.text.format.DateFormat.CAPITAL_AM_PM : '.');
        sb.append((this.mPrivateFlags & Integer.MIN_VALUE) == 0 ? '.' : 'I');
        sb.append((this.mPrivateFlags & 2097152) != 0 ? 'D' : '.');
        sb.append(' ');
        sb.append(this.mLeft);
        sb.append(',');
        sb.append(this.mTop);
        sb.append('-');
        sb.append(this.mRight);
        sb.append(',');
        sb.append(this.mBottom);
        appendId(sb);
        if (this.mAutofillId != null) {
            sb.append(" aid=");
            sb.append(this.mAutofillId);
        }
        sb.append("}");
        return sb.toString();
    }

    void appendId(java.lang.StringBuilder sb) {
        java.lang.String resourcePackageName;
        int id = getId();
        if (id != -1) {
            sb.append(" #");
            sb.append(java.lang.Integer.toHexString(id));
            android.content.res.Resources resources = this.mResources;
            if (id > 0 && android.content.res.Resources.resourceHasPackage(id) && resources != null) {
                switch ((-16777216) & id) {
                    case 16777216:
                        resourcePackageName = "android";
                        java.lang.String resourceTypeName = resources.getResourceTypeName(id);
                        java.lang.String resourceEntryName = resources.getResourceEntryName(id);
                        sb.append(" ");
                        sb.append(resourcePackageName);
                        sb.append(":");
                        sb.append(resourceTypeName);
                        sb.append("/");
                        sb.append(resourceEntryName);
                        return;
                    case 2130706432:
                        resourcePackageName = "app";
                        java.lang.String resourceTypeName2 = resources.getResourceTypeName(id);
                        java.lang.String resourceEntryName2 = resources.getResourceEntryName(id);
                        sb.append(" ");
                        sb.append(resourcePackageName);
                        sb.append(":");
                        sb.append(resourceTypeName2);
                        sb.append("/");
                        sb.append(resourceEntryName2);
                        return;
                    default:
                        try {
                            resourcePackageName = resources.getResourcePackageName(id);
                            java.lang.String resourceTypeName22 = resources.getResourceTypeName(id);
                            java.lang.String resourceEntryName22 = resources.getResourceEntryName(id);
                            sb.append(" ");
                            sb.append(resourcePackageName);
                            sb.append(":");
                            sb.append(resourceTypeName22);
                            sb.append("/");
                            sb.append(resourceEntryName22);
                            return;
                        } catch (android.content.res.Resources.NotFoundException e) {
                        }
                }
            }
        }
    }

    protected void initializeFadingEdge(android.content.res.TypedArray typedArray) {
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.View);
        initializeFadingEdgeInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    protected void initializeFadingEdgeInternal(android.content.res.TypedArray typedArray) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = typedArray.getDimensionPixelSize(25, android.view.ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    public int getVerticalFadingEdgeLength() {
        android.view.View.ScrollabilityCache scrollabilityCache;
        if (isVerticalFadingEdgeEnabled() && (scrollabilityCache = this.mScrollCache) != null) {
            return scrollabilityCache.fadingEdgeLength;
        }
        return 0;
    }

    public void setFadingEdgeLength(int i) {
        initScrollCache();
        this.mScrollCache.fadingEdgeLength = i;
    }

    public void clearCredentialManagerRequest() {
        if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            android.util.Log.v(AUTOFILL_LOG_TAG, "clearCredentialManagerRequest called");
        }
        this.mViewCredentialHandler = null;
    }

    public void setCredentialManagerRequest(android.credentials.GetCredentialRequest getCredentialRequest, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
        com.android.internal.util.Preconditions.checkNotNull(getCredentialRequest, "request must not be null");
        com.android.internal.util.Preconditions.checkNotNull(outcomeReceiver, "request must not be null");
        for (android.credentials.CredentialOption credentialOption : getCredentialRequest.getCredentialOptions()) {
            java.util.ArrayList<? extends android.os.Parcelable> parcelableArrayList = credentialOption.getCandidateQueryData().getParcelableArrayList(android.service.credentials.CredentialProviderService.EXTRA_AUTOFILL_ID, android.view.autofill.AutofillId.class);
            if (parcelableArrayList == null) {
                parcelableArrayList = new java.util.ArrayList<>();
            }
            if (!parcelableArrayList.contains(getAutofillId())) {
                parcelableArrayList.add(getAutofillId());
            }
            credentialOption.getCandidateQueryData().putParcelableArrayList(android.service.credentials.CredentialProviderService.EXTRA_AUTOFILL_ID, parcelableArrayList);
        }
        this.mViewCredentialHandler = new android.view.ViewCredentialHandler(getCredentialRequest, outcomeReceiver);
    }

    public android.view.ViewCredentialHandler getViewCredentialHandler() {
        return this.mViewCredentialHandler;
    }

    public int getHorizontalFadingEdgeLength() {
        android.view.View.ScrollabilityCache scrollabilityCache;
        if (isHorizontalFadingEdgeEnabled() && (scrollabilityCache = this.mScrollCache) != null) {
            return scrollabilityCache.fadingEdgeLength;
        }
        return 0;
    }

    public int getVerticalScrollbarWidth() {
        android.widget.ScrollBarDrawable scrollBarDrawable;
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || (scrollBarDrawable = scrollabilityCache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBarDrawable.getSize(true);
        if (size <= 0) {
            return scrollabilityCache.scrollBarSize;
        }
        return size;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getHorizontalScrollbarHeight() {
        android.widget.ScrollBarDrawable scrollBarDrawable;
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || (scrollBarDrawable = scrollabilityCache.scrollBar) == null) {
            return 0;
        }
        int size = scrollBarDrawable.getSize(false);
        if (size <= 0) {
            return scrollabilityCache.scrollBarSize;
        }
        return size;
    }

    protected void initializeScrollbars(android.content.res.TypedArray typedArray) {
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.View);
        initializeScrollbarsInternal(obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    private void initializeScrollBarDrawable() {
        initScrollCache();
        if (this.mScrollCache.scrollBar == null) {
            this.mScrollCache.scrollBar = new android.widget.ScrollBarDrawable();
            this.mScrollCache.scrollBar.setState(getDrawableState());
            this.mScrollCache.scrollBar.setCallback(this);
        }
    }

    protected void initializeScrollbarsInternal(android.content.res.TypedArray typedArray) {
        initScrollCache();
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new android.widget.ScrollBarDrawable();
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        boolean z = typedArray.getBoolean(47, true);
        if (!z) {
            scrollabilityCache.state = 1;
        }
        scrollabilityCache.fadeScrollBars = z;
        scrollabilityCache.scrollBarFadeDuration = typedArray.getInt(45, android.view.ViewConfiguration.getScrollBarFadeDuration());
        scrollabilityCache.scrollBarDefaultDelayBeforeFade = typedArray.getInt(46, android.view.ViewConfiguration.getScrollDefaultDelay());
        scrollabilityCache.scrollBarSize = typedArray.getDimensionPixelSize(1, android.view.ViewConfiguration.get(this.mContext).getScaledScrollBarSize());
        scrollabilityCache.scrollBar.setHorizontalTrackDrawable(typedArray.getDrawable(4));
        android.graphics.drawable.Drawable drawable = typedArray.getDrawable(2);
        if (drawable != null) {
            scrollabilityCache.scrollBar.setHorizontalThumbDrawable(drawable);
        }
        if (typedArray.getBoolean(6, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        android.graphics.drawable.Drawable drawable2 = typedArray.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(drawable2);
        android.graphics.drawable.Drawable drawable3 = typedArray.getDrawable(3);
        if (drawable3 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(drawable3);
        }
        if (typedArray.getBoolean(7, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawVerticalTrack(true);
        }
        int layoutDirection = getLayoutDirection();
        if (drawable2 != null) {
            drawable2.setLayoutDirection(layoutDirection);
        }
        if (drawable3 != null) {
            drawable3.setLayoutDirection(layoutDirection);
        }
        resolvePadding();
    }

    public void setVerticalScrollbarThumbDrawable(android.graphics.drawable.Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalThumbDrawable(drawable);
    }

    public void setVerticalScrollbarTrackDrawable(android.graphics.drawable.Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalTrackDrawable(drawable);
    }

    public void setHorizontalScrollbarThumbDrawable(android.graphics.drawable.Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalThumbDrawable(drawable);
    }

    public void setHorizontalScrollbarTrackDrawable(android.graphics.drawable.Drawable drawable) {
        initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalTrackDrawable(drawable);
    }

    public android.graphics.drawable.Drawable getVerticalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalThumbDrawable();
        }
        return null;
    }

    public android.graphics.drawable.Drawable getVerticalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getVerticalTrackDrawable();
        }
        return null;
    }

    public android.graphics.drawable.Drawable getHorizontalScrollbarThumbDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalThumbDrawable();
        }
        return null;
    }

    public android.graphics.drawable.Drawable getHorizontalScrollbarTrackDrawable() {
        if (this.mScrollCache != null) {
            return this.mScrollCache.scrollBar.getHorizontalTrackDrawable();
        }
        return null;
    }

    private void initializeScrollIndicatorsInternal() {
        if (this.mScrollIndicatorDrawable == null) {
            this.mScrollIndicatorDrawable = this.mContext.getDrawable(com.android.internal.R.drawable.scroll_indicator_material);
        }
    }

    private void initScrollCache() {
        if (this.mScrollCache == null) {
            this.mScrollCache = new android.view.View.ScrollabilityCache(android.view.ViewConfiguration.get(this.mContext), this);
        }
    }

    private android.view.View.ScrollabilityCache getScrollCache() {
        initScrollCache();
        return this.mScrollCache;
    }

    public void setVerticalScrollbarPosition(int i) {
        if (this.mVerticalScrollbarPosition != i) {
            this.mVerticalScrollbarPosition = i;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public int getVerticalScrollbarPosition() {
        return this.mVerticalScrollbarPosition;
    }

    boolean isOnScrollbar(float f, float f2) {
        if (this.mScrollCache == null) {
            return false;
        }
        float scrollX = f + getScrollX();
        float scrollY = f2 + getScrollY();
        boolean z = computeVerticalScrollRange() > computeVerticalScrollExtent();
        if (isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && z) {
            android.graphics.Rect rect = this.mScrollCache.mScrollBarTouchBounds;
            getVerticalScrollBarBounds(null, rect);
            if (rect.contains((int) scrollX, (int) scrollY)) {
                return true;
            }
        }
        boolean z2 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        if (isHorizontalScrollBarEnabled() && z2) {
            android.graphics.Rect rect2 = this.mScrollCache.mScrollBarTouchBounds;
            getHorizontalScrollBarBounds(null, rect2);
            if (rect2.contains((int) scrollX, (int) scrollY)) {
                return true;
            }
        }
        return false;
    }

    boolean isOnScrollbarThumb(float f, float f2) {
        return isOnVerticalScrollbarThumb(f, f2) || isOnHorizontalScrollbarThumb(f, f2);
    }

    private boolean isOnVerticalScrollbarThumb(float f, float f2) {
        int computeVerticalScrollRange;
        int computeVerticalScrollExtent;
        if (this.mScrollCache != null && isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden() && (computeVerticalScrollRange = computeVerticalScrollRange()) > (computeVerticalScrollExtent = computeVerticalScrollExtent())) {
            float scrollX = f + getScrollX();
            float scrollY = f2 + getScrollY();
            android.graphics.Rect rect = this.mScrollCache.mScrollBarBounds;
            getVerticalScrollBarBounds(rect, this.mScrollCache.mScrollBarTouchBounds);
            int computeVerticalScrollOffset = computeVerticalScrollOffset();
            int thumbLength = com.android.internal.widget.ScrollBarUtils.getThumbLength(rect.height(), rect.width(), computeVerticalScrollExtent, computeVerticalScrollRange);
            int thumbOffset = rect.top + com.android.internal.widget.ScrollBarUtils.getThumbOffset(rect.height(), thumbLength, computeVerticalScrollExtent, computeVerticalScrollRange, computeVerticalScrollOffset);
            int max = java.lang.Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (scrollX >= r4.left && scrollX <= r4.right && scrollY >= thumbOffset - max && scrollY <= thumbOffset + thumbLength + max) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnHorizontalScrollbarThumb(float f, float f2) {
        int computeHorizontalScrollRange;
        int computeHorizontalScrollExtent;
        if (this.mScrollCache != null && isHorizontalScrollBarEnabled() && (computeHorizontalScrollRange = computeHorizontalScrollRange()) > (computeHorizontalScrollExtent = computeHorizontalScrollExtent())) {
            float scrollX = f + getScrollX();
            float scrollY = f2 + getScrollY();
            android.graphics.Rect rect = this.mScrollCache.mScrollBarBounds;
            getHorizontalScrollBarBounds(rect, this.mScrollCache.mScrollBarTouchBounds);
            int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
            int thumbLength = com.android.internal.widget.ScrollBarUtils.getThumbLength(rect.width(), rect.height(), computeHorizontalScrollExtent, computeHorizontalScrollRange);
            int thumbOffset = rect.left + com.android.internal.widget.ScrollBarUtils.getThumbOffset(rect.width(), thumbLength, computeHorizontalScrollExtent, computeHorizontalScrollRange, computeHorizontalScrollOffset);
            int max = java.lang.Math.max(this.mScrollCache.scrollBarMinTouchTarget - thumbLength, 0) / 2;
            if (scrollX >= thumbOffset - max && scrollX <= thumbOffset + thumbLength + max && scrollY >= r4.top && scrollY <= r4.bottom) {
                return true;
            }
        }
        return false;
    }

    boolean isDraggingScrollBar() {
        return (this.mScrollCache == null || this.mScrollCache.mScrollBarDraggingState == 0) ? false : true;
    }

    @android.view.RemotableViewMethod
    public void setScrollIndicators(int i) {
        setScrollIndicators(i, 63);
    }

    public void setScrollIndicators(int i, int i2) {
        int i3 = (i2 << 8) & SCROLL_INDICATORS_PFLAG3_MASK;
        int i4 = (i << 8) & i3;
        int i5 = ((~i3) & this.mPrivateFlags3) | i4;
        if (this.mPrivateFlags3 != i5) {
            this.mPrivateFlags3 = i5;
            if (i4 != 0) {
                initializeScrollIndicatorsInternal();
            }
            invalidate();
        }
    }

    public int getScrollIndicators() {
        return (this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) >>> 8;
    }

    android.view.View.ListenerInfo getListenerInfo() {
        if (this.mListenerInfo != null) {
            return this.mListenerInfo;
        }
        this.mListenerInfo = new android.view.View.ListenerInfo();
        return this.mListenerInfo;
    }

    public void setOnScrollChangeListener(android.view.View.OnScrollChangeListener onScrollChangeListener) {
        getListenerInfo().mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setOnFocusChangeListener(android.view.View.OnFocusChangeListener onFocusChangeListener) {
        getListenerInfo().mOnFocusChangeListener = onFocusChangeListener;
    }

    public void addOnLayoutChangeListener(android.view.View.OnLayoutChangeListener onLayoutChangeListener) {
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mOnLayoutChangeListeners == null) {
            listenerInfo.mOnLayoutChangeListeners = new java.util.ArrayList();
        }
        if (!listenerInfo.mOnLayoutChangeListeners.contains(onLayoutChangeListener)) {
            listenerInfo.mOnLayoutChangeListeners.add(onLayoutChangeListener);
        }
    }

    public void removeOnLayoutChangeListener(android.view.View.OnLayoutChangeListener onLayoutChangeListener) {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnLayoutChangeListeners == null) {
            return;
        }
        listenerInfo.mOnLayoutChangeListeners.remove(onLayoutChangeListener);
    }

    public void addOnAttachStateChangeListener(android.view.View.OnAttachStateChangeListener onAttachStateChangeListener) {
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mOnAttachStateChangeListeners == null) {
            listenerInfo.mOnAttachStateChangeListeners = new java.util.concurrent.CopyOnWriteArrayList();
        }
        listenerInfo.mOnAttachStateChangeListeners.add(onAttachStateChangeListener);
    }

    public void removeOnAttachStateChangeListener(android.view.View.OnAttachStateChangeListener onAttachStateChangeListener) {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo == null || listenerInfo.mOnAttachStateChangeListeners == null) {
            return;
        }
        listenerInfo.mOnAttachStateChangeListeners.remove(onAttachStateChangeListener);
    }

    public android.view.View.OnFocusChangeListener getOnFocusChangeListener() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo.mOnFocusChangeListener;
        }
        return null;
    }

    public void setOnClickListener(android.view.View.OnClickListener onClickListener) {
        if (!isClickable()) {
            setClickable(true);
        }
        getListenerInfo().mOnClickListener = onClickListener;
    }

    public boolean hasOnClickListeners() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mOnClickListener == null) ? false : true;
    }

    public void setOnLongClickListener(android.view.View.OnLongClickListener onLongClickListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnLongClickListener = onLongClickListener;
    }

    public boolean hasOnLongClickListeners() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        return (listenerInfo == null || listenerInfo.mOnLongClickListener == null) ? false : true;
    }

    public android.view.View.OnLongClickListener getOnLongClickListener() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo.mOnLongClickListener;
        }
        return null;
    }

    public void setOnContextClickListener(android.view.View.OnContextClickListener onContextClickListener) {
        if (!isContextClickable()) {
            setContextClickable(true);
        }
        getListenerInfo().mOnContextClickListener = onContextClickListener;
    }

    public void setOnCreateContextMenuListener(android.view.View.OnCreateContextMenuListener onCreateContextMenuListener) {
        if (!isLongClickable()) {
            setLongClickable(true);
        }
        getListenerInfo().mOnCreateContextMenuListener = onCreateContextMenuListener;
    }

    public void addFrameMetricsListener(android.view.Window window, android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, android.os.Handler handler) {
        if (this.mAttachInfo != null) {
            if (this.mAttachInfo.mThreadedRenderer != null) {
                if (this.mFrameMetricsObservers == null) {
                    this.mFrameMetricsObservers = new java.util.ArrayList<>();
                }
                android.view.FrameMetricsObserver frameMetricsObserver = new android.view.FrameMetricsObserver(window, handler, onFrameMetricsAvailableListener);
                this.mFrameMetricsObservers.add(frameMetricsObserver);
                this.mAttachInfo.mThreadedRenderer.addObserver(frameMetricsObserver.getRendererObserver());
                return;
            }
            android.util.Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            return;
        }
        if (this.mFrameMetricsObservers == null) {
            this.mFrameMetricsObservers = new java.util.ArrayList<>();
        }
        this.mFrameMetricsObservers.add(new android.view.FrameMetricsObserver(window, handler, onFrameMetricsAvailableListener));
    }

    public void removeFrameMetricsListener(android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        android.view.ThreadedRenderer threadedRenderer = getThreadedRenderer();
        android.view.FrameMetricsObserver findFrameMetricsObserver = findFrameMetricsObserver(onFrameMetricsAvailableListener);
        if (findFrameMetricsObserver == null) {
            throw new java.lang.IllegalArgumentException("attempt to remove OnFrameMetricsAvailableListener that was never added");
        }
        if (this.mFrameMetricsObservers != null) {
            this.mFrameMetricsObservers.remove(findFrameMetricsObserver);
            if (threadedRenderer != null) {
                threadedRenderer.removeObserver(findFrameMetricsObserver.getRendererObserver());
            }
        }
    }

    private void registerPendingFrameMetricsObservers() {
        if (this.mFrameMetricsObservers != null) {
            android.view.ThreadedRenderer threadedRenderer = getThreadedRenderer();
            if (threadedRenderer != null) {
                java.util.Iterator<android.view.FrameMetricsObserver> it = this.mFrameMetricsObservers.iterator();
                while (it.hasNext()) {
                    threadedRenderer.addObserver(it.next().getRendererObserver());
                }
                return;
            }
            android.util.Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
        }
    }

    private android.view.FrameMetricsObserver findFrameMetricsObserver(android.view.Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (this.mFrameMetricsObservers != null) {
            for (int i = 0; i < this.mFrameMetricsObservers.size(); i++) {
                android.view.FrameMetricsObserver frameMetricsObserver = this.mFrameMetricsObservers.get(i);
                if (frameMetricsObserver.mListener == onFrameMetricsAvailableListener) {
                    return frameMetricsObserver;
                }
            }
            return null;
        }
        return null;
    }

    public void setNotifyAutofillManagerOnClick(boolean z) {
        if (z) {
            this.mPrivateFlags |= 536870912;
        } else {
            this.mPrivateFlags &= -536870913;
        }
    }

    private void notifyAutofillManagerOnClick() {
        if ((this.mPrivateFlags & 536870912) != 0) {
            try {
                getAutofillManager().notifyViewClicked(this);
            } finally {
                this.mPrivateFlags = (-536870913) & this.mPrivateFlags;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performClickInternal() {
        notifyAutofillManagerOnClick();
        return performClick();
    }

    public boolean performClick() {
        notifyAutofillManagerOnClick();
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        boolean z = false;
        if (listenerInfo != null && listenerInfo.mOnClickListener != null) {
            playSoundEffect(0);
            listenerInfo.mOnClickListener.onClick(this);
            z = true;
        }
        sendAccessibilityEvent(1);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        return z;
    }

    public boolean callOnClick() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnClickListener != null) {
            listenerInfo.mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }

    public boolean performLongClick() {
        return performLongClickInternal(this.mLongClickX, this.mLongClickY);
    }

    public boolean performLongClick(float f, float f2) {
        this.mLongClickX = f;
        this.mLongClickY = f2;
        boolean performLongClick = performLongClick();
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        return performLongClick;
    }

    private boolean performLongClickInternal(float f, float f2) {
        boolean z;
        boolean z2;
        sendAccessibilityEvent(2);
        android.view.View.OnLongClickListener onLongClickListener = this.mListenerInfo == null ? null : this.mListenerInfo.mOnLongClickListener;
        if (onLongClickListener == null) {
            z = true;
            z2 = false;
        } else {
            z2 = onLongClickListener.onLongClick(this);
            if (!z2) {
                z = true;
            } else {
                z = onLongClickListener.onLongClickUseDefaultHapticFeedback(this);
            }
        }
        if (!z2) {
            z2 = (java.lang.Float.isNaN(f) || java.lang.Float.isNaN(f2)) ? false : true ? showContextMenu(f, f2) : showContextMenu();
        }
        if ((this.mViewFlags & 1073741824) == 1073741824 && !z2) {
            z2 = showLongClickTooltip((int) f, (int) f2);
        }
        if (z2 && z) {
            performHapticFeedback(0);
        }
        return z2;
    }

    public boolean performContextClick(float f, float f2) {
        return performContextClick();
    }

    public boolean performContextClick() {
        boolean z;
        sendAccessibilityEvent(8388608);
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnContextClickListener != null) {
            z = listenerInfo.mOnContextClickListener.onContextClick(this);
        } else {
            z = false;
        }
        if (z) {
            performHapticFeedback(6);
        }
        return z;
    }

    protected boolean performButtonActionOnTouchDown(android.view.MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194) && (motionEvent.getButtonState() & 2) != 0) {
            showContextMenu(motionEvent.getX(), motionEvent.getY());
            this.mPrivateFlags |= 67108864;
            return true;
        }
        return false;
    }

    public boolean showContextMenu() {
        return getParent().showContextMenuForChild(this);
    }

    public boolean showContextMenu(float f, float f2) {
        return getParent().showContextMenuForChild(this, f, f2);
    }

    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback) {
        return startActionMode(callback, 0);
    }

    public android.view.ActionMode startActionMode(android.view.ActionMode.Callback callback, int i) {
        android.view.ViewParent parent = getParent();
        if (parent == null) {
            return null;
        }
        try {
            return parent.startActionModeForChild(this, callback, i);
        } catch (java.lang.AbstractMethodError e) {
            return parent.startActionModeForChild(this, callback);
        }
    }

    public void startActivityForResult(android.content.Intent intent, int i) {
        this.mStartActivityRequestWho = "@android:view:" + java.lang.System.identityHashCode(this);
        getContext().startActivityForResult(this.mStartActivityRequestWho, intent, i, null);
    }

    public boolean dispatchActivityResult(java.lang.String str, int i, int i2, android.content.Intent intent) {
        if (this.mStartActivityRequestWho != null && this.mStartActivityRequestWho.equals(str)) {
            onActivityResult(i, i2, intent);
            this.mStartActivityRequestWho = null;
            return true;
        }
        return false;
    }

    public void onActivityResult(int i, int i2, android.content.Intent intent) {
    }

    public void setOnKeyListener(android.view.View.OnKeyListener onKeyListener) {
        getListenerInfo().mOnKeyListener = onKeyListener;
    }

    public void setOnTouchListener(android.view.View.OnTouchListener onTouchListener) {
        getListenerInfo().mOnTouchListener = onTouchListener;
    }

    public void setOnGenericMotionListener(android.view.View.OnGenericMotionListener onGenericMotionListener) {
        getListenerInfo().mOnGenericMotionListener = onGenericMotionListener;
    }

    public void setOnHoverListener(android.view.View.OnHoverListener onHoverListener) {
        getListenerInfo().mOnHoverListener = onHoverListener;
    }

    public void setOnDragListener(android.view.View.OnDragListener onDragListener) {
        getListenerInfo().mOnDragListener = onDragListener;
    }

    void handleFocusGainInternal(int i, android.graphics.Rect rect) {
        if ((this.mPrivateFlags & 2) == 0) {
            this.mPrivateFlags |= 2;
            android.view.View findFocus = this.mAttachInfo != null ? getRootView().findFocus() : null;
            if (this.mParent != null) {
                this.mParent.requestChildFocus(this, this);
                updateFocusedInCluster(findFocus, i);
            }
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(findFocus, this);
            }
            onFocusChanged(true, i, rect);
            refreshDrawableState();
        }
    }

    public final void setRevealOnFocusHint(boolean z) {
        if (z) {
            this.mPrivateFlags3 &= -67108865;
        } else {
            this.mPrivateFlags3 |= 67108864;
        }
    }

    public final boolean getRevealOnFocusHint() {
        return (this.mPrivateFlags3 & 67108864) == 0;
    }

    public void getHotspotBounds(android.graphics.Rect rect) {
        android.graphics.drawable.Drawable background = getBackground();
        if (background != null) {
            background.getHotspotBounds(rect);
        } else {
            getBoundsOnScreen(rect);
        }
    }

    public boolean requestRectangleOnScreen(android.graphics.Rect rect) {
        return requestRectangleOnScreen(rect, false);
    }

    public boolean requestRectangleOnScreen(android.graphics.Rect rect, boolean z) {
        if (this.mParent == null) {
            return false;
        }
        android.graphics.RectF rectF = this.mAttachInfo != null ? this.mAttachInfo.mTmpTransformRect : new android.graphics.RectF();
        rectF.set(rect);
        android.view.ViewParent viewParent = this.mParent;
        boolean z2 = false;
        android.view.View view = this;
        while (viewParent != null) {
            rect.set((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            z2 |= viewParent.requestChildRectangleOnScreen(view, rect, z);
            if (!(viewParent instanceof android.view.View)) {
                break;
            }
            rectF.offset(view.mLeft - view.getScrollX(), view.mTop - view.getScrollY());
            view = viewParent;
            viewParent = view.getParent();
        }
        return z2;
    }

    public void clearFocus() {
        clearFocusInternal(null, true, sAlwaysAssignFocus || !isInTouchMode());
    }

    public void clearFocusInternal(android.view.View view, boolean z, boolean z2) {
        if ((this.mPrivateFlags & 2) != 0) {
            this.mPrivateFlags &= -3;
            clearParentsWantFocus();
            if (z && this.mParent != null) {
                this.mParent.clearChildFocus(this);
            }
            onFocusChanged(false, 0, null);
            refreshDrawableState();
            if (z) {
                if (!z2 || !rootViewRequestFocus()) {
                    notifyGlobalFocusCleared(this);
                }
            }
        }
    }

    void notifyGlobalFocusCleared(android.view.View view) {
        if (view != null && this.mAttachInfo != null) {
            this.mAttachInfo.mTreeObserver.dispatchOnGlobalFocusChange(view, null);
        }
    }

    boolean rootViewRequestFocus() {
        android.view.View rootView = getRootView();
        return rootView != null && rootView.requestFocus();
    }

    void unFocus(android.view.View view) {
        clearFocusInternal(view, false, false);
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public boolean hasFocus() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public boolean hasFocusable() {
        return hasFocusable(!sHasFocusableExcludeAutoFocusable, false);
    }

    public boolean hasExplicitFocusable() {
        return hasFocusable(false, true);
    }

    boolean hasFocusable(boolean z, boolean z2) {
        if (!isFocusableInTouchMode()) {
            for (android.view.ViewParent viewParent = this.mParent; viewParent instanceof android.view.ViewGroup; viewParent = viewParent.getParent()) {
                if (((android.view.ViewGroup) viewParent).shouldBlockFocusForTouchscreen()) {
                    return false;
                }
            }
        }
        if ((this.mViewFlags & 12) == 0 && (this.mViewFlags & 32) == 0) {
            return (z || getFocusable() != 16) && isFocusable();
        }
        return false;
    }

    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        android.view.ViewRootImpl viewRootImpl;
        if (z) {
            sendAccessibilityEvent(8);
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        switchDefaultFocusHighlight();
        if (!z) {
            if (isPressed()) {
                setPressed(false);
            }
            if (hasWindowFocus()) {
                notifyFocusChangeToImeFocusController(false);
            }
            onFocusLost();
        } else if (hasWindowFocus()) {
            notifyFocusChangeToImeFocusController(true);
            if (this.mIsHandwritingDelegate && (viewRootImpl = getViewRootImpl()) != null) {
                viewRootImpl.getHandwritingInitiator().onDelegateViewFocused(this);
            }
        }
        invalidate(true);
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnFocusChangeListener != null) {
            listenerInfo.mOnFocusChangeListener.onFocusChange(this, z);
        }
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mKeyDispatchState.reset(this);
        }
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
        notifyEnterOrExitForAutoFillIfNeeded(z);
        updatePreferKeepClearForFocus();
    }

    private void notifyFocusChangeToImeFocusController(boolean z) {
        if (this.mAttachInfo == null) {
            return;
        }
        this.mAttachInfo.mViewRootImpl.getImeFocusController().onViewFocusChanged(this, z);
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean z) {
        android.view.autofill.AutofillManager autofillManager;
        if (canNotifyAutofillEnterExitEvent() && (autofillManager = getAutofillManager()) != null) {
            if (z) {
                if (!isLaidOut() || !isVisibleToUser()) {
                    this.mPrivateFlags3 |= 134217728;
                    return;
                } else {
                    if (isVisibleToUser()) {
                        if (isFocused()) {
                            autofillManager.notifyViewEntered(this);
                            return;
                        } else {
                            autofillManager.notifyViewEnteredForFillDialog(this);
                            return;
                        }
                    }
                    return;
                }
            }
            if (!isFocused()) {
                autofillManager.notifyViewExited(this);
            }
        }
    }

    public void setAccessibilityPaneTitle(java.lang.CharSequence charSequence) {
        if (!android.text.TextUtils.equals(charSequence, this.mAccessibilityPaneTitle)) {
            boolean z = this.mAccessibilityPaneTitle == null;
            boolean z2 = charSequence == null;
            this.mAccessibilityPaneTitle = charSequence;
            if (this.mAccessibilityPaneTitle != null && getImportantForAccessibility() == 0) {
                setImportantForAccessibility(1);
            }
            if (z) {
                notifyViewAccessibilityStateChangedIfNeeded(16);
            } else if (z2) {
                notifyViewAccessibilityStateChangedIfNeeded(32);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(8);
            }
        }
    }

    public java.lang.CharSequence getAccessibilityPaneTitle() {
        return this.mAccessibilityPaneTitle;
    }

    private boolean isAccessibilityPane() {
        return this.mAccessibilityPaneTitle != null;
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEvent(int i) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEvent(this, i);
        } else {
            sendAccessibilityEventInternal(i);
        }
    }

    public void announceForAccessibility(java.lang.CharSequence charSequence) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mParent != null) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(16384);
            onInitializeAccessibilityEvent(obtain);
            obtain.getText().add(charSequence);
            obtain.setContentDescription(null);
            this.mParent.requestSendAccessibilityEvent(this, obtain);
        }
    }

    public void sendAccessibilityEventInternal(int i) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            sendAccessibilityEventUnchecked(android.view.accessibility.AccessibilityEvent.obtain(i));
        }
    }

    @Override // android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, accessibilityEvent);
        } else {
            sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(final android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        boolean z = (accessibilityEvent.getEventType() == 32) && (accessibilityEvent.getContentChangeTypes() & 32) != 0;
        boolean detached = detached();
        if (!isShown() && !z && !detached) {
            return;
        }
        onInitializeAccessibilityEvent(accessibilityEvent);
        if ((accessibilityEvent.getEventType() & POPULATING_ACCESSIBILITY_EVENT_TYPES) != 0) {
            dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        android.view.View.SendAccessibilityEventThrottle throttleForAccessibilityEvent = getThrottleForAccessibilityEvent(accessibilityEvent);
        if (throttleForAccessibilityEvent != null) {
            throttleForAccessibilityEvent.post(accessibilityEvent);
        } else if (!z && detached) {
            postDelayed(new java.lang.Runnable() { // from class: android.view.View$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.View.this.lambda$sendAccessibilityEventUncheckedInternal$0(accessibilityEvent);
                }
            }, android.view.ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
        } else {
            requestParentSendAccessibilityEvent(accessibilityEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendAccessibilityEventUncheckedInternal$0(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && isShown()) {
            requestParentSendAccessibilityEvent(accessibilityEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestParentSendAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (getParent() != null) {
            getParent().requestSendAccessibilityEvent(this, accessibilityEvent);
        }
    }

    private android.view.View.SendAccessibilityEventThrottle getThrottleForAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        byte b = 0;
        if (accessibilityEvent.getEventType() == 4096) {
            if (this.mSendViewScrolledAccessibilityEvent == null) {
                this.mSendViewScrolledAccessibilityEvent = new android.view.View.SendViewScrolledAccessibilityEvent();
            }
            return this.mSendViewScrolledAccessibilityEvent;
        }
        boolean z = (accessibilityEvent.getContentChangeTypes() & 64) != 0;
        if (accessibilityEvent.getEventType() != 2048 || !z) {
            return null;
        }
        if (this.mSendStateChangedAccessibilityEvent == null) {
            this.mSendStateChangedAccessibilityEvent = new android.view.View.SendAccessibilityEventThrottle();
        }
        return this.mSendStateChangedAccessibilityEvent;
    }

    private void clearAccessibilityThrottles() {
        cancel(this.mSendViewScrolledAccessibilityEvent);
        cancel(this.mSendStateChangedAccessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
        }
        return dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return false;
    }

    public void onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onPopulateAccessibilityEvent(this, accessibilityEvent);
        } else {
            onPopulateAccessibilityEventInternal(accessibilityEvent);
        }
    }

    public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32 && isAccessibilityPane()) {
            accessibilityEvent.getText().add(getAccessibilityPaneTitle());
        }
    }

    public void onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityEvent(this, accessibilityEvent);
        } else {
            onInitializeAccessibilityEventInternal(accessibilityEvent);
        }
    }

    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(getAccessibilityClassName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        accessibilityEvent.setEnabled(isEnabled());
        accessibilityEvent.setContentDescription(this.mContentDescription);
        accessibilityEvent.setScrollX(getScrollX());
        accessibilityEvent.setScrollY(getScrollY());
        switch (accessibilityEvent.getEventType()) {
            case 8:
                java.util.ArrayList<android.view.View> arrayList = this.mAttachInfo != null ? this.mAttachInfo.mTempArrayList : new java.util.ArrayList<>();
                getRootView().addFocusables(arrayList, 2, 0);
                accessibilityEvent.setItemCount(arrayList.size());
                accessibilityEvent.setCurrentItemIndex(arrayList.indexOf(this));
                if (this.mAttachInfo != null) {
                    arrayList.clear();
                    break;
                }
                break;
            case 8192:
                java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
                if (iterableTextForAccessibility != null && iterableTextForAccessibility.length() > 0) {
                    accessibilityEvent.setFromIndex(getAccessibilitySelectionStart());
                    accessibilityEvent.setToIndex(getAccessibilitySelectionEnd());
                    accessibilityEvent.setItemCount(iterableTextForAccessibility.length());
                    break;
                }
                break;
        }
    }

    public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.createAccessibilityNodeInfo(this);
        }
        return createAccessibilityNodeInfoInternal();
    }

    public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfoInternal() {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            return accessibilityNodeProvider.createAccessibilityNodeInfo(-1);
        }
        android.view.accessibility.AccessibilityNodeInfo obtain = android.view.accessibility.AccessibilityNodeInfo.obtain(this);
        onInitializeAccessibilityNodeInfo(obtain);
        return obtain;
    }

    public void onInitializeAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        if (this.mAccessibilityDelegate != null) {
            this.mAccessibilityDelegate.onInitializeAccessibilityNodeInfo(this, accessibilityNodeInfo);
        } else {
            onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }
    }

    public void getBoundsOnScreen(android.graphics.Rect rect) {
        getBoundsOnScreen(rect, false);
    }

    public void getBoundsOnScreen(android.graphics.Rect rect, boolean z) {
        if (this.mAttachInfo == null) {
            return;
        }
        android.graphics.RectF rectF = this.mAttachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(rectF, z);
        rect.set(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom));
        this.mAttachInfo.mViewRootImpl.applyViewBoundsSandboxingIfNeeded(rect);
    }

    public void getBoundsOnScreen(android.graphics.RectF rectF, boolean z) {
        if (this.mAttachInfo == null) {
            return;
        }
        android.graphics.RectF rectF2 = this.mAttachInfo.mTmpTransformRect;
        getBoundsToScreenInternal(rectF2, z);
        rectF.set(rectF2.left, rectF2.top, rectF2.right, rectF2.bottom);
    }

    public void getBoundsInWindow(android.graphics.Rect rect, boolean z) {
        if (this.mAttachInfo == null) {
            return;
        }
        android.graphics.RectF rectF = this.mAttachInfo.mTmpTransformRect;
        getBoundsToWindowInternal(rectF, z);
        rect.set(java.lang.Math.round(rectF.left), java.lang.Math.round(rectF.top), java.lang.Math.round(rectF.right), java.lang.Math.round(rectF.bottom));
    }

    private void getBoundsToScreenInternal(android.graphics.RectF rectF, boolean z) {
        rectF.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToScreenCoords(rectF, z);
    }

    private void getBoundsToWindowInternal(android.graphics.RectF rectF, boolean z) {
        rectF.set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        mapRectFromViewToWindowCoords(rectF, z);
    }

    public void mapRectFromViewToScreenCoords(android.graphics.RectF rectF, boolean z) {
        mapRectFromViewToWindowCoords(rectF, z);
        rectF.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    public void mapRectFromViewToWindowCoords(android.graphics.RectF rectF, boolean z) {
        if (!hasIdentityMatrix()) {
            getMatrix().mapRect(rectF);
        }
        rectF.offset(this.mLeft, this.mTop);
        java.lang.Object obj = this.mParent;
        while (obj instanceof android.view.View) {
            android.view.View view = (android.view.View) obj;
            rectF.offset(-view.mScrollX, -view.mScrollY);
            if (z) {
                rectF.left = java.lang.Math.max(rectF.left, 0.0f);
                rectF.top = java.lang.Math.max(rectF.top, 0.0f);
                rectF.right = java.lang.Math.min(rectF.right, view.getWidth());
                rectF.bottom = java.lang.Math.min(rectF.bottom, view.getHeight());
            }
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapRect(rectF);
            }
            rectF.offset(view.mLeft, view.mTop);
            obj = view.mParent;
        }
        if (obj instanceof android.view.ViewRootImpl) {
            rectF.offset(0.0f, -((android.view.ViewRootImpl) obj).mCurScrollY);
        }
    }

    public java.lang.CharSequence getAccessibilityClassName() {
        return android.view.View.class.getName();
    }

    public void onProvideStructure(android.view.ViewStructure viewStructure) {
        onProvideStructure(viewStructure, 0, 0);
    }

    public void onProvideAutofillStructure(android.view.ViewStructure viewStructure, int i) {
        onProvideStructure(viewStructure, 1, i);
    }

    public void onProvideContentCaptureStructure(android.view.ViewStructure viewStructure, int i) {
        onProvideStructure(viewStructure, 2, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected void onProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        int i3;
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        int i4 = this.mID;
        android.view.View view = null;
        if (i4 != -1 && !isViewIdGenerated(i4)) {
            try {
                android.content.res.Resources resources = getResources();
                str2 = resources.getResourceEntryName(i4);
                str3 = resources.getResourceTypeName(i4);
                str = resources.getResourcePackageName(i4);
            } catch (android.content.res.Resources.NotFoundException e) {
                str = null;
                str2 = null;
                str3 = null;
            }
            viewStructure.setId(i4, str, str3, str2);
        } else {
            viewStructure.setId(i4, null, null, null);
        }
        if (i == 1 || i == 2) {
            int autofillType = getAutofillType();
            if (autofillType != 0) {
                viewStructure.setAutofillType(autofillType);
                viewStructure.setAutofillHints(getAutofillHints());
                viewStructure.setAutofillValue(getAutofillValue());
                viewStructure.setIsCredential(isCredential());
            }
            if (getViewCredentialHandler() != null) {
                viewStructure.setCredentialManagerRequest(getViewCredentialHandler().getRequest(), getViewCredentialHandler().getCallback());
            }
            viewStructure.setImportantForAutofill(getImportantForAutofill());
            viewStructure.setReceiveContentMimeTypes(getReceiveContentMimeTypes());
        }
        int i5 = 0;
        if (i == 1 && (i2 & 1) == 0) {
            java.lang.Object parent = getParent();
            if (!(parent instanceof android.view.View)) {
                i3 = 0;
            } else {
                view = (android.view.View) parent;
                i3 = 0;
            }
            while (view != null && !view.isImportantForAutofill()) {
                i5 += view.mLeft - view.mScrollX;
                i3 += view.mTop - view.mScrollY;
                java.lang.Object parent2 = view.getParent();
                if (!(parent2 instanceof android.view.View)) {
                    break;
                } else {
                    view = (android.view.View) parent2;
                }
            }
        } else {
            i3 = 0;
        }
        viewStructure.setDimens(i5 + this.mLeft, i3 + this.mTop, this.mScrollX, this.mScrollY, this.mRight - this.mLeft, this.mBottom - this.mTop);
        if (i == 0) {
            if (!hasIdentityMatrix()) {
                viewStructure.setTransformation(getMatrix());
            }
            viewStructure.setElevation(getZ());
        }
        viewStructure.setVisibility(getVisibility());
        viewStructure.setEnabled(isEnabled());
        if (isClickable()) {
            viewStructure.setClickable(true);
        }
        if (isFocusable()) {
            viewStructure.setFocusable(true);
        }
        if (isFocused()) {
            viewStructure.setFocused(true);
        }
        if (isAccessibilityFocused()) {
            viewStructure.setAccessibilityFocused(true);
        }
        if (isSelected()) {
            viewStructure.setSelected(true);
        }
        if (isActivated()) {
            viewStructure.setActivated(true);
        }
        if (isLongClickable()) {
            viewStructure.setLongClickable(true);
        }
        if (this instanceof android.widget.Checkable) {
            viewStructure.setCheckable(true);
            if (((android.widget.Checkable) this).isChecked()) {
                viewStructure.setChecked(true);
            }
        }
        if (isOpaque()) {
            viewStructure.setOpaque(true);
        }
        if (isContextClickable()) {
            viewStructure.setContextClickable(true);
        }
        viewStructure.setClassName(getAccessibilityClassName().toString());
        viewStructure.setContentDescription(getContentDescription());
    }

    public void onProvideVirtualStructure(android.view.ViewStructure viewStructure) {
        onProvideVirtualStructureCompat(viewStructure, false);
    }

    private void onProvideVirtualStructureCompat(android.view.ViewStructure viewStructure, boolean z) {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            if (z && android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                android.util.Log.v(AUTOFILL_LOG_TAG, "onProvideVirtualStructureCompat() for " + this);
            }
            android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = createAccessibilityNodeInfo();
            viewStructure.setChildCount(1);
            android.view.ViewStructure newChild = viewStructure.newChild(0);
            if (createAccessibilityNodeInfo == null) {
                android.util.Log.w(AUTOFILL_LOG_TAG, "AccessibilityNodeInfo is null.");
            } else {
                populateVirtualStructure(newChild, accessibilityNodeProvider, createAccessibilityNodeInfo, z);
                createAccessibilityNodeInfo.recycle();
            }
        }
    }

    public void onProvideAutofillVirtualStructure(android.view.ViewStructure viewStructure, int i) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            onProvideVirtualStructureCompat(viewStructure, true);
        }
    }

    public void setOnReceiveContentListener(java.lang.String[] strArr, android.view.OnReceiveContentListener onReceiveContentListener) {
        if (onReceiveContentListener != null) {
            com.android.internal.util.Preconditions.checkArgument(strArr != null && strArr.length > 0, "When the listener is set, MIME types must also be set");
        }
        if (strArr != null) {
            com.android.internal.util.Preconditions.checkArgument(java.util.Arrays.stream(strArr).noneMatch(new java.util.function.Predicate() { // from class: android.view.View$$ExternalSyntheticLambda8
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    boolean startsWith;
                    startsWith = ((java.lang.String) obj).startsWith("*");
                    return startsWith;
                }
            }), "A MIME type set here must not start with *: " + java.util.Arrays.toString(strArr));
        }
        if (com.android.internal.util.ArrayUtils.isEmpty(strArr)) {
            strArr = null;
        }
        this.mReceiveContentMimeTypes = strArr;
        getListenerInfo().mOnReceiveContentListener = onReceiveContentListener;
    }

    public android.view.ContentInfo performReceiveContent(android.view.ContentInfo contentInfo) {
        android.view.OnReceiveContentListener onReceiveContentListener = this.mListenerInfo == null ? null : getListenerInfo().mOnReceiveContentListener;
        if (onReceiveContentListener != null) {
            android.view.ContentInfo onReceiveContent = onReceiveContentListener.onReceiveContent(this, contentInfo);
            if (onReceiveContent == null) {
                return null;
            }
            return onReceiveContent(onReceiveContent);
        }
        return onReceiveContent(contentInfo);
    }

    public android.view.ContentInfo onReceiveContent(android.view.ContentInfo contentInfo) {
        return contentInfo;
    }

    public java.lang.String[] getReceiveContentMimeTypes() {
        return this.mReceiveContentMimeTypes;
    }

    public void autofill(android.view.autofill.AutofillValue autofillValue) {
    }

    public void autofill(android.util.SparseArray<android.view.autofill.AutofillValue> sparseArray) {
        android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider;
        if (!this.mContext.isAutofillCompatibilityEnabled() || (accessibilityNodeProvider = getAccessibilityNodeProvider()) == null) {
            return;
        }
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            android.view.autofill.AutofillValue valueAt = sparseArray.valueAt(i);
            if (valueAt.isText()) {
                int keyAt = sparseArray.keyAt(i);
                java.lang.CharSequence textValue = valueAt.getTextValue();
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putCharSequence(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, textValue);
                accessibilityNodeProvider.performAction(keyAt, 2097152, bundle);
            }
        }
    }

    public void onGetCredentialResponse(android.credentials.GetCredentialResponse getCredentialResponse) {
        if (getCredentialManagerCallback() == null) {
            android.util.Log.w(AUTOFILL_LOG_TAG, "onGetCredentialResponse called but no callback found");
        } else {
            getCredentialManagerCallback().onResult(getCredentialResponse);
        }
    }

    public void onGetCredentialException(java.lang.String str, java.lang.String str2) {
        if (getCredentialManagerCallback() == null) {
            android.util.Log.w(AUTOFILL_LOG_TAG, "onGetCredentialException called but no callback found");
        } else {
            getCredentialManagerCallback().onError(new android.credentials.GetCredentialException(str, str2));
        }
    }

    public final android.view.autofill.AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new android.view.autofill.AutofillId(getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public final android.credentials.GetCredentialRequest getCredentialManagerRequest() {
        if (this.mViewCredentialHandler == null) {
            return null;
        }
        return this.mViewCredentialHandler.getRequest();
    }

    public final android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> getCredentialManagerCallback() {
        if (this.mViewCredentialHandler == null) {
            return null;
        }
        return this.mViewCredentialHandler.getCallback();
    }

    public void setAutofillId(android.view.autofill.AutofillId autofillId) {
        if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            android.util.Log.v(AUTOFILL_LOG_TAG, "setAutofill(): from " + this.mAutofillId + " to " + autofillId);
        }
        if (isAttachedToWindow()) {
            throw new java.lang.IllegalStateException("Cannot set autofill id when view is attached");
        }
        if (autofillId != null && !autofillId.isNonVirtual()) {
            throw new java.lang.IllegalStateException("Cannot set autofill id assigned to virtual views");
        }
        if (autofillId == null && (this.mPrivateFlags3 & 1073741824) == 0) {
            return;
        }
        this.mAutofillId = autofillId;
        if (autofillId != null) {
            this.mAutofillViewId = autofillId.getViewId();
            this.mPrivateFlags3 |= 1073741824;
        } else {
            this.mAutofillViewId = -1;
            this.mPrivateFlags3 &= -1073741825;
        }
    }

    public void resetSubtreeAutofillIds() {
        if (this.mAutofillViewId == -1) {
            return;
        }
        if (android.util.Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
            android.util.Log.v(CONTENT_CAPTURE_LOG_TAG, "resetAutofillId() for " + this.mAutofillViewId);
        } else if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            android.util.Log.v(AUTOFILL_LOG_TAG, "resetAutofillId() for " + this.mAutofillViewId);
        }
        this.mAutofillId = null;
        this.mAutofillViewId = -1;
        this.mPrivateFlags3 &= -1073741825;
    }

    public int getAutofillType() {
        return 0;
    }

    @android.view.ViewDebug.ExportedProperty
    public java.lang.String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public boolean isAutofilled() {
        return (this.mPrivateFlags3 & 65536) != 0;
    }

    public boolean hideAutofillHighlight() {
        return (this.mPrivateFlags4 & 512) != 0;
    }

    public android.view.autofill.AutofillValue getAutofillValue() {
        return null;
    }

    @android.view.ViewDebug.ExportedProperty(mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "auto"), @android.view.ViewDebug.IntToString(from = 1, to = android.media.MediaMetrics.Value.YES), @android.view.ViewDebug.IntToString(from = 2, to = android.media.MediaMetrics.Value.NO), @android.view.ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"), @android.view.ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")})
    public int getImportantForAutofill() {
        return (this.mPrivateFlags3 & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) >> 19;
    }

    public void setImportantForAutofill(int i) {
        this.mPrivateFlags3 &= -7864321;
        this.mPrivateFlags3 = ((i << 19) & PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK) | this.mPrivateFlags3;
    }

    public final boolean isImportantForAutofill() {
        java.lang.String str;
        for (android.view.ViewParent viewParent = this.mParent; viewParent instanceof android.view.View; viewParent = viewParent.getParent()) {
            int importantForAutofill = ((android.view.View) viewParent).getImportantForAutofill();
            if (importantForAutofill == 8 || importantForAutofill == 4) {
                if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                    android.util.Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because parent " + viewParent + "'s importance is " + importantForAutofill);
                }
                return false;
            }
        }
        int importantForAutofill2 = getImportantForAutofill();
        if (importantForAutofill2 == 4 || importantForAutofill2 == 1) {
            return true;
        }
        if (importantForAutofill2 == 8 || importantForAutofill2 == 2) {
            if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                android.util.Log.v(AUTOFILL_LOG_TAG, "View (" + this + ") is not important for autofill because its importance is " + importantForAutofill2);
            }
            return false;
        }
        if (importantForAutofill2 != 0) {
            android.util.Log.w(AUTOFILL_LOG_TAG, "invalid autofill importance (" + importantForAutofill2 + " on view " + this);
            return false;
        }
        int i = this.mID;
        if (i != -1 && !isViewIdGenerated(i)) {
            android.content.res.Resources resources = getResources();
            java.lang.String str2 = null;
            try {
                str = resources.getResourceEntryName(i);
                try {
                    str2 = resources.getResourcePackageName(i);
                } catch (android.content.res.Resources.NotFoundException e) {
                }
            } catch (android.content.res.Resources.NotFoundException e2) {
                str = null;
            }
            if (str != null && str2 != null && str2.equals(this.mContext.getPackageName())) {
                return true;
            }
        }
        return getAutofillHints() != null;
    }

    public final void setContentSensitivity(int i) {
        this.mPrivateFlags4 &= -50331649;
        this.mPrivateFlags4 = ((i << 24) & 50331648) | this.mPrivateFlags4;
        if (android.view.flags.Flags.sensitiveContentAppProtection()) {
            updateSensitiveViewsCountIfNeeded(isAggregatedVisible());
        }
    }

    public final int getContentSensitivity() {
        return (this.mPrivateFlags4 & 50331648) >> 24;
    }

    public final boolean isContentSensitive() {
        int contentSensitivity = getContentSensitivity();
        if (contentSensitivity == 1) {
            return true;
        }
        if (contentSensitivity != 2 && android.view.flags.Flags.sensitiveContentAppProtection()) {
            return android.view.View.SensitiveAutofillHintsHelper.containsSensitiveAutofillHint(getAutofillHints());
        }
        return false;
    }

    private void updateSensitiveViewsCountIfNeeded(boolean z) {
        if (!android.view.flags.Flags.sensitiveContentAppProtection() || this.mAttachInfo == null) {
            return;
        }
        if (!z || !isContentSensitive()) {
            if ((this.mPrivateFlags4 & 67108864) != 0) {
                this.mPrivateFlags4 &= -67108865;
                this.mAttachInfo.decreaseSensitiveViewsCount();
                return;
            }
            return;
        }
        if ((this.mPrivateFlags4 & 67108864) == 0) {
            this.mPrivateFlags4 |= 67108864;
            this.mAttachInfo.increaseSensitiveViewsCount();
        }
    }

    @android.view.ViewDebug.ExportedProperty(mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "auto"), @android.view.ViewDebug.IntToString(from = 1, to = android.media.MediaMetrics.Value.YES), @android.view.ViewDebug.IntToString(from = 2, to = android.media.MediaMetrics.Value.NO), @android.view.ViewDebug.IntToString(from = 4, to = "yesExcludeDescendants"), @android.view.ViewDebug.IntToString(from = 8, to = "noExcludeDescendants")})
    public int getImportantForContentCapture() {
        return this.mPrivateFlags4 & 15;
    }

    public void setImportantForContentCapture(int i) {
        this.mPrivateFlags4 &= -16;
        this.mPrivateFlags4 = (i & 15) | this.mPrivateFlags4;
    }

    public final boolean isImportantForContentCapture() {
        if ((this.mPrivateFlags4 & 64) != 0) {
            return (this.mPrivateFlags4 & 128) != 0;
        }
        boolean calculateIsImportantForContentCapture = calculateIsImportantForContentCapture();
        this.mPrivateFlags4 &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        if (calculateIsImportantForContentCapture) {
            this.mPrivateFlags4 |= 128;
        }
        this.mPrivateFlags4 |= 64;
        return calculateIsImportantForContentCapture;
    }

    private boolean calculateIsImportantForContentCapture() {
        for (android.view.ViewParent viewParent = this.mParent; viewParent instanceof android.view.View; viewParent = viewParent.getParent()) {
            int importantForContentCapture = ((android.view.View) viewParent).getImportantForContentCapture();
            if (importantForContentCapture == 8 || importantForContentCapture == 4) {
                if (android.util.Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                    android.util.Log.v(CONTENT_CAPTURE_LOG_TAG, "View (" + this + ") is not important for content capture because parent " + viewParent + "'s importance is " + importantForContentCapture);
                }
                return false;
            }
        }
        int importantForContentCapture2 = getImportantForContentCapture();
        if (importantForContentCapture2 == 4 || importantForContentCapture2 == 1) {
            return true;
        }
        if (importantForContentCapture2 == 8 || importantForContentCapture2 == 2) {
            if (android.util.Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 2)) {
                android.util.Log.v(CONTENT_CAPTURE_LOG_TAG, "View (" + this + ") is not important for content capture because its importance is " + importantForContentCapture2);
            }
            return false;
        }
        if (importantForContentCapture2 != 0) {
            android.util.Log.w(CONTENT_CAPTURE_LOG_TAG, "invalid content capture importance (" + importantForContentCapture2 + " on view " + this);
            return false;
        }
        if (this instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                if (viewGroup.getChildAt(i).isImportantForContentCapture()) {
                    return true;
                }
            }
        }
        return getAutofillHints() != null;
    }

    private void notifyAppearedOrDisappearedForContentCaptureIfNeeded(boolean z) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if ((attachInfo == null || attachInfo.mReadyForContentCaptureUpdates) && this.mContext.getContentCaptureOptions() != null) {
            if (z) {
                boolean z2 = getNotifiedContentCaptureDisappeared() && getVisibility() == 0 && !isLayoutRequested();
                if (getVisibility() == 0 && !getNotifiedContentCaptureAppeared()) {
                    if (!isLaidOut() && !z2) {
                        return;
                    }
                } else {
                    return;
                }
            } else if (!getNotifiedContentCaptureAppeared() || getNotifiedContentCaptureDisappeared()) {
                return;
            }
            android.view.contentcapture.ContentCaptureSession contentCaptureSession = getContentCaptureSession();
            if (contentCaptureSession != null && isImportantForContentCapture()) {
                if (z) {
                    setNotifiedContentCaptureAppeared();
                    if (attachInfo != null) {
                        makeParentImportantAndNotifyAppearedEventIfNeed();
                        attachInfo.delayNotifyContentCaptureEvent(contentCaptureSession, this, z);
                        return;
                    }
                    return;
                }
                this.mPrivateFlags4 |= 32;
                this.mPrivateFlags4 &= -17;
                if (attachInfo != null) {
                    attachInfo.delayNotifyContentCaptureEvent(contentCaptureSession, this, z);
                }
                if (!isTemporarilyDetached()) {
                    clearTranslationState();
                }
            }
        }
    }

    private void makeParentImportantAndNotifyAppearedEventIfNeed() {
        java.lang.Object parent = getParent();
        if (parent instanceof android.view.View) {
            android.view.View view = (android.view.View) parent;
            if (view.getNotifiedContentCaptureAppeared()) {
                return;
            }
            view.mPrivateFlags4 |= 192;
            view.notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
        }
    }

    private void setNotifiedContentCaptureAppeared() {
        this.mPrivateFlags4 |= 16;
        this.mPrivateFlags4 &= -33;
    }

    protected boolean getNotifiedContentCaptureAppeared() {
        return (this.mPrivateFlags4 & 16) != 0;
    }

    private boolean getNotifiedContentCaptureDisappeared() {
        return (this.mPrivateFlags4 & 32) != 0;
    }

    public void setContentCaptureSession(android.view.contentcapture.ContentCaptureSession contentCaptureSession) {
        this.mContentCaptureSession = contentCaptureSession;
    }

    public final android.view.contentcapture.ContentCaptureSession getContentCaptureSession() {
        if (this.mContentCaptureSessionCached) {
            return this.mContentCaptureSession;
        }
        this.mContentCaptureSession = getAndCacheContentCaptureSession();
        this.mContentCaptureSessionCached = true;
        return this.mContentCaptureSession;
    }

    private android.view.contentcapture.ContentCaptureSession getAndCacheContentCaptureSession() {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (this.mContentCaptureSession != null) {
            return this.mContentCaptureSession;
        }
        if (!(this.mParent instanceof android.view.View)) {
            contentCaptureSession = null;
        } else {
            contentCaptureSession = ((android.view.View) this.mParent).getContentCaptureSession();
        }
        if (contentCaptureSession == null) {
            android.view.contentcapture.ContentCaptureManager contentCaptureManager = (android.view.contentcapture.ContentCaptureManager) this.mContext.getSystemService(android.view.contentcapture.ContentCaptureManager.class);
            if (contentCaptureManager == null) {
                return null;
            }
            return contentCaptureManager.getMainContentCaptureSession();
        }
        return contentCaptureSession;
    }

    private android.view.autofill.AutofillManager getAutofillManager() {
        return (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class);
    }

    final boolean isActivityDeniedForAutofillForUnimportantView() {
        android.view.autofill.AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null) {
            return false;
        }
        return autofillManager.isActivityDeniedForAutofill();
    }

    final boolean isMatchingAutofillableHeuristics() {
        android.view.autofill.AutofillManager autofillManager = getAutofillManager();
        if (autofillManager == null || !autofillManager.isTriggerFillRequestOnUnimportantViewEnabled()) {
            return false;
        }
        return autofillManager.isAutofillable(this);
    }

    private boolean isAutofillable() {
        android.view.autofill.AutofillManager autofillManager;
        if (getAutofillType() == 0 || (autofillManager = getAutofillManager()) == null || getAutofillViewId() <= 1073741823) {
            return false;
        }
        if ((isImportantForAutofill() && autofillManager.isTriggerFillRequestOnFilteredImportantViewsEnabled()) || (!isImportantForAutofill() && autofillManager.isTriggerFillRequestOnUnimportantViewEnabled())) {
            if (autofillManager.isAutofillable(this)) {
                return true;
            }
            return notifyAugmentedAutofillIfNeeded(autofillManager);
        }
        if (isImportantForAutofill()) {
            return true;
        }
        return notifyAugmentedAutofillIfNeeded(autofillManager);
    }

    private boolean notifyAugmentedAutofillIfNeeded(android.view.autofill.AutofillManager autofillManager) {
        android.content.AutofillOptions autofillOptions = this.mContext.getAutofillOptions();
        if (autofillOptions == null || !autofillOptions.isAugmentedAutofillEnabled(this.mContext)) {
            return false;
        }
        autofillManager.notifyViewEnteredForAugmentedAutofill(this);
        return true;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        return isAutofillable() && isAttachedToWindow();
    }

    private void populateVirtualStructure(android.view.ViewStructure viewStructure, android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        viewStructure.setId(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId()), null, null, accessibilityNodeInfo.getViewIdResourceName());
        android.graphics.Rect tempRect = viewStructure.getTempRect();
        accessibilityNodeInfo.getBoundsInParent(tempRect);
        viewStructure.setDimens(tempRect.left, tempRect.top, 0, 0, tempRect.width(), tempRect.height());
        viewStructure.setVisibility(0);
        viewStructure.setEnabled(accessibilityNodeInfo.isEnabled());
        if (accessibilityNodeInfo.isClickable()) {
            viewStructure.setClickable(true);
        }
        if (accessibilityNodeInfo.isFocusable()) {
            viewStructure.setFocusable(true);
        }
        if (accessibilityNodeInfo.isFocused()) {
            viewStructure.setFocused(true);
        }
        if (accessibilityNodeInfo.isAccessibilityFocused()) {
            viewStructure.setAccessibilityFocused(true);
        }
        if (accessibilityNodeInfo.isSelected()) {
            viewStructure.setSelected(true);
        }
        if (accessibilityNodeInfo.isLongClickable()) {
            viewStructure.setLongClickable(true);
        }
        if (accessibilityNodeInfo.isCheckable()) {
            viewStructure.setCheckable(true);
            if (accessibilityNodeInfo.isChecked()) {
                viewStructure.setChecked(true);
            }
        }
        if (accessibilityNodeInfo.isContextClickable()) {
            viewStructure.setContextClickable(true);
        }
        if (z) {
            viewStructure.setAutofillId(new android.view.autofill.AutofillId(getAutofillId(), android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId())));
        }
        if (getViewCredentialHandler() != null) {
            viewStructure.setCredentialManagerRequest(getViewCredentialHandler().getRequest(), getViewCredentialHandler().getCallback());
        }
        java.lang.CharSequence className = accessibilityNodeInfo.getClassName();
        viewStructure.setClassName(className != null ? className.toString() : null);
        viewStructure.setContentDescription(accessibilityNodeInfo.getContentDescription());
        if (z) {
            int maxTextLength = accessibilityNodeInfo.getMaxTextLength();
            if (maxTextLength != -1) {
                viewStructure.setMaxTextLength(maxTextLength);
            }
            viewStructure.setHint(accessibilityNodeInfo.getHintText());
        }
        java.lang.CharSequence text = accessibilityNodeInfo.getText();
        boolean z2 = (text == null && accessibilityNodeInfo.getError() == null) ? false : true;
        if (z2) {
            viewStructure.setText(text, accessibilityNodeInfo.getTextSelectionStart(), accessibilityNodeInfo.getTextSelectionEnd());
        }
        if (z) {
            if (accessibilityNodeInfo.isEditable()) {
                viewStructure.setDataIsSensitive(true);
                if (z2) {
                    viewStructure.setAutofillType(1);
                    viewStructure.setAutofillValue(android.view.autofill.AutofillValue.forText(text));
                }
                int inputType = accessibilityNodeInfo.getInputType();
                if (inputType == 0 && accessibilityNodeInfo.isPassword()) {
                    inputType = 129;
                }
                viewStructure.setInputType(inputType);
            } else {
                viewStructure.setDataIsSensitive(false);
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        if (childCount > 0) {
            viewStructure.setChildCount(childCount);
            for (int i = 0; i < childCount; i++) {
                if (android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildNodeIds().get(i)) == -1) {
                    android.util.Log.e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                } else {
                    android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildId(i)));
                    if (createAccessibilityNodeInfo != null) {
                        populateVirtualStructure(viewStructure.newChild(i), accessibilityNodeProvider, createAccessibilityNodeInfo, z);
                        createAccessibilityNodeInfo.recycle();
                    }
                }
            }
        }
    }

    public void dispatchProvideStructure(android.view.ViewStructure viewStructure) {
        dispatchProvideStructure(viewStructure, 0, 0);
    }

    public void dispatchProvideAutofillStructure(android.view.ViewStructure viewStructure, int i) {
        dispatchProvideStructure(viewStructure, 1, i);
    }

    private void dispatchProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        if (i == 1) {
            viewStructure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(viewStructure, i2);
            onProvideAutofillVirtualStructure(viewStructure, i2);
        } else if (!isAssistBlocked()) {
            onProvideStructure(viewStructure);
            onProvideVirtualStructure(viewStructure);
        } else {
            viewStructure.setClassName(getAccessibilityClassName().toString());
            viewStructure.setAssistBlocked(true);
        }
    }

    public void dispatchInitialProvideContentCaptureStructure() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            android.util.Log.w(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no AttachInfo for " + this);
            return;
        }
        android.view.contentcapture.ContentCaptureManager contentCaptureManager = attachInfo.mContentCaptureManager;
        if (contentCaptureManager == null) {
            android.util.Log.w(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no ContentCaptureManager for " + this);
            return;
        }
        attachInfo.mReadyForContentCaptureUpdates = true;
        if (!isImportantForContentCapture()) {
            if (android.util.Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                android.util.Log.d(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): decorView is not important");
                return;
            }
            return;
        }
        attachInfo.mContentCaptureManager = contentCaptureManager;
        android.view.contentcapture.ContentCaptureSession contentCaptureSession = getContentCaptureSession();
        if (contentCaptureSession == null) {
            if (android.util.Log.isLoggable(CONTENT_CAPTURE_LOG_TAG, 3)) {
                android.util.Log.d(CONTENT_CAPTURE_LOG_TAG, "dispatchProvideContentCaptureStructure(): no session for " + this);
            }
        } else {
            contentCaptureSession.notifyViewTreeEvent(true);
            try {
                dispatchProvideContentCaptureStructure();
            } finally {
                contentCaptureSession.notifyViewTreeEvent(false);
            }
        }
    }

    void dispatchProvideContentCaptureStructure() {
        android.view.contentcapture.ContentCaptureSession contentCaptureSession = getContentCaptureSession();
        if (contentCaptureSession != null) {
            android.view.ViewStructure newViewStructure = contentCaptureSession.newViewStructure(this);
            onProvideContentCaptureStructure(newViewStructure, 0);
            setNotifiedContentCaptureAppeared();
            contentCaptureSession.notifyViewAppeared(newViewStructure);
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction accessibilityAction;
        if (this.mAttachInfo == null) {
            return;
        }
        android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
        getDrawingRect(rect);
        accessibilityNodeInfo.setBoundsInParent(rect);
        getBoundsOnScreen(rect, true);
        accessibilityNodeInfo.setBoundsInScreen(rect);
        getBoundsInWindow(rect, true);
        accessibilityNodeInfo.setBoundsInWindow(rect);
        java.lang.Object parentForAccessibility = getParentForAccessibility();
        if (parentForAccessibility instanceof android.view.View) {
            accessibilityNodeInfo.setParent((android.view.View) parentForAccessibility);
        }
        if (this.mID != -1) {
            android.view.View rootView = getRootView();
            if (rootView == null) {
                rootView = this;
            }
            android.view.View findLabelForView = rootView.findLabelForView(this, this.mID);
            if (findLabelForView != null) {
                accessibilityNodeInfo.setLabeledBy(findLabelForView);
            }
            if ((this.mAttachInfo.mAccessibilityFetchFlags & 256) != 0 && android.content.res.Resources.resourceHasPackage(this.mID)) {
                try {
                    accessibilityNodeInfo.setViewIdResourceName(getResources().getResourceName(this.mID));
                } catch (android.content.res.Resources.NotFoundException e) {
                }
            }
        }
        if (this.mLabelForId != -1) {
            android.view.View rootView2 = getRootView();
            if (rootView2 == null) {
                rootView2 = this;
            }
            android.view.View findViewInsideOutShouldExist = rootView2.findViewInsideOutShouldExist(this, this.mLabelForId);
            if (findViewInsideOutShouldExist != null) {
                accessibilityNodeInfo.setLabelFor(findViewInsideOutShouldExist);
            }
        }
        if (this.mAccessibilityTraversalBeforeId != -1) {
            android.view.View rootView3 = getRootView();
            if (rootView3 == null) {
                rootView3 = this;
            }
            android.view.View findViewInsideOutShouldExist2 = rootView3.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalBeforeId);
            if (findViewInsideOutShouldExist2 != null && findViewInsideOutShouldExist2.includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalBefore(findViewInsideOutShouldExist2);
            }
        }
        if (this.mAccessibilityTraversalAfterId != -1) {
            android.view.View rootView4 = getRootView();
            if (rootView4 == null) {
                rootView4 = this;
            }
            android.view.View findViewInsideOutShouldExist3 = rootView4.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalAfterId);
            if (findViewInsideOutShouldExist3 != null && findViewInsideOutShouldExist3.includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalAfter(findViewInsideOutShouldExist3);
            }
        }
        accessibilityNodeInfo.setVisibleToUser(isVisibleToUser());
        accessibilityNodeInfo.setImportantForAccessibility(isImportantForAccessibility());
        accessibilityNodeInfo.setAccessibilityDataSensitive(isAccessibilityDataSensitive());
        accessibilityNodeInfo.setPackageName(this.mContext.getPackageName());
        accessibilityNodeInfo.setClassName(getAccessibilityClassName());
        accessibilityNodeInfo.setStateDescription(getStateDescription());
        accessibilityNodeInfo.setContentDescription(getContentDescription());
        accessibilityNodeInfo.setEnabled(isEnabled());
        accessibilityNodeInfo.setClickable(isClickable());
        accessibilityNodeInfo.setFocusable(isFocusable());
        accessibilityNodeInfo.setScreenReaderFocusable(isScreenReaderFocusable());
        accessibilityNodeInfo.setFocused(isFocused());
        accessibilityNodeInfo.setAccessibilityFocused(isAccessibilityFocused());
        accessibilityNodeInfo.setSelected(isSelected());
        accessibilityNodeInfo.setLongClickable(isLongClickable());
        accessibilityNodeInfo.setContextClickable(isContextClickable());
        accessibilityNodeInfo.setLiveRegion(getAccessibilityLiveRegion());
        if (this.mTooltipInfo != null && this.mTooltipInfo.mTooltipText != null) {
            accessibilityNodeInfo.setTooltipText(this.mTooltipInfo.mTooltipText);
            if (this.mTooltipInfo.mTooltipPopup == null) {
                accessibilityAction = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP;
            } else {
                accessibilityAction = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            }
            accessibilityNodeInfo.addAction(accessibilityAction);
        }
        accessibilityNodeInfo.addAction(4);
        accessibilityNodeInfo.addAction(8);
        if (isFocusable()) {
            if (isFocused()) {
                accessibilityNodeInfo.addAction(2);
            } else {
                accessibilityNodeInfo.addAction(1);
            }
        }
        if (!isAccessibilityFocused()) {
            accessibilityNodeInfo.addAction(64);
        } else {
            accessibilityNodeInfo.addAction(128);
        }
        if (isClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(16);
        }
        if (isLongClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(32);
        }
        if (isContextClickable() && isEnabled()) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
        }
        java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (iterableTextForAccessibility != null && iterableTextForAccessibility.length() > 0) {
            accessibilityNodeInfo.setTextSelection(getAccessibilitySelectionStart(), getAccessibilitySelectionEnd());
            accessibilityNodeInfo.addAction(131072);
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(11);
        }
        accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        populateAccessibilityNodeInfoDrawingOrderInParent(accessibilityNodeInfo);
        accessibilityNodeInfo.setPaneTitle(this.mAccessibilityPaneTitle);
        accessibilityNodeInfo.setHeading(isAccessibilityHeading());
        if (this.mTouchDelegate != null) {
            accessibilityNodeInfo.setTouchDelegateInfo(this.mTouchDelegate.getTouchDelegateInfo());
        }
        if (startedSystemDragForAccessibility()) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_CANCEL);
        }
        if (canAcceptAccessibilityDrop()) {
            accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_DRAG_DROP);
        }
    }

    public void addExtraDataToAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.lang.String str, android.os.Bundle bundle) {
    }

    private void populateAccessibilityNodeInfoDrawingOrderInParent(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        int i = 0;
        if ((this.mPrivateFlags & 16) == 0) {
            accessibilityNodeInfo.setDrawingOrder(0);
            return;
        }
        android.view.ViewParent parentForAccessibility = getParentForAccessibility();
        android.view.View view = this;
        int i2 = 1;
        while (true) {
            if (view == parentForAccessibility) {
                i = i2;
                break;
            }
            java.lang.Object parent = view.getParent();
            if (!(parent instanceof android.view.ViewGroup)) {
                break;
            }
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) parent;
            int childCount = viewGroup.getChildCount();
            if (childCount > 1) {
                java.util.ArrayList<android.view.View> buildOrderedChildList = viewGroup.buildOrderedChildList();
                if (buildOrderedChildList != null) {
                    int indexOf = buildOrderedChildList.indexOf(view);
                    for (int i3 = 0; i3 < indexOf; i3++) {
                        i2 += numViewsForAccessibility(buildOrderedChildList.get(i3));
                    }
                    buildOrderedChildList.clear();
                } else {
                    int indexOfChild = viewGroup.indexOfChild(view);
                    boolean isChildrenDrawingOrderEnabled = viewGroup.isChildrenDrawingOrderEnabled();
                    if (indexOfChild >= 0 && isChildrenDrawingOrderEnabled) {
                        indexOfChild = viewGroup.getChildDrawingOrder(childCount, indexOfChild);
                    }
                    int i4 = isChildrenDrawingOrderEnabled ? childCount : indexOfChild;
                    if (indexOfChild != 0) {
                        for (int i5 = 0; i5 < i4; i5++) {
                            if ((isChildrenDrawingOrderEnabled ? viewGroup.getChildDrawingOrder(childCount, i5) : i5) < indexOfChild) {
                                i2 += numViewsForAccessibility(viewGroup.getChildAt(i5));
                            }
                        }
                    }
                }
            }
            view = (android.view.View) parent;
        }
        accessibilityNodeInfo.setDrawingOrder(i);
    }

    private static int numViewsForAccessibility(android.view.View view) {
        if (view != null) {
            if (view.includeForAccessibility()) {
                return 1;
            }
            if (view instanceof android.view.ViewGroup) {
                return ((android.view.ViewGroup) view).getNumChildrenForAccessibility();
            }
            return 0;
        }
        return 0;
    }

    private android.view.View findLabelForView(android.view.View view, int i) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new android.view.View.MatchLabelForPredicate();
        }
        this.mMatchLabelForPredicate.mLabeledId = i;
        return findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    public boolean isVisibleToUserForAutofill(int i) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            android.view.accessibility.AccessibilityNodeProvider accessibilityNodeProvider = getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) {
                android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(i);
                if (createAccessibilityNodeInfo != null) {
                    return createAccessibilityNodeInfo.isVisibleToUser();
                }
                return false;
            }
            android.util.Log.w(VIEW_LOG_TAG, "isVisibleToUserForAutofill(" + i + "): no provider");
            return false;
        }
        return true;
    }

    public boolean isVisibleToUser() {
        return isVisibleToUser(null);
    }

    protected boolean isVisibleToUser(android.graphics.Rect rect) {
        if (this.mAttachInfo == null || this.mAttachInfo.mWindowVisibility != 0) {
            return false;
        }
        java.lang.Object obj = this;
        while (obj instanceof android.view.View) {
            android.view.View view = (android.view.View) obj;
            if (view.getAlpha() <= 0.0f || view.getTransitionAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            obj = view.mParent;
        }
        android.graphics.Rect rect2 = this.mAttachInfo.mTmpInvalRect;
        android.graphics.Point point = this.mAttachInfo.mPoint;
        if (!getGlobalVisibleRect(rect2, point)) {
            return false;
        }
        if (rect != null) {
            rect2.offset(-point.x, -point.y);
            return rect.intersect(rect2);
        }
        return true;
    }

    public android.view.View.AccessibilityDelegate getAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public void setAccessibilityDelegate(android.view.View.AccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
    }

    public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.getAccessibilityNodeProvider(this);
        }
        return null;
    }

    public int getAccessibilityViewId() {
        if (this.mAccessibilityViewId == -1) {
            int i = sNextAccessibilityViewId;
            sNextAccessibilityViewId = i + 1;
            this.mAccessibilityViewId = i;
        }
        return this.mAccessibilityViewId;
    }

    public int getAutofillViewId() {
        if (this.mAutofillViewId == -1) {
            this.mAutofillViewId = this.mContext.getNextAutofillId();
        }
        return this.mAutofillViewId;
    }

    public int getAccessibilityWindowId() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mAccessibilityWindowId;
        }
        return -1;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.content.Context.ACCESSIBILITY_SERVICE)
    public final java.lang.CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.content.Context.ACCESSIBILITY_SERVICE)
    public java.lang.CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @android.view.RemotableViewMethod
    public void setStateDescription(java.lang.CharSequence charSequence) {
        if (this.mStateDescription == null) {
            if (charSequence == null) {
                return;
            }
        } else if (this.mStateDescription.equals(charSequence)) {
            return;
        }
        this.mStateDescription = charSequence;
        if (!android.text.TextUtils.isEmpty(charSequence) && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    @android.view.RemotableViewMethod
    public void setContentDescription(java.lang.CharSequence charSequence) {
        if (this.mContentDescription == null) {
            if (charSequence == null) {
                return;
            }
        } else if (this.mContentDescription.equals(charSequence)) {
            return;
        }
        this.mContentDescription = charSequence;
        if ((charSequence != null && charSequence.length() > 0) && getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
            notifySubtreeAccessibilityStateChangedIfNeeded();
        } else {
            notifyViewAccessibilityStateChangedIfNeeded(4);
        }
    }

    @android.view.RemotableViewMethod
    public void setAccessibilityTraversalBefore(int i) {
        if (this.mAccessibilityTraversalBeforeId == i) {
            return;
        }
        this.mAccessibilityTraversalBeforeId = i;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @android.view.RemotableViewMethod
    public void setAccessibilityTraversalAfter(int i) {
        if (this.mAccessibilityTraversalAfterId == i) {
            return;
        }
        this.mAccessibilityTraversalAfterId = i;
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public int getAccessibilityTraversalAfter() {
        return this.mAccessibilityTraversalAfterId;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.content.Context.ACCESSIBILITY_SERVICE)
    public int getLabelFor() {
        return this.mLabelForId;
    }

    @android.view.RemotableViewMethod
    public void setLabelFor(int i) {
        if (this.mLabelForId == i) {
            return;
        }
        this.mLabelForId = i;
        if (this.mLabelForId != -1 && this.mID == -1) {
            this.mID = generateViewId();
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    protected void onFocusLost() {
        resetPressedState();
    }

    private void resetPressedState() {
        if ((this.mViewFlags & 32) != 32 && isPressed()) {
            setPressed(false);
            if (!this.mHasPerformedLongPress) {
                removeLongPressCallback();
            }
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public boolean isFocused() {
        return (this.mPrivateFlags & 2) != 0;
    }

    public android.view.View findFocus() {
        if ((this.mPrivateFlags & 2) != 0) {
            return this;
        }
        return null;
    }

    public boolean isScrollContainer() {
        return (this.mPrivateFlags & 1048576) != 0;
    }

    public void setScrollContainer(boolean z) {
        if (!z) {
            if ((this.mPrivateFlags & 1048576) != 0) {
                this.mAttachInfo.mScrollContainers.remove(this);
            }
            this.mPrivateFlags &= -1572865;
        } else {
            if (this.mAttachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
                this.mAttachInfo.mScrollContainers.add(this);
                this.mPrivateFlags |= 1048576;
            }
            this.mPrivateFlags |= 524288;
        }
    }

    @java.lang.Deprecated
    public int getDrawingCacheQuality() {
        return this.mViewFlags & 1572864;
    }

    @java.lang.Deprecated
    public void setDrawingCacheQuality(int i) {
        setFlags(i, 1572864);
    }

    public boolean getKeepScreenOn() {
        return (this.mViewFlags & 67108864) != 0;
    }

    public void setKeepScreenOn(boolean z) {
        setFlags(z ? 67108864 : 0, 67108864);
    }

    public int getNextFocusLeftId() {
        return this.mNextFocusLeftId;
    }

    public void setNextFocusLeftId(int i) {
        this.mNextFocusLeftId = i;
    }

    public int getNextFocusRightId() {
        return this.mNextFocusRightId;
    }

    public void setNextFocusRightId(int i) {
        this.mNextFocusRightId = i;
    }

    public int getNextFocusUpId() {
        return this.mNextFocusUpId;
    }

    public void setNextFocusUpId(int i) {
        this.mNextFocusUpId = i;
    }

    public int getNextFocusDownId() {
        return this.mNextFocusDownId;
    }

    public void setNextFocusDownId(int i) {
        this.mNextFocusDownId = i;
    }

    public int getNextFocusForwardId() {
        return this.mNextFocusForwardId;
    }

    public void setNextFocusForwardId(int i) {
        this.mNextFocusForwardId = i;
    }

    public int getNextClusterForwardId() {
        return this.mNextClusterForwardId;
    }

    public void setNextClusterForwardId(int i) {
        this.mNextClusterForwardId = i;
    }

    public boolean isShown() {
        java.lang.Object obj;
        android.view.View view = this;
        while ((view.mViewFlags & 12) == 0 && (obj = view.mParent) != null) {
            if (!(obj instanceof android.view.View)) {
                return true;
            }
            view = (android.view.View) obj;
            if (view == null) {
                return false;
            }
        }
        return false;
    }

    private boolean detached() {
        android.view.View view = this;
        while ((view.mPrivateFlags4 & 8192) == 0) {
            java.lang.Object obj = view.mParent;
            if (obj == null || !(obj instanceof android.view.View) || (view = (android.view.View) obj) == null) {
                return false;
            }
        }
        return true;
    }

    @java.lang.Deprecated
    protected boolean fitSystemWindows(android.graphics.Rect rect) {
        if ((this.mPrivateFlags3 & 32) == 0) {
            if (rect == null) {
                return false;
            }
            try {
                this.mPrivateFlags3 |= 64;
                return dispatchApplyWindowInsets(new android.view.WindowInsets(rect)).isConsumed();
            } finally {
                this.mPrivateFlags3 &= -65;
            }
        }
        return fitSystemWindowsInt(rect);
    }

    private boolean fitSystemWindowsInt(android.graphics.Rect rect) {
        if ((this.mViewFlags & 2) == 2) {
            android.graphics.Rect rect2 = sThreadLocal.get();
            boolean computeFitSystemWindows = computeFitSystemWindows(rect, rect2);
            applyInsets(rect2);
            return computeFitSystemWindows;
        }
        return false;
    }

    private void applyInsets(android.graphics.Rect rect) {
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = rect.left;
        this.mUserPaddingRightInitial = rect.right;
        internalSetPadding(rect.left, rect.top, rect.right, rect.bottom);
    }

    public android.view.WindowInsets onApplyWindowInsets(android.view.WindowInsets windowInsets) {
        if ((this.mPrivateFlags4 & 256) != 0 && (this.mViewFlags & 2) != 0) {
            return onApplyFrameworkOptionalFitSystemWindows(windowInsets);
        }
        if ((this.mPrivateFlags3 & 64) == 0) {
            if (fitSystemWindows(windowInsets.getSystemWindowInsetsAsRect())) {
                return windowInsets.consumeSystemWindowInsets();
            }
        } else if (fitSystemWindowsInt(windowInsets.getSystemWindowInsetsAsRect())) {
            return windowInsets.consumeSystemWindowInsets();
        }
        return windowInsets;
    }

    private android.view.WindowInsets onApplyFrameworkOptionalFitSystemWindows(android.view.WindowInsets windowInsets) {
        android.graphics.Rect rect = sThreadLocal.get();
        android.view.WindowInsets computeSystemWindowInsets = computeSystemWindowInsets(windowInsets, rect);
        applyInsets(rect);
        return computeSystemWindowInsets;
    }

    public void setOnApplyWindowInsetsListener(android.view.View.OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        getListenerInfo().mOnApplyWindowInsetsListener = onApplyWindowInsetsListener;
    }

    public android.view.WindowInsets dispatchApplyWindowInsets(android.view.WindowInsets windowInsets) {
        try {
            this.mPrivateFlags3 |= 32;
            if (this.mListenerInfo != null && this.mListenerInfo.mOnApplyWindowInsetsListener != null) {
                return this.mListenerInfo.mOnApplyWindowInsetsListener.onApplyWindowInsets(this, windowInsets);
            }
            return onApplyWindowInsets(windowInsets);
        } finally {
            this.mPrivateFlags3 &= -33;
        }
    }

    public void setWindowInsetsAnimationCallback(android.view.WindowInsetsAnimation.Callback callback) {
        getListenerInfo().mWindowInsetsAnimationCallback = callback;
    }

    public boolean hasWindowInsetsAnimationCallback() {
        return getListenerInfo().mWindowInsetsAnimationCallback != null;
    }

    public void dispatchWindowInsetsAnimationPrepare(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            this.mListenerInfo.mWindowInsetsAnimationCallback.onPrepare(windowInsetsAnimation);
        }
    }

    public android.view.WindowInsetsAnimation.Bounds dispatchWindowInsetsAnimationStart(android.view.WindowInsetsAnimation windowInsetsAnimation, android.view.WindowInsetsAnimation.Bounds bounds) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            return this.mListenerInfo.mWindowInsetsAnimationCallback.onStart(windowInsetsAnimation, bounds);
        }
        return bounds;
    }

    public android.view.WindowInsets dispatchWindowInsetsAnimationProgress(android.view.WindowInsets windowInsets, java.util.List<android.view.WindowInsetsAnimation> list) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            return this.mListenerInfo.mWindowInsetsAnimationCallback.onProgress(windowInsets, list);
        }
        return windowInsets;
    }

    public void dispatchWindowInsetsAnimationEnd(android.view.WindowInsetsAnimation windowInsetsAnimation) {
        if (this.mListenerInfo != null && this.mListenerInfo.mWindowInsetsAnimationCallback != null) {
            this.mListenerInfo.mWindowInsetsAnimationCallback.onEnd(windowInsetsAnimation);
        }
    }

    public void setSystemGestureExclusionRects(java.util.List<android.graphics.Rect> list) {
        if (list.isEmpty() && this.mListenerInfo == null) {
            return;
        }
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mSystemGestureExclusionRects != null) {
            listenerInfo.mSystemGestureExclusionRects.clear();
            listenerInfo.mSystemGestureExclusionRects.addAll(list);
        } else {
            listenerInfo.mSystemGestureExclusionRects = new java.util.ArrayList(list);
        }
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda4(this));
    }

    private void updatePositionUpdateListener() {
        final android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (getSystemGestureExclusionRects().isEmpty() && collectPreferKeepClearRects().isEmpty() && collectUnrestrictedPreferKeepClearRects().isEmpty() && (listenerInfo.mHandwritingArea == null || !shouldInitiateHandwriting())) {
            if (listenerInfo.mPositionUpdateListener != null) {
                this.mRenderNode.removePositionUpdateListener(listenerInfo.mPositionUpdateListener);
                listenerInfo.mPositionUpdateListener = null;
                listenerInfo.mPositionChangedUpdate = null;
                return;
            }
            return;
        }
        if (listenerInfo.mPositionUpdateListener == null) {
            listenerInfo.mPositionChangedUpdate = new java.lang.Runnable() { // from class: android.view.View$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.View.this.lambda$updatePositionUpdateListener$2();
                }
            };
            listenerInfo.mPositionUpdateListener = new android.graphics.RenderNode.PositionUpdateListener() { // from class: android.view.View.1
                @Override // android.graphics.RenderNode.PositionUpdateListener
                public void positionChanged(long j, int i, int i2, int i3, int i4) {
                    android.view.View.this.postUpdate(listenerInfo.mPositionChangedUpdate);
                }

                @Override // android.graphics.RenderNode.PositionUpdateListener
                public void positionLost(long j) {
                    android.view.View.this.postUpdate(listenerInfo.mPositionChangedUpdate);
                }
            };
            this.mRenderNode.addPositionUpdateListener(listenerInfo.mPositionUpdateListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePositionUpdateListener$2() {
        updateSystemGestureExclusionRects();
        updateKeepClearRects();
        updateHandwritingArea();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postUpdate(java.lang.Runnable runnable) {
        android.os.Handler handler = getHandler();
        if (handler != null) {
            handler.postAtFrontOfQueue(runnable);
        }
    }

    void updateSystemGestureExclusionRects() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    public java.util.List<android.graphics.Rect> getSystemGestureExclusionRects() {
        java.util.List<android.graphics.Rect> list;
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && (list = listenerInfo.mSystemGestureExclusionRects) != null) {
            return list;
        }
        return java.util.Collections.emptyList();
    }

    public final void setPreferKeepClear(boolean z) {
        getListenerInfo().mPreferKeepClear = z;
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda5(this));
    }

    public final boolean isPreferKeepClear() {
        return this.mListenerInfo != null && this.mListenerInfo.mPreferKeepClear;
    }

    public final void setPreferKeepClearRects(java.util.List<android.graphics.Rect> list) {
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mKeepClearRects != null) {
            listenerInfo.mKeepClearRects.clear();
            listenerInfo.mKeepClearRects.addAll(list);
        } else {
            listenerInfo.mKeepClearRects = new java.util.ArrayList(list);
        }
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda5(this));
    }

    public final java.util.List<android.graphics.Rect> getPreferKeepClearRects() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mKeepClearRects != null) {
            return new java.util.ArrayList(listenerInfo.mKeepClearRects);
        }
        return java.util.Collections.emptyList();
    }

    @android.annotation.SystemApi
    public final void setUnrestrictedPreferKeepClearRects(java.util.List<android.graphics.Rect> list) {
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        if (listenerInfo.mUnrestrictedKeepClearRects != null) {
            listenerInfo.mUnrestrictedKeepClearRects.clear();
            listenerInfo.mUnrestrictedKeepClearRects.addAll(list);
        } else {
            listenerInfo.mUnrestrictedKeepClearRects = new java.util.ArrayList(list);
        }
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda5(this));
    }

    @android.annotation.SystemApi
    public final java.util.List<android.graphics.Rect> getUnrestrictedPreferKeepClearRects() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnrestrictedKeepClearRects != null) {
            return new java.util.ArrayList(listenerInfo.mUnrestrictedKeepClearRects);
        }
        return java.util.Collections.emptyList();
    }

    void updateKeepClearRects() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.updateKeepClearRectsForView(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public java.util.List<android.graphics.Rect> collectPreferKeepClearRects() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        boolean z = (listenerInfo != null && listenerInfo.mPreferKeepClear) || (isFocused() && android.view.ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled());
        boolean z2 = (listenerInfo == null || listenerInfo.mKeepClearRects == null) ? false : true;
        if (!z && !z2) {
            return java.util.Collections.emptyList();
        }
        if (z && !z2) {
            return java.util.Collections.singletonList(new android.graphics.Rect(0, 0, getWidth(), getHeight()));
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (z) {
            arrayList.add(new android.graphics.Rect(0, 0, getWidth(), getHeight()));
        }
        if (z2) {
            arrayList.addAll(listenerInfo.mKeepClearRects);
        }
        return arrayList;
    }

    private void updatePreferKeepClearForFocus() {
        if (android.view.ViewConfiguration.get(this.mContext).isPreferKeepClearForFocusEnabled()) {
            updatePositionUpdateListener();
            post(new android.view.View$$ExternalSyntheticLambda5(this));
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public java.util.List<android.graphics.Rect> collectUnrestrictedPreferKeepClearRects() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnrestrictedKeepClearRects != null) {
            return listenerInfo.mUnrestrictedKeepClearRects;
        }
        return java.util.Collections.emptyList();
    }

    public void setHandwritingBoundsOffsets(float f, float f2, float f3, float f4) {
        this.mHandwritingBoundsOffsetLeft = f;
        this.mHandwritingBoundsOffsetTop = f2;
        this.mHandwritingBoundsOffsetRight = f3;
        this.mHandwritingBoundsOffsetBottom = f4;
    }

    public float getHandwritingBoundsOffsetLeft() {
        return this.mHandwritingBoundsOffsetLeft;
    }

    public float getHandwritingBoundsOffsetTop() {
        return this.mHandwritingBoundsOffsetTop;
    }

    public float getHandwritingBoundsOffsetRight() {
        return this.mHandwritingBoundsOffsetRight;
    }

    public float getHandwritingBoundsOffsetBottom() {
        return this.mHandwritingBoundsOffsetBottom;
    }

    public void setHandwritingArea(android.graphics.Rect rect) {
        getListenerInfo().mHandwritingArea = rect;
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda0(this));
    }

    public android.graphics.Rect getHandwritingArea() {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mHandwritingArea != null) {
            return new android.graphics.Rect(listenerInfo.mHandwritingArea);
        }
        return null;
    }

    void updateHandwritingArea() {
        android.view.View.AttachInfo attachInfo;
        if (shouldInitiateHandwriting() && (attachInfo = this.mAttachInfo) != null) {
            attachInfo.mViewRootImpl.getHandwritingInitiator().updateHandwritingAreasForView(this);
        }
    }

    boolean shouldInitiateHandwriting() {
        return isAutoHandwritingEnabled() || getHandwritingDelegatorCallback() != null;
    }

    public void setHandwritingDelegatorCallback(java.lang.Runnable runnable) {
        this.mHandwritingDelegatorCallback = runnable;
        if (runnable != null) {
            setHandwritingArea(new android.graphics.Rect(0, 0, getWidth(), getHeight()));
        }
    }

    public java.lang.Runnable getHandwritingDelegatorCallback() {
        return this.mHandwritingDelegatorCallback;
    }

    public void setAllowedHandwritingDelegatePackage(java.lang.String str) {
        this.mAllowedHandwritingDelegatePackageName = str;
    }

    public java.lang.String getAllowedHandwritingDelegatePackageName() {
        return this.mAllowedHandwritingDelegatePackageName;
    }

    public void setIsHandwritingDelegate(boolean z) {
        this.mIsHandwritingDelegate = z;
    }

    public boolean isHandwritingDelegate() {
        return this.mIsHandwritingDelegate;
    }

    public void setAllowedHandwritingDelegatorPackage(java.lang.String str) {
        this.mAllowedHandwritingDelegatorPackageName = str;
    }

    public java.lang.String getAllowedHandwritingDelegatorPackageName() {
        return this.mAllowedHandwritingDelegatorPackageName;
    }

    public void setHandwritingDelegateFlags(int i) {
        this.mHandwritingDelegateFlags = i;
    }

    public int getHandwritingDelegateFlags() {
        return this.mHandwritingDelegateFlags;
    }

    public void getLocationInSurface(int[] iArr) {
        getLocationInWindow(iArr);
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRootImpl != null) {
            iArr[0] = iArr[0] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.left;
            iArr[1] = iArr[1] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.top;
        }
    }

    public android.view.WindowInsets getRootWindowInsets() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getWindowInsets(false);
        }
        return null;
    }

    public android.view.WindowInsetsController getWindowInsetsController() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl.getInsetsController();
        }
        java.lang.Object parent = getParent();
        if (parent instanceof android.view.View) {
            return ((android.view.View) parent).getWindowInsetsController();
        }
        if (parent instanceof android.view.ViewRootImpl) {
            return ((android.view.ViewRootImpl) parent).getInsetsController();
        }
        return null;
    }

    public final android.window.OnBackInvokedDispatcher findOnBackInvokedDispatcher() {
        android.view.ViewParent parent = getParent();
        if (parent != null) {
            return parent.findOnBackInvokedDispatcherForChild(this, this);
        }
        return null;
    }

    @java.lang.Deprecated
    protected boolean computeFitSystemWindows(android.graphics.Rect rect, android.graphics.Rect rect2) {
        android.view.WindowInsets computeSystemWindowInsets = computeSystemWindowInsets(new android.view.WindowInsets(rect), rect2);
        rect.set(computeSystemWindowInsets.getSystemWindowInsetsAsRect());
        return computeSystemWindowInsets.isSystemWindowInsetsConsumed();
    }

    public android.view.WindowInsets computeSystemWindowInsets(android.view.WindowInsets windowInsets, android.graphics.Rect rect) {
        if ((((this.mViewFlags & 2048) == 0 && (this.mPrivateFlags4 & 256) == 0) ? false : true) && this.mAttachInfo != null) {
            android.view.Window.OnContentApplyWindowInsetsListener onContentApplyWindowInsetsListener = this.mAttachInfo.mContentOnApplyWindowInsetsListener;
            if (onContentApplyWindowInsetsListener == null) {
                rect.setEmpty();
                return windowInsets;
            }
            android.util.Pair<android.graphics.Insets, android.view.WindowInsets> onContentApplyWindowInsets = onContentApplyWindowInsetsListener.onContentApplyWindowInsets(this, windowInsets);
            rect.set(onContentApplyWindowInsets.first.toRect());
            return onContentApplyWindowInsets.second;
        }
        rect.set(windowInsets.getSystemWindowInsetsAsRect());
        return windowInsets.consumeSystemWindowInsets().inset(rect);
    }

    public void setFitsSystemWindows(boolean z) {
        setFlags(z ? 2 : 0, 2);
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean getFitsSystemWindows() {
        return (this.mViewFlags & 2) == 2;
    }

    public boolean fitsSystemWindows() {
        return getFitsSystemWindows();
    }

    @java.lang.Deprecated
    public void requestFitSystemWindows() {
        if (this.mParent != null) {
            this.mParent.requestFitSystemWindows();
        }
    }

    public void requestApplyInsets() {
        requestFitSystemWindows();
    }

    public void makeOptionalFitsSystemWindows() {
        setFlags(2048, 2048);
    }

    public void makeFrameworkOptionalFitsSystemWindows() {
        this.mPrivateFlags4 |= 256;
    }

    public boolean isFrameworkOptionalFitsSystemWindows() {
        return (this.mPrivateFlags4 & 256) != 0;
    }

    @android.view.ViewDebug.ExportedProperty(mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "VISIBLE"), @android.view.ViewDebug.IntToString(from = 4, to = "INVISIBLE"), @android.view.ViewDebug.IntToString(from = 8, to = "GONE")})
    public int getVisibility() {
        return this.mViewFlags & 12;
    }

    @android.view.RemotableViewMethod
    public void setVisibility(int i) {
        setFlags(i, 12);
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isEnabled() {
        return (this.mViewFlags & 32) == 0;
    }

    @android.view.RemotableViewMethod
    public void setEnabled(boolean z) {
        if (z == isEnabled()) {
            return;
        }
        setFlags(z ? 0 : 32, 32);
        refreshDrawableState();
        invalidate(true);
        if (!z) {
            cancelPendingInputEvents();
        }
        notifyViewAccessibilityStateChangedIfNeeded(4096);
    }

    @android.view.RemotableViewMethod
    public void setFocusable(boolean z) {
        setFocusable(z ? 1 : 0);
    }

    @android.view.RemotableViewMethod
    public void setFocusable(int i) {
        if ((i & 17) == 0) {
            setFlags(0, 262144);
        }
        setFlags(i, 17);
    }

    @android.view.RemotableViewMethod
    public void setFocusableInTouchMode(boolean z) {
        setFlags(z ? 262144 : 0, 262144);
        if (z) {
            setFlags(1, 17);
        }
    }

    public void setAutofillHints(java.lang.String... strArr) {
        if (strArr == null || strArr.length == 0) {
            this.mAutofillHints = null;
        } else {
            this.mAutofillHints = strArr;
        }
        if (android.view.flags.Flags.sensitiveContentAppProtection() && getContentSensitivity() == 0) {
            updateSensitiveViewsCountIfNeeded(isAggregatedVisible());
        }
    }

    public void setAutofilled(boolean z, boolean z2) {
        if (z != isAutofilled()) {
            if (z) {
                this.mPrivateFlags3 |= 65536;
            } else {
                this.mPrivateFlags3 &= -65537;
            }
            if (z2) {
                this.mPrivateFlags4 |= 512;
            } else {
                this.mPrivateFlags4 &= -513;
            }
            invalidate();
        }
    }

    public void setSoundEffectsEnabled(boolean z) {
        setFlags(z ? 134217728 : 0, 134217728);
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isSoundEffectsEnabled() {
        return 134217728 == (this.mViewFlags & 134217728);
    }

    public void setHapticFeedbackEnabled(boolean z) {
        setFlags(z ? 268435456 : 0, 268435456);
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        return 268435456 == (this.mViewFlags & 268435456);
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "LTR"), @android.view.ViewDebug.IntToString(from = 1, to = "RTL"), @android.view.ViewDebug.IntToString(from = 2, to = "INHERIT"), @android.view.ViewDebug.IntToString(from = 3, to = "LOCALE")})
    public int getRawLayoutDirection() {
        return (this.mPrivateFlags2 & 12) >> 2;
    }

    @android.view.RemotableViewMethod
    public void setLayoutDirection(int i) {
        if (getRawLayoutDirection() != i) {
            this.mPrivateFlags2 &= -13;
            resetRtlProperties();
            this.mPrivateFlags2 = ((i << 2) & 12) | this.mPrivateFlags2;
            resolveRtlPropertiesIfNeeded();
            requestLayout();
            invalidate(true);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "RESOLVED_DIRECTION_LTR"), @android.view.ViewDebug.IntToString(from = 1, to = "RESOLVED_DIRECTION_RTL")})
    public int getLayoutDirection() {
        if (getContext().getApplicationInfo().targetSdkVersion >= 17) {
            return (this.mPrivateFlags2 & 16) == 16 ? 1 : 0;
        }
        this.mPrivateFlags2 |= 32;
        return 0;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public boolean isLayoutRtl() {
        return getLayoutDirection() == 1;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public boolean hasTransientState() {
        return (this.mPrivateFlags2 & Integer.MIN_VALUE) == Integer.MIN_VALUE;
    }

    public void setHasTransientState(boolean z) {
        boolean hasTransientState = hasTransientState();
        this.mTransientStateCount = z ? this.mTransientStateCount + 1 : this.mTransientStateCount - 1;
        if (this.mTransientStateCount < 0) {
            this.mTransientStateCount = 0;
            android.util.Log.e(VIEW_LOG_TAG, "hasTransientState decremented below 0: unmatched pair of setHasTransientState calls");
            return;
        }
        if ((z && this.mTransientStateCount == 1) || (!z && this.mTransientStateCount == 0)) {
            this.mPrivateFlags2 = (this.mPrivateFlags2 & Integer.MAX_VALUE) | (z ? Integer.MIN_VALUE : 0);
            boolean hasTransientState2 = hasTransientState();
            if (this.mParent != null && hasTransientState2 != hasTransientState) {
                try {
                    this.mParent.childHasTransientStateChanged(this, hasTransientState2);
                } catch (java.lang.AbstractMethodError e) {
                    android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    public void setHasTranslationTransientState(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 16384;
        } else {
            this.mPrivateFlags4 &= -16385;
        }
    }

    public boolean hasTranslationTransientState() {
        return (this.mPrivateFlags4 & 16384) == 16384;
    }

    public void clearTranslationState() {
        if (this.mViewTranslationCallback != null) {
            this.mViewTranslationCallback.onClearTranslation(this);
        }
        clearViewTranslationResponse();
        if (hasTranslationTransientState()) {
            setHasTransientState(false);
            setHasTranslationTransientState(false);
        }
    }

    public boolean isAttachedToWindow() {
        return this.mAttachInfo != null;
    }

    public boolean isLaidOut() {
        return (this.mPrivateFlags3 & 4) == 4;
    }

    boolean isLayoutValid() {
        return isLaidOut() && (this.mPrivateFlags & 4096) == 0;
    }

    public void setWillNotDraw(boolean z) {
        setFlags(z ? 128 : 0, 128);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean willNotDraw() {
        return (this.mViewFlags & 128) == 128;
    }

    @java.lang.Deprecated
    public void setWillNotCacheDrawing(boolean z) {
        setFlags(z ? 131072 : 0, 131072);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    @java.lang.Deprecated
    public boolean willNotCacheDrawing() {
        return (this.mViewFlags & 131072) == 131072;
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isClickable() {
        return (this.mViewFlags & 16384) == 16384;
    }

    public void setClickable(boolean z) {
        setFlags(z ? 16384 : 0, 16384);
    }

    public void setAllowClickWhenDisabled(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 4096;
        } else {
            this.mPrivateFlags4 &= -4097;
        }
    }

    public boolean isLongClickable() {
        return (this.mViewFlags & 2097152) == 2097152;
    }

    public void setLongClickable(boolean z) {
        setFlags(z ? 2097152 : 0, 2097152);
    }

    public boolean isContextClickable() {
        return (this.mViewFlags & 8388608) == 8388608;
    }

    public void setContextClickable(boolean z) {
        setFlags(z ? 8388608 : 0, 8388608);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPressed(boolean z, float f, float f2) {
        if (z) {
            drawableHotspotChanged(f, f2);
        }
        setPressed(z);
    }

    public void setPressed(boolean z) {
        boolean z2 = z != ((this.mPrivateFlags & 16384) == 16384);
        if (z) {
            this.mPrivateFlags |= 16384;
        } else {
            this.mPrivateFlags &= -16385;
        }
        if (z2) {
            refreshDrawableState();
        }
        dispatchSetPressed(z);
    }

    protected void dispatchSetPressed(boolean z) {
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isPressed() {
        return (this.mPrivateFlags & 16384) == 16384;
    }

    public boolean isAssistBlocked() {
        return (this.mPrivateFlags3 & 16384) != 0;
    }

    public void setAssistBlocked(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 16384;
        } else {
            this.mPrivateFlags3 &= -16385;
        }
    }

    public boolean isSaveEnabled() {
        return (this.mViewFlags & 65536) != 65536;
    }

    public void setSaveEnabled(boolean z) {
        setFlags(z ? 0 : 65536, 65536);
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        return (this.mViewFlags & 1024) != 0;
    }

    public void setFilterTouchesWhenObscured(boolean z) {
        setFlags(z ? 1024 : 0, 1024);
        calculateAccessibilityDataSensitive();
    }

    public boolean isSaveFromParentEnabled() {
        return (this.mViewFlags & 536870912) != 536870912;
    }

    public void setSaveFromParentEnabled(boolean z) {
        setFlags(z ? 0 : 536870912, 536870912);
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusable() {
        return 1 == (this.mViewFlags & 1);
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "NOT_FOCUSABLE"), @android.view.ViewDebug.IntToString(from = 1, to = "FOCUSABLE"), @android.view.ViewDebug.IntToString(from = 16, to = "FOCUSABLE_AUTO")})
    public int getFocusable() {
        if ((this.mViewFlags & 16) > 0) {
            return 16;
        }
        return this.mViewFlags & 1;
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusableInTouchMode() {
        return 262144 == (this.mViewFlags & 262144);
    }

    public boolean isScreenReaderFocusable() {
        return (this.mPrivateFlags3 & 268435456) != 0;
    }

    public void setScreenReaderFocusable(boolean z) {
        updatePflags3AndNotifyA11yIfChanged(268435456, z);
    }

    public boolean isAccessibilityHeading() {
        return (this.mPrivateFlags3 & Integer.MIN_VALUE) != 0;
    }

    public void setAccessibilityHeading(boolean z) {
        updatePflags3AndNotifyA11yIfChanged(Integer.MIN_VALUE, z);
    }

    private void updatePflags3AndNotifyA11yIfChanged(int i, boolean z) {
        int i2;
        int i3 = this.mPrivateFlags3;
        if (z) {
            i2 = i | i3;
        } else {
            i2 = (~i) & i3;
        }
        if (i2 != this.mPrivateFlags3) {
            this.mPrivateFlags3 = i2;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public android.view.View focusSearch(int i) {
        if (this.mParent != null) {
            return this.mParent.focusSearch(this, i);
        }
        return null;
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public final boolean isKeyboardNavigationCluster() {
        return (this.mPrivateFlags3 & 32768) != 0;
    }

    android.view.View findKeyboardNavigationCluster() {
        if (this.mParent instanceof android.view.View) {
            android.view.View findKeyboardNavigationCluster = ((android.view.View) this.mParent).findKeyboardNavigationCluster();
            if (findKeyboardNavigationCluster != null) {
                return findKeyboardNavigationCluster;
            }
            if (isKeyboardNavigationCluster()) {
                return this;
            }
            return null;
        }
        return null;
    }

    public void setKeyboardNavigationCluster(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 32768;
        } else {
            this.mPrivateFlags3 &= -32769;
        }
    }

    public final void setFocusedInCluster() {
        setFocusedInCluster(findKeyboardNavigationCluster());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.view.ViewParent] */
    /* JADX WARN: Type inference failed for: r1v3 */
    private void setFocusedInCluster(android.view.View view) {
        if (this instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) this).mFocusedInCluster = null;
        }
        if (view == this) {
            return;
        }
        android.view.View view2 = this;
        for (android.view.ViewGroup viewGroup = this.mParent; viewGroup instanceof android.view.ViewGroup; viewGroup = viewGroup.getParent()) {
            viewGroup.mFocusedInCluster = view2;
            if (viewGroup != view) {
                view2 = viewGroup;
            } else {
                return;
            }
        }
    }

    private void updateFocusedInCluster(android.view.View view, int i) {
        android.view.View findKeyboardNavigationCluster;
        if (view != null && (findKeyboardNavigationCluster = view.findKeyboardNavigationCluster()) != findKeyboardNavigationCluster()) {
            view.setFocusedInCluster(findKeyboardNavigationCluster);
            if (!(view.mParent instanceof android.view.ViewGroup)) {
                return;
            }
            if (i == 2 || i == 1) {
                ((android.view.ViewGroup) view.mParent).clearFocusedInCluster(view);
            } else if ((view instanceof android.view.ViewGroup) && ((android.view.ViewGroup) view).getDescendantFocusability() == 262144 && android.view.ViewRootImpl.isViewDescendantOf(this, view)) {
                ((android.view.ViewGroup) view.mParent).clearFocusedInCluster(view);
            }
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public final boolean isFocusedByDefault() {
        return (this.mPrivateFlags3 & 262144) != 0;
    }

    @android.view.RemotableViewMethod
    public void setFocusedByDefault(boolean z) {
        if (z == ((this.mPrivateFlags3 & 262144) != 0)) {
            return;
        }
        if (z) {
            this.mPrivateFlags3 |= 262144;
        } else {
            this.mPrivateFlags3 &= -262145;
        }
        if (this.mParent instanceof android.view.ViewGroup) {
            if (z) {
                ((android.view.ViewGroup) this.mParent).setDefaultFocus(this);
            } else {
                ((android.view.ViewGroup) this.mParent).clearDefaultFocus(this);
            }
        }
    }

    boolean hasDefaultFocus() {
        return isFocusedByDefault();
    }

    public android.view.View keyboardNavigationClusterSearch(android.view.View view, int i) {
        if (isKeyboardNavigationCluster()) {
            view = this;
        }
        if (isRootNamespace()) {
            return android.view.FocusFinder.getInstance().findNextKeyboardNavigationCluster(this, view, i);
        }
        if (this.mParent != null) {
            return this.mParent.keyboardNavigationClusterSearch(view, i);
        }
        return null;
    }

    public boolean dispatchUnhandledMove(android.view.View view, int i) {
        return false;
    }

    public void setDefaultFocusHighlightEnabled(boolean z) {
        this.mDefaultFocusHighlightEnabled = z;
    }

    @android.view.ViewDebug.ExportedProperty(category = "focus")
    public final boolean getDefaultFocusHighlightEnabled() {
        return this.mDefaultFocusHighlightEnabled;
    }

    android.view.View findUserSetNextFocus(final android.view.View view, int i) {
        switch (i) {
            case 1:
                if (this.mID != -1) {
                    break;
                }
                break;
            case 2:
                if (this.mNextFocusForwardId != -1) {
                    break;
                }
                break;
            case 17:
                if (this.mNextFocusLeftId != -1) {
                    break;
                }
                break;
            case 33:
                if (this.mNextFocusUpId != -1) {
                    break;
                }
                break;
            case 66:
                if (this.mNextFocusRightId != -1) {
                    break;
                }
                break;
            case 130:
                if (this.mNextFocusDownId != -1) {
                    break;
                }
                break;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$findUserSetNextFocus$3(android.view.View view, android.view.View view2, android.view.View view3) {
        return findViewInsideOutShouldExist(view, view3, view3.mNextFocusForwardId) == view2;
    }

    android.view.View findUserSetNextKeyboardNavigationCluster(android.view.View view, int i) {
        switch (i) {
            case 1:
                if (this.mID != -1) {
                    final int i2 = this.mID;
                    break;
                }
                break;
            case 2:
                if (this.mNextClusterForwardId != -1) {
                    break;
                }
                break;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$findUserSetNextKeyboardNavigationCluster$4(int i, android.view.View view) {
        return view.mNextClusterForwardId == i;
    }

    private android.view.View findViewInsideOutShouldExist(android.view.View view, int i) {
        return findViewInsideOutShouldExist(view, this, i);
    }

    private android.view.View findViewInsideOutShouldExist(android.view.View view, android.view.View view2, int i) {
        if (this.mMatchIdPredicate == null) {
            this.mMatchIdPredicate = new android.view.View.MatchIdPredicate();
        }
        this.mMatchIdPredicate.mId = i;
        android.view.View findViewByPredicateInsideOut = view.findViewByPredicateInsideOut(view2, this.mMatchIdPredicate);
        if (findViewByPredicateInsideOut == null) {
            android.util.Log.w(VIEW_LOG_TAG, "couldn't find view with id " + i);
        }
        return findViewByPredicateInsideOut;
    }

    public java.util.ArrayList<android.view.View> getFocusables(int i) {
        java.util.ArrayList<android.view.View> arrayList = new java.util.ArrayList<>(24);
        addFocusables(arrayList, i);
        return arrayList;
    }

    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i) {
        addFocusables(arrayList, i, isInTouchMode() ? 1 : 0);
    }

    public void addFocusables(java.util.ArrayList<android.view.View> arrayList, int i, int i2) {
        if (arrayList == null || !canTakeFocus()) {
            return;
        }
        if ((i2 & 1) == 1 && !isFocusableInTouchMode()) {
            return;
        }
        arrayList.add(this);
    }

    public void addKeyboardNavigationClusters(java.util.Collection<android.view.View> collection, int i) {
        if (!isKeyboardNavigationCluster() || !hasFocusable()) {
            return;
        }
        collection.add(this);
    }

    public void findViewsWithText(java.util.ArrayList<android.view.View> arrayList, java.lang.CharSequence charSequence, int i) {
        if (getAccessibilityNodeProvider() != null) {
            if ((i & 4) != 0) {
                arrayList.add(this);
            }
        } else if ((i & 2) != 0 && charSequence != null && charSequence.length() > 0 && this.mContentDescription != null && this.mContentDescription.length() > 0) {
            if (this.mContentDescription.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                arrayList.add(this);
            }
        }
    }

    public java.util.ArrayList<android.view.View> getTouchables() {
        java.util.ArrayList<android.view.View> arrayList = new java.util.ArrayList<>();
        addTouchables(arrayList);
        return arrayList;
    }

    public void addTouchables(java.util.ArrayList<android.view.View> arrayList) {
        int i = this.mViewFlags;
        if (((i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608) && (i & 32) == 0) {
            arrayList.add(this);
        }
    }

    public boolean isAccessibilityFocused() {
        return (this.mPrivateFlags2 & 67108864) != 0;
    }

    public boolean requestAccessibilityFocus() {
        android.view.accessibility.AccessibilityManager accessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(this.mContext);
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled() || (this.mViewFlags & 12) != 0 || (this.mPrivateFlags2 & 67108864) != 0) {
            return false;
        }
        this.mPrivateFlags2 |= 67108864;
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.setAccessibilityFocus(this, null);
        }
        invalidate();
        sendAccessibilityEvent(32768);
        return true;
    }

    public void clearAccessibilityFocus() {
        android.view.View accessibilityFocusedHost;
        clearAccessibilityFocusNoCallbacks(0);
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null && (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) != null && android.view.ViewRootImpl.isViewDescendantOf(accessibilityFocusedHost, this)) {
            viewRootImpl.setAccessibilityFocus(null, null);
        }
    }

    private void sendAccessibilityHoverEvent(int i) {
        android.view.View view = this;
        while (!view.includeForAccessibility(false)) {
            java.lang.Object parent = view.getParent();
            if (parent instanceof android.view.View) {
                view = (android.view.View) parent;
            } else {
                return;
            }
        }
        view.sendAccessibilityEvent(i);
    }

    void clearAccessibilityFocusNoCallbacks(int i) {
        if ((this.mPrivateFlags2 & 67108864) != 0) {
            this.mPrivateFlags2 &= -67108865;
            invalidate();
            if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(65536);
                obtain.setAction(i);
                if (this.mAccessibilityDelegate != null) {
                    this.mAccessibilityDelegate.sendAccessibilityEventUnchecked(this, obtain);
                } else {
                    sendAccessibilityEventUnchecked(obtain);
                }
            }
            updatePreferKeepClearForFocus();
        }
    }

    public final boolean requestFocus() {
        return requestFocus(130);
    }

    public boolean restoreFocusInCluster(int i) {
        if (restoreDefaultFocus()) {
            return true;
        }
        return requestFocus(i);
    }

    public boolean restoreFocusNotInCluster() {
        return requestFocus(130);
    }

    public boolean restoreDefaultFocus() {
        return requestFocus(130);
    }

    public final boolean requestFocus(int i) {
        return requestFocus(i, null);
    }

    public boolean requestFocus(int i, android.graphics.Rect rect) {
        return requestFocusNoSearch(i, rect);
    }

    private boolean requestFocusNoSearch(int i, android.graphics.Rect rect) {
        if (!canTakeFocus()) {
            return false;
        }
        if ((isInTouchMode() && 262144 != (this.mViewFlags & 262144)) || hasAncestorThatBlocksDescendantFocus()) {
            return false;
        }
        if (!isLayoutValid()) {
            this.mPrivateFlags |= 1;
        } else {
            clearParentsWantFocus();
        }
        handleFocusGainInternal(i, rect);
        return true;
    }

    void clearParentsWantFocus() {
        if (this.mParent instanceof android.view.View) {
            ((android.view.View) this.mParent).mPrivateFlags &= -2;
            ((android.view.View) this.mParent).clearParentsWantFocus();
        }
    }

    public final boolean requestFocusFromTouch() {
        android.view.ViewRootImpl viewRootImpl;
        if (isInTouchMode() && (viewRootImpl = getViewRootImpl()) != null) {
            viewRootImpl.ensureTouchMode(false);
        }
        return requestFocus(130);
    }

    private boolean hasAncestorThatBlocksDescendantFocus() {
        boolean isFocusableInTouchMode = isFocusableInTouchMode();
        android.view.ViewParent viewParent = this.mParent;
        while (viewParent instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) viewParent;
            if (viewGroup.getDescendantFocusability() != 393216) {
                if (!isFocusableInTouchMode && viewGroup.shouldBlockFocusForTouchscreen()) {
                    return true;
                }
                viewParent = viewGroup.getParent();
            } else {
                return true;
            }
        }
        return false;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.content.Context.ACCESSIBILITY_SERVICE, mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "auto"), @android.view.ViewDebug.IntToString(from = 1, to = android.media.MediaMetrics.Value.YES), @android.view.ViewDebug.IntToString(from = 2, to = android.media.MediaMetrics.Value.NO), @android.view.ViewDebug.IntToString(from = 4, to = "noHideDescendants")})
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK) >> 20;
    }

    public void setAccessibilityLiveRegion(int i) {
        if (i != getAccessibilityLiveRegion()) {
            this.mPrivateFlags2 &= -25165825;
            this.mPrivateFlags2 = ((i << 23) & 25165824) | this.mPrivateFlags2;
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public int getAccessibilityLiveRegion() {
        return (this.mPrivateFlags2 & 25165824) >> 23;
    }

    public void setImportantForAccessibility(int i) {
        android.view.View findAccessibilityFocusHost;
        int importantForAccessibility = getImportantForAccessibility();
        if (i != importantForAccessibility) {
            boolean z = i == 4;
            if ((i == 2 || z) && (findAccessibilityFocusHost = findAccessibilityFocusHost(z)) != null) {
                findAccessibilityFocusHost.clearAccessibilityFocus();
            }
            boolean z2 = importantForAccessibility == 0 || i == 0;
            boolean z3 = z2 && includeForAccessibility(false);
            this.mPrivateFlags2 &= -7340033;
            this.mPrivateFlags2 = ((i << 20) & PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK) | this.mPrivateFlags2;
            if (!z2 || z3 != includeForAccessibility(false)) {
                notifySubtreeAccessibilityStateChangedIfNeeded();
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    private android.view.View findAccessibilityFocusHost(boolean z) {
        android.view.ViewRootImpl viewRootImpl;
        android.view.View accessibilityFocusedHost;
        if (isAccessibilityFocusedViewOrHost()) {
            return this;
        }
        if (z && (viewRootImpl = getViewRootImpl()) != null && (accessibilityFocusedHost = viewRootImpl.getAccessibilityFocusedHost()) != null && android.view.ViewRootImpl.isViewDescendantOf(accessibilityFocusedHost, this)) {
            return accessibilityFocusedHost;
        }
        return null;
    }

    public boolean isImportantForAccessibility() {
        int importantForAccessibility = getImportantForAccessibility();
        if (importantForAccessibility == 2 || importantForAccessibility == 4) {
            return false;
        }
        for (android.view.ViewParent viewParent = this.mParent; viewParent instanceof android.view.View; viewParent = viewParent.getParent()) {
            if (((android.view.View) viewParent).getImportantForAccessibility() == 4) {
                return false;
            }
        }
        return importantForAccessibility == 1 || isActionableForAccessibility() || hasListenersForAccessibility() || getAccessibilityNodeProvider() != null || getAccessibilityDelegate() != null || getAccessibilityLiveRegion() != 0 || isAccessibilityPane() || isAccessibilityHeading();
    }

    public android.view.ViewParent getParentForAccessibility() {
        if (this.mParent instanceof android.view.View) {
            if (((android.view.View) this.mParent).includeForAccessibility()) {
                return this.mParent;
            }
            return this.mParent.getParentForAccessibility();
        }
        return null;
    }

    android.view.View getSelfOrParentImportantForA11y() {
        if (isImportantForAccessibility()) {
            return this;
        }
        java.lang.Object parentForAccessibility = getParentForAccessibility();
        if (parentForAccessibility instanceof android.view.View) {
            return (android.view.View) parentForAccessibility;
        }
        return null;
    }

    public void addChildrenForAccessibility(java.util.ArrayList<android.view.View> arrayList) {
    }

    public boolean includeForAccessibility() {
        return includeForAccessibility(true);
    }

    public boolean includeForAccessibility(boolean z) {
        if (this.mAttachInfo == null) {
            return false;
        }
        if (z && !android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isRequestFromAccessibilityTool() && isAccessibilityDataSensitive()) {
            return false;
        }
        return (this.mAttachInfo.mAccessibilityFetchFlags & 128) != 0 || isImportantForAccessibility();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.content.Context.ACCESSIBILITY_SERVICE)
    public boolean isAccessibilityDataSensitive() {
        if (this.mInferredAccessibilityDataSensitive == 0) {
            calculateAccessibilityDataSensitive();
        }
        return this.mInferredAccessibilityDataSensitive == 1;
    }

    void calculateAccessibilityDataSensitive() {
        if (this.mExplicitAccessibilityDataSensitive != 0) {
            this.mInferredAccessibilityDataSensitive = this.mExplicitAccessibilityDataSensitive;
            return;
        }
        if (getFilterTouchesWhenObscured()) {
            this.mInferredAccessibilityDataSensitive = 1;
        } else if ((this.mParent instanceof android.view.View) && ((android.view.View) this.mParent).isAccessibilityDataSensitive()) {
            this.mInferredAccessibilityDataSensitive = 1;
        } else {
            this.mInferredAccessibilityDataSensitive = 2;
        }
    }

    public void setAccessibilityDataSensitive(int i) {
        this.mExplicitAccessibilityDataSensitive = i;
        calculateAccessibilityDataSensitive();
    }

    public boolean isActionableForAccessibility() {
        return isClickable() || isLongClickable() || isFocusable() || isContextClickable() || isScreenReaderFocusable();
    }

    private boolean hasListenersForAccessibility() {
        android.view.View.ListenerInfo listenerInfo = getListenerInfo();
        return (this.mTouchDelegate == null && listenerInfo.mOnKeyListener == null && listenerInfo.mOnTouchListener == null && listenerInfo.mOnGenericMotionListener == null && listenerInfo.mOnHoverListener == null && listenerInfo.mOnDragListener == null) ? false : true;
    }

    public void notifyViewAccessibilityStateChangedIfNeeded(int i) {
        if (!android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() || this.mAttachInfo == null) {
            return;
        }
        if (i != 1 && ((isAccessibilityPane() || (i == 32 && isAggregatedVisible())) && (isAggregatedVisible() || i == 32))) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
            onInitializeAccessibilityEvent(obtain);
            obtain.setEventType(32);
            obtain.setContentChangeTypes(i);
            obtain.setSource(this);
            onPopulateAccessibilityEvent(obtain);
            if (this.mParent != null) {
                try {
                    this.mParent.requestSendAccessibilityEvent(this, obtain);
                    return;
                } catch (java.lang.AbstractMethodError e) {
                    android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                    return;
                }
            }
            return;
        }
        if (getAccessibilityLiveRegion() != 0) {
            android.view.accessibility.AccessibilityEvent obtain2 = android.view.accessibility.AccessibilityEvent.obtain();
            obtain2.setEventType(2048);
            obtain2.setContentChangeTypes(i);
            sendAccessibilityEventUnchecked(obtain2);
            return;
        }
        if (this.mParent != null) {
            try {
                this.mParent.notifySubtreeAccessibilityStateChanged(this, this, i);
            } catch (java.lang.AbstractMethodError e2) {
                android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
            }
        }
    }

    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null && (this.mPrivateFlags2 & 134217728) == 0) {
            this.mPrivateFlags2 |= 134217728;
            if (this.mParent != null) {
                try {
                    this.mParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
                } catch (java.lang.AbstractMethodError e) {
                    android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    private void notifySubtreeAccessibilityStateChangedByParentIfNeeded() {
        android.view.View view;
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && (view = (android.view.View) getParentForAccessibility()) != null && view.isShown()) {
            view.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionVisibility(int i) {
        this.mViewFlags = i | (this.mViewFlags & (-13));
    }

    void resetSubtreeAccessibilityStateChanged() {
        this.mPrivateFlags2 &= -134217729;
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int i, android.os.Bundle bundle) {
        for (android.view.ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent.onNestedPrePerformAccessibilityAction(this, i, bundle)) {
                return true;
            }
        }
        return false;
    }

    public boolean performAccessibilityAction(int i, android.os.Bundle bundle) {
        if (this.mAccessibilityDelegate != null) {
            return this.mAccessibilityDelegate.performAccessibilityAction(this, i, bundle);
        }
        return performAccessibilityActionInternal(i, bundle);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        int i2;
        if (isNestedScrollingEnabled() && ((i == 8192 || i == 4096 || i == 16908344 || i == 16908345 || i == 16908346 || i == 16908347) && dispatchNestedPrePerformAccessibilityAction(i, bundle))) {
            return true;
        }
        switch (i) {
            case 1:
                if (!hasFocus()) {
                    getViewRootImpl().ensureTouchMode(false);
                    return requestFocus();
                }
                return false;
            case 2:
                if (hasFocus()) {
                    clearFocus();
                    return !isFocused();
                }
                return false;
            case 4:
                if (!isSelected()) {
                    setSelected(true);
                    return isSelected();
                }
                return false;
            case 8:
                if (isSelected()) {
                    setSelected(false);
                    return !isSelected();
                }
                return false;
            case 16:
                if (isClickable()) {
                    performClickInternal();
                    return true;
                }
                return false;
            case 32:
                if (isLongClickable()) {
                    performLongClick();
                    return true;
                }
                return false;
            case 64:
                if (!isAccessibilityFocused()) {
                    return requestAccessibilityFocus();
                }
                return false;
            case 128:
                if (isAccessibilityFocused()) {
                    clearAccessibilityFocus();
                    return true;
                }
                return false;
            case 256:
                if (bundle != null) {
                    return traverseAtGranularity(bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT), true, bundle.getBoolean(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN));
                }
                return false;
            case 512:
                if (bundle != null) {
                    return traverseAtGranularity(bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT), false, bundle.getBoolean(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN));
                }
                return false;
            case 131072:
                if (getIterableTextForAccessibility() == null) {
                    return false;
                }
                if (bundle != null) {
                    i2 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1);
                } else {
                    i2 = -1;
                }
                int i3 = bundle != null ? bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1) : -1;
                if ((getAccessibilitySelectionStart() != i2 || getAccessibilitySelectionEnd() != i3) && i2 == i3) {
                    setAccessibilitySelection(i2, i3);
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                    return true;
                }
                return false;
            case 16908342:
                if (this.mAttachInfo != null) {
                    android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
                    getDrawingRect(rect);
                    return requestRectangleOnScreen(rect, true);
                }
                return false;
            case 16908348:
                if (isContextClickable()) {
                    performContextClick();
                    return true;
                }
                return false;
            case 16908356:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return showLongClickTooltip(0, 0);
                }
                return false;
            case 16908357:
                if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
                    return false;
                }
                hideTooltip();
                return true;
            case 16908374:
                if (!canAcceptAccessibilityDrop()) {
                    return false;
                }
                try {
                    if (this.mAttachInfo != null && this.mAttachInfo.mSession != null) {
                        int[] iArr = new int[2];
                        getLocationInWindow(iArr);
                        return this.mAttachInfo.mSession.dropForAccessibility(this.mAttachInfo.mWindow, iArr[0] + (getWidth() / 2), iArr[1] + (getHeight() / 2));
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(VIEW_LOG_TAG, "Unable to drop for accessibility", e);
                }
                return false;
            case 16908375:
                if (!startedSystemDragForAccessibility() || this.mAttachInfo == null || this.mAttachInfo.mDragToken == null) {
                    return false;
                }
                cancelDragAndDrop();
                return true;
            default:
                return false;
        }
    }

    private boolean canAcceptAccessibilityDrop() {
        android.view.View.ListenerInfo listenerInfo;
        if (canAcceptDrag() && (listenerInfo = this.mListenerInfo) != null) {
            return (listenerInfo.mOnDragListener == null && listenerInfo.mOnReceiveContentListener == null) ? false : true;
        }
        return false;
    }

    private boolean traverseAtGranularity(int i, boolean z, boolean z2) {
        android.view.AccessibilityIterators.TextSegmentIterator iteratorForGranularity;
        int i2;
        int i3;
        java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (iterableTextForAccessibility == null || iterableTextForAccessibility.length() == 0 || (iteratorForGranularity = getIteratorForGranularity(i)) == null) {
            return false;
        }
        int accessibilitySelectionEnd = getAccessibilitySelectionEnd();
        if (accessibilitySelectionEnd == -1) {
            accessibilitySelectionEnd = z ? 0 : iterableTextForAccessibility.length();
        }
        int[] following = z ? iteratorForGranularity.following(accessibilitySelectionEnd) : iteratorForGranularity.preceding(accessibilitySelectionEnd);
        if (following == null) {
            return false;
        }
        int i4 = following[0];
        int i5 = following[1];
        if (z2 && isAccessibilitySelectionExtendable()) {
            prepareForExtendedAccessibilitySelection();
            i2 = getAccessibilitySelectionStart();
            if (i2 == -1) {
                i2 = z ? i4 : i5;
            }
            i3 = z ? i5 : i4;
        } else {
            i2 = z ? i5 : i4;
            i3 = i2;
        }
        setAccessibilitySelection(i2, i3);
        sendViewTextTraversedAtGranularityEvent(z ? 256 : 512, i, i4, i5);
        return true;
    }

    public java.lang.CharSequence getIterableTextForAccessibility() {
        return getContentDescription();
    }

    public boolean isAccessibilitySelectionExtendable() {
        return false;
    }

    public void prepareForExtendedAccessibilitySelection() {
    }

    public int getAccessibilitySelectionStart() {
        return this.mAccessibilityCursorPosition;
    }

    public int getAccessibilitySelectionEnd() {
        return getAccessibilitySelectionStart();
    }

    public void setAccessibilitySelection(int i, int i2) {
        if (i == i2 && i2 == this.mAccessibilityCursorPosition) {
            return;
        }
        if (i >= 0 && i == i2 && i2 <= getIterableTextForAccessibility().length()) {
            this.mAccessibilityCursorPosition = i;
        } else {
            this.mAccessibilityCursorPosition = -1;
        }
        sendAccessibilityEvent(8192);
    }

    private void sendViewTextTraversedAtGranularityEvent(int i, int i2, int i3, int i4) {
        if (this.mParent == null) {
            return;
        }
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(131072);
        onInitializeAccessibilityEvent(obtain);
        onPopulateAccessibilityEvent(obtain);
        obtain.setFromIndex(i3);
        obtain.setToIndex(i4);
        obtain.setAction(i);
        obtain.setMovementGranularity(i2);
        this.mParent.requestSendAccessibilityEvent(this, obtain);
    }

    public android.view.AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int i) {
        switch (i) {
            case 1:
                java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
                if (iterableTextForAccessibility != null && iterableTextForAccessibility.length() > 0) {
                    android.view.AccessibilityIterators.CharacterTextSegmentIterator characterTextSegmentIterator = android.view.AccessibilityIterators.CharacterTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                    characterTextSegmentIterator.initialize(iterableTextForAccessibility.toString());
                    return characterTextSegmentIterator;
                }
                return null;
            case 2:
                java.lang.CharSequence iterableTextForAccessibility2 = getIterableTextForAccessibility();
                if (iterableTextForAccessibility2 != null && iterableTextForAccessibility2.length() > 0) {
                    android.view.AccessibilityIterators.WordTextSegmentIterator wordTextSegmentIterator = android.view.AccessibilityIterators.WordTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                    wordTextSegmentIterator.initialize(iterableTextForAccessibility2.toString());
                    return wordTextSegmentIterator;
                }
                return null;
            case 8:
                java.lang.CharSequence iterableTextForAccessibility3 = getIterableTextForAccessibility();
                if (iterableTextForAccessibility3 != null && iterableTextForAccessibility3.length() > 0) {
                    android.view.AccessibilityIterators.ParagraphTextSegmentIterator paragraphTextSegmentIterator = android.view.AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
                    paragraphTextSegmentIterator.initialize(iterableTextForAccessibility3.toString());
                    return paragraphTextSegmentIterator;
                }
                return null;
            default:
                return null;
        }
    }

    public final boolean isTemporarilyDetached() {
        return (this.mPrivateFlags3 & 33554432) != 0;
    }

    public void dispatchStartTemporaryDetach() {
        this.mPrivateFlags3 |= 33554432;
        notifyEnterOrExitForAutoFillIfNeeded(false);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(false);
        onStartTemporaryDetach();
    }

    public void onStartTemporaryDetach() {
        removeUnsetPressCallback();
        this.mPrivateFlags |= 67108864;
    }

    public void dispatchFinishTemporaryDetach() {
        this.mPrivateFlags3 &= -33554433;
        onFinishTemporaryDetach();
        if (hasWindowFocus() && hasFocus()) {
            notifyFocusChangeToImeFocusController(true);
        }
        notifyEnterOrExitForAutoFillIfNeeded(true);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
    }

    public void onFinishTemporaryDetach() {
    }

    public android.view.KeyEvent.DispatcherState getKeyDispatcherState() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mKeyDispatchState;
        }
        return null;
    }

    public boolean dispatchKeyEventPreIme(android.view.KeyEvent keyEvent) {
        return onKeyPreIme(keyEvent.getKeyCode(), keyEvent);
    }

    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onKeyEvent(keyEvent, 0);
        }
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnKeyListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnKeyListener.onKey(this, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        if (keyEvent.dispatch(this, this.mAttachInfo != null ? this.mAttachInfo.mKeyDispatchState : null, this)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(keyEvent, 0);
        }
        return false;
    }

    public boolean dispatchKeyShortcutEvent(android.view.KeyEvent keyEvent) {
        return onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    public boolean dispatchTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        if (motionEvent.isTargetAccessibilityFocus()) {
            if (!isAccessibilityFocusedViewOrHost()) {
                return false;
            }
            motionEvent.setTargetAccessibilityFocus(false);
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTouchEvent(motionEvent, 0);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            stopNestedScroll();
        }
        if (!onFilterTouchEventForSecurity(motionEvent)) {
            z = false;
        } else {
            z = performOnTouchCallback(motionEvent);
        }
        if (!z && this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        if (actionMasked == 1 || actionMasked == 3 || (actionMasked == 0 && !z)) {
            stopNestedScroll();
        }
        return z;
    }

    private boolean performOnTouchCallback(android.view.MotionEvent motionEvent) {
        boolean z;
        if ((this.mViewFlags & 32) == 0 && handleScrollBarDragging(motionEvent)) {
            z = true;
        } else {
            z = false;
        }
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnTouchListener != null && (this.mViewFlags & 32) == 0) {
            try {
                android.os.Trace.traceBegin(8L, "View.onTouchListener#onTouch");
                z = listenerInfo.mOnTouchListener.onTouch(this, motionEvent);
            } finally {
            }
        }
        if (z) {
            return true;
        }
        try {
            android.os.Trace.traceBegin(8L, "View#onTouchEvent");
            return onTouchEvent(motionEvent);
        } finally {
        }
    }

    boolean isAccessibilityFocusedViewOrHost() {
        return isAccessibilityFocused() || (getViewRootImpl() != null && getViewRootImpl().getAccessibilityFocusedHost() == this);
    }

    protected boolean canReceivePointerEvents() {
        return (this.mViewFlags & 12) == 0 || getAnimation() != null;
    }

    public boolean onFilterTouchEventForSecurity(android.view.MotionEvent motionEvent) {
        return (this.mViewFlags & 1024) == 0 || (motionEvent.getFlags() & 1) == 0;
    }

    public boolean dispatchTrackballEvent(android.view.MotionEvent motionEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onTrackballEvent(motionEvent, 0);
        }
        return onTrackballEvent(motionEvent);
    }

    public boolean dispatchCapturedPointerEvent(android.view.MotionEvent motionEvent) {
        if (!hasPointerCapture()) {
            return false;
        }
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnCapturedPointerListener != null && listenerInfo.mOnCapturedPointerListener.onCapturedPointer(this, motionEvent)) {
            return true;
        }
        return onCapturedPointerEvent(motionEvent);
    }

    public boolean dispatchGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        if ((motionEvent.getSource() & 2) != 0) {
            int action = motionEvent.getAction();
            if (action == 9 || action == 7 || action == 10) {
                if (dispatchHoverEvent(motionEvent)) {
                    return true;
                }
            } else if (dispatchGenericPointerEvent(motionEvent)) {
                return true;
            }
        } else if (dispatchGenericFocusedEvent(motionEvent)) {
            return true;
        }
        if (dispatchGenericMotionEventInternal(motionEvent)) {
            return true;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    private boolean dispatchGenericMotionEventInternal(android.view.MotionEvent motionEvent) {
        boolean isFromSource = motionEvent.isFromSource(4194304);
        if (isFromSource && (this.mPrivateFlags4 & 1048576) == 0) {
            if (android.view.ViewConfiguration.get(this.mContext).isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
                this.mPrivateFlags4 |= 2097152;
            }
            this.mPrivateFlags4 |= 1048576;
        }
        boolean z = isFromSource && (this.mPrivateFlags4 & 2097152) != 0;
        if (z) {
            this.mPrivateFlags4 &= -4194305;
            this.mPrivateFlags4 |= 8388608;
        }
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnGenericMotionListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnGenericMotionListener.onGenericMotion(this, motionEvent)) {
            return true;
        }
        boolean onGenericMotionEvent = onGenericMotionEvent(motionEvent);
        if (z) {
            if ((4194304 & this.mPrivateFlags4) != 0) {
                doRotaryProgressForScrollHaptics(motionEvent);
            } else {
                doRotaryLimitForScrollHaptics(motionEvent);
            }
        }
        if (onGenericMotionEvent) {
            return true;
        }
        int actionButton = motionEvent.getActionButton();
        switch (motionEvent.getActionMasked()) {
            case 11:
                if (isContextClickable() && !this.mInContextButtonPress && !this.mHasPerformedLongPress && ((actionButton == 32 || actionButton == 2) && performContextClick(motionEvent.getX(), motionEvent.getY()))) {
                    this.mInContextButtonPress = true;
                    setPressed(true, motionEvent.getX(), motionEvent.getY());
                    removeTapCallback();
                    removeLongPressCallback();
                    return true;
                }
                break;
            case 12:
                if (this.mInContextButtonPress && (actionButton == 32 || actionButton == 2)) {
                    this.mInContextButtonPress = false;
                    this.mIgnoreNextUpEvent = true;
                    break;
                }
                break;
        }
        if (this.mInputEventConsistencyVerifier != null) {
            this.mInputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    protected boolean dispatchHoverEvent(android.view.MotionEvent motionEvent) {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnHoverListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnHoverListener.onHover(this, motionEvent)) {
            return true;
        }
        return onHoverEvent(motionEvent);
    }

    protected boolean hasHoveredChild() {
        return false;
    }

    protected boolean pointInHoveredChild(android.view.MotionEvent motionEvent) {
        return false;
    }

    protected boolean dispatchGenericPointerEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    protected boolean dispatchGenericFocusedEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public final boolean dispatchPointerEvent(android.view.MotionEvent motionEvent) {
        if (motionEvent.isTouchEvent()) {
            return dispatchTouchEvent(motionEvent);
        }
        return dispatchGenericMotionEvent(motionEvent);
    }

    public void dispatchWindowFocusChanged(boolean z) {
        onWindowFocusChanged(z);
    }

    public void onWindowFocusChanged(boolean z) {
        if (!z) {
            if (isPressed()) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if ((this.mPrivateFlags & 2) != 0) {
                notifyFocusChangeToImeFocusController(false);
            }
            removeLongPressCallback();
            removeTapCallback();
            onFocusLost();
        } else if ((this.mPrivateFlags & 2) != 0) {
            notifyFocusChangeToImeFocusController(true);
        }
        refreshDrawableState();
    }

    public boolean hasWindowFocus() {
        return this.mAttachInfo != null && this.mAttachInfo.mHasWindowFocus;
    }

    public boolean hasImeFocus() {
        return getViewRootImpl() != null && getViewRootImpl().getImeFocusController().hasImeFocus();
    }

    protected void dispatchVisibilityChanged(android.view.View view, int i) {
        onVisibilityChanged(view, i);
    }

    protected void onVisibilityChanged(android.view.View view, int i) {
    }

    public void dispatchDisplayHint(int i) {
        onDisplayHint(i);
    }

    protected void onDisplayHint(int i) {
    }

    public void dispatchWindowVisibilityChanged(int i) {
        onWindowVisibilityChanged(i);
    }

    protected void onWindowVisibilityChanged(int i) {
        if (i == 0) {
            initialAwakenScrollBars();
        }
    }

    public boolean isAggregatedVisible() {
        return (this.mPrivateFlags3 & 536870912) != 0;
    }

    boolean dispatchVisibilityAggregated(boolean z) {
        boolean z2 = getVisibility() == 0;
        if (z2 || !z) {
            onVisibilityAggregated(z);
        }
        return z2 && z;
    }

    public void onVisibilityAggregated(boolean z) {
        int i;
        boolean isAggregatedVisible = isAggregatedVisible();
        this.mPrivateFlags3 = z ? this.mPrivateFlags3 | 536870912 : this.mPrivateFlags3 & (-536870913);
        if (z && this.mAttachInfo != null) {
            initialAwakenScrollBars();
        }
        android.graphics.drawable.Drawable drawable = this.mBackground;
        if (drawable != null && z != drawable.isVisible()) {
            drawable.setVisible(z, false);
        }
        android.graphics.drawable.Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null && z != drawable2.isVisible()) {
            drawable2.setVisible(z, false);
        }
        android.graphics.drawable.Drawable drawable3 = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (drawable3 != null && z != drawable3.isVisible()) {
            drawable3.setVisible(z, false);
        }
        notifyAutofillManagerViewVisibilityChanged(z);
        if (z != isAggregatedVisible) {
            if (isAccessibilityPane()) {
                if (z) {
                    i = 16;
                } else {
                    i = 32;
                }
                notifyViewAccessibilityStateChangedIfNeeded(i);
            }
            notifyAppearedOrDisappearedForContentCaptureIfNeeded(z);
            updateSensitiveViewsCountIfNeeded(z);
            if (!getSystemGestureExclusionRects().isEmpty()) {
                postUpdate(new android.view.View$$ExternalSyntheticLambda4(this));
            }
            if (!collectPreferKeepClearRects().isEmpty()) {
                postUpdate(new android.view.View$$ExternalSyntheticLambda5(this));
            }
        }
    }

    private void notifyAutofillManagerViewVisibilityChanged(boolean z) {
        android.view.autofill.AutofillManager autofillManager;
        if (isAutofillable() && (autofillManager = getAutofillManager()) != null && getAutofillViewId() > 1073741823) {
            if (this.mVisibilityChangeForAutofillHandler != null) {
                this.mVisibilityChangeForAutofillHandler.removeMessages(0);
            }
            if (z) {
                autofillManager.notifyViewVisibilityChanged(this, true);
                return;
            }
            if (this.mVisibilityChangeForAutofillHandler == null) {
                this.mVisibilityChangeForAutofillHandler = new android.view.View.VisibilityChangeForAutofillHandler(autofillManager, this);
            }
            this.mVisibilityChangeForAutofillHandler.obtainMessage(0, this).sendToTarget();
        }
    }

    public int getWindowVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowVisibility;
        }
        return 8;
    }

    public void getWindowVisibleDisplayFrame(android.graphics.Rect rect) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.getWindowVisibleDisplayFrame(rect);
            return;
        }
        android.view.WindowMetrics maximumWindowMetrics = ((android.view.WindowManager) this.mContext.getSystemService(android.view.WindowManager.class)).getMaximumWindowMetrics();
        android.graphics.Insets insets = maximumWindowMetrics.getWindowInsets().getInsets(android.view.WindowInsets.Type.navigationBars() | android.view.WindowInsets.Type.displayCutout());
        rect.set(maximumWindowMetrics.getBounds());
        rect.inset(insets);
        rect.offsetTo(0, 0);
    }

    public void getWindowDisplayFrame(android.graphics.Rect rect) {
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.getDisplayFrame(rect);
        } else {
            android.hardware.display.DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(rect);
        }
    }

    public void dispatchConfigurationChanged(android.content.res.Configuration configuration) {
        onConfigurationChanged(configuration);
    }

    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
    }

    void dispatchCollectViewAttributes(android.view.View.AttachInfo attachInfo, int i) {
        performCollectViewAttributes(attachInfo, i);
    }

    void performCollectViewAttributes(android.view.View.AttachInfo attachInfo, int i) {
        if ((i & 12) == 0) {
            if ((this.mViewFlags & 67108864) == 67108864) {
                attachInfo.mKeepScreenOn = true;
            }
            attachInfo.mSystemUiVisibility |= this.mSystemUiVisibility;
            android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo != null && listenerInfo.mOnSystemUiVisibilityChangeListener != null) {
                attachInfo.mHasSystemUiListeners = true;
            }
        }
    }

    void needGlobalAttributesUpdate(boolean z) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && !attachInfo.mRecomputeGlobalAttributes) {
            if (z || attachInfo.mKeepScreenOn || attachInfo.mSystemUiVisibility != 0 || attachInfo.mHasSystemUiListeners) {
                attachInfo.mRecomputeGlobalAttributes = true;
            }
        }
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isInTouchMode() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mInTouchMode;
        }
        return this.mResources.getBoolean(com.android.internal.R.bool.config_defaultInTouchMode);
    }

    @android.view.ViewDebug.CapturedViewProperty
    public final android.content.Context getContext() {
        return this.mContext;
    }

    public boolean onKeyPreIme(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (android.view.KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers()) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if (keyEvent.getRepeatCount() == 0) {
                boolean z = (this.mViewFlags & 16384) == 16384 || (this.mViewFlags & 2097152) == 2097152;
                if (z || (this.mViewFlags & 1073741824) == 1073741824) {
                    float width = getWidth() / 2.0f;
                    float height = getHeight() / 2.0f;
                    if (z) {
                        setPressed(true, width, height);
                    }
                    checkForLongClick(android.view.ViewConfiguration.getLongPressTimeout(), width, height, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        if (android.view.KeyEvent.isConfirmKey(i) && keyEvent.hasNoModifiers()) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if ((this.mViewFlags & 16384) == 16384 && isPressed()) {
                setPressed(false);
                if (!this.mHasPerformedLongPress) {
                    removeLongPressCallback();
                    if (!keyEvent.isCanceled()) {
                        return performClickInternal();
                    }
                }
            }
        }
        return false;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        return false;
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
        return null;
    }

    public void onInputConnectionOpenedInternal(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo, android.os.Handler handler) {
    }

    public void onInputConnectionClosedInternal() {
    }

    public boolean checkInputConnectionProxy(android.view.View view) {
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void createContextMenu(android.view.ContextMenu contextMenu) {
        android.view.ContextMenu.ContextMenuInfo contextMenuInfo = getContextMenuInfo();
        com.android.internal.view.menu.MenuBuilder menuBuilder = (com.android.internal.view.menu.MenuBuilder) contextMenu;
        menuBuilder.setCurrentMenuInfo(contextMenuInfo);
        onCreateContextMenu(contextMenu);
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnCreateContextMenuListener != null) {
            listenerInfo.mOnCreateContextMenuListener.onCreateContextMenu(contextMenu, this, contextMenuInfo);
        }
        menuBuilder.setCurrentMenuInfo(null);
        if (this.mParent != null) {
            this.mParent.createContextMenu(contextMenu);
        }
    }

    protected android.view.ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    protected void onCreateContextMenu(android.view.ContextMenu contextMenu) {
    }

    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    private boolean dispatchTouchExplorationHoverEvent(android.view.MotionEvent motionEvent) {
        android.view.accessibility.AccessibilityManager accessibilityManager = android.view.accessibility.AccessibilityManager.getInstance(this.mContext);
        if (!accessibilityManager.isEnabled() || !accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        boolean z = this.mHoveringTouchDelegate;
        int actionMasked = motionEvent.getActionMasked();
        android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo touchDelegateInfo = this.mTouchDelegate.getTouchDelegateInfo();
        boolean z2 = false;
        for (int i = 0; i < touchDelegateInfo.getRegionCount(); i++) {
            if (touchDelegateInfo.getRegionAt(i).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                z2 = true;
            }
        }
        if (!z) {
            if ((actionMasked == 9 || actionMasked == 7) && !pointInHoveredChild(motionEvent) && z2) {
                this.mHoveringTouchDelegate = true;
            }
        } else if (actionMasked == 10 || (actionMasked == 7 && (pointInHoveredChild(motionEvent) || !z2))) {
            this.mHoveringTouchDelegate = false;
        }
        switch (actionMasked) {
            case 7:
                if (z && this.mHoveringTouchDelegate) {
                    break;
                } else if (!z && this.mHoveringTouchDelegate) {
                    if (motionEvent.getHistorySize() != 0) {
                        motionEvent = android.view.MotionEvent.obtainNoHistory(motionEvent);
                    }
                    motionEvent.setAction(9);
                    boolean onTouchExplorationHoverEvent = this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    motionEvent.setAction(actionMasked);
                    break;
                } else if (z && !this.mHoveringTouchDelegate) {
                    boolean isHoverExitPending = motionEvent.isHoverExitPending();
                    motionEvent.setHoverExitPending(true);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    if (motionEvent.getHistorySize() != 0) {
                        motionEvent = android.view.MotionEvent.obtainNoHistory(motionEvent);
                    }
                    motionEvent.setHoverExitPending(isHoverExitPending);
                    motionEvent.setAction(10);
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    break;
                }
                break;
            case 9:
                if (!z && this.mHoveringTouchDelegate) {
                    break;
                }
                break;
            case 10:
                if (z) {
                    this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                    break;
                }
                break;
        }
        return false;
    }

    public boolean onHoverEvent(android.view.MotionEvent motionEvent) {
        if (this.mTouchDelegate != null && dispatchTouchExplorationHoverEvent(motionEvent)) {
            return true;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (!this.mSendingHoverAccessibilityEvents) {
            if ((actionMasked == 9 || actionMasked == 7) && !hasHoveredChild() && pointInView(motionEvent.getX(), motionEvent.getY())) {
                sendAccessibilityHoverEvent(128);
                this.mSendingHoverAccessibilityEvents = true;
            }
        } else if (actionMasked == 10 || (actionMasked == 7 && !pointInView(motionEvent.getX(), motionEvent.getY()))) {
            this.mSendingHoverAccessibilityEvents = false;
            sendAccessibilityHoverEvent(256);
        }
        if ((actionMasked == 9 || actionMasked == 7) && motionEvent.isFromSource(8194) && isOnScrollbar(motionEvent.getX(), motionEvent.getY())) {
            awakenScrollBars();
        }
        if (!isHoverable() && !isHovered()) {
            return false;
        }
        switch (actionMasked) {
            case 9:
                setHovered(true);
                break;
            case 10:
                setHovered(false);
                break;
        }
        dispatchGenericMotionEventInternal(motionEvent);
        return true;
    }

    private boolean isHoverable() {
        int i = this.mViewFlags;
        if ((i & 32) == 32) {
            return false;
        }
        return (i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608;
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isHovered() {
        return (this.mPrivateFlags & 268435456) != 0;
    }

    public void setHovered(boolean z) {
        if (z) {
            if ((this.mPrivateFlags & 268435456) == 0) {
                this.mPrivateFlags |= 268435456;
                refreshDrawableState();
                onHoverChanged(true);
                return;
            }
            return;
        }
        if ((this.mPrivateFlags & 268435456) != 0) {
            this.mPrivateFlags &= -268435457;
            refreshDrawableState();
            onHoverChanged(false);
        }
    }

    public void onHoverChanged(boolean z) {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0123  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected boolean handleScrollBarDragging(android.view.MotionEvent motionEvent) {
        int round;
        int round2;
        if (this.mScrollCache == null) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int action = motionEvent.getAction();
        if ((this.mScrollCache.mScrollBarDraggingState == 0 && action != 0) || !motionEvent.isFromSource(8194) || !motionEvent.isButtonPressed(1)) {
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        switch (action) {
            case 0:
                if (this.mScrollCache.state == 0) {
                    if (isOnVerticalScrollbarThumb(x, y)) {
                        this.mScrollCache.mScrollBarDraggingState = 1;
                        this.mScrollCache.mScrollBarDraggingPos = y;
                        break;
                    } else {
                        if (isOnHorizontalScrollbarThumb(x, y)) {
                            this.mScrollCache.mScrollBarDraggingState = 2;
                            this.mScrollCache.mScrollBarDraggingPos = x;
                            break;
                        }
                        this.mScrollCache.mScrollBarDraggingState = 0;
                        break;
                    }
                }
                break;
            case 1:
            default:
                this.mScrollCache.mScrollBarDraggingState = 0;
                break;
            case 2:
                if (this.mScrollCache.mScrollBarDraggingState != 0) {
                    if (this.mScrollCache.mScrollBarDraggingState != 1) {
                        if (this.mScrollCache.mScrollBarDraggingState == 2) {
                            android.graphics.Rect rect = this.mScrollCache.mScrollBarBounds;
                            getHorizontalScrollBarBounds(rect, null);
                            int computeHorizontalScrollRange = computeHorizontalScrollRange();
                            int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
                            int computeHorizontalScrollExtent = computeHorizontalScrollExtent();
                            int thumbLength = com.android.internal.widget.ScrollBarUtils.getThumbLength(rect.width(), rect.height(), computeHorizontalScrollExtent, computeHorizontalScrollRange);
                            int thumbOffset = com.android.internal.widget.ScrollBarUtils.getThumbOffset(rect.width(), thumbLength, computeHorizontalScrollExtent, computeHorizontalScrollRange, computeHorizontalScrollOffset);
                            float f = x - this.mScrollCache.mScrollBarDraggingPos;
                            float width = rect.width() - thumbLength;
                            float min = java.lang.Math.min(java.lang.Math.max(thumbOffset + f, 0.0f), width);
                            int width2 = getWidth();
                            if (java.lang.Math.round(min) != thumbOffset && width > 0.0f && width2 > 0 && computeHorizontalScrollExtent > 0 && (round = java.lang.Math.round(((computeHorizontalScrollRange - computeHorizontalScrollExtent) / (computeHorizontalScrollExtent / width2)) * (min / width))) != getScrollX()) {
                                this.mScrollCache.mScrollBarDraggingPos = x;
                                setScrollX(round);
                                break;
                            }
                        }
                        if (this.mScrollCache.state == 0) {
                        }
                    } else {
                        android.graphics.Rect rect2 = this.mScrollCache.mScrollBarBounds;
                        getVerticalScrollBarBounds(rect2, null);
                        int computeVerticalScrollRange = computeVerticalScrollRange();
                        int computeVerticalScrollOffset = computeVerticalScrollOffset();
                        int computeVerticalScrollExtent = computeVerticalScrollExtent();
                        int thumbLength2 = com.android.internal.widget.ScrollBarUtils.getThumbLength(rect2.height(), rect2.width(), computeVerticalScrollExtent, computeVerticalScrollRange);
                        int thumbOffset2 = com.android.internal.widget.ScrollBarUtils.getThumbOffset(rect2.height(), thumbLength2, computeVerticalScrollExtent, computeVerticalScrollRange, computeVerticalScrollOffset);
                        float f2 = y - this.mScrollCache.mScrollBarDraggingPos;
                        float height = rect2.height() - thumbLength2;
                        float min2 = java.lang.Math.min(java.lang.Math.max(thumbOffset2 + f2, 0.0f), height);
                        int height2 = getHeight();
                        if (java.lang.Math.round(min2) != thumbOffset2 && height > 0.0f && height2 > 0 && computeVerticalScrollExtent > 0 && (round2 = java.lang.Math.round(((computeVerticalScrollRange - computeVerticalScrollExtent) / (computeVerticalScrollExtent / height2)) * (min2 / height))) != getScrollY()) {
                            this.mScrollCache.mScrollBarDraggingPos = y;
                            setScrollY(round2);
                            break;
                        }
                    }
                }
                break;
        }
        return false;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x01f3, code lost:
    
        return true;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int i = this.mViewFlags;
        int action = motionEvent.getAction();
        boolean z2 = (i & 16384) == 16384 || (i & 2097152) == 2097152 || (i & 8388608) == 8388608;
        if ((i & 32) == 32 && (this.mPrivateFlags4 & 4096) == 0) {
            if (action == 1 && (this.mPrivateFlags & 16384) != 0) {
                setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            return z2;
        }
        if (this.mTouchDelegate != null && this.mTouchDelegate.onTouchEvent(motionEvent)) {
            return true;
        }
        if (!z2 && (i & 1073741824) != 1073741824) {
            return false;
        }
        byte b = 0;
        byte b2 = 0;
        switch (action) {
            case 0:
                if (motionEvent.getSource() == 4098) {
                    this.mPrivateFlags3 |= 131072;
                }
                this.mHasPerformedLongPress = false;
                if (!z2) {
                    checkForLongClick(android.view.ViewConfiguration.getLongPressTimeout(), x, y, 3);
                    break;
                } else if (!performButtonActionOnTouchDown(motionEvent)) {
                    if (isInScrollingContainer()) {
                        this.mPrivateFlags |= 33554432;
                        if (this.mPendingCheckForTap == null) {
                            this.mPendingCheckForTap = new android.view.View.CheckForTap();
                        }
                        this.mPendingCheckForTap.x = motionEvent.getX();
                        this.mPendingCheckForTap.y = motionEvent.getY();
                        postDelayed(this.mPendingCheckForTap, android.view.ViewConfiguration.getTapTimeout());
                        break;
                    } else {
                        setPressed(true, x, y);
                        checkForLongClick(android.view.ViewConfiguration.getLongPressTimeout(), x, y, 3);
                        break;
                    }
                }
                break;
            case 1:
                this.mPrivateFlags3 &= -131073;
                if ((i & 1073741824) == 1073741824) {
                    handleTooltipUp();
                }
                if (z2) {
                    byte b3 = (this.mPrivateFlags & 33554432) != 0;
                    if ((this.mPrivateFlags & 16384) != 0 || b3 != false) {
                        if (isFocusable() && isFocusableInTouchMode() && !isFocused()) {
                            z = requestFocus();
                        } else {
                            z = false;
                        }
                        if (b3 != false) {
                            setPressed(true, x, y);
                        }
                        if (!this.mHasPerformedLongPress && !this.mIgnoreNextUpEvent) {
                            removeLongPressCallback();
                            if (!z) {
                                if (this.mPerformClick == null) {
                                    this.mPerformClick = new android.view.View.PerformClick();
                                }
                                if (!post(this.mPerformClick)) {
                                    performClickInternal();
                                }
                            }
                        }
                        if (this.mUnsetPressedState == null) {
                            this.mUnsetPressedState = new android.view.View.UnsetPressedState();
                        }
                        if (b3 != false) {
                            postDelayed(this.mUnsetPressedState, android.view.ViewConfiguration.getPressedStateDuration());
                        } else if (!post(this.mUnsetPressedState)) {
                            this.mUnsetPressedState.run();
                        }
                        removeTapCallback();
                    }
                    this.mIgnoreNextUpEvent = false;
                    break;
                } else {
                    removeTapCallback();
                    removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                    break;
                }
                break;
            case 2:
                if (z2) {
                    drawableHotspotChanged(x, y);
                }
                int classification = motionEvent.getClassification();
                byte b4 = classification == 1;
                int i2 = this.mTouchSlop;
                if (b4 != false && hasPendingLongPressCallback()) {
                    float f = i2;
                    if (!pointInView(x, y, f)) {
                        removeLongPressCallback();
                        checkForLongClick(((long) (android.view.ViewConfiguration.getLongPressTimeout() * this.mAmbiguousGestureMultiplier)) - (motionEvent.getEventTime() - motionEvent.getDownTime()), x, y, 3);
                    }
                    i2 = (int) (f * this.mAmbiguousGestureMultiplier);
                }
                if (!pointInView(x, y, i2)) {
                    removeTapCallback();
                    removeLongPressCallback();
                    if ((this.mPrivateFlags & 16384) != 0) {
                        setPressed(false);
                    }
                    this.mPrivateFlags3 &= -131073;
                }
                if ((classification == 2) && hasPendingLongPressCallback()) {
                    removeLongPressCallback();
                    checkForLongClick(0L, x, y, 4);
                    break;
                }
                break;
            case 3:
                if (z2) {
                    setPressed(false);
                }
                removeTapCallback();
                removeLongPressCallback();
                this.mInContextButtonPress = false;
                this.mHasPerformedLongPress = false;
                this.mIgnoreNextUpEvent = false;
                this.mPrivateFlags3 &= -131073;
                break;
        }
    }

    public boolean isInScrollingContainer() {
        for (android.view.ViewParent parent = getParent(); parent != null && (parent instanceof android.view.ViewGroup); parent = parent.getParent()) {
            if (((android.view.ViewGroup) parent).shouldDelayChildPressedState()) {
                return true;
            }
        }
        return false;
    }

    private void removeLongPressCallback() {
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
    }

    private boolean hasPendingLongPressCallback() {
        android.view.View.AttachInfo attachInfo;
        if (this.mPendingCheckForLongPress == null || (attachInfo = this.mAttachInfo) == null) {
            return false;
        }
        return attachInfo.mHandler.hasCallbacks(this.mPendingCheckForLongPress);
    }

    private void removePerformClickCallback() {
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
    }

    private void removeUnsetPressCallback() {
        if ((this.mPrivateFlags & 16384) != 0 && this.mUnsetPressedState != null) {
            setPressed(false);
            removeCallbacks(this.mUnsetPressedState);
        }
    }

    private void removeTapCallback() {
        if (this.mPendingCheckForTap != null) {
            this.mPrivateFlags &= -33554433;
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    public void cancelLongPress() {
        removeLongPressCallback();
        removeTapCallback();
    }

    public void setTouchDelegate(android.view.TouchDelegate touchDelegate) {
        this.mTouchDelegate = touchDelegate;
    }

    public android.view.TouchDelegate getTouchDelegate() {
        return this.mTouchDelegate;
    }

    public final void requestUnbufferedDispatch(android.view.MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (this.mAttachInfo != null) {
            if ((action != 0 && action != 2) || !motionEvent.isTouchEvent()) {
                return;
            }
            this.mAttachInfo.mUnbufferedDispatchRequested = true;
        }
    }

    public final void requestUnbufferedDispatch(int i) {
        if (this.mUnbufferedInputSource == i) {
            return;
        }
        this.mUnbufferedInputSource = i;
        if (this.mParent != null) {
            this.mParent.onDescendantUnbufferedRequested();
        }
    }

    private boolean hasSize() {
        return this.mBottom > this.mTop && this.mRight > this.mLeft;
    }

    private boolean canTakeFocus() {
        return (this.mViewFlags & 12) == 0 && (this.mViewFlags & 1) == 1 && (this.mViewFlags & 32) == 0 && (sCanFocusZeroSized || !isLayoutValid() || hasSize());
    }

    /* JADX WARN: Removed duplicated region for block: B:121:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:178:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0156  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setFlags(int i, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        boolean isEnabled = android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled();
        boolean z2 = isEnabled && includeForAccessibility(false);
        int i7 = this.mViewFlags;
        this.mViewFlags = (i2 & i) | (this.mViewFlags & (~i2));
        int i8 = this.mViewFlags ^ i7;
        if (i8 == 0) {
            return;
        }
        int i9 = this.mPrivateFlags;
        if ((this.mViewFlags & 16) != 0 && (i8 & android.os.BatteryStats.HistoryItem.EVENT_TEMP_WHITELIST_FINISH) != 0) {
            if ((this.mViewFlags & 16384) != 0) {
                i6 = 1;
            } else {
                i6 = 0;
            }
            this.mViewFlags = (this.mViewFlags & (-2)) | i6;
            i3 = (i6 & 1) ^ (i7 & 1);
            i8 = (i8 & (-2)) | i3;
        } else {
            i3 = 0;
        }
        if ((i8 & 1) != 0 && (i9 & 16) != 0) {
            int i10 = i7 & 1;
            if (i10 == 1 && (i9 & 2) != 0) {
                clearFocus();
                if (this.mParent instanceof android.view.ViewGroup) {
                    ((android.view.ViewGroup) this.mParent).clearFocusedInCluster();
                }
            } else if (i10 == 0 && (i9 & 2) == 0 && this.mParent != null) {
                android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
                if (!sAutoFocusableOffUIThreadWontNotifyParents || i3 == 0 || viewRootImpl == null || viewRootImpl.mThread == java.lang.Thread.currentThread()) {
                    z = canTakeFocus();
                    i4 = i & 12;
                    if (i4 == 0 && (i8 & 12) != 0) {
                        this.mPrivateFlags |= 32;
                        invalidate(true);
                        needGlobalAttributesUpdate(true);
                        z = hasSize();
                    }
                    if ((i8 & 32) != 0) {
                        if ((this.mViewFlags & 32) == 0) {
                            z = canTakeFocus();
                        } else if (isFocused()) {
                            clearFocus();
                        }
                    }
                    if (z && this.mParent != null) {
                        this.mParent.focusableViewAvailable(this);
                    }
                    if ((i8 & 8) != 0) {
                        needGlobalAttributesUpdate(false);
                        requestLayout();
                        if ((this.mViewFlags & 12) == 8) {
                            if (hasFocus()) {
                                clearFocus();
                                if (this.mParent instanceof android.view.ViewGroup) {
                                    ((android.view.ViewGroup) this.mParent).clearFocusedInCluster();
                                }
                            }
                            clearAccessibilityFocus();
                            destroyDrawingCache();
                            if (this.mParent instanceof android.view.View) {
                                ((android.view.View) this.mParent).invalidate(true);
                            }
                            this.mPrivateFlags |= 32;
                        }
                        if (this.mAttachInfo != null) {
                            this.mAttachInfo.mViewVisibilityChanged = true;
                        }
                    }
                    if ((i8 & 4) != 0) {
                        needGlobalAttributesUpdate(false);
                        this.mPrivateFlags |= 32;
                        if ((this.mViewFlags & 12) == 4 && getRootView() != this) {
                            if (hasFocus()) {
                                clearFocus();
                                if (this.mParent instanceof android.view.ViewGroup) {
                                    ((android.view.ViewGroup) this.mParent).clearFocusedInCluster();
                                }
                            }
                            clearAccessibilityFocus();
                        }
                        if (this.mAttachInfo != null) {
                            this.mAttachInfo.mViewVisibilityChanged = true;
                        }
                    }
                    i5 = i8 & 12;
                    if (i5 != 0) {
                        if (i4 != 0 && this.mAttachInfo != null) {
                            cleanupDraw();
                        }
                        if (this.mParent instanceof android.view.ViewGroup) {
                            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mParent;
                            viewGroup.onChildVisibilityChanged(this, i5, i4);
                            viewGroup.invalidate(true);
                        } else if (this.mParent != null) {
                            this.mParent.invalidateChild(this, null);
                        }
                        if (this.mAttachInfo != null) {
                            dispatchVisibilityChanged(this, i4);
                            if (this.mParent != null && getWindowVisibility() == 0 && (!(this.mParent instanceof android.view.ViewGroup) || ((android.view.ViewGroup) this.mParent).isShown())) {
                                dispatchVisibilityAggregated(i4 == 0);
                            }
                            if ((i7 & 12) == 0) {
                                notifySubtreeAccessibilityStateChangedByParentIfNeeded();
                            } else {
                                notifySubtreeAccessibilityStateChangedIfNeeded();
                            }
                        }
                    }
                    if ((131072 & i8) != 0) {
                        destroyDrawingCache();
                    }
                    if ((32768 & i8) != 0) {
                        destroyDrawingCache();
                        this.mPrivateFlags &= -32769;
                        invalidateParentCaches();
                    }
                    if ((1572864 & i8) != 0) {
                        destroyDrawingCache();
                        this.mPrivateFlags &= -32769;
                    }
                    if ((i8 & 128) != 0) {
                        if ((this.mViewFlags & 128) != 0) {
                            if (this.mBackground != null || this.mDefaultFocusHighlight != null || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null)) {
                                this.mPrivateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                            } else {
                                this.mPrivateFlags |= 128;
                            }
                        } else {
                            this.mPrivateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                        }
                        requestLayout();
                        invalidate(true);
                    }
                    if ((67108864 & i8) != 0 && this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
                        this.mParent.recomputeViewAttributes(this);
                    }
                    if (!isEnabled) {
                        if (isAccessibilityPane()) {
                            i8 &= -13;
                        }
                        if ((i8 & 1) != 0 || (i8 & 12) != 0 || (i8 & 16384) != 0 || (2097152 & i8) != 0 || (8388608 & i8) != 0) {
                            if (z2 != includeForAccessibility(false)) {
                                notifySubtreeAccessibilityStateChangedIfNeeded();
                                return;
                            } else {
                                notifyViewAccessibilityStateChangedIfNeeded(0);
                                return;
                            }
                        }
                        if ((i8 & 32) != 0) {
                            notifyViewAccessibilityStateChangedIfNeeded(0);
                            return;
                        }
                        return;
                    }
                    return;
                }
            }
        }
        z = false;
        i4 = i & 12;
        if (i4 == 0) {
            this.mPrivateFlags |= 32;
            invalidate(true);
            needGlobalAttributesUpdate(true);
            z = hasSize();
        }
        if ((i8 & 32) != 0) {
        }
        if (z) {
            this.mParent.focusableViewAvailable(this);
        }
        if ((i8 & 8) != 0) {
        }
        if ((i8 & 4) != 0) {
        }
        i5 = i8 & 12;
        if (i5 != 0) {
        }
        if ((131072 & i8) != 0) {
        }
        if ((32768 & i8) != 0) {
        }
        if ((1572864 & i8) != 0) {
        }
        if ((i8 & 128) != 0) {
        }
        if ((67108864 & i8) != 0) {
            this.mParent.recomputeViewAttributes(this);
        }
        if (!isEnabled) {
        }
    }

    public void bringToFront() {
        if (this.mParent != null) {
            this.mParent.bringChildToFront(this);
        }
    }

    private android.view.HapticScrollFeedbackProvider getScrollFeedbackProvider() {
        if (this.mScrollFeedbackProvider == null) {
            this.mScrollFeedbackProvider = new android.view.HapticScrollFeedbackProvider(this, android.view.ViewConfiguration.get(this.mContext), false);
        }
        return this.mScrollFeedbackProvider;
    }

    private void doRotaryProgressForScrollHaptics(android.view.MotionEvent motionEvent) {
        getScrollFeedbackProvider().onScrollProgress(motionEvent.getDeviceId(), 4194304, 26, -java.lang.Math.round(motionEvent.getAxisValue(26) * android.view.ViewConfiguration.get(this.mContext).getScaledVerticalScrollFactor()));
    }

    private void doRotaryLimitForScrollHaptics(android.view.MotionEvent motionEvent) {
        getScrollFeedbackProvider().onScrollLimit(motionEvent.getDeviceId(), 4194304, 26, motionEvent.getAxisValue(26) > 0.0f);
    }

    private void processScrollEventForRotaryEncoderHaptics() {
        int i = this.mPrivateFlags4 | 8388608;
        this.mPrivateFlags4 = i;
        if (i != 0) {
            this.mPrivateFlags4 |= 4194304;
            this.mPrivateFlags4 &= -8388609;
        }
    }

    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        notifySubtreeAccessibilityStateChangedIfNeeded();
        postSendViewScrolledAccessibilityEventCallback(i - i3, i2 - i4);
        processScrollEventForRotaryEncoderHaptics();
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (this.mForegroundInfo != null) {
            this.mForegroundInfo.mBoundsChanged = true;
        }
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewScrollChanged = true;
        }
        if (this.mListenerInfo != null && this.mListenerInfo.mOnScrollChangeListener != null) {
            this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, i, i2, i3, i4);
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
    }

    protected void dispatchDraw(android.graphics.Canvas canvas) {
    }

    public final android.view.ViewParent getParent() {
        return this.mParent;
    }

    public void setScrollX(int i) {
        scrollTo(i, this.mScrollY);
    }

    public void setScrollY(int i) {
        scrollTo(this.mScrollX, i);
    }

    public final int getScrollX() {
        return this.mScrollX;
    }

    public final int getScrollY() {
        return this.mScrollY;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public final int getWidth() {
        return this.mRight - this.mLeft;
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public final int getHeight() {
        return this.mBottom - this.mTop;
    }

    public void getDrawingRect(android.graphics.Rect rect) {
        rect.left = this.mScrollX;
        rect.top = this.mScrollY;
        rect.right = this.mScrollX + (this.mRight - this.mLeft);
        rect.bottom = this.mScrollY + (this.mBottom - this.mTop);
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth & 16777215;
    }

    @android.view.ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@android.view.ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredWidthAndState() {
        return this.mMeasuredWidth;
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight & 16777215;
    }

    @android.view.ViewDebug.ExportedProperty(category = "measurement", flagMapping = {@android.view.ViewDebug.FlagToString(equals = 16777216, mask = -16777216, name = "MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredHeightAndState() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredState() {
        return (this.mMeasuredWidth & (-16777216)) | ((this.mMeasuredHeight >> 16) & (-256));
    }

    public android.graphics.Matrix getMatrix() {
        ensureTransformationInfo();
        android.graphics.Matrix matrix = this.mTransformationInfo.mMatrix;
        this.mRenderNode.getMatrix(matrix);
        return matrix;
    }

    public final boolean hasIdentityMatrix() {
        return this.mRenderNode.hasIdentityMatrix();
    }

    void ensureTransformationInfo() {
        if (this.mTransformationInfo == null) {
            this.mTransformationInfo = new android.view.View.TransformationInfo();
        }
    }

    public final android.graphics.Matrix getInverseMatrix() {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mInverseMatrix == null) {
            this.mTransformationInfo.mInverseMatrix = new android.graphics.Matrix();
        }
        android.graphics.Matrix matrix = this.mTransformationInfo.mInverseMatrix;
        this.mRenderNode.getInverseMatrix(matrix);
        return matrix;
    }

    public float getCameraDistance() {
        return this.mRenderNode.getCameraDistance() * this.mResources.getDisplayMetrics().densityDpi;
    }

    public void setCameraDistance(float f) {
        float f2 = this.mResources.getDisplayMetrics().densityDpi;
        invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(java.lang.Math.abs(f) / f2);
        invalidateViewProperty(false, false);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getRotation() {
        return this.mRenderNode.getRotationZ();
    }

    @android.view.RemotableViewMethod
    public void setRotation(float f) {
        if (f != getRotation()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationZ(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationY() {
        return this.mRenderNode.getRotationY();
    }

    @android.view.RemotableViewMethod
    public void setRotationY(float f) {
        if (f != getRotationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationY(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getRotationX() {
        return this.mRenderNode.getRotationX();
    }

    @android.view.RemotableViewMethod
    public void setRotationX(float f) {
        if (f != getRotationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setRotationX(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleX() {
        return this.mRenderNode.getScaleX();
    }

    @android.view.RemotableViewMethod
    public void setScaleX(float f) {
        if (f != getScaleX()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "scaleX");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleX(sanitizeFloatPropertyValue);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getScaleY() {
        return this.mRenderNode.getScaleY();
    }

    @android.view.RemotableViewMethod
    public void setScaleY(float f) {
        if (f != getScaleY()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "scaleY");
            invalidateViewProperty(true, false);
            this.mRenderNode.setScaleY(sanitizeFloatPropertyValue);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotX() {
        return this.mRenderNode.getPivotX();
    }

    @android.view.RemotableViewMethod
    public void setPivotX(float f) {
        if (!this.mRenderNode.isPivotExplicitlySet() || f != getPivotX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotX(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getPivotY() {
        return this.mRenderNode.getPivotY();
    }

    @android.view.RemotableViewMethod
    public void setPivotY(float f) {
        if (!this.mRenderNode.isPivotExplicitlySet() || f != getPivotY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setPivotY(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public boolean isPivotSet() {
        return this.mRenderNode.isPivotExplicitlySet();
    }

    public void resetPivot() {
        if (this.mRenderNode.resetPivot()) {
            invalidateViewProperty(false, false);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha;
        }
        return 1.0f;
    }

    public void forceHasOverlappingRendering(boolean z) {
        this.mPrivateFlags3 |= 16777216;
        if (z) {
            this.mPrivateFlags3 |= 8388608;
        } else {
            this.mPrivateFlags3 &= -8388609;
        }
    }

    public final boolean getHasOverlappingRendering() {
        if ((this.mPrivateFlags3 & 16777216) != 0) {
            return (this.mPrivateFlags3 & 8388608) != 0;
        }
        return hasOverlappingRendering();
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasOverlappingRendering() {
        return true;
    }

    @android.view.RemotableViewMethod
    public void setAlpha(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != f) {
            setAlphaInternal(f);
            if (onSetAlpha((int) (f * 255.0f))) {
                this.mPrivateFlags |= 262144;
                invalidateParentCaches();
                invalidate(true);
            } else {
                this.mPrivateFlags &= -262145;
                invalidateViewProperty(true, false);
                this.mRenderNode.setAlpha(getFinalAlpha());
            }
        }
    }

    boolean setAlphaNoInvalidation(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != f) {
            setAlphaInternal(f);
            if (onSetAlpha((int) (f * 255.0f))) {
                this.mPrivateFlags |= 262144;
                return true;
            }
            this.mPrivateFlags &= -262145;
            this.mRenderNode.setAlpha(getFinalAlpha());
            return false;
        }
        return false;
    }

    void setAlphaInternal(float f) {
        float f2 = this.mTransformationInfo.mAlpha;
        this.mTransformationInfo.mAlpha = f;
        if ((f == 0.0f) ^ (f2 == 0.0f)) {
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTransitionAlpha(float f) {
        ensureTransformationInfo();
        if (this.mTransformationInfo.mTransitionAlpha != f) {
            this.mTransformationInfo.mTransitionAlpha = f;
            this.mPrivateFlags &= -262145;
            invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(getFinalAlpha());
        }
    }

    private float getFinalAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mAlpha * this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getTransitionAlpha() {
        if (this.mTransformationInfo != null) {
            return this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    public void setForceDarkAllowed(boolean z) {
        if (this.mRenderNode.setForceDarkAllowed(z)) {
            invalidate();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean isForceDarkAllowed() {
        return this.mRenderNode.isForceDarkAllowed();
    }

    @android.view.ViewDebug.CapturedViewProperty
    public final int getTop() {
        return this.mTop;
    }

    public final void setTop(int i) {
        int i2;
        int i3;
        if (i != this.mTop) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    if (i < this.mTop) {
                        i3 = i - this.mTop;
                        i2 = i;
                    } else {
                        i2 = this.mTop;
                        i3 = 0;
                    }
                    invalidate(0, i3, this.mRight - this.mLeft, this.mBottom - i2);
                }
            } else {
                invalidate(true);
            }
            int i4 = this.mRight - this.mLeft;
            int i5 = this.mBottom - this.mTop;
            this.mTop = i;
            this.mRenderNode.setTop(this.mTop);
            sizeChange(i4, this.mBottom - this.mTop, i4, i5);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @android.view.ViewDebug.CapturedViewProperty
    public final int getBottom() {
        return this.mBottom;
    }

    public boolean isDirty() {
        return (this.mPrivateFlags & 2097152) != 0;
    }

    public final void setBottom(int i) {
        int i2;
        if (i != this.mBottom) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    if (i < this.mBottom) {
                        i2 = this.mBottom;
                    } else {
                        i2 = i;
                    }
                    invalidate(0, 0, this.mRight - this.mLeft, i2 - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i3 = this.mRight - this.mLeft;
            int i4 = this.mBottom - this.mTop;
            this.mBottom = i;
            this.mRenderNode.setBottom(this.mBottom);
            sizeChange(i3, this.mBottom - this.mTop, i3, i4);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @android.view.ViewDebug.CapturedViewProperty
    public final int getLeft() {
        return this.mLeft;
    }

    public final void setLeft(int i) {
        int i2;
        int i3;
        if (i != this.mLeft) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    if (i < this.mLeft) {
                        i3 = i - this.mLeft;
                        i2 = i;
                    } else {
                        i2 = this.mLeft;
                        i3 = 0;
                    }
                    invalidate(i3, 0, this.mRight - i2, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i4 = this.mRight - this.mLeft;
            int i5 = this.mBottom - this.mTop;
            this.mLeft = i;
            this.mRenderNode.setLeft(i);
            sizeChange(this.mRight - this.mLeft, i5, i4, i5);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    @android.view.ViewDebug.CapturedViewProperty
    public final int getRight() {
        return this.mRight;
    }

    public final void setRight(int i) {
        int i2;
        if (i != this.mRight) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (this.mAttachInfo != null) {
                    if (i < this.mRight) {
                        i2 = this.mRight;
                    } else {
                        i2 = i;
                    }
                    invalidate(0, 0, i2 - this.mLeft, this.mBottom - this.mTop);
                }
            } else {
                invalidate(true);
            }
            int i3 = this.mRight - this.mLeft;
            int i4 = this.mBottom - this.mTop;
            this.mRight = i;
            this.mRenderNode.setRight(this.mRight);
            sizeChange(this.mRight - this.mLeft, i4, i3, i4);
            if (!hasIdentityMatrix) {
                this.mPrivateFlags |= 32;
                invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            if (this.mForegroundInfo != null) {
                this.mForegroundInfo.mBoundsChanged = true;
            }
            invalidateParentIfNeeded();
        }
    }

    private static float sanitizeFloatPropertyValue(float f, java.lang.String str) {
        return sanitizeFloatPropertyValue(f, str, -3.4028235E38f, Float.MAX_VALUE);
    }

    private static float sanitizeFloatPropertyValue(float f, java.lang.String str, float f2, float f3) {
        if (f >= f2 && f <= f3) {
            return f;
        }
        if (f < f2 || f == Float.NEGATIVE_INFINITY) {
            if (sThrowOnInvalidFloatProperties) {
                throw new java.lang.IllegalArgumentException("Cannot set '" + str + "' to " + f + ", the value must be >= " + f2);
            }
            return f2;
        }
        if (f > f3 || f == Float.POSITIVE_INFINITY) {
            if (sThrowOnInvalidFloatProperties) {
                throw new java.lang.IllegalArgumentException("Cannot set '" + str + "' to " + f + ", the value must be <= " + f3);
            }
            return f3;
        }
        if (java.lang.Float.isNaN(f)) {
            if (sThrowOnInvalidFloatProperties) {
                throw new java.lang.IllegalArgumentException("Cannot set '" + str + "' to Float.NaN");
            }
            return 0.0f;
        }
        throw new java.lang.IllegalStateException("How do you get here?? " + f);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getX() {
        return this.mLeft + getTranslationX();
    }

    public void setX(float f) {
        setTranslationX(f - this.mLeft);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getY() {
        return this.mTop + getTranslationY();
    }

    public void setY(float f) {
        setTranslationY(f - this.mTop);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getZ() {
        return getElevation() + getTranslationZ();
    }

    public void setZ(float f) {
        setTranslationZ(f - getElevation());
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getElevation() {
        return this.mRenderNode.getElevation();
    }

    @android.view.RemotableViewMethod
    public void setElevation(float f) {
        if (f != getElevation()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "elevation");
            invalidateViewProperty(true, false);
            this.mRenderNode.setElevation(sanitizeFloatPropertyValue);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationX() {
        return this.mRenderNode.getTranslationX();
    }

    @android.view.RemotableViewMethod
    public void setTranslationX(float f) {
        if (f != getTranslationX()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationX(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationY() {
        return this.mRenderNode.getTranslationY();
    }

    @android.view.RemotableViewMethod
    public void setTranslationY(float f) {
        if (f != getTranslationY()) {
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationY(f);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public float getTranslationZ() {
        return this.mRenderNode.getTranslationZ();
    }

    @android.view.RemotableViewMethod
    public void setTranslationZ(float f) {
        if (f != getTranslationZ()) {
            float sanitizeFloatPropertyValue = sanitizeFloatPropertyValue(f, "translationZ");
            invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationZ(sanitizeFloatPropertyValue);
            invalidateViewProperty(false, true);
            invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setAnimationMatrix(android.graphics.Matrix matrix) {
        invalidateViewProperty(true, false);
        this.mRenderNode.setAnimationMatrix(matrix);
        invalidateViewProperty(false, true);
        invalidateParentIfNeededAndWasQuickRejected();
    }

    public android.graphics.Matrix getAnimationMatrix() {
        return this.mRenderNode.getAnimationMatrix();
    }

    public android.animation.StateListAnimator getStateListAnimator() {
        return this.mStateListAnimator;
    }

    public void setStateListAnimator(android.animation.StateListAnimator stateListAnimator) {
        if (this.mStateListAnimator == stateListAnimator) {
            return;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setTarget(null);
        }
        this.mStateListAnimator = stateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.setTarget(this);
            if (isAttachedToWindow()) {
                stateListAnimator.setState(getDrawableState());
            }
        }
    }

    public final boolean getClipToOutline() {
        return this.mRenderNode.getClipToOutline();
    }

    @android.view.RemotableViewMethod
    public void setClipToOutline(boolean z) {
        damageInParent();
        if (getClipToOutline() != z) {
            this.mRenderNode.setClipToOutline(z);
        }
    }

    private void setOutlineProviderFromAttribute(int i) {
        switch (i) {
            case 0:
                setOutlineProvider(android.view.ViewOutlineProvider.BACKGROUND);
                break;
            case 1:
                setOutlineProvider(null);
                break;
            case 2:
                setOutlineProvider(android.view.ViewOutlineProvider.BOUNDS);
                break;
            case 3:
                setOutlineProvider(android.view.ViewOutlineProvider.PADDED_BOUNDS);
                break;
        }
    }

    public void setOutlineProvider(android.view.ViewOutlineProvider viewOutlineProvider) {
        if (this.mOutlineProvider != viewOutlineProvider) {
            this.mOutlineProvider = viewOutlineProvider;
            invalidateOutline();
        }
    }

    public android.view.ViewOutlineProvider getOutlineProvider() {
        return this.mOutlineProvider;
    }

    public void invalidateOutline() {
        rebuildOutline();
        notifySubtreeAccessibilityStateChangedIfNeeded();
        invalidateViewProperty(false, false);
    }

    private void rebuildOutline() {
        if (this.mAttachInfo == null) {
            return;
        }
        if (this.mOutlineProvider == null) {
            this.mRenderNode.setOutline(null);
            return;
        }
        android.graphics.Outline outline = this.mAttachInfo.mTmpOutline;
        outline.setEmpty();
        outline.setAlpha(1.0f);
        this.mOutlineProvider.getOutline(this, outline);
        this.mRenderNode.setOutline(outline);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean hasShadow() {
        return this.mRenderNode.hasShadow();
    }

    public void setOutlineSpotShadowColor(int i) {
        if (this.mRenderNode.setSpotShadowColor(i)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineSpotShadowColor() {
        return this.mRenderNode.getSpotShadowColor();
    }

    public void setOutlineAmbientShadowColor(int i) {
        if (this.mRenderNode.setAmbientShadowColor(i)) {
            invalidateViewProperty(true, true);
        }
    }

    public int getOutlineAmbientShadowColor() {
        return this.mRenderNode.getAmbientShadowColor();
    }

    public void setRevealClip(boolean z, float f, float f2, float f3) {
        this.mRenderNode.setRevealClip(z, f, f2, f3);
        invalidateViewProperty(false, false);
    }

    public void getHitRect(android.graphics.Rect rect) {
        if (hasIdentityMatrix() || this.mAttachInfo == null) {
            rect.set(this.mLeft, this.mTop, this.mRight, this.mBottom);
            return;
        }
        android.graphics.RectF rectF = this.mAttachInfo.mTmpTransformRect;
        rectF.set(0.0f, 0.0f, getWidth(), getHeight());
        getMatrix().mapRect(rectF);
        rect.set(((int) rectF.left) + this.mLeft, ((int) rectF.top) + this.mTop, ((int) rectF.right) + this.mLeft, ((int) rectF.bottom) + this.mTop);
    }

    final boolean pointInView(float f, float f2) {
        return pointInView(f, f2, 0.0f);
    }

    public boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        return f >= f4 && f2 >= f4 && f < ((float) (this.mRight - this.mLeft)) + f3 && f2 < ((float) (this.mBottom - this.mTop)) + f3;
    }

    public void getFocusedRect(android.graphics.Rect rect) {
        getDrawingRect(rect);
    }

    public boolean getGlobalVisibleRect(android.graphics.Rect rect, android.graphics.Point point) {
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        if (i <= 0 || i2 <= 0) {
            return false;
        }
        rect.set(0, 0, i, i2);
        if (point != null) {
            point.set(-this.mScrollX, -this.mScrollY);
        }
        return this.mParent == null || this.mParent.getChildVisibleRect(this, rect, point);
    }

    public final boolean getGlobalVisibleRect(android.graphics.Rect rect) {
        return getGlobalVisibleRect(rect, null);
    }

    public final boolean getLocalVisibleRect(android.graphics.Rect rect) {
        android.graphics.Point point = this.mAttachInfo != null ? this.mAttachInfo.mPoint : new android.graphics.Point();
        if (getGlobalVisibleRect(rect, point)) {
            rect.offset(-point.x, -point.y);
            return true;
        }
        return false;
    }

    public void offsetTopAndBottom(int i) {
        int i2;
        int i3;
        int i4;
        if (i != 0) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    android.view.ViewParent viewParent = this.mParent;
                    if (viewParent != null && this.mAttachInfo != null) {
                        android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
                        if (i < 0) {
                            i2 = this.mTop + i;
                            i3 = this.mBottom;
                            i4 = i;
                        } else {
                            i2 = this.mTop;
                            i3 = this.mBottom + i;
                            i4 = 0;
                        }
                        rect.set(0, i4, this.mRight - this.mLeft, i3 - i2);
                        viewParent.invalidateChild(this, rect);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mTop += i;
            this.mBottom += i;
            this.mRenderNode.offsetTopAndBottom(i);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!hasIdentityMatrix) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void offsetLeftAndRight(int i) {
        int i2;
        int i3;
        if (i != 0) {
            boolean hasIdentityMatrix = hasIdentityMatrix();
            if (hasIdentityMatrix) {
                if (isHardwareAccelerated()) {
                    invalidateViewProperty(false, false);
                } else {
                    android.view.ViewParent viewParent = this.mParent;
                    if (viewParent != null && this.mAttachInfo != null) {
                        android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
                        if (i < 0) {
                            i2 = this.mLeft + i;
                            i3 = this.mRight;
                        } else {
                            i2 = this.mLeft;
                            i3 = this.mRight + i;
                        }
                        rect.set(0, 0, i3 - i2, this.mBottom - this.mTop);
                        viewParent.invalidateChild(this, rect);
                    }
                }
            } else {
                invalidateViewProperty(false, false);
            }
            this.mLeft += i;
            this.mRight += i;
            this.mRenderNode.offsetLeftAndRight(i);
            if (isHardwareAccelerated()) {
                invalidateViewProperty(false, false);
                invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!hasIdentityMatrix) {
                    invalidateViewProperty(false, true);
                }
                invalidateParentIfNeeded();
            }
            notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(deepExport = true, prefix = "layout_")
    public android.view.ViewGroup.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    public void setLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams == null) {
            throw new java.lang.NullPointerException("Layout parameters cannot be null");
        }
        this.mLayoutParams = layoutParams;
        resolveLayoutParams();
        if (this.mParent instanceof android.view.ViewGroup) {
            ((android.view.ViewGroup) this.mParent).onSetLayoutParams(this, layoutParams);
        }
        requestLayout();
    }

    public void resolveLayoutParams() {
        if (this.mLayoutParams != null) {
            this.mLayoutParams.resolveLayoutDirection(getLayoutDirection());
        }
    }

    public void scrollTo(int i, int i2) {
        if (this.mScrollX != i || this.mScrollY != i2) {
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            this.mScrollX = i;
            this.mScrollY = i2;
            invalidateParentCaches();
            onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
            }
        }
    }

    public void scrollBy(int i, int i2) {
        scrollTo(this.mScrollX + i, this.mScrollY + i2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars() {
        return this.mScrollCache != null && awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade, true);
    }

    private boolean initialAwakenScrollBars() {
        return this.mScrollCache != null && awakenScrollBars(this.mScrollCache.scrollBarDefaultDelayBeforeFade * 4, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars(int i) {
        return awakenScrollBars(i, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean awakenScrollBars(int i, boolean z) {
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || !scrollabilityCache.fadeScrollBars) {
            return false;
        }
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new android.widget.ScrollBarDrawable();
            scrollabilityCache.scrollBar.setState(getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        if (!isHorizontalScrollBarEnabled() && !isVerticalScrollBarEnabled()) {
            return false;
        }
        if (z) {
            postInvalidateOnAnimation();
        }
        if (scrollabilityCache.state == 0) {
            i = java.lang.Math.max(750, i);
        }
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() + i;
        scrollabilityCache.fadeStartTime = currentAnimationTimeMillis;
        scrollabilityCache.state = 1;
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mHandler.removeCallbacks(scrollabilityCache);
            this.mAttachInfo.mHandler.postAtTime(scrollabilityCache, currentAnimationTimeMillis);
        }
        return true;
    }

    private boolean skipInvalidate() {
        return ((this.mViewFlags & 12) == 0 || this.mCurrentAnimation != null || ((this.mParent instanceof android.view.ViewGroup) && ((android.view.ViewGroup) this.mParent).isViewTransitioning(this))) ? false : true;
    }

    @java.lang.Deprecated
    public void invalidate(android.graphics.Rect rect) {
        int i = this.mScrollX;
        int i2 = this.mScrollY;
        invalidateInternal(rect.left - i, rect.top - i2, rect.right - i, rect.bottom - i2, true, false);
    }

    @java.lang.Deprecated
    public void invalidate(int i, int i2, int i3, int i4) {
        int i5 = this.mScrollX;
        int i6 = this.mScrollY;
        invalidateInternal(i - i5, i2 - i6, i3 - i5, i4 - i6, true, false);
    }

    public void invalidate() {
        invalidate(true);
    }

    public void invalidate(boolean z) {
        invalidateInternal(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, z, true);
    }

    void invalidateInternal(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        android.view.View projectionReceiver;
        if (this.mGhostView != null) {
            this.mGhostView.invalidate(true);
            return;
        }
        if (skipInvalidate()) {
            return;
        }
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            updateInfrequentCount();
            votePreferredFrameRate();
        }
        this.mPrivateFlags4 &= -193;
        this.mContentCaptureSessionCached = false;
        if ((this.mPrivateFlags & 48) == 48 || ((z && (this.mPrivateFlags & 32768) == 32768) || (this.mPrivateFlags & Integer.MIN_VALUE) != Integer.MIN_VALUE || (z2 && isOpaque() != this.mLastIsOpaque))) {
            if (z2) {
                this.mLastIsOpaque = isOpaque();
                this.mPrivateFlags &= -33;
            }
            this.mPrivateFlags |= 2097152;
            if (z) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
                this.mPrivateFlags &= -32769;
            }
            android.view.View.AttachInfo attachInfo = this.mAttachInfo;
            android.view.ViewParent viewParent = this.mParent;
            if (viewParent != null && attachInfo != null && i < i3 && i2 < i4) {
                android.graphics.Rect rect = attachInfo.mTmpInvalRect;
                rect.set(i, i2, i3, i4);
                viewParent.invalidateChild(this, rect);
            }
            if (this.mBackground != null && this.mBackground.isProjected() && (projectionReceiver = getProjectionReceiver()) != null) {
                projectionReceiver.damageInParent();
            }
        }
    }

    private android.view.View getProjectionReceiver() {
        for (android.view.ViewParent parent = getParent(); parent != null && (parent instanceof android.view.View); parent = parent.getParent()) {
            android.view.View view = (android.view.View) parent;
            if (view.isProjectionReceiver()) {
                return view;
            }
        }
        return null;
    }

    private boolean isProjectionReceiver() {
        return this.mBackground != null;
    }

    void invalidateViewProperty(boolean z, boolean z2) {
        if (!isHardwareAccelerated() || !this.mRenderNode.hasDisplayList() || (this.mPrivateFlags & 64) != 0) {
            if (z) {
                invalidateParentCaches();
            }
            if (z2) {
                this.mPrivateFlags |= 32;
            }
            invalidate(false);
            return;
        }
        damageInParent();
    }

    protected void damageInParent() {
        if (this.mParent != null && this.mAttachInfo != null) {
            if (sToolkitSetFrameRateReadOnlyFlagValue) {
                updateInfrequentCount();
                votePreferredFrameRate();
            }
            this.mParent.onDescendantInvalidated(this, this);
        }
    }

    protected void invalidateParentCaches() {
        if (this.mParent instanceof android.view.View) {
            ((android.view.View) this.mParent).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    protected void invalidateParentIfNeeded() {
        if (isHardwareAccelerated() && (this.mParent instanceof android.view.View)) {
            ((android.view.View) this.mParent).invalidate(true);
        }
    }

    protected void invalidateParentIfNeededAndWasQuickRejected() {
        if ((this.mPrivateFlags2 & 268435456) != 0) {
            invalidateParentIfNeeded();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean isOpaque() {
        return (this.mPrivateFlags & 25165824) == 25165824 && getFinalAlpha() >= 1.0f;
    }

    protected void computeOpaqueFlags() {
        int i;
        if (this.mBackground != null && this.mBackground.getOpacity() == -1) {
            this.mPrivateFlags |= 8388608;
        } else {
            this.mPrivateFlags &= -8388609;
        }
        int i2 = this.mViewFlags;
        if (((i2 & 512) == 0 && (i2 & 256) == 0) || (i = i2 & 50331648) == 0 || i == 33554432) {
            this.mPrivateFlags |= 16777216;
        } else {
            this.mPrivateFlags &= -16777217;
        }
    }

    protected boolean hasOpaqueScrollbars() {
        return (this.mPrivateFlags & 16777216) == 16777216;
    }

    public android.os.Handler getHandler() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler;
        }
        return null;
    }

    private android.view.HandlerActionQueue getRunQueue() {
        if (this.mRunQueue == null) {
            this.mRunQueue = new android.view.HandlerActionQueue();
        }
        return this.mRunQueue;
    }

    public android.view.ViewRootImpl getViewRootImpl() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mViewRootImpl;
        }
        return null;
    }

    public android.view.ThreadedRenderer getThreadedRenderer() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mThreadedRenderer;
        }
        return null;
    }

    public boolean post(java.lang.Runnable runnable) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.post(runnable);
        }
        getRunQueue().post(runnable);
        return true;
    }

    public boolean postDelayed(java.lang.Runnable runnable, long j) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.postDelayed(runnable, j);
        }
        getRunQueue().postDelayed(runnable, j);
        return true;
    }

    public void postOnAnimation(java.lang.Runnable runnable) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, runnable, null);
        } else {
            getRunQueue().post(runnable);
        }
    }

    public void postOnAnimationDelayed(java.lang.Runnable runnable, long j) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, null, j);
        } else {
            getRunQueue().postDelayed(runnable, j);
        }
    }

    public boolean removeCallbacks(java.lang.Runnable runnable) {
        if (runnable != null) {
            android.view.View.AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(runnable);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, null);
            }
            getRunQueue().removeCallbacks(runnable);
        }
        return true;
    }

    public void postInvalidate() {
        postInvalidateDelayed(0L);
    }

    public void postInvalidate(int i, int i2, int i3, int i4) {
        postInvalidateDelayed(0L, i, i2, i3, i4);
    }

    public void postInvalidateDelayed(long j) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateDelayed(this, j);
        }
    }

    public void postInvalidateDelayed(long j, int i, int i2, int i3, int i4) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            android.view.View.AttachInfo.InvalidateInfo obtain = android.view.View.AttachInfo.InvalidateInfo.obtain();
            obtain.target = this;
            obtain.left = i;
            obtain.top = i2;
            obtain.right = i3;
            obtain.bottom = i4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectDelayed(obtain, j);
        }
    }

    public void postInvalidateOnAnimation() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateOnAnimation(this);
        }
    }

    public void postInvalidateOnAnimation(int i, int i2, int i3, int i4) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            android.view.View.AttachInfo.InvalidateInfo obtain = android.view.View.AttachInfo.InvalidateInfo.obtain();
            obtain.target = this;
            obtain.left = i;
            obtain.top = i2;
            obtain.right = i3;
            obtain.bottom = i4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectOnAnimation(obtain);
        }
    }

    private void postSendViewScrolledAccessibilityEventCallback(int i, int i2) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(4096);
            obtain.setScrollDeltaX(i);
            obtain.setScrollDeltaY(i2);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public void computeScroll() {
    }

    public boolean isHorizontalFadingEdgeEnabled() {
        return (this.mViewFlags & 4096) == 4096;
    }

    public void setHorizontalFadingEdgeEnabled(boolean z) {
        if (isHorizontalFadingEdgeEnabled() != z) {
            if (z) {
                initScrollCache();
            }
            this.mViewFlags ^= 4096;
        }
    }

    public boolean isVerticalFadingEdgeEnabled() {
        return (this.mViewFlags & 8192) == 8192;
    }

    public void setVerticalFadingEdgeEnabled(boolean z) {
        if (isVerticalFadingEdgeEnabled() != z) {
            if (z) {
                initScrollCache();
            }
            this.mViewFlags ^= 8192;
        }
    }

    public int getFadingEdge() {
        return this.mViewFlags & 12288;
    }

    public int getFadingEdgeLength() {
        if (this.mScrollCache != null && (this.mViewFlags & 12288) != 0) {
            return this.mScrollCache.fadingEdgeLength;
        }
        return 0;
    }

    protected float getTopFadingEdgeStrength() {
        return computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getBottomFadingEdgeStrength() {
        return computeVerticalScrollOffset() + computeVerticalScrollExtent() < computeVerticalScrollRange() ? 1.0f : 0.0f;
    }

    protected float getLeftFadingEdgeStrength() {
        return computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
    }

    protected float getRightFadingEdgeStrength() {
        return computeHorizontalScrollOffset() + computeHorizontalScrollExtent() < computeHorizontalScrollRange() ? 1.0f : 0.0f;
    }

    public boolean isHorizontalScrollBarEnabled() {
        return (this.mViewFlags & 256) == 256;
    }

    public void setHorizontalScrollBarEnabled(boolean z) {
        if (isHorizontalScrollBarEnabled() != z) {
            this.mViewFlags ^= 256;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    public boolean isVerticalScrollBarEnabled() {
        return (this.mViewFlags & 512) == 512;
    }

    public void setVerticalScrollBarEnabled(boolean z) {
        if (isVerticalScrollBarEnabled() != z) {
            this.mViewFlags ^= 512;
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    protected void recomputePadding() {
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
    }

    public void setScrollbarFadingEnabled(boolean z) {
        initScrollCache();
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        scrollabilityCache.fadeScrollBars = z;
        if (z) {
            scrollabilityCache.state = 0;
        } else {
            scrollabilityCache.state = 1;
        }
    }

    public boolean isScrollbarFadingEnabled() {
        return this.mScrollCache != null && this.mScrollCache.fadeScrollBars;
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        return this.mScrollCache == null ? android.view.ViewConfiguration.getScrollDefaultDelay() : this.mScrollCache.scrollBarDefaultDelayBeforeFade;
    }

    public void setScrollBarDefaultDelayBeforeFade(int i) {
        getScrollCache().scrollBarDefaultDelayBeforeFade = i;
    }

    public int getScrollBarFadeDuration() {
        return this.mScrollCache == null ? android.view.ViewConfiguration.getScrollBarFadeDuration() : this.mScrollCache.scrollBarFadeDuration;
    }

    public void setScrollBarFadeDuration(int i) {
        getScrollCache().scrollBarFadeDuration = i;
    }

    public int getScrollBarSize() {
        return this.mScrollCache == null ? android.view.ViewConfiguration.get(this.mContext).getScaledScrollBarSize() : this.mScrollCache.scrollBarSize;
    }

    public void setScrollBarSize(int i) {
        getScrollCache().scrollBarSize = i;
    }

    public void setScrollBarStyle(int i) {
        if (i != (this.mViewFlags & 50331648)) {
            this.mViewFlags = (i & 50331648) | (this.mViewFlags & (-50331649));
            computeOpaqueFlags();
            resolvePadding();
        }
    }

    @android.view.ViewDebug.ExportedProperty(mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "INSIDE_OVERLAY"), @android.view.ViewDebug.IntToString(from = 16777216, to = "INSIDE_INSET"), @android.view.ViewDebug.IntToString(from = 33554432, to = "OUTSIDE_OVERLAY"), @android.view.ViewDebug.IntToString(from = 50331648, to = "OUTSIDE_INSET")})
    public int getScrollBarStyle() {
        return this.mViewFlags & 50331648;
    }

    protected int computeHorizontalScrollRange() {
        return getWidth();
    }

    protected int computeHorizontalScrollOffset() {
        return this.mScrollX;
    }

    protected int computeHorizontalScrollExtent() {
        return getWidth();
    }

    protected int computeVerticalScrollRange() {
        return getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mScrollY;
    }

    protected int computeVerticalScrollExtent() {
        return getHeight();
    }

    public boolean canScrollHorizontally(int i) {
        int computeHorizontalScrollOffset = computeHorizontalScrollOffset();
        int computeHorizontalScrollRange = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
        if (computeHorizontalScrollRange == 0) {
            return false;
        }
        if (i < 0) {
            if (computeHorizontalScrollOffset <= 0) {
                return false;
            }
            return true;
        }
        if (computeHorizontalScrollOffset >= computeHorizontalScrollRange - 1) {
            return false;
        }
        return true;
    }

    public boolean canScrollVertically(int i) {
        int computeVerticalScrollOffset = computeVerticalScrollOffset();
        int computeVerticalScrollRange = computeVerticalScrollRange() - computeVerticalScrollExtent();
        if (computeVerticalScrollRange == 0) {
            return false;
        }
        if (i < 0) {
            if (computeVerticalScrollOffset <= 0) {
                return false;
            }
            return true;
        }
        if (computeVerticalScrollOffset >= computeVerticalScrollRange - 1) {
            return false;
        }
        return true;
    }

    void getScrollIndicatorBounds(android.graphics.Rect rect) {
        rect.left = this.mScrollX;
        rect.right = (this.mScrollX + this.mRight) - this.mLeft;
        rect.top = this.mScrollY;
        rect.bottom = (this.mScrollY + this.mBottom) - this.mTop;
    }

    private void onDrawScrollIndicators(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable;
        if ((this.mPrivateFlags3 & SCROLL_INDICATORS_PFLAG3_MASK) == 0 || (drawable = this.mScrollIndicatorDrawable) == null || this.mAttachInfo == null) {
            return;
        }
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        android.graphics.Rect rect = this.mAttachInfo.mTmpInvalRect;
        getScrollIndicatorBounds(rect);
        if ((this.mPrivateFlags3 & 256) != 0 && canScrollVertically(-1)) {
            drawable.setBounds(rect.left, rect.top, rect.right, rect.top + intrinsicHeight);
            drawable.draw(canvas);
        }
        if ((this.mPrivateFlags3 & 512) != 0 && canScrollVertically(1)) {
            drawable.setBounds(rect.left, rect.bottom - intrinsicHeight, rect.right, rect.bottom);
            drawable.draw(canvas);
        }
        int i = 8192;
        int i2 = 4096;
        if (getLayoutDirection() != 1) {
            i2 = 8192;
            i = 4096;
        }
        if (((i | 1024) & this.mPrivateFlags3) != 0 && canScrollHorizontally(-1)) {
            drawable.setBounds(rect.left, rect.top, rect.left + intrinsicWidth, rect.bottom);
            drawable.draw(canvas);
        }
        if (((i2 | 2048) & this.mPrivateFlags3) != 0 && canScrollHorizontally(1)) {
            drawable.setBounds(rect.right - intrinsicWidth, rect.top, rect.right, rect.bottom);
            drawable.draw(canvas);
        }
    }

    private void getHorizontalScrollBarBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int i = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        boolean z = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        int horizontalScrollbarHeight = getHorizontalScrollbarHeight();
        int verticalScrollbarWidth = z ? getVerticalScrollbarWidth() : 0;
        int i2 = this.mRight - this.mLeft;
        int i3 = this.mBottom - this.mTop;
        rect.top = ((this.mScrollY + i3) - horizontalScrollbarHeight) - (this.mUserPaddingBottom & i);
        rect.left = this.mScrollX + (this.mPaddingLeft & i);
        rect.right = ((this.mScrollX + i2) - (i & this.mUserPaddingRight)) - verticalScrollbarWidth;
        rect.bottom = rect.top + horizontalScrollbarHeight;
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        int i4 = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.height() < i4) {
            rect2.bottom = java.lang.Math.min(rect2.bottom + ((i4 - rect2.height()) / 2), this.mScrollY + i3);
            rect2.top = rect2.bottom - i4;
        }
        if (rect2.width() < i4) {
            rect2.left -= (i4 - rect2.width()) / 2;
            rect2.right = rect2.left + i4;
        }
    }

    private void getVerticalScrollBarBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (this.mRoundScrollbarRenderer == null) {
            getStraightVerticalScrollBarBounds(rect, rect2);
            return;
        }
        android.view.RoundScrollbarRenderer roundScrollbarRenderer = this.mRoundScrollbarRenderer;
        if (rect == null) {
            rect = rect2;
        }
        roundScrollbarRenderer.getRoundVerticalScrollBarBounds(rect);
    }

    private void getStraightVerticalScrollBarBounds(android.graphics.Rect rect, android.graphics.Rect rect2) {
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int i = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        int verticalScrollbarWidth = getVerticalScrollbarWidth();
        int i2 = this.mVerticalScrollbarPosition;
        if (i2 == 0) {
            if (!isLayoutRtl()) {
                i2 = 2;
            } else {
                i2 = 1;
            }
        }
        int i3 = this.mRight - this.mLeft;
        int i4 = this.mBottom - this.mTop;
        switch (i2) {
            case 1:
                rect.left = this.mScrollX + (this.mUserPaddingLeft & i);
                break;
            default:
                rect.left = ((this.mScrollX + i3) - verticalScrollbarWidth) - (this.mUserPaddingRight & i);
                break;
        }
        rect.top = this.mScrollY + (this.mPaddingTop & i);
        rect.right = rect.left + verticalScrollbarWidth;
        rect.bottom = (this.mScrollY + i4) - (i & this.mUserPaddingBottom);
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        int i5 = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.width() < i5) {
            int width = (i5 - rect2.width()) / 2;
            if (i2 == 2) {
                rect2.right = java.lang.Math.min(rect2.right + width, this.mScrollX + i3);
                rect2.left = rect2.right - i5;
            } else {
                rect2.left = java.lang.Math.max(rect2.left + width, this.mScrollX);
                rect2.right = rect2.left + i5;
            }
        }
        if (rect2.height() < i5) {
            rect2.top -= (i5 - rect2.height()) / 2;
            rect2.bottom = rect2.top + i5;
        }
    }

    protected final void onDrawScrollBars(android.graphics.Canvas canvas) {
        int i;
        boolean z;
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache == null || (i = scrollabilityCache.state) == 0) {
            return;
        }
        boolean z2 = true;
        if (i == 2) {
            if (scrollabilityCache.interpolatorValues == null) {
                scrollabilityCache.interpolatorValues = new float[1];
            }
            float[] fArr = scrollabilityCache.interpolatorValues;
            if (scrollabilityCache.scrollBarInterpolator.timeToValues(fArr) == android.graphics.Interpolator.Result.FREEZE_END) {
                scrollabilityCache.state = 0;
            } else {
                scrollabilityCache.scrollBar.mutate().setAlpha(java.lang.Math.round(fArr[0]));
            }
            z = true;
        } else {
            scrollabilityCache.scrollBar.mutate().setAlpha(255);
            z = false;
        }
        boolean isHorizontalScrollBarEnabled = isHorizontalScrollBarEnabled();
        boolean z3 = isVerticalScrollBarEnabled() && !isVerticalScrollBarHidden();
        if (this.mRoundScrollbarRenderer != null) {
            if (z3) {
                android.graphics.Rect rect = scrollabilityCache.mScrollBarBounds;
                getVerticalScrollBarBounds(rect, null);
                if (this.mVerticalScrollbarPosition != 1 && (this.mVerticalScrollbarPosition != 0 || !isLayoutRtl())) {
                    z2 = false;
                }
                this.mRoundScrollbarRenderer.drawRoundScrollbars(canvas, scrollabilityCache.scrollBar.getAlpha() / 255.0f, rect, z2);
                if (z) {
                    invalidate();
                    return;
                }
                return;
            }
            return;
        }
        if (z3 || isHorizontalScrollBarEnabled) {
            android.widget.ScrollBarDrawable scrollBarDrawable = scrollabilityCache.scrollBar;
            if (isHorizontalScrollBarEnabled) {
                scrollBarDrawable.setParameters(computeHorizontalScrollRange(), computeHorizontalScrollOffset(), computeHorizontalScrollExtent(), false);
                android.graphics.Rect rect2 = scrollabilityCache.mScrollBarBounds;
                getHorizontalScrollBarBounds(rect2, null);
                onDrawHorizontalScrollBar(canvas, scrollBarDrawable, rect2.left, rect2.top, rect2.right, rect2.bottom);
                if (z) {
                    invalidate(rect2);
                }
            }
            if (z3) {
                scrollBarDrawable.setParameters(computeVerticalScrollRange(), computeVerticalScrollOffset(), computeVerticalScrollExtent(), true);
                android.graphics.Rect rect3 = scrollabilityCache.mScrollBarBounds;
                getVerticalScrollBarBounds(rect3, null);
                onDrawVerticalScrollBar(canvas, scrollBarDrawable, rect3.left, rect3.top, rect3.right, rect3.bottom);
                if (z) {
                    invalidate(rect3);
                }
            }
        }
    }

    protected boolean isVerticalScrollBarHidden() {
        return false;
    }

    protected void onDrawHorizontalScrollBar(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setBounds(i, i2, i3, i4);
        drawable.draw(canvas);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onDrawVerticalScrollBar(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setBounds(i, i2, i3, i4);
        drawable.draw(canvas);
    }

    protected void onDraw(android.graphics.Canvas canvas) {
    }

    void assignParent(android.view.ViewParent viewParent) {
        if (this.mParent == null) {
            this.mParent = viewParent;
        } else {
            if (viewParent == null) {
                this.mParent = null;
                return;
            }
            throw new java.lang.RuntimeException("view " + this + " being added, but it already has a parent");
        }
    }

    protected void onAttachedToWindow() {
        if (this.mParent != null && (this.mPrivateFlags & 512) != 0) {
            this.mParent.requestTransparentRegion(this);
        }
        this.mPrivateFlags3 &= -5;
        jumpDrawablesToCurrentState();
        android.view.accessibility.AccessibilityNodeIdManager.getInstance().registerViewWithId(this, getAccessibilityViewId());
        resetSubtreeAccessibilityStateChanged();
        rebuildOutline();
        if (isFocused()) {
            notifyFocusChangeToImeFocusController(true);
        }
        if (sTraceLayoutSteps) {
            setTraversalTracingEnabled(true);
        }
        if (sTraceRequestLayoutClass != null && sTraceRequestLayoutClass.equals(getClass().getSimpleName())) {
            setRelayoutTracingEnabled(true);
        }
    }

    public boolean resolveRtlPropertiesIfNeeded() {
        if (!needRtlPropertiesResolution()) {
            return false;
        }
        if (!isLayoutDirectionResolved()) {
            resolveLayoutDirection();
            resolveLayoutParams();
        }
        if (!isTextDirectionResolved()) {
            resolveTextDirection();
        }
        if (!isTextAlignmentResolved()) {
            resolveTextAlignment();
        }
        if (!areDrawablesResolved()) {
            resolveDrawables();
        }
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        onRtlPropertiesChanged(getLayoutDirection());
        return true;
    }

    public void resetRtlProperties() {
        resetResolvedLayoutDirection();
        resetResolvedTextDirection();
        resetResolvedTextAlignment();
        resetResolvedPadding();
        resetResolvedDrawables();
    }

    void dispatchScreenStateChanged(int i) {
        onScreenStateChanged(i);
    }

    public void onScreenStateChanged(int i) {
    }

    void dispatchMovedToDisplay(android.view.Display display, android.content.res.Configuration configuration) {
        this.mAttachInfo.mDisplay = display;
        this.mAttachInfo.mDisplayState = display.getState();
        onMovedToDisplay(display.getDisplayId(), configuration);
    }

    public void onMovedToDisplay(int i, android.content.res.Configuration configuration) {
    }

    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean isRtlCompatibilityMode() {
        return getContext().getApplicationInfo().targetSdkVersion < 17 || !hasRtlSupport();
    }

    private boolean needRtlPropertiesResolution() {
        return (this.mPrivateFlags2 & ALL_RTL_PROPERTIES_RESOLVED) != ALL_RTL_PROPERTIES_RESOLVED;
    }

    public void onRtlPropertiesChanged(int i) {
    }

    public boolean resolveLayoutDirection() {
        this.mPrivateFlags2 &= -49;
        if (hasRtlSupport()) {
            switch ((this.mPrivateFlags2 & 12) >> 2) {
                case 1:
                    this.mPrivateFlags2 |= 16;
                    break;
                case 2:
                    if (!canResolveLayoutDirection()) {
                        return false;
                    }
                    try {
                        if (!this.mParent.isLayoutDirectionResolved()) {
                            return false;
                        }
                        if (this.mParent.getLayoutDirection() == 1) {
                            this.mPrivateFlags2 |= 16;
                            break;
                        }
                    } catch (java.lang.AbstractMethodError e) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        break;
                    }
                    break;
                case 3:
                    if (1 == android.text.TextUtils.getLayoutDirectionFromLocale(java.util.Locale.getDefault())) {
                        this.mPrivateFlags2 |= 16;
                        break;
                    }
                    break;
            }
        }
        this.mPrivateFlags2 |= 32;
        return true;
    }

    public boolean canResolveLayoutDirection() {
        switch (getRawLayoutDirection()) {
            case 2:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveLayoutDirection();
                    } catch (java.lang.AbstractMethodError e) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedLayoutDirection() {
        this.mPrivateFlags2 &= -49;
    }

    public boolean isLayoutDirectionInherited() {
        return getRawLayoutDirection() == 2;
    }

    public boolean isLayoutDirectionResolved() {
        return (this.mPrivateFlags2 & 32) == 32;
    }

    boolean isPaddingResolved() {
        return (this.mPrivateFlags2 & 536870912) == 536870912;
    }

    public void resolvePadding() {
        int layoutDirection = getLayoutDirection();
        if (!isRtlCompatibilityMode()) {
            if (this.mBackground != null && (!this.mLeftPaddingDefined || !this.mRightPaddingDefined)) {
                android.graphics.Rect rect = sThreadLocal.get();
                if (rect == null) {
                    rect = new android.graphics.Rect();
                    sThreadLocal.set(rect);
                }
                this.mBackground.getPadding(rect);
                if (!this.mLeftPaddingDefined) {
                    this.mUserPaddingLeftInitial = rect.left;
                }
                if (!this.mRightPaddingDefined) {
                    this.mUserPaddingRightInitial = rect.right;
                }
            }
            switch (layoutDirection) {
                case 1:
                    if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                        this.mUserPaddingRight = this.mUserPaddingStart;
                    } else {
                        this.mUserPaddingRight = this.mUserPaddingRightInitial;
                    }
                    if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                        this.mUserPaddingLeft = this.mUserPaddingEnd;
                        break;
                    } else {
                        this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                        break;
                    }
                default:
                    if (this.mUserPaddingStart != Integer.MIN_VALUE) {
                        this.mUserPaddingLeft = this.mUserPaddingStart;
                    } else {
                        this.mUserPaddingLeft = this.mUserPaddingLeftInitial;
                    }
                    if (this.mUserPaddingEnd != Integer.MIN_VALUE) {
                        this.mUserPaddingRight = this.mUserPaddingEnd;
                        break;
                    } else {
                        this.mUserPaddingRight = this.mUserPaddingRightInitial;
                        break;
                    }
            }
            this.mUserPaddingBottom = this.mUserPaddingBottom >= 0 ? this.mUserPaddingBottom : this.mPaddingBottom;
        }
        internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
        onRtlPropertiesChanged(layoutDirection);
        this.mPrivateFlags2 |= 536870912;
    }

    public void resetResolvedPadding() {
        resetResolvedPaddingInternal();
    }

    void resetResolvedPaddingInternal() {
        this.mPrivateFlags2 &= -536870913;
    }

    protected void onDetachedFromWindow() {
    }

    protected void onDetachedFromWindowInternal() {
        this.mPrivateFlags &= -67108865;
        this.mPrivateFlags3 &= -5;
        this.mPrivateFlags3 &= -33554433;
        removeUnsetPressCallback();
        removeLongPressCallback();
        removePerformClickCallback();
        clearAccessibilityThrottles();
        stopNestedScroll();
        jumpDrawablesToCurrentState();
        destroyDrawingCache();
        cleanupDraw();
        this.mCurrentAnimation = null;
        if ((this.mViewFlags & 1073741824) == 1073741824) {
            removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
            removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
            hideTooltip();
        }
        android.view.accessibility.AccessibilityNodeIdManager.getInstance().unregisterViewWithId(getAccessibilityViewId());
        if (this.mBackgroundRenderNode != null) {
            this.mBackgroundRenderNode.forceEndAnimators();
        }
        this.mRenderNode.forceEndAnimators();
    }

    private void cleanupDraw() {
        resetDisplayList();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mViewRootImpl.cancelInvalidate(this);
        }
    }

    void invalidateInheritedLayoutMode(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getWindowAttachCount() {
        return this.mWindowAttachCount;
    }

    public android.os.IBinder getWindowToken() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindowToken;
        }
        return null;
    }

    public android.view.WindowId getWindowId() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        if (attachInfo.mWindowId == null) {
            try {
                attachInfo.mIWindowId = attachInfo.mSession.getWindowId(attachInfo.mWindowToken);
                if (attachInfo.mIWindowId != null) {
                    attachInfo.mWindowId = new android.view.WindowId(attachInfo.mIWindowId);
                }
            } catch (android.os.RemoteException e) {
            }
        }
        return attachInfo.mWindowId;
    }

    public android.os.IBinder getApplicationWindowToken() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            android.os.IBinder iBinder = attachInfo.mPanelParentWindowToken;
            if (iBinder == null) {
                return attachInfo.mWindowToken;
            }
            return iBinder;
        }
        return null;
    }

    public android.view.Display getDisplay() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDisplay;
        }
        return null;
    }

    android.view.IWindowSession getWindowSession() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSession;
        }
        return null;
    }

    protected android.view.IWindow getWindow() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mWindow;
        }
        return null;
    }

    int combineVisibility(int i, int i2) {
        return java.lang.Math.max(i, i2);
    }

    public void fakeFocusAfterAttachingToWindow() {
        this.mShouldFakeFocus = true;
    }

    void dispatchAttachedToWindow(android.view.View.AttachInfo attachInfo, int i) {
        this.mAttachInfo = attachInfo;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchAttachedToWindow(attachInfo, i);
        }
        this.mWindowAttachCount++;
        this.mPrivateFlags |= 1024;
        if (this.mFloatingTreeObserver != null) {
            attachInfo.mTreeObserver.merge(this.mFloatingTreeObserver);
            this.mFloatingTreeObserver = null;
        }
        registerPendingFrameMetricsObservers();
        if ((this.mPrivateFlags & 524288) != 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags |= 1048576;
        }
        if (this.mRunQueue != null) {
            this.mRunQueue.executeActions(attachInfo.mHandler);
            this.mRunQueue = null;
        }
        performCollectViewAttributes(this.mAttachInfo, i);
        onAttachedToWindow();
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        java.util.concurrent.CopyOnWriteArrayList copyOnWriteArrayList = listenerInfo != null ? listenerInfo.mOnAttachStateChangeListeners : null;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((android.view.View.OnAttachStateChangeListener) it.next()).onViewAttachedToWindow(this);
            }
        }
        int i2 = attachInfo.mWindowVisibility;
        if (i2 != 8) {
            onWindowVisibilityChanged(i2);
            if (isShown()) {
                onVisibilityAggregated(i2 == 0);
            }
        }
        onVisibilityChanged(this, i);
        if ((this.mPrivateFlags & 1024) != 0) {
            refreshDrawableState();
        }
        needGlobalAttributesUpdate(false);
        notifyEnterOrExitForAutoFillIfNeeded(true);
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
        if (this.mShouldFakeFocus) {
            getViewRootImpl().dispatchCompatFakeFocus();
            this.mShouldFakeFocus = false;
        }
    }

    void dispatchDetachedFromWindow() {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mWindowVisibility != 8) {
            onWindowVisibilityChanged(8);
            if (isShown()) {
                onVisibilityAggregated(false);
            } else {
                notifyAutofillManagerViewVisibilityChanged(false);
            }
        }
        onDetachedFromWindow();
        onDetachedFromWindowInternal();
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.getImeFocusController().onViewDetachedFromWindow(this);
        }
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        java.util.concurrent.CopyOnWriteArrayList copyOnWriteArrayList = listenerInfo != null ? listenerInfo.mOnAttachStateChangeListeners : null;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            java.util.Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((android.view.View.OnAttachStateChangeListener) it.next()).onViewDetachedFromWindow(this);
            }
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
            this.mPrivateFlags &= -1048577;
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(false);
        updateSensitiveViewsCountIfNeeded(false);
        this.mAttachInfo = null;
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().dispatchDetachedFromWindow();
        }
        notifyEnterOrExitForAutoFillIfNeeded(false);
        if (attachInfo != null && !collectPreferKeepClearRects().isEmpty()) {
            attachInfo.mViewRootImpl.updateKeepClearRectsForView(this);
        }
    }

    public final void cancelPendingInputEvents() {
        dispatchCancelPendingInputEvents();
    }

    void dispatchCancelPendingInputEvents() {
        this.mPrivateFlags3 &= -17;
        onCancelPendingInputEvents();
        if ((this.mPrivateFlags3 & 16) != 16) {
            throw new android.util.SuperNotCalledException("View " + getClass().getSimpleName() + " did not call through to super.onCancelPendingInputEvents()");
        }
    }

    public void onCancelPendingInputEvents() {
        removePerformClickCallback();
        cancelLongPress();
        this.mPrivateFlags3 |= 16;
    }

    public void saveHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchSaveInstanceState(sparseArray);
    }

    protected void dispatchSaveInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        if (this.mID != -1 && (this.mViewFlags & 65536) == 0) {
            this.mPrivateFlags &= -131073;
            android.os.Parcelable onSaveInstanceState = onSaveInstanceState();
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new java.lang.IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
            if (onSaveInstanceState != null) {
                sparseArray.put(this.mID, onSaveInstanceState);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public android.os.Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho != null || isAutofilled() || this.mAutofillViewId > 1073741823) {
            android.view.View.BaseSavedState baseSavedState = new android.view.View.BaseSavedState(android.view.AbsSavedState.EMPTY_STATE);
            if (this.mStartActivityRequestWho != null) {
                baseSavedState.mSavedData |= 1;
            }
            if (isAutofilled()) {
                baseSavedState.mSavedData |= 2;
            }
            if (this.mAutofillViewId > 1073741823) {
                baseSavedState.mSavedData |= 4;
            }
            baseSavedState.mStartActivityRequestWhoSaved = this.mStartActivityRequestWho;
            baseSavedState.mIsAutofilled = isAutofilled();
            baseSavedState.mHideHighlight = hideAutofillHighlight();
            baseSavedState.mAutofillViewId = this.mAutofillViewId;
            return baseSavedState;
        }
        return android.view.View.BaseSavedState.EMPTY_STATE;
    }

    public void restoreHierarchyState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        dispatchRestoreInstanceState(sparseArray);
    }

    protected void dispatchRestoreInstanceState(android.util.SparseArray<android.os.Parcelable> sparseArray) {
        android.os.Parcelable parcelable;
        if (this.mID != -1 && (parcelable = sparseArray.get(this.mID)) != null) {
            this.mPrivateFlags &= -131073;
            onRestoreInstanceState(parcelable);
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new java.lang.IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        this.mPrivateFlags |= 131072;
        if (parcelable != null && !(parcelable instanceof android.view.AbsSavedState)) {
            throw new java.lang.IllegalArgumentException("Wrong state class, expecting View State but received " + parcelable.getClass().toString() + " instead. This usually happens when two views of different type have the same id in the same hierarchy. This view's id is " + android.view.ViewDebug.resolveId(this.mContext, getId()) + ". Make sure other views do not use the same id.");
        }
        if (parcelable != null && (parcelable instanceof android.view.View.BaseSavedState)) {
            android.view.View.BaseSavedState baseSavedState = (android.view.View.BaseSavedState) parcelable;
            if ((baseSavedState.mSavedData & 1) != 0) {
                this.mStartActivityRequestWho = baseSavedState.mStartActivityRequestWhoSaved;
            }
            if ((baseSavedState.mSavedData & 2) != 0) {
                setAutofilled(baseSavedState.mIsAutofilled, baseSavedState.mHideHighlight);
            }
            if ((baseSavedState.mSavedData & 4) != 0) {
                baseSavedState.mSavedData &= -5;
                if ((this.mPrivateFlags3 & 1073741824) != 0) {
                    if (android.util.Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                        android.util.Log.d(AUTOFILL_LOG_TAG, "onRestoreInstanceState(): not setting autofillId to " + baseSavedState.mAutofillViewId + " because view explicitly set it to " + this.mAutofillId);
                    }
                } else {
                    this.mAutofillViewId = baseSavedState.mAutofillViewId;
                    this.mAutofillId = null;
                }
            }
        }
    }

    public long getDrawingTime() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mDrawingTime;
        }
        return 0L;
    }

    public void setDuplicateParentStateEnabled(boolean z) {
        setFlags(z ? 4194304 : 0, 4194304);
    }

    public boolean isDuplicateParentStateEnabled() {
        return (this.mViewFlags & 4194304) == 4194304;
    }

    public void setLayerType(int i, android.graphics.Paint paint) {
        if (i < 0 || i > 2) {
            throw new java.lang.IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
        }
        if (!this.mRenderNode.setLayerType(i)) {
            setLayerPaint(paint);
            return;
        }
        if (i != 1) {
            destroyDrawingCache();
        }
        this.mLayerType = i;
        if (this.mLayerType == 0) {
            paint = null;
        }
        this.mLayerPaint = paint;
        this.mRenderNode.setLayerPaint(this.mLayerPaint);
        invalidateParentCaches();
        invalidate(true);
    }

    public void setRenderEffect(android.graphics.RenderEffect renderEffect) {
        if (this.mRenderNode.setRenderEffect(renderEffect)) {
            invalidateViewProperty(true, true);
        }
    }

    public void setBackdropRenderEffect(android.graphics.RenderEffect renderEffect) {
        if (this.mRenderNode.setBackdropRenderEffect(renderEffect)) {
            invalidateViewProperty(true, true);
        }
    }

    public void setLayerPaint(android.graphics.Paint paint) {
        int layerType = getLayerType();
        if (layerType != 0) {
            this.mLayerPaint = paint;
            if (layerType == 2) {
                if (this.mRenderNode.setLayerPaint(paint)) {
                    invalidateViewProperty(false, false);
                    return;
                }
                return;
            }
            invalidate();
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = android.security.keystore.KeyProperties.DIGEST_NONE), @android.view.ViewDebug.IntToString(from = 1, to = "SOFTWARE"), @android.view.ViewDebug.IntToString(from = 2, to = "HARDWARE")})
    public int getLayerType() {
        return this.mLayerType;
    }

    public void buildLayer() {
        if (this.mLayerType == 0) {
            return;
        }
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            throw new java.lang.IllegalStateException("This view must be attached to a window first");
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        switch (this.mLayerType) {
            case 1:
                buildDrawingCache(true);
                return;
            case 2:
                updateDisplayListIfDirty();
                if (attachInfo.mThreadedRenderer != null && this.mRenderNode.hasDisplayList()) {
                    attachInfo.mThreadedRenderer.buildLayer(this.mRenderNode);
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean probablyHasInput() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.probablyHasInput();
    }

    protected void destroyHardwareResources() {
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().destroyHardwareResources();
        }
        if (this.mGhostView != null) {
            this.mGhostView.destroyHardwareResources();
        }
    }

    @java.lang.Deprecated
    public void setDrawingCacheEnabled(boolean z) {
        int i = 0;
        this.mCachingFailed = false;
        if (z) {
            i = 32768;
        }
        setFlags(i, 32768);
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    @java.lang.Deprecated
    public boolean isDrawingCacheEnabled() {
        return (this.mViewFlags & 32768) == 32768;
    }

    public void outputDirtyFlags(java.lang.String str, boolean z, int i) {
        android.util.Log.d(VIEW_LOG_TAG, str + this + "             DIRTY(" + (this.mPrivateFlags & 2097152) + ") DRAWN(" + (this.mPrivateFlags & 32) + ") CACHE_VALID(" + (this.mPrivateFlags & 32768) + ") INVALIDATED(" + (this.mPrivateFlags & Integer.MIN_VALUE) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        if (z) {
            this.mPrivateFlags &= i;
        }
        if (this instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) this;
            int childCount = viewGroup.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                viewGroup.getChildAt(i2).outputDirtyFlags(str + "  ", z, i);
            }
        }
    }

    protected void dispatchGetDisplayList() {
    }

    public boolean canHaveDisplayList() {
        return (this.mAttachInfo == null || this.mAttachInfo.mThreadedRenderer == null) ? false : true;
    }

    public android.graphics.RenderNode updateDisplayListIfDirty() {
        android.graphics.RenderNode renderNode = this.mRenderNode;
        if (!canHaveDisplayList()) {
            return renderNode;
        }
        if ((this.mPrivateFlags & 32768) == 0 || !renderNode.hasDisplayList() || this.mRecreateDisplayList) {
            if (renderNode.hasDisplayList() && !this.mRecreateDisplayList) {
                this.mPrivateFlags |= 32800;
                this.mPrivateFlags &= -2097153;
                dispatchGetDisplayList();
                return renderNode;
            }
            this.mRecreateDisplayList = true;
            int i = this.mRight - this.mLeft;
            int i2 = this.mBottom - this.mTop;
            int layerType = getLayerType();
            renderNode.clearStretch();
            android.graphics.RecordingCanvas beginRecording = renderNode.beginRecording(i, i2);
            try {
                if (layerType == 1) {
                    buildDrawingCache(true);
                    android.graphics.Bitmap drawingCache = getDrawingCache(true);
                    if (drawingCache != null) {
                        beginRecording.drawBitmap(drawingCache, 0.0f, 0.0f, this.mLayerPaint);
                    }
                } else {
                    computeScroll();
                    beginRecording.translate(-this.mScrollX, -this.mScrollY);
                    this.mPrivateFlags |= 32800;
                    this.mPrivateFlags &= -2097153;
                    if ((this.mPrivateFlags & 128) == 128) {
                        dispatchDraw(beginRecording);
                        drawAutofilledHighlight(beginRecording);
                        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                            this.mOverlay.getOverlayView().draw(beginRecording);
                        }
                        if (isShowingLayoutBounds()) {
                            debugDrawFocus(beginRecording);
                        }
                    } else {
                        draw(beginRecording);
                    }
                }
            } finally {
                renderNode.endRecording();
                setDisplayListProperties(renderNode);
            }
        } else {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
        }
        return renderNode;
    }

    private void resetDisplayList() {
        this.mRenderNode.discardDisplayList();
        if (this.mBackgroundRenderNode != null) {
            this.mBackgroundRenderNode.discardDisplayList();
        }
    }

    @java.lang.Deprecated
    public android.graphics.Bitmap getDrawingCache() {
        return getDrawingCache(false);
    }

    @java.lang.Deprecated
    public android.graphics.Bitmap getDrawingCache(boolean z) {
        if ((this.mViewFlags & 131072) == 131072) {
            return null;
        }
        if ((this.mViewFlags & 32768) == 32768) {
            buildDrawingCache(z);
        }
        return z ? this.mDrawingCache : this.mUnscaledDrawingCache;
    }

    @java.lang.Deprecated
    public void destroyDrawingCache() {
        if (this.mDrawingCache != null) {
            this.mDrawingCache.recycle();
            this.mDrawingCache = null;
        }
        if (this.mUnscaledDrawingCache != null) {
            this.mUnscaledDrawingCache.recycle();
            this.mUnscaledDrawingCache = null;
        }
    }

    @java.lang.Deprecated
    public void setDrawingCacheBackgroundColor(int i) {
        if (i != this.mDrawingCacheBackgroundColor) {
            this.mDrawingCacheBackgroundColor = i;
            this.mPrivateFlags &= -32769;
        }
    }

    @java.lang.Deprecated
    public int getDrawingCacheBackgroundColor() {
        return this.mDrawingCacheBackgroundColor;
    }

    @java.lang.Deprecated
    public void buildDrawingCache() {
        buildDrawingCache(false);
    }

    @java.lang.Deprecated
    public void buildDrawingCache(boolean z) {
        if ((this.mPrivateFlags & 32768) != 0) {
            if (z) {
                if (this.mDrawingCache != null) {
                    return;
                }
            } else if (this.mUnscaledDrawingCache != null) {
                return;
            }
        }
        if (android.os.Trace.isTagEnabled(8L)) {
            android.os.Trace.traceBegin(8L, "buildDrawingCache/SW Layer for " + getClass().getSimpleName());
        }
        try {
            buildDrawingCacheImpl(z);
        } finally {
            android.os.Trace.traceEnd(8L);
        }
    }

    private void buildDrawingCacheImpl(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        android.graphics.Bitmap.Config config;
        android.graphics.Canvas canvas;
        boolean z5 = false;
        this.mCachingFailed = false;
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        boolean z6 = true;
        if (attachInfo == null || !attachInfo.mScalingRequired) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z && z2) {
            i = (int) ((i * attachInfo.mApplicationScale) + 0.5f);
            i2 = (int) ((i2 * attachInfo.mApplicationScale) + 0.5f);
        }
        int i3 = this.mDrawingCacheBackgroundColor;
        if (i3 == 0 && !isOpaque()) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (attachInfo == null || !attachInfo.mUse32BitDrawingCache) {
            z4 = false;
        } else {
            z4 = true;
        }
        long j = i * i2 * ((!z3 || z4) ? 4 : 2);
        long scaledMaximumDrawingCacheSize = android.view.ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
        if (i <= 0 || i2 <= 0 || j > scaledMaximumDrawingCacheSize) {
            if (i > 0 && i2 > 0) {
                android.util.Log.w(VIEW_LOG_TAG, getClass().getSimpleName() + " not displayed because it is too large to fit into a software layer (or drawing cache), needs " + j + " bytes, only " + scaledMaximumDrawingCacheSize + " available");
            }
            destroyDrawingCache();
            this.mCachingFailed = true;
            return;
        }
        android.graphics.Bitmap bitmap = z ? this.mDrawingCache : this.mUnscaledDrawingCache;
        if (bitmap == null || bitmap.getWidth() != i || bitmap.getHeight() != i2) {
            if (!z3) {
                config = android.graphics.Bitmap.Config.ARGB_8888;
            } else {
                config = z4 ? android.graphics.Bitmap.Config.ARGB_8888 : android.graphics.Bitmap.Config.RGB_565;
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
            try {
                bitmap = android.graphics.Bitmap.createBitmap(this.mResources.getDisplayMetrics(), i, i2, config);
                bitmap.setDensity(getResources().getDisplayMetrics().densityDpi);
                if (z) {
                    this.mDrawingCache = bitmap;
                } else {
                    this.mUnscaledDrawingCache = bitmap;
                }
                if (z3 && z4) {
                    bitmap.setHasAlpha(false);
                }
                if (i3 != 0) {
                    z5 = true;
                }
                z6 = z5;
            } catch (java.lang.OutOfMemoryError e) {
                if (z) {
                    this.mDrawingCache = null;
                } else {
                    this.mUnscaledDrawingCache = null;
                }
                this.mCachingFailed = true;
                return;
            }
        }
        if (attachInfo != null) {
            canvas = attachInfo.mCanvas;
            if (canvas == null) {
                canvas = new android.graphics.Canvas();
            }
            canvas.setBitmap(bitmap);
            attachInfo.mCanvas = null;
        } else {
            canvas = new android.graphics.Canvas(bitmap);
        }
        if (z6) {
            bitmap.eraseColor(i3);
        }
        computeScroll();
        int save = canvas.save();
        if (z && z2) {
            float f = attachInfo.mApplicationScale;
            canvas.scale(f, f);
        }
        canvas.translate(-this.mScrollX, -this.mScrollY);
        this.mPrivateFlags |= 32;
        if (this.mAttachInfo == null || !this.mAttachInfo.mHardwareAccelerated || this.mLayerType != 0) {
            this.mPrivateFlags |= 32768;
        }
        if ((this.mPrivateFlags & 128) == 128) {
            this.mPrivateFlags &= -2097153;
            dispatchDraw(canvas);
            drawAutofilledHighlight(canvas);
            if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().draw(canvas);
            }
        } else {
            draw(canvas);
        }
        canvas.restoreToCount(save);
        canvas.setBitmap(null);
        if (attachInfo != null) {
            attachInfo.mCanvas = canvas;
        }
    }

    public android.graphics.Bitmap createSnapshot(android.view.ViewDebug.CanvasProvider canvasProvider, boolean z) {
        int i = this.mRight - this.mLeft;
        int i2 = this.mBottom - this.mTop;
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        float f = attachInfo != null ? attachInfo.mApplicationScale : 1.0f;
        int i3 = (int) ((i * f) + 0.5f);
        int i4 = (int) ((i2 * f) + 0.5f);
        if (i3 <= 0) {
            i3 = 1;
        }
        if (i4 <= 0) {
            i4 = 1;
        }
        android.graphics.Canvas canvas = null;
        try {
            android.graphics.Canvas canvas2 = canvasProvider.getCanvas(this, i3, i4);
            if (attachInfo != null) {
                android.graphics.Canvas canvas3 = attachInfo.mCanvas;
                try {
                    attachInfo.mCanvas = null;
                    canvas = canvas3;
                } catch (java.lang.Throwable th) {
                    th = th;
                    canvas = canvas3;
                    if (canvas != null) {
                        attachInfo.mCanvas = canvas;
                    }
                    throw th;
                }
            }
            computeScroll();
            int save = canvas2.save();
            canvas2.scale(f, f);
            canvas2.translate(-this.mScrollX, -this.mScrollY);
            int i5 = this.mPrivateFlags;
            this.mPrivateFlags &= -2097153;
            if ((this.mPrivateFlags & 128) == 128) {
                dispatchDraw(canvas2);
                drawAutofilledHighlight(canvas2);
                if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(canvas2);
                }
            } else {
                draw(canvas2);
            }
            this.mPrivateFlags = i5;
            canvas2.restoreToCount(save);
            android.graphics.Bitmap createBitmap = canvasProvider.createBitmap();
            if (canvas != null) {
                attachInfo.mCanvas = canvas;
            }
            return createBitmap;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    public boolean isInEditMode() {
        return false;
    }

    protected boolean isPaddingOffsetRequired() {
        return false;
    }

    protected int getLeftPaddingOffset() {
        return 0;
    }

    protected int getRightPaddingOffset() {
        return 0;
    }

    protected int getTopPaddingOffset() {
        return 0;
    }

    protected int getBottomPaddingOffset() {
        return 0;
    }

    protected int getFadeTop(boolean z) {
        int i = this.mPaddingTop;
        return z ? i + getTopPaddingOffset() : i;
    }

    protected int getFadeHeight(boolean z) {
        int i = this.mPaddingTop;
        if (z) {
            i += getTopPaddingOffset();
        }
        return ((this.mBottom - this.mTop) - this.mPaddingBottom) - i;
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public boolean isHardwareAccelerated() {
        return this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated;
    }

    public void setClipBounds(android.graphics.Rect rect) {
        if (rect != this.mClipBounds) {
            if (rect != null && rect.equals(this.mClipBounds)) {
                return;
            }
            if (rect != null) {
                if (this.mClipBounds == null) {
                    this.mClipBounds = new android.graphics.Rect(rect);
                } else {
                    this.mClipBounds.set(rect);
                }
            } else {
                this.mClipBounds = null;
            }
            this.mRenderNode.setClipRect(this.mClipBounds);
            invalidateViewProperty(false, false);
        }
    }

    public android.graphics.Rect getClipBounds() {
        if (this.mClipBounds != null) {
            return new android.graphics.Rect(this.mClipBounds);
        }
        return null;
    }

    public boolean getClipBounds(android.graphics.Rect rect) {
        if (this.mClipBounds != null) {
            rect.set(this.mClipBounds);
            return true;
        }
        return false;
    }

    private boolean applyLegacyAnimation(android.view.ViewGroup viewGroup, long j, android.view.animation.Animation animation, boolean z) {
        android.view.animation.Transformation transformation;
        int i = viewGroup.mGroupFlags;
        if (!animation.isInitialized()) {
            animation.initialize(this.mRight - this.mLeft, this.mBottom - this.mTop, viewGroup.getWidth(), viewGroup.getHeight());
            animation.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            if (this.mAttachInfo != null) {
                animation.setListenerHandler(this.mAttachInfo.mHandler);
            }
            onAnimationStart();
        }
        android.view.animation.Transformation childTransformation = viewGroup.getChildTransformation();
        boolean transformation2 = animation.getTransformation(j, childTransformation, 1.0f);
        if (z && this.mAttachInfo.mApplicationScale != 1.0f) {
            if (viewGroup.mInvalidationTransformation == null) {
                viewGroup.mInvalidationTransformation = new android.view.animation.Transformation();
            }
            android.view.animation.Transformation transformation3 = viewGroup.mInvalidationTransformation;
            animation.getTransformation(j, transformation3, 1.0f);
            transformation = transformation3;
        } else {
            transformation = childTransformation;
        }
        if (transformation2) {
            if (!animation.willChangeBounds()) {
                if ((i & 144) == 128) {
                    viewGroup.mGroupFlags |= 4;
                } else if ((i & 4) == 0) {
                    viewGroup.mPrivateFlags |= 64;
                    viewGroup.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
                }
            } else {
                if (viewGroup.mInvalidateRegion == null) {
                    viewGroup.mInvalidateRegion = new android.graphics.RectF();
                }
                android.graphics.RectF rectF = viewGroup.mInvalidateRegion;
                animation.getInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, rectF, transformation);
                viewGroup.mPrivateFlags |= 64;
                int i2 = this.mLeft + ((int) rectF.left);
                int i3 = this.mTop + ((int) rectF.top);
                viewGroup.invalidate(i2, i3, ((int) (rectF.width() + 0.5f)) + i2, ((int) (rectF.height() + 0.5f)) + i3);
            }
        }
        return transformation2;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void setDisplayListProperties(android.graphics.RenderNode renderNode) {
        float f;
        int transformationType;
        if (renderNode != null) {
            renderNode.setHasOverlappingRendering(getHasOverlappingRendering());
            renderNode.setClipToBounds((this.mParent instanceof android.view.ViewGroup) && ((android.view.ViewGroup) this.mParent).getClipChildren());
            if ((this.mParent instanceof android.view.ViewGroup) && (((android.view.ViewGroup) this.mParent).mGroupFlags & 2048) != 0) {
                android.view.ViewGroup viewGroup = (android.view.ViewGroup) this.mParent;
                android.view.animation.Transformation childTransformation = viewGroup.getChildTransformation();
                if (viewGroup.getChildStaticTransformation(this, childTransformation) && (transformationType = childTransformation.getTransformationType()) != 0) {
                    if ((transformationType & 1) == 0) {
                        f = 1.0f;
                    } else {
                        f = childTransformation.getAlpha();
                    }
                    if ((transformationType & 2) != 0) {
                        renderNode.setStaticMatrix(childTransformation.getMatrix());
                    }
                    if (this.mTransformationInfo == null) {
                        float finalAlpha = f * getFinalAlpha();
                        renderNode.setAlpha((finalAlpha >= 1.0f || !onSetAlpha((int) (255.0f * finalAlpha))) ? finalAlpha : 1.0f);
                        return;
                    } else {
                        if (f < 1.0f) {
                            renderNode.setAlpha(f);
                            return;
                        }
                        return;
                    }
                }
            }
            f = 1.0f;
            if (this.mTransformationInfo == null) {
            }
        }
    }

    protected final boolean drawsWithRenderNode(android.graphics.Canvas canvas) {
        return this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerated && canvas.isHardwareAccelerated();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0318  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0361  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    boolean draw(android.graphics.Canvas canvas, android.view.ViewGroup viewGroup, long j) {
        android.graphics.RenderNode renderNode;
        android.view.animation.Animation animation;
        android.view.animation.Transformation transformation;
        boolean z;
        boolean z2;
        int i;
        android.graphics.RenderNode drawingCache;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        float alpha;
        android.graphics.RenderNode renderNode2;
        int i7;
        int i8;
        int i9;
        float f;
        int i10;
        float f2;
        android.graphics.RenderNode renderNode3;
        int i11;
        android.graphics.Bitmap bitmap;
        android.view.animation.Animation animation2;
        int i12;
        boolean isHardwareAccelerated = canvas.isHardwareAccelerated();
        boolean drawsWithRenderNode = drawsWithRenderNode(canvas);
        boolean hasIdentityMatrix = hasIdentityMatrix();
        int i13 = viewGroup.mGroupFlags;
        if ((i13 & 256) != 0) {
            viewGroup.getChildTransformation().clear();
            viewGroup.mGroupFlags &= -257;
        }
        boolean z3 = this.mAttachInfo != null && this.mAttachInfo.mScalingRequired;
        android.view.animation.Animation animation3 = getAnimation();
        if (animation3 != null) {
            renderNode = null;
            animation = animation3;
            boolean applyLegacyAnimation = applyLegacyAnimation(viewGroup, j, animation3, z3);
            boolean willChangeTransformationMatrix = animation.willChangeTransformationMatrix();
            if (willChangeTransformationMatrix) {
                this.mPrivateFlags3 |= 1;
            }
            transformation = viewGroup.getChildTransformation();
            z2 = applyLegacyAnimation;
            z = willChangeTransformationMatrix;
        } else {
            renderNode = null;
            animation = animation3;
            if ((this.mPrivateFlags3 & 1) != 0) {
                this.mRenderNode.setAnimationMatrix(null);
                this.mPrivateFlags3 &= -2;
            }
            if (!drawsWithRenderNode && (i13 & 2048) != 0) {
                android.view.animation.Transformation childTransformation = viewGroup.getChildTransformation();
                if (viewGroup.getChildStaticTransformation(this, childTransformation)) {
                    int transformationType = childTransformation.getTransformationType();
                    transformation = transformationType != 0 ? childTransformation : null;
                    z = (transformationType & 2) != 0;
                    z2 = false;
                }
            }
            transformation = null;
            z = false;
            z2 = false;
        }
        boolean z4 = z | (!hasIdentityMatrix);
        this.mPrivateFlags |= 32;
        if (z4 || (i13 & 2049) != 1 || !canvas.quickReject(this.mLeft, this.mTop, this.mRight, this.mBottom) || (this.mPrivateFlags & 64) != 0) {
            this.mPrivateFlags2 &= -268435457;
            if (isHardwareAccelerated) {
                this.mRecreateDisplayList = (this.mPrivateFlags & Integer.MIN_VALUE) != 0;
                this.mPrivateFlags &= Integer.MAX_VALUE;
            }
            int layerType = getLayerType();
            if (layerType == 1 || !drawsWithRenderNode) {
                if (layerType != 0) {
                    buildDrawingCache(true);
                    layerType = 1;
                }
                i = layerType;
                drawingCache = getDrawingCache(true);
            } else {
                i = layerType;
                drawingCache = renderNode;
            }
            if (drawsWithRenderNode) {
                android.graphics.RenderNode updateDisplayListIfDirty = updateDisplayListIfDirty();
                if (updateDisplayListIfDirty.hasDisplayList()) {
                    renderNode = updateDisplayListIfDirty;
                } else {
                    drawsWithRenderNode = false;
                }
            }
            if (drawsWithRenderNode) {
                i2 = 0;
                i3 = 0;
            } else {
                computeScroll();
                int i14 = this.mScrollX;
                i2 = this.mScrollY;
                i3 = i14;
            }
            boolean z5 = (drawingCache == null || drawsWithRenderNode) ? false : true;
            boolean z6 = drawingCache == null && !drawsWithRenderNode;
            int save = (drawsWithRenderNode && transformation == null) ? -1 : canvas.save();
            if (z6) {
                i4 = save;
                canvas.translate(this.mLeft - i3, this.mTop - i2);
            } else {
                i4 = save;
                if (!drawsWithRenderNode) {
                    canvas.translate(this.mLeft, this.mTop);
                }
                if (z3) {
                    if (!drawsWithRenderNode) {
                        i5 = i4;
                    } else {
                        i5 = canvas.save();
                    }
                    float f3 = 1.0f / this.mAttachInfo.mApplicationScale;
                    canvas.scale(f3, f3);
                    i6 = i5;
                    alpha = !drawsWithRenderNode ? 1.0f : getAlpha() * getTransitionAlpha();
                    if (transformation == null || alpha < 1.0f || !hasIdentityMatrix() || (this.mPrivateFlags3 & 2) != 0) {
                        if (transformation == null || !hasIdentityMatrix) {
                            if (z6) {
                                renderNode2 = drawingCache;
                                i7 = 0;
                                i8 = 0;
                            } else {
                                i8 = -i3;
                                renderNode2 = drawingCache;
                                i7 = -i2;
                            }
                            if (transformation != null) {
                                i9 = i6;
                            } else {
                                if (!z4) {
                                    i9 = i6;
                                } else {
                                    if (drawsWithRenderNode) {
                                        renderNode.setAnimationMatrix(transformation.getMatrix());
                                        i9 = i6;
                                    } else {
                                        i9 = i6;
                                        canvas.translate(-i8, -i7);
                                        canvas.concat(transformation.getMatrix());
                                        canvas.translate(i8, i7);
                                    }
                                    viewGroup.mGroupFlags |= 256;
                                }
                                float alpha2 = transformation.getAlpha();
                                if (alpha2 < 1.0f) {
                                    alpha *= alpha2;
                                    viewGroup.mGroupFlags |= 256;
                                }
                            }
                            if (!hasIdentityMatrix && !drawsWithRenderNode) {
                                canvas.translate(-i8, -i7);
                                canvas.concat(getMatrix());
                                canvas.translate(i8, i7);
                            }
                            f = alpha;
                        } else {
                            f = alpha;
                            renderNode2 = drawingCache;
                            i9 = i6;
                        }
                        if (f >= 1.0f || (this.mPrivateFlags3 & 2) != 0) {
                            if (f >= 1.0f) {
                                this.mPrivateFlags3 |= 2;
                            } else {
                                this.mPrivateFlags3 &= -3;
                            }
                            viewGroup.mGroupFlags |= 256;
                            if (!z5) {
                                i10 = i3;
                                f2 = f;
                                renderNode3 = renderNode2;
                                i11 = i2;
                            } else {
                                int i15 = (int) (f * 255.0f);
                                if (!onSetAlpha(i15)) {
                                    if (drawsWithRenderNode) {
                                        renderNode.setAlpha(getAlpha() * f * getTransitionAlpha());
                                        i10 = i3;
                                        f2 = f;
                                        renderNode3 = renderNode2;
                                        i11 = i2;
                                    } else if (i != 0) {
                                        i10 = i3;
                                        f2 = f;
                                        renderNode3 = renderNode2;
                                        i11 = i2;
                                    } else {
                                        f2 = f;
                                        i11 = i2;
                                        i10 = i3;
                                        renderNode3 = renderNode2;
                                        canvas.saveLayerAlpha(i3, i2, getWidth() + i3, getHeight() + i2, i15);
                                    }
                                } else {
                                    i10 = i3;
                                    f2 = f;
                                    renderNode3 = renderNode2;
                                    i11 = i2;
                                    this.mPrivateFlags |= 262144;
                                }
                            }
                        } else {
                            i10 = i3;
                            f2 = f;
                            renderNode3 = renderNode2;
                            i11 = i2;
                        }
                        alpha = f2;
                        bitmap = renderNode3;
                    } else {
                        if ((this.mPrivateFlags & 262144) == 262144) {
                            onSetAlpha(255);
                            this.mPrivateFlags &= -262145;
                        }
                        i11 = i2;
                        i10 = i3;
                        i9 = i6;
                        bitmap = drawingCache;
                    }
                    if (!drawsWithRenderNode) {
                        if ((i13 & 1) != 0 && bitmap == 0) {
                            if (z6) {
                                canvas.clipRect(i10, i11, i10 + getWidth(), i11 + getHeight());
                            } else {
                                if (!z3) {
                                    i12 = 0;
                                } else if (bitmap == 0) {
                                    i12 = 0;
                                } else {
                                    canvas.clipRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                                }
                                canvas.clipRect(i12, i12, getWidth(), getHeight());
                            }
                        }
                        if (this.mClipBounds != null) {
                            canvas.clipRect(this.mClipBounds);
                        }
                    }
                    if (z5) {
                        if (drawsWithRenderNode) {
                            this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                            ((android.graphics.RecordingCanvas) canvas).drawRenderNode(renderNode);
                        } else if ((this.mPrivateFlags & 128) == 128) {
                            this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                            dispatchDraw(canvas);
                        } else {
                            draw(canvas);
                        }
                    } else if (bitmap != 0) {
                        this.mPrivateFlags = (-2097153) & this.mPrivateFlags;
                        if (i == 0 || this.mLayerPaint == null) {
                            android.graphics.Paint paint = viewGroup.mCachePaint;
                            if (paint == null) {
                                paint = new android.graphics.Paint();
                                paint.setDither(false);
                                viewGroup.mCachePaint = paint;
                            }
                            paint.setAlpha((int) (alpha * 255.0f));
                            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                        } else {
                            int alpha3 = this.mLayerPaint.getAlpha();
                            if (alpha < 1.0f) {
                                this.mLayerPaint.setAlpha((int) (alpha * alpha3));
                            }
                            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.mLayerPaint);
                            if (alpha < 1.0f) {
                                this.mLayerPaint.setAlpha(alpha3);
                            }
                        }
                    }
                    if (i9 >= 0) {
                        canvas.restoreToCount(i9);
                    }
                    animation2 = animation;
                    if (animation2 != null && !z2) {
                        if (!isHardwareAccelerated && !animation2.getFillAfter()) {
                            onSetAlpha(255);
                        }
                        viewGroup.finishAnimatingView(this, animation2);
                    }
                    if (z2 && isHardwareAccelerated && animation2.hasAlpha() && (this.mPrivateFlags & 262144) == 262144) {
                        invalidate(true);
                    }
                    this.mRecreateDisplayList = false;
                    return z2;
                }
            }
            i6 = i4;
            if (!drawsWithRenderNode) {
            }
            if (transformation == null) {
            }
            if (transformation == null) {
            }
            if (z6) {
            }
            if (transformation != null) {
            }
            if (!hasIdentityMatrix) {
                canvas.translate(-i8, -i7);
                canvas.concat(getMatrix());
                canvas.translate(i8, i7);
            }
            f = alpha;
            if (f >= 1.0f) {
            }
            if (f >= 1.0f) {
            }
            viewGroup.mGroupFlags |= 256;
            if (!z5) {
            }
            alpha = f2;
            bitmap = renderNode3;
            if (!drawsWithRenderNode) {
            }
            if (z5) {
            }
            if (i9 >= 0) {
            }
            animation2 = animation;
            if (animation2 != null) {
                if (!isHardwareAccelerated) {
                    onSetAlpha(255);
                }
                viewGroup.finishAnimatingView(this, animation2);
            }
            if (z2) {
                invalidate(true);
            }
            this.mRecreateDisplayList = false;
            return z2;
        }
        this.mPrivateFlags2 |= 268435456;
        return z2;
    }

    static android.graphics.Paint getDebugPaint() {
        if (sDebugPaint == null) {
            sDebugPaint = new android.graphics.Paint();
            sDebugPaint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    final int dipsToPixels(int i) {
        return (int) ((i * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void debugDrawFocus(android.graphics.Canvas canvas) {
        if (isFocused()) {
            int dipsToPixels = dipsToPixels(8);
            int i = this.mScrollX;
            int i2 = (this.mRight + i) - this.mLeft;
            int i3 = this.mScrollY;
            int i4 = (this.mBottom + i3) - this.mTop;
            android.graphics.Paint debugPaint = getDebugPaint();
            debugPaint.setColor(DEBUG_CORNERS_COLOR);
            debugPaint.setStyle(android.graphics.Paint.Style.FILL);
            float f = i;
            float f2 = i3;
            float f3 = i + dipsToPixels;
            float f4 = i3 + dipsToPixels;
            canvas.drawRect(f, f2, f3, f4, debugPaint);
            float f5 = i2 - dipsToPixels;
            float f6 = i2;
            canvas.drawRect(f5, f2, f6, f4, debugPaint);
            float f7 = i4 - dipsToPixels;
            float f8 = i4;
            canvas.drawRect(f, f7, f3, f8, debugPaint);
            canvas.drawRect(f5, f7, f6, f8, debugPaint);
            debugPaint.setStyle(android.graphics.Paint.Style.STROKE);
            canvas.drawLine(f, f2, f6, f8, debugPaint);
            canvas.drawLine(f, f8, f6, f2, debugPaint);
        }
    }

    public void draw(android.graphics.Canvas canvas) {
        int i;
        float f;
        float f2;
        boolean z;
        boolean z2;
        float f3;
        boolean z3;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        android.graphics.Shader shader;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        this.mPrivateFlags = (this.mPrivateFlags & (-2097153)) | 32;
        float f4 = 0.0f;
        this.mFrameContentVelocity = 0.0f;
        drawBackground(canvas);
        int i14 = this.mViewFlags;
        boolean z4 = false;
        boolean z5 = (i14 & 4096) != 0;
        boolean z6 = (i14 & 8192) != 0;
        if (!z6 && !z5) {
            onDraw(canvas);
            dispatchDraw(canvas);
            drawAutofilledHighlight(canvas);
            if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().dispatchDraw(canvas);
            }
            onDrawForeground(canvas);
            drawDefaultFocusHighlight(canvas);
            if (isShowingLayoutBounds()) {
                debugDrawFocus(canvas);
                return;
            }
            return;
        }
        int i15 = this.mPaddingLeft;
        boolean isPaddingOffsetRequired = isPaddingOffsetRequired();
        if (isPaddingOffsetRequired) {
            i15 += getLeftPaddingOffset();
        }
        int i16 = this.mScrollX + i15;
        int i17 = (((this.mRight + i16) - this.mLeft) - this.mPaddingRight) - i15;
        int fadeTop = getFadeTop(isPaddingOffsetRequired) + this.mScrollY;
        int fadeHeight = getFadeHeight(isPaddingOffsetRequired) + fadeTop;
        if (!isPaddingOffsetRequired) {
            i = fadeHeight;
        } else {
            i17 += getRightPaddingOffset();
            i = fadeHeight + getBottomPaddingOffset();
        }
        android.view.View.ScrollabilityCache scrollabilityCache = this.mScrollCache;
        float f5 = scrollabilityCache.fadingEdgeLength;
        int i18 = (int) f5;
        if (z6 && fadeTop + i18 > i - i18) {
            i18 = (i - fadeTop) / 2;
        }
        if (z5 && i16 + i18 > i17 - i18) {
            i18 = (i17 - i16) / 2;
        }
        if (z6) {
            float max = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, getTopFadingEdgeStrength()));
            boolean z7 = max * f5 > 1.0f;
            float max2 = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, getBottomFadingEdgeStrength()));
            z = z7;
            z2 = max2 * f5 > 1.0f;
            f = max;
            f2 = max2;
        } else {
            f = 0.0f;
            f2 = 0.0f;
            z = false;
            z2 = false;
        }
        if (z5) {
            float max3 = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, getLeftFadingEdgeStrength()));
            boolean z8 = max3 * f5 > 1.0f;
            f4 = java.lang.Math.max(0.0f, java.lang.Math.min(1.0f, getRightFadingEdgeStrength()));
            f3 = max3;
            z3 = z8;
            z4 = f4 * f5 > 1.0f;
        } else {
            f3 = 0.0f;
            z3 = false;
        }
        int saveCount = canvas.getSaveCount();
        int solidColor = getSolidColor();
        if (solidColor == 0) {
            if (!z) {
                i10 = -1;
            } else {
                i10 = canvas.saveUnclippedLayer(i16, fadeTop, i17, fadeTop + i18);
            }
            if (!z2) {
                i11 = -1;
            } else {
                i11 = canvas.saveUnclippedLayer(i16, i - i18, i17, i);
            }
            if (!z3) {
                i12 = i10;
                i13 = -1;
            } else {
                i12 = i10;
                i13 = canvas.saveUnclippedLayer(i16, fadeTop, i16 + i18, i);
            }
            if (!z4) {
                i2 = i11;
                i5 = i12;
                i4 = i13;
                i3 = -1;
            } else {
                int i19 = i13;
                i3 = canvas.saveUnclippedLayer(i17 - i18, fadeTop, i17, i);
                i2 = i11;
                i5 = i12;
                i4 = i19;
            }
        } else {
            scrollabilityCache.setFadeColor(solidColor);
            i2 = -1;
            i3 = -1;
            i4 = -1;
            i5 = -1;
        }
        onDraw(canvas);
        dispatchDraw(canvas);
        float f6 = f;
        android.graphics.Paint paint = scrollabilityCache.paint;
        int i20 = i2;
        android.graphics.Matrix matrix = scrollabilityCache.matrix;
        android.graphics.Shader shader2 = scrollabilityCache.shader;
        if (!z4) {
            i6 = solidColor;
            i7 = saveCount;
            i8 = i17;
            shader = shader2;
        } else {
            matrix.setScale(1.0f, f4 * f5);
            matrix.postRotate(90.0f);
            float f7 = i17;
            float f8 = fadeTop;
            matrix.postTranslate(f7, f8);
            shader2.setLocalMatrix(matrix);
            paint.setShader(shader2);
            if (solidColor == 0) {
                canvas.restoreUnclippedLayer(i3, paint);
                i6 = solidColor;
                i7 = saveCount;
                i8 = i17;
                shader = shader2;
            } else {
                i6 = solidColor;
                i7 = saveCount;
                i8 = i17;
                shader = shader2;
                canvas.drawRect(i17 - i18, f8, f7, i, paint);
            }
        }
        if (z3) {
            matrix.setScale(1.0f, f5 * f3);
            matrix.postRotate(-90.0f);
            float f9 = i16;
            float f10 = fadeTop;
            matrix.postTranslate(f9, f10);
            shader.setLocalMatrix(matrix);
            paint.setShader(shader);
            if (i6 == 0) {
                canvas.restoreUnclippedLayer(i4, paint);
            } else {
                canvas.drawRect(f9, f10, i16 + i18, i, paint);
            }
        }
        if (!z2) {
            i9 = i8;
        } else {
            matrix.setScale(1.0f, f5 * f2);
            matrix.postRotate(180.0f);
            float f11 = i16;
            float f12 = i;
            matrix.postTranslate(f11, f12);
            shader.setLocalMatrix(matrix);
            paint.setShader(shader);
            if (i6 == 0) {
                canvas.restoreUnclippedLayer(i20, paint);
                i9 = i8;
            } else {
                float f13 = i - i18;
                i9 = i8;
                canvas.drawRect(f11, f13, i9, f12, paint);
            }
        }
        if (z) {
            matrix.setScale(1.0f, f5 * f6);
            float f14 = i16;
            float f15 = fadeTop;
            matrix.postTranslate(f14, f15);
            shader.setLocalMatrix(matrix);
            paint.setShader(shader);
            if (i6 == 0) {
                canvas.restoreUnclippedLayer(i5, paint);
            } else {
                canvas.drawRect(f14, f15, i9, fadeTop + i18, paint);
            }
        }
        canvas.restoreToCount(i7);
        drawAutofilledHighlight(canvas);
        if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
            this.mOverlay.getOverlayView().dispatchDraw(canvas);
        }
        onDrawForeground(canvas);
        drawDefaultFocusHighlight(canvas);
        if (isShowingLayoutBounds()) {
            debugDrawFocus(canvas);
        }
    }

    private void drawBackground(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable drawable = this.mBackground;
        if (drawable == null) {
            return;
        }
        setBackgroundBounds();
        if (canvas.isHardwareAccelerated() && this.mAttachInfo != null && this.mAttachInfo.mThreadedRenderer != null) {
            this.mBackgroundRenderNode = getDrawableRenderNode(drawable, this.mBackgroundRenderNode);
            android.graphics.RenderNode renderNode = this.mBackgroundRenderNode;
            if (renderNode != null && renderNode.hasDisplayList()) {
                setBackgroundRenderNodeProperties(renderNode);
                ((android.graphics.RecordingCanvas) canvas).drawRenderNode(renderNode);
                return;
            }
        }
        int i = this.mScrollX;
        int i2 = this.mScrollY;
        if ((i | i2) == 0) {
            drawable.draw(canvas);
            return;
        }
        canvas.translate(i, i2);
        drawable.draw(canvas);
        canvas.translate(-i, -i2);
    }

    void setBackgroundBounds() {
        if (this.mBackgroundSizeChanged && this.mBackground != null) {
            this.mBackground.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            this.mBackgroundSizeChanged = false;
            rebuildOutline();
        }
    }

    private void setBackgroundRenderNodeProperties(android.graphics.RenderNode renderNode) {
        renderNode.setTranslationX(this.mScrollX);
        renderNode.setTranslationY(this.mScrollY);
    }

    private android.graphics.RenderNode getDrawableRenderNode(android.graphics.drawable.Drawable drawable, android.graphics.RenderNode renderNode) {
        if (renderNode == null) {
            renderNode = android.graphics.RenderNode.create(drawable.getClass().getName(), new android.view.ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        android.graphics.Rect bounds = drawable.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        renderNode.clearStretch();
        android.graphics.RecordingCanvas beginRecording = renderNode.beginRecording(width, height);
        beginRecording.translate(-bounds.left, -bounds.top);
        try {
            drawable.draw(beginRecording);
            renderNode.endRecording();
            renderNode.setLeftTopRightBottom(bounds.left, bounds.top, bounds.right, bounds.bottom);
            renderNode.setProjectBackwards(drawable.isProjected());
            renderNode.setProjectionReceiver(true);
            renderNode.setClipToBounds(false);
            return renderNode;
        } catch (java.lang.Throwable th) {
            renderNode.endRecording();
            throw th;
        }
    }

    public android.view.ViewOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new android.view.ViewOverlay(this.mContext, this);
        }
        return this.mOverlay;
    }

    @android.view.ViewDebug.ExportedProperty(category = "drawing")
    public int getSolidColor() {
        return 0;
    }

    private static java.lang.String printFlags(int i) {
        java.lang.String str = "";
        char c = 1;
        if ((i & 1) != 1) {
            c = 0;
        } else {
            str = "TAKES_FOCUS";
        }
        switch (i & 12) {
            case 4:
                if (c > 0) {
                    str = str + " ";
                }
                return str + "INVISIBLE";
            case 8:
                if (c > 0) {
                    str = str + " ";
                }
                return str + "GONE";
            default:
                return str;
        }
    }

    private static java.lang.String printPrivateFlags(int i) {
        java.lang.String str = "";
        int i2 = 1;
        if ((i & 1) != 1) {
            i2 = 0;
        } else {
            str = "WANTS_FOCUS";
        }
        if ((i & 2) == 2) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "FOCUSED";
            i2++;
        }
        if ((i & 4) == 4) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "SELECTED";
            i2++;
        }
        if ((i & 8) == 8) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "IS_ROOT_NAMESPACE";
            i2++;
        }
        if ((i & 16) == 16) {
            if (i2 > 0) {
                str = str + " ";
            }
            str = str + "HAS_BOUNDS";
            i2++;
        }
        if ((i & 32) == 32) {
            if (i2 > 0) {
                str = str + " ";
            }
            return str + "DRAWN";
        }
        return str;
    }

    public boolean isLayoutRequested() {
        return (this.mPrivateFlags & 4096) == 4096;
    }

    public static boolean isLayoutModeOptical(java.lang.Object obj) {
        return (obj instanceof android.view.ViewGroup) && ((android.view.ViewGroup) obj).isLayoutModeOptical();
    }

    public static void setTraceLayoutSteps(boolean z) {
        sTraceLayoutSteps = z;
    }

    public static void setTracedRequestLayoutClassClass(java.lang.String str) {
        sTraceRequestLayoutClass = str;
    }

    private boolean setOpticalFrame(int i, int i2, int i3, int i4) {
        android.graphics.Insets opticalInsets = this.mParent instanceof android.view.View ? ((android.view.View) this.mParent).getOpticalInsets() : android.graphics.Insets.NONE;
        android.graphics.Insets opticalInsets2 = getOpticalInsets();
        return setFrame((i + opticalInsets.left) - opticalInsets2.left, (i2 + opticalInsets.top) - opticalInsets2.top, i3 + opticalInsets.left + opticalInsets2.right, i4 + opticalInsets.top + opticalInsets2.bottom);
    }

    public void layout(int i, int i2, int i3, int i4) {
        android.view.View view;
        if ((this.mPrivateFlags3 & 8) != 0) {
            if (isTraversalTracingEnabled()) {
                android.os.Trace.beginSection(this.mTracingStrings.onMeasureBeforeLayout);
            }
            onMeasure(this.mOldWidthMeasureSpec, this.mOldHeightMeasureSpec);
            if (isTraversalTracingEnabled()) {
                android.os.Trace.endSection();
            }
            this.mPrivateFlags3 &= -9;
        }
        int i5 = this.mLeft;
        int i6 = this.mTop;
        int i7 = this.mBottom;
        int i8 = this.mRight;
        boolean opticalFrame = isLayoutModeOptical(this.mParent) ? setOpticalFrame(i, i2, i3, i4) : setFrame(i, i2, i3, i4);
        android.view.View view2 = null;
        if (opticalFrame || (this.mPrivateFlags & 8192) == 8192) {
            if (isTraversalTracingEnabled()) {
                android.os.Trace.beginSection(this.mTracingStrings.onLayout);
            }
            onLayout(opticalFrame, i, i2, i3, i4);
            if (isTraversalTracingEnabled()) {
                android.os.Trace.endSection();
            }
            if (shouldDrawRoundScrollbar()) {
                if (this.mRoundScrollbarRenderer == null) {
                    this.mRoundScrollbarRenderer = new android.view.RoundScrollbarRenderer(this);
                }
            } else {
                this.mRoundScrollbarRenderer = null;
            }
            this.mPrivateFlags &= -8193;
            android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo == null || listenerInfo.mOnLayoutChangeListeners == null) {
                view = null;
            } else {
                java.util.ArrayList arrayList = (java.util.ArrayList) listenerInfo.mOnLayoutChangeListeners.clone();
                int size = arrayList.size();
                int i9 = 0;
                while (i9 < size) {
                    ((android.view.View.OnLayoutChangeListener) arrayList.get(i9)).onLayoutChange(this, i, i2, i3, i4, i5, i6, i8, i7);
                    i9++;
                    view2 = view2;
                    size = size;
                    arrayList = arrayList;
                    i5 = i5;
                }
                view = view2;
            }
        } else {
            view = null;
        }
        boolean isLayoutValid = isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!isLayoutValid && isFocused()) {
            this.mPrivateFlags &= -2;
            if (canTakeFocus()) {
                clearParentsWantFocus();
            } else if (getViewRootImpl() == null || !getViewRootImpl().isInLayout()) {
                clearFocusInternal(view, true, false);
                clearParentsWantFocus();
            } else if (!hasParentWantsFocus()) {
                clearFocusInternal(view, true, false);
            }
        } else if ((this.mPrivateFlags & 1) != 0) {
            this.mPrivateFlags &= -2;
            android.view.View findFocus = findFocus();
            if (findFocus != null && !restoreDefaultFocus() && !hasParentWantsFocus()) {
                findFocus.clearFocusInternal(view, true, false);
            }
        }
        if ((this.mPrivateFlags3 & 134217728) != 0) {
            this.mPrivateFlags3 &= -134217729;
            notifyEnterOrExitForAutoFillIfNeeded(true);
        }
        notifyAppearedOrDisappearedForContentCaptureIfNeeded(true);
    }

    private boolean hasParentWantsFocus() {
        android.view.ViewParent viewParent = this.mParent;
        while (viewParent instanceof android.view.ViewGroup) {
            android.view.ViewGroup viewGroup = (android.view.ViewGroup) viewParent;
            if ((viewGroup.mPrivateFlags & 1) != 0) {
                return true;
            }
            viewParent = viewGroup.mParent;
        }
        return false;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean setFrame(int i, int i2, int i3, int i4) {
        if (this.mLeft == i && this.mRight == i3 && this.mTop == i2 && this.mBottom == i4) {
            return false;
        }
        int i5 = this.mPrivateFlags & 32;
        int i6 = this.mRight - this.mLeft;
        int i7 = this.mBottom - this.mTop;
        int i8 = i3 - i;
        int i9 = i4 - i2;
        boolean z = (i8 == i6 && i9 == i7) ? false : true;
        invalidate(z);
        this.mLeft = i;
        this.mTop = i2;
        this.mRight = i3;
        this.mBottom = i4;
        this.mRenderNode.setLeftTopRightBottom(this.mLeft, this.mTop, this.mRight, this.mBottom);
        this.mPrivateFlags |= 16;
        if (z) {
            sizeChange(i8, i9, i6, i7);
        }
        if ((this.mViewFlags & 12) == 0 || this.mGhostView != null) {
            this.mPrivateFlags |= 32;
            invalidate(z);
            invalidateParentCaches();
        }
        this.mPrivateFlags |= i5;
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (this.mForegroundInfo != null) {
            this.mForegroundInfo.mBoundsChanged = true;
        }
        notifySubtreeAccessibilityStateChangedIfNeeded();
        return true;
    }

    public final void setLeftTopRightBottom(int i, int i2, int i3, int i4) {
        setFrame(i, i2, i3, i4);
    }

    private void sizeChange(int i, int i2, int i3, int i4) {
        onSizeChanged(i, i2, i3, i4);
        if (this.mOverlay != null) {
            this.mOverlay.getOverlayView().setRight(i);
            this.mOverlay.getOverlayView().setBottom(i2);
        }
        if (!sCanFocusZeroSized && isLayoutValid() && (!(this.mParent instanceof android.view.ViewGroup) || !((android.view.ViewGroup) this.mParent).isLayoutSuppressed())) {
            if (i <= 0 || i2 <= 0) {
                if (hasFocus()) {
                    clearFocus();
                    if (this.mParent instanceof android.view.ViewGroup) {
                        ((android.view.ViewGroup) this.mParent).clearFocusedInCluster();
                    }
                }
                clearAccessibilityFocus();
            } else if ((i3 <= 0 || i4 <= 0) && this.mParent != null && canTakeFocus()) {
                this.mParent.focusableViewAvailable(this);
            }
        }
        rebuildOutline();
        if (onCheckIsTextEditor() || this.mHandwritingDelegatorCallback != null) {
            setHandwritingArea(new android.graphics.Rect(0, 0, i, i2));
        }
    }

    protected void onFinishInflate() {
    }

    public android.content.res.Resources getResources() {
        return this.mResources;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        if (verifyDrawable(drawable)) {
            android.graphics.Rect dirtyBounds = drawable.getDirtyBounds();
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            invalidate(dirtyBounds.left + i, dirtyBounds.top + i2, dirtyBounds.right + i, dirtyBounds.bottom + i2);
            rebuildOutline();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable, long j) {
        if (verifyDrawable(drawable) && runnable != null) {
            long uptimeMillis = j - android.os.SystemClock.uptimeMillis();
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, drawable, android.view.Choreographer.subtractFrameDelay(uptimeMillis));
            } else {
                getRunQueue().postDelayed(runnable, uptimeMillis);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(android.graphics.drawable.Drawable drawable, java.lang.Runnable runnable) {
        if (verifyDrawable(drawable) && runnable != null) {
            if (this.mAttachInfo != null) {
                this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, drawable);
            }
            getRunQueue().removeCallbacks(runnable);
        }
    }

    public void unscheduleDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mAttachInfo != null && drawable != null) {
            this.mAttachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, null, drawable);
        }
    }

    protected void resolveDrawables() {
        if (!isLayoutDirectionResolved() && getRawLayoutDirection() == 2) {
            return;
        }
        int layoutDirection = isLayoutDirectionResolved() ? getLayoutDirection() : getRawLayoutDirection();
        if (this.mBackground != null) {
            this.mBackground.setLayoutDirection(layoutDirection);
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setLayoutDirection(layoutDirection);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setLayoutDirection(layoutDirection);
        }
        this.mPrivateFlags2 |= 1073741824;
        onResolveDrawables(layoutDirection);
    }

    boolean areDrawablesResolved() {
        return (this.mPrivateFlags2 & 1073741824) == 1073741824;
    }

    public void onResolveDrawables(int i) {
    }

    protected void resetResolvedDrawables() {
        resetResolvedDrawablesInternal();
    }

    void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        return drawable == this.mBackground || (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable == drawable) || this.mDefaultFocusHighlight == drawable;
    }

    protected void drawableStateChanged() {
        boolean z;
        android.widget.ScrollBarDrawable scrollBarDrawable;
        int[] drawableState = getDrawableState();
        android.graphics.drawable.Drawable drawable = this.mBackground;
        boolean z2 = false;
        if (drawable != null && drawable.isStateful()) {
            z = drawable.setState(drawableState) | false;
        } else {
            z = false;
        }
        android.graphics.drawable.Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        android.graphics.drawable.Drawable drawable3 = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (drawable3 != null && drawable3.isStateful()) {
            z |= drawable3.setState(drawableState);
        }
        if (this.mScrollCache != null && (scrollBarDrawable = this.mScrollCache.scrollBar) != null && scrollBarDrawable.isStateful()) {
            if (scrollBarDrawable.setState(drawableState) && this.mScrollCache.state != 0) {
                z2 = true;
            }
            z |= z2;
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.setState(drawableState);
        }
        if (!isAggregatedVisible()) {
            jumpDrawablesToCurrentState();
        }
        if (z) {
            invalidate();
        }
    }

    public void drawableHotspotChanged(float f, float f2) {
        if (this.mBackground != null) {
            this.mBackground.setHotspot(f, f2);
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.setHotspot(f, f2);
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.setHotspot(f, f2);
        }
        dispatchDrawableHotspotChanged(f, f2);
    }

    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    public void refreshDrawableState() {
        this.mPrivateFlags |= 1024;
        drawableStateChanged();
        android.view.ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.childDrawableStateChanged(this);
        }
    }

    private android.graphics.drawable.Drawable getDefaultFocusHighlightDrawable() {
        if (this.mDefaultFocusHighlightCache == null && this.mContext != null) {
            android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16843534});
            this.mDefaultFocusHighlightCache = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
        }
        return this.mDefaultFocusHighlightCache;
    }

    private void setDefaultFocusHighlight(android.graphics.drawable.Drawable drawable) {
        this.mDefaultFocusHighlight = drawable;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (drawable != null) {
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                drawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            drawable.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
            this.mPrivateFlags |= 128;
        }
        invalidate();
    }

    public boolean isDefaultFocusHighlightNeeded(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        return !isInTouchMode() && getDefaultFocusHighlightEnabled() && ((drawable == null || !drawable.isStateful() || !drawable.hasFocusStateSpecified()) && (drawable2 == null || !drawable2.isStateful() || !drawable2.hasFocusStateSpecified())) && isAttachedToWindow() && sUseDefaultFocusHighlight;
    }

    private void switchDefaultFocusHighlight() {
        if (isFocused()) {
            boolean isDefaultFocusHighlightNeeded = isDefaultFocusHighlightNeeded(this.mBackground, this.mForegroundInfo == null ? null : this.mForegroundInfo.mDrawable);
            boolean z = this.mDefaultFocusHighlight != null;
            if (isDefaultFocusHighlightNeeded && !z) {
                setDefaultFocusHighlight(getDefaultFocusHighlightDrawable());
            } else if (!isDefaultFocusHighlightNeeded && z) {
                setDefaultFocusHighlight(null);
            }
        }
    }

    private void drawDefaultFocusHighlight(android.graphics.Canvas canvas) {
        if (this.mDefaultFocusHighlight != null && isFocused()) {
            if (this.mDefaultFocusHighlightSizeChanged) {
                this.mDefaultFocusHighlightSizeChanged = false;
                int i = this.mScrollX;
                int i2 = (this.mRight + i) - this.mLeft;
                int i3 = this.mScrollY;
                this.mDefaultFocusHighlight.setBounds(i, i3, i2, (this.mBottom + i3) - this.mTop);
            }
            this.mDefaultFocusHighlight.draw(canvas);
        }
    }

    public final int[] getDrawableState() {
        if (this.mDrawableState != null && (this.mPrivateFlags & 1024) == 0) {
            return this.mDrawableState;
        }
        this.mDrawableState = onCreateDrawableState(0);
        this.mPrivateFlags &= -1025;
        return this.mDrawableState;
    }

    protected int[] onCreateDrawableState(int i) {
        if ((this.mViewFlags & 4194304) == 4194304 && (this.mParent instanceof android.view.View)) {
            return ((android.view.View) this.mParent).onCreateDrawableState(i);
        }
        int i2 = this.mPrivateFlags;
        int i3 = (i2 & 16384) != 0 ? 16 : 0;
        if ((this.mViewFlags & 32) == 0) {
            i3 |= 8;
        }
        if (isFocused()) {
            i3 |= 4;
        }
        if ((i2 & 4) != 0) {
            i3 |= 2;
        }
        if (hasWindowFocus()) {
            i3 |= 1;
        }
        if ((1073741824 & i2) != 0) {
            i3 |= 32;
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mHardwareAccelerationRequested) {
            i3 |= 64;
        }
        if ((i2 & 268435456) != 0) {
            i3 |= 128;
        }
        int i4 = this.mPrivateFlags2;
        if ((i4 & 1) != 0) {
            i3 |= 256;
        }
        if ((i4 & 2) != 0) {
            i3 |= 512;
        }
        int[] iArr = android.util.StateSet.get(i3);
        if (i == 0) {
            return iArr;
        }
        if (iArr != null) {
            int[] iArr2 = new int[iArr.length + i];
            java.lang.System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
            return iArr2;
        }
        return new int[i];
    }

    protected static int[] mergeDrawableStates(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        while (length >= 0 && iArr[length] == 0) {
            length--;
        }
        java.lang.System.arraycopy(iArr2, 0, iArr, length + 1, iArr2.length);
        return iArr;
    }

    public void jumpDrawablesToCurrentState() {
        if (this.mBackground != null) {
            this.mBackground.jumpToCurrentState();
        }
        if (this.mStateListAnimator != null) {
            this.mStateListAnimator.jumpToCurrentState();
        }
        if (this.mDefaultFocusHighlight != null) {
            this.mDefaultFocusHighlight.jumpToCurrentState();
        }
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null) {
            this.mForegroundInfo.mDrawable.jumpToCurrentState();
        }
    }

    @android.view.RemotableViewMethod
    public void setBackgroundColor(int i) {
        if (this.mBackground instanceof android.graphics.drawable.ColorDrawable) {
            ((android.graphics.drawable.ColorDrawable) this.mBackground.mutate()).setColor(i);
            computeOpaqueFlags();
            this.mBackgroundResource = 0;
            return;
        }
        setBackground(new android.graphics.drawable.ColorDrawable(i));
    }

    @android.view.RemotableViewMethod
    public void setBackgroundResource(int i) {
        android.graphics.drawable.Drawable drawable;
        if (i != 0 && i == this.mBackgroundResource) {
            return;
        }
        if (i == 0) {
            drawable = null;
        } else {
            drawable = this.mContext.getDrawable(i);
        }
        setBackground(drawable);
        this.mBackgroundResource = i;
    }

    public void setBackground(android.graphics.drawable.Drawable drawable) {
        setBackgroundDrawable(drawable);
    }

    @java.lang.Deprecated
    public void setBackgroundDrawable(android.graphics.drawable.Drawable drawable) {
        boolean z;
        boolean z2;
        computeOpaqueFlags();
        if (drawable == this.mBackground) {
            return;
        }
        this.mBackgroundResource = 0;
        if (this.mBackground != null) {
            if (isAttachedToWindow()) {
                this.mBackground.setVisible(false, false);
            }
            this.mBackground.setCallback(null);
            unscheduleDrawable(this.mBackground);
        }
        if (drawable != null) {
            android.graphics.Rect rect = sThreadLocal.get();
            if (rect == null) {
                rect = new android.graphics.Rect();
                sThreadLocal.set(rect);
            }
            resetResolvedDrawablesInternal();
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.getPadding(rect)) {
                resetResolvedPaddingInternal();
                switch (drawable.getLayoutDirection()) {
                    case 1:
                        this.mUserPaddingLeftInitial = rect.right;
                        this.mUserPaddingRightInitial = rect.left;
                        internalSetPadding(rect.right, rect.top, rect.left, rect.bottom);
                        break;
                    default:
                        this.mUserPaddingLeftInitial = rect.left;
                        this.mUserPaddingRightInitial = rect.right;
                        internalSetPadding(rect.left, rect.top, rect.right, rect.bottom);
                        break;
                }
                this.mLeftPaddingDefined = false;
                this.mRightPaddingDefined = false;
            }
            if (this.mBackground != null && this.mBackground.getMinimumHeight() == drawable.getMinimumHeight() && this.mBackground.getMinimumWidth() == drawable.getMinimumWidth()) {
                z = false;
            } else {
                z = true;
            }
            this.mBackground = drawable;
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (isAttachedToWindow()) {
                if (getWindowVisibility() != 0 || !isShown()) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                drawable.setVisible(z2, false);
            }
            applyBackgroundTint();
            drawable.setCallback(this);
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
                z = true;
            }
        } else {
            this.mBackground = null;
            if ((this.mViewFlags & 128) != 0 && this.mDefaultFocusHighlight == null && (this.mForegroundInfo == null || this.mForegroundInfo.mDrawable == null)) {
                this.mPrivateFlags |= 128;
            }
            z = true;
        }
        computeOpaqueFlags();
        if (z) {
            requestLayout();
        }
        this.mBackgroundSizeChanged = true;
        invalidate(true);
        invalidateOutline();
    }

    public android.graphics.drawable.Drawable getBackground() {
        return this.mBackground;
    }

    @android.view.RemotableViewMethod
    public void setBackgroundTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new android.view.View.TintInfo();
        }
        this.mBackgroundTint.mTintList = colorStateList;
        this.mBackgroundTint.mHasTintList = true;
        applyBackgroundTint();
    }

    public android.content.res.ColorStateList getBackgroundTintList() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mTintList;
        }
        return null;
    }

    public void setBackgroundTintMode(android.graphics.PorterDuff.Mode mode) {
        android.graphics.BlendMode blendMode;
        if (mode == null) {
            blendMode = null;
        } else {
            blendMode = android.graphics.BlendMode.fromValue(mode.nativeInt);
        }
        setBackgroundTintBlendMode(blendMode);
    }

    @android.view.RemotableViewMethod
    public void setBackgroundTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new android.view.View.TintInfo();
        }
        this.mBackgroundTint.mBlendMode = blendMode;
        this.mBackgroundTint.mHasTintMode = true;
        applyBackgroundTint();
    }

    public android.graphics.PorterDuff.Mode getBackgroundTintMode() {
        if (this.mBackgroundTint != null && this.mBackgroundTint.mBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getBackgroundTintBlendMode() {
        if (this.mBackgroundTint != null) {
            return this.mBackgroundTint.mBlendMode;
        }
        return null;
    }

    private void applyBackgroundTint() {
        if (this.mBackground != null && this.mBackgroundTint != null) {
            android.view.View.TintInfo tintInfo = this.mBackgroundTint;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mBackground = this.mBackground.mutate();
                if (tintInfo.mHasTintList) {
                    this.mBackground.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mBackground.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mBackground.isStateful()) {
                    this.mBackground.setState(getDrawableState());
                }
            }
        }
    }

    public android.graphics.drawable.Drawable getForeground() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mDrawable;
        }
        return null;
    }

    public void setForeground(android.graphics.drawable.Drawable drawable) {
        if (this.mForegroundInfo == null) {
            if (drawable == null) {
                return;
            } else {
                this.mForegroundInfo = new android.view.View.ForegroundInfo();
            }
        }
        if (drawable == this.mForegroundInfo.mDrawable) {
            return;
        }
        if (this.mForegroundInfo.mDrawable != null) {
            if (isAttachedToWindow()) {
                this.mForegroundInfo.mDrawable.setVisible(false, false);
            }
            this.mForegroundInfo.mDrawable.setCallback(null);
            unscheduleDrawable(this.mForegroundInfo.mDrawable);
        }
        this.mForegroundInfo.mDrawable = drawable;
        this.mForegroundInfo.mBoundsChanged = true;
        if (drawable != null) {
            if ((this.mPrivateFlags & 128) != 0) {
                this.mPrivateFlags &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
            }
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            applyForegroundTint();
            if (isAttachedToWindow()) {
                drawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
            drawable.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && this.mDefaultFocusHighlight == null) {
            this.mPrivateFlags |= 128;
        }
        requestLayout();
        invalidate();
    }

    public boolean isForegroundInsidePadding() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mInsidePadding;
        }
        return true;
    }

    public int getForegroundGravity() {
        if (this.mForegroundInfo != null) {
            return this.mForegroundInfo.mGravity;
        }
        return 8388659;
    }

    public void setForegroundGravity(int i) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new android.view.View.ForegroundInfo();
        }
        if (this.mForegroundInfo.mGravity != i) {
            if ((8388615 & i) == 0) {
                i |= android.view.Gravity.START;
            }
            if ((i & 112) == 0) {
                i |= 48;
            }
            this.mForegroundInfo.mGravity = i;
            requestLayout();
        }
    }

    @android.view.RemotableViewMethod
    public void setForegroundTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new android.view.View.ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new android.view.View.TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mTintList = colorStateList;
        this.mForegroundInfo.mTintInfo.mHasTintList = true;
        applyForegroundTint();
    }

    public android.content.res.ColorStateList getForegroundTintList() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mTintList;
    }

    public void setForegroundTintMode(android.graphics.PorterDuff.Mode mode) {
        android.graphics.BlendMode blendMode;
        if (mode == null) {
            blendMode = null;
        } else {
            blendMode = android.graphics.BlendMode.fromValue(mode.nativeInt);
        }
        setForegroundTintBlendMode(blendMode);
    }

    @android.view.RemotableViewMethod
    public void setForegroundTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new android.view.View.ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new android.view.View.TintInfo();
        }
        this.mForegroundInfo.mTintInfo.mBlendMode = blendMode;
        this.mForegroundInfo.mTintInfo.mHasTintMode = true;
        applyForegroundTint();
    }

    public android.graphics.PorterDuff.Mode getForegroundTintMode() {
        android.graphics.BlendMode blendMode = (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) ? null : this.mForegroundInfo.mTintInfo.mBlendMode;
        if (blendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getForegroundTintBlendMode() {
        if (this.mForegroundInfo == null || this.mForegroundInfo.mTintInfo == null) {
            return null;
        }
        return this.mForegroundInfo.mTintInfo.mBlendMode;
    }

    private void applyForegroundTint() {
        if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null && this.mForegroundInfo.mTintInfo != null) {
            android.view.View.TintInfo tintInfo = this.mForegroundInfo.mTintInfo;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mForegroundInfo.mDrawable = this.mForegroundInfo.mDrawable.mutate();
                if (tintInfo.mHasTintList) {
                    this.mForegroundInfo.mDrawable.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mForegroundInfo.mDrawable.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mForegroundInfo.mDrawable.isStateful()) {
                    this.mForegroundInfo.mDrawable.setState(getDrawableState());
                }
            }
        }
    }

    private android.graphics.drawable.Drawable getAutofilledDrawable() {
        if (this.mAttachInfo == null) {
            return null;
        }
        if (this.mAttachInfo.mAutofilledDrawable == null) {
            android.content.Context context = getRootView().getContext();
            android.content.res.TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(AUTOFILL_HIGHLIGHT_ATTR);
            int resourceId = obtainStyledAttributes.getResourceId(0, 0);
            this.mAttachInfo.mAutofilledDrawable = context.getDrawable(resourceId);
            obtainStyledAttributes.recycle();
        }
        return this.mAttachInfo.mAutofilledDrawable;
    }

    private void drawAutofilledHighlight(android.graphics.Canvas canvas) {
        android.graphics.drawable.Drawable autofilledDrawable;
        if (isAutofilled() && !hideAutofillHighlight() && (autofilledDrawable = getAutofilledDrawable()) != null) {
            autofilledDrawable.setBounds(0, 0, getWidth(), getHeight());
            autofilledDrawable.draw(canvas);
        }
    }

    public void onDrawForeground(android.graphics.Canvas canvas) {
        onDrawScrollIndicators(canvas);
        onDrawScrollBars(canvas);
        android.graphics.drawable.Drawable drawable = this.mForegroundInfo != null ? this.mForegroundInfo.mDrawable : null;
        if (drawable != null) {
            if (this.mForegroundInfo.mBoundsChanged) {
                this.mForegroundInfo.mBoundsChanged = false;
                android.graphics.Rect rect = this.mForegroundInfo.mSelfBounds;
                android.graphics.Rect rect2 = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    rect.set(0, 0, getWidth(), getHeight());
                } else {
                    rect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                }
                android.view.Gravity.apply(this.mForegroundInfo.mGravity, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), rect, rect2, getLayoutDirection());
                drawable.setBounds(rect2);
            }
            drawable.draw(canvas);
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = i;
        this.mUserPaddingRightInitial = i3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        internalSetPadding(i, i2, i3, i4);
    }

    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        this.mUserPaddingLeft = i;
        this.mUserPaddingRight = i3;
        this.mUserPaddingBottom = i4;
        int i5 = this.mViewFlags;
        boolean z = false;
        if ((i5 & 768) != 0) {
            if ((i5 & 512) != 0) {
                int verticalScrollbarWidth = (i5 & 16777216) == 0 ? 0 : getVerticalScrollbarWidth();
                switch (this.mVerticalScrollbarPosition) {
                    case 0:
                        if (isLayoutRtl()) {
                            i += verticalScrollbarWidth;
                            break;
                        } else {
                            i3 += verticalScrollbarWidth;
                            break;
                        }
                    case 1:
                        i += verticalScrollbarWidth;
                        break;
                    case 2:
                        i3 += verticalScrollbarWidth;
                        break;
                }
            }
            if ((i5 & 256) != 0) {
                i4 += (i5 & 16777216) == 0 ? 0 : getHorizontalScrollbarHeight();
            }
        }
        boolean z2 = true;
        if (this.mPaddingLeft != i) {
            this.mPaddingLeft = i;
            z = true;
        }
        if (this.mPaddingTop != i2) {
            this.mPaddingTop = i2;
            z = true;
        }
        if (this.mPaddingRight != i3) {
            this.mPaddingRight = i3;
            z = true;
        }
        if (this.mPaddingBottom == i4) {
            z2 = z;
        } else {
            this.mPaddingBottom = i4;
        }
        if (z2) {
            requestLayout();
            invalidateOutline();
        }
    }

    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        resetResolvedPaddingInternal();
        this.mUserPaddingStart = i;
        this.mUserPaddingEnd = i3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        switch (getLayoutDirection()) {
            case 1:
                this.mUserPaddingLeftInitial = i3;
                this.mUserPaddingRightInitial = i;
                internalSetPadding(i3, i2, i, i4);
                break;
            default:
                this.mUserPaddingLeftInitial = i;
                this.mUserPaddingRightInitial = i3;
                internalSetPadding(i, i2, i3, i4);
                break;
        }
    }

    public int getSourceLayoutResId() {
        return this.mSourceLayoutId;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public int getPaddingLeft() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingLeft;
    }

    public int getPaddingStart() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingRight : this.mPaddingLeft;
    }

    public int getPaddingRight() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return this.mPaddingRight;
    }

    public int getPaddingEnd() {
        if (!isPaddingResolved()) {
            resolvePadding();
        }
        return getLayoutDirection() == 1 ? this.mPaddingLeft : this.mPaddingRight;
    }

    public boolean isPaddingRelative() {
        return (this.mUserPaddingStart == Integer.MIN_VALUE && this.mUserPaddingEnd == Integer.MIN_VALUE) ? false : true;
    }

    android.graphics.Insets computeOpticalInsets() {
        return this.mBackground == null ? android.graphics.Insets.NONE : this.mBackground.getOpticalInsets();
    }

    public void resetPaddingToInitialValues() {
        if (isRtlCompatibilityMode()) {
            this.mPaddingLeft = this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingRightInitial;
        } else if (isLayoutRtl()) {
            this.mPaddingLeft = this.mUserPaddingEnd >= 0 ? this.mUserPaddingEnd : this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingStart >= 0 ? this.mUserPaddingStart : this.mUserPaddingRightInitial;
        } else {
            this.mPaddingLeft = this.mUserPaddingStart >= 0 ? this.mUserPaddingStart : this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingEnd >= 0 ? this.mUserPaddingEnd : this.mUserPaddingRightInitial;
        }
    }

    public android.graphics.Insets getOpticalInsets() {
        if (this.mLayoutInsets == null) {
            this.mLayoutInsets = computeOpticalInsets();
        }
        return this.mLayoutInsets;
    }

    public void setOpticalInsets(android.graphics.Insets insets) {
        this.mLayoutInsets = insets;
    }

    public void setSelected(boolean z) {
        if (((this.mPrivateFlags & 4) != 0) != z) {
            this.mPrivateFlags = (this.mPrivateFlags & (-5)) | (z ? 4 : 0);
            if (!z) {
                resetPressedState();
            }
            invalidate(true);
            refreshDrawableState();
            dispatchSetSelected(z);
            if (z) {
                sendAccessibilityEvent(4);
            } else {
                notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    protected void dispatchSetSelected(boolean z) {
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isSelected() {
        return (this.mPrivateFlags & 4) != 0;
    }

    public void setActivated(boolean z) {
        if (((this.mPrivateFlags & 1073741824) != 0) != z) {
            this.mPrivateFlags = (this.mPrivateFlags & (-1073741825)) | (z ? 1073741824 : 0);
            invalidate(true);
            refreshDrawableState();
            dispatchSetActivated(z);
        }
    }

    protected void dispatchSetActivated(boolean z) {
    }

    @android.view.ViewDebug.ExportedProperty
    public boolean isActivated() {
        return (this.mPrivateFlags & 1073741824) != 0;
    }

    public android.view.ViewTreeObserver getViewTreeObserver() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mTreeObserver;
        }
        if (this.mFloatingTreeObserver == null) {
            this.mFloatingTreeObserver = new android.view.ViewTreeObserver(this.mContext);
        }
        return this.mFloatingTreeObserver;
    }

    public android.view.View getRootView() {
        android.view.View view;
        if (this.mAttachInfo != null && (view = this.mAttachInfo.mRootView) != null) {
            return view;
        }
        android.view.View view2 = this;
        while (view2.mParent instanceof android.view.View) {
            view2 = (android.view.View) view2.mParent;
        }
        return view2;
    }

    public boolean toGlobalMotionEvent(android.view.MotionEvent motionEvent) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return false;
        }
        android.graphics.Matrix matrix = attachInfo.mTmpMatrix;
        matrix.set(android.graphics.Matrix.IDENTITY_MATRIX);
        transformMatrixToGlobal(matrix);
        motionEvent.transform(matrix);
        return true;
    }

    public boolean toLocalMotionEvent(android.view.MotionEvent motionEvent) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return false;
        }
        android.graphics.Matrix matrix = attachInfo.mTmpMatrix;
        matrix.set(android.graphics.Matrix.IDENTITY_MATRIX);
        transformMatrixToLocal(matrix);
        motionEvent.transform(matrix);
        return true;
    }

    public void transformMatrixToGlobal(android.graphics.Matrix matrix) {
        java.lang.Object obj = this.mParent;
        if (obj instanceof android.view.View) {
            ((android.view.View) obj).transformMatrixToGlobal(matrix);
            matrix.preTranslate(-r0.mScrollX, -r0.mScrollY);
        } else if (obj instanceof android.view.ViewRootImpl) {
            ((android.view.ViewRootImpl) obj).transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, -r0.mCurScrollY);
        }
        matrix.preTranslate(this.mLeft, this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.preConcat(getMatrix());
        }
    }

    public void transformMatrixToLocal(android.graphics.Matrix matrix) {
        java.lang.Object obj = this.mParent;
        if (obj instanceof android.view.View) {
            ((android.view.View) obj).transformMatrixToLocal(matrix);
            matrix.postTranslate(r0.mScrollX, r0.mScrollY);
        } else if (obj instanceof android.view.ViewRootImpl) {
            ((android.view.ViewRootImpl) obj).transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, r0.mCurScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (!hasIdentityMatrix()) {
            matrix.postConcat(getInverseMatrix());
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT, indexMapping = {@android.view.ViewDebug.IntToString(from = 0, to = "x"), @android.view.ViewDebug.IntToString(from = 1, to = "y")})
    public int[] getLocationOnScreen() {
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        return iArr;
    }

    public void getLocationOnScreen(int[] iArr) {
        getLocationInWindow(iArr);
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            iArr[0] = iArr[0] + attachInfo.mWindowLeft;
            iArr[1] = iArr[1] + attachInfo.mWindowTop;
            attachInfo.mViewRootImpl.applyViewLocationSandboxingIfNeeded(iArr);
        }
    }

    public void getLocationInWindow(int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            throw new java.lang.IllegalArgumentException("outLocation must be an array of two integers");
        }
        iArr[0] = 0;
        iArr[1] = 0;
        transformFromViewToWindowSpace(iArr);
    }

    public void transformFromViewToWindowSpace(int[] iArr) {
        if (iArr == null || iArr.length < 2) {
            throw new java.lang.IllegalArgumentException("inOutLocation must be an array of two integers");
        }
        if (this.mAttachInfo == null) {
            iArr[1] = 0;
            iArr[0] = 0;
            return;
        }
        float[] fArr = this.mAttachInfo.mTmpTransformLocation;
        fArr[0] = iArr[0];
        fArr[1] = iArr[1];
        if (!hasIdentityMatrix()) {
            getMatrix().mapPoints(fArr);
        }
        fArr[0] = fArr[0] + this.mLeft;
        fArr[1] = fArr[1] + this.mTop;
        java.lang.Object obj = this.mParent;
        while (obj instanceof android.view.View) {
            android.view.View view = (android.view.View) obj;
            fArr[0] = fArr[0] - view.mScrollX;
            fArr[1] = fArr[1] - view.mScrollY;
            if (!view.hasIdentityMatrix()) {
                view.getMatrix().mapPoints(fArr);
            }
            fArr[0] = fArr[0] + view.mLeft;
            fArr[1] = fArr[1] + view.mTop;
            obj = view.mParent;
        }
        if (obj instanceof android.view.ViewRootImpl) {
            fArr[1] = fArr[1] - ((android.view.ViewRootImpl) obj).mCurScrollY;
        }
        iArr[0] = java.lang.Math.round(fArr[0]);
        iArr[1] = java.lang.Math.round(fArr[1]);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends android.view.View> T findViewTraversal(int i) {
        if (i == this.mID) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends android.view.View> T findViewWithTagTraversal(java.lang.Object obj) {
        if (obj != null && obj.equals(this.mTag)) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <T extends android.view.View> T findViewByPredicateTraversal(java.util.function.Predicate<android.view.View> predicate, android.view.View view) {
        if (predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final <T extends android.view.View> T findViewById(int i) {
        if (i == -1) {
            return null;
        }
        return (T) findViewTraversal(i);
    }

    public final <T extends android.view.View> T requireViewById(int i) {
        T t = (T) findViewById(i);
        if (t == null) {
            throw new java.lang.IllegalArgumentException("ID does not reference a View inside this View");
        }
        return t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends android.view.View> T findViewByAccessibilityIdTraversal(int i) {
        if (getAccessibilityViewId() == i) {
            return this;
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T extends android.view.View> T findViewByAutofillIdTraversal(int i) {
        if (getAutofillViewId() == i) {
            return this;
        }
        return null;
    }

    public final <T extends android.view.View> T findViewWithTag(java.lang.Object obj) {
        if (obj == null) {
            return null;
        }
        return (T) findViewWithTagTraversal(obj);
    }

    public final <T extends android.view.View> T findViewByPredicate(java.util.function.Predicate<android.view.View> predicate) {
        return (T) findViewByPredicateTraversal(predicate, null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x001e, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final <T extends android.view.View> T findViewByPredicateInsideOut(android.view.View view, java.util.function.Predicate<android.view.View> predicate) {
        android.view.View view2 = null;
        while (true) {
            T t = (T) view.findViewByPredicateTraversal(predicate, view2);
            if (t != null || view == this) {
                break;
            }
            java.lang.Object parent = view.getParent();
            if (parent == null || !(parent instanceof android.view.View)) {
                break;
            }
            android.view.View view3 = (android.view.View) parent;
            view2 = view;
            view = view3;
        }
        return null;
    }

    public void setId(int i) {
        this.mID = i;
        if (this.mID == -1 && this.mLabelForId != -1) {
            this.mID = generateViewId();
        }
    }

    public void setIsRootNamespace(boolean z) {
        if (z) {
            this.mPrivateFlags |= 8;
        } else {
            this.mPrivateFlags &= -9;
        }
    }

    public boolean isRootNamespace() {
        return (this.mPrivateFlags & 8) != 0;
    }

    @android.view.ViewDebug.CapturedViewProperty
    public int getId() {
        return this.mID;
    }

    public long getUniqueDrawingId() {
        return this.mRenderNode.getUniqueId();
    }

    @android.view.ViewDebug.ExportedProperty
    public java.lang.Object getTag() {
        return this.mTag;
    }

    public void setTag(java.lang.Object obj) {
        this.mTag = obj;
    }

    public java.lang.Object getTag(int i) {
        if (this.mKeyedTags != null) {
            return this.mKeyedTags.get(i);
        }
        return null;
    }

    public void setTag(int i, java.lang.Object obj) {
        if ((i >>> 24) < 2) {
            throw new java.lang.IllegalArgumentException("The key must be an application-specific resource id.");
        }
        setKeyedTag(i, obj);
    }

    public void setTagInternal(int i, java.lang.Object obj) {
        if ((i >>> 24) != 1) {
            throw new java.lang.IllegalArgumentException("The key must be a framework-specific resource id.");
        }
        setKeyedTag(i, obj);
    }

    private void setKeyedTag(int i, java.lang.Object obj) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new android.util.SparseArray<>(2);
        }
        this.mKeyedTags.put(i, obj);
    }

    public void debug() {
        debug(0);
    }

    protected void debug(int i) {
        java.lang.String debug;
        java.lang.String str = debugIndent(i - 1) + "+ " + this;
        int id = getId();
        if (id != -1) {
            str = str + " (id=" + id + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        java.lang.Object tag = getTag();
        if (tag != null) {
            str = str + " (tag=" + tag + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
        android.util.Log.d(VIEW_LOG_TAG, str);
        if ((this.mPrivateFlags & 2) != 0) {
            android.util.Log.d(VIEW_LOG_TAG, debugIndent(i) + " FOCUSED");
        }
        android.util.Log.d(VIEW_LOG_TAG, debugIndent(i) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ");
        if (this.mPaddingLeft != 0 || this.mPaddingTop != 0 || this.mPaddingRight != 0 || this.mPaddingBottom != 0) {
            android.util.Log.d(VIEW_LOG_TAG, debugIndent(i) + "padding={" + this.mPaddingLeft + ", " + this.mPaddingTop + ", " + this.mPaddingRight + ", " + this.mPaddingBottom + "}");
        }
        android.util.Log.d(VIEW_LOG_TAG, debugIndent(i) + "mMeasureWidth=" + this.mMeasuredWidth + " mMeasureHeight=" + this.mMeasuredHeight);
        java.lang.String debugIndent = debugIndent(i);
        if (this.mLayoutParams == null) {
            debug = debugIndent + "BAD! no layout params";
        } else {
            debug = this.mLayoutParams.debug(debugIndent);
        }
        android.util.Log.d(VIEW_LOG_TAG, debug);
        android.util.Log.d(VIEW_LOG_TAG, ((debugIndent(i) + "flags={") + printFlags(this.mViewFlags)) + "}");
        android.util.Log.d(VIEW_LOG_TAG, ((debugIndent(i) + "privateFlags={") + printPrivateFlags(this.mPrivateFlags)) + "}");
    }

    protected static java.lang.String debugIndent(int i) {
        int i2 = (i * 2) + 3;
        java.lang.StringBuilder sb = new java.lang.StringBuilder(i2 * 2);
        for (int i3 = 0; i3 < i2; i3++) {
            sb.append(' ').append(' ');
        }
        return sb.toString();
    }

    @android.view.ViewDebug.ExportedProperty(category = android.media.TtmlUtils.TAG_LAYOUT)
    public int getBaseline() {
        return -1;
    }

    public boolean isInLayout() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        return viewRootImpl != null && viewRootImpl.isInLayout();
    }

    private void printStackStrace(java.lang.String str) {
        android.util.Log.d(VIEW_LOG_TAG, "---- ST:" + str);
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        java.lang.StackTraceElement[] stackTrace = java.lang.Thread.currentThread().getStackTrace();
        int min = java.lang.Math.min(stackTrace.length, 21);
        for (int i = 1; i < min; i++) {
            java.lang.StackTraceElement stackTraceElement = stackTrace[i];
            sb.append(stackTraceElement.getMethodName()).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START).append(stackTraceElement.getFileName()).append(":").append(stackTraceElement.getLineNumber()).append(") <- ");
        }
        android.util.Log.d(VIEW_LOG_TAG, str + ": " + ((java.lang.Object) sb));
    }

    public void requestLayout() {
        if (isRelayoutTracingEnabled()) {
            android.os.Trace.instantForTrack(4096L, "requestLayoutTracing", this.mTracingStrings.classSimpleName);
            printStackStrace(this.mTracingStrings.requestLayoutStacktracePrefix);
        }
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == null) {
            android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null && viewRootImpl.isInLayout() && !viewRootImpl.requestLayoutDuringLayout(this)) {
                return;
            } else {
                this.mAttachInfo.mViewRequestingLayout = this;
            }
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
        if (this.mParent != null && !this.mParent.isLayoutRequested()) {
            this.mParent.requestLayout();
        }
        if (this.mAttachInfo != null && this.mAttachInfo.mViewRequestingLayout == this) {
            this.mAttachInfo.mViewRequestingLayout = null;
        }
    }

    public void forceLayout() {
        if (this.mMeasureCache != null) {
            this.mMeasureCache.clear();
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
    }

    public final void measure(int i, int i2) {
        int indexOfKey;
        boolean isLayoutModeOptical = isLayoutModeOptical(this);
        if (isLayoutModeOptical != isLayoutModeOptical(this.mParent)) {
            android.graphics.Insets opticalInsets = getOpticalInsets();
            int i3 = opticalInsets.left + opticalInsets.right;
            int i4 = opticalInsets.top + opticalInsets.bottom;
            if (isLayoutModeOptical) {
                i3 = -i3;
            }
            i = android.view.View.MeasureSpec.adjust(i, i3);
            if (isLayoutModeOptical) {
                i4 = -i4;
            }
            i2 = android.view.View.MeasureSpec.adjust(i2, i4);
        }
        long j = (i << 32) | (i2 & 4294967295L);
        if (this.mMeasureCache == null) {
            this.mMeasureCache = new android.util.LongSparseLongArray(2);
        }
        boolean z = true;
        boolean z2 = (this.mPrivateFlags & 4096) == 4096;
        boolean z3 = (i == this.mOldWidthMeasureSpec && i2 == this.mOldHeightMeasureSpec) ? false : true;
        boolean z4 = android.view.View.MeasureSpec.getMode(i) == 1073741824 && android.view.View.MeasureSpec.getMode(i2) == 1073741824;
        boolean z5 = getMeasuredWidth() == android.view.View.MeasureSpec.getSize(i) && getMeasuredHeight() == android.view.View.MeasureSpec.getSize(i2);
        if (!z3 || (!sAlwaysRemeasureExactly && z4 && z5)) {
            z = false;
        }
        if (z2 || z) {
            this.mPrivateFlags &= -2049;
            resolveRtlPropertiesIfNeeded();
            if (sUseMeasureCacheDuringForceLayoutFlagValue) {
                indexOfKey = this.mMeasureCache.indexOfKey(j);
            } else {
                indexOfKey = z2 ? -1 : this.mMeasureCache.indexOfKey(j);
            }
            if (indexOfKey < 0 || sIgnoreMeasureCache) {
                if (isTraversalTracingEnabled()) {
                    android.os.Trace.beginSection(this.mTracingStrings.onMeasure);
                }
                onMeasure(i, i2);
                if (isTraversalTracingEnabled()) {
                    android.os.Trace.endSection();
                }
                this.mPrivateFlags3 &= -9;
            } else {
                long valueAt = this.mMeasureCache.valueAt(indexOfKey);
                setMeasuredDimensionRaw((int) (valueAt >> 32), (int) valueAt);
                this.mPrivateFlags3 |= 8;
            }
            if ((this.mPrivateFlags & 2048) != 2048) {
                throw new java.lang.IllegalStateException("View with id " + getId() + ": " + getClass().getName() + "#onMeasure() did not set the measured dimension by calling setMeasuredDimension()");
            }
            this.mPrivateFlags |= 8192;
        }
        this.mOldWidthMeasureSpec = i;
        this.mOldHeightMeasureSpec = i2;
        this.mMeasureCache.put(j, (this.mMeasuredWidth << 32) | (this.mMeasuredHeight & 4294967295L));
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), i), getDefaultSize(getSuggestedMinimumHeight(), i2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void setMeasuredDimension(int i, int i2) {
        boolean isLayoutModeOptical = isLayoutModeOptical(this);
        if (isLayoutModeOptical != isLayoutModeOptical(this.mParent)) {
            android.graphics.Insets opticalInsets = getOpticalInsets();
            int i3 = opticalInsets.left + opticalInsets.right;
            int i4 = opticalInsets.top + opticalInsets.bottom;
            if (!isLayoutModeOptical) {
                i3 = -i3;
            }
            i += i3;
            if (!isLayoutModeOptical) {
                i4 = -i4;
            }
            i2 += i4;
        }
        setMeasuredDimensionRaw(i, i2);
    }

    private void setMeasuredDimensionRaw(int i, int i2) {
        this.mMeasuredWidth = i;
        this.mMeasuredHeight = i2;
        this.mPrivateFlags |= 2048;
    }

    public static int combineMeasuredStates(int i, int i2) {
        return i | i2;
    }

    public static int resolveSize(int i, int i2) {
        return resolveSizeAndState(i, i2, 0) & 16777215;
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        int mode = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
                if (size < i) {
                    i = 16777216 | size;
                    break;
                }
                break;
            case 1073741824:
                i = size;
                break;
        }
        return i | ((-16777216) & i3);
    }

    public static int getDefaultSize(int i, int i2) {
        int mode = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i2);
        switch (mode) {
            case Integer.MIN_VALUE:
            case 1073741824:
                return size;
            case 0:
            default:
                return i;
        }
    }

    protected int getSuggestedMinimumHeight() {
        return this.mBackground == null ? this.mMinHeight : java.lang.Math.max(this.mMinHeight, this.mBackground.getMinimumHeight());
    }

    protected int getSuggestedMinimumWidth() {
        return this.mBackground == null ? this.mMinWidth : java.lang.Math.max(this.mMinWidth, this.mBackground.getMinimumWidth());
    }

    public int getMinimumHeight() {
        return this.mMinHeight;
    }

    @android.view.RemotableViewMethod
    public void setMinimumHeight(int i) {
        this.mMinHeight = i;
        requestLayout();
    }

    public int getMinimumWidth() {
        return this.mMinWidth;
    }

    @android.view.RemotableViewMethod
    public void setMinimumWidth(int i) {
        this.mMinWidth = i;
        requestLayout();
    }

    public android.view.animation.Animation getAnimation() {
        return this.mCurrentAnimation;
    }

    public void startAnimation(android.view.animation.Animation animation) {
        animation.setStartTime(-1L);
        setAnimation(animation);
        invalidateParentCaches();
        invalidate(true);
    }

    public void clearAnimation() {
        if (this.mCurrentAnimation != null) {
            this.mCurrentAnimation.detach();
        }
        this.mCurrentAnimation = null;
        invalidateParentIfNeeded();
    }

    public void setAnimation(android.view.animation.Animation animation) {
        this.mCurrentAnimation = animation;
        if (animation != null) {
            if (this.mAttachInfo != null && this.mAttachInfo.mDisplayState == 1 && animation.getStartTime() == -1) {
                animation.setStartTime(android.view.animation.AnimationUtils.currentAnimationTimeMillis());
            }
            animation.reset();
        }
    }

    protected void onAnimationStart() {
        this.mPrivateFlags |= 65536;
    }

    protected void onAnimationEnd() {
        this.mPrivateFlags &= -65537;
    }

    protected boolean onSetAlpha(int i) {
        return false;
    }

    public boolean gatherTransparentRegion(android.graphics.Region region) {
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (region != null && attachInfo != null) {
            if ((this.mPrivateFlags & 128) == 0) {
                int[] iArr = attachInfo.mTransparentLocation;
                getLocationInWindow(iArr);
                int z = getZ() > 0.0f ? (int) getZ() : 0;
                region.op(iArr[0] - z, iArr[1] - z, ((iArr[0] + this.mRight) - this.mLeft) + z, ((iArr[1] + this.mBottom) - this.mTop) + (z * 3), android.graphics.Region.Op.DIFFERENCE);
            } else {
                if (this.mBackground != null && this.mBackground.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mBackground, region);
                }
                if (this.mForegroundInfo != null && this.mForegroundInfo.mDrawable != null && this.mForegroundInfo.mDrawable.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                if (this.mDefaultFocusHighlight != null && this.mDefaultFocusHighlight.getOpacity() != -2) {
                    applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public void playSoundEffect(int i) {
        if (this.mAttachInfo == null || this.mAttachInfo.mRootCallbacks == null || !isSoundEffectsEnabled()) {
            return;
        }
        this.mAttachInfo.mRootCallbacks.playSoundEffect(i);
    }

    public boolean performHapticFeedback(int i) {
        return performHapticFeedback(i, 0);
    }

    public boolean performHapticFeedback(int i, int i2) {
        boolean z;
        if (i == -1 || this.mAttachInfo == null) {
            return false;
        }
        if ((i2 & 1) == 0 && !isHapticFeedbackEnabled()) {
            return false;
        }
        boolean z2 = (i2 & 2) != 0;
        if (this.mAttachInfo.mViewRootImpl == null) {
            z = false;
        } else {
            z = this.mAttachInfo.mViewRootImpl.mWindowAttributes.type == 2011;
        }
        if (android.os.vibrator.Flags.useVibratorHapticFeedback()) {
            if (!this.mAttachInfo.canPerformHapticFeedback()) {
                return false;
            }
            getSystemVibrator().performHapticFeedback(i, z2, "View#performHapticFeedback", z);
            return true;
        }
        return this.mAttachInfo.mRootCallbacks.performHapticFeedback(i, z2, z);
    }

    private android.os.Vibrator getSystemVibrator() {
        if (this.mVibrator != null) {
            return this.mVibrator;
        }
        android.os.Vibrator vibrator = (android.os.Vibrator) this.mContext.getSystemService(android.os.Vibrator.class);
        this.mVibrator = vibrator;
        return vibrator;
    }

    @java.lang.Deprecated
    public void setSystemUiVisibility(int i) {
        if (i != this.mSystemUiVisibility) {
            this.mSystemUiVisibility = i;
            if (this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    @java.lang.Deprecated
    public int getSystemUiVisibility() {
        return this.mSystemUiVisibility;
    }

    @java.lang.Deprecated
    public int getWindowSystemUiVisibility() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.mSystemUiVisibility;
        }
        return 0;
    }

    @java.lang.Deprecated
    public void onWindowSystemUiVisibilityChanged(int i) {
    }

    @java.lang.Deprecated
    public void dispatchWindowSystemUiVisiblityChanged(int i) {
        onWindowSystemUiVisibilityChanged(i);
    }

    @java.lang.Deprecated
    public void setOnSystemUiVisibilityChangeListener(android.view.View.OnSystemUiVisibilityChangeListener onSystemUiVisibilityChangeListener) {
        getListenerInfo().mOnSystemUiVisibilityChangeListener = onSystemUiVisibilityChangeListener;
        if (this.mParent != null && this.mAttachInfo != null && !this.mAttachInfo.mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
    }

    @java.lang.Deprecated
    public void dispatchSystemUiVisibilityChanged(int i) {
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnSystemUiVisibilityChangeListener != null) {
            listenerInfo.mOnSystemUiVisibilityChangeListener.onSystemUiVisibilityChange(i & PUBLIC_STATUS_BAR_VISIBILITY_MASK);
        }
    }

    boolean updateLocalSystemUiVisibility(int i, int i2) {
        int i3 = (i & i2) | (this.mSystemUiVisibility & (~i2));
        if (i3 != this.mSystemUiVisibility) {
            setSystemUiVisibility(i3);
            return true;
        }
        return false;
    }

    public void setDisabledSystemUiVisibility(int i) {
        if (this.mAttachInfo != null && this.mAttachInfo.mDisabledSystemUiVisibility != i) {
            this.mAttachInfo.mDisabledSystemUiVisibility = i;
            if (this.mParent != null) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    public void onSystemBarAppearanceChanged(int i) {
    }

    public static class DragShadowBuilder {
        private final java.lang.ref.WeakReference<android.view.View> mView;

        public DragShadowBuilder(android.view.View view) {
            this.mView = new java.lang.ref.WeakReference<>(view);
        }

        public DragShadowBuilder() {
            this.mView = new java.lang.ref.WeakReference<>(null);
        }

        public final android.view.View getView() {
            return this.mView.get();
        }

        public void onProvideShadowMetrics(android.graphics.Point point, android.graphics.Point point2) {
            android.view.View view = this.mView.get();
            if (view != null) {
                point.set(view.getWidth(), view.getHeight());
                point2.set(point.x / 2, point.y / 2);
            } else {
                android.util.Log.e(android.view.View.VIEW_LOG_TAG, "Asked for drag thumb metrics but no view");
            }
        }

        public void onDrawShadow(android.graphics.Canvas canvas) {
            android.view.View view = this.mView.get();
            if (view != null) {
                view.draw(canvas);
            } else {
                android.util.Log.e(android.view.View.VIEW_LOG_TAG, "Asked to draw drag shadow but no view");
            }
        }
    }

    @java.lang.Deprecated
    public final boolean startDrag(android.content.ClipData clipData, android.view.View.DragShadowBuilder dragShadowBuilder, java.lang.Object obj, int i) {
        return startDragAndDrop(clipData, dragShadowBuilder, obj, i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02bb  */
    /* JADX WARN: Type inference failed for: r15v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v3, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5, types: [android.view.Surface] */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r21v1 */
    /* JADX WARN: Type inference failed for: r21v10 */
    /* JADX WARN: Type inference failed for: r21v11, types: [android.view.SurfaceControl$Transaction] */
    /* JADX WARN: Type inference failed for: r21v2 */
    /* JADX WARN: Type inference failed for: r21v3 */
    /* JADX WARN: Type inference failed for: r21v5 */
    /* JADX WARN: Type inference failed for: r21v7 */
    /* JADX WARN: Type inference failed for: r21v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startDragAndDrop(android.content.ClipData clipData, android.view.View.DragShadowBuilder dragShadowBuilder, java.lang.Object obj, int i) {
        int i2;
        boolean isEnabled;
        android.graphics.Point point;
        android.view.SurfaceSession surfaceSession;
        android.view.SurfaceControl surfaceControl;
        java.lang.String str;
        boolean z;
        android.graphics.Canvas lockHardwareCanvas;
        boolean z2;
        int i3 = i;
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        ?? r15 = VIEW_LOG_TAG;
        if (attachInfo == null) {
            android.util.Log.w(VIEW_LOG_TAG, "startDragAndDrop called on a detached view.");
            return false;
        }
        if (!this.mAttachInfo.mViewRootImpl.mSurface.isValid()) {
            android.util.Log.w(VIEW_LOG_TAG, "startDragAndDrop called with an invalid surface.");
            return false;
        }
        if ((i3 & 256) != 0 && (i3 & 4096) != 0) {
            android.util.Log.w(VIEW_LOG_TAG, "startDragAndDrop called with both DRAG_FLAG_GLOBAL and DRAG_FLAG_GLOBAL_SAME_APPLICATION, the drag will default to DRAG_FLAG_GLOBAL_SAME_APPLICATION");
            i3 &= -257;
        }
        if (clipData != null) {
            if (com.android.window.flags.Flags.delegateUnhandledDrags()) {
                clipData.prepareToLeaveProcess((i3 & 4352) != 0);
                if ((i3 & 8192) != 0 && !hasActivityPendingIntents(clipData)) {
                    android.util.Log.w(VIEW_LOG_TAG, "startDragAndDrop called with DRAG_FLAG_START_INTENT_ON_UNHANDLED_DRAG but the clip data contains non-activity PendingIntents");
                    i2 = i3 & (-8193);
                    getBoundsOnScreen(new android.graphics.Rect(), true);
                    android.graphics.Point point2 = new android.graphics.Point();
                    this.mAttachInfo.mViewRootImpl.getLastTouchPoint(point2);
                    android.view.ViewRootImpl viewRootImpl = this.mAttachInfo.mViewRootImpl;
                    isEnabled = android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled();
                    if (!isEnabled && (i2 & 1024) != 0) {
                        try {
                            android.os.IBinder performDrag = this.mAttachInfo.mSession.performDrag(this.mAttachInfo.mWindow, i2, null, this.mAttachInfo.mViewRootImpl.getLastTouchSource(), this.mAttachInfo.mViewRootImpl.getLastTouchDeviceId(), this.mAttachInfo.mViewRootImpl.getLastTouchPointerId(), 0.0f, 0.0f, 0.0f, 0.0f, clipData);
                            if (performDrag != null) {
                                viewRootImpl.setLocalDragState(obj);
                                this.mAttachInfo.mDragToken = performDrag;
                                this.mAttachInfo.mDragData = clipData;
                                this.mAttachInfo.mViewRootImpl.setDragStartedViewForAccessibility(this);
                                z2 = true;
                                setAccessibilityDragStarted(true);
                            } else {
                                z2 = true;
                            }
                            if (performDrag != null) {
                                return z2;
                            }
                            return false;
                        } catch (java.lang.Exception e) {
                            android.util.Log.e(VIEW_LOG_TAG, "Unable to initiate a11y drag", e);
                            return false;
                        }
                    }
                    point = new android.graphics.Point();
                    android.graphics.Point point3 = new android.graphics.Point();
                    dragShadowBuilder.onProvideShadowMetrics(point, point3);
                    if (point.x >= 0 || point.y < 0 || point3.x < 0 || point3.y < 0) {
                        throw new java.lang.IllegalStateException("Drag shadow dimensions must not be negative");
                    }
                    float overrideInvertedScale = android.content.res.CompatibilityInfo.getOverrideInvertedScale();
                    if (overrideInvertedScale != 1.0f) {
                        point3.x = (int) (point3.x / overrideInvertedScale);
                        point3.y = (int) (point3.y / overrideInvertedScale);
                    }
                    if (point.x == 0 || point.y == 0) {
                        if (!sAcceptZeroSizeDragShadow) {
                            throw new java.lang.IllegalStateException("Drag shadow dimensions must be positive");
                        }
                        point.x = 1;
                        point.y = 1;
                    }
                    android.view.SurfaceSession surfaceSession2 = new android.view.SurfaceSession();
                    android.view.SurfaceControl build = new android.view.SurfaceControl.Builder(surfaceSession2).setName("drag surface").setParent(viewRootImpl.getSurfaceControl()).setBufferSize(point.x, point.y).setFormat(-3).setCallsite("View.startDragAndDrop").build();
                    if (overrideInvertedScale != 1.0f) {
                        str = new android.view.SurfaceControl.Transaction();
                        float f = 1.0f / overrideInvertedScale;
                        str.setMatrix(build, f, 0.0f, 0.0f, f).apply();
                    }
                    android.view.Surface surface = new android.view.Surface();
                    surface.copyFrom(build);
                    android.os.IBinder iBinder = null;
                    try {
                        try {
                            if (isHardwareAccelerated()) {
                                try {
                                    lockHardwareCanvas = surface.lockHardwareCanvas();
                                } catch (java.lang.Exception e2) {
                                    e = e2;
                                    surfaceSession = surfaceSession2;
                                    surfaceControl = build;
                                    str = VIEW_LOG_TAG;
                                    z = false;
                                    r15 = surface;
                                    android.util.Log.e(str, "Unable to initiate drag", e);
                                    if (iBinder == null) {
                                    }
                                    surfaceSession.kill();
                                    surfaceControl.release();
                                    return z;
                                } catch (java.lang.Throwable th) {
                                    th = th;
                                    surfaceSession = surfaceSession2;
                                    r15 = surface;
                                    surfaceControl = build;
                                    if (iBinder == null) {
                                    }
                                    surfaceSession.kill();
                                    surfaceControl.release();
                                    throw th;
                                }
                            } else {
                                lockHardwareCanvas = surface.lockCanvas(null);
                            }
                            try {
                                try {
                                } catch (java.lang.Exception e3) {
                                    e = e3;
                                } catch (java.lang.Throwable th2) {
                                    th = th2;
                                }
                            } catch (java.lang.Throwable th3) {
                                th = th3;
                            }
                            try {
                                lockHardwareCanvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
                                dragShadowBuilder.onDrawShadow(lockHardwareCanvas);
                                try {
                                    surface.unlockCanvasAndPost(lockHardwareCanvas);
                                    android.view.IWindowSession iWindowSession = this.mAttachInfo.mSession;
                                    android.view.IWindow iWindow = this.mAttachInfo.mWindow;
                                    int lastTouchSource = viewRootImpl.getLastTouchSource();
                                    int lastTouchDeviceId = viewRootImpl.getLastTouchDeviceId();
                                    int lastTouchPointerId = viewRootImpl.getLastTouchPointerId();
                                    float f2 = point2.x;
                                    float f3 = point2.y;
                                    try {
                                        float f4 = point3.x;
                                        float f5 = point3.y;
                                        z = false;
                                        surfaceSession = surfaceSession2;
                                        str = VIEW_LOG_TAG;
                                        r15 = surface;
                                        surfaceControl = build;
                                        iBinder = iWindowSession.performDrag(iWindow, i2, build, lastTouchSource, lastTouchDeviceId, lastTouchPointerId, f2, f3, f4, f5, clipData);
                                        if (iBinder != null) {
                                            try {
                                                try {
                                                    if (this.mAttachInfo.mDragSurface != null) {
                                                        this.mAttachInfo.mDragSurface.release();
                                                    }
                                                    if (this.mAttachInfo.mDragData != null) {
                                                        cleanUpPendingIntents(this.mAttachInfo.mDragData);
                                                    }
                                                    this.mAttachInfo.mDragSurface = r15;
                                                    this.mAttachInfo.mDragToken = iBinder;
                                                    this.mAttachInfo.mDragData = clipData;
                                                    viewRootImpl.setLocalDragState(obj);
                                                    if (isEnabled) {
                                                        this.mAttachInfo.mViewRootImpl.setDragStartedViewForAccessibility(this);
                                                    }
                                                } catch (java.lang.Throwable th4) {
                                                    th = th4;
                                                    if (iBinder == null) {
                                                        r15.destroy();
                                                    }
                                                    surfaceSession.kill();
                                                    surfaceControl.release();
                                                    throw th;
                                                }
                                            } catch (java.lang.Exception e4) {
                                                e = e4;
                                                android.util.Log.e(str, "Unable to initiate drag", e);
                                                if (iBinder == null) {
                                                    r15.destroy();
                                                }
                                                surfaceSession.kill();
                                                surfaceControl.release();
                                                return z;
                                            }
                                        }
                                        boolean z3 = iBinder != null;
                                        if (iBinder == null) {
                                            r15.destroy();
                                        }
                                        surfaceSession.kill();
                                        surfaceControl.release();
                                        return z3;
                                    } catch (java.lang.Exception e5) {
                                        e = e5;
                                        surfaceSession = surfaceSession2;
                                        surfaceControl = build;
                                        str = VIEW_LOG_TAG;
                                        z = false;
                                        r15 = surface;
                                        iBinder = null;
                                        android.util.Log.e(str, "Unable to initiate drag", e);
                                        if (iBinder == null) {
                                        }
                                        surfaceSession.kill();
                                        surfaceControl.release();
                                        return z;
                                    } catch (java.lang.Throwable th5) {
                                        th = th5;
                                        surfaceSession = surfaceSession2;
                                        r15 = surface;
                                        surfaceControl = build;
                                        iBinder = null;
                                        if (iBinder == null) {
                                        }
                                        surfaceSession.kill();
                                        surfaceControl.release();
                                        throw th;
                                    }
                                } catch (java.lang.Exception e6) {
                                    e = e6;
                                    z = false;
                                    surfaceSession = surfaceSession2;
                                    surfaceControl = build;
                                    str = VIEW_LOG_TAG;
                                    r15 = surface;
                                    android.util.Log.e(str, "Unable to initiate drag", e);
                                    if (iBinder == null) {
                                    }
                                    surfaceSession.kill();
                                    surfaceControl.release();
                                    return z;
                                }
                            } catch (java.lang.Throwable th6) {
                                th = th6;
                                surface.unlockCanvasAndPost(lockHardwareCanvas);
                                throw th;
                            }
                        } catch (java.lang.Throwable th7) {
                            th = th7;
                        }
                    } catch (java.lang.Exception e7) {
                        e = e7;
                    }
                }
            } else {
                clipData.prepareToLeaveProcess((i3 & 256) != 0);
            }
        }
        i2 = i3;
        getBoundsOnScreen(new android.graphics.Rect(), true);
        android.graphics.Point point22 = new android.graphics.Point();
        this.mAttachInfo.mViewRootImpl.getLastTouchPoint(point22);
        android.view.ViewRootImpl viewRootImpl2 = this.mAttachInfo.mViewRootImpl;
        isEnabled = android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled();
        if (!isEnabled) {
        }
        point = new android.graphics.Point();
        android.graphics.Point point32 = new android.graphics.Point();
        dragShadowBuilder.onProvideShadowMetrics(point, point32);
        if (point.x >= 0) {
        }
        throw new java.lang.IllegalStateException("Drag shadow dimensions must not be negative");
    }

    static boolean hasActivityPendingIntents(android.content.ClipData clipData) {
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            android.content.ClipData.Item itemAt = clipData.getItemAt(i);
            if (itemAt.getIntentSender() != null && new android.app.PendingIntent(itemAt.getIntentSender().getTarget()).isActivity()) {
                return true;
            }
        }
        return false;
    }

    static void cleanUpPendingIntents(android.content.ClipData clipData) {
        int itemCount = clipData.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            android.content.ClipData.Item itemAt = clipData.getItemAt(i);
            if (itemAt.getIntentSender() != null) {
                new android.app.PendingIntent(itemAt.getIntentSender().getTarget()).cancel();
            }
        }
    }

    void setAccessibilityDragStarted(boolean z) {
        int i;
        int i2 = this.mPrivateFlags4;
        if (z) {
            i = 32768 | i2;
        } else {
            i = (-32769) & i2;
        }
        if (i != this.mPrivateFlags4) {
            this.mPrivateFlags4 = i;
            sendWindowContentChangedAccessibilityEvent(0);
        }
    }

    private boolean startedSystemDragForAccessibility() {
        return (this.mPrivateFlags4 & 32768) != 0;
    }

    public final void cancelDragAndDrop() {
        if (this.mAttachInfo == null) {
            android.util.Log.w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
            return;
        }
        if (this.mAttachInfo.mDragToken != null) {
            try {
                this.mAttachInfo.mSession.cancelDragAndDrop(this.mAttachInfo.mDragToken, false);
            } catch (java.lang.Exception e) {
                android.util.Log.e(VIEW_LOG_TAG, "Unable to cancel drag", e);
            }
            this.mAttachInfo.mDragToken = null;
            return;
        }
        android.util.Log.e(VIEW_LOG_TAG, "No active drag to cancel");
    }

    public final void updateDragShadow(android.view.View.DragShadowBuilder dragShadowBuilder) {
        android.graphics.Canvas lockCanvas;
        if (this.mAttachInfo == null) {
            android.util.Log.w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
            return;
        }
        if (this.mAttachInfo.mDragToken != null) {
            try {
                if (isHardwareAccelerated()) {
                    lockCanvas = this.mAttachInfo.mDragSurface.lockHardwareCanvas();
                } else {
                    lockCanvas = this.mAttachInfo.mDragSurface.lockCanvas(null);
                }
                try {
                    lockCanvas.drawColor(0, android.graphics.PorterDuff.Mode.CLEAR);
                    dragShadowBuilder.onDrawShadow(lockCanvas);
                    this.mAttachInfo.mDragSurface.unlockCanvasAndPost(lockCanvas);
                    return;
                } catch (java.lang.Throwable th) {
                    this.mAttachInfo.mDragSurface.unlockCanvasAndPost(lockCanvas);
                    throw th;
                }
            } catch (java.lang.Exception e) {
                android.util.Log.e(VIEW_LOG_TAG, "Unable to update drag shadow", e);
                return;
            }
        }
        android.util.Log.e(VIEW_LOG_TAG, "No active drag");
    }

    public final boolean startMovingTask(float f, float f2) {
        try {
            return this.mAttachInfo.mSession.startMovingTask(this.mAttachInfo.mWindow, f, f2);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(VIEW_LOG_TAG, "Unable to start moving", e);
            return false;
        }
    }

    public void finishMovingTask() {
        try {
            this.mAttachInfo.mSession.finishMovingTask(this.mAttachInfo.mWindow);
        } catch (android.os.RemoteException e) {
            android.util.Log.e(VIEW_LOG_TAG, "Unable to finish moving", e);
        }
    }

    public boolean onDragEvent(android.view.DragEvent dragEvent) {
        if (this.mListenerInfo == null || this.mListenerInfo.mOnReceiveContentListener == null) {
            return false;
        }
        if (dragEvent.getAction() == 1) {
            return true;
        }
        if (dragEvent.getAction() != 3) {
            return false;
        }
        android.view.DragAndDropPermissions obtain = android.view.DragAndDropPermissions.obtain(dragEvent);
        if (obtain != null) {
            obtain.takeTransient();
        }
        android.view.ContentInfo build = new android.view.ContentInfo.Builder(dragEvent.getClipData(), 3).setDragAndDropPermissions(obtain).build();
        return performReceiveContent(build) != build;
    }

    boolean dispatchDragEnterExitInPreN(android.view.DragEvent dragEvent) {
        return callDragEventHandler(dragEvent);
    }

    public boolean dispatchDragEvent(android.view.DragEvent dragEvent) {
        dragEvent.mEventHandlerWasCalled = true;
        if (dragEvent.mAction == 2 || dragEvent.mAction == 3) {
            getViewRootImpl().setDragFocus(this, dragEvent);
        }
        return callDragEventHandler(dragEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006b, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final boolean callDragEventHandler(android.view.DragEvent dragEvent) {
        boolean onDragEvent;
        android.view.View.ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnDragListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnDragListener.onDrag(this, dragEvent)) {
            onDragEvent = true;
        } else {
            onDragEvent = onDragEvent(dragEvent);
        }
        switch (dragEvent.mAction) {
            case 1:
                if (onDragEvent && listenerInfo != null && listenerInfo.mOnDragListener != null) {
                    sendWindowContentChangedAccessibilityEvent(0);
                    break;
                }
                break;
            case 3:
                if (onDragEvent && listenerInfo != null && (listenerInfo.mOnDragListener != null || listenerInfo.mOnReceiveContentListener != null)) {
                    sendWindowContentChangedAccessibilityEvent(256);
                    break;
                }
                break;
            case 4:
                sendWindowContentChangedAccessibilityEvent(0);
                this.mPrivateFlags2 &= -4;
                refreshDrawableState();
                break;
            case 5:
                this.mPrivateFlags2 |= 2;
                refreshDrawableState();
                break;
            case 6:
                this.mPrivateFlags2 &= -3;
                refreshDrawableState();
                break;
        }
    }

    boolean canAcceptDrag() {
        return (this.mPrivateFlags2 & 1) != 0;
    }

    void sendWindowContentChangedAccessibilityEvent(int i) {
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(i);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public void onCloseSystemDialogs(java.lang.String str) {
    }

    public void applyDrawableToTransparentRegion(android.graphics.drawable.Drawable drawable, android.graphics.Region region) {
        android.graphics.Region transparentRegion = drawable.getTransparentRegion();
        android.graphics.Rect bounds = drawable.getBounds();
        android.view.View.AttachInfo attachInfo = this.mAttachInfo;
        if (transparentRegion != null && attachInfo != null) {
            int right = getRight() - getLeft();
            int bottom = getBottom() - getTop();
            if (bounds.left > 0) {
                transparentRegion.op(0, 0, bounds.left, bottom, android.graphics.Region.Op.UNION);
            }
            if (bounds.right < right) {
                transparentRegion.op(bounds.right, 0, right, bottom, android.graphics.Region.Op.UNION);
            }
            if (bounds.top > 0) {
                transparentRegion.op(0, 0, right, bounds.top, android.graphics.Region.Op.UNION);
            }
            if (bounds.bottom < bottom) {
                transparentRegion.op(0, bounds.bottom, right, bottom, android.graphics.Region.Op.UNION);
            }
            int[] iArr = attachInfo.mTransparentLocation;
            getLocationInWindow(iArr);
            transparentRegion.translate(iArr[0], iArr[1]);
            region.op(transparentRegion, android.graphics.Region.Op.INTERSECT);
            return;
        }
        region.op(bounds, android.graphics.Region.Op.DIFFERENCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkForLongClick(long j, float f, float f2, int i) {
        if ((this.mViewFlags & 2097152) == 2097152 || (this.mViewFlags & 1073741824) == 1073741824) {
            this.mHasPerformedLongPress = false;
            if (this.mPendingCheckForLongPress == null) {
                this.mPendingCheckForLongPress = new android.view.View.CheckForLongPress();
            }
            this.mPendingCheckForLongPress.setAnchor(f, f2);
            this.mPendingCheckForLongPress.rememberWindowAttachCount();
            this.mPendingCheckForLongPress.rememberPressedState();
            this.mPendingCheckForLongPress.setClassification(i);
            postDelayed(this.mPendingCheckForLongPress, j);
        }
    }

    public static android.view.View inflate(android.content.Context context, int i, android.view.ViewGroup viewGroup) {
        return android.view.LayoutInflater.from(context).inflate(i, viewGroup);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        boolean z2;
        boolean z3;
        int i9 = this.mOverScrollMode;
        boolean z4 = computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        boolean z5 = computeVerticalScrollRange() > computeVerticalScrollExtent();
        boolean z6 = i9 == 0 || (i9 == 1 && z4);
        boolean z7 = i9 == 0 || (i9 == 1 && z5);
        int i10 = i3 + i;
        if (!z6) {
            i7 = 0;
        }
        int i11 = i4 + i2;
        if (!z7) {
            i8 = 0;
        }
        int i12 = -i7;
        int i13 = i7 + i5;
        int i14 = -i8;
        int i15 = i8 + i6;
        if (i10 > i13) {
            i10 = i13;
            z2 = true;
        } else if (i10 >= i12) {
            z2 = false;
        } else {
            i10 = i12;
            z2 = true;
        }
        if (i11 > i15) {
            i11 = i15;
            z3 = true;
        } else if (i11 >= i14) {
            z3 = false;
        } else {
            i11 = i14;
            z3 = true;
        }
        onOverScrolled(i10, i11, z2, z3);
        return z2 || z3;
    }

    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int i) {
        if (i != 0 && i != 1 && i != 2) {
            throw new java.lang.IllegalArgumentException("Invalid overscroll mode " + i);
        }
        this.mOverScrollMode = i;
    }

    public void setNestedScrollingEnabled(boolean z) {
        if (z) {
            this.mPrivateFlags3 |= 128;
        } else {
            stopNestedScroll();
            this.mPrivateFlags3 &= android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE;
        }
    }

    public boolean isNestedScrollingEnabled() {
        return (this.mPrivateFlags3 & 128) == 128;
    }

    public boolean startNestedScroll(int i) {
        if (hasNestedScrollingParent()) {
            return true;
        }
        if (isNestedScrollingEnabled()) {
            android.view.View view = this;
            for (android.view.ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
                try {
                    if (parent.onStartNestedScroll(view, this, i)) {
                        this.mNestedScrollingParent = parent;
                        parent.onNestedScrollAccepted(view, this, i);
                        return true;
                    }
                } catch (java.lang.AbstractMethodError e) {
                    android.util.Log.e(VIEW_LOG_TAG, "ViewParent " + parent + " does not implement interface method onStartNestedScroll", e);
                }
                if (parent instanceof android.view.View) {
                    view = parent;
                }
            }
            return false;
        }
        return false;
    }

    public void stopNestedScroll() {
        if (this.mNestedScrollingParent != null) {
            this.mNestedScrollingParent.onStopNestedScroll(this);
            this.mNestedScrollingParent = null;
        }
    }

    public boolean hasNestedScrollingParent() {
        return this.mNestedScrollingParent != null;
    }

    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (i != 0 || i2 != 0 || i3 != 0 || i4 != 0) {
                if (iArr == null) {
                    i5 = 0;
                    i6 = 0;
                } else {
                    getLocationInWindow(iArr);
                    i5 = iArr[0];
                    i6 = iArr[1];
                }
                this.mNestedScrollingParent.onNestedScroll(this, i, i2, i3, i4);
                if (iArr != null) {
                    getLocationInWindow(iArr);
                    iArr[0] = iArr[0] - i5;
                    iArr[1] = iArr[1] - i6;
                }
                return true;
            }
            if (iArr != null) {
                iArr[0] = 0;
                iArr[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        int i3;
        int i4;
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (i != 0 || i2 != 0) {
                if (iArr2 == null) {
                    i3 = 0;
                    i4 = 0;
                } else {
                    getLocationInWindow(iArr2);
                    i3 = iArr2[0];
                    i4 = iArr2[1];
                }
                if (iArr == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new int[2];
                    }
                    iArr = this.mTempNestedScrollConsumed;
                }
                iArr[0] = 0;
                iArr[1] = 0;
                this.mNestedScrollingParent.onNestedPreScroll(this, i, i2, iArr);
                if (iArr2 != null) {
                    getLocationInWindow(iArr2);
                    iArr2[0] = iArr2[0] - i3;
                    iArr2[1] = iArr2[1] - i4;
                }
                return (iArr[0] == 0 && iArr[1] == 0) ? false : true;
            }
            if (iArr2 != null) {
                iArr2[0] = 0;
                iArr2[1] = 0;
            }
        }
        return false;
    }

    public boolean dispatchNestedFling(float f, float f2, boolean z) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedFling(this, f, f2, z);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        if (isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            return this.mNestedScrollingParent.onNestedPreFling(this, f, f2);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            if (!this.mContext.getTheme().resolveAttribute(16842829, typedValue, true)) {
                throw new java.lang.IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
            this.mVerticalScrollFactor = typedValue.getDimension(this.mContext.getResources().getDisplayMetrics());
        }
        return this.mVerticalScrollFactor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getHorizontalScrollFactor() {
        return getVerticalScrollFactor();
    }

    @android.view.ViewDebug.ExportedProperty(category = "text", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "INHERIT"), @android.view.ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @android.view.ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @android.view.ViewDebug.IntToString(from = 3, to = "LTR"), @android.view.ViewDebug.IntToString(from = 4, to = "RTL"), @android.view.ViewDebug.IntToString(from = 5, to = "LOCALE"), @android.view.ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @android.view.ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getRawTextDirection() {
        return (this.mPrivateFlags2 & 448) >> 6;
    }

    public void setTextDirection(int i) {
        if (getRawTextDirection() != i) {
            this.mPrivateFlags2 &= -449;
            resetResolvedTextDirection();
            this.mPrivateFlags2 = ((i << 6) & 448) | this.mPrivateFlags2;
            resolveTextDirection();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "text", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "INHERIT"), @android.view.ViewDebug.IntToString(from = 1, to = "FIRST_STRONG"), @android.view.ViewDebug.IntToString(from = 2, to = "ANY_RTL"), @android.view.ViewDebug.IntToString(from = 3, to = "LTR"), @android.view.ViewDebug.IntToString(from = 4, to = "RTL"), @android.view.ViewDebug.IntToString(from = 5, to = "LOCALE"), @android.view.ViewDebug.IntToString(from = 6, to = "FIRST_STRONG_LTR"), @android.view.ViewDebug.IntToString(from = 7, to = "FIRST_STRONG_RTL")})
    public int getTextDirection() {
        return (this.mPrivateFlags2 & 7168) >> 10;
    }

    public boolean resolveTextDirection() {
        int i;
        this.mPrivateFlags2 &= -7681;
        if (hasRtlSupport()) {
            int rawTextDirection = getRawTextDirection();
            switch (rawTextDirection) {
                case 0:
                    if (!canResolveTextDirection()) {
                        this.mPrivateFlags2 |= 1024;
                        return false;
                    }
                    try {
                        if (!this.mParent.isTextDirectionResolved()) {
                            this.mPrivateFlags2 |= 1024;
                            return false;
                        }
                        try {
                            i = this.mParent.getTextDirection();
                        } catch (java.lang.AbstractMethodError e) {
                            android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                            i = 3;
                        }
                        switch (i) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                                this.mPrivateFlags2 = (i << 10) | this.mPrivateFlags2;
                                break;
                            default:
                                this.mPrivateFlags2 |= 1024;
                                break;
                        }
                    } catch (java.lang.AbstractMethodError e2) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 |= 1536;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    this.mPrivateFlags2 |= rawTextDirection << 10;
                    break;
                default:
                    this.mPrivateFlags2 |= 1024;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 1024;
        }
        this.mPrivateFlags2 |= 512;
        return true;
    }

    public boolean canResolveTextDirection() {
        switch (getRawTextDirection()) {
            case 0:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveTextDirection();
                    } catch (java.lang.AbstractMethodError e) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedTextDirection() {
        this.mPrivateFlags2 &= -7681;
        this.mPrivateFlags2 |= 1024;
    }

    public boolean isTextDirectionInherited() {
        return getRawTextDirection() == 0;
    }

    public boolean isTextDirectionResolved() {
        return (this.mPrivateFlags2 & 512) == 512;
    }

    @android.view.ViewDebug.ExportedProperty(category = "text", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "INHERIT"), @android.view.ViewDebug.IntToString(from = 1, to = "GRAVITY"), @android.view.ViewDebug.IntToString(from = 2, to = "TEXT_START"), @android.view.ViewDebug.IntToString(from = 3, to = "TEXT_END"), @android.view.ViewDebug.IntToString(from = 4, to = "CENTER"), @android.view.ViewDebug.IntToString(from = 5, to = "VIEW_START"), @android.view.ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    public int getRawTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_MASK) >> 13;
    }

    public void setTextAlignment(int i) {
        if (i != getRawTextAlignment()) {
            this.mPrivateFlags2 &= -57345;
            resetResolvedTextAlignment();
            this.mPrivateFlags2 = ((i << 13) & PFLAG2_TEXT_ALIGNMENT_MASK) | this.mPrivateFlags2;
            resolveTextAlignment();
            onRtlPropertiesChanged(getLayoutDirection());
            requestLayout();
            invalidate(true);
        }
    }

    @android.view.ViewDebug.ExportedProperty(category = "text", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = "INHERIT"), @android.view.ViewDebug.IntToString(from = 1, to = "GRAVITY"), @android.view.ViewDebug.IntToString(from = 2, to = "TEXT_START"), @android.view.ViewDebug.IntToString(from = 3, to = "TEXT_END"), @android.view.ViewDebug.IntToString(from = 4, to = "CENTER"), @android.view.ViewDebug.IntToString(from = 5, to = "VIEW_START"), @android.view.ViewDebug.IntToString(from = 6, to = "VIEW_END")})
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK) >> 17;
    }

    public boolean resolveTextAlignment() {
        int i;
        this.mPrivateFlags2 &= -983041;
        if (hasRtlSupport()) {
            int rawTextAlignment = getRawTextAlignment();
            switch (rawTextAlignment) {
                case 0:
                    if (!canResolveTextAlignment()) {
                        this.mPrivateFlags2 |= 131072;
                        return false;
                    }
                    try {
                        if (!this.mParent.isTextAlignmentResolved()) {
                            this.mPrivateFlags2 |= 131072;
                            return false;
                        }
                        try {
                            i = this.mParent.getTextAlignment();
                        } catch (java.lang.AbstractMethodError e) {
                            android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                            i = 1;
                        }
                        switch (i) {
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.mPrivateFlags2 = (i << 17) | this.mPrivateFlags2;
                                break;
                            default:
                                this.mPrivateFlags2 |= 131072;
                                break;
                        }
                    } catch (java.lang.AbstractMethodError e2) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e2);
                        this.mPrivateFlags2 |= 196608;
                        return true;
                    }
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    this.mPrivateFlags2 |= rawTextAlignment << 17;
                    break;
                default:
                    this.mPrivateFlags2 |= 131072;
                    break;
            }
        } else {
            this.mPrivateFlags2 |= 131072;
        }
        this.mPrivateFlags2 |= 65536;
        return true;
    }

    public boolean canResolveTextAlignment() {
        switch (getRawTextAlignment()) {
            case 0:
                if (this.mParent != null) {
                    try {
                        return this.mParent.canResolveTextAlignment();
                    } catch (java.lang.AbstractMethodError e) {
                        android.util.Log.e(VIEW_LOG_TAG, this.mParent.getClass().getSimpleName() + " does not fully implement ViewParent", e);
                        return false;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    public void resetResolvedTextAlignment() {
        this.mPrivateFlags2 &= -983041;
        this.mPrivateFlags2 |= 131072;
    }

    public boolean isTextAlignmentInherited() {
        return getRawTextAlignment() == 0;
    }

    public boolean isTextAlignmentResolved() {
        return (this.mPrivateFlags2 & 65536) == 65536;
    }

    public static int generateViewId() {
        int i;
        int i2;
        do {
            i = sNextGeneratedId.get();
            i2 = i + 1;
            if (i2 > 16777215) {
                i2 = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(i, i2));
        return i;
    }

    private static boolean isViewIdGenerated(int i) {
        return ((-16777216) & i) == 0 && (i & 16777215) != 0;
    }

    public void captureTransitioningViews(java.util.List<android.view.View> list) {
        if (getVisibility() == 0) {
            list.add(this);
        }
    }

    public void findNamedViews(java.util.Map<java.lang.String, android.view.View> map) {
        java.lang.String transitionName;
        if ((getVisibility() == 0 || this.mGhostView != null) && (transitionName = getTransitionName()) != null) {
            map.put(transitionName, this);
        }
    }

    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        float x = motionEvent.getX(i);
        float y = motionEvent.getY(i);
        if (isDraggingScrollBar() || isOnScrollbarThumb(x, y) || !motionEvent.isFromSource(8194)) {
            return null;
        }
        return this.mMousePointerIcon;
    }

    public void setPointerIcon(android.view.PointerIcon pointerIcon) {
        this.mMousePointerIcon = pointerIcon;
        if (com.android.input.flags.Flags.enablePointerChoreographer()) {
            android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl == null) {
                return;
            }
            viewRootImpl.refreshPointerIcon();
            return;
        }
        if (this.mAttachInfo == null || this.mAttachInfo.mHandlingPointerEvent) {
            return;
        }
        try {
            this.mAttachInfo.mSession.updatePointerIcon(this.mAttachInfo.mWindow);
        } catch (android.os.RemoteException e) {
        }
    }

    public android.view.PointerIcon getPointerIcon() {
        return this.mMousePointerIcon;
    }

    public boolean hasPointerCapture() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.hasPointerCapture();
    }

    public void requestPointerCapture() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(true);
        }
    }

    public void releasePointerCapture() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(false);
        }
    }

    public void onPointerCaptureChange(boolean z) {
    }

    public void dispatchPointerCaptureChanged(boolean z) {
        onPointerCaptureChange(z);
    }

    public boolean onCapturedPointerEvent(android.view.MotionEvent motionEvent) {
        return false;
    }

    public void setOnCapturedPointerListener(android.view.View.OnCapturedPointerListener onCapturedPointerListener) {
        getListenerInfo().mOnCapturedPointerListener = onCapturedPointerListener;
    }

    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface MeasureSpecMode {
        }

        public static int makeMeasureSpec(int i, int i2) {
            if (android.view.View.sUseBrokenMakeMeasureSpec) {
                return i + i2;
            }
            return (i & android.view.View.LAST_APP_AUTOFILL_ID) | (i2 & (-1073741824));
        }

        public static int makeSafeMeasureSpec(int i, int i2) {
            if (android.view.View.sUseZeroUnspecifiedMeasureSpec && i2 == 0) {
                return 0;
            }
            return makeMeasureSpec(i, i2);
        }

        public static int getMode(int i) {
            return i & (-1073741824);
        }

        public static int getSize(int i) {
            return i & android.view.View.LAST_APP_AUTOFILL_ID;
        }

        static int adjust(int i, int i2) {
            int mode = getMode(i);
            int size = getSize(i);
            int i3 = 0;
            if (mode == 0) {
                return makeMeasureSpec(size, 0);
            }
            int i4 = size + i2;
            if (i4 >= 0) {
                i3 = i4;
            } else {
                android.util.Log.e(android.view.View.VIEW_LOG_TAG, "MeasureSpec.adjust: new size would be negative! (" + i4 + ") spec: " + toString(i) + " delta: " + i2);
            }
            return makeMeasureSpec(i3, mode);
        }

        public static java.lang.String toString(int i) {
            int mode = getMode(i);
            int size = getSize(i);
            java.lang.StringBuilder sb = new java.lang.StringBuilder("MeasureSpec: ");
            if (mode == 0) {
                sb.append("UNSPECIFIED ");
            } else if (mode == 1073741824) {
                sb.append("EXACTLY ");
            } else if (mode == Integer.MIN_VALUE) {
                sb.append("AT_MOST ");
            } else {
                sb.append(mode).append(" ");
            }
            sb.append(size);
            return sb.toString();
        }
    }

    private final class CheckForLongPress implements java.lang.Runnable {
        private int mClassification;
        private boolean mOriginalPressedState;
        private int mOriginalWindowAttachCount;
        private float mX;
        private float mY;

        private CheckForLongPress() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mOriginalPressedState == android.view.View.this.isPressed() && android.view.View.this.mParent != null && this.mOriginalWindowAttachCount == android.view.View.this.mWindowAttachCount) {
                android.view.View.this.recordGestureClassification(this.mClassification);
                if (android.view.View.this.performLongClick(this.mX, this.mY)) {
                    android.view.View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        public void rememberWindowAttachCount() {
            this.mOriginalWindowAttachCount = android.view.View.this.mWindowAttachCount;
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = android.view.View.this.isPressed();
        }

        public void setClassification(int i) {
            this.mClassification = i;
        }
    }

    private final class CheckForTap implements java.lang.Runnable {
        public float x;
        public float y;

        private CheckForTap() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View.this.mPrivateFlags &= -33554433;
            android.view.View.this.setPressed(true, this.x, this.y);
            android.view.View.this.checkForLongClick(android.view.ViewConfiguration.getLongPressTimeout() - android.view.ViewConfiguration.getTapTimeout(), this.x, this.y, 3);
        }
    }

    private final class PerformClick implements java.lang.Runnable {
        private PerformClick() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View.this.recordGestureClassification(1);
            android.view.View.this.performClickInternal();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recordGestureClassification(int i) {
        if (i == 0) {
            return;
        }
        com.android.internal.util.FrameworkStatsLog.write(177, getClass().getName(), i);
    }

    public android.view.ViewPropertyAnimator animate() {
        if (this.mAnimator == null) {
            this.mAnimator = new android.view.ViewPropertyAnimator(this);
        }
        return this.mAnimator;
    }

    public final void setTransitionName(java.lang.String str) {
        this.mTransitionName = str;
    }

    @android.view.ViewDebug.ExportedProperty
    public java.lang.String getTransitionName() {
        return this.mTransitionName;
    }

    public void requestKeyboardShortcuts(java.util.List<android.view.KeyboardShortcutGroup> list, int i) {
    }

    public interface OnLongClickListener {
        boolean onLongClick(android.view.View view);

        default boolean onLongClickUseDefaultHapticFeedback(android.view.View view) {
            return true;
        }
    }

    private final class UnsetPressedState implements java.lang.Runnable {
        private UnsetPressedState() {
        }

        @Override // java.lang.Runnable
        public void run() {
            android.view.View.this.setPressed(false);
        }
    }

    private static class VisibilityChangeForAutofillHandler extends android.os.Handler {
        private final android.view.autofill.AutofillManager mAfm;
        private final android.view.View mView;

        private VisibilityChangeForAutofillHandler(android.view.autofill.AutofillManager autofillManager, android.view.View view) {
            this.mAfm = autofillManager;
            this.mView = view;
        }

        @Override // android.os.Handler
        public void handleMessage(android.os.Message message) {
            this.mAfm.notifyViewVisibilityChanged(this.mView, this.mView.isShown());
        }
    }

    public static class BaseSavedState extends android.view.AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final android.os.Parcelable.Creator<android.view.View.BaseSavedState> CREATOR = new android.os.Parcelable.ClassLoaderCreator<android.view.View.BaseSavedState>() { // from class: android.view.View.BaseSavedState.1
            @Override // android.os.Parcelable.Creator
            public android.view.View.BaseSavedState createFromParcel(android.os.Parcel parcel) {
                return new android.view.View.BaseSavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public android.view.View.BaseSavedState createFromParcel(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
                return new android.view.View.BaseSavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public android.view.View.BaseSavedState[] newArray(int i) {
                return new android.view.View.BaseSavedState[i];
            }
        };
        static final int IS_AUTOFILLED = 2;
        static final int START_ACTIVITY_REQUESTED_WHO_SAVED = 1;
        int mAutofillViewId;
        boolean mHideHighlight;
        boolean mIsAutofilled;
        int mSavedData;
        java.lang.String mStartActivityRequestWhoSaved;

        public BaseSavedState(android.os.Parcel parcel) {
            this(parcel, null);
        }

        public BaseSavedState(android.os.Parcel parcel, java.lang.ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mSavedData = parcel.readInt();
            this.mStartActivityRequestWhoSaved = parcel.readString();
            this.mIsAutofilled = parcel.readBoolean();
            this.mHideHighlight = parcel.readBoolean();
            this.mAutofillViewId = parcel.readInt();
        }

        public BaseSavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mSavedData);
            parcel.writeString(this.mStartActivityRequestWhoSaved);
            parcel.writeBoolean(this.mIsAutofilled);
            parcel.writeBoolean(this.mHideHighlight);
            parcel.writeInt(this.mAutofillViewId);
        }
    }

    static final class AttachInfo {
        int mAccessibilityFetchFlags;
        android.graphics.drawable.Drawable mAccessibilityFocusDrawable;
        boolean mAlwaysConsumeSystemBars;
        float mApplicationScale;
        android.graphics.drawable.Drawable mAutofilledDrawable;
        android.graphics.Canvas mCanvas;
        android.util.SparseArray<java.util.ArrayList<java.lang.Object>> mContentCaptureEvents;
        android.view.contentcapture.ContentCaptureManager mContentCaptureManager;
        android.view.Window.OnContentApplyWindowInsetsListener mContentOnApplyWindowInsetsListener;
        int mDisabledSystemUiVisibility;
        android.view.Display mDisplay;
        android.content.ClipData mDragData;
        public android.view.Surface mDragSurface;
        android.os.IBinder mDragToken;
        long mDrawingTime;
        boolean mForceReportNewAttributes;
        final android.os.Handler mHandler;
        boolean mHandlingPointerEvent;
        boolean mHardwareAccelerated;
        boolean mHardwareAccelerationRequested;
        boolean mHasNonEmptyGivenInternalInsets;
        boolean mHasSystemUiListeners;
        boolean mHasWindowFocus;
        android.view.IWindowId mIWindowId;
        boolean mInTouchMode;
        boolean mKeepScreenOn;
        int mLeashedParentAccessibilityViewId;
        android.os.IBinder mLeashedParentToken;
        boolean mNeedsUpdateLightCenter;
        android.os.IBinder mPanelParentWindowToken;
        java.util.List<android.graphics.RenderNode> mPendingAnimatingRenderNodes;
        boolean mReadyForContentCaptureUpdates;
        boolean mRecomputeGlobalAttributes;
        final android.view.View.AttachInfo.Callbacks mRootCallbacks;
        android.view.View mRootView;
        boolean mScalingRequired;
        com.android.internal.view.ScrollCaptureInternal mScrollCaptureInternal;
        int mSensitiveViewsCount;
        final android.view.IWindowSession mSession;
        int mSystemUiVisibility;
        android.view.ThreadedRenderer mThreadedRenderer;
        android.view.View mTooltipHost;
        final android.view.ViewTreeObserver mTreeObserver;
        boolean mUnbufferedDispatchRequested;
        boolean mUse32BitDrawingCache;
        android.view.View mViewRequestingLayout;
        final android.view.ViewRootImpl mViewRootImpl;
        boolean mViewScrollChanged;
        boolean mViewVisibilityChanged;
        final android.view.IWindow mWindow;
        android.view.WindowId mWindowId;
        int mWindowLeft;
        android.graphics.Matrix mWindowMatrixInEmbeddedHierarchy;
        final android.os.IBinder mWindowToken;
        int mWindowTop;
        int mWindowVisibility;
        int mDisplayState = 0;
        final android.graphics.Rect mContentInsets = new android.graphics.Rect();
        final android.graphics.Rect mVisibleInsets = new android.graphics.Rect();
        final android.graphics.Rect mStableInsets = new android.graphics.Rect();
        final android.graphics.Rect mCaptionInsets = new android.graphics.Rect();
        final android.view.ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets = new android.view.ViewTreeObserver.InternalInsetsInfo();
        final java.util.ArrayList<android.view.View> mScrollContainers = new java.util.ArrayList<>();
        final android.view.KeyEvent.DispatcherState mKeyDispatchState = new android.view.KeyEvent.DispatcherState();
        final int[] mTransparentLocation = new int[2];
        final int[] mInvalidateChildLocation = new int[2];
        final int[] mTmpLocation = new int[2];
        final float[] mTmpTransformLocation = new float[2];
        final android.graphics.Rect mTmpInvalRect = new android.graphics.Rect();
        final android.graphics.RectF mTmpTransformRect = new android.graphics.RectF();
        final android.graphics.RectF mTmpTransformRect1 = new android.graphics.RectF();
        final java.util.List<android.graphics.RectF> mTmpRectList = new java.util.ArrayList();
        final android.graphics.Matrix mTmpMatrix = new android.graphics.Matrix();
        final android.view.animation.Transformation mTmpTransformation = new android.view.animation.Transformation();
        final android.graphics.Outline mTmpOutline = new android.graphics.Outline();
        final java.util.ArrayList<android.view.View> mTempArrayList = new java.util.ArrayList<>(24);
        boolean mNextFocusLooped = false;
        int mAccessibilityWindowId = -1;
        boolean mDebugLayout = android.sysprop.DisplayProperties.debug_layout().orElse(false).booleanValue();
        final android.graphics.Point mPoint = new android.graphics.Point();

        interface Callbacks {
            boolean performHapticFeedback(int i, boolean z, boolean z2);

            void playSoundEffect(int i);
        }

        static class InvalidateInfo {
            private static final int POOL_LIMIT = 10;
            private static final android.util.Pools.SynchronizedPool<android.view.View.AttachInfo.InvalidateInfo> sPool = new android.util.Pools.SynchronizedPool<>(10);
            int bottom;
            int left;
            int right;
            android.view.View target;
            int top;

            InvalidateInfo() {
            }

            public static android.view.View.AttachInfo.InvalidateInfo obtain() {
                android.view.View.AttachInfo.InvalidateInfo acquire = sPool.acquire();
                return acquire != null ? acquire : new android.view.View.AttachInfo.InvalidateInfo();
            }

            public void recycle() {
                this.target = null;
                sPool.release(this);
            }
        }

        AttachInfo(android.view.IWindowSession iWindowSession, android.view.IWindow iWindow, android.view.Display display, android.view.ViewRootImpl viewRootImpl, android.os.Handler handler, android.view.View.AttachInfo.Callbacks callbacks, android.content.Context context) {
            this.mSession = iWindowSession;
            this.mWindow = iWindow;
            this.mWindowToken = iWindow.asBinder();
            this.mDisplay = display;
            this.mViewRootImpl = viewRootImpl;
            this.mHandler = handler;
            this.mRootCallbacks = callbacks;
            this.mTreeObserver = new android.view.ViewTreeObserver(context);
        }

        void increaseSensitiveViewsCount() {
            if (this.mSensitiveViewsCount == 0) {
                this.mViewRootImpl.notifySensitiveContentAppProtection(true);
            }
            this.mSensitiveViewsCount++;
        }

        void decreaseSensitiveViewsCount() {
            this.mSensitiveViewsCount--;
            if (this.mSensitiveViewsCount == 0) {
                this.mViewRootImpl.notifySensitiveContentAppProtection(false);
            }
            if (this.mSensitiveViewsCount < 0) {
                android.util.Log.wtf(android.view.View.VIEW_LOG_TAG, "mSensitiveViewsCount is negative" + this.mSensitiveViewsCount);
                this.mSensitiveViewsCount = 0;
            }
        }

        android.view.contentcapture.ContentCaptureManager getContentCaptureManager(android.content.Context context) {
            if (this.mContentCaptureManager != null) {
                return this.mContentCaptureManager;
            }
            this.mContentCaptureManager = (android.view.contentcapture.ContentCaptureManager) context.getSystemService(android.view.contentcapture.ContentCaptureManager.class);
            return this.mContentCaptureManager;
        }

        void delayNotifyContentCaptureInsetsEvent(android.graphics.Insets insets) {
            if (this.mContentCaptureManager == null) {
                return;
            }
            ensureEvents(this.mContentCaptureManager.getMainContentCaptureSession()).add(insets);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void delayNotifyContentCaptureEvent(android.view.contentcapture.ContentCaptureSession contentCaptureSession, android.view.View view, boolean z) {
            java.util.ArrayList<java.lang.Object> ensureEvents = ensureEvents(contentCaptureSession);
            android.view.autofill.AutofillId autofillId = view;
            if (!z) {
                autofillId = view.getAutofillId();
            }
            ensureEvents.add(autofillId);
        }

        private java.util.ArrayList<java.lang.Object> ensureEvents(android.view.contentcapture.ContentCaptureSession contentCaptureSession) {
            if (this.mContentCaptureEvents == null) {
                this.mContentCaptureEvents = new android.util.SparseArray<>(1);
            }
            int id = contentCaptureSession.getId();
            java.util.ArrayList<java.lang.Object> arrayList = this.mContentCaptureEvents.get(id);
            if (arrayList == null) {
                java.util.ArrayList<java.lang.Object> arrayList2 = new java.util.ArrayList<>();
                this.mContentCaptureEvents.put(id, arrayList2);
                return arrayList2;
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean canPerformHapticFeedback() {
            return this.mSession != null && (this.mDisplay.getFlags() & 1024) == 0;
        }

        com.android.internal.view.ScrollCaptureInternal getScrollCaptureInternal() {
            if (this.mScrollCaptureInternal != null) {
                this.mScrollCaptureInternal = new com.android.internal.view.ScrollCaptureInternal();
            }
            return this.mScrollCaptureInternal;
        }

        android.view.AttachedSurfaceControl getRootSurfaceControl() {
            return this.mViewRootImpl;
        }

        public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
            java.lang.String str2 = str + "  ";
            printWriter.println(str + "AttachInfo:");
            printWriter.println(str2 + "mHasWindowFocus=" + this.mHasWindowFocus);
            printWriter.println(str2 + "mWindowVisibility=" + this.mWindowVisibility);
            printWriter.println(str2 + "mInTouchMode=" + this.mInTouchMode);
            printWriter.println(str2 + "mUnbufferedDispatchRequested=" + this.mUnbufferedDispatchRequested);
        }
    }

    private static class ScrollabilityCache implements java.lang.Runnable {
        public static final int DRAGGING_HORIZONTAL_SCROLL_BAR = 2;
        public static final int DRAGGING_VERTICAL_SCROLL_BAR = 1;
        public static final int FADING = 2;
        public static final int NOT_DRAGGING = 0;
        public static final int OFF = 0;
        public static final int ON = 1;
        private static final float[] OPAQUE = {255.0f};
        private static final float[] TRANSPARENT = {0.0f};
        public boolean fadeScrollBars;
        public long fadeStartTime;
        public int fadingEdgeLength;
        public android.view.View host;
        public float[] interpolatorValues;
        private int mLastColor;
        public android.widget.ScrollBarDrawable scrollBar;
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public final android.graphics.Interpolator scrollBarInterpolator = new android.graphics.Interpolator(1, 2);
        public int state = 0;
        public final android.graphics.Rect mScrollBarBounds = new android.graphics.Rect();
        public final android.graphics.Rect mScrollBarTouchBounds = new android.graphics.Rect();
        public int mScrollBarDraggingState = 0;
        public float mScrollBarDraggingPos = 0.0f;
        public int scrollBarDefaultDelayBeforeFade = android.view.ViewConfiguration.getScrollDefaultDelay();
        public int scrollBarFadeDuration = android.view.ViewConfiguration.getScrollBarFadeDuration();
        public final android.graphics.Paint paint = new android.graphics.Paint();
        public final android.graphics.Matrix matrix = new android.graphics.Matrix();
        public android.graphics.Shader shader = new android.graphics.LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, android.graphics.Shader.TileMode.CLAMP);

        public ScrollabilityCache(android.view.ViewConfiguration viewConfiguration, android.view.View view) {
            this.fadingEdgeLength = viewConfiguration.getScaledFadingEdgeLength();
            this.scrollBarSize = viewConfiguration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = viewConfiguration.getScaledMinScrollbarTouchTarget();
            this.paint.setShader(this.shader);
            this.paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
            this.host = view;
        }

        public void setFadeColor(int i) {
            if (i != this.mLastColor) {
                this.mLastColor = i;
                if (i != 0) {
                    this.shader = new android.graphics.LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, i | (-16777216), i & 16777215, android.graphics.Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(null);
                } else {
                    this.shader = new android.graphics.LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, android.graphics.Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
                }
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
            if (currentAnimationTimeMillis >= this.fadeStartTime) {
                int i = (int) currentAnimationTimeMillis;
                android.graphics.Interpolator interpolator = this.scrollBarInterpolator;
                interpolator.setKeyFrame(0, i, OPAQUE);
                interpolator.setKeyFrame(1, i + this.scrollBarFadeDuration, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }
    }

    private class SendAccessibilityEventThrottle implements java.lang.Runnable {
        private android.view.accessibility.AccessibilityEvent mAccessibilityEvent;
        public volatile boolean mIsPending;

        private SendAccessibilityEventThrottle() {
        }

        public void post(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            updateWithAccessibilityEvent(accessibilityEvent);
            if (!this.mIsPending) {
                this.mIsPending = true;
                android.view.View.this.postDelayed(this, android.view.ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            if (android.view.accessibility.AccessibilityManager.getInstance(android.view.View.this.mContext).isEnabled() && android.view.View.this.isShown()) {
                android.view.View.this.requestParentSendAccessibilityEvent(this.mAccessibilityEvent);
            }
            reset();
        }

        public void updateWithAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            this.mAccessibilityEvent = accessibilityEvent;
        }

        public void reset() {
            this.mIsPending = false;
            this.mAccessibilityEvent = null;
        }
    }

    private class SendViewScrolledAccessibilityEvent extends android.view.View.SendAccessibilityEventThrottle {
        public int mDeltaX;
        public int mDeltaY;

        private SendViewScrolledAccessibilityEvent() {
            super();
        }

        @Override // android.view.View.SendAccessibilityEventThrottle
        public void updateWithAccessibilityEvent(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            super.updateWithAccessibilityEvent(accessibilityEvent);
            this.mDeltaX += accessibilityEvent.getScrollDeltaX();
            this.mDeltaY += accessibilityEvent.getScrollDeltaY();
            accessibilityEvent.setScrollDeltaX(this.mDeltaX);
            accessibilityEvent.setScrollDeltaY(this.mDeltaY);
        }

        @Override // android.view.View.SendAccessibilityEventThrottle
        public void reset() {
            super.reset();
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }
    }

    private void cancel(android.view.View.SendAccessibilityEventThrottle sendAccessibilityEventThrottle) {
        if (sendAccessibilityEventThrottle == null || !sendAccessibilityEventThrottle.mIsPending) {
            return;
        }
        removeCallbacks(sendAccessibilityEventThrottle);
        sendAccessibilityEventThrottle.reset();
    }

    public static class AccessibilityDelegate {
        public void sendAccessibilityEvent(android.view.View view, int i) {
            view.sendAccessibilityEventInternal(i);
        }

        public boolean performAccessibilityAction(android.view.View view, int i, android.os.Bundle bundle) {
            return view.performAccessibilityActionInternal(i, bundle);
        }

        public void sendAccessibilityEventUnchecked(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            view.sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }

        public boolean dispatchPopulateAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            return view.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public void onPopulateAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            view.onPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public void onInitializeAccessibilityEvent(android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            view.onInitializeAccessibilityEventInternal(accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
            view.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }

        public void addExtraDataToAccessibilityNodeInfo(android.view.View view, android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.lang.String str, android.os.Bundle bundle) {
            view.addExtraDataToAccessibilityNodeInfo(accessibilityNodeInfo, str, bundle);
        }

        public boolean onRequestSendAccessibilityEvent(android.view.ViewGroup viewGroup, android.view.View view, android.view.accessibility.AccessibilityEvent accessibilityEvent) {
            return viewGroup.onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
        }

        public android.view.accessibility.AccessibilityNodeProvider getAccessibilityNodeProvider(android.view.View view) {
            return null;
        }

        public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo(android.view.View view) {
            return view.createAccessibilityNodeInfoInternal();
        }
    }

    private static class MatchIdPredicate implements java.util.function.Predicate<android.view.View> {
        public int mId;

        private MatchIdPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(android.view.View view) {
            return view.mID == this.mId;
        }
    }

    private static class MatchLabelForPredicate implements java.util.function.Predicate<android.view.View> {
        private int mLabeledId;

        private MatchLabelForPredicate() {
        }

        @Override // java.util.function.Predicate
        public boolean test(android.view.View view) {
            return view.mLabelForId == this.mLabeledId;
        }
    }

    private static class SensitiveAutofillHintsHelper {
        private static final android.util.ArraySet<java.lang.String> SENSITIVE_CONTENT_AUTOFILL_HINTS = new android.util.ArraySet<>();

        private SensitiveAutofillHintsHelper() {
        }

        static {
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(android.view.View.AUTOFILL_HINT_USERNAME);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add(android.view.View.AUTOFILL_HINT_PASSWORD_AUTO);
            SENSITIVE_CONTENT_AUTOFILL_HINTS.add("password");
        }

        static boolean containsSensitiveAutofillHint(java.lang.String[] strArr) {
            if (strArr == null) {
                return false;
            }
            for (java.lang.String str : strArr) {
                if (SENSITIVE_CONTENT_AUTOFILL_HINTS.contains(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public int getScrollCaptureHint() {
        return (this.mPrivateFlags4 & 7168) >> 10;
    }

    public void setScrollCaptureHint(int i) {
        this.mPrivateFlags4 &= -7169;
        if ((i & 1) != 0) {
            i &= -3;
        }
        this.mPrivateFlags4 = ((i << 10) & 7168) | this.mPrivateFlags4;
    }

    public final void setScrollCaptureCallback(android.view.ScrollCaptureCallback scrollCaptureCallback) {
        getListenerInfo().mScrollCaptureCallback = scrollCaptureCallback;
    }

    public android.view.ScrollCaptureCallback createScrollCaptureCallbackInternal(android.graphics.Rect rect, android.graphics.Point point) {
        if (this.mAttachInfo == null) {
            return null;
        }
        if (this.mAttachInfo.mScrollCaptureInternal == null) {
            this.mAttachInfo.mScrollCaptureInternal = new com.android.internal.view.ScrollCaptureInternal();
        }
        return this.mAttachInfo.mScrollCaptureInternal.requestCallback(this, rect, point);
    }

    public void dispatchScrollCaptureSearch(android.graphics.Rect rect, android.graphics.Point point, java.util.function.Consumer<android.view.ScrollCaptureTarget> consumer) {
        onScrollCaptureSearch(rect, point, consumer);
    }

    public void onScrollCaptureSearch(android.graphics.Rect rect, android.graphics.Point point, java.util.function.Consumer<android.view.ScrollCaptureTarget> consumer) {
        boolean z = true;
        if ((getScrollCaptureHint() & 1) != 0) {
            return;
        }
        if (this.mClipBounds != null) {
            z = rect.intersect(this.mClipBounds);
        }
        if (!z) {
            return;
        }
        android.view.ScrollCaptureCallback scrollCaptureCallback = this.mListenerInfo == null ? null : this.mListenerInfo.mScrollCaptureCallback;
        if (scrollCaptureCallback == null) {
            scrollCaptureCallback = createScrollCaptureCallbackInternal(rect, point);
        }
        if (scrollCaptureCallback != null) {
            consumer.accept(new android.view.ScrollCaptureTarget(this, new android.graphics.Rect(rect), new android.graphics.Point(point.x, point.y), scrollCaptureCallback));
        }
    }

    private static void dumpFlags() {
        java.util.HashMap newHashMap = com.google.android.collect.Maps.newHashMap();
        try {
            for (java.lang.reflect.Field field : android.view.View.class.getDeclaredFields()) {
                int modifiers = field.getModifiers();
                if (java.lang.reflect.Modifier.isStatic(modifiers) && java.lang.reflect.Modifier.isFinal(modifiers)) {
                    if (field.getType().equals(java.lang.Integer.TYPE)) {
                        dumpFlag(newHashMap, field.getName(), field.getInt(null));
                    } else if (field.getType().equals(int[].class)) {
                        int[] iArr = (int[]) field.get(null);
                        for (int i = 0; i < iArr.length; i++) {
                            dumpFlag(newHashMap, field.getName() + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START + i + android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END, iArr[i]);
                        }
                    }
                }
            }
            java.util.ArrayList newArrayList = com.google.android.collect.Lists.newArrayList();
            newArrayList.addAll(newHashMap.keySet());
            java.util.Collections.sort(newArrayList);
            java.util.Iterator it = newArrayList.iterator();
            while (it.hasNext()) {
                android.util.Log.d(VIEW_LOG_TAG, (java.lang.String) newHashMap.get((java.lang.String) it.next()));
            }
        } catch (java.lang.IllegalAccessException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private static void dumpFlag(java.util.HashMap<java.lang.String, java.lang.String> hashMap, java.lang.String str, int i) {
        java.lang.String replace = java.lang.String.format("%32s", java.lang.Integer.toBinaryString(i)).replace('0', ' ');
        int indexOf = str.indexOf(95);
        hashMap.put((indexOf > 0 ? str.substring(0, indexOf) : str) + replace + str, replace + " " + str);
    }

    public void encode(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        viewHierarchyEncoder.beginObject(this);
        encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.endObject();
    }

    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        java.lang.Object resolveId = android.view.ViewDebug.resolveId(getContext(), this.mID);
        if (resolveId instanceof java.lang.String) {
            viewHierarchyEncoder.addProperty("id", (java.lang.String) resolveId);
        } else {
            viewHierarchyEncoder.addProperty("id", this.mID);
        }
        viewHierarchyEncoder.addProperty("misc:transformation.alpha", this.mTransformationInfo != null ? this.mTransformationInfo.mAlpha : 0.0f);
        viewHierarchyEncoder.addProperty("misc:transitionName", getTransitionName());
        viewHierarchyEncoder.addProperty("layout:left", this.mLeft);
        viewHierarchyEncoder.addProperty("layout:right", this.mRight);
        viewHierarchyEncoder.addProperty("layout:top", this.mTop);
        viewHierarchyEncoder.addProperty("layout:bottom", this.mBottom);
        viewHierarchyEncoder.addProperty("layout:width", getWidth());
        viewHierarchyEncoder.addProperty("layout:height", getHeight());
        viewHierarchyEncoder.addProperty("layout:layoutDirection", getLayoutDirection());
        viewHierarchyEncoder.addProperty("layout:layoutRtl", isLayoutRtl());
        viewHierarchyEncoder.addProperty("layout:hasTransientState", hasTransientState());
        viewHierarchyEncoder.addProperty("layout:baseline", getBaseline());
        android.view.ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            viewHierarchyEncoder.addPropertyKey("layoutParams");
            layoutParams.encode(viewHierarchyEncoder);
        }
        viewHierarchyEncoder.addProperty("scrolling:scrollX", this.mScrollX);
        viewHierarchyEncoder.addProperty("scrolling:scrollY", this.mScrollY);
        viewHierarchyEncoder.addProperty("padding:paddingLeft", this.mPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:paddingRight", this.mPaddingRight);
        viewHierarchyEncoder.addProperty("padding:paddingTop", this.mPaddingTop);
        viewHierarchyEncoder.addProperty("padding:paddingBottom", this.mPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingRight", this.mUserPaddingRight);
        viewHierarchyEncoder.addProperty("padding:userPaddingLeft", this.mUserPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:userPaddingBottom", this.mUserPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingStart", this.mUserPaddingStart);
        viewHierarchyEncoder.addProperty("padding:userPaddingEnd", this.mUserPaddingEnd);
        viewHierarchyEncoder.addProperty("measurement:minHeight", this.mMinHeight);
        viewHierarchyEncoder.addProperty("measurement:minWidth", this.mMinWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredWidth", this.mMeasuredWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredHeight", this.mMeasuredHeight);
        viewHierarchyEncoder.addProperty("drawing:elevation", getElevation());
        viewHierarchyEncoder.addProperty("drawing:translationX", getTranslationX());
        viewHierarchyEncoder.addProperty("drawing:translationY", getTranslationY());
        viewHierarchyEncoder.addProperty("drawing:translationZ", getTranslationZ());
        viewHierarchyEncoder.addProperty("drawing:rotation", getRotation());
        viewHierarchyEncoder.addProperty("drawing:rotationX", getRotationX());
        viewHierarchyEncoder.addProperty("drawing:rotationY", getRotationY());
        viewHierarchyEncoder.addProperty("drawing:scaleX", getScaleX());
        viewHierarchyEncoder.addProperty("drawing:scaleY", getScaleY());
        viewHierarchyEncoder.addProperty("drawing:pivotX", getPivotX());
        viewHierarchyEncoder.addProperty("drawing:pivotY", getPivotY());
        viewHierarchyEncoder.addProperty("drawing:clipBounds", this.mClipBounds == null ? null : this.mClipBounds.toString());
        viewHierarchyEncoder.addProperty("drawing:opaque", isOpaque());
        viewHierarchyEncoder.addProperty("drawing:alpha", getAlpha());
        viewHierarchyEncoder.addProperty("drawing:transitionAlpha", getTransitionAlpha());
        viewHierarchyEncoder.addProperty("drawing:shadow", hasShadow());
        viewHierarchyEncoder.addProperty("drawing:solidColor", getSolidColor());
        viewHierarchyEncoder.addProperty("drawing:layerType", this.mLayerType);
        viewHierarchyEncoder.addProperty("drawing:willNotDraw", willNotDraw());
        viewHierarchyEncoder.addProperty("drawing:hardwareAccelerated", isHardwareAccelerated());
        viewHierarchyEncoder.addProperty("drawing:willNotCacheDrawing", willNotCacheDrawing());
        viewHierarchyEncoder.addProperty("drawing:drawingCacheEnabled", isDrawingCacheEnabled());
        viewHierarchyEncoder.addProperty("drawing:overlappingRendering", hasOverlappingRendering());
        viewHierarchyEncoder.addProperty("drawing:outlineAmbientShadowColor", getOutlineAmbientShadowColor());
        viewHierarchyEncoder.addProperty("drawing:outlineSpotShadowColor", getOutlineSpotShadowColor());
        viewHierarchyEncoder.addProperty("focus:hasFocus", hasFocus());
        viewHierarchyEncoder.addProperty("focus:isFocused", isFocused());
        viewHierarchyEncoder.addProperty("focus:focusable", getFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusable", isFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusableInTouchMode", isFocusableInTouchMode());
        viewHierarchyEncoder.addProperty("misc:clickable", isClickable());
        viewHierarchyEncoder.addProperty("misc:pressed", isPressed());
        viewHierarchyEncoder.addProperty("misc:selected", isSelected());
        viewHierarchyEncoder.addProperty("misc:touchMode", isInTouchMode());
        viewHierarchyEncoder.addProperty("misc:hovered", isHovered());
        viewHierarchyEncoder.addProperty("misc:activated", isActivated());
        viewHierarchyEncoder.addProperty("misc:visibility", getVisibility());
        viewHierarchyEncoder.addProperty("misc:fitsSystemWindows", getFitsSystemWindows());
        viewHierarchyEncoder.addProperty("misc:filterTouchesWhenObscured", getFilterTouchesWhenObscured());
        viewHierarchyEncoder.addProperty("misc:enabled", isEnabled());
        viewHierarchyEncoder.addProperty("misc:soundEffectsEnabled", isSoundEffectsEnabled());
        viewHierarchyEncoder.addProperty("misc:hapticFeedbackEnabled", isHapticFeedbackEnabled());
        android.content.res.Resources.Theme theme = getContext().getTheme();
        if (theme != null) {
            viewHierarchyEncoder.addPropertyKey("theme");
            theme.encode(viewHierarchyEncoder);
        }
        int length = this.mAttributes != null ? this.mAttributes.length : 0;
        viewHierarchyEncoder.addProperty("meta:__attrCount__", length / 2);
        for (int i = 0; i < length; i += 2) {
            viewHierarchyEncoder.addProperty("meta:__attr__" + this.mAttributes[i], this.mAttributes[i + 1]);
        }
        viewHierarchyEncoder.addProperty("misc:scrollBarStyle", getScrollBarStyle());
        viewHierarchyEncoder.addProperty("text:textDirection", getTextDirection());
        viewHierarchyEncoder.addProperty("text:textAlignment", getTextAlignment());
        java.lang.CharSequence contentDescription = getContentDescription();
        viewHierarchyEncoder.addUserProperty("accessibility:contentDescription", contentDescription == null ? "" : contentDescription.toString());
        viewHierarchyEncoder.addProperty("accessibility:labelFor", getLabelFor());
        viewHierarchyEncoder.addProperty("accessibility:importantForAccessibility", getImportantForAccessibility());
    }

    boolean shouldDrawRoundScrollbar() {
        if (!this.mResources.getConfiguration().isScreenRound() || this.mAttachInfo == null) {
            return false;
        }
        android.view.View rootView = getRootView();
        getRootWindowInsets();
        return getHeight() == rootView.getHeight() && getWidth() == rootView.getWidth();
    }

    public void setTooltipText(java.lang.CharSequence charSequence) {
        if (android.text.TextUtils.isEmpty(charSequence)) {
            setFlags(0, 1073741824);
            hideTooltip();
            this.mTooltipInfo = null;
            return;
        }
        setFlags(1073741824, 1073741824);
        if (this.mTooltipInfo == null) {
            this.mTooltipInfo = new android.view.View.TooltipInfo();
            this.mTooltipInfo.mShowTooltipRunnable = new java.lang.Runnable() { // from class: android.view.View$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.View.this.showHoverTooltip();
                }
            };
            this.mTooltipInfo.mHideTooltipRunnable = new java.lang.Runnable() { // from class: android.view.View$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    android.view.View.this.hideTooltip();
                }
            };
            this.mTooltipInfo.mHoverSlop = android.view.ViewConfiguration.get(this.mContext).getScaledHoverSlop();
            this.mTooltipInfo.clearAnchorPos();
        }
        this.mTooltipInfo.mTooltipText = charSequence;
    }

    public void setTooltip(java.lang.CharSequence charSequence) {
        setTooltipText(charSequence);
    }

    public java.lang.CharSequence getTooltipText() {
        if (this.mTooltipInfo != null) {
            return this.mTooltipInfo.mTooltipText;
        }
        return null;
    }

    public java.lang.CharSequence getTooltip() {
        return getTooltipText();
    }

    private boolean showTooltip(int i, int i2, boolean z) {
        if (this.mAttachInfo == null || this.mTooltipInfo == null) {
            return false;
        }
        if ((z && (this.mViewFlags & 32) != 0) || android.text.TextUtils.isEmpty(this.mTooltipInfo.mTooltipText)) {
            return false;
        }
        hideTooltip();
        this.mTooltipInfo.mTooltipFromLongClick = z;
        this.mTooltipInfo.mTooltipPopup = new com.android.internal.view.TooltipPopup(getContext());
        this.mTooltipInfo.mTooltipPopup.show(this, i, i2, (this.mPrivateFlags3 & 131072) == 131072, this.mTooltipInfo.mTooltipText);
        this.mAttachInfo.mTooltipHost = this;
        notifyViewAccessibilityStateChangedIfNeeded(0);
        return true;
    }

    void hideTooltip() {
        if (this.mTooltipInfo == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        if (this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        this.mTooltipInfo.mTooltipPopup.hide();
        this.mTooltipInfo.mTooltipPopup = null;
        this.mTooltipInfo.mTooltipFromLongClick = false;
        this.mTooltipInfo.clearAnchorPos();
        if (this.mAttachInfo != null) {
            this.mAttachInfo.mTooltipHost = null;
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    private boolean showLongClickTooltip(int i, int i2) {
        removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return showTooltip(i, i2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean showHoverTooltip() {
        return showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean dispatchTooltipHoverEvent(android.view.MotionEvent motionEvent) {
        int hoverTooltipHideTimeout;
        if (this.mTooltipInfo == null) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 7:
                if ((this.mViewFlags & 1073741824) == 1073741824) {
                    if (!this.mTooltipInfo.mTooltipFromLongClick && this.mTooltipInfo.updateAnchorPos(motionEvent)) {
                        if (this.mTooltipInfo.mTooltipPopup == null) {
                            removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
                            postDelayed(this.mTooltipInfo.mShowTooltipRunnable, android.view.ViewConfiguration.getHoverTooltipShowTimeout());
                        }
                        if ((getWindowSystemUiVisibility() & 1) == 1) {
                            hoverTooltipHideTimeout = android.view.ViewConfiguration.getHoverTooltipHideShortTimeout();
                        } else {
                            hoverTooltipHideTimeout = android.view.ViewConfiguration.getHoverTooltipHideTimeout();
                        }
                        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
                        postDelayed(this.mTooltipInfo.mHideTooltipRunnable, hoverTooltipHideTimeout);
                    }
                    return true;
                }
                return false;
            case 10:
                this.mTooltipInfo.clearAnchorPos();
                if (!this.mTooltipInfo.mTooltipFromLongClick) {
                    hideTooltip();
                }
                return false;
            default:
                return false;
        }
    }

    void handleTooltipKey(android.view.KeyEvent keyEvent) {
        switch (keyEvent.getAction()) {
            case 0:
                if (keyEvent.getRepeatCount() == 0) {
                    hideTooltip();
                    break;
                }
                break;
            case 1:
                handleTooltipUp();
                break;
        }
    }

    private void handleTooltipUp() {
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        postDelayed(this.mTooltipInfo.mHideTooltipRunnable, android.view.ViewConfiguration.getLongPressTooltipHideTimeout());
    }

    private int getFocusableAttribute(android.content.res.TypedArray typedArray) {
        android.util.TypedValue typedValue = new android.util.TypedValue();
        if (typedArray.getValue(19, typedValue)) {
            if (typedValue.type == 18) {
                return typedValue.data == 0 ? 0 : 1;
            }
            return typedValue.data;
        }
        return 16;
    }

    public android.view.View getTooltipView() {
        if (this.mTooltipInfo == null || this.mTooltipInfo.mTooltipPopup == null) {
            return null;
        }
        return this.mTooltipInfo.mTooltipPopup.getContentView();
    }

    public static boolean isDefaultFocusHighlightEnabled() {
        return sUseDefaultFocusHighlight;
    }

    android.view.View dispatchUnhandledKeyEvent(android.view.KeyEvent keyEvent) {
        if (onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    boolean onUnhandledKeyEvent(android.view.KeyEvent keyEvent) {
        if (this.mListenerInfo != null && this.mListenerInfo.mUnhandledKeyListeners != null) {
            for (int size = this.mListenerInfo.mUnhandledKeyListeners.size() - 1; size >= 0; size--) {
                if (((android.view.View.OnUnhandledKeyEventListener) this.mListenerInfo.mUnhandledKeyListeners.get(size)).onUnhandledKeyEvent(this, keyEvent)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    boolean hasUnhandledKeyListener() {
        return (this.mListenerInfo == null || this.mListenerInfo.mUnhandledKeyListeners == null || this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) ? false : true;
    }

    public void addOnUnhandledKeyEventListener(android.view.View.OnUnhandledKeyEventListener onUnhandledKeyEventListener) {
        java.util.ArrayList arrayList = getListenerInfo().mUnhandledKeyListeners;
        if (arrayList == null) {
            arrayList = new java.util.ArrayList();
            getListenerInfo().mUnhandledKeyListeners = arrayList;
        }
        arrayList.add(onUnhandledKeyEventListener);
        if (arrayList.size() == 1 && (this.mParent instanceof android.view.ViewGroup)) {
            ((android.view.ViewGroup) this.mParent).incrementChildUnhandledKeyListeners();
        }
    }

    public void removeOnUnhandledKeyEventListener(android.view.View.OnUnhandledKeyEventListener onUnhandledKeyEventListener) {
        if (this.mListenerInfo != null && this.mListenerInfo.mUnhandledKeyListeners != null && !this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            this.mListenerInfo.mUnhandledKeyListeners.remove(onUnhandledKeyEventListener);
            if (this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
                this.mListenerInfo.mUnhandledKeyListeners = null;
                if (this.mParent instanceof android.view.ViewGroup) {
                    ((android.view.ViewGroup) this.mParent).decrementChildUnhandledKeyListeners();
                }
            }
        }
    }

    protected void setDetached(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 8192;
        } else {
            this.mPrivateFlags4 &= -8193;
        }
    }

    public void setIsCredential(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 131072;
        } else {
            this.mPrivateFlags4 &= -131073;
        }
    }

    public boolean isCredential() {
        return (this.mPrivateFlags4 & 131072) == 131072;
    }

    public void setAutoHandwritingEnabled(boolean z) {
        if (z) {
            this.mPrivateFlags4 |= 65536;
        } else {
            this.mPrivateFlags4 &= -65537;
        }
        updatePositionUpdateListener();
        postUpdate(new android.view.View$$ExternalSyntheticLambda0(this));
    }

    public boolean isAutoHandwritingEnabled() {
        return (this.mPrivateFlags4 & 65536) == 65536;
    }

    public boolean isStylusHandwritingAvailable() {
        return ((android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class)).isStylusHandwritingAvailable();
    }

    private void setTraversalTracingEnabled(boolean z) {
        if (z) {
            if (this.mTracingStrings == null) {
                this.mTracingStrings = new android.view.ViewTraversalTracingStrings(this);
            }
            this.mPrivateFlags4 |= 262144;
            return;
        }
        this.mPrivateFlags4 &= -262145;
    }

    private boolean isTraversalTracingEnabled() {
        return (this.mPrivateFlags4 & 262144) == 262144;
    }

    private void setRelayoutTracingEnabled(boolean z) {
        if (z) {
            if (this.mTracingStrings == null) {
                this.mTracingStrings = new android.view.ViewTraversalTracingStrings(this);
            }
            this.mPrivateFlags4 |= 524288;
            return;
        }
        this.mPrivateFlags4 &= -524289;
    }

    private boolean isRelayoutTracingEnabled() {
        return (this.mPrivateFlags4 & 524288) == 524288;
    }

    public void onCreateViewTranslationRequest(int[] iArr, java.util.function.Consumer<android.view.translation.ViewTranslationRequest> consumer) {
    }

    public void onCreateVirtualViewTranslationRequests(long[] jArr, int[] iArr, java.util.function.Consumer<android.view.translation.ViewTranslationRequest> consumer) {
    }

    public android.view.translation.ViewTranslationCallback getViewTranslationCallback() {
        return this.mViewTranslationCallback;
    }

    public void setViewTranslationCallback(android.view.translation.ViewTranslationCallback viewTranslationCallback) {
        this.mViewTranslationCallback = viewTranslationCallback;
    }

    public void clearViewTranslationCallback() {
        this.mViewTranslationCallback = null;
    }

    public android.view.translation.ViewTranslationResponse getViewTranslationResponse() {
        return this.mViewTranslationResponse;
    }

    public void onViewTranslationResponse(android.view.translation.ViewTranslationResponse viewTranslationResponse) {
        this.mViewTranslationResponse = viewTranslationResponse;
    }

    public void clearViewTranslationResponse() {
        this.mViewTranslationResponse = null;
    }

    public void onVirtualViewTranslationResponses(android.util.LongSparseArray<android.view.translation.ViewTranslationResponse> longSparseArray) {
    }

    public void dispatchCreateViewTranslationRequest(java.util.Map<android.view.autofill.AutofillId, long[]> map, int[] iArr, android.view.translation.TranslationCapability translationCapability, final java.util.List<android.view.translation.ViewTranslationRequest> list) {
        android.view.autofill.AutofillId autofillId = getAutofillId();
        if (map.containsKey(autofillId)) {
            if (map.get(autofillId) == null) {
                onCreateViewTranslationRequest(iArr, new android.view.View.ViewTranslationRequestConsumer(list));
            } else {
                onCreateVirtualViewTranslationRequests(map.get(autofillId), iArr, new java.util.function.Consumer() { // from class: android.view.View$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        list.add((android.view.translation.ViewTranslationRequest) obj);
                    }
                });
            }
        }
    }

    private class ViewTranslationRequestConsumer implements java.util.function.Consumer<android.view.translation.ViewTranslationRequest> {
        private boolean mCalled;
        private final java.util.List<android.view.translation.ViewTranslationRequest> mRequests;

        ViewTranslationRequestConsumer(java.util.List<android.view.translation.ViewTranslationRequest> list) {
            this.mRequests = list;
        }

        @Override // java.util.function.Consumer
        public void accept(android.view.translation.ViewTranslationRequest viewTranslationRequest) {
            if (this.mCalled) {
                throw new java.lang.IllegalStateException("The translation Consumer is not reusable.");
            }
            this.mCalled = true;
            if (viewTranslationRequest != null && viewTranslationRequest.getKeys().size() > 0) {
                this.mRequests.add(viewTranslationRequest);
                if (android.util.Log.isLoggable(android.view.View.CONTENT_CAPTURE_LOG_TAG, 2)) {
                    android.util.Log.v(android.view.View.CONTENT_CAPTURE_LOG_TAG, "Calling setHasTransientState(true) for " + android.view.View.this.getAutofillId());
                }
                android.view.View.this.setHasTransientState(true);
                android.view.View.this.setHasTranslationTransientState(true);
            }
        }
    }

    public void generateDisplayHash(java.lang.String str, android.graphics.Rect rect, final java.util.concurrent.Executor executor, final android.view.displayhash.DisplayHashResultCallback displayHashResultCallback) {
        android.view.IWindowSession windowSession = getWindowSession();
        if (windowSession == null) {
            displayHashResultCallback.onDisplayHashError(-3);
            return;
        }
        android.view.IWindow window = getWindow();
        if (window == null) {
            displayHashResultCallback.onDisplayHashError(-3);
            return;
        }
        android.graphics.Rect rect2 = new android.graphics.Rect();
        getGlobalVisibleRect(rect2);
        if (rect != null && rect.isEmpty()) {
            displayHashResultCallback.onDisplayHashError(-2);
            return;
        }
        if (rect != null) {
            rect.offset(rect2.left, rect2.top);
            rect2.intersectUnchecked(rect);
        }
        if (rect2.isEmpty()) {
            displayHashResultCallback.onDisplayHashError(-4);
            return;
        }
        try {
            windowSession.generateDisplayHash(window, rect2, str, new android.os.RemoteCallback(new android.os.RemoteCallback.OnResultListener() { // from class: android.view.View$$ExternalSyntheticLambda6
                @Override // android.os.RemoteCallback.OnResultListener
                public final void onResult(android.os.Bundle bundle) {
                    executor.execute(new java.lang.Runnable() { // from class: android.view.View$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            android.view.View.lambda$generateDisplayHash$6(android.os.Bundle.this, r2);
                        }
                    });
                }
            }));
        } catch (android.os.RemoteException e) {
            android.util.Log.e(VIEW_LOG_TAG, "Failed to call generateDisplayHash");
            displayHashResultCallback.onDisplayHashError(-1);
        }
    }

    static /* synthetic */ void lambda$generateDisplayHash$6(android.os.Bundle bundle, android.view.displayhash.DisplayHashResultCallback displayHashResultCallback) {
        android.view.displayhash.DisplayHash displayHash = (android.view.displayhash.DisplayHash) bundle.getParcelable(android.view.displayhash.DisplayHashResultCallback.EXTRA_DISPLAY_HASH, android.view.displayhash.DisplayHash.class);
        int i = bundle.getInt(android.view.displayhash.DisplayHashResultCallback.EXTRA_DISPLAY_HASH_ERROR_CODE, -1);
        if (displayHash != null) {
            displayHashResultCallback.onDisplayHashResult(displayHash);
        } else {
            displayHashResultCallback.onDisplayHashError(i);
        }
    }

    public android.view.AttachedSurfaceControl getRootSurfaceControl() {
        if (this.mAttachInfo != null) {
            return this.mAttachInfo.getRootSurfaceControl();
        }
        return null;
    }

    private float getSizePercentage() {
        float f = this.mTransformationInfo != null ? this.mTransformationInfo.mAlpha : 1.0f;
        int i = this.mViewFlags & 12;
        if (this.mResources == null || f == 0.0f || i != 0) {
            return 0.0f;
        }
        android.util.DisplayMetrics displayMetrics = this.mResources.getDisplayMetrics();
        int i2 = displayMetrics.widthPixels * displayMetrics.heightPixels;
        int width = getWidth() * getHeight();
        if (i2 == 0 || width == 0) {
            return 0.0f;
        }
        return width / i2;
    }

    protected int calculateFrameRateCategory(float f) {
        if (this.mMinusTwoFrameIntervalMillis + this.mMinusOneFrameIntervalMillis < INFREQUENT_UPDATE_INTERVAL_MILLIS) {
            return f <= FRAME_RATE_SIZE_PERCENTAGE_THRESHOLD ? 3 : 5;
        }
        if (this.mInfrequentUpdateCount == 2) {
            return 3;
        }
        return this.mLastFrameRateCategory;
    }

    private void votePreferredFrameRate() {
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        float sizePercentage = getSizePercentage();
        int calculateFrameRateCategory = calculateFrameRateCategory(sizePercentage);
        if (viewRootImpl != null && sizePercentage > 0.0f) {
            if (sToolkitMetricsForFrameRateDecisionFlagValue) {
                viewRootImpl.recordViewPercentage(sizePercentage);
            }
            if (!java.lang.Float.isNaN(this.mPreferredFrameRate)) {
                if (this.mPreferredFrameRate >= 0.0f) {
                    viewRootImpl.votePreferredFrameRate(this.mPreferredFrameRate, this.mFrameRateCompatibility);
                    return;
                }
                if (this.mPreferredFrameRate == -1.0f) {
                    calculateFrameRateCategory = 1;
                } else if (this.mPreferredFrameRate == -2.0f) {
                    calculateFrameRateCategory = 2;
                } else if (this.mPreferredFrameRate == -3.0f) {
                    calculateFrameRateCategory = 3;
                } else if (this.mPreferredFrameRate == -4.0f) {
                    calculateFrameRateCategory = 5;
                }
            }
            viewRootImpl.votePreferredFrameRateCategory(calculateFrameRateCategory);
            this.mLastFrameRateCategory = calculateFrameRateCategory;
        }
    }

    public void setFrameContentVelocity(float f) {
        if (android.view.flags.Flags.viewVelocityApi()) {
            this.mFrameContentVelocity = java.lang.Math.abs(f);
            if (sToolkitMetricsForFrameRateDecisionFlagValue) {
                android.os.Trace.setCounter("Set frame velocity", (long) this.mFrameContentVelocity);
            }
        }
    }

    public float getFrameContentVelocity() {
        if (android.view.flags.Flags.viewVelocityApi()) {
            return this.mFrameContentVelocity;
        }
        return 0.0f;
    }

    public void setRequestedFrameRate(float f) {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            this.mPreferredFrameRate = f;
        }
    }

    public float getRequestedFrameRate() {
        if (sToolkitSetFrameRateReadOnlyFlagValue) {
            return this.mPreferredFrameRate;
        }
        return 0.0f;
    }

    private void updateInfrequentCount() {
        long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
        long j = currentAnimationTimeMillis - this.mLastUpdateTimeMillis;
        this.mMinusTwoFrameIntervalMillis = this.mMinusOneFrameIntervalMillis;
        this.mMinusOneFrameIntervalMillis = j;
        this.mLastUpdateTimeMillis = currentAnimationTimeMillis;
        if (this.mMinusTwoFrameIntervalMillis < 30 || j >= 2) {
            if (j >= INFREQUENT_UPDATE_INTERVAL_MILLIS) {
                this.mInfrequentUpdateCount = this.mInfrequentUpdateCount == 2 ? this.mInfrequentUpdateCount : this.mInfrequentUpdateCount + 1;
            } else {
                this.mInfrequentUpdateCount = 0;
            }
        }
    }
}
