## This script gets you the fasta, events and the alignedfasta files
## using the poretools command. The script assumes you have a local
## blast alignment tool
## Input Argument:
##                $1 -> poretools binary (full path if not in PATH)
##                $2 -> directory containing fast5 file
##                $3 -> original genome db
##                $4 -> output dir of the fasta files
##                $5 -> output dir of the events files
##                $6 -> output dir of the alignedfasta files
##                $7 -> blast script
##                $8 -> remove gap script
##                $9 -> feature vector extraction script
## 		  $10 -> output dir for FVs of svm
##		  $11 -> output dir for FVs of bayes

## Parse the input arguments

## poretools cmd
my $cmd = $ARGV[0];
chomp($cmd);

## fast5 directory
my $dir = $ARGV[1];
chomp($dir);
opendir(D, $dir);

## original genome db
my $orig_genome_db = $ARGV[2];
chomp($orig_genome_db);

## output dir for fasta
my $output_fasta_dir = $ARGV[3];
chomp($output_fasta_dir);

## output dir for events
my $output_events_dir = $ARGV[4];
chomp($output_events_dir);

## output dir for alignedfasta
my $output_alignedfasta_dir = $ARGV[5];
chomp($output_alignedfasta_dir);

## blast script
my $blast = $ARGV[6];
chomp($blast);

## remove gaps script
my $remove_gap = $ARGV[7];
chomp($remove_gap);

## feature extraction script binary
my $feature_extract = $ARGV[8];
chomp($feature_extract);

## svm output folder containing FVs
my $output_svm_dir = $ARGV[9];
chomp($output_svm_dir);

## Naive Bayes folder containing FVs
my $output_bayes_dir = $ARGV[10];
chomp($output_bayes_dir);

## Incase we need to keep the count
my $counter =0;

while(my $filename = readdir(D)) {


  ## Get the fast5 files
  if ($filename =~ /(.*)\.fast5$/) {
  $counter++;

    ## Extract the corresponding events
    my @output = `$cmd events $dir/$filename`;
    my $outputfile = ">./$output_events_dir/$1.events";
    open(FD, "$outputfile");
    for(my $i=0;$i<=$#output;$i++) {
      print FD "$output[$i]";
    }
    close(FD);

    ## Extract the corresponding fasta 
    my @output_fasta  = `$cmd fasta $dir/$filename`;
    my $outputfile_fasta = ">./$output_fasta_dir/$1.fasta";
    open(FD, "$outputfile_fasta");
    for(my $i=0;$i<=$#output_fasta;$i++) {
      ## Just get the template reads
      if ($output_fasta[$i] =~ /_template/) {
        print FD "$output_fasta[$i+1]";
      }
    }   
    close(FD);
    print "$counter\n";

    if ($counter == 750) {
      last;
    }
  }
}
    
print "\nEvent files extraction done";
print "\nFasta files extraction done";

## We should be having the fasta files in the $output_fasta_dir directory
system( "sh", "$blast", "$orig_genome_db", "$output_fasta_dir", "$output_alignedfasta_dir");
if ($? == -1) {
  print "failed to execute $blast: $!\n";
}

print "\n\nBlast script executed successfully";
## Remove the gaps in the out files
system("javac", "$remove_gap".".java");
system("java", "$remove_gap", "$output_alignedfasta_dir");
if ($? == -1) {
  print "failed to execute $remove_gap: $!\n";
}

print "\n\nremove gap script executed successfully";
## Remove the out files in the aligned fasta dir
`rm $output_alignedfasta_dir/*.out`;

## Run the feature vector extraction script

# open the aligned fasta dir and recurse through the files
my $alignedfasta_dir = $output_alignedfasta_dir;
chomp($alignedfasta_dir);
opendir(D1, $alignedfasta_dir);

while(my $alignedfasta_file = readdir(D1)) {

  ## Get the alignedfata file
  if ($alignedfasta_file =~ /(.*)\.alignedfasta$/) {
    my $eventfile = "$output_events_dir" . "/$1.events";
    my $alignedfastafile = "$alignedfasta_dir" . "/$alignedfasta_file";
    ## get the corresponding events file
    if (-e $eventfile) {
      ## Run the feature extraction script (P.S may need to shift to system)
      `$feature_extract $alignedfastafile $eventfile $output_svm_dir $output_bayes_dir`;
    }
    else {
      print "\n=== WARNING:- Corresponding events does not exist for alignedfasta file $alignedfasta_file ===";
    }
  }
}

print "\n\nextraction of feature vectors done"
