using Consul;
using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using System.Net.Http;
using System.Threading.Tasks;
using Polly;

using Polly.CircuitBreaker;
using Microsoft.Extensions.Http;
using Polly.Extensions.Http;
using static policy.ResiliencyPolicy;
public class Startup
{
    public void ConfigureServices(IServiceCollection services)
    {
        var consulAddress = Environment.GetEnvironmentVariable("CONSUL_ADDRESS") ?? "http://localhost:8500";

        services.AddControllers();
        services.AddSingleton<IConsulClient, ConsulClient>(p => new ConsulClient(consulConfig =>
        {
            consulConfig.Address = new Uri(consulAddress);
        }));


        services.AddHttpClient("MyHttpClient")
                //.AddPolicyHandler(GetCircuitBreakerPolicy())
                //.AddPolicyHandler(GetBulkheadPolicy())
                ;
        services.AddSingleton<IRemoteService, RemoteService>();

        services.AddHostedService<ConsulService>();

        services.AddCors(options =>
        {
            options.AddPolicy("CorsPolicy",
                builder => builder
                    .AllowAnyOrigin()
                    .AllowAnyMethod()
                    .AllowAnyHeader());
        });


    }


  

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    {

        //1 Exception handler
        if (env.IsDevelopment())
        {
            app.UseDeveloperExceptionPage();
            //app.UseDatabaseErrorPage();
        }
        else
        {
            app.UseExceptionHandler("/api/error");
            app.UseHsts();
        }

        //2.https redirect
        app.UseHttpsRedirection();
        app.UseStaticFiles();
        //app.UseCookiePolicy();


        app.UseRouting();
        app.UseCors("CorsPolicy");

        //app.UseAuthentication();
        //app.UseAuthorization();
        //app.UseSession();


    
       
        app.UseWhen(context => context.Request.Path.StartsWithSegments("/api"), ap =>
        {
            ap.UseAPILoggerMiddleware();
        });

   
        app.UseEndpoints(endpoints =>
        {
            endpoints.MapControllers();
            
        });
    }
}