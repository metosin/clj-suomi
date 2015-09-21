#!/bin/bash
COVERALLS_URL='https://coveralls.io/api/v1/jobs'
curl -F 'json_file=@target/coverage/coveralls.json' "$COVERALLS_URL"
