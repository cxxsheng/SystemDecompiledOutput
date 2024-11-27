package android.app.assist;

/* loaded from: classes.dex */
public class AssistStructure implements android.os.Parcelable {
    private static final boolean DEBUG_PARCEL = false;
    private static final boolean DEBUG_PARCEL_CHILDREN = false;
    private static final boolean DEBUG_PARCEL_TREE = false;
    private static final java.lang.String DESCRIPTOR = "android.app.AssistStructure";
    private static final java.lang.String TAG = "AssistStructure";
    private static final int TRANSACTION_XFER = 2;
    private static final int VALIDATE_VIEW_TOKEN = 572662306;
    private static final int VALIDATE_WINDOW_TOKEN = 286331153;
    private long mAcquisitionEndTime;
    private long mAcquisitionStartTime;
    private android.content.ComponentName mActivityComponent;
    private int mAutofillFlags;
    private int mFlags;
    private boolean mHaveData;
    private boolean mIsHomeActivity;
    private final java.util.ArrayList<android.app.assist.AssistStructure.ViewNodeBuilder> mPendingAsyncChildren;
    private android.os.IBinder mReceiveChannel;
    private boolean mSanitizeOnWrite;
    private android.app.assist.AssistStructure.SendChannel mSendChannel;
    private int mTaskId;
    private android.graphics.Rect mTmpRect;
    private final java.util.ArrayList<android.app.assist.AssistStructure.WindowNode> mWindowNodes;
    public static final android.os.Parcelable.Creator<android.app.assist.AssistStructure> CREATOR = new android.os.Parcelable.Creator<android.app.assist.AssistStructure>() { // from class: android.app.assist.AssistStructure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.AssistStructure createFromParcel(android.os.Parcel parcel) {
            return new android.app.assist.AssistStructure(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.assist.AssistStructure[] newArray(int i) {
            return new android.app.assist.AssistStructure[i];
        }
    };
    private static final android.util.ArrayMap<java.lang.Integer, java.lang.String> INPUT_TYPE_VARIATIONS = new android.util.ArrayMap<>();

    public static class AutofillOverlay {
        public boolean focused;
        public android.view.autofill.AutofillValue value;
    }

    public void setAcquisitionStartTime(long j) {
        this.mAcquisitionStartTime = j;
    }

    public void setAcquisitionEndTime(long j) {
        this.mAcquisitionEndTime = j;
    }

    public void setHomeActivity(boolean z) {
        this.mIsHomeActivity = z;
    }

    public long getAcquisitionStartTime() {
        ensureData();
        return this.mAcquisitionStartTime;
    }

    public long getAcquisitionEndTime() {
        ensureData();
        return this.mAcquisitionEndTime;
    }

    static final class SendChannel extends android.os.Binder {
        volatile android.app.assist.AssistStructure mAssistStructure;

        SendChannel(android.app.assist.AssistStructure assistStructure) {
            this.mAssistStructure = assistStructure;
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i == 2) {
                android.app.assist.AssistStructure assistStructure = this.mAssistStructure;
                if (assistStructure == null) {
                    return true;
                }
                parcel.enforceInterface(android.app.assist.AssistStructure.DESCRIPTOR);
                android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    if (readStrongBinder instanceof android.app.assist.AssistStructure.ParcelTransferWriter) {
                        ((android.app.assist.AssistStructure.ParcelTransferWriter) readStrongBinder).writeToParcel(assistStructure, parcel2);
                        return true;
                    }
                    android.util.Log.w(android.app.assist.AssistStructure.TAG, "Caller supplied bad token type: " + readStrongBinder);
                    return true;
                }
                new android.app.assist.AssistStructure.ParcelTransferWriter(assistStructure, parcel2).writeToParcel(assistStructure, parcel2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    static final class ViewStackEntry {
        int curChild;
        android.app.assist.AssistStructure.ViewNode node;
        int numChildren;

        ViewStackEntry() {
        }
    }

    static final class ParcelTransferWriter extends android.os.Binder {
        android.app.assist.AssistStructure.ViewStackEntry mCurViewStackEntry;
        int mCurViewStackPos;
        int mCurWindow;
        int mNumWindows;
        int mNumWrittenViews;
        int mNumWrittenWindows;
        final boolean mSanitizeOnWrite;
        final boolean mWriteStructure;
        final java.util.ArrayList<android.app.assist.AssistStructure.ViewStackEntry> mViewStack = new java.util.ArrayList<>();
        final float[] mTmpMatrix = new float[9];

        ParcelTransferWriter(android.app.assist.AssistStructure assistStructure, android.os.Parcel parcel) {
            this.mSanitizeOnWrite = assistStructure.mSanitizeOnWrite;
            this.mWriteStructure = assistStructure.waitForReady();
            parcel.writeInt(assistStructure.mFlags);
            parcel.writeInt(assistStructure.mAutofillFlags);
            parcel.writeLong(assistStructure.mAcquisitionStartTime);
            parcel.writeLong(assistStructure.mAcquisitionEndTime);
            this.mNumWindows = assistStructure.mWindowNodes.size();
            if (this.mWriteStructure && this.mNumWindows > 0) {
                parcel.writeInt(this.mNumWindows);
            } else {
                parcel.writeInt(0);
            }
        }

        void writeToParcel(android.app.assist.AssistStructure assistStructure, android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            this.mNumWrittenWindows = 0;
            this.mNumWrittenViews = 0;
            android.util.Log.i(android.app.assist.AssistStructure.TAG, "Flattened " + (writeToParcelInner(assistStructure, parcel) ? android.app.slice.Slice.HINT_PARTIAL : "final") + " assist data: " + (parcel.dataPosition() - dataPosition) + " bytes, containing " + this.mNumWrittenWindows + " windows, " + this.mNumWrittenViews + " views");
        }

        boolean writeToParcelInner(android.app.assist.AssistStructure assistStructure, android.os.Parcel parcel) {
            if (this.mNumWindows == 0) {
                return false;
            }
            android.os.PooledStringWriter pooledStringWriter = new android.os.PooledStringWriter(parcel);
            while (writeNextEntryToParcel(assistStructure, parcel, pooledStringWriter)) {
                if (parcel.dataSize() > 65536) {
                    parcel.writeInt(0);
                    parcel.writeStrongBinder(this);
                    pooledStringWriter.finish();
                    return true;
                }
            }
            pooledStringWriter.finish();
            this.mViewStack.clear();
            return false;
        }

        void pushViewStackEntry(android.app.assist.AssistStructure.ViewNode viewNode, int i) {
            android.app.assist.AssistStructure.ViewStackEntry viewStackEntry;
            if (i >= this.mViewStack.size()) {
                viewStackEntry = new android.app.assist.AssistStructure.ViewStackEntry();
                this.mViewStack.add(viewStackEntry);
            } else {
                viewStackEntry = this.mViewStack.get(i);
            }
            viewStackEntry.node = viewNode;
            viewStackEntry.numChildren = viewNode.getChildCount();
            viewStackEntry.curChild = 0;
            this.mCurViewStackEntry = viewStackEntry;
        }

        void writeView(android.app.assist.AssistStructure.ViewNode viewNode, android.os.Parcel parcel, android.os.PooledStringWriter pooledStringWriter, int i) {
            parcel.writeInt(android.app.assist.AssistStructure.VALIDATE_VIEW_TOKEN);
            int writeSelfToParcel = viewNode.writeSelfToParcel(parcel, pooledStringWriter, this.mSanitizeOnWrite, this.mTmpMatrix, true);
            this.mNumWrittenViews++;
            if ((writeSelfToParcel & 1048576) != 0) {
                parcel.writeInt(viewNode.mChildren.length);
                int i2 = this.mCurViewStackPos + 1;
                this.mCurViewStackPos = i2;
                pushViewStackEntry(viewNode, i2);
            }
        }

        boolean writeNextEntryToParcel(android.app.assist.AssistStructure assistStructure, android.os.Parcel parcel, android.os.PooledStringWriter pooledStringWriter) {
            if (this.mCurViewStackEntry != null) {
                if (this.mCurViewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                    android.app.assist.AssistStructure.ViewNode viewNode = this.mCurViewStackEntry.node.mChildren[this.mCurViewStackEntry.curChild];
                    this.mCurViewStackEntry.curChild++;
                    writeView(viewNode, parcel, pooledStringWriter, 1);
                    return true;
                }
                while (true) {
                    int i = this.mCurViewStackPos - 1;
                    this.mCurViewStackPos = i;
                    if (i < 0) {
                        this.mCurViewStackEntry = null;
                        break;
                    }
                    this.mCurViewStackEntry = this.mViewStack.get(i);
                    if (this.mCurViewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                        break;
                    }
                }
                return true;
            }
            int i2 = this.mCurWindow;
            if (i2 >= this.mNumWindows) {
                return false;
            }
            android.app.assist.AssistStructure.WindowNode windowNode = (android.app.assist.AssistStructure.WindowNode) assistStructure.mWindowNodes.get(i2);
            this.mCurWindow++;
            parcel.writeInt(android.app.assist.AssistStructure.VALIDATE_WINDOW_TOKEN);
            windowNode.writeSelfToParcel(parcel, pooledStringWriter, this.mTmpMatrix);
            this.mNumWrittenWindows++;
            android.app.assist.AssistStructure.ViewNode viewNode2 = windowNode.mRoot;
            this.mCurViewStackPos = 0;
            writeView(viewNode2, parcel, pooledStringWriter, 0);
            return true;
        }
    }

    final class ParcelTransferReader {
        private final android.os.IBinder mChannel;
        private android.os.Parcel mCurParcel;
        int mNumReadViews;
        int mNumReadWindows;
        android.os.PooledStringReader mStringReader;
        final float[] mTmpMatrix = new float[9];
        private android.os.IBinder mTransferToken;

        ParcelTransferReader(android.os.IBinder iBinder) {
            this.mChannel = iBinder;
        }

        void go() {
            fetchData();
            android.app.assist.AssistStructure.this.mFlags = this.mCurParcel.readInt();
            android.app.assist.AssistStructure.this.mAutofillFlags = this.mCurParcel.readInt();
            android.app.assist.AssistStructure.this.mAcquisitionStartTime = this.mCurParcel.readLong();
            android.app.assist.AssistStructure.this.mAcquisitionEndTime = this.mCurParcel.readLong();
            int readInt = this.mCurParcel.readInt();
            if (readInt > 0) {
                this.mStringReader = new android.os.PooledStringReader(this.mCurParcel);
                for (int i = 0; i < readInt; i++) {
                    android.app.assist.AssistStructure.this.mWindowNodes.add(new android.app.assist.AssistStructure.WindowNode(this));
                }
            }
            this.mCurParcel.recycle();
            this.mCurParcel = null;
        }

        android.os.Parcel readParcel(int i, int i2) {
            int readInt = this.mCurParcel.readInt();
            if (readInt != 0) {
                if (readInt != i) {
                    throw new android.os.BadParcelableException("Got token " + java.lang.Integer.toHexString(readInt) + ", expected token " + java.lang.Integer.toHexString(i));
                }
                return this.mCurParcel;
            }
            this.mTransferToken = this.mCurParcel.readStrongBinder();
            if (this.mTransferToken == null) {
                throw new java.lang.IllegalStateException("Reached end of partial data without transfer token");
            }
            fetchData();
            this.mStringReader = new android.os.PooledStringReader(this.mCurParcel);
            this.mCurParcel.readInt();
            return this.mCurParcel;
        }

        private void fetchData() {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            try {
                obtain.writeInterfaceToken(android.app.assist.AssistStructure.DESCRIPTOR);
                obtain.writeStrongBinder(this.mTransferToken);
                if (this.mCurParcel != null) {
                    this.mCurParcel.recycle();
                }
                this.mCurParcel = android.os.Parcel.obtain();
                try {
                    this.mChannel.transact(2, obtain, this.mCurParcel, 0);
                    obtain.recycle();
                    this.mNumReadViews = 0;
                    this.mNumReadWindows = 0;
                } catch (android.os.RemoteException e) {
                    android.util.Log.w(android.app.assist.AssistStructure.TAG, "Failure reading AssistStructure data", e);
                    throw new java.lang.IllegalStateException("Failure reading AssistStructure data: " + e);
                }
            } catch (java.lang.Throwable th) {
                obtain.recycle();
                throw th;
            }
        }
    }

    static final class ViewNodeText {
        java.lang.String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        java.lang.CharSequence mText;
        int mTextBackgroundColor;
        int mTextColor;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
        }

        boolean isSimple() {
            return this.mTextBackgroundColor == 1 && this.mTextSelectionStart == 0 && this.mTextSelectionEnd == 0 && this.mLineCharOffsets == null && this.mLineBaselines == null && this.mHint == null;
        }

        ViewNodeText(android.os.Parcel parcel, boolean z) {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
            this.mText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (!z) {
                this.mTextBackgroundColor = parcel.readInt();
                this.mTextSelectionStart = parcel.readInt();
                this.mTextSelectionEnd = parcel.readInt();
                this.mLineCharOffsets = parcel.createIntArray();
                this.mLineBaselines = parcel.createIntArray();
                this.mHint = parcel.readString();
            }
        }

        void writeToParcel(android.os.Parcel parcel, boolean z, boolean z2) {
            android.text.TextUtils.writeToParcel(z2 ? this.mText : "", parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (!z) {
                parcel.writeInt(this.mTextBackgroundColor);
                parcel.writeInt(this.mTextSelectionStart);
                parcel.writeInt(this.mTextSelectionEnd);
                parcel.writeIntArray(this.mLineCharOffsets);
                parcel.writeIntArray(this.mLineBaselines);
                parcel.writeString(this.mHint);
            }
        }
    }

    public static class WindowNode {
        final int mDisplayId;
        final int mHeight;
        final android.app.assist.AssistStructure.ViewNode mRoot;
        final java.lang.CharSequence mTitle;
        final int mWidth;
        final int mX;
        final int mY;

        WindowNode(android.app.assist.AssistStructure assistStructure, android.view.ViewRootImpl viewRootImpl, boolean z, int i) {
            android.view.View view = viewRootImpl.getView();
            android.graphics.Rect rect = new android.graphics.Rect();
            view.getBoundsOnScreen(rect);
            this.mX = rect.left - view.getLeft();
            this.mY = rect.top - view.getTop();
            this.mWidth = rect.width();
            this.mHeight = rect.height();
            this.mTitle = viewRootImpl.getTitle();
            this.mDisplayId = viewRootImpl.getDisplayId();
            this.mRoot = new android.app.assist.AssistStructure.ViewNode();
            android.app.assist.AssistStructure.ViewNodeBuilder viewNodeBuilder = new android.app.assist.AssistStructure.ViewNodeBuilder(assistStructure, this.mRoot, false);
            if ((viewRootImpl.getWindowFlags() & 8192) != 0) {
                if (z) {
                    view.onProvideAutofillStructure(viewNodeBuilder, resolveViewAutofillFlags(view.getContext(), i));
                } else {
                    view.onProvideStructure(viewNodeBuilder);
                    viewNodeBuilder.setAssistBlocked(true);
                    return;
                }
            }
            if (z) {
                view.dispatchProvideAutofillStructure(viewNodeBuilder, resolveViewAutofillFlags(view.getContext(), i));
            } else {
                view.dispatchProvideStructure(viewNodeBuilder);
            }
        }

        WindowNode(android.app.assist.AssistStructure.ParcelTransferReader parcelTransferReader) {
            android.os.Parcel readParcel = parcelTransferReader.readParcel(android.app.assist.AssistStructure.VALIDATE_WINDOW_TOKEN, 0);
            parcelTransferReader.mNumReadWindows++;
            this.mX = readParcel.readInt();
            this.mY = readParcel.readInt();
            this.mWidth = readParcel.readInt();
            this.mHeight = readParcel.readInt();
            this.mTitle = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(readParcel);
            this.mDisplayId = readParcel.readInt();
            this.mRoot = new android.app.assist.AssistStructure.ViewNode(parcelTransferReader, 0);
        }

        int resolveViewAutofillFlags(android.content.Context context, int i) {
            return ((i & 1) == 0 && !context.isAutofillCompatibilityEnabled() && (i & 512) == 0) ? 0 : 1;
        }

        void writeSelfToParcel(android.os.Parcel parcel, android.os.PooledStringWriter pooledStringWriter, float[] fArr) {
            parcel.writeInt(this.mX);
            parcel.writeInt(this.mY);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            android.text.TextUtils.writeToParcel(this.mTitle, parcel, 0);
            parcel.writeInt(this.mDisplayId);
        }

        public int getLeft() {
            return this.mX;
        }

        public int getTop() {
            return this.mY;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public java.lang.CharSequence getTitle() {
            return this.mTitle;
        }

        public int getDisplayId() {
            return this.mDisplayId;
        }

        public android.app.assist.AssistStructure.ViewNode getRootViewNode() {
            return this.mRoot;
        }
    }

    public static class ViewNode {
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_HINTS = 16;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_OPTIONS = 32;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_SESSION_ID = 2048;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_TYPE = 8;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VALUE = 4;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIEW_ID = 1;
        static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIRTUAL_VIEW_ID = 2;
        static final int AUTOFILL_FLAGS_HAS_HINT_ID_ENTRY = 4096;
        static final int AUTOFILL_FLAGS_HAS_HTML_INFO = 64;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_EMS = 512;
        static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_LENGTH = 1024;
        static final int AUTOFILL_FLAGS_HAS_MIN_TEXT_EMS = 256;
        static final int AUTOFILL_FLAGS_HAS_TEXT_ID_ENTRY = 128;
        static final int FLAGS_ACCESSIBILITY_FOCUSED = 4096;
        static final int FLAGS_ACTIVATED = 8192;
        static final int FLAGS_ALL_CONTROL = -65536;
        static final int FLAGS_ASSIST_BLOCKED = 128;
        static final int FLAGS_CHECKABLE = 256;
        static final int FLAGS_CHECKED = 512;
        static final int FLAGS_CLICKABLE = 1024;
        static final int FLAGS_CONTEXT_CLICKABLE = 16384;
        static final int FLAGS_DISABLED = 1;
        static final int FLAGS_FOCUSABLE = 16;
        static final int FLAGS_FOCUSED = 32;
        static final int FLAGS_HAS_ALPHA = 536870912;
        static final int FLAGS_HAS_CHILDREN = 1048576;
        static final int FLAGS_HAS_COMPLEX_TEXT = 8388608;
        static final int FLAGS_HAS_CONTENT_DESCRIPTION = 33554432;
        static final int FLAGS_HAS_ELEVATION = 268435456;
        static final int FLAGS_HAS_EXTRAS = 4194304;
        static final int FLAGS_HAS_ID = 2097152;
        static final int FLAGS_HAS_INPUT_TYPE = 262144;
        static final int FLAGS_HAS_LARGE_COORDS = 67108864;
        static final int FLAGS_HAS_LOCALE_LIST = 65536;
        static final int FLAGS_HAS_MATRIX = 1073741824;
        static final int FLAGS_HAS_MIME_TYPES = Integer.MIN_VALUE;
        static final int FLAGS_HAS_SCROLL = 134217728;
        static final int FLAGS_HAS_TEXT = 16777216;
        static final int FLAGS_HAS_URL_DOMAIN = 524288;
        static final int FLAGS_HAS_URL_SCHEME = 131072;
        static final int FLAGS_LONG_CLICKABLE = 2048;
        static final int FLAGS_OPAQUE = 32768;
        static final int FLAGS_SELECTED = 64;
        static final int FLAGS_VISIBILITY_MASK = 12;
        public static final int TEXT_COLOR_UNDEFINED = 1;
        public static final int TEXT_STYLE_BOLD = 1;
        public static final int TEXT_STYLE_ITALIC = 2;
        public static final int TEXT_STYLE_STRIKE_THRU = 8;
        public static final int TEXT_STYLE_UNDERLINE = 4;
        int mAutofillFlags;
        java.lang.String[] mAutofillHints;
        android.view.autofill.AutofillId mAutofillId;
        java.lang.CharSequence[] mAutofillOptions;
        android.app.assist.AssistStructure.AutofillOverlay mAutofillOverlay;
        android.view.autofill.AutofillValue mAutofillValue;
        android.app.assist.AssistStructure.ViewNode[] mChildren;
        java.lang.String mClassName;
        java.lang.CharSequence mContentDescription;
        float mElevation;
        android.os.Bundle mExtras;
        int mFlags;
        android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> mGetCredentialCallback;
        android.credentials.GetCredentialRequest mGetCredentialRequest;
        int mHeight;
        java.lang.String mHintIdEntry;
        android.view.ViewStructure.HtmlInfo mHtmlInfo;
        java.lang.String mIdEntry;
        java.lang.String mIdPackage;
        java.lang.String mIdType;
        int mImportantForAutofill;
        int mInputType;
        boolean mIsCredential;
        android.os.LocaleList mLocaleList;
        android.graphics.Matrix mMatrix;
        java.lang.String[] mReceiveContentMimeTypes;
        boolean mSanitized;
        int mScrollX;
        int mScrollY;
        android.app.assist.AssistStructure.ViewNodeText mText;
        java.lang.String mTextIdEntry;
        java.lang.String mWebDomain;
        java.lang.String mWebScheme;
        int mWidth;
        int mX;
        int mY;
        int mId = -1;
        int mAutofillType = 0;
        int mMinEms = -1;
        int mMaxEms = -1;
        int mMaxLength = -1;
        float mAlpha = 1.0f;

        @android.annotation.SystemApi
        public ViewNode() {
        }

        ViewNode(android.os.Parcel parcel) {
            initializeFromParcelWithoutChildren(parcel, null, null);
        }

        ViewNode(android.app.assist.AssistStructure.ParcelTransferReader parcelTransferReader, int i) {
            android.os.Parcel readParcel = parcelTransferReader.readParcel(android.app.assist.AssistStructure.VALIDATE_VIEW_TOKEN, i);
            parcelTransferReader.mNumReadViews++;
            initializeFromParcelWithoutChildren(readParcel, (android.os.PooledStringReader) java.util.Objects.requireNonNull(parcelTransferReader.mStringReader), (float[]) java.util.Objects.requireNonNull(parcelTransferReader.mTmpMatrix));
            if ((this.mFlags & 1048576) != 0) {
                int readInt = readParcel.readInt();
                this.mChildren = new android.app.assist.AssistStructure.ViewNode[readInt];
                for (int i2 = 0; i2 < readInt; i2++) {
                    this.mChildren[i2] = new android.app.assist.AssistStructure.ViewNode(parcelTransferReader, i + 1);
                }
            }
        }

        private static void writeString(android.os.Parcel parcel, android.os.PooledStringWriter pooledStringWriter, java.lang.String str) {
            if (pooledStringWriter != null) {
                pooledStringWriter.writeString(str);
            } else {
                parcel.writeString(str);
            }
        }

        private static java.lang.String readString(android.os.Parcel parcel, android.os.PooledStringReader pooledStringReader) {
            if (pooledStringReader != null) {
                return pooledStringReader.readString();
            }
            return parcel.readString();
        }

        void initializeFromParcelWithoutChildren(android.os.Parcel parcel, android.os.PooledStringReader pooledStringReader, float[] fArr) {
            this.mClassName = readString(parcel, pooledStringReader);
            this.mFlags = parcel.readInt();
            int i = this.mFlags;
            this.mAutofillFlags = parcel.readInt();
            int i2 = this.mAutofillFlags;
            if ((2097152 & i) != 0) {
                this.mId = parcel.readInt();
                if (this.mId != -1) {
                    this.mIdEntry = readString(parcel, pooledStringReader);
                    if (this.mIdEntry != null) {
                        this.mIdType = readString(parcel, pooledStringReader);
                        this.mIdPackage = readString(parcel, pooledStringReader);
                    }
                }
            }
            if (i2 != 0) {
                this.mSanitized = parcel.readInt() == 1;
                this.mIsCredential = parcel.readInt() == 1;
                this.mImportantForAutofill = parcel.readInt();
                if ((i2 & 1) != 0) {
                    int readInt = parcel.readInt();
                    if ((i2 & 2) != 0) {
                        this.mAutofillId = new android.view.autofill.AutofillId(readInt, parcel.readInt());
                    } else {
                        this.mAutofillId = new android.view.autofill.AutofillId(readInt);
                    }
                    if ((i2 & 2048) != 0) {
                        this.mAutofillId.setSessionId(parcel.readInt());
                    }
                }
                if ((i2 & 8) != 0) {
                    this.mAutofillType = parcel.readInt();
                }
                if ((i2 & 16) != 0) {
                    this.mAutofillHints = parcel.readStringArray();
                }
                if ((i2 & 4) != 0) {
                    this.mAutofillValue = (android.view.autofill.AutofillValue) parcel.readParcelable(null, android.view.autofill.AutofillValue.class);
                }
                if ((i2 & 32) != 0) {
                    this.mAutofillOptions = parcel.readCharSequenceArray();
                }
                if ((i2 & 64) != 0) {
                    this.mHtmlInfo = (android.view.ViewStructure.HtmlInfo) parcel.readParcelable(null, android.view.ViewStructure.HtmlInfo.class);
                }
                if ((i2 & 256) != 0) {
                    this.mMinEms = parcel.readInt();
                }
                if ((i2 & 512) != 0) {
                    this.mMaxEms = parcel.readInt();
                }
                if ((i2 & 1024) != 0) {
                    this.mMaxLength = parcel.readInt();
                }
                if ((i2 & 128) != 0) {
                    this.mTextIdEntry = readString(parcel, pooledStringReader);
                }
                if ((i2 & 4096) != 0) {
                    this.mHintIdEntry = readString(parcel, pooledStringReader);
                }
            }
            if ((67108864 & i) != 0) {
                this.mX = parcel.readInt();
                this.mY = parcel.readInt();
                this.mWidth = parcel.readInt();
                this.mHeight = parcel.readInt();
            } else {
                int readInt2 = parcel.readInt();
                this.mX = readInt2 & 32767;
                this.mY = (readInt2 >> 16) & 32767;
                int readInt3 = parcel.readInt();
                this.mWidth = readInt3 & 32767;
                this.mHeight = (readInt3 >> 16) & 32767;
            }
            if ((134217728 & i) != 0) {
                this.mScrollX = parcel.readInt();
                this.mScrollY = parcel.readInt();
            }
            if ((1073741824 & i) != 0) {
                this.mMatrix = new android.graphics.Matrix();
                if (fArr == null) {
                    fArr = new float[9];
                }
                parcel.readFloatArray(fArr);
                this.mMatrix.setValues(fArr);
            }
            if ((268435456 & i) != 0) {
                this.mElevation = parcel.readFloat();
            }
            if ((536870912 & i) != 0) {
                this.mAlpha = parcel.readFloat();
            }
            if ((33554432 & i) != 0) {
                this.mContentDescription = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if ((16777216 & i) != 0) {
                this.mText = new android.app.assist.AssistStructure.ViewNodeText(parcel, (8388608 & i) == 0);
            }
            if ((262144 & i) != 0) {
                this.mInputType = parcel.readInt();
            }
            if ((131072 & i) != 0) {
                this.mWebScheme = parcel.readString();
            }
            if ((524288 & i) != 0) {
                this.mWebDomain = parcel.readString();
            }
            if ((65536 & i) != 0) {
                this.mLocaleList = (android.os.LocaleList) parcel.readParcelable(null, android.os.LocaleList.class);
            }
            if ((Integer.MIN_VALUE & i) != 0) {
                this.mReceiveContentMimeTypes = parcel.readStringArray();
            }
            if ((4194304 & i) != 0) {
                this.mExtras = parcel.readBundle();
            }
            this.mGetCredentialRequest = (android.credentials.GetCredentialRequest) parcel.readTypedObject(android.credentials.GetCredentialRequest.CREATOR);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
        
            if ((((r23.mWidth & (-32768)) != 0) | ((r23.mHeight & (-32768)) != 0)) != false) goto L19;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        int writeSelfToParcel(android.os.Parcel parcel, android.os.PooledStringWriter pooledStringWriter, boolean z, float[] fArr, boolean z2) {
            int i;
            int i2;
            boolean z3;
            float[] fArr2;
            android.view.autofill.AutofillValue autofillValue;
            int i3 = this.mFlags & 65535;
            if (this.mId != -1) {
                i3 |= 2097152;
            }
            if ((this.mX & (-32768)) == 0 && (this.mY & (-32768)) == 0) {
            }
            i3 |= 67108864;
            if (this.mScrollX != 0 || this.mScrollY != 0) {
                i3 |= 134217728;
            }
            if (this.mMatrix != null) {
                i3 |= 1073741824;
            }
            if (this.mElevation != 0.0f) {
                i3 |= 268435456;
            }
            if (this.mAlpha != 1.0f) {
                i3 |= 536870912;
            }
            if (this.mContentDescription != null) {
                i3 |= 33554432;
            }
            if (this.mText != null) {
                i3 |= 16777216;
                if (!this.mText.isSimple()) {
                    i3 |= 8388608;
                }
            }
            if (this.mInputType != 0) {
                i3 |= 262144;
            }
            if (this.mWebScheme != null) {
                i3 |= 131072;
            }
            if (this.mWebDomain != null) {
                i3 |= 524288;
            }
            if (this.mLocaleList != null) {
                i3 |= 65536;
            }
            if (this.mReceiveContentMimeTypes != null) {
                i3 |= Integer.MIN_VALUE;
            }
            if (this.mExtras != null) {
                i3 |= 4194304;
            }
            if (this.mChildren != null && z2) {
                i3 |= 1048576;
            }
            if (this.mAutofillId == null) {
                i = 0;
            } else {
                if (!this.mAutofillId.isVirtualInt()) {
                    i = 1;
                } else {
                    i = 3;
                }
                if (this.mAutofillId.hasSession()) {
                    i |= 2048;
                }
            }
            if (this.mAutofillValue != null) {
                i |= 4;
            }
            if (this.mAutofillType != 0) {
                i |= 8;
            }
            if (this.mAutofillHints != null) {
                i |= 16;
            }
            if (this.mAutofillOptions != null) {
                i |= 32;
            }
            if (this.mHtmlInfo instanceof android.os.Parcelable) {
                i |= 64;
            }
            if (this.mMinEms > -1) {
                i |= 256;
            }
            if (this.mMaxEms > -1) {
                i |= 512;
            }
            if (this.mMaxLength > -1) {
                i |= 1024;
            }
            if (this.mTextIdEntry != null) {
                i |= 128;
            }
            if (this.mHintIdEntry != null) {
                i |= 4096;
            }
            writeString(parcel, pooledStringWriter, this.mClassName);
            if (i != 0 && (this.mSanitized || !z)) {
                i2 = i3 & (-513);
            } else {
                i2 = i3;
            }
            if (this.mAutofillOverlay != null) {
                if (this.mAutofillOverlay.focused) {
                    i2 |= 32;
                } else {
                    i2 &= -33;
                }
            }
            parcel.writeInt(i2);
            parcel.writeInt(i);
            if ((2097152 & i3) != 0) {
                parcel.writeInt(this.mId);
                if (this.mId != -1) {
                    writeString(parcel, pooledStringWriter, this.mIdEntry);
                    if (this.mIdEntry != null) {
                        writeString(parcel, pooledStringWriter, this.mIdType);
                        writeString(parcel, pooledStringWriter, this.mIdPackage);
                    }
                }
            }
            if (i == 0) {
                z3 = true;
            } else {
                parcel.writeInt(this.mSanitized ? 1 : 0);
                parcel.writeInt(this.mIsCredential ? 1 : 0);
                parcel.writeInt(this.mImportantForAutofill);
                z3 = this.mSanitized || !z;
                if ((i & 1) != 0) {
                    parcel.writeInt(this.mAutofillId.getViewId());
                    if ((i & 2) != 0) {
                        parcel.writeInt(this.mAutofillId.getVirtualChildIntId());
                    }
                    if ((i & 2048) != 0) {
                        parcel.writeInt(this.mAutofillId.getSessionId());
                    }
                }
                if ((i & 8) != 0) {
                    parcel.writeInt(this.mAutofillType);
                }
                if ((i & 16) != 0) {
                    parcel.writeStringArray(this.mAutofillHints);
                }
                if ((i & 4) != 0) {
                    if (z3) {
                        autofillValue = this.mAutofillValue;
                    } else if (this.mAutofillOverlay != null && this.mAutofillOverlay.value != null) {
                        autofillValue = this.mAutofillOverlay.value;
                    } else {
                        autofillValue = null;
                    }
                    parcel.writeParcelable(autofillValue, 0);
                }
                if ((i & 32) != 0) {
                    parcel.writeCharSequenceArray(this.mAutofillOptions);
                }
                if ((i & 64) != 0) {
                    parcel.writeParcelable((android.os.Parcelable) this.mHtmlInfo, 0);
                }
                if ((i & 256) != 0) {
                    parcel.writeInt(this.mMinEms);
                }
                if ((i & 512) != 0) {
                    parcel.writeInt(this.mMaxEms);
                }
                if ((i & 1024) != 0) {
                    parcel.writeInt(this.mMaxLength);
                }
                if ((i & 128) != 0) {
                    writeString(parcel, pooledStringWriter, this.mTextIdEntry);
                }
                if ((i & 4096) != 0) {
                    writeString(parcel, pooledStringWriter, this.mHintIdEntry);
                }
            }
            if ((i3 & 67108864) == 0) {
                parcel.writeInt((this.mY << 16) | this.mX);
                parcel.writeInt((this.mHeight << 16) | this.mWidth);
            } else {
                parcel.writeInt(this.mX);
                parcel.writeInt(this.mY);
                parcel.writeInt(this.mWidth);
                parcel.writeInt(this.mHeight);
            }
            if ((i3 & 134217728) != 0) {
                parcel.writeInt(this.mScrollX);
                parcel.writeInt(this.mScrollY);
            }
            if ((i3 & 1073741824) != 0) {
                if (fArr != null) {
                    fArr2 = fArr;
                } else {
                    fArr2 = new float[9];
                }
                this.mMatrix.getValues(fArr2);
                parcel.writeFloatArray(fArr2);
            }
            if ((i3 & 268435456) != 0) {
                parcel.writeFloat(this.mElevation);
            }
            if ((i3 & 536870912) != 0) {
                parcel.writeFloat(this.mAlpha);
            }
            if ((i3 & 33554432) != 0) {
                android.text.TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
            }
            if ((i3 & 16777216) != 0) {
                this.mText.writeToParcel(parcel, (8388608 & i3) == 0, z3);
            }
            if ((i3 & 262144) != 0) {
                parcel.writeInt(this.mInputType);
            }
            if ((i3 & 131072) != 0) {
                parcel.writeString(this.mWebScheme);
            }
            if ((i3 & 524288) != 0) {
                parcel.writeString(this.mWebDomain);
            }
            if ((i3 & 65536) != 0) {
                parcel.writeParcelable(this.mLocaleList, 0);
            }
            if ((i3 & Integer.MIN_VALUE) != 0) {
                parcel.writeStringArray(this.mReceiveContentMimeTypes);
            }
            if ((i3 & 4194304) != 0) {
                parcel.writeBundle(this.mExtras);
            }
            parcel.writeTypedObject(this.mGetCredentialRequest, i3);
            return i3;
        }

        public int getId() {
            return this.mId;
        }

        public java.lang.String getIdPackage() {
            return this.mIdPackage;
        }

        public java.lang.String getIdType() {
            return this.mIdType;
        }

        public java.lang.String getIdEntry() {
            return this.mIdEntry;
        }

        public android.view.autofill.AutofillId getAutofillId() {
            return this.mAutofillId;
        }

        public int getAutofillType() {
            return this.mAutofillType;
        }

        public java.lang.String[] getAutofillHints() {
            return this.mAutofillHints;
        }

        public android.view.autofill.AutofillValue getAutofillValue() {
            return this.mAutofillValue;
        }

        public void setAutofillOverlay(android.app.assist.AssistStructure.AutofillOverlay autofillOverlay) {
            this.mAutofillOverlay = autofillOverlay;
        }

        public java.lang.CharSequence[] getAutofillOptions() {
            return this.mAutofillOptions;
        }

        public boolean isCredential() {
            return this.mIsCredential;
        }

        public android.credentials.GetCredentialRequest getCredentialManagerRequest() {
            return this.mGetCredentialRequest;
        }

        public android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> getCredentialManagerCallback() {
            return this.mGetCredentialCallback;
        }

        public int getInputType() {
            return this.mInputType;
        }

        public boolean isSanitized() {
            return this.mSanitized;
        }

        public void updateAutofillValue(android.view.autofill.AutofillValue autofillValue) {
            this.mAutofillValue = autofillValue;
            if (autofillValue.isText()) {
                if (this.mText == null) {
                    this.mText = new android.app.assist.AssistStructure.ViewNodeText();
                }
                this.mText.mText = autofillValue.getTextValue();
            }
        }

        public int getLeft() {
            return this.mX;
        }

        public int getTop() {
            return this.mY;
        }

        public int getScrollX() {
            return this.mScrollX;
        }

        public int getScrollY() {
            return this.mScrollY;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public android.graphics.Matrix getTransformation() {
            return this.mMatrix;
        }

        public float getElevation() {
            return this.mElevation;
        }

        public float getAlpha() {
            return this.mAlpha;
        }

        public int getVisibility() {
            return this.mFlags & 12;
        }

        public boolean isAssistBlocked() {
            return (this.mFlags & 128) != 0;
        }

        public boolean isEnabled() {
            return (this.mFlags & 1) == 0;
        }

        public boolean isClickable() {
            return (this.mFlags & 1024) != 0;
        }

        public boolean isFocusable() {
            return (this.mFlags & 16) != 0;
        }

        public boolean isFocused() {
            return (this.mFlags & 32) != 0;
        }

        public boolean isAccessibilityFocused() {
            return (this.mFlags & 4096) != 0;
        }

        public boolean isCheckable() {
            return (this.mFlags & 256) != 0;
        }

        public boolean isChecked() {
            return (this.mFlags & 512) != 0;
        }

        public boolean isSelected() {
            return (this.mFlags & 64) != 0;
        }

        public boolean isActivated() {
            return (this.mFlags & 8192) != 0;
        }

        public boolean isOpaque() {
            return (this.mFlags & 32768) != 0;
        }

        public boolean isLongClickable() {
            return (this.mFlags & 2048) != 0;
        }

        public boolean isContextClickable() {
            return (this.mFlags & 16384) != 0;
        }

        public java.lang.String getClassName() {
            return this.mClassName;
        }

        public java.lang.CharSequence getContentDescription() {
            return this.mContentDescription;
        }

        public java.lang.String getWebDomain() {
            return this.mWebDomain;
        }

        public void setWebDomain(java.lang.String str) {
            if (str == null) {
                return;
            }
            android.net.Uri parse = android.net.Uri.parse(str);
            if (parse == null) {
                android.util.Log.w(android.app.assist.AssistStructure.TAG, "Failed to parse web domain");
                return;
            }
            this.mWebScheme = parse.getScheme();
            if (this.mWebScheme == null) {
                parse = android.net.Uri.parse("http://" + str);
            }
            this.mWebDomain = parse.getHost();
        }

        public java.lang.String getWebScheme() {
            return this.mWebScheme;
        }

        public android.view.ViewStructure.HtmlInfo getHtmlInfo() {
            return this.mHtmlInfo;
        }

        public android.os.LocaleList getLocaleList() {
            return this.mLocaleList;
        }

        public java.lang.String[] getReceiveContentMimeTypes() {
            return this.mReceiveContentMimeTypes;
        }

        public java.lang.CharSequence getText() {
            if (this.mText != null) {
                return this.mText.mText;
            }
            return null;
        }

        public int getTextSelectionStart() {
            if (this.mText != null) {
                return this.mText.mTextSelectionStart;
            }
            return -1;
        }

        public int getTextSelectionEnd() {
            if (this.mText != null) {
                return this.mText.mTextSelectionEnd;
            }
            return -1;
        }

        public int getTextColor() {
            if (this.mText != null) {
                return this.mText.mTextColor;
            }
            return 1;
        }

        public int getTextBackgroundColor() {
            if (this.mText != null) {
                return this.mText.mTextBackgroundColor;
            }
            return 1;
        }

        public float getTextSize() {
            if (this.mText != null) {
                return this.mText.mTextSize;
            }
            return 0.0f;
        }

        public int getTextStyle() {
            if (this.mText != null) {
                return this.mText.mTextStyle;
            }
            return 0;
        }

        public int[] getTextLineCharOffsets() {
            if (this.mText != null) {
                return this.mText.mLineCharOffsets;
            }
            return null;
        }

        public int[] getTextLineBaselines() {
            if (this.mText != null) {
                return this.mText.mLineBaselines;
            }
            return null;
        }

        public java.lang.String getTextIdEntry() {
            return this.mTextIdEntry;
        }

        public java.lang.String getHint() {
            if (this.mText != null) {
                return this.mText.mHint;
            }
            return null;
        }

        public java.lang.String getHintIdEntry() {
            return this.mHintIdEntry;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public int getChildCount() {
            if (this.mChildren != null) {
                return this.mChildren.length;
            }
            return 0;
        }

        public android.app.assist.AssistStructure.ViewNode getChildAt(int i) {
            return this.mChildren[i];
        }

        public int getMinTextEms() {
            return this.mMinEms;
        }

        public int getMaxTextEms() {
            return this.mMaxEms;
        }

        public int getMaxTextLength() {
            return this.mMaxLength;
        }

        public int getImportantForAutofill() {
            return this.mImportantForAutofill;
        }
    }

    public static final class ViewNodeParcelable implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.assist.AssistStructure.ViewNodeParcelable> CREATOR = new android.os.Parcelable.Creator<android.app.assist.AssistStructure.ViewNodeParcelable>() { // from class: android.app.assist.AssistStructure.ViewNodeParcelable.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.assist.AssistStructure.ViewNodeParcelable createFromParcel(android.os.Parcel parcel) {
                return new android.app.assist.AssistStructure.ViewNodeParcelable(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.assist.AssistStructure.ViewNodeParcelable[] newArray(int i) {
                return new android.app.assist.AssistStructure.ViewNodeParcelable[i];
            }
        };
        private final android.app.assist.AssistStructure.ViewNode mViewNode;

        public ViewNodeParcelable(android.app.assist.AssistStructure.ViewNode viewNode) {
            this.mViewNode = viewNode;
        }

        public ViewNodeParcelable(android.os.Parcel parcel) {
            this.mViewNode = new android.app.assist.AssistStructure.ViewNode(parcel);
        }

        public android.app.assist.AssistStructure.ViewNode getViewNode() {
            return this.mViewNode;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            this.mViewNode.writeSelfToParcel(parcel, null, false, null, false);
        }
    }

    public static class ViewNodeBuilder extends android.view.ViewStructure {
        final android.app.assist.AssistStructure mAssist;
        final boolean mAsync;
        final android.app.assist.AssistStructure.ViewNode mNode;

        public ViewNodeBuilder() {
            this.mAssist = new android.app.assist.AssistStructure();
            this.mNode = new android.app.assist.AssistStructure.ViewNode();
            this.mAsync = false;
        }

        ViewNodeBuilder(android.app.assist.AssistStructure assistStructure, android.app.assist.AssistStructure.ViewNode viewNode, boolean z) {
            this.mAssist = assistStructure;
            this.mNode = viewNode;
            this.mAsync = z;
        }

        public android.app.assist.AssistStructure.ViewNode getViewNode() {
            return this.mNode;
        }

        @Override // android.view.ViewStructure
        public void setId(int i, java.lang.String str, java.lang.String str2, java.lang.String str3) {
            this.mNode.mId = i;
            this.mNode.mIdPackage = str;
            this.mNode.mIdType = str2;
            this.mNode.mIdEntry = str3;
        }

        @Override // android.view.ViewStructure
        public void setDimens(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mNode.mX = i;
            this.mNode.mY = i2;
            this.mNode.mScrollX = i3;
            this.mNode.mScrollY = i4;
            this.mNode.mWidth = i5;
            this.mNode.mHeight = i6;
        }

        @Override // android.view.ViewStructure
        public void setTransformation(android.graphics.Matrix matrix) {
            if (matrix == null) {
                this.mNode.mMatrix = null;
            } else {
                this.mNode.mMatrix = new android.graphics.Matrix(matrix);
            }
        }

        @Override // android.view.ViewStructure
        public void setElevation(float f) {
            this.mNode.mElevation = f;
        }

        @Override // android.view.ViewStructure
        public void setAlpha(float f) {
            this.mNode.mAlpha = f;
        }

        @Override // android.view.ViewStructure
        public void setVisibility(int i) {
            this.mNode.mFlags = (i & 12) | (this.mNode.mFlags & (-13));
        }

        @Override // android.view.ViewStructure
        public void setAssistBlocked(boolean z) {
            this.mNode.mFlags = (z ? 128 : 0) | (this.mNode.mFlags & android.content.pm.PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE);
        }

        @Override // android.view.ViewStructure
        public void setEnabled(boolean z) {
            this.mNode.mFlags = (!z ? 1 : 0) | (this.mNode.mFlags & (-2));
        }

        @Override // android.view.ViewStructure
        public void setClickable(boolean z) {
            this.mNode.mFlags = (z ? 1024 : 0) | (this.mNode.mFlags & (-1025));
        }

        @Override // android.view.ViewStructure
        public void setLongClickable(boolean z) {
            this.mNode.mFlags = (z ? 2048 : 0) | (this.mNode.mFlags & (-2049));
        }

        @Override // android.view.ViewStructure
        public void setContextClickable(boolean z) {
            this.mNode.mFlags = (z ? 16384 : 0) | (this.mNode.mFlags & (-16385));
        }

        @Override // android.view.ViewStructure
        public void setFocusable(boolean z) {
            this.mNode.mFlags = (z ? 16 : 0) | (this.mNode.mFlags & (-17));
        }

        @Override // android.view.ViewStructure
        public void setFocused(boolean z) {
            this.mNode.mFlags = (z ? 32 : 0) | (this.mNode.mFlags & (-33));
        }

        @Override // android.view.ViewStructure
        public void setAccessibilityFocused(boolean z) {
            this.mNode.mFlags = (z ? 4096 : 0) | (this.mNode.mFlags & (-4097));
        }

        @Override // android.view.ViewStructure
        public void setCheckable(boolean z) {
            this.mNode.mFlags = (z ? 256 : 0) | (this.mNode.mFlags & (-257));
        }

        @Override // android.view.ViewStructure
        public void setChecked(boolean z) {
            this.mNode.mFlags = (z ? 512 : 0) | (this.mNode.mFlags & (-513));
        }

        @Override // android.view.ViewStructure
        public void setSelected(boolean z) {
            this.mNode.mFlags = (z ? 64 : 0) | (this.mNode.mFlags & (-65));
        }

        @Override // android.view.ViewStructure
        public void setActivated(boolean z) {
            this.mNode.mFlags = (z ? 8192 : 0) | (this.mNode.mFlags & (-8193));
        }

        @Override // android.view.ViewStructure
        public void setOpaque(boolean z) {
            this.mNode.mFlags = (z ? 32768 : 0) | (this.mNode.mFlags & (-32769));
        }

        @Override // android.view.ViewStructure
        public void setClassName(java.lang.String str) {
            this.mNode.mClassName = str;
        }

        @Override // android.view.ViewStructure
        public void setContentDescription(java.lang.CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        private final android.app.assist.AssistStructure.ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new android.app.assist.AssistStructure.ViewNodeText();
            return this.mNode.mText;
        }

        @Override // android.view.ViewStructure
        public void setText(java.lang.CharSequence charSequence) {
            android.app.assist.AssistStructure.ViewNodeText nodeText = getNodeText();
            nodeText.mText = android.text.TextUtils.trimToParcelableSize(stripAllSpansFromText(charSequence));
            nodeText.mTextSelectionEnd = -1;
            nodeText.mTextSelectionStart = -1;
        }

        @Override // android.view.ViewStructure
        public void setText(java.lang.CharSequence charSequence, int i, int i2) {
            android.app.assist.AssistStructure.ViewNodeText nodeText = getNodeText();
            nodeText.mText = stripAllSpansFromText(charSequence);
            nodeText.mTextSelectionStart = i;
            nodeText.mTextSelectionEnd = i2;
        }

        @Override // android.view.ViewStructure
        public void setTextStyle(float f, int i, int i2, int i3) {
            android.app.assist.AssistStructure.ViewNodeText nodeText = getNodeText();
            nodeText.mTextColor = i;
            nodeText.mTextBackgroundColor = i2;
            nodeText.mTextSize = f;
            nodeText.mTextStyle = i3;
        }

        @Override // android.view.ViewStructure
        public void setTextLines(int[] iArr, int[] iArr2) {
            android.app.assist.AssistStructure.ViewNodeText nodeText = getNodeText();
            nodeText.mLineCharOffsets = iArr;
            nodeText.mLineBaselines = iArr2;
        }

        @Override // android.view.ViewStructure
        public void setTextIdEntry(java.lang.String str) {
            this.mNode.mTextIdEntry = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public void setHint(java.lang.CharSequence charSequence) {
            getNodeText().mHint = charSequence != null ? charSequence.toString() : null;
        }

        @Override // android.view.ViewStructure
        public void setHintIdEntry(java.lang.String str) {
            this.mNode.mHintIdEntry = (java.lang.String) java.util.Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public java.lang.CharSequence getText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mText;
            }
            return null;
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionStart() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mTextSelectionStart;
            }
            return -1;
        }

        @Override // android.view.ViewStructure
        public int getTextSelectionEnd() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mTextSelectionEnd;
            }
            return -1;
        }

        @Override // android.view.ViewStructure
        public java.lang.CharSequence getHint() {
            if (this.mNode.mText != null) {
                return this.mNode.mText.mHint;
            }
            return null;
        }

        @Override // android.view.ViewStructure
        public android.os.Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new android.os.Bundle();
            return this.mNode.mExtras;
        }

        @Override // android.view.ViewStructure
        public boolean hasExtras() {
            return this.mNode.mExtras != null;
        }

        @Override // android.view.ViewStructure
        public void setChildCount(int i) {
            this.mNode.mChildren = new android.app.assist.AssistStructure.ViewNode[i];
        }

        @Override // android.view.ViewStructure
        public int addChildCount(int i) {
            if (this.mNode.mChildren == null) {
                setChildCount(i);
                return 0;
            }
            int length = this.mNode.mChildren.length;
            android.app.assist.AssistStructure.ViewNode[] viewNodeArr = new android.app.assist.AssistStructure.ViewNode[i + length];
            java.lang.System.arraycopy(this.mNode.mChildren, 0, viewNodeArr, 0, length);
            this.mNode.mChildren = viewNodeArr;
            return length;
        }

        @Override // android.view.ViewStructure
        public int getChildCount() {
            if (this.mNode.mChildren != null) {
                return this.mNode.mChildren.length;
            }
            return 0;
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure newChild(int i) {
            android.app.assist.AssistStructure.ViewNode viewNode = new android.app.assist.AssistStructure.ViewNode();
            this.mNode.mChildren[i] = viewNode;
            return new android.app.assist.AssistStructure.ViewNodeBuilder(this.mAssist, viewNode, false);
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure asyncNewChild(int i) {
            android.app.assist.AssistStructure.ViewNodeBuilder viewNodeBuilder;
            synchronized (this.mAssist) {
                android.app.assist.AssistStructure.ViewNode viewNode = new android.app.assist.AssistStructure.ViewNode();
                this.mNode.mChildren[i] = viewNode;
                viewNodeBuilder = new android.app.assist.AssistStructure.ViewNodeBuilder(this.mAssist, viewNode, true);
                this.mAssist.mPendingAsyncChildren.add(viewNodeBuilder);
            }
            return viewNodeBuilder;
        }

        @Override // android.view.ViewStructure
        public android.credentials.GetCredentialRequest getCredentialManagerRequest() {
            return this.mNode.mGetCredentialRequest;
        }

        @Override // android.view.ViewStructure
        public android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> getCredentialManagerCallback() {
            return this.mNode.mGetCredentialCallback;
        }

        @Override // android.view.ViewStructure
        public void asyncCommit() {
            synchronized (this.mAssist) {
                if (!this.mAsync) {
                    throw new java.lang.IllegalStateException("Child " + this + " was not created with ViewStructure.asyncNewChild");
                }
                if (!this.mAssist.mPendingAsyncChildren.remove(this)) {
                    throw new java.lang.IllegalStateException("Child " + this + " already committed");
                }
                this.mAssist.notifyAll();
            }
        }

        @Override // android.view.ViewStructure
        public android.graphics.Rect getTempRect() {
            return this.mAssist.mTmpRect;
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(android.view.autofill.AutofillId autofillId) {
            this.mNode.mAutofillId = autofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(android.view.autofill.AutofillId autofillId, int i) {
            this.mNode.mAutofillId = new android.view.autofill.AutofillId(autofillId, i);
        }

        @Override // android.view.ViewStructure
        public android.view.autofill.AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillType(int i) {
            this.mNode.mAutofillType = i;
        }

        @Override // android.view.ViewStructure
        public void setAutofillHints(java.lang.String[] strArr) {
            this.mNode.mAutofillHints = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillValue(android.view.autofill.AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override // android.view.ViewStructure
        public void setAutofillOptions(java.lang.CharSequence[] charSequenceArr) {
            this.mNode.mAutofillOptions = charSequenceArr;
        }

        @Override // android.view.ViewStructure
        public void setImportantForAutofill(int i) {
            this.mNode.mImportantForAutofill = i;
        }

        @Override // android.view.ViewStructure
        public void setIsCredential(boolean z) {
            this.mNode.mIsCredential = z;
        }

        @Override // android.view.ViewStructure
        public void setCredentialManagerRequest(android.credentials.GetCredentialRequest getCredentialRequest, android.os.OutcomeReceiver<android.credentials.GetCredentialResponse, android.credentials.GetCredentialException> outcomeReceiver) {
            this.mNode.mGetCredentialRequest = getCredentialRequest;
            this.mNode.mGetCredentialCallback = outcomeReceiver;
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
        }

        @Override // android.view.ViewStructure
        public void setReceiveContentMimeTypes(java.lang.String[] strArr) {
            this.mNode.mReceiveContentMimeTypes = strArr;
        }

        @Override // android.view.ViewStructure
        public void setInputType(int i) {
            this.mNode.mInputType = i;
        }

        @Override // android.view.ViewStructure
        public void setMinTextEms(int i) {
            this.mNode.mMinEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextEms(int i) {
            this.mNode.mMaxEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextLength(int i) {
            this.mNode.mMaxLength = i;
        }

        @Override // android.view.ViewStructure
        public void setDataIsSensitive(boolean z) {
            this.mNode.mSanitized = !z;
        }

        @Override // android.view.ViewStructure
        public void setWebDomain(java.lang.String str) {
            this.mNode.setWebDomain(str);
        }

        @Override // android.view.ViewStructure
        public void setLocaleList(android.os.LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override // android.view.ViewStructure
        public android.view.ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(java.lang.String str) {
            return new android.app.assist.AssistStructure.HtmlInfoNodeBuilder(str);
        }

        @Override // android.view.ViewStructure
        public void setHtmlInfo(android.view.ViewStructure.HtmlInfo htmlInfo) {
            this.mNode.mHtmlInfo = htmlInfo;
        }

        private java.lang.CharSequence stripAllSpansFromText(java.lang.CharSequence charSequence) {
            if (charSequence instanceof android.text.Spanned) {
                return charSequence.toString();
            }
            return charSequence;
        }
    }

    private static final class HtmlInfoNode extends android.view.ViewStructure.HtmlInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.assist.AssistStructure.HtmlInfoNode> CREATOR = new android.os.Parcelable.Creator<android.app.assist.AssistStructure.HtmlInfoNode>() { // from class: android.app.assist.AssistStructure.HtmlInfoNode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.assist.AssistStructure.HtmlInfoNode createFromParcel(android.os.Parcel parcel) {
                android.app.assist.AssistStructure.HtmlInfoNodeBuilder htmlInfoNodeBuilder = new android.app.assist.AssistStructure.HtmlInfoNodeBuilder(parcel.readString());
                java.lang.String[] readStringArray = parcel.readStringArray();
                java.lang.String[] readStringArray2 = parcel.readStringArray();
                if (readStringArray != null && readStringArray2 != null) {
                    if (readStringArray.length != readStringArray2.length) {
                        android.util.Log.w(android.app.assist.AssistStructure.TAG, "HtmlInfo attributes mismatch: names=" + readStringArray.length + ", values=" + readStringArray2.length);
                    } else {
                        for (int i = 0; i < readStringArray.length; i++) {
                            htmlInfoNodeBuilder.addAttribute(readStringArray[i], readStringArray2[i]);
                        }
                    }
                }
                return htmlInfoNodeBuilder.build();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.assist.AssistStructure.HtmlInfoNode[] newArray(int i) {
                return new android.app.assist.AssistStructure.HtmlInfoNode[i];
            }
        };
        private java.util.ArrayList<android.util.Pair<java.lang.String, java.lang.String>> mAttributes;
        private final java.lang.String[] mNames;
        private final java.lang.String mTag;
        private final java.lang.String[] mValues;

        private HtmlInfoNode(android.app.assist.AssistStructure.HtmlInfoNodeBuilder htmlInfoNodeBuilder) {
            this.mTag = htmlInfoNodeBuilder.mTag;
            if (htmlInfoNodeBuilder.mNames == null) {
                this.mNames = null;
                this.mValues = null;
            } else {
                this.mNames = new java.lang.String[htmlInfoNodeBuilder.mNames.size()];
                this.mValues = new java.lang.String[htmlInfoNodeBuilder.mValues.size()];
                htmlInfoNodeBuilder.mNames.toArray(this.mNames);
                htmlInfoNodeBuilder.mValues.toArray(this.mValues);
            }
        }

        @Override // android.view.ViewStructure.HtmlInfo
        public java.lang.String getTag() {
            return this.mTag;
        }

        @Override // android.view.ViewStructure.HtmlInfo
        public java.util.List<android.util.Pair<java.lang.String, java.lang.String>> getAttributes() {
            if (this.mAttributes == null && this.mNames != null) {
                this.mAttributes = new java.util.ArrayList<>(this.mNames.length);
                for (int i = 0; i < this.mNames.length; i++) {
                    this.mAttributes.add(i, new android.util.Pair<>(this.mNames[i], this.mValues[i]));
                }
            }
            return this.mAttributes;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mTag);
            parcel.writeStringArray(this.mNames);
            parcel.writeStringArray(this.mValues);
        }
    }

    private static final class HtmlInfoNodeBuilder extends android.view.ViewStructure.HtmlInfo.Builder {
        private java.util.ArrayList<java.lang.String> mNames;
        private final java.lang.String mTag;
        private java.util.ArrayList<java.lang.String> mValues;

        HtmlInfoNodeBuilder(java.lang.String str) {
            this.mTag = str;
        }

        @Override // android.view.ViewStructure.HtmlInfo.Builder
        public android.view.ViewStructure.HtmlInfo.Builder addAttribute(java.lang.String str, java.lang.String str2) {
            if (this.mNames == null) {
                this.mNames = new java.util.ArrayList<>();
                this.mValues = new java.util.ArrayList<>();
            }
            this.mNames.add(str);
            this.mValues.add(str2);
            return this;
        }

        @Override // android.view.ViewStructure.HtmlInfo.Builder
        public android.app.assist.AssistStructure.HtmlInfoNode build() {
            return new android.app.assist.AssistStructure.HtmlInfoNode(this);
        }
    }

    public AssistStructure(android.app.Activity activity, boolean z, int i) {
        this.mWindowNodes = new java.util.ArrayList<>();
        this.mPendingAsyncChildren = new java.util.ArrayList<>();
        this.mTmpRect = new android.graphics.Rect();
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = i;
        java.util.ArrayList<android.view.ViewRootImpl> rootViews = android.view.WindowManagerGlobal.getInstance().getRootViews(activity.getActivityToken());
        for (int i2 = 0; i2 < rootViews.size(); i2++) {
            android.view.ViewRootImpl viewRootImpl = rootViews.get(i2);
            if (viewRootImpl.getView() == null) {
                android.util.Log.w(TAG, "Skipping window with dettached view: " + ((java.lang.Object) viewRootImpl.getTitle()));
            } else {
                this.mWindowNodes.add(new android.app.assist.AssistStructure.WindowNode(this, viewRootImpl, z, i));
            }
        }
    }

    public AssistStructure() {
        this.mWindowNodes = new java.util.ArrayList<>();
        this.mPendingAsyncChildren = new java.util.ArrayList<>();
        this.mTmpRect = new android.graphics.Rect();
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = 0;
    }

    public AssistStructure(android.os.Parcel parcel) {
        this.mWindowNodes = new java.util.ArrayList<>();
        this.mPendingAsyncChildren = new java.util.ArrayList<>();
        this.mTmpRect = new android.graphics.Rect();
        this.mSanitizeOnWrite = false;
        this.mTaskId = parcel.readInt();
        this.mActivityComponent = android.content.ComponentName.readFromParcel(parcel);
        this.mIsHomeActivity = parcel.readInt() == 1;
        this.mReceiveChannel = parcel.readStrongBinder();
    }

    public void sanitizeForParceling(boolean z) {
        this.mSanitizeOnWrite = z;
    }

    public void dump(boolean z) {
        java.lang.String str;
        if (this.mActivityComponent == null) {
            android.util.Log.i(TAG, "dump(): calling ensureData() first");
            ensureData();
        }
        android.util.Log.i(TAG, "Task id: " + this.mTaskId);
        java.lang.StringBuilder append = new java.lang.StringBuilder().append("Activity: ");
        if (this.mActivityComponent != null) {
            str = this.mActivityComponent.flattenToShortString();
        } else {
            str = null;
        }
        android.util.Log.i(TAG, append.append(str).toString());
        android.util.Log.i(TAG, "Sanitize on write: " + this.mSanitizeOnWrite);
        android.util.Log.i(TAG, "Flags: " + this.mFlags);
        int windowNodeCount = getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            android.app.assist.AssistStructure.WindowNode windowNodeAt = getWindowNodeAt(i);
            android.util.Log.i(TAG, "Window #" + i + " [" + windowNodeAt.getLeft() + "," + windowNodeAt.getTop() + " " + windowNodeAt.getWidth() + "x" + windowNodeAt.getHeight() + "] " + ((java.lang.Object) windowNodeAt.getTitle()));
            dump("  ", windowNodeAt.getRootViewNode(), z);
        }
    }

    void dump(java.lang.String str, android.app.assist.AssistStructure.ViewNode viewNode, boolean z) {
        java.lang.String charSequence;
        android.util.Log.i(TAG, str + "View [" + viewNode.getLeft() + "," + viewNode.getTop() + " " + viewNode.getWidth() + "x" + viewNode.getHeight() + "] " + viewNode.getClassName());
        int id = viewNode.getId();
        if (id != 0) {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append(str);
            sb.append("  ID: #");
            sb.append(java.lang.Integer.toHexString(id));
            java.lang.String idEntry = viewNode.getIdEntry();
            if (idEntry != null) {
                java.lang.String idType = viewNode.getIdType();
                java.lang.String idPackage = viewNode.getIdPackage();
                sb.append(" ");
                sb.append(idPackage);
                sb.append(":");
                sb.append(idType);
                sb.append("/");
                sb.append(idEntry);
            }
            android.util.Log.i(TAG, sb.toString());
        }
        int scrollX = viewNode.getScrollX();
        int scrollY = viewNode.getScrollY();
        if (scrollX != 0 || scrollY != 0) {
            android.util.Log.i(TAG, str + "  Scroll: " + scrollX + "," + scrollY);
        }
        android.graphics.Matrix transformation = viewNode.getTransformation();
        if (transformation != null) {
            android.util.Log.i(TAG, str + "  Transformation: " + transformation);
        }
        float elevation = viewNode.getElevation();
        if (elevation != 0.0f) {
            android.util.Log.i(TAG, str + "  Elevation: " + elevation);
        }
        if (viewNode.getAlpha() != 0.0f) {
            android.util.Log.i(TAG, str + "  Alpha: " + elevation);
        }
        java.lang.CharSequence contentDescription = viewNode.getContentDescription();
        if (contentDescription != null) {
            android.util.Log.i(TAG, str + "  Content description: " + ((java.lang.Object) contentDescription));
        }
        java.lang.CharSequence text = viewNode.getText();
        if (text != null) {
            if (viewNode.isSanitized() || z) {
                charSequence = text.toString();
            } else {
                charSequence = "REDACTED[" + text.length() + " chars]";
            }
            android.util.Log.i(TAG, str + "  Text (sel " + viewNode.getTextSelectionStart() + com.android.internal.content.NativeLibraryHelper.CLEAR_ABI_OVERRIDE + viewNode.getTextSelectionEnd() + "): " + charSequence);
            android.util.Log.i(TAG, str + "  Text size: " + viewNode.getTextSize() + " , style: #" + viewNode.getTextStyle());
            android.util.Log.i(TAG, str + "  Text color fg: #" + java.lang.Integer.toHexString(viewNode.getTextColor()) + ", bg: #" + java.lang.Integer.toHexString(viewNode.getTextBackgroundColor()));
            android.util.Log.i(TAG, str + "  Input type: " + getInputTypeString(viewNode.getInputType()));
            android.util.Log.i(TAG, str + "  Resource id: " + viewNode.getTextIdEntry());
        }
        java.lang.String webDomain = viewNode.getWebDomain();
        if (webDomain != null) {
            android.util.Log.i(TAG, str + "  Web domain: " + webDomain);
        }
        android.view.ViewStructure.HtmlInfo htmlInfo = viewNode.getHtmlInfo();
        if (htmlInfo != null) {
            android.util.Log.i(TAG, str + "  HtmlInfo: tag=" + htmlInfo.getTag() + ", attr=" + htmlInfo.getAttributes());
        }
        android.os.LocaleList localeList = viewNode.getLocaleList();
        if (localeList != null) {
            android.util.Log.i(TAG, str + "  LocaleList: " + localeList);
        }
        java.lang.String[] receiveContentMimeTypes = viewNode.getReceiveContentMimeTypes();
        if (receiveContentMimeTypes != null) {
            android.util.Log.i(TAG, str + "  MIME types: " + java.util.Arrays.toString(receiveContentMimeTypes));
        }
        java.lang.String hint = viewNode.getHint();
        if (hint != null) {
            android.util.Log.i(TAG, str + "  Hint: " + hint);
            android.util.Log.i(TAG, str + "  Resource id: " + viewNode.getHintIdEntry());
        }
        android.os.Bundle extras = viewNode.getExtras();
        if (extras != null) {
            android.util.Log.i(TAG, str + "  Extras: " + extras);
        }
        if (viewNode.isAssistBlocked()) {
            android.util.Log.i(TAG, str + "  BLOCKED");
        }
        android.view.autofill.AutofillId autofillId = viewNode.getAutofillId();
        if (autofillId == null) {
            android.util.Log.i(TAG, str + " No autofill ID");
        } else {
            android.util.Log.i(TAG, str + "  Autofill info: id= " + autofillId + ", type=" + viewNode.getAutofillType() + ", options=" + java.util.Arrays.toString(viewNode.getAutofillOptions()) + ", hints=" + java.util.Arrays.toString(viewNode.getAutofillHints()) + ", value=" + viewNode.getAutofillValue() + ", sanitized=" + viewNode.isSanitized() + ", important=" + viewNode.getImportantForAutofill() + ", visibility=" + viewNode.getVisibility() + ", isCredential=" + viewNode.isCredential());
        }
        android.credentials.GetCredentialRequest credentialManagerRequest = viewNode.getCredentialManagerRequest();
        if (credentialManagerRequest == null) {
            android.util.Log.i(TAG, str + " No Credential Manager Request");
        } else {
            android.util.Log.i(TAG, str + "  GetCredentialRequest: no. of options= " + credentialManagerRequest.getCredentialOptions().size());
        }
        int childCount = viewNode.getChildCount();
        if (childCount > 0) {
            android.util.Log.i(TAG, str + "  Children:");
            java.lang.String str2 = str + "    ";
            for (int i = 0; i < childCount; i++) {
                dump(str2, viewNode.getChildAt(i), z);
            }
        }
    }

    public void setTaskId(int i) {
        this.mTaskId = i;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public void setActivityComponent(android.content.ComponentName componentName) {
        this.mActivityComponent = componentName;
    }

    public android.content.ComponentName getActivityComponent() {
        return this.mActivityComponent;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean isHomeActivity() {
        return this.mIsHomeActivity;
    }

    public int getWindowNodeCount() {
        ensureData();
        return this.mWindowNodes.size();
    }

    public android.app.assist.AssistStructure.WindowNode getWindowNodeAt(int i) {
        ensureData();
        return this.mWindowNodes.get(i);
    }

    public void ensureDataForAutofill() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        android.os.Binder.allowBlocking(this.mReceiveChannel);
        try {
            new android.app.assist.AssistStructure.ParcelTransferReader(this.mReceiveChannel).go();
        } finally {
            android.os.Binder.defaultBlocking(this.mReceiveChannel);
        }
    }

    public void ensureData() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        new android.app.assist.AssistStructure.ParcelTransferReader(this.mReceiveChannel).go();
    }

    boolean waitForReady() {
        boolean z;
        synchronized (this) {
            long uptimeMillis = android.os.SystemClock.uptimeMillis() + 5000;
            while (this.mPendingAsyncChildren.size() > 0) {
                long uptimeMillis2 = android.os.SystemClock.uptimeMillis();
                if (uptimeMillis2 >= uptimeMillis) {
                    break;
                }
                try {
                    wait(uptimeMillis - uptimeMillis2);
                } catch (java.lang.InterruptedException e) {
                }
            }
            if (this.mPendingAsyncChildren.size() <= 0) {
                z = false;
            } else {
                android.util.Log.w(TAG, "Skipping assist structure, waiting too long for async children (have " + this.mPendingAsyncChildren.size() + " remaining");
                z = true;
            }
        }
        return !z;
    }

    public void clearSendChannel() {
        if (this.mSendChannel != null) {
            this.mSendChannel.mAssistStructure = null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        android.content.ComponentName.writeToParcel(this.mActivityComponent, parcel);
        parcel.writeInt(this.mIsHomeActivity ? 1 : 0);
        if (this.mHaveData) {
            if (this.mSendChannel == null) {
                this.mSendChannel = new android.app.assist.AssistStructure.SendChannel(this);
            }
            parcel.writeStrongBinder(this.mSendChannel);
            return;
        }
        parcel.writeStrongBinder(this.mReceiveChannel);
    }

    static {
        INPUT_TYPE_VARIATIONS.put(32, "EmailSubject");
        INPUT_TYPE_VARIATIONS.put(112, "PostalAddress");
        INPUT_TYPE_VARIATIONS.put(96, "PersonName");
        INPUT_TYPE_VARIATIONS.put(128, "Password");
        INPUT_TYPE_VARIATIONS.put(144, "VisiblePassword");
        INPUT_TYPE_VARIATIONS.put(16, "URI");
        INPUT_TYPE_VARIATIONS.put(208, "WebEmailAddress");
        INPUT_TYPE_VARIATIONS.put(224, "WebPassword");
        INPUT_TYPE_VARIATIONS.put(80, "LongMessage");
        INPUT_TYPE_VARIATIONS.put(64, "ShortMessage");
        INPUT_TYPE_VARIATIONS.put(131072, "MultiLine");
        INPUT_TYPE_VARIATIONS.put(262144, "ImeMultiLine");
        INPUT_TYPE_VARIATIONS.put(176, "Filter");
    }

    private static java.lang.String getInputTypeString(int i) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(i);
        sb.append("(class=").append(i & 15).append(')');
        java.util.Iterator<java.lang.Integer> it = INPUT_TYPE_VARIATIONS.keySet().iterator();
        while (it.hasNext()) {
            int intValue = it.next().intValue();
            if ((intValue & i) == intValue) {
                sb.append('|').append(INPUT_TYPE_VARIATIONS.get(java.lang.Integer.valueOf(intValue)));
            }
        }
        return sb.toString();
    }
}
