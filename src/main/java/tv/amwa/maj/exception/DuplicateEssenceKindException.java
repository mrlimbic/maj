/* 
 **********************************************************************
 *
 * $Id: DuplicateEssenceKindException.java,v 1.3 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: DuplicateEssenceKindException.java,v $
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:58  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:34  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.exception;

// References: CodecDefinition

/** 
 * <p>Thrown when an attempt is made to add an essence kind to that supported by a 
 * {@linkplain tv.amwa.maj.model.CodecDefinition codec definition} that is already
 * listed as supported by that codec.</p>
 * 
 * <p>No equivalent C result code.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class DuplicateEssenceKindException 
	extends DuplicateException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -1291509081658762914L;

	/**
	 * <p>Create a new duplicate essence kind exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public DuplicateEssenceKindException(
			String msg) {
	
		super(msg);
	}

	/**
	 * <p>Create a new duplicate essence kind exception with no message.</p>
	 */
	public DuplicateEssenceKindException() {
		super();
	}
}
