#!/usr/bin/env bash
# CI owns this Appium server. Local development may supply its own server.
set -euo pipefail
mkdir -p reports
appium --address 127.0.0.1 --port 4723 > reports/appium.log 2>&1 &
server_pid=$!
trap 'kill "$server_pid" 2>/dev/null || true' EXIT
for attempt in {1..60}; do
  if curl --silent --fail http://127.0.0.1:4723/status >/dev/null; then break; fi
  if ! kill -0 "$server_pid" 2>/dev/null; then cat reports/appium.log; exit 1; fi
  sleep 1
done
curl --silent --fail http://127.0.0.1:4723/status >/dev/null
python -m robot --outputdir reports tests
