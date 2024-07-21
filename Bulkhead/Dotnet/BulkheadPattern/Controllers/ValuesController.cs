using Microsoft.AspNetCore.Mvc;

namespace DremioExample.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ValuesController : ControllerBase
    {
        [HttpGet]
        public IActionResult Get()
        {
            var values = new[] { "Value1", "Value2", "Value3" };
            return Ok(values);
        }
    }
}
