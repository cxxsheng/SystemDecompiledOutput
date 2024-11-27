package android.widget;

/* loaded from: classes4.dex */
public class QuickContactBadge extends android.widget.ImageView implements android.view.View.OnClickListener {
    static final int EMAIL_ID_COLUMN_INDEX = 0;
    static final int EMAIL_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final java.lang.String EXTRA_URI_CONTENT = "uri_content";
    static final int PHONE_ID_COLUMN_INDEX = 0;
    static final int PHONE_LOOKUP_STRING_COLUMN_INDEX = 1;
    private static final int TOKEN_EMAIL_LOOKUP = 0;
    private static final int TOKEN_EMAIL_LOOKUP_AND_TRIGGER = 2;
    private static final int TOKEN_PHONE_LOOKUP = 1;
    private static final int TOKEN_PHONE_LOOKUP_AND_TRIGGER = 3;
    private java.lang.String mContactEmail;
    private java.lang.String mContactPhone;
    private android.net.Uri mContactUri;
    private android.graphics.drawable.Drawable mDefaultAvatar;
    protected java.lang.String[] mExcludeMimes;
    private android.os.Bundle mExtras;
    private android.graphics.drawable.Drawable mOverlay;
    private java.lang.String mPrioritizedMimeType;
    private android.widget.QuickContactBadge.QueryHandler mQueryHandler;
    static final java.lang.String[] EMAIL_LOOKUP_PROJECTION = {"contact_id", "lookup"};
    static final java.lang.String[] PHONE_LOOKUP_PROJECTION = {"_id", "lookup"};

    public QuickContactBadge(android.content.Context context) {
        this(context, null);
    }

    public QuickContactBadge(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QuickContactBadge(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public QuickContactBadge(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mExtras = null;
        this.mExcludeMimes = null;
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(com.android.internal.R.styleable.Theme);
        this.mOverlay = obtainStyledAttributes.getDrawable(389);
        obtainStyledAttributes.recycle();
        setOnClickListener(this);
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mQueryHandler = new android.widget.QuickContactBadge.QueryHandler(this.mContext.getContentResolver());
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        android.graphics.drawable.Drawable drawable = this.mOverlay;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        if (this.mOverlay != null) {
            this.mOverlay.setHotspot(f, f2);
        }
    }

    public void setMode(int i) {
    }

    public void setPrioritizedMimeType(java.lang.String str) {
        this.mPrioritizedMimeType = str;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(android.graphics.Canvas canvas) {
        super.onDraw(canvas);
        if (!isEnabled() || this.mOverlay == null || this.mOverlay.getIntrinsicWidth() == 0 || this.mOverlay.getIntrinsicHeight() == 0) {
            return;
        }
        this.mOverlay.setBounds(0, 0, getWidth(), getHeight());
        if (this.mPaddingTop == 0 && this.mPaddingLeft == 0) {
            this.mOverlay.draw(canvas);
            return;
        }
        int saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.translate(this.mPaddingLeft, this.mPaddingTop);
        this.mOverlay.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    private boolean isAssigned() {
        return (this.mContactUri == null && this.mContactEmail == null && this.mContactPhone == null) ? false : true;
    }

    public void setImageToDefault() {
        if (this.mDefaultAvatar == null) {
            this.mDefaultAvatar = this.mContext.getDrawable(com.android.internal.R.drawable.ic_contact_picture);
        }
        lambda$setImageURIAsync$2(this.mDefaultAvatar);
    }

    public void assignContactUri(android.net.Uri uri) {
        this.mContactUri = uri;
        this.mContactEmail = null;
        this.mContactPhone = null;
        onContactUriChanged();
    }

    public void assignContactFromEmail(java.lang.String str, boolean z) {
        assignContactFromEmail(str, z, null);
    }

    public void assignContactFromEmail(java.lang.String str, boolean z, android.os.Bundle bundle) {
        this.mContactEmail = str;
        this.mExtras = bundle;
        if (!z && this.mQueryHandler != null) {
            this.mQueryHandler.startQuery(0, null, android.net.Uri.withAppendedPath(android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, android.net.Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            onContactUriChanged();
        }
    }

    public void assignContactFromPhone(java.lang.String str, boolean z) {
        assignContactFromPhone(str, z, new android.os.Bundle());
    }

    public void assignContactFromPhone(java.lang.String str, boolean z, android.os.Bundle bundle) {
        this.mContactPhone = str;
        this.mExtras = bundle;
        if (!z && this.mQueryHandler != null) {
            this.mQueryHandler.startQuery(1, null, android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
        } else {
            this.mContactUri = null;
            onContactUriChanged();
        }
    }

    public void setOverlay(android.graphics.drawable.Drawable drawable) {
        this.mOverlay = drawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onContactUriChanged() {
        setEnabled(isAssigned());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        android.os.Bundle bundle = this.mExtras == null ? new android.os.Bundle() : this.mExtras;
        if (this.mContactUri != null) {
            android.provider.ContactsContract.QuickContact.showQuickContact(getContext(), this, this.mContactUri, this.mExcludeMimes, this.mPrioritizedMimeType);
            return;
        }
        if (this.mContactEmail != null && this.mQueryHandler != null) {
            bundle.putString(EXTRA_URI_CONTENT, this.mContactEmail);
            this.mQueryHandler.startQuery(2, bundle, android.net.Uri.withAppendedPath(android.provider.ContactsContract.CommonDataKinds.Email.CONTENT_LOOKUP_URI, android.net.Uri.encode(this.mContactEmail)), EMAIL_LOOKUP_PROJECTION, null, null, null);
        } else if (this.mContactPhone != null && this.mQueryHandler != null) {
            bundle.putString(EXTRA_URI_CONTENT, this.mContactPhone);
            this.mQueryHandler.startQuery(3, bundle, android.net.Uri.withAppendedPath(android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI, this.mContactPhone), PHONE_LOOKUP_PROJECTION, null, null, null);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public java.lang.CharSequence getAccessibilityClassName() {
        return android.widget.QuickContactBadge.class.getName();
    }

    public void setExcludeMimes(java.lang.String[] strArr) {
        this.mExcludeMimes = strArr;
    }

    private class QueryHandler extends android.content.AsyncQueryHandler {
        public QueryHandler(android.content.ContentResolver contentResolver) {
            super(contentResolver);
        }

        @Override // android.content.AsyncQueryHandler
        protected void onQueryComplete(int i, java.lang.Object obj, android.database.Cursor cursor) {
            android.net.Uri fromParts;
            boolean z;
            android.os.Bundle bundle = obj != null ? (android.os.Bundle) obj : new android.os.Bundle();
            android.net.Uri uri = null;
            boolean z2 = false;
            try {
                switch (i) {
                    case 0:
                        fromParts = null;
                        z = false;
                        if (cursor != null && cursor.moveToFirst()) {
                            uri = android.provider.ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                            z2 = z;
                            break;
                        }
                        z2 = z;
                        break;
                    case 1:
                        fromParts = null;
                        z = false;
                        if (cursor != null && cursor.moveToFirst()) {
                            uri = android.provider.ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                            z2 = z;
                            break;
                        }
                        z2 = z;
                        break;
                    case 2:
                        fromParts = android.net.Uri.fromParts("mailto", bundle.getString(android.widget.QuickContactBadge.EXTRA_URI_CONTENT), null);
                        z = true;
                        if (cursor != null) {
                            uri = android.provider.ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                            z2 = z;
                            break;
                        }
                        z2 = z;
                        break;
                    case 3:
                        fromParts = android.net.Uri.fromParts(android.telecom.PhoneAccount.SCHEME_TEL, bundle.getString(android.widget.QuickContactBadge.EXTRA_URI_CONTENT), null);
                        z = true;
                        if (cursor != null) {
                            uri = android.provider.ContactsContract.Contacts.getLookupUri(cursor.getLong(0), cursor.getString(1));
                            z2 = z;
                            break;
                        }
                        z2 = z;
                        break;
                    default:
                        fromParts = null;
                        break;
                }
                if (cursor != null) {
                    cursor.close();
                }
                android.widget.QuickContactBadge.this.mContactUri = uri;
                android.widget.QuickContactBadge.this.onContactUriChanged();
                if (z2 && android.widget.QuickContactBadge.this.mContactUri != null) {
                    android.provider.ContactsContract.QuickContact.showQuickContact(android.widget.QuickContactBadge.this.getContext(), android.widget.QuickContactBadge.this, android.widget.QuickContactBadge.this.mContactUri, android.widget.QuickContactBadge.this.mExcludeMimes, android.widget.QuickContactBadge.this.mPrioritizedMimeType);
                    return;
                }
                if (fromParts != null) {
                    android.content.Intent intent = new android.content.Intent("com.android.contacts.action.SHOW_OR_CREATE_CONTACT", fromParts);
                    if (bundle != null) {
                        android.os.Bundle bundle2 = new android.os.Bundle(bundle);
                        bundle2.remove(android.widget.QuickContactBadge.EXTRA_URI_CONTENT);
                        intent.putExtras(bundle2);
                    }
                    android.widget.QuickContactBadge.this.getContext().startActivity(intent);
                }
            } catch (java.lang.Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
    }
}
