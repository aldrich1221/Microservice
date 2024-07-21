

using System.Net;
public class LogMetadata
{
    public string RequestBody { get; set; }
    public string RequestUri { get; set; }
    public string RequestMethod { get; set; }
    public DateTime? RequestTimestamp { get; set; }
    public string ResponseBody { get; set; }
    public HttpStatusCode ResponseStatusCode { get; set; }
    public DateTime? ResponseTimestamp { get; set; }
    public string TakeTime { get; set; }
}