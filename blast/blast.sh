#!/bin/bash

#Arguments
#$1 -> original genome full path
#$2 -> input directory
#$3 -> output directory

original_genome="$1"
original_genome_base=${original_genome##*/}
cp $original_genome .
makeblastdb -in $original_genome_base -dbtype nucl -parse_seqids

input_dir="$2"
output_dir="$3"

input_files=$input_dir/*.fasta
for input_file in $input_files
do
	input_file_base=${input_file##*/}
	input_file_prefix=${input_file_base%.*}
	output_file_base=$input_file_prefix".out"
	output_file=$output_dir/$output_file_base
	blastn -db $original_genome_base -query $input_file -out $output_file -outfmt '6 qstart qend qseq sseq' -max_target_seqs 1
done

rm $original_genome_base*
