set terminal png enhanced
set output 'plotInDegree.png'

set title "In-degree distribution"
set xlabel "in-degree"
set ylabel "number of nodes"
set key right top
plot "id30.txt" title 'Basic Shuffle c = 30' with histeps, \
	"idRandom30.txt" title 'Random Graph c = 30' with histeps, \
	"id50.txt" title 'Basic Shuffle c = 50' with histeps, \
	"idRandom50.txt" title 'Random Graph c = 50' with histeps
	