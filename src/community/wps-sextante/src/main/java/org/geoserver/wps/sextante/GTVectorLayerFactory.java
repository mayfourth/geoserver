/* Copyright (c) 2012 TOPP - www.openplans.org. All rights reserved.
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wps.sextante;

import es.unex.sextante.dataObjects.IVectorLayer;

public interface GTVectorLayerFactory {

	public abstract IVectorLayer create(String sName, int iShapeType, Class<?>[] sFields,
			String[] fields, String filename, Object crs);

}