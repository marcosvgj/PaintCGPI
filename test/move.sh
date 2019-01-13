#!/bin/bash
# Change all directories names (removing a specific substring) from a parent directory
# Obs.: this runs inside the wished directory

for folder in *;
    do
        if [ ${folder} != "move.sh" ];then 
	    mv ${folder} ${folder//$1/};
	fi
    done



