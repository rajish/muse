export PATH=~/bin/play2:$PATH

rmdir target > /dev/null 2>&1
mkdir target > /dev/null 2>&1

# sudo mkfs -q /dev/ram1 65536
# sudo mount /dev/ram1 target
findmnt target
if [  $? ]; then
    sudo mount -t tmpfs -o size=1g none target
fi
