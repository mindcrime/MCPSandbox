#!/usr/bin/env python3

import asyncio
from fastmcp import Client, FastMCP

async def main():

    print( "Hello, FastMCP Client!\n" )
    sse_url = "http://localhost:8000/sse"       # SSE server URL
    # sse_url = "http://10.133.65.94:80/sse"
    client = Client(sse_url)

    print( client.transport )

    # Connection is established here
    async with client:
        print(f"Client connected: {client.is_connected()}")

        # Make MCP calls within the context
        tools = await client.list_tools()
        print(f"Available tools: {tools}")


        if any(tool.name == "multiply" for tool in tools):
            result = await client.call_tool("multiply", {"a": 3, "b":4})
            print(f"Greet result: {result}")

    # Connection is closed automatically here
    print(f"Client connected: {client.is_connected()}")


        
    print( "Done" )
    
    
if __name__ == "__main__":

    asyncio.run( main() )
    
