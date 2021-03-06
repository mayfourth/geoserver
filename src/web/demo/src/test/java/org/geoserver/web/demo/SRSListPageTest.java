/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.web.demo;

import org.geoserver.web.GeoServerWicketTestSupport;
import org.junit.Test;

public class SRSListPageTest extends GeoServerWicketTestSupport {
    @Test
    public void testBasicPage() throws Exception {
        tester.startPage(SRSListPage.class);
        tester.assertLabel("srsListPanel:table:listContainer:items:1:itemProperties:0:component:link:label","2000");
        tester.clickLink("srsListPanel:table:listContainer:items:1:itemProperties:0:component:link");
        tester.assertRenderedPage(SRSDescriptionPage.class);
    }
}
