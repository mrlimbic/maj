/* 
 **********************************************************************
 *
 * $Id: CompArray.java,v 1.5 2011/01/04 10:42:32 vizigoth Exp $
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
 * $Log: CompArray.java,v $
 * Revision 1.5  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2007/12/12 12:52:27  vizigoth
 * Added documentation tag to annotations.
 *
 * Revision 1.3  2007/12/03 12:39:24  vizigoth
 * Minor comment updates.
 *
 * Revision 1.2  2007/11/23 15:04:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:08:07  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.misctype;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** <p>Labels an array that specifies the order in which the RGBA components are 
 * stored as an array of characters.  Each element in 
 * the array represents a different color component. The array can contain the following 
 * codes, as specified by the enumeration {@link tv.amwa.maj.enumeration.RGBAComponentKind 
 * RGBAComponentKind}:</p>
 * 
 * <ul>
 *  <li>'A' - Alpha component</li>
 *  <li>'B' - Blue component</li>
 *  <li>'F' - Fill component</li>
 *  <li>'G' - Green component</li>
 *  <li>'P' - Palette code</li>
 *  <li>'R' - Red component</li>
 *  <li>'0' - no component</li>
 * </ul>
 * 
 * <p>Each code, except '0', can appear no more than one time in the array. The array
 * is terminated by a 0-valued byte and has a maximum of 8 elements (including the terminating 
 * character). Note that a character with the Unicode '0' indicates no component and a byte with 
 * a Unicode NULL terminates the string.</p>
 * 
 * <p>Note that in Java, this is represented by creating an array of 
 * {@link tv.amwa.maj.record.RGBAComponent RGBAComponent} objects rather than characters.</p>
 * 
 * <p>
 * Original C name: <code>aafCompArray_t</code><br>
 * C base type: <code>aafInt8 *</code><br>
 * Java base type: {@link tv.amwa.maj.record.RGBAComponent tv.amwa.maj.record.RGBAComponent[]}
 * </p>
 *
 * @see RGBALayout
 * @see tv.amwa.maj.record.RGBAComponent
 * @see tv.amwa.maj.model.RGBADescriptor
 * @see tv.amwa.maj.enumeration.RGBAComponentKind
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface CompArray { }

