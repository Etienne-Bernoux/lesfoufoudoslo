# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plotPath.png'

set title "Average Path Length"
set xlabel "cycles"
set ylabel "Average Path Length (log)"
set key right top
set logscale y 
plot "apRandom30.txt" title 'Random Graph c = 30' with lines, \
	"ap30.txt" title 'Shuffle c = 30' with lines, \
	"apRandom50.txt" title 'Random Graph c = 50' with lines, \
	"ap50.txt" title 'Shuffle c = 50' with lines