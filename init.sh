#!/bin/bash

echo "Checking your device info for testing your device deviation."
echo "Please enter your information"
echo -n "api_key : "
read api_key
echo -n "api_secret : "
read api_secret

curl -d  "api_key=$api_key&api_secret=$api_secret" -X POST https://oa.tapaculo365.com/tp365/v1/account/check | jq . > check.json

verify_api=$(jq .status check.json)

if [ $verify_api == true ]; then
	echo "VERIFY SUCCESS !!!"
else
	echo "VERIFY FAIL !!!"
	echo "PLEASE CHECK YOUR API KEY AND API SECRET"
	echo "SHELL SCRIPT IS DONE."
	exit
fi

echo -n "device mac address : "
read device_mac
echo -n "sensor mac address : "
read sensor_mac

echo "Get historical time data for the specified node."
echo "Please enter your data Start Time like 'yyyy-MM-dd-HH-mm-ss'"
start_time=`java trans_timestamp`
echo "Please enter your data End Time like 'yyyy-MM-dd-HH-mm-ss'"
end_time=`java trans_timestamp`

echo "Get the list and current value of all channels owned by yours. Please enter device info."
echo -n "search : "
read search_channel

curl -d  "api_key=$api_key&api_secret=$api_secret&search=$search_channel" -X POST https://oa.tapaculo365.com/tp365/v1/channel/get_lst | jq . > channel_get_lst.json

curl -d  "api_key=$api_key&api_secret=$api_secret&device_mac=$device_mac&sensor_mac=$sensor_mac&start_time=$start_time&end_time=$end_time" -X POST https://oa.tapaculo365.com/tp365/v1/channel/get_olddata | jq . > get_olddata.json

curl -d  "api_key=$api_key&api_secret=$api_secret&device_mac=$device_mac&sensor_mac=$sensor_mac" -X POST https://oa.tapaculo365.com/tp365/v1/channel/get_recentdata | jq. > get_recentdata.json

echo "Get the current value of the specified channel. Please enter sensor id."
echo -n "sensor id : "
read sensor_id

curl -d  "api_key=$api_key&api_secret=$api_secret&sensor_id=$sensor_id" -X POST https://oa.tapaculo365.com/tp365/v1/channel/get_value | jq . > get_value.json

echo "Get multiple specified channel values at once. Please enter sensors (using ',')."
echo -n "sensors : "
read sensors

curl -d  "api_key=$api_key&api_secret=$api_secret&sensors=$sensors" -X POST https://oa.tapaculo365.com/tp365/v1/channel/get_values | jq . > get_values.json

echo "Get specific device information. Please enter device MAC."
echo -n "MAC : "
read mac

curl -d  "api_key=$api_key&api_secret=$api_secret&MAC=$mac" -X POST https://oa.tapaculo365.com/tp365/v1/device/get_info | jq . > get_info.json

echo "Get device list. Please enter device info."
echo -n "search : "
read search

curl -d  "api_key=$api_key&api_secret=$api_secret&search=$search" -X POST https://oa.tapaculo365.com/tp365/v1/device/get_lst | jq . > get_lst.json
