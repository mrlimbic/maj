/*
 * Copyright 2016 Advanced Media Workflow Assocation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Log: OperationChoice.java,v $
 * Revision 1.4  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:08  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** TODO this comment or remove? 
 * <p>Specifies the kind of operation to search for.</p>

    <p>Autogenerated from AAFTypes.idl, original C name aafOperationChoice_e.<br>
    Defined type name for C enumeration is _aafOperationChoice_e.</p>

    @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>

*/

public enum OperationChoice 
	implements MediaEnumerationValue {

    FindNull (0), 
    FindIncoming (1), 
    FindOutgoing (2), 
    FindRender (3), 
    FindOperationSrc1 (4), 
    FindOperationSrc2 (5), 
    FindOperationSrc3 (6), 
    FindOperationSrc4 (7), 
    FindOperationSrc5 (8), 
    FindOperationSrc6 (9), 
    FindOperationSrc7 (10), 
    FindOperationSrc8 (11), 
     ;

    private final int value;

    OperationChoice (int value) { this.value = value; }

    @Int64 public long value() { return (long) value; }
    
    public String symbol() { return name(); }

}
