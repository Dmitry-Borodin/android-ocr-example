package com.two_two.android_ocr_example.domain.tess;

/**
 * @author Dmitry Borodin on 2017-01-01.
 */

public class TessHelper {

    /** Languages for which Cube data is available. */
    public static final String[] CUBE_SUPPORTED_LANGUAGES = {
            "ara", // Arabic
            "eng", // English
            "hin" // Hindi
    };

    /** Languages that require Cube, and cannot run using Tesseract. */
    public static final String[] CUBE_REQUIRED_LANGUAGES = {
            "ara" // Arabic
    };

    /** Resource to use for data file downloads. */
    public static final String DOWNLOAD_BASE = "http://tesseract-ocr.googlecode.com/files/";

    /** Download filename for orientation and script detection (OSD) data. */
    public static final String OSD_FILENAME = "tesseract-ocr-3.01.osd.tar";

    /** Destination filename for orientation and script detection (OSD) data. */
    public static final String OSD_FILENAME_BASE = "osd.traineddata";
}
