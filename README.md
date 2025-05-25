# MCPSandbox

This repo contains a mish-mash of sample code and examples related to using Model Context Protocol (MCP).

So far there are examples of creating and using MCP servers and clients in Python, using both stdio transport and sse transport,
and examples of creating an MCP server in Java, using the sse transport. The Java example is intentionally minimalistic
and uses the HttpServletSseServerTransportProvider object as a Servlet directly with embedded Tomcat, instead of relying on the
Spring Boot support that exists. For building "real" Java MCP servers it's probably easier to use Spring Boot, but this was
intended purely for didactic purposes.


For the Java example, I cribbed ideas from several different places to get a working example put together.
Specifically, I used the following resources:

- https://modelcontextprotocol.io/sdk/java/mcp-server
- https://stackoverflow.com/questions/71383171/simple-embedded-tomcat-10-example
- https://github.com/cyberkaida/reverse-engineering-assistant/tree/9b593f5eb9d21dfb3a8da344ad19e7316bdd893b
- https://github.com/cyberkaida/reverse-engineering-assistant/blob/9b593f5eb9d21dfb3a8da344ad19e7316bdd893b/src/main/java/reva/server/McpServerManager.java

For the Python examples, note that there are two tracks: one that uses the FastMCP library, and one that uses the "vanilla" Python SDK.
FastMCP is probably recommended for actual usage, but again, this is all for learning purposes first and foremost.

Additionally, I highly recommend using the 'uv' tool to setup your Python environment if you want to use any of this Python sample code.
It's not required, but it makes life easier. To setup this project using uv just do the following:

```
$> uv init --name mcpsandbox

$> uv python install 3.12.7

add your needed dependencies to the pyproject.toml file ("mcp" and "fastmcp" should be about it)

$> uv venv --python 3.12.7

$> uv sync
```

then use "uv run" to run the .py files you want to run.

