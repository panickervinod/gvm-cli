package sdkman.steps

import java.util.zip.ZipException
import java.util.zip.ZipFile

import static cucumber.api.groovy.EN.*
import static sdkman.stubs.WebServiceStub.primeEndpointWithString
import static sdkman.stubs.WebServiceStub.primeSelfupdate

import sdkman.env.SdkmanBashEnvBuilder

And(~'^the sdkman work folder is created$') { ->
    assert sdkmanDir.isDirectory(), "The SDKMAN directory does not exist."
}

And(~'^the "([^"]*)" folder exists in user home$') { String arg1 ->
    assert sdkmanDir.isDirectory(), "The SDKMAN directory does not exist."
}

And(~'^the archive for candidate "([^"]*)" version "([^"]*)" is corrupt$') { String candidate, String version ->
	try {
		new ZipFile(new File("src/test/resources/__files/${candidate}-${version}.zip"))
		assert false, "Archive was not corrupt!"

	} catch (ZipException ze){
		//expected behaviour
	}
}

And(~'^the archive for candidate "([^"]*)" version "([^"]*)" is removed$') { String candidate, String version ->
	def archive = new File("${sdkmanDir}/archives/${candidate}-${version}.zip")
	assert ! archive.exists()
}

And(~'^the internet is reachable$') {->
    primeEndpointWithString("/broadcast/latest/id", "12345")
    primeEndpointWithString("/broadcast/latest", "broadcast message")
    primeEndpointWithString("/app/version", sdkmanVersion)
    primeSelfupdate()

    offlineMode = false
    serviceUrlEnv = SERVICE_UP_URL
    javaHome = FAKE_JDK_PATH
}

And(~'^the internet is not reachable$') {->
    offlineMode = false
    serviceUrlEnv = SERVICE_DOWN_URL
    javaHome = FAKE_JDK_PATH
}

And(~'^offline mode is disabled with reachable internet$') {->
    primeEndpointWithString("/broadcast/latest/id", "12345")
    primeEndpointWithString("/broadcast/latest", "broadcast message")
    primeEndpointWithString("/app/version", sdkmanVersion)

    offlineMode = false
    serviceUrlEnv = SERVICE_UP_URL
    javaHome = FAKE_JDK_PATH
}

And(~'^offline mode is enabled with reachable internet$') {->
    primeEndpointWithString("/broadcast/latest/id", "12345")
    primeEndpointWithString("/broadcast/latest", "broadcast message")
    primeEndpointWithString("/app/version", sdkmanVersion)

    offlineMode = true
    serviceUrlEnv = SERVICE_UP_URL
    javaHome = FAKE_JDK_PATH
}

And(~'^offline mode is enabled with unreachable internet$') {->
    offlineMode = true
    serviceUrlEnv = SERVICE_DOWN_URL
    javaHome = FAKE_JDK_PATH
}

And(~'^an initialised environment$') {->
    bash = SdkmanBashEnvBuilder.create(sdkmanBaseDir)
        .withOfflineMode(offlineMode)
        .withService(serviceUrlEnv)
        .withBroadcastService(serviceUrlEnv)
        .withJdkHome(javaHome)
        .withHttpProxy(HTTP_PROXY)
        .withVersionToken(sdkmanVersion)
        .withSdkmanVersion(sdkmanVersion)
        .build()
}

And(~'^an outdated initialised environment$') {->
    bash = SdkmanBashEnvBuilder.create(sdkmanBaseDir)
        .withOfflineMode(offlineMode)
        .withService(serviceUrlEnv)
        .withBroadcastService(serviceUrlEnv)
        .withJdkHome(javaHome)
        .withHttpProxy(HTTP_PROXY)
        .withVersionToken(sdkmanVersionOutdated)
        .withSdkmanVersion(sdkmanVersionOutdated)
        .build()

    def twoDaysAgoInMillis = System.currentTimeMillis() - 172800000

    def upgradeToken = "$sdkmanDir/var/delay_upgrade" as File
    upgradeToken.createNewFile()
    upgradeToken.setLastModified(twoDaysAgoInMillis)

    def versionToken = "$sdkmanDir/var/version" as File
    versionToken.setLastModified(twoDaysAgoInMillis)

    def initFile = "$sdkmanDir/bin/sdkman-init.sh" as File
    initFile.text = initFile.text.replace(sdkmanVersion, sdkmanVersionOutdated)
}

And(~'^the system is bootstrapped$') {->
    bash.start()
    bash.execute("source $sdkmanDirEnv/bin/sdkman-init.sh")
}

And(~'^the system is bootstrapped again$') {->
    bash.execute("source $sdkmanDirEnv/bin/sdkman-init.sh")
}

And(~/^the sdkman version is "([^"]*)"$/) { String version ->
    sdkmanVersion = version
}