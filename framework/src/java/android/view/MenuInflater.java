package android.view;

/* loaded from: classes4.dex */
public class MenuInflater {
    private static final java.lang.String LOG_TAG = "MenuInflater";
    private static final int NO_ID = 0;
    private static final java.lang.String XML_GROUP = "group";
    private static final java.lang.String XML_ITEM = "item";
    private static final java.lang.String XML_MENU = "menu";
    private final java.lang.Object[] mActionProviderConstructorArguments;
    private final java.lang.Object[] mActionViewConstructorArguments;
    private android.content.Context mContext;
    private java.lang.Object mRealOwner;
    private static final java.lang.Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = {android.content.Context.class};
    private static final java.lang.Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

    public MenuInflater(android.content.Context context) {
        this.mContext = context;
        this.mActionViewConstructorArguments = new java.lang.Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public MenuInflater(android.content.Context context, java.lang.Object obj) {
        this.mContext = context;
        this.mRealOwner = obj;
        this.mActionViewConstructorArguments = new java.lang.Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    public void inflate(int i, android.view.Menu menu) {
        android.content.res.XmlResourceParser xmlResourceParser = null;
        try {
            try {
                try {
                    xmlResourceParser = this.mContext.getResources().getLayout(i);
                    parseMenu(xmlResourceParser, android.util.Xml.asAttributeSet(xmlResourceParser), menu);
                } catch (java.io.IOException e) {
                    throw new android.view.InflateException("Error inflating menu XML", e);
                }
            } catch (org.xmlpull.v1.XmlPullParserException e2) {
                throw new android.view.InflateException("Error inflating menu XML", e2);
            }
        } finally {
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x004a, code lost:
    
        switch(r14) {
            case 1: goto L58;
            case 2: goto L38;
            case 3: goto L17;
            default: goto L70;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x004f, code lost:
    
        r14 = r12.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0053, code lost:
    
        if (r6 == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0059, code lost:
    
        if (r14.equals(r7) == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x005c, code lost:
    
        r6 = false;
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00d8, code lost:
    
        r14 = r12.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0064, code lost:
    
        if (r14.equals(android.view.MenuInflater.XML_GROUP) == false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0066, code lost:
    
        r0.resetGroup();
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006f, code lost:
    
        if (r14.equals("item") == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0075, code lost:
    
        if (r0.hasAddedItem() != false) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x007b, code lost:
    
        if (r0.itemActionProvider == null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0085, code lost:
    
        if (r0.itemActionProvider.hasSubMenu() == false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0087, code lost:
    
        registerMenu(r0.addSubMenuItem(), r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x008f, code lost:
    
        registerMenu(r0.addItem(), r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x009b, code lost:
    
        if (r14.equals(android.view.MenuInflater.XML_MENU) == false) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x009d, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x009f, code lost:
    
        if (r6 == false) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a2, code lost:
    
        r14 = r12.getName();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00aa, code lost:
    
        if (r14.equals(android.view.MenuInflater.XML_GROUP) == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ac, code lost:
    
        r0.readGroup(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00b4, code lost:
    
        if (r14.equals("item") == false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b6, code lost:
    
        r0.readItem(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00be, code lost:
    
        if (r14.equals(android.view.MenuInflater.XML_MENU) == false) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00c0, code lost:
    
        r14 = r0.addSubMenuItem();
        registerMenu(r14, r13);
        parseMenu(r12, r13, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00cb, code lost:
    
        r7 = r14;
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00d7, code lost:
    
        throw new java.lang.RuntimeException("Unexpected end of document");
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00de, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x003f, code lost:
    
        r5 = false;
        r6 = false;
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0044, code lost:
    
        if (r5 != false) goto L57;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void parseMenu(org.xmlpull.v1.XmlPullParser xmlPullParser, android.util.AttributeSet attributeSet, android.view.Menu menu) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        android.view.MenuInflater.MenuState menuState = new android.view.MenuInflater.MenuState(menu);
        int eventType = xmlPullParser.getEventType();
        while (true) {
            if (eventType == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals(XML_MENU)) {
                    eventType = xmlPullParser.next();
                } else {
                    throw new java.lang.RuntimeException("Expecting menu, got " + name);
                }
            } else {
                eventType = xmlPullParser.next();
                if (eventType == 1) {
                    break;
                }
            }
        }
    }

    private void registerMenu(android.view.MenuItem menuItem, android.util.AttributeSet attributeSet) {
    }

    private void registerMenu(android.view.SubMenu subMenu, android.util.AttributeSet attributeSet) {
    }

    android.content.Context getContext() {
        return this.mContext;
    }

    private static class InflatedOnMenuItemClickListener implements android.view.MenuItem.OnMenuItemClickListener {
        private static final java.lang.Class<?>[] PARAM_TYPES = {android.view.MenuItem.class};
        private java.lang.reflect.Method mMethod;
        private java.lang.Object mRealOwner;

        public InflatedOnMenuItemClickListener(java.lang.Object obj, java.lang.String str) {
            this.mRealOwner = obj;
            java.lang.Class<?> cls = obj.getClass();
            try {
                this.mMethod = cls.getMethod(str, PARAM_TYPES);
            } catch (java.lang.Exception e) {
                android.view.InflateException inflateException = new android.view.InflateException("Couldn't resolve menu item onClick handler " + str + " in class " + cls.getName());
                inflateException.initCause(e);
                throw inflateException;
            }
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(android.view.MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == java.lang.Boolean.TYPE) {
                    return ((java.lang.Boolean) this.mMethod.invoke(this.mRealOwner, menuItem)).booleanValue();
                }
                this.mMethod.invoke(this.mRealOwner, menuItem);
                return true;
            } catch (java.lang.Exception e) {
                throw new java.lang.RuntimeException(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public java.lang.Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = findRealOwner(this.mContext);
        }
        return this.mRealOwner;
    }

    private java.lang.Object findRealOwner(java.lang.Object obj) {
        if (obj instanceof android.app.Activity) {
            return obj;
        }
        if (obj instanceof android.content.ContextWrapper) {
            return findRealOwner(((android.content.ContextWrapper) obj).getBaseContext());
        }
        return obj;
    }

    private class MenuState {
        private static final int defaultGroupId = 0;
        private static final int defaultItemCategory = 0;
        private static final int defaultItemCheckable = 0;
        private static final boolean defaultItemChecked = false;
        private static final boolean defaultItemEnabled = true;
        private static final int defaultItemId = 0;
        private static final int defaultItemOrder = 0;
        private static final boolean defaultItemVisible = true;
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        private android.view.ActionProvider itemActionProvider;
        private java.lang.String itemActionProviderClassName;
        private java.lang.String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private java.lang.CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private int itemId;
        private java.lang.String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private java.lang.CharSequence itemTitle;
        private java.lang.CharSequence itemTitleCondensed;
        private java.lang.CharSequence itemTooltipText;
        private boolean itemVisible;
        private android.view.Menu menu;
        private android.content.res.ColorStateList itemIconTintList = null;
        private android.graphics.BlendMode mItemIconBlendMode = null;

        public MenuState(android.view.Menu menu) {
            this.menu = menu;
            resetGroup();
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }

        public void readGroup(android.util.AttributeSet attributeSet) {
            android.content.res.TypedArray obtainStyledAttributes = android.view.MenuInflater.this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MenuGroup);
            this.groupId = obtainStyledAttributes.getResourceId(1, 0);
            this.groupCategory = obtainStyledAttributes.getInt(3, 0);
            this.groupOrder = obtainStyledAttributes.getInt(4, 0);
            this.groupCheckable = obtainStyledAttributes.getInt(5, 0);
            this.groupVisible = obtainStyledAttributes.getBoolean(2, true);
            this.groupEnabled = obtainStyledAttributes.getBoolean(0, true);
            obtainStyledAttributes.recycle();
        }

        public void readItem(android.util.AttributeSet attributeSet) {
            android.content.res.TypedArray obtainStyledAttributes = android.view.MenuInflater.this.mContext.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.MenuItem);
            this.itemId = obtainStyledAttributes.getResourceId(2, 0);
            this.itemCategoryOrder = (obtainStyledAttributes.getInt(5, this.groupCategory) & (-65536)) | (obtainStyledAttributes.getInt(6, this.groupOrder) & 65535);
            this.itemTitle = obtainStyledAttributes.getText(7);
            this.itemTitleCondensed = obtainStyledAttributes.getText(8);
            this.itemIconResId = obtainStyledAttributes.getResourceId(0, 0);
            if (obtainStyledAttributes.hasValue(22)) {
                this.mItemIconBlendMode = android.graphics.drawable.Drawable.parseBlendMode(obtainStyledAttributes.getInt(22, -1), this.mItemIconBlendMode);
            } else {
                this.mItemIconBlendMode = null;
            }
            if (obtainStyledAttributes.hasValue(21)) {
                this.itemIconTintList = obtainStyledAttributes.getColorStateList(21);
            } else {
                this.itemIconTintList = null;
            }
            this.itemAlphabeticShortcut = getShortcut(obtainStyledAttributes.getString(9));
            this.itemAlphabeticModifiers = obtainStyledAttributes.getInt(19, 4096);
            this.itemNumericShortcut = getShortcut(obtainStyledAttributes.getString(10));
            this.itemNumericModifiers = obtainStyledAttributes.getInt(20, 4096);
            if (obtainStyledAttributes.hasValue(11)) {
                this.itemCheckable = obtainStyledAttributes.getBoolean(11, false) ? 1 : 0;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = obtainStyledAttributes.getBoolean(3, false);
            this.itemVisible = obtainStyledAttributes.getBoolean(4, this.groupVisible);
            this.itemEnabled = obtainStyledAttributes.getBoolean(1, this.groupEnabled);
            this.itemShowAsAction = obtainStyledAttributes.getInt(14, -1);
            this.itemListenerMethodName = obtainStyledAttributes.getString(12);
            this.itemActionViewLayout = obtainStyledAttributes.getResourceId(15, 0);
            this.itemActionViewClassName = obtainStyledAttributes.getString(16);
            this.itemActionProviderClassName = obtainStyledAttributes.getString(17);
            boolean z = this.itemActionProviderClassName != null;
            if (z && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (android.view.ActionProvider) newInstance(this.itemActionProviderClassName, android.view.MenuInflater.ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, android.view.MenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (z) {
                    android.util.Log.w(android.view.MenuInflater.LOG_TAG, "Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = obtainStyledAttributes.getText(13);
            this.itemTooltipText = obtainStyledAttributes.getText(18);
            obtainStyledAttributes.recycle();
            this.itemAdded = false;
        }

        private char getShortcut(java.lang.String str) {
            if (str == null) {
                return (char) 0;
            }
            return str.charAt(0);
        }

        private void setItem(android.view.MenuItem menuItem) {
            boolean z = false;
            menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled).setCheckable(this.itemCheckable >= 1).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId).setAlphabeticShortcut(this.itemAlphabeticShortcut, this.itemAlphabeticModifiers).setNumericShortcut(this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemShowAsAction >= 0) {
                menuItem.setShowAsAction(this.itemShowAsAction);
            }
            if (this.mItemIconBlendMode != null) {
                menuItem.setIconTintBlendMode(this.mItemIconBlendMode);
            }
            if (this.itemIconTintList != null) {
                menuItem.setIconTintList(this.itemIconTintList);
            }
            if (this.itemListenerMethodName != null) {
                if (android.view.MenuInflater.this.mContext.isRestricted()) {
                    throw new java.lang.IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener(new android.view.MenuInflater.InflatedOnMenuItemClickListener(android.view.MenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (menuItem instanceof com.android.internal.view.menu.MenuItemImpl) {
                com.android.internal.view.menu.MenuItemImpl menuItemImpl = (com.android.internal.view.menu.MenuItemImpl) menuItem;
                if (this.itemCheckable >= 2) {
                    menuItemImpl.setExclusiveCheckable(true);
                }
            }
            if (this.itemActionViewClassName != null) {
                menuItem.setActionView((android.view.View) newInstance(this.itemActionViewClassName, android.view.MenuInflater.ACTION_VIEW_CONSTRUCTOR_SIGNATURE, android.view.MenuInflater.this.mActionViewConstructorArguments));
                z = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!z) {
                    menuItem.setActionView(this.itemActionViewLayout);
                } else {
                    android.util.Log.w(android.view.MenuInflater.LOG_TAG, "Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                menuItem.setActionProvider(this.itemActionProvider);
            }
            menuItem.setContentDescription(this.itemContentDescription);
            menuItem.setTooltipText(this.itemTooltipText);
        }

        public android.view.MenuItem addItem() {
            this.itemAdded = true;
            android.view.MenuItem add = this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(add);
            return add;
        }

        public android.view.SubMenu addSubMenuItem() {
            this.itemAdded = true;
            android.view.SubMenu addSubMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            setItem(addSubMenu.getItem());
            return addSubMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        private <T> T newInstance(java.lang.String str, java.lang.Class<?>[] clsArr, java.lang.Object[] objArr) {
            try {
                java.lang.reflect.Constructor<?> constructor = android.view.MenuInflater.this.mContext.getClassLoader().loadClass(str).getConstructor(clsArr);
                constructor.setAccessible(true);
                return (T) constructor.newInstance(objArr);
            } catch (java.lang.Exception e) {
                android.util.Log.w(android.view.MenuInflater.LOG_TAG, "Cannot instantiate class: " + str, e);
                return null;
            }
        }
    }
}
