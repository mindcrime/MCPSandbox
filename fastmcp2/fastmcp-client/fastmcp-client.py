#!../fastmcp2-env/bin/python3

import asyncio
from mcp.client.streamable_http import streamablehttp_client
from mcp import ClientSession


async def main():
    # Connect to a streamable HTTP server
    async with streamablehttp_client("http://localhost:8000/mcp") as (
        read_stream, write_stream, _, ):
        
        # Create a session using the client streams
        async with ClientSession(read_stream, write_stream) as session:
            # Initialize the connection
            await session.initialize()
            # Call a tool
            
            print( "Tools: ", await session.list_tools() )
            
            tool_result = await session.call_tool("echo", {"message": "hello"})
            print( tool_result )

if __name__ == "__main__":
   asyncio.run( main() )
   
