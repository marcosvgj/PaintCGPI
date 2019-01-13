#!/bin/bash

# Check if the current repository on the loop already exists on the Semantix/Open Galaxy
# If exists just pass. Else, move a new repository to there 

for folder in *;
do
    SITE_REPO_URL=https://gitlab.com/Semantix/OpenGalaxy/${folder}
    git ls-remote "$SITE_REPO_URL" &>-
    if [ "$?" -ne 0 ]; then
	if [ ${folder} != "move.sh" ] && [ ${folder} != "test_push.sh" ];then 
	    # Build pipeline
	    if [ ! -f "/${folder}/Dockerfile" ]; then
		#check if exists a Dockerfile
		if [ ! -f "/${folder}/docker-compose.yml" ]; then
		    #check if exists a Docker compose
		    if [ ! -f "/${folder}/.gitlab-ci.yml" ]; then
		        # Creates a new repo
            	        echo "[OPG] Insert a new api project inside Semantix/OpenGalaxy: '$SITE_REPO_URL'"
			echo "[OPG] git commit -m'$SITE_REPO_URL'"
		    fi
	        fi  
	    fi
	fi
    else		
        echo "[OPG] Already exists on Semantix/OpenGalaxy: '$SITE_REPO_URL'" 
    fi
done
	
