/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web;

import static org.junit.Assert.*;

import java.util.Locale;

import org.apache.wicket.IConverterLocator;
import org.apache.wicket.util.convert.IConverter;
import org.junit.Test;

public class ConverterTest extends GeoServerWicketTestSupport {

    @Test
    public void testConvertEmtpyString() {
        // Wicket forms rely on converters returning null from an empty string conversion
        IConverterLocator locator = tester.getApplication().getConverterLocator();
        IConverter convert = locator.getConverter(Integer.class);
        assertNotNull(convert);
        assertNull(convert.convertToObject("", Locale.getDefault()));
    }
}
