#!/bin/sh
cd $1
total_files="$(ls|grep $2|wc -l)"
training_count=$((total_files * 3/5)) 
test_count=$((total_files * 4/5))
validation_count=$((total_files))
mkdir ../validation_$2
mkdir ../test_$2
mkdir ../training_$2
#echo $training_count
count=0
input_files=*.$2
for input_file in $input_files
do
	
	if [ $count -lt $training_count ]
	then
		cp $input_file ../training_$2/$input_file
	elif [ $count -lt $test_count ]
	then
		cp $input_file ../test_$2/$input_file
	else
		cp $input_file ../validation_$2/$input_file
	fi
	count=$((count+1))
done
#while [ $total_files -gt 0 ] 
#do
	#echo $total_files
	#total_files=$((total_files-1))
	#$total_files = $total_files - 1
	
#done
