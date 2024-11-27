package android.view.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes4.dex */
public final class ContentCaptureEvent implements android.os.Parcelable {
    public static final int MAX_INVALID_VALUE = -1;
    public static final int TYPE_CONTEXT_UPDATED = 6;
    public static final int TYPE_SESSION_FINISHED = -2;
    public static final int TYPE_SESSION_PAUSED = 8;
    public static final int TYPE_SESSION_RESUMED = 7;
    public static final int TYPE_SESSION_STARTED = -1;
    public static final int TYPE_VIEW_APPEARED = 1;
    public static final int TYPE_VIEW_DISAPPEARED = 2;
    public static final int TYPE_VIEW_INSETS_CHANGED = 9;
    public static final int TYPE_VIEW_TEXT_CHANGED = 3;
    public static final int TYPE_VIEW_TREE_APPEARED = 5;
    public static final int TYPE_VIEW_TREE_APPEARING = 4;
    public static final int TYPE_WINDOW_BOUNDS_CHANGED = 10;
    private android.graphics.Rect mBounds;
    private android.view.contentcapture.ContentCaptureContext mClientContext;
    private int mComposingEnd;
    private int mComposingStart;
    private final long mEventTime;
    private android.view.autofill.AutofillId mId;
    private java.util.ArrayList<android.view.autofill.AutofillId> mIds;
    private android.graphics.Insets mInsets;
    private android.view.contentcapture.ViewNode mNode;
    private int mParentSessionId;
    private int mSelectionEndIndex;
    private int mSelectionStartIndex;
    private final int mSessionId;
    private java.lang.CharSequence mText;
    private boolean mTextHasComposingSpan;
    private final int mType;
    private static final java.lang.String TAG = android.view.contentcapture.ContentCaptureEvent.class.getSimpleName();
    public static final android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureEvent> CREATOR = new android.os.Parcelable.Creator<android.view.contentcapture.ContentCaptureEvent>() { // from class: android.view.contentcapture.ContentCaptureEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureEvent createFromParcel(android.os.Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            android.view.contentcapture.ContentCaptureEvent contentCaptureEvent = new android.view.contentcapture.ContentCaptureEvent(readInt, readInt2, parcel.readLong());
            android.view.autofill.AutofillId autofillId = (android.view.autofill.AutofillId) parcel.readParcelable(null, android.view.autofill.AutofillId.class);
            if (autofillId != null) {
                contentCaptureEvent.setAutofillId(autofillId);
            }
            java.util.ArrayList<android.view.autofill.AutofillId> createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillId.CREATOR);
            if (createTypedArrayList != null) {
                contentCaptureEvent.setAutofillIds(createTypedArrayList);
            }
            android.view.contentcapture.ViewNode readFromParcel = android.view.contentcapture.ViewNode.readFromParcel(parcel);
            if (readFromParcel != null) {
                contentCaptureEvent.setViewNode(readFromParcel);
            }
            contentCaptureEvent.setText(parcel.readCharSequence());
            if (readInt2 == -1 || readInt2 == -2) {
                contentCaptureEvent.setParentSessionId(parcel.readInt());
            }
            if (readInt2 == -1 || readInt2 == 6) {
                contentCaptureEvent.setClientContext((android.view.contentcapture.ContentCaptureContext) parcel.readParcelable(null, android.view.contentcapture.ContentCaptureContext.class));
            }
            if (readInt2 == 9) {
                contentCaptureEvent.setInsets((android.graphics.Insets) parcel.readParcelable(null, android.graphics.Insets.class));
            }
            if (readInt2 == 10) {
                contentCaptureEvent.setBounds((android.graphics.Rect) parcel.readParcelable(null, android.graphics.Rect.class));
            }
            if (readInt2 == 3) {
                contentCaptureEvent.setComposingIndex(parcel.readInt(), parcel.readInt());
                contentCaptureEvent.restoreComposingSpan();
                contentCaptureEvent.setSelectionIndex(parcel.readInt(), parcel.readInt());
                contentCaptureEvent.restoreSelectionSpans();
            }
            return contentCaptureEvent;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.contentcapture.ContentCaptureEvent[] newArray(int i) {
            return new android.view.contentcapture.ContentCaptureEvent[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface EventType {
    }

    public ContentCaptureEvent(int i, int i2, long j) {
        this.mParentSessionId = 0;
        this.mComposingStart = -1;
        this.mComposingEnd = -1;
        this.mSelectionStartIndex = -1;
        this.mSelectionEndIndex = -1;
        this.mSessionId = i;
        this.mType = i2;
        this.mEventTime = j;
    }

    public ContentCaptureEvent(int i, int i2) {
        this(i, i2, java.lang.System.currentTimeMillis());
    }

    public android.view.contentcapture.ContentCaptureEvent setAutofillId(android.view.autofill.AutofillId autofillId) {
        this.mId = (android.view.autofill.AutofillId) java.util.Objects.requireNonNull(autofillId);
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setAutofillIds(java.util.ArrayList<android.view.autofill.AutofillId> arrayList) {
        this.mIds = (java.util.ArrayList) java.util.Objects.requireNonNull(arrayList);
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent addAutofillId(android.view.autofill.AutofillId autofillId) {
        java.util.Objects.requireNonNull(autofillId);
        if (this.mIds == null) {
            this.mIds = new java.util.ArrayList<>();
            if (this.mId == null) {
                android.util.Log.w(TAG, "addAutofillId(" + autofillId + ") called without an initial id");
            } else {
                this.mIds.add(this.mId);
                this.mId = null;
            }
        }
        this.mIds.add(autofillId);
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setParentSessionId(int i) {
        this.mParentSessionId = i;
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setClientContext(android.view.contentcapture.ContentCaptureContext contentCaptureContext) {
        this.mClientContext = contentCaptureContext;
        return this;
    }

    public int getSessionId() {
        return this.mSessionId;
    }

    public int getParentSessionId() {
        return this.mParentSessionId;
    }

    public android.view.contentcapture.ContentCaptureContext getContentCaptureContext() {
        return this.mClientContext;
    }

    public android.view.contentcapture.ContentCaptureEvent setViewNode(android.view.contentcapture.ViewNode viewNode) {
        this.mNode = (android.view.contentcapture.ViewNode) java.util.Objects.requireNonNull(viewNode);
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setText(java.lang.CharSequence charSequence) {
        this.mText = charSequence;
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setComposingIndex(int i, int i2) {
        this.mComposingStart = i;
        this.mComposingEnd = i2;
        return this;
    }

    public boolean hasComposingSpan() {
        return this.mComposingStart > -1;
    }

    public android.view.contentcapture.ContentCaptureEvent setSelectionIndex(int i, int i2) {
        this.mSelectionStartIndex = i;
        this.mSelectionEndIndex = i2;
        return this;
    }

    boolean hasSameComposingSpan(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        return this.mComposingStart == contentCaptureEvent.mComposingStart && this.mComposingEnd == contentCaptureEvent.mComposingEnd;
    }

    boolean hasSameSelectionSpan(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        return this.mSelectionStartIndex == contentCaptureEvent.mSelectionStartIndex && this.mSelectionEndIndex == contentCaptureEvent.mSelectionEndIndex;
    }

    private int getComposingStart() {
        return this.mComposingStart;
    }

    private int getComposingEnd() {
        return this.mComposingEnd;
    }

    private int getSelectionStart() {
        return this.mSelectionStartIndex;
    }

    private int getSelectionEnd() {
        return this.mSelectionEndIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreComposingSpan() {
        if (this.mComposingStart <= -1 || this.mComposingEnd <= -1) {
            return;
        }
        if (this.mText instanceof android.text.Spannable) {
            android.view.inputmethod.BaseInputConnection.setComposingSpans((android.text.Spannable) this.mText, this.mComposingStart, this.mComposingEnd);
        } else {
            android.util.Log.w(TAG, "Text is not a Spannable.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void restoreSelectionSpans() {
        if (this.mSelectionStartIndex <= -1 || this.mSelectionEndIndex <= -1) {
            return;
        }
        if (this.mText instanceof android.text.SpannableString) {
            android.text.SpannableString spannableString = (android.text.SpannableString) this.mText;
            spannableString.setSpan(android.text.Selection.SELECTION_START, this.mSelectionStartIndex, this.mSelectionStartIndex, 0);
            spannableString.setSpan(android.text.Selection.SELECTION_END, this.mSelectionEndIndex, this.mSelectionEndIndex, 0);
            return;
        }
        android.util.Log.w(TAG, "Text is not a SpannableString.");
    }

    public android.view.contentcapture.ContentCaptureEvent setInsets(android.graphics.Insets insets) {
        this.mInsets = insets;
        return this;
    }

    public android.view.contentcapture.ContentCaptureEvent setBounds(android.graphics.Rect rect) {
        this.mBounds = rect;
        return this;
    }

    public int getType() {
        return this.mType;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public android.view.contentcapture.ViewNode getViewNode() {
        return this.mNode;
    }

    public android.view.autofill.AutofillId getId() {
        return this.mId;
    }

    public java.util.List<android.view.autofill.AutofillId> getIds() {
        return this.mIds;
    }

    public java.lang.CharSequence getText() {
        return this.mText;
    }

    public android.graphics.Insets getInsets() {
        return this.mInsets;
    }

    public android.graphics.Rect getBounds() {
        return this.mBounds;
    }

    public void mergeEvent(android.view.contentcapture.ContentCaptureEvent contentCaptureEvent) {
        java.util.Objects.requireNonNull(contentCaptureEvent);
        int type = contentCaptureEvent.getType();
        if (this.mType != type) {
            android.util.Log.e(TAG, "mergeEvent(" + getTypeAsString(type) + ") cannot be merged with different eventType=" + getTypeAsString(this.mType));
            return;
        }
        if (type == 2) {
            java.util.List<android.view.autofill.AutofillId> ids = contentCaptureEvent.getIds();
            android.view.autofill.AutofillId id = contentCaptureEvent.getId();
            if (ids != null) {
                if (id != null) {
                    android.util.Log.w(TAG, "got TYPE_VIEW_DISAPPEARED event with both id and ids: " + contentCaptureEvent);
                }
                for (int i = 0; i < ids.size(); i++) {
                    addAutofillId(ids.get(i));
                }
                return;
            }
            if (id != null) {
                addAutofillId(id);
                return;
            }
            throw new java.lang.IllegalArgumentException("mergeEvent(): got TYPE_VIEW_DISAPPEARED event with neither id or ids: " + contentCaptureEvent);
        }
        if (type == 3) {
            setText(contentCaptureEvent.getText());
            setComposingIndex(contentCaptureEvent.getComposingStart(), contentCaptureEvent.getComposingEnd());
            setSelectionIndex(contentCaptureEvent.getSelectionStart(), contentCaptureEvent.getSelectionEnd());
            return;
        }
        android.util.Log.e(TAG, "mergeEvent(" + getTypeAsString(type) + ") does not support this event type.");
    }

    public void dump(java.io.PrintWriter printWriter) {
        printWriter.print("type=");
        printWriter.print(getTypeAsString(this.mType));
        printWriter.print(", time=");
        printWriter.print(this.mEventTime);
        if (this.mId != null) {
            printWriter.print(", id=");
            printWriter.print(this.mId);
        }
        if (this.mIds != null) {
            printWriter.print(", ids=");
            printWriter.print(this.mIds);
        }
        if (this.mNode != null) {
            printWriter.print(", mNode.id=");
            printWriter.print(this.mNode.getAutofillId());
        }
        if (this.mSessionId != 0) {
            printWriter.print(", sessionId=");
            printWriter.print(this.mSessionId);
        }
        if (this.mParentSessionId != 0) {
            printWriter.print(", parentSessionId=");
            printWriter.print(this.mParentSessionId);
        }
        if (this.mText != null) {
            printWriter.print(", text=");
            printWriter.println(android.view.contentcapture.ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            printWriter.print(", context=");
            this.mClientContext.dump(printWriter);
            printWriter.println();
        }
        if (this.mInsets != null) {
            printWriter.print(", insets=");
            printWriter.println(this.mInsets);
        }
        if (this.mBounds != null) {
            printWriter.print(", bounds=");
            printWriter.println(this.mBounds);
        }
        if (this.mComposingStart > -1) {
            printWriter.print(", composing(");
            printWriter.print(this.mComposingStart);
            printWriter.print(", ");
            printWriter.print(this.mComposingEnd);
            printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        if (this.mSelectionStartIndex > -1) {
            printWriter.print(", selection(");
            printWriter.print(this.mSelectionStartIndex);
            printWriter.print(", ");
            printWriter.print(this.mSelectionEndIndex);
            printWriter.print(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder("ContentCaptureEvent[type=").append(getTypeAsString(this.mType));
        append.append(", session=").append(this.mSessionId);
        if (this.mType == -1 && this.mParentSessionId != 0) {
            append.append(", parent=").append(this.mParentSessionId);
        }
        if (this.mId != null) {
            append.append(", id=").append(this.mId);
        }
        if (this.mIds != null) {
            append.append(", ids=").append(this.mIds);
        }
        if (this.mNode != null) {
            append.append(", class=").append(this.mNode.getClassName());
            append.append(", id=").append(this.mNode.getAutofillId());
            if (this.mNode.getText() != null) {
                append.append(", text=").append((java.lang.CharSequence) android.view.contentcapture.ContentCaptureHelper.getSanitizedString(this.mNode.getText()));
            }
        }
        if (this.mText != null) {
            append.append(", text=").append((java.lang.CharSequence) android.view.contentcapture.ContentCaptureHelper.getSanitizedString(this.mText));
        }
        if (this.mClientContext != null) {
            append.append(", context=").append(this.mClientContext);
        }
        if (this.mInsets != null) {
            append.append(", insets=").append(this.mInsets);
        }
        if (this.mBounds != null) {
            append.append(", bounds=").append(this.mBounds);
        }
        if (this.mComposingStart > -1) {
            append.append(", composing=[").append(this.mComposingStart).append(",").append(this.mComposingEnd).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (this.mSelectionStartIndex > -1) {
            append.append(", selection=[").append(this.mSelectionStartIndex).append(",").append(this.mSelectionEndIndex).append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        return append.append(']').toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mSessionId);
        parcel.writeInt(this.mType);
        parcel.writeLong(this.mEventTime);
        parcel.writeParcelable(this.mId, i);
        parcel.writeTypedList(this.mIds);
        android.view.contentcapture.ViewNode.writeToParcel(parcel, this.mNode, i);
        parcel.writeCharSequence(this.mText);
        if (this.mType == -1 || this.mType == -2) {
            parcel.writeInt(this.mParentSessionId);
        }
        if (this.mType == -1 || this.mType == 6) {
            parcel.writeParcelable(this.mClientContext, i);
        }
        if (this.mType == 9) {
            parcel.writeParcelable(this.mInsets, i);
        }
        if (this.mType == 10) {
            parcel.writeParcelable(this.mBounds, i);
        }
        if (this.mType == 3) {
            parcel.writeInt(this.mComposingStart);
            parcel.writeInt(this.mComposingEnd);
            parcel.writeInt(this.mSelectionStartIndex);
            parcel.writeInt(this.mSelectionEndIndex);
        }
    }

    public static java.lang.String getTypeAsString(int i) {
        switch (i) {
            case -2:
                return "SESSION_FINISHED";
            case -1:
                return "SESSION_STARTED";
            case 0:
            default:
                return "UKNOWN_TYPE: " + i;
            case 1:
                return "VIEW_APPEARED";
            case 2:
                return "VIEW_DISAPPEARED";
            case 3:
                return "VIEW_TEXT_CHANGED";
            case 4:
                return "VIEW_TREE_APPEARING";
            case 5:
                return "VIEW_TREE_APPEARED";
            case 6:
                return "CONTEXT_UPDATED";
            case 7:
                return "SESSION_RESUMED";
            case 8:
                return "SESSION_PAUSED";
            case 9:
                return "VIEW_INSETS_CHANGED";
            case 10:
                return "TYPE_WINDOW_BOUNDS_CHANGED";
        }
    }
}
