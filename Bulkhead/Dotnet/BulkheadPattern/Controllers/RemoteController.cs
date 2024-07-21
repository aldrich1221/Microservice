
using Microsoft.AspNetCore.Mvc;

[ApiController]
[Route("api/[controller]")]
public class RemoteController : ControllerBase
{
    private readonly IRemoteService _remoteService;

    public RemoteController(IRemoteService remoteService)
    {
        _remoteService = remoteService;
    }

    [HttpGet("data")]
    public async Task<IActionResult> GetData()
    {
        var data = await _remoteService.GetDataAsync();
        return Ok(data);
    }
}