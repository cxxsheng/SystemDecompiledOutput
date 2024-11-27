package com.android.server.display.config;

/* loaded from: classes.dex */
public class DisplayConfiguration {

    @android.annotation.NonNull
    private com.android.server.display.config.Thresholds ambientBrightnessChangeThresholds;
    private com.android.server.display.config.Thresholds ambientBrightnessChangeThresholdsIdle;
    private java.math.BigInteger ambientLightHorizonLong;
    private java.math.BigInteger ambientLightHorizonShort;
    private com.android.server.display.config.AutoBrightness autoBrightness;

    @android.annotation.Nullable
    private com.android.server.display.config.DensityMapping densityMapping;

    @android.annotation.NonNull
    private com.android.server.display.config.Thresholds displayBrightnessChangeThresholds;
    private com.android.server.display.config.Thresholds displayBrightnessChangeThresholdsIdle;

    @android.annotation.Nullable
    private com.android.server.display.config.HdrBrightnessConfig hdrBrightnessConfig;
    private com.android.server.display.config.HighBrightnessMode highBrightnessMode;
    private com.android.server.display.config.SensorDetails lightSensor;
    private com.android.server.display.config.LuxThrottling luxThrottling;

    @android.annotation.Nullable
    private java.lang.String name;
    private com.android.server.display.config.PowerThrottlingConfig powerThrottlingConfig;
    private com.android.server.display.config.SensorDetails proxSensor;
    private com.android.server.display.config.DisplayQuirks quirks;
    private com.android.server.display.config.RefreshRateConfigs refreshRate;
    private java.math.BigDecimal screenBrightnessCapForWearBedtimeMode;

    @android.annotation.NonNull
    private java.math.BigDecimal screenBrightnessDefault;

    @android.annotation.NonNull
    private com.android.server.display.config.NitsMap screenBrightnessMap;
    private java.math.BigInteger screenBrightnessRampDecreaseMaxIdleMillis;
    private java.math.BigInteger screenBrightnessRampDecreaseMaxMillis;
    private java.math.BigDecimal screenBrightnessRampFastDecrease;
    private java.math.BigDecimal screenBrightnessRampFastIncrease;
    private java.math.BigInteger screenBrightnessRampIncreaseMaxIdleMillis;
    private java.math.BigInteger screenBrightnessRampIncreaseMaxMillis;
    private java.math.BigDecimal screenBrightnessRampSlowDecrease;
    private java.math.BigDecimal screenBrightnessRampSlowDecreaseIdle;
    private java.math.BigDecimal screenBrightnessRampSlowIncrease;
    private java.math.BigDecimal screenBrightnessRampSlowIncreaseIdle;
    private com.android.server.display.config.SensorDetails screenOffBrightnessSensor;
    private com.android.server.display.config.IntegerArray screenOffBrightnessSensorValueToLux;
    private com.android.server.display.config.SensorDetails tempSensor;

    @android.annotation.NonNull
    private com.android.server.display.config.ThermalThrottling thermalThrottling;
    private com.android.server.display.config.UsiVersion usiVersion;

    @android.annotation.Nullable
    public final java.lang.String getName() {
        return this.name;
    }

    boolean hasName() {
        if (this.name == null) {
            return false;
        }
        return true;
    }

    public final void setName(@android.annotation.Nullable java.lang.String str) {
        this.name = str;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.DensityMapping getDensityMapping() {
        return this.densityMapping;
    }

    boolean hasDensityMapping() {
        if (this.densityMapping == null) {
            return false;
        }
        return true;
    }

    public final void setDensityMapping(@android.annotation.Nullable com.android.server.display.config.DensityMapping densityMapping) {
        this.densityMapping = densityMapping;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.NitsMap getScreenBrightnessMap() {
        return this.screenBrightnessMap;
    }

    boolean hasScreenBrightnessMap() {
        if (this.screenBrightnessMap == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessMap(@android.annotation.NonNull com.android.server.display.config.NitsMap nitsMap) {
        this.screenBrightnessMap = nitsMap;
    }

    @android.annotation.NonNull
    public final java.math.BigDecimal getScreenBrightnessDefault() {
        return this.screenBrightnessDefault;
    }

    boolean hasScreenBrightnessDefault() {
        if (this.screenBrightnessDefault == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessDefault(@android.annotation.NonNull java.math.BigDecimal bigDecimal) {
        this.screenBrightnessDefault = bigDecimal;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.ThermalThrottling getThermalThrottling() {
        return this.thermalThrottling;
    }

    boolean hasThermalThrottling() {
        if (this.thermalThrottling == null) {
            return false;
        }
        return true;
    }

    public final void setThermalThrottling(@android.annotation.NonNull com.android.server.display.config.ThermalThrottling thermalThrottling) {
        this.thermalThrottling = thermalThrottling;
    }

    public com.android.server.display.config.PowerThrottlingConfig getPowerThrottlingConfig() {
        return this.powerThrottlingConfig;
    }

    boolean hasPowerThrottlingConfig() {
        if (this.powerThrottlingConfig == null) {
            return false;
        }
        return true;
    }

    public void setPowerThrottlingConfig(com.android.server.display.config.PowerThrottlingConfig powerThrottlingConfig) {
        this.powerThrottlingConfig = powerThrottlingConfig;
    }

    public com.android.server.display.config.LuxThrottling getLuxThrottling() {
        return this.luxThrottling;
    }

    boolean hasLuxThrottling() {
        if (this.luxThrottling == null) {
            return false;
        }
        return true;
    }

    public void setLuxThrottling(com.android.server.display.config.LuxThrottling luxThrottling) {
        this.luxThrottling = luxThrottling;
    }

    public com.android.server.display.config.HighBrightnessMode getHighBrightnessMode() {
        return this.highBrightnessMode;
    }

    boolean hasHighBrightnessMode() {
        if (this.highBrightnessMode == null) {
            return false;
        }
        return true;
    }

    public void setHighBrightnessMode(com.android.server.display.config.HighBrightnessMode highBrightnessMode) {
        this.highBrightnessMode = highBrightnessMode;
    }

    @android.annotation.Nullable
    public final com.android.server.display.config.HdrBrightnessConfig getHdrBrightnessConfig() {
        return this.hdrBrightnessConfig;
    }

    boolean hasHdrBrightnessConfig() {
        if (this.hdrBrightnessConfig == null) {
            return false;
        }
        return true;
    }

    public final void setHdrBrightnessConfig(@android.annotation.Nullable com.android.server.display.config.HdrBrightnessConfig hdrBrightnessConfig) {
        this.hdrBrightnessConfig = hdrBrightnessConfig;
    }

    public com.android.server.display.config.DisplayQuirks getQuirks() {
        return this.quirks;
    }

    boolean hasQuirks() {
        if (this.quirks == null) {
            return false;
        }
        return true;
    }

    public void setQuirks(com.android.server.display.config.DisplayQuirks displayQuirks) {
        this.quirks = displayQuirks;
    }

    public com.android.server.display.config.AutoBrightness getAutoBrightness() {
        return this.autoBrightness;
    }

    boolean hasAutoBrightness() {
        if (this.autoBrightness == null) {
            return false;
        }
        return true;
    }

    public void setAutoBrightness(com.android.server.display.config.AutoBrightness autoBrightness) {
        this.autoBrightness = autoBrightness;
    }

    public com.android.server.display.config.RefreshRateConfigs getRefreshRate() {
        return this.refreshRate;
    }

    boolean hasRefreshRate() {
        if (this.refreshRate == null) {
            return false;
        }
        return true;
    }

    public void setRefreshRate(com.android.server.display.config.RefreshRateConfigs refreshRateConfigs) {
        this.refreshRate = refreshRateConfigs;
    }

    public final java.math.BigDecimal getScreenBrightnessRampFastDecrease() {
        return this.screenBrightnessRampFastDecrease;
    }

    boolean hasScreenBrightnessRampFastDecrease() {
        if (this.screenBrightnessRampFastDecrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampFastDecrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampFastDecrease = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampFastIncrease() {
        return this.screenBrightnessRampFastIncrease;
    }

    boolean hasScreenBrightnessRampFastIncrease() {
        if (this.screenBrightnessRampFastIncrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampFastIncrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampFastIncrease = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampSlowDecrease() {
        return this.screenBrightnessRampSlowDecrease;
    }

    boolean hasScreenBrightnessRampSlowDecrease() {
        if (this.screenBrightnessRampSlowDecrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampSlowDecrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampSlowDecrease = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampSlowIncrease() {
        return this.screenBrightnessRampSlowIncrease;
    }

    boolean hasScreenBrightnessRampSlowIncrease() {
        if (this.screenBrightnessRampSlowIncrease == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampSlowIncrease(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampSlowIncrease = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampSlowDecreaseIdle() {
        return this.screenBrightnessRampSlowDecreaseIdle;
    }

    boolean hasScreenBrightnessRampSlowDecreaseIdle() {
        if (this.screenBrightnessRampSlowDecreaseIdle == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampSlowDecreaseIdle(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampSlowDecreaseIdle = bigDecimal;
    }

    public final java.math.BigDecimal getScreenBrightnessRampSlowIncreaseIdle() {
        return this.screenBrightnessRampSlowIncreaseIdle;
    }

    boolean hasScreenBrightnessRampSlowIncreaseIdle() {
        if (this.screenBrightnessRampSlowIncreaseIdle == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampSlowIncreaseIdle(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessRampSlowIncreaseIdle = bigDecimal;
    }

    public final java.math.BigInteger getScreenBrightnessRampIncreaseMaxMillis() {
        return this.screenBrightnessRampIncreaseMaxMillis;
    }

    boolean hasScreenBrightnessRampIncreaseMaxMillis() {
        if (this.screenBrightnessRampIncreaseMaxMillis == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampIncreaseMaxMillis(java.math.BigInteger bigInteger) {
        this.screenBrightnessRampIncreaseMaxMillis = bigInteger;
    }

    public final java.math.BigInteger getScreenBrightnessRampDecreaseMaxMillis() {
        return this.screenBrightnessRampDecreaseMaxMillis;
    }

    boolean hasScreenBrightnessRampDecreaseMaxMillis() {
        if (this.screenBrightnessRampDecreaseMaxMillis == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampDecreaseMaxMillis(java.math.BigInteger bigInteger) {
        this.screenBrightnessRampDecreaseMaxMillis = bigInteger;
    }

    public final java.math.BigInteger getScreenBrightnessRampIncreaseMaxIdleMillis() {
        return this.screenBrightnessRampIncreaseMaxIdleMillis;
    }

    boolean hasScreenBrightnessRampIncreaseMaxIdleMillis() {
        if (this.screenBrightnessRampIncreaseMaxIdleMillis == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampIncreaseMaxIdleMillis(java.math.BigInteger bigInteger) {
        this.screenBrightnessRampIncreaseMaxIdleMillis = bigInteger;
    }

    public final java.math.BigInteger getScreenBrightnessRampDecreaseMaxIdleMillis() {
        return this.screenBrightnessRampDecreaseMaxIdleMillis;
    }

    boolean hasScreenBrightnessRampDecreaseMaxIdleMillis() {
        if (this.screenBrightnessRampDecreaseMaxIdleMillis == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessRampDecreaseMaxIdleMillis(java.math.BigInteger bigInteger) {
        this.screenBrightnessRampDecreaseMaxIdleMillis = bigInteger;
    }

    public final com.android.server.display.config.SensorDetails getLightSensor() {
        return this.lightSensor;
    }

    boolean hasLightSensor() {
        if (this.lightSensor == null) {
            return false;
        }
        return true;
    }

    public final void setLightSensor(com.android.server.display.config.SensorDetails sensorDetails) {
        this.lightSensor = sensorDetails;
    }

    public final com.android.server.display.config.SensorDetails getScreenOffBrightnessSensor() {
        return this.screenOffBrightnessSensor;
    }

    boolean hasScreenOffBrightnessSensor() {
        if (this.screenOffBrightnessSensor == null) {
            return false;
        }
        return true;
    }

    public final void setScreenOffBrightnessSensor(com.android.server.display.config.SensorDetails sensorDetails) {
        this.screenOffBrightnessSensor = sensorDetails;
    }

    public final com.android.server.display.config.SensorDetails getProxSensor() {
        return this.proxSensor;
    }

    boolean hasProxSensor() {
        if (this.proxSensor == null) {
            return false;
        }
        return true;
    }

    public final void setProxSensor(com.android.server.display.config.SensorDetails sensorDetails) {
        this.proxSensor = sensorDetails;
    }

    public final com.android.server.display.config.SensorDetails getTempSensor() {
        return this.tempSensor;
    }

    boolean hasTempSensor() {
        if (this.tempSensor == null) {
            return false;
        }
        return true;
    }

    public final void setTempSensor(com.android.server.display.config.SensorDetails sensorDetails) {
        this.tempSensor = sensorDetails;
    }

    public final java.math.BigInteger getAmbientLightHorizonLong() {
        return this.ambientLightHorizonLong;
    }

    boolean hasAmbientLightHorizonLong() {
        if (this.ambientLightHorizonLong == null) {
            return false;
        }
        return true;
    }

    public final void setAmbientLightHorizonLong(java.math.BigInteger bigInteger) {
        this.ambientLightHorizonLong = bigInteger;
    }

    public final java.math.BigInteger getAmbientLightHorizonShort() {
        return this.ambientLightHorizonShort;
    }

    boolean hasAmbientLightHorizonShort() {
        if (this.ambientLightHorizonShort == null) {
            return false;
        }
        return true;
    }

    public final void setAmbientLightHorizonShort(java.math.BigInteger bigInteger) {
        this.ambientLightHorizonShort = bigInteger;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.Thresholds getDisplayBrightnessChangeThresholds() {
        return this.displayBrightnessChangeThresholds;
    }

    boolean hasDisplayBrightnessChangeThresholds() {
        if (this.displayBrightnessChangeThresholds == null) {
            return false;
        }
        return true;
    }

    public final void setDisplayBrightnessChangeThresholds(@android.annotation.NonNull com.android.server.display.config.Thresholds thresholds) {
        this.displayBrightnessChangeThresholds = thresholds;
    }

    @android.annotation.NonNull
    public final com.android.server.display.config.Thresholds getAmbientBrightnessChangeThresholds() {
        return this.ambientBrightnessChangeThresholds;
    }

    boolean hasAmbientBrightnessChangeThresholds() {
        if (this.ambientBrightnessChangeThresholds == null) {
            return false;
        }
        return true;
    }

    public final void setAmbientBrightnessChangeThresholds(@android.annotation.NonNull com.android.server.display.config.Thresholds thresholds) {
        this.ambientBrightnessChangeThresholds = thresholds;
    }

    public final com.android.server.display.config.Thresholds getDisplayBrightnessChangeThresholdsIdle() {
        return this.displayBrightnessChangeThresholdsIdle;
    }

    boolean hasDisplayBrightnessChangeThresholdsIdle() {
        if (this.displayBrightnessChangeThresholdsIdle == null) {
            return false;
        }
        return true;
    }

    public final void setDisplayBrightnessChangeThresholdsIdle(com.android.server.display.config.Thresholds thresholds) {
        this.displayBrightnessChangeThresholdsIdle = thresholds;
    }

    public final com.android.server.display.config.Thresholds getAmbientBrightnessChangeThresholdsIdle() {
        return this.ambientBrightnessChangeThresholdsIdle;
    }

    boolean hasAmbientBrightnessChangeThresholdsIdle() {
        if (this.ambientBrightnessChangeThresholdsIdle == null) {
            return false;
        }
        return true;
    }

    public final void setAmbientBrightnessChangeThresholdsIdle(com.android.server.display.config.Thresholds thresholds) {
        this.ambientBrightnessChangeThresholdsIdle = thresholds;
    }

    public final com.android.server.display.config.IntegerArray getScreenOffBrightnessSensorValueToLux() {
        return this.screenOffBrightnessSensorValueToLux;
    }

    boolean hasScreenOffBrightnessSensorValueToLux() {
        if (this.screenOffBrightnessSensorValueToLux == null) {
            return false;
        }
        return true;
    }

    public final void setScreenOffBrightnessSensorValueToLux(com.android.server.display.config.IntegerArray integerArray) {
        this.screenOffBrightnessSensorValueToLux = integerArray;
    }

    public final com.android.server.display.config.UsiVersion getUsiVersion() {
        return this.usiVersion;
    }

    boolean hasUsiVersion() {
        if (this.usiVersion == null) {
            return false;
        }
        return true;
    }

    public final void setUsiVersion(com.android.server.display.config.UsiVersion usiVersion) {
        this.usiVersion = usiVersion;
    }

    public final java.math.BigDecimal getScreenBrightnessCapForWearBedtimeMode() {
        return this.screenBrightnessCapForWearBedtimeMode;
    }

    boolean hasScreenBrightnessCapForWearBedtimeMode() {
        if (this.screenBrightnessCapForWearBedtimeMode == null) {
            return false;
        }
        return true;
    }

    public final void setScreenBrightnessCapForWearBedtimeMode(java.math.BigDecimal bigDecimal) {
        this.screenBrightnessCapForWearBedtimeMode = bigDecimal;
    }

    static com.android.server.display.config.DisplayConfiguration read(org.xmlpull.v1.XmlPullParser xmlPullParser) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException, javax.xml.datatype.DatatypeConfigurationException {
        int next;
        com.android.server.display.config.DisplayConfiguration displayConfiguration = new com.android.server.display.config.DisplayConfiguration();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                java.lang.String name = xmlPullParser.getName();
                if (name.equals("name")) {
                    displayConfiguration.setName(com.android.server.display.config.XmlParser.readText(xmlPullParser));
                } else if (name.equals("densityMapping")) {
                    displayConfiguration.setDensityMapping(com.android.server.display.config.DensityMapping.read(xmlPullParser));
                } else if (name.equals("screenBrightnessMap")) {
                    displayConfiguration.setScreenBrightnessMap(com.android.server.display.config.NitsMap.read(xmlPullParser));
                } else if (name.equals("screenBrightnessDefault")) {
                    displayConfiguration.setScreenBrightnessDefault(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("thermalThrottling")) {
                    displayConfiguration.setThermalThrottling(com.android.server.display.config.ThermalThrottling.read(xmlPullParser));
                } else if (name.equals("powerThrottlingConfig")) {
                    displayConfiguration.setPowerThrottlingConfig(com.android.server.display.config.PowerThrottlingConfig.read(xmlPullParser));
                } else if (name.equals("luxThrottling")) {
                    displayConfiguration.setLuxThrottling(com.android.server.display.config.LuxThrottling.read(xmlPullParser));
                } else if (name.equals("highBrightnessMode")) {
                    displayConfiguration.setHighBrightnessMode(com.android.server.display.config.HighBrightnessMode.read(xmlPullParser));
                } else if (name.equals("hdrBrightnessConfig")) {
                    displayConfiguration.setHdrBrightnessConfig(com.android.server.display.config.HdrBrightnessConfig.read(xmlPullParser));
                } else if (name.equals("quirks")) {
                    displayConfiguration.setQuirks(com.android.server.display.config.DisplayQuirks.read(xmlPullParser));
                } else if (name.equals("autoBrightness")) {
                    displayConfiguration.setAutoBrightness(com.android.server.display.config.AutoBrightness.read(xmlPullParser));
                } else if (name.equals("refreshRate")) {
                    displayConfiguration.setRefreshRate(com.android.server.display.config.RefreshRateConfigs.read(xmlPullParser));
                } else if (name.equals("screenBrightnessRampFastDecrease")) {
                    displayConfiguration.setScreenBrightnessRampFastDecrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampFastIncrease")) {
                    displayConfiguration.setScreenBrightnessRampFastIncrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampSlowDecrease")) {
                    displayConfiguration.setScreenBrightnessRampSlowDecrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampSlowIncrease")) {
                    displayConfiguration.setScreenBrightnessRampSlowIncrease(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampSlowDecreaseIdle")) {
                    displayConfiguration.setScreenBrightnessRampSlowDecreaseIdle(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampSlowIncreaseIdle")) {
                    displayConfiguration.setScreenBrightnessRampSlowIncreaseIdle(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampIncreaseMaxMillis")) {
                    displayConfiguration.setScreenBrightnessRampIncreaseMaxMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampDecreaseMaxMillis")) {
                    displayConfiguration.setScreenBrightnessRampDecreaseMaxMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampIncreaseMaxIdleMillis")) {
                    displayConfiguration.setScreenBrightnessRampIncreaseMaxIdleMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("screenBrightnessRampDecreaseMaxIdleMillis")) {
                    displayConfiguration.setScreenBrightnessRampDecreaseMaxIdleMillis(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("lightSensor")) {
                    displayConfiguration.setLightSensor(com.android.server.display.config.SensorDetails.read(xmlPullParser));
                } else if (name.equals("screenOffBrightnessSensor")) {
                    displayConfiguration.setScreenOffBrightnessSensor(com.android.server.display.config.SensorDetails.read(xmlPullParser));
                } else if (name.equals("proxSensor")) {
                    displayConfiguration.setProxSensor(com.android.server.display.config.SensorDetails.read(xmlPullParser));
                } else if (name.equals("tempSensor")) {
                    displayConfiguration.setTempSensor(com.android.server.display.config.SensorDetails.read(xmlPullParser));
                } else if (name.equals("ambientLightHorizonLong")) {
                    displayConfiguration.setAmbientLightHorizonLong(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("ambientLightHorizonShort")) {
                    displayConfiguration.setAmbientLightHorizonShort(new java.math.BigInteger(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else if (name.equals("displayBrightnessChangeThresholds")) {
                    displayConfiguration.setDisplayBrightnessChangeThresholds(com.android.server.display.config.Thresholds.read(xmlPullParser));
                } else if (name.equals("ambientBrightnessChangeThresholds")) {
                    displayConfiguration.setAmbientBrightnessChangeThresholds(com.android.server.display.config.Thresholds.read(xmlPullParser));
                } else if (name.equals("displayBrightnessChangeThresholdsIdle")) {
                    displayConfiguration.setDisplayBrightnessChangeThresholdsIdle(com.android.server.display.config.Thresholds.read(xmlPullParser));
                } else if (name.equals("ambientBrightnessChangeThresholdsIdle")) {
                    displayConfiguration.setAmbientBrightnessChangeThresholdsIdle(com.android.server.display.config.Thresholds.read(xmlPullParser));
                } else if (name.equals("screenOffBrightnessSensorValueToLux")) {
                    displayConfiguration.setScreenOffBrightnessSensorValueToLux(com.android.server.display.config.IntegerArray.read(xmlPullParser));
                } else if (name.equals("usiVersion")) {
                    displayConfiguration.setUsiVersion(com.android.server.display.config.UsiVersion.read(xmlPullParser));
                } else if (name.equals("screenBrightnessCapForWearBedtimeMode")) {
                    displayConfiguration.setScreenBrightnessCapForWearBedtimeMode(new java.math.BigDecimal(com.android.server.display.config.XmlParser.readText(xmlPullParser)));
                } else {
                    com.android.server.display.config.XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next != 3) {
            throw new javax.xml.datatype.DatatypeConfigurationException("DisplayConfiguration is not closed");
        }
        return displayConfiguration;
    }
}
