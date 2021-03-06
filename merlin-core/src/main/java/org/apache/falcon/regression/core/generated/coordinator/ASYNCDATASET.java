/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation,
// vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.05.15 at 11:34:31 AM GMT+05:30 
//


package org.apache.falcon.regression.core.generated.coordinator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ASYNCDATASET complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="ASYNCDATASET">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="uri-template" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{uri:oozie:coordinator:0.3}IDENTIFIER" />
 *       &lt;attribute name="sequence-type" use="required" type="{http://www.w3
 *       .org/2001/XMLSchema}string" />
 *       &lt;attribute name="initial-version" use="required" type="{http://www.w3
 *       .org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ASYNCDATASET", propOrder = {
        "uriTemplate"
})
public class ASYNCDATASET {

    @XmlElement(name = "uri-template", required = true)
    protected String uriTemplate;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(name = "sequence-type", required = true)
    protected String sequenceType;
    @XmlAttribute(name = "initial-version", required = true)
    protected String initialVersion;

    /**
     * Gets the value of the uriTemplate property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUriTemplate() {
        return uriTemplate;
    }

    /**
     * Sets the value of the uriTemplate property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUriTemplate(String value) {
        this.uriTemplate = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sequenceType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSequenceType() {
        return sequenceType;
    }

    /**
     * Sets the value of the sequenceType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSequenceType(String value) {
        this.sequenceType = value;
    }

    /**
     * Gets the value of the initialVersion property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInitialVersion() {
        return initialVersion;
    }

    /**
     * Sets the value of the initialVersion property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInitialVersion(String value) {
        this.initialVersion = value;
    }

}
