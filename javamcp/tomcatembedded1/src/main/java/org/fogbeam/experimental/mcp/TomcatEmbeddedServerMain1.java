package org.fogbeam.experimental.mcp;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class TomcatEmbeddedServerMain1
{
    public static void main( String[] args ) throws Exception
    {

    	Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();
        
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);
    	
    	SampleServlet sampleServlet = new SampleServlet();
    	
    	String servletName = "SampleServlet";
        String urlPattern = "/aa";

        tomcat.addServlet(contextPath, servletName, sampleServlet );
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        tomcat.getServer().await();
        
        System.out.println( "done" );
    }
}
