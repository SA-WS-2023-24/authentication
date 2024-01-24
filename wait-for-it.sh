#!/bin/sh

set -e

host="$1"
shift
cmd="$@"

echo "Waiting for $host to be ready..."

until $(curl --output /dev/null --silent --head --fail http://$host); do
    echo '.'
    sleep 5
done

echo "Keycloak is up - executing command"
exec $cmd