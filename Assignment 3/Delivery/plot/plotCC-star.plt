# You can uncomment the following lines to produce a png figure
set terminal png enhanced
set output 'plotCC-star.png'

set title "Average Clustering Coefficient (star)"
set xlabel "cycles"
set ylabel "clustering coefficient (log)"
set key right top
set logscale y 
plot "random/ccRandom30.txt" title 'Random Graph c = 30' with lines, \
	"star/cc30.txt" title 'Shuffle c = 30' with lines, \
	"random/ccRandom50.txt" title 'Random Graph c = 50' with lines, \
	"star/cc50.txt" title 'Shuffle c = 50' with lines