/* 
 **********************************************************************
 *
 * $Id: DefinitionCriteriaType.java,v 1.6 2011/01/04 10:39:03 vizigoth Exp $
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
 * $Log: DefinitionCriteriaType.java,v $
 * Revision 1.6  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:56:04  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/01/14 16:07:35  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2008/01/09 12:24:53  vizigoth
 * Edited javadoc comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:15:17  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Defines the type of the associated criteria for matching a 
 * {@linkplain tv.amwa.maj.model.DefinitionObject definition}.</p>
 * 
 * <p>Original C name: <code>aafDefinitionCritType_e</code></p>
 * 
 * @see DefinitionCriteria
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public enum DefinitionCriteriaType 
	implements MediaEnumerationValue {

	/** 
	 * <p>No definition criteria provided.</p> 
	 * 
	 * @see DefinitionCriteriaNoSearch
	 */
	NoSearch (0), 
	/** 
	 * <p>Search by kind of the definition, such as data defintion, property definition etc..</p> 
	 *
	 * @see DefinitionCriteriaByKind
	 */
	ByKind (1), 
	/** 
	 * <p>Search by name of the definition.</p>
	 * 
	 * @see DefinitionCriteriaByName
	 */
	ByName (2), 
	/** 
	 * <p>Search by class id ({@linkplain tv.amwa.maj.record.AUID AUID}) of the definition.</p>
	 * 
	 * @see DefinitionCriteriaByClass
	 */
	ByClass (3), 
    ;

    private final int value;

    DefinitionCriteriaType (int value) { this.value = value; }

    @Int64 
    public long value() { return (long) value; }

	public String symbol() {

		return name();
	}

}
