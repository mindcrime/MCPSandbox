package org.fogbeam.experimental.mcp;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletSseServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.Tool;



public class MCPServerMain1
{
    public static void main( String[] args ) throws Exception
    {    	
    	/* NOTES:
    	  
    	  	https://modelcontextprotocol.io/sdk/java/mcp-server
    	 	https://stackoverflow.com/questions/71383171/simple-embedded-tomcat-10-example
    	 	https://github.com/cyberkaida/reverse-engineering-assistant/tree/9b593f5eb9d21dfb3a8da344ad19e7316bdd893b
    	 	https://github.com/cyberkaida/reverse-engineering-assistant/blob/9b593f5eb9d21dfb3a8da344ad19e7316bdd893b/src/main/java/reva/server/McpServerManager.java
    	 */
    		
    	HttpServletSseServerTransportProvider transportProvider = 
    			new HttpServletSseServerTransportProvider(new ObjectMapper(), "/mcp/message");
    	
    	
    	McpSyncServer syncServer = McpServer.sync(transportProvider)
    		    .serverInfo("my-server", "1.0.0")
    		    .capabilities(ServerCapabilities.builder()
    		        .resources(false, false)     // Enable resource support
    		        .tools(true)         // Enable tool support
    		        .prompts(false)       // Enable prompt support
    		        .logging()           // Enable logging support
    		        .build())
    		    .build();

    	
    	SyncToolSpecification syncToolSpecification = createTool();
    	
		// Register tools, resources, and prompts
		syncServer.addTool(syncToolSpecification);
		// syncServer.addResource(syncResourceSpecification);
		// syncServer.addPrompt(syncPromptSpecification);

		
		// Start the server    	
    	Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();
        
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();

        Context context = tomcat.addContext(contextPath, docBase);
    	    	
    	String servletName = "MCPServlet";
        String urlPattern = "/*";

        tomcat.addServlet(contextPath, servletName, transportProvider );
        context.addServletMappingDecoded(urlPattern, servletName);

        tomcat.start();
        tomcat.getServer().await();
    	
        
        System.out.println( "Done" );
    }

	private static SyncToolSpecification createTool()
	{
		// Sync tool specification
		var schema = """
		            {
		              "type" : "object",
		              "id" : "urn:jsonschema:Operation",
		              "properties" : {
		                "operation" : {
		                  "type" : "string"
		                },
		                "a" : {
		                  "type" : "number"
		                },
		                "b" : {
		                  "type" : "number"
		                }
		              }
		            }
		            """;
		
		var syncToolSpecification = new McpServerFeatures.SyncToolSpecification(
		    new Tool("calculator", "Basic calculator", schema),
		    (exchange, arguments) -> {
		        
		    	// Tool implementation
		    	
		    	System.out.println( "Tool invoked with arguments: " + arguments );
		    	
		    	String aStr = (String) arguments.get("a");
		    	String bStr = (String) arguments.get("b");
		    	
		    	String result = Integer.toString( ( Integer.parseInt(aStr) + Integer.parseInt(bStr) ) );
		    	
		        return new CallToolResult(result, false);
		    }
		);	
		
		return syncToolSpecification;
	}
}
