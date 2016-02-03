/* 
 **********************************************************************
 *
 * $Id: MediaPropertyContains.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
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
 * $Log: MediaPropertyContains.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinitionSet;

/**
 * <p>Labels a method as one used to test if an element is contained in an {@linkplain TypeDefinitionSet AAF-type set} 
 * for the {@linkplain PropertyDefinition property} of the given name. The labelled method should
 * have a signature similar to:</p>
 * 
 * <p><code>&nbsp;&nbsp;&nbsp;&nbsp;public&nbsp;boolean&nbsp;contains</code>&lt;<em>propertyName</em>&gt;<code>(</code><br>
 * <code>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</code>&lt;<em>elementType</em>&gt;<code>&nbsp;value)</code></p>
 * 
 * <p>Note that sometimes these methods are called <em>is ... present</em> rather than <em>contains</em>,
 * but the effect is the same.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see TypeDefinitionSet
 * @see tv.amwa.maj.model.Preface#isDescriptiveSchemePresent(tv.amwa.maj.record.AUID)
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MediaPropertyContains {

	/** Property name of the property the method tests for containment. */
	String value();
}
