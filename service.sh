#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo $DIR

read -p "Enter"

# Keep the pwd in mind!
# Example: RUN="java -jar $DIR/target/magic.jar"
RUN="java -jar $DIR/target/code.web-1.0.0.jar server $DIR/target/application.yml"
NAME="webservice"

DATA_FILE=$2

PIDFILE=$DIR/target/$NAME.pid
LOGFILE=$DIR/target/$NAME.log

echo $PIDFILE

echo $LOGFILE

start() {
echo "start"
read -p "enter"
    if [ -f $PIDFILE ]; then
        if kill -0 $(cat $PIDFILE); then
            echo 'Service already running' >&2
            return 1
        else
            rm -f $PIDFILE
        fi
    fi
    local CMD="$RUN $DATA_FILE &> \"$LOGFILE\" & echo \$!"
	echo $CMD
    sh -c "$CMD" > $PIDFILE
	echo "end"
    read -p "enter"
}

stop() {
    if [ ! -f $PIDFILE ] || ! kill -0 $(cat $PIDFILE); then
        echo 'Service not running' >&2
        return 1
    fi
    kill -15 $(cat $PIDFILE) && rm -f $PIDFILE
}


case $1 in
    start)
        start
        ;;
    stop)
        stop
        ;;
    block)
        start
        sleep infinity
        ;;
    *)
        echo "Usage: $0 {start|stop|block} DATA_FILE"
esac