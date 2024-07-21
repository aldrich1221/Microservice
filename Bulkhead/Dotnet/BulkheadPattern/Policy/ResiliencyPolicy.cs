using Polly;
using Polly.Extensions.Http;

namespace policy
{
    public class ResiliencyPolicy
    {
        public static IAsyncPolicy<HttpResponseMessage> GetBulkheadPolicy()
        {
            return Policy.BulkheadAsync<HttpResponseMessage>(
                maxParallelization: 2,
                maxQueuingActions: 4,
                onBulkheadRejectedAsync: OnBulkheadRejectedAsync);
        }
        public static IAsyncPolicy<HttpResponseMessage> GetCircuitBreakerPolicy()
        {
            return HttpPolicyExtensions
                .HandleTransientHttpError()
                .CircuitBreakerAsync(5, TimeSpan.FromSeconds(30));
        }

        static Task OnBulkheadRejectedAsync(Context context)
        {
            Console.WriteLine("Bulkhead rejected execution.");
            return Task.CompletedTask;
        }
    }
}