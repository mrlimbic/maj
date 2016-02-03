/* 
 **********************************************************************
 *
 * $Id: TraversalNotPossibleException.java,v 1.4 2011/01/04 10:41:20 vizigoth Exp $
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
 * $Log: TraversalNotPossibleException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:57  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// TODO References: Component, EssenceGroup, Package, Track, Selector, SourcePackage, TimelineTrack
// but really just to do with SearchSounce, so consider removing?

/** 
 * <p>Thrown when a search operation cannot proceed.</p>
 * 
 * <p>An equivalent C result code could not be found.</p>
 * 
 * @see tv.amwa.maj.model.SearchSource
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class TraversalNotPossibleException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 2518206830971688494L;

	/**
	 * <p>Create a new traversal not possible exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public TraversalNotPossibleException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new traversal not possible exception with no message.</p>
	 */
	public TraversalNotPossibleException() {
		super();
	}
}
