#!/bin/bash
if [ "$(sudo nm-tool | grep "State: connected" | wc -l)" == 1 ]; then
  python /home/a/scripts/new.py
else
  printf "\0"
fi

