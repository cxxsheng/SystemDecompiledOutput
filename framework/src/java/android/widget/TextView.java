package android.widget;

@android.widget.RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class TextView extends android.view.View implements android.view.ViewTreeObserver.OnPreDrawListener {
    static final int ACCESSIBILITY_ACTION_PROCESS_TEXT_START_ID = 268435712;
    private static final int ACCESSIBILITY_ACTION_SHARE = 268435456;
    static final int ACCESSIBILITY_ACTION_SMART_START_ID = 268439552;
    private static final int ANIMATED_SCROLL_GAP = 250;
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    public static final long BORINGLAYOUT_FALLBACK_LINESPACING = 210923482;
    private static final int CHANGE_WATCHER_PRIORITY = 100;
    static final boolean DEBUG_CURSOR = false;
    static final boolean DEBUG_EXTRACT = false;
    private static final int DECIMAL = 4;
    private static final int DEFAULT_AUTO_SIZE_GRANULARITY_IN_PX = 1;
    private static final int DEFAULT_AUTO_SIZE_MAX_TEXT_SIZE_IN_SP = 112;
    private static final int DEFAULT_AUTO_SIZE_MIN_TEXT_SIZE_IN_SP = 12;
    private static final int DEFAULT_LINE_BREAK_STYLE = 0;
    private static final int DEFAULT_LINE_BREAK_WORD_STYLE = 0;
    private static final int DEFAULT_TYPEFACE = -1;
    private static final int DEVICE_PROVISIONED_NO = 1;
    private static final int DEVICE_PROVISIONED_UNKNOWN = 0;
    private static final int DEVICE_PROVISIONED_YES = 2;
    private static final int ELLIPSIZE_END = 3;
    private static final int ELLIPSIZE_MARQUEE = 4;
    private static final int ELLIPSIZE_MIDDLE = 2;
    private static final int ELLIPSIZE_NONE = 0;
    private static final int ELLIPSIZE_NOT_SET = -1;
    private static final int ELLIPSIZE_START = 1;
    private static final int EMS = 1;
    private static final int FALLBACK_LINE_SPACING_ALL = 2;
    private static final int FALLBACK_LINE_SPACING_NONE = 0;
    private static final int FALLBACK_LINE_SPACING_STATIC_LAYOUT_ONLY = 1;
    private static final int FLOATING_TOOLBAR_SELECT_ALL_REFRESH_DELAY = 500;
    public static final int FOCUSED_SEARCH_RESULT_INDEX_NONE = -1;
    static final int ID_ASSIST = 16908353;
    static final int ID_AUTOFILL = 16908355;
    static final int ID_COPY = 16908321;
    static final int ID_CUT = 16908320;
    static final int ID_PASTE = 16908322;
    static final int ID_PASTE_AS_PLAIN_TEXT = 16908337;
    static final int ID_REDO = 16908339;
    static final int ID_REPLACE = 16908340;
    static final int ID_SELECT_ALL = 16908319;
    static final int ID_SHARE = 16908341;
    static final int ID_UNDO = 16908338;
    private static final int KEY_DOWN_HANDLED_BY_KEY_LISTENER = 1;
    private static final int KEY_DOWN_HANDLED_BY_MOVEMENT_METHOD = 2;
    private static final int KEY_EVENT_HANDLED = -1;
    private static final int KEY_EVENT_NOT_HANDLED = 0;
    private static final int LINES = 1;
    static final java.lang.String LOG_TAG = "TextView";
    private static final int MARQUEE_FADE_NORMAL = 0;
    private static final int MARQUEE_FADE_SWITCH_SHOW_ELLIPSIS = 1;
    private static final int MARQUEE_FADE_SWITCH_SHOW_FADE = 2;
    private static final int MAX_LENGTH_FOR_SINGLE_LINE_EDIT_TEXT = 5000;
    private static final int MONOSPACE = 3;
    private static final int NO_POINTER_ID = -1;
    private static final int OFFSET_MAPPING_SPAN_PRIORITY = 200;
    private static final int PIXELS = 2;
    public static final int PROCESS_TEXT_REQUEST_CODE = 100;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int SIGNED = 2;
    public static final long STATICLAYOUT_FALLBACK_LINESPACING = 37756858;
    public static final android.text.BoringLayout.Metrics UNKNOWN_BORING;
    private static final float UNSET_AUTO_SIZE_UNIFORM_CONFIGURATION_VALUE = -1.0f;
    public static final long USE_BOUNDS_FOR_WIDTH = 63938206;
    static final int VERY_WIDE = 1048576;
    static long sLastCutCopyOrTextChangedTime;
    private boolean mAllowTransformationLengthChange;
    private int mAutoLinkMask;
    private float mAutoSizeMaxTextSizeInPx;
    private float mAutoSizeMinTextSizeInPx;
    private float mAutoSizeStepGranularityInPx;
    private int[] mAutoSizeTextSizesInPx;
    private int mAutoSizeTextType;
    private android.text.BoringLayout.Metrics mBoring;
    private int mBreakStrategy;
    private android.widget.TextView.BufferType mBufferType;
    private android.widget.TextView.ChangeWatcher mChangeWatcher;
    private android.widget.TextView.CharWrapper mCharWrapper;
    private int mCurHintTextColor;

    @android.view.ViewDebug.ExportedProperty(category = "text")
    private int mCurTextColor;
    private volatile java.util.Locale mCurrentSpellCheckerLocaleCache;
    private android.graphics.drawable.Drawable mCursorDrawable;
    int mCursorDrawableRes;
    private boolean mCursorVisibleFromAttr;
    private int mDeferScroll;
    private int mDesiredHeightAtMeasure;
    private int mDeviceProvisionedState;
    android.widget.TextView.Drawables mDrawables;
    private android.text.Editable.Factory mEditableFactory;
    private android.widget.Editor mEditor;
    private android.text.TextUtils.TruncateAt mEllipsize;
    private android.text.InputFilter[] mFilters;
    private int mFocusedSearchResultHighlightColor;
    private android.graphics.Paint mFocusedSearchResultHighlightPaint;
    private int mFocusedSearchResultIndex;
    private int mFontWeightAdjustment;
    private boolean mFreezesText;
    private int mGesturePreviewHighlightEnd;
    private android.graphics.Paint mGesturePreviewHighlightPaint;
    private int mGesturePreviewHighlightStart;

    @android.view.ViewDebug.ExportedProperty(category = "text")
    private int mGravity;
    private boolean mHasPresetAutoSizeValues;
    private boolean mHideHint;
    int mHighlightColor;
    private final android.graphics.Paint mHighlightPaint;
    private java.util.List<android.graphics.Paint> mHighlightPaints;
    private android.graphics.Path mHighlightPath;
    private boolean mHighlightPathBogus;
    private java.util.List<android.graphics.Path> mHighlightPaths;
    private boolean mHighlightPathsBogus;
    private android.text.Highlights mHighlights;
    private java.lang.CharSequence mHint;
    private android.text.BoringLayout.Metrics mHintBoring;
    private int mHintId;
    private android.text.Layout mHintLayout;
    private android.content.res.ColorStateList mHintTextColor;
    private boolean mHorizontallyScrolling;
    private int mHyphenationFrequency;
    private boolean mImeIsConsumingInput;
    private boolean mIncludePad;
    private boolean mIsPrimePointerFromHandleView;
    private int mJustificationMode;
    private int mLastInputSource;
    private int mLastLayoutDirection;
    private int mLastOrientation;
    private long mLastScroll;
    private android.text.Layout mLayout;
    private int mLineBreakStyle;
    private int mLineBreakWordStyle;
    private int mLineHeightComplexDimen;
    private android.content.res.ColorStateList mLinkTextColor;
    private boolean mLinksClickable;
    private boolean mListenerChanged;
    private java.util.ArrayList<android.text.TextWatcher> mListeners;
    private android.graphics.Paint.FontMetrics mLocalePreferredFontMetrics;
    private boolean mLocalesChanged;
    private android.widget.TextView.Marquee mMarquee;
    private int mMarqueeFadeMode;
    private int mMarqueeRepeatLimit;
    private int mMaxMode;
    private int mMaxWidth;
    private int mMaxWidthMode;
    private int mMaximum;
    private int mMinMode;
    private int mMinWidth;
    private int mMinWidthMode;
    private int mMinimum;
    private android.graphics.Paint.FontMetrics mMinimumFontMetrics;
    private android.text.method.MovementMethod mMovement;
    private boolean mNeedsAutoSizeText;
    private int mOldMaxMode;
    private int mOldMaximum;
    private android.graphics.Typeface mOriginalTypeface;
    private final java.util.List<android.graphics.Path> mPathRecyclePool;
    private boolean mPreDrawListenerDetached;
    private boolean mPreDrawRegistered;
    private android.text.PrecomputedText mPrecomputed;
    private boolean mPreventDefaultMovement;
    private int mPrimePointerId;
    private boolean mRestartMarquee;
    private android.text.BoringLayout mSavedHintLayout;
    private android.text.BoringLayout mSavedLayout;
    private android.text.Layout mSavedMarqueeModeLayout;
    private android.widget.Scroller mScroller;
    private int mSearchResultHighlightColor;
    private android.graphics.Paint mSearchResultHighlightPaint;
    private int[] mSearchResultHighlights;
    private int mShadowColor;
    private float mShadowDx;
    private float mShadowDy;
    private float mShadowRadius;
    private boolean mShiftDrawingOffsetForStartOverhang;
    private boolean mSingleLine;
    private android.text.InputFilter.LengthFilter mSingleLineLengthFilter;
    private float mSpacingAdd;
    private float mSpacingMult;
    private android.text.Spannable mSpannable;
    private android.text.Spannable.Factory mSpannableFactory;
    private java.lang.Object mTempCursor;
    private android.graphics.Rect mTempRect;
    private android.text.TextPaint mTempTextPaint;

    @android.view.ViewDebug.ExportedProperty(category = "text")
    private java.lang.CharSequence mText;
    private android.view.textclassifier.TextClassificationContext mTextClassificationContext;
    private android.view.textclassifier.TextClassifier mTextClassificationSession;
    private android.view.textclassifier.TextClassifier mTextClassifier;
    private android.content.res.ColorStateList mTextColor;
    private android.text.TextDirectionHeuristic mTextDir;
    int mTextEditSuggestionContainerLayout;
    int mTextEditSuggestionHighlightStyle;
    int mTextEditSuggestionItemLayout;
    private int mTextId;
    private android.os.UserHandle mTextOperationUser;
    private final android.text.TextPaint mTextPaint;
    private android.graphics.drawable.Drawable mTextSelectHandle;
    private android.graphics.drawable.Drawable mTextSelectHandleLeft;
    int mTextSelectHandleLeftRes;
    int mTextSelectHandleRes;
    private android.graphics.drawable.Drawable mTextSelectHandleRight;
    int mTextSelectHandleRightRes;
    private boolean mTextSetFromXmlOrResourceId;
    private int mTextSizeUnit;
    private android.text.method.TransformationMethod mTransformation;
    private java.lang.CharSequence mTransformed;
    private boolean mUseBoundsForWidth;
    private int mUseFallbackLineSpacing;
    private final boolean mUseInternationalizedInput;
    private boolean mUseLocalePreferredLineHeightForMinimum;
    private final boolean mUseTextPaddingForUiTranslation;
    private boolean mUserSetTextScaleX;
    private java.util.regex.Pattern mWhitespacePattern;
    private static final float[] TEMP_POSITION = new float[2];
    private static final android.graphics.RectF TEMP_RECTF = new android.graphics.RectF();
    private static final android.text.InputFilter[] NO_FILTERS = new android.text.InputFilter[0];
    private static final android.text.Spanned EMPTY_SPANNED = new android.text.SpannedString("");
    private static final int[] MULTILINE_STATE_SET = {16843597};
    private static final android.util.SparseIntArray sAppearanceValues = new android.util.SparseIntArray();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface AutoSizeTextType {
    }

    public enum BufferType {
        NORMAL,
        SPANNABLE,
        EDITABLE
    }

    public interface OnEditorActionListener {
        boolean onEditorAction(android.widget.TextView textView, int i, android.view.KeyEvent keyEvent);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface XMLTypefaceAttr {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<android.widget.TextView> {
        private int mAutoLinkId;
        private int mAutoSizeMaxTextSizeId;
        private int mAutoSizeMinTextSizeId;
        private int mAutoSizeStepGranularityId;
        private int mAutoSizeTextTypeId;
        private int mBreakStrategyId;
        private int mCursorVisibleId;
        private int mDrawableBlendModeId;
        private int mDrawablePaddingId;
        private int mDrawableTintId;
        private int mDrawableTintModeId;
        private int mElegantTextHeightId;
        private int mEllipsizeId;
        private int mFallbackLineSpacingId;
        private int mFirstBaselineToTopHeightId;
        private int mFontFeatureSettingsId;
        private int mFreezesTextId;
        private int mGravityId;
        private int mHintId;
        private int mHyphenationFrequencyId;
        private int mImeActionIdId;
        private int mImeActionLabelId;
        private int mImeOptionsId;
        private int mIncludeFontPaddingId;
        private int mInputTypeId;
        private int mJustificationModeId;
        private int mLastBaselineToBottomHeightId;
        private int mLetterSpacingId;
        private int mLineHeightId;
        private int mLineSpacingExtraId;
        private int mLineSpacingMultiplierId;
        private int mLinksClickableId;
        private int mMarqueeRepeatLimitId;
        private int mMaxEmsId;
        private int mMaxHeightId;
        private int mMaxLinesId;
        private int mMaxWidthId;
        private int mMinEmsId;
        private int mMinLinesId;
        private int mMinWidthId;
        private int mPrivateImeOptionsId;
        private boolean mPropertiesMapped = false;
        private int mScrollHorizontallyId;
        private int mShadowColorId;
        private int mShadowDxId;
        private int mShadowDyId;
        private int mShadowRadiusId;
        private int mSingleLineId;
        private int mTextAllCapsId;
        private int mTextColorHighlightId;
        private int mTextColorHintId;
        private int mTextColorId;
        private int mTextColorLinkId;
        private int mTextId;
        private int mTextIsSelectableId;
        private int mTextScaleXId;
        private int mTextSizeId;
        private int mTypefaceId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(android.view.inspector.PropertyMapper propertyMapper) {
            android.view.inspector.IntFlagMapping intFlagMapping = new android.view.inspector.IntFlagMapping();
            intFlagMapping.add(2, 2, "email");
            intFlagMapping.add(8, 8, "map");
            intFlagMapping.add(4, 4, "phone");
            intFlagMapping.add(1, 1, "web");
            java.util.Objects.requireNonNull(intFlagMapping);
            this.mAutoLinkId = propertyMapper.mapIntFlag("autoLink", 16842928, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping));
            this.mAutoSizeMaxTextSizeId = propertyMapper.mapInt("autoSizeMaxTextSize", 16844102);
            this.mAutoSizeMinTextSizeId = propertyMapper.mapInt("autoSizeMinTextSize", 16844088);
            this.mAutoSizeStepGranularityId = propertyMapper.mapInt("autoSizeStepGranularity", 16844086);
            android.util.SparseArray sparseArray = new android.util.SparseArray();
            sparseArray.put(0, "none");
            sparseArray.put(1, "uniform");
            java.util.Objects.requireNonNull(sparseArray);
            this.mAutoSizeTextTypeId = propertyMapper.mapIntEnum("autoSizeTextType", 16844085, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray));
            android.util.SparseArray sparseArray2 = new android.util.SparseArray();
            sparseArray2.put(0, "simple");
            sparseArray2.put(1, "high_quality");
            sparseArray2.put(2, android.speech.RecognizerIntent.LANGUAGE_SWITCH_BALANCED);
            java.util.Objects.requireNonNull(sparseArray2);
            this.mBreakStrategyId = propertyMapper.mapIntEnum("breakStrategy", 16843997, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray2));
            this.mCursorVisibleId = propertyMapper.mapBoolean("cursorVisible", 16843090);
            this.mDrawableBlendModeId = propertyMapper.mapObject("drawableBlendMode", 80);
            this.mDrawablePaddingId = propertyMapper.mapInt("drawablePadding", 16843121);
            this.mDrawableTintId = propertyMapper.mapObject("drawableTint", 16843990);
            this.mDrawableTintModeId = propertyMapper.mapObject("drawableTintMode", 16843991);
            this.mElegantTextHeightId = propertyMapper.mapBoolean("elegantTextHeight", 16843869);
            this.mEllipsizeId = propertyMapper.mapObject("ellipsize", 16842923);
            this.mFallbackLineSpacingId = propertyMapper.mapBoolean("fallbackLineSpacing", 16844155);
            this.mFirstBaselineToTopHeightId = propertyMapper.mapInt("firstBaselineToTopHeight", 16844157);
            this.mFontFeatureSettingsId = propertyMapper.mapObject("fontFeatureSettings", 16843959);
            this.mFreezesTextId = propertyMapper.mapBoolean("freezesText", 16843116);
            this.mGravityId = propertyMapper.mapGravity("gravity", 16842927);
            this.mHintId = propertyMapper.mapObject("hint", 16843088);
            android.util.SparseArray sparseArray3 = new android.util.SparseArray();
            sparseArray3.put(0, "none");
            sparseArray3.put(1, android.graphics.FontListParser.STYLE_NORMAL);
            sparseArray3.put(2, "full");
            java.util.Objects.requireNonNull(sparseArray3);
            this.mHyphenationFrequencyId = propertyMapper.mapIntEnum("hyphenationFrequency", 16843998, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray3));
            this.mImeActionIdId = propertyMapper.mapInt("imeActionId", 16843366);
            this.mImeActionLabelId = propertyMapper.mapObject("imeActionLabel", 16843365);
            android.view.inspector.IntFlagMapping intFlagMapping2 = new android.view.inspector.IntFlagMapping();
            intFlagMapping2.add(255, 6, "actionDone");
            intFlagMapping2.add(255, 2, "actionGo");
            intFlagMapping2.add(255, 5, "actionNext");
            intFlagMapping2.add(255, 1, "actionNone");
            intFlagMapping2.add(255, 7, "actionPrevious");
            intFlagMapping2.add(255, 3, "actionSearch");
            intFlagMapping2.add(255, 4, "actionSend");
            intFlagMapping2.add(255, 0, "actionUnspecified");
            intFlagMapping2.add(Integer.MIN_VALUE, Integer.MIN_VALUE, "flagForceAscii");
            intFlagMapping2.add(134217728, 134217728, "flagNavigateNext");
            intFlagMapping2.add(67108864, 67108864, "flagNavigatePrevious");
            intFlagMapping2.add(536870912, 536870912, "flagNoAccessoryAction");
            intFlagMapping2.add(1073741824, 1073741824, "flagNoEnterAction");
            intFlagMapping2.add(268435456, 268435456, "flagNoExtractUi");
            intFlagMapping2.add(33554432, 33554432, "flagNoFullscreen");
            intFlagMapping2.add(16777216, 16777216, "flagNoPersonalizedLearning");
            intFlagMapping2.add(-1, 0, android.graphics.FontListParser.STYLE_NORMAL);
            java.util.Objects.requireNonNull(intFlagMapping2);
            this.mImeOptionsId = propertyMapper.mapIntFlag("imeOptions", 16843364, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping2));
            this.mIncludeFontPaddingId = propertyMapper.mapBoolean("includeFontPadding", 16843103);
            android.view.inspector.IntFlagMapping intFlagMapping3 = new android.view.inspector.IntFlagMapping();
            intFlagMapping3.add(4095, 20, "date");
            intFlagMapping3.add(4095, 4, android.view.textclassifier.TextClassifier.TYPE_DATE_TIME);
            intFlagMapping3.add(-1, 0, "none");
            intFlagMapping3.add(4095, 2, "number");
            intFlagMapping3.add(16773135, 8194, "numberDecimal");
            intFlagMapping3.add(4095, 18, "numberPassword");
            intFlagMapping3.add(16773135, 4098, "numberSigned");
            intFlagMapping3.add(4095, 3, "phone");
            intFlagMapping3.add(4095, 1, "text");
            intFlagMapping3.add(16773135, 65537, "textAutoComplete");
            intFlagMapping3.add(16773135, 32769, "textAutoCorrect");
            intFlagMapping3.add(16773135, 4097, "textCapCharacters");
            intFlagMapping3.add(16773135, 16385, "textCapSentences");
            intFlagMapping3.add(16773135, 8193, "textCapWords");
            intFlagMapping3.add(4095, 33, "textEmailAddress");
            intFlagMapping3.add(4095, 49, "textEmailSubject");
            intFlagMapping3.add(4095, 177, "textFilter");
            intFlagMapping3.add(16773135, 262145, "textImeMultiLine");
            intFlagMapping3.add(4095, 81, "textLongMessage");
            intFlagMapping3.add(16773135, 131073, "textMultiLine");
            intFlagMapping3.add(16773135, 524289, "textNoSuggestions");
            intFlagMapping3.add(4095, 129, "textPassword");
            intFlagMapping3.add(4095, 97, "textPersonName");
            intFlagMapping3.add(4095, 193, "textPhonetic");
            intFlagMapping3.add(4095, 113, "textPostalAddress");
            intFlagMapping3.add(4095, 65, "textShortMessage");
            intFlagMapping3.add(4095, 17, "textUri");
            intFlagMapping3.add(4095, 145, "textVisiblePassword");
            intFlagMapping3.add(4095, 161, "textWebEditText");
            intFlagMapping3.add(4095, 209, "textWebEmailAddress");
            intFlagMapping3.add(4095, 225, "textWebPassword");
            intFlagMapping3.add(4095, 36, android.os.DropBoxManager.EXTRA_TIME);
            java.util.Objects.requireNonNull(intFlagMapping3);
            this.mInputTypeId = propertyMapper.mapIntFlag("inputType", 16843296, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda1(intFlagMapping3));
            android.util.SparseArray sparseArray4 = new android.util.SparseArray();
            sparseArray4.put(0, "none");
            sparseArray4.put(1, "inter_word");
            java.util.Objects.requireNonNull(sparseArray4);
            this.mJustificationModeId = propertyMapper.mapIntEnum("justificationMode", 16844135, new android.view.View$InspectionCompanion$$ExternalSyntheticLambda0(sparseArray4));
            this.mLastBaselineToBottomHeightId = propertyMapper.mapInt("lastBaselineToBottomHeight", 16844158);
            this.mLetterSpacingId = propertyMapper.mapFloat("letterSpacing", 16843958);
            this.mLineHeightId = propertyMapper.mapInt("lineHeight", 16844159);
            this.mLineSpacingExtraId = propertyMapper.mapFloat("lineSpacingExtra", 16843287);
            this.mLineSpacingMultiplierId = propertyMapper.mapFloat("lineSpacingMultiplier", 16843288);
            this.mLinksClickableId = propertyMapper.mapBoolean("linksClickable", 16842929);
            this.mMarqueeRepeatLimitId = propertyMapper.mapInt("marqueeRepeatLimit", 16843293);
            this.mMaxEmsId = propertyMapper.mapInt("maxEms", 16843095);
            this.mMaxHeightId = propertyMapper.mapInt("maxHeight", 16843040);
            this.mMaxLinesId = propertyMapper.mapInt("maxLines", 16843091);
            this.mMaxWidthId = propertyMapper.mapInt("maxWidth", 16843039);
            this.mMinEmsId = propertyMapper.mapInt("minEms", 16843098);
            this.mMinLinesId = propertyMapper.mapInt("minLines", 16843094);
            this.mMinWidthId = propertyMapper.mapInt("minWidth", 16843071);
            this.mPrivateImeOptionsId = propertyMapper.mapObject("privateImeOptions", 16843299);
            this.mScrollHorizontallyId = propertyMapper.mapBoolean("scrollHorizontally", 16843099);
            this.mShadowColorId = propertyMapper.mapColor("shadowColor", 16843105);
            this.mShadowDxId = propertyMapper.mapFloat("shadowDx", 16843106);
            this.mShadowDyId = propertyMapper.mapFloat("shadowDy", 16843107);
            this.mShadowRadiusId = propertyMapper.mapFloat("shadowRadius", 16843108);
            this.mSingleLineId = propertyMapper.mapBoolean("singleLine", 16843101);
            this.mTextId = propertyMapper.mapObject("text", 16843087);
            this.mTextAllCapsId = propertyMapper.mapBoolean("textAllCaps", 16843660);
            this.mTextColorId = propertyMapper.mapObject("textColor", 16842904);
            this.mTextColorHighlightId = propertyMapper.mapColor("textColorHighlight", 16842905);
            this.mTextColorHintId = propertyMapper.mapObject("textColorHint", 16842906);
            this.mTextColorLinkId = propertyMapper.mapObject("textColorLink", 16842907);
            this.mTextIsSelectableId = propertyMapper.mapBoolean("textIsSelectable", 16843542);
            this.mTextScaleXId = propertyMapper.mapFloat("textScaleX", 16843089);
            this.mTextSizeId = propertyMapper.mapFloat("textSize", 16842901);
            this.mTypefaceId = propertyMapper.mapObject("typeface", 16842902);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(android.widget.TextView textView, android.view.inspector.PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new android.view.inspector.InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readIntFlag(this.mAutoLinkId, textView.getAutoLinkMask());
            propertyReader.readInt(this.mAutoSizeMaxTextSizeId, textView.getAutoSizeMaxTextSize());
            propertyReader.readInt(this.mAutoSizeMinTextSizeId, textView.getAutoSizeMinTextSize());
            propertyReader.readInt(this.mAutoSizeStepGranularityId, textView.getAutoSizeStepGranularity());
            propertyReader.readIntEnum(this.mAutoSizeTextTypeId, textView.getAutoSizeTextType());
            propertyReader.readIntEnum(this.mBreakStrategyId, textView.getBreakStrategy());
            propertyReader.readBoolean(this.mCursorVisibleId, textView.isCursorVisible());
            propertyReader.readObject(this.mDrawableBlendModeId, textView.getCompoundDrawableTintBlendMode());
            propertyReader.readInt(this.mDrawablePaddingId, textView.getCompoundDrawablePadding());
            propertyReader.readObject(this.mDrawableTintId, textView.getCompoundDrawableTintList());
            propertyReader.readObject(this.mDrawableTintModeId, textView.getCompoundDrawableTintMode());
            propertyReader.readBoolean(this.mElegantTextHeightId, textView.isElegantTextHeight());
            propertyReader.readObject(this.mEllipsizeId, textView.getEllipsize());
            propertyReader.readBoolean(this.mFallbackLineSpacingId, textView.isFallbackLineSpacing());
            propertyReader.readInt(this.mFirstBaselineToTopHeightId, textView.getFirstBaselineToTopHeight());
            propertyReader.readObject(this.mFontFeatureSettingsId, textView.getFontFeatureSettings());
            propertyReader.readBoolean(this.mFreezesTextId, textView.getFreezesText());
            propertyReader.readGravity(this.mGravityId, textView.getGravity());
            propertyReader.readObject(this.mHintId, textView.getHint());
            propertyReader.readIntEnum(this.mHyphenationFrequencyId, textView.getHyphenationFrequency());
            propertyReader.readInt(this.mImeActionIdId, textView.getImeActionId());
            propertyReader.readObject(this.mImeActionLabelId, textView.getImeActionLabel());
            propertyReader.readIntFlag(this.mImeOptionsId, textView.getImeOptions());
            propertyReader.readBoolean(this.mIncludeFontPaddingId, textView.getIncludeFontPadding());
            propertyReader.readIntFlag(this.mInputTypeId, textView.getInputType());
            propertyReader.readIntEnum(this.mJustificationModeId, textView.getJustificationMode());
            propertyReader.readInt(this.mLastBaselineToBottomHeightId, textView.getLastBaselineToBottomHeight());
            propertyReader.readFloat(this.mLetterSpacingId, textView.getLetterSpacing());
            propertyReader.readInt(this.mLineHeightId, textView.getLineHeight());
            propertyReader.readFloat(this.mLineSpacingExtraId, textView.getLineSpacingExtra());
            propertyReader.readFloat(this.mLineSpacingMultiplierId, textView.getLineSpacingMultiplier());
            propertyReader.readBoolean(this.mLinksClickableId, textView.getLinksClickable());
            propertyReader.readInt(this.mMarqueeRepeatLimitId, textView.getMarqueeRepeatLimit());
            propertyReader.readInt(this.mMaxEmsId, textView.getMaxEms());
            propertyReader.readInt(this.mMaxHeightId, textView.getMaxHeight());
            propertyReader.readInt(this.mMaxLinesId, textView.getMaxLines());
            propertyReader.readInt(this.mMaxWidthId, textView.getMaxWidth());
            propertyReader.readInt(this.mMinEmsId, textView.getMinEms());
            propertyReader.readInt(this.mMinLinesId, textView.getMinLines());
            propertyReader.readInt(this.mMinWidthId, textView.getMinWidth());
            propertyReader.readObject(this.mPrivateImeOptionsId, textView.getPrivateImeOptions());
            propertyReader.readBoolean(this.mScrollHorizontallyId, textView.isHorizontallyScrollable());
            propertyReader.readColor(this.mShadowColorId, textView.getShadowColor());
            propertyReader.readFloat(this.mShadowDxId, textView.getShadowDx());
            propertyReader.readFloat(this.mShadowDyId, textView.getShadowDy());
            propertyReader.readFloat(this.mShadowRadiusId, textView.getShadowRadius());
            propertyReader.readBoolean(this.mSingleLineId, textView.isSingleLine());
            propertyReader.readObject(this.mTextId, textView.getText());
            propertyReader.readBoolean(this.mTextAllCapsId, textView.isAllCaps());
            propertyReader.readObject(this.mTextColorId, textView.getTextColors());
            propertyReader.readColor(this.mTextColorHighlightId, textView.getHighlightColor());
            propertyReader.readObject(this.mTextColorHintId, textView.getHintTextColors());
            propertyReader.readObject(this.mTextColorLinkId, textView.getLinkTextColors());
            propertyReader.readBoolean(this.mTextIsSelectableId, textView.isTextSelectable());
            propertyReader.readFloat(this.mTextScaleXId, textView.getTextScaleX());
            propertyReader.readFloat(this.mTextSizeId, textView.getTextSize());
            propertyReader.readObject(this.mTypefaceId, textView.getTypeface());
        }
    }

    static {
        sAppearanceValues.put(6, 4);
        sAppearanceValues.put(99, 22);
        sAppearanceValues.put(100, 23);
        sAppearanceValues.put(5, 3);
        sAppearanceValues.put(7, 5);
        sAppearanceValues.put(8, 6);
        sAppearanceValues.put(2, 0);
        sAppearanceValues.put(96, 19);
        sAppearanceValues.put(3, 1);
        sAppearanceValues.put(75, 12);
        sAppearanceValues.put(4, 2);
        sAppearanceValues.put(95, 18);
        sAppearanceValues.put(72, 11);
        sAppearanceValues.put(36, 7);
        sAppearanceValues.put(37, 8);
        sAppearanceValues.put(38, 9);
        sAppearanceValues.put(39, 10);
        sAppearanceValues.put(76, 13);
        sAppearanceValues.put(91, 17);
        sAppearanceValues.put(77, 14);
        sAppearanceValues.put(78, 15);
        sAppearanceValues.put(90, 16);
        sAppearanceValues.put(97, 20);
        sAppearanceValues.put(98, 21);
        UNKNOWN_BORING = new android.text.BoringLayout.Metrics();
    }

    static class Drawables {
        static final int BOTTOM = 3;
        static final int DRAWABLE_LEFT = 1;
        static final int DRAWABLE_NONE = -1;
        static final int DRAWABLE_RIGHT = 0;
        static final int LEFT = 0;
        static final int RIGHT = 2;
        static final int TOP = 1;
        android.graphics.BlendMode mBlendMode;
        android.graphics.drawable.Drawable mDrawableEnd;
        android.graphics.drawable.Drawable mDrawableError;
        int mDrawableHeightEnd;
        int mDrawableHeightError;
        int mDrawableHeightLeft;
        int mDrawableHeightRight;
        int mDrawableHeightStart;
        int mDrawableHeightTemp;
        android.graphics.drawable.Drawable mDrawableLeftInitial;
        int mDrawablePadding;
        android.graphics.drawable.Drawable mDrawableRightInitial;
        int mDrawableSizeBottom;
        int mDrawableSizeEnd;
        int mDrawableSizeError;
        int mDrawableSizeLeft;
        int mDrawableSizeRight;
        int mDrawableSizeStart;
        int mDrawableSizeTemp;
        int mDrawableSizeTop;
        android.graphics.drawable.Drawable mDrawableStart;
        android.graphics.drawable.Drawable mDrawableTemp;
        int mDrawableWidthBottom;
        int mDrawableWidthTop;
        boolean mHasTint;
        boolean mHasTintMode;
        boolean mIsRtlCompatibilityMode;
        boolean mOverride;
        android.content.res.ColorStateList mTintList;
        final android.graphics.Rect mCompoundRect = new android.graphics.Rect();
        final android.graphics.drawable.Drawable[] mShowing = new android.graphics.drawable.Drawable[4];
        int mDrawableSaved = -1;

        public Drawables(android.content.Context context) {
            this.mIsRtlCompatibilityMode = context.getApplicationInfo().targetSdkVersion < 17 || !context.getApplicationInfo().hasRtlSupport();
            this.mOverride = false;
        }

        public boolean hasMetadata() {
            return this.mDrawablePadding != 0 || this.mHasTintMode || this.mHasTint;
        }

        public boolean resolveWithLayoutDirection(int i) {
            android.graphics.drawable.Drawable drawable = this.mShowing[0];
            android.graphics.drawable.Drawable drawable2 = this.mShowing[2];
            this.mShowing[0] = this.mDrawableLeftInitial;
            this.mShowing[2] = this.mDrawableRightInitial;
            if (this.mIsRtlCompatibilityMode) {
                if (this.mDrawableStart != null && this.mShowing[0] == null) {
                    this.mShowing[0] = this.mDrawableStart;
                    this.mDrawableSizeLeft = this.mDrawableSizeStart;
                    this.mDrawableHeightLeft = this.mDrawableHeightStart;
                }
                if (this.mDrawableEnd != null && this.mShowing[2] == null) {
                    this.mShowing[2] = this.mDrawableEnd;
                    this.mDrawableSizeRight = this.mDrawableSizeEnd;
                    this.mDrawableHeightRight = this.mDrawableHeightEnd;
                }
            } else {
                switch (i) {
                    case 1:
                        if (this.mOverride) {
                            this.mShowing[2] = this.mDrawableStart;
                            this.mDrawableSizeRight = this.mDrawableSizeStart;
                            this.mDrawableHeightRight = this.mDrawableHeightStart;
                            this.mShowing[0] = this.mDrawableEnd;
                            this.mDrawableSizeLeft = this.mDrawableSizeEnd;
                            this.mDrawableHeightLeft = this.mDrawableHeightEnd;
                            break;
                        }
                        break;
                    default:
                        if (this.mOverride) {
                            this.mShowing[0] = this.mDrawableStart;
                            this.mDrawableSizeLeft = this.mDrawableSizeStart;
                            this.mDrawableHeightLeft = this.mDrawableHeightStart;
                            this.mShowing[2] = this.mDrawableEnd;
                            this.mDrawableSizeRight = this.mDrawableSizeEnd;
                            this.mDrawableHeightRight = this.mDrawableHeightEnd;
                            break;
                        }
                        break;
                }
            }
            applyErrorDrawableIfNeeded(i);
            return (this.mShowing[0] == drawable && this.mShowing[2] == drawable2) ? false : true;
        }

        public void setErrorDrawable(android.graphics.drawable.Drawable drawable, android.widget.TextView textView) {
            if (this.mDrawableError != drawable && this.mDrawableError != null) {
                this.mDrawableError.setCallback(null);
            }
            this.mDrawableError = drawable;
            if (this.mDrawableError != null) {
                android.graphics.Rect rect = this.mCompoundRect;
                this.mDrawableError.setState(textView.getDrawableState());
                this.mDrawableError.copyBounds(rect);
                this.mDrawableError.setCallback(textView);
                this.mDrawableSizeError = rect.width();
                this.mDrawableHeightError = rect.height();
                return;
            }
            this.mDrawableHeightError = 0;
            this.mDrawableSizeError = 0;
        }

        private void applyErrorDrawableIfNeeded(int i) {
            switch (this.mDrawableSaved) {
                case 0:
                    this.mShowing[2] = this.mDrawableTemp;
                    this.mDrawableSizeRight = this.mDrawableSizeTemp;
                    this.mDrawableHeightRight = this.mDrawableHeightTemp;
                    break;
                case 1:
                    this.mShowing[0] = this.mDrawableTemp;
                    this.mDrawableSizeLeft = this.mDrawableSizeTemp;
                    this.mDrawableHeightLeft = this.mDrawableHeightTemp;
                    break;
            }
            if (this.mDrawableError != null) {
                switch (i) {
                    case 1:
                        this.mDrawableSaved = 1;
                        this.mDrawableTemp = this.mShowing[0];
                        this.mDrawableSizeTemp = this.mDrawableSizeLeft;
                        this.mDrawableHeightTemp = this.mDrawableHeightLeft;
                        this.mShowing[0] = this.mDrawableError;
                        this.mDrawableSizeLeft = this.mDrawableSizeError;
                        this.mDrawableHeightLeft = this.mDrawableHeightError;
                        break;
                    default:
                        this.mDrawableSaved = 0;
                        this.mDrawableTemp = this.mShowing[2];
                        this.mDrawableSizeTemp = this.mDrawableSizeRight;
                        this.mDrawableHeightTemp = this.mDrawableHeightRight;
                        this.mShowing[2] = this.mDrawableError;
                        this.mDrawableSizeRight = this.mDrawableSizeError;
                        this.mDrawableHeightRight = this.mDrawableHeightError;
                        break;
                }
            }
        }
    }

    public static void preloadFontCache() {
    }

    public TextView(android.content.Context context) {
        this(context, null);
    }

    public TextView(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
    }

    public TextView(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0f50  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0f57  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0fc9  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0fe8  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0fee  */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0ff4  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x101c  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x1038  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x105a  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x1085  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x1090  */
    /* JADX WARN: Removed duplicated region for block: B:249:0x1094  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x109b  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x10dd  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x1129  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x1139  */
    /* JADX WARN: Removed duplicated region for block: B:302:0x1144  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x1150  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x1199  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x11a0  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x11a9  */
    /* JADX WARN: Removed duplicated region for block: B:338:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:339:0x1192  */
    /* JADX WARN: Removed duplicated region for block: B:340:0x1149  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x108a  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x1066  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x1021  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0f73  */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0f81  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x0f8e  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x0f8b  */
    /* JADX WARN: Removed duplicated region for block: B:362:0x0f7d  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v85 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TextView(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray typedArray;
        int i3;
        boolean z;
        android.widget.TextView.BufferType bufferType;
        android.content.res.ColorStateList colorStateList;
        android.content.Context context2;
        boolean z2;
        int i4;
        boolean z3;
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes;
        int i5;
        java.lang.CharSequence charSequence;
        java.lang.CharSequence charSequence2;
        int focusable;
        int indexCount;
        int i6;
        int i7;
        int i8;
        int i9;
        float f;
        int i10;
        float f2;
        float f3;
        float f4;
        android.text.method.TextKeyListener.Capitalize capitalize;
        int i11;
        int i12;
        boolean z4;
        boolean z5;
        boolean z6;
        int i13;
        boolean z7;
        android.graphics.drawable.Drawable drawable;
        android.graphics.drawable.Drawable drawable2;
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes2;
        boolean z8;
        int i14;
        int i15;
        android.graphics.drawable.Drawable drawable3;
        int i16;
        int i17;
        this.mSingleLineLengthFilter = null;
        this.mEditableFactory = android.text.Editable.Factory.getInstance();
        this.mSpannableFactory = android.text.Spannable.Factory.getInstance();
        this.mCursorVisibleFromAttr = true;
        this.mMarqueeRepeatLimit = 3;
        this.mLastLayoutDirection = -1;
        this.mMarqueeFadeMode = 0;
        this.mBufferType = android.widget.TextView.BufferType.NORMAL;
        this.mLocalesChanged = false;
        this.mTextSizeUnit = -1;
        this.mLineBreakStyle = 0;
        this.mLineBreakWordStyle = 0;
        this.mListenerChanged = false;
        this.mGravity = 8388659;
        this.mLinksClickable = true;
        this.mSpacingMult = 1.0f;
        this.mSpacingAdd = 0.0f;
        this.mMaximum = Integer.MAX_VALUE;
        this.mMaxMode = 1;
        this.mMinimum = 0;
        this.mMinMode = 1;
        this.mOldMaximum = this.mMaximum;
        this.mOldMaxMode = this.mMaxMode;
        this.mMaxWidth = Integer.MAX_VALUE;
        this.mMaxWidthMode = 2;
        this.mMinWidth = 0;
        this.mMinWidthMode = 2;
        this.mDesiredHeightAtMeasure = -1;
        this.mIncludePad = true;
        this.mDeferScroll = -1;
        this.mFilters = NO_FILTERS;
        this.mHighlightColor = 1714664933;
        this.mHighlightPathBogus = true;
        this.mSearchResultHighlights = null;
        this.mSearchResultHighlightPaint = null;
        this.mFocusedSearchResultHighlightPaint = null;
        this.mFocusedSearchResultHighlightColor = -27086;
        this.mSearchResultHighlightColor = -256;
        this.mFocusedSearchResultIndex = -1;
        this.mGesturePreviewHighlightStart = -1;
        this.mGesturePreviewHighlightEnd = -1;
        this.mPathRecyclePool = new java.util.ArrayList();
        this.mHighlightPathsBogus = true;
        this.mPrimePointerId = -1;
        this.mDeviceProvisionedState = 0;
        this.mLastInputSource = 4098;
        this.mAutoSizeTextType = 0;
        this.mNeedsAutoSizeText = false;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = libcore.util.EmptyArray.INT;
        this.mHasPresetAutoSizeValues = false;
        this.mTextSetFromXmlOrResourceId = false;
        this.mTextId = 0;
        this.mHintId = 0;
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        if (getImportantForContentCapture() == 0) {
            setImportantForContentCapture(1);
        }
        setTextInternal("");
        android.content.res.Resources resources = getResources();
        android.content.res.CompatibilityInfo compatibilityInfo = resources.getCompatibilityInfo();
        this.mTextPaint = new android.text.TextPaint(1);
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        this.mTextPaint.setCompatibilityScaling(compatibilityInfo.applicationScale);
        this.mHighlightPaint = new android.graphics.Paint(1);
        this.mHighlightPaint.setCompatibilityScaling(compatibilityInfo.applicationScale);
        this.mMovement = getDefaultMovementMethod();
        this.mTransformation = null;
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes3 = new android.widget.TextView.TextAppearanceAttributes();
        textAppearanceAttributes3.mTextColor = android.content.res.ColorStateList.valueOf(-16777216);
        textAppearanceAttributes3.mTextSize = 15;
        this.mBreakStrategy = 0;
        this.mHyphenationFrequency = 0;
        this.mJustificationMode = 0;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        android.content.res.Resources.Theme theme = context.getTheme();
        android.content.res.TypedArray obtainStyledAttributes = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TextViewAppearance, i, i2);
        int[] iArr = com.android.internal.R.styleable.TextViewAppearance;
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes4 = textAppearanceAttributes3;
        java.lang.String str = "Failure reading input extras";
        int i18 = 0;
        java.lang.String str2 = LOG_TAG;
        saveAttributeDataForStyleable(context, iArr, attributeSet, obtainStyledAttributes, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        obtainStyledAttributes.recycle();
        if (resourceId == -1) {
            typedArray = null;
        } else {
            android.content.res.TypedArray obtainStyledAttributes2 = theme.obtainStyledAttributes(resourceId, com.android.internal.R.styleable.TextAppearance);
            saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TextAppearance, null, obtainStyledAttributes2, 0, resourceId);
            typedArray = obtainStyledAttributes2;
        }
        if (typedArray != null) {
            readTextAppearance(context, typedArray, textAppearanceAttributes4, false);
            textAppearanceAttributes4.mFontFamilyExplicit = false;
            typedArray.recycle();
        }
        boolean defaultEditable = getDefaultEditable();
        android.content.res.TypedArray obtainStyledAttributes3 = theme.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.TextView, i, i2);
        saveAttributeDataForStyleable(context, com.android.internal.R.styleable.TextView, attributeSet, obtainStyledAttributes3, i, i2);
        readTextAppearance(context, obtainStyledAttributes3, textAppearanceAttributes4, true);
        int indexCount2 = obtainStyledAttributes3.getIndexCount();
        int i19 = 0;
        int i20 = 0;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        int i21 = 0;
        boolean z12 = false;
        boolean z13 = false;
        int i22 = 0;
        int i23 = 0;
        boolean z14 = false;
        boolean z15 = false;
        java.lang.CharSequence charSequence3 = "";
        boolean z16 = defaultEditable;
        android.graphics.BlendMode blendMode = null;
        android.graphics.drawable.Drawable drawable4 = null;
        android.graphics.drawable.Drawable drawable5 = null;
        android.graphics.drawable.Drawable drawable6 = null;
        android.graphics.drawable.Drawable drawable7 = null;
        float f5 = -1.0f;
        android.graphics.drawable.Drawable drawable8 = null;
        android.graphics.drawable.Drawable drawable9 = null;
        android.content.res.ColorStateList colorStateList2 = null;
        java.lang.CharSequence charSequence4 = null;
        int i24 = -1;
        int i25 = -1;
        int i26 = -1;
        int i27 = -1;
        int i28 = -1;
        int i29 = -1;
        java.lang.CharSequence charSequence5 = null;
        java.lang.CharSequence charSequence6 = null;
        float f6 = -1.0f;
        float f7 = -1.0f;
        float f8 = -1.0f;
        while (i19 < indexCount2) {
            int index = obtainStyledAttributes3.getIndex(i19);
            switch (index) {
                case 0:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    setEnabled(obtainStyledAttributes3.getBoolean(index, isEnabled()));
                    break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 36:
                case 37:
                case 38:
                case 39:
                case 65:
                case 66:
                case 68:
                case 69:
                case 72:
                case 75:
                case 76:
                case 77:
                case 78:
                case 90:
                case 91:
                case 95:
                case 96:
                case 99:
                case 100:
                default:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 9:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i29 = obtainStyledAttributes3.getInt(index, i29);
                    drawable5 = drawable5;
                    drawable6 = drawable6;
                    z9 = z9;
                    z13 = z13;
                    i21 = i21;
                    drawable4 = drawable4;
                    z11 = z11;
                    continue;
                case 10:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    setGravity(obtainStyledAttributes3.getInt(index, -1));
                    i17 = i29;
                    break;
                case 11:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    this.mAutoLinkMask = obtainStyledAttributes3.getInt(index, 0);
                    i17 = i29;
                    break;
                case 12:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    this.mLinksClickable = obtainStyledAttributes3.getBoolean(index, true);
                    i17 = i29;
                    break;
                case 13:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    setMaxWidth(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    i17 = i29;
                    break;
                case 14:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    setMaxHeight(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    i17 = i29;
                    break;
                case 15:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    setMinWidth(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    i17 = i29;
                    break;
                case 16:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    setMinHeight(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    i17 = i29;
                    break;
                case 17:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i20 = obtainStyledAttributes3.getInt(index, i20);
                    drawable5 = drawable5;
                    z9 = z9;
                    z13 = z13;
                    i21 = i21;
                    drawable4 = drawable4;
                    z11 = z11;
                    continue;
                case 18:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    this.mTextId = obtainStyledAttributes3.getResourceId(index, i18);
                    charSequence3 = obtainStyledAttributes3.getText(index);
                    drawable5 = drawable5;
                    drawable6 = drawable6;
                    z15 = true;
                    z9 = z9;
                    z13 = z13;
                    i21 = i21;
                    drawable4 = drawable4;
                    z11 = z11;
                    continue;
                case 19:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    this.mHintId = obtainStyledAttributes3.getResourceId(index, 0);
                    charSequence4 = obtainStyledAttributes3.getText(index);
                    drawable5 = drawable5;
                    drawable6 = drawable6;
                    z9 = z9;
                    z13 = z13;
                    i21 = i21;
                    drawable4 = drawable4;
                    z11 = z11;
                    continue;
                case 20:
                    i12 = indexCount2;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i14 = i21;
                    i15 = i22;
                    boolean z17 = z11;
                    drawable = drawable4;
                    z8 = z9;
                    drawable3 = drawable5;
                    z4 = z17;
                    setTextScaleX(obtainStyledAttributes3.getFloat(index, 1.0f));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 21:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    if (obtainStyledAttributes3.getBoolean(index, true)) {
                        drawable2 = drawable6;
                        i16 = i20;
                        i17 = i29;
                        break;
                    } else {
                        setCursorVisible(false);
                        drawable2 = drawable6;
                        i16 = i20;
                        i17 = i29;
                        break;
                    }
                case 22:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setMaxLines(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 23:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setLines(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 24:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setHeight(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 25:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setMinLines(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 26:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setMaxEms(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 27:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setEms(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 28:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setWidth(obtainStyledAttributes3.getDimensionPixelSize(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 29:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    setMinEms(obtainStyledAttributes3.getInt(index, -1));
                    drawable2 = drawable6;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 30:
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    if (obtainStyledAttributes3.getBoolean(index, false)) {
                        setHorizontallyScrolling(true);
                        drawable2 = drawable6;
                        i16 = i20;
                        i17 = i29;
                        break;
                    } else {
                        drawable2 = drawable6;
                        i16 = i20;
                        i17 = i29;
                        break;
                    }
                case 31:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z9 = obtainStyledAttributes3.getBoolean(index, z9);
                    drawable4 = drawable4;
                    z11 = z11;
                    i21 = i21;
                    continue;
                case 32:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z10 = obtainStyledAttributes3.getBoolean(index, z10);
                    drawable4 = drawable4;
                    z11 = z11;
                    i21 = i21;
                    continue;
                case 33:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z11 = obtainStyledAttributes3.getBoolean(index, z11);
                    i21 = i21;
                    continue;
                case 34:
                    i12 = indexCount2;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i14 = i21;
                    i15 = i22;
                    if (!obtainStyledAttributes3.getBoolean(index, true)) {
                        setIncludeFontPadding(false);
                        drawable2 = drawable6;
                        z4 = z11;
                        drawable = drawable4;
                        z8 = z9;
                        drawable3 = drawable5;
                        i16 = i20;
                        i17 = i29;
                        break;
                    } else {
                        drawable2 = drawable6;
                        z4 = z11;
                        drawable = drawable4;
                        z8 = z9;
                        drawable3 = drawable5;
                        i16 = i20;
                        i17 = i29;
                        break;
                    }
                case 35:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i25 = obtainStyledAttributes3.getInt(index, -1);
                    i22 = i22;
                    i21 = i21;
                    continue;
                case 40:
                    i12 = indexCount2;
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i21 = obtainStyledAttributes3.getInt(index, i21);
                    continue;
                case 41:
                    i12 = indexCount2;
                    z5 = z12;
                    charSequence6 = obtainStyledAttributes3.getText(index);
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 42:
                    i12 = indexCount2;
                    z5 = obtainStyledAttributes3.getBoolean(index, z12);
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 43:
                    i12 = indexCount2;
                    charSequence5 = obtainStyledAttributes3.getText(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 44:
                    i12 = indexCount2;
                    i24 = obtainStyledAttributes3.getInt(index, i24);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 45:
                    i12 = indexCount2;
                    z13 = obtainStyledAttributes3.getBoolean(index, z13);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 46:
                    i12 = indexCount2;
                    z16 = obtainStyledAttributes3.getBoolean(index, z16);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 47:
                    i12 = indexCount2;
                    this.mFreezesText = obtainStyledAttributes3.getBoolean(index, false);
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    i14 = i21;
                    i15 = i22;
                    z4 = z11;
                    drawable = drawable4;
                    z8 = z9;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 48:
                    i12 = indexCount2;
                    drawable5 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 49:
                    i12 = indexCount2;
                    drawable7 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 50:
                    i12 = indexCount2;
                    drawable4 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 51:
                    i12 = indexCount2;
                    drawable6 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 52:
                    i12 = indexCount2;
                    i22 = obtainStyledAttributes3.getDimensionPixelSize(index, i22);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 53:
                    i12 = indexCount2;
                    this.mSpacingAdd = obtainStyledAttributes3.getDimensionPixelSize(index, (int) this.mSpacingAdd);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 54:
                    i12 = indexCount2;
                    this.mSpacingMult = obtainStyledAttributes3.getFloat(index, this.mSpacingMult);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 55:
                    i12 = indexCount2;
                    setMarqueeRepeatLimit(obtainStyledAttributes3.getInt(index, this.mMarqueeRepeatLimit));
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 56:
                    i12 = indexCount2;
                    i23 = obtainStyledAttributes3.getInt(index, 0);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 57:
                    i12 = indexCount2;
                    setPrivateImeOptions(obtainStyledAttributes3.getString(index));
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 58:
                    i12 = indexCount2;
                    try {
                        setInputExtras(obtainStyledAttributes3.getResourceId(index, 0));
                    } catch (java.io.IOException e) {
                        android.util.Log.w(str2, str, e);
                        z4 = z11;
                        z5 = z12;
                        z6 = z13;
                        i13 = i24;
                        z7 = z16;
                        drawable = drawable4;
                        drawable2 = drawable6;
                        textAppearanceAttributes2 = textAppearanceAttributes4;
                        z8 = z9;
                        i14 = i21;
                        i15 = i22;
                        drawable3 = drawable5;
                        i16 = i20;
                        i17 = i29;
                        break;
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                        java.lang.String str3 = str;
                        java.lang.String str4 = str2;
                        android.util.Log.w(str4, str3, e2);
                        str2 = str4;
                        str = str3;
                    }
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 59:
                    i12 = indexCount2;
                    createEditorIfNeeded();
                    this.mEditor.createInputContentTypeIfNeeded();
                    this.mEditor.mInputContentType.imeOptions = obtainStyledAttributes3.getInt(index, this.mEditor.mInputContentType.imeOptions);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 60:
                    i12 = indexCount2;
                    createEditorIfNeeded();
                    this.mEditor.createInputContentTypeIfNeeded();
                    this.mEditor.mInputContentType.imeActionLabel = obtainStyledAttributes3.getText(index);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 61:
                    i12 = indexCount2;
                    createEditorIfNeeded();
                    this.mEditor.createInputContentTypeIfNeeded();
                    this.mEditor.mInputContentType.imeActionId = obtainStyledAttributes3.getInt(index, this.mEditor.mInputContentType.imeActionId);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 62:
                    i12 = indexCount2;
                    this.mTextSelectHandleLeftRes = obtainStyledAttributes3.getResourceId(index, i18);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 63:
                    i12 = indexCount2;
                    this.mTextSelectHandleRightRes = obtainStyledAttributes3.getResourceId(index, i18);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 64:
                    i12 = indexCount2;
                    this.mTextSelectHandleRes = obtainStyledAttributes3.getResourceId(index, i18);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 67:
                    i12 = indexCount2;
                    setTextIsSelectable(obtainStyledAttributes3.getBoolean(index, i18));
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 70:
                    i12 = indexCount2;
                    this.mCursorDrawableRes = obtainStyledAttributes3.getResourceId(index, i18);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 71:
                    i12 = indexCount2;
                    this.mTextEditSuggestionItemLayout = obtainStyledAttributes3.getResourceId(index, 0);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 73:
                    i12 = indexCount2;
                    drawable8 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 74:
                    i12 = indexCount2;
                    drawable9 = obtainStyledAttributes3.getDrawable(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 79:
                    i12 = indexCount2;
                    colorStateList2 = obtainStyledAttributes3.getColorStateList(index);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 80:
                    i12 = indexCount2;
                    blendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes3.getInt(index, -1), blendMode);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 81:
                    i12 = indexCount2;
                    this.mBreakStrategy = obtainStyledAttributes3.getInt(index, i18);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 82:
                    i12 = indexCount2;
                    this.mHyphenationFrequency = obtainStyledAttributes3.getInt(index, 0);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 83:
                    i12 = indexCount2;
                    createEditorIfNeeded();
                    this.mEditor.mAllowUndo = obtainStyledAttributes3.getBoolean(index, true);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 84:
                    i12 = indexCount2;
                    this.mAutoSizeTextType = obtainStyledAttributes3.getInt(index, 0);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 85:
                    i12 = indexCount2;
                    f5 = obtainStyledAttributes3.getDimension(index, -1.0f);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 86:
                    i12 = indexCount2;
                    int resourceId2 = obtainStyledAttributes3.getResourceId(index, 0);
                    if (resourceId2 <= 0) {
                        z4 = z11;
                        z5 = z12;
                        z6 = z13;
                        i13 = i24;
                        z7 = z16;
                        drawable = drawable4;
                        drawable2 = drawable6;
                        textAppearanceAttributes2 = textAppearanceAttributes4;
                        z8 = z9;
                        i14 = i21;
                        i15 = i22;
                        drawable3 = drawable5;
                        i16 = i20;
                        i17 = i29;
                        break;
                    } else {
                        android.content.res.TypedArray obtainTypedArray = obtainStyledAttributes3.getResources().obtainTypedArray(resourceId2);
                        setupAutoSizeUniformPresetSizes(obtainTypedArray);
                        obtainTypedArray.recycle();
                        z4 = z11;
                        z5 = z12;
                        z6 = z13;
                        i13 = i24;
                        z7 = z16;
                        drawable = drawable4;
                        drawable2 = drawable6;
                        textAppearanceAttributes2 = textAppearanceAttributes4;
                        z8 = z9;
                        i14 = i21;
                        i15 = i22;
                        drawable3 = drawable5;
                        i16 = i20;
                        i17 = i29;
                        break;
                    }
                case 87:
                    i12 = indexCount2;
                    f6 = obtainStyledAttributes3.getDimension(index, -1.0f);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 88:
                    i12 = indexCount2;
                    f8 = obtainStyledAttributes3.getDimension(index, -1.0f);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 89:
                    i12 = indexCount2;
                    this.mJustificationMode = obtainStyledAttributes3.getInt(index, 0);
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 92:
                    i12 = indexCount2;
                    i26 = obtainStyledAttributes3.getDimensionPixelSize(index, -1);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 93:
                    i12 = indexCount2;
                    i27 = obtainStyledAttributes3.getDimensionPixelSize(index, -1);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 94:
                    android.util.TypedValue peekValue = obtainStyledAttributes3.peekValue(index);
                    if (peekValue != null) {
                        i12 = indexCount2;
                        if (peekValue.type == 5) {
                            int complexUnit = peekValue.getComplexUnit();
                            f7 = android.util.TypedValue.complexToFloat(peekValue.data);
                            i28 = complexUnit;
                            z5 = z12;
                            textAppearanceAttributes2 = textAppearanceAttributes4;
                            continue;
                        }
                    } else {
                        i12 = indexCount2;
                    }
                    f7 = obtainStyledAttributes3.getDimensionPixelSize(index, -1);
                    z5 = z12;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    break;
                case 97:
                    this.mLineBreakStyle = obtainStyledAttributes3.getInt(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 98:
                    this.mLineBreakWordStyle = obtainStyledAttributes3.getInt(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 101:
                    this.mTextEditSuggestionContainerLayout = obtainStyledAttributes3.getResourceId(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 102:
                    this.mTextEditSuggestionHighlightStyle = obtainStyledAttributes3.getResourceId(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 103:
                    this.mUseBoundsForWidth = obtainStyledAttributes3.getBoolean(index, i18);
                    i12 = indexCount2;
                    z5 = z12;
                    z14 = true;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    continue;
                case 104:
                    this.mUseLocalePreferredLineHeightForMinimum = obtainStyledAttributes3.getBoolean(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
                case 105:
                    this.mShiftDrawingOffsetForStartOverhang = obtainStyledAttributes3.getBoolean(index, i18);
                    i12 = indexCount2;
                    z4 = z11;
                    z5 = z12;
                    z6 = z13;
                    i13 = i24;
                    z7 = z16;
                    drawable = drawable4;
                    drawable2 = drawable6;
                    textAppearanceAttributes2 = textAppearanceAttributes4;
                    z8 = z9;
                    i14 = i21;
                    i15 = i22;
                    drawable3 = drawable5;
                    i16 = i20;
                    i17 = i29;
                    break;
            }
            z16 = z7;
            i24 = i13;
            i29 = i17;
            i22 = i15;
            drawable6 = drawable2;
            i20 = i16;
            z13 = z6;
            i21 = i14;
            drawable5 = drawable3;
            z9 = z8;
            drawable4 = drawable;
            z11 = z4;
            i19++;
            textAppearanceAttributes4 = textAppearanceAttributes2;
            indexCount2 = i12;
            i18 = 0;
            z12 = z5;
        }
        boolean z18 = z11;
        boolean z19 = z12;
        boolean z20 = z13;
        int i30 = i24;
        boolean z21 = z16;
        android.graphics.drawable.Drawable drawable10 = drawable4;
        android.graphics.drawable.Drawable drawable11 = drawable6;
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes5 = textAppearanceAttributes4;
        boolean z22 = z9;
        int i31 = i21;
        int i32 = i22;
        android.graphics.drawable.Drawable drawable12 = drawable5;
        int i33 = i20;
        int i34 = i29;
        obtainStyledAttributes3.recycle();
        android.widget.TextView.BufferType bufferType2 = android.widget.TextView.BufferType.EDITABLE;
        int i35 = i23;
        int i36 = i35 & 4095;
        boolean z23 = i36 == 129;
        boolean z24 = i36 == 225;
        android.graphics.drawable.Drawable drawable13 = drawable7;
        boolean z25 = i36 == 18;
        int i37 = context.getApplicationInfo().targetSdkVersion;
        this.mUseInternationalizedInput = i37 >= 26;
        if (android.app.compat.CompatChanges.isChangeEnabled(BORINGLAYOUT_FALLBACK_LINESPACING)) {
            this.mUseFallbackLineSpacing = 2;
        } else if (android.app.compat.CompatChanges.isChangeEnabled(STATICLAYOUT_FALLBACK_LINESPACING)) {
            this.mUseFallbackLineSpacing = 1;
        } else {
            this.mUseFallbackLineSpacing = 0;
        }
        if (!z14) {
            if (android.app.compat.CompatChanges.isChangeEnabled(USE_BOUNDS_FOR_WIDTH)) {
                this.mUseBoundsForWidth = android.text.ClientFlags.useBoundsForWidth();
            } else {
                this.mUseBoundsForWidth = false;
            }
        }
        this.mUseTextPaddingForUiTranslation = i37 <= 30;
        if (charSequence5 != null) {
            try {
                java.lang.Class<?> cls = java.lang.Class.forName(charSequence5.toString());
                try {
                    createEditorIfNeeded();
                    this.mEditor.mKeyListener = (android.text.method.KeyListener) cls.newInstance();
                    try {
                        android.widget.Editor editor = this.mEditor;
                        if (i35 == 0) {
                            i35 = this.mEditor.mKeyListener.getInputType();
                        }
                        editor.mInputType = i35;
                    } catch (java.lang.IncompatibleClassChangeError e3) {
                        this.mEditor.mInputType = 1;
                    }
                    i3 = 3;
                } catch (java.lang.IllegalAccessException e4) {
                    throw new java.lang.RuntimeException(e4);
                } catch (java.lang.InstantiationException e5) {
                    throw new java.lang.RuntimeException(e5);
                }
            } catch (java.lang.ClassNotFoundException e6) {
                throw new java.lang.RuntimeException(e6);
            }
        } else if (charSequence6 != null) {
            createEditorIfNeeded();
            this.mEditor.mKeyListener = android.text.method.DigitsKeyListener.getInstance(charSequence6.toString());
            this.mEditor.mInputType = i35 == 0 ? 1 : i35;
            i3 = 3;
        } else {
            if (i35 != 0) {
                setInputType(i35, true);
                bufferType = bufferType2;
                z = !isMultilineInputType(i35);
                i3 = 3;
            } else if (z19) {
                createEditorIfNeeded();
                this.mEditor.mKeyListener = android.text.method.DialerKeyListener.getInstance();
                i3 = 3;
                this.mEditor.mInputType = 3;
            } else {
                i3 = 3;
                if (i31 != 0) {
                    createEditorIfNeeded();
                    this.mEditor.mKeyListener = android.text.method.DigitsKeyListener.getInstance(null, (i31 & 2) != 0, (i31 & 4) != 0);
                    this.mEditor.mInputType = this.mEditor.mKeyListener.getInputType();
                } else if (z20 || i30 != -1) {
                    switch (i30) {
                        case 1:
                            capitalize = android.text.method.TextKeyListener.Capitalize.SENTENCES;
                            i11 = 16385;
                            break;
                        case 2:
                            capitalize = android.text.method.TextKeyListener.Capitalize.WORDS;
                            i11 = 8193;
                            break;
                        case 3:
                            capitalize = android.text.method.TextKeyListener.Capitalize.CHARACTERS;
                            i11 = 4097;
                            break;
                        default:
                            capitalize = android.text.method.TextKeyListener.Capitalize.NONE;
                            i11 = 1;
                            break;
                    }
                    createEditorIfNeeded();
                    this.mEditor.mKeyListener = android.text.method.TextKeyListener.getInstance(z20, capitalize);
                    this.mEditor.mInputType = i11;
                } else if (z21) {
                    createEditorIfNeeded();
                    this.mEditor.mKeyListener = android.text.method.TextKeyListener.getInstance();
                    this.mEditor.mInputType = 1;
                } else if (isTextSelectable()) {
                    if (this.mEditor != null) {
                        this.mEditor.mKeyListener = null;
                        this.mEditor.mInputType = 0;
                    }
                    bufferType = android.widget.TextView.BufferType.SPANNABLE;
                    setMovementMethod(android.text.method.ArrowKeyMovementMethod.getInstance());
                    z = z10;
                } else {
                    if (this.mEditor != null) {
                        this.mEditor.mKeyListener = null;
                    }
                    switch (i33) {
                        case 0:
                            bufferType = android.widget.TextView.BufferType.NORMAL;
                            z = z10;
                            break;
                        case 1:
                            bufferType = android.widget.TextView.BufferType.SPANNABLE;
                            z = z10;
                            break;
                        case 2:
                            bufferType = android.widget.TextView.BufferType.EDITABLE;
                            z = z10;
                            break;
                    }
                }
            }
            if (this.mEditor != null) {
                this.mEditor.adjustInputType(z22, z23, z24, z25);
            }
            if (z18) {
                createEditorIfNeeded();
                this.mEditor.mSelectAllOnFocus = true;
                if (bufferType == android.widget.TextView.BufferType.NORMAL) {
                    bufferType = android.widget.TextView.BufferType.SPANNABLE;
                }
            }
            colorStateList = colorStateList2;
            if (colorStateList == null || blendMode != null) {
                if (this.mDrawables != null) {
                    context2 = context;
                    this.mDrawables = new android.widget.TextView.Drawables(context2);
                } else {
                    context2 = context;
                }
                if (colorStateList != null) {
                    z2 = true;
                } else {
                    this.mDrawables.mTintList = colorStateList;
                    z2 = true;
                    this.mDrawables.mHasTint = true;
                }
                if (blendMode != null) {
                    this.mDrawables.mBlendMode = blendMode;
                    this.mDrawables.mHasTintMode = z2;
                }
            } else {
                context2 = context;
            }
            setCompoundDrawablesWithIntrinsicBounds(drawable10, drawable12, drawable11, drawable13);
            setRelativeDrawablesIfNeeded(drawable8, drawable9);
            setCompoundDrawablePadding(i32);
            setInputTypeSingleLine(z);
            applySingleLine(z, z, z, false);
            if (z || getKeyListener() != null) {
                i4 = i34;
            } else {
                i4 = i34;
                if (i4 == -1) {
                    i4 = i3;
                }
            }
            switch (i4) {
                case 1:
                    setEllipsize(android.text.TextUtils.TruncateAt.START);
                    break;
                case 2:
                    setEllipsize(android.text.TextUtils.TruncateAt.MIDDLE);
                    break;
                case 3:
                    setEllipsize(android.text.TextUtils.TruncateAt.END);
                    break;
                case 4:
                    if (android.view.ViewConfiguration.get(context).isFadingMarqueeEnabled()) {
                        setHorizontalFadingEdgeEnabled(true);
                        this.mMarqueeFadeMode = 0;
                    } else {
                        setHorizontalFadingEdgeEnabled(false);
                        this.mMarqueeFadeMode = 1;
                    }
                    setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
                    break;
            }
            z3 = !z22 || z23 || z24 || z25;
            if (!z3 || (this.mEditor != null && (this.mEditor.mInputType & 4095) == 129)) {
                textAppearanceAttributes = textAppearanceAttributes5;
            } else {
                textAppearanceAttributes = textAppearanceAttributes5;
                textAppearanceAttributes.mTypefaceIndex = i3;
            }
            this.mFontWeightAdjustment = getContext().getResources().getConfiguration().fontWeightAdjustment;
            applyTextAppearance(textAppearanceAttributes);
            if (z3) {
                setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            }
            if (bufferType == android.widget.TextView.BufferType.EDITABLE || !z) {
                i5 = i25;
            } else {
                i5 = i25;
                if (i5 == -1) {
                    this.mSingleLineLengthFilter = new android.text.InputFilter.LengthFilter(5000);
                }
            }
            if (this.mSingleLineLengthFilter == null) {
                setFilters(new android.text.InputFilter[]{this.mSingleLineLengthFilter});
            } else if (i5 >= 0) {
                setFilters(new android.text.InputFilter[]{new android.text.InputFilter.LengthFilter(i5)});
            } else {
                setFilters(NO_FILTERS);
            }
            setText(charSequence3, bufferType);
            if (this.mText != null) {
                charSequence = "";
                this.mText = charSequence;
            } else {
                charSequence = "";
            }
            if (this.mTransformed == null) {
                this.mTransformed = charSequence;
            }
            if (z15) {
                this.mTextSetFromXmlOrResourceId = true;
            }
            charSequence2 = charSequence4;
            if (charSequence2 != null) {
                setHint(charSequence2);
            }
            android.content.res.TypedArray obtainStyledAttributes4 = context2.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.View, i, i2);
            boolean z26 = this.mMovement == null || getKeyListener() != null;
            boolean z27 = !z26 || isClickable();
            boolean z28 = !z26 || isLongClickable();
            focusable = getFocusable();
            indexCount = obtainStyledAttributes4.getIndexCount();
            boolean z29 = true;
            for (i6 = 0; i6 < indexCount; i6++) {
                int index2 = obtainStyledAttributes4.getIndex(i6);
                switch (index2) {
                    case 19:
                        android.util.TypedValue typedValue = new android.util.TypedValue();
                        if (obtainStyledAttributes4.getValue(index2, typedValue)) {
                            if (typedValue.type == 18) {
                                if (typedValue.data == 0) {
                                    focusable = 0;
                                    break;
                                } else {
                                    focusable = 1;
                                    break;
                                }
                            } else {
                                focusable = typedValue.data;
                                break;
                            }
                        } else {
                            break;
                        }
                    case 30:
                        z27 = obtainStyledAttributes4.getBoolean(index2, z27);
                        break;
                    case 31:
                        z28 = obtainStyledAttributes4.getBoolean(index2, z28);
                        break;
                    case 107:
                        z29 = obtainStyledAttributes4.getBoolean(index2, true);
                        break;
                }
            }
            obtainStyledAttributes4.recycle();
            if (focusable != getFocusable()) {
                setFocusable(focusable);
            }
            setClickable(z27);
            setLongClickable(z28);
            setAutoHandwritingEnabled(z29);
            if (this.mEditor != null) {
                this.mEditor.prepareCursorControllers();
            }
            if (getImportantForAccessibility() == 0) {
                i7 = 1;
            } else {
                i7 = 1;
                setImportantForAccessibility(1);
            }
            if (supportsAutoSizeText()) {
                this.mAutoSizeTextType = 0;
            } else if (this.mAutoSizeTextType == i7) {
                if (!this.mHasPresetAutoSizeValues) {
                    android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    if (f6 != -1.0f) {
                        i10 = 2;
                        f2 = f6;
                    } else {
                        i10 = 2;
                        f2 = android.util.TypedValue.applyDimension(2, 12.0f, displayMetrics);
                    }
                    if (f8 != -1.0f) {
                        f3 = f8;
                    } else {
                        f3 = android.util.TypedValue.applyDimension(i10, 112.0f, displayMetrics);
                    }
                    if (f5 != -1.0f) {
                        f4 = f5;
                    } else {
                        f4 = 1.0f;
                    }
                    validateAndSetAutoSizeTextTypeUniformConfiguration(f2, f3, f4);
                }
                setupAutoSizeText();
            }
            i8 = i26;
            if (i8 >= 0) {
                setFirstBaselineToTopHeight(i8);
            }
            i9 = i27;
            if (i9 >= 0) {
                setLastBaselineToBottomHeight(i9);
            }
            f = f7;
            if (f < 0.0f) {
                int i38 = i28;
                if (i38 == -1) {
                    setLineHeightPx(f);
                    return;
                } else {
                    setLineHeight(i38, f);
                    return;
                }
            }
            return;
        }
        bufferType = bufferType2;
        z = z10;
        if (this.mEditor != null) {
        }
        if (z18) {
        }
        colorStateList = colorStateList2;
        if (colorStateList == null) {
        }
        if (this.mDrawables != null) {
        }
        if (colorStateList != null) {
        }
        if (blendMode != null) {
        }
        setCompoundDrawablesWithIntrinsicBounds(drawable10, drawable12, drawable11, drawable13);
        setRelativeDrawablesIfNeeded(drawable8, drawable9);
        setCompoundDrawablePadding(i32);
        setInputTypeSingleLine(z);
        applySingleLine(z, z, z, false);
        if (z) {
        }
        i4 = i34;
        switch (i4) {
        }
        if (z22) {
        }
        if (!z3 || (this.mEditor != null && (this.mEditor.mInputType & 4095) == 129)) {
        }
        this.mFontWeightAdjustment = getContext().getResources().getConfiguration().fontWeightAdjustment;
        applyTextAppearance(textAppearanceAttributes);
        if (z3) {
        }
        if (bufferType == android.widget.TextView.BufferType.EDITABLE) {
        }
        i5 = i25;
        if (this.mSingleLineLengthFilter == null) {
        }
        setText(charSequence3, bufferType);
        if (this.mText != null) {
        }
        if (this.mTransformed == null) {
        }
        if (z15) {
        }
        charSequence2 = charSequence4;
        if (charSequence2 != null) {
        }
        android.content.res.TypedArray obtainStyledAttributes42 = context2.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.View, i, i2);
        if (this.mMovement == null) {
        }
        if (z26) {
        }
        if (z26) {
        }
        focusable = getFocusable();
        indexCount = obtainStyledAttributes42.getIndexCount();
        boolean z292 = true;
        while (i6 < indexCount) {
        }
        obtainStyledAttributes42.recycle();
        if (focusable != getFocusable()) {
        }
        setClickable(z27);
        setLongClickable(z28);
        setAutoHandwritingEnabled(z292);
        if (this.mEditor != null) {
        }
        if (getImportantForAccessibility() == 0) {
        }
        if (supportsAutoSizeText()) {
        }
        i8 = i26;
        if (i8 >= 0) {
        }
        i9 = i27;
        if (i9 >= 0) {
        }
        f = f7;
        if (f < 0.0f) {
        }
    }

    private void setTextInternal(java.lang.CharSequence charSequence) {
        this.mText = charSequence;
        this.mSpannable = charSequence instanceof android.text.Spannable ? (android.text.Spannable) charSequence : null;
        this.mPrecomputed = charSequence instanceof android.text.PrecomputedText ? (android.text.PrecomputedText) charSequence : null;
    }

    public void setAutoSizeTextTypeWithDefaults(int i) {
        if (supportsAutoSizeText()) {
            switch (i) {
                case 0:
                    clearAutoSizeConfiguration();
                    return;
                case 1:
                    android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    validateAndSetAutoSizeTextTypeUniformConfiguration(android.util.TypedValue.applyDimension(2, 12.0f, displayMetrics), android.util.TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                    if (setupAutoSizeText()) {
                        autoSizeText();
                        invalidate();
                        return;
                    }
                    return;
                default:
                    throw new java.lang.IllegalArgumentException("Unknown auto-size text type: " + i);
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithConfiguration(int i, int i2, int i3, int i4) {
        if (supportsAutoSizeText()) {
            android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            validateAndSetAutoSizeTextTypeUniformConfiguration(android.util.TypedValue.applyDimension(i4, i, displayMetrics), android.util.TypedValue.applyDimension(i4, i2, displayMetrics), android.util.TypedValue.applyDimension(i4, i3, displayMetrics));
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public void setAutoSizeTextTypeUniformWithPresetSizes(int[] iArr, int i) {
        if (supportsAutoSizeText()) {
            int length = iArr.length;
            if (length > 0) {
                int[] iArr2 = new int[length];
                if (i == 0) {
                    iArr2 = java.util.Arrays.copyOf(iArr, length);
                } else {
                    android.util.DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                    for (int i2 = 0; i2 < length; i2++) {
                        iArr2[i2] = java.lang.Math.round(android.util.TypedValue.applyDimension(i, iArr[i2], displayMetrics));
                    }
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr2);
                if (!setupAutoSizeUniformPresetSizesConfiguration()) {
                    throw new java.lang.IllegalArgumentException("None of the preset sizes is valid: " + java.util.Arrays.toString(iArr));
                }
            } else {
                this.mHasPresetAutoSizeValues = false;
            }
            if (setupAutoSizeText()) {
                autoSizeText();
                invalidate();
            }
        }
    }

    public int getAutoSizeTextType() {
        return this.mAutoSizeTextType;
    }

    public int getAutoSizeStepGranularity() {
        return java.lang.Math.round(this.mAutoSizeStepGranularityInPx);
    }

    public int getAutoSizeMinTextSize() {
        return java.lang.Math.round(this.mAutoSizeMinTextSizeInPx);
    }

    public int getAutoSizeMaxTextSize() {
        return java.lang.Math.round(this.mAutoSizeMaxTextSizeInPx);
    }

    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextSizesInPx;
    }

    private void setupAutoSizeUniformPresetSizes(android.content.res.TypedArray typedArray) {
        int length = typedArray.length();
        int[] iArr = new int[length];
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                iArr[i] = typedArray.getDimensionPixelSize(i, -1);
            }
            this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr);
            setupAutoSizeUniformPresetSizesConfiguration();
        }
    }

    private boolean setupAutoSizeUniformPresetSizesConfiguration() {
        this.mHasPresetAutoSizeValues = this.mAutoSizeTextSizesInPx.length > 0;
        if (this.mHasPresetAutoSizeValues) {
            this.mAutoSizeTextType = 1;
            this.mAutoSizeMinTextSizeInPx = this.mAutoSizeTextSizesInPx[0];
            this.mAutoSizeMaxTextSizeInPx = this.mAutoSizeTextSizesInPx[r0 - 1];
            this.mAutoSizeStepGranularityInPx = -1.0f;
        }
        return this.mHasPresetAutoSizeValues;
    }

    private void validateAndSetAutoSizeTextTypeUniformConfiguration(float f, float f2, float f3) {
        if (f <= 0.0f) {
            throw new java.lang.IllegalArgumentException("Minimum auto-size text size (" + f + "px) is less or equal to (0px)");
        }
        if (f2 <= f) {
            throw new java.lang.IllegalArgumentException("Maximum auto-size text size (" + f2 + "px) is less or equal to minimum auto-size text size (" + f + "px)");
        }
        if (f3 <= 0.0f) {
            throw new java.lang.IllegalArgumentException("The auto-size step granularity (" + f3 + "px) is less or equal to (0px)");
        }
        this.mAutoSizeTextType = 1;
        this.mAutoSizeMinTextSizeInPx = f;
        this.mAutoSizeMaxTextSizeInPx = f2;
        this.mAutoSizeStepGranularityInPx = f3;
        this.mHasPresetAutoSizeValues = false;
    }

    private void clearAutoSizeConfiguration() {
        this.mAutoSizeTextType = 0;
        this.mAutoSizeMinTextSizeInPx = -1.0f;
        this.mAutoSizeMaxTextSizeInPx = -1.0f;
        this.mAutoSizeStepGranularityInPx = -1.0f;
        this.mAutoSizeTextSizesInPx = libcore.util.EmptyArray.INT;
        this.mNeedsAutoSizeText = false;
    }

    private int[] cleanupAutoSizePresetSizes(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return iArr;
        }
        java.util.Arrays.sort(iArr);
        android.util.IntArray intArray = new android.util.IntArray();
        for (int i : iArr) {
            if (i > 0 && intArray.binarySearch(i) < 0) {
                intArray.add(i);
            }
        }
        if (length == intArray.size()) {
            return iArr;
        }
        return intArray.toArray();
    }

    private boolean setupAutoSizeText() {
        if (supportsAutoSizeText() && this.mAutoSizeTextType == 1) {
            if (!this.mHasPresetAutoSizeValues || this.mAutoSizeTextSizesInPx.length == 0) {
                int floor = ((int) java.lang.Math.floor((this.mAutoSizeMaxTextSizeInPx - this.mAutoSizeMinTextSizeInPx) / this.mAutoSizeStepGranularityInPx)) + 1;
                int[] iArr = new int[floor];
                for (int i = 0; i < floor; i++) {
                    iArr[i] = java.lang.Math.round(this.mAutoSizeMinTextSizeInPx + (i * this.mAutoSizeStepGranularityInPx));
                }
                this.mAutoSizeTextSizesInPx = cleanupAutoSizePresetSizes(iArr);
            }
            this.mNeedsAutoSizeText = true;
        } else {
            this.mNeedsAutoSizeText = false;
        }
        return this.mNeedsAutoSizeText;
    }

    private int[] parseDimensionArray(android.content.res.TypedArray typedArray) {
        if (typedArray == null) {
            return null;
        }
        int length = typedArray.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = typedArray.getDimensionPixelSize(i, 0);
        }
        return iArr;
    }

    @Override // android.view.View
    public void onActivityResult(int i, int i2, android.content.Intent intent) {
        if (i == 100) {
            if (i2 == -1 && intent != null) {
                java.lang.CharSequence charSequenceExtra = intent.getCharSequenceExtra(android.content.Intent.EXTRA_PROCESS_TEXT);
                if (charSequenceExtra != null) {
                    if (isTextEditable()) {
                        performReceiveContent(new android.view.ContentInfo.Builder(android.content.ClipData.newPlainText("", charSequenceExtra), 5).build());
                        if (this.mEditor != null) {
                            this.mEditor.refreshTextActionMode();
                            return;
                        }
                        return;
                    }
                    if (charSequenceExtra.length() > 0) {
                        android.widget.Toast.makeText(getContext(), java.lang.String.valueOf(charSequenceExtra), 1).show();
                        return;
                    }
                    return;
                }
                return;
            }
            if (this.mSpannable != null) {
                android.text.Selection.setSelection(this.mSpannable, getSelectionEnd());
            }
        }
    }

    private void setTypefaceFromAttrs(android.graphics.Typeface typeface, java.lang.String str, int i, int i2, int i3) {
        if (typeface == null && str != null) {
            resolveStyleAndSetTypeface(android.graphics.Typeface.create(str, 0), i2, i3);
            return;
        }
        if (typeface != null) {
            resolveStyleAndSetTypeface(typeface, i2, i3);
            return;
        }
        switch (i) {
            case 1:
                resolveStyleAndSetTypeface(android.graphics.Typeface.SANS_SERIF, i2, i3);
                break;
            case 2:
                resolveStyleAndSetTypeface(android.graphics.Typeface.SERIF, i2, i3);
                break;
            case 3:
                resolveStyleAndSetTypeface(android.graphics.Typeface.MONOSPACE, i2, i3);
                break;
            default:
                resolveStyleAndSetTypeface(null, i2, i3);
                break;
        }
    }

    private void resolveStyleAndSetTypeface(android.graphics.Typeface typeface, int i, int i2) {
        if (i2 >= 0) {
            setTypeface(android.graphics.Typeface.create(typeface, java.lang.Math.min(1000, i2), (i & 2) != 0));
        } else {
            setTypeface(typeface, i);
        }
    }

    private void setRelativeDrawablesIfNeeded(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2) {
        if ((drawable == null && drawable2 == null) ? false : true) {
            android.widget.TextView.Drawables drawables = this.mDrawables;
            if (drawables == null) {
                drawables = new android.widget.TextView.Drawables(getContext());
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            android.graphics.Rect rect = drawables.mCompoundRect;
            int[] drawableState = getDrawableState();
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                drawable.setState(drawableState);
                drawable.copyBounds(rect);
                drawable.setCallback(this);
                drawables.mDrawableStart = drawable;
                drawables.mDrawableSizeStart = rect.width();
                drawables.mDrawableHeightStart = rect.height();
            } else {
                drawables.mDrawableHeightStart = 0;
                drawables.mDrawableSizeStart = 0;
            }
            if (drawable2 != null) {
                drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                drawable2.setState(drawableState);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                drawables.mDrawableEnd = drawable2;
                drawables.mDrawableSizeEnd = rect.width();
                drawables.mDrawableHeightEnd = rect.height();
            } else {
                drawables.mDrawableHeightEnd = 0;
                drawables.mDrawableSizeEnd = 0;
            }
            resetResolvedDrawables();
            resolveDrawables();
            applyCompoundDrawableTint();
        }
    }

    @Override // android.view.View
    @android.view.RemotableViewMethod
    public void setEnabled(boolean z) {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        android.view.inputmethod.InputMethodManager inputMethodManager2;
        if (z == isEnabled()) {
            return;
        }
        if (!z && (inputMethodManager2 = getInputMethodManager()) != null) {
            inputMethodManager2.hideSoftInputFromView(this, 0);
        }
        super.setEnabled(z);
        if (z && (inputMethodManager = getInputMethodManager()) != null) {
            inputMethodManager.restartInput(this);
        }
        if (this.mEditor != null) {
            this.mEditor.invalidateTextDisplayList();
            this.mEditor.prepareCursorControllers();
            this.mEditor.makeBlink();
        }
    }

    public void setTypeface(android.graphics.Typeface typeface, int i) {
        android.graphics.Typeface create;
        if (i > 0) {
            if (typeface == null) {
                create = android.graphics.Typeface.defaultFromStyle(i);
            } else {
                create = android.graphics.Typeface.create(typeface, i);
            }
            setTypeface(create);
            int i2 = (~(create != null ? create.getStyle() : 0)) & i;
            this.mTextPaint.setFakeBoldText((i2 & 1) != 0);
            this.mTextPaint.setTextSkewX((i2 & 2) != 0 ? -0.25f : 0.0f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setTypeface(typeface);
    }

    protected boolean getDefaultEditable() {
        return false;
    }

    protected android.text.method.MovementMethod getDefaultMovementMethod() {
        return null;
    }

    @android.view.ViewDebug.CapturedViewProperty
    public java.lang.CharSequence getText() {
        android.view.translation.ViewTranslationCallback viewTranslationCallback;
        if (this.mUseTextPaddingForUiTranslation && (viewTranslationCallback = getViewTranslationCallback()) != null && (viewTranslationCallback instanceof android.widget.TextViewTranslationCallback)) {
            android.widget.TextViewTranslationCallback textViewTranslationCallback = (android.widget.TextViewTranslationCallback) viewTranslationCallback;
            if (textViewTranslationCallback.isTextPaddingEnabled() && textViewTranslationCallback.isShowingTranslation()) {
                return textViewTranslationCallback.getPaddedText(this.mText, this.mTransformed);
            }
        }
        return this.mText;
    }

    public int length() {
        return this.mText.length();
    }

    public android.text.Editable getEditableText() {
        if (this.mText instanceof android.text.Editable) {
            return (android.text.Editable) this.mText;
        }
        return null;
    }

    public java.lang.CharSequence getTransformed() {
        return this.mTransformed;
    }

    public int getLineHeight() {
        return com.android.internal.util.FastMath.round((this.mTextPaint.getFontMetricsInt(null) * this.mSpacingMult) + this.mSpacingAdd);
    }

    public final android.text.Layout getLayout() {
        return this.mLayout;
    }

    final android.text.Layout getHintLayout() {
        return this.mHintLayout;
    }

    public final android.content.UndoManager getUndoManager() {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public final android.widget.Editor getEditorForTesting() {
        return this.mEditor;
    }

    public final void setUndoManager(android.content.UndoManager undoManager, java.lang.String str) {
        throw new java.lang.UnsupportedOperationException("not implemented");
    }

    public final android.text.method.KeyListener getKeyListener() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mKeyListener;
    }

    public void setKeyListener(android.text.method.KeyListener keyListener) {
        this.mListenerChanged = true;
        setKeyListenerOnly(keyListener);
        fixFocusableAndClickableSettings();
        if (keyListener != null) {
            createEditorIfNeeded();
            setInputTypeFromEditor();
        } else if (this.mEditor != null) {
            this.mEditor.mInputType = 0;
        }
        android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager != null) {
            inputMethodManager.restartInput(this);
        }
    }

    private void setInputTypeFromEditor() {
        try {
            this.mEditor.mInputType = this.mEditor.mKeyListener.getInputType();
        } catch (java.lang.IncompatibleClassChangeError e) {
            this.mEditor.mInputType = 1;
        }
        setInputTypeSingleLine(this.mSingleLine);
    }

    private void setKeyListenerOnly(android.text.method.KeyListener keyListener) {
        if (this.mEditor == null && keyListener == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mKeyListener != keyListener) {
            this.mEditor.mKeyListener = keyListener;
            if (keyListener != null && !(this.mText instanceof android.text.Editable)) {
                lambda$setTextAsync$0(this.mText);
            }
            setFilters((android.text.Editable) this.mText, this.mFilters);
        }
    }

    public final android.text.method.MovementMethod getMovementMethod() {
        return this.mMovement;
    }

    public final void setMovementMethod(android.text.method.MovementMethod movementMethod) {
        if (this.mMovement != movementMethod) {
            this.mMovement = movementMethod;
            if (movementMethod != null && this.mSpannable == null) {
                lambda$setTextAsync$0(this.mText);
            }
            fixFocusableAndClickableSettings();
            if (this.mEditor != null) {
                this.mEditor.prepareCursorControllers();
            }
        }
    }

    private void fixFocusableAndClickableSettings() {
        if (this.mMovement != null || (this.mEditor != null && this.mEditor.mKeyListener != null)) {
            setFocusable(1);
            setClickable(true);
            setLongClickable(true);
        } else {
            setFocusable(16);
            setClickable(false);
            setLongClickable(false);
        }
    }

    public final android.text.method.TransformationMethod getTransformationMethod() {
        return this.mTransformation;
    }

    public final void setTransformationMethod(android.text.method.TransformationMethod transformationMethod) {
        if (this.mEditor != null) {
            this.mEditor.setTransformationMethod(transformationMethod);
        } else {
            setTransformationMethodInternal(transformationMethod, true);
        }
    }

    void setTransformationMethodInternal(android.text.method.TransformationMethod transformationMethod, boolean z) {
        if (transformationMethod == this.mTransformation) {
            return;
        }
        if (this.mTransformation != null && this.mSpannable != null) {
            this.mSpannable.removeSpan(this.mTransformation);
        }
        this.mTransformation = transformationMethod;
        if (transformationMethod instanceof android.text.method.TransformationMethod2) {
            android.text.method.TransformationMethod2 transformationMethod2 = (android.text.method.TransformationMethod2) transformationMethod;
            this.mAllowTransformationLengthChange = (isTextSelectable() || (this.mText instanceof android.text.Editable)) ? false : true;
            transformationMethod2.setLengthChangesAllowed(this.mAllowTransformationLengthChange);
        } else {
            this.mAllowTransformationLengthChange = false;
        }
        if (z) {
            if (com.android.text.flags.Flags.insertModeNotUpdateSelection()) {
                if (this.mTransformation == null) {
                    this.mTransformed = this.mText;
                } else {
                    this.mTransformed = this.mTransformation.getTransformation(this.mText, this);
                }
                if (this.mTransformed == null) {
                    this.mTransformed = "";
                }
                boolean z2 = this.mTransformed instanceof android.text.method.OffsetMapping;
                if (this.mTransformation != null && (this.mText instanceof android.text.Spannable) && (!this.mAllowTransformationLengthChange || z2)) {
                    ((android.text.Spannable) this.mText).setSpan(this.mTransformation, 0, this.mText.length(), ((z2 ? 200 : 0) << 16) | 18);
                }
                if (this.mLayout != null) {
                    nullLayouts();
                    requestLayout();
                    invalidate();
                }
            } else {
                lambda$setTextAsync$0(this.mText);
            }
        }
        if (hasPasswordTransformationMethod()) {
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        this.mTextDir = getTextDirectionHeuristic();
    }

    public int getCompoundPaddingTop() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[1] == null) {
            return this.mPaddingTop;
        }
        return this.mPaddingTop + drawables.mDrawablePadding + drawables.mDrawableSizeTop;
    }

    public int getCompoundPaddingBottom() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[3] == null) {
            return this.mPaddingBottom;
        }
        return this.mPaddingBottom + drawables.mDrawablePadding + drawables.mDrawableSizeBottom;
    }

    public int getCompoundPaddingLeft() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[0] == null) {
            return this.mPaddingLeft;
        }
        return this.mPaddingLeft + drawables.mDrawablePadding + drawables.mDrawableSizeLeft;
    }

    public int getCompoundPaddingRight() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables == null || drawables.mShowing[2] == null) {
            return this.mPaddingRight;
        }
        return this.mPaddingRight + drawables.mDrawablePadding + drawables.mDrawableSizeRight;
    }

    public int getCompoundPaddingStart() {
        resolveDrawables();
        switch (getLayoutDirection()) {
            case 1:
                return getCompoundPaddingRight();
            default:
                return getCompoundPaddingLeft();
        }
    }

    public int getCompoundPaddingEnd() {
        resolveDrawables();
        switch (getLayoutDirection()) {
            case 1:
                return getCompoundPaddingLeft();
            default:
                return getCompoundPaddingRight();
        }
    }

    public int getExtendedPaddingTop() {
        if (this.mMaxMode != 1) {
            return getCompoundPaddingTop();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingTop();
        }
        int compoundPaddingTop = getCompoundPaddingTop();
        int height = (getHeight() - compoundPaddingTop) - getCompoundPaddingBottom();
        int lineTop = this.mLayout.getLineTop(this.mMaximum);
        if (lineTop >= height) {
            return compoundPaddingTop;
        }
        int i = this.mGravity & 112;
        if (i == 48) {
            return compoundPaddingTop;
        }
        if (i == 80) {
            return (compoundPaddingTop + height) - lineTop;
        }
        return compoundPaddingTop + ((height - lineTop) / 2);
    }

    public int getExtendedPaddingBottom() {
        if (this.mMaxMode != 1) {
            return getCompoundPaddingBottom();
        }
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mLayout.getLineCount() <= this.mMaximum) {
            return getCompoundPaddingBottom();
        }
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int height = (getHeight() - compoundPaddingTop) - compoundPaddingBottom;
        int lineTop = this.mLayout.getLineTop(this.mMaximum);
        if (lineTop >= height) {
            return compoundPaddingBottom;
        }
        int i = this.mGravity & 112;
        if (i == 48) {
            return (compoundPaddingBottom + height) - lineTop;
        }
        if (i == 80) {
            return compoundPaddingBottom;
        }
        return compoundPaddingBottom + ((height - lineTop) / 2);
    }

    public int getTotalPaddingLeft() {
        return getCompoundPaddingLeft();
    }

    public int getTotalPaddingRight() {
        return getCompoundPaddingRight();
    }

    public int getTotalPaddingStart() {
        return getCompoundPaddingStart();
    }

    public int getTotalPaddingEnd() {
        return getCompoundPaddingEnd();
    }

    public int getTotalPaddingTop() {
        return getExtendedPaddingTop() + getVerticalOffset(true);
    }

    public int getTotalPaddingBottom() {
        return getExtendedPaddingBottom() + getBottomVerticalOffset(true);
    }

    public void setCompoundDrawables(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, android.graphics.drawable.Drawable drawable4) {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            if (drawables.mDrawableStart != null) {
                drawables.mDrawableStart.setCallback(null);
            }
            drawables.mDrawableStart = null;
            if (drawables.mDrawableEnd != null) {
                drawables.mDrawableEnd.setCallback(null);
            }
            drawables.mDrawableEnd = null;
            drawables.mDrawableHeightStart = 0;
            drawables.mDrawableSizeStart = 0;
            drawables.mDrawableHeightEnd = 0;
            drawables.mDrawableSizeEnd = 0;
        }
        if (!((drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) ? false : true)) {
            if (drawables != null) {
                if (drawables.hasMetadata()) {
                    for (int length = drawables.mShowing.length - 1; length >= 0; length--) {
                        if (drawables.mShowing[length] != null) {
                            drawables.mShowing[length].setCallback(null);
                        }
                        drawables.mShowing[length] = null;
                    }
                    drawables.mDrawableHeightLeft = 0;
                    drawables.mDrawableSizeLeft = 0;
                    drawables.mDrawableHeightRight = 0;
                    drawables.mDrawableSizeRight = 0;
                    drawables.mDrawableWidthTop = 0;
                    drawables.mDrawableSizeTop = 0;
                    drawables.mDrawableWidthBottom = 0;
                    drawables.mDrawableSizeBottom = 0;
                } else {
                    this.mDrawables = null;
                }
            }
        } else {
            if (drawables == null) {
                drawables = new android.widget.TextView.Drawables(getContext());
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = false;
            if (drawables.mShowing[0] != drawable && drawables.mShowing[0] != null) {
                drawables.mShowing[0].setCallback(null);
            }
            drawables.mShowing[0] = drawable;
            if (drawables.mShowing[1] != drawable2 && drawables.mShowing[1] != null) {
                drawables.mShowing[1].setCallback(null);
            }
            drawables.mShowing[1] = drawable2;
            if (drawables.mShowing[2] != drawable3 && drawables.mShowing[2] != null) {
                drawables.mShowing[2].setCallback(null);
            }
            drawables.mShowing[2] = drawable3;
            if (drawables.mShowing[3] != drawable4 && drawables.mShowing[3] != null) {
                drawables.mShowing[3].setCallback(null);
            }
            drawables.mShowing[3] = drawable4;
            android.graphics.Rect rect = drawables.mCompoundRect;
            int[] drawableState = getDrawableState();
            if (drawable != null) {
                drawable.setState(drawableState);
                drawable.copyBounds(rect);
                drawable.setCallback(this);
                drawables.mDrawableSizeLeft = rect.width();
                drawables.mDrawableHeightLeft = rect.height();
            } else {
                drawables.mDrawableHeightLeft = 0;
                drawables.mDrawableSizeLeft = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(drawableState);
                drawable3.copyBounds(rect);
                drawable3.setCallback(this);
                drawables.mDrawableSizeRight = rect.width();
                drawables.mDrawableHeightRight = rect.height();
            } else {
                drawables.mDrawableHeightRight = 0;
                drawables.mDrawableSizeRight = 0;
            }
            if (drawable2 != null) {
                drawable2.setState(drawableState);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                drawables.mDrawableSizeTop = rect.height();
                drawables.mDrawableWidthTop = rect.width();
            } else {
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(drawableState);
                drawable4.copyBounds(rect);
                drawable4.setCallback(this);
                drawables.mDrawableSizeBottom = rect.height();
                drawables.mDrawableWidthBottom = rect.width();
            } else {
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        }
        if (drawables != null) {
            drawables.mDrawableLeftInitial = drawable;
            drawables.mDrawableRightInitial = drawable3;
        }
        resetResolvedDrawables();
        resolveDrawables();
        applyCompoundDrawableTint();
        invalidate();
        requestLayout();
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        android.content.Context context = getContext();
        setCompoundDrawablesWithIntrinsicBounds(i != 0 ? context.getDrawable(i) : null, i2 != 0 ? context.getDrawable(i2) : null, i3 != 0 ? context.getDrawable(i3) : null, i4 != 0 ? context.getDrawable(i4) : null);
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablesWithIntrinsicBounds(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, android.graphics.drawable.Drawable drawable4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablesRelative(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, android.graphics.drawable.Drawable drawable4) {
        boolean z;
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            if (drawables.mShowing[0] != null) {
                drawables.mShowing[0].setCallback(null);
            }
            android.graphics.drawable.Drawable[] drawableArr = drawables.mShowing;
            drawables.mDrawableLeftInitial = null;
            drawableArr[0] = null;
            if (drawables.mShowing[2] != null) {
                drawables.mShowing[2].setCallback(null);
            }
            android.graphics.drawable.Drawable[] drawableArr2 = drawables.mShowing;
            drawables.mDrawableRightInitial = null;
            drawableArr2[2] = null;
            drawables.mDrawableHeightLeft = 0;
            drawables.mDrawableSizeLeft = 0;
            drawables.mDrawableHeightRight = 0;
            drawables.mDrawableSizeRight = 0;
        }
        if (drawable == null && drawable2 == null && drawable3 == null && drawable4 == null) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            if (drawables != null) {
                if (!drawables.hasMetadata()) {
                    this.mDrawables = null;
                } else {
                    if (drawables.mDrawableStart != null) {
                        drawables.mDrawableStart.setCallback(null);
                    }
                    drawables.mDrawableStart = null;
                    if (drawables.mShowing[1] != null) {
                        drawables.mShowing[1].setCallback(null);
                    }
                    drawables.mShowing[1] = null;
                    if (drawables.mDrawableEnd != null) {
                        drawables.mDrawableEnd.setCallback(null);
                    }
                    drawables.mDrawableEnd = null;
                    if (drawables.mShowing[3] != null) {
                        drawables.mShowing[3].setCallback(null);
                    }
                    drawables.mShowing[3] = null;
                    drawables.mDrawableHeightStart = 0;
                    drawables.mDrawableSizeStart = 0;
                    drawables.mDrawableHeightEnd = 0;
                    drawables.mDrawableSizeEnd = 0;
                    drawables.mDrawableWidthTop = 0;
                    drawables.mDrawableSizeTop = 0;
                    drawables.mDrawableWidthBottom = 0;
                    drawables.mDrawableSizeBottom = 0;
                }
            }
        } else {
            if (drawables == null) {
                drawables = new android.widget.TextView.Drawables(getContext());
                this.mDrawables = drawables;
            }
            this.mDrawables.mOverride = true;
            if (drawables.mDrawableStart != drawable && drawables.mDrawableStart != null) {
                drawables.mDrawableStart.setCallback(null);
            }
            drawables.mDrawableStart = drawable;
            if (drawables.mShowing[1] != drawable2 && drawables.mShowing[1] != null) {
                drawables.mShowing[1].setCallback(null);
            }
            drawables.mShowing[1] = drawable2;
            if (drawables.mDrawableEnd != drawable3 && drawables.mDrawableEnd != null) {
                drawables.mDrawableEnd.setCallback(null);
            }
            drawables.mDrawableEnd = drawable3;
            if (drawables.mShowing[3] != drawable4 && drawables.mShowing[3] != null) {
                drawables.mShowing[3].setCallback(null);
            }
            drawables.mShowing[3] = drawable4;
            android.graphics.Rect rect = drawables.mCompoundRect;
            int[] drawableState = getDrawableState();
            if (drawable != null) {
                drawable.setState(drawableState);
                drawable.copyBounds(rect);
                drawable.setCallback(this);
                drawables.mDrawableSizeStart = rect.width();
                drawables.mDrawableHeightStart = rect.height();
            } else {
                drawables.mDrawableHeightStart = 0;
                drawables.mDrawableSizeStart = 0;
            }
            if (drawable3 != null) {
                drawable3.setState(drawableState);
                drawable3.copyBounds(rect);
                drawable3.setCallback(this);
                drawables.mDrawableSizeEnd = rect.width();
                drawables.mDrawableHeightEnd = rect.height();
            } else {
                drawables.mDrawableHeightEnd = 0;
                drawables.mDrawableSizeEnd = 0;
            }
            if (drawable2 != null) {
                drawable2.setState(drawableState);
                drawable2.copyBounds(rect);
                drawable2.setCallback(this);
                drawables.mDrawableSizeTop = rect.height();
                drawables.mDrawableWidthTop = rect.width();
            } else {
                drawables.mDrawableWidthTop = 0;
                drawables.mDrawableSizeTop = 0;
            }
            if (drawable4 != null) {
                drawable4.setState(drawableState);
                drawable4.copyBounds(rect);
                drawable4.setCallback(this);
                drawables.mDrawableSizeBottom = rect.height();
                drawables.mDrawableWidthBottom = rect.width();
            } else {
                drawables.mDrawableWidthBottom = 0;
                drawables.mDrawableSizeBottom = 0;
            }
        }
        resetResolvedDrawables();
        resolveDrawables();
        invalidate();
        requestLayout();
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(int i, int i2, int i3, int i4) {
        android.content.Context context = getContext();
        setCompoundDrawablesRelativeWithIntrinsicBounds(i != 0 ? context.getDrawable(i) : null, i2 != 0 ? context.getDrawable(i2) : null, i3 != 0 ? context.getDrawable(i3) : null, i4 != 0 ? context.getDrawable(i4) : null);
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablesRelativeWithIntrinsicBounds(android.graphics.drawable.Drawable drawable, android.graphics.drawable.Drawable drawable2, android.graphics.drawable.Drawable drawable3, android.graphics.drawable.Drawable drawable4) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        if (drawable3 != null) {
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), drawable3.getIntrinsicHeight());
        }
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
        }
        if (drawable4 != null) {
            drawable4.setBounds(0, 0, drawable4.getIntrinsicWidth(), drawable4.getIntrinsicHeight());
        }
        setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    public android.graphics.drawable.Drawable[] getCompoundDrawables() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return (android.graphics.drawable.Drawable[]) drawables.mShowing.clone();
        }
        return new android.graphics.drawable.Drawable[]{null, null, null, null};
    }

    public android.graphics.drawable.Drawable[] getCompoundDrawablesRelative() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return new android.graphics.drawable.Drawable[]{drawables.mDrawableStart, drawables.mShowing[1], drawables.mDrawableEnd, drawables.mShowing[3]};
        }
        return new android.graphics.drawable.Drawable[]{null, null, null, null};
    }

    @android.view.RemotableViewMethod
    public void setCompoundDrawablePadding(int i) {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (i == 0) {
            if (drawables != null) {
                drawables.mDrawablePadding = i;
            }
        } else {
            if (drawables == null) {
                drawables = new android.widget.TextView.Drawables(getContext());
                this.mDrawables = drawables;
            }
            drawables.mDrawablePadding = i;
        }
        invalidate();
        requestLayout();
    }

    public int getCompoundDrawablePadding() {
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            return drawables.mDrawablePadding;
        }
        return 0;
    }

    public void setCompoundDrawableTintList(android.content.res.ColorStateList colorStateList) {
        if (this.mDrawables == null) {
            this.mDrawables = new android.widget.TextView.Drawables(getContext());
        }
        this.mDrawables.mTintList = colorStateList;
        this.mDrawables.mHasTint = true;
        applyCompoundDrawableTint();
    }

    public android.content.res.ColorStateList getCompoundDrawableTintList() {
        if (this.mDrawables != null) {
            return this.mDrawables.mTintList;
        }
        return null;
    }

    public void setCompoundDrawableTintMode(android.graphics.PorterDuff.Mode mode) {
        setCompoundDrawableTintBlendMode(mode != null ? android.graphics.BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setCompoundDrawableTintBlendMode(android.graphics.BlendMode blendMode) {
        if (this.mDrawables == null) {
            this.mDrawables = new android.widget.TextView.Drawables(getContext());
        }
        this.mDrawables.mBlendMode = blendMode;
        this.mDrawables.mHasTintMode = true;
        applyCompoundDrawableTint();
    }

    public android.graphics.PorterDuff.Mode getCompoundDrawableTintMode() {
        android.graphics.BlendMode compoundDrawableTintBlendMode = getCompoundDrawableTintBlendMode();
        if (compoundDrawableTintBlendMode != null) {
            return android.graphics.BlendMode.blendModeToPorterDuffMode(compoundDrawableTintBlendMode);
        }
        return null;
    }

    public android.graphics.BlendMode getCompoundDrawableTintBlendMode() {
        if (this.mDrawables != null) {
            return this.mDrawables.mBlendMode;
        }
        return null;
    }

    private void applyCompoundDrawableTint() {
        if (this.mDrawables == null) {
            return;
        }
        if (this.mDrawables.mHasTint || this.mDrawables.mHasTintMode) {
            android.content.res.ColorStateList colorStateList = this.mDrawables.mTintList;
            android.graphics.BlendMode blendMode = this.mDrawables.mBlendMode;
            boolean z = this.mDrawables.mHasTint;
            boolean z2 = this.mDrawables.mHasTintMode;
            int[] drawableState = getDrawableState();
            for (android.graphics.drawable.Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null && drawable != this.mDrawables.mDrawableError) {
                    drawable.mutate();
                    if (z) {
                        drawable.setTintList(colorStateList);
                    }
                    if (z2) {
                        drawable.setTintBlendMode(blendMode);
                    }
                    if (drawable.isStateful()) {
                        drawable.setState(drawableState);
                    }
                }
            }
        }
    }

    @Override // android.view.View
    public void setPadding(int i, int i2, int i3, int i4) {
        if (i != this.mPaddingLeft || i3 != this.mPaddingRight || i2 != this.mPaddingTop || i4 != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPadding(i, i2, i3, i4);
        invalidate();
    }

    @Override // android.view.View
    public void setPaddingRelative(int i, int i2, int i3, int i4) {
        if (i != getPaddingStart() || i3 != getPaddingEnd() || i2 != this.mPaddingTop || i4 != this.mPaddingBottom) {
            nullLayouts();
        }
        super.setPaddingRelative(i, i2, i3, i4);
        invalidate();
    }

    public void setFirstBaselineToTopHeight(int i) {
        int i2;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        android.graphics.Paint.FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            i2 = fontMetricsInt.top;
        } else {
            i2 = fontMetricsInt.ascent;
        }
        if (i > java.lang.Math.abs(i2)) {
            setPadding(getPaddingLeft(), i - (-i2), getPaddingRight(), getPaddingBottom());
        }
    }

    public void setLastBaselineToBottomHeight(int i) {
        int i2;
        com.android.internal.util.Preconditions.checkArgumentNonnegative(i);
        android.graphics.Paint.FontMetricsInt fontMetricsInt = getPaint().getFontMetricsInt();
        if (getIncludeFontPadding()) {
            i2 = fontMetricsInt.bottom;
        } else {
            i2 = fontMetricsInt.descent;
        }
        if (i > java.lang.Math.abs(i2)) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), i - i2);
        }
    }

    public int getFirstBaselineToTopHeight() {
        return getPaddingTop() - getPaint().getFontMetricsInt().top;
    }

    public int getLastBaselineToBottomHeight() {
        return getPaddingBottom() + getPaint().getFontMetricsInt().bottom;
    }

    public final int getAutoLinkMask() {
        return this.mAutoLinkMask;
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandle(android.graphics.drawable.Drawable drawable) {
        com.android.internal.util.Preconditions.checkNotNull(drawable, "The text select handle should not be null.");
        this.mTextSelectHandle = drawable;
        this.mTextSelectHandleRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandle(int i) {
        com.android.internal.util.Preconditions.checkArgument(i != 0, "The text select handle should be a valid drawable resource id.");
        setTextSelectHandle(this.mContext.getDrawable(i));
    }

    public android.graphics.drawable.Drawable getTextSelectHandle() {
        if (this.mTextSelectHandle == null && this.mTextSelectHandleRes != 0) {
            this.mTextSelectHandle = this.mContext.getDrawable(this.mTextSelectHandleRes);
        }
        return this.mTextSelectHandle;
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandleLeft(android.graphics.drawable.Drawable drawable) {
        com.android.internal.util.Preconditions.checkNotNull(drawable, "The left text select handle should not be null.");
        this.mTextSelectHandleLeft = drawable;
        this.mTextSelectHandleLeftRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandleLeft(int i) {
        com.android.internal.util.Preconditions.checkArgument(i != 0, "The text select left handle should be a valid drawable resource id.");
        setTextSelectHandleLeft(this.mContext.getDrawable(i));
    }

    public android.graphics.drawable.Drawable getTextSelectHandleLeft() {
        if (this.mTextSelectHandleLeft == null && this.mTextSelectHandleLeftRes != 0) {
            this.mTextSelectHandleLeft = this.mContext.getDrawable(this.mTextSelectHandleLeftRes);
        }
        return this.mTextSelectHandleLeft;
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandleRight(android.graphics.drawable.Drawable drawable) {
        com.android.internal.util.Preconditions.checkNotNull(drawable, "The right text select handle should not be null.");
        this.mTextSelectHandleRight = drawable;
        this.mTextSelectHandleRightRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadHandleDrawables(true);
        }
    }

    @android.view.RemotableViewMethod
    public void setTextSelectHandleRight(int i) {
        com.android.internal.util.Preconditions.checkArgument(i != 0, "The text select right handle should be a valid drawable resource id.");
        setTextSelectHandleRight(this.mContext.getDrawable(i));
    }

    public android.graphics.drawable.Drawable getTextSelectHandleRight() {
        if (this.mTextSelectHandleRight == null && this.mTextSelectHandleRightRes != 0) {
            this.mTextSelectHandleRight = this.mContext.getDrawable(this.mTextSelectHandleRightRes);
        }
        return this.mTextSelectHandleRight;
    }

    public void setTextCursorDrawable(android.graphics.drawable.Drawable drawable) {
        this.mCursorDrawable = drawable;
        this.mCursorDrawableRes = 0;
        if (this.mEditor != null) {
            this.mEditor.loadCursorDrawable();
        }
    }

    public void setTextCursorDrawable(int i) {
        setTextCursorDrawable(i != 0 ? this.mContext.getDrawable(i) : null);
    }

    public android.graphics.drawable.Drawable getTextCursorDrawable() {
        if (this.mCursorDrawable == null && this.mCursorDrawableRes != 0) {
            this.mCursorDrawable = this.mContext.getDrawable(this.mCursorDrawableRes);
        }
        return this.mCursorDrawable;
    }

    public void setTextAppearance(int i) {
        setTextAppearance(this.mContext, i);
    }

    @java.lang.Deprecated
    public void setTextAppearance(android.content.Context context, int i) {
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, android.R.styleable.TextAppearance);
        android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes = new android.widget.TextView.TextAppearanceAttributes();
        readTextAppearance(context, obtainStyledAttributes, textAppearanceAttributes, false);
        obtainStyledAttributes.recycle();
        applyTextAppearance(textAppearanceAttributes);
    }

    private static class TextAppearanceAttributes {
        boolean mAllCaps;
        boolean mElegant;
        boolean mFallbackLineSpacing;
        int mFocusedSearchResultHighlightColor;
        java.lang.String mFontFamily;
        boolean mFontFamilyExplicit;
        java.lang.String mFontFeatureSettings;
        android.graphics.Typeface mFontTypeface;
        java.lang.String mFontVariationSettings;
        int mFontWeight;
        boolean mHasElegant;
        boolean mHasFallbackLineSpacing;
        boolean mHasLetterSpacing;
        boolean mHasLineBreakStyle;
        boolean mHasLineBreakWordStyle;
        float mLetterSpacing;
        int mLineBreakStyle;
        int mLineBreakWordStyle;
        int mSearchResultHighlightColor;
        int mShadowColor;
        float mShadowDx;
        float mShadowDy;
        float mShadowRadius;
        android.content.res.ColorStateList mTextColor;
        int mTextColorHighlight;
        android.content.res.ColorStateList mTextColorHint;
        android.content.res.ColorStateList mTextColorLink;
        android.os.LocaleList mTextLocales;
        int mTextSize;
        int mTextSizeUnit;
        int mTextStyle;
        int mTypefaceIndex;

        private TextAppearanceAttributes() {
            this.mTextColorHighlight = 0;
            this.mSearchResultHighlightColor = 0;
            this.mFocusedSearchResultHighlightColor = 0;
            this.mTextColor = null;
            this.mTextColorHint = null;
            this.mTextColorLink = null;
            this.mTextSize = -1;
            this.mTextSizeUnit = -1;
            this.mTextLocales = null;
            this.mFontFamily = null;
            this.mFontTypeface = null;
            this.mFontFamilyExplicit = false;
            this.mTypefaceIndex = -1;
            this.mTextStyle = 0;
            this.mFontWeight = -1;
            this.mAllCaps = false;
            this.mShadowColor = 0;
            this.mShadowDx = 0.0f;
            this.mShadowDy = 0.0f;
            this.mShadowRadius = 0.0f;
            this.mHasElegant = false;
            this.mElegant = false;
            this.mHasFallbackLineSpacing = false;
            this.mFallbackLineSpacing = false;
            this.mHasLetterSpacing = false;
            this.mLetterSpacing = 0.0f;
            this.mFontFeatureSettings = null;
            this.mFontVariationSettings = null;
            this.mHasLineBreakStyle = false;
            this.mHasLineBreakWordStyle = false;
            this.mLineBreakStyle = 0;
            this.mLineBreakWordStyle = 0;
        }

        public java.lang.String toString() {
            return "TextAppearanceAttributes {\n    mTextColorHighlight:" + this.mTextColorHighlight + "\n    mSearchResultHighlightColor: " + this.mSearchResultHighlightColor + "\n    mFocusedSearchResultHighlightColor: " + this.mFocusedSearchResultHighlightColor + "\n    mTextColor:" + this.mTextColor + "\n    mTextColorHint:" + this.mTextColorHint + "\n    mTextColorLink:" + this.mTextColorLink + "\n    mTextSize:" + this.mTextSize + "\n    mTextSizeUnit:" + this.mTextSizeUnit + "\n    mTextLocales:" + this.mTextLocales + "\n    mFontFamily:" + this.mFontFamily + "\n    mFontTypeface:" + this.mFontTypeface + "\n    mFontFamilyExplicit:" + this.mFontFamilyExplicit + "\n    mTypefaceIndex:" + this.mTypefaceIndex + "\n    mTextStyle:" + this.mTextStyle + "\n    mFontWeight:" + this.mFontWeight + "\n    mAllCaps:" + this.mAllCaps + "\n    mShadowColor:" + this.mShadowColor + "\n    mShadowDx:" + this.mShadowDx + "\n    mShadowDy:" + this.mShadowDy + "\n    mShadowRadius:" + this.mShadowRadius + "\n    mHasElegant:" + this.mHasElegant + "\n    mElegant:" + this.mElegant + "\n    mHasFallbackLineSpacing:" + this.mHasFallbackLineSpacing + "\n    mFallbackLineSpacing:" + this.mFallbackLineSpacing + "\n    mHasLetterSpacing:" + this.mHasLetterSpacing + "\n    mLetterSpacing:" + this.mLetterSpacing + "\n    mFontFeatureSettings:" + this.mFontFeatureSettings + "\n    mFontVariationSettings:" + this.mFontVariationSettings + "\n    mHasLineBreakStyle:" + this.mHasLineBreakStyle + "\n    mHasLineBreakWordStyle:" + this.mHasLineBreakWordStyle + "\n    mLineBreakStyle:" + this.mLineBreakStyle + "\n    mLineBreakWordStyle:" + this.mLineBreakWordStyle + "\n}";
        }
    }

    private void readTextAppearance(android.content.Context context, android.content.res.TypedArray typedArray, android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes, boolean z) {
        int i;
        int i2;
        int indexCount = typedArray.getIndexCount();
        while (i < indexCount) {
            int index = typedArray.getIndex(i);
            if (z) {
                i2 = sAppearanceValues.get(index, -1);
                i = i2 == -1 ? i + 1 : 0;
            } else {
                i2 = index;
            }
            switch (i2) {
                case 0:
                    textAppearanceAttributes.mTextSize = typedArray.getDimensionPixelSize(index, textAppearanceAttributes.mTextSize);
                    textAppearanceAttributes.mTextSizeUnit = typedArray.peekValue(index).getComplexUnit();
                    break;
                case 1:
                    textAppearanceAttributes.mTypefaceIndex = typedArray.getInt(index, textAppearanceAttributes.mTypefaceIndex);
                    if (textAppearanceAttributes.mTypefaceIndex != -1 && !textAppearanceAttributes.mFontFamilyExplicit) {
                        textAppearanceAttributes.mFontFamily = null;
                        break;
                    }
                    break;
                case 2:
                    textAppearanceAttributes.mTextStyle = typedArray.getInt(index, textAppearanceAttributes.mTextStyle);
                    break;
                case 3:
                    textAppearanceAttributes.mTextColor = typedArray.getColorStateList(index);
                    break;
                case 4:
                    textAppearanceAttributes.mTextColorHighlight = typedArray.getColor(index, textAppearanceAttributes.mTextColorHighlight);
                    break;
                case 5:
                    textAppearanceAttributes.mTextColorHint = typedArray.getColorStateList(index);
                    break;
                case 6:
                    textAppearanceAttributes.mTextColorLink = typedArray.getColorStateList(index);
                    break;
                case 7:
                    textAppearanceAttributes.mShadowColor = typedArray.getInt(index, textAppearanceAttributes.mShadowColor);
                    break;
                case 8:
                    textAppearanceAttributes.mShadowDx = typedArray.getFloat(index, textAppearanceAttributes.mShadowDx);
                    break;
                case 9:
                    textAppearanceAttributes.mShadowDy = typedArray.getFloat(index, textAppearanceAttributes.mShadowDy);
                    break;
                case 10:
                    textAppearanceAttributes.mShadowRadius = typedArray.getFloat(index, textAppearanceAttributes.mShadowRadius);
                    break;
                case 11:
                    textAppearanceAttributes.mAllCaps = typedArray.getBoolean(index, textAppearanceAttributes.mAllCaps);
                    break;
                case 12:
                    if (!context.isRestricted() && context.canLoadUnsafeResources()) {
                        try {
                            textAppearanceAttributes.mFontTypeface = typedArray.getFont(index);
                        } catch (android.content.res.Resources.NotFoundException | java.lang.UnsupportedOperationException e) {
                        }
                    }
                    if (textAppearanceAttributes.mFontTypeface == null) {
                        textAppearanceAttributes.mFontFamily = typedArray.getString(index);
                    }
                    textAppearanceAttributes.mFontFamilyExplicit = true;
                    break;
                case 13:
                    textAppearanceAttributes.mHasElegant = true;
                    textAppearanceAttributes.mElegant = typedArray.getBoolean(index, textAppearanceAttributes.mElegant);
                    break;
                case 14:
                    textAppearanceAttributes.mHasLetterSpacing = true;
                    textAppearanceAttributes.mLetterSpacing = typedArray.getFloat(index, textAppearanceAttributes.mLetterSpacing);
                    break;
                case 15:
                    textAppearanceAttributes.mFontFeatureSettings = typedArray.getString(index);
                    break;
                case 16:
                    textAppearanceAttributes.mFontVariationSettings = typedArray.getString(index);
                    break;
                case 17:
                    textAppearanceAttributes.mHasFallbackLineSpacing = true;
                    textAppearanceAttributes.mFallbackLineSpacing = typedArray.getBoolean(index, textAppearanceAttributes.mFallbackLineSpacing);
                    break;
                case 18:
                    textAppearanceAttributes.mFontWeight = typedArray.getInt(index, textAppearanceAttributes.mFontWeight);
                    break;
                case 19:
                    java.lang.String string = typedArray.getString(index);
                    if (string == null) {
                        break;
                    } else {
                        android.os.LocaleList forLanguageTags = android.os.LocaleList.forLanguageTags(string);
                        if (!forLanguageTags.isEmpty()) {
                            textAppearanceAttributes.mTextLocales = forLanguageTags;
                            break;
                        } else {
                            break;
                        }
                    }
                case 20:
                    textAppearanceAttributes.mHasLineBreakStyle = true;
                    textAppearanceAttributes.mLineBreakStyle = typedArray.getInt(index, textAppearanceAttributes.mLineBreakStyle);
                    break;
                case 21:
                    textAppearanceAttributes.mHasLineBreakWordStyle = true;
                    textAppearanceAttributes.mLineBreakWordStyle = typedArray.getInt(index, textAppearanceAttributes.mLineBreakWordStyle);
                    break;
                case 22:
                    textAppearanceAttributes.mSearchResultHighlightColor = typedArray.getColor(index, textAppearanceAttributes.mSearchResultHighlightColor);
                    break;
                case 23:
                    textAppearanceAttributes.mFocusedSearchResultHighlightColor = typedArray.getColor(index, textAppearanceAttributes.mFocusedSearchResultHighlightColor);
                    break;
            }
        }
    }

    private void applyTextAppearance(android.widget.TextView.TextAppearanceAttributes textAppearanceAttributes) {
        if (textAppearanceAttributes.mTextColor != null) {
            setTextColor(textAppearanceAttributes.mTextColor);
        }
        if (textAppearanceAttributes.mTextColorHint != null) {
            setHintTextColor(textAppearanceAttributes.mTextColorHint);
        }
        if (textAppearanceAttributes.mTextColorLink != null) {
            setLinkTextColor(textAppearanceAttributes.mTextColorLink);
        }
        if (textAppearanceAttributes.mTextColorHighlight != 0) {
            setHighlightColor(textAppearanceAttributes.mTextColorHighlight);
        }
        if (textAppearanceAttributes.mSearchResultHighlightColor != 0) {
            setSearchResultHighlightColor(textAppearanceAttributes.mSearchResultHighlightColor);
        }
        if (textAppearanceAttributes.mFocusedSearchResultHighlightColor != 0) {
            setFocusedSearchResultHighlightColor(textAppearanceAttributes.mFocusedSearchResultHighlightColor);
        }
        if (textAppearanceAttributes.mTextSize != -1) {
            this.mTextSizeUnit = textAppearanceAttributes.mTextSizeUnit;
            setRawTextSize(textAppearanceAttributes.mTextSize, true);
        }
        if (textAppearanceAttributes.mTextLocales != null) {
            setTextLocales(textAppearanceAttributes.mTextLocales);
        }
        if (textAppearanceAttributes.mTypefaceIndex != -1 && !textAppearanceAttributes.mFontFamilyExplicit) {
            textAppearanceAttributes.mFontFamily = null;
        }
        setTypefaceFromAttrs(textAppearanceAttributes.mFontTypeface, textAppearanceAttributes.mFontFamily, textAppearanceAttributes.mTypefaceIndex, textAppearanceAttributes.mTextStyle, textAppearanceAttributes.mFontWeight);
        if (textAppearanceAttributes.mShadowColor != 0) {
            setShadowLayer(textAppearanceAttributes.mShadowRadius, textAppearanceAttributes.mShadowDx, textAppearanceAttributes.mShadowDy, textAppearanceAttributes.mShadowColor);
        }
        if (textAppearanceAttributes.mAllCaps) {
            setTransformationMethod(new android.text.method.AllCapsTransformationMethod(getContext()));
        }
        if (textAppearanceAttributes.mHasElegant) {
            setElegantTextHeight(textAppearanceAttributes.mElegant);
        }
        if (textAppearanceAttributes.mHasFallbackLineSpacing) {
            setFallbackLineSpacing(textAppearanceAttributes.mFallbackLineSpacing);
        }
        if (textAppearanceAttributes.mHasLetterSpacing) {
            setLetterSpacing(textAppearanceAttributes.mLetterSpacing);
        }
        if (textAppearanceAttributes.mFontFeatureSettings != null) {
            setFontFeatureSettings(textAppearanceAttributes.mFontFeatureSettings);
        }
        if (textAppearanceAttributes.mFontVariationSettings != null) {
            setFontVariationSettings(textAppearanceAttributes.mFontVariationSettings);
        }
        if (textAppearanceAttributes.mHasLineBreakStyle || textAppearanceAttributes.mHasLineBreakWordStyle) {
            updateLineBreakConfigFromTextAppearance(textAppearanceAttributes.mHasLineBreakStyle, textAppearanceAttributes.mHasLineBreakWordStyle, textAppearanceAttributes.mLineBreakStyle, textAppearanceAttributes.mLineBreakWordStyle);
        }
    }

    private void updateLineBreakConfigFromTextAppearance(boolean z, boolean z2, int i, int i2) {
        boolean z3;
        boolean z4 = true;
        if (z && this.mLineBreakStyle != i) {
            this.mLineBreakStyle = i;
            z3 = true;
        } else {
            z3 = false;
        }
        if (z2 && this.mLineBreakWordStyle != i2) {
            this.mLineBreakWordStyle = i2;
        } else {
            z4 = z3;
        }
        if (z4 && this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public java.util.Locale getTextLocale() {
        return this.mTextPaint.getTextLocale();
    }

    public android.os.LocaleList getTextLocales() {
        return this.mTextPaint.getTextLocales();
    }

    private void changeListenerLocaleTo(java.util.Locale locale) {
        android.text.method.KeyListener dateTimeKeyListener;
        if (!this.mListenerChanged && this.mEditor != null) {
            android.text.method.KeyListener keyListener = this.mEditor.mKeyListener;
            if (keyListener instanceof android.text.method.DigitsKeyListener) {
                dateTimeKeyListener = android.text.method.DigitsKeyListener.getInstance(locale, (android.text.method.DigitsKeyListener) keyListener);
            } else if (keyListener instanceof android.text.method.DateKeyListener) {
                dateTimeKeyListener = android.text.method.DateKeyListener.getInstance(locale);
            } else if (keyListener instanceof android.text.method.TimeKeyListener) {
                dateTimeKeyListener = android.text.method.TimeKeyListener.getInstance(locale);
            } else if (keyListener instanceof android.text.method.DateTimeKeyListener) {
                dateTimeKeyListener = android.text.method.DateTimeKeyListener.getInstance(locale);
            } else {
                return;
            }
            boolean isPasswordInputType = isPasswordInputType(this.mEditor.mInputType);
            setKeyListenerOnly(dateTimeKeyListener);
            setInputTypeFromEditor();
            if (isPasswordInputType) {
                int i = this.mEditor.mInputType & 15;
                if (i == 1) {
                    this.mEditor.mInputType |= 128;
                } else if (i == 2) {
                    this.mEditor.mInputType |= 16;
                }
            }
        }
    }

    public void setTextLocale(java.util.Locale locale) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocale(locale);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public void setTextLocales(android.os.LocaleList localeList) {
        this.mLocalesChanged = true;
        this.mTextPaint.setTextLocales(localeList);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    protected void onConfigurationChanged(android.content.res.Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (!this.mLocalesChanged) {
            this.mTextPaint.setTextLocales(android.os.LocaleList.getDefault());
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
        if (this.mFontWeightAdjustment != configuration.fontWeightAdjustment) {
            this.mFontWeightAdjustment = configuration.fontWeightAdjustment;
            setTypeface(getTypeface());
        }
        android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
        if (this.mLastOrientation != configuration.orientation && inputMethodManager != null && inputMethodManager.hasActiveInputConnection(this)) {
            inputMethodManager.restartInput(this);
        }
        this.mLastOrientation = configuration.orientation;
    }

    @android.view.ViewDebug.ExportedProperty(category = "text")
    public float getTextSize() {
        return this.mTextPaint.getTextSize();
    }

    @android.view.ViewDebug.ExportedProperty(category = "text")
    public float getScaledTextSize() {
        return this.mTextPaint.getTextSize() / this.mTextPaint.density;
    }

    @android.view.ViewDebug.ExportedProperty(category = "text", mapping = {@android.view.ViewDebug.IntToString(from = 0, to = android.database.sqlite.SQLiteDatabase.SYNC_MODE_NORMAL), @android.view.ViewDebug.IntToString(from = 1, to = "BOLD"), @android.view.ViewDebug.IntToString(from = 2, to = "ITALIC"), @android.view.ViewDebug.IntToString(from = 3, to = "BOLD_ITALIC")})
    public int getTypefaceStyle() {
        android.graphics.Typeface typeface = this.mTextPaint.getTypeface();
        if (typeface != null) {
            return typeface.getStyle();
        }
        return 0;
    }

    @android.view.RemotableViewMethod
    public void setTextSize(float f) {
        setTextSize(2, f);
    }

    public void setTextSize(int i, float f) {
        if (!isAutoSizeEnabled()) {
            setTextSizeInternal(i, f, true);
        }
    }

    private android.util.DisplayMetrics getDisplayMetricsOrSystem() {
        android.content.res.Resources resources;
        android.content.Context context = getContext();
        if (context == null) {
            resources = android.content.res.Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        return resources.getDisplayMetrics();
    }

    private void setTextSizeInternal(int i, float f, boolean z) {
        this.mTextSizeUnit = i;
        setRawTextSize(android.util.TypedValue.applyDimension(i, f, getDisplayMetricsOrSystem()), z);
    }

    private void setRawTextSize(float f, boolean z) {
        if (f != this.mTextPaint.getTextSize()) {
            this.mTextPaint.setTextSize(f);
            maybeRecalculateLineHeight();
            if (z && this.mLayout != null) {
                this.mNeedsAutoSizeText = false;
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public int getTextSizeUnit() {
        return this.mTextSizeUnit;
    }

    public float getTextScaleX() {
        return this.mTextPaint.getTextScaleX();
    }

    @android.view.RemotableViewMethod
    public void setTextScaleX(float f) {
        if (f != this.mTextPaint.getTextScaleX()) {
            this.mUserSetTextScaleX = true;
            this.mTextPaint.setTextScaleX(f);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setTypeface(android.graphics.Typeface typeface) {
        this.mOriginalTypeface = typeface;
        if (this.mFontWeightAdjustment != 0 && this.mFontWeightAdjustment != Integer.MAX_VALUE) {
            if (typeface == null) {
                typeface = android.graphics.Typeface.DEFAULT;
            } else {
                typeface = android.graphics.Typeface.create(typeface, java.lang.Math.min(java.lang.Math.max(typeface.getWeight() + this.mFontWeightAdjustment, 1), 1000), ((typeface != null ? typeface.getStyle() : 0) & 2) != 0);
            }
        }
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public android.graphics.Typeface getTypeface() {
        return this.mOriginalTypeface;
    }

    public void setElegantTextHeight(boolean z) {
        if (z != this.mTextPaint.isElegantTextHeight()) {
            this.mTextPaint.setElegantTextHeight(z);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setFallbackLineSpacing(boolean z) {
        int i;
        if (z) {
            if (android.app.compat.CompatChanges.isChangeEnabled(BORINGLAYOUT_FALLBACK_LINESPACING)) {
                i = 2;
            } else {
                i = 1;
            }
        } else {
            i = 0;
        }
        if (this.mUseFallbackLineSpacing != i) {
            this.mUseFallbackLineSpacing = i;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setUseBoundsForWidth(boolean z) {
        if (this.mUseBoundsForWidth != z) {
            this.mUseBoundsForWidth = z;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getUseBoundsForWidth() {
        return this.mUseBoundsForWidth;
    }

    public void setShiftDrawingOffsetForStartOverhang(boolean z) {
        if (this.mShiftDrawingOffsetForStartOverhang != z) {
            this.mShiftDrawingOffsetForStartOverhang = z;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getShiftDrawingOffsetForStartOverhang() {
        return this.mShiftDrawingOffsetForStartOverhang;
    }

    public void setMinimumFontMetrics(android.graphics.Paint.FontMetrics fontMetrics) {
        this.mMinimumFontMetrics = fontMetrics;
    }

    public android.graphics.Paint.FontMetrics getMinimumFontMetrics() {
        return this.mMinimumFontMetrics;
    }

    public boolean isLocalePreferredLineHeightForMinimumUsed() {
        return this.mUseLocalePreferredLineHeightForMinimum;
    }

    public void setLocalePreferredLineHeightForMinimumUsed(boolean z) {
        this.mUseLocalePreferredLineHeightForMinimum = z;
    }

    public boolean isFallbackLineSpacing() {
        return this.mUseFallbackLineSpacing != 0;
    }

    private boolean isFallbackLineSpacingForBoringLayout() {
        return this.mUseFallbackLineSpacing == 2;
    }

    boolean isFallbackLineSpacingForStaticLayout() {
        return this.mUseFallbackLineSpacing == 2 || this.mUseFallbackLineSpacing == 1;
    }

    public boolean isElegantTextHeight() {
        return this.mTextPaint.isElegantTextHeight();
    }

    public float getLetterSpacing() {
        return this.mTextPaint.getLetterSpacing();
    }

    @android.view.RemotableViewMethod
    public void setLetterSpacing(float f) {
        if (f != this.mTextPaint.getLetterSpacing()) {
            this.mTextPaint.setLetterSpacing(f);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public java.lang.String getFontFeatureSettings() {
        return this.mTextPaint.getFontFeatureSettings();
    }

    public java.lang.String getFontVariationSettings() {
        return this.mTextPaint.getFontVariationSettings();
    }

    public void setBreakStrategy(int i) {
        this.mBreakStrategy = i;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getBreakStrategy() {
        return this.mBreakStrategy;
    }

    public void setHyphenationFrequency(int i) {
        this.mHyphenationFrequency = i;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getHyphenationFrequency() {
        return this.mHyphenationFrequency;
    }

    public void setLineBreakStyle(int i) {
        if (this.mLineBreakStyle != i) {
            this.mLineBreakStyle = i;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setLineBreakWordStyle(int i) {
        if (this.mLineBreakWordStyle != i) {
            this.mLineBreakWordStyle = i;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public int getLineBreakStyle() {
        return this.mLineBreakStyle;
    }

    public int getLineBreakWordStyle() {
        return this.mLineBreakWordStyle;
    }

    public android.text.PrecomputedText.Params getTextMetricsParams() {
        return new android.text.PrecomputedText.Params(new android.text.TextPaint(this.mTextPaint), android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle), getTextDirectionHeuristic(), this.mBreakStrategy, this.mHyphenationFrequency);
    }

    public void setTextMetricsParams(android.text.PrecomputedText.Params params) {
        this.mTextPaint.set(params.getTextPaint());
        this.mUserSetTextScaleX = true;
        this.mTextDir = params.getTextDirection();
        this.mBreakStrategy = params.getBreakStrategy();
        this.mHyphenationFrequency = params.getHyphenationFrequency();
        android.graphics.text.LineBreakConfig lineBreakConfig = params.getLineBreakConfig();
        this.mLineBreakStyle = android.graphics.text.LineBreakConfig.getResolvedLineBreakStyle(lineBreakConfig);
        this.mLineBreakWordStyle = android.graphics.text.LineBreakConfig.getResolvedLineBreakWordStyle(lineBreakConfig);
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    @android.view.RemotableViewMethod
    public void setJustificationMode(int i) {
        this.mJustificationMode = i;
        if (this.mLayout != null) {
            nullLayouts();
            requestLayout();
            invalidate();
        }
    }

    public int getJustificationMode() {
        return this.mJustificationMode;
    }

    @android.view.RemotableViewMethod
    public void setFontFeatureSettings(java.lang.String str) {
        if (str != this.mTextPaint.getFontFeatureSettings()) {
            this.mTextPaint.setFontFeatureSettings(str);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    @android.view.RemotableViewMethod
    public boolean setFontVariationSettings(java.lang.String str) {
        java.lang.String fontVariationSettings = this.mTextPaint.getFontVariationSettings();
        if (str != fontVariationSettings) {
            if (str != null && str.equals(fontVariationSettings)) {
                return true;
            }
            boolean fontVariationSettings2 = this.mTextPaint.setFontVariationSettings(str);
            if (fontVariationSettings2 && this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
            return fontVariationSettings2;
        }
        return true;
    }

    @android.view.RemotableViewMethod
    public void setTextColor(int i) {
        this.mTextColor = android.content.res.ColorStateList.valueOf(i);
        updateTextColors();
    }

    @android.view.RemotableViewMethod
    public void setTextColor(android.content.res.ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new java.lang.NullPointerException();
        }
        this.mTextColor = colorStateList;
        updateTextColors();
    }

    public final android.content.res.ColorStateList getTextColors() {
        return this.mTextColor;
    }

    public final int getCurrentTextColor() {
        return this.mCurTextColor;
    }

    @android.view.RemotableViewMethod
    public void setHighlightColor(int i) {
        if (this.mHighlightColor != i) {
            this.mHighlightColor = i;
            invalidate();
        }
    }

    public int getHighlightColor() {
        return this.mHighlightColor;
    }

    @android.view.RemotableViewMethod
    public final void setShowSoftInputOnFocus(boolean z) {
        createEditorIfNeeded();
        this.mEditor.mShowSoftInputOnFocus = z;
    }

    public final boolean getShowSoftInputOnFocus() {
        return this.mEditor == null || this.mEditor.mShowSoftInputOnFocus;
    }

    public void setShadowLayer(float f, float f2, float f3, int i) {
        this.mTextPaint.setShadowLayer(f, f2, f3, i);
        this.mShadowRadius = f;
        this.mShadowDx = f2;
        this.mShadowDy = f3;
        this.mShadowColor = i;
        if (this.mEditor != null) {
            this.mEditor.invalidateTextDisplayList();
            this.mEditor.invalidateHandlesAndActionMode();
        }
        invalidate();
    }

    public float getShadowRadius() {
        return this.mShadowRadius;
    }

    public float getShadowDx() {
        return this.mShadowDx;
    }

    public float getShadowDy() {
        return this.mShadowDy;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public android.text.TextPaint getPaint() {
        return this.mTextPaint;
    }

    @android.view.RemotableViewMethod
    public final void setAutoLinkMask(int i) {
        this.mAutoLinkMask = i;
    }

    @android.view.RemotableViewMethod
    public final void setLinksClickable(boolean z) {
        this.mLinksClickable = z;
    }

    public final boolean getLinksClickable() {
        return this.mLinksClickable;
    }

    public android.text.style.URLSpan[] getUrls() {
        if (this.mText instanceof android.text.Spanned) {
            return (android.text.style.URLSpan[]) ((android.text.Spanned) this.mText).getSpans(0, this.mText.length(), android.text.style.URLSpan.class);
        }
        return new android.text.style.URLSpan[0];
    }

    @android.view.RemotableViewMethod
    public final void setHintTextColor(int i) {
        this.mHintTextColor = android.content.res.ColorStateList.valueOf(i);
        updateTextColors();
    }

    public final void setHintTextColor(android.content.res.ColorStateList colorStateList) {
        this.mHintTextColor = colorStateList;
        updateTextColors();
    }

    public final android.content.res.ColorStateList getHintTextColors() {
        return this.mHintTextColor;
    }

    public final int getCurrentHintTextColor() {
        return this.mHintTextColor != null ? this.mCurHintTextColor : this.mCurTextColor;
    }

    @android.view.RemotableViewMethod
    public final void setLinkTextColor(int i) {
        this.mLinkTextColor = android.content.res.ColorStateList.valueOf(i);
        updateTextColors();
    }

    public final void setLinkTextColor(android.content.res.ColorStateList colorStateList) {
        this.mLinkTextColor = colorStateList;
        updateTextColors();
    }

    public final android.content.res.ColorStateList getLinkTextColors() {
        return this.mLinkTextColor;
    }

    @android.view.RemotableViewMethod
    public void setGravity(int i) {
        boolean z;
        if ((i & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
            i |= android.view.Gravity.START;
        }
        if ((i & 112) == 0) {
            i |= 48;
        }
        if ((i & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == (8388615 & this.mGravity)) {
            z = false;
        } else {
            z = true;
        }
        if (i != this.mGravity) {
            invalidate();
        }
        this.mGravity = i;
        if (this.mLayout != null && z) {
            makeNewLayout(this.mLayout.getWidth(), this.mHintLayout != null ? this.mHintLayout.getWidth() : 0, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), true);
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public int getPaintFlags() {
        return this.mTextPaint.getFlags();
    }

    @android.view.RemotableViewMethod
    public void setPaintFlags(int i) {
        if (this.mTextPaint.getFlags() != i) {
            this.mTextPaint.setFlags(i);
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setHorizontallyScrolling(boolean z) {
        if (this.mHorizontallyScrolling != z) {
            this.mHorizontallyScrolling = z;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public final boolean isHorizontallyScrollable() {
        return this.mHorizontallyScrolling;
    }

    public boolean getHorizontallyScrolling() {
        return this.mHorizontallyScrolling;
    }

    @android.view.RemotableViewMethod
    public void setMinLines(int i) {
        this.mMinimum = i;
        this.mMinMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinLines() {
        if (this.mMinMode == 1) {
            return this.mMinimum;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMinHeight(int i) {
        this.mMinimum = i;
        this.mMinMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinHeight() {
        if (this.mMinMode == 2) {
            return this.mMinimum;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMaxLines(int i) {
        this.mMaximum = i;
        this.mMaxMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxLines() {
        if (this.mMaxMode == 1) {
            return this.mMaximum;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMaxHeight(int i) {
        this.mMaximum = i;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxHeight() {
        if (this.mMaxMode == 2) {
            return this.mMaximum;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setLines(int i) {
        this.mMinimum = i;
        this.mMaximum = i;
        this.mMinMode = 1;
        this.mMaxMode = 1;
        requestLayout();
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setHeight(int i) {
        this.mMinimum = i;
        this.mMaximum = i;
        this.mMinMode = 2;
        this.mMaxMode = 2;
        requestLayout();
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setMinEms(int i) {
        this.mMinWidth = i;
        this.mMinWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMinEms() {
        if (this.mMinWidthMode == 1) {
            return this.mMinWidth;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMinWidth(int i) {
        this.mMinWidth = i;
        this.mMinWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMinWidth() {
        if (this.mMinWidthMode == 2) {
            return this.mMinWidth;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMaxEms(int i) {
        this.mMaxWidth = i;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    public int getMaxEms() {
        if (this.mMaxWidthMode == 1) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public int getMaxWidth() {
        if (this.mMaxWidthMode == 2) {
            return this.mMaxWidth;
        }
        return -1;
    }

    @android.view.RemotableViewMethod
    public void setEms(int i) {
        this.mMinWidth = i;
        this.mMaxWidth = i;
        this.mMinWidthMode = 1;
        this.mMaxWidthMode = 1;
        requestLayout();
        invalidate();
    }

    @android.view.RemotableViewMethod
    public void setWidth(int i) {
        this.mMinWidth = i;
        this.mMaxWidth = i;
        this.mMinWidthMode = 2;
        this.mMaxWidthMode = 2;
        requestLayout();
        invalidate();
    }

    public void setLineSpacing(float f, float f2) {
        if (this.mSpacingAdd != f || this.mSpacingMult != f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public float getLineSpacingMultiplier() {
        return this.mSpacingMult;
    }

    public float getLineSpacingExtra() {
        return this.mSpacingAdd;
    }

    @android.view.RemotableViewMethod
    public void setLineHeight(int i) {
        setLineHeightPx(i);
    }

    private void setLineHeightPx(float f) {
        com.android.internal.util.Preconditions.checkArgumentNonNegative(f, "Expecting non-negative lineHeight while the input is " + f);
        float fontMetricsInt = getPaint().getFontMetricsInt(null);
        if (f != fontMetricsInt) {
            setLineSpacing(f - fontMetricsInt, 1.0f);
            this.mLineHeightComplexDimen = android.util.TypedValue.createComplexDimension(f, 0);
        }
    }

    @android.view.RemotableViewMethod
    public void setLineHeight(int i, float f) {
        android.util.DisplayMetrics displayMetricsOrSystem = getDisplayMetricsOrSystem();
        if (!android.content.res.FontScaleConverterFactory.isNonLinearFontScalingActive(getResources().getConfiguration().fontScale) || i != 2 || this.mTextSizeUnit != 2) {
            setLineHeightPx(android.util.TypedValue.applyDimension(i, f, displayMetricsOrSystem));
            this.mLineHeightComplexDimen = android.util.TypedValue.createComplexDimension(f, i);
        } else {
            float textSize = getTextSize();
            setLineHeightPx(textSize * (f / android.util.TypedValue.convertPixelsToDimension(2, textSize, displayMetricsOrSystem)));
            this.mLineHeightComplexDimen = android.util.TypedValue.createComplexDimension(f, i);
        }
    }

    private void maybeRecalculateLineHeight() {
        int unitFromComplexDimension;
        if (this.mLineHeightComplexDimen == 0 || (unitFromComplexDimension = android.util.TypedValue.getUnitFromComplexDimension(this.mLineHeightComplexDimen)) != 2) {
            return;
        }
        setLineHeight(unitFromComplexDimension, android.util.TypedValue.complexToFloat(this.mLineHeightComplexDimen));
    }

    public void setHighlights(android.text.Highlights highlights) {
        this.mHighlights = highlights;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public android.text.Highlights getHighlights() {
        return this.mHighlights;
    }

    public void setSearchResultHighlights(int... iArr) {
        if (iArr != null) {
            if (iArr.length % 2 == 1) {
                throw new java.lang.IllegalArgumentException("Flatten ranges must have even numbered elements");
            }
            for (int i = 0; i < iArr.length / 2; i++) {
                int i2 = i * 2;
                int i3 = iArr[i2];
                int i4 = iArr[i2 + 1];
                if (i3 > i4) {
                    throw new java.lang.IllegalArgumentException("Reverse range found in the flatten range: " + i3 + ", " + i4 + " at " + i + "-th range");
                }
            }
            this.mHighlightPathsBogus = true;
            this.mSearchResultHighlights = iArr;
            this.mFocusedSearchResultIndex = -1;
            invalidate();
            return;
        }
        this.mSearchResultHighlights = null;
        this.mHighlightPathsBogus = true;
    }

    public int[] getSearchResultHighlights() {
        return this.mSearchResultHighlights;
    }

    public void setFocusedSearchResultIndex(int i) {
        if (this.mSearchResultHighlights == null) {
            throw new java.lang.IllegalArgumentException("Search result range must be set beforehand.");
        }
        if (i < -1 || i >= this.mSearchResultHighlights.length / 2) {
            throw new java.lang.IllegalArgumentException("Focused index(" + i + ") must be larger than -1 and less than range count(" + (this.mSearchResultHighlights.length / 2) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mFocusedSearchResultIndex = i;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    public int getFocusedSearchResultIndex() {
        return this.mFocusedSearchResultIndex;
    }

    public void setSearchResultHighlightColor(int i) {
        this.mSearchResultHighlightColor = i;
    }

    public int getSearchResultHighlightColor() {
        return this.mSearchResultHighlightColor;
    }

    public void setFocusedSearchResultHighlightColor(int i) {
        this.mFocusedSearchResultHighlightColor = i;
    }

    public int getFocusedSearchResultHighlightColor() {
        return this.mFocusedSearchResultHighlightColor;
    }

    private void setSelectGesturePreviewHighlight(int i, int i2) {
        setGesturePreviewHighlight(i, i2, this.mHighlightColor);
    }

    private void setDeleteGesturePreviewHighlight(int i, int i2) {
        setGesturePreviewHighlight(i, i2, com.android.internal.graphics.ColorUtils.setAlphaComponent(this.mTextColor.getDefaultColor(), (int) (android.graphics.Color.alpha(r0) * 0.2f)));
    }

    private void setGesturePreviewHighlight(int i, int i2, int i3) {
        this.mGesturePreviewHighlightStart = i;
        this.mGesturePreviewHighlightEnd = i2;
        if (this.mGesturePreviewHighlightPaint == null) {
            this.mGesturePreviewHighlightPaint = new android.graphics.Paint();
            this.mGesturePreviewHighlightPaint.setStyle(android.graphics.Paint.Style.FILL);
        }
        this.mGesturePreviewHighlightPaint.setColor(i3);
        if (this.mEditor != null) {
            this.mEditor.hideCursorAndSpanControllers();
            this.mEditor.stopTextActionModeWithPreservingSelection();
        }
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void clearGesturePreviewHighlight() {
        this.mGesturePreviewHighlightStart = -1;
        this.mGesturePreviewHighlightEnd = -1;
        this.mHighlightPathsBogus = true;
        invalidate();
    }

    boolean hasGesturePreviewHighlight() {
        return this.mGesturePreviewHighlightStart >= 0;
    }

    public final void append(java.lang.CharSequence charSequence) {
        append(charSequence, 0, charSequence.length());
    }

    public void append(java.lang.CharSequence charSequence, int i, int i2) {
        if (!(this.mText instanceof android.text.Editable)) {
            setText(this.mText, android.widget.TextView.BufferType.EDITABLE);
        }
        ((android.text.Editable) this.mText).append(charSequence, i, i2);
        if (this.mAutoLinkMask != 0 && android.text.util.Linkify.addLinks(this.mSpannable, this.mAutoLinkMask) && this.mLinksClickable && !textCanBeSelected()) {
            setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0041, code lost:
    
        if (r6.mText.length() == 0) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void updateTextColors() {
        boolean z;
        int colorForState;
        int colorForState2;
        int[] drawableState = getDrawableState();
        int colorForState3 = this.mTextColor.getColorForState(drawableState, 0);
        boolean z2 = true;
        if (colorForState3 == this.mCurTextColor) {
            z = false;
        } else {
            this.mCurTextColor = colorForState3;
            z = true;
        }
        if (this.mLinkTextColor != null && (colorForState2 = this.mLinkTextColor.getColorForState(drawableState, 0)) != this.mTextPaint.linkColor) {
            this.mTextPaint.linkColor = colorForState2;
            z = true;
        }
        if (this.mHintTextColor != null && (colorForState = this.mHintTextColor.getColorForState(drawableState, 0)) != this.mCurHintTextColor) {
            this.mCurHintTextColor = colorForState;
        }
        z2 = z;
        if (z2) {
            if (this.mEditor != null) {
                this.mEditor.invalidateTextDisplayList();
            }
            invalidate();
        }
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if ((this.mTextColor != null && this.mTextColor.isStateful()) || ((this.mHintTextColor != null && this.mHintTextColor.isStateful()) || (this.mLinkTextColor != null && this.mLinkTextColor.isStateful()))) {
            updateTextColors();
        }
        if (this.mDrawables != null) {
            int[] drawableState = getDrawableState();
            for (android.graphics.drawable.Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null && drawable.isStateful() && drawable.setState(drawableState)) {
                    invalidateDrawable(drawable);
                }
            }
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mDrawables != null) {
            for (android.graphics.drawable.Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null) {
                    drawable.setHotspot(f, f2);
                }
            }
        }
    }

    @Override // android.view.View
    public android.os.Parcelable onSaveInstanceState() {
        int i;
        int i2;
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        boolean freezesText = getFreezesText();
        boolean z = false;
        if (this.mText == null) {
            i = -1;
            i2 = -1;
        } else {
            i = getSelectionStart();
            i2 = getSelectionEnd();
            if (i >= 0 || i2 >= 0) {
                z = true;
            }
        }
        if (freezesText || z) {
            android.widget.TextView.SavedState savedState = new android.widget.TextView.SavedState(onSaveInstanceState);
            if (freezesText) {
                if (this.mText instanceof android.text.Spanned) {
                    android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(this.mText);
                    if (this.mEditor != null) {
                        removeMisspelledSpans(spannableStringBuilder);
                        spannableStringBuilder.removeSpan(this.mEditor.mSuggestionRangeSpan);
                    }
                    savedState.text = spannableStringBuilder;
                } else {
                    savedState.text = this.mText.toString();
                }
            }
            if (z) {
                savedState.selStart = i;
                savedState.selEnd = i2;
            }
            if (isFocused() && i >= 0 && i2 >= 0) {
                savedState.frozenWithFocus = true;
            }
            savedState.error = getError();
            if (this.mEditor != null) {
                savedState.editorState = this.mEditor.saveInstanceState();
            }
            return savedState;
        }
        return onSaveInstanceState;
    }

    void removeMisspelledSpans(android.text.Spannable spannable) {
        android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) spannable.getSpans(0, spannable.length(), android.text.style.SuggestionSpan.class);
        for (int i = 0; i < suggestionSpanArr.length; i++) {
            int flags = suggestionSpanArr[i].getFlags();
            if ((flags & 1) != 0 && (flags & 2) != 0) {
                spannable.removeSpan(suggestionSpanArr[i]);
            }
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(android.os.Parcelable parcelable) {
        java.lang.String str;
        if (!(parcelable instanceof android.widget.TextView.SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.widget.TextView.SavedState savedState = (android.widget.TextView.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        if (savedState.text != null) {
            lambda$setTextAsync$0(savedState.text);
        }
        if (savedState.selStart >= 0 && savedState.selEnd >= 0 && this.mSpannable != null) {
            int length = this.mText.length();
            if (savedState.selStart > length || savedState.selEnd > length) {
                if (savedState.text == null) {
                    str = "";
                } else {
                    str = "(restored) ";
                }
                android.util.Log.e(LOG_TAG, "Saved cursor position " + savedState.selStart + "/" + savedState.selEnd + " out of range for " + str + "text " + ((java.lang.Object) this.mText));
            } else {
                android.text.Selection.setSelection(this.mSpannable, savedState.selStart, savedState.selEnd);
                if (savedState.frozenWithFocus) {
                    createEditorIfNeeded();
                    this.mEditor.mFrozenWithFocus = true;
                }
            }
        }
        if (savedState.error != null) {
            final java.lang.CharSequence charSequence = savedState.error;
            post(new java.lang.Runnable() { // from class: android.widget.TextView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (android.widget.TextView.this.mEditor == null || !android.widget.TextView.this.mEditor.mErrorWasChanged) {
                        android.widget.TextView.this.setError(charSequence);
                    }
                }
            });
        }
        if (savedState.editorState != null) {
            createEditorIfNeeded();
            this.mEditor.restoreInstanceState(savedState.editorState);
        }
    }

    @android.view.RemotableViewMethod
    public void setFreezesText(boolean z) {
        this.mFreezesText = z;
    }

    public boolean getFreezesText() {
        return this.mFreezesText;
    }

    public final void setEditableFactory(android.text.Editable.Factory factory) {
        this.mEditableFactory = factory;
        lambda$setTextAsync$0(this.mText);
    }

    public final void setSpannableFactory(android.text.Spannable.Factory factory) {
        this.mSpannableFactory = factory;
        lambda$setTextAsync$0(this.mText);
    }

    @android.view.RemotableViewMethod(asyncImpl = "setTextAsync")
    /* renamed from: setText, reason: merged with bridge method [inline-methods] */
    public final void lambda$setTextAsync$0(java.lang.CharSequence charSequence) {
        setText(charSequence, this.mBufferType);
    }

    public java.lang.Runnable setTextAsync(final java.lang.CharSequence charSequence) {
        return new java.lang.Runnable() { // from class: android.widget.TextView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                android.widget.TextView.this.lambda$setTextAsync$0(charSequence);
            }
        };
    }

    @android.view.RemotableViewMethod
    public final void setTextKeepState(java.lang.CharSequence charSequence) {
        setTextKeepState(charSequence, this.mBufferType);
    }

    public void setText(java.lang.CharSequence charSequence, android.widget.TextView.BufferType bufferType) {
        setText(charSequence, bufferType, true, 0);
        this.mCharWrapper = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x020e  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:128:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01e6 A[LOOP:1: B:96:0x01e4->B:97:0x01e6, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void setText(java.lang.CharSequence charSequence, android.widget.TextView.BufferType bufferType, boolean z, int i) {
        int i2;
        boolean z2;
        java.lang.CharSequence charSequence2;
        int i3;
        java.lang.CharSequence charSequence3;
        int length;
        android.text.Spannable spannable;
        android.widget.TextView.BufferType bufferType2 = bufferType;
        if (this.mEditor != null) {
            this.mEditor.beforeSetText();
        }
        this.mTextSetFromXmlOrResourceId = false;
        java.lang.CharSequence charSequence4 = charSequence == null ? "" : charSequence;
        java.lang.CharSequence charSequence5 = charSequence4;
        if (!isSuggestionsEnabled()) {
            charSequence5 = removeSuggestionSpans(charSequence4);
        }
        if (!this.mUserSetTextScaleX) {
            this.mTextPaint.setTextScaleX(1.0f);
        }
        if ((charSequence5 instanceof android.text.Spanned) && ((android.text.Spanned) charSequence5).getSpanStart(android.text.TextUtils.TruncateAt.MARQUEE) >= 0) {
            if (android.view.ViewConfiguration.get(this.mContext).isFadingMarqueeEnabled()) {
                setHorizontalFadingEdgeEnabled(true);
                this.mMarqueeFadeMode = 0;
            } else {
                setHorizontalFadingEdgeEnabled(false);
                this.mMarqueeFadeMode = 1;
            }
            setEllipsize(android.text.TextUtils.TruncateAt.MARQUEE);
        }
        int length2 = this.mFilters.length;
        int i4 = 0;
        java.lang.CharSequence charSequence6 = charSequence5;
        while (i4 < length2) {
            java.lang.CharSequence filter = this.mFilters[i4].filter(charSequence6, 0, charSequence6.length(), EMPTY_SPANNED, 0, 0);
            if (filter != null) {
                charSequence6 = filter;
            }
            i4++;
            charSequence6 = charSequence6;
        }
        if (z) {
            if (this.mText == null) {
                sendBeforeTextChanged("", 0, 0, charSequence6.length());
            } else {
                i2 = this.mText.length();
                sendBeforeTextChanged(this.mText, 0, i2, charSequence6.length());
                if (this.mListeners == null && this.mListeners.size() != 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                android.text.PrecomputedText precomputedText = !(charSequence6 instanceof android.text.PrecomputedText) ? (android.text.PrecomputedText) charSequence6 : null;
                if (bufferType2 != android.widget.TextView.BufferType.EDITABLE || getKeyListener() != null || z2) {
                    createEditorIfNeeded();
                    this.mEditor.forgetUndoRedo();
                    this.mEditor.scheduleRestartInputForSetText();
                    android.text.Editable newEditable = this.mEditableFactory.newEditable(charSequence6);
                    setFilters(newEditable, this.mFilters);
                    charSequence2 = newEditable;
                } else if (precomputedText == null) {
                    if (bufferType2 == android.widget.TextView.BufferType.SPANNABLE || this.mMovement != null) {
                        charSequence2 = this.mSpannableFactory.newSpannable(charSequence6);
                    } else {
                        boolean z3 = charSequence6 instanceof android.widget.TextView.CharWrapper;
                        charSequence2 = charSequence6;
                        if (!z3) {
                            charSequence2 = android.text.TextUtils.stringOrSpannedString(charSequence6);
                        }
                    }
                } else {
                    if (this.mTextDir == null) {
                        this.mTextDir = getTextDirectionHeuristic();
                    }
                    charSequence2 = charSequence6;
                    switch (precomputedText.getParams().checkResultUsable(getPaint(), this.mTextDir, this.mBreakStrategy, this.mHyphenationFrequency, android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle))) {
                        case 0:
                            throw new java.lang.IllegalArgumentException("PrecomputedText's Parameters don't match the parameters of this TextView.Consider using setTextMetricsParams(precomputedText.getParams()) to override the settings of this TextView: PrecomputedText: " + precomputedText.getParams() + "TextView: " + getTextMetricsParams());
                        case 1:
                            android.text.PrecomputedText.create(precomputedText, getTextMetricsParams());
                            charSequence2 = charSequence6;
                            break;
                    }
                }
                if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                    i3 = 0;
                } else {
                    i3 = com.android.internal.accessibility.util.AccessibilityUtils.textOrSpanChanged(charSequence2, this.mText);
                }
                charSequence3 = charSequence2;
                if (this.mAutoLinkMask != 0) {
                    if (bufferType2 == android.widget.TextView.BufferType.EDITABLE || (charSequence2 instanceof android.text.Spannable)) {
                        spannable = (android.text.Spannable) charSequence2;
                    } else {
                        spannable = this.mSpannableFactory.newSpannable(charSequence2);
                    }
                    charSequence3 = charSequence2;
                    if (android.text.util.Linkify.addLinks(spannable, this.mAutoLinkMask)) {
                        bufferType2 = bufferType2 == android.widget.TextView.BufferType.EDITABLE ? android.widget.TextView.BufferType.EDITABLE : android.widget.TextView.BufferType.SPANNABLE;
                        setTextInternal(spannable);
                        if (i3 == 0) {
                            i3 = 2;
                        }
                        if (this.mLinksClickable && !textCanBeSelected()) {
                            setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
                        }
                        charSequence3 = spannable;
                    }
                }
                this.mBufferType = bufferType2;
                setTextInternal(charSequence3);
                if (this.mTransformation == null) {
                    this.mTransformed = this.mTransformation.getTransformation(charSequence3, this);
                } else {
                    this.mTransformed = charSequence3;
                }
                if (this.mTransformed == null) {
                    this.mTransformed = "";
                }
                length = charSequence3.length();
                boolean z4 = this.mTransformed instanceof android.text.method.OffsetMapping;
                if ((charSequence3 instanceof android.text.Spannable) && (!this.mAllowTransformationLengthChange || z4)) {
                    android.text.Spannable spannable2 = (android.text.Spannable) charSequence3;
                    for (android.widget.TextView.ChangeWatcher changeWatcher : (android.widget.TextView.ChangeWatcher[]) spannable2.getSpans(0, spannable2.length(), android.widget.TextView.ChangeWatcher.class)) {
                        spannable2.removeSpan(changeWatcher);
                    }
                    if (this.mChangeWatcher == null) {
                        this.mChangeWatcher = new android.widget.TextView.ChangeWatcher();
                    }
                    spannable2.setSpan(this.mChangeWatcher, 0, length, 6553618);
                    if (this.mEditor != null) {
                        this.mEditor.addSpanWatchers(spannable2);
                    }
                    if (this.mTransformation != null) {
                        spannable2.setSpan(this.mTransformation, 0, length, ((z4 ? 200 : 0) << 16) | 18);
                    }
                    if (this.mMovement != null) {
                        this.mMovement.initialize(this, spannable2);
                        if (this.mEditor != null) {
                            this.mEditor.mSelectionMoved = false;
                        }
                    }
                }
                if (this.mLayout != null) {
                    checkForRelayout();
                }
                sendOnTextChanged(charSequence3, 0, i2, length);
                onTextChanged(charSequence3, 0, i2, length);
                this.mHideHint = false;
                if (i3 != 1) {
                    notifyViewAccessibilityStateChangedIfNeeded(2);
                } else if (i3 == 2) {
                    notifyViewAccessibilityStateChangedIfNeeded(0);
                }
                if (!z2) {
                    sendAfterTextChanged((android.text.Editable) charSequence3);
                } else {
                    notifyListeningManagersAfterTextChanged();
                }
                if (this.mEditor == null) {
                    this.mEditor.prepareCursorControllers();
                    this.mEditor.maybeFireScheduledRestartInputForSetText();
                    return;
                }
                return;
            }
        }
        i2 = i;
        if (this.mListeners == null) {
        }
        z2 = false;
        if (!(charSequence6 instanceof android.text.PrecomputedText)) {
        }
        if (bufferType2 != android.widget.TextView.BufferType.EDITABLE) {
        }
        createEditorIfNeeded();
        this.mEditor.forgetUndoRedo();
        this.mEditor.scheduleRestartInputForSetText();
        android.text.Editable newEditable2 = this.mEditableFactory.newEditable(charSequence6);
        setFilters(newEditable2, this.mFilters);
        charSequence2 = newEditable2;
        if (android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled()) {
        }
        charSequence3 = charSequence2;
        if (this.mAutoLinkMask != 0) {
        }
        this.mBufferType = bufferType2;
        setTextInternal(charSequence3);
        if (this.mTransformation == null) {
        }
        if (this.mTransformed == null) {
        }
        length = charSequence3.length();
        boolean z42 = this.mTransformed instanceof android.text.method.OffsetMapping;
        if (charSequence3 instanceof android.text.Spannable) {
            android.text.Spannable spannable22 = (android.text.Spannable) charSequence3;
            while (r13 < r12) {
            }
            if (this.mChangeWatcher == null) {
            }
            spannable22.setSpan(this.mChangeWatcher, 0, length, 6553618);
            if (this.mEditor != null) {
            }
            if (this.mTransformation != null) {
            }
            if (this.mMovement != null) {
            }
        }
        if (this.mLayout != null) {
        }
        sendOnTextChanged(charSequence3, 0, i2, length);
        onTextChanged(charSequence3, 0, i2, length);
        this.mHideHint = false;
        if (i3 != 1) {
        }
        if (!z2) {
        }
        if (this.mEditor == null) {
        }
    }

    public final void setText(char[] cArr, int i, int i2) {
        int i3;
        if (i < 0 || i2 < 0 || i + i2 > cArr.length) {
            throw new java.lang.IndexOutOfBoundsException(i + ", " + i2);
        }
        if (this.mText != null) {
            i3 = this.mText.length();
            sendBeforeTextChanged(this.mText, 0, i3, i2);
        } else {
            sendBeforeTextChanged("", 0, 0, i2);
            i3 = 0;
        }
        if (this.mCharWrapper == null) {
            this.mCharWrapper = new android.widget.TextView.CharWrapper(cArr, i, i2);
        } else {
            this.mCharWrapper.set(cArr, i, i2);
        }
        setText(this.mCharWrapper, this.mBufferType, false, i3);
    }

    public final void setTextKeepState(java.lang.CharSequence charSequence, android.widget.TextView.BufferType bufferType) {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        int length = charSequence.length();
        setText(charSequence, bufferType);
        if ((selectionStart >= 0 || selectionEnd >= 0) && this.mSpannable != null) {
            android.text.Selection.setSelection(this.mSpannable, java.lang.Math.max(0, java.lang.Math.min(selectionStart, length)), java.lang.Math.max(0, java.lang.Math.min(selectionEnd, length)));
        }
    }

    @android.view.RemotableViewMethod
    public final void setText(int i) {
        lambda$setTextAsync$0(getContext().getResources().getText(i));
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = i;
    }

    public final void setText(int i, android.widget.TextView.BufferType bufferType) {
        setText(getContext().getResources().getText(i), bufferType);
        this.mTextSetFromXmlOrResourceId = true;
        this.mTextId = i;
    }

    @android.view.RemotableViewMethod
    public final void setHint(java.lang.CharSequence charSequence) {
        setHintInternal(charSequence);
        if (this.mEditor != null && isInputMethodTarget()) {
            this.mEditor.reportExtractedText();
        }
    }

    private void setHintInternal(java.lang.CharSequence charSequence) {
        this.mHideHint = false;
        this.mHint = android.text.TextUtils.stringOrSpannedString(charSequence);
        if (this.mLayout != null) {
            checkForRelayout();
        }
        if (this.mText.length() == 0) {
            invalidate();
        }
        if (this.mEditor != null && this.mText.length() == 0 && this.mHint != null) {
            this.mEditor.invalidateTextDisplayList();
        }
    }

    @android.view.RemotableViewMethod
    public final void setHint(int i) {
        this.mHintId = i;
        setHint(getContext().getResources().getText(i));
    }

    @android.view.ViewDebug.CapturedViewProperty
    public java.lang.CharSequence getHint() {
        return this.mHint;
    }

    public void hideHint() {
        if (isShowingHint()) {
            this.mHideHint = true;
            invalidate();
        }
    }

    public boolean isSingleLine() {
        return this.mSingleLine;
    }

    private static boolean isMultilineInputType(int i) {
        return (i & 131087) == 131073;
    }

    java.lang.CharSequence removeSuggestionSpans(java.lang.CharSequence charSequence) {
        android.text.Spannable newSpannable;
        if (charSequence instanceof android.text.Spanned) {
            if (charSequence instanceof android.text.Spannable) {
                newSpannable = (android.text.Spannable) charSequence;
            } else {
                newSpannable = this.mSpannableFactory.newSpannable(charSequence);
            }
            android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) newSpannable.getSpans(0, charSequence.length(), android.text.style.SuggestionSpan.class);
            if (suggestionSpanArr.length == 0) {
                return charSequence;
            }
            for (android.text.style.SuggestionSpan suggestionSpan : suggestionSpanArr) {
                newSpannable.removeSpan(suggestionSpan);
            }
            return newSpannable;
        }
        return charSequence;
    }

    public void setInputType(int i) {
        boolean isPasswordInputType = isPasswordInputType(getInputType());
        boolean isVisiblePasswordInputType = isVisiblePasswordInputType(getInputType());
        boolean z = false;
        setInputType(i, false);
        boolean isPasswordInputType2 = isPasswordInputType(i);
        boolean isVisiblePasswordInputType2 = isVisiblePasswordInputType(i);
        if (isPasswordInputType2) {
            setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
            setTypefaceFromAttrs(null, null, 3, 0, -1);
        } else if (isVisiblePasswordInputType2) {
            if (this.mTransformation == android.text.method.PasswordTransformationMethod.getInstance()) {
                z = true;
            }
            setTypefaceFromAttrs(null, null, 3, 0, -1);
        } else if (isPasswordInputType || isVisiblePasswordInputType) {
            setTypefaceFromAttrs(null, null, -1, 0, -1);
            if (this.mTransformation == android.text.method.PasswordTransformationMethod.getInstance()) {
                z = true;
            }
        }
        boolean z2 = !isMultilineInputType(i);
        if (this.mSingleLine != z2 || z) {
            applySingleLine(z2, !isPasswordInputType2, true, true);
        }
        if (!isSuggestionsEnabled()) {
            setTextInternal(removeSuggestionSpans(this.mText));
        }
        android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
        if (inputMethodManager != null) {
            inputMethodManager.restartInput(this);
        }
    }

    boolean hasPasswordTransformationMethod() {
        return this.mTransformation instanceof android.text.method.PasswordTransformationMethod;
    }

    public boolean isAnyPasswordInputType() {
        int inputType = getInputType();
        return isPasswordInputType(inputType) || isVisiblePasswordInputType(inputType);
    }

    static boolean isPasswordInputType(int i) {
        int i2 = i & 4095;
        return i2 == 129 || i2 == 225 || i2 == 18;
    }

    private static boolean isVisiblePasswordInputType(int i) {
        return (i & 4095) == 145;
    }

    public void setRawInputType(int i) {
        if (i == 0 && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        this.mEditor.mInputType = i;
    }

    @Override // android.view.View
    public java.lang.String[] getAutofillHints() {
        java.lang.String[] autofillHints = super.getAutofillHints();
        if (isAnyPasswordInputType() && !com.android.internal.util.ArrayUtils.contains(autofillHints, android.view.View.AUTOFILL_HINT_PASSWORD_AUTO)) {
            return (java.lang.String[]) com.android.internal.util.ArrayUtils.appendElement(java.lang.String.class, autofillHints, android.view.View.AUTOFILL_HINT_PASSWORD_AUTO);
        }
        return autofillHints;
    }

    private java.util.Locale getCustomLocaleForKeyListenerOrNull() {
        android.os.LocaleList imeHintLocales;
        if (this.mUseInternationalizedInput && (imeHintLocales = getImeHintLocales()) != null) {
            return imeHintLocales.get(0);
        }
        return null;
    }

    private void setInputType(int i, boolean z) {
        android.text.method.KeyListener textKeyListener;
        android.text.method.TextKeyListener.Capitalize capitalize;
        int i2 = i & 15;
        if (i2 == 1) {
            boolean z2 = (32768 & i) != 0;
            if ((i & 4096) != 0) {
                capitalize = android.text.method.TextKeyListener.Capitalize.CHARACTERS;
            } else if ((i & 8192) != 0) {
                capitalize = android.text.method.TextKeyListener.Capitalize.WORDS;
            } else if ((i & 16384) != 0) {
                capitalize = android.text.method.TextKeyListener.Capitalize.SENTENCES;
            } else {
                capitalize = android.text.method.TextKeyListener.Capitalize.NONE;
            }
            textKeyListener = android.text.method.TextKeyListener.getInstance(z2, capitalize);
        } else if (i2 == 2) {
            java.util.Locale customLocaleForKeyListenerOrNull = getCustomLocaleForKeyListenerOrNull();
            android.text.method.DigitsKeyListener digitsKeyListener = android.text.method.DigitsKeyListener.getInstance(customLocaleForKeyListenerOrNull, (i & 4096) != 0, (i & 8192) != 0);
            if (customLocaleForKeyListenerOrNull != null) {
                int inputType = digitsKeyListener.getInputType();
                if ((inputType & 15) != 2) {
                    if ((i & 16) != 0) {
                        inputType |= 128;
                    }
                    i = inputType;
                }
            }
            textKeyListener = digitsKeyListener;
        } else if (i2 == 4) {
            java.util.Locale customLocaleForKeyListenerOrNull2 = getCustomLocaleForKeyListenerOrNull();
            switch (i & android.text.InputType.TYPE_MASK_VARIATION) {
                case 16:
                    textKeyListener = android.text.method.DateKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
                    break;
                case 32:
                    textKeyListener = android.text.method.TimeKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
                    break;
                default:
                    textKeyListener = android.text.method.DateTimeKeyListener.getInstance(customLocaleForKeyListenerOrNull2);
                    break;
            }
            if (this.mUseInternationalizedInput) {
                i = textKeyListener.getInputType();
            }
        } else if (i2 == 3) {
            textKeyListener = android.text.method.DialerKeyListener.getInstance();
        } else {
            textKeyListener = android.text.method.TextKeyListener.getInstance();
        }
        setRawInputType(i);
        this.mListenerChanged = false;
        if (z) {
            createEditorIfNeeded();
            this.mEditor.mKeyListener = textKeyListener;
        } else {
            setKeyListenerOnly(textKeyListener);
        }
    }

    public int getInputType() {
        if (this.mEditor == null) {
            return 0;
        }
        return this.mEditor.mInputType;
    }

    public void setImeOptions(int i) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeOptions = i;
    }

    public int getImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeOptions;
    }

    public void setImeActionLabel(java.lang.CharSequence charSequence, int i) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeActionLabel = charSequence;
        this.mEditor.mInputContentType.imeActionId = i;
    }

    public java.lang.CharSequence getImeActionLabel() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeActionLabel;
    }

    public int getImeActionId() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return 0;
        }
        return this.mEditor.mInputContentType.imeActionId;
    }

    public void setOnEditorActionListener(android.widget.TextView.OnEditorActionListener onEditorActionListener) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.onEditorActionListener = onEditorActionListener;
    }

    public void onEditorAction(int i) {
        android.widget.Editor.InputContentType inputContentType = this.mEditor == null ? null : this.mEditor.mInputContentType;
        if (inputContentType != null) {
            if (inputContentType.onEditorActionListener != null && inputContentType.onEditorActionListener.onEditorAction(this, i, null)) {
                return;
            }
            if (i == 5) {
                android.view.View focusSearch = focusSearch(2);
                if (focusSearch != null && !focusSearch.requestFocus(2)) {
                    throw new java.lang.IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (i == 7) {
                android.view.View focusSearch2 = focusSearch(1);
                if (focusSearch2 != null && !focusSearch2.requestFocus(1)) {
                    throw new java.lang.IllegalStateException("focus search returned a view that wasn't able to take focus!");
                }
                return;
            }
            if (i == 6) {
                android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromView(this, 0);
                    return;
                }
                return;
            }
        }
        android.view.ViewRootImpl viewRootImpl = getViewRootImpl();
        if (viewRootImpl != null) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            viewRootImpl.dispatchKeyFromIme(new android.view.KeyEvent(uptimeMillis, uptimeMillis, 0, 66, 0, 0, -1, 0, 22));
            viewRootImpl.dispatchKeyFromIme(new android.view.KeyEvent(android.os.SystemClock.uptimeMillis(), uptimeMillis, 1, 66, 0, 0, -1, 0, 22));
        }
    }

    public void setPrivateImeOptions(java.lang.String str) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.privateImeOptions = str;
    }

    public java.lang.String getPrivateImeOptions() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.privateImeOptions;
    }

    public void setInputExtras(int i) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        createEditorIfNeeded();
        android.content.res.XmlResourceParser xml = getResources().getXml(i);
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.extras = new android.os.Bundle();
        getResources().parseBundleExtras(xml, this.mEditor.mInputContentType.extras);
    }

    public android.os.Bundle getInputExtras(boolean z) {
        if (this.mEditor == null && !z) {
            return null;
        }
        createEditorIfNeeded();
        if (this.mEditor.mInputContentType == null) {
            if (!z) {
                return null;
            }
            this.mEditor.createInputContentTypeIfNeeded();
        }
        if (this.mEditor.mInputContentType.extras == null) {
            if (!z) {
                return null;
            }
            this.mEditor.mInputContentType.extras = new android.os.Bundle();
        }
        return this.mEditor.mInputContentType.extras;
    }

    public void setImeHintLocales(android.os.LocaleList localeList) {
        createEditorIfNeeded();
        this.mEditor.createInputContentTypeIfNeeded();
        this.mEditor.mInputContentType.imeHintLocales = localeList;
        if (this.mUseInternationalizedInput) {
            changeListenerLocaleTo(localeList == null ? null : localeList.get(0));
        }
    }

    public android.os.LocaleList getImeHintLocales() {
        if (this.mEditor == null || this.mEditor.mInputContentType == null) {
            return null;
        }
        return this.mEditor.mInputContentType.imeHintLocales;
    }

    public java.lang.CharSequence getError() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mError;
    }

    @android.view.RemotableViewMethod
    public void setError(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            setError(null, null);
            return;
        }
        android.graphics.drawable.Drawable drawable = getContext().getDrawable(com.android.internal.R.drawable.indicator_input_error);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        setError(charSequence, drawable);
    }

    public void setError(java.lang.CharSequence charSequence, android.graphics.drawable.Drawable drawable) {
        createEditorIfNeeded();
        this.mEditor.setError(charSequence, drawable);
        notifyViewAccessibilityStateChangedIfNeeded(3072);
    }

    @Override // android.view.View
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        boolean frame = super.setFrame(i, i2, i3, i4);
        if (this.mEditor != null) {
            this.mEditor.setFrame();
        }
        restartMarqueeIfNeeded();
        return frame;
    }

    private void restartMarqueeIfNeeded() {
        if (this.mRestartMarquee && this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE) {
            this.mRestartMarquee = false;
            startMarquee();
        }
    }

    public void setFilters(android.text.InputFilter[] inputFilterArr) {
        if (inputFilterArr == null) {
            throw new java.lang.IllegalArgumentException();
        }
        this.mFilters = inputFilterArr;
        if (this.mText instanceof android.text.Editable) {
            setFilters((android.text.Editable) this.mText, inputFilterArr);
        }
    }

    private void setFilters(android.text.Editable editable, android.text.InputFilter[] inputFilterArr) {
        if (this.mEditor != null) {
            int i = 1;
            boolean z = this.mEditor.mUndoInputFilter != null;
            boolean z2 = this.mEditor.mKeyListener instanceof android.text.InputFilter;
            int i2 = z ? 1 : 0;
            if (z2) {
                i2++;
            }
            if (i2 > 0) {
                android.text.InputFilter[] inputFilterArr2 = new android.text.InputFilter[inputFilterArr.length + i2];
                java.lang.System.arraycopy(inputFilterArr, 0, inputFilterArr2, 0, inputFilterArr.length);
                if (!z) {
                    i = 0;
                } else {
                    inputFilterArr2[inputFilterArr.length] = this.mEditor.mUndoInputFilter;
                }
                if (z2) {
                    inputFilterArr2[inputFilterArr.length + i] = (android.text.InputFilter) this.mEditor.mKeyListener;
                }
                editable.setFilters(inputFilterArr2);
                return;
            }
        }
        editable.setFilters(inputFilterArr);
    }

    public android.text.InputFilter[] getFilters() {
        return this.mFilters;
    }

    private int getBoxHeight(android.text.Layout layout) {
        int extendedPaddingTop;
        android.graphics.Insets opticalInsets = isLayoutModeOptical(this.mParent) ? getOpticalInsets() : android.graphics.Insets.NONE;
        if (layout == this.mHintLayout) {
            extendedPaddingTop = getCompoundPaddingTop() + getCompoundPaddingBottom();
        } else {
            extendedPaddingTop = getExtendedPaddingTop() + getExtendedPaddingBottom();
        }
        return (getMeasuredHeight() - extendedPaddingTop) + opticalInsets.top + opticalInsets.bottom;
    }

    int getVerticalOffset(boolean z) {
        int boxHeight;
        int height;
        int i = this.mGravity & 112;
        android.text.Layout layout = this.mLayout;
        if (!z && this.mText.length() == 0 && this.mHintLayout != null) {
            layout = this.mHintLayout;
        }
        if (i != 48 && (height = layout.getHeight()) < (boxHeight = getBoxHeight(layout))) {
            if (i == 80) {
                return boxHeight - height;
            }
            return (boxHeight - height) >> 1;
        }
        return 0;
    }

    private int getBottomVerticalOffset(boolean z) {
        int boxHeight;
        int height;
        int i = this.mGravity & 112;
        android.text.Layout layout = this.mLayout;
        if (!z && this.mText.length() == 0 && this.mHintLayout != null) {
            layout = this.mHintLayout;
        }
        if (i != 80 && (height = layout.getHeight()) < (boxHeight = getBoxHeight(layout))) {
            if (i == 48) {
                return boxHeight - height;
            }
            return (boxHeight - height) >> 1;
        }
        return 0;
    }

    void invalidateCursorPath() {
        if (this.mHighlightPathBogus) {
            invalidateCursor();
            return;
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop() + getVerticalOffset(true);
        if (this.mEditor.mDrawableForCursor == null) {
            synchronized (TEMP_RECTF) {
                float ceil = (float) java.lang.Math.ceil(this.mTextPaint.getStrokeWidth());
                if (ceil < 1.0f) {
                    ceil = 1.0f;
                }
                float f = ceil / 2.0f;
                this.mHighlightPath.computeBounds(TEMP_RECTF, false);
                float f2 = compoundPaddingLeft;
                float f3 = extendedPaddingTop;
                invalidate((int) java.lang.Math.floor((TEMP_RECTF.left + f2) - f), (int) java.lang.Math.floor((TEMP_RECTF.top + f3) - f), (int) java.lang.Math.ceil(f2 + TEMP_RECTF.right + f), (int) java.lang.Math.ceil(f3 + TEMP_RECTF.bottom + f));
            }
            return;
        }
        android.graphics.Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
        invalidate(bounds.left + compoundPaddingLeft, bounds.top + extendedPaddingTop, bounds.right + compoundPaddingLeft, bounds.bottom + extendedPaddingTop);
    }

    void invalidateCursor() {
        int selectionEnd = getSelectionEnd();
        invalidateCursor(selectionEnd, selectionEnd, selectionEnd);
    }

    private void invalidateCursor(int i, int i2, int i3) {
        if (i >= 0 || i2 >= 0 || i3 >= 0) {
            invalidateRegion(java.lang.Math.min(java.lang.Math.min(i, i2), i3), java.lang.Math.max(java.lang.Math.max(i, i2), i3), true);
        }
    }

    void invalidateRegion(int i, int i2, boolean z) {
        int lineForOffset;
        int width;
        if (this.mLayout == null) {
            invalidate();
            return;
        }
        int originalToTransformed = originalToTransformed(i, 1);
        int originalToTransformed2 = originalToTransformed(i2, 1);
        int lineForOffset2 = this.mLayout.getLineForOffset(originalToTransformed);
        int lineTop = this.mLayout.getLineTop(lineForOffset2);
        if (lineForOffset2 > 0) {
            lineTop -= this.mLayout.getLineDescent(lineForOffset2 - 1);
        }
        if (originalToTransformed == originalToTransformed2) {
            lineForOffset = lineForOffset2;
        } else {
            lineForOffset = this.mLayout.getLineForOffset(originalToTransformed2);
        }
        int lineBottom = this.mLayout.getLineBottom(lineForOffset);
        if (z && this.mEditor != null && this.mEditor.mDrawableForCursor != null) {
            android.graphics.Rect bounds = this.mEditor.mDrawableForCursor.getBounds();
            lineTop = java.lang.Math.min(lineTop, bounds.top);
            lineBottom = java.lang.Math.max(lineBottom, bounds.bottom);
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop() + getVerticalOffset(true);
        if (lineForOffset2 == lineForOffset && !z) {
            width = ((int) (this.mLayout.getPrimaryHorizontal(originalToTransformed2) + 1.0d)) + compoundPaddingLeft;
            compoundPaddingLeft = ((int) this.mLayout.getPrimaryHorizontal(originalToTransformed)) + compoundPaddingLeft;
        } else {
            width = getWidth() - getCompoundPaddingRight();
        }
        invalidate(this.mScrollX + compoundPaddingLeft, lineTop + extendedPaddingTop, this.mScrollX + width, extendedPaddingTop + lineBottom);
    }

    private void registerForPreDraw() {
        if (!this.mPreDrawRegistered) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawRegistered = true;
        }
    }

    private void unregisterForPreDraw() {
        getViewTreeObserver().removeOnPreDrawListener(this);
        this.mPreDrawRegistered = false;
        this.mPreDrawListenerDetached = false;
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        if (this.mLayout == null) {
            assumeLayout();
        }
        if (this.mMovement != null) {
            int selectionEnd = getSelectionEnd();
            if (this.mEditor != null && this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isSelectionStartDragged()) {
                selectionEnd = getSelectionStart();
            }
            if (selectionEnd < 0 && (this.mGravity & 112) == 80) {
                selectionEnd = this.mText.length();
            }
            if (selectionEnd >= 0) {
                bringPointIntoView(selectionEnd);
            }
        } else {
            bringTextIntoView();
        }
        if (this.mEditor != null && this.mEditor.mCreatedWithASelection) {
            this.mEditor.refreshTextActionMode();
            this.mEditor.mCreatedWithASelection = false;
        }
        unregisterForPreDraw();
        return true;
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mEditor != null) {
            this.mEditor.onAttachedToWindow();
        }
        if (this.mPreDrawListenerDetached) {
            getViewTreeObserver().addOnPreDrawListener(this);
            this.mPreDrawListenerDetached = false;
        }
    }

    @Override // android.view.View
    protected void onDetachedFromWindowInternal() {
        if (this.mPreDrawRegistered) {
            getViewTreeObserver().removeOnPreDrawListener(this);
            this.mPreDrawListenerDetached = true;
        }
        resetResolvedDrawables();
        if (this.mEditor != null) {
            this.mEditor.onDetachedFromWindow();
        }
        super.onDetachedFromWindowInternal();
    }

    @Override // android.view.View
    public void onScreenStateChanged(int i) {
        super.onScreenStateChanged(i);
        if (this.mEditor != null) {
            this.mEditor.onScreenStateChanged(i);
        }
    }

    @Override // android.view.View
    protected boolean isPaddingOffsetRequired() {
        return (this.mShadowRadius == 0.0f && this.mDrawables == null) ? false : true;
    }

    @Override // android.view.View
    protected int getLeftPaddingOffset() {
        return (getCompoundPaddingLeft() - this.mPaddingLeft) + ((int) java.lang.Math.min(0.0f, this.mShadowDx - this.mShadowRadius));
    }

    @Override // android.view.View
    protected int getTopPaddingOffset() {
        return (int) java.lang.Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
    }

    @Override // android.view.View
    protected int getBottomPaddingOffset() {
        return (int) java.lang.Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
    }

    @Override // android.view.View
    protected int getRightPaddingOffset() {
        return (-(getCompoundPaddingRight() - this.mPaddingRight)) + ((int) java.lang.Math.max(0.0f, this.mShadowDx + this.mShadowRadius));
    }

    @Override // android.view.View
    protected boolean verifyDrawable(android.graphics.drawable.Drawable drawable) {
        boolean verifyDrawable = super.verifyDrawable(drawable);
        if (!verifyDrawable && this.mDrawables != null) {
            for (android.graphics.drawable.Drawable drawable2 : this.mDrawables.mShowing) {
                if (drawable == drawable2) {
                    return true;
                }
            }
        }
        return verifyDrawable;
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mDrawables != null) {
            for (android.graphics.drawable.Drawable drawable : this.mDrawables.mShowing) {
                if (drawable != null) {
                    drawable.jumpToCurrentState();
                }
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(android.graphics.drawable.Drawable drawable) {
        boolean z = false;
        if (verifyDrawable(drawable)) {
            android.graphics.Rect bounds = drawable.getBounds();
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            android.widget.TextView.Drawables drawables = this.mDrawables;
            if (drawables != null) {
                if (drawable == drawables.mShowing[0]) {
                    int compoundPaddingTop = getCompoundPaddingTop();
                    int compoundPaddingBottom = ((this.mBottom - this.mTop) - getCompoundPaddingBottom()) - compoundPaddingTop;
                    i += this.mPaddingLeft;
                    i2 += compoundPaddingTop + ((compoundPaddingBottom - drawables.mDrawableHeightLeft) / 2);
                    z = true;
                } else if (drawable == drawables.mShowing[2]) {
                    int compoundPaddingTop2 = getCompoundPaddingTop();
                    int compoundPaddingBottom2 = ((this.mBottom - this.mTop) - getCompoundPaddingBottom()) - compoundPaddingTop2;
                    i += ((this.mRight - this.mLeft) - this.mPaddingRight) - drawables.mDrawableSizeRight;
                    i2 += compoundPaddingTop2 + ((compoundPaddingBottom2 - drawables.mDrawableHeightRight) / 2);
                    z = true;
                } else if (drawable == drawables.mShowing[1]) {
                    int compoundPaddingLeft = getCompoundPaddingLeft();
                    i += compoundPaddingLeft + (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft) - drawables.mDrawableWidthTop) / 2);
                    i2 += this.mPaddingTop;
                    z = true;
                } else if (drawable == drawables.mShowing[3]) {
                    int compoundPaddingLeft2 = getCompoundPaddingLeft();
                    i += compoundPaddingLeft2 + (((((this.mRight - this.mLeft) - getCompoundPaddingRight()) - compoundPaddingLeft2) - drawables.mDrawableWidthBottom) / 2);
                    i2 += ((this.mBottom - this.mTop) - this.mPaddingBottom) - drawables.mDrawableSizeBottom;
                    z = true;
                }
            }
            if (z) {
                invalidate(bounds.left + i, bounds.top + i2, bounds.right + i, bounds.bottom + i2);
            }
        }
        if (!z) {
            super.invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return ((getBackground() == null || getBackground().getCurrent() == null) && this.mSpannable == null && !hasSelection() && !isHorizontalFadingEdgeEnabled() && this.mShadowColor == 0) ? false : true;
    }

    public boolean isTextSelectable() {
        if (this.mEditor == null) {
            return false;
        }
        return this.mEditor.mTextIsSelectable;
    }

    public void setTextIsSelectable(boolean z) {
        if (z || this.mEditor != null) {
            createEditorIfNeeded();
            if (this.mEditor.mTextIsSelectable == z) {
                return;
            }
            this.mEditor.mTextIsSelectable = z;
            setFocusableInTouchMode(z);
            setFocusable(16);
            setClickable(z);
            setLongClickable(z);
            setMovementMethod(z ? android.text.method.ArrowKeyMovementMethod.getInstance() : null);
            setText(this.mText, z ? android.widget.TextView.BufferType.SPANNABLE : android.widget.TextView.BufferType.NORMAL);
            this.mEditor.prepareCursorControllers();
        }
    }

    @Override // android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState;
        if (this.mSingleLine) {
            onCreateDrawableState = super.onCreateDrawableState(i);
        } else {
            onCreateDrawableState = super.onCreateDrawableState(i + 1);
            mergeDrawableStates(onCreateDrawableState, MULTILINE_STATE_SET);
        }
        if (isTextSelectable()) {
            int length = onCreateDrawableState.length;
            for (int i2 = 0; i2 < length; i2++) {
                if (onCreateDrawableState[i2] == 16842919) {
                    int[] iArr = new int[length - 1];
                    java.lang.System.arraycopy(onCreateDrawableState, 0, iArr, 0, i2);
                    java.lang.System.arraycopy(onCreateDrawableState, i2 + 1, iArr, i2, (length - i2) - 1);
                    return iArr;
                }
            }
        }
        return onCreateDrawableState;
    }

    private void maybeUpdateHighlightPaths() {
        android.graphics.Path path;
        final android.graphics.Path path2;
        if (!this.mHighlightPathsBogus) {
            return;
        }
        if (this.mHighlightPaths != null) {
            this.mPathRecyclePool.addAll(this.mHighlightPaths);
            this.mHighlightPaths.clear();
            this.mHighlightPaints.clear();
        } else {
            this.mHighlightPaths = new java.util.ArrayList();
            this.mHighlightPaints = new java.util.ArrayList();
        }
        if (this.mHighlights != null) {
            for (int i = 0; i < this.mHighlights.getSize(); i++) {
                int[] ranges = this.mHighlights.getRanges(i);
                android.graphics.Paint paint = this.mHighlights.getPaint(i);
                if (this.mPathRecyclePool.isEmpty()) {
                    path2 = new android.graphics.Path();
                } else {
                    path2 = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                    this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                    path2.reset();
                }
                boolean z = false;
                for (int i2 = 0; i2 < ranges.length / 2; i2++) {
                    int i3 = i2 * 2;
                    int i4 = ranges[i3];
                    int i5 = ranges[i3 + 1];
                    if (i4 < i5) {
                        this.mLayout.getSelection(i4, i5, new android.text.Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda0
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i6) {
                                android.graphics.Path.this.addRect(f, f2, f3, f4, android.graphics.Path.Direction.CW);
                            }
                        });
                        z = true;
                    }
                }
                if (z) {
                    this.mHighlightPaths.add(path2);
                    this.mHighlightPaints.add(paint);
                }
            }
        }
        addSearchHighlightPaths();
        if (hasGesturePreviewHighlight()) {
            if (this.mPathRecyclePool.isEmpty()) {
                path = new android.graphics.Path();
            } else {
                path = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                path.reset();
            }
            this.mLayout.getSelectionPath(this.mGesturePreviewHighlightStart, this.mGesturePreviewHighlightEnd, path);
            this.mHighlightPaths.add(path);
            this.mHighlightPaints.add(this.mGesturePreviewHighlightPaint);
        }
        this.mHighlightPathsBogus = false;
    }

    private void addSearchHighlightPaths() {
        final android.graphics.Path path;
        final android.graphics.Path path2;
        if (this.mSearchResultHighlights != null) {
            if (this.mPathRecyclePool.isEmpty()) {
                path = new android.graphics.Path();
            } else {
                path = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                path.reset();
            }
            if (this.mFocusedSearchResultIndex == -1) {
                path2 = null;
            } else if (this.mPathRecyclePool.isEmpty()) {
                path2 = new android.graphics.Path();
            } else {
                path2 = this.mPathRecyclePool.get(this.mPathRecyclePool.size() - 1);
                this.mPathRecyclePool.remove(this.mPathRecyclePool.size() - 1);
                path2.reset();
            }
            boolean z = false;
            for (int i = 0; i < this.mSearchResultHighlights.length / 2; i++) {
                int i2 = i * 2;
                int i3 = this.mSearchResultHighlights[i2];
                int i4 = this.mSearchResultHighlights[i2 + 1];
                if (i3 < i4) {
                    if (i == this.mFocusedSearchResultIndex) {
                        this.mLayout.getSelection(i3, i4, new android.text.Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda6
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i5) {
                                android.graphics.Path.this.addRect(f, f2, f3, f4, android.graphics.Path.Direction.CW);
                            }
                        });
                    } else {
                        this.mLayout.getSelection(i3, i4, new android.text.Layout.SelectionRectangleConsumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda7
                            @Override // android.text.Layout.SelectionRectangleConsumer
                            public final void accept(float f, float f2, float f3, float f4, int i5) {
                                android.graphics.Path.this.addRect(f, f2, f3, f4, android.graphics.Path.Direction.CW);
                            }
                        });
                        z = true;
                    }
                }
            }
            if (z) {
                if (this.mSearchResultHighlightPaint == null) {
                    this.mSearchResultHighlightPaint = new android.graphics.Paint();
                }
                this.mSearchResultHighlightPaint.setColor(this.mSearchResultHighlightColor);
                this.mSearchResultHighlightPaint.setStyle(android.graphics.Paint.Style.FILL);
                this.mHighlightPaths.add(path);
                this.mHighlightPaints.add(this.mSearchResultHighlightPaint);
            }
            if (path2 != null) {
                if (this.mFocusedSearchResultHighlightPaint == null) {
                    this.mFocusedSearchResultHighlightPaint = new android.graphics.Paint();
                }
                this.mFocusedSearchResultHighlightPaint.setColor(this.mFocusedSearchResultHighlightColor);
                this.mFocusedSearchResultHighlightPaint.setStyle(android.graphics.Paint.Style.FILL);
                this.mHighlightPaths.add(path2);
                this.mHighlightPaints.add(this.mFocusedSearchResultHighlightPaint);
            }
        }
    }

    private android.graphics.Path getUpdatedHighlightPath() {
        android.graphics.Paint paint = this.mHighlightPaint;
        int selectionStartTransformed = getSelectionStartTransformed();
        int selectionEndTransformed = getSelectionEndTransformed();
        if (this.mMovement != null && ((isFocused() || isPressed()) && selectionStartTransformed >= 0)) {
            if (selectionStartTransformed == selectionEndTransformed) {
                if (this.mEditor != null && this.mEditor.shouldRenderCursor()) {
                    if (this.mHighlightPathBogus) {
                        if (this.mHighlightPath == null) {
                            this.mHighlightPath = new android.graphics.Path();
                        }
                        this.mHighlightPath.reset();
                        this.mLayout.getCursorPath(selectionStartTransformed, this.mHighlightPath, this.mText);
                        this.mEditor.updateCursorPosition();
                        this.mHighlightPathBogus = false;
                    }
                    paint.setColor(this.mCurTextColor);
                    paint.setStyle(android.graphics.Paint.Style.STROKE);
                    return this.mHighlightPath;
                }
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new android.graphics.Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                paint.setColor(this.mHighlightColor);
                paint.setStyle(android.graphics.Paint.Style.FILL);
                return this.mHighlightPath;
            }
        }
        return null;
    }

    public int getHorizontalOffsetForDrawables() {
        return 0;
    }

    @Override // android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        int i;
        int i2;
        restartMarqueeIfNeeded();
        super.onDraw(canvas);
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int compoundPaddingTop = getCompoundPaddingTop();
        int compoundPaddingRight = getCompoundPaddingRight();
        int compoundPaddingBottom = getCompoundPaddingBottom();
        int i3 = this.mScrollX;
        int i4 = this.mScrollY;
        int i5 = this.mRight;
        int i6 = this.mLeft;
        int i7 = this.mBottom;
        int i8 = this.mTop;
        boolean isLayoutRtl = isLayoutRtl();
        int horizontalOffsetForDrawables = getHorizontalOffsetForDrawables();
        int i9 = isLayoutRtl ? 0 : horizontalOffsetForDrawables;
        if (!isLayoutRtl) {
            horizontalOffsetForDrawables = 0;
        }
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            int i10 = ((i7 - i8) - compoundPaddingBottom) - compoundPaddingTop;
            int i11 = ((i5 - i6) - compoundPaddingRight) - compoundPaddingLeft;
            if (drawables.mShowing[0] != null) {
                canvas.save();
                canvas.translate(this.mPaddingLeft + i3 + i9, i4 + compoundPaddingTop + ((i10 - drawables.mDrawableHeightLeft) / 2));
                drawables.mShowing[0].draw(canvas);
                canvas.restore();
            }
            if (drawables.mShowing[2] != null) {
                canvas.save();
                canvas.translate(((((i3 + i5) - i6) - this.mPaddingRight) - drawables.mDrawableSizeRight) - horizontalOffsetForDrawables, i4 + compoundPaddingTop + ((i10 - drawables.mDrawableHeightRight) / 2));
                drawables.mShowing[2].draw(canvas);
                canvas.restore();
            }
            if (drawables.mShowing[1] != null) {
                canvas.save();
                canvas.translate(i3 + compoundPaddingLeft + ((i11 - drawables.mDrawableWidthTop) / 2), this.mPaddingTop + i4);
                drawables.mShowing[1].draw(canvas);
                canvas.restore();
            }
            if (drawables.mShowing[3] != null) {
                canvas.save();
                canvas.translate(i3 + compoundPaddingLeft + ((i11 - drawables.mDrawableWidthBottom) / 2), (((i4 + i7) - i8) - this.mPaddingBottom) - drawables.mDrawableSizeBottom);
                drawables.mShowing[3].draw(canvas);
                canvas.restore();
            }
        }
        int i12 = this.mCurTextColor;
        if (this.mLayout == null) {
            assumeLayout();
        }
        android.text.Layout layout = this.mLayout;
        if (this.mHint != null && !this.mHideHint && this.mText.length() == 0) {
            if (this.mHintTextColor != null) {
                i12 = this.mCurHintTextColor;
            }
            layout = this.mHintLayout;
        }
        this.mTextPaint.setColor(i12);
        this.mTextPaint.drawableState = getDrawableState();
        canvas.save();
        int extendedPaddingTop = getExtendedPaddingTop();
        int extendedPaddingBottom = getExtendedPaddingBottom();
        int height = this.mLayout.getHeight() - (((this.mBottom - this.mTop) - compoundPaddingBottom) - compoundPaddingTop);
        float f = compoundPaddingLeft + i3;
        float f2 = i4 == 0 ? 0.0f : extendedPaddingTop + i4;
        float compoundPaddingRight2 = ((i5 - i6) - getCompoundPaddingRight()) + i3;
        int i13 = (i7 - i8) + i4;
        if (i4 == height) {
            extendedPaddingBottom = 0;
        }
        float f3 = i13 - extendedPaddingBottom;
        if (this.mShadowRadius != 0.0f) {
            f += java.lang.Math.min(0.0f, this.mShadowDx - this.mShadowRadius);
            compoundPaddingRight2 += java.lang.Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            f2 += java.lang.Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            f3 += java.lang.Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
        }
        canvas.clipRect(f, f2, compoundPaddingRight2, f3);
        if ((this.mGravity & 112) != 48) {
            i = getVerticalOffset(false);
            i2 = getVerticalOffset(true);
        } else {
            i = 0;
            i2 = 0;
        }
        canvas.translate(compoundPaddingLeft, extendedPaddingTop + i);
        int absoluteGravity = android.view.Gravity.getAbsoluteGravity(this.mGravity, getLayoutDirection());
        if (isMarqueeFadeEnabled()) {
            if (!this.mSingleLine && getLineCount() == 1 && canMarquee() && (absoluteGravity & 7) != 3) {
                canvas.translate(layout.getParagraphDirection(0) * (this.mLayout.getLineRight(0) - ((this.mRight - this.mLeft) - (getCompoundPaddingLeft() + getCompoundPaddingRight()))), 0.0f);
            }
            if (this.mMarquee != null && this.mMarquee.isRunning()) {
                canvas.translate(layout.getParagraphDirection(0) * (-this.mMarquee.getScroll()), 0.0f);
            }
        }
        int i14 = i2 - i;
        maybeUpdateHighlightPaths();
        android.graphics.Path updatedHighlightPath = hasGesturePreviewHighlight() ? null : getUpdatedHighlightPath();
        if (this.mEditor != null) {
            this.mEditor.onDraw(canvas, layout, this.mHighlightPaths, this.mHighlightPaints, updatedHighlightPath, this.mHighlightPaint, i14);
        } else {
            layout.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, updatedHighlightPath, this.mHighlightPaint, i14);
        }
        if (this.mMarquee != null && this.mMarquee.shouldDrawGhost()) {
            canvas.translate(layout.getParagraphDirection(0) * this.mMarquee.getGhostOffset(), 0.0f);
            layout.draw(canvas, this.mHighlightPaths, this.mHighlightPaints, updatedHighlightPath, this.mHighlightPaint, i14);
        }
        canvas.restore();
    }

    @Override // android.view.View
    public void getFocusedRect(android.graphics.Rect rect) {
        if (this.mLayout == null) {
            super.getFocusedRect(rect);
            return;
        }
        int selectionEndTransformed = getSelectionEndTransformed();
        if (selectionEndTransformed < 0) {
            super.getFocusedRect(rect);
            return;
        }
        int selectionStartTransformed = getSelectionStartTransformed();
        if (selectionStartTransformed < 0 || selectionStartTransformed >= selectionEndTransformed) {
            int lineForOffset = this.mLayout.getLineForOffset(selectionEndTransformed);
            rect.top = this.mLayout.getLineTop(lineForOffset);
            rect.bottom = this.mLayout.getLineBottom(lineForOffset);
            rect.left = ((int) this.mLayout.getPrimaryHorizontal(selectionEndTransformed)) - 2;
            rect.right = rect.left + 4;
        } else {
            int lineForOffset2 = this.mLayout.getLineForOffset(selectionStartTransformed);
            int lineForOffset3 = this.mLayout.getLineForOffset(selectionEndTransformed);
            rect.top = this.mLayout.getLineTop(lineForOffset2);
            rect.bottom = this.mLayout.getLineBottom(lineForOffset3);
            if (lineForOffset2 == lineForOffset3) {
                rect.left = (int) this.mLayout.getPrimaryHorizontal(selectionStartTransformed);
                rect.right = (int) this.mLayout.getPrimaryHorizontal(selectionEndTransformed);
            } else {
                if (this.mHighlightPathBogus) {
                    if (this.mHighlightPath == null) {
                        this.mHighlightPath = new android.graphics.Path();
                    }
                    this.mHighlightPath.reset();
                    this.mLayout.getSelectionPath(selectionStartTransformed, selectionEndTransformed, this.mHighlightPath);
                    this.mHighlightPathBogus = false;
                }
                synchronized (TEMP_RECTF) {
                    this.mHighlightPath.computeBounds(TEMP_RECTF, true);
                    rect.left = ((int) TEMP_RECTF.left) - 1;
                    rect.right = ((int) TEMP_RECTF.right) + 1;
                }
            }
        }
        int compoundPaddingLeft = getCompoundPaddingLeft();
        int extendedPaddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            extendedPaddingTop += getVerticalOffset(false);
        }
        rect.offset(compoundPaddingLeft, extendedPaddingTop);
        rect.bottom += getExtendedPaddingBottom();
    }

    public int getLineCount() {
        if (this.mLayout != null) {
            return this.mLayout.getLineCount();
        }
        return 0;
    }

    public int getLineBounds(int i, android.graphics.Rect rect) {
        if (this.mLayout == null) {
            if (rect != null) {
                rect.set(0, 0, 0, 0);
            }
            return 0;
        }
        int lineBounds = this.mLayout.getLineBounds(i, rect);
        int extendedPaddingTop = getExtendedPaddingTop();
        if ((this.mGravity & 112) != 48) {
            extendedPaddingTop += getVerticalOffset(true);
        }
        if (rect != null) {
            rect.offset(getCompoundPaddingLeft(), extendedPaddingTop);
        }
        return lineBounds + extendedPaddingTop;
    }

    @Override // android.view.View
    public int getBaseline() {
        if (this.mLayout == null) {
            return super.getBaseline();
        }
        return getBaselineOffset() + this.mLayout.getLineBaseline(0);
    }

    int getBaselineOffset() {
        int i;
        if ((this.mGravity & 112) == 48) {
            i = 0;
        } else {
            i = getVerticalOffset(true);
        }
        if (isLayoutModeOptical(this.mParent)) {
            i -= getOpticalInsets().top;
        }
        return getExtendedPaddingTop() + i;
    }

    @Override // android.view.View
    protected int getFadeTop(boolean z) {
        if (this.mLayout == null) {
            return 0;
        }
        int verticalOffset = (this.mGravity & 112) != 48 ? getVerticalOffset(true) : 0;
        if (z) {
            verticalOffset += getTopPaddingOffset();
        }
        return getExtendedPaddingTop() + verticalOffset;
    }

    @Override // android.view.View
    protected int getFadeHeight(boolean z) {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return 0;
    }

    @Override // android.view.View
    public android.view.PointerIcon onResolvePointerIcon(android.view.MotionEvent motionEvent, int i) {
        if (motionEvent.isFromSource(8194)) {
            if (this.mSpannable != null && this.mLinksClickable) {
                int offsetForPosition = getOffsetForPosition(motionEvent.getX(i), motionEvent.getY(i));
                if (((android.text.style.ClickableSpan[]) this.mSpannable.getSpans(offsetForPosition, offsetForPosition, android.text.style.ClickableSpan.class)).length > 0) {
                    return android.view.PointerIcon.getSystemIcon(this.mContext, 1002);
                }
            }
            if (isTextSelectable() || isTextEditable()) {
                return android.view.PointerIcon.getSystemIcon(this.mContext, 1008);
            }
        }
        return super.onResolvePointerIcon(motionEvent, i);
    }

    @Override // android.view.View
    public boolean onKeyPreIme(int i, android.view.KeyEvent keyEvent) {
        if (i == 4 && handleBackInTextActionModeIfNeeded(keyEvent)) {
            return true;
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    public boolean handleBackInTextActionModeIfNeeded(android.view.KeyEvent keyEvent) {
        if (this.mEditor == null || this.mEditor.getTextActionMode() == null) {
            return false;
        }
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            android.view.KeyEvent.DispatcherState keyDispatcherState = getKeyDispatcherState();
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        }
        if (keyEvent.getAction() == 1) {
            android.view.KeyEvent.DispatcherState keyDispatcherState2 = getKeyDispatcherState();
            if (keyDispatcherState2 != null) {
                keyDispatcherState2.handleUpEvent(keyEvent);
            }
            if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                stopTextActionMode();
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        if (doKeyDown(i, keyEvent, null) == 0) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i, int i2, android.view.KeyEvent keyEvent) {
        android.view.KeyEvent changeAction = android.view.KeyEvent.changeAction(keyEvent, 0);
        int doKeyDown = doKeyDown(i, changeAction, keyEvent);
        if (doKeyDown == 0) {
            return super.onKeyMultiple(i, i2, keyEvent);
        }
        if (doKeyDown == -1) {
            return true;
        }
        int i3 = i2 - 1;
        android.view.KeyEvent changeAction2 = android.view.KeyEvent.changeAction(keyEvent, 1);
        if (doKeyDown == 1) {
            this.mEditor.mKeyListener.onKeyUp(this, (android.text.Editable) this.mText, i, changeAction2);
            while (true) {
                i3--;
                if (i3 <= 0) {
                    break;
                }
                this.mEditor.mKeyListener.onKeyDown(this, (android.text.Editable) this.mText, i, changeAction);
                this.mEditor.mKeyListener.onKeyUp(this, (android.text.Editable) this.mText, i, changeAction2);
            }
            hideErrorIfUnchanged();
        } else if (doKeyDown == 2) {
            this.mMovement.onKeyUp(this, this.mSpannable, i, changeAction2);
            while (true) {
                i3--;
                if (i3 <= 0) {
                    break;
                }
                this.mMovement.onKeyDown(this, this.mSpannable, i, changeAction);
                this.mMovement.onKeyUp(this, this.mSpannable, i, changeAction2);
            }
        }
        return true;
    }

    private boolean shouldAdvanceFocusOnEnter() {
        int i;
        if (getKeyListener() == null) {
            return false;
        }
        if (this.mSingleLine) {
            return true;
        }
        return this.mEditor != null && (this.mEditor.mInputType & 15) == 1 && ((i = this.mEditor.mInputType & android.text.InputType.TYPE_MASK_VARIATION) == 32 || i == 48);
    }

    private boolean isDirectionalNavigationKey(int i) {
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x0172  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int doKeyDown(int i, android.view.KeyEvent keyEvent, android.view.KeyEvent keyEvent2) {
        boolean z;
        boolean onKeyOther;
        boolean z2;
        if (!isEnabled()) {
            return 0;
        }
        if (keyEvent.getRepeatCount() == 0 && !android.view.KeyEvent.isModifierKey(i)) {
            this.mPreventDefaultMovement = false;
        }
        switch (i) {
            case 4:
                if (this.mEditor != null && this.mEditor.getTextActionMode() != null) {
                    stopTextActionMode();
                    return -1;
                }
                break;
            case 23:
                if (keyEvent.hasNoModifiers() && shouldAdvanceFocusOnEnter()) {
                    return 0;
                }
                break;
            case 61:
                if (keyEvent.hasNoModifiers() || keyEvent.hasModifiers(1)) {
                    return 0;
                }
                break;
            case 66:
            case 160:
                if (keyEvent.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), keyEvent)) {
                        this.mEditor.mInputContentType.enterDown = true;
                        return -1;
                    }
                    if ((keyEvent.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) {
                        return hasOnClickListeners() ? 0 : -1;
                    }
                }
                break;
            case 111:
                if (com.android.text.flags.Flags.escapeClearsFocus() && keyEvent.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.getTextActionMode() != null) {
                        stopTextActionMode();
                        return -1;
                    }
                    if (hasFocus()) {
                        clearFocusInternal(null, true, false);
                        android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
                        if (inputMethodManager != null) {
                            inputMethodManager.hideSoftInputFromView(this, 0);
                        }
                        return -1;
                    }
                }
                break;
            case 112:
                if (keyEvent.hasModifiers(1) && canCut() && onTextContextMenuItem(16908320)) {
                    return -1;
                }
                break;
            case 124:
                if (keyEvent.hasModifiers(4096) && canCopy()) {
                    if (onTextContextMenuItem(16908321)) {
                        return -1;
                    }
                } else if (keyEvent.hasModifiers(1) && canPaste() && onTextContextMenuItem(16908322)) {
                    return -1;
                }
                break;
            case 277:
                if (keyEvent.hasNoModifiers() && canCut() && onTextContextMenuItem(16908320)) {
                    return -1;
                }
                break;
            case 278:
                if (keyEvent.hasNoModifiers() && canCopy() && onTextContextMenuItem(16908321)) {
                    return -1;
                }
                break;
            case 279:
                if (keyEvent.hasNoModifiers() && canPaste() && onTextContextMenuItem(16908322)) {
                    return -1;
                }
                break;
        }
        if (this.mEditor != null && this.mEditor.mKeyListener != null) {
            if (keyEvent2 != null) {
                try {
                    beginBatchEdit();
                    onKeyOther = this.mEditor.mKeyListener.onKeyOther(this, (android.text.Editable) this.mText, keyEvent2);
                    hideErrorIfUnchanged();
                } catch (java.lang.AbstractMethodError e) {
                } finally {
                    endBatchEdit();
                }
                if (onKeyOther) {
                    return -1;
                }
                endBatchEdit();
                z2 = false;
                if (z2) {
                    beginBatchEdit();
                    boolean onKeyDown = this.mEditor.mKeyListener.onKeyDown(this, (android.text.Editable) this.mText, i, keyEvent);
                    endBatchEdit();
                    hideErrorIfUnchanged();
                    if (onKeyDown) {
                        return 1;
                    }
                }
            }
            z2 = true;
            if (z2) {
            }
        }
        if (this.mMovement != null && this.mLayout != null) {
            if (keyEvent2 != null) {
                if (this.mMovement.onKeyOther(this, this.mSpannable, keyEvent2)) {
                    return -1;
                }
                z = false;
                if (!z && this.mMovement.onKeyDown(this, this.mSpannable, i, keyEvent)) {
                    if (keyEvent.getRepeatCount() != 0 || android.view.KeyEvent.isModifierKey(i)) {
                        return 2;
                    }
                    this.mPreventDefaultMovement = true;
                    return 2;
                }
                if (keyEvent.getSource() == 257 && isDirectionalNavigationKey(i)) {
                    return -1;
                }
            }
            z = true;
            if (!z) {
            }
            if (keyEvent.getSource() == 257) {
                return -1;
            }
        }
        return (!this.mPreventDefaultMovement || android.view.KeyEvent.isModifierKey(i)) ? 0 : -1;
    }

    public void resetErrorChangedFlag() {
        if (this.mEditor != null) {
            this.mEditor.mErrorWasChanged = false;
        }
    }

    public void hideErrorIfUnchanged() {
        if (this.mEditor != null && this.mEditor.mError != null && !this.mEditor.mErrorWasChanged) {
            setError(null, null);
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        android.view.inputmethod.InputMethodManager inputMethodManager;
        if (!isEnabled()) {
            return super.onKeyUp(i, keyEvent);
        }
        if (!android.view.KeyEvent.isModifierKey(i)) {
            this.mPreventDefaultMovement = false;
        }
        switch (i) {
            case 23:
                if (keyEvent.hasNoModifiers() && !hasOnClickListeners() && this.mMovement != null && (this.mText instanceof android.text.Editable) && this.mLayout != null && onCheckIsTextEditor()) {
                    android.view.inputmethod.InputMethodManager inputMethodManager2 = getInputMethodManager();
                    viewClicked(inputMethodManager2);
                    if (inputMethodManager2 != null && getShowSoftInputOnFocus()) {
                        inputMethodManager2.showSoftInput(this, 0);
                    }
                }
                return super.onKeyUp(i, keyEvent);
            case 66:
            case 160:
                if (keyEvent.hasNoModifiers()) {
                    if (this.mEditor != null && this.mEditor.mInputContentType != null && this.mEditor.mInputContentType.onEditorActionListener != null && this.mEditor.mInputContentType.enterDown) {
                        this.mEditor.mInputContentType.enterDown = false;
                        if (this.mEditor.mInputContentType.onEditorActionListener.onEditorAction(this, getActionIdForEnterEvent(), keyEvent)) {
                            return true;
                        }
                    }
                    if (((keyEvent.getFlags() & 16) != 0 || shouldAdvanceFocusOnEnter()) && !hasOnClickListeners()) {
                        android.view.View focusSearch = focusSearch(130);
                        if (focusSearch != null) {
                            if (!focusSearch.requestFocus(130)) {
                                throw new java.lang.IllegalStateException("focus search returned a view that wasn't able to take focus!");
                            }
                            super.onKeyUp(i, keyEvent);
                            return true;
                        }
                        if ((keyEvent.getFlags() & 16) != 0 && (inputMethodManager = getInputMethodManager()) != null) {
                            inputMethodManager.hideSoftInputFromView(this, 0);
                        }
                    }
                    return super.onKeyUp(i, keyEvent);
                }
                break;
        }
        if (this.mEditor != null && this.mEditor.mKeyListener != null && this.mEditor.mKeyListener.onKeyUp(this, (android.text.Editable) this.mText, i, keyEvent)) {
            return true;
        }
        if (this.mMovement != null && this.mLayout != null && this.mMovement.onKeyUp(this, this.mSpannable, i, keyEvent)) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    private int getActionIdForEnterEvent() {
        if (!isSingleLine()) {
            return 0;
        }
        return getImeOptions() & 255;
    }

    @Override // android.view.View
    public boolean onCheckIsTextEditor() {
        return (this.mEditor == null || this.mEditor.mInputType == 0) ? false : true;
    }

    private boolean hasEditorInFocusSearchDirection(int i) {
        android.view.View focusSearch = focusSearch(i);
        return focusSearch != null && focusSearch.onCheckIsTextEditor();
    }

    @Override // android.view.View
    public android.view.inputmethod.InputConnection onCreateInputConnection(android.view.inputmethod.EditorInfo editorInfo) {
        if (onCheckIsTextEditor() && isEnabled()) {
            this.mEditor.createInputMethodStateIfNeeded();
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = 0;
            this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = 0;
            editorInfo.inputType = getInputType();
            if (this.mEditor.mInputContentType != null) {
                editorInfo.imeOptions = this.mEditor.mInputContentType.imeOptions;
                editorInfo.privateImeOptions = this.mEditor.mInputContentType.privateImeOptions;
                editorInfo.actionLabel = this.mEditor.mInputContentType.imeActionLabel;
                editorInfo.actionId = this.mEditor.mInputContentType.imeActionId;
                editorInfo.extras = this.mEditor.mInputContentType.extras;
                editorInfo.hintLocales = this.mEditor.mInputContentType.imeHintLocales;
            } else {
                editorInfo.imeOptions = 0;
                editorInfo.hintLocales = null;
            }
            if (hasEditorInFocusSearchDirection(130)) {
                editorInfo.imeOptions |= 134217728;
            }
            if (hasEditorInFocusSearchDirection(33)) {
                editorInfo.imeOptions |= 67108864;
            }
            if ((editorInfo.imeOptions & 255) == 0) {
                if ((editorInfo.imeOptions & 134217728) != 0) {
                    editorInfo.imeOptions |= 5;
                } else {
                    editorInfo.imeOptions |= 6;
                }
                if (!shouldAdvanceFocusOnEnter()) {
                    editorInfo.imeOptions |= 1073741824;
                }
            }
            if (getResources().getConfiguration().orientation == 1) {
                editorInfo.internalImeOptions |= 1;
            }
            if (isMultilineInputType(editorInfo.inputType)) {
                editorInfo.imeOptions |= 1073741824;
            }
            editorInfo.hintText = this.mHint;
            editorInfo.targetInputMethodUser = this.mTextOperationUser;
            if (this.mText instanceof android.text.Editable) {
                com.android.internal.inputmethod.EditableInputConnection editableInputConnection = new com.android.internal.inputmethod.EditableInputConnection(this);
                editorInfo.initialSelStart = getSelectionStart();
                editorInfo.initialSelEnd = getSelectionEnd();
                editorInfo.initialCapsMode = editableInputConnection.getCursorCapsMode(getInputType());
                editorInfo.setInitialSurroundingText(this.mText);
                editorInfo.contentMimeTypes = getReceiveContentMimeTypes();
                if (android.view.inputmethod.Flags.editorinfoHandwritingEnabled() && isAutoHandwritingEnabled()) {
                    editorInfo.setStylusHandwritingEnabled(true);
                }
                java.util.ArrayList arrayList = new java.util.ArrayList();
                arrayList.add(android.view.inputmethod.SelectGesture.class);
                arrayList.add(android.view.inputmethod.SelectRangeGesture.class);
                arrayList.add(android.view.inputmethod.DeleteGesture.class);
                arrayList.add(android.view.inputmethod.DeleteRangeGesture.class);
                arrayList.add(android.view.inputmethod.InsertGesture.class);
                arrayList.add(android.view.inputmethod.RemoveSpaceGesture.class);
                arrayList.add(android.view.inputmethod.JoinOrSplitGesture.class);
                arrayList.add(android.view.inputmethod.InsertModeGesture.class);
                editorInfo.setSupportedHandwritingGestures(arrayList);
                java.util.Set<java.lang.Class<? extends android.view.inputmethod.PreviewableHandwritingGesture>> arraySet = new android.util.ArraySet<>();
                arraySet.add(android.view.inputmethod.SelectGesture.class);
                arraySet.add(android.view.inputmethod.SelectRangeGesture.class);
                arraySet.add(android.view.inputmethod.DeleteGesture.class);
                arraySet.add(android.view.inputmethod.DeleteRangeGesture.class);
                editorInfo.setSupportedHandwritingGesturePreviews(arraySet);
                return editableInputConnection;
            }
        }
        return null;
    }

    public void onRequestCursorUpdatesInternal(int i, int i2) {
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoMode = i;
        this.mEditor.mInputMethodState.mUpdateCursorAnchorInfoFilter = i2;
        if ((i & 1) != 0 && !isInLayout()) {
            requestLayout();
        }
    }

    public boolean extractText(android.view.inputmethod.ExtractedTextRequest extractedTextRequest, android.view.inputmethod.ExtractedText extractedText) {
        createEditorIfNeeded();
        return this.mEditor.extractText(extractedTextRequest, extractedText);
    }

    static void removeParcelableSpans(android.text.Spannable spannable, int i, int i2) {
        java.lang.Object[] spans = spannable.getSpans(i, i2, android.text.ParcelableSpan.class);
        int length = spans.length;
        while (length > 0) {
            length--;
            spannable.removeSpan(spans[length]);
        }
    }

    public void setExtractedText(android.view.inputmethod.ExtractedText extractedText) {
        int i;
        android.text.Editable editableText = getEditableText();
        int i2 = 0;
        if (extractedText.text != null) {
            if (editableText == null) {
                setText(extractedText.text, android.widget.TextView.BufferType.EDITABLE);
            } else {
                int length = editableText.length();
                if (extractedText.partialStartOffset < 0) {
                    i = 0;
                } else {
                    length = editableText.length();
                    int i3 = extractedText.partialStartOffset;
                    if (i3 > length) {
                        i3 = length;
                    }
                    int i4 = extractedText.partialEndOffset;
                    i = i3;
                    if (i4 <= length) {
                        length = i4;
                    }
                }
                removeParcelableSpans(editableText, i, length);
                if (android.text.TextUtils.equals(editableText.subSequence(i, length), extractedText.text)) {
                    if (extractedText.text instanceof android.text.Spanned) {
                        android.text.TextUtils.copySpansFrom((android.text.Spanned) extractedText.text, 0, length - i, java.lang.Object.class, editableText, i);
                    }
                } else {
                    editableText.replace(i, length, extractedText.text);
                }
            }
        }
        android.text.Spannable spannable = (android.text.Spannable) getText();
        int length2 = spannable.length();
        int i5 = extractedText.selectionStart;
        if (i5 < 0) {
            i5 = 0;
        } else if (i5 > length2) {
            i5 = length2;
        }
        int i6 = extractedText.selectionEnd;
        if (i6 >= 0) {
            if (i6 <= length2) {
                i2 = i6;
            } else {
                i2 = length2;
            }
        }
        android.text.Selection.setSelection(spannable, i5, i2);
        if ((extractedText.flags & 2) != 0) {
            android.text.method.MetaKeyKeyListener.startSelecting(this, spannable);
        } else {
            android.text.method.MetaKeyKeyListener.stopSelecting(this, spannable);
        }
        setHintInternal(extractedText.hint);
    }

    public void setExtracting(android.view.inputmethod.ExtractedTextRequest extractedTextRequest) {
        if (this.mEditor.mInputMethodState != null) {
            this.mEditor.mInputMethodState.mExtractedTextRequest = extractedTextRequest;
        }
        this.mEditor.hideCursorAndSpanControllers();
        stopTextActionMode();
        if (this.mEditor.mSelectionModifierCursorController != null) {
            this.mEditor.mSelectionModifierCursorController.resetTouchOffsets();
        }
    }

    public void onCommitCompletion(android.view.inputmethod.CompletionInfo completionInfo) {
    }

    public void onCommitCorrection(android.view.inputmethod.CorrectionInfo correctionInfo) {
        if (this.mEditor != null) {
            this.mEditor.onCommitCorrection(correctionInfo);
        }
    }

    public void beginBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.beginBatchEdit();
        }
    }

    public void endBatchEdit() {
        if (this.mEditor != null) {
            this.mEditor.endBatchEdit();
        }
    }

    public void onBeginBatchEdit() {
    }

    public void onEndBatchEdit() {
    }

    public void onPerformSpellCheck() {
        if (this.mEditor != null && this.mEditor.mSpellChecker != null) {
            this.mEditor.mSpellChecker.onPerformSpellCheck();
        }
    }

    public boolean onPrivateIMECommand(java.lang.String str, android.os.Bundle bundle) {
        return false;
    }

    public boolean isOffsetMappingAvailable() {
        return this.mTransformation != null && (this.mTransformed instanceof android.text.method.OffsetMapping);
    }

    public boolean previewHandwritingGesture(android.view.inputmethod.PreviewableHandwritingGesture previewableHandwritingGesture, android.os.CancellationSignal cancellationSignal) {
        if (previewableHandwritingGesture instanceof android.view.inputmethod.SelectGesture) {
            performHandwritingSelectGesture((android.view.inputmethod.SelectGesture) previewableHandwritingGesture, true);
        } else if (previewableHandwritingGesture instanceof android.view.inputmethod.SelectRangeGesture) {
            performHandwritingSelectRangeGesture((android.view.inputmethod.SelectRangeGesture) previewableHandwritingGesture, true);
        } else if (previewableHandwritingGesture instanceof android.view.inputmethod.DeleteGesture) {
            performHandwritingDeleteGesture((android.view.inputmethod.DeleteGesture) previewableHandwritingGesture, true);
        } else if (previewableHandwritingGesture instanceof android.view.inputmethod.DeleteRangeGesture) {
            performHandwritingDeleteRangeGesture((android.view.inputmethod.DeleteRangeGesture) previewableHandwritingGesture, true);
        } else {
            return false;
        }
        if (cancellationSignal != null) {
            cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda4
                @Override // android.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    android.widget.TextView.this.clearGesturePreviewHighlight();
                }
            });
        }
        return true;
    }

    public int performHandwritingSelectGesture(android.view.inputmethod.SelectGesture selectGesture) {
        return performHandwritingSelectGesture(selectGesture, false);
    }

    private int performHandwritingSelectGesture(android.view.inputmethod.SelectGesture selectGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(selectGesture.getSelectionArea()), selectGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(selectGesture, z);
        }
        return performHandwritingSelectGesture(rangeForRect, z);
    }

    private int performHandwritingSelectGesture(int[] iArr, boolean z) {
        if (z) {
            setSelectGesturePreviewHighlight(iArr[0], iArr[1]);
        } else {
            android.text.Selection.setSelection(getEditableText(), iArr[0], iArr[1]);
            this.mEditor.startSelectionActionModeAsync(false);
        }
        return 1;
    }

    public int performHandwritingSelectRangeGesture(android.view.inputmethod.SelectRangeGesture selectRangeGesture) {
        return performHandwritingSelectRangeGesture(selectRangeGesture, false);
    }

    private int performHandwritingSelectRangeGesture(android.view.inputmethod.SelectRangeGesture selectRangeGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(selectRangeGesture.getSelectionStartArea()), selectRangeGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(selectRangeGesture, z);
        }
        int[] rangeForRect2 = getRangeForRect(convertFromScreenToContentCoordinates(selectRangeGesture.getSelectionEndArea()), selectRangeGesture.getGranularity());
        if (rangeForRect2 == null) {
            return handleGestureFailure(selectRangeGesture, z);
        }
        return performHandwritingSelectGesture(new int[]{java.lang.Math.min(rangeForRect[0], rangeForRect2[0]), java.lang.Math.max(rangeForRect[1], rangeForRect2[1])}, z);
    }

    public int performHandwritingDeleteGesture(android.view.inputmethod.DeleteGesture deleteGesture) {
        return performHandwritingDeleteGesture(deleteGesture, false);
    }

    private int performHandwritingDeleteGesture(android.view.inputmethod.DeleteGesture deleteGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(deleteGesture.getDeletionArea()), deleteGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(deleteGesture, z);
        }
        return performHandwritingDeleteGesture(rangeForRect, deleteGesture.getGranularity(), z);
    }

    private int performHandwritingDeleteGesture(int[] iArr, int i, boolean z) {
        if (z) {
            setDeleteGesturePreviewHighlight(iArr[0], iArr[1]);
        } else {
            if (i == 1) {
                iArr = adjustHandwritingDeleteGestureRange(iArr);
            }
            android.text.Selection.setSelection(getEditableText(), iArr[0]);
            getEditableText().delete(iArr[0], iArr[1]);
        }
        return 1;
    }

    public int performHandwritingDeleteRangeGesture(android.view.inputmethod.DeleteRangeGesture deleteRangeGesture) {
        return performHandwritingDeleteRangeGesture(deleteRangeGesture, false);
    }

    private int performHandwritingDeleteRangeGesture(android.view.inputmethod.DeleteRangeGesture deleteRangeGesture, boolean z) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        int[] rangeForRect = getRangeForRect(convertFromScreenToContentCoordinates(deleteRangeGesture.getDeletionStartArea()), deleteRangeGesture.getGranularity());
        if (rangeForRect == null) {
            return handleGestureFailure(deleteRangeGesture, z);
        }
        int[] rangeForRect2 = getRangeForRect(convertFromScreenToContentCoordinates(deleteRangeGesture.getDeletionEndArea()), deleteRangeGesture.getGranularity());
        if (rangeForRect2 == null) {
            return handleGestureFailure(deleteRangeGesture, z);
        }
        return performHandwritingDeleteGesture(new int[]{java.lang.Math.min(rangeForRect[0], rangeForRect2[0]), java.lang.Math.max(rangeForRect[1], rangeForRect2[1])}, deleteRangeGesture.getGranularity(), z);
    }

    private int[] adjustHandwritingDeleteGestureRange(int[] iArr) {
        int i;
        int i2 = iArr[0];
        int i3 = iArr[1];
        int i4 = 10;
        if (i2 <= 0) {
            i = 10;
        } else {
            i = java.lang.Character.codePointBefore(this.mText, i2);
        }
        if (i3 < this.mText.length()) {
            i4 = java.lang.Character.codePointAt(this.mText, i3);
        }
        if (android.text.TextUtils.isWhitespaceExceptNewline(i) && (android.text.TextUtils.isWhitespace(i4) || android.text.TextUtils.isPunctuation(i4))) {
            do {
                i2 -= java.lang.Character.charCount(i);
                if (i2 == 0) {
                    break;
                }
                i = java.lang.Character.codePointBefore(this.mText, i2);
            } while (android.text.TextUtils.isWhitespaceExceptNewline(i));
            return new int[]{i2, i3};
        }
        if (android.text.TextUtils.isWhitespaceExceptNewline(i4) && (android.text.TextUtils.isWhitespace(i) || android.text.TextUtils.isPunctuation(i))) {
            do {
                i3 += java.lang.Character.charCount(i4);
                if (i3 == this.mText.length()) {
                    break;
                }
                i4 = java.lang.Character.codePointAt(this.mText, i3);
            } while (android.text.TextUtils.isWhitespaceExceptNewline(i4));
            return new int[]{i2, i3};
        }
        return iArr;
    }

    public int performHandwritingInsertGesture(android.view.inputmethod.InsertGesture insertGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        android.graphics.PointF convertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(insertGesture.getInsertionPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(convertFromScreenToContentCoordinates);
        if (lineForHandwritingGesture == -1) {
            return handleGestureFailure(insertGesture);
        }
        return tryInsertTextForHandwritingGesture(this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, convertFromScreenToContentCoordinates.x), insertGesture.getTextToInsert(), insertGesture);
    }

    public int performHandwritingRemoveSpaceGesture(android.view.inputmethod.RemoveSpaceGesture removeSpaceGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        android.graphics.PointF convertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(removeSpaceGesture.getStartPoint());
        android.graphics.PointF convertFromScreenToContentCoordinates2 = convertFromScreenToContentCoordinates(removeSpaceGesture.getEndPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(convertFromScreenToContentCoordinates);
        int lineForHandwritingGesture2 = getLineForHandwritingGesture(convertFromScreenToContentCoordinates2);
        if (lineForHandwritingGesture == -1) {
            if (lineForHandwritingGesture2 == -1) {
                return handleGestureFailure(removeSpaceGesture);
            }
        } else {
            if (lineForHandwritingGesture2 != -1) {
                lineForHandwritingGesture = java.lang.Math.min(lineForHandwritingGesture, lineForHandwritingGesture2);
            }
            lineForHandwritingGesture2 = lineForHandwritingGesture;
        }
        float lineTop = (this.mLayout.getLineTop(lineForHandwritingGesture2) + this.mLayout.getLineBottom(lineForHandwritingGesture2, false)) / 2.0f;
        int[] rangeForRect = this.mLayout.getRangeForRect(new android.graphics.RectF(java.lang.Math.min(convertFromScreenToContentCoordinates.x, convertFromScreenToContentCoordinates2.x), lineTop + 0.1f, java.lang.Math.max(convertFromScreenToContentCoordinates.x, convertFromScreenToContentCoordinates2.x), lineTop - 0.1f), new android.text.GraphemeClusterSegmentFinder(this.mText, this.mTextPaint), android.text.Layout.INCLUSION_STRATEGY_ANY_OVERLAP);
        if (rangeForRect == null) {
            return handleGestureFailure(removeSpaceGesture);
        }
        int i = rangeForRect[0];
        int i2 = rangeForRect[1];
        java.util.regex.Pattern whitespacePattern = getWhitespacePattern();
        java.util.regex.Matcher matcher = whitespacePattern.matcher(this.mText.subSequence(i, i2));
        int i3 = -1;
        while (matcher.find()) {
            i3 = matcher.start() + i;
            getEditableText().delete(i3, i + matcher.end());
            i2 -= matcher.end() - matcher.start();
            if (i3 == i2) {
                break;
            }
            matcher = whitespacePattern.matcher(this.mText.subSequence(i3, i2));
            i = i3;
        }
        if (i3 == -1) {
            return handleGestureFailure(removeSpaceGesture);
        }
        android.text.Selection.setSelection(getEditableText(), i3);
        return 1;
    }

    public int performHandwritingJoinOrSplitGesture(android.view.inputmethod.JoinOrSplitGesture joinOrSplitGesture) {
        if (isOffsetMappingAvailable()) {
            return 3;
        }
        android.graphics.PointF convertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(joinOrSplitGesture.getJoinOrSplitPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(convertFromScreenToContentCoordinates);
        if (lineForHandwritingGesture == -1) {
            return handleGestureFailure(joinOrSplitGesture);
        }
        int offsetForHorizontal = this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, convertFromScreenToContentCoordinates.x);
        if (this.mLayout.isLevelBoundary(offsetForHorizontal)) {
            return handleGestureFailure(joinOrSplitGesture);
        }
        int i = offsetForHorizontal;
        while (i > 0) {
            int codePointBefore = java.lang.Character.codePointBefore(this.mText, i);
            if (!android.text.TextUtils.isWhitespace(codePointBefore)) {
                break;
            }
            i -= java.lang.Character.charCount(codePointBefore);
        }
        while (offsetForHorizontal < this.mText.length()) {
            int codePointAt = java.lang.Character.codePointAt(this.mText, offsetForHorizontal);
            if (!android.text.TextUtils.isWhitespace(codePointAt)) {
                break;
            }
            offsetForHorizontal += java.lang.Character.charCount(codePointAt);
        }
        if (i < offsetForHorizontal) {
            android.text.Selection.setSelection(getEditableText(), i);
            getEditableText().delete(i, offsetForHorizontal);
            return 1;
        }
        return tryInsertTextForHandwritingGesture(i, " ", joinOrSplitGesture);
    }

    public int performHandwritingInsertModeGesture(android.view.inputmethod.InsertModeGesture insertModeGesture) {
        android.graphics.PointF convertFromScreenToContentCoordinates = convertFromScreenToContentCoordinates(insertModeGesture.getInsertionPoint());
        int lineForHandwritingGesture = getLineForHandwritingGesture(convertFromScreenToContentCoordinates);
        android.os.CancellationSignal cancellationSignal = insertModeGesture.getCancellationSignal();
        if (lineForHandwritingGesture == -1 || cancellationSignal == null) {
            return handleGestureFailure(insertModeGesture);
        }
        if (!this.mEditor.enterInsertMode(this.mLayout.getOffsetForHorizontal(lineForHandwritingGesture, convertFromScreenToContentCoordinates.x))) {
            return 3;
        }
        cancellationSignal.setOnCancelListener(new android.os.CancellationSignal.OnCancelListener() { // from class: android.widget.TextView$$ExternalSyntheticLambda5
            @Override // android.os.CancellationSignal.OnCancelListener
            public final void onCancel() {
                android.widget.TextView.this.lambda$performHandwritingInsertModeGesture$4();
            }
        });
        return 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performHandwritingInsertModeGesture$4() {
        this.mEditor.exitInsertMode();
    }

    private int handleGestureFailure(android.view.inputmethod.HandwritingGesture handwritingGesture) {
        return handleGestureFailure(handwritingGesture, false);
    }

    private int handleGestureFailure(android.view.inputmethod.HandwritingGesture handwritingGesture, boolean z) {
        clearGesturePreviewHighlight();
        if (!z && !android.text.TextUtils.isEmpty(handwritingGesture.getFallbackText())) {
            getEditableText().replace(getSelectionStart(), getSelectionEnd(), handwritingGesture.getFallbackText());
            return 5;
        }
        return 3;
    }

    private int getLineForHandwritingGesture(android.graphics.PointF pointF) {
        int lineForVertical = this.mLayout.getLineForVertical((int) pointF.y);
        int scaledHandwritingGestureLineMargin = android.view.ViewConfiguration.get(this.mContext).getScaledHandwritingGestureLineMargin();
        if (lineForVertical < this.mLayout.getLineCount() - 1 && pointF.y > this.mLayout.getLineBottom(lineForVertical) - scaledHandwritingGestureLineMargin && pointF.y > (this.mLayout.getLineBottom(lineForVertical, false) + this.mLayout.getLineBottom(lineForVertical)) / 2.0f) {
            lineForVertical++;
        } else if (pointF.y < this.mLayout.getLineTop(lineForVertical) - scaledHandwritingGestureLineMargin || pointF.y > this.mLayout.getLineBottom(lineForVertical, false) + scaledHandwritingGestureLineMargin) {
            return -1;
        }
        if (pointF.x < (-scaledHandwritingGestureLineMargin) || pointF.x > this.mLayout.getWidth() + scaledHandwritingGestureLineMargin) {
            return -1;
        }
        return lineForVertical;
    }

    private int[] getRangeForRect(android.graphics.RectF rectF, int i) {
        android.text.SegmentFinder graphemeClusterSegmentFinder;
        if (i == 1) {
            android.text.method.WordIterator wordIterator = getWordIterator();
            wordIterator.setCharSequence(this.mText, 0, this.mText.length());
            graphemeClusterSegmentFinder = new android.text.WordSegmentFinder(this.mText, wordIterator);
        } else {
            graphemeClusterSegmentFinder = new android.text.GraphemeClusterSegmentFinder(this.mText, this.mTextPaint);
        }
        return this.mLayout.getRangeForRect(rectF, graphemeClusterSegmentFinder, android.text.Layout.INCLUSION_STRATEGY_CONTAINS_CENTER);
    }

    private int tryInsertTextForHandwritingGesture(int i, java.lang.String str, android.view.inputmethod.HandwritingGesture handwritingGesture) {
        android.text.Editable editableText = getEditableText();
        if (this.mTempCursor == null) {
            this.mTempCursor = new android.text.NoCopySpan.Concrete();
        }
        editableText.setSpan(this.mTempCursor, i, i, 34);
        editableText.insert(i, str);
        int spanStart = editableText.getSpanStart(this.mTempCursor);
        editableText.removeSpan(this.mTempCursor);
        if (spanStart == i) {
            return handleGestureFailure(handwritingGesture);
        }
        android.text.Selection.setSelection(editableText, spanStart);
        return 1;
    }

    private java.util.regex.Pattern getWhitespacePattern() {
        if (this.mWhitespacePattern == null) {
            this.mWhitespacePattern = java.util.regex.Pattern.compile("\\s+");
        }
        return this.mWhitespacePattern;
    }

    public void nullLayouts() {
        if ((this.mLayout instanceof android.text.BoringLayout) && this.mSavedLayout == null) {
            this.mSavedLayout = (android.text.BoringLayout) this.mLayout;
        }
        if ((this.mHintLayout instanceof android.text.BoringLayout) && this.mSavedHintLayout == null) {
            this.mSavedHintLayout = (android.text.BoringLayout) this.mHintLayout;
        }
        this.mHintLayout = null;
        this.mLayout = null;
        this.mSavedMarqueeModeLayout = null;
        this.mHintBoring = null;
        this.mBoring = null;
        if (this.mEditor != null) {
            this.mEditor.prepareCursorControllers();
        }
    }

    private void assumeLayout() {
        int i;
        int i2;
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (compoundPaddingLeft >= 1) {
            i = compoundPaddingLeft;
        } else {
            i = 0;
        }
        if (!this.mHorizontallyScrolling) {
            i2 = i;
        } else {
            i2 = 1048576;
        }
        makeNewLayout(i2, i, UNKNOWN_BORING, UNKNOWN_BORING, i, false);
    }

    private android.text.Layout.Alignment getLayoutAlignment() {
        switch (getTextAlignment()) {
            case 1:
                switch (this.mGravity & android.view.Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
                    case 1:
                        return android.text.Layout.Alignment.ALIGN_CENTER;
                    case 3:
                        return android.text.Layout.Alignment.ALIGN_LEFT;
                    case 5:
                        return android.text.Layout.Alignment.ALIGN_RIGHT;
                    case android.view.Gravity.START /* 8388611 */:
                        return android.text.Layout.Alignment.ALIGN_NORMAL;
                    case android.view.Gravity.END /* 8388613 */:
                        return android.text.Layout.Alignment.ALIGN_OPPOSITE;
                    default:
                        return android.text.Layout.Alignment.ALIGN_NORMAL;
                }
            case 2:
                return android.text.Layout.Alignment.ALIGN_NORMAL;
            case 3:
                return android.text.Layout.Alignment.ALIGN_OPPOSITE;
            case 4:
                return android.text.Layout.Alignment.ALIGN_CENTER;
            case 5:
                return getLayoutDirection() == 1 ? android.text.Layout.Alignment.ALIGN_RIGHT : android.text.Layout.Alignment.ALIGN_LEFT;
            case 6:
                return getLayoutDirection() == 1 ? android.text.Layout.Alignment.ALIGN_LEFT : android.text.Layout.Alignment.ALIGN_RIGHT;
            default:
                return android.text.Layout.Alignment.ALIGN_NORMAL;
        }
    }

    private android.graphics.Paint.FontMetrics getResolvedMinimumFontMetrics() {
        if (this.mMinimumFontMetrics != null) {
            return this.mMinimumFontMetrics;
        }
        if (!this.mUseLocalePreferredLineHeightForMinimum) {
            return null;
        }
        if (this.mLocalePreferredFontMetrics == null) {
            this.mLocalePreferredFontMetrics = new android.graphics.Paint.FontMetrics();
        }
        this.mTextPaint.getFontMetricsForLocale(this.mLocalePreferredFontMetrics);
        return this.mLocalePreferredFontMetrics;
    }

    public void makeNewLayout(int i, int i2, android.text.BoringLayout.Metrics metrics, android.text.BoringLayout.Metrics metrics2, int i3, boolean z) {
        int i4;
        int i5;
        boolean z2;
        boolean z3;
        boolean z4;
        android.text.TextUtils.TruncateAt truncateAt;
        boolean z5;
        int i6;
        boolean z6;
        int i7;
        int i8;
        android.text.BoringLayout.Metrics metrics3;
        android.text.Layout.Alignment alignment;
        int i9;
        stopMarquee();
        this.mOldMaximum = this.mMaximum;
        this.mOldMaxMode = this.mMaxMode;
        this.mHighlightPathBogus = true;
        this.mHighlightPathsBogus = true;
        if (i >= 0) {
            i4 = i;
        } else {
            i4 = 0;
        }
        if (i2 >= 0) {
            i5 = i2;
        } else {
            i5 = 0;
        }
        android.text.Layout.Alignment layoutAlignment = getLayoutAlignment();
        if (this.mSingleLine && this.mLayout != null && (layoutAlignment == android.text.Layout.Alignment.ALIGN_NORMAL || layoutAlignment == android.text.Layout.Alignment.ALIGN_OPPOSITE)) {
            z2 = true;
        } else {
            z2 = false;
        }
        int paragraphDirection = z2 ? this.mLayout.getParagraphDirection(0) : 0;
        if (this.mEllipsize != null && getKeyListener() == null) {
            z3 = true;
        } else {
            z3 = false;
        }
        if (this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 0) {
            z4 = true;
        } else {
            z4 = false;
        }
        android.text.TextUtils.TruncateAt truncateAt2 = this.mEllipsize;
        if (this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode == 1) {
            truncateAt = android.text.TextUtils.TruncateAt.END_SMALL;
        } else {
            truncateAt = truncateAt2;
        }
        if (this.mTextDir == null) {
            this.mTextDir = getTextDirectionHeuristic();
        }
        if (truncateAt == this.mEllipsize) {
            z5 = true;
        } else {
            z5 = false;
        }
        android.text.TextUtils.TruncateAt truncateAt3 = truncateAt;
        this.mLayout = makeSingleLayout(i4, metrics, i3, layoutAlignment, z3, truncateAt, z5);
        if (z4) {
            this.mSavedMarqueeModeLayout = makeSingleLayout(i4, metrics, i3, layoutAlignment, z3, truncateAt3 == android.text.TextUtils.TruncateAt.MARQUEE ? android.text.TextUtils.TruncateAt.END : android.text.TextUtils.TruncateAt.MARQUEE, truncateAt3 != this.mEllipsize);
        }
        boolean z7 = this.mEllipsize != null;
        this.mHintLayout = null;
        if (this.mHint == null) {
            i6 = paragraphDirection;
            z6 = true;
            i7 = i3;
            i8 = 0;
        } else {
            int i10 = z7 ? i4 : i5;
            if (metrics2 != UNKNOWN_BORING) {
                metrics3 = metrics2;
            } else {
                android.text.BoringLayout.Metrics isBoring = android.text.BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mHintBoring);
                if (isBoring != null) {
                    this.mHintBoring = isBoring;
                }
                metrics3 = isBoring;
            }
            if (metrics3 == null) {
                i6 = paragraphDirection;
                alignment = layoutAlignment;
                i9 = i10;
                i7 = i3;
                i8 = 0;
            } else if (metrics3.width <= i10 && (!z7 || metrics3.width <= i3)) {
                if (this.mSavedHintLayout != null) {
                    this.mHintLayout = this.mSavedHintLayout.replaceOrMake(this.mHint, this.mTextPaint, i10, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad);
                } else {
                    this.mHintLayout = android.text.BoringLayout.make(this.mHint, this.mTextPaint, i10, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad);
                }
                this.mSavedHintLayout = (android.text.BoringLayout) this.mHintLayout;
                i6 = paragraphDirection;
                alignment = layoutAlignment;
                i9 = i10;
                i7 = i3;
                i8 = 0;
            } else if (!z7 || metrics3.width > i10) {
                i6 = paragraphDirection;
                alignment = layoutAlignment;
                i9 = i10;
                i7 = i3;
                i8 = 0;
            } else if (this.mSavedHintLayout != null) {
                alignment = layoutAlignment;
                i6 = paragraphDirection;
                i9 = i10;
                i8 = 0;
                i7 = i3;
                this.mHintLayout = this.mSavedHintLayout.replaceOrMake(this.mHint, this.mTextPaint, i10, alignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad, this.mEllipsize, i3);
            } else {
                i6 = paragraphDirection;
                alignment = layoutAlignment;
                i9 = i10;
                i7 = i3;
                i8 = 0;
                this.mHintLayout = android.text.BoringLayout.make(this.mHint, this.mTextPaint, i9, layoutAlignment, this.mSpacingMult, this.mSpacingAdd, metrics3, this.mIncludePad, this.mEllipsize, i3);
            }
            if (this.mHintLayout != null) {
                z6 = true;
            } else {
                z6 = true;
                android.text.StaticLayout.Builder minimumFontMetrics = android.text.StaticLayout.Builder.obtain(this.mHint, i8, this.mHint.length(), this.mTextPaint, i9).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setLineBreakConfig(android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
                if (z7) {
                    minimumFontMetrics.setEllipsize(this.mEllipsize).setEllipsizedWidth(i7);
                }
                this.mHintLayout = minimumFontMetrics.build();
            }
        }
        if (z || (z2 && i6 != this.mLayout.getParagraphDirection(i8))) {
            registerForPreDraw();
        }
        if (this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE && !compressText(i7)) {
            int i11 = this.mLayoutParams.height;
            if (i11 != -2 && i11 != -1) {
                startMarquee();
            } else {
                this.mRestartMarquee = z6;
            }
        }
        if (this.mEditor != null) {
            this.mEditor.prepareCursorControllers();
        }
    }

    public boolean useDynamicLayout() {
        return isTextSelectable() || (this.mSpannable != null && this.mPrecomputed == null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v10, types: [android.text.Layout] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v32, types: [android.text.DynamicLayout] */
    /* JADX WARN: Type inference failed for: r2v34 */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v4, types: [android.text.BoringLayout] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7 */
    /* JADX WARN: Type inference failed for: r2v9 */
    protected android.text.Layout makeSingleLayout(int i, android.text.BoringLayout.Metrics metrics, int i2, android.text.Layout.Alignment alignment, boolean z, android.text.TextUtils.TruncateAt truncateAt, boolean z2) {
        android.text.BoringLayout.Metrics metrics2;
        android.text.BoringLayout.Metrics metrics3;
        android.text.BoringLayout boringLayout = 0;
        boringLayout = 0;
        boringLayout = 0;
        if (useDynamicLayout()) {
            boringLayout = android.text.DynamicLayout.Builder.obtain(this.mText, this.mTextPaint, i).setDisplayText(this.mTransformed).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setLineBreakConfig(android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setEllipsize(getKeyListener() == null ? truncateAt : null).setEllipsizedWidth(i2).setMinimumFontMetrics(getResolvedMinimumFontMetrics()).build();
        } else {
            if (metrics != UNKNOWN_BORING) {
                metrics2 = metrics;
            } else {
                android.text.BoringLayout.Metrics isBoring = android.text.BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (isBoring != null) {
                    this.mBoring = isBoring;
                }
                metrics2 = isBoring;
            }
            if (metrics2 != null) {
                if (metrics2.width > i) {
                    metrics3 = metrics2;
                } else if (truncateAt == null || metrics2.width <= i2) {
                    if (z2 && this.mSavedLayout != null) {
                        boringLayout = this.mSavedLayout.replaceOrMake(this.mTransformed, this.mTextPaint, i, alignment, this.mSpacingMult, this.mSpacingAdd, metrics2, this.mIncludePad, null, i, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                    } else {
                        boringLayout = new android.text.BoringLayout(this.mTransformed, this.mTextPaint, i, alignment, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), i, null, metrics2, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                    }
                    if (z2) {
                        this.mSavedLayout = boringLayout;
                    }
                } else {
                    metrics3 = metrics2;
                }
                if (z) {
                    android.text.BoringLayout.Metrics metrics4 = metrics3;
                    if (metrics4.width <= i) {
                        if (z2 && this.mSavedLayout != null) {
                            boringLayout = this.mSavedLayout.replaceOrMake(this.mTransformed, this.mTextPaint, i, alignment, this.mSpacingMult, this.mSpacingAdd, metrics4, this.mIncludePad, truncateAt, i2, isFallbackLineSpacingForBoringLayout(), this.mUseBoundsForWidth, getResolvedMinimumFontMetrics());
                        } else {
                            boringLayout = new android.text.BoringLayout(this.mTransformed, this.mTextPaint, i, alignment, this.mSpacingMult, this.mSpacingAdd, this.mIncludePad, isFallbackLineSpacingForBoringLayout(), i2, truncateAt, metrics4, this.mUseBoundsForWidth, this.mShiftDrawingOffsetForStartOverhang, getResolvedMinimumFontMetrics());
                        }
                    }
                }
            }
        }
        if (boringLayout == 0) {
            android.text.StaticLayout.Builder minimumFontMetrics = android.text.StaticLayout.Builder.obtain(this.mTransformed, 0, this.mTransformed.length(), this.mTextPaint, i).setAlignment(alignment).setTextDirection(this.mTextDir).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(this.mIncludePad).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setLineBreakConfig(android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
            if (z) {
                minimumFontMetrics.setEllipsize(truncateAt).setEllipsizedWidth(i2);
            }
            return minimumFontMetrics.build();
        }
        return boringLayout;
    }

    private boolean compressText(float f) {
        if (!isHardwareAccelerated() && f > 0.0f && this.mLayout != null && getLineCount() == 1 && !this.mUserSetTextScaleX && this.mTextPaint.getTextScaleX() == 1.0f) {
            float lineWidth = ((this.mLayout.getLineWidth(0) + 1.0f) - f) / f;
            if (lineWidth > 0.0f && lineWidth <= 0.07f) {
                this.mTextPaint.setTextScaleX((1.0f - lineWidth) - 0.005f);
                post(new java.lang.Runnable() { // from class: android.widget.TextView.2
                    @Override // java.lang.Runnable
                    public void run() {
                        android.widget.TextView.this.requestLayout();
                    }
                });
                return true;
            }
        }
        return false;
    }

    private static int desired(android.text.Layout layout, boolean z) {
        int lineCount = layout.getLineCount();
        java.lang.CharSequence text = layout.getText();
        for (int i = 0; i < lineCount - 1; i++) {
            if (text.charAt(layout.getLineEnd(i) - 1) != '\n') {
                return -1;
            }
        }
        float f = 0.0f;
        for (int i2 = 0; i2 < lineCount; i2++) {
            f = java.lang.Math.max(f, layout.getLineMax(i2));
        }
        if (z) {
            f = java.lang.Math.max(f, layout.computeDrawingBoundingBox().width());
        }
        return (int) java.lang.Math.ceil(f);
    }

    public void setIncludeFontPadding(boolean z) {
        if (this.mIncludePad != z) {
            this.mIncludePad = z;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public boolean getIncludeFontPadding() {
        return this.mIncludePad;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        int min;
        int i7;
        int max;
        int i8;
        boolean z2;
        int i9;
        android.text.BoringLayout.Metrics metrics;
        android.text.BoringLayout.Metrics metrics2;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int mode = android.view.View.MeasureSpec.getMode(i);
        int mode2 = android.view.View.MeasureSpec.getMode(i2);
        int size = android.view.View.MeasureSpec.getSize(i);
        int size2 = android.view.View.MeasureSpec.getSize(i2);
        android.text.BoringLayout.Metrics metrics3 = UNKNOWN_BORING;
        android.text.BoringLayout.Metrics metrics4 = UNKNOWN_BORING;
        if (this.mTextDir == null) {
            this.mTextDir = getTextDirectionHeuristic();
        }
        float f = mode == Integer.MIN_VALUE ? size : Float.MAX_VALUE;
        if (mode == 1073741824) {
            i8 = size;
            metrics = metrics4;
            i9 = -1;
            i7 = 1;
            z2 = false;
            metrics2 = metrics3;
        } else {
            if (this.mLayout != null && this.mEllipsize == null) {
                i3 = desired(this.mLayout, this.mUseBoundsForWidth);
            } else {
                i3 = -1;
            }
            if (i3 < 0) {
                metrics3 = android.text.BoringLayout.isBoring(this.mTransformed, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mBoring);
                if (metrics3 != null) {
                    this.mBoring = metrics3;
                }
                z = false;
            } else {
                z = true;
            }
            if (metrics3 == null || metrics3 == UNKNOWN_BORING) {
                if (i3 < 0) {
                    i3 = (int) java.lang.Math.ceil(android.text.Layout.getDesiredWidthWithLimit(this.mTransformed, 0, this.mTransformed.length(), this.mTextPaint, this.mTextDir, f, this.mUseBoundsForWidth));
                }
                i4 = i3;
                i5 = i4;
            } else if (this.mUseBoundsForWidth) {
                i4 = java.lang.Math.max(metrics3.width, (int) java.lang.Math.ceil(metrics3.getDrawingBoundingBox().width()));
                i5 = i3;
            } else {
                i4 = metrics3.width;
                i5 = i3;
            }
            android.widget.TextView.Drawables drawables = this.mDrawables;
            if (drawables == null) {
                i6 = i4;
            } else {
                i6 = java.lang.Math.max(java.lang.Math.max(i4, drawables.mDrawableWidthTop), drawables.mDrawableWidthBottom);
            }
            if (this.mHint != null) {
                if (this.mHintLayout != null && this.mEllipsize == null) {
                    i10 = desired(this.mHintLayout, this.mUseBoundsForWidth);
                } else {
                    i10 = -1;
                }
                if (i10 < 0 && (metrics4 = android.text.BoringLayout.isBoring(this.mHint, this.mTextPaint, this.mTextDir, isFallbackLineSpacingForBoringLayout(), getResolvedMinimumFontMetrics(), this.mHintBoring)) != null) {
                    this.mHintBoring = metrics4;
                }
                if (metrics4 == null || metrics4 == UNKNOWN_BORING) {
                    if (i10 >= 0) {
                        i11 = i6;
                    } else {
                        i11 = i6;
                        i10 = (int) java.lang.Math.ceil(android.text.Layout.getDesiredWidthWithLimit(this.mHint, 0, this.mHint.length(), this.mTextPaint, this.mTextDir, f, this.mUseBoundsForWidth));
                    }
                    i6 = i10;
                } else {
                    i11 = i6;
                    i6 = metrics4.width;
                }
                if (i6 <= i11) {
                    i6 = i11;
                }
            }
            int compoundPaddingLeft = i6 + getCompoundPaddingLeft() + getCompoundPaddingRight();
            if (this.mMaxWidthMode == 1) {
                min = java.lang.Math.min(compoundPaddingLeft, this.mMaxWidth * getLineHeight());
            } else {
                min = java.lang.Math.min(compoundPaddingLeft, this.mMaxWidth);
            }
            i7 = 1;
            if (this.mMinWidthMode == 1) {
                max = java.lang.Math.max(min, this.mMinWidth * getLineHeight());
            } else {
                max = java.lang.Math.max(min, this.mMinWidth);
            }
            int max2 = java.lang.Math.max(max, getSuggestedMinimumWidth());
            if (mode != Integer.MIN_VALUE) {
                i8 = max2;
                z2 = z;
                i9 = i5;
                metrics = metrics4;
                metrics2 = metrics3;
            } else {
                i8 = java.lang.Math.min(size, max2);
                metrics = metrics4;
                z2 = z;
                i9 = i5;
                metrics2 = metrics3;
            }
        }
        int compoundPaddingLeft2 = (i8 - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int i15 = this.mHorizontallyScrolling ? 1048576 : compoundPaddingLeft2;
        int width = this.mHintLayout == null ? i15 : this.mHintLayout.getWidth();
        if (this.mLayout == null) {
            i14 = -1;
            i12 = i7;
            i13 = 1073741824;
            makeNewLayout(i15, i15, metrics2, metrics, (i8 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
        } else {
            i12 = i7;
            i13 = 1073741824;
            i14 = -1;
            int i16 = (this.mLayout.getWidth() == i15 && width == i15 && this.mLayout.getEllipsizedWidth() == (i8 - getCompoundPaddingLeft()) - getCompoundPaddingRight()) ? 0 : i12;
            int i17 = (this.mHint != null || this.mEllipsize != null || i15 <= this.mLayout.getWidth() || (!(this.mLayout instanceof android.text.BoringLayout) && (!z2 || i9 < 0 || i9 > i15))) ? 0 : i12;
            int i18 = (this.mMaxMode == this.mOldMaxMode && this.mMaximum == this.mOldMaximum) ? 0 : i12;
            if (i16 != 0 || i18 != 0) {
                if (i18 == 0 && i17 != 0) {
                    this.mLayout.increaseWidthTo(i15);
                } else {
                    makeNewLayout(i15, i15, metrics2, metrics, (i8 - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                }
            }
        }
        if (mode2 == i13) {
            this.mDesiredHeightAtMeasure = i14;
        } else {
            int desiredHeight = getDesiredHeight();
            this.mDesiredHeightAtMeasure = desiredHeight;
            if (mode2 != Integer.MIN_VALUE) {
                size2 = desiredHeight;
            } else {
                size2 = java.lang.Math.min(desiredHeight, size2);
            }
        }
        int compoundPaddingTop = (size2 - getCompoundPaddingTop()) - getCompoundPaddingBottom();
        if (this.mMaxMode == i12 && this.mLayout.getLineCount() > this.mMaximum) {
            compoundPaddingTop = java.lang.Math.min(compoundPaddingTop, this.mLayout.getLineTop(this.mMaximum));
        }
        if (this.mMovement == null && this.mLayout.getWidth() <= compoundPaddingLeft2 && this.mLayout.getHeight() <= compoundPaddingTop) {
            scrollTo(0, 0);
        } else {
            registerForPreDraw();
        }
        setMeasuredDimension(i8, size2);
    }

    private void autoSizeText() {
        int measuredWidth;
        if (!isAutoSizeEnabled()) {
            return;
        }
        if (this.mNeedsAutoSizeText) {
            if (getMeasuredWidth() <= 0 || getMeasuredHeight() <= 0) {
                return;
            }
            if (this.mHorizontallyScrolling) {
                measuredWidth = 1048576;
            } else {
                measuredWidth = (getMeasuredWidth() - getTotalPaddingLeft()) - getTotalPaddingRight();
            }
            int measuredHeight = (getMeasuredHeight() - getExtendedPaddingBottom()) - getExtendedPaddingTop();
            if (measuredWidth <= 0 || measuredHeight <= 0) {
                return;
            }
            synchronized (TEMP_RECTF) {
                TEMP_RECTF.setEmpty();
                TEMP_RECTF.right = measuredWidth;
                TEMP_RECTF.bottom = measuredHeight;
                float findLargestTextSizeWhichFits = findLargestTextSizeWhichFits(TEMP_RECTF);
                if (findLargestTextSizeWhichFits != getTextSize()) {
                    setTextSizeInternal(0, findLargestTextSizeWhichFits, false);
                    makeNewLayout(measuredWidth, 0, UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
                }
            }
        }
        this.mNeedsAutoSizeText = true;
    }

    private int findLargestTextSizeWhichFits(android.graphics.RectF rectF) {
        int length = this.mAutoSizeTextSizesInPx.length;
        if (length == 0) {
            throw new java.lang.IllegalStateException("No available text sizes to choose from.");
        }
        int i = 1;
        int i2 = length - 1;
        int i3 = 0;
        while (i <= i2) {
            int i4 = (i + i2) / 2;
            if (suggestedSizeFitsInSpace(this.mAutoSizeTextSizesInPx[i4], rectF)) {
                int i5 = i4 + 1;
                i3 = i;
                i = i5;
            } else {
                i3 = i4 - 1;
                i2 = i3;
            }
        }
        return this.mAutoSizeTextSizesInPx[i3];
    }

    private boolean suggestedSizeFitsInSpace(int i, android.graphics.RectF rectF) {
        java.lang.CharSequence text;
        if (this.mTransformed != null) {
            text = this.mTransformed;
        } else {
            text = getText();
        }
        int maxLines = getMaxLines();
        if (this.mTempTextPaint == null) {
            this.mTempTextPaint = new android.text.TextPaint();
        } else {
            this.mTempTextPaint.reset();
        }
        this.mTempTextPaint.set(getPaint());
        this.mTempTextPaint.setTextSize(i);
        android.text.StaticLayout.Builder obtain = android.text.StaticLayout.Builder.obtain(text, 0, text.length(), this.mTempTextPaint, java.lang.Math.round(rectF.right));
        obtain.setAlignment(getLayoutAlignment()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(isFallbackLineSpacingForStaticLayout()).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency()).setJustificationMode(getJustificationMode()).setMaxLines(this.mMaxMode == 1 ? this.mMaximum : Integer.MAX_VALUE).setTextDirection(getTextDirectionHeuristic()).setLineBreakConfig(android.graphics.text.LineBreakConfig.getLineBreakConfig(this.mLineBreakStyle, this.mLineBreakWordStyle)).setUseBoundsForWidth(this.mUseBoundsForWidth).setMinimumFontMetrics(getResolvedMinimumFontMetrics());
        android.text.StaticLayout build = obtain.build();
        return (maxLines == -1 || build.getLineCount() <= maxLines) && ((float) build.getHeight()) <= rectF.bottom;
    }

    private int getDesiredHeight() {
        return java.lang.Math.max(getDesiredHeight(this.mLayout, true), getDesiredHeight(this.mHintLayout, this.mEllipsize != null));
    }

    private int getDesiredHeight(android.text.Layout layout, boolean z) {
        if (layout == null) {
            return 0;
        }
        int height = layout.getHeight(z);
        android.widget.TextView.Drawables drawables = this.mDrawables;
        if (drawables != null) {
            height = java.lang.Math.max(java.lang.Math.max(height, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
        }
        int lineCount = layout.getLineCount();
        int compoundPaddingTop = getCompoundPaddingTop() + getCompoundPaddingBottom();
        int i = height + compoundPaddingTop;
        if (this.mMaxMode != 1) {
            i = java.lang.Math.min(i, this.mMaximum);
        } else if (z && lineCount > this.mMaximum && ((layout instanceof android.text.DynamicLayout) || (layout instanceof android.text.BoringLayout))) {
            int lineTop = layout.getLineTop(this.mMaximum);
            if (drawables != null) {
                lineTop = java.lang.Math.max(java.lang.Math.max(lineTop, drawables.mDrawableHeightLeft), drawables.mDrawableHeightRight);
            }
            i = lineTop + compoundPaddingTop;
            lineCount = this.mMaximum;
        }
        if (this.mMinMode == 1) {
            if (lineCount < this.mMinimum) {
                i += getLineHeight() * (this.mMinimum - lineCount);
            }
        } else {
            i = java.lang.Math.max(i, this.mMinimum);
        }
        return java.lang.Math.max(i, getSuggestedMinimumHeight());
    }

    private void checkForResize() {
        boolean z = false;
        if (this.mLayout != null) {
            if (this.mLayoutParams.width == -2) {
                invalidate();
                z = true;
            }
            if (this.mLayoutParams.height == -2) {
                if (getDesiredHeight() != getHeight()) {
                    z = true;
                }
            } else if (this.mLayoutParams.height == -1 && this.mDesiredHeightAtMeasure >= 0 && getDesiredHeight() != this.mDesiredHeightAtMeasure) {
                z = true;
            }
        }
        if (z) {
            requestLayout();
        }
    }

    private void checkForRelayout() {
        if ((this.mLayoutParams.width != -2 || (this.mMaxWidthMode == this.mMinWidthMode && this.mMaxWidth == this.mMinWidth)) && ((this.mHint == null || this.mHintLayout != null) && ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight() > 0)) {
            int height = this.mLayout.getHeight();
            makeNewLayout(this.mLayout.getWidth(), this.mHintLayout == null ? 0 : this.mHintLayout.getWidth(), UNKNOWN_BORING, UNKNOWN_BORING, ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight(), false);
            if (this.mEllipsize != android.text.TextUtils.TruncateAt.MARQUEE) {
                if (this.mLayoutParams.height != -2 && this.mLayoutParams.height != -1) {
                    autoSizeText();
                    invalidate();
                    return;
                } else if (this.mLayout.getHeight() == height && (this.mHintLayout == null || this.mHintLayout.getHeight() == height)) {
                    autoSizeText();
                    invalidate();
                    return;
                }
            }
            requestLayout();
            invalidate();
            return;
        }
        nullLayouts();
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mDeferScroll >= 0) {
            int i5 = this.mDeferScroll;
            this.mDeferScroll = -1;
            bringPointIntoView(java.lang.Math.min(i5, this.mText.length()));
        }
        autoSizeText();
    }

    private boolean isShowingHint() {
        return (!android.text.TextUtils.isEmpty(this.mText) || android.text.TextUtils.isEmpty(this.mHint) || this.mHideHint) ? false : true;
    }

    private boolean bringTextIntoView() {
        int i;
        int floor;
        int i2;
        android.text.Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
        if ((this.mGravity & 112) != 80) {
            i = 0;
        } else {
            i = layout.getLineCount() - 1;
        }
        android.text.Layout.Alignment paragraphAlignment = layout.getParagraphAlignment(i);
        int paragraphDirection = layout.getParagraphDirection(i);
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int height = layout.getHeight();
        if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_NORMAL) {
            paragraphAlignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_LEFT : android.text.Layout.Alignment.ALIGN_RIGHT;
        } else if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_OPPOSITE) {
            paragraphAlignment = paragraphDirection == 1 ? android.text.Layout.Alignment.ALIGN_RIGHT : android.text.Layout.Alignment.ALIGN_LEFT;
        }
        if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_CENTER) {
            floor = (int) java.lang.Math.floor(layout.getLineLeft(i));
            int ceil = (int) java.lang.Math.ceil(layout.getLineRight(i));
            if (ceil - floor < compoundPaddingLeft) {
                floor = ((ceil + floor) / 2) - (compoundPaddingLeft / 2);
            } else if (paragraphDirection < 0) {
                floor = ceil - compoundPaddingLeft;
            }
        } else if (paragraphAlignment == android.text.Layout.Alignment.ALIGN_RIGHT) {
            floor = ((int) java.lang.Math.ceil(layout.getLineRight(i))) - compoundPaddingLeft;
        } else {
            floor = (int) java.lang.Math.floor(layout.getLineLeft(i));
        }
        if (height < extendedPaddingTop) {
            i2 = 0;
        } else if ((this.mGravity & 112) == 80) {
            i2 = height - extendedPaddingTop;
        } else {
            i2 = 0;
        }
        if (floor == this.mScrollX && i2 == this.mScrollY) {
            return false;
        }
        scrollTo(floor, i2);
        return true;
    }

    public boolean bringPointIntoView(int i) {
        return bringPointIntoView(i, false);
    }

    public boolean bringPointIntoView(int i, boolean z) {
        int i2;
        boolean z2;
        if (!isLayoutRequested()) {
            int originalToTransformed = originalToTransformed(i, 1);
            android.text.Layout layout = isShowingHint() ? this.mHintLayout : this.mLayout;
            if (layout == null) {
                return false;
            }
            int lineForOffset = layout.getLineForOffset(originalToTransformed);
            switch (layout.getParagraphAlignment(lineForOffset)) {
                case ALIGN_LEFT:
                    i2 = 1;
                    break;
                case ALIGN_RIGHT:
                    i2 = -1;
                    break;
                case ALIGN_NORMAL:
                    i2 = layout.getParagraphDirection(lineForOffset);
                    break;
                case ALIGN_OPPOSITE:
                    i2 = -layout.getParagraphDirection(lineForOffset);
                    break;
                default:
                    i2 = 0;
                    break;
            }
            int primaryHorizontal = (int) layout.getPrimaryHorizontal(originalToTransformed, i2 > 0);
            int lineTop = layout.getLineTop(lineForOffset);
            int lineTop2 = layout.getLineTop(lineForOffset + 1);
            int floor = (int) java.lang.Math.floor(layout.getLineLeft(lineForOffset));
            int ceil = (int) java.lang.Math.ceil(layout.getLineRight(lineForOffset));
            int height = layout.getHeight();
            int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
            if (!this.mHorizontallyScrolling && ceil - floor > compoundPaddingLeft && ceil > primaryHorizontal) {
                ceil = java.lang.Math.max(primaryHorizontal, floor + compoundPaddingLeft);
            }
            int i3 = (lineTop2 - lineTop) / 2;
            int i4 = extendedPaddingTop / 4;
            if (i3 <= i4) {
                i4 = i3;
            }
            int i5 = compoundPaddingLeft / 4;
            if (i3 > i5) {
                i3 = i5;
            }
            int i6 = this.mScrollX;
            int i7 = this.mScrollY;
            if (lineTop - i7 < i4) {
                i7 = lineTop - i4;
            }
            int i8 = extendedPaddingTop - i4;
            if (lineTop2 - i7 > i8) {
                i7 = lineTop2 - i8;
            }
            if (height - i7 < extendedPaddingTop) {
                i7 = height - extendedPaddingTop;
            }
            if (0 - i7 > 0) {
                i7 = 0;
            }
            if (i2 != 0) {
                if (primaryHorizontal - i6 < i3) {
                    i6 = primaryHorizontal - i3;
                }
                int i9 = compoundPaddingLeft - i3;
                if (primaryHorizontal - i6 > i9) {
                    i6 = primaryHorizontal - i9;
                }
            }
            if (i2 < 0) {
                if (floor - i6 <= 0) {
                    floor = i6;
                }
                if (ceil - floor < compoundPaddingLeft) {
                    floor = ceil - compoundPaddingLeft;
                }
            } else if (i2 > 0) {
                if (ceil - i6 < compoundPaddingLeft) {
                    i6 = ceil - compoundPaddingLeft;
                }
                if (floor - i6 <= 0) {
                    floor = i6;
                }
            } else {
                int i10 = ceil - floor;
                if (i10 <= compoundPaddingLeft) {
                    floor -= (compoundPaddingLeft - i10) / 2;
                } else if (primaryHorizontal > ceil - i3) {
                    floor = ceil - compoundPaddingLeft;
                } else if (primaryHorizontal >= floor + i3 && floor <= i6) {
                    if (ceil < i6 + compoundPaddingLeft) {
                        floor = ceil - compoundPaddingLeft;
                    } else {
                        if (primaryHorizontal - i6 >= i3) {
                            floor = i6;
                        } else {
                            floor = primaryHorizontal - i3;
                        }
                        int i11 = compoundPaddingLeft - i3;
                        if (primaryHorizontal - floor > i11) {
                            floor = primaryHorizontal - i11;
                        }
                    }
                }
            }
            if (floor == this.mScrollX && i7 == this.mScrollY) {
                z2 = false;
            } else {
                if (this.mScroller != null) {
                    long currentAnimationTimeMillis = android.view.animation.AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
                    int i12 = floor - this.mScrollX;
                    int i13 = i7 - this.mScrollY;
                    if (currentAnimationTimeMillis > 250) {
                        this.mScroller.startScroll(this.mScrollX, this.mScrollY, i12, i13);
                        awakenScrollBars(this.mScroller.getDuration());
                        invalidate();
                    } else {
                        if (!this.mScroller.isFinished()) {
                            this.mScroller.abortAnimation();
                        }
                        scrollBy(i12, i13);
                    }
                    this.mLastScroll = android.view.animation.AnimationUtils.currentAnimationTimeMillis();
                } else {
                    scrollTo(floor, i7);
                }
                z2 = true;
            }
            if (z || isFocused()) {
                if (this.mTempRect == null) {
                    this.mTempRect = new android.graphics.Rect();
                }
                this.mTempRect.set(primaryHorizontal - 2, lineTop, primaryHorizontal + 2, lineTop2);
                getInterestingRect(this.mTempRect, lineForOffset);
                this.mTempRect.offset(this.mScrollX, this.mScrollY);
                if (requestRectangleOnScreen(this.mTempRect)) {
                    return true;
                }
            }
            return z2;
        }
        this.mDeferScroll = i;
        return false;
    }

    public boolean moveCursorToVisibleOffset() {
        int selectionStartTransformed;
        if (!(this.mText instanceof android.text.Spannable) || (selectionStartTransformed = getSelectionStartTransformed()) != getSelectionEndTransformed()) {
            return false;
        }
        int lineForOffset = this.mLayout.getLineForOffset(selectionStartTransformed);
        int lineTop = this.mLayout.getLineTop(lineForOffset);
        int lineTop2 = this.mLayout.getLineTop(lineForOffset + 1);
        int extendedPaddingTop = ((this.mBottom - this.mTop) - getExtendedPaddingTop()) - getExtendedPaddingBottom();
        int i = lineTop2 - lineTop;
        int i2 = i / 2;
        int i3 = extendedPaddingTop / 4;
        if (i2 > i3) {
            i2 = i3;
        }
        int i4 = this.mScrollY;
        int i5 = i4 + i2;
        if (lineTop < i5) {
            lineForOffset = this.mLayout.getLineForVertical(i5 + i);
        } else {
            int i6 = (extendedPaddingTop + i4) - i2;
            if (lineTop2 > i6) {
                lineForOffset = this.mLayout.getLineForVertical(i6 - i);
            }
        }
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        int offsetForHorizontal = this.mLayout.getOffsetForHorizontal(lineForOffset, this.mScrollX);
        int offsetForHorizontal2 = this.mLayout.getOffsetForHorizontal(lineForOffset, compoundPaddingLeft + r4);
        int i7 = offsetForHorizontal < offsetForHorizontal2 ? offsetForHorizontal : offsetForHorizontal2;
        if (offsetForHorizontal <= offsetForHorizontal2) {
            offsetForHorizontal = offsetForHorizontal2;
        }
        if (selectionStartTransformed >= i7) {
            if (selectionStartTransformed <= offsetForHorizontal) {
                i7 = selectionStartTransformed;
            } else {
                i7 = offsetForHorizontal;
            }
        }
        if (i7 == selectionStartTransformed) {
            return false;
        }
        android.text.Selection.setSelection(this.mSpannable, transformedToOriginal(i7, 1));
        return true;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller != null && this.mScroller.computeScrollOffset()) {
            this.mScrollX = this.mScroller.getCurrX();
            this.mScrollY = this.mScroller.getCurrY();
            invalidateParentCaches();
            postInvalidate();
        }
    }

    private void getInterestingRect(android.graphics.Rect rect, int i) {
        convertFromViewportToContentCoordinates(rect);
        if (i == 0) {
            rect.top -= getExtendedPaddingTop();
        }
        if (i == this.mLayout.getLineCount() - 1) {
            rect.bottom += getExtendedPaddingBottom();
        }
    }

    private void convertFromViewportToContentCoordinates(android.graphics.Rect rect) {
        int viewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
        rect.left += viewportToContentHorizontalOffset;
        rect.right += viewportToContentHorizontalOffset;
        int viewportToContentVerticalOffset = viewportToContentVerticalOffset();
        rect.top += viewportToContentVerticalOffset;
        rect.bottom += viewportToContentVerticalOffset;
    }

    private android.graphics.PointF convertFromScreenToContentCoordinates(android.graphics.PointF pointF) {
        int[] locationOnScreen = getLocationOnScreen();
        android.graphics.PointF pointF2 = new android.graphics.PointF(pointF);
        pointF2.offset(-(locationOnScreen[0] + viewportToContentHorizontalOffset()), -(locationOnScreen[1] + viewportToContentVerticalOffset()));
        return pointF2;
    }

    private android.graphics.RectF convertFromScreenToContentCoordinates(android.graphics.RectF rectF) {
        int[] locationOnScreen = getLocationOnScreen();
        android.graphics.RectF rectF2 = new android.graphics.RectF(rectF);
        rectF2.offset(-(locationOnScreen[0] + viewportToContentHorizontalOffset()), -(locationOnScreen[1] + viewportToContentVerticalOffset()));
        return rectF2;
    }

    int viewportToContentHorizontalOffset() {
        return getCompoundPaddingLeft() - this.mScrollX;
    }

    int viewportToContentVerticalOffset() {
        int extendedPaddingTop = getExtendedPaddingTop() - this.mScrollY;
        if ((this.mGravity & 112) != 48) {
            return extendedPaddingTop + getVerticalOffset(false);
        }
        return extendedPaddingTop;
    }

    @Override // android.view.View
    public void debug(int i) {
        java.lang.String str;
        super.debug(i);
        java.lang.String str2 = debugIndent(i) + "frame={" + this.mLeft + ", " + this.mTop + ", " + this.mRight + ", " + this.mBottom + "} scroll={" + this.mScrollX + ", " + this.mScrollY + "} ";
        if (this.mText != null) {
            str = str2 + "mText=\"" + ((java.lang.Object) this.mText) + "\" ";
            if (this.mLayout != null) {
                str = str + "mLayout width=" + this.mLayout.getWidth() + " height=" + this.mLayout.getHeight();
            }
        } else {
            str = str2 + "mText=NULL";
        }
        android.util.Log.d("View", str);
    }

    @android.view.ViewDebug.ExportedProperty(category = "text")
    public int getSelectionStart() {
        return android.text.Selection.getSelectionStart(getText());
    }

    @android.view.ViewDebug.ExportedProperty(category = "text")
    public int getSelectionEnd() {
        return android.text.Selection.getSelectionEnd(getText());
    }

    public void getSelection(int i, int i2, android.text.Layout.SelectionRectangleConsumer selectionRectangleConsumer) {
        this.mLayout.getSelection(originalToTransformed(i, 1), originalToTransformed(i2, 1), selectionRectangleConsumer);
    }

    int getSelectionStartTransformed() {
        int selectionStart = getSelectionStart();
        return selectionStart < 0 ? selectionStart : originalToTransformed(selectionStart, 1);
    }

    int getSelectionEndTransformed() {
        int selectionEnd = getSelectionEnd();
        return selectionEnd < 0 ? selectionEnd : originalToTransformed(selectionEnd, 1);
    }

    public boolean hasSelection() {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (selectionStart >= selectionEnd) {
            selectionEnd = selectionStart;
            selectionStart = selectionEnd;
        }
        return selectionStart >= 0 && selectionEnd > 0 && selectionStart != selectionEnd;
    }

    java.lang.String getSelectedText() {
        if (!hasSelection()) {
            return null;
        }
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        java.lang.CharSequence charSequence = this.mText;
        return java.lang.String.valueOf(selectionStart > selectionEnd ? charSequence.subSequence(selectionEnd, selectionStart) : charSequence.subSequence(selectionStart, selectionEnd));
    }

    public void setSingleLine() {
        setSingleLine(true);
    }

    @android.view.RemotableViewMethod
    public void setAllCaps(boolean z) {
        if (z) {
            setTransformationMethod(new android.text.method.AllCapsTransformationMethod(getContext()));
        } else {
            setTransformationMethod(null);
        }
    }

    public boolean isAllCaps() {
        android.text.method.TransformationMethod transformationMethod = getTransformationMethod();
        return transformationMethod != null && (transformationMethod instanceof android.text.method.AllCapsTransformationMethod);
    }

    @android.view.RemotableViewMethod
    public void setSingleLine(boolean z) {
        setInputTypeSingleLine(z);
        applySingleLine(z, true, true, true);
    }

    private void setInputTypeSingleLine(boolean z) {
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 1) {
            if (z) {
                this.mEditor.mInputType &= -131073;
            } else {
                this.mEditor.mInputType |= 131072;
            }
        }
    }

    private void applySingleLine(boolean z, boolean z2, boolean z3, boolean z4) {
        this.mSingleLine = z;
        if (z) {
            setLines(1);
            setHorizontallyScrolling(true);
            if (z2) {
                setTransformationMethod(android.text.method.SingleLineTransformationMethod.getInstance());
            }
            if (z4 && this.mBufferType == android.widget.TextView.BufferType.EDITABLE) {
                android.text.InputFilter[] filters = getFilters();
                for (android.text.InputFilter inputFilter : getFilters()) {
                    if (inputFilter instanceof android.text.InputFilter.LengthFilter) {
                        return;
                    }
                }
                if (this.mSingleLineLengthFilter == null) {
                    this.mSingleLineLengthFilter = new android.text.InputFilter.LengthFilter(5000);
                }
                android.text.InputFilter[] inputFilterArr = new android.text.InputFilter[filters.length + 1];
                java.lang.System.arraycopy(filters, 0, inputFilterArr, 0, filters.length);
                inputFilterArr[filters.length] = this.mSingleLineLengthFilter;
                setFilters(inputFilterArr);
                lambda$setTextAsync$0(getText());
                return;
            }
            return;
        }
        if (z3) {
            setMaxLines(Integer.MAX_VALUE);
        }
        setHorizontallyScrolling(false);
        if (z2) {
            setTransformationMethod(null);
        }
        if (z4 && this.mBufferType == android.widget.TextView.BufferType.EDITABLE) {
            android.text.InputFilter[] filters2 = getFilters();
            if (filters2.length == 0 || this.mSingleLineLengthFilter == null) {
                return;
            }
            int i = 0;
            while (true) {
                if (i >= filters2.length) {
                    i = -1;
                    break;
                } else if (filters2[i] == this.mSingleLineLengthFilter) {
                    break;
                } else {
                    i++;
                }
            }
            if (i == -1) {
                return;
            }
            if (filters2.length == 1) {
                setFilters(NO_FILTERS);
                return;
            }
            android.text.InputFilter[] inputFilterArr2 = new android.text.InputFilter[filters2.length - 1];
            java.lang.System.arraycopy(filters2, 0, inputFilterArr2, 0, i);
            java.lang.System.arraycopy(filters2, i + 1, inputFilterArr2, i, (filters2.length - i) - 1);
            setFilters(inputFilterArr2);
            this.mSingleLineLengthFilter = null;
        }
    }

    public void setEllipsize(android.text.TextUtils.TruncateAt truncateAt) {
        if (this.mEllipsize != truncateAt) {
            this.mEllipsize = truncateAt;
            if (this.mLayout != null) {
                nullLayouts();
                requestLayout();
                invalidate();
            }
        }
    }

    public void setMarqueeRepeatLimit(int i) {
        this.mMarqueeRepeatLimit = i;
    }

    public int getMarqueeRepeatLimit() {
        return this.mMarqueeRepeatLimit;
    }

    @android.view.ViewDebug.ExportedProperty
    public android.text.TextUtils.TruncateAt getEllipsize() {
        return this.mEllipsize;
    }

    @android.view.RemotableViewMethod
    public void setSelectAllOnFocus(boolean z) {
        createEditorIfNeeded();
        this.mEditor.mSelectAllOnFocus = z;
        if (z && !(this.mText instanceof android.text.Spannable)) {
            setText(this.mText, android.widget.TextView.BufferType.SPANNABLE);
        }
    }

    @android.view.RemotableViewMethod
    public void setCursorVisible(boolean z) {
        this.mCursorVisibleFromAttr = z;
        updateCursorVisibleInternal();
    }

    public void setImeConsumesInput(boolean z) {
        this.mImeIsConsumingInput = z;
        updateCursorVisibleInternal();
    }

    private void updateCursorVisibleInternal() {
        boolean z = this.mCursorVisibleFromAttr && !this.mImeIsConsumingInput;
        if (z && this.mEditor == null) {
            return;
        }
        createEditorIfNeeded();
        if (this.mEditor.mCursorVisible != z) {
            this.mEditor.mCursorVisible = z;
            invalidate();
            this.mEditor.makeBlink();
            this.mEditor.prepareCursorControllers();
        }
    }

    public boolean isCursorVisible() {
        if (this.mEditor == null) {
            return true;
        }
        return this.mEditor.mCursorVisible;
    }

    public boolean isCursorVisibleFromAttr() {
        return this.mCursorVisibleFromAttr;
    }

    private boolean canMarquee() {
        int compoundPaddingLeft = ((this.mRight - this.mLeft) - getCompoundPaddingLeft()) - getCompoundPaddingRight();
        if (compoundPaddingLeft <= 0) {
            return false;
        }
        float f = compoundPaddingLeft;
        return this.mLayout.getLineWidth(0) > f || !(this.mMarqueeFadeMode == 0 || this.mSavedMarqueeModeLayout == null || this.mSavedMarqueeModeLayout.getLineWidth(0) <= f);
    }

    protected void startMarquee() {
        if (getKeyListener() != null || compressText((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight())) {
            return;
        }
        if ((this.mMarquee == null || this.mMarquee.isStopped()) && isAggregatedVisible()) {
            if ((isFocused() || isSelected()) && getLineCount() == 1 && canMarquee()) {
                if (this.mMarqueeFadeMode == 1) {
                    this.mMarqueeFadeMode = 2;
                    android.text.Layout layout = this.mLayout;
                    this.mLayout = this.mSavedMarqueeModeLayout;
                    this.mSavedMarqueeModeLayout = layout;
                    setHorizontalFadingEdgeEnabled(true);
                    requestLayout();
                    invalidate();
                }
                if (this.mMarquee == null) {
                    this.mMarquee = new android.widget.TextView.Marquee(this);
                }
                this.mMarquee.start(this.mMarqueeRepeatLimit);
            }
        }
    }

    protected void stopMarquee() {
        if (this.mMarquee != null && !this.mMarquee.isStopped()) {
            this.mMarquee.stop();
        }
        if (this.mMarqueeFadeMode == 2) {
            this.mMarqueeFadeMode = 1;
            android.text.Layout layout = this.mSavedMarqueeModeLayout;
            this.mSavedMarqueeModeLayout = this.mLayout;
            this.mLayout = layout;
            setHorizontalFadingEdgeEnabled(false);
            requestLayout();
            invalidate();
        }
    }

    private void startStopMarquee(boolean z) {
        if (this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE) {
            if (z) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    protected void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
    }

    protected void onSelectionChanged(int i, int i2) {
        sendAccessibilityEvent(8192);
    }

    public void addTextChangedListener(android.text.TextWatcher textWatcher) {
        if (this.mListeners == null) {
            this.mListeners = new java.util.ArrayList<>();
        }
        this.mListeners.add(textWatcher);
    }

    public void removeTextChangedListener(android.text.TextWatcher textWatcher) {
        int indexOf;
        if (this.mListeners != null && (indexOf = this.mListeners.indexOf(textWatcher)) >= 0) {
            this.mListeners.remove(indexOf);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBeforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (this.mListeners != null) {
            java.util.ArrayList<android.text.TextWatcher> arrayList = this.mListeners;
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.get(i4).beforeTextChanged(charSequence, i, i2, i3);
            }
        }
        int i5 = i2 + i;
        removeIntersectingNonAdjacentSpans(i, i5, android.text.style.SpellCheckSpan.class);
        removeIntersectingNonAdjacentSpans(i, i5, android.text.style.SuggestionSpan.class);
    }

    private <T> void removeIntersectingNonAdjacentSpans(int i, int i2, java.lang.Class<T> cls) {
        if (this.mText instanceof android.text.Editable) {
            android.text.Editable editable = (android.text.Editable) this.mText;
            java.lang.Object[] spans = editable.getSpans(i, i2, cls);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (java.lang.Object obj : spans) {
                int spanStart = editable.getSpanStart(obj);
                if (editable.getSpanEnd(obj) != i && spanStart != i2) {
                    arrayList.add(obj);
                }
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                editable.removeSpan(it.next());
            }
        }
    }

    void removeAdjacentSuggestionSpans(int i) {
        if (this.mText instanceof android.text.Editable) {
            android.text.Editable editable = (android.text.Editable) this.mText;
            android.text.style.SuggestionSpan[] suggestionSpanArr = (android.text.style.SuggestionSpan[]) editable.getSpans(i, i, android.text.style.SuggestionSpan.class);
            int length = suggestionSpanArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                int spanStart = editable.getSpanStart(suggestionSpanArr[i2]);
                int spanEnd = editable.getSpanEnd(suggestionSpanArr[i2]);
                if ((spanEnd == i || spanStart == i) && android.widget.SpellChecker.haveWordBoundariesChanged(editable, i, i, spanStart, spanEnd)) {
                    editable.removeSpan(suggestionSpanArr[i2]);
                }
            }
        }
    }

    void sendOnTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        if (this.mListeners != null) {
            java.util.ArrayList<android.text.TextWatcher> arrayList = this.mListeners;
            int size = arrayList.size();
            for (int i4 = 0; i4 < size; i4++) {
                arrayList.get(i4).onTextChanged(charSequence, i, i2, i3);
            }
        }
        if (this.mEditor != null) {
            this.mEditor.sendOnTextChanged(i, i2, i3);
        }
    }

    void sendAfterTextChanged(android.text.Editable editable) {
        if (this.mListeners != null) {
            java.util.ArrayList<android.text.TextWatcher> arrayList = this.mListeners;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i).afterTextChanged(editable);
            }
        }
        notifyListeningManagersAfterTextChanged();
        hideErrorIfUnchanged();
    }

    private void notifyListeningManagersAfterTextChanged() {
        android.view.autofill.AutofillManager autofillManager;
        if (isAutofillable() && (autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class)) != null) {
            if (android.view.autofill.Helper.sVerbose) {
                android.util.Log.v(LOG_TAG, "notifyAutoFillManagerAfterTextChanged");
            }
            autofillManager.notifyValueChanged(this);
        }
        notifyContentCaptureTextChanged();
    }

    public void notifyContentCaptureTextChanged() {
        android.view.contentcapture.ContentCaptureManager contentCaptureManager;
        android.view.contentcapture.ContentCaptureSession contentCaptureSession;
        if (isLaidOut() && isImportantForContentCapture() && getNotifiedContentCaptureAppeared() && (contentCaptureManager = (android.view.contentcapture.ContentCaptureManager) this.mContext.getSystemService(android.view.contentcapture.ContentCaptureManager.class)) != null && contentCaptureManager.isContentCaptureEnabled() && (contentCaptureSession = getContentCaptureSession()) != null) {
            contentCaptureSession.notifyViewTextChanged(getAutofillId(), getText());
        }
    }

    private boolean isAutofillable() {
        return getAutofillType() != 0;
    }

    void updateAfterEdit() {
        invalidate();
        int selectionStart = getSelectionStart();
        if (selectionStart >= 0 || (this.mGravity & 112) == 80) {
            registerForPreDraw();
        }
        checkForResize();
        if (selectionStart >= 0) {
            this.mHighlightPathBogus = true;
            if (this.mEditor != null) {
                this.mEditor.makeBlink();
            }
            bringPointIntoView(selectionStart);
        }
    }

    void handleTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        sLastCutCopyOrTextChangedTime = 0L;
        android.widget.Editor.InputMethodState inputMethodState = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
            updateAfterEdit();
        }
        if (inputMethodState != null) {
            inputMethodState.mContentChanged = true;
            if (inputMethodState.mChangedStart < 0) {
                inputMethodState.mChangedStart = i;
                inputMethodState.mChangedEnd = i + i2;
            } else {
                inputMethodState.mChangedStart = java.lang.Math.min(inputMethodState.mChangedStart, i);
                inputMethodState.mChangedEnd = java.lang.Math.max(inputMethodState.mChangedEnd, (i + i2) - inputMethodState.mChangedDelta);
            }
            inputMethodState.mChangedDelta += i3 - i2;
        }
        resetErrorChangedFlag();
        sendOnTextChanged(charSequence, i, i2, i3);
        onTextChanged(charSequence, i, i2, i3);
        this.mHideHint = false;
        clearGesturePreviewHighlight();
    }

    void spanChange(android.text.Spanned spanned, java.lang.Object obj, int i, int i2, int i3, int i4) {
        boolean z;
        int i5;
        android.widget.Editor.InputMethodState inputMethodState = this.mEditor == null ? null : this.mEditor.mInputMethodState;
        int i6 = -1;
        if (obj != android.text.Selection.SELECTION_END) {
            z = false;
            i5 = -1;
        } else {
            if (i >= 0 || i2 >= 0) {
                invalidateCursor(android.text.Selection.getSelectionStart(spanned), i, i2);
                checkForResize();
                registerForPreDraw();
                if (this.mEditor != null) {
                    this.mEditor.makeBlink();
                }
            }
            i5 = i2;
            z = true;
        }
        if (obj == android.text.Selection.SELECTION_START) {
            if (i >= 0 || i2 >= 0) {
                invalidateCursor(android.text.Selection.getSelectionEnd(spanned), i, i2);
            }
            i6 = i2;
            z = true;
        }
        if (z) {
            clearGesturePreviewHighlight();
            this.mHighlightPathBogus = true;
            if (this.mEditor != null && !isFocused()) {
                this.mEditor.mSelectionMoved = true;
            }
            if ((spanned.getSpanFlags(obj) & 512) == 0) {
                if (i6 < 0) {
                    i6 = android.text.Selection.getSelectionStart(spanned);
                }
                if (i5 < 0) {
                    i5 = android.text.Selection.getSelectionEnd(spanned);
                }
                if (this.mEditor != null) {
                    this.mEditor.refreshTextActionMode();
                    if (!hasSelection() && this.mEditor.getTextActionMode() == null && hasTransientState()) {
                        setHasTransientState(false);
                    }
                }
                onSelectionChanged(i6, i5);
            }
        }
        if ((obj instanceof android.text.style.UpdateAppearance) || (obj instanceof android.text.style.ParagraphStyle) || (obj instanceof android.text.style.CharacterStyle)) {
            if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
                invalidate();
                this.mHighlightPathBogus = true;
                checkForResize();
            } else {
                inputMethodState.mContentChanged = true;
            }
            if (this.mEditor != null) {
                if (i >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, i, i3);
                }
                if (i2 >= 0) {
                    this.mEditor.invalidateTextDisplayList(this.mLayout, i2, i4);
                }
                this.mEditor.invalidateHandlesAndActionMode();
            }
        }
        if (android.text.method.MetaKeyKeyListener.isMetaTracker(spanned, obj)) {
            this.mHighlightPathBogus = true;
            if (inputMethodState != null && android.text.method.MetaKeyKeyListener.isSelectingMetaTracker(spanned, obj)) {
                inputMethodState.mSelectionModeChanged = true;
            }
            if (android.text.Selection.getSelectionStart(spanned) >= 0) {
                if (inputMethodState == null || inputMethodState.mBatchEditNesting == 0) {
                    invalidateCursor();
                } else {
                    inputMethodState.mCursorChanged = true;
                }
            }
        }
        if ((obj instanceof android.text.ParcelableSpan) && inputMethodState != null && inputMethodState.mExtractedTextRequest != null) {
            if (inputMethodState.mBatchEditNesting != 0) {
                if (i >= 0) {
                    if (inputMethodState.mChangedStart > i) {
                        inputMethodState.mChangedStart = i;
                    }
                    if (inputMethodState.mChangedStart > i3) {
                        inputMethodState.mChangedStart = i3;
                    }
                }
                if (i2 >= 0) {
                    if (inputMethodState.mChangedStart > i2) {
                        inputMethodState.mChangedStart = i2;
                    }
                    if (inputMethodState.mChangedStart > i4) {
                        inputMethodState.mChangedStart = i4;
                    }
                }
            } else {
                inputMethodState.mContentChanged = true;
            }
        }
        if (this.mEditor != null && this.mEditor.mSpellChecker != null && i2 < 0 && (obj instanceof android.text.style.SpellCheckSpan)) {
            this.mEditor.mSpellChecker.onSpellCheckSpanRemoved((android.text.style.SpellCheckSpan) obj);
        }
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean z, int i, android.graphics.Rect rect) {
        if (isTemporarilyDetached()) {
            super.onFocusChanged(z, i, rect);
            return;
        }
        this.mHideHint = false;
        if (this.mEditor != null) {
            this.mEditor.onFocusChanged(z, i);
        }
        if (z && this.mSpannable != null) {
            android.text.method.MetaKeyKeyListener.resetMetaState(this.mSpannable);
        }
        startStopMarquee(z);
        if (this.mTransformation != null) {
            this.mTransformation.onFocusChanged(this, this.mText, z, i, rect);
        }
        super.onFocusChanged(z, i, rect);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (this.mEditor != null) {
            this.mEditor.onWindowFocusChanged(z);
        }
        startStopMarquee(z);
    }

    @Override // android.view.View
    protected void onVisibilityChanged(android.view.View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mEditor != null && i != 0) {
            this.mEditor.hideCursorAndSpanControllers();
            stopTextActionMode();
        }
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        startStopMarquee(z);
    }

    public void clearComposingText() {
        if (this.mText instanceof android.text.Spannable) {
            android.view.inputmethod.BaseInputConnection.removeComposingSpans(this.mSpannable);
        }
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        boolean isSelected = isSelected();
        super.setSelected(z);
        if (z != isSelected && this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE) {
            if (z) {
                startMarquee();
            } else {
                stopMarquee();
            }
        }
    }

    boolean isFromPrimePointer(android.view.MotionEvent motionEvent, boolean z) {
        boolean z2 = false;
        if (this.mPrimePointerId == -1) {
            this.mPrimePointerId = motionEvent.getPointerId(0);
            this.mIsPrimePointerFromHandleView = z;
        } else if (this.mPrimePointerId != motionEvent.getPointerId(0)) {
            if (this.mIsPrimePointerFromHandleView && z) {
                z2 = true;
            }
            if (motionEvent.getActionMasked() != 1 || motionEvent.getActionMasked() == 3) {
                this.mPrimePointerId = -1;
            }
            return z2;
        }
        z2 = true;
        if (motionEvent.getActionMasked() != 1) {
        }
        this.mPrimePointerId = -1;
        return z2;
    }

    @Override // android.view.View
    public boolean onTouchEvent(android.view.MotionEvent motionEvent) {
        boolean z;
        this.mLastInputSource = motionEvent.getSource();
        int actionMasked = motionEvent.getActionMasked();
        if (this.mEditor != null) {
            if (!isFromPrimePointer(motionEvent, false)) {
                return true;
            }
            this.mEditor.onTouchEvent(motionEvent);
            if (this.mEditor.mInsertionPointCursorController != null && this.mEditor.mInsertionPointCursorController.isCursorBeingModified()) {
                return true;
            }
            if (this.mEditor.mSelectionModifierCursorController != null && this.mEditor.mSelectionModifierCursorController.isDragAcceleratorActive()) {
                return true;
            }
        }
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        if (this.mEditor != null && this.mEditor.mDiscardNextActionUp && actionMasked == 1) {
            this.mEditor.mDiscardNextActionUp = false;
            if (this.mEditor.mIsInsertionActionModeStartPending) {
                this.mEditor.startInsertionActionMode();
                this.mEditor.mIsInsertionActionModeStartPending = false;
            }
            return onTouchEvent;
        }
        boolean z2 = actionMasked == 1 && (this.mEditor == null || !this.mEditor.mIgnoreActionUpEvent) && isFocused();
        if ((this.mMovement != null || onCheckIsTextEditor()) && isEnabled() && (this.mText instanceof android.text.Spannable) && this.mLayout != null) {
            if (this.mMovement == null) {
                z = false;
            } else {
                z = this.mMovement.onTouchEvent(this, this.mSpannable, motionEvent) | false;
            }
            boolean isTextSelectable = isTextSelectable();
            if (z2 && this.mLinksClickable && this.mAutoLinkMask != 0 && isTextSelectable) {
                android.text.style.ClickableSpan[] clickableSpanArr = (android.text.style.ClickableSpan[]) this.mSpannable.getSpans(getSelectionStart(), getSelectionEnd(), android.text.style.ClickableSpan.class);
                if (clickableSpanArr.length > 0) {
                    clickableSpanArr[0].onClick(this);
                    z = true;
                }
            }
            if (z2 && (isTextEditable() || isTextSelectable)) {
                android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
                viewClicked(inputMethodManager);
                if (isTextEditable() && this.mEditor.mShowSoftInputOnFocus && inputMethodManager != null && !showAutofillDialog()) {
                    inputMethodManager.showSoftInput(this, 0);
                }
                this.mEditor.onTouchUpEvent(motionEvent);
                z = true;
            }
            if (z) {
                return true;
            }
        }
        return onTouchEvent;
    }

    public final boolean showUIForTouchScreen() {
        return (this.mLastInputSource & 4098) == 4098;
    }

    private boolean showAutofillDialog() {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager != null) {
            return autofillManager.showAutofillDialog(this);
        }
        return false;
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(android.view.MotionEvent motionEvent) {
        if (this.mMovement != null && (this.mText instanceof android.text.Spannable) && this.mLayout != null) {
            try {
                if (this.mMovement.onGenericMotionEvent(this, this.mSpannable, motionEvent)) {
                    return true;
                }
            } catch (java.lang.AbstractMethodError e) {
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onCreateContextMenu(android.view.ContextMenu contextMenu) {
        if (this.mEditor != null) {
            this.mEditor.onCreateContextMenu(contextMenu);
        }
    }

    @Override // android.view.View
    public boolean showContextMenu() {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(Float.NaN, Float.NaN);
        }
        return super.showContextMenu();
    }

    @Override // android.view.View
    public boolean showContextMenu(float f, float f2) {
        if (this.mEditor != null) {
            this.mEditor.setContextMenuAnchor(f, f2);
        }
        return super.showContextMenu(f, f2);
    }

    boolean isTextEditable() {
        return (this.mText instanceof android.text.Editable) && onCheckIsTextEditor() && isEnabled();
    }

    boolean isTextAutofillable() {
        return (this.mText instanceof android.text.Editable) && onCheckIsTextEditor();
    }

    public boolean didTouchFocusSelect() {
        return this.mEditor != null && this.mEditor.mTouchFocusSelected;
    }

    @Override // android.view.View
    public void cancelLongPress() {
        super.cancelLongPress();
        if (this.mEditor != null) {
            this.mEditor.mIgnoreActionUpEvent = true;
        }
    }

    @Override // android.view.View
    public boolean onTrackballEvent(android.view.MotionEvent motionEvent) {
        if (this.mMovement != null && this.mSpannable != null && this.mLayout != null && this.mMovement.onTrackballEvent(this, this.mSpannable, motionEvent)) {
            return true;
        }
        return super.onTrackballEvent(motionEvent);
    }

    public void setScroller(android.widget.Scroller scroller) {
        this.mScroller = scroller;
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            android.widget.TextView.Marquee marquee = this.mMarquee;
            if (marquee.shouldDrawLeftFade()) {
                return getHorizontalFadingEdgeStrength(marquee.getScroll(), 0.0f);
            }
            return 0.0f;
        }
        if (getLineCount() == 1) {
            float lineLeft = getLayout().getLineLeft(0);
            if (lineLeft > this.mScrollX) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(this.mScrollX, lineLeft);
        }
        return super.getLeftFadingEdgeStrength();
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        if (isMarqueeFadeEnabled() && this.mMarquee != null && !this.mMarquee.isStopped()) {
            android.widget.TextView.Marquee marquee = this.mMarquee;
            return getHorizontalFadingEdgeStrength(marquee.getMaxFadeScroll(), marquee.getScroll());
        }
        if (getLineCount() == 1) {
            float width = this.mScrollX + ((getWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight());
            float lineRight = getLayout().getLineRight(0);
            if (lineRight < width) {
                return 0.0f;
            }
            return getHorizontalFadingEdgeStrength(width, lineRight);
        }
        return super.getRightFadingEdgeStrength();
    }

    private float getHorizontalFadingEdgeStrength(float f, float f2) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (horizontalFadingEdgeLength == 0) {
            return 0.0f;
        }
        float abs = java.lang.Math.abs(f - f2);
        float f3 = horizontalFadingEdgeLength;
        if (abs > f3) {
            return 1.0f;
        }
        return abs / f3;
    }

    private boolean isMarqueeFadeEnabled() {
        return this.mEllipsize == android.text.TextUtils.TruncateAt.MARQUEE && this.mMarqueeFadeMode != 1;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        if (this.mLayout != null) {
            return (this.mSingleLine && (this.mGravity & 7) == 3) ? (int) this.mLayout.getLineWidth(0) : this.mLayout.getWidth();
        }
        return super.computeHorizontalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        if (this.mLayout != null) {
            return this.mLayout.getHeight();
        }
        return super.computeVerticalScrollRange();
    }

    @Override // android.view.View
    protected int computeVerticalScrollExtent() {
        return (getHeight() - getCompoundPaddingTop()) - getCompoundPaddingBottom();
    }

    @Override // android.view.View
    public void findViewsWithText(java.util.ArrayList<android.view.View> arrayList, java.lang.CharSequence charSequence, int i) {
        super.findViewsWithText(arrayList, charSequence, i);
        if (!arrayList.contains(this) && (i & 1) != 0 && !android.text.TextUtils.isEmpty(charSequence) && !android.text.TextUtils.isEmpty(this.mText)) {
            if (this.mText.toString().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                arrayList.add(this);
            }
        }
    }

    public static android.content.res.ColorStateList getTextColors(android.content.Context context, android.content.res.TypedArray typedArray) {
        int resourceId;
        if (typedArray == null) {
            throw new java.lang.NullPointerException();
        }
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(android.R.styleable.TextView);
        android.content.res.ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(5);
        if (colorStateList == null && (resourceId = obtainStyledAttributes.getResourceId(1, 0)) != 0) {
            android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, android.R.styleable.TextAppearance);
            colorStateList = obtainStyledAttributes2.getColorStateList(3);
            obtainStyledAttributes2.recycle();
        }
        obtainStyledAttributes.recycle();
        return colorStateList;
    }

    public static int getTextColor(android.content.Context context, android.content.res.TypedArray typedArray, int i) {
        android.content.res.ColorStateList textColors = getTextColors(context, typedArray);
        if (textColors == null) {
            return i;
        }
        return textColors.getDefaultColor();
    }

    @Override // android.view.View
    public boolean onKeyShortcut(int i, android.view.KeyEvent keyEvent) {
        if (keyEvent.hasModifiers(4096)) {
            switch (i) {
                case 29:
                    if (canSelectText()) {
                        return onTextContextMenuItem(16908319);
                    }
                    break;
                case 31:
                    if (canCopy()) {
                        return onTextContextMenuItem(16908321);
                    }
                    break;
                case 50:
                    if (canPaste()) {
                        return onTextContextMenuItem(16908322);
                    }
                    break;
                case 52:
                    if (canCut()) {
                        return onTextContextMenuItem(16908320);
                    }
                    break;
                case 53:
                    if (canRedo()) {
                        return onTextContextMenuItem(16908339);
                    }
                    break;
                case 54:
                    if (canUndo()) {
                        return onTextContextMenuItem(16908338);
                    }
                    break;
            }
        } else if (keyEvent.hasModifiers(4097)) {
            switch (i) {
                case 50:
                    if (canPaste()) {
                        return onTextContextMenuItem(16908337);
                    }
                    break;
                case 54:
                    if (canRedo()) {
                        return onTextContextMenuItem(16908339);
                    }
                    break;
            }
        }
        return super.onKeyShortcut(i, keyEvent);
    }

    boolean canSelectText() {
        return (this.mText.length() == 0 || this.mEditor == null || !this.mEditor.hasSelectionController()) ? false : true;
    }

    boolean textCanBeSelected() {
        if (this.mMovement == null || !this.mMovement.canSelectArbitrarily()) {
            return false;
        }
        return isTextEditable() || (isTextSelectable() && (this.mText instanceof android.text.Spannable) && isEnabled());
    }

    private java.util.Locale getTextServicesLocale(boolean z) {
        updateTextServicesLocaleAsync();
        return (this.mCurrentSpellCheckerLocaleCache != null || z) ? this.mCurrentSpellCheckerLocaleCache : java.util.Locale.getDefault();
    }

    public final void setTextOperationUser(android.os.UserHandle userHandle) {
        if (java.util.Objects.equals(this.mTextOperationUser, userHandle)) {
            return;
        }
        if (userHandle != null && !android.os.Process.myUserHandle().equals(userHandle) && getContext().checkSelfPermission(android.Manifest.permission.INTERACT_ACROSS_USERS_FULL) != 0) {
            throw new java.lang.SecurityException("INTERACT_ACROSS_USERS_FULL is required. userId=" + userHandle.getIdentifier() + " callingUserId" + android.os.UserHandle.myUserId());
        }
        this.mTextOperationUser = userHandle;
        this.mCurrentSpellCheckerLocaleCache = null;
        if (this.mEditor != null) {
            this.mEditor.onTextOperationUserChanged();
        }
    }

    @Override // android.view.View
    public boolean isAutoHandwritingEnabled() {
        return super.isAutoHandwritingEnabled() && !isAnyPasswordInputType();
    }

    @Override // android.view.View
    public boolean isStylusHandwritingAvailable() {
        if (this.mTextOperationUser == null) {
            return super.isStylusHandwritingAvailable();
        }
        return getInputMethodManager().isStylusHandwritingAvailableAsUser(this.mTextOperationUser);
    }

    final android.view.textservice.TextServicesManager getTextServicesManagerForUser() {
        return (android.view.textservice.TextServicesManager) getServiceManagerForUser("android", android.view.textservice.TextServicesManager.class);
    }

    final android.content.ClipboardManager getClipboardManagerForUser() {
        return (android.content.ClipboardManager) getServiceManagerForUser(getContext().getPackageName(), android.content.ClipboardManager.class);
    }

    final android.view.textclassifier.TextClassificationManager getTextClassificationManagerForUser() {
        return (android.view.textclassifier.TextClassificationManager) getServiceManagerForUser(getContext().getPackageName(), android.view.textclassifier.TextClassificationManager.class);
    }

    final <T> T getServiceManagerForUser(java.lang.String str, java.lang.Class<T> cls) {
        if (this.mTextOperationUser == null) {
            return (T) getContext().getSystemService(cls);
        }
        try {
            return (T) getContext().createPackageContextAsUser(str, 0, this.mTextOperationUser).getSystemService(cls);
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    void startActivityAsTextOperationUserIfNecessary(android.content.Intent intent) {
        if (this.mTextOperationUser != null) {
            getContext().startActivityAsUser(intent, this.mTextOperationUser);
        } else {
            getContext().startActivity(intent);
        }
    }

    public java.util.Locale getTextServicesLocale() {
        return getTextServicesLocale(false);
    }

    public boolean isInExtractedMode() {
        return false;
    }

    private boolean isAutoSizeEnabled() {
        return supportsAutoSizeText() && this.mAutoSizeTextType != 0;
    }

    protected boolean supportsAutoSizeText() {
        return true;
    }

    public java.util.Locale getSpellCheckerLocale() {
        return getTextServicesLocale(true);
    }

    private void updateTextServicesLocaleAsync() {
        android.os.AsyncTask.execute(new java.lang.Runnable() { // from class: android.widget.TextView.3
            @Override // java.lang.Runnable
            public void run() {
                android.widget.TextView.this.updateTextServicesLocaleLocked();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTextServicesLocaleLocked() {
        java.util.Locale locale;
        android.view.textservice.TextServicesManager textServicesManagerForUser = getTextServicesManagerForUser();
        if (textServicesManagerForUser == null) {
            return;
        }
        android.view.textservice.SpellCheckerSubtype currentSpellCheckerSubtype = textServicesManagerForUser.getCurrentSpellCheckerSubtype(true);
        if (currentSpellCheckerSubtype != null) {
            locale = currentSpellCheckerSubtype.getLocaleObject();
        } else {
            locale = null;
        }
        this.mCurrentSpellCheckerLocaleCache = locale;
    }

    void onLocaleChanged() {
        this.mEditor.onLocaleChanged();
    }

    public android.text.method.WordIterator getWordIterator() {
        if (this.mEditor != null) {
            return this.mEditor.getWordIterator();
        }
        return null;
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        if (isAccessibilityDataSensitive() && !accessibilityEvent.isAccessibilityDataSensitive()) {
            return;
        }
        java.lang.CharSequence textForAccessibility = getTextForAccessibility();
        if (!android.text.TextUtils.isEmpty(textForAccessibility)) {
            accessibilityEvent.getText().add(textForAccessibility);
        }
    }

    @Override // android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.TextView.class.getName();
    }

    @Override // android.view.View
    protected void onProvideStructure(android.view.ViewStructure viewStructure, int i, int i2) {
        int lineAtCoordinateUnclamped;
        int i3;
        int i4;
        int i5;
        int i6;
        super.onProvideStructure(viewStructure, i, i2);
        boolean z = hasPasswordTransformationMethod() || isPasswordInputType(getInputType());
        if (i == 1 || i == 2) {
            if (i == 1) {
                viewStructure.setDataIsSensitive(!this.mTextSetFromXmlOrResourceId);
            }
            if (this.mTextId != 0) {
                try {
                    viewStructure.setTextIdEntry(getResources().getResourceEntryName(this.mTextId));
                } catch (android.content.res.Resources.NotFoundException e) {
                    if (android.view.autofill.Helper.sVerbose) {
                        android.util.Log.v(LOG_TAG, "onProvideAutofillStructure(): cannot set name for text id " + this.mTextId + ": " + e.getMessage());
                    }
                }
            }
            java.lang.String[] receiveContentMimeTypes = getReceiveContentMimeTypes();
            if (receiveContentMimeTypes == null && this.mEditor != null) {
                receiveContentMimeTypes = this.mEditor.getDefaultOnReceiveContentListener().getFallbackMimeTypesForAutofill(this);
            }
            viewStructure.setReceiveContentMimeTypes(receiveContentMimeTypes);
        }
        if (!z || i == 1 || i == 2) {
            if (this.mLayout == null) {
                if (i == 2) {
                    android.util.Log.w(LOG_TAG, "onProvideContentCaptureStructure(): calling assumeLayout()");
                }
                assumeLayout();
            }
            android.text.Layout layout = this.mLayout;
            int lineCount = layout.getLineCount();
            if (lineCount <= 1) {
                java.lang.CharSequence text = getText();
                if (i == 1) {
                    viewStructure.setText(text);
                } else {
                    viewStructure.setText(text, getSelectionStart(), getSelectionEnd());
                }
                i4 = 0;
            } else {
                int[] iArr = new int[2];
                getLocationInWindow(iArr);
                int i7 = iArr[1];
                android.view.ViewParent parent = getParent();
                android.view.View view = this;
                while (parent instanceof android.view.View) {
                    view = (android.view.View) parent;
                    parent = view.getParent();
                }
                int height = view.getHeight();
                if (i7 >= 0) {
                    i3 = getLineAtCoordinateUnclamped(0.0f);
                    lineAtCoordinateUnclamped = getLineAtCoordinateUnclamped(height - 1);
                } else {
                    int lineAtCoordinateUnclamped2 = getLineAtCoordinateUnclamped(-i7);
                    lineAtCoordinateUnclamped = getLineAtCoordinateUnclamped((height - 1) - i7);
                    i3 = lineAtCoordinateUnclamped2;
                }
                int i8 = lineAtCoordinateUnclamped - i3;
                int i9 = i8 / 2;
                int i10 = i3 - i9;
                if (i10 < 0) {
                    i10 = 0;
                }
                int i11 = i9 + lineAtCoordinateUnclamped;
                if (i11 >= lineCount) {
                    i11 = lineCount - 1;
                }
                int transformedToOriginal = transformedToOriginal(layout.getLineStart(i10), 0);
                int transformedToOriginal2 = transformedToOriginal(layout.getLineEnd(i11), 0);
                int selectionStart = getSelectionStart();
                int selectionEnd = getSelectionEnd();
                if (selectionStart < selectionEnd) {
                    if (selectionStart < transformedToOriginal) {
                        transformedToOriginal = selectionStart;
                    }
                    if (selectionEnd > transformedToOriginal2) {
                        transformedToOriginal2 = selectionEnd;
                    }
                }
                java.lang.CharSequence text2 = getText();
                if (text2 == null) {
                    i4 = 0;
                } else {
                    if (transformedToOriginal > 0 || transformedToOriginal2 < text2.length()) {
                        transformedToOriginal = java.lang.Math.min(transformedToOriginal, text2.length());
                        text2 = text2.subSequence(transformedToOriginal, java.lang.Math.min(transformedToOriginal2, text2.length()));
                    }
                    if (i == 1) {
                        viewStructure.setText(text2);
                        i4 = 0;
                    } else {
                        viewStructure.setText(text2, selectionStart - transformedToOriginal, selectionEnd - transformedToOriginal);
                        int i12 = i8 + 1;
                        int[] iArr2 = new int[i12];
                        int[] iArr3 = new int[i12];
                        int baselineOffset = getBaselineOffset();
                        for (int i13 = i3; i13 <= lineAtCoordinateUnclamped; i13++) {
                            int i14 = i13 - i3;
                            iArr2[i14] = transformedToOriginal(layout.getLineStart(i13), 0);
                            iArr3[i14] = layout.getLineBaseline(i13) + baselineOffset;
                        }
                        i4 = 0;
                        viewStructure.setTextLines(iArr2, iArr3);
                    }
                }
            }
            if (i == 0 || i == 2) {
                int typefaceStyle = getTypefaceStyle();
                if ((typefaceStyle & 1) == 0) {
                    i5 = i4;
                } else {
                    i5 = 1;
                }
                if ((typefaceStyle & 2) != 0) {
                    i5 |= 2;
                }
                int flags = this.mTextPaint.getFlags();
                if ((flags & 32) != 0) {
                    i5 |= 1;
                }
                if ((flags & 8) != 0) {
                    i5 |= 4;
                }
                if ((flags & 16) != 0) {
                    i5 |= 8;
                }
                viewStructure.setTextStyle(getTextSize(), getCurrentTextColor(), 1, i5);
            }
            if (i == 1 || i == 2) {
                viewStructure.setMinTextEms(getMinEms());
                viewStructure.setMaxTextEms(getMaxEms());
                android.text.InputFilter[] filters = getFilters();
                int length = filters.length;
                int i15 = i4;
                while (true) {
                    if (i15 >= length) {
                        i6 = -1;
                        break;
                    }
                    android.text.InputFilter inputFilter = filters[i15];
                    if (!(inputFilter instanceof android.text.InputFilter.LengthFilter)) {
                        i15++;
                    } else {
                        i6 = ((android.text.InputFilter.LengthFilter) inputFilter).getMax();
                        break;
                    }
                }
                viewStructure.setMaxTextLength(i6);
            }
        }
        if (this.mHintId != 0) {
            try {
                viewStructure.setHintIdEntry(getResources().getResourceEntryName(this.mHintId));
            } catch (android.content.res.Resources.NotFoundException e2) {
                if (android.view.autofill.Helper.sVerbose) {
                    android.util.Log.v(LOG_TAG, "onProvideAutofillStructure(): cannot set name for hint id " + this.mHintId + ": " + e2.getMessage());
                }
            }
        }
        viewStructure.setHint(getHint());
        viewStructure.setInputType(getInputType());
    }

    boolean canRequestAutofill() {
        android.view.autofill.AutofillManager autofillManager;
        if (isAutofillable() && (autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class)) != null) {
            return autofillManager.isEnabled();
        }
        return false;
    }

    private void requestAutofill() {
        android.view.autofill.AutofillManager autofillManager = (android.view.autofill.AutofillManager) this.mContext.getSystemService(android.view.autofill.AutofillManager.class);
        if (autofillManager != null) {
            autofillManager.requestAutofill(this);
        }
    }

    @Override // android.view.View
    public void autofill(android.view.autofill.AutofillValue autofillValue) {
        if (!isTextAutofillable()) {
            android.util.Log.w(LOG_TAG, "cannot autofill non-editable TextView: " + this);
        } else if (!autofillValue.isText()) {
            android.util.Log.w(LOG_TAG, "value of type " + autofillValue.describeContents() + " cannot be autofilled into " + this);
        } else {
            performReceiveContent(new android.view.ContentInfo.Builder(android.content.ClipData.newPlainText("", autofillValue.getTextValue()), 4).build());
        }
    }

    @Override // android.view.View
    public int getAutofillType() {
        return isTextAutofillable() ? 1 : 0;
    }

    @Override // android.view.View
    public android.view.autofill.AutofillValue getAutofillValue() {
        if (isTextAutofillable()) {
            return android.view.autofill.AutofillValue.forText(android.text.TextUtils.trimToParcelableSize(getText()));
        }
        return null;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setPassword(hasPasswordTransformationMethod());
        if (accessibilityEvent.getEventType() == 8192) {
            accessibilityEvent.setFromIndex(android.text.Selection.getSelectionStart(this.mText));
            accessibilityEvent.setToIndex(android.text.Selection.getSelectionEnd(this.mText));
            accessibilityEvent.setItemCount(this.mText.length());
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setPassword(hasPasswordTransformationMethod());
        accessibilityNodeInfo.setText(getTextForAccessibility());
        accessibilityNodeInfo.setHintText(this.mHint);
        accessibilityNodeInfo.setShowingHintText(isShowingHint());
        if (this.mBufferType == android.widget.TextView.BufferType.EDITABLE) {
            accessibilityNodeInfo.setEditable(true);
            if (isEnabled()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_TEXT);
            }
        }
        if (this.mEditor != null) {
            accessibilityNodeInfo.setInputType(this.mEditor.mInputType);
            if (this.mEditor.mError != null) {
                accessibilityNodeInfo.setContentInvalid(true);
                accessibilityNodeInfo.setError(this.mEditor.mError);
            }
            if (isTextEditable() && isFocused()) {
                java.lang.CharSequence string = this.mContext.getResources().getString(com.android.internal.R.string.keyboardview_keycode_enter);
                if (getImeActionLabel() != null) {
                    string = getImeActionLabel();
                }
                accessibilityNodeInfo.addAction(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(16908372, string));
            }
        }
        if (!android.text.TextUtils.isEmpty(this.mText)) {
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(31);
            accessibilityNodeInfo.addAction(131072);
            accessibilityNodeInfo.setAvailableExtraData(java.util.Arrays.asList(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY, android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY));
            accessibilityNodeInfo.setTextSelectable(isTextSelectable() || isTextEditable());
        } else {
            accessibilityNodeInfo.setAvailableExtraData(java.util.Arrays.asList(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY));
        }
        if (isFocused()) {
            if (canCopy()) {
                accessibilityNodeInfo.addAction(16384);
            }
            if (canPaste()) {
                accessibilityNodeInfo.addAction(32768);
            }
            if (canCut()) {
                accessibilityNodeInfo.addAction(65536);
            }
            if (canReplace()) {
                accessibilityNodeInfo.addAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TEXT_SUGGESTIONS);
            }
            if (canShare()) {
                accessibilityNodeInfo.addAction(new android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction(268435456, getResources().getString(com.android.internal.R.string.share)));
            }
            if (canProcessText()) {
                this.mEditor.mProcessTextIntentActionsHandler.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                this.mEditor.onInitializeSmartActionsAccessibilityNodeInfo(accessibilityNodeInfo);
            }
        }
        int length = this.mFilters.length;
        for (int i = 0; i < length; i++) {
            android.text.InputFilter inputFilter = this.mFilters[i];
            if (inputFilter instanceof android.text.InputFilter.LengthFilter) {
                accessibilityNodeInfo.setMaxTextLength(((android.text.InputFilter.LengthFilter) inputFilter).getMax());
            }
        }
        if (!isSingleLine()) {
            accessibilityNodeInfo.setMultiLine(true);
        }
        if (accessibilityNodeInfo.isClickable() || accessibilityNodeInfo.isLongClickable()) {
            if ((this.mMovement instanceof android.text.method.LinkMovementMethod) || (isTextSelectable() && !isTextEditable())) {
                if (!hasOnClickListeners()) {
                    accessibilityNodeInfo.setClickable(false);
                    accessibilityNodeInfo.removeAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                }
                if (!hasOnLongClickListeners()) {
                    accessibilityNodeInfo.setLongClickable(false);
                    accessibilityNodeInfo.removeAction(android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                }
            }
        }
    }

    @Override // android.view.View
    public void addExtraDataToAccessibilityNodeInfo(android.view.accessibility.AccessibilityNodeInfo accessibilityNodeInfo, java.lang.String str, android.os.Bundle bundle) {
        android.graphics.RectF characterBounds;
        if (bundle != null && str.equals(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY)) {
            int i = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX, -1);
            int i2 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1);
            if (i2 <= 0 || i < 0 || i >= this.mText.length()) {
                android.util.Log.e(LOG_TAG, "Invalid arguments for accessibility character locations");
                return;
            }
            android.graphics.RectF[] rectFArr = new android.graphics.RectF[i2];
            android.view.inputmethod.CursorAnchorInfo.Builder builder = new android.view.inputmethod.CursorAnchorInfo.Builder();
            populateCharacterBounds(builder, i, java.lang.Math.min(i + i2, length()), viewportToContentHorizontalOffset(), viewportToContentVerticalOffset());
            android.view.inputmethod.CursorAnchorInfo build = builder.setMatrix(null).build();
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i + i3;
                if ((build.getCharacterBoundsFlags(i4) & 1) == 1 && (characterBounds = build.getCharacterBounds(i4)) != null) {
                    mapRectFromViewToScreenCoords(characterBounds, true);
                    rectFArr[i3] = characterBounds;
                }
            }
            accessibilityNodeInfo.getExtras().putParcelableArray(str, rectFArr);
            return;
        }
        if (str.equals(android.view.accessibility.AccessibilityNodeInfo.EXTRA_DATA_RENDERING_INFO_KEY)) {
            android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo obtain = android.view.accessibility.AccessibilityNodeInfo.ExtraRenderingInfo.obtain();
            obtain.setLayoutSize(getLayoutParams().width, getLayoutParams().height);
            obtain.setTextSizeInPx(getTextSize());
            obtain.setTextSizeUnit(getTextSizeUnit());
            accessibilityNodeInfo.setExtraRenderingInfo(obtain);
        }
    }

    private boolean getViewVisibleRect(android.graphics.Rect rect) {
        if (!getLocalVisibleRect(rect)) {
            return false;
        }
        rect.offset(-getScrollX(), -getScrollY());
        return true;
    }

    private boolean getContentVisibleRect(android.graphics.Rect rect) {
        if (!getViewVisibleRect(rect)) {
            return false;
        }
        return rect.intersect(getCompoundPaddingLeft(), getCompoundPaddingTop(), getWidth() - getCompoundPaddingRight(), getHeight() - getCompoundPaddingBottom());
    }

    public void populateCharacterBounds(android.view.inputmethod.CursorAnchorInfo.Builder builder, int i, int i2, float f, float f2) {
        int i3;
        if (isOffsetMappingAvailable()) {
            return;
        }
        android.graphics.Rect rect = new android.graphics.Rect();
        getContentVisibleRect(rect);
        android.graphics.RectF rectF = new android.graphics.RectF(rect);
        float[] characterBounds = getCharacterBounds(i, i2, f, f2);
        int i4 = i2 - i;
        for (int i5 = 0; i5 < i4; i5++) {
            int i6 = i5 * 4;
            float f3 = characterBounds[i6];
            float f4 = characterBounds[i6 + 1];
            float f5 = characterBounds[i6 + 2];
            float f6 = characterBounds[i6 + 3];
            boolean intersects = rectF.intersects(f3, f4, f5, f6);
            int i7 = 1;
            boolean z = !rectF.contains(f3, f4, f5, f6);
            if (!intersects) {
                i7 = 0;
            }
            if (z) {
                i7 |= 2;
            }
            if (!this.mLayout.isRtlCharAt(i5)) {
                i3 = i7;
            } else {
                i3 = i7 | 4;
            }
            builder.addCharacterBounds(i5 + i, f3, f4, f5, f6, i3);
        }
    }

    private float[] getCharacterBounds(int i, int i2, float f, float f2) {
        int i3 = i2 - i;
        float[] fArr = new float[i3 * 4];
        this.mLayout.fillCharacterBounds(i, i2, fArr, 0);
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = i4 * 4;
            fArr[i5] = fArr[i5] + f;
            int i6 = i5 + 1;
            fArr[i6] = fArr[i6] + f2;
            int i7 = i5 + 2;
            fArr[i7] = fArr[i7] + f;
            int i8 = i5 + 3;
            fArr[i8] = fArr[i8] + f2;
        }
        return fArr;
    }

    public android.view.inputmethod.CursorAnchorInfo getCursorAnchorInfo(int i, android.view.inputmethod.CursorAnchorInfo.Builder builder, android.graphics.Matrix matrix) {
        float f;
        int i2;
        android.graphics.RectF rectF;
        android.graphics.RectF rectF2;
        android.text.Layout layout = getLayout();
        if (layout == null) {
            return null;
        }
        int i3 = 1;
        boolean z = (i & 4) != 0;
        boolean z2 = (i & 8) != 0;
        boolean z3 = (i & 16) != 0;
        boolean z4 = (i & 32) != 0;
        boolean z5 = (i & 64) != 0;
        boolean z6 = (z || z2 || z3 || z4 || z5) ? false : true;
        boolean z7 = z | z6;
        boolean z8 = z2 | z6;
        boolean z9 = z3 | z6;
        boolean z10 = z4 | z6;
        boolean z11 = z5 | z6;
        builder.reset();
        int selectionStart = getSelectionStart();
        builder.setSelectionRange(selectionStart, getSelectionEnd());
        matrix.reset();
        transformMatrixToGlobal(matrix);
        builder.setMatrix(matrix);
        if (z7) {
            if (this.mTempRect == null) {
                this.mTempRect = new android.graphics.Rect();
            }
            android.graphics.Rect rect = this.mTempRect;
            if (getViewVisibleRect(rect)) {
                rectF = new android.graphics.RectF(rect);
                rectF2 = new android.graphics.RectF(rectF);
                rectF2.top -= getHandwritingBoundsOffsetTop();
                rectF2.left -= getHandwritingBoundsOffsetLeft();
                rectF2.bottom += getHandwritingBoundsOffsetBottom();
                rectF2.right += getHandwritingBoundsOffsetRight();
            } else {
                rectF = new android.graphics.RectF();
                rectF2 = new android.graphics.RectF();
            }
            builder.setEditorBoundsInfo(new android.view.inputmethod.EditorBoundsInfo.Builder().setEditorBounds(rectF).setHandwritingBounds(rectF2).build());
        }
        if (z8 || z9 || z10) {
            float viewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
            float viewportToContentVerticalOffset = viewportToContentVerticalOffset();
            boolean z12 = getTransformationMethod() != null && (getTransformed() instanceof android.text.method.OffsetMapping);
            if (!z8 || z12) {
                f = viewportToContentVerticalOffset;
            } else {
                java.lang.CharSequence text = getText();
                if (!(text instanceof android.text.Spannable)) {
                    f = viewportToContentVerticalOffset;
                } else {
                    android.text.Spannable spannable = (android.text.Spannable) text;
                    int composingSpanStart = com.android.internal.inputmethod.EditableInputConnection.getComposingSpanStart(spannable);
                    int composingSpanEnd = com.android.internal.inputmethod.EditableInputConnection.getComposingSpanEnd(spannable);
                    if (composingSpanEnd >= composingSpanStart) {
                        i2 = composingSpanEnd;
                    } else {
                        i2 = composingSpanStart;
                        composingSpanStart = composingSpanEnd;
                    }
                    if (!(composingSpanStart >= 0 && composingSpanStart < i2)) {
                        f = viewportToContentVerticalOffset;
                    } else {
                        builder.setComposingText(composingSpanStart, text.subSequence(composingSpanStart, i2));
                        f = viewportToContentVerticalOffset;
                        populateCharacterBounds(builder, composingSpanStart, i2, viewportToContentHorizontalOffset, viewportToContentVerticalOffset);
                    }
                }
            }
            if (z9 && selectionStart >= 0) {
                int originalToTransformed = originalToTransformed(selectionStart, 1);
                float primaryHorizontal = layout.getPrimaryHorizontal(originalToTransformed, layout.shouldClampCursor(layout.getLineForOffset(originalToTransformed))) + viewportToContentHorizontalOffset;
                float lineTop = layout.getLineTop(r1) + f;
                float lineBaseline = layout.getLineBaseline(r1) + f;
                float lineBottom = layout.getLineBottom(r1, false) + f;
                boolean isPositionVisible = isPositionVisible(primaryHorizontal, lineTop);
                boolean isPositionVisible2 = isPositionVisible(primaryHorizontal, lineBottom);
                if (!isPositionVisible && !isPositionVisible2) {
                    i3 = 0;
                }
                if (!isPositionVisible || !isPositionVisible2) {
                    i3 |= 2;
                }
                if (layout.isRtlCharAt(originalToTransformed)) {
                    i3 |= 4;
                }
                builder.setInsertionMarkerLocation(primaryHorizontal, lineTop, lineBaseline, lineBottom, i3);
            }
            if (z10) {
                if (getContentVisibleRect(new android.graphics.Rect())) {
                    float f2 = r0.top - f;
                    int lineForVertical = layout.getLineForVertical((int) java.lang.Math.ceil(r0.bottom - f));
                    for (int lineForVertical2 = layout.getLineForVertical((int) java.lang.Math.floor(f2)); lineForVertical2 <= lineForVertical; lineForVertical2++) {
                        builder.addVisibleLineBounds(layout.getLineLeft(lineForVertical2) + viewportToContentHorizontalOffset, layout.getLineTop(lineForVertical2) + f, layout.getLineRight(lineForVertical2) + viewportToContentHorizontalOffset, layout.getLineBottom(lineForVertical2, false) + f);
                    }
                }
            }
        }
        if (z11) {
            builder.setTextAppearanceInfo(android.view.inputmethod.TextAppearanceInfo.createFromTextView(this));
        }
        return builder.build();
    }

    public android.view.inputmethod.TextBoundsInfo getTextBoundsInfo(android.graphics.RectF rectF) {
        java.lang.CharSequence text;
        int i;
        android.text.Layout layout = getLayout();
        if (layout == null || (text = layout.getText()) == null || isOffsetMappingAvailable()) {
            return null;
        }
        android.graphics.Matrix matrix = new android.graphics.Matrix();
        transformMatrixToGlobal(matrix);
        android.graphics.Matrix matrix2 = new android.graphics.Matrix();
        if (!matrix.invert(matrix2)) {
            return null;
        }
        float viewportToContentHorizontalOffset = viewportToContentHorizontalOffset();
        float viewportToContentVerticalOffset = viewportToContentVerticalOffset();
        android.graphics.RectF rectF2 = new android.graphics.RectF(rectF);
        matrix2.mapRect(rectF2);
        rectF2.offset(-viewportToContentHorizontalOffset, -viewportToContentVerticalOffset);
        if (!rectF2.intersects(0.0f, 0.0f, layout.getWidth(), layout.getHeight()) || text.length() == 0) {
            android.view.inputmethod.TextBoundsInfo.Builder builder = new android.view.inputmethod.TextBoundsInfo.Builder(0, 0);
            android.text.SegmentFinder.PrescribedSegmentFinder prescribedSegmentFinder = new android.text.SegmentFinder.PrescribedSegmentFinder(new int[0]);
            builder.setMatrix(matrix).setCharacterBounds(new float[0]).setCharacterBidiLevel(new int[0]).setCharacterFlags(new int[0]).setGraphemeSegmentFinder(prescribedSegmentFinder).setLineSegmentFinder(prescribedSegmentFinder).setWordSegmentFinder(prescribedSegmentFinder);
            return builder.build();
        }
        int lineForVertical = layout.getLineForVertical((int) java.lang.Math.floor(rectF2.top));
        int lineForVertical2 = layout.getLineForVertical((int) java.lang.Math.floor(rectF2.bottom));
        int lineStart = layout.getLineStart(lineForVertical);
        int lineEnd = layout.getLineEnd(lineForVertical2);
        float[] characterBounds = getCharacterBounds(lineStart, lineEnd, viewportToContentHorizontalOffset, viewportToContentVerticalOffset);
        int i2 = lineEnd - lineStart;
        int[] iArr = new int[i2];
        int[] iArr2 = new int[i2];
        int i3 = lineForVertical;
        while (i3 <= lineForVertical2) {
            int lineStart2 = layout.getLineStart(i3);
            int lineEnd2 = layout.getLineEnd(i3);
            android.text.Layout.Directions lineDirections = layout.getLineDirections(i3);
            int i4 = 0;
            while (i4 < lineDirections.getRunCount()) {
                int runStart = lineDirections.getRunStart(i4) + lineStart2;
                java.util.Arrays.fill(iArr2, runStart - lineStart, java.lang.Math.min(runStart + lineDirections.getRunLength(i4), lineEnd2) - lineStart, lineDirections.getRunLevel(i4));
                i4++;
                characterBounds = characterBounds;
                lineStart2 = lineStart2;
            }
            float[] fArr = characterBounds;
            int i5 = lineStart2;
            boolean z = layout.getParagraphDirection(i3) == -1;
            for (int i6 = i5; i6 < lineEnd2; i6++) {
                if (!android.text.TextUtils.isWhitespace(text.charAt(i6))) {
                    i = 0;
                } else {
                    i = 1;
                }
                if (android.text.TextUtils.isPunctuation(java.lang.Character.codePointAt(text, i6))) {
                    i |= 4;
                }
                if (android.text.TextUtils.isNewline(java.lang.Character.codePointAt(text, i6))) {
                    i |= 2;
                }
                if (z) {
                    i |= 8;
                }
                iArr[i6 - lineStart] = i;
            }
            i3++;
            characterBounds = fArr;
        }
        float[] fArr2 = characterBounds;
        android.text.GraphemeClusterSegmentFinder graphemeClusterSegmentFinder = new android.text.GraphemeClusterSegmentFinder(text, layout.getPaint());
        android.text.method.WordIterator wordIterator = getWordIterator();
        wordIterator.setCharSequence(text, 0, text.length());
        android.text.WordSegmentFinder wordSegmentFinder = new android.text.WordSegmentFinder(text, wordIterator);
        int[] iArr3 = new int[((lineForVertical2 - lineForVertical) + 1) * 2];
        for (int i7 = lineForVertical; i7 <= lineForVertical2; i7++) {
            int i8 = (i7 - lineForVertical) * 2;
            iArr3[i8] = layout.getLineStart(i7);
            iArr3[i8 + 1] = layout.getLineEnd(i7);
        }
        return new android.view.inputmethod.TextBoundsInfo.Builder(lineStart, lineEnd).setMatrix(matrix).setCharacterBounds(fArr2).setCharacterBidiLevel(iArr2).setCharacterFlags(iArr).setGraphemeSegmentFinder(graphemeClusterSegmentFinder).setLineSegmentFinder(new android.text.SegmentFinder.PrescribedSegmentFinder(iArr3)).setWordSegmentFinder(wordSegmentFinder).build();
    }

    public boolean isPositionVisible(float f, float f2) {
        synchronized (TEMP_POSITION) {
            float[] fArr = TEMP_POSITION;
            fArr[0] = f;
            fArr[1] = f2;
            android.view.View view = this;
            while (view != null) {
                if (view != this) {
                    fArr[0] = fArr[0] - view.getScrollX();
                    fArr[1] = fArr[1] - view.getScrollY();
                }
                if (fArr[0] >= 0.0f && fArr[1] >= 0.0f && fArr[0] <= view.getWidth() && fArr[1] <= view.getHeight()) {
                    if (!view.getMatrix().isIdentity()) {
                        view.getMatrix().mapPoints(fArr);
                    }
                    fArr[0] = fArr[0] + view.getLeft();
                    fArr[1] = fArr[1] + view.getTop();
                    java.lang.Object parent = view.getParent();
                    if (parent instanceof android.view.View) {
                        view = (android.view.View) parent;
                    } else {
                        view = null;
                    }
                }
                return false;
            }
            return true;
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, android.os.Bundle bundle) {
        int i2;
        int i3;
        int length;
        if (this.mEditor != null && (this.mEditor.mProcessTextIntentActionsHandler.performAccessibilityAction(i) || this.mEditor.performSmartActionsAccessibilityAction(i))) {
            return true;
        }
        switch (i) {
            case 16:
                return performAccessibilityActionClick(bundle);
            case 32:
                if (!isLongClickable()) {
                    return false;
                }
                if (isEnabled() && this.mBufferType == android.widget.TextView.BufferType.EDITABLE) {
                    this.mEditor.mIsBeingLongClickedByAccessibility = true;
                    try {
                        return performLongClick();
                    } finally {
                        this.mEditor.mIsBeingLongClickedByAccessibility = false;
                    }
                }
                return performLongClick();
            case 256:
            case 512:
                ensureIterableTextForAccessibilitySelectable();
                return super.performAccessibilityActionInternal(i, bundle);
            case 16384:
                return isFocused() && canCopy() && onTextContextMenuItem(16908321);
            case 32768:
                return isFocused() && canPaste() && onTextContextMenuItem(16908322);
            case 65536:
                return isFocused() && canCut() && onTextContextMenuItem(16908320);
            case 131072:
                ensureIterableTextForAccessibilitySelectable();
                java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
                if (iterableTextForAccessibility == null) {
                    return false;
                }
                if (bundle != null) {
                    i2 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_START_INT, -1);
                } else {
                    i2 = -1;
                }
                if (bundle != null) {
                    i3 = bundle.getInt(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SELECTION_END_INT, -1);
                } else {
                    i3 = -1;
                }
                if (getSelectionStart() != i2 || getSelectionEnd() != i3) {
                    if (i2 == i3 && i3 == -1) {
                        android.text.Selection.removeSelection((android.text.Spannable) iterableTextForAccessibility);
                        return true;
                    }
                    if (i2 >= 0 && i2 <= i3 && i3 <= iterableTextForAccessibility.length()) {
                        requestFocusOnNonEditableSelectableText();
                        android.text.Selection.setSelection((android.text.Spannable) iterableTextForAccessibility, i2, i3);
                        if (this.mEditor != null) {
                            this.mEditor.startSelectionActionModeAsync(false);
                        }
                        return true;
                    }
                }
                return false;
            case 2097152:
                if (!isEnabled() || this.mBufferType != android.widget.TextView.BufferType.EDITABLE) {
                    return false;
                }
                lambda$setTextAsync$0(bundle != null ? bundle.getCharSequence(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE) : null);
                if (this.mText != null && (length = this.mText.length()) > 0) {
                    android.text.Selection.setSelection(this.mSpannable, length);
                }
                return true;
            case 16908372:
                if (isFocused() && isTextEditable()) {
                    onEditorAction(getImeActionId());
                }
                return true;
            case 268435456:
                return isFocused() && canShare() && onTextContextMenuItem(16908341);
            default:
                if (i == 16908376) {
                    return isFocused() && canReplace() && onTextContextMenuItem(16908340);
                }
                return super.performAccessibilityActionInternal(i, bundle);
        }
    }

    private boolean performAccessibilityActionClick(android.os.Bundle bundle) {
        boolean z;
        if (!isEnabled()) {
            return false;
        }
        if (isClickable() || isLongClickable()) {
            if (isFocusable() && !isFocused()) {
                requestFocus();
            }
            performClick();
            z = true;
        } else {
            z = false;
        }
        if ((this.mMovement != null || onCheckIsTextEditor()) && hasSpannableText() && this.mLayout != null) {
            if ((isTextEditable() || isTextSelectable()) && isFocused()) {
                android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
                viewClicked(inputMethodManager);
                if (!isTextSelectable() && this.mEditor.mShowSoftInputOnFocus && inputMethodManager != null) {
                    return z | inputMethodManager.showSoftInput(this, 0);
                }
                return z;
            }
            return z;
        }
        return z;
    }

    private void requestFocusOnNonEditableSelectableText() {
        if (!isTextEditable() && isTextSelectable() && isEnabled() && isFocusable() && !isFocused()) {
            requestFocus();
        }
    }

    private boolean hasSpannableText() {
        return this.mText != null && (this.mText instanceof android.text.Spannable);
    }

    @Override // android.view.View
    public void sendAccessibilityEventInternal(int i) {
        if (i == 32768 && this.mEditor != null) {
            this.mEditor.mProcessTextIntentActionsHandler.initializeAccessibilityActions();
        }
        super.sendAccessibilityEventInternal(i);
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(android.view.accessibility.AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 4096) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    private java.lang.CharSequence getTextForAccessibility() {
        if (android.text.TextUtils.isEmpty(this.mText)) {
            return this.mHint;
        }
        return android.text.TextUtils.trimToParcelableSize(this.mTransformed);
    }

    boolean isVisibleToAccessibility() {
        return android.view.accessibility.AccessibilityManager.getInstance(this.mContext).isEnabled() && (isFocused() || (isSelected() && isShown()));
    }

    void sendAccessibilityEventTypeViewTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(16);
        obtain.setFromIndex(i);
        obtain.setRemovedCount(i2);
        obtain.setAddedCount(i3);
        obtain.setBeforeText(charSequence);
        sendAccessibilityEventUnchecked(obtain);
    }

    void sendAccessibilityEventTypeViewTextChanged(java.lang.CharSequence charSequence, int i, int i2) {
        android.view.accessibility.AccessibilityEvent obtain = android.view.accessibility.AccessibilityEvent.obtain(16);
        obtain.setFromIndex(i);
        obtain.setToIndex(i2);
        obtain.setBeforeText(charSequence);
        sendAccessibilityEventUnchecked(obtain);
    }

    private android.view.inputmethod.InputMethodManager getInputMethodManager() {
        return (android.view.inputmethod.InputMethodManager) getContext().getSystemService(android.view.inputmethod.InputMethodManager.class);
    }

    public boolean isInputMethodTarget() {
        android.view.inputmethod.InputMethodManager inputMethodManager = getInputMethodManager();
        return inputMethodManager != null && inputMethodManager.isActive(this);
    }

    public boolean onTextContextMenuItem(int i) {
        int i2;
        int length = this.mText.length();
        if (!isFocused()) {
            i2 = 0;
        } else {
            int selectionStart = getSelectionStart();
            int selectionEnd = getSelectionEnd();
            i2 = java.lang.Math.max(0, java.lang.Math.min(selectionStart, selectionEnd));
            length = java.lang.Math.max(0, java.lang.Math.max(selectionStart, selectionEnd));
        }
        switch (i) {
            case 16908319:
                boolean hasSelection = hasSelection();
                selectAllText();
                if (this.mEditor != null && hasSelection) {
                    this.mEditor.invalidateActionModeAsync();
                }
                return true;
            case 16908320:
                if (setPrimaryClip(android.content.ClipData.newPlainText(null, getTransformedText(i2, length)))) {
                    deleteText_internal(i2, length);
                } else {
                    android.widget.Toast.makeText(getContext(), com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908321:
                int selectionStart2 = getSelectionStart();
                int selectionEnd2 = getSelectionEnd();
                if (setPrimaryClip(android.content.ClipData.newPlainText(null, getTransformedText(java.lang.Math.max(0, java.lang.Math.min(selectionStart2, selectionEnd2)), java.lang.Math.max(0, java.lang.Math.max(selectionStart2, selectionEnd2)))))) {
                    stopTextActionMode();
                } else {
                    android.widget.Toast.makeText(getContext(), com.android.internal.R.string.failed_to_copy_to_clipboard, 0).show();
                }
                return true;
            case 16908322:
                paste(true);
                return true;
            case 16908337:
                paste(false);
                return true;
            case 16908338:
                if (this.mEditor != null) {
                    this.mEditor.undo();
                }
                return true;
            case 16908339:
                if (this.mEditor != null) {
                    this.mEditor.redo();
                }
                return true;
            case 16908340:
                if (this.mEditor != null) {
                    this.mEditor.replace();
                }
                return true;
            case 16908341:
                shareSelectedText();
                return true;
            case 16908355:
                requestAutofill();
                stopTextActionMode();
                return true;
            default:
                return false;
        }
    }

    java.lang.CharSequence getTransformedText(int i, int i2) {
        return removeSuggestionSpans(this.mTransformed.subSequence(i, i2));
    }

    @Override // android.view.View
    public boolean performLongClick() {
        boolean z;
        boolean z2;
        if (this.mEditor != null) {
            this.mEditor.mIsBeingLongClicked = true;
        }
        if (!super.performLongClick()) {
            z = false;
            z2 = false;
        } else {
            z = true;
            z2 = true;
        }
        if (this.mEditor != null) {
            z |= this.mEditor.performLongClick(z);
            this.mEditor.mIsBeingLongClicked = false;
        }
        if (z) {
            if (!z2) {
                performHapticFeedback(0);
            }
            if (this.mEditor != null) {
                this.mEditor.mDiscardNextActionUp = true;
            }
        } else {
            com.android.internal.logging.MetricsLogger.action(this.mContext, 629, 0);
        }
        return z;
    }

    @Override // android.view.View
    protected void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.mEditor != null) {
            this.mEditor.onScrollChanged();
        }
    }

    public boolean isSuggestionsEnabled() {
        if (this.mEditor == null || (this.mEditor.mInputType & 15) != 1 || (this.mEditor.mInputType & 524288) > 0) {
            return false;
        }
        int i = this.mEditor.mInputType & android.text.InputType.TYPE_MASK_VARIATION;
        return i == 0 || i == 48 || i == 80 || i == 64 || i == 160;
    }

    public void setCustomSelectionActionModeCallback(android.view.ActionMode.Callback callback) {
        createEditorIfNeeded();
        this.mEditor.mCustomSelectionActionModeCallback = callback;
    }

    public android.view.ActionMode.Callback getCustomSelectionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomSelectionActionModeCallback;
    }

    public void setCustomInsertionActionModeCallback(android.view.ActionMode.Callback callback) {
        createEditorIfNeeded();
        this.mEditor.mCustomInsertionActionModeCallback = callback;
    }

    public android.view.ActionMode.Callback getCustomInsertionActionModeCallback() {
        if (this.mEditor == null) {
            return null;
        }
        return this.mEditor.mCustomInsertionActionModeCallback;
    }

    public void setTextClassifier(android.view.textclassifier.TextClassifier textClassifier) {
        this.mTextClassifier = textClassifier;
    }

    public android.view.textclassifier.TextClassifier getTextClassifier() {
        if (this.mTextClassifier == null) {
            android.view.textclassifier.TextClassificationManager textClassificationManagerForUser = getTextClassificationManagerForUser();
            if (textClassificationManagerForUser != null) {
                return textClassificationManagerForUser.getTextClassifier();
            }
            return android.view.textclassifier.TextClassifier.NO_OP;
        }
        return this.mTextClassifier;
    }

    android.view.textclassifier.TextClassifier getTextClassificationSession() {
        java.lang.String str;
        if (this.mTextClassificationSession == null || this.mTextClassificationSession.isDestroyed()) {
            android.view.textclassifier.TextClassificationManager textClassificationManagerForUser = getTextClassificationManagerForUser();
            if (textClassificationManagerForUser != null) {
                if (isTextEditable()) {
                    str = android.view.textclassifier.TextClassifier.WIDGET_TYPE_EDITTEXT;
                } else if (isTextSelectable()) {
                    str = android.view.textclassifier.TextClassifier.WIDGET_TYPE_TEXTVIEW;
                } else {
                    str = android.view.textclassifier.TextClassifier.WIDGET_TYPE_UNSELECTABLE_TEXTVIEW;
                }
                this.mTextClassificationContext = new android.view.textclassifier.TextClassificationContext.Builder(this.mContext.getPackageName(), str).build();
                if (this.mTextClassifier != null) {
                    this.mTextClassificationSession = textClassificationManagerForUser.createTextClassificationSession(this.mTextClassificationContext, this.mTextClassifier);
                } else {
                    this.mTextClassificationSession = textClassificationManagerForUser.createTextClassificationSession(this.mTextClassificationContext);
                }
            } else {
                this.mTextClassificationSession = android.view.textclassifier.TextClassifier.NO_OP;
            }
        }
        return this.mTextClassificationSession;
    }

    android.view.textclassifier.TextClassificationContext getTextClassificationContext() {
        return this.mTextClassificationContext;
    }

    boolean usesNoOpTextClassifier() {
        return getTextClassifier() == android.view.textclassifier.TextClassifier.NO_OP;
    }

    public boolean requestActionMode(android.view.textclassifier.TextLinks.TextLinkSpan textLinkSpan) {
        com.android.internal.util.Preconditions.checkNotNull(textLinkSpan);
        if (!(this.mText instanceof android.text.Spanned)) {
            return false;
        }
        int spanStart = ((android.text.Spanned) this.mText).getSpanStart(textLinkSpan);
        int spanEnd = ((android.text.Spanned) this.mText).getSpanEnd(textLinkSpan);
        if (spanStart < 0 || spanEnd > this.mText.length() || spanStart >= spanEnd) {
            return false;
        }
        createEditorIfNeeded();
        this.mEditor.startLinkActionModeAsync(spanStart, spanEnd);
        return true;
    }

    public boolean handleClick(android.view.textclassifier.TextLinks.TextLinkSpan textLinkSpan) {
        com.android.internal.util.Preconditions.checkNotNull(textLinkSpan);
        if (this.mText instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) this.mText;
            int spanStart = spanned.getSpanStart(textLinkSpan);
            int spanEnd = spanned.getSpanEnd(textLinkSpan);
            if (spanStart >= 0 && spanEnd <= this.mText.length() && spanStart < spanEnd) {
                final android.view.textclassifier.TextClassification.Request build = new android.view.textclassifier.TextClassification.Request.Builder(this.mText, spanStart, spanEnd).setDefaultLocales(getTextLocales()).build();
                java.util.function.Supplier supplier = new java.util.function.Supplier() { // from class: android.widget.TextView$$ExternalSyntheticLambda1
                    @Override // java.util.function.Supplier
                    public final java.lang.Object get() {
                        android.view.textclassifier.TextClassification lambda$handleClick$5;
                        lambda$handleClick$5 = android.widget.TextView.this.lambda$handleClick$5(build);
                        return lambda$handleClick$5;
                    }
                };
                java.util.concurrent.CompletableFuture.supplyAsync(supplier).completeOnTimeout(null, 1L, java.util.concurrent.TimeUnit.SECONDS).thenAccept(new java.util.function.Consumer() { // from class: android.widget.TextView$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(java.lang.Object obj) {
                        android.widget.TextView.lambda$handleClick$6((android.view.textclassifier.TextClassification) obj);
                    }
                });
                return true;
            }
            return false;
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ android.view.textclassifier.TextClassification lambda$handleClick$5(android.view.textclassifier.TextClassification.Request request) {
        return getTextClassificationSession().classifyText(request);
    }

    static /* synthetic */ void lambda$handleClick$6(android.view.textclassifier.TextClassification textClassification) {
        if (textClassification == null) {
            android.util.Log.d(LOG_TAG, "Timeout while classifying text");
            return;
        }
        if (textClassification.getActions().isEmpty()) {
            android.util.Log.d(LOG_TAG, "No link action to perform");
            return;
        }
        try {
            textClassification.getActions().get(0).getActionIntent().send();
        } catch (android.app.PendingIntent.CanceledException e) {
            android.util.Log.e(LOG_TAG, "Error sending PendingIntent", e);
        }
    }

    protected void stopTextActionMode() {
        if (this.mEditor != null) {
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    public void hideFloatingToolbar(int i) {
        if (this.mEditor != null) {
            this.mEditor.hideFloatingToolbar(i);
        }
    }

    boolean canUndo() {
        return this.mEditor != null && this.mEditor.canUndo();
    }

    boolean canRedo() {
        return this.mEditor != null && this.mEditor.canRedo();
    }

    boolean canCut() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && hasSelection() && (this.mText instanceof android.text.Editable) && this.mEditor != null && this.mEditor.mKeyListener != null;
    }

    boolean canCopy() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && hasSelection() && this.mEditor != null;
    }

    boolean canReplace() {
        return !hasPasswordTransformationMethod() && this.mText.length() > 0 && (this.mText instanceof android.text.Editable) && this.mEditor != null && isSuggestionsEnabled() && this.mEditor.shouldOfferToShowSuggestions();
    }

    boolean canShare() {
        if (!getContext().canStartActivityForResult() || !isDeviceProvisioned() || !getContext().getResources().getBoolean(com.android.internal.R.bool.config_textShareSupported)) {
            return false;
        }
        return canCopy();
    }

    boolean isDeviceProvisioned() {
        int i;
        if (this.mDeviceProvisionedState == 0) {
            if (android.provider.Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
                i = 2;
            } else {
                i = 1;
            }
            this.mDeviceProvisionedState = i;
        }
        return this.mDeviceProvisionedState == 2;
    }

    boolean canPaste() {
        return (this.mText instanceof android.text.Editable) && this.mEditor != null && this.mEditor.mKeyListener != null && getSelectionStart() >= 0 && getSelectionEnd() >= 0 && getClipboardManagerForUser().hasPrimaryClip();
    }

    boolean canPasteAsPlainText() {
        android.content.ClipDescription primaryClipDescription;
        if (canPaste() && (primaryClipDescription = getClipboardManagerForUser().getPrimaryClipDescription()) != null) {
            return (primaryClipDescription.hasMimeType("text/plain") && primaryClipDescription.isStyledText()) || primaryClipDescription.hasMimeType("text/html");
        }
        return false;
    }

    boolean canProcessText() {
        if (getId() == -1) {
            return false;
        }
        return canShare();
    }

    boolean canSelectAllText() {
        return (!canSelectText() || hasPasswordTransformationMethod() || (getSelectionStart() == 0 && getSelectionEnd() == this.mText.length())) ? false : true;
    }

    boolean selectAllText() {
        if (this.mEditor != null) {
            hideFloatingToolbar(500);
        }
        int length = this.mText.length();
        android.text.Selection.setSelection(this.mSpannable, 0, length);
        return length > 0;
    }

    private void paste(boolean z) {
        android.content.ClipData primaryClip = getClipboardManagerForUser().getPrimaryClip();
        if (primaryClip == null) {
            return;
        }
        performReceiveContent(new android.view.ContentInfo.Builder(primaryClip, 1).setFlags(!z ? 1 : 0).build());
        sLastCutCopyOrTextChangedTime = 0L;
    }

    private void shareSelectedText() {
        java.lang.String selectedText = getSelectedText();
        if (selectedText != null && !selectedText.isEmpty()) {
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.removeExtra(android.content.Intent.EXTRA_TEXT);
            intent.putExtra(android.content.Intent.EXTRA_TEXT, (java.lang.String) android.text.TextUtils.trimToParcelableSize(selectedText));
            getContext().startActivity(android.content.Intent.createChooser(intent, null));
            android.text.Selection.setSelection(this.mSpannable, getSelectionEnd());
        }
    }

    private boolean setPrimaryClip(android.content.ClipData clipData) {
        try {
            getClipboardManagerForUser().setPrimaryClip(clipData);
            sLastCutCopyOrTextChangedTime = android.os.SystemClock.uptimeMillis();
            return true;
        } catch (java.lang.Throwable th) {
            return false;
        }
    }

    public int getOffsetForPosition(float f, float f2) {
        if (getLayout() == null) {
            return -1;
        }
        return getOffsetAtCoordinate(getLineAtCoordinate(f2), f);
    }

    float convertToLocalHorizontalCoordinate(float f) {
        return java.lang.Math.min((getWidth() - getTotalPaddingRight()) - 1, java.lang.Math.max(0.0f, f - getTotalPaddingLeft())) + getScrollX();
    }

    int getLineAtCoordinate(float f) {
        return getLayout().getLineForVertical((int) (java.lang.Math.min((getHeight() - getTotalPaddingBottom()) - 1, java.lang.Math.max(0.0f, f - getTotalPaddingTop())) + getScrollY()));
    }

    int getLineAtCoordinateUnclamped(float f) {
        return getLayout().getLineForVertical((int) ((f - getTotalPaddingTop()) + getScrollY()));
    }

    int getOffsetAtCoordinate(int i, float f) {
        return transformedToOriginal(getLayout().getOffsetForHorizontal(i, convertToLocalHorizontalCoordinate(f)), 1);
    }

    public int transformedToOriginal(int i, int i2) {
        if (getTransformationMethod() == null) {
            return i;
        }
        if (this.mTransformed instanceof android.text.method.OffsetMapping) {
            return ((android.text.method.OffsetMapping) this.mTransformed).transformedToOriginal(i, i2);
        }
        return i;
    }

    public int originalToTransformed(int i, int i2) {
        if (getTransformationMethod() == null) {
            return i;
        }
        if (this.mTransformed instanceof android.text.method.OffsetMapping) {
            return ((android.text.method.OffsetMapping) this.mTransformed).originalToTransformed(i, i2);
        }
        return i;
    }

    @Override // android.view.View
    public boolean onDragEvent(android.view.DragEvent dragEvent) {
        if (this.mEditor == null || !this.mEditor.hasInsertionController()) {
            return super.onDragEvent(dragEvent);
        }
        switch (dragEvent.getAction()) {
            case 2:
                if (this.mText instanceof android.text.Spannable) {
                    android.text.Selection.setSelection(this.mSpannable, getOffsetForPosition(dragEvent.getX(), dragEvent.getY()));
                    break;
                }
                break;
            case 3:
                if (this.mEditor != null) {
                    this.mEditor.onDrop(dragEvent);
                    break;
                }
                break;
            case 5:
                requestFocus();
                break;
        }
        return true;
    }

    boolean isInBatchEditMode() {
        if (this.mEditor == null) {
            return false;
        }
        android.widget.Editor.InputMethodState inputMethodState = this.mEditor.mInputMethodState;
        if (inputMethodState != null) {
            return inputMethodState.mBatchEditNesting > 0;
        }
        return this.mEditor.mInBatchEditControllers;
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        android.text.TextDirectionHeuristic textDirectionHeuristic = getTextDirectionHeuristic();
        if (this.mTextDir != textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            if (this.mLayout != null) {
                checkForRelayout();
            }
        }
    }

    public android.text.TextDirectionHeuristic getTextDirectionHeuristic() {
        if (hasPasswordTransformationMethod()) {
            return android.text.TextDirectionHeuristics.LTR;
        }
        if (this.mEditor != null && (this.mEditor.mInputType & 15) == 3) {
            byte directionality = java.lang.Character.getDirectionality(android.icu.text.DecimalFormatSymbols.getInstance(getTextLocale()).getDigitStrings()[0].codePointAt(0));
            if (directionality == 1 || directionality == 2) {
                return android.text.TextDirectionHeuristics.RTL;
            }
            return android.text.TextDirectionHeuristics.LTR;
        }
        boolean z = getLayoutDirection() == 1;
        switch (getTextDirection()) {
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                if (!z) {
                    break;
                } else {
                    break;
                }
        }
        return android.text.TextDirectionHeuristics.LTR;
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        if (this.mLastLayoutDirection == i) {
            return;
        }
        this.mLastLayoutDirection = i;
        if (this.mDrawables != null && this.mDrawables.resolveWithLayoutDirection(i)) {
            prepareDrawableForDisplay(this.mDrawables.mShowing[0]);
            prepareDrawableForDisplay(this.mDrawables.mShowing[2]);
            applyCompoundDrawableTint();
        }
    }

    private void prepareDrawableForDisplay(android.graphics.drawable.Drawable drawable) {
        if (drawable == null) {
            return;
        }
        drawable.setLayoutDirection(getLayoutDirection());
        if (drawable.isStateful()) {
            drawable.setState(getDrawableState());
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    protected void resetResolvedDrawables() {
        super.resetResolvedDrawables();
        this.mLastLayoutDirection = -1;
    }

    protected void viewClicked(android.view.inputmethod.InputMethodManager inputMethodManager) {
        if (inputMethodManager != null) {
            inputMethodManager.viewClicked(this);
        }
    }

    protected void deleteText_internal(int i, int i2) {
        ((android.text.Editable) this.mText).delete(i, i2);
    }

    protected void replaceText_internal(int i, int i2, java.lang.CharSequence charSequence) {
        ((android.text.Editable) this.mText).replace(i, i2, charSequence);
    }

    protected void setSpan_internal(java.lang.Object obj, int i, int i2, int i3) {
        ((android.text.Editable) this.mText).setSpan(obj, i, i2, i3);
    }

    protected void setCursorPosition_internal(int i, int i2) {
        android.text.Selection.setSelection((android.text.Editable) this.mText, i, i2);
    }

    private void createEditorIfNeeded() {
        if (this.mEditor == null) {
            this.mEditor = new android.widget.Editor(this);
        }
    }

    @Override // android.view.View
    public java.lang.CharSequence getIterableTextForAccessibility() {
        return this.mText;
    }

    private void ensureIterableTextForAccessibilitySelectable() {
        if (!(this.mText instanceof android.text.Spannable)) {
            setText(this.mText, android.widget.TextView.BufferType.SPANNABLE);
            if (getLayout() == null) {
                assumeLayout();
            }
        }
    }

    @Override // android.view.View
    public android.view.AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int i) {
        switch (i) {
            case 4:
                android.text.Spannable spannable = (android.text.Spannable) getIterableTextForAccessibility();
                if (!android.text.TextUtils.isEmpty(spannable) && getLayout() != null) {
                    android.widget.AccessibilityIterators.LineTextSegmentIterator lineTextSegmentIterator = android.widget.AccessibilityIterators.LineTextSegmentIterator.getInstance();
                    lineTextSegmentIterator.initialize(spannable, getLayout());
                    return lineTextSegmentIterator;
                }
                break;
            case 16:
                if (!android.text.TextUtils.isEmpty((android.text.Spannable) getIterableTextForAccessibility()) && getLayout() != null) {
                    android.widget.AccessibilityIterators.PageTextSegmentIterator pageTextSegmentIterator = android.widget.AccessibilityIterators.PageTextSegmentIterator.getInstance();
                    pageTextSegmentIterator.initialize(this);
                    return pageTextSegmentIterator;
                }
                break;
        }
        return super.getIteratorForGranularity(i);
    }

    @Override // android.view.View
    public int getAccessibilitySelectionStart() {
        return getSelectionStart();
    }

    @Override // android.view.View
    public boolean isAccessibilitySelectionExtendable() {
        return true;
    }

    @Override // android.view.View
    public void prepareForExtendedAccessibilitySelection() {
        requestFocusOnNonEditableSelectableText();
    }

    @Override // android.view.View
    public int getAccessibilitySelectionEnd() {
        return getSelectionEnd();
    }

    @Override // android.view.View
    public void setAccessibilitySelection(int i, int i2) {
        if (getAccessibilitySelectionStart() == i && getAccessibilitySelectionEnd() == i2) {
            return;
        }
        java.lang.CharSequence iterableTextForAccessibility = getIterableTextForAccessibility();
        if (java.lang.Math.min(i, i2) >= 0 && java.lang.Math.max(i, i2) <= iterableTextForAccessibility.length()) {
            android.text.Selection.setSelection((android.text.Spannable) iterableTextForAccessibility, i, i2);
        } else {
            android.text.Selection.removeSelection((android.text.Spannable) iterableTextForAccessibility);
        }
        if (this.mEditor != null) {
            this.mEditor.hideCursorAndSpanControllers();
            this.mEditor.lambda$startActionModeInternal$0();
        }
    }

    @Override // android.view.View
    protected void encodeProperties(android.view.ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        android.text.TextUtils.TruncateAt ellipsize = getEllipsize();
        viewHierarchyEncoder.addProperty("text:ellipsize", ellipsize == null ? null : ellipsize.name());
        viewHierarchyEncoder.addProperty("text:textSize", getTextSize());
        viewHierarchyEncoder.addProperty("text:scaledTextSize", getScaledTextSize());
        viewHierarchyEncoder.addProperty("text:typefaceStyle", getTypefaceStyle());
        viewHierarchyEncoder.addProperty("text:selectionStart", getSelectionStart());
        viewHierarchyEncoder.addProperty("text:selectionEnd", getSelectionEnd());
        viewHierarchyEncoder.addProperty("text:curTextColor", this.mCurTextColor);
        viewHierarchyEncoder.addUserProperty("text:text", this.mText != null ? this.mText.toString() : null);
        viewHierarchyEncoder.addProperty("text:gravity", this.mGravity);
    }

    public static class SavedState extends android.view.View.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.widget.TextView.SavedState> CREATOR = new android.os.Parcelable.Creator<android.widget.TextView.SavedState>() { // from class: android.widget.TextView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.TextView.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.widget.TextView.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.widget.TextView.SavedState[] newArray(int i) {
                return new android.widget.TextView.SavedState[i];
            }
        };
        android.os.ParcelableParcel editorState;
        java.lang.CharSequence error;
        boolean frozenWithFocus;
        int selEnd;
        int selStart;
        java.lang.CharSequence text;

        SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
            this.selStart = -1;
            this.selEnd = -1;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.selStart);
            parcel.writeInt(this.selEnd);
            parcel.writeInt(this.frozenWithFocus ? 1 : 0);
            android.text.TextUtils.writeToParcel(this.text, parcel, i);
            if (this.error == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                android.text.TextUtils.writeToParcel(this.error, parcel, i);
            }
            if (this.editorState == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                this.editorState.writeToParcel(parcel, i);
            }
        }

        public java.lang.String toString() {
            java.lang.String str = "TextView.SavedState{" + java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)) + " start=" + this.selStart + " end=" + this.selEnd;
            if (this.text != null) {
                str = str + " text=" + ((java.lang.Object) this.text);
            }
            return str + "}";
        }

        private SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.selStart = -1;
            this.selEnd = -1;
            this.selStart = parcel.readInt();
            this.selEnd = parcel.readInt();
            this.frozenWithFocus = parcel.readInt() != 0;
            this.text = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() != 0) {
                this.error = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.editorState = android.os.ParcelableParcel.CREATOR.createFromParcel(parcel);
            }
        }
    }

    private static class CharWrapper implements java.lang.CharSequence, android.text.GetChars, android.text.GraphicsOperations {
        private char[] mChars;
        private int mLength;
        private int mStart;

        CharWrapper(char[] cArr, int i, int i2) {
            this.mChars = cArr;
            this.mStart = i;
            this.mLength = i2;
        }

        void set(char[] cArr, int i, int i2) {
            this.mChars = cArr;
            this.mStart = i;
            this.mLength = i2;
        }

        @Override // java.lang.CharSequence
        public int length() {
            return this.mLength;
        }

        @Override // java.lang.CharSequence
        public char charAt(int i) {
            return this.mChars[i + this.mStart];
        }

        @Override // java.lang.CharSequence
        public java.lang.String toString() {
            return new java.lang.String(this.mChars, this.mStart, this.mLength);
        }

        @Override // java.lang.CharSequence
        public java.lang.CharSequence subSequence(int i, int i2) {
            if (i < 0 || i2 < 0 || i > this.mLength || i2 > this.mLength) {
                throw new java.lang.IndexOutOfBoundsException(i + ", " + i2);
            }
            return new java.lang.String(this.mChars, this.mStart + i, i2 - i);
        }

        @Override // android.text.GetChars
        public void getChars(int i, int i2, char[] cArr, int i3) {
            if (i < 0 || i2 < 0 || i > this.mLength || i2 > this.mLength) {
                throw new java.lang.IndexOutOfBoundsException(i + ", " + i2);
            }
            java.lang.System.arraycopy(this.mChars, this.mStart + i, cArr, i3, i2 - i);
        }

        @Override // android.text.GraphicsOperations
        public void drawText(android.graphics.BaseCanvas baseCanvas, int i, int i2, float f, float f2, android.graphics.Paint paint) {
            baseCanvas.drawText(this.mChars, i + this.mStart, i2 - i, f, f2, paint);
        }

        @Override // android.text.GraphicsOperations
        public void drawTextRun(android.graphics.BaseCanvas baseCanvas, int i, int i2, int i3, int i4, float f, float f2, boolean z, android.graphics.Paint paint) {
            baseCanvas.drawTextRun(this.mChars, this.mStart + i, i2 - i, i3 + this.mStart, i4 - i3, f, f2, z, paint);
        }

        @Override // android.text.GraphicsOperations
        public float measureText(int i, int i2, android.graphics.Paint paint) {
            return paint.measureText(this.mChars, this.mStart + i, i2 - i);
        }

        @Override // android.text.GraphicsOperations
        public int getTextWidths(int i, int i2, float[] fArr, android.graphics.Paint paint) {
            return paint.getTextWidths(this.mChars, this.mStart + i, i2 - i, fArr);
        }

        @Override // android.text.GraphicsOperations
        public float getTextRunAdvances(int i, int i2, int i3, int i4, boolean z, float[] fArr, int i5, android.graphics.Paint paint) {
            return paint.getTextRunAdvances(this.mChars, this.mStart + i, i2 - i, i3 + this.mStart, i4 - i3, z, fArr, i5);
        }

        @Override // android.text.GraphicsOperations
        public int getTextRunCursor(int i, int i2, boolean z, int i3, int i4, android.graphics.Paint paint) {
            return paint.getTextRunCursor(this.mChars, i + this.mStart, i2 - i, z, i3 + this.mStart, i4);
        }
    }

    private static final class Marquee {
        private static final int MARQUEE_DELAY = 1200;
        private static final float MARQUEE_DELTA_MAX = 0.07f;
        private static final int MARQUEE_DP_PER_SECOND = 30;
        private static final byte MARQUEE_RUNNING = 2;
        private static final byte MARQUEE_STARTING = 1;
        private static final byte MARQUEE_STOPPED = 0;
        private float mFadeStop;
        private float mGhostOffset;
        private float mGhostStart;
        private long mLastAnimationMs;
        private float mMaxFadeScroll;
        private float mMaxScroll;
        private final float mPixelsPerMs;
        private int mRepeatLimit;
        private float mScroll;
        private final java.lang.ref.WeakReference<android.widget.TextView> mView;
        private byte mStatus = 0;
        private android.view.Choreographer.FrameCallback mTickCallback = new android.view.Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                android.widget.TextView.Marquee.this.tick();
            }
        };
        private android.view.Choreographer.FrameCallback mStartCallback = new android.view.Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.2
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                android.widget.TextView.Marquee.this.mStatus = (byte) 2;
                android.widget.TextView.Marquee.this.mLastAnimationMs = android.widget.TextView.Marquee.this.mChoreographer.getFrameTime();
                android.widget.TextView.Marquee.this.tick();
            }
        };
        private android.view.Choreographer.FrameCallback mRestartCallback = new android.view.Choreographer.FrameCallback() { // from class: android.widget.TextView.Marquee.3
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                if (android.widget.TextView.Marquee.this.mStatus == 2) {
                    if (android.widget.TextView.Marquee.this.mRepeatLimit >= 0) {
                        android.widget.TextView.Marquee marquee = android.widget.TextView.Marquee.this;
                        marquee.mRepeatLimit--;
                    }
                    android.widget.TextView.Marquee.this.start(android.widget.TextView.Marquee.this.mRepeatLimit);
                }
            }
        };
        private final android.view.Choreographer mChoreographer = android.view.Choreographer.getInstance();

        Marquee(android.widget.TextView textView) {
            this.mPixelsPerMs = (textView.getContext().getResources().getDisplayMetrics().density * 30.0f) / 1000.0f;
            this.mView = new java.lang.ref.WeakReference<>(textView);
        }

        void tick() {
            if (this.mStatus != 2) {
                return;
            }
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            android.widget.TextView textView = this.mView.get();
            if (textView != null && textView.isAggregatedVisible()) {
                if (textView.isFocused() || textView.isSelected()) {
                    long frameTime = this.mChoreographer.getFrameTime();
                    long j = frameTime - this.mLastAnimationMs;
                    this.mLastAnimationMs = frameTime;
                    this.mScroll += j * this.mPixelsPerMs;
                    if (this.mScroll > this.mMaxScroll) {
                        this.mScroll = this.mMaxScroll;
                        this.mChoreographer.postFrameCallbackDelayed(this.mRestartCallback, 1200L);
                    } else {
                        this.mChoreographer.postFrameCallback(this.mTickCallback);
                    }
                    textView.invalidate();
                }
            }
        }

        void stop() {
            this.mStatus = (byte) 0;
            this.mChoreographer.removeFrameCallback(this.mStartCallback);
            this.mChoreographer.removeFrameCallback(this.mRestartCallback);
            this.mChoreographer.removeFrameCallback(this.mTickCallback);
            resetScroll();
        }

        private void resetScroll() {
            this.mScroll = 0.0f;
            android.widget.TextView textView = this.mView.get();
            if (textView != null) {
                textView.invalidate();
            }
        }

        void start(int i) {
            if (i == 0) {
                stop();
                return;
            }
            this.mRepeatLimit = i;
            android.widget.TextView textView = this.mView.get();
            if (textView != null && textView.mLayout != null) {
                this.mStatus = (byte) 1;
                this.mScroll = 0.0f;
                int width = (textView.getWidth() - textView.getCompoundPaddingLeft()) - textView.getCompoundPaddingRight();
                float lineWidth = textView.mLayout.getLineWidth(0);
                float f = width;
                float f2 = f / 3.0f;
                this.mGhostStart = (lineWidth - f) + f2;
                this.mMaxScroll = this.mGhostStart + f;
                this.mGhostOffset = f2 + lineWidth;
                this.mFadeStop = (f / 6.0f) + lineWidth;
                this.mMaxFadeScroll = this.mGhostStart + lineWidth + lineWidth;
                textView.invalidate();
                this.mChoreographer.postFrameCallback(this.mStartCallback);
            }
        }

        float getGhostOffset() {
            return this.mGhostOffset;
        }

        float getScroll() {
            return this.mScroll;
        }

        float getMaxFadeScroll() {
            return this.mMaxFadeScroll;
        }

        boolean shouldDrawLeftFade() {
            return this.mScroll <= this.mFadeStop;
        }

        boolean shouldDrawGhost() {
            return this.mStatus == 2 && this.mScroll > this.mGhostStart;
        }

        boolean isRunning() {
            return this.mStatus == 2;
        }

        boolean isStopped() {
            return this.mStatus == 0;
        }
    }

    private class ChangeWatcher implements android.text.TextWatcher, android.text.SpanWatcher {
        private java.lang.CharSequence mBeforeText;

        private ChangeWatcher() {
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            if (android.view.accessibility.AccessibilityManager.getInstance(android.widget.TextView.this.mContext).isEnabled() && android.widget.TextView.this.mTransformed != null) {
                this.mBeforeText = android.widget.TextView.this.mTransformed.toString();
            }
            android.widget.TextView.this.sendBeforeTextChanged(charSequence, i, i2, i3);
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(java.lang.CharSequence charSequence, int i, int i2, int i3) {
            android.widget.TextView.this.handleTextChanged(charSequence, i, i2, i3);
            if (android.widget.TextView.this.isVisibleToAccessibility()) {
                android.widget.TextView.this.sendAccessibilityEventTypeViewTextChanged(this.mBeforeText, i, i2, i3);
                this.mBeforeText = null;
            }
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(android.text.Editable editable) {
            android.widget.TextView.this.sendAfterTextChanged(editable);
            if (android.text.method.MetaKeyKeyListener.getMetaState(editable, 2048) != 0) {
                android.text.method.MetaKeyKeyListener.stopSelecting(android.widget.TextView.this, editable);
            }
        }

        @Override // android.text.SpanWatcher
        public void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4) {
            android.widget.TextView.this.spanChange(spannable, obj, i, i3, i2, i4);
        }

        @Override // android.text.SpanWatcher
        public void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            android.widget.TextView.this.spanChange(spannable, obj, -1, i, -1, i2);
        }

        @Override // android.text.SpanWatcher
        public void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
            android.widget.TextView.this.spanChange(spannable, obj, i, -1, i2, -1);
        }
    }

    @Override // android.view.View
    public void onInputConnectionOpenedInternal(android.view.inputmethod.InputConnection inputConnection, android.view.inputmethod.EditorInfo editorInfo, android.os.Handler handler) {
        if (this.mEditor != null) {
            this.mEditor.getDefaultOnReceiveContentListener().setInputConnectionInfo(this, inputConnection, editorInfo);
        }
    }

    @Override // android.view.View
    public void onInputConnectionClosedInternal() {
        if (this.mEditor != null) {
            this.mEditor.getDefaultOnReceiveContentListener().clearInputConnectionInfo();
        }
    }

    @Override // android.view.View
    public android.view.ContentInfo onReceiveContent(android.view.ContentInfo contentInfo) {
        if (this.mEditor != null) {
            return this.mEditor.getDefaultOnReceiveContentListener().onReceiveContent(this, contentInfo);
        }
        return contentInfo;
    }

    private static void logCursor(java.lang.String str, java.lang.String str2, java.lang.Object... objArr) {
        if (str2 != null) {
            android.util.Log.d(LOG_TAG, str + ": " + java.lang.String.format(str2, objArr));
        } else {
            android.util.Log.d(LOG_TAG, str);
        }
    }

    @Override // android.view.View
    public void onCreateViewTranslationRequest(int[] iArr, java.util.function.Consumer<android.view.translation.ViewTranslationRequest> consumer) {
        if (iArr == null || iArr.length == 0) {
            if (android.view.translation.UiTranslationController.DEBUG) {
                android.util.Log.w(LOG_TAG, "Do not provide the support translation formats.");
                return;
            }
            return;
        }
        android.view.translation.ViewTranslationRequest.Builder builder = new android.view.translation.ViewTranslationRequest.Builder(getAutofillId());
        boolean z = true;
        if (com.android.internal.util.ArrayUtils.contains(iArr, 1)) {
            if (this.mText == null || this.mText.length() == 0) {
                if (android.view.translation.UiTranslationController.DEBUG) {
                    android.util.Log.w(LOG_TAG, "Cannot create translation request for the empty text.");
                    return;
                }
                return;
            }
            if (!isAnyPasswordInputType() && !hasPasswordTransformationMethod()) {
                z = false;
            }
            if (isTextEditable() || z) {
                android.util.Log.w(LOG_TAG, "Cannot create translation request. editable = " + isTextEditable() + ", isPassword = " + z);
                return;
            } else {
                builder.setValue(android.view.translation.ViewTranslationRequest.ID_TEXT, android.view.translation.TranslationRequestValue.forText(this.mText));
                if (!android.text.TextUtils.isEmpty(getContentDescription())) {
                    builder.setValue(android.view.translation.ViewTranslationRequest.ID_CONTENT_DESCRIPTION, android.view.translation.TranslationRequestValue.forText(getContentDescription()));
                }
            }
        }
        consumer.accept(builder.build());
    }
}
