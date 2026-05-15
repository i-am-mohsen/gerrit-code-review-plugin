package jenkins.plugins.gerrit;

import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.http.HttpClientBuilderExtension;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.impl.client.HttpClientBuilder;

public class CloudflareAccessClientBuilderExtension extends HttpClientBuilderExtension {
  private final String clientId;
  private final String clientSecret;

  public CloudflareAccessClientBuilderExtension(String clientId, String clientSecret) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @Override
  public HttpClientBuilder extend(HttpClientBuilder httpClientBuilder, GerritAuthData authData) {
    httpClientBuilder.addInterceptorLast(
        (HttpRequestInterceptor)
            (request, context) -> {
              request.setHeader("CF-Access-Client-Id", clientId);
              request.setHeader("CF-Access-Client-Secret", clientSecret);
            });
    return super.extend(httpClientBuilder, authData);
  }
}
