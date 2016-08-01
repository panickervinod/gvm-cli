#!/bin/bash

#
#   Copyright 2012 Marco Vermeulen
#
#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at
#
#       http://www.apache.org/licenses/LICENSE-2.0
#
#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.
#

function __sdkman_echo_debug {
	if [[ "$SDKMAN_DEBUG_MODE" == 'true' ]]; then
		echo "$1"
	fi
}

function __sdkman_secure_curl {
	if [[ "${sdkman_insecure_ssl}" == 'true' ]]; then
		curl --insecure --silent --location "$1"
	else
		curl --silent --location "$1"
	fi
}

function __sdkman_secure_curl_download {
	if [[ "${sdkman_insecure_ssl}" == 'true' ]]; then
		curl --insecure --progress-bar --location "$1"
	else
		curl --progress-bar --location "$1"
	fi
}

function __sdkman_secure_curl_with_timeouts {
	if [[ "${sdkman_insecure_ssl}" == 'true' ]]; then
		curl --insecure --silent --location --connect-timeout ${sdkman_curl_connect_timeout} --max-time ${sdkman_curl_max_time} "$1"
	else
		curl --silent --location --connect-timeout ${sdkman_curl_connect_timeout} --max-time ${sdkman_curl_max_time} "$1"
	fi
}

function __sdkman_page {
    local PAGER="${PAGER-$(which less)}"

    if [[ -n "$PAGER" ]]; then
        "$@" | "$PAGER"
    else
        "$@"
    fi
}
