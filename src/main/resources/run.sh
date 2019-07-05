#!/bin/bash
#########################################################################
# File Name: run.sh
# Function:
# Author: Mason
# Version: V1.0
# Created Time: 28/1/2019 11:41:48
#########################################################################

# chkconfig: - 98 33
# description: Starts and stops the java project daemon \
#              used to provide some java jar packet services.

source /etc/profile

# base env parameters setting
BASEDIR=$(dirname $(readlink -f $0))
APPNAME=goods
VERSION=1.0.0
PORT=8713

JAVAOPT="-Xms256m -Xmx512m -XX:PermSize=128M -XX:MaxPermSize=256M"


# define some functions
get_pid(){
	PID=$(ps -ef|grep ${APPNAME} |grep "java" |grep -v grep |awk '{print $2}')
}

start(){
	if [ -z ${PID} ];then
		nohup java ${JAVAOPT} -Dlogging.config=${BASEDIR}/logback-spring.xml -D$APPNAME  -jar ${BASEDIR}/${APPNAME}-${VERSION}.jar --spring.config.location=${BASEDIR}/application.yml &>/dev/null &

		inter=1
		time=20
		i=0
		while ((i < time));do
			get_pid
			#if [ $(netstat -lntup|grep -c $PID) -le 1 ];then
		    if((i>(time-2)));then
                if [ ${PID} ];then
                    echo "${APPNAME} started OK."
                    break
                else
                    sleep ${inter}
                    let i++
                fi
            else
                sleep ${inter}
                let i++
            fi
		done
		if ((i == time));then
			echo "${APPNAME} started FAIL in $((inter*time)) second!"
			exit 1
		fi
	else
		echo "${APPNAME} is still running with pid ${PID}!"
		exit 1
	fi
}

stop(){
	if [ ! -z ${PID} ];then
		kill -9 ${PID} && echo "${APPNAME} was killed."
	else
		echo "${APPNAME} is not running!"
		exit 1
	fi
}

restart(){
	stop
	start
}

status(){
	if [ ! -z ${PID} ];then
		echo "${APPNAME} is running with pid ${PID}"
	else
		echo "${APPNAME} is not running."
	fi
}

# the main program started
get_pid
case "$1" in
  start)
        start
        ;;
  stop)
        stop
        ;;
  restart)
        restart
        ;;
  status)
        status
        ;;
  *)
        echo $"Usage: $0 {start|stop|restart|status}"
        exit 2
esac
exit $?
