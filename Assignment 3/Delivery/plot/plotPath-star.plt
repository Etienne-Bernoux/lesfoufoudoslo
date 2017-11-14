# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plotPath-star.png'

set title "Average Path Length (star)"
set xlabel "cycles"
set ylabel "Average Path Length (log)"
set key right top
set logscale y 
plot "random/paRandom30.txt" title 'Random Graph c = 30' with lines, \
	"star/pa30.txt" title 'Shuffle c = 30' with lines, \
	"random/paRandom50.txt" title 'Random Graph c = 50' with lines, \
	"star/pa50.txt" title 'Shuffle c = 50' with lines