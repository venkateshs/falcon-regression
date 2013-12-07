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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COORDINATOR-APP complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="COORDINATOR-APP">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="controls" type="{uri:oozie:coordinator:0.3}CONTROLS" minOccurs="0"/>
 *         &lt;element name="datasets" type="{uri:oozie:coordinator:0.3}DATASETS" minOccurs="0"/>
 *         &lt;element name="input-events" type="{uri:oozie:coordinator:0.3}INPUTEVENTS"
 *         minOccurs="0"/>
 *         &lt;element name="output-events" type="{uri:oozie:coordinator:0.3}OUTPUTEVENTS"
 *         minOccurs="0"/>
 *         &lt;element name="action" type="{uri:oozie:coordinator:0.3}ACTION"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{uri:oozie:coordinator:0.3}IDENTIFIER" />
 *       &lt;attribute name="frequency" use="required" type="{http://www.w3
 *       .org/2001/XMLSchema}string" />
 *       &lt;attribute name="start" use="required" type="{http://www.w3
 *       .org/2001/XMLSchema}string" />
 *       &lt;attribute name="end" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="timezone" use="required" type="{http://www.w3
 *       .org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COORDINATOR-APP", propOrder = {
        "controls",
        "datasets",
        "inputEvents",
        "outputEvents",
        "action"
})

@XmlRootElement(name = "COORDINATORAPP")
public class COORDINATORAPP {

    protected CONTROLS controls;
    protected DATASETS datasets;
    @XmlElement(name = "input-events")
    protected INPUTEVENTS inputEvents;
    @XmlElement(name = "output-events")
    protected OUTPUTEVENTS outputEvents;
    @XmlElement(required = true)
    protected ACTION action;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(required = true)
    protected String frequency;
    @XmlAttribute(required = true)
    protected String start;
    @XmlAttribute(required = true)
    protected String end;
    @XmlAttribute(required = true)
    protected String timezone;

    /**
     * Gets the value of the controls property.
     *
     * @return possible object is
     * {@link CONTROLS }
     */
    public CONTROLS getControls() {
        return controls;
    }

    /**
     * Sets the value of the controls property.
     *
     * @param value allowed object is
     *              {@link CONTROLS }
     */
    public void setControls(CONTROLS value) {
        this.controls = value;
    }

    /**
     * Gets the value of the datasets property.
     *
     * @return possible object is
     * {@link DATASETS }
     */
    public DATASETS getDatasets() {
        return datasets;
    }

    /**
     * Sets the value of the datasets property.
     *
     * @param value allowed object is
     *              {@link DATASETS }
     */
    public void setDatasets(DATASETS value) {
        this.datasets = value;
    }

    /**
     * Gets the value of the inputEvents property.
     *
     * @return possible object is
     * {@link INPUTEVENTS }
     */
    public INPUTEVENTS getInputEvents() {
        return inputEvents;
    }

    /**
     * Sets the value of the inputEvents property.
     *
     * @param value allowed object is
     *              {@link INPUTEVENTS }
     */
    public void setInputEvents(INPUTEVENTS value) {
        this.inputEvents = value;
    }

    /**
     * Gets the value of the outputEvents property.
     *
     * @return possible object is
     * {@link OUTPUTEVENTS }
     */
    public OUTPUTEVENTS getOutputEvents() {
        return outputEvents;
    }

    /**
     * Sets the value of the outputEvents property.
     *
     * @param value allowed object is
     *              {@link OUTPUTEVENTS }
     */
    public void setOutputEvents(OUTPUTEVENTS value) {
        this.outputEvents = value;
    }

    /**
     * Gets the value of the action property.
     *
     * @return possible object is
     * {@link ACTION }
     */
    public ACTION getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     *
     * @param value allowed object is
     *              {@link ACTION }
     */
    public void setAction(ACTION value) {
        this.action = value;
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
     * Gets the value of the frequency property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFrequency(String value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the start property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * Gets the value of the timezone property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTimezone(String value) {
        this.timezone = value;
    }

}