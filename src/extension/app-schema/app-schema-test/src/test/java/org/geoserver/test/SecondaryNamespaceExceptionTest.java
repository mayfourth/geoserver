/*
 * Copyright (c) 2001 - 2010 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.test;

import static org.junit.Assert.*;

import org.junit.Test;

import org.w3c.dom.Document;

/**
 * Test exception thrown during encoding of secondary (transitively imported) namespace without the
 * secondary namespace declared
 * 
 * @author Victor Tey, CSIRO Earth Science and Resource Engineering
 */
public class SecondaryNamespaceExceptionTest extends AbstractAppSchemaTestSupport {

    @Override
    protected SecondaryNamespaceExceptionMockData createTestData() {
        return new SecondaryNamespaceExceptionMockData();
    }

    /**
     * Test encoding of sa namespace.
     */
    @Test
    public void testNamespaces() {
        Document doc = getAsDOM("wfs?request=GetFeature&typeName=ex:ShapeContent");
        LOGGER.info("Secondary Namespace Response:\n" + prettyString(doc));
        assertEquals("ows:ExceptionReport", doc.getDocumentElement().getNodeName());
    }

}
