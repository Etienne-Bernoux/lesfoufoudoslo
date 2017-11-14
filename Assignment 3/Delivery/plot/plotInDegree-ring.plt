set terminal png enhanced
set output 'plotInDegree-ring.png'

set title "In-degree distribution (ring)"
set xlabel "in-degree"
set ylabel "number of nodes"
set key right top
plot "ring/id30.txt" title 'Basic Shuffle c = 30' with histeps, \
	"random/idRandom30.txt" title 'Random Graph c = 30' with histeps, \
	"ring/id50.txt" title 'Basic Shuffle c = 50' with histeps, \
	"random/idRandom50.txt" title 'Random Graph c = 50' with histeps
	