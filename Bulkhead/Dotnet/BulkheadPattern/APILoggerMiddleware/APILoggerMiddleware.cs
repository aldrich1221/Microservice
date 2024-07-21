using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using System.Diagnostics;
using System.Threading.Tasks;
using System.Text;

public class APILoggerMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger _logger;
    private const string ResponseTime = "X-Response-Time-ms";

    public APILoggerMiddleware(RequestDelegate next, ILoggerFactory loggerFactory)
    {
        _next = next;
        _logger = loggerFactory.CreateLogger<APILoggerMiddleware>();
    }

    public async Task Invoke(HttpContext httpContext)
    {
        var watch = new Stopwatch();
        watch.Start();

   
        var request = await FormatRequest(httpContext.Request);

      
        var originalBodyStream = httpContext.Response.Body;
        using (var responseBody = new MemoryStream())
        {
            httpContext.Response.Body = responseBody;

            await _next(httpContext);

            watch.Stop();
            var responseTimeForCompleteRequest = watch.ElapsedMilliseconds;
            httpContext.Response.Headers[ResponseTime] = responseTimeForCompleteRequest.ToString();

            // Log details
            _logger.LogInformation("Request: {0}", request);
            _logger.LogInformation("Method: {0}", httpContext.Request.Method);
            _logger.LogInformation("Response Time: {0} ms", responseTimeForCompleteRequest);

            var response = await FormatResponse(httpContext.Response);
            _logger.LogInformation("Response: {0}", response);

            await responseBody.CopyToAsync(originalBodyStream);
        }
    }

    private async Task<string> FormatRequest(HttpRequest request)
    {
        request.EnableBuffering();
        var buffer = new byte[Convert.ToInt32(request.ContentLength)];
        await request.Body.ReadAsync(buffer, 0, buffer.Length);
        var bodyAsText = Encoding.UTF8.GetString(buffer);
        request.Body.Position = 0;
        return $"{request.Scheme} {request.Host}{request.Path} {request.QueryString} {bodyAsText}";
    }

    private async Task<string> FormatResponse(HttpResponse response)
    {
        response.Body.Seek(0, SeekOrigin.Begin);
        var text = await new StreamReader(response.Body).ReadToEndAsync();
        response.Body.Seek(0, SeekOrigin.Begin);
        return text;
    }
}

public static class APILoggerMiddlewareExtensions
{
    public static IApplicationBuilder UseAPILoggerMiddleware(this IApplicationBuilder builder)
    {
        return builder.UseMiddleware<APILoggerMiddleware>();
    }
}