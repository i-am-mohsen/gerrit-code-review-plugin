// Copyright (C) 2022 Réda Housni Alaoui <reda-alaoui@hey.com>
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package jenkins.plugins.gerrit;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.net.URISyntaxException;
import javax.annotation.CheckForNull;
import jenkins.scm.api.trait.SCMSourceBuilder;
import org.eclipse.jgit.transport.URIish;

public class GerritSCMSourceBuilder
    extends SCMSourceBuilder<GerritSCMSourceBuilder, GerritSCMSource> {

  private final String id;
  private final GerritURI gerritURI;
  private final boolean insecureHttps;
  @CheckForNull private final String credentialsId;
  @CheckForNull private final String cloudflareClientIdCredentialId;
  @CheckForNull private final String cloudflareClientSecretCredentialId;

  public GerritSCMSourceBuilder(
      String id,
      @NonNull String projectName,
      GerritURI gerritURI,
      boolean insecureHttps,
      @CheckForNull String credentialsId,
      @CheckForNull String cloudflareClientIdCredentialId,
      @CheckForNull String cloudflareClientSecretCredentialId) {
    super(GerritSCMSource.class, projectName);
    this.id = id;
    this.gerritURI = gerritURI;
    this.insecureHttps = insecureHttps;
    this.credentialsId = credentialsId;
    this.cloudflareClientIdCredentialId = cloudflareClientIdCredentialId;
    this.cloudflareClientSecretCredentialId = cloudflareClientSecretCredentialId;
  }

  @NonNull
  @Override
  public GerritSCMSource build() {
    URIish projectUri;
    try {
      projectUri = gerritURI.setProject(projectName());
    } catch (URISyntaxException e) {
      throw new IllegalStateException(e);
    }

    GerritSCMSource scmSource = new GerritSCMSource(projectUri.toString());
    scmSource.setId(id);
    scmSource.setCredentialsId(credentialsId);
    scmSource.setInsecureHttps(insecureHttps);
    scmSource.setCloudflareClientIdCredentialId(cloudflareClientIdCredentialId);
    scmSource.setCloudflareClientSecretCredentialId(cloudflareClientSecretCredentialId);
    scmSource.setTraits(traits());
    return scmSource;
  }
}
