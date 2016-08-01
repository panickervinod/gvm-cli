package sdkman.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.*

class WebServiceStub {

    static primeEndpointWithString(String endpoint, String body) {
        stubFor(get(urlEqualTo(endpoint)).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody(body)))
    }

    static primeEndpointWithBinary(String endpoint, byte[] body) {
        stubFor(get(urlEqualTo(endpoint)).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/octet-stream")
                        .withBody(body)))
    }

    static primeDownloadFor(String host, String candidate, String version, String platform) {
        stubFor(get(urlEqualTo("/download/${candidate}/${version}?platform=${platform}")).willReturn(
                aResponse()
                        .withHeader("Location", "${host}/${candidate}-${version}.zip")
                        .withStatus(302)))

        def binary = "${candidate}-${version}.zip"
        stubFor(get(urlEqualTo("/$binary")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/zip")
                        .withBodyFile(binary)))
    }

    static primeSelfupdate() {
        stubFor(get(urlEqualTo("/selfupdate")).willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBodyFile("selfupdate.sh")))
    }

}
