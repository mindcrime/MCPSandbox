#!/usr/bin/env python3

from fastmcp import FastMCP
import uvicorn
from starlette.applications import Starlette
from starlette.routing import Mount

def main():

    print( "Hello, FastMCP Server!\n" )

    # Create a basic server instance
    mcp = FastMCP(name="MyMCPServer",
                  instructions="This server provides data analysis tools. Call multiply() to multiply two integers" )

    # print( dir ( mcp ) )
    
    
    @mcp.tool()
    def multiply( a: int, b: int ) -> int:
       """multiply - multiplies two integers and returns an integer"""
       return a*b

    # Server Sent Events
    # mcp.run(transport="sse")

    sse_app = mcp.sse_app()
    main_app = Starlette(routes=[Mount("/testmcp/phil/", app=sse_app)])

    uvicorn.run(main_app, host="0.0.0.0", port=8000)
    
if __name__ == "__main__":

    main()
    
