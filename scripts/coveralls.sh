#!/bin/bash
COVERALLS_URL='https://coveralls.io/api/v1/jobs'

json=$(cat target/coverage/coveralls.json)
json="${json::-1}, \"service_pull_request\": \"${CI_PULL_REQUEST}\", \"repo_token\": \"${COVERALLS_REPO_TOKEN}\", \"service_number\": \"${CI_BUILD_NUMBER}\", \"service_branch\": \"${CI_BRANCH}\", \"service_build_url\": \"${CI_BUILD_URL}\", \"git\": {\"branch\": \"${GIT_BRANCH}\", \"id\": \"${GIT_ID}\", \"author_name\": \"${GIT_AUTHOR_NAME}\", \"author_email\": \"${GIT_AUTHOR_NAME}\", \"committer_name\": \"${GIT_COMMITER_NAME}\", \"committer_email\": \"${GIT_COMMITER_EMAIL}\", \"message\": \"${GIT_MESSAGE}\"}}"

echo $json | curl -F 'json_file=@-' "$COVERALLS_URL"
