using System.Net.Http;
using System.Threading.Tasks;

public interface IRemoteService
{
    Task<string> GetDataAsync();
}

public class RemoteService : IRemoteService
{
    private readonly HttpClient _httpClient;

    public RemoteService(IHttpClientFactory httpClientFactory)
    {
        _httpClient = httpClientFactory.CreateClient("RemoteServiceClient");
    }

    public async Task<string> GetDataAsync()
    {
        var response = await _httpClient.GetAsync("https://jsonplaceholder.typicode.com/posts/1");
        response.EnsureSuccessStatusCode();

        return await response.Content.ReadAsStringAsync();
    }
}