/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2004-2017 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package customization.jaxb_custom.doclit.client;

import junit.framework.TestCase;
import junit.framework.Assert;
import testutil.ClientServerTestUtil;
import javax.xml.namespace.QName;
import javax.xml.ws.Holder;

/**
 *
 * @author JAX-RPC RI Development Team
 */
public class HelloLiteralTest extends TestCase {

    private static HelloWorld stub;

    public HelloLiteralTest(String name) throws Exception{
        super(name);
        CustomService service = new CustomService();
        stub = service.getCustomizedPort();
        ClientServerTestUtil.setTransport(stub);
//            stub = (HelloWorld) ClientServerTestUtil.getPort(CustomService.class, HelloWorld.class, new QName("urn:test", "HelloPort"));
       
    }

    public void testHello() throws Exception {
        try{
            String arg = "Hello";
            String extra = "Works";
            
            Holder<String> argHolder = new Holder<String>();
            argHolder.value = arg;
            
            Holder<String> extHolder = new Holder<String>();
            extHolder.value = extra;
                        
            stub.helloWorld(argHolder, extHolder);
            assertEquals(argHolder.value, arg + " World!");
            assertEquals(extHolder.value, extra + " Fine!");
        } catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }
    
//    public void testHellowithHeaderFault() throws Exception {
//        try{
//            String arg = "Hello";
//            String extra = "Works";
//
//            Holder<String> argHolder = new Holder<String>();
//            argHolder.value = arg;
//
//            Holder<String> extHolder = new Holder<String>();
//            extHolder.value = extra;
//
//            stub.helloWorld(argHolder, extHolder, "please throw exception!");
//            assertTrue(false);
//        } catch(InfoException ie){
//            if(ie.getFaultInfo().equals("InfoException on request"))
//                assertTrue(true);
//            else
//                assertTrue(false);
//        }catch(Exception e){
//            e.printStackTrace();
//            assertTrue(false);
//        }
//    }

    public void testAbstract() throws Exception{
        try{
            MyAbstractType req = new MyAbstractType();
            req.setAbstract("Hello Abstract");
            req.setStatic("Hello Static");
            Holder<String> abstractReq = new Holder<String>("abstract");
            Holder<String> staticReq = new Holder<String>("static");

            stub.helloAbstract(abstractReq, staticReq);
            assertTrue(abstractReq.value.equals("hello abstract!") && staticReq.value.equals("hello static!"));   
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    public void testFoo() throws Exception {
        try{
            RenamedFoo req = new RenamedFoo();
            req.setFooChild1("Hello");
            req.setFooChild2("World!");
            FooResponse resp = stub.echoFoo(req);
            assertEquals(resp.getFooResponse1(), "Hello World!");
        }catch(Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

}
