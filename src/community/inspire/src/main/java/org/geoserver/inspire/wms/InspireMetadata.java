/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.inspire.wms;

public enum InspireMetadata {
    LANGUAGE("inspire.language"), SERVICE_METADATA_URL("inspire.metadataURL"), SERVICE_METADATA_TYPE(
            "inspire.metadataURLType");

    public String key;

    private InspireMetadata(String key) {
        this.key = key;
    }
}
