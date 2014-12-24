for f in *.nb.predictedfasta; do
base=`basename $f .nb.predictedfasta`
mv $f $base.nb-output.txt
done
