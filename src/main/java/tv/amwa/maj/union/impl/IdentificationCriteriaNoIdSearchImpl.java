/* 
 **********************************************************************
 *
 * $Id: IdentificationCriteriaNoIdSearchImpl.java,v 1.1 2011/01/04 10:40:23 vizigoth Exp $
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
 * $Log: IdentificationCriteriaNoIdSearchImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.union.IdentificationCriteriaType;


/**
 * <p>Implementation of a criteria for matching an {@linkplain tv.amwa.maj.model.Identification identification}
 * that matches nothing.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public class IdentificationCriteriaNoIdSearchImpl 
	extends IdentificationCriteriaImpl
	implements tv.amwa.maj.union.IdentificationCriteriaNoIdSearch,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 2149914413733298761L;

	/** 
	 * <p>Create a new criteria that matches no identifications.</p>
	 */
	public IdentificationCriteriaNoIdSearchImpl() {
		super(IdentificationCriteriaType.NoIdSearch);
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) return false;
		return o instanceof tv.amwa.maj.union.IdentificationCriteriaNoIdSearch;
	}
	
	/**
	 * <p>Pseudo-XML representation of this identification criteria. No corresponding XML schema or DTD is defined.
	 * For example:</p>
	 * 
	 * <pre>
	 * &lt;IdentificationCriteria /&gt;
	 * </pre>
	 * 
	 * @return String representation of an identification criteria that matches nothing.
	 */
	@Override
	public String toString() {
	
		return "<IdentificationCriteria />";
	}

	@Override
	public IdentificationCriteriaNoIdSearchImpl clone() 
		throws CloneNotSupportedException {

		return (IdentificationCriteriaNoIdSearchImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return 0;
	}
}
