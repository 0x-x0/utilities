#!/bin/bash
   #if computer is connected to internet then continue
if [ "$(sudo nm-tool | grep "State: connected" | wc -l)" == 1 ]; then
   #if connected device is ppp0 then file modification timestamp
  if [ "$(grep ppp0 /proc/net/dev | wc -l)" == 1 ]; then
  s="$(stat /run/network/ifup.ppp0 | grep Modify | sed 's/^.*Modify: //; s/ddd.*$//')"
   #file modification time in seconds
  a="$(date +'%s' -d "$s")" 
  d="$(date +%s)"
  COUNT=`expr $d - $a`
   if [ $COUNT == 1765 ]; then
     "$(notify-send "Please Disconnect Internet 30 sec Remaining")"
   fi
   if [ $COUNT == 1785 ]; then
     "$(notify-send "Please Disconnect Internet 10 sec Remaining")"
   fi
  echo - | awk -v "S=$COUNT" '{printf "%02dHr\n%02d:%02d",S/(60*60),S%(60*60)/60,S%60}'
  fi
   
   if [ "$(grep eth1 /proc/net/dev | wc -l)" == 1 ]; then
  s="$(stat /run/network/ifup.eth1 | grep Modify | sed 's/^.*Modify: //; s/ddd.*$//')"
   #file modification time in seconds
  a="$(date +'%s' -d "$s")" 
  d="$(date +%s)"
  COUNT=`expr $d - $a`
  echo - | awk -v "S=$COUNT" '{printf "%02dHr\n%02d:%02d",S/(60*60),S%(60*60)/60,S%60}'
  fi

 if [ "$(grep usb0 /proc/net/dev | wc -l)" == 1 ]; then
  s="$(stat /run/network/ifup.usb0 | grep Modify | sed 's/^.*Modify: //; s/ddd.*$//')"
   #file modification time in seconds
  a="$(date +'%s' -d "$s")" 
  d="$(date +%s)"
  COUNT=`expr $d - $a`
   if [ $COUNT == 1765 ]; then
     "$(notify-send "Please Disconnect Internet 30 sec Remaining")"
   fi
   if [ $COUNT == 1785 ]; then
     "$(notify-send "Please Disconnect Internet 10 sec Remaining")"
   fi
  echo - | awk -v "S=$COUNT" '{printf "%02dHr\n%02d:%02d",S/(60*60),S%(60*60)/60,S%60}'
  fi

else
  printf "\0"
fi
