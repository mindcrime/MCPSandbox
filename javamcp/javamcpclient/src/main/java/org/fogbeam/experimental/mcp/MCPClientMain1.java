package org.fogbeam.experimental.mcp;

import java.time.Duration;
import java.util.Map;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientSseClientTransport;
import io.modelcontextprotocol.spec.McpClientTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.ClientCapabilities;
import io.modelcontextprotocol.spec.McpSchema.ListToolsResult;
import io.modelcontextprotocol.spec.McpSchema.TextContent;

public class MCPClientMain1
{
    public static void main(String[] args ) throws Exception
    {
        
    	McpClientTransport transport = HttpClientSseClientTransport.builder("http://localhost:8080/mcp").build();
    	
    	// Create a sync client with custom configuration
    	McpSyncClient client = McpClient.sync(transport)
    	    .requestTimeout(Duration.ofSeconds(10))
    	    .capabilities(ClientCapabilities.builder()
    	        .roots(true)      // Enable roots capability
    	        .sampling()       // Enable sampling capability
    	        .build())
    	    .build();

    	// Initialize connection
    	client.initialize();

    	// List available tools
    	ListToolsResult tools = client.listTools();
    	
    	tools.tools().forEach( tool ->{
		    System.out.println("Tool: " + tool.name() + ", Description: " + tool.description() );
    	} );
    	
    	// Execute a tool with parameters
    	CallToolRequest callToolRequest = new CallToolRequest( "calculator", 
    												Map.of("a", "17", "b", "34"));
    	
    	var result = client.callTool(callToolRequest);
    	
    	TextContent toolText = (TextContent) result.content().get( 0 );
    	
    	System.out.println("Tool Result: " + toolText.text() );
    	
        System.out.println( "done" );
    }
}
