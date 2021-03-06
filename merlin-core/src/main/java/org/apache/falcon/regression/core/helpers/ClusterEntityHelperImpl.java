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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.apache.falcon.regression.core.helpers;

import org.apache.falcon.regression.core.generated.cluster.Cluster;
import org.apache.falcon.regression.core.interfaces.IEntityManagerHelper;
import org.apache.falcon.regression.core.response.APIResult;
import org.apache.falcon.regression.core.response.ProcessInstancesResult;
import org.apache.falcon.regression.core.response.ServiceResponse;
import org.apache.falcon.regression.core.util.Util;
import org.testng.log4testng.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;

public class ClusterEntityHelperImpl extends IEntityManagerHelper {


    public ClusterEntityHelperImpl() {

    }

    public ClusterEntityHelperImpl(String envFileName) throws Exception {
        super(envFileName);
    }

    Logger logger = Logger.getLogger(this.getClass());

    public ServiceResponse delete(String url, String data) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        url += "/cluster/" + Util.readClusterName(data) + colo;
        return Util.sendRequest(url);
    }

    public ServiceResponse getEntityDefinition(String url, String data) throws Exception {
        url += "/cluster/" + Util.readClusterName(data);

        return Util.sendRequest(url);

    }

    public ServiceResponse getStatus(String url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse getStatus(Util.URLS url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse resume(String url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse schedule(String url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse submitAndSchedule(String url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse submitAndSchedule(Util.URLS url, String data) throws Exception {
        return null;
    }

    public ServiceResponse submitEntity(String url, String data) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        url += "/cluster" + colo;

        return Util.sendRequest(url, data);
    }

    public ServiceResponse suspend(String url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse suspend(Util.URLS url, String data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ServiceResponse validateEntity(String url, String data) throws Exception {

        return Util.sendRequest(url, data);
    }

    public void validateResponse(String response, APIResult.Status expectedResponse,
                                 String filename) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServiceResponse submitEntity(Util.URLS url, String data) throws Exception {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Not supported yet.");
        return submitEntity(this.hostname + url.getValue(), data);
    }

    @Override
    public ServiceResponse schedule(Util.URLS scheduleUrl, String processData)
    throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ServiceResponse delete(Util.URLS deleteUrl, String data) throws Exception {
        // TODO Auto-generated method stub
        return delete(this.hostname + deleteUrl.getValue(), data);
    }

    @Override
    public ServiceResponse resume(Util.URLS url, String data) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    public ProcessInstancesResult getRunningInstance(
            Util.URLS processRuningInstance, String name) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }


    public ProcessInstancesResult getProcessInstanceStatus(
            String readEntityName, String params) {
        // TODO Auto-generated method stub
        return null;
    }


    public ProcessInstancesResult getProcessInstanceSuspend(
            String readEntityName, String params) {
        // TODO Auto-generated method stub
        return null;
    }

    public String writeEntityToFile(String entity) throws Exception {
        File file = new File("/tmp/" + Util.readClusterName(entity) + ".xml");
        BufferedWriter bf = new BufferedWriter(new FileWriter(file));
        bf.write(entity);
        bf.close();
        return "/tmp/" + Util.readClusterName(entity) + ".xml";
    }

    @Override
    public String submitEntityViaCLI(String filePath) throws Exception {

        //System.out.println(BASE_COMMAND+ " entity -submit -url "+this.hostname+" -type cluster
        // -file "+filePath);
        return Util.executeCommand(
                BASE_COMMAND + " entity -submit -url " + this.hostname + " -type cluster -file " +
                        filePath);
    }

    @Override
    public String validateEntityViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -validate -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    @Override
    public String submitAndScheduleViaCLI(String filePath) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -submitAndSchedule -url " + this.hostname +
                        " -type cluster -file " + filePath);
    }

    @Override
    public String scheduleViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -schedule -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    @Override
    public String resumeViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -resume -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    @Override
    public String getStatusViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -status -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    @Override
    public String getEntityDefinitionViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -definition -url " + this.hostname +
                        " -type cluster -name " + entityName);
    }

    @Override
    public String deleteViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -delete -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    @Override
    public String suspendViaCLI(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -suspend -url " + this.hostname + " -type cluster -name " +
                        entityName);
    }

    public String updateViaCLI(String processName, String newProcessFilePath) throws Exception {
        return null;
    }

    public String list() throws Exception {
        return Util.executeCommand(
                BASE_COMMAND + " entity -list -url " + this.hostname + " -type cluster");
    }

    @Override
    public String getDependencies(String entityName) throws Exception {

        return Util.executeCommand(
                BASE_COMMAND + " entity -dependency -url " + this.hostname +
                        " -type cluster -name " + entityName);
    }

    @Override
    public ProcessInstancesResult getRunningInstance(String processRuningInstance, String name)
    throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<String> getArchiveInfo() throws Exception {

        return Util.getClusterArchiveInfo(this);
    }

    @Override
    public List<String> getStoreInfo() throws Exception {

        return Util.getClusterStoreInfo(this);
    }

    @Override
    public ServiceResponse getEntityDefinition(Util.URLS url, String data)
    throws Exception {
        return getEntityDefinition(this.hostname + url.getValue(), data);
    }

    @Override
    public ServiceResponse update(String oldEntity, String newEntity) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString(Object object) throws Exception {
        Cluster processObject = (Cluster) object;

        JAXBContext context = JAXBContext.newInstance(Cluster.class);
        Marshaller um = context.createMarshaller();
        StringWriter writer = new StringWriter();
        um.marshal(processObject, writer);
        return writer.toString();
    }

    @Override
    public ProcessInstancesResult getInstanceRerun(String EntityName, String params)
    throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ProcessInstancesResult getProcessInstanceKill(String readEntityName,
                                                         String string) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProcessInstancesResult getProcessInstanceRerun(
            String readEntityName, String string) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProcessInstancesResult getProcessInstanceResume(
            String readEntityName, String string) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getProcessInstanceStatusViaCli(String EntityName,
                                                 String start, String end, String colos)
    throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

}
