/* 
 **********************************************************************
 *
 * $Id: DescriptiveFramework.java,v 1.3 2011/10/05 17:14:27 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: DescriptiveFramework.java,v $
 * Revision 1.3  2011/10/05 17:14:27  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:35  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:40  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:47  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies a framework for descriptive metadata. Sub-interfaces of this interface are taken
 * from vocabularies of descriptive metadata, such as DMS-1, known as <em>descriptive
 * metadata schemes</em>.</p>
 * 
 * <p>Note that in the current implementation of the MAJ API, no descriptive schemes
 * have been implemented.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see Preface#getDescriptiveSchemes()
 * @see DescriptiveObject
 * @see DescriptiveMarker#getDescriptiveFrameworkObject()
 * @see tv.amwa.maj.industry.TypeDefinitions#DescriptiveFrameworkStrongReference
 */
public abstract interface DescriptiveFramework 
	extends InterchangeObject {

	/**
	 * <p>Return the identifier of the {@linkplain DescriptiveMarker descriptive marker} that strongly references 
	 * this descriptive framework instance. This is an optional property.</p>
	 * 
	 * @return Identifier of the descriptive marker that strongly references this descriptive framework instance.
	 * 
	 * @throws PropertyNotPresentException The optional linked descriptive framework plugin property
	 * is not present for this descriptive framework.
	 * 
	 * @see DescriptiveMarker#getDescriptiveMetadataPluginID()
	 */
	public AUID getLinkedDescriptiveFrameworkPluginID()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the identifier of the {@linkplain DescriptiveMarker descriptive marker} that strongly references 
	 * this descriptive framework instance. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param linkedDescriptiveFrameworkPluginID Identifier of the Descriptive marker that strongly references this 
	 * descriptive framework instance.
	 */
	public void setLinkedDescriptiveFrameworkPluginID(
			AUID linkedDescriptiveFrameworkPluginID);
	
	/**
	 * <p>Create a cloned copy of this descriptive framework.</p>
	 *
	 * @return Cloned copy of this descriptive framework.
	 */
	public DescriptiveFramework clone();
	
}
