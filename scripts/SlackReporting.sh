#!/usr/bin/env bash

function usage {
	programName=$0
	echo "description: use this program to post messages to Slack channel"
	echo "usage: $programName [ -t \"sample title\"]  [-b \"message body\"] [-c \"mychannel\"] [-u \" url\"]"
	echo "	-t 	the title of hte message you are reporting"
	echo "	-b 	The message body"
	echo "	-c 	The channel you are posting to"
	echo "	-u 	The slcak hook url to post to"
	exit 1
}

while getopts ":t:b:c:u:h" opt; do
	case ${opt} in 
		t) msgTitle="$OPTARG"
		;;
		u) slackUrl="$OPTARG"
		;;
		b) msgBody="$OPTARG"
		;;
		c) channelName="$OPTARG"
		;;
		h) usage
		;;
		\?) echo "Invalid option -$OPTARG" >&2
		;;
	esac
done

if [[ ! "${msgTitle}" || ! "${slackUrl}" || ! "${msgBody}" || ! "${channelName}" ]]; then
	echo "all arguments are required"
	usage
fi

read -d '' payLoad << EOF
{
	"channel": "#${channelName}",
	"username": "AUTOMATION",
	"icon_emoji": ":sunglasses:",
	"attachments": [
	{
		"fallback": "${msgTitle}",
		"color": "good",
		"title": "${msgTitle}",
		"fields": [{
			"value": "${msgBody}",
			"short": false
		}]
	}]
}
EOF

statusCode=$(curl -X POST -H 'Content-Type: application/json' --data "${payLoad}" ${slackUrl})

echo ${statusCode}
