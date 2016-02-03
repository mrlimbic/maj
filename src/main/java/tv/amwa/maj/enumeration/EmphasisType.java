/* 
 **********************************************************************
 *
 * $Id: EmphasisType.java,v 1.7 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: EmphasisType.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:15  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/** 
 * <p>Specifies the encoded audio signal pre-emphasis for sound data.</p>
 * 
 * <p>The values associated with elements of this enumeration are specified in section&nbsp;4 of
 * <a href="http://www.ebu.ch/CMSimages/en/tec_doc_t3250-2004_tcm6-12767.pdf">EBU tech. 3250</a>.</p>
 * 
 * @see tv.amwa.maj.model.AES3PCMDescriptor#getEmphasis()
 * @see tv.amwa.maj.industry.TypeDefinitions#EmphasisType
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public enum EmphasisType 
	implements MediaEnumerationValue {
	
	/**
	 * <p>Emphasis not indicated. Receiver defaults to no emphasis with manual over-ride enabled.</p>
	 */
	Unknown(0),
	/**
	 * <p>Reserved.</p>
	 */
	Reserved0(1),
	/**
	 * <p>Reserved.</p>
	 */
	Reserved1(2),
	/**
	 * <p>Reserved.</p>
	 */
	Reserved2(3),
	/**
	 * <p>No emphasis. Receiver manual over-ride disabled.</p>
	 */
	None(4),
	/**
	 * <p>Reserved.</p>
	 */
	Reserved3(5),
	/**
	 * <p>50/15 pico s emphasis. Receiver manual over-ride disabled.</p>
	 */
	Emphasis15and50(6),
	/**
	 * <p>CCITT J.17 emphasis (with 6.5&nbsp;dB insertion loss at 500&nbsp;Hz). Receiver manual 
	 * over-ride disabled.</p>
	 */
	ITU(7);

	private int value;
	
	private EmphasisType(int value) {
		this.value = value;
	}
	
	public long value() {

		return (long) value;
	}

	public String symbol() {
		
		if (this == Emphasis15and50)
			return "Emphasis_15and50";
		else
			return "Emphasis_" + name();
	}
}
