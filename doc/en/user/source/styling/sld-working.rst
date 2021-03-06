.. _sld_working:

Working with SLD
================

This section describes how to create, view and troubleshoot SLD styling in GeoServer.

Creating
--------

GeoServer comes with some basic styles defined in its catalog.
Any number of new styles can be added to the catalog.
Styles can also be specified **externally** to the server,
either to define a complete map, 
or to extend the server style catalog using **library mode**.

Catalog Styles
^^^^^^^^^^^^^^

Styles in the catalog can be viewed, edited and validated via the :ref:`webadmin_styles` menu of the :ref:`web_admin`. 
They may also be created and accessed via the :ref:`rest_extension` API.

Catalog styles consist of a :ref:`sld_reference_sld` document 
containing a single ``<NamedLayer>`` element, 
which contains a single ``<UserStyle>`` element to specify the styling.
The layer name is ignored, since the style may be applied to many different layers.

Every layer (featuretype) registered with GeoServer must have at least one catalog style associated with it,
which is the default style for rendering the layer.  
Any number of additional styles can be associated with a layer.
This allows layers to have appropriate styles advertised in the WMS ``GetCapabilities`` document.
A layer's styles can be changed 
using the :ref:`webadmin_layers` page of the :ref:`web_admin`.  

.. note:: When adding a layer and a style for it to GeoServer at the same time, the style should be added first, 
          so that the new layer can be associated with the style immediately. 

          
External Styles
^^^^^^^^^^^^^^^

Styling can be defined externally to the server in a number of ways:

* An internet-accessible SLD document can be provided via 
  the ``SLD=url`` parameter in a WMS :ref:`wms_getmap` GET request
* An SLD document can be provided directly in a 
  WMS :ref:`wms_getmap` GET request using the ``SLD_BODY=style`` parameter.
  The SLD XML must be URL-encoded.
* A :ref:`sld_reference_sld` element can be included in a WMS ``GetMap`` POST request XML document.
  
In all of these cases, if the WMS ``layers`` parameter is not supplied
then the map content is defined completely 
by the layers and styles present in the SLD.
If the ``layers`` parameter is present, then styling operates in **library mode**.

External styles can define new layers, 
by using the :ref:`sld_reference_inlinefeature` element
to provide feature data.
External stying may be generated dynamically, 
which provides a powerful way to control styling effects.

Library Mode
^^^^^^^^^^^^

In **library mode** externally-defined styles are treated as a *style library*,
which acts as an extension to the server style catalog.  
Library mode occurs when map layers and styles are specified using the ``layers`` and ``styles`` WMS parameters,
and additional styling is supplied externally 
using one of the methods described in the previous section.
The styles in the external style document 
take precedence over the catalog styles during rendering. 

Style lookup in library mode operates as follows:

* For each layer in the ``layers`` list, the applied style is either 
  a named style specified in the ``styles`` list (if present), or the default style
* For a named style, if the eternal style document has a ``<NamedLayer>...<UserStyle>``
  with matching layer name and style name, then it is used.
  Otherwise, the style name is searched for in the catalog.
  If it is not found there, an error occurs.
* For a default style, the external style document is
  searched to find a ``<NamedLayer>`` element with the layer name. 
  If it contains a ``<UserStyle>`` with the ``<IsDefault>`` element having the value ``1``
  then that style is used.
  Otherwise the default server style (which must exist) is used.

Generally it is simpler and more performant
to use styles from the server catalog.
However, library mode can be useful if it is required to style a map containing many layers and 
where only a few of them need to have their styling defined externally.

Viewing
-------

Once a style has been associated with a layer, the resulting rendering of the layer data
can be viewed by using the :ref:`layerpreview`. 
The most convenient output format to use is the built-in OpenLayers viewer.
Styles can be modified while the view is open, and take effect as
soon as the map view is moved or zoomed.
Alternate styles can be viewed by setting them as the value of the ``styles`` parameter.

To view the effect of compositing multiple styled layers, several approaches are available:

* Create a **layer group** for the desired layers using the :ref:`webadmin_layergroups` page, and preview it.  
  Non-default styles can be specified for layers if required.
* Submit a WMS :ref:`wms_getmap` GET request specifying the ``layers`` parameter, 
  and the ``styles`` parameter if non-default styles are required.
* Submit a WMS ``GetMap`` POST request containing a :ref:`sld_reference_sld` element
  specifying server layers, optional layers of inline data,
  and either named catalog styles or user-defined styling for each layer.


Troubleshooting
---------------

SLD is a type of programming language, not unlike creating a web page or building a script.  
As such, problems may arise that may require troubleshooting. 

Syntax Errors
^^^^^^^^^^^^^

To minimize syntax errors when creating the SLD, 
it is recommended to use a text editor that is designed to work with XML
(such as the :guilabel:`Style Editor` provided in the GeoServer UI).  
XML editors can make finding syntax errors easier by providing syntax highlighting and (sometimes) built-in error checking.

The GeoServer :guilabel:`Style Editor` allows validating a document against the SLD XML schema.
This is not mandatory, but is recommended to do before saving styles.

Semantic Errors
^^^^^^^^^^^^^^^

Semantic errors cannot be caught by SLD validation, 
but show up when a style is applied during map rendering.  
Most of the time this will result in a map displaying no features (a blank map), 
but some errors will prevent the map from rendering at all.

The easiest way to fix semantic errors in an SLD is to try to isolate the error.  
If the SLD is long with many rules and filters, try temporarily removing some of them to see if the errors go away.

In some cases the server will produce a WMS Exception document which may help to identify the error.
It is also worth checking the server log to see if any error messages have been recorded.

