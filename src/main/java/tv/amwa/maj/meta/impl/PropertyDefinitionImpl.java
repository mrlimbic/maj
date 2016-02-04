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
 * $Log: PropertyDefinitionImpl.java,v $
 * Revision 1.8  2011/10/05 17:14:28  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.7  2011/07/29 16:21:36  vizigoth
 * Improved error handling when the reason a property value cannot be determined has no underlying cause.
 *
 * Revision 1.6  2011/07/27 17:36:16  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/21 09:51:58  vizigoth
 * Completed writing tests for media engine.
 *
 * Revision 1.3  2011/01/19 21:37:53  vizigoth
 * Added property initialization code.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.3  2010/03/19 16:15:51  vizigoth
 * Added resiliance to null PIDs. Set to zero and treat as autogenerated.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2008/01/14 20:56:15  vizigoth
 * Change to type category enumeration element names.
 *
 * Revision 1.1  2007/11/13 22:13:31  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.IllegalPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.io.aaf.AAFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.impl.SingletonTypeDefinitionImpl.SingletonMethodBag;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl.SetMethodBag;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl.VariableArrayMethodBag;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.impl.ApplicationPluginObjectImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.util.Utilities;


/** 
 * <p>Implementation of a AAF property definition, which describes properties allowed 
 * for a class.</p>
 * 
 * <p>The preferred method of creating and managing property definitions in this Java
 * implementation is by annotating get methods on class implementations with the 
 * {@link MediaProperty} annotation. Property definitions are then created and attached
 * to their appropriate class definition using {@link ClassDefinitionImpl#forClass(Class)}.</p>
 * 
 * @see tv.amwa.maj.meta.PropertyDefinition
 * @see MediaProperty
 * @see tv.amwa.maj.model.InterchangeObject#getProperties()
 * @see tv.amwa.maj.industry.PropertyValue
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#PropertyDefinitionWeakReferenceSet
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0202, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "PropertyDefinition",
		  description = "The PropertyDefinition class describes properties allowed for a class.",
		  symbol = "PropertyDefinition")
public class PropertyDefinitionImpl
	extends 
		MetaDefinitionImpl
	implements 
		PropertyDefinition,
		Serializable,
		XMLSerializable,
		CommonConstants,
		Comparable<PropertyDefinition> {

	/** <p></p> */
	private static final long serialVersionUID = -784815624516057438L;
	
	/** <p>Template for local identifications, as used in {@link #appendXMLChildren(Node)}.</p> */
	// private static char[] localIdTemplate = new char[] {
	//	'0', 'x', '*', '*', '*', '*'
	// };
	
	/** <p>Hexidecimal character map used in processing local identifications into strings
	 * in {@link #appendXMLChildren(Node)}.</p> */
	// private static char[] hexCharMap = new char[] {
	// 	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
	// };
 	
	/** <p>Specifies the property type.</p> */
	private TypeDefinition propertyType = null;
	/** <p>Specifies whether objects instances can omit a value for the property.</p> */
	private transient String typeName;
	private boolean isOptional;
	/** <p>Specifies a local integer identification that is used to identify the property 
	 * in an AAF persistent unit. Used for MXF 2-byte set identifiers.</p> */
	private short localIdentification = 0;
	/** <p>Specifies that this property provides a unique identification for its associated
	 * object.</p> */
	private Boolean isUniqueIdentifier = null;
	/** <p>Class definition for the class that this defined property is a member of.</p> */
	private WeakReference<ClassDefinition> memberOf;
	private MethodBag methodBag = null;
	private Method getter = null;
	private boolean isXMLCDATA = false;
	private boolean isXMLAttribute = false;
	private boolean flattenXML = false;
	private int weight = 0;
	
	private static Map<String, String> propertyNameMap = null;
	
	public final static void initalizePropertyNameMap() {
		if (AAFConstants.MAP_SS_PROPERTY_NAMES) {
		
			int propertyMapSize = AAFConstants.propertyNameAliases.length / 2;
			propertyNameMap = 
				Collections.synchronizedMap(new HashMap<String, String>(propertyMapSize));
			for ( int x = 0 ; x < propertyMapSize ; x++ )
				propertyNameMap.put(AAFConstants.propertyNameAliases[x * 2], 
						AAFConstants.propertyNameAliases[x * 2 + 1]);
		}
	}
	
	/** Default constructor is not public to avoid unset required fields. */
	protected PropertyDefinitionImpl() {
	}

	/**
	 * <p>Creates and initializes a new property definition, which describes an allowed property of a
	 * {@link ClassDefinitionImpl class definition}.</p>
	 *
	 * <p>It is not normally necessary to create property definitions directly as they are 
	 * available through methods of 
	 * {@link tv.amwa.maj.meta.ClassDefinition ClassDefinition}. To extend the
	 * AAF object model with user defined classes, the {@link MediaProperty} annotation can be
	 * used to automatically generate property definitions.</p>
	 * 
	 * @param identification Unique identifier for the new property definition.
	 * @param name Display name of the new property definition.
	 * @param type Property type for the new property definition.
	 * @param memberOf Reference to the class this property is a member of, or <code>null</code>
	 * if this property is not associated with a class.
	 * @param isOptional Can an object omit a value for this property?
	 * 
	 * @throws NullPointerException One of the identification, name or type arguments is/are 
	 * <code>null</code> and they are all required in this implementation.
	 */
	public PropertyDefinitionImpl(
			AUID identification,
			@AAFString String name,
			String type,
			ClassDefinition memberOf,
			@Bool boolean isOptional)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a new property definition using a null identification.");
		if (type == null)
			throw new NullPointerException("Cannot create a new property definition using a null type definition.");
		
		setIdentification(identification);
		setName(name);
		setSymbol(name);
		this.typeName = type;
		setIsOptional(isOptional);
		setMemberOf(memberOf);
	}
	
	@MediaProperty(uuid1 = 0x03010202, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IsOptional",
			aliases = { "Optional" },
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x000C,
			symbol = "IsOptional")
	public boolean getIsOptional() {

		return isOptional;
	}

	@MediaPropertySetter("IsOptional")
	public void setIsOptional(boolean isOptional) {
		
		this.isOptional = isOptional;
	}
	
	public final static boolean initializeIsOptional() {
		
		return true;
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0600, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "IsUniqueIdentifier",
			aliases = { "UniqueIdentifier", "IsUnique", "Unique" },
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x000E,
			symbol = "IsUniqueIdentifier")
	public boolean getIsUniqueIdentifier() 
		throws PropertyNotPresentException {

		if (isUniqueIdentifier == null)
			throw new PropertyNotPresentException("The optional is unique identifier property is not present for this property definition.");
		
		return isUniqueIdentifier;
	}
	
	@MediaPropertySetter("IsUniqueIdentifier")
	public void setIsUniqueIdentifier(
			Boolean isUniqueIdentifier) {
		
		this.isUniqueIdentifier = isUniqueIdentifier;
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PropertyType",
			aliases = { "Type", "PropertyDefinitionType" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x000B,
			symbol = "PropertyType")
	public AUID getPropertyType() {
		
		return getTypeDefinition().getAUID();
	}

	@MediaPropertySetter("PropertyType")
	public void setPropertyType(
			AUID propertyType)
		throws NullPointerException {
		
		if (propertyType == null)
			throw new NullPointerException("Cannot set the type of this property with a null value.");
		
		this.propertyType = Warehouse.lookForType(propertyType);
		
		// When reading property defs from a file, the type may not be known
		if (this.propertyType != null)
			this.typeName = this.propertyType.getName();
		// TODO consider an error message here
	}
	
	public final static AUID initializePropertyType() {
		
		return TypeDefinitions.UTF16String.getAUID();
	}
	
	public TypeDefinition getTypeDefinition() {
		
		if (propertyType == null)
			propertyType = Warehouse.lookForType(typeName);
		
		return propertyType;
	}
	
	public void setTypeDefinition(
			TypeDefinition type) 
		throws NullPointerException {
		
		if (type == null)
			throw new NullPointerException("Cannot set the type definition of this class definition with a null value.");
	
		this.propertyType = type;
		this.typeName = type.getName();
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x0500, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "LocalIdentification",
			typeName = "UInt16",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x000D,
			symbol = "LocalIdentification")
	public short getLocalIdentification()  {
				
		return localIdentification;
	}
	
	@MediaPropertySetter("LocalIdentification")
	public void setLocalIdentification(
			Short localIdentification) {
	
		if (localIdentification == null)
			this.localIdentification = 0;
		else
			this.localIdentification = localIdentification;
	}
	
	public final static short initializeLocalIdentification() {
		
		return 0;
	}
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x2200, uuid3 = (short) 0x0000,
			uuid4 = { 0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x0d },
			definedName = "MemberOf",
			typeName = "ClassDefinitionWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x002b,
			symbol = "MemberOf",
			description = "Specifies the class in which this property may be present.")
	public ClassDefinition getMemberOf() 
		throws PropertyNotPresentException {
		
		if (memberOf == null)
			throw new PropertyNotPresentException("The optional member of a class property is not present in this property definition: " +
					getName() + ".");
		
		return memberOf.getTarget();
	}
	
	@MediaPropertySetter("MemberOf")
	public void setMemberOf(
			ClassDefinition memberOf) {
		
		if (memberOf == null)
			this.memberOf = null;
		else
			this.memberOf = new WeakReference<ClassDefinition>(memberOf);
	}
	
	// TODO comment
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyValue propertyValue) 
		throws IllegalArgumentException {
		
		try {
			if (memberOf == null)
				getTypeDefinition().setPropertyValue(metadataObject, this, propertyValue);
			else {
				try {
					memberOf.getTarget().lookupPropertyDefinition(getAUID());
					getTypeDefinition().setPropertyValue(metadataObject, this, propertyValue);
				}
				catch (BadParameterException bpe) {
					// Treat as an extension property
					if (!(metadataObject instanceof InterchangeObject))
						throw new IllegalArgumentException("Cannot set an extension property value on a non interchange object.");
					
					InterchangeObject interchangeable = (InterchangeObject) metadataObject;
					ApplicationPluginObject plugin = null;
					if (interchangeable.countApplicationPlugins() > 0)
						for ( ApplicationPluginObject pluginItem : interchangeable.getApplicationPlugins() ) {
							if (pluginItem.getApplicationScheme().getSchemeURI().equals(getNamespace())) {
								plugin = pluginItem;
								break;
							}
						}
					
					if (plugin == null) {
						plugin = new ApplicationPluginObjectImpl();
						plugin.setApplicationPluginInstanceID(Forge.randomAUID());
						plugin.setApplicationScheme(Warehouse.lookupExtensionScheme(getNamespace()));
						interchangeable.addApplicationPlugin(plugin);
					}
					
					plugin.putExtensionProperty(getAUID(), propertyValue);
				}
			}
		}
		catch (InvocationTargetException ite) {
			Throwable target = ite.getTargetException();
			if (memberOf != null)
				throw new IllegalArgumentException("Unable to set the value of property " + getMemberOf().getName() + "." + getName() + 
						" due to a local " + target.getClass().getName() + ": " + target.getMessage());
			else
				throw new IllegalArgumentException("Unable to set the value of a property named " + getName() + " with unknown membership due to a local " +
						target.getClass().getName() + ": " + target.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			if (memberOf != null)
				throw new IllegalArgumentException("Unable to set the value of property " + getMemberOf().getName() + "." + getName() + 
						" becuase of a " + e.getClass().getName() + ": " + e.getMessage());
			else
				throw new IllegalArgumentException("Unable to set the value of a property named " + getName() + " with unknown membership due to a " +
						e.getClass().getName() + ": " + e.getMessage());
		}
	}
	
	// TODO comment
	public PropertyValue getPropertyValue(
			MetadataObject metadataObject) 
		throws NullPointerException,
			IllegalPropertyException,
			IllegalArgumentException,
			PropertyNotPresentException {
		
		try {
			return getTypeDefinition().getPropertyValue(metadataObject, this);
		}
		catch (Exception e) {
			if (e.getCause() instanceof PropertyNotPresentException)
				throw (PropertyNotPresentException) e.getCause();
			IllegalArgumentException iae = new IllegalArgumentException("Unable to get the value of property " + getMemberOf().getName() + "." + getName() + 
					" becuase of a " + e.getClass().getName() + ": " + 
					((e.getCause() != null) ? e.getCause().getMessage() : ""));
			if (e instanceof InvocationTargetException)
				iae.initCause(e.getCause());
			throw iae;
		}
	}
	
	// TODO comment
	public MethodBag getMethodBag() {
		
		if (memberOf == null)
			System.err.println("*-*-*: " + getName());
		if (methodBag == null)
			methodBag = TypeDefinitionImpl.makeMethodBagForType(
					getTypeDefinition(),
					getter,
					getMemberOf().getJavaImplementation().getDeclaredMethods(), 
					getName());
		
		return methodBag;
	}
	
	// TODO comment
	public void setAnnotatedGetter(
			Method getter) {
		
		this.getter = getter;
	}

	public boolean isPropertyPresent(
			MetadataObject metadataObject)
		throws NullPointerException,
			IllegalPropertyException {

		if (metadataObject == null)
			throw new NullPointerException("Cannot check if a property is present on a null object.");
		
		ClassDefinition metaClass = Warehouse.lookForClass(metadataObject.getClass());
		try {
			metaClass.lookupPropertyDefinition(getAUID());
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("Property " + getName() + " is not defined for class + " + metaClass.getName() +
					" so its presence cannot be tested for.");
		}
		
		if (!isOptional)
			return true; // Required objects always present
		
		try {
			getter.invoke(metadataObject);
		}
		catch (Exception e) { // If you can't get the value, treat it as not present
			if (e instanceof IllegalArgumentException) 
				throw new IllegalPropertyException("The given metadata object does not contain this property.");
			return false;
		}
		
		// Getter succeeded without throwing property not present
		return true;
	}

	public void omitOptionalProperty(
				MetadataObject metadataObject)	
		throws NullPointerException, 
			IllegalPropertyException,
			IllegalArgumentException {
		
		if (metadataObject == null)
			throw new NullPointerException("Cannot omit an optional property for a null object.");
		
		ClassDefinition metaClass = Warehouse.lookForClass(metadataObject.getClass());
		try {
			metaClass.lookupPropertyDefinition(getAUID());
		}
		catch (BadParameterException bpe) {
			throw new IllegalPropertyException("Property " + getName() + " is not a member of " + metaClass.getName() + " and so cannot be omitted.");
		}
		
		if (!isOptional)
			throw new IllegalPropertyException("Property " + getName() + " is not optional in the context of " + metaClass.getName() + " and so cannot be omitted.");
		
		try {
			switch (getTypeDefinition().getTypeCategory()) {
			
			case Set:
				SetMethodBag setMethods = (SetMethodBag) getMethodBag();
				setMethods.clear(metadataObject);
				break;
			case VariableArray:
				VariableArrayMethodBag arrayMethods = (VariableArrayMethodBag) getMethodBag();
				arrayMethods.clear(metadataObject);
				break;
			default:
				SingletonMethodBag singletonMethods = (SingletonMethodBag) getMethodBag();
				singletonMethods.set(metadataObject, null);
				break;
			}
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Unable to omit optional property " + metaClass.getName() + "." + getName() + 
					" due to an " + e.getCause().getClass().getName() + ": " + e.getCause().getMessage(), e);
		}
	}
	
	@Override
	public void appendXMLChildren(
			Node parent) {
		
		XMLBuilder.appendComment(parent, "Symbol is " + symbol);
	}
	
	@Override
	public String getNamespace() {
		
		if (namespace == null)
			return memberOf.getTarget().getNamespace();
		return namespace;
	}

	@Override
	public String getPrefix() {
		
		if (prefix == null)
			return memberOf.getTarget().getPrefix();
		return prefix;
	}
	
	void setIsXMLCDATA(
			boolean isXMLCDATA) {
		
		this.isXMLCDATA = isXMLCDATA;
	}

	public boolean getIsXMLCDATA() {
		
		return isXMLCDATA;
	}
	
	void setIsXMLAttribute(
			boolean isXMLAttribute) {
		
		this.isXMLAttribute = isXMLAttribute;
	}
	
	public boolean getIsXMLAttribute() {
		
		return isXMLAttribute;
	}

	void setFlattenXML(
			boolean flattenXML) {
		
		this.flattenXML = flattenXML;
	}
	
	public boolean getFlattenXML() {
		
		return flattenXML;
	}

	public int compareTo(
			tv.amwa.maj.meta.PropertyDefinition o) {

		if (weight < o.getWeight()) return -1;
		if (weight > o.getWeight()) return 1;
		
		return getAUID().compareTo(o.getAUID());
	}
	
	void setWeight(
			int weight) {
		
		this.weight = weight;
	}
	
	public int getWeight() {
		
		return weight;
	}
	
	@Override
	public String nameToAAFName(
			String name) {
		
		if (getAUID().equals(AAFConstants.IdentificationGenerationIDProperty))
			return "GenerationAUID";
		
		if (propertyNameMap != null) {
			String mappedName = propertyNameMap.get(name);
			return (mappedName != null) ? mappedName : name;
		}
		
		return name;
	}
	
	@Override
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
//		System.out.println(getMemberOf().getName() + " : " + getName());
		
//		if (getName().equals("PrivateInformation")) {
//			System.err.println(this.toString());
//			return;
//		}
		
		Element propertyElement = XMLBuilder.createChild(metadict, namespace, 
				prefix, "PropertyDefinition");
	
		super.appendMetadictXML(propertyElement, namespace, prefix);
		
		XMLBuilder.appendElement(propertyElement, namespace, prefix, 
				"MemberOf", memberOf.getTarget().getName());
		XMLBuilder.appendElement(propertyElement, namespace, prefix,
				"Type", typeName);
		if (getTypeDefinition() == null) {
			XMLBuilder.appendComment(propertyElement, "Warning: Unable to resolve type at runtime.");
			System.err.println("Unable to resolve type name " + typeName + " for property " + 
					getMemberOf().getName() + "." + getName() + "." );
		}
		XMLBuilder.appendElement(propertyElement, namespace, prefix, 
				"IsOptional", isOptional);
		
		StringBuilder pidBuilder = new StringBuilder(6);
		pidBuilder.append(Utilities.bytesToHexChars(new byte[] {
				(byte) ((((int) localIdentification) >>> 8) & 255),
				(byte) (((int) localIdentification) & 255) }));
		while (pidBuilder.length() < 4) pidBuilder.insert(0, ' ');
		pidBuilder.insert(0, "0x");
		
		XMLBuilder.appendElement(propertyElement, namespace, prefix, 
				"LocalIdentification", pidBuilder.toString());
	}
	
	
	public PropertyDefinition clone() {
		
		return (PropertyDefinition) super.clone();
	}
}

