#!/bin/sh
cd $1

count=0
input_files=*.$2
for input_file in $input_files
do

		if [ $count -lt $3 ]
		then
		mv $input_file ../validation_fast5/$input_file
		else
		break
		fi
	count=$((count+1))
done
