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

package org.apache.falcon.regression.prism;

import org.apache.falcon.regression.core.bundle.Bundle;
import org.apache.falcon.regression.core.helpers.ColoHelper;
import org.apache.falcon.regression.core.helpers.PrismHelper;
import org.apache.falcon.regression.core.response.ServiceResponse;
import org.apache.falcon.regression.core.util.PrismUtil;
import org.apache.falcon.regression.core.util.Util;
import org.apache.falcon.regression.core.util.Util.URLS;
import org.testng.Assert;
import org.testng.TestNGException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class PrismSubmitTest {

    @BeforeMethod(alwaysRun = true)
    public void testName(Method method) throws Exception {
        Util.print("test name: " + method.getName());
        //restart server as precaution
        Util.restartService(UA1coloHelper.getClusterHelper());
        Util.restartService(ivoryqa1.getClusterHelper());
    }

    public PrismSubmitTest() throws Exception {

    }

    PrismHelper prismHelper = new PrismHelper("prism.properties");

    ColoHelper UA1coloHelper = new ColoHelper("gs1001.config.properties");

    ColoHelper ivoryqa1 = new ColoHelper("ivoryqa-1.config.properties");

    @Test
    public void submitCluster_1prism1coloPrismdown() {
        Bundle b = null;
        try {
            try {
                b = (Bundle) Util.readELBundles()[0][0];
                b.generateUniqueBundle();
                b = new Bundle(b, UA1coloHelper.getEnvFileName());

                Util.shutDownService(prismHelper.getClusterHelper());

                List<String> beforeSubmit = UA1coloHelper.getClusterHelper().getStoreInfo();
                try {
                    ServiceResponse r =
                            prismHelper.getClusterHelper()
                                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
                } catch (Exception e) {

                    Assert.assertTrue(e.getMessage()
                            .contains("Connection to http://10.14.118.26:8082 refused"));
                }
                List<String> afterSubmit = UA1coloHelper.getClusterHelper().getStoreInfo();

                PrismUtil
                        .compareDataStoreStates(beforeSubmit, afterSubmit,
                                Util.readClusterName(b.getClusters().get(0)),
                                0);
            } catch (Exception e) {
            }
        } finally {
            try {
                Util.restartService(prismHelper.getClusterHelper());
                prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
            } catch (Exception e) {
            }
        }

    }

    @Test
    public void submitCluster_resubmitDiffContent() throws Exception {
        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();


        try {


            //submit unique entity
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            //b.setInputFeedAvailabilityFlag("hihello");
            b.setCLusterWorkingPath(b.getClusters().get(0), "/projects/ivory/someRandomPath");
            Util.print("modified cluster Data: " + b.getClusters().get(0));
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            //		Assert.assertTrue(r.getMessage().contains("already registered with
            // configuration store. Can't be
            // submitted again. Try removing before submitting"));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_resubmitAlreadyPARTIALWithAllUp() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {


            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));

            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);

            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));


        } finally {

            Util.restartService(UA1coloHelper.getClusterHelper());

            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitProcess_1ColoDownAfter2FeedSubmitStartAfterProcessSubmitAnsDeleteProcess()
    throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(1));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(12000);


            List<String> beforeSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            r = prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());

            r = prismHelper.getProcessHelper().submitEntity(URLS.SUBMIT_URL, b.getProcessData());
            Util.assertFailed(r);
            //Util.assertSucceeded(r);
            List<String> afterSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.getProcessName(b.getProcessData()),
                    1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.getProcessName(b.getProcessData()), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.getProcessName(b.getProcessData()),
                    0);


            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(15000);


            beforeSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            beforeSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            beforeSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();


            r = prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());
            //Assert.assertTrue(r.getMessage().contains("FAILED"));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));


            afterSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            afterSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            afterSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.getProcessName(b.getProcessData()),
                    0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.getProcessName(b.getProcessData()), -1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.getProcessName(b.getProcessData()),
                    0);

        } finally {
            Util.restartService(UA1coloHelper.getClusterHelper());
            prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());
            prismHelper.getFeedHelper().delete(URLS.DELETE_URL, b.getDataSets().get(0));
            prismHelper.getFeedHelper().delete(URLS.DELETE_URL, b.getDataSets().get(1));
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));

        }
    }

    @Test
    public void submitProcess_ideal() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));


            List<String> beforeSubmit_ua1 = UA1coloHelper.getFeedHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getFeedHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getFeedHelper().getStoreInfo();

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(1));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            List<String> afterSubmit_ua1 = UA1coloHelper.getFeedHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getFeedHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getFeedHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1, 2);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism, 2);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2, 0);


            beforeSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            beforeSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            beforeSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();


            r = prismHelper.getProcessHelper().submitEntity(URLS.SUBMIT_URL, b.getProcessData());
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            afterSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            afterSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            afterSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.getProcessName(b.getProcessData()),
                    1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.getProcessName(b.getProcessData()), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.getProcessName(b.getProcessData()),
                    0);

        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
            prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());
        }
    }

    @Test
    public void submitCluster_1prism1coloColoDown() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            Util.shutDownService(UA1coloHelper.getClusterHelper());

            List<String> beforeSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));


            List<String> afterSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            //should be partial
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            ///PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
            // Util.readClusterName(b.getClusters
            // ().get(0)),1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readClusterName(b.getClusters().get(0)), 1);


            Util.startService(UA1coloHelper.getClusterHelper());

            Thread.sleep(10000);


            beforeSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            beforeSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            beforeSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));


            afterSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            afterSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            afterSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            //should be succeeded
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            ///PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
            // Util.readClusterName(b.getClusters
            // ().get(0)),0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readClusterName(b.getClusters().get(0)), 0);

        } finally {
            Util.startService(UA1coloHelper.getClusterHelper());
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_1prism1coloSubmitDeleted() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());


            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));


            List<String> beforeSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));


            List<String> afterSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readClusterName(b.getClusters().get(0)), 1);


        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitProcess_woClusterSubmit() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getProcessHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getProcessData());

            Assert.assertTrue(r.getMessage().contains("FAILED"));
            Assert.assertTrue(r.getMessage().contains("is not registered"));

        } finally {
            prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());
        }
    }

    @Test
    public void submitProcess_woFeedSubmit() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            r = prismHelper.getProcessHelper().submitEntity(URLS.SUBMIT_URL, b.getProcessData());

            Assert.assertTrue(r.getMessage().contains("FAILED"));
            Assert.assertTrue(r.getMessage().contains("is not registered"));

        } finally {

            prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());

            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test(groups = {"prism", "0.2"})
    public void submitCluster_resubmitAlreadyPARTIAL() throws Exception {
        Util u = new Util();
        Bundle b1 = (Bundle) Util.readELBundles()[0][0];
        b1.generateUniqueBundle();
        Bundle b2 = (Bundle) Util.readELBundles()[0][0];
        b2.generateUniqueBundle();

        try {
            b1 = new Bundle(b1, UA1coloHelper.getEnvFileName());
            b2 = new Bundle(b2, ivoryqa1.getEnvFileName());

            List<String> before_colo1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> before_prism = prismHelper.getClusterHelper().getStoreInfo();
            List<String> before_colo2 = ivoryqa1.getClusterHelper().getStoreInfo();

            Util.shutDownService(UA1coloHelper.getFeedHelper());

            b2.setCLusterColo("ua2");
            Util.print("cluster b2: " + b2.getClusters().get(0));
            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b2.getClusters().get(0));
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));

            List<String> par_colo1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> par_prism = prismHelper.getClusterHelper().getStoreInfo();
            List<String> par_colo2 = ivoryqa1.getClusterHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(par_colo1, before_colo1,
                    Util.readClusterName(b1.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(before_prism, par_prism,
                    Util.readClusterName(b2.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(before_colo2, par_colo2,
                    Util.readClusterName(b2.getClusters().get(0)), 1);

            Util.restartService(UA1coloHelper.getFeedHelper());

            b1.setCLusterColo("ua1");
            Util.print("cluster b1: " + b1.getClusters().get(0));
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b1.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            List<String> after_colo1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> after_prism = prismHelper.getClusterHelper().getStoreInfo();
            List<String> after_colo2 = ivoryqa1.getClusterHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(par_colo1, after_colo1,
                    Util.readClusterName(b1.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(after_prism, par_prism,
                    Util.readClusterName(b2.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(after_colo2, par_colo2,
                    Util.readClusterName(b2.getClusters().get(0)), 0);

        } catch (Exception e) {
            e.printStackTrace();
            throw new TestNGException(e.getMessage());
        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b1.getClusters().get(0));
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b2.getClusters().get(0));

        }

    }

    @Test
    public void submitCluster_polarization() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            //shutdown one colo and submit

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 1);


            //resubmit PARTIAL success
            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);
            beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);


        } finally {
            Util.restartService(UA1coloHelper.getClusterHelper());
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_resubmitDiffContentPARTIAL() throws Exception {
        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();


        try {


            //submit unique entity PARTIAL
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);
            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);

            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            //b.setInputFeedAvailabilityFlag("hihello");
            b.setCLusterWorkingPath(b.getClusters().get(0), "/projects/ivory/someRandomPath");
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            //Assert.assertTrue(r.getMessage().contains("FAILED"));
            //Assert.assertTrue(r.getMessage().contains("already registered with configuration
            // store. Can't be
            // submitted again. Try removing before submitting"));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_PARTIALDeletedOfPARTIALSubmit() throws Exception {
        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();


        try {


            //submit unique entity PARTIAL
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);
            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            //Util.startService(UA1coloHelper.getClusterHelper());
            //Thread.sleep(30000);


            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            r = prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), -1);

            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            //Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.restartService(UA1coloHelper.getClusterHelper());

            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_submitPartialDeleted() throws Exception {
        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();


        try {


            //submit unique entity PARTIAL
            b = new Bundle(b, UA1coloHelper.getEnvFileName());
            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            Thread.sleep(30000);

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);

            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            r = prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("PARTIAL"));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), -1);

            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);


            beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 1);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Util.restartService(UA1coloHelper.getClusterHelper());

            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_resubmitAlreadySucceeded() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            //submit unique entity
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            List<String> beforeSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            List<String> afterSubmit_colo = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = ivoryqa1.getClusterHelper().getStoreInfo();
            PrismUtil.compareDataStoreStates(beforeSubmit_colo, afterSubmit_colo,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);
        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_1prism1coloAllUp() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];

        try {


            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            List<String> beforeSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();


            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));


            List<String> afterSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readClusterName(b.getClusters().get(0)), 1);


        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitCluster_1prism1coloAlreadySubmitted() throws Exception {

        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());


            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));

            List<String> beforeSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));


            List<String> afterSubmit_ua1 = UA1coloHelper.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getClusterHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getClusterHelper().getStoreInfo();

            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));
            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readClusterName(b.getClusters().get(0)), 0);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readClusterName(b.getClusters().get(0)), 0);

        } finally {
            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

    @Test
    public void submitProcess_1ColoDownAfter1FeedSubmitStartAfter2feed() throws Exception {
        Bundle b = (Bundle) Util.readELBundles()[0][0];
        b.generateUniqueBundle();

        try {
            b = new Bundle(b, UA1coloHelper.getEnvFileName());

            ServiceResponse r = prismHelper.getClusterHelper()
                    .submitEntity(URLS.SUBMIT_URL, b.getClusters().get(0));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(0));
            Assert.assertTrue(r.getMessage().contains("FAILED"));

            Util.shutDownService(UA1coloHelper.getClusterHelper());
            Thread.sleep(30000);


            List<String> beforeSubmit_ua1 = UA1coloHelper.getFeedHelper().getStoreInfo();
            List<String> beforeSubmit_ua2 = ivoryqa1.getFeedHelper().getStoreInfo();
            List<String> beforeSubmit_prism = prismHelper.getFeedHelper().getStoreInfo();

            r = prismHelper.getFeedHelper().submitEntity(URLS.SUBMIT_URL, b.getDataSets().get(1));
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            List<String> afterSubmit_ua1 = UA1coloHelper.getFeedHelper().getStoreInfo();
            List<String> afterSubmit_ua2 = ivoryqa1.getFeedHelper().getStoreInfo();
            List<String> afterSubmit_prism = prismHelper.getFeedHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.readDatasetName(b.getDataSets().get(1)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.readDatasetName(b.getDataSets().get(1)), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.readDatasetName(b.getDataSets().get(1)), 0);


            Util.startService(UA1coloHelper.getClusterHelper());
            Thread.sleep(15000);


            beforeSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            beforeSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            beforeSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            r = prismHelper.getProcessHelper().submitEntity(URLS.SUBMIT_URL, b.getProcessData());
            Assert.assertTrue(r.getMessage().contains("SUCCEEDED"));

            afterSubmit_ua1 = UA1coloHelper.getProcessHelper().getStoreInfo();
            afterSubmit_ua2 = ivoryqa1.getProcessHelper().getStoreInfo();
            afterSubmit_prism = prismHelper.getProcessHelper().getStoreInfo();

            PrismUtil.compareDataStoreStates(beforeSubmit_ua1, afterSubmit_ua1,
                    Util.getProcessName(b.getProcessData()),
                    1);
            PrismUtil.compareDataStoreStates(beforeSubmit_prism, afterSubmit_prism,
                    Util.getProcessName(b.getProcessData()), 1);
            PrismUtil.compareDataStoreStates(beforeSubmit_ua2, afterSubmit_ua2,
                    Util.getProcessName(b.getProcessData()),
                    0);


        } finally {
            Util.restartService(UA1coloHelper.getClusterHelper());
            prismHelper.getProcessHelper().delete(URLS.DELETE_URL, b.getProcessData());

            prismHelper.getFeedHelper().delete(URLS.DELETE_URL, b.getDataSets().get(0));
            prismHelper.getFeedHelper().delete(URLS.DELETE_URL, b.getDataSets().get(1));

            prismHelper.getClusterHelper().delete(URLS.DELETE_URL, b.getClusters().get(0));
        }
    }

}
