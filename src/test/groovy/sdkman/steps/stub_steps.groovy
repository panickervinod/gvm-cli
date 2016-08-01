package sdkman.steps

import static cucumber.api.groovy.EN.And
import static sdkman.stubs.WebServiceStub.*
import static sdkman.support.FilesystemUtils.readCurrentFromCandidateFolder
import static sdkman.support.FilesystemUtils.readVersionsCsvFromCandidateFolder

And(~'^the default "([^"]*)" candidate is "([^"]*)"$') { String candidate, String version ->
    primeEndpointWithString("/candidates/${candidate}/default", version)
    primeDownloadFor(SERVICE_UP_URL, candidate, version, PLATFORM)
}

And(~'^an available selfupdate$') { ->
    primeEndpointWithString("/selfupdate", 'echo "Successfully upgraded SDKMAN."')
}

And(~'^the candidate "([^"]*)" version "([^"]*)" is available for download$') { String candidate, String version ->
    primeEndpointWithString("/candidates/${candidate}/${version}", "valid")
    primeDownloadFor(SERVICE_UP_URL, candidate, version, PLATFORM)
}

And(~'^the candidate "([^"]*)" version "([^"]*)" is not available for download$') { String candidate, String version ->
    primeEndpointWithString("/candidates/${candidate}/${version}", "invalid")
}

And(~'^a "([^"]*)" list view is available for consumption$') { String candidate ->
    primeEndpointWithString("/candidates/${candidate}/list?current=&installed=", "Available ${candidate.capitalize()} Versions")
}

And(~'^the candidate "([^"]*)" version "([^"]*)" is a valid candidate version$') { String candidate, String version ->
    primeEndpointWithString("/candidates/${candidate}/${version}", "valid")
}

And(~'^the candidate "([^"]*)" version "([^"]*)" is not a valid candidate version$') { String candidate, String version ->
    primeEndpointWithString("/candidates/${candidate}/${version}", "invalid")
}

And(~/^the candidate "(.*?)" has a version list available$/) { String candidate ->
    def current = readCurrentFromCandidateFolder(candidatesDir, candidate)
    def versions = readVersionsCsvFromCandidateFolder(candidatesDir, candidate)
    def url = "/candidates/${candidate}/list?current=${current}&installed=${versions}"
    println("Priming url: $url")
    primeEndpointWithString(url, "Candidate: $candidate; Versions: $versions; Current: $current")
}

And(~/^The candidate list is available$/) { ->
    primeEndpointWithString("/candidates/list", "Candidate List")
}
