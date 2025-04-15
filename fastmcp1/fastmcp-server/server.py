#!/usr/bin/env python3

from fastmcp import FastMCP


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

    
    mcp.run(transport="sse")

   
if __name__ == "__main__":

    main()
    
