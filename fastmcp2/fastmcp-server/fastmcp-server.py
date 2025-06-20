#!../fastmcp2-env/bin/python3

from mcp.server.fastmcp import FastMCP

def main():



    # Stateful server (maintains session state)
    # mcp = FastMCP("StatefulServer")

    # Stateless server (no session persistence)
    # mcp = FastMCP("StatelessServer", stateless_http=True)

    # Stateless server (no session persistence, no sse stream with supported client)
    mcp = FastMCP("StatelessServer", stateless_http=True, json_response=True)

    @mcp.tool(description="A simple add tool")
    def add(n: int, m:int) -> int:
        return n + m

    @mcp.tool(description="A simple multiply tool")
    def multiply(n: int, m: int) -> int:
        return n*m
    
    @mcp.tool()
    def echo(message: str) -> str:
        """Echo a message as a tool"""
        return f"Tool echo: {message}"    

    
    # Run server with streamable_http transport
    mcp.run(transport="streamable-http")


if __name__ == "__main__":
    main()
