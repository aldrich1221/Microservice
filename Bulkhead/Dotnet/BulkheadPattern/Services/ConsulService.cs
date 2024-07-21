using Consul;
using Microsoft.Extensions.Hosting;
using System;
using System.Threading;
using System.Threading.Tasks;

public class ConsulService : IHostedService
{
    private readonly IConsulClient _consulClient;
    private readonly string _serviceId;
    private readonly string _serviceName = "Example";

    public ConsulService(IConsulClient consulClient)
    {
        _consulClient = consulClient;
        _serviceId = Guid.NewGuid().ToString();
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        var registration = new AgentServiceRegistration
        {
            ID = _serviceId,
            Name = _serviceName,
            Address = "localhost",
            Port = 5001,
            Checks = new[]
            {
                new AgentServiceCheck
                {
                    HTTP = "http://localhost:5001/health",
                    Interval = TimeSpan.FromSeconds(30),
                    Timeout = TimeSpan.FromSeconds(5)
                }
            }
        };

        await _consulClient.Agent.ServiceRegister(registration, cancellationToken);
    }

    public async Task StopAsync(CancellationToken cancellationToken)
    {
        await _consulClient.Agent.ServiceDeregister(_serviceId, cancellationToken);
    }
}