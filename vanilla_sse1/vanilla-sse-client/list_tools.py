#!/usr/bin/env python3


import asyncio
import json
import os
from typing import Optional
from contextlib import AsyncExitStack

from mcp import ClientSession
from mcp.client.sse import sse_client


async def connect_to_sse_server(server_url: str):
        """Connect to an MCP server running with SSE transport"""
        # Store the context managers so they stay alive
        streams_context = sse_client(url=server_url)
        streams = await streams_context.__aenter__()

        session_context = ClientSession(*streams)
        session: ClientSession = await session_context.__aenter__()

        # Initialize
        await session.initialize()

        # List available tools to verify connection
        print("Initialized SSE client...")
        print("Listing tools...")
        response = await session.list_tools()
        tools = response.tools
        print("\nConnected to server with tools:", [tool.name for tool in tools])

        #  print( dir( session ) )
        
        if( session_context ):
                await session_context.__aexit__(None, None, None)
        if( streams_context ):
                await streams_context.__aexit__(None, None, None)
        
async def main():

    print( "Hello, MCP Client!" )
    
    await connect_to_sse_server( "http://localhost:8080/sse" )

    print( "Done" )
    
if __name__=="__main__":

    asyncio.run( main() )
    
