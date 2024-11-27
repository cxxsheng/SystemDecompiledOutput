package android.provider;

/* loaded from: classes3.dex */
public final class MetadataReader {
    private static final java.lang.String JPEG_MIME_TYPE = "image/jpeg";
    private static final java.lang.String JPG_MIME_TYPE = "image/jpg";
    private static final int TYPE_DOUBLE = 1;
    private static final int TYPE_INT = 0;
    private static final int TYPE_STRING = 2;
    private static final java.lang.String[] DEFAULT_EXIF_TAGS = {"FNumber", android.media.ExifInterface.TAG_COPYRIGHT, android.media.ExifInterface.TAG_DATETIME, android.media.ExifInterface.TAG_EXPOSURE_TIME, android.media.ExifInterface.TAG_FOCAL_LENGTH, "FNumber", android.media.ExifInterface.TAG_GPS_LATITUDE, android.media.ExifInterface.TAG_GPS_LATITUDE_REF, android.media.ExifInterface.TAG_GPS_LONGITUDE, android.media.ExifInterface.TAG_GPS_LONGITUDE_REF, android.media.ExifInterface.TAG_IMAGE_LENGTH, android.media.ExifInterface.TAG_IMAGE_WIDTH, "ISOSpeedRatings", android.media.ExifInterface.TAG_MAKE, android.media.ExifInterface.TAG_MODEL, android.media.ExifInterface.TAG_ORIENTATION, android.media.ExifInterface.TAG_SHUTTER_SPEED_VALUE};
    private static final java.util.Map<java.lang.String, java.lang.Integer> TYPE_MAPPING = new java.util.HashMap();

    private MetadataReader() {
    }

    static {
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ARTIST, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_BITS_PER_SAMPLE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_COMPRESSION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_COPYRIGHT, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DATETIME, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_IMAGE_DESCRIPTION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_IMAGE_LENGTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_IMAGE_WIDTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_MAKE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_MODEL, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ORIENTATION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_PHOTOMETRIC_INTERPRETATION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_PLANAR_CONFIGURATION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_PRIMARY_CHROMATICITIES, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_REFERENCE_BLACK_WHITE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RESOLUTION_UNIT, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ROWS_PER_STRIP, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SAMPLES_PER_PIXEL, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SOFTWARE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_STRIP_BYTE_COUNTS, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_STRIP_OFFSETS, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_TRANSFER_FUNCTION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_WHITE_POINT, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_X_RESOLUTION, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_Y_CB_CR_COEFFICIENTS, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_Y_CB_CR_POSITIONING, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_Y_CB_CR_SUB_SAMPLING, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_Y_RESOLUTION, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_APERTURE_VALUE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_BRIGHTNESS_VALUE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_CFA_PATTERN, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_COLOR_SPACE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_COMPONENTS_CONFIGURATION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_COMPRESSED_BITS_PER_PIXEL, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_CONTRAST, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_CUSTOM_RENDERED, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DATETIME_DIGITIZED, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DATETIME_ORIGINAL, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DEVICE_SETTING_DESCRIPTION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DIGITAL_ZOOM_RATIO, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXIF_VERSION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXPOSURE_BIAS_VALUE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXPOSURE_INDEX, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXPOSURE_MODE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXPOSURE_PROGRAM, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_EXPOSURE_TIME, 1);
        TYPE_MAPPING.put("FNumber", 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FILE_SOURCE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FLASH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FLASH_ENERGY, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FLASHPIX_VERSION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FOCAL_LENGTH, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FOCAL_LENGTH_IN_35MM_FILM, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FOCAL_PLANE_RESOLUTION_UNIT, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FOCAL_PLANE_X_RESOLUTION, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_FOCAL_PLANE_Y_RESOLUTION, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GAIN_CONTROL, 0);
        TYPE_MAPPING.put("ISOSpeedRatings", 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_IMAGE_UNIQUE_ID, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_LIGHT_SOURCE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_MAKER_NOTE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_MAX_APERTURE_VALUE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_METERING_MODE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_NEW_SUBFILE_TYPE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_OECF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_PIXEL_X_DIMENSION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_PIXEL_Y_DIMENSION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RELATED_SOUND_FILE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SATURATION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SCENE_CAPTURE_TYPE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SCENE_TYPE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SENSING_METHOD, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SHARPNESS, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SHUTTER_SPEED_VALUE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SPATIAL_FREQUENCY_RESPONSE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SPECTRAL_SENSITIVITY, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBFILE_TYPE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBSEC_TIME, 2);
        TYPE_MAPPING.put("SubSecTimeDigitized", 2);
        TYPE_MAPPING.put("SubSecTimeOriginal", 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBJECT_AREA, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBJECT_DISTANCE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBJECT_DISTANCE_RANGE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_SUBJECT_LOCATION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_USER_COMMENT, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_WHITE_BALANCE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_ALTITUDE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_ALTITUDE_REF, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_AREA_INFORMATION, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DOP, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DATESTAMP, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_BEARING, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_BEARING_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_DISTANCE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_DISTANCE_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_LATITUDE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_LATITUDE_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_LONGITUDE, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DEST_LONGITUDE_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_DIFFERENTIAL, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_IMG_DIRECTION, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_IMG_DIRECTION_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_LATITUDE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_LATITUDE_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_LONGITUDE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_LONGITUDE_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_MAP_DATUM, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_MEASURE_MODE, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_PROCESSING_METHOD, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_SATELLITES, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_SPEED, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_SPEED_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_STATUS, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_TIMESTAMP, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_TRACK, 1);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_TRACK_REF, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_GPS_VERSION_ID, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_INTEROPERABILITY_INDEX, 2);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_THUMBNAIL_IMAGE_LENGTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_THUMBNAIL_IMAGE_WIDTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DNG_VERSION, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_DEFAULT_CROP_SIZE, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ORF_PREVIEW_IMAGE_START, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ORF_PREVIEW_IMAGE_LENGTH, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_ORF_ASPECT_FRAME, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RW2_SENSOR_BOTTOM_BORDER, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RW2_SENSOR_LEFT_BORDER, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RW2_SENSOR_RIGHT_BORDER, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RW2_SENSOR_TOP_BORDER, 0);
        TYPE_MAPPING.put(android.media.ExifInterface.TAG_RW2_ISO, 0);
    }

    public static boolean isSupportedMimeType(java.lang.String str) {
        return "image/jpg".equals(str) || "image/jpeg".equals(str);
    }

    public static void getMetadata(android.os.Bundle bundle, java.io.InputStream inputStream, java.lang.String str, java.lang.String[] strArr) throws java.io.IOException {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        if (isSupportedMimeType(str)) {
            android.os.Bundle exifData = getExifData(inputStream, strArr);
            if (exifData.size() > 0) {
                bundle.putBundle(android.provider.DocumentsContract.METADATA_EXIF, exifData);
                arrayList.add(android.provider.DocumentsContract.METADATA_EXIF);
            }
        }
        bundle.putStringArray(android.provider.DocumentsContract.METADATA_TYPES, (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]));
    }

    private static android.os.Bundle getExifData(java.io.InputStream inputStream, java.lang.String[] strArr) throws java.io.IOException {
        java.lang.String attribute;
        if (strArr == null) {
            strArr = DEFAULT_EXIF_TAGS;
        }
        android.media.ExifInterface exifInterface = new android.media.ExifInterface(inputStream);
        android.os.Bundle bundle = new android.os.Bundle();
        for (java.lang.String str : strArr) {
            if (TYPE_MAPPING.get(str).equals(0)) {
                int attributeInt = exifInterface.getAttributeInt(str, Integer.MIN_VALUE);
                if (attributeInt != Integer.MIN_VALUE) {
                    bundle.putInt(str, attributeInt);
                }
            } else if (TYPE_MAPPING.get(str).equals(1)) {
                double attributeDouble = exifInterface.getAttributeDouble(str, Double.MIN_VALUE);
                if (attributeDouble != Double.MIN_VALUE) {
                    bundle.putDouble(str, attributeDouble);
                }
            } else if (TYPE_MAPPING.get(str).equals(2) && (attribute = exifInterface.getAttribute(str)) != null) {
                bundle.putString(str, attribute);
            }
        }
        return bundle;
    }
}
