package tv.amwa.maj.extensions.example;

import tv.amwa.maj.extensions.example.impl.ContributorImpl;
import tv.amwa.maj.extensions.example.impl.PersonImpl;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.meta.TypeDefinitionEnumeration;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.impl.TypeDefinitionEnumerationImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionStrongObjectReferenceImpl;

/**
 * <p>Extension type definitions defined for this package.</p>
 *
 * @see tv.amwa.maj.meta.TypeDefinition
 * @see tv.amwa.maj.industry.Warehouse
 * @see tv.amwa.maj.industry.TypeDefinitions
 */
public interface TypeDefinitions {
    
    /**
     * <p>Different genres of resource.</p>
     *
     * @see DCMIType
     */
    public final static TypeDefinitionEnumeration DCMIType = new TypeDefinitionEnumerationImpl(
            Forge.makeAUID(0x0f201101, (short) 0x0100, (short) 0x0000,
                    new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
            "DCMIType",
            DCMIType.class,
            tv.amwa.maj.industry.TypeDefinitions.UInt8);

    /**
     * <p>Strong reference to the value of a person used as a contact.</p>
     */
    public final static TypeDefinitionStrongObjectReference PersonStrongReference = new TypeDefinitionStrongObjectReferenceImpl(
            Forge.makeAUID(0x0f201101, (short) 0x0300, (short) 0x0000,
                    new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
            "PersonStrongReference",
            Warehouse.lookForClass(PersonImpl.class) );

    /**
     * <p>Strong reference to the value of a contributor.</p>
     */
    public final static TypeDefinitionStrongObjectReference ContributorStrongReference = new TypeDefinitionStrongObjectReferenceImpl(
            Forge.makeAUID(0x0f201101, (short) 0x0200, (short) 0x0000,
                    new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x04, 0x01, 0x01 }),
            "ContributorStrongReference",
            Warehouse.lookForClass(ContributorImpl.class) );

}
