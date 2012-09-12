import static cucumber.runtime.groovy.EN.*
import cucumber.runtime.PendingException

scriptPath = 'bin'
home = System.getProperty('user.home')
gvmDir = new File("${home}/.gvm")    

Given(~'^the default "([^"]*)" candidate is "([^"]*)"$') { String candidate, String version ->
	def candidateVersion = new URL("http://localhost:8080/${candidate}/version").text
	assert candidateVersion == version
}

Then(~'^the candidate "([^"]*)" version "([^"]*)" is installed$') { String candidate, String version ->
	def file = new File("${gvmDir}/${candidate}/${version}")
	assert file.exists()
}

When(~'^the candidate "([^"]*)" version "([^"]*)" is already installed$') { String candidate, String version ->
	def command = "gvm install $candidate $version"
	command = "$scriptPath/$command"
    def proc = command.execute()
    proc.waitFor()
    def result = "${proc.in.text}"
    assert result.contains("Done installing!")
}