# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plotPath.png'

set title "Average Path Length"
set xlabel "cycles"
set ylabel "Average Path Length (log)"
set key right top
set logscale y 
plot "paRandom30.txt" title 'Random Graph c = 30' with lines, \
	"pa30.txt" title 'Shuffle c = 30' with lines, \
	"paRandom50.txt" title 'Random Graph c = 50' with lines, \
	"pa50.txt" title 'Shuffle c = 50' with lines