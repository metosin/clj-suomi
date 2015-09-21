#!/bin/bash
COVERALLS_URL='https://coveralls.io/api/v1/jobs'

json=$(cat target/coverage/coveralls.json)
json="${json::-1}, \"service_pull_request\": \"${CI_PULL_REQUEST}\", \"repo_token\": \"${COVERALLS_REPO_TOKEN}\"}"

echo $json | curl -F 'json_file=@-' "$COVERALLS_URL"
