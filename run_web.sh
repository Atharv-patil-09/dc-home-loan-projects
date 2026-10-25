#!/usr/bin/env bash
echo "Starting BANKCORE Web Console at http://localhost:3000..."
if which xdg-open > /dev/null; then
  xdg-open "http://localhost:3000" &
elif which open > /dev/null; then
  open "http://localhost:3000" &
fi
python3 -m http.server 3000 --directory frontend
