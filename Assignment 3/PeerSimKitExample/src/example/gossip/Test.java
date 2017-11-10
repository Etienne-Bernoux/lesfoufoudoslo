package example.gossip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Test {
	
	private static List<Integer> getShuffleEntryWithout(int nbEntry, Integer forbidenEntry, List<Integer> cache)
	{
		List<Integer> subset = new ArrayList<Integer>(nbEntry);
		if(cache.isEmpty())
		{
			return subset;
		}
		List<Integer> indexes = new ArrayList<Integer>(cache.size());
		for(int i = 0; i < cache.size(); i ++)
		{
			indexes.add(i, i);
		}
		Collections.shuffle(indexes);
		boolean inside = false;
		int upto = Integer.min(nbEntry, cache.size());
		for(int i = 0; i < upto; i ++)
		{
			Integer e = cache.get(indexes.get(i));
			if (e.equals(forbidenEntry))
				inside = true;
			else
				subset.add(e);
		}
		/* And the end we check if  */
		if(inside && upto < cache.size())
			subset.add(cache.get(indexes.get(upto)));
		
		return subset;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		List<Integer> l1 = new ArrayList<Integer>();
		List<Integer> l2 =  new ArrayList<Integer>();
		List<Integer> l3 =  new ArrayList<Integer>();
		List<Integer> l4 =  new ArrayList<Integer>();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);
		

		System.out.println(l1);

		l3 = Test.getShuffleEntryWithout(5, null, l1);
		l4 = Test.getShuffleEntryWithout(5, null, l2);

		System.out.println(l3);
		System.out.println(l4);
		

	}

}
