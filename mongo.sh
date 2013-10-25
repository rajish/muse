#!/bin/bash

if [ ! -d "db" ]; then
    mkdir db
    cat <<EOF
If it's the first run of mongod on the computer you have to enter mongo commandline:

     $ mongo --host localhost

and issue the command:

     > rs.initiate()

Hit ENTER to continue.
EOF
    read
fi

if [ ! -d "tmp" ]; then
    mkdir tmp
fi


mongod -vvv --rest --dbpath db --unixSocketPrefix tmp --replSet bzp
