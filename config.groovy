sdkmanCliVersion = '5.0.0-SNAPSHOT'
environments {
	local {
		sdkmanService = 'http://localhost:8080'
		sdkmanBroadcastService = 'http://localhost:8080'
		sdkmanBrokerService = 'http://localhost:8080'
	}
	production {
		sdkmanService = 'https://api.sdkman.io'
		sdkmanBroadcastService = 'http://cast.sdkman.io'
		sdkmanBrokerService = 'http://broker.sdkman.io'
	}
}
